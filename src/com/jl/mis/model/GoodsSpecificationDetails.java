package com.jl.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:23:41
 * @Description 商品规格详情model
 */
public class GoodsSpecificationDetails {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 商品规格id(购销存)
	 */
	private Integer commoditySpecificationId;
	/**
	 * 原价格(划横线价格)
	 */
	private Double oldPrice;
	/**
	 * 商品ID（外键）
	 */
	private Integer goodsId;
	/**
	 * 销量
	 */
	private Integer salesCount;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 上架时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date onShelvesTime;
	/**
	 * 下架时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date offShelvesTime;
	/**
	 * 操作人
	 */
	private String operatorIdentifier;
	/**
	 * 操作时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date operatorTime;
	
	 //根据结果需要，在model里另添格外的字段
	/**
	 * 编号
	 */
	private String identifier;
	/**
	 * 规格
	 */
	private String specifications;
	/**
	 * 购销存商品ID（外键）
	 */
	private Integer saleId;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 最低销售价格
	 */
	private Double miniPrice;
	/**
	 * 创建人信息
	 */
	private User user;
	/**
	 * 商品详情表
	 */
	private GoodsDetails goodsDetails;
	/**
	 * 参加的活动
	 */
	private String participateActivities;
	/**
	 * 参加的活动
	 */
	private List<String> participateActivitieList;
	
	/**
	 * 购销存中商品的状态(1:是，2：不是）
	 */
	private Integer gxcGoodsState;
	
	/**
	 * 购销存中商品的库存
	 */
	private Integer gxcGoodsStock;
	
	/**
	 * 购销存中的商品进价
	 */
	private double gxcPurchase;
	
	/**
	 * 购销存中的商品品牌
	 */
	private String brand;

	/**
	 * 商品规格表对应的多张图片
	 */
	private List<GoodsDisplayPicture> goodsDisplayPictures;
	
	/**
	 * 商品参与的活动
	 */
	private List<GoodsActivity> goodsActivitys;

	
	

	
	public double getGxcPurchase() {
		return gxcPurchase;
	}

	public void setGxcPurchase(double gxcPurchase) {
		this.gxcPurchase = gxcPurchase;
	}

	public List<GoodsActivity> getGoodsActivitys() {
		return goodsActivitys;
	}

	public void setGoodsActivitys(List<GoodsActivity> goodsActivitys) {
		this.goodsActivitys = goodsActivitys;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<GoodsDisplayPicture> getGoodsDisplayPictures() {
		return goodsDisplayPictures;
	}

	public void setGoodsDisplayPictures(List<GoodsDisplayPicture> goodsDisplayPictures) {
		this.goodsDisplayPictures = goodsDisplayPictures;
	}

	public List<String> getParticipateActivitieList() {
		return participateActivitieList;
	}

	public void setParticipateActivitieList(List<String> participateActivitieList) {
		this.participateActivitieList = participateActivitieList;
	}

	public Integer getGxcGoodsStock() {
		return gxcGoodsStock;
	}

	public void setGxcGoodsStock(Integer gxcGoodsStock) {
		this.gxcGoodsStock = gxcGoodsStock;
	}

	public Integer getGxcGoodsState() {
		return gxcGoodsState;
	}

	public void setGxcGoodsState(Integer gxcGoodsState) {
		this.gxcGoodsState = gxcGoodsState;
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

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications == null ? null : specifications.trim();
	}

	public Integer getSaleId() {
		return saleId;
	}

	public void setSaleId(Integer saleId) {
		this.saleId = saleId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(Integer salesCount) {
		this.salesCount = salesCount;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getOnShelvesTime() {
		return onShelvesTime;
	}

	public void setOnShelvesTime(Date onShelvesTime) {
		this.onShelvesTime = onShelvesTime;
	}

	public Date getOffShelvesTime() {
		return offShelvesTime;
	}

	public void setOffShelvesTime(Date offShelvesTime) {
		this.offShelvesTime = offShelvesTime;
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

	public GoodsDetails getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(GoodsDetails goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public String getParticipateActivities() {
		return participateActivities;
	}

	public void setParticipateActivities(String participateActivities) {
		this.participateActivities = participateActivities;
	}

	public Integer getCommoditySpecificationId() {
		return commoditySpecificationId;
	}

	public void setCommoditySpecificationId(Integer commoditySpecificationId) {
		this.commoditySpecificationId = commoditySpecificationId;
	}

	public Double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(Double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public Double getMiniPrice() {
		return miniPrice;
	}

	public void setMiniPrice(Double miniPrice) {
		this.miniPrice = miniPrice;
	}
	
}