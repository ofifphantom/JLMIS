package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:17:18
 * @Description 包邮地址model
 */
public class FreeDeliveryAddress {
	/**
	 * 主键
	 */
    private Integer id;
/**
	 * 省
	 */
    private String province;
/**
	 * 市
	 */
    private String city;
    /**
	 * 区/县
	 */
    private String county;
/**
	 * 环
	 */
    private String ring;
 /**
	 * 包邮设置表ID(外键)
	 */
    private Integer freeDeliveryId;
/**
	 * 操作人
	 */
    private String operatorIdentifier;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    public String getRing() {
		return ring;
	}

	public void setRing(String ring) {
		this.ring = ring;
	}

	public Integer getFreeDeliveryId() {
        return freeDeliveryId;
    }

    public void setFreeDeliveryId(Integer freeDeliveryId) {
        this.freeDeliveryId = freeDeliveryId;
    }

    public String getOperatorIdentifier() {
        return operatorIdentifier;
    }

    public void setOperatorIdentifier(String operatorIdentifier) {
        this.operatorIdentifier = operatorIdentifier == null ? null : operatorIdentifier.trim();
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}