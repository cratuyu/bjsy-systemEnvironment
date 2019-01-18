package com.leadmap.management.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leadmap.management.mapper.DocumentMapper;
import com.leadmap.management.model.DocumentInfo;
import com.leadmap.management.service.DocumentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/10/30 8:44
 */
@Service("documentService")
public class DocumentServiceImpl extends BaseService<DocumentInfo> implements DocumentService {

    @Resource
    private DocumentMapper documentMapper;

    @Override
    public PageInfo<DocumentInfo> selectByPage(DocumentInfo documentInfo, int start, int length) {
        int page = start/length+1;
        String title = documentInfo.getTitle();
        String type = documentInfo.getType();
        //分页查询
        PageHelper.startPage(page, length);
        List<DocumentInfo> documentInfoList = documentMapper.selectByTitleAndType(documentInfo,title,type);
        return new PageInfo<>(documentInfoList);
    }

    @Override
    public PageInfo<DocumentInfo> selectByDocument(DocumentInfo documentInfo, Date beginTimes, Date endTimes, String title, String type, int start, int length) {
        int page = start/length+1;
        //分页查询
        PageHelper.startPage(page, length);
        List<DocumentInfo> documentInfoList = documentMapper.selectByDocument(documentInfo,beginTimes,endTimes,title,type);
        return new PageInfo<>(documentInfoList);
    }

    @Override
    public void insertDocumentInfo(DocumentInfo documentInfo) {
        documentMapper.insertDocumentInfo(documentInfo);
    }

    @Override
    public void updateDocumentInfo(DocumentInfo documentInfo) {
        documentMapper.updateDocumentInfo(documentInfo);
    }

    @Override
    public List<DocumentInfo> selectDocumentById(Long id) {
        return documentMapper.selectDocumentById(id);
    }
}
