package com.leadmap.management.mapper;

import com.leadmap.management.model.UserInfo;
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
public interface UserInfoMapper extends MyMapper<UserInfo> {

    List<UserInfo> getUserRegister(@Param("beginTimes")Date beginTimes, @Param("endTimes") Date endTimes);

    Integer getAllUser();
}