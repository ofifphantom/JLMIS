package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:25:03
 * @Description 热门分类model
 */
public class HotCategories {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 分类名称
	 */
    private String categoriesName;
    /**
	 * 分类图片URL
	 */
    private String categoriesUrl;
    /**
	 * 广告信息ID
	 */
    private Integer advertisementInformationId;
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

    public String getCategoriesName() {
        return categoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        this.categoriesName = categoriesName == null ? null : categoriesName.trim();
    }

    public String getCategoriesUrl() {
        return categoriesUrl;
    }

    public void setCategoriesUrl(String categoriesUrl) {
        this.categoriesUrl = categoriesUrl == null ? null : categoriesUrl.trim();
    }

    public Integer getAdvertisementInformationId() {
        return advertisementInformationId;
    }

    public void setAdvertisementInformationId(Integer advertisementInformationId) {
        this.advertisementInformationId = advertisementInformationId;
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