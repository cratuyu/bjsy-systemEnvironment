package com.leadmap.environmentalrotection.web.controller;

import com.leadmap.environmentalrotection.dao.VersionInfoDao;
import com.leadmap.environmentalrotection.dto.VersionInfoDTO;
import com.leadmap.environmentalrotection.entity.user.VersionInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/25 17:15
 */
@RestController
public class VersionController {

    private final static Logger logger = LoggerFactory.getLogger(VersionController.class);

    @Autowired
    private VersionInfoDao versionInfoDao;

    @ApiOperation(value = "版本更新", notes = "版本更新")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "versionName", value = "versionName", required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "versionPlatform", value = "versionPlatform", required = true, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "getCheckUpdate", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public Map<String,Object> getCheckUpdate(ServletRequest request, ServletResponse response, HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        try {
            String versionName = request.getParameter("versionName");
            String versionPlatform = request.getParameter("versionPlatform");
            if (StringUtils.isBlank(versionName) || StringUtils.isBlank(versionPlatform)) {
                map.put("code", 500);
                map.put("msg", "版本号或者平台编号不能为空！");
            } else {
                VersionInfo versionInfo = versionInfoDao.queryByVersionPlatform(versionPlatform);
                String newVersionName = versionInfo.getVersionName();
                if (comparaVersion(versionName, newVersionName) == true) {
                    VersionInfoDTO versionInfo1 = new VersionInfoDTO();
                    versionInfo1.setVersionName(versionInfo.getVersionName());
                    versionInfo1.setVersionPlatform(versionInfo.getVersionPlatform());
                    versionInfo1.setVersionUrl(versionInfo.getVersionUrl());
                    versionInfo1.setVersionDesc(versionInfo.getVersionDesc());
                    versionInfo1.setForceUpdata(Boolean.getBoolean(versionInfo.getIsForceUpdata()));
                    map.put("code", 200);
                    map.put("msg", "success");
                    map.put("data", versionInfo1);
                } else {
                    map.put("code", 200);
                    map.put("msg", "最新版本，无需升级！");
                    map.put("data", "");
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            map.put("cod", "500");
            map.put("msg", "failure");
            map.put("data", "");
        }
        return map;
    }


    public static boolean comparaVersion(String oldVersion,String newVersion){
        try {
            if(Integer.parseInt(oldVersion.replace(".", ""))<Integer.parseInt(newVersion.replace(".", ""))){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            logger.error("版本号转换异常:", e);
            return false;
        }
    }

}
