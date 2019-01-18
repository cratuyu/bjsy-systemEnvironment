package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.IpInfo;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface IpInfoService extends IService<IpInfo> {

    PageInfo<IpInfo> selectByIpInfo(IpInfo ipInfo, Date beginTimes, Date endTimes,String userAgent, int start, int length);

    PageInfo<IpInfo> selectByPage(IpInfo ipInfo,int start, int length);

    List<IpInfo> getWeekIpInfo();

    List<IpInfo> getMmonthIpInfo(String time);

}
