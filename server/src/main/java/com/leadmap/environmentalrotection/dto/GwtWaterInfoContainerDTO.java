package com.leadmap.environmentalrotection.dto;

import com.leadmap.environmentalrotection.entity.river.GwtWaterInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: yxm
 * @Date: 2018/9/4 11:01
 */
public class GwtWaterInfoContainerDTO implements Serializable {

    private List<GwtWaterInfo> gwtWaterInfoList;

    public List<GwtWaterInfo> getGwtWaterInfoList() {
        return gwtWaterInfoList;
    }

    public void setGwtWaterInfoList(List<GwtWaterInfo> gwtWaterInfoList) {
        this.gwtWaterInfoList = gwtWaterInfoList;
    }

}
