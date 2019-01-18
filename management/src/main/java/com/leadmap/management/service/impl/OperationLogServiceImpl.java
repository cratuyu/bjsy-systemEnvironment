package com.leadmap.management.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.mapper.OperationLogMapper;
import com.leadmap.management.model.OperationLog;
import com.leadmap.management.service.OperationLogService;
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
@Service("operationLogService")
public class OperationLogServiceImpl extends BaseService<OperationLog> implements OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Override
    public PageInfo<OperationLog> selectByPage(OperationLog operationLog, int start, int length) {
        int page = start/length+1;
        //分页查询
        PageHelper.startPage(page, length);
        List<OperationLog> operationLogs = operationLogMapper.selectByLogSign(operationLog ,operationLog.getLogSign());
        return new PageInfo<>(operationLogs);
    }

    @Override
    public PageInfo<OperationLog> selectByOperationLog(OperationLog operationLog, Date beginTimes, Date endTimes, String logSign, int start, int length) {
        int page = start/length+1;
        //分页查询
        PageHelper.startPage(page, length);
        List<OperationLog> operationLogs = operationLogMapper.selectByOperationLog(operationLog,beginTimes,endTimes ,logSign);
        return new PageInfo<>(operationLogs);
    }

}
