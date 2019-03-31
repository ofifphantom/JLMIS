package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:36:56
 * @Description 收货地址model
 */
public class ShippingAddress {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 用户ID(外键)
	 */
    private Integer userId;
    /**
	 * 收货人姓名
	 */
    private String consigneeName;
    /**
	 * 收货人电话
	 */
    private String consigneeTel;
    /**
	 * 所在地区
	 */
    private String region;
    /**
	 * 详细地址
	 */
    private String detailedAddress;
    /**
	 * 是否为常用
	 */
    private Integer isCommonlyUsed;
    /**
     * 省编号
     */
    private String provinceCode;
    /**
     * 城市编号
     */
    private String cityCode;
    /**
     * 区/县编号
     */
    private String countyCode;
    /**
     * 环编号
     */
    private String ringCode;
    /**
	 * 操作时间
	 */
    private Date operatorTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress == null ? null : detailedAddress.trim();
    }

    public Integer getIsCommonlyUsed() {
        return isCommonlyUsed;
    }

    public void setIsCommonlyUsed(Integer isCommonlyUsed) {
        this.isCommonlyUsed = isCommonlyUsed;
    }

    public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	public String getRingCode() {
		return ringCode;
	}

	public void setRingCode(String ringCode) {
		this.ringCode = ringCode;
	}

	public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}