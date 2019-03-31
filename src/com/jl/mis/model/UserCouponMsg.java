package com.jl.mis.model;


public class UserCouponMsg implements Cloneable{
	
	    /**
	     * 用户优惠券信息
	     */
	    private UserCoupons userCoupons;
	    
	    /**
	     * 将要购买的商品使用优惠券满减后的价格
	     */
	    private Double afterDiscountMoney;

		public UserCoupons getUserCoupons() {
			return userCoupons;
		}

		public void setUserCoupons(UserCoupons userCoupons) {
			this.userCoupons = userCoupons;
		}

		public Double getAfterDiscountMoney() {
			return afterDiscountMoney;
		}

		public void setAfterDiscountMoney(Double afterDiscountMoney) {
			this.afterDiscountMoney = afterDiscountMoney;
		}

		 @Override  
		 public UserCouponMsg clone() throws CloneNotSupportedException {  
		     return (UserCouponMsg)super.clone();  
		 }
		
	    

}
