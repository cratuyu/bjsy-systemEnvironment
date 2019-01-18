package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.dao.AvenueInfoDao;
import com.leadmap.environmentalrotection.entity.weather.AvenueInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/19 20:25
 */
@Component
public class AvenueInfoService {

    @Autowired
    private AvenueInfoDao avenueInfoDao;

    public List<AvenueInfo> getAvenueInfos() {
        List<AvenueInfo> arrayList = new ArrayList<>();
        Iterable<AvenueInfo> avenueInfoIterable = avenueInfoDao.findAll();
        for (AvenueInfo avenueInfo : avenueInfoIterable) {
            arrayList.add(avenueInfo);
        }
        return arrayList;
    }
}
