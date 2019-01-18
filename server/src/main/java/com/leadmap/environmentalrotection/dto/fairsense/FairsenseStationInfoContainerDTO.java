package com.leadmap.environmentalrotection.dto.fairsense;

import com.leadmap.environmentalrotection.dto.air.DataContainerDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/4 9:39
 */
public class FairsenseStationInfoContainerDTO implements Serializable {

    public FairsenseStationInfoContainerDTO() {
        aqi = new DataContainerDTO();
        pm25 = new DataContainerDTO();
        so2 = new DataContainerDTO();
        no2 = new DataContainerDTO();
        o3 = new DataContainerDTO();
        co = new DataContainerDTO();
        pm10 = new DataContainerDTO();
    }

    private DataContainerDTO aqi;
    private DataContainerDTO pm25;
    private DataContainerDTO so2;
    private DataContainerDTO no2;
    private DataContainerDTO o3;
    private DataContainerDTO co;
    private DataContainerDTO pm10;


    public List<FairsenseStationInfoDTO> fairsenseStationInfoDTOList;


    private long recordCount;

    private long pageCount;

    public DataContainerDTO getAqi() {
        return aqi;
    }

    public void setAqi(DataContainerDTO aqi) {
        this.aqi = aqi;
    }

    public DataContainerDTO getPm25() {
        return pm25;
    }

    public void setPm25(DataContainerDTO pm25) {
        this.pm25 = pm25;
    }

    public DataContainerDTO getSo2() {
        return so2;
    }

    public void setSo2(DataContainerDTO so2) {
        this.so2 = so2;
    }

    public DataContainerDTO getNo2() {
        return no2;
    }

    public void setNo2(DataContainerDTO no2) {
        this.no2 = no2;
    }

    public DataContainerDTO getO3() {
        return o3;
    }

    public void setO3(DataContainerDTO o3) {
        this.o3 = o3;
    }

    public DataContainerDTO getCo() {
        return co;
    }

    public void setCo(DataContainerDTO co) {
        this.co = co;
    }

    public DataContainerDTO getPm10() {
        return pm10;
    }

    public void setPm10(DataContainerDTO pm10) {
        this.pm10 = pm10;
    }

    public List<FairsenseStationInfoDTO> getFairsenseStationInfoDTOList() {
        return fairsenseStationInfoDTOList;
    }

    public void setFairsenseStationInfoDTOList(List<FairsenseStationInfoDTO> fairsenseStationInfoDTOList) {
        this.fairsenseStationInfoDTOList = fairsenseStationInfoDTOList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }
}
