package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午3:55:10
 * @Description 广告图片model
 */
public class AdvertisementPicture {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 图片URL
	 */
    private String picUrl;
    /**
	 * 链接类型
	 */
    private Integer urlType;
    /**
	 * 链接参数id
	 */
    private Integer urlParameterId;
    /**
	 * 广告信息ID
	 */
    private Integer advertisementInformationId;
    /**
	 * 生效时间
	 */
    private Date effectTime;
    /**
	 * 是否生效
	 */
    private Integer effect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

   

    public Integer getUrlType() {
		return urlType;
	}

	public void setUrlType(Integer urlType) {
		this.urlType = urlType;
	}

	public Integer getUrlParameterId() {
		return urlParameterId;
	}

	public void setUrlParameterId(Integer urlParameterId) {
		this.urlParameterId = urlParameterId;
	}

	public Integer getAdvertisementInformationId() {
        return advertisementInformationId;
    }

    public void setAdvertisementInformationId(Integer advertisementInformationId) {
        this.advertisementInformationId = advertisementInformationId;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }
}