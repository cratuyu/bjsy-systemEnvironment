package com.leadmap.management.controller;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.*;
import com.leadmap.management.service.DocumentService;
import com.leadmap.management.service.OperationLogService;
import com.leadmap.management.service.UserCollectDocInfoService;
import com.leadmap.management.service.UserLikeDocInfoService;
import com.leadmap.management.shiro.ShiroService;
import com.leadmap.management.util.DateFormat;
import com.leadmap.management.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description: 资讯管理
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private UserLikeDocInfoService userLikeDocInfoService;

    @Autowired
    private UserCollectDocInfoService userCollectDocInfoService;

    @RequestMapping
    public Map<String, Object> getAll(DocumentInfo documentInfo, String draw,
                                      @RequestParam(required = false, defaultValue = "1") int start,
                                      @RequestParam(required = false, defaultValue = "10") int length, HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<>();
            String beginTime = request.getParameter("beginTime");
            String endTime = request.getParameter("endTime");
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            if (beginTime != null && !"".equals(beginTime) && endTime != null && !"".equals(endTime)) {
                Date beginTimes = null;
                Date endTimes = null;
                if (!StringUtils.isEmpty(beginTime)) {
                    beginTimes = DateUtil.string2Date(beginTime + " 00:00:00", DateFormat.DateFull);
                }
                if (!StringUtils.isEmpty(endTime)) {
                    endTimes = DateUtil.string2Date(endTime + DateUtil.getTodayDateHHmmss(), DateFormat.DateFull);
                }
                PageInfo<DocumentInfo> pageInfo = documentService.selectByDocument(documentInfo, beginTimes, endTimes, title, type, start, length);
                System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
                map.put("draw", draw);
                map.put("recordsTotal", pageInfo.getTotal());
                map.put("recordsFiltered", pageInfo.getTotal());
                map.put("data", pageInfo.getList());
                return map;

            }
            PageInfo<DocumentInfo> pageInfo = documentService.selectByPage(documentInfo, start, length);
            System.out.println("pageInfo.getTotal():" + pageInfo.getTotal());
            map.put("draw", draw);
            map.put("recordsTotal", pageInfo.getTotal());
            map.put("recordsFiltered", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("获取资讯列表失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return null;
        }
    }

    @RequestMapping(value = "/add")
    public String add(HttpServletRequest request) {
        try {
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            String documentUrl = request.getParameter("documentUrl");
            String imageUrl = request.getParameter("imageUrl");
            String publisher = request.getParameter("publisher");
            String publishDate = DateUtil.getDateYYmmdd(1);

            DocumentInfo documentInfo = new DocumentInfo();
            documentInfo.setTitle(title);
            documentInfo.setType(type);
            documentInfo.setDocumentUrl(documentUrl);
            documentInfo.setImageUrl(imageUrl);
            documentInfo.setPublisher(publisher);
            documentInfo.setPublishDate(publishDate);
            documentInfo.setCreateTime(new Date());
            documentService.insertDocumentInfo(documentInfo);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("资讯添加失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    @RequestMapping(value = "/delete")
    public String delete(long id, HttpServletRequest request) {
        try {
            documentService.delete(id);

            List<UserLikeDocInfo> userLikeDocInfos = userLikeDocInfoService.getUserLikeDocInfoByDocId(String.valueOf(id));
            if (userLikeDocInfos != null && userLikeDocInfos.size() > 0) {
                userLikeDocInfoService.deleteUserLikeByDoc(String.valueOf(id));
            }
            List<UserCollectDocInfo> userCollectDocInfos = userCollectDocInfoService.getUserCollectDocInfoByDocId(String.valueOf(id));
            if (userCollectDocInfos !=null && userCollectDocInfos.size()>0){
                userCollectDocInfoService.deleteUserCollectByDoc(String.valueOf(id));
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            User user = (User) request.getSession().getAttribute("user");
            String userName = user.getUsername();
            OperationLog operationLog = new OperationLog();
            operationLog.setIp(request.getRemoteAddr());
            operationLog.setUserName(userName);
            operationLog.setLogSign("异常日志");
            operationLog.setLogName("删除资讯失败");
            operationLog.setCreateTime(new Date());
            operationLogService.save(operationLog);
            return "fail";
        }
    }

    /**
     * 通过id 查找资讯信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/selectDocumentById")
    public List<DocumentInfo> selectDocumentById(long id) {
        return documentService.selectDocumentById(id);
    }

    /**
     * 更新资讯
     *
     * @param
     * @return
     */
    @RequestMapping("/update")
    public String saveUpdate(HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String title = request.getParameter("title");
            String type = request.getParameter("type");
            String documentUrl = request.getParameter("documentUrl");
            String imageUrl = request.getParameter("imageUrl");
            String publisher = request.getParameter("publisher");
            String publishDate = DateUtil.getDateYYmmdd(1);

            DocumentInfo documentInfo = new DocumentInfo();
            documentInfo.setId(Long.valueOf(id));
            documentInfo.setTitle(title);
            documentInfo.setType(type);
            documentInfo.setDocumentUrl(documentUrl);
            documentInfo.setImageUrl(imageUrl);
            documentInfo.setPublisher(publisher);
            documentInfo.setPublishDate(publishDate);
            documentInfo.setCreateTime(new Date());

            documentService.updateDocumentInfo(documentInfo);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
