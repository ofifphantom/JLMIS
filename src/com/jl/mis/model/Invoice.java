package com.jl.mis.model;
/**
 * 发票信息model
 * @author 景雅倩
 * @date  2017-11-3  下午3:49:09
 * @Description TODO
 */
public class Invoice {
    private Integer id;
    
    /**
     * 订单ID(外键)
     */
    private Integer orderId;
    
    /**
     * 发票类型（0：普通发票，1：增值发票）
     */
    private Integer invoiceType;
    
    /**
     * 发票内容（0：商品，1：明细）
     */
    private Integer invoiceContent;
    
    /**
     * 发票抬头类型（0：单位，1：个人）
     */
    private Integer invoiceLookedUpType;
    
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

    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(Integer invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public Integer getInvoiceLookedUpType() {
        return invoiceLookedUpType;
    }

    public void setInvoiceLookedUpType(Integer invoiceLookedUpType) {
        this.invoiceLookedUpType = invoiceLookedUpType;
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
}