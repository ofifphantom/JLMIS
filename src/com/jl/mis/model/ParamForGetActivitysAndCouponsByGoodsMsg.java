package com.jl.mis.model;

import java.util.List;

/**
 * 根据商品信息列表获取活动列表和优惠券列表 参数model
 * @author 景雅倩
 *
 */
public class ParamForGetActivitysAndCouponsByGoodsMsg {
	/**
	 * 商品信息列表
	 */
	private List<GoodsMsg> goodsMsgList;
	
	/**
	 * 用户id
	 */
	private Integer userId;

	public List<GoodsMsg> getGoodsMsgList() {
		return goodsMsgList;
	}

	public void setGoodsMsgList(List<GoodsMsg> goodsMsgList) {
		this.goodsMsgList = goodsMsgList;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	

}
