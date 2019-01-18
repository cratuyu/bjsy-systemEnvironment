package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.user.InterfaceAccess;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/10 15:54
 */
@Repository
public interface InterfaceAccessDao extends PagingAndSortingRepository<InterfaceAccess, Long>, JpaSpecificationExecutor<InterfaceAccess> {

    /**
     *  判断今天是否存在是否存在
     *  @param
     */
    @Query(value = "select * from interface_access where create_time=?1 and interface_address =?2  order by update_time desc limit 1 ",nativeQuery = true)
    public InterfaceAccess findCreateTimeAndInterfaceAddress(Date date,String methodName );

    /**
     * 添加
     * @param methodName
     * @param diffTime
     */
    @Modifying
    @Transactional
    @Query(value = "insert into interface_access (id,count,create_time, diff_time, interface_address, interface_name,update_time ) " +
            "values ( NULL ,1, now(), ?2, ?1,null ,now() )", nativeQuery = true)
    public void insertInterfaceAccess(String methodName, long diffTime);


    /**
     * 更新
     * @param date
     * @param methodName
     * @param count
     * @param diffTime
     */
    @Modifying
    @Transactional
    @Query(value = "update interface_access set count=?3 , diff_time=?4 ,update_time =now() " +
            "where create_time =?1 and interface_address = ?2 " ,nativeQuery = true)
    public void updateInterfaceAccess(Date date,String methodName,long count, long diffTime);


    /**
     * 根据接口地址获取访问次数
     * @param date
     * @param methodName
     * @return
     */
    @Query(value = "select count from interface_access where create_time=?1 and interface_address =?2  order by update_time desc limit 1 ",nativeQuery = true)
    public String getCountByInterfaceAddress(Date date, String methodName );
}
