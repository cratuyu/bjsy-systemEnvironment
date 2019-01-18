package com.leadmap.mapservice.dao;

import com.leadmap.mapservice.entity.IpInfo;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/20 17:01
 */

@Repository
public interface IpInfoDao extends PagingAndSortingRepository<IpInfo, Long>, JpaSpecificationExecutor<IpInfo> {

    @Query(value = "select count(distinct ip) from ip_info where create_time=:time ", nativeQuery = true)
    Long getIpDayActive(@Param("time") String time);

    /**
     * 查找全部 flag为2 的记录
     * @param page
     * @return
     */
    @Query(value = "select * from ip_info where flag=2 order by update_time desc limit :page,10", nativeQuery = true)
    List<IpInfo> getAllByFlag(@Param("page")int page);

    @Query(value = "select count(id) from ip_info where flag=2", nativeQuery = true)
    Long getCount();

    /**
     * 根据起止时间查找
     * @param begintime
     * @param endtime
     * @param page
     * @return
     */
    @Query(value = "select * from ip_info where flag=2 and update_time between :begintime and :endtime order by update_time desc limit :page,10",nativeQuery = true)
    List<IpInfo> getByTime(@Param("begintime") Date begintime, @Param("endtime") Date endtime, @Param("page") int page);

    @Query(value = "select count(id) from ip_info where flag=2 and update_time between :begintime and :endtime",nativeQuery = true)
    Long getTimeCount(@Param("begintime") Date begintime, @Param("endtime") Date endtime);

    /**
     * 根据登录客户端查找
     * @param agent
     * @param page
     * @return
     */
    @Query(value = "select * from ip_info where flag=2 and user_agent = :agent order by update_time desc :page,10", nativeQuery = true)
    List<IpInfo> getByAgent(@Param("agent") String agent,@Param("page") int page);

    @Query(value = "select count(id) from ip_info where flag=2 and user_agent = :agent", nativeQuery = true)
    Long getAgentCount(@Param("agent") String agent);

    /**
     * 根据 起止时间和登录客户端 查找
     * @param begintime
     * @param endtime
     * @param agent
     * @param page
     * @return
     */
    @Query(value = "select * from ip_info where flag=2 and user_agent = :agent and update_time between :begintime and :endtime order by update_time desc limit :page,10", nativeQuery = true)
    List<IpInfo> getByAgentAndTime(@Param("begintime") Date begintime, @Param("endtime") Date endtime,@Param("agent") String agent ,@Param("page") int page);

    @Query(value = "select count(id) from ip_info where flag=2 and user_agent = :agent and update_time between :begintime and :endtime", nativeQuery = true)
    Long getAgentAndTimeCount(@Param("begintime") Date begintime, @Param("endtime") Date endtime,@Param("agent") String agent );

}
