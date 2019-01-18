package com.leadmap.environmentalrotection.entity.company;

import com.leadmap.environmentalrotection.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Company: www.leadmap.net
 * Description:
 * 项目信息
 * @author: ttq
 * @Date: 2018/7/10 10:12
 */
@Entity
@Table(name = "ProjectInfo")
public class ProjectInfo extends IdEntity implements Serializable {
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    /**
     * 项目名称
     */
    @Column(name = "projectName")
    private String projectName;

    /**
     * 所属行政区
     */
    @Column(name = "cityName")
    private String cityName;

    /**
     * 企业名称
     */
    @Column(name = "companyName")
    private String companyName;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 进展
     */
    @Column(name = "progress")
    private String progress;
}