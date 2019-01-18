package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.VersionInfo;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface VersionInfoService extends IService<VersionInfo> {

    PageInfo<VersionInfo> selectByPage(VersionInfo versionInfo, int start, int length);

    void insertVersionInfo(VersionInfo versionInfo);

}
