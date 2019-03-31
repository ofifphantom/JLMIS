package com.jl.mis.model;

import java.util.Date;
/**
 * 用户优惠券model
 * @author 景雅倩
 * @date  2017-11-3  下午3:53:07
 * @Description TODO
 */
public class UserCoupons {
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 优惠券id
     */
    private Integer couponInformationId;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 使用时间
     */
    private Date useTime;
    
    /**
     * 获取时间
     */
    private Date getTime;

    //根据结果需要，在model里另添格外的字段
    /**
     * 优惠券信息
     */
    private CouponInformation couponInformation;
    
    /**
     * 状态信息
     */
    private String stateMsg ;
    
    /**
     * 使用说明
     */
    private String useInstruction;
    
    
    
    public String getUseInstruction() {
		return useInstruction;
	}

	public void setUseInstruction(String useInstruction) {
		this.useInstruction = useInstruction;
	}

	public String getStateMsg() {
		return stateMsg;
	}

	public void setStateMsg(String stateMsg) {
		this.stateMsg = stateMsg;
	}

	public CouponInformation getCouponInformation() {
		return couponInformation;
	}

	public void setCouponInformation(CouponInformation couponInformation) {
		this.couponInformation = couponInformation;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCouponInformationId() {
        return couponInformationId;
    }

    public void setCouponInformationId(Integer couponInformationId) {
        this.couponInformationId = couponInformationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
}