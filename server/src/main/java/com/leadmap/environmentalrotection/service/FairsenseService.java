package com.leadmap.environmentalrotection.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leadmap.environmentalrotection.common.httprequest.HttpRequest;
import com.leadmap.environmentalrotection.common.util.Util;
import com.leadmap.environmentalrotection.dao.FairsenseMonitorInfoDao;
import com.leadmap.environmentalrotection.dao.FairsenseStationInfoDao;
import com.leadmap.environmentalrotection.dto.fairsense.FairsenseStationInfoDTO;
import com.leadmap.environmentalrotection.dto.fairsense.MonitorContainerInfo;
import com.leadmap.environmentalrotection.dto.fairsense.UserContainer;
import com.leadmap.environmentalrotection.entity.air.AirQualityIndex;
import com.leadmap.environmentalrotection.entity.fairsense.FairsenseMonitorInfo;
import com.leadmap.environmentalrotection.entity.fairsense.FairsenseStationInfo;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/17 14:46
 */
@Component
public class FairsenseService {
    private final static Logger logger = LoggerFactory.getLogger(FairsenseService.class);

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

    public List<FairsenseStationInfoDTO> getStationInfos() throws IOException {
        if (fairsenseStationInfoDao.count() == 0) {
            String loginUrl = this.loginUrl;
            String loginParam = "username=" + this.userName + "&password=" + this.password + "&valid=";
            String dataStr = HttpRequest.sendPost(loginUrl, loginParam);
            UserContainer userContainer = JSONObject.parseObject(dataStr, UserContainer.class);

            List<BasicClientCookie> basicClientCookieList = new ArrayList<>();
            BasicClientCookie basicClientCookie = new BasicClientCookie("username", this.userName);
            basicClientCookieList.add(basicClientCookie);
            basicClientCookie = new BasicClientCookie("ssid", userContainer.getUser().getSsid());
            basicClientCookieList.add(basicClientCookie);
            List<FairsenseStationInfoDTO> fairsenseStationInfoDTOList = JSONArray.parseArray(dataStr = HttpRequest.sendPostAndGetJsonStr(this.getStationInfoUrl, null, "username=scbs01;ssid=" + userContainer.getUser().getSsid()), FairsenseStationInfoDTO.class);

            List<FairsenseStationInfo> fairsenseStationInfos = new ArrayList<>();
            for (FairsenseStationInfoDTO fairsenseStationInfoDTO : fairsenseStationInfoDTOList) {
                FairsenseStationInfo fairsenseStationInfo = new FairsenseStationInfo();
                fairsenseStationInfo.setKeyId(fairsenseStationInfoDTO.getStationId());
                fairsenseStationInfo.setLat(fairsenseStationInfoDTO.getY());
                fairsenseStationInfo.setLng(fairsenseStationInfoDTO.getX());
//                fairsenseStationInfo.setHeight(fairsenseStationInfoDTO.getGeo().getHeight());
//                fairsenseStationInfo.setLat(fairsenseStationInfoDTO.getGeo().getLat());
//                fairsenseStationInfo.setLng(fairsenseStationInfoDTO.getGeo().getLng());
//                fairsenseStationInfo.setHeight(fairsenseStationInfoDTO.getGeo().getHeight());
                fairsenseStationInfo.setName(fairsenseStationInfoDTO.getStationName());
//                fairsenseStationInfo.setParams(String.join(",", fairsenseStationInfoDTO.getParams()));
                fairsenseStationInfo.setStationtype(fairsenseStationInfoDTO.getStation_type());
                fairsenseStationInfos.add(fairsenseStationInfo);
            }
            fairsenseStationInfoDao.save(fairsenseStationInfos);
            return fairsenseStationInfoDTOList;
        } else {
            List<FairsenseStationInfoDTO> fairsenseStationInfoDTOList = new ArrayList<>();
            for (FairsenseStationInfo fairsenseStationInfo : fairsenseStationInfoDao.findAll()) {
                FairsenseStationInfoDTO fairsenseStationInfoDTO = new FairsenseStationInfoDTO();
                fairsenseStationInfoDTO.setStation_type(fairsenseStationInfo.getStationtype());
                fairsenseStationInfoDTO.setStationName(fairsenseStationInfo.getName());
                fairsenseStationInfoDTO.setX(fairsenseStationInfo.getLng());
                fairsenseStationInfoDTO.setY(fairsenseStationInfo.getLat());
//                GeoInfoDTO geoInfoDTO = new GeoInfoDTO();
//                geoInfoDTO.setHeight(fairsenseStationInfo.getHeight());
//                geoInfoDTO.setLat(fairsenseStationInfo.getLat());
//                geoInfoDTO.setLng(fairsenseStationInfo.getLng());
//                fairsenseStationInfoDTO.setGeo(geoInfoDTO);
                fairsenseStationInfoDTO.setStationId(fairsenseStationInfo.getKeyId());
//                List<String> params = Arrays.asList(fairsenseStationInfo.getParams().split(","));
//                fairsenseStationInfoDTO.setParams(params);

                fairsenseStationInfoDTOList.add(fairsenseStationInfoDTO);
            }

            return fairsenseStationInfoDTOList;
        }
    }

