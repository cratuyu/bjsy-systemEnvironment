package com.leadmap.mapservice.service;

import com.leadmap.mapservice.common.TwoTuple;
import com.leadmap.mapservice.common.Util;
import com.leadmap.mapservice.dao.DocumentInfoDao;
import com.leadmap.mapservice.dao.OpinionFeedbackDao;
import com.leadmap.mapservice.entity.DocumentInfo;
import com.leadmap.mapservice.entity.OpinionFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/28 9:17
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentInfoDao documentInfoDao;

    @Autowired
    private OpinionFeedbackDao opinionFeedbackDao;

    public TwoTuple<List<DocumentInfo>, Integer> getPageDocumentInfo(int start, int count, String beginTime, String endTime, String type, String title){
        Pageable page = new PageRequest(start, count);
        List<DocumentInfo> list = new ArrayList<>();
        Specification querySpecifi = new Specification<DocumentInfo>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                if(!beginTime.isEmpty()){
                    Date beginDate = Util.StrToDate(beginTime+" 00:00:00");
                    predicatesList.add(criteriaBuilder.greaterThan(root.get("createTime"), beginDate));
                }
                if(!endTime.isEmpty()){
                    Date endDate = Util.StrToDate(endTime + " 00:00:00");
                    predicatesList.add(criteriaBuilder.lessThan(root.get("createTime"), endDate));
                }
                if(!type.isEmpty()){
                    predicatesList.add(criteriaBuilder.like(root.get("type"),"%"+type+"%"));
                }
                if(!title.isEmpty()){
                    predicatesList.add(criteriaBuilder.like(root.get("title"), "%" + title + "%"));
                }
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id")));
                criteriaQuery.where(criteriaBuilder.and(predicatesList.toArray(new Predicate[predicatesList.size()])));
                return criteriaQuery.getRestriction();
            }
        };
        list = documentInfoDao.findAll(querySpecifi,page).getContent();
        Integer counts = documentInfoDao.findAll(querySpecifi).size() / 10;
        return new TwoTuple<List<DocumentInfo>, Integer>(list, counts);
    }

    public List<OpinionFeedback> getPageOpinionFeedbackInfo(int start, int count){
        Pageable page = new PageRequest(start,count);
        return opinionFeedbackDao.findAllByOrderByIdDesc(page);
    }
}
