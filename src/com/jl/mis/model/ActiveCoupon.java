package com.jl.mis.model;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午3:52:59
 * @Description 活动优惠券model
 */
public class ActiveCoupon {
	/**
	 * 主键
	 */
    private Integer id;
    
    /**
	 * 优惠券ID（外键）
	 */
    private Integer couponId;
    
    /**
	 * 活动信息ID（外键）
	 */
    private Integer activityInformationId;
    
    /**
     * 优惠券信息
     */
    private CouponInformation couponInformation;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getActivityInformationId() {
        return activityInformationId;
    }

    public void setActivityInformationId(Integer activityInformationId) {
        this.activityInformationId = activityInformationId;
    }

	public CouponInformation getCouponInformation() {
		return couponInformation;
	}

	public void setCouponInformation(CouponInformation couponInformation) {
		this.couponInformation = couponInformation;
	}
    
}