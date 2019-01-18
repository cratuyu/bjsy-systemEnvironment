package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.dao.InterfaceAccessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/10 15:52
 */
@Component
public class InterfaceAccessService {

    @Autowired
    private InterfaceAccessDao interfaceAccessDao;

    /**
     * 根据接口地址获取访问次数
     *
     * @param methodName
     */
    public long getCountByInterfaceAddress(String methodName) {
        try {
            Date date = new Date();
            String number = interfaceAccessDao.getCountByInterfaceAddress(date, methodName);
            if (number == null) {
                return 0;
            } else {
                return Long.parseLong(number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 添加接口访问时间和次数
     *
     * @param methodName
     * @param
     * @param diffTime
     */
    public void addInterfaceAccessInfo(String methodName, long diffTime) {
        try {
//            Date date = new Date();
//            InterfaceAccess interfaceAccess = interfaceAccessDao.findCreateTimeAndInterfaceAddress(date,methodName);
//            if(interfaceAccess == null){
            interfaceAccessDao.insertInterfaceAccess(methodName, diffTime);
//            }else{
//                interfaceAccessDao.updateInterfaceAccess(date, methodName, count,  diffTime);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
