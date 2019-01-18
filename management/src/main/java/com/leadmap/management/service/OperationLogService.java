package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.OperationLog;

import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface OperationLogService extends IService<OperationLog> {

    PageInfo<OperationLog> selectByPage(OperationLog operationLog, int start, int length);

    PageInfo<OperationLog> selectByOperationLog(OperationLog operationLog, Date beginTimes, Date endTimes, String logSign, int start, int length);

}