    public List<MonitorContainerInfo> getRealTimeData(String stationId) throws IOException {
        Date date = this.getNewestDate();
        Specification<FairsenseMonitorInfo> spec = new Specification<FairsenseMonitorInfo>() {
            @Override
            public Predicate toPredicate(Root<FairsenseMonitorInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("monitorTime"), Util.getStringDate(date)));

                predicates.add(criteriaBuilder.equal(root.get("monitorId"), stationId));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<FairsenseMonitorInfo> fairsenseMonitorInfoList = fairsenseMonitorInfoDao.findAll(spec);
        if (fairsenseMonitorInfoList.size() > 0) {
            String result = fairsenseMonitorInfoList.get(0).getInfo();
            List<MonitorContainerInfo> monitorContainerInfoList = JSONArray.parseArray(result, MonitorContainerInfo.class);
            return monitorContainerInfoList;
        }

        return new ArrayList<>();
    }

    public List<MonitorContainerInfo> getHistoryData(String stationId) throws IOException {
        List<MonitorContainerInfo> monitorContainerInfoList = new ArrayList<>();
        List<FairsenseMonitorInfo> fairsenseMonitorInfoInPre48h = this.getFairsenseMonitorInfoInPre48h();
        for (FairsenseMonitorInfo fairsenseMonitorInfo : fairsenseMonitorInfoInPre48h) {
            if ((stationId).equalsIgnoreCase(fairsenseMonitorInfo.getMonitorId())) {
                String result = fairsenseMonitorInfo.getInfo();
                List<MonitorContainerInfo> temp = JSONArray.parseArray(result, MonitorContainerInfo.class);
                monitorContainerInfoList.addAll(temp);
            }
        }
        Collections.reverse(monitorContainerInfoList);
        return monitorContainerInfoList;
    }

    public List<String> getLast48HourList() {
        List<String> last48HourList = new ArrayList<>();

        Date date = new Date();
        for (int i = 0; i < 48; i++) {
            last48HourList.add(getLastHourTime(date, i));
        }

        return last48HourList;
    }

