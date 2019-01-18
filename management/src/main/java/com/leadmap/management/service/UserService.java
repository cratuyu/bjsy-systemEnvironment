package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.User;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface UserService extends IService<User> {

    PageInfo<User> selectByPage(User user, int start, int length);

    User selectByUsername(String username);

    void delUser(Integer userid);

}
