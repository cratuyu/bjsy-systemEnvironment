package com.leadmap.environmentalrotection.web.controller;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dto.ResultInfo;
import com.leadmap.environmentalrotection.entity.company.ProjectInfo;
import com.leadmap.environmentalrotection.service.ProjectInfoService;
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

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 15:05
 */
@RestController
public class ProjectInfoController {

    private final static Logger logger = LoggerFactory.getLogger(ProjectInfoController.class);

    @Autowired
    private ProjectInfoService documentInfoService;

    /**
     * 获取微信公众号的文档列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "查询企业项目进展情况", notes = "查询企业项目进展情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "search", value = "项目名称查询条件", required = false, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getProjectInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultInfo<ProjectInfo> getProjectInfos(ServletRequest request, HttpSession session) {
        String search = request.getParameter("search");
        PageRequest pageRequest = Util.buildPageRequest(request);
        return documentInfoService.getProjectInfos(search, pageRequest);
    }
}
