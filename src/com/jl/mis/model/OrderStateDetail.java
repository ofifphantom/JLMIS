package com.jl.mis.model;

import java.util.Date;
/**
 * 订单状态详情model
 * @author 景雅倩
 * @date  2017-11-3  下午3:51:25
 * @Description TODO
 */
public class OrderStateDetail {
    private Integer id;
    
    /**
     * 订单状态详情
     */
    private String orderStateDetail;
    
    /**
     * 添加时间
     */
    private Date addTime;
    
    /**
     * 订单ID（外键）
     */
    private Integer orderId;
    /**
     * 状态  0：未读   1：已读
     */
    private Integer status=0;
    
    /**
     * 状态  0：未删除   1：已删除
     */
    private Integer isdelete=0;
    
    
    
    private OrderTable orderTable;
    
    
    
    
    public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
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

    public String getOrderStateDetail() {
        return orderStateDetail;
    }

    public void setOrderStateDetail(String orderStateDetail) {
        this.orderStateDetail = orderStateDetail == null ? null : orderStateDetail.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}