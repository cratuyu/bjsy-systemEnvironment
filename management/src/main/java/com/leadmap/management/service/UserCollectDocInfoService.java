package com.leadmap.management.service;

import com.leadmap.management.model.UserCollectDocInfo;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/18 14:42
 */
public interface UserCollectDocInfoService extends IService<UserCollectDocInfo>  {

    public int deleteUserCollectByDoc(String docId);

    public List<UserCollectDocInfo> getUserCollectDocInfoByDocId(String docId);
}
