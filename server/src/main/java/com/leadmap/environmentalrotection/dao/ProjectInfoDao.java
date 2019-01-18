package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.company.ProjectInfo;
import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
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
public interface ProjectInfoDao extends PagingAndSortingRepository<ProjectInfo, Long>, JpaSpecificationExecutor<ProjectInfo> {

//    /**
//     * @param type
//     * @return
//     */
//    Iterable<DocumentInfo> findByType(String type);
//
//    /**
//     * 根据id查找
//     *
//     * @param id
//     * @return
//     */
//    DocumentInfo findById(long id);
//
//    /**
//     * 根据id查找
//     *
//     * @param id
//     * @param pageable
//     * @return
//     */
//    @Query("from DocumentInfo t where id = :id")
//    List<DocumentInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

}
