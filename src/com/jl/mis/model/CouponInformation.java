package com.jl.mis.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:13:27
 * @Description 优惠券信息model
 */
public class CouponInformation {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 编号
	 */
    private String identifier;
    /**
	 * 名称
	 */
    private String name;
    /**
	 * 金额
	 */
    private Double price;
    /**
	 * 数量
	 */
    private Integer total;
    /**
	 * 使用门栏
	 */
    private Double useLimit;
    /**
	 * 剩余数量
	 */
    private Integer count;
    /**
	 * 状态
	 */
    private Integer state;
    /**
	 * 领取规则
	 */
    private Integer rules;
    /**
	 * 有效期开始
	 */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date beginValidityTime;
    /**
	 * 有效期结束
	 */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date endValidityTime;
    /**
	 * 开始领取时间
	 */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date beginTime;
    /**
	 * 结束领取时间
	 */
    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private Date endTime;
    /**
	 * 创建人
	 */
    private String operatorIdentifier;
    /**
	 * 创建时间
	 */
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date operatorTime;
    
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier == null ? null : identifier.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

   
	public Double getUseLimit() {
		return useLimit;
	}

	public void setUseLimit(Double useLimit) {
		this.useLimit = useLimit;
	}

	public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRules() {
        return rules;
    }

    public void setRules(Integer rules) {
        this.rules = rules;
    }

    public Date getBeginValidityTime() {
        return beginValidityTime;
    }

    public void setBeginValidityTime(Date beginValidityTime) {
        this.beginValidityTime = beginValidityTime;
    }

    public Date getEndValidityTime() {
        return endValidityTime;
    }

    public void setEndValidityTime(Date endValidityTime) {
        this.endValidityTime = endValidityTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOperatorIdentifier() {
        return operatorIdentifier;
    }

    public void setOperatorIdentifier(String operatorIdentifier) {
        this.operatorIdentifier = operatorIdentifier == null ? null : operatorIdentifier.trim();
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}