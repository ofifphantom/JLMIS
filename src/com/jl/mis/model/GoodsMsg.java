package com.jl.mis.model;

import java.util.List;
/**
 * 将要购买的商品列表信息model
 * @author 景雅倩
 *
 */
public class GoodsMsg {
	/**
	 * 商品参与的活动列表
	 */
	private List<ActivityInformation> goodsActivityList;
	
	/**
	 * 商品规格参与的活动列表
	 */
	private List<ActivityInformation> goodsSpeActivityList;
	/**
	 * 要购买的数量
	 */
	private Integer number;
	
	/**
	 * 商品单价(原价)
	 */
	private Double unitPrice;
	/**
	 * 商品分类id
	 */
	//private Integer classificationId;
	/**
	 * 商品id
	 */
	//private Integer goodsId;
	/**
	 * 商品规格id
	 */
	private Integer goodsSpeId;
	
	public List<ActivityInformation> getGoodsActivityList() {
		return goodsActivityList;
	}
	public void setGoodsActivityList(List<ActivityInformation> goodsActivityList) {
		this.goodsActivityList = goodsActivityList;
	}
	public List<ActivityInformation> getGoodsSpeActivityList() {
		return goodsSpeActivityList;
	}
	public void setGoodsSpeActivityList(
			List<ActivityInformation> goodsSpeActivityList) {
		this.goodsSpeActivityList = goodsSpeActivityList;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
/*	public Integer getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}*/
	public Integer getGoodsSpeId() {
		return goodsSpeId;
	}
	public void setGoodsSpeId(Integer goodsSpeId) {
		this.goodsSpeId = goodsSpeId;
	}
	
	

}
