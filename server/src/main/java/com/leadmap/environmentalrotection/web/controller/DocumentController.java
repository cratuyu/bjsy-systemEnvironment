package com.leadmap.environmentalrotection.web.controller;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dto.DocumentSimpleInfoDTO;
import com.leadmap.environmentalrotection.dto.ResultInfo;
import com.leadmap.environmentalrotection.dto.ResultObjInfo;
import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
import com.leadmap.environmentalrotection.entity.document.DocumentTypes;
import com.leadmap.environmentalrotection.service.DocumentInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 15:05
 */
@RestController
public class DocumentController {

    private final static Logger logger = LoggerFactory.getLogger(DocumentController.class);
    @Autowired
    private DocumentInfoService documentInfoService;

    /**
     * 获取微信公众号的文档列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取微信公众号的文档列表", notes = "获取微信公众号的文档列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "type", value = "type", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "search", value = "search", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getDocumentInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<DocumentSimpleInfoDTO> getDocumentInfos(ServletRequest request, HttpSession session) {
        ResultInfo<DocumentSimpleInfoDTO> resultInfo = new ResultInfo<>();

        String type = request.getParameter("type");
        String search = request.getParameter("search");
        String token = request.getParameter("token");
        PageRequest pageRequest = Util.buildPageRequest(request);

        return documentInfoService.getDocumentSimpleInfo(type, search, token, pageRequest);
    }

    /**
     * 获取微信公众号单篇文章
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取微信公众号单篇文章", notes = "获取微信公众号单篇文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "docId", value = "docId", required = true, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "getDocument", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<DocumentInfo> getDocument(ServletRequest request) {
        ResultObjInfo<DocumentInfo> resultInfo = new ResultObjInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(documentInfoService.getDocument(request));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
        }

        return resultInfo;
    }

    /**
     * 获取微信公共号文档类别列表
     *
     * @return
     */
    @ApiOperation(value = "获取微信公共号文档类别列表", notes = "获取微信公共号文档类别列表")
    @RequestMapping(value = "getDocumentTypes", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<DocumentTypes> getDocumentTypes() {
        ResultInfo<DocumentTypes> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(documentInfoService.getDocumentTypes());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }
}
