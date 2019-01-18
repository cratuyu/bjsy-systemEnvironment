package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.InterfaceAccess;

import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface InterfaceService extends IService<InterfaceAccess> {

    PageInfo<InterfaceAccess> selectByInterface(InterfaceAccess interfaceAccess, Date beginTimes, Date endTimes, int start, int length);

    PageInfo<InterfaceAccess> selectByPage(InterfaceAccess interfaceAccess, int start, int length);

}
