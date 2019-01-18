package com.leadmap.management.mapper;

import com.leadmap.management.model.OperationLog;
import com.leadmap.management.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface OperationLogMapper extends MyMapper<OperationLog> {

    List<OperationLog> selectByOperationLog(@Param("operationLog")OperationLog operationLog, @Param("beginTimes")Date beginTimes, @Param("endTimes") Date endTimes , @Param("logSign")String logSign);

    List<OperationLog> selectByLogSign(@Param("operationLog")OperationLog operationLog, @Param("logSign")String logSign);
}