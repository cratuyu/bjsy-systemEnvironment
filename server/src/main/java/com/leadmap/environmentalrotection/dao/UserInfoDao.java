package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/5/17 10:12
 */

@Repository
public interface UserInfoDao extends PagingAndSortingRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    UserInfo findById(Long id);

    /**
     * @param userName
     * @return
     */
    UserInfo findByUserName(String userName);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from UserInfo t where id = :id")
    List<UserInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    /**
     * @param openId
     * @return
     */
    UserInfo findByOpenId(String openId);

    /**
     * @param userName
     * @return
     */
    @Query("from UserInfo t where userName = :userName and SMSVerificationCode= :SMSVerificationCode")
    UserInfo getUserInfoList(@Param("userName") String userName , @Param("SMSVerificationCode") String SMSVerificationCode);


    /**
     * @param
     * @return
     */
    @Query("select t from UserInfo t where t.userName=:userName and t.password=:password")
    public UserInfo findByloginNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    List<UserInfo> queryUserInfoByUserName(String userName);


    @Modifying
    @Transactional
    @Query(value = "select * from user_info ", nativeQuery = true)
    List<UserInfo> queryUserInfo();

}
