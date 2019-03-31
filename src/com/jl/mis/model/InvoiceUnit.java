package com.jl.mis.model;

import java.util.Date;
/**
 * 发票抬头单位model
 * @author 景雅倩
 * @date  2017-11-3  下午3:49:51
 * @Description TODO
 */
public class InvoiceUnit {
    private Integer id;
    
    /**
     * 单位名称
     */
    private String unitName;
    
    /**
     * 纳税人识别号
     */
    private String taxpayerIdentificationNumber;
    
    /**
     * 用户ID（外键）
     */
    private Integer userId;
    
    /**
     * 操作时间
     */
    private Date operatorTime;
    
   
    
    
    
    
    public Integer getId() {
        return id;
    }
    
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getTaxpayerIdentificationNumber() {
        return taxpayerIdentificationNumber;
    }

    public void setTaxpayerIdentificationNumber(String taxpayerIdentificationNumber) {
        this.taxpayerIdentificationNumber = taxpayerIdentificationNumber == null ? null : taxpayerIdentificationNumber.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}