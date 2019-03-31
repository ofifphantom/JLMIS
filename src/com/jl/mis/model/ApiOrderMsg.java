package com.jl.mis.model;

import java.util.Date;

/**
 * 
 * @author 柳亚婷
 * @Description: 订单信息api所需model
 * @date: 2018年7月2日 下午9:00:41
 */
public class ApiOrderMsg {
	
	/**
	 * 订单编号
	 */
	String orderNo;
	/**
	 * 封面照片
	 */
	String goodsCoverUrl;
	/**
	 * 订单状态
	 */
	Integer orderState;
	/**
	 * 订单金额
	 */
	double orderPrice;
	/**
	 * 下单时间
	 */
    private Date placeOrderTime;
    
    
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getGoodsCoverUrl() {
		return goodsCoverUrl;
	}
	public void setGoodsCoverUrl(String goodsCoverUrl) {
		this.goodsCoverUrl = goodsCoverUrl;
	}
	public Integer getOrderState() {
		return orderState;
	}
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}
	public double getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	public Date getPlaceOrderTime() {
		return placeOrderTime;
	}
	public void setPlaceOrderTime(Date placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}

    
}
