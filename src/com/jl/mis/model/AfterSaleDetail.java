package com.jl.mis.model;

import java.util.Date;
import java.util.List;
/**
 * 售后详情model
 * @author 景雅倩
 * @date  2017-11-3  下午3:46:40
 * @Description TODO
 */
public class AfterSaleDetail {
    private Integer id;
    
    /**
     * 服务类型
     */
    private Integer serviceType;
    
    /**
     * 问题描述
     */

    private String problemDescription;
    
    /**
     * 姓名
     */

    private String name;
    
    /**
     * 联系方式
     */

    private String phone;
    
    /**
     * 状态
     */

    private Integer status;
    
    /**
     * 订单id
     */
    private Integer orderId;
    
    /**
     * 添加时间
     */
    private Date addTime;
    
  //根据结果需要，在model里另添格外的字段
    
    /**
     * 用户信息
     */
    private User user;
    
    /**
     * 售后图片表
     */
    private List<AfterSalePic> afterSalePics;

    /**
     * 订单表
     */
    private OrderTable orderTable;
    
    
    
    
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<AfterSalePic> getAfterSalePics() {
		return afterSalePics;
	}

	public void setAfterSalePics(List<AfterSalePic> afterSalePics) {
		this.afterSalePics = afterSalePics;
	}

	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription == null ? null : problemDescription.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}