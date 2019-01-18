package com.leadmap.erawl.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leadmap.erawl.common.httprequest.HttpRequest;
import com.leadmap.erawl.common.util.Util;
import com.leadmap.erawl.dao.FairsenseMonitorInfoDao;
import com.leadmap.erawl.dao.FairsenseStationInfoDao;
import com.leadmap.erawl.dto.MonitorContainerInfo;
import com.leadmap.erawl.dto.UserContainer;
import com.leadmap.erawl.entity.fairsense.FairsenseMonitorInfo;
import com.leadmap.erawl.entity.fairsense.FairsenseStationInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * @Date: 2018/7/17 14:46
 */
@Component
public class ErawlFairsenseService {
    private final static Logger logger = LoggerFactory.getLogger(ErawlFairsenseService.class);

    @Value("${fairsense.username}")
    private String userName;
    @Value("${fairsense.password}")
    private String password;
    @Value("${fairsense.loginurl}")
    private String loginUrl;
    @Value("${fairsense.getstationinfourl}")
    private String getStationInfoUrl;
    @Value("${fairsense.getrealtimedataurl}")
    private String getRealTimeDataUrl;
    @Value("${fairsense.gethistorydataurl}")
    private String getHistoryDataUrl;

    @Autowired
    private FairsenseStationInfoDao fairsenseStationInfoDao;

    @Autowired
    private FairsenseMonitorInfoDao fairsenseMonitorInfoDao;


    public void generateAllStationsRealTimeData() {
        try {
            String loginUrl = this.loginUrl;
            String loginParam = "username=" + this.userName + "&password=" + this.password + "&valid=";
            String dataStr = HttpRequest.sendPost(loginUrl, loginParam);
            UserContainer userContainer = JSONObject.parseObject(dataStr, UserContainer.class);

            List<BasicClientCookie> basicClientCookieList = new ArrayList<>();
            BasicClientCookie basicClientCookie = new BasicClientCookie("username", this.userName);
            basicClientCookieList.add(basicClientCookie);
            basicClientCookie = new BasicClientCookie("ssid", userContainer.getUser().getSsid());
            basicClientCookieList.add(basicClientCookie);

            List<FairsenseMonitorInfo> fairsenseMonitorInfoList = new ArrayList<>();
            for (FairsenseStationInfo fairsenseStationInfo : fairsenseStationInfoDao.findAll()) {
                String stationIds = fairsenseStationInfo.getKeyId() + "";
                String param = "";
                if (StringUtils.isNotBlank(stationIds)) {
                    param = "sids=" + stationIds;
                }

                String result = HttpRequest.sendPostAndGetJsonStr(this.getRealTimeDataUrl, param, "username=scbs01;ssid=" + userContainer.getUser().getSsid());
                List<MonitorContainerInfo> stationInfoList = JSONArray.parseArray(result, MonitorContainerInfo.class);
                if (stationInfoList != null && stationInfoList.size() > 0) {
                    MonitorContainerInfo monitorContainerInfo = stationInfoList.get(0);

                    Specification<FairsenseMonitorInfo> spec = new Specification<FairsenseMonitorInfo>() {
                        @Override
                        public Predicate toPredicate(Root<FairsenseMonitorInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                            List<Predicate> predicates = new ArrayList<>();

                            predicates.add(criteriaBuilder.equal(root.get("monitorTime"), monitorContainerInfo.getRecordTime()));
                            predicates.add(criteriaBuilder.equal(root.get("monitorId"), stationIds));
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        }
                    };

                    if (fairsenseMonitorInfoDao.findAll(spec).size() > 0) {
                        continue;
                    } else {
                        FairsenseMonitorInfo fairsenseMonitorInfo = new FairsenseMonitorInfo();
                        fairsenseMonitorInfo.setMonitorId(stationIds);
                        fairsenseMonitorInfo.setMonitorTime(monitorContainerInfo.getRecordTime());
                        fairsenseMonitorInfo.setInfo(result);
                        fairsenseMonitorInfo.setCreateTime(Util.StrToDate(monitorContainerInfo.getRecordTime()));
                        fairsenseMonitorInfoList.add(fairsenseMonitorInfo);
                    }
                }
            }
            fairsenseMonitorInfoDao.save(fairsenseMonitorInfoList);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

}
