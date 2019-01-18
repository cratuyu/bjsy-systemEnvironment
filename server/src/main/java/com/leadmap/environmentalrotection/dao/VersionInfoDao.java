package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.VersionInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/25 17:12
 */
@Repository
public interface VersionInfoDao extends PagingAndSortingRepository<VersionInfo, Long>, JpaSpecificationExecutor<VersionInfo> {

    @Transactional
    @Query(value = "select * from Version_info where version_platform=?1 order by update_time desc limit 1 ", nativeQuery = true)
    VersionInfo queryByVersionPlatform(String versionPlatform);

}
