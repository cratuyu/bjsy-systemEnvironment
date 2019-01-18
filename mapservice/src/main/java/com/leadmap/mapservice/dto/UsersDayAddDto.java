package com.leadmap.mapservice.dto;

import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/26 13:41
 */

public class UsersDayAddDto implements Serializable{
    private String createTime;

    private Long count;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getCreateTime() {

        return createTime;
    }

    public Long getCount() {
        return count;
    }

}
