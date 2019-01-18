package com.leadmap.management.mapper;

import com.leadmap.management.model.UserLikeDocInfo;
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
public interface UserLikeDocMapper extends MyMapper<UserLikeDocInfo> {

    public int deleteUserLikeByDoc(@Param("docId")String docId);

    public List<UserLikeDocInfo> getUserLikeDocInfoByDocId(@Param("docId")String docId);
}
