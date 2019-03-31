package com.jl.mis.model;

import java.util.Date;

public class VersionMessage {
	/**
	 * 主键
	 */
    private Integer id;

    /**
     * 版本号
     */
    private Integer versionCode;

    /**
     * 版本名
     */
    private String versionName;
    
    /**
     * 版本信息
     */
    private String description;
    
    /**
     * 是android的还是ios的
     */
    private Integer isAndroidOriOS;

    /**
     * 是否为必须更新(0:不必，1：必更)
     */
    private Integer isMustUpdate;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName == null ? null : versionName.trim();
    }
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIsMustUpdate() {
        return isMustUpdate;
    }

    public void setIsMustUpdate(Integer isMustUpdate) {
        this.isMustUpdate = isMustUpdate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Integer getIsAndroidOriOS() {
		return isAndroidOriOS;
	}

	public void setIsAndroidOriOS(Integer isAndroidOriOS) {
		this.isAndroidOriOS = isAndroidOriOS;
	}
    
    
}