package com.leadmap.environmentalrotection.web.controller;

import com.leadmap.environmentalrotection.common.md5.MD5Util;
import com.leadmap.environmentalrotection.common.util.*;
import com.leadmap.environmentalrotection.dao.*;
import com.leadmap.environmentalrotection.dto.*;
import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
import com.leadmap.environmentalrotection.entity.user.*;
import com.leadmap.environmentalrotection.service.IpInfoService;
import com.leadmap.environmentalrotection.service.JSMSExample;
import com.leadmap.environmentalrotection.service.UserHistoryInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 15:05
 */
@RestController
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    private final static String token = "token";

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserOperateDocInfoDao userOperateDocInfoDao;

    @Autowired
    private UserCommentInfoDao userCommentInfoDao;

    @Autowired
    private UserTokenMappedInfoDao userTokenMappedInfoDao;

    @Autowired
    private DocumentInfoDao documentInfoDao;

    @Autowired
    private UserLikeDocInfoDao userLikeDocInfoDao;

    @Autowired
    private UserCollectDocInfoDao userCollectDocInfoDao;

    @Autowired
    private UserHistoryInfoService userHistoryInfoService;

    @Autowired
    private OpinionFeedbackDao opinionFeedbackDao;

    @Autowired
    private OpinionFeedbackReplyDao opinionFeedbackReplyDao;

    @Value("${userdefaultpassword}")
    private String userdefaultpassword;

    @Autowired
    private IpInfoService ipInfoService;

    @Value("${optionFeedbackSave}")
    private String optionFeedbackSaveUrl;

    @Value("${optionFeedbackReplySave}")
    private String optionFeedbackReplySaveUrl;

    /**
     * 账号登录
     */
    @ApiOperation(value = "登录", notes = "登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "mobile", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "password", value = "password", required = true, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserAuthInfo> login(ServletRequest request, ServletResponse response, HttpSession session) {
        ResultObjInfo<UserAuthInfo> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            UserAuthInfo userAuthInfo = new UserAuthInfo();
            String mobile = request.getParameter("mobile");
            String password = request.getParameter("password");
            if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = userInfoDao.findByUserName(mobile);
            if (userInfo != null && MD5Util.MD5(password).equalsIgnoreCase(userInfo.getPassword())) {
                userAuthInfo.setUserName(mobile);
                String accessToken = UUID.randomUUID().toString().replace("-", "");
                userAuthInfo.setAccessToken(accessToken);
                userAuthInfo.setAvatarUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + userInfo.getAvatarUrl());
                userAuthInfo.setIsCertification(1);

                UserTokenMappedInfo userTokenMappedInfo = new UserTokenMappedInfo();
                userTokenMappedInfo.setToken(accessToken);
                userTokenMappedInfo.setUserId(userInfo.getId());
                userTokenMappedInfoDao.save(userTokenMappedInfo);
                session.setAttribute(token, accessToken);
            } else {
                if (userInfo == null) {
                    resultObjInfo.setCode("500");
                    resultObjInfo.setMsg("用户不存在");
                } else {
                    resultObjInfo.setCode("500");
                    resultObjInfo.setMsg("密码错误");
                }
                userAuthInfo.setUserName(mobile);
                userAuthInfo.setIsCertification(0);
            }
            //获取登录用户ip
            String ip = GetIpUtil.getIp((HttpServletRequest) request);
            String systemType = UserAgentUtil.getUserAgent((HttpServletRequest) request);
            ipInfoService.addRegisteredUserIpInfo(ip ,systemType ,mobile);

            resultObjInfo.setData(userAuthInfo);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    /**
     *用户退出
     */
    @ApiOperation(value = "退出", notes = "退出")
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserAuthInfo> logout(ServletRequest request, HttpSession session) {
        ResultObjInfo<UserAuthInfo> resultObjInfo = new ResultObjInfo<>();
        try {
            String token = request.getParameter("token");
            UserTokenMappedInfo userTokenMappedInfo = userTokenMappedInfoDao.findByToken(token);
            if (userTokenMappedInfo != null) {
                userTokenMappedInfoDao.delete(userTokenMappedInfo);
            }
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            // 移除session
            session.removeAttribute(token);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }


    @ApiOperation(value = "提交某个用户对某篇文章的点赞和收藏信息", notes = "提交某个用户对某篇文章的点赞和收藏信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "getIsLike", value = "getIsLike", required = true, dataType = "boolean", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "getIsCollect", value = "getIsCollect", required = true, dataType = "boolean", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "postUserOperateDocInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserOperateDocDTO> postUserOperateDocInfo(UserOperateDocDTO newObjInfo) {
        ResultObjInfo<UserOperateDocDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            if (newObjInfo == null) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = getUserInfoByToken(newObjInfo.getToken());

            Specification<UserOperateDocInfo> spec = new Specification<UserOperateDocInfo>() {
                @Override
                public Predicate toPredicate(Root<UserOperateDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("userId"), userInfo.getId()));
                    predicates.add(criteriaBuilder.equal(root.get("docId"), newObjInfo.getDocId()));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            Iterable<UserOperateDocInfo> iterable = userOperateDocInfoDao.findAll(spec);
            if (iterable != null && !((List<UserOperateDocInfo>) iterable).isEmpty()) {
                UserOperateDocInfo oldObjInfo = ((List<UserOperateDocInfo>) iterable).get(0);
                oldObjInfo.setIsCollect(newObjInfo.getIsCollect());
                oldObjInfo.setIsLike(newObjInfo.getIsLike());
                userOperateDocInfoDao.save(oldObjInfo);
            } else {
                UserOperateDocInfo userOperateDocInfo = new UserOperateDocInfo();
                userOperateDocInfo.setUserId(userInfo.getId().toString());
                userOperateDocInfo.setDocId(newObjInfo.getDocId());
                userOperateDocInfo.setIsLike(newObjInfo.getIsLike());
                userOperateDocInfo.setIsCollect(newObjInfo.getIsCollect());
                userOperateDocInfoDao.save(userOperateDocInfo);
            }
            resultObjInfo.setData(newObjInfo);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "新增某个用户对某篇文章的评论信息", notes = "新增某个用户对某篇文章的评论信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "content", value = "content", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "postUserCommentInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserCommentInfoDTO> postUserCommentInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<UserCommentInfoDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String token = request.getParameter("token");
            String docId = request.getParameter("docId");
            String content = request.getParameter("content");

            if (StringUtils.isBlank(token) || StringUtils.isBlank(docId) || StringUtils.isBlank(content)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = getUserInfoByToken(token);

            UserCommentInfo userCommentInfo = new UserCommentInfo();
            userCommentInfo.setUserId(userInfo.getId().toString());
            userCommentInfo.setDocId(docId);
            userCommentInfo.setContent(content);
            userCommentInfo.setCommentTime(new Date());
            userCommentInfoDao.save(userCommentInfo);

            DocumentInfo documentInfo = documentInfoDao.findById(Long.parseLong(docId));
            UserCommentInfoDTO userCommentInfoDTO = new UserCommentInfoDTO();
            userCommentInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userCommentInfoDTO.setCommentTime(f.format(userCommentInfo.getCommentTime()));
            userCommentInfoDTO.setDocId(docId);
            userCommentInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
            userCommentInfoDTO.setCommentContent(userCommentInfo.getContent());
            userCommentInfoDTO.setUserName(userInfo.getUserName());
            userCommentInfoDTO.setDocTitle(documentInfo.getTitle());
            userCommentInfoDTO.setPublishDate(documentInfo.getPublishDate());
            userCommentInfoDTO.setPublisher(documentInfo.getPublisher());
            userCommentInfoDTO.setImageUrl(documentInfo.getImageUrl());

            resultObjInfo.setData(userCommentInfoDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "修改某个用户对某篇文章的评论信息", notes = "修改某个用户对某篇文章的评论信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "commentId", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "content", value = "content", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "putUserCommentInfo", method = RequestMethod.PUT)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserCommentInfoDTO> putUserCommentInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<UserCommentInfoDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String commentId = request.getParameter("commentId");
            String content = request.getParameter("content");

            Specification<UserCommentInfo> spec = new Specification<UserCommentInfo>() {
                @Override
                public Predicate toPredicate(Root<UserCommentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("id"), commentId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            Iterable<UserCommentInfo> iterable = userCommentInfoDao.findAll(spec);
            if (iterable != null && !((List<UserCommentInfo>) iterable).isEmpty()) {
                UserCommentInfo oldObjInfo = ((List<UserCommentInfo>) iterable).get(0);
                oldObjInfo.setContent(content);
                oldObjInfo.setCommentTime(new Date());
                userCommentInfoDao.save(oldObjInfo);

                UserInfo userInfo = userInfoDao.findById(Long.parseLong(oldObjInfo.getUserId()));
                DocumentInfo documentInfo = documentInfoDao.findById(Long.parseLong(oldObjInfo.getDocId()));
                UserCommentInfoDTO userCommentInfoDTO = new UserCommentInfoDTO();
                userCommentInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
                Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                userCommentInfoDTO.setCommentTime(f.format(oldObjInfo.getCommentTime()));
                userCommentInfoDTO.setDocId(oldObjInfo.getDocId());
                userCommentInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
                userCommentInfoDTO.setCommentContent(oldObjInfo.getContent());
                userCommentInfoDTO.setUserName(userInfo.getUserName());
                userCommentInfoDTO.setDocTitle(documentInfo.getTitle());
                userCommentInfoDTO.setPublishDate(documentInfo.getPublishDate());
                userCommentInfoDTO.setPublisher(documentInfo.getPublisher());
                userCommentInfoDTO.setImageUrl(documentInfo.getImageUrl());
                resultObjInfo.setData(userCommentInfoDTO);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "删除某个用户对某篇文章的评论信息", notes = "删除某个用户对某篇文章的评论信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "commentId", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "deleteUserCommentInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<Boolean> deleteUserCommentInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<Boolean> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String commentId = request.getParameter("commentId");

            Specification<UserCommentInfo> spec = new Specification<UserCommentInfo>() {
                @Override
                public Predicate toPredicate(Root<UserCommentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("id"), commentId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            Iterable<UserCommentInfo> iterable = userCommentInfoDao.findAll(spec);
            if (iterable != null && !((List<UserCommentInfo>) iterable).isEmpty()) {
                UserCommentInfo oldObjInfo = ((List<UserCommentInfo>) iterable).get(0);
                oldObjInfo.setCommentTime(new Date());
                userCommentInfoDao.delete(oldObjInfo);
                resultObjInfo.setData(true);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(false);
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "新增点赞", notes = "新增点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "postUserLikeInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserLikeInfoDTO> postUserLikeInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<UserLikeInfoDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String token = request.getParameter("token");
            String docId = request.getParameter("docId");

            if (StringUtils.isBlank(token) || StringUtils.isBlank(docId)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = getUserInfoByToken(token);
            String userId = userInfo.getId().toString();
            UserLikeDocInfo userLikeDocInfo = userLikeDocInfoDao.findByDocIdAndUserId(docId,userId);
            if (userLikeDocInfo !=null) {
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("已点赞，不要重复点赞！");
            }else {
                userLikeDocInfo = new UserLikeDocInfo();
                userLikeDocInfo.setUserId(userId);
                userLikeDocInfo.setDocId(docId);
                userLikeDocInfo.setLikeTime(new Date());
                userLikeDocInfoDao.save(userLikeDocInfo);
            }

            DocumentInfo documentInfo = documentInfoDao.findById(Long.parseLong(docId));
            UserLikeInfoDTO userLikeInfoDTO = new UserLikeInfoDTO();
            userLikeInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userLikeInfoDTO.setLikeTime(f.format(userLikeDocInfo.getLikeTime()));
            userLikeInfoDTO.setDocId(docId);
            userLikeInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
            userLikeInfoDTO.setUserName(userInfo.getUserName());
            userLikeInfoDTO.setDocTitle(documentInfo.getTitle());
            userLikeInfoDTO.setPublishDate(documentInfo.getPublishDate());
            userLikeInfoDTO.setPublisher(documentInfo.getPublisher());
            userLikeInfoDTO.setImageUrl(documentInfo.getImageUrl());
            resultObjInfo.setData(userLikeInfoDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "取消点赞", notes = "取消点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "deleteUserLikeInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<Boolean> deleteUserLikeInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<Boolean> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String token = request.getParameter("token");
            String docId = request.getParameter("docId");

            if (StringUtils.isBlank(token) || StringUtils.isBlank(docId)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = getUserInfoByToken(token);

            Specification<UserLikeDocInfo> spec = new Specification<UserLikeDocInfo>() {
                @Override
                public Predicate toPredicate(Root<UserLikeDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("userId"), userInfo.getId()));
                    predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            userLikeDocInfoDao.delete(userLikeDocInfoDao.findAll(spec));
            resultObjInfo.setData(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(false);
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "新增收藏", notes = "新增收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "postUserCollectInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserCollectInfoDTO> postUserCollectInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<UserCollectInfoDTO> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String token = request.getParameter("token");
            String docId = request.getParameter("docId");

            if (StringUtils.isBlank(token) || StringUtils.isBlank(docId)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = getUserInfoByToken(token);
            String userId = userInfo.getId().toString();

            UserCollectDocInfo userCollectDocInfo = userCollectDocInfoDao.findByDocIdAndUserId(docId,userId);
            if (userCollectDocInfo !=null) {
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("已经收藏，不要重复收藏！");
            }else {
                userCollectDocInfo = new UserCollectDocInfo();
                userCollectDocInfo.setUserId(userInfo.getId().toString());
                userCollectDocInfo.setDocId(docId);
                userCollectDocInfo.setCollectTime(new Date());
                userCollectDocInfoDao.save(userCollectDocInfo);
            }
            DocumentInfo documentInfo = documentInfoDao.findById(Long.parseLong(docId));
            UserCollectInfoDTO userCollectInfoDTO = new UserCollectInfoDTO();
            userCollectInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            userCollectInfoDTO.setCollectTime(f.format(userCollectDocInfo.getCollectTime()));
            userCollectInfoDTO.setDocId(docId);
            userCollectInfoDTO.setAvatarUrl(userInfo.getAvatarUrl());
            userCollectInfoDTO.setUserName(userInfo.getUserName());
            userCollectInfoDTO.setDocTitle(documentInfo.getTitle());
            userCollectInfoDTO.setPublisher(documentInfo.getPublisher());
            userCollectInfoDTO.setPublishDate(documentInfo.getPublishDate());
            userCollectInfoDTO.setImageUrl(documentInfo.getImageUrl());

            resultObjInfo.setData(userCollectInfoDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "取消收藏", notes = "取消收藏")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "deleteUserCollectInfo", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<Boolean> deleteUserCollectInfo(ServletRequest request, HttpSession session) {
        ResultObjInfo<Boolean> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String token = request.getParameter("token");
            String docId = request.getParameter("docId");

            if (StringUtils.isBlank(token) || StringUtils.isBlank(docId)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = getUserInfoByToken(token);

            Specification<UserCollectDocInfo> spec = new Specification<UserCollectDocInfo>() {
                @Override
                public Predicate toPredicate(Root<UserCollectDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("userId"), userInfo.getId()));
                    predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            userCollectDocInfoDao.delete(userCollectDocInfoDao.findAll(spec));
            resultObjInfo.setData(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
            resultObjInfo.setData(false);
        }

        return resultObjInfo;
    }

    /**
     * 获取用户足迹
     *
     * @return
     */
    @ApiOperation(value = "获取用户足迹", notes = "获取用户足迹")
    @RequestMapping(value = "getUserHistoryInfo", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserHistoryInfo> getUserHistoryInfo(ServletRequest request) {
        ResultObjInfo<UserHistoryInfo> resultInfo = new ResultObjInfo<>();
        try {
            String token = request.getParameter("token");
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            if (StringUtils.isBlank(token)) {
                throw new IllegalArgumentException();
            }

            UserHistoryInfo userHistoryInfo = new UserHistoryInfo();
            userHistoryInfo.setUserCollectHistoryList(userHistoryInfoService.queryUserCollectHistorys(token));
            userHistoryInfo.setUserCommentHistoryList(userHistoryInfoService.queryUserCommentHistorys(token));
            userHistoryInfo.setUserLikeHistoryList(userHistoryInfoService.queryUserLikeHistorys(token));
            resultInfo.setData(userHistoryInfo);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
        }

        return resultInfo;
    }

    /**
     * 获取用户收藏足迹
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户收藏足迹", notes = "获取用户收藏足迹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getUserCollectHistoryList", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<UserCollectInfoDTO> getUserCollectHistoryList(ServletRequest request, HttpSession session) {
        ResultInfo<UserCollectInfoDTO> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");

            String token = request.getParameter("token");
            String strPageNumber = request.getParameter("pageNumber");
            String strPageSize = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(strPageNumber) && StringUtils.isNotBlank(strPageSize)) {
                long pageNumber = Long.parseLong(strPageNumber);
                long pageSize = Long.parseLong(strPageSize);
                List<UserCollectInfoDTO> userOperateHistoryInfoList = userHistoryInfoService.queryUserCollectHistorys(token, pageNumber, pageSize);
                long count = userHistoryInfoService.queryUserCollectHistoryCount(token);
                resultInfo.setRecordCount(count);
                long pageCount = count / pageSize;
                if (count % pageSize > 0) {
                    pageCount += 1;
                }
                resultInfo.setPageCount(pageCount);
                resultInfo.setData(userOperateHistoryInfoList);
            } else {
                List<UserCollectInfoDTO> userOperateHistoryInfoList = userHistoryInfoService.queryUserCollectHistorys(token);
                resultInfo.setData(userOperateHistoryInfoList);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }

    /**
     * 获取用户评论足迹
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户评论足迹", notes = "获取用户评论足迹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getUserCommentHistoryList", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<UserCommentInfoDTO> getUserCommentHistoryList(ServletRequest request, HttpSession session) {
        ResultInfo<UserCommentInfoDTO> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");

            String token = request.getParameter("token");
            String strPageNumber = request.getParameter("pageNumber");
            String strPageSize = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(strPageNumber) && StringUtils.isNotBlank(strPageSize)) {
                long pageNumber = Long.parseLong(strPageNumber);
                long pageSize = Long.parseLong(strPageSize);
                List<UserCommentInfoDTO> userCommentInfoDTOList = userHistoryInfoService.queryUserCommentHistorys(token, pageNumber, pageSize);

                if (userCommentInfoDTOList != null) {
                    for (UserCommentInfoDTO userCommentInfoDTO : userCommentInfoDTOList) {
                        if (!userCommentInfoDTO.getAvatarUrl().contains("images/")) {
                            userCommentInfoDTO.setAvatarUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + userCommentInfoDTO.getAvatarUrl());
                        }
                    }
                }

                long count = userHistoryInfoService.queryUserCommentHistoryCount(token);
                resultInfo.setRecordCount(count);
                long pageCount = count / pageSize;
                if (count % pageSize > 0) {
                    pageCount += 1;
                }
                resultInfo.setPageCount(pageCount);
                resultInfo.setData(userCommentInfoDTOList);
            } else {
                List<UserCommentInfoDTO> userOperateHistoryInfoList = userHistoryInfoService.queryUserCommentHistorys(token);
                resultInfo.setData(userOperateHistoryInfoList);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }

    /**
     * 获取用户点赞足迹
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用户点赞足迹", notes = "获取用户点赞足迹")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getUserLikeHistoryList", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<UserLikeInfoDTO> getUserLikeHistoryList(ServletRequest request, HttpSession session) {
        ResultInfo<UserLikeInfoDTO> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");

            String token = request.getParameter("token");
            String strPageNumber = request.getParameter("pageNumber");
            String strPageSize = request.getParameter("pageSize");
            if (StringUtils.isNotBlank(strPageNumber) && StringUtils.isNotBlank(strPageSize)) {
                long pageNumber = Long.parseLong(strPageNumber);
                long pageSize = Long.parseLong(strPageSize);
                List<UserLikeInfoDTO> userOperateHistoryInfoList = userHistoryInfoService.queryUserLikeHistorys(token, pageNumber, pageSize);
                long count = userHistoryInfoService.queryUserLikeHistoryCount(token);
                resultInfo.setRecordCount(count);
                long pageCount = count / pageSize;
                if (count % pageSize > 0) {
                    pageCount += 1;
                }
                resultInfo.setPageCount(pageCount);
                resultInfo.setData(userOperateHistoryInfoList);
            } else {
                List<UserLikeInfoDTO> userOperateHistoryInfoList = userHistoryInfoService.queryUserLikeHistorys(token);
                resultInfo.setData(userOperateHistoryInfoList);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }

        return resultInfo;
    }

    private UserInfo getUserInfoByToken(String token) {
        UserTokenMappedInfo userTokenMappedInfo = userTokenMappedInfoDao.findByToken(token);
        if (userTokenMappedInfo != null) {
            return userInfoDao.findById(userTokenMappedInfo.getUserId());
        }
        return null;
    }

    /**
     * 获取手机短信验证码
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取手机短信验证码", notes = "获取手机短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getSMSVerificationCode", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<Boolean> getSMSVerificationCode(ServletRequest request, ServletResponse response, HttpSession session) {
        ResultObjInfo<Boolean> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("发送短信验证码成功");
            resultObjInfo.setData(true);
            String mobile = request.getParameter("mobile");
            if (StringUtils.isBlank(mobile)) {
                resultObjInfo.setMsg("请输入手机号");
                resultObjInfo.setData(false);
                return resultObjInfo;
            }

            if (!ValidateUtils.isMobile(mobile)) {
                resultObjInfo.setMsg("请输入正确的手机号");
                resultObjInfo.setData(false);
                return resultObjInfo;
            }
            UserInfo userInfo = userInfoDao.findByUserName(mobile);
            String msgId = JSMSExample.testSendSMSCode(mobile);
            if (userInfo != null) {
                userInfo.setSMSVerificationCode(msgId);
                userInfoDao.save(userInfo);
            } else {
                userInfo = new UserInfo();
                userInfo.setUserName(mobile);
                userInfo.setAvatarUrl("noavatar_small.gif");
                userInfo.setSMSVerificationCode(msgId);
                userInfoDao.save(userInfo);
            }
            resultObjInfo.setData(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("发送短信验证码失败");
        }
        return resultObjInfo;
    }


    /**
     * 获取手机语音验证码
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取手机语音验证码", notes = "获取手机语音验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getVoiceMessagesCode", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<Boolean> getVoiceMessagesCode(ServletRequest request, ServletResponse response, HttpSession session) {
        ResultObjInfo<Boolean> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("发送语音验证码成功");
            resultObjInfo.setData(true);
            String mobile = request.getParameter("mobile");
            if (StringUtils.isBlank(mobile)) {
                resultObjInfo.setMsg("请输入手机号");
                resultObjInfo.setData(false);
                return resultObjInfo;
            }
            if (!ValidateUtils.isMobile(mobile)) {
                resultObjInfo.setMsg("请输入正确的手机号");
                resultObjInfo.setData(false);
                return resultObjInfo;
            }
            UserInfo userInfo = userInfoDao.findByUserName(mobile);
            String msgId = JSMSExample.testSendVoiceSMSCode(mobile);
            if (userInfo != null) {
                userInfo.setSMSVerificationCode(msgId);
                userInfoDao.save(userInfo);
            } else {
                userInfo = new UserInfo();
                userInfo.setUserName(mobile);
                userInfo.setAvatarUrl("noavatar_small.gif");
                userInfo.setSMSVerificationCode(msgId);
                userInfoDao.save(userInfo);
            }
            resultObjInfo.setData(true);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("发送语音验证码失败");
        }
        return resultObjInfo;
    }


    /**
     * 验证码登录
     */
    @ApiOperation(value = "短信或语音验证码登录", notes = "短信或语音验证码登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "smscode", value = "短信或语音验证码", required = true, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "loginBySMSCode", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserAuthInfo> loginBySMSCode(ServletRequest request, ServletResponse response, HttpSession session) {
        ResultObjInfo<UserAuthInfo> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            UserAuthInfo userAuthInfo = new UserAuthInfo();
            String mobile = request.getParameter("mobile");
            String smscode = request.getParameter("smscode");
            if (StringUtils.isBlank(mobile) || StringUtils.isBlank(smscode)) {
                throw new IllegalArgumentException();
            }
            if (Util.isNumeric(smscode) == false) {
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("验证码格式错误！");
                return resultObjInfo;
            }
            UserInfo userInfo = userInfoDao.findByUserName(mobile);
            String msgId = userInfo.getSMSVerificationCode();
            boolean s = JSMSExample.testSendValidSMSCode(msgId,smscode);
            if (userInfo != null && s==true) {
                userAuthInfo.setUserName(mobile);
                String accessToken = UUID.randomUUID().toString().replace("-", "");
                userAuthInfo.setAccessToken(accessToken);
                userAuthInfo.setAvatarUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + userInfo.getAvatarUrl());
                userAuthInfo.setIsCertification(1);

                UserTokenMappedInfo userTokenMappedInfo = new UserTokenMappedInfo();
                userTokenMappedInfo.setToken(accessToken);
                userTokenMappedInfo.setUserId(userInfo.getId());
                userTokenMappedInfoDao.save(userTokenMappedInfo);
                session.setAttribute(token, accessToken);
            } else {
                if (userInfo == null) {
                    resultObjInfo.setCode("500");
                    resultObjInfo.setMsg("用户不存在");
                } else {
                    resultObjInfo.setCode("500");
                    resultObjInfo.setMsg("短信或语音验证码已过期！");
                }
                userAuthInfo.setUserName(mobile);
                userAuthInfo.setIsCertification(0);
            }

            String ip = GetIpUtil.getIp((HttpServletRequest) request);
            String systemType = UserAgentUtil.getUserAgent((HttpServletRequest) request);
            ipInfoService.addRegisteredUserIpInfo(ip ,systemType ,mobile);

            resultObjInfo.setData(userAuthInfo);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }
        return resultObjInfo;
    }

    /**
     * 第三方登录
     */
    @ApiOperation(value = "第三方登录", notes = "第三方登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", value = "openid", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "avatarUrl", value = "头像", required = true, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "loginByOpenId", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserAuthInfo> loginByOpenId(ServletRequest request, ServletResponse response, HttpSession session) {
        ResultObjInfo<UserAuthInfo> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            UserAuthInfo userAuthInfo = new UserAuthInfo();
            String openid = request.getParameter("openid");
            String userName = request.getParameter("userName");
            String avatarUrl = request.getParameter("avatarUrl");
            if (StringUtils.isBlank(openid) && StringUtils.isBlank(userName) && StringUtils.isBlank(avatarUrl)) {
                throw new IllegalArgumentException();
            }

            UserInfo userInfo = userInfoDao.findByOpenId(openid);
            if (userInfo !=null){
                userInfo.setUserName(userName);
                userInfo.setAvatarUrl(avatarUrl);
                userInfoDao.save(userInfo);
            }else{
                userInfo = new UserInfo();
                userInfo.setUserName(userName);
                userInfo.setOpenId(openid);
                userInfo.setPassword(MD5Util.MD5(userdefaultpassword));
                userInfo.setAvatarUrl(avatarUrl);
                userInfoDao.save(userInfo);
            }

            userAuthInfo.setUserName(userInfo.getUserName());
            String accessToken = UUID.randomUUID().toString().replace("-", "");
            userAuthInfo.setAccessToken(accessToken);
            userAuthInfo.setAvatarUrl(userInfo.getAvatarUrl());
            userAuthInfo.setIsCertification(1);

            UserTokenMappedInfo userTokenMappedInfo = new UserTokenMappedInfo();
            userTokenMappedInfo.setToken(accessToken);
            userTokenMappedInfo.setUserId(userInfo.getId());
            userTokenMappedInfoDao.save(userTokenMappedInfo);
            session.setAttribute(token, accessToken);

            String ip = GetIpUtil.getIp((HttpServletRequest) request);
            String systemType = UserAgentUtil.getUserAgent((HttpServletRequest) request);
            ipInfoService.addRegisteredUserIpInfo(ip ,systemType ,userName);

            resultObjInfo.setData(userAuthInfo);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    /**
     * 注册
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "输入手机号", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "smscode", value = "输入验证码", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "password", value = "输入密码", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "password1", value = "再次输入密码", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "userRegister", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<UserInfo> addUser(ServletRequest request) {
        ResultObjInfo<UserInfo> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");
            String userName = request.getParameter("userName");
            String smscode = request.getParameter("smscode");
            String password = request.getParameter("password");
            String password1 = request.getParameter("password1");

            UserInfo userInfo = userInfoDao.findByUserName(userName);
            if(userInfo==null){
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("请先获取验证码");
            }else {
                String psw = userInfo.getPassword();
                if(!StringUtils.isBlank(psw) ){
                    resultObjInfo.setCode("500");
                    resultObjInfo.setMsg("该手机已注册");
                }else {
                    if (StringUtils.isBlank(userName) || StringUtils.isBlank(smscode)) {
                        resultObjInfo.setCode("500");
                        resultObjInfo.setMsg("手机号或验证码不能为空");
                    } else if (StringUtils.isBlank(password) || StringUtils.isBlank(password1) || !password.equals(password1)) {
                        resultObjInfo.setCode("500");
                        resultObjInfo.setMsg("两次密码不一致");
                    } else {
                        String msgId = userInfo.getSMSVerificationCode();
                        boolean s = JSMSExample.testSendValidSMSCode(msgId,smscode);
                        UserInfo userInfos = userInfoDao.getUserInfoList(userName, msgId);
                        if (userInfos != null && s==true) {
                            String accessToken = UUID.randomUUID().toString().replace("-", "");
                            userInfos.setUserName(userName);
                            userInfos.setSMSVerificationCode(msgId);
                            userInfos.setPassword(MD5Util.MD5(password));
                            userInfos.setAvatarUrl("noavatar_small.gif");
                            userInfos.setAccessToken(accessToken);
                            userInfoDao.save(userInfos);

                            userInfos = new UserInfo();
                            userInfos.setId(userInfo.getId());
                            userInfos.setUserName(userInfo.getUserName());
                            userInfos.setPassword(userInfo.getPassword());
                            userInfos.setAvatarUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + userInfo.getAvatarUrl());
                            userInfos.setAccessToken(userInfo.getAccessToken());
                            userInfos.setSMSVerificationCode(userInfo.getSMSVerificationCode());

                            UserTokenMappedInfo userTokenMappedInfo = new UserTokenMappedInfo();
                            userTokenMappedInfo.setToken(accessToken);
                            userTokenMappedInfo.setUserId(userInfo.getId());
                            userTokenMappedInfoDao.save(userTokenMappedInfo);

                            //获取登录用户ip
                            String ip = GetIpUtil.getIp((HttpServletRequest) request);
                            String systemType = UserAgentUtil.getUserAgent((HttpServletRequest) request);
                            ipInfoService.addRegisteredUserIpInfo(ip ,systemType ,userName);

                            resultObjInfo.setData(userInfos);
                        } else {
                            resultObjInfo.setCode("500");
                            resultObjInfo.setMsg("验证码错误");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }
        return resultObjInfo;
    }


    @ApiOperation(value = "意见反馈", notes = "意见反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "content", value = "content", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "postOpinionFeedback", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<OpinionFeedback> postOpinionFeedback(ServletRequest request, HttpSession session) {
        ResultObjInfo<OpinionFeedback> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String token = request.getParameter("token");
            String content = request.getParameter("content");

            UserInfo userInfo = getUserInfoByToken(token);
            if (StringUtils.isBlank(token) || StringUtils.isBlank(content)) {
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("请先登录或者内容不能为空！");
            }else {
                OpinionFeedback opinionFeedback = new OpinionFeedback();
                String id = UUID.randomUUID().toString();
                opinionFeedback.setId(id);
                opinionFeedback.setContent(content);
                opinionFeedback.setUserId(userInfo.getId().toString());
                opinionFeedback.setUserName(userInfo.getUserName());
                Date date = new Date();
                opinionFeedback.setCreateTime(date);
                opinionFeedbackDao.save(opinionFeedback);
                resultObjInfo.setData(opinionFeedback);

                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                Map<String,Object> map = new HashMap<>();
                map.put("id",id);
                map.put("createTime",dateStr);
                map.put("content",content);
                map.put("userId",userInfo.getId().toString());
                map.put("userName",userInfo.getUserName());
                String s = HttpClientUtils.doPost(optionFeedbackSaveUrl,map );
                logger.info(s);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }

        return resultObjInfo;
    }

    @ApiOperation(value = "意见回复", notes = "意见回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "opFeedId", value = "opFeedId", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "content", value = "content", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "saveOpinionsReply", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultObjInfo<OpinionFeedbackInfoReply> saveOpinionsReply(ServletRequest request, HttpSession session) {
        ResultObjInfo<OpinionFeedbackInfoReply> resultObjInfo = new ResultObjInfo<>();
        try {
            resultObjInfo.setCode("200");
            resultObjInfo.setMsg("success");

            String opFeedId = request.getParameter("opFeedId");
            String content = request.getParameter("content");
            if (StringUtils.isBlank(opFeedId) || StringUtils.isBlank(content)) {
                resultObjInfo.setCode("500");
                resultObjInfo.setMsg("反馈表id不能为空！");
            }else {
                String id = UUID.randomUUID().toString();
                Date date = new Date();

                OpinionFeedbackInfoReply reply = new OpinionFeedbackInfoReply();
                reply.setId(id);
                reply.setContent(content);
                reply.setOpFeedId(opFeedId);
                reply.setCreateTime(date);
                opinionFeedbackReplyDao.save(reply);
                resultObjInfo.setData(reply);

                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                Map<String,Object> map = new HashMap<>();
                map.put("id",id);
                map.put("opFeedId",opFeedId);
                map.put("content",content);
                map.put("createTime",dateStr);
                String s = HttpClientUtils.doPost(optionFeedbackReplySaveUrl,map);
                logger.info(s);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultObjInfo.setCode("500");
            resultObjInfo.setMsg("failure");
        }
        return resultObjInfo;
    }
}
