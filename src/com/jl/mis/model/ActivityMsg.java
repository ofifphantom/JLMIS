package com.jl.mis.model;

import java.util.List;


public class ActivityMsg {
	
	    /**
	     * 活动信息
	     */
	    private ActivityInformation activityInformation;
	    
	    /**
	     * 将要购买的商品通过活动优惠后的价格
	     */
	    private Double afterDiscountMoney;
	    
	    /**
	     * 经该活动优惠后可使用的优惠券列表
	     */
	    private List<UserCouponMsg> userCouponMsgList;
	    /**
	     * 可以参与该活动的商品规格id
	     */
	    private List<Integer> goodsSpeIdList;

		public ActivityInformation getActivityInformation() {
			return activityInformation;
		}

		public void setActivityInformation(ActivityInformation activityInformation) {
			this.activityInformation = activityInformation;
		}

		public Double getAfterDiscountMoney() {
			return afterDiscountMoney;
		}

		public void setAfterDiscountMoney(Double afterDiscountMoney) {
			this.afterDiscountMoney = afterDiscountMoney;
		}

		public List<UserCouponMsg> getUserCouponMsgList() {
			return userCouponMsgList;
		}

		public void setUserCouponMsgList(List<UserCouponMsg> userCouponMsgList) {
			this.userCouponMsgList = userCouponMsgList;
		}

		public List<Integer> getGoodsSpeIdList() {
			return goodsSpeIdList;
		}

		public void setGoodsSpeIdList(List<Integer> goodsSpeIdList) {
			this.goodsSpeIdList = goodsSpeIdList;
		}

		
	    
		

}
