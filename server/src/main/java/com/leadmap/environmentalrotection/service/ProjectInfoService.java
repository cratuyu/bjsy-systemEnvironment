package com.leadmap.environmentalrotection.service;

import com.google.common.collect.Lists;
import com.leadmap.environmentalrotection.dao.*;
import com.leadmap.environmentalrotection.dto.*;
import com.leadmap.environmentalrotection.entity.company.ProjectInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/18 10:54
 */
@Component
public class ProjectInfoService {
    private final static Logger logger = LoggerFactory.getLogger(ProjectInfoService.class);

    @Autowired
    private ProjectInfoDao projectInfoDao;

    public ResultInfo<ProjectInfo> getProjectInfos(String search, PageRequest pageRequest) {
        ResultInfo<ProjectInfo> resultInfo = new ResultInfo<>();
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            Specification<ProjectInfo> spec = null;
            if (StringUtils.isNotBlank(search)) {
                spec = new Specification<ProjectInfo>() {
                    @Override
                    public Predicate toPredicate(Root<ProjectInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                        List<Predicate> predicates = new ArrayList<>();

                        predicates.add(criteriaBuilder.like(root.get("projectName"), "%" + search + "%"));
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                };
            }

            long count = projectInfoDao.count(spec);
            resultInfo.setRecordCount(count);
            if (pageRequest != null) {
                long pageCount = count / pageRequest.getPageSize();
                if ((count % pageRequest.getPageSize()) > 0) {
                    pageCount += 1;
                }
                resultInfo.setPageCount(pageCount);
            }

            Iterable<ProjectInfo> iterable = projectInfoDao.findAll(spec, pageRequest);
            resultInfo.setData(Lists.newArrayList(iterable));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }
}
