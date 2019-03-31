package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午3:54:26
 * @Description 广告信息model
 */
public class AdvertisementInformation {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 编号
	 */
	private String identifier;
	/**
	 * 分类
	 */
	private Integer type;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 图片URL
	 */
	private String picUrl;
	/**
	 * 链接类型
	 */
	private Integer urlType;
	/**
	 * 链接参数编号
	 */
	private Integer urlParameterId;

	/**
	 * 生效时间
	 */
	private Date effectTime;
	/**
	 * 是否生效
	 */
	private Integer effect;
	
	/**
	 * 创建人
	 */
	private String operatorIdentifier;
	/**
	 * 提交时间
	 */
	private Date operatorTime;
	
	//根据结果需要，在model里另添格外的字段
    /**
     * 用户表
     */
    private User user;
    /**
     * 商品详情表
     */
    private GoodsDetails goodsDetails;
    /**
     * 活动信息表
     */
    private ActivityInformation activityInformation;

    public GoodsDetails getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(GoodsDetails goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public ActivityInformation getActivityInformation() {
		return activityInformation;
	}

	public void setActivityInformation(ActivityInformation activityInformation) {
		this.activityInformation = activityInformation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier == null ? null : identifier.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getOperatorIdentifier() {
		return operatorIdentifier;
	}

	public void setOperatorIdentifier(String operatorIdentifier) {
		this.operatorIdentifier = operatorIdentifier == null ? null
				: operatorIdentifier.trim();
	}

	public Date getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(Date operatorTime) {
		this.operatorTime = operatorTime;
	}


}