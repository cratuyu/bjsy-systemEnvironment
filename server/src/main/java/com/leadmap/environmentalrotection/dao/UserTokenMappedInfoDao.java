package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.UserInfo;
import com.leadmap.environmentalrotection.entity.user.UserTokenMappedInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:12
 */

@Repository
public interface UserTokenMappedInfoDao extends PagingAndSortingRepository<UserTokenMappedInfo, Long>, JpaSpecificationExecutor<UserTokenMappedInfo> {
    UserTokenMappedInfo findByToken(String accessToken);
}
