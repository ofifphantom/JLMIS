package com.jl.mis.model;

import java.util.Date;
import java.util.List;
/**
 * 增票资质model
 * @author 景雅倩
 * @date  2017-11-3  下午3:53:26
 * @Description TODO
 */
public class VatInvoiceAptitude {
    private Integer id;
    
    /**
     * 编号
     */
    private String identifier;
    
    /**
     * 单位名称
     */
    private String unitName;
    
    /**
     * 纳税人识别号
     */
    private String taxpayerIdentificationNumber;
    
    /**
     * 注册地址
     */
    private String registeredAddress;
    
    /**
     * 注册电话
     */
    private String registeredTel;
    
    /**
     * 开户银行
     */
    private String depositBank;
    
    /**
     * 银行账号
     */
    private String bankAccount;
    
    /**
     * 营业执照URL
     */
    private String businessLicenseUrl;
    
    /**
     * 状态
     */
    private Integer state;
    
    /**
     * 用户ID(外键)
     */
    private Integer userId;
    
    /**
     * 操作时间
     */
    private Date operatorTime;

 //根据结果需要，在model里另添格外的字段
    
    /**
     * 用户信息
     */
    private User user;
    
    
    
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

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress == null ? null : registeredAddress.trim();
    }

    public String getRegisteredTel() {
        return registeredTel;
    }

    public void setRegisteredTel(String registeredTel) {
        this.registeredTel = registeredTel == null ? null : registeredTel.trim();
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank == null ? null : depositBank.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBusinessLicenseUrl() {
        return businessLicenseUrl;
    }

    public void setBusinessLicenseUrl(String businessLicenseUrl) {
        this.businessLicenseUrl = businessLicenseUrl == null ? null : businessLicenseUrl.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}