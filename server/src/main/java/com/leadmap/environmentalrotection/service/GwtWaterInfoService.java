package com.leadmap.environmentalrotection.service;

import com.google.common.collect.Iterables;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.GwtWaterInfoDao;
import com.leadmap.environmentalrotection.dto.GwtWaterInfoDTO;
import com.leadmap.environmentalrotection.entity.river.GwtWaterInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 14:16
 */
@Component
public class GwtWaterInfoService {

    private final static Logger logger = LoggerFactory.getLogger(GwtWaterInfoService.class);

    @Value("${gwtWaterInfoUrl}")
    private String gwtWaterInfoUrl;

    @Autowired
    private GwtWaterInfoDao gwtWaterInfoDao;

    public Date getNewestDate() {
        List<GwtWaterInfo> gwtWaterInfoList = gwtWaterInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
            return gwtWaterInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }

    public Date getNewestInSpecialDay(Date date) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Iterable<GwtWaterInfo> gwtWaterInfoIterable = gwtWaterInfoDao.findAll(Util.getDaySpecification(date), sort);
        if (gwtWaterInfoIterable != null && ((List<GwtWaterInfo>) gwtWaterInfoIterable).size() > 0) {
            return ((List<GwtWaterInfo>) gwtWaterInfoIterable).get(0).getCreateTime();
        }
        return date;
    }

    public List<GwtWaterInfo> getGwtWaterInfo(String stationName, Date date) {
        if (gwtWaterInfoDao.count() == 0) {
            return null;
        }
        List<GwtWaterInfo> gwtWaterInfoList = new ArrayList<>();
        Iterable<GwtWaterInfo> gwtWaterInfoIterable = gwtWaterInfoDao.getGwtWaterInfoList(stationName);
        for (GwtWaterInfo gwtWaterInfo : gwtWaterInfoIterable) {
            generateGwtWaterInfo48h(gwtWaterInfo);
            generateGwtWaterInfo30d(gwtWaterInfo);
            gwtWaterInfoList.add(gwtWaterInfo);
        }
        return gwtWaterInfoList;
    }

    public long getGwtWaterInfoCount(Date date) {
        return gwtWaterInfoDao.count(Util.getHourSpecification(date));
    }

    public void generateGwtWaterInfo48h(GwtWaterInfo gwtWaterInfo) {
        Specification<GwtWaterInfo> spec1 = new Specification<GwtWaterInfo>() {
            @Override
            public Predicate toPredicate(Root<GwtWaterInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("siteName"), gwtWaterInfo.getSiteName()));
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), gwtWaterInfo.getCreateTime()));

                Calendar c = Calendar.getInstance();
                c.setTime(gwtWaterInfo.getCreateTime());
                c.add(Calendar.HOUR_OF_DAY, -48);
                Date preTime = c.getTime();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), preTime));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Iterable<GwtWaterInfo> gwtWaterInfoIterable = gwtWaterInfoDao.findAll(spec1, sort);
        String codmn48h = "";
        String strDo48h = "";
        String nh448h = "";
        String toc48h = "";
        String ph48h = "";

        for (GwtWaterInfo gwtWaterInfo1 : gwtWaterInfoIterable) {
            codmn48h += gwtWaterInfo1.getCodmn() + ",";
            strDo48h += gwtWaterInfo1.getStrDo() + ",";
            nh448h += gwtWaterInfo1.getNh4() + ",";
            toc48h += gwtWaterInfo1.getToc() + ",";
            ph48h += gwtWaterInfo1.getpH() + ",";
        }
