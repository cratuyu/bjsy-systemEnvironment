package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.RiverWaterQualityInfoDao;
import com.leadmap.environmentalrotection.dao.WaterQualityReservoirInfoDao;
import com.leadmap.environmentalrotection.entity.river.WaterQualityReservoirInfo;
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
 * @Date: 2018/7/18 14:41
 */
@Component
public class WaterQualityReservoirInfoService {
    @Autowired
    private WaterQualityReservoirInfoDao waterQualityReservoirInfoDao;

    public List<WaterQualityReservoirInfo> getWaterQualityReservoirInfo(String pageNumber, String pageSize) {
        PageRequest pageRequest = Util.buildPageRequest(pageNumber, pageSize, StringUtils.EMPTY);
        List<WaterQualityReservoirInfo> waterQualityReservoirInfoList = new ArrayList<>();
        Iterable<WaterQualityReservoirInfo> waterQualityReservoirInfoIterable = waterQualityReservoirInfoDao.findAll(pageRequest);
        for (WaterQualityReservoirInfo waterQualityReservoirInfo : waterQualityReservoirInfoIterable) {
            waterQualityReservoirInfoList.add(waterQualityReservoirInfo);
        }
        return waterQualityReservoirInfoList;
    }

    public long getWaterQualityReservoirInfoCount() {
        return waterQualityReservoirInfoDao.count();
    }
}
