package com.leadmap.management.mapper;

import com.leadmap.management.model.InterfaceAccess;
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
public interface InterfaceMapper extends MyMapper<InterfaceAccess> {

    List<InterfaceAccess> selectByInterface(@Param("interfaceAccess")InterfaceAccess interfaceAccess, @Param("beginTimes")Date beginTimes, @Param("endTimes") Date endTimes);

    List<InterfaceAccess> selectAllInterface(@Param("interfaceAccess")InterfaceAccess interfaceAccess);

}