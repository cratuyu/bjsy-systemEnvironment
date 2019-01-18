package com.leadmap.mapservice.controller;

import com.leadmap.mapservice.common.TwoTuple;
import com.leadmap.mapservice.dao.DocumentInfoDao;
import com.leadmap.mapservice.dao.OpinionFeedbackDao;
import com.leadmap.mapservice.dao.UserCollectDocDao;
import com.leadmap.mapservice.dao.UserLikeDocDao;
import com.leadmap.mapservice.dto.ResultInfo;
import com.leadmap.mapservice.entity.DocumentInfo;
import com.leadmap.mapservice.entity.OpinionFeedback;
import com.leadmap.mapservice.service.DocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/20 10:31
 */
@Controller
public class DocumentController {

    private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);

    @Autowired
    private DocumentInfoDao documentInfoDao;

    @Autowired
    private OpinionFeedbackDao opinionFeedbackDao;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserCollectDocDao userCollectDocDao;

    @Autowired
    private UserLikeDocDao userLikeDocDao;

    /**
     * 下列四个方法 监听报表的四块内容
     * @return
     */
    @RequestMapping(value="/documentInfo")
    public String deleteDocument(){
        return "documentInfo";
    };

    @RequestMapping(value = "/dayActive")
    public String dayActive(){
        return "dayActive";
    }

    @RequestMapping(value = "/dayAdd")
    public String dayAdd(){
        return "dayAdd";
    }

    @ResponseBody
    @RequestMapping(value = "/documentList", method = RequestMethod.POST)
    public ResultInfo<List<DocumentInfo>> getAll(HttpServletRequest request){
        ResultInfo<List<DocumentInfo>>  resultInfo = new ResultInfo<>();
        int pageNum = Integer.valueOf(request.getParameter("pageNum"));
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        String type = request.getParameter("type");
        String title = request.getParameter("title");
        TwoTuple<List<DocumentInfo>, Integer> tuple = documentService.getPageDocumentInfo(pageNum,10, beginTime, endTime, type, title);
        resultInfo.setCode("200");
        resultInfo.setMsg("success");
        resultInfo.setData(tuple.getFirst());
        resultInfo.setRecordCount(tuple.getSecond());
        return resultInfo;
    }

    @Transactional
    @RequestMapping(value = "/addDocumentInfo", method = RequestMethod.POST)
    public String addDocumentInfo(ServletRequest request) {
        DocumentInfo documentInfo = new DocumentInfo();
        documentInfo.setDocumentUrl(request.getParameter("documentUrl"));
        documentInfo.setImageUrl(request.getParameter("imageUrl"));
        documentInfo.setTitle(request.getParameter("title"));
        documentInfo.setType(request.getParameter("type"));
        documentInfo.setPublisher(request.getParameter("publisher"));
        try {
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            String str = ft.format(new Date());
            Date dNow = ft.parse(str);
            documentInfo.setCreateTime(dNow);
            SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy-MM-dd");
            String str1 = ft1.format(new Date());
            documentInfo.setPublishDate(str1);
            documentInfoDao.save(documentInfo);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "documentInfo";
    }

    @Transactional
    @RequestMapping(value = "/updateDocumentInfo", method = RequestMethod.POST)
    public String updateDocumentInfo(ServletRequest request) {
        Long id = Long.parseLong(request.getParameter("hiddenId"));
        String documentUrl = request.getParameter("documentUrl");
        String imageUrl = request.getParameter("imageUrl");
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String publisher = request.getParameter("publisher");
        try {
            documentInfoDao.updateData(id, documentUrl, imageUrl, title, type, publisher);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "documentInfo";
    }


    @Transactional
    @RequestMapping(value = "/deleteDocumentInfo",method = RequestMethod.POST)
    public String deleteDocumentInfo(ServletRequest request){
        try {

            String id = request.getParameter("id");
            Long id1 = Long.parseLong(id);
            documentInfoDao.delete(id1);
            userCollectDocDao.deleteByDocId(id);
            userLikeDocDao.deleteByDocId(id);
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return "documentInfo";
    }

    @RequestMapping("/opinionFeedback")
    public String opinionFeedback(){
        return "opinionFeedback";
    }

    @ResponseBody
    @RequestMapping(value = "/opinionFeedbackList", method = RequestMethod.POST)
    public ResultInfo<List<OpinionFeedback>> opinionFeedbackList(HttpServletRequest request){
        ResultInfo<List<OpinionFeedback>> resultInfo = new ResultInfo<>();
        int pageNum = Integer.valueOf(request.getParameter("pageNum"));
        List<OpinionFeedback> list = documentService.getPageOpinionFeedbackInfo(pageNum, 10);
        Long recordCount = opinionFeedbackDao.getCount() / 10;
        resultInfo.setRecordCount(recordCount);
        resultInfo.setMsg("success");
        resultInfo.setCode("200");
        resultInfo.setData(list);
        return resultInfo;
    }

    @Transactional
    @RequestMapping(value = "/deleteOpinionFeedback", method = RequestMethod.POST)
    public String deleteOpinionFeedback(ServletRequest request){
        try{
            String id = request.getParameter("id");
            opinionFeedbackDao.deleteAllById(id);
        }catch (Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return "opinionFeedback";
    }
}
