package com.jl.mis.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 * 向进销存传递  销售计划单
 * @author guole
 *
 */
public class SalesPlanOrder {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 编号
	 */
    private String identifier;
    /**
	 * 生成日期
	 */
    private Date createTime;
 

    /**
	 * 状态（1：未生成销售订单    2：已生成销售订单   3：销售计划失败）
	 */
    private Integer state;
    /**
	 * 是否是APP订单（1：否  2：是）
	 */
    private Integer isAppOrder;
    /**
	 * APP收货人姓名
	 */
    private String appConsigneeName;
    /**
	 * APP收货人电话
	 */
    private String appConsigneePhone;
    /**
	 * APP收货地址
	 */
    private String appConsigneeAddress;
    
	/**
  	 * miss后台传来的订单id
  	 */
    private Integer missOrderId;
 
   	/**
   	 * 销售商品信息
   	 */
   	List<SalesPlanOrderCommodity> salesPlanOrderCommodities;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsAppOrder() {
		return isAppOrder;
	}

	public void setIsAppOrder(Integer isAppOrder) {
		this.isAppOrder = isAppOrder;
	}

	public String getAppConsigneeName() {
		return appConsigneeName;
	}

	public void setAppConsigneeName(String appConsigneeName) {
		this.appConsigneeName = appConsigneeName;
	}

	public String getAppConsigneePhone() {
		return appConsigneePhone;
	}

	public void setAppConsigneePhone(String appConsigneePhone) {
		this.appConsigneePhone = appConsigneePhone;
	}

	public String getAppConsigneeAddress() {
		return appConsigneeAddress;
	}

	public void setAppConsigneeAddress(String appConsigneeAddress) {
		this.appConsigneeAddress = appConsigneeAddress;
	}

	public Integer getMissOrderId() {
		return missOrderId;
	}

	public void setMissOrderId(Integer missOrderId) {
		this.missOrderId = missOrderId;
	}

	public List<SalesPlanOrderCommodity> getSalesPlanOrderCommodities() {
		return salesPlanOrderCommodities;
	}

	public void setSalesPlanOrderCommodities(List<SalesPlanOrderCommodity> salesPlanOrderCommodities) {
		this.salesPlanOrderCommodities = salesPlanOrderCommodities;
	}
   
 

    

}