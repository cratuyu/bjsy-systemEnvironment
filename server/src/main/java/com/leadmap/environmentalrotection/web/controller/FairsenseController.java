package com.leadmap.environmentalrotection.web.controller;

import com.leadmap.environmentalrotection.dto.ResultInfo;
import com.leadmap.environmentalrotection.dto.ResultObjInfo;
import com.leadmap.environmentalrotection.dto.fairsense.FairsenseStationInfoContainerDTO;
import com.leadmap.environmentalrotection.dto.fairsense.FairsenseStationInfoDTO;
import com.leadmap.environmentalrotection.dto.fairsense.MonitorContainerInfo;
import com.leadmap.environmentalrotection.entity.air.AirQualityIndex;
import com.leadmap.environmentalrotection.entity.fairsense.FairsenseMonitorInfo;
import com.leadmap.environmentalrotection.service.CityAirService;
import com.leadmap.environmentalrotection.service.FairsenseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/11 15:05
 */
@RestController
public class FairsenseController {

    private final static Logger logger = LoggerFactory.getLogger(FairsenseController.class);

    @Autowired
    private FairsenseService fairsenseService;

    @Autowired
    private CityAirService cityAirService;

    /**
     * 获取微站站点列表
     *
     * @return
     */
    @ApiOperation(value = "获取微站站点列表", notes = "获取微站站点列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "pageNumber", required = true, dataType = "int", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, dataType = "int", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getFairsenseStationInfos", method = RequestMethod.GET)
    @ResponseBody
    @Transactional
    public ResultObjInfo<FairsenseStationInfoContainerDTO> getFairsenseStationInfos(ServletRequest request) {

        ResultObjInfo<FairsenseStationInfoContainerDTO> resultInfo = new ResultObjInfo<>();
        try {
            FairsenseStationInfoContainerDTO fairsenseStationInfoContainerDTO = new FairsenseStationInfoContainerDTO();

            List<FairsenseStationInfoDTO> finalList = new ArrayList<>();
            String strPageNumber = request.getParameter("pageNumber");
            String strPageSize = request.getParameter("pageSize");

            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            List<FairsenseStationInfoDTO> tempList = fairsenseService.getStationInfos();
            if (StringUtils.isNotBlank(strPageNumber) && StringUtils.isNotBlank(strPageSize)) {
                int pageNumber = Integer.parseInt(strPageNumber);
                int pageSize = Integer.parseInt(strPageSize);
                int currIdx = (pageNumber > 1 ? (pageNumber - 1) * pageSize : 0);
                for (int i = 0; i < pageSize && i < tempList.size() - currIdx; i++) {
                    finalList.add(tempList.get(currIdx + i));
                }

                long count = tempList.size();
                fairsenseStationInfoContainerDTO.setRecordCount(count);
                long pageCount = count / pageSize;
                if (count % pageSize > 0) {
                    pageCount += 1;
                }
                fairsenseStationInfoContainerDTO.setPageCount(pageCount);
            }
            List<FairsenseMonitorInfo> newestFairsenseMonitorInfo = fairsenseService.getNewestFairsenseMonitorInfo();

            for (FairsenseStationInfoDTO fairsenseStationInfoDTO : finalList) {
                for (FairsenseMonitorInfo fairsenseMonitorInfo : newestFairsenseMonitorInfo){
                    if ((fairsenseStationInfoDTO.getStationId() + "").equalsIgnoreCase(fairsenseMonitorInfo.getMonitorId())) {
                        String result = fairsenseMonitorInfo.getInfo();
                        List<AirQualityIndex> airQualityIndices = fairsenseService.getData(result);
                        fairsenseStationInfoDTO.setAirQualityIndexList(airQualityIndices);
                        break;
                    }else {
                        String result = null;
                        List<AirQualityIndex> airQualityIndices = fairsenseService.getData(result);
                        fairsenseStationInfoDTO.setAirQualityIndexList(airQualityIndices);
                    }
                }
            }
            fairsenseStationInfoContainerDTO.getAqi().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("aqi"));
            fairsenseStationInfoContainerDTO.getPm25().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("pm25"));
            fairsenseStationInfoContainerDTO.getSo2().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("so2"));
            fairsenseStationInfoContainerDTO.getNo2().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("no2"));
            fairsenseStationInfoContainerDTO.getO3().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("o3"));
            fairsenseStationInfoContainerDTO.getCo().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("co"));
            fairsenseStationInfoContainerDTO.getPm10().setAirIndicatorInfoList(cityAirService.getAirIndicatorByName("pm10"));
            fairsenseStationInfoContainerDTO.setFairsenseStationInfoDTOList(finalList);
            resultInfo.setData(fairsenseStationInfoContainerDTO);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
        }
        return resultInfo;
    }



    /**
     * 获取微站站点实时监测数据
     *
     * @return
     */
    @ApiOperation(value = "获取微站站点实时监测数据", notes = "获取微站站点实时监测数据(如果stations=1,2,3)则返回id=1,2,3的站点的实时数据，如果不传stations参数,则返回所有站点的监测数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stations", value = "stations", required = false, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value = "getFairsenseStationRealTimeData", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo<MonitorContainerInfo> getFairsenseStationRealTimeData(ServletRequest request) {
        ResultInfo<MonitorContainerInfo> resultInfo = new ResultInfo<>();
        String stations = request.getParameter("stations");
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(fairsenseService.getRealTimeData(stations));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }

    /**
     * 获取微站站点历史监测数据
     * @param request
     * @return
     */
    @ApiOperation(value = "获取微站站点历史监测数据", notes = "获取微站站点历史监测数据(传入单个站点id)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationid", value = "stationid", required = false, dataType = "string", paramType = "query", defaultValue = ""),
    })
    @RequestMapping(value = "getFairsenseStationHistoryData", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public ResultInfo<MonitorContainerInfo> getFairsenseStationHistoryData(ServletRequest request) {
        ResultInfo<MonitorContainerInfo> resultInfo = new ResultInfo<>();
        String stations = request.getParameter("stationid");
        try {
            resultInfo.setCode("200");
            resultInfo.setMsg("success");
            resultInfo.setData(fairsenseService.getHistoryData(stations));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resultInfo.setCode("500");
            resultInfo.setMsg("failure");
            resultInfo.setData(new ArrayList<>());
        }
        return resultInfo;
    }
}
