package com.leadmap.management.service;

import com.leadmap.management.model.UserInfo;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface UserInfoService extends IService<UserInfo> {

    List<UserInfo> getUserRegister();

    List<UserInfo> getAllUserRegister(String time);

    Integer getAllUser();

}