    private String getLastHourTime(Date date, int n) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        ca.set(Calendar.HOUR_OF_DAY, ca.get(Calendar.HOUR_OF_DAY) - n);
        date = ca.getTime();
        return "" + date.getTime();
    }


    public Date getNewestDate() {
        List<FairsenseMonitorInfo> gwtWaterInfoList = fairsenseMonitorInfoDao.findTop1ByOrderByCreateTimeDesc();
        if (gwtWaterInfoList != null && gwtWaterInfoList.size() > 0) {
            return gwtWaterInfoList.get(0).getCreateTime();
        } else {
            return null;
        }
    }

    public List<FairsenseMonitorInfo> getNewestFairsenseMonitorInfo() {
        Date date = this.getNewestDate();
        Specification<FairsenseMonitorInfo> spec = new Specification<FairsenseMonitorInfo>() {
            @Override
            public Predicate toPredicate(Root<FairsenseMonitorInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.equal(root.get("monitorTime"), Util.getStringDate(date)));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        List<FairsenseMonitorInfo> fairsenseMonitorInfoList = fairsenseMonitorInfoDao.findAll(spec);
        return fairsenseMonitorInfoList;
    }

    public List<FairsenseMonitorInfo> getFairsenseMonitorInfoInPre48h() {
        Date date = this.getNewestDate();
        Specification<FairsenseMonitorInfo> spec = new Specification<FairsenseMonitorInfo>() {
            @Override
            public Predicate toPredicate(Root<FairsenseMonitorInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), date));

                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.HOUR_OF_DAY, -48);
                Date preDay = c.getTime();
                predicates.add(criteriaBuilder.greaterThan(root.get("createTime"), preDay));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<FairsenseMonitorInfo> fairsenseMonitorInfoList = fairsenseMonitorInfoDao.findAll(spec, sort);
        return fairsenseMonitorInfoList;
    }

    /**
     * 解析data 数据
     * @param result
     * @return
     */
    public List<AirQualityIndex> getData(String result){

        String pm2_5;
        String SO2;
        String CO;
        String PM10;
        String AQI;
        String O3;
        String NO2;

        if (result == null){
             pm2_5 = "-";
             SO2 = "-";
             CO = "-";
             PM10 = "-";
             AQI = "-";
             O3 = "-";
             NO2 = "-";
        }else {
            JSONArray jsonObj = JSONArray.parseArray(result);
            String json = jsonObj.getString(0);

            net.sf.json.JSONObject jsonObj1 = net.sf.json.JSONObject.fromObject(json);
            net.sf.json.JSONObject data = (net.sf.json.JSONObject) jsonObj1.get("data");
            net.sf.json.JSONObject jsonObj2 = net.sf.json.JSONObject.fromObject(data);

            net.sf.json.JSONObject json1 = (net.sf.json.JSONObject) jsonObj2.get("PM2_5");
            net.sf.json.JSONObject json2 = (net.sf.json.JSONObject) jsonObj2.get("SO2");
            net.sf.json.JSONObject json3 = (net.sf.json.JSONObject) jsonObj2.get("CO");
            net.sf.json.JSONObject json4 = (net.sf.json.JSONObject) jsonObj2.get("PM10");
            net.sf.json.JSONObject json6 = (net.sf.json.JSONObject) jsonObj2.get("AQI");
            net.sf.json.JSONObject json7 = (net.sf.json.JSONObject) jsonObj2.get("O3");
            net.sf.json.JSONObject json8 = (net.sf.json.JSONObject) jsonObj2.get("NO2");
            try {
                pm2_5 = json1.getString("Value");
            } catch (Exception ex) {
                pm2_5 = "-";
            }
            try {
                SO2 = json2.getString("Value");
            } catch (Exception ex) {
                SO2 = "-";
            }
            try {
                CO = json3.getString("Value");
            } catch (Exception ex) {
                CO = "-";
            }
            try {
                PM10 = json4.getString("Value");
            } catch (Exception ex) {
                PM10 = "-";
            }
            try {
                AQI = json6.getString("Value");
            } catch (Exception ex) {
                AQI = "-";
            }
            try {
                O3 = json7.getString("Value");
            } catch (Exception ex) {
                O3 = "-";
            }
            try {
                NO2 = json8.getString("Value");
            } catch (Exception ex) {
                NO2 = "-";
            }
        }

        AirQualityIndex airQualityIndex = new AirQualityIndex();

        airQualityIndex.setPM2_5(pm2_5);
        airQualityIndex.setSO2(SO2);
        airQualityIndex.setCO(CO);
        airQualityIndex.setPM10(PM10);
        airQualityIndex.setAQI(AQI);
        airQualityIndex.setO3(O3);
        airQualityIndex.setNO2(NO2);
        List<AirQualityIndex> airQualityIndices = new ArrayList<>();
        airQualityIndices.add(airQualityIndex);
        return airQualityIndices;
    }
}
