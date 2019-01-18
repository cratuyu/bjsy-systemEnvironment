package com.leadmap.management.service;

import com.leadmap.management.model.UserLikeDocInfo;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/18 14:40
 */
public interface UserLikeDocInfoService extends IService<UserLikeDocInfo>{

    public int deleteUserLikeByDoc(String docId);

    public List<UserLikeDocInfo> getUserLikeDocInfoByDocId(String docId);
}
