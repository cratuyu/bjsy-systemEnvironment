//package com.leadmap.management.dao;
//
//import com.leadmap.management.model.IpInfo;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import javax.transaction.Transactional;
//import java.util.Date;
//import java.util.List;
//
///**
// * Company: www.leadmap.net
// * Description:
// *
// * @author: yxm
// * @Date: 2018/10/9 14:56
// */
//@Repository
//public interface IpInfoDao extends JpaRepository<IpInfo, Long> {
//
//    @Modifying
//    @Transactional
//    @Query(value = "select * from ip_info where updateTime between ?2 and ?3 ", nativeQuery = true)
//    public List<IpInfo> selectByIpInfo(IpInfo ipInfo, Date beginTime, Date endTime);
//
//
//}
