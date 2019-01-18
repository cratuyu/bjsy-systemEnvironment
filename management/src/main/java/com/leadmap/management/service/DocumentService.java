package com.leadmap.management.service;

import com.github.pagehelper.PageInfo;
import com.leadmap.management.model.DocumentInfo;

import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 10:22
 */
public interface DocumentService extends IService<DocumentInfo>  {

    PageInfo<DocumentInfo> selectByPage(DocumentInfo documentInfo, int start, int length);

    PageInfo<DocumentInfo> selectByDocument(DocumentInfo documentInfo, Date beginTimes, Date endTimes,String title,String type, int start, int length);

    void insertDocumentInfo(DocumentInfo documentInfo);

    void updateDocumentInfo(DocumentInfo documentInfo);

    public List<DocumentInfo> selectDocumentById(Long id);
}
