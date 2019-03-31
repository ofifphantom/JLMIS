package com.jl.mis.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:16:12
 * @Description 包邮设置model
 */
public class FreeDelivery {
	/**
	 * 主键
	 */
    private Integer id;
/**
	 * 包邮金额
	 */
    private Double freeDeliveryMoney;
/**
	 * 基础油费
	 */
    private Double basePostage;
    
  //根据结果需要，在model里另添格外的字段
    
    /**
     * 获取包邮设置里对应的范围
     */
    List<FreeDeliveryAddress> freeDeliveryAddresses;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getFreeDeliveryMoney() {
        return freeDeliveryMoney;
    }

    public void setFreeDeliveryMoney(Double freeDeliveryMoney) {
        this.freeDeliveryMoney = freeDeliveryMoney;
    }

    public Double getBasePostage() {
        return basePostage;
    }

    public void setBasePostage(Double basePostage) {
        this.basePostage = basePostage;
    }

	public List<FreeDeliveryAddress> getFreeDeliveryAddresses() {
		return freeDeliveryAddresses;
	}

	public void setFreeDeliveryAddresses(List<FreeDeliveryAddress> freeDeliveryAddresses) {
		this.freeDeliveryAddresses = freeDeliveryAddresses;
	}
    
}