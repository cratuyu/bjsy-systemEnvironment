package com.leadmap.management.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.mapper.VersionInfoMapper;
import com.leadmap.management.model.VersionInfo;
import com.leadmap.management.service.VersionInfoService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@Service("versionInfoService")
public class VersionInfoServiceImpl extends BaseService<VersionInfo> implements VersionInfoService {

    @Resource
    private VersionInfoMapper versionInfoMapper;

    @Override
    public PageInfo<VersionInfo> selectByPage(VersionInfo versionInfo, int start, int length) {
        int page = start/length+1;
        Example example = new Example(VersionInfo.class);
        //分页查询
        PageHelper.startPage(page, length);
        List<VersionInfo> versionInfoList = versionInfoMapper.selectByVersionInfo(versionInfo);
        return new PageInfo<>(versionInfoList);
    }

    @Override
    public void insertVersionInfo(VersionInfo versionInfo) {
        versionInfoMapper.insertVersionInfo(versionInfo);
    }
}
