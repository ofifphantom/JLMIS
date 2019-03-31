package com.jl.mis.model;

import java.util.Date;
/**
 * 活动消息model
 * @author 景雅倩
 * @date  2017-11-3  下午3:46:09
 * @Description TODO
 */
public class ActivityMessage {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 消息获取时间
     */

    private Date getTime;
    
    /**
     * 状态
     */

    private Integer status;
    
    /**
     * 活动信息表id
     */

    private Integer activityInformatId;
    
    /**
     * 活动信息
     */
    private ActivityInformation activityInformation;
    
    
    public ActivityInformation getActivityInformation() {
		return activityInformation;
	}

	public void setActivityInformation(ActivityInformation activityInformation) {
		this.activityInformation = activityInformation;
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

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getActivityInformatId() {
        return activityInformatId;
    }

    public void setActivityInformatId(Integer activityInformatId) {
        this.activityInformatId = activityInformatId;
    }
}