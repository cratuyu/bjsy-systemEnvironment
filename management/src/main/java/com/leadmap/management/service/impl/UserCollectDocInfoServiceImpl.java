package com.leadmap.management.service.impl;

import com.leadmap.management.mapper.UserCollectDocMapper;
import com.leadmap.management.model.UserCollectDocInfo;
import com.leadmap.management.service.UserCollectDocInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/12/18 14:46
 */
@Service("userCollectDocInfoService")
public class UserCollectDocInfoServiceImpl extends BaseService<UserCollectDocInfo> implements UserCollectDocInfoService{

    @Resource
    private UserCollectDocMapper userCollectDocMapper;

    @Override
    public int deleteUserCollectByDoc(String docId) {
        return userCollectDocMapper.deleteUserCollectByDoc(docId);
    }

    @Override
    public List<UserCollectDocInfo> getUserCollectDocInfoByDocId(String docId) {
        return userCollectDocMapper.getUserCollectDocInfoByDocId(docId);
    }
}
