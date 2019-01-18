package com.leadmap.management.mapper;

import com.leadmap.management.model.Role;
import com.leadmap.management.util.MyMapper;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface RoleMapper extends MyMapper<Role> {

    public List<Role> queryRoleListWithSelected(Integer id);
}