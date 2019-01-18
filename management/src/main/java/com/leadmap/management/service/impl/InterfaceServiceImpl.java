package com.leadmap.management.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.mapper.InterfaceMapper;
import com.leadmap.management.model.InterfaceAccess;
import com.leadmap.management.service.InterfaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@Service("interfaceService")
public class InterfaceServiceImpl extends BaseService<InterfaceAccess> implements InterfaceService {

    @Resource
    private InterfaceMapper interfaceMapper;

    @Override
    public PageInfo<InterfaceAccess> selectByInterface(InterfaceAccess interfaceAccess, Date beginTimes, Date endTimes, int start, int length) {
        int page = start/length+1;
        //分页查询
        PageHelper.startPage(page, length);
        List<InterfaceAccess> interfaceAccessList = interfaceMapper.selectByInterface(interfaceAccess ,beginTimes,endTimes) ;
        return new PageInfo<>(interfaceAccessList);
    }

    @Override
    public PageInfo<InterfaceAccess> selectByPage(InterfaceAccess interfaceAccess, int start, int length) {
        int page = start/length+1;
        //分页查询
        PageHelper.startPage(page, length);
        List<InterfaceAccess> interfaceAccessList = interfaceMapper.selectAllInterface(interfaceAccess);
        return new PageInfo<>(interfaceAccessList);
    }

}
