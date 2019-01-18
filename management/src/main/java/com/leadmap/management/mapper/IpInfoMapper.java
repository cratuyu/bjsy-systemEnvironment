package com.leadmap.management.mapper;

import com.leadmap.management.model.IpInfo;
import com.leadmap.management.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface IpInfoMapper extends MyMapper<IpInfo> {

    List<IpInfo> selectByIpInfo(@Param("ipInfo")IpInfo ipInfo, @Param("beginTimes")Date beginTimes,@Param("endTimes") Date endTimes ,@Param("userAgent")String userAgent);

    List<IpInfo> selectByIpInfos(@Param("userAgent")String userAgent);

    Integer getWeekIpInfo(@Param("beginTimes")Date beginTimes, @Param("endTimes") Date endTimes);

    Integer getMmonthIpInfo(@Param("beginTimes")Date beginTimes, @Param("endTimes") Date endTimes);

}