package com.leadmap.mapservice.dao;

import com.leadmap.mapservice.entity.DocumentInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/20 10:33
 */

@Repository
public interface DocumentInfoDao extends PagingAndSortingRepository<DocumentInfo, Long>, JpaSpecificationExecutor<DocumentInfo> {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    DocumentInfo findById(Long id);

    /**
     * 根据id查找
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query("from DocumentInfo t where id = :id")
    List<DocumentInfo> queryFamilyList(@Param("id") Long id, Pageable pageable);

    @Query("from DocumentInfo")
    List<DocumentInfo> getDocumentInfoList(Pageable page);

    @Query("select count(id) from DocumentInfo ")
    Long getCount();


    /**
     *
     * @param id
     * @param documentUrl
     * @param imageUrl
     * @param title
     * @param type
     * @param publisher
     * @return
     */
    @Query("update DocumentInfo e set e.documentUrl = :documentUrl, e.imageUrl = :imageUrl, " +
            "e.title = :title, e.type = :type, e.publisher = :publisher where e.id = :id ")
    @Modifying
    Integer updateData(@Param("id") Long id, @Param("documentUrl") String documentUrl, @Param("imageUrl") String imageUrl,
                       @Param("title") String title, @Param("type") String type, @Param("publisher") String publisher);
}
