package com.leadmap.mapservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:
 *
 * @author: ljy
 * @Date: 2018/12/28 13:20
 */
@Entity
@Table(name = "InterfaceAccess")
public class InterfaceAccess extends IdEntity implements Serializable {

    /**
     * 接口地址
     */
    @Column(name = "interfaceAddress")
    private String interfaceAddress;

    /**
     * 接口名字
     */
    @Column(name = "interfaceName")
    private String interfaceName;

    /**
     * 创建时间
     */
    @Column(name = "updateTime")
    private Date updateTime;

    /**
     * 访问次数
     */
    @Column(name = "count")
    private long count;

    /**
     * 接口访问时间 ms
     */
    @Column(name = "diffTime")
    private long diffTime;

    public String getInterfaceAddress() {
        return interfaceAddress;
    }

    public void setInterfaceAddress(String interfaceAddress) {
        this.interfaceAddress = interfaceAddress;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getDiffTime() {
        return diffTime;
    }

    public void setDiffTime(long diffTime) {
        this.diffTime = diffTime;
    }
}
