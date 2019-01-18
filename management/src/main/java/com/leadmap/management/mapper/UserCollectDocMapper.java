package com.leadmap.management.mapper;

import com.leadmap.management.model.UserCollectDocInfo;
import com.leadmap.management.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/18 14:49
 */
public interface UserCollectDocMapper extends MyMapper<UserCollectDocInfo> {

    public int deleteUserCollectByDoc(@Param("docId") String docId);

    public List<UserCollectDocInfo> getUserCollectDocInfoByDocId(@Param("docId")String docId);
}
