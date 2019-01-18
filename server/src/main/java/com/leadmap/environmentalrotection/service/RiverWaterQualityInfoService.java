package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.RiverWaterQualityInfoDao;
import com.leadmap.environmentalrotection.entity.river.RiverWaterQualityInfo;
import com.leadmap.environmentalrotection.web.controller.AirController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 14:38
 */
@Component
public class RiverWaterQualityInfoService {
    @Autowired
    private RiverWaterQualityInfoDao riverWaterQualityInfoDao;

    public List<RiverWaterQualityInfo> getRiverWaterQualityInfo(String pageNumber, String pageSize) {
        PageRequest pageRequest = Util.buildPageRequest(pageNumber, pageSize, StringUtils.EMPTY);
        List<RiverWaterQualityInfo> riverWaterQualityInfoList = new ArrayList<>();
        for (RiverWaterQualityInfo riverWaterQualityInfo : riverWaterQualityInfoDao.findAll(pageRequest)) {
            riverWaterQualityInfoList.add(riverWaterQualityInfo);
        }
        return riverWaterQualityInfoList;
    }

    public long getRiverWaterQualityInfoCount() {
        return riverWaterQualityInfoDao.count();
    }
}
