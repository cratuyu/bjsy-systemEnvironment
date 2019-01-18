package com.leadmap.environmentalrotection.entity.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Company: www.leadmap.net
 * Description:接口访问统计
 *
 * @author: yxm
 * @Date: 2018/10/10 15:29
 */
@Entity
@Table(name = "interface_access")
public class InterfaceAccess implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 接口地址
     */
    @Column(name = "interface_address")
    private String interfaceAddress;

    /**
     * 接口名字
     */
    @Column(name = "interface_name")
    private String interfaceName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 访问次数
     */
    @Column(name = "count")
    private long count;

    /**
     * 接口访问时间 ms
     */
    @Column(name = "diff_time")
    private long diffTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
