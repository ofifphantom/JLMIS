package com.jl.mis.model;

import java.util.Date;
import java.util.List;

/**
 * 购物车model
 * 
 * @author 景雅倩
 * @date 2017-11-3 下午3:52:06
 * @Description TODO
 */
public class ShoppingCart {
	/**
	 * 主键id
	 */
	private Integer id;
	/**
	 * 商品id
	 */
	private Integer goodsDetailsId;
	/**
	 * 商品规格id
	 */
	private Integer goodsSpecificationDetailsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品规格名
	 */
	private String goodsSpecificationDetailsName;
	/**
	 * 商品封面图url
	 */
	private String goodsPicUrl;
	/**
	 * 商品现价
	 */
	private Double goodsUnitlPrice;
	/**
	 * 商品数量
	 */
	private Integer goodsNum;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 状态(0:有效，1：无效)
	 */
	private Integer state;
	/**
	 * 添加时间
	 */
	private Date addTime;
	
	 //根据结果需要，在model里另添格外的字段
	/**
	 * 商品信息
	 */
	private GoodsDetails goodsDetails;
	
	/**
	 * 查询出来的需要发送短信的单用户的商品总数
	 */
	private Integer goodsAllNum;
	
	/**
	 * 用户信息
	 */
	private User user;
	
	/**
	 * 活动id
	 */
	private Integer activityId;
	/**
	 * 活动名称
	 */
	private String activityName;
	
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getGoodsAllNum() {
		return goodsAllNum;
	}

	public void setGoodsAllNum(Integer goodsAllNum) {
		this.goodsAllNum = goodsAllNum;
	}

	public GoodsDetails getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(GoodsDetails goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodsDetailsId() {
		return goodsDetailsId;
	}

	public void setGoodsDetailsId(Integer goodsDetailsId) {
		this.goodsDetailsId = goodsDetailsId;
	}

	public Integer getGoodsSpecificationDetailsId() {
		return goodsSpecificationDetailsId;
	}

	public void setGoodsSpecificationDetailsId(Integer goodsSpecificationDetailsId) {
		this.goodsSpecificationDetailsId = goodsSpecificationDetailsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName == null ? null : goodsName.trim();
	}

	public String getGoodsSpecificationDetailsName() {
		return goodsSpecificationDetailsName;
	}

	public void setGoodsSpecificationDetailsName(String goodsSpecificationDetailsName) {
		this.goodsSpecificationDetailsName = goodsSpecificationDetailsName == null ? null
				: goodsSpecificationDetailsName.trim();
	}

	public String getGoodsPicUrl() {
		return goodsPicUrl;
	}

	public void setGoodsPicUrl(String goodsPicUrl) {
		this.goodsPicUrl = goodsPicUrl == null ? null : goodsPicUrl.trim();
	}

	public Double getGoodsUnitlPrice() {
		return goodsUnitlPrice;
	}

	public void setGoodsUnitlPrice(Double goodsUnitlPrice) {
		this.goodsUnitlPrice = goodsUnitlPrice;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
}