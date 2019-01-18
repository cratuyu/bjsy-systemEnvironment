package com.leadmap.environmentalrotection.dto.air;

import com.leadmap.environmentalrotection.entity.air.AirIndicatorInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ttq
 * @Date: 2018/7/12 20:20
 */
public class DataContainerDTO implements Serializable {
    public DataContainerDTO() {
        airIndicatorInfoList = new ArrayList<>();
        dataInfoDTOList = new ArrayList<>();
    }

    public List<AirIndicatorInfo> getAirIndicatorInfoList() {
        return airIndicatorInfoList;
    }

    public void setAirIndicatorInfoList(List<AirIndicatorInfo> airIndicatorInfoList) {
        this.airIndicatorInfoList = airIndicatorInfoList;
    }

    public List<DataInfoDTO> getDataInfoDTOList() {
        return dataInfoDTOList;
    }

    public void setDataInfoDTOList(List<DataInfoDTO> dataInfoDTOList) {
        this.dataInfoDTOList = dataInfoDTOList;
    }

    private List<AirIndicatorInfo> airIndicatorInfoList;

    private List<DataInfoDTO> dataInfoDTOList;
}
