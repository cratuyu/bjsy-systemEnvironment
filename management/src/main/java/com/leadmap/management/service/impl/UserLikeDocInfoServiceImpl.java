package com.leadmap.management.service.impl;

import com.leadmap.management.mapper.UserLikeDocMapper;
import com.leadmap.management.model.UserLikeDocInfo;
import com.leadmap.management.service.UserLikeDocInfoService;
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
@Service("userLikeDocInfoService")
public class UserLikeDocInfoServiceImpl extends BaseService<UserLikeDocInfo> implements UserLikeDocInfoService {

    @Resource
    private UserLikeDocMapper userLikeDocMapper;

    @Override
    public int deleteUserLikeByDoc(String docId) {
        return userLikeDocMapper.deleteUserLikeByDoc(docId);
    }

    @Override
    public List<UserLikeDocInfo> getUserLikeDocInfoByDocId(String docId) {
        return userLikeDocMapper.getUserLikeDocInfoByDocId(docId);
    }
}
