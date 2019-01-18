package com.leadmap.environmentalrotection.dao;

import com.leadmap.environmentalrotection.entity.document.DocumentInfo;
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
public interface DocumentInfoDao extends PagingAndSortingRepository<DocumentInfo, Long>, JpaSpecificationExecutor<DocumentInfo> {

    /**
     * @param type
     * @return
     */
    Iterable<DocumentInfo> findByType(String type);

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    DocumentInfo findById(long id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from DocumentInfo t where id = :id")
    List<DocumentInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "select id,document_url,publish_date,publisher,title,type,image_url,content from document_info ", nativeQuery = true)
    List<DocumentInfo> queryDocumentInfo();


    @Modifying
    @Transactional
    @Query(value = "select id,document_url,publish_date,publisher,title,type,image_url,content from document_info where title=:title", nativeQuery = true)
    List<DocumentInfo> queryDocumentInfoByTitle(@Param("title") String title);

}