//        gwtWaterInfo.setCodmn48h(codmn48h);
//        gwtWaterInfo.setStrDo48h(strDo48h);
//        gwtWaterInfo.setNh448h(nh448h);
//        gwtWaterInfo.setToc48h(toc48h);
//        gwtWaterInfo.setPh48h(ph48h);

    }

    public void generateGwtWaterInfo30d(GwtWaterInfo gwtWaterInfo) {
        Specification<GwtWaterInfo> spec1 = new Specification<GwtWaterInfo>() {
            @Override
            public Predicate toPredicate(Root<GwtWaterInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                Date date = Util.getIntegralPointDay(gwtWaterInfo.getCreateTime());
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DAY_OF_MONTH, 1);
                Date nextDay = c.getTime();
                predicates.add(criteriaBuilder.equal(root.get("siteName"), gwtWaterInfo.getSiteName()));
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"), nextDay));

                c = Calendar.getInstance();
                c.setTime(gwtWaterInfo.getCreateTime());
                c.add(Calendar.DAY_OF_MONTH, -29);
                Date pre30day = c.getTime();
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), pre30day));

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Sort sort = new Sort(Sort.Direction.ASC, "createTime");
        Iterable<GwtWaterInfo> gwtWaterInfoIterable = gwtWaterInfoDao.findAll(spec1, sort);

        String codmn30d = "";
        String strDo30d = "";
        String nh430d = "";
        String toc30d = "";
        String ph30d = "";
        for (int i = 29; i >= 0; i--) {
            Calendar c = Calendar.getInstance();
            c.setTime(Util.getIntegralPointDay(new Date()));
            c.add(Calendar.DAY_OF_MONTH, -i + 1);
            Date upperDay = c.getTime();

            c = Calendar.getInstance();
            c.setTime(Util.getIntegralPointDay(new Date()));
            c.add(Calendar.DAY_OF_MONTH, -i);
            Date lowerDay = c.getTime();

            Iterable<GwtWaterInfo> gwtWaterInfos = Iterables.filter(gwtWaterInfoIterable, new com.google.common.base.Predicate<GwtWaterInfo>() {
                @Override
                public boolean apply(GwtWaterInfo gwtWaterInfo1) {
                    return gwtWaterInfo1.getCreateTime().after(lowerDay) && gwtWaterInfo1.getCreateTime().before(upperDay);
                }
            });

            int number = 0;
            float codmnSum = 0;
            float strDoSum = 0;
            float nh4Sum = 0;
            float tocSum = 0;
            float phSum = 0;
            for (GwtWaterInfo gwtWaterInfo1 : gwtWaterInfos) {
                try {
                    float codmnValue = Float.parseFloat(gwtWaterInfo1.getCodmn());
                    codmnSum += codmnValue;
                } catch (Exception ex) {
                    codmnSum += 0;
                }

                try {
                    float doValue = Float.parseFloat(gwtWaterInfo1.getStrDo());
                    strDoSum += doValue;
                } catch (Exception ex) {
                    strDoSum += 0;
                }

                try {
                    float nh4Value = Float.parseFloat(gwtWaterInfo1.getNh4());
                    nh4Sum += nh4Value;
                } catch (Exception ex) {
                    nh4Sum += 0;
                }

                try {
                    float tocValue = Float.parseFloat(gwtWaterInfo1.getToc());
                    tocSum += tocValue;
                } catch (Exception ex) {
                    tocSum += 0;
                }

                try {
                    float phValue = Float.parseFloat(gwtWaterInfo1.getpH());
                    phSum += phValue;
                } catch (Exception ex) {
                    phSum += 0;
                }

                number++;
            }
            if (number != 0) {
                float avgInDay = 0;
                if (codmnSum == 0) {
                    codmn30d += "--,";
                } else {
                    avgInDay = codmnSum / number;
                    codmn30d += avgInDay + ",";
                }

                if (strDoSum == 0) {
                    strDo30d += "--,";
                } else {
                    avgInDay = strDoSum / number;
                    strDo30d += avgInDay + ",";
                }

                if (nh4Sum == 0) {
                    nh430d += "--,";
                } else {
                    avgInDay = nh4Sum / number;
                    nh430d += avgInDay + ",";
                }

                if (tocSum == 0) {
                    toc30d += "--,";
                } else {
                    avgInDay = tocSum / number;
                    toc30d += avgInDay + ",";
                }

                if (phSum == 0) {
                    ph30d += "--,";
                } else {
                    avgInDay = phSum / number;
                    ph30d += avgInDay + ",";
                }
            }
        }

//        gwtWaterInfo.setCodmn30d(codmn30d);
//        gwtWaterInfo.setStrDo30d(strDo30d);
//        gwtWaterInfo.setNh430d(nh430d);
//        gwtWaterInfo.setToc30d(toc30d);
//        gwtWaterInfo.setPh30d(ph30d);
    }


    /**
     * 获取水质列表信息
     * @param date
     * @return
     */
    public List<GwtWaterInfoDTO> getGwtWaterInfoList(Date date) {
        if (gwtWaterInfoDao.count() == 0) {
            return null;
        }
        List<GwtWaterInfoDTO> gwtWaterInfoDTOList = new ArrayList<>();

        Iterable<GwtWaterInfo> gwtWaterInfoIterable = gwtWaterInfoDao.findAll(Util.getHourSpecification(date));
        for (GwtWaterInfo gwtWaterInfo : gwtWaterInfoIterable) {
            GwtWaterInfoDTO gwtWaterInfoDTO = new GwtWaterInfoDTO();
            gwtWaterInfoDTO.setStationId(gwtWaterInfo.getId());
            gwtWaterInfoDTO.setStationName(gwtWaterInfo.getSiteName());
            gwtWaterInfoDTO.setX(gwtWaterInfo.getX());
            gwtWaterInfoDTO.setY(gwtWaterInfo.getY());
            gwtWaterInfoDTO.setCreateTime(gwtWaterInfo.getCreateTime());
            gwtWaterInfoDTO.setDateTime(gwtWaterInfo.getDateTime());
            gwtWaterInfoDTO.setCodmn(gwtWaterInfo.getCodmn());
            gwtWaterInfoDTO.setStrDo(gwtWaterInfo.getStrDo());
            gwtWaterInfoDTO.setNh4(gwtWaterInfo.getNh4());
            gwtWaterInfoDTO.setpH(gwtWaterInfo.getpH());
            gwtWaterInfoDTO.setLevel(gwtWaterInfo.getLevel());
            gwtWaterInfoDTOList.add(gwtWaterInfoDTO);
        }
        return gwtWaterInfoDTOList;
    }


    /**
     * 获取30天水质信息
     * @param stationName
     * @param date
     * @return
     */
    @Cacheable(value = "getGwtWaterInfo30d")
    public List<GwtWaterInfo> getGwtWaterInfo30d(String stationName, Date date) {
        List<GwtWaterInfo> gwtWaterInfoList = new ArrayList<>();
        Iterable<GwtWaterInfo> gwtWaterInfoIterable = gwtWaterInfoDao.getGwtWaterInfoList(stationName);
        for (GwtWaterInfo gwtWaterInfo : gwtWaterInfoIterable) {
            gwtWaterInfoList.add(gwtWaterInfo);
        }
        return gwtWaterInfoList;
    }

}
