package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.dao.*;
import com.leadmap.environmentalrotection.dto.*;
import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
import com.leadmap.environmentalrotection.entity.document.DocumentTypes;
import com.leadmap.environmentalrotection.entity.user.UserCollectDocInfo;
import com.leadmap.environmentalrotection.entity.user.UserInfo;
import com.leadmap.environmentalrotection.entity.user.UserLikeDocInfo;
import com.leadmap.environmentalrotection.entity.user.UserTokenMappedInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 10:54
 */
@Component
public class DocumentInfoService {
    private final static Logger logger = LoggerFactory.getLogger(DocumentInfoService.class);

    @Autowired
    private DocumentInfoDao documentInfoDao;

    @Autowired
    private UserLikeDocInfoDao userLikeDocInfoDao;

    @Autowired
    private UserCollectDocInfoDao userCollectDocInfoDao;

    @Autowired
    private UserHistoryInfoService userHistoryInfoService;

    @Autowired
    private UserTokenMappedInfoDao userTokenMappedInfoDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private DocumentTypesDao documentTypesDao;

    public ResultInfo<DocumentSimpleInfoDTO> getDocumentSimpleInfo(String type, String search, String token, PageRequest pageRequest) {
        ResultInfo<DocumentSimpleInfoDTO> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            List<DocumentSimpleInfoDTO> documentInfoList = new ArrayList<>();
            Specification<DocumentInfo> spec = null;

            if (StringUtils.isNotBlank(type)) {
                if (StringUtils.isNotBlank(search)) {
                    spec = new Specification<DocumentInfo>() {
                        @Override
                        public Predicate toPredicate(Root<DocumentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                            List<Predicate> predicates = new ArrayList<>();

                            predicates.add(criteriaBuilder.equal(root.get("type"), type));
                            predicates.add(criteriaBuilder.like(root.get("title"), "%" + search + "%"));
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        }
                    };
                } else {
                    spec = new Specification<DocumentInfo>() {
                        @Override
                        public Predicate toPredicate(Root<DocumentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                            List<Predicate> predicates = new ArrayList<>();

                            predicates.add(criteriaBuilder.equal(root.get("type"), type));
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        }
                    };
                }
            } else if (StringUtils.isNotBlank(search)) {
                spec = new Specification<DocumentInfo>() {
                    @Override
                    public Predicate toPredicate(Root<DocumentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();

                        predicates.add(criteriaBuilder.like(root.get("title"), "%" + search + "%"));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };
            }

            long count = documentInfoDao.count(spec);
            resultInfo.setRecordCount(count);
            if (pageRequest != null) {
                long pageCount = count / pageRequest.getPageSize();
                if ((count % pageRequest.getPageSize()) > 0) {
                    pageCount += 1;
                }
                resultInfo.setPageCount(pageCount);
            }

            Iterable<DocumentInfo> iterable = documentInfoDao.findAll(spec, pageRequest);
            if (iterable != null) {
                for (DocumentInfo documentInfo : iterable) {
//                    setDocumentCommentInfo(documentInfo, token);
                    setUserCollectInfoInfo(documentInfo, token);
                    setUserLikeInfoInfo(documentInfo, token);

                    if (StringUtils.isNotBlank(token)) {
                        if (documentInfo.getUserLikeInfoDTOList().size() > 0) {
                            documentInfo.setIsLike(true);
                        } else {
                            documentInfo.setIsLike(false);
                        }

                        if (documentInfo.getUserCollectInfoDTOList().size() > 0) {
                            documentInfo.setIsCollect(true);
                        } else {
                            documentInfo.setIsCollect(false);
                        }
                    } else {
                        documentInfo.setIsLike(false);
                        documentInfo.setIsCollect(false);
                    }
//                    setUserOperateDocInfo(documentInfo, token);
                    DocumentSimpleInfoDTO documentSimpleInfoDTO = new DocumentSimpleInfoDTO();
                    documentSimpleInfoDTO.setDocId(documentInfo.getId());
                    documentSimpleInfoDTO.setIsCollect(documentInfo.getIsCollect());
                    documentSimpleInfoDTO.setIsLike(documentInfo.getIsLike());
                    documentSimpleInfoDTO.setImageUrl(documentInfo.getImageUrl());
                    documentSimpleInfoDTO.setTitle(documentInfo.getTitle());
                    documentSimpleInfoDTO.setPublishDate(documentInfo.getPublishDate());
                    documentSimpleInfoDTO.setUserCollectCount(documentInfo.getUserCollectCount());
                    documentSimpleInfoDTO.setUserLikeCount(documentInfo.getUserLikeCount());
                    documentSimpleInfoDTO.setDocUrl(documentInfo.getDocumentUrl());
                    documentInfoList.add(documentSimpleInfoDTO);
                }
            }
            Collections.reverse(documentInfoList);
            resultInfo.setData(documentInfoList);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }

    public DocumentInfo getDocument(ServletRequest request) {
        String token = request.getParameter("token");
        String docId = request.getParameter("docId");

        List<DocumentInfo> documentInfoList = new ArrayList<>();
        Specification<DocumentInfo> spec = null;
        if (StringUtils.isNotBlank(docId)) {
            spec = new Specification<DocumentInfo>() {
                @Override
                public Predicate toPredicate(Root<DocumentInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("id"), docId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
        }

        Iterable<DocumentInfo> iterable = documentInfoDao.findAll(spec);
        if (iterable != null) {
            for (DocumentInfo documentInfo : iterable) {
//                    setDocumentCommentInfo(documentInfo, token);
                setUserLikeInfoInfo(documentInfo, token);
                setUserCollectInfoInfo(documentInfo, token);

                if (StringUtils.isNotBlank(token)) {
                    if (documentInfo.getUserLikeInfoDTOList().size() > 0) {
                        documentInfo.setIsLike(true);
                    } else {
                        documentInfo.setIsLike(false);
                    }

                    if (documentInfo.getUserCollectInfoDTOList().size() > 0) {
                        documentInfo.setIsCollect(true);
                    } else {
                        documentInfo.setIsCollect(false);
                    }
                } else {
                    documentInfo.setIsLike(false);
                    documentInfo.setIsCollect(false);
                }

//                    setUserOperateDocInfo(documentInfo, token);
                documentInfoList.add(documentInfo);
                List<UserCommentInfoDTO> userCommentInfoDTOList = documentInfo.getUserCommentInfoList();
                if (userCommentInfoDTOList != null) {
                    for (UserCommentInfoDTO userCommentInfoDTO : userCommentInfoDTOList) {
                        if (!userCommentInfoDTO.getAvatarUrl().contains("images/")) {
                            userCommentInfoDTO.setAvatarUrl("http://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + userCommentInfoDTO.getAvatarUrl());
                        }
                    }
                }
                documentInfoList.add(documentInfo);
            }
        }
        if (documentInfoList.size() > 0) {
            return documentInfoList.get(0);
        } else {
            return null;
        }
    }

    private void setUserLikeInfoInfo(DocumentInfo documentInfo, String token) {
        if (StringUtils.isNotBlank(token)) {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                String docId = documentInfo.getId().toString();
                Specification<UserLikeDocInfo> spec = null;
                Specification<UserLikeDocInfo> spec1 = null;
                spec = new Specification<UserLikeDocInfo>() {
                    @Override
                    public Predicate toPredicate(Root<UserLikeDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("userId"), userInfo.getId()));
                        predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };

                spec1 = new Specification<UserLikeDocInfo>() {
                    @Override
                    public Predicate toPredicate(Root<UserLikeDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };
                List<UserLikeDocInfo> userLikeDocInfoDaoAll = userLikeDocInfoDao.findAll(spec1);

                List<UserLikeDocInfo> userLikeDocInfos = userLikeDocInfoDao.findAll(spec);
                if (userLikeDocInfos != null && userLikeDocInfos.size() > 0) {
                    List<UserLikeInfoDTO> userLikeInfoDTOS = new ArrayList<>();
                    for (UserLikeDocInfo userLikeDocInfo : userLikeDocInfos) {
                        UserLikeInfoDTO userLikeInfoDTO = userHistoryInfoService.userLikeInfo2Dto(userLikeDocInfo);
                        userLikeInfoDTOS.add(userLikeInfoDTO);
                    }
                    documentInfo.setUserLikeInfoDTOList(userLikeInfoDTOS);
                } else {
                    documentInfo.setUserLikeInfoDTOList(new ArrayList<>());
                }
                documentInfo.setUserLikeCount(userLikeDocInfoDaoAll.size());
            }
        } else {
            String docId = documentInfo.getId().toString();
            Specification<UserLikeDocInfo> spec = null;
            spec = new Specification<UserLikeDocInfo>() {
                @Override
                public Predicate toPredicate(Root<UserLikeDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            List<UserLikeDocInfo> userLikeDocInfoDaoAll = userLikeDocInfoDao.findAll(spec);
            if (userLikeDocInfoDaoAll != null && userLikeDocInfoDaoAll.size() > 0) {
                List<UserLikeInfoDTO> userLikeInfoDTOList = new ArrayList<>();
                for (UserLikeDocInfo userLikeDocInfo : userLikeDocInfoDaoAll) {
                    UserLikeInfoDTO userLikeInfoDTO = userHistoryInfoService.userLikeInfo2Dto(userLikeDocInfo);
                    userLikeInfoDTOList.add(userLikeInfoDTO);
                }
                documentInfo.setUserLikeInfoDTOList(userLikeInfoDTOList);
            } else {
                documentInfo.setUserLikeInfoDTOList(new ArrayList<>());
            }
            documentInfo.setUserLikeCount(userLikeDocInfoDaoAll.size());
        }
    }

    private void setUserCollectInfoInfo(DocumentInfo documentInfo, String token) {
        if (StringUtils.isNotBlank(token)) {
            UserInfo userInfo = getUserInfoByToken(token);
            if (userInfo != null) {
                String docId = documentInfo.getId().toString();
                Specification<UserCollectDocInfo> spec = null;
                Specification<UserCollectDocInfo> spec1 = null;
                spec = new Specification<UserCollectDocInfo>() {
                    @Override
                    public Predicate toPredicate(Root<UserCollectDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("userId"), userInfo.getId()));
                        predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };

                spec1 = new Specification<UserCollectDocInfo>() {
                    @Override
                    public Predicate toPredicate(Root<UserCollectDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();
                        predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };

                List<UserCollectDocInfo> userCollectDocInfoDaoAll = userCollectDocInfoDao.findAll(spec1);

                List<UserCollectDocInfo> userCollectDocInfos = userCollectDocInfoDao.findAll(spec);
                if (userCollectDocInfos != null && userCollectDocInfos.size() > 0){
                    List<UserCollectInfoDTO> userCollectInfoDTOS = new ArrayList<>();
                    for (UserCollectDocInfo userCollectDocInfo : userCollectDocInfos) {
                        UserCollectInfoDTO userCollectInfoDTO = userHistoryInfoService.userCollectInfo2Dto(userCollectDocInfo);
                        userCollectInfoDTOS.add(userCollectInfoDTO);
                    }
                    documentInfo.setUserCollectInfoDTOList(userCollectInfoDTOS);
                }else {
                    documentInfo.setUserCollectInfoDTOList(new ArrayList<>());
                }
                documentInfo.setUserCollectCount(userCollectDocInfoDaoAll.size());
            }
        } else {
            String docId = documentInfo.getId().toString();
            Specification<UserCollectDocInfo> spec = null;
            spec = new Specification<UserCollectDocInfo>() {
                @Override
                public Predicate toPredicate(Root<UserCollectDocInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(criteriaBuilder.equal(root.get("docId"), docId));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };

            List<UserCollectDocInfo> userCollectDocInfoDaoAll = userCollectDocInfoDao.findAll(spec);
            if (userCollectDocInfoDaoAll != null && userCollectDocInfoDaoAll.size() > 0) {
                List<UserCollectInfoDTO> userCollectInfoDTOList = new ArrayList<>();
                for (UserCollectDocInfo userCollectDocInfo : userCollectDocInfoDaoAll) {
                    UserCollectInfoDTO userCollectInfoDTO = userHistoryInfoService.userCollectInfo2Dto(userCollectDocInfo);
                    userCollectInfoDTOList.add(userCollectInfoDTO);
                }
                documentInfo.setUserCollectInfoDTOList(userCollectInfoDTOList);
            } else {
                documentInfo.setUserCollectInfoDTOList(new ArrayList<>());
            }
            documentInfo.setUserCollectCount(userCollectDocInfoDaoAll.size());
        }
    }

    private void setDocumentCommentInfo(DocumentInfo documentInfo, String token) {
        List<UserCommentInfoDTO> userCommentInfoDTOList = null;
        if (StringUtils.isNotBlank(token)) {
            userCommentInfoDTOList = userHistoryInfoService.queryUserCommentHistorysByDocId(documentInfo.getId().toString(), token);
        } else {
            userCommentInfoDTOList = userHistoryInfoService.queryUserCommentHistorysByDocId(documentInfo.getId().toString());
        }
        documentInfo.setUserCommentInfoList(userCommentInfoDTOList);
        if (userCommentInfoDTOList != null && userCommentInfoDTOList.size() >= 0) {
            documentInfo.setUserCommentCount(userCommentInfoDTOList.size());
        } else {
            documentInfo.setUserCommentCount(0);
        }
    }

    private UserInfo getUserInfoByToken(String token) {
        UserTokenMappedInfo userTokenMappedInfo = userTokenMappedInfoDao.findByToken(token);
        if (userTokenMappedInfo != null) {
            return userInfoDao.findById(userTokenMappedInfo.getUserId());
        }

        return null;
    }

    public List<DocumentTypes> getDocumentTypes() {
        List<DocumentTypes> documentTypesArrayList = new ArrayList<>();
        Iterable<DocumentTypes> gwtWaterInfoIterable = documentTypesDao.findAll();
        for (DocumentTypes gwtWaterInfo : gwtWaterInfoIterable) {
            documentTypesArrayList.add(gwtWaterInfo);
        }

        return documentTypesArrayList;
    }
}
