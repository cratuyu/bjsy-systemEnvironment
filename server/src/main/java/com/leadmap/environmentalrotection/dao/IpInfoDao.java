package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.IpInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/9 14:56
 */
@Repository
public interface IpInfoDao extends PagingAndSortingRepository<IpInfo, Long>, JpaSpecificationExecutor<IpInfo> {


    /**
     *  判断ip是否存在
     *  @param ip
     *  @param flag
     */
    @Query(value = "select * from ip_info where ip =?1 and flag =?2 and create_time=?3 and user_name=?4", nativeQuery = true)
    public List<IpInfo> findByIpAndFlag(String ip, int flag , String date , String userName);

    /**
     *  判断ip是否存在
     *  @param ip
     *  @param flag
     */
    @Query(value = "select * from ip_info where ip =?1 and flag =?2 and create_time=?3", nativeQuery = true)
    public List<IpInfo> findByIp(String ip, int flag , String date);

    /**
     *  添加ip信息
     *  @param ip
     *
     */
    @Modifying
    @Transactional
    @Query(value = "insert into ip_info (id,ip,create_time, update_time, count, flag,user_agent ,user_name) " +
            "values ( NULL ,?1, ?2, now(), 1, 1,null ,null )", nativeQuery = true)
    public void insertIpInfo(String ip ,String data );

    /**
     *  更新ip信息
     * @param ip
     *
     */
    @Modifying
    @Transactional
    @Query(value = "update ip_info set update_time = now(), count=count+1 " +
            "where ip = ?1 and create_time =?2 and flag=1" ,nativeQuery = true)
    public void updateIpInfo(String ip , String date);


    /**
     *  添加ip信息
     *  @param ip
     *
     */
    @Modifying
    @Transactional
    @Query(value = "insert into ip_info (id,ip,create_time, update_time, count, flag ,user_agent ,user_name) " +
            "values ( NULL ,?1, ?2, now(), 1, 2 ,?3 ,?4)", nativeQuery = true)
    public void insertRegisteredUserIpInfo(String ip , String date ,String userAgent ,String userName);

    /**
     *  更新ip信息
     *  @param ip
     */
    @Modifying
    @Transactional
    @Query(value = "update ip_info set update_time = now(), count=count+1 " +
            "where ip = ?1 and create_time = ?2 and user_agent=?3 and user_name=?4 and flag=2 " ,nativeQuery = true)
    public void updateRegisteredUserIpInfo(String ip ,String date ,String userAgent ,String userName);

}
