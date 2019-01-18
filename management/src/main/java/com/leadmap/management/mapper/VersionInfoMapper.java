package com.leadmap.management.mapper;

import com.leadmap.management.model.VersionInfo;
import com.leadmap.management.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface VersionInfoMapper extends MyMapper<VersionInfo> {

    VersionInfo insertVersionInfo(VersionInfo versionInfo);

    List<VersionInfo> selectByVersionInfo(@Param("versionInfo")VersionInfo versionInfo);
}