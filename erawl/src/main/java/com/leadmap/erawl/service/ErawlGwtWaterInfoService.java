package com.leadmap.erawl.service;

import com.alibaba.fastjson.JSONArray;
import com.leadmap.erawl.common.httprequest.HttpRequest;
import com.leadmap.erawl.dao.GwtWaterInfoDao;
import com.leadmap.erawl.entity.river.GwtWaterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 14:16
 */
@Component
public class ErawlGwtWaterInfoService {

    private final static Logger logger = LoggerFactory.getLogger(ErawlGwtWaterInfoService.class);

    @Value("${gwtWaterInfoUrl}")
    private String gwtWaterInfoUrl;

    @Autowired
    private GwtWaterInfoDao gwtWaterInfoDao;

    public void generateGwtWaterInfoFromSiteServer() {
        try {
            String postParam = "Method=SelectRealData";
            String dataStr = HttpRequest.sendPost(this.gwtWaterInfoUrl, postParam);
            List<GwtWaterInfo> gwtWaterInfoList = JSONArray.parseArray(dataStr, GwtWaterInfo.class);
            List<GwtWaterInfo> gwtWaterInfoList1 = new ArrayList<>();
            if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
                for (GwtWaterInfo gwtWaterInfo : gwtWaterInfoList) {
                    if (gwtWaterInfo.getSiteName().contains("北京古北口")) {
                        gwtWaterInfo.setStrDo(gwtWaterInfo.getDO());
                        gwtWaterInfo.setCreateTime(new Date());
                        gwtWaterInfo.setX("117.163402");
                        gwtWaterInfo.setY("40.6913");
                        gwtWaterInfoList1.add(gwtWaterInfo);
                    } else if (gwtWaterInfo.getSiteName().contains("北京门头沟沿河城")) {
                        gwtWaterInfo.setStrDo(gwtWaterInfo.getDO());
                        gwtWaterInfo.setCreateTime(new Date());
                        gwtWaterInfo.setX("117.160665");
                        gwtWaterInfo.setY("40.691003");
                        gwtWaterInfoList1.add(gwtWaterInfo);
                    }
                }
            }
            if (gwtWaterInfoList1 != null && gwtWaterInfoList1.size() > 0) {
                gwtWaterInfoDao.save(gwtWaterInfoList1);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }



}
