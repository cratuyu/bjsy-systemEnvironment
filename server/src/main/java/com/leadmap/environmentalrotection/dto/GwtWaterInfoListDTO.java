package com.leadmap.environmentalrotection.dto;

import com.leadmap.environmentalrotection.dto.air.DataContainerDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/11/20 10:43
 */
public class GwtWaterInfoListDTO implements Serializable {

    public GwtWaterInfoListDTO(){
        gwt = new DataContainerDTO();
    }

    private DataContainerDTO gwt;

    public List<GwtWaterInfoDTO> gwtWaterInfoList;


    public DataContainerDTO getGwt() {
        return gwt;
    }

    public void setGwt(DataContainerDTO gwt) {
        this.gwt = gwt;
    }

    public List<GwtWaterInfoDTO> getGwtWaterInfoList() {
        return gwtWaterInfoList;
    }

    public void setGwtWaterInfoList(List<GwtWaterInfoDTO> gwtWaterInfoList) {
        this.gwtWaterInfoList = gwtWaterInfoList;
    }

}
