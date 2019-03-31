package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午3:53:44
 * @Description 活动信息model
 */
public class ActivityInformation {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 编号
	 */
	private String identifier;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 活动类型
	 */
	private Integer activityType;
	/**
	 * 原价/门栏金额
	 */
	private Double price;
	/**
	 * 现价/折扣/立减金额/优惠金额
	 */
	private Double discount;
	/**
	 * 单人最大购买数量
	 */
	private Integer maxNum;
	/**
	 * 介绍
	 */
	private String introduction;
	/**
	 * 消息图url
	 */
	private String messagePicUrl;
	/**
	 * 展示图url
	 */
	private String showPicUrl;
	/**
	 * 预算
	 */
	private Double budget;
	/**
	 * 有效期开始
	 */
	private Date beginValidityTime;
	/**
	 * 有效期结束
	 */
	private Date endValidityTime;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 创建人
	 */
	private String operatorIdentifier;
	/**
	 * 操作时间
	 */
	private Date operatorTime;
	/**
	 * 提交人
	 */
	private String submitterIdentifier;
	/**
	 * 提交时间
	 */
	private Date submitTime;
	
	  //根据结果需要，在model里另添格外的字段
    /**
     * 用户表
     */
    private User user;
    
	
	/**
	 * 商品/分类
	 */
	private String goods;
	
	/**
	 * 商品/分类状态
	 */
	private String goodsState;
	
	/**
	 * 优惠券
	 */
	private String coupon;
	
	/**
	 * 提交人姓名
	 */
	private String submitterName;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction == null ? null : introduction.trim();
	}

	public String getMessagePicUrl() {
		return messagePicUrl;
	}

	public void setMessagePicUrl(String messagePicUrl) {
		this.messagePicUrl = messagePicUrl == null ? null : messagePicUrl.trim();
	}

	public String getShowPicUrl() {
		return showPicUrl;
	}

	public void setShowPicUrl(String showPicUrl) {
		this.showPicUrl = showPicUrl == null ? null : showPicUrl.trim();
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Date getBeginValidityTime() {
		return beginValidityTime;
	}

	public void setBeginValidityTime(Date beginValidityTime) {
		this.beginValidityTime = beginValidityTime;
	}

	public Date getEndValidityTime() {
		return endValidityTime;
	}

	public void setEndValidityTime(Date endValidityTime) {
		this.endValidityTime = endValidityTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSubmitterIdentifier() {
		return submitterIdentifier;
	}

	public void setSubmitterIdentifier(String submitterIdentifier) {
		this.submitterIdentifier = submitterIdentifier;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getSubmitterName() {
		return submitterName;
	}

	public void setSubmitterName(String submitterName) {
		this.submitterName = submitterName;
	}

	

	
}