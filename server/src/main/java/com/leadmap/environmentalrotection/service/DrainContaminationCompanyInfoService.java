package com.leadmap.environmentalrotection.service;

import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.DrainContaminationCompanyInfoDao;
import com.leadmap.environmentalrotection.dao.DrainContaminationCompanyTypesDao;
import com.leadmap.environmentalrotection.entity.company.DrainContaminationCompanyInfo;
import com.leadmap.environmentalrotection.entity.company.DrainContaminationCompanyTypes;
import com.leadmap.environmentalrotection.entity.document.DocumentTypes;
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
 * @Date: 2018/7/18 14:44
 */
@Component
public class DrainContaminationCompanyInfoService {

    @Autowired
    private DrainContaminationCompanyInfoDao drainContaminationCompanyInfoDao;

    @Autowired
    private DrainContaminationCompanyTypesDao drainContaminationCompanyTypesDao;

    public List<DrainContaminationCompanyInfo> getDrainContaminationCompanyInfo(String type, String pageNumber, String pageSize) {
        PageRequest pageRequest = Util.buildPageRequest(pageNumber, pageSize, StringUtils.EMPTY);
        Specification<DrainContaminationCompanyInfo> spec = null;
        if (StringUtils.isNotBlank(type)) {
            spec = new Specification<DrainContaminationCompanyInfo>() {
                @Override
                public Predicate toPredicate(Root<DrainContaminationCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("monitorType"), type));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
        }

        List<DrainContaminationCompanyInfo> drainContaminationCompanyInfoList = new ArrayList<>();
        for (DrainContaminationCompanyInfo drainContaminationCompanyInfo : drainContaminationCompanyInfoDao.findAll(spec, pageRequest)) {
            drainContaminationCompanyInfoList.add(drainContaminationCompanyInfo);
        }
        return drainContaminationCompanyInfoList;
    }

    public long getDrainContaminationCompanyInfoCount(String type) {
        Specification<DrainContaminationCompanyInfo> spec = null;
        if (StringUtils.isNotBlank(type)) {
            spec = new Specification<DrainContaminationCompanyInfo>() {
                @Override
                public Predicate toPredicate(Root<DrainContaminationCompanyInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates = new ArrayList<>();

                    predicates.add(criteriaBuilder.equal(root.get("monitorType"), type));
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
            };
        }

        return drainContaminationCompanyInfoDao.count(spec);
    }

    public List<DrainContaminationCompanyTypes> getDrainContaminationCompanyTypes() {
        List<DrainContaminationCompanyTypes> drainContaminationCompanyTypes = new ArrayList<>();
        Iterable<DrainContaminationCompanyTypes> drainContaminationCompanyTypesDaoAll = drainContaminationCompanyTypesDao.findAll();
        for (DrainContaminationCompanyTypes gwtWaterInfo : drainContaminationCompanyTypesDaoAll) {
            drainContaminationCompanyTypes.add(gwtWaterInfo);
        }

        return drainContaminationCompanyTypes;
    }
}
