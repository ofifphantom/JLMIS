package com.jl.mis.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 日志model
 * @author 景雅倩
 * @date  2017-11-3  下午3:50:32
 * @Description TODO
 */
public class Log {
    private Integer id;
    
    /**
     * 操作类型
     */
    private Integer operateType;
    
    /**
     * 操作对象
     */
    private String operateObject;
    
    /**
     * 操作人编号
     */
    private String operatorIdentifier;
    
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operateTime;
    
  //根据结果需要，在model里另添格外的字段
    /**
     * 用户表
     */
    private User user;
    
    
    
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getOperatorIdentifier() {
        return operatorIdentifier;
    }

    public void setOperatorIdentifier(String operatorIdentifier) {
        this.operatorIdentifier = operatorIdentifier == null ? null : operatorIdentifier.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateObject() {
        return operateObject;
    }

    public void setOperateObject(String operateObject) {
        this.operateObject = operateObject == null ? null : operateObject.trim();
    }
}