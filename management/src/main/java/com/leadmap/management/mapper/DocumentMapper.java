package com.leadmap.management.mapper;

import com.leadmap.management.model.DocumentInfo;
import com.leadmap.management.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
public interface DocumentMapper extends MyMapper<DocumentInfo> {

    /**
     *
     * @param documentInfo
     * @param beginTimes
     * @param endTimes
     * @param title
     * @param type
     * @return
     */
    List<DocumentInfo> selectByDocument(@Param("documentInfo")DocumentInfo documentInfo, @Param("beginTimes")Date beginTimes, @Param("endTimes") Date endTimes , @Param("title")String title,@Param("type")String type);

    List<DocumentInfo> selectByTitleAndType(@Param("documentInfo")DocumentInfo documentInfo,@Param("title")String title,@Param("type")String type);

    DocumentInfo insertDocumentInfo(DocumentInfo documentInfo);

    DocumentInfo updateDocumentInfo(DocumentInfo documentInfo);

    public List<DocumentInfo> selectDocumentById(Long id);

}