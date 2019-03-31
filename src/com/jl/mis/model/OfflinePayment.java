package com.jl.mis.model;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:27:30
 * @Description 线下支付信息model
 */
public class OfflinePayment {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 订单ID(外键)
	 */
    private Integer orderId;
    /**
	 * 支付人姓名
	 */
    private String payerName;
    /**
	 * 支付人手机号
	 */
    private String payerTel;
    /**
	 * 汇款人姓名
	 */
    private String remitterName;
    /**
	 * 汇款人账号
	 */
    private String remitterAccount;
    /**
	 * 收款人姓名
	 */
    private String payeeName;
    /**
	 * 收款人账号
	 */
    private String payeeAccount;
    /**
	 * 收款人开户行
	 */
    private String payeeAccountDepositBank;
    /**
	 * 汇款金额
	 */
    private Double remittanceAmount;
    /**
	 * 支付凭证URL1
	 */
    private String paymentVoucherUrlOne;
    /**
	 * 支付凭证URL2
	 */
    private String paymentVoucherUrlTwo;
    /**
	 * 状态
	 */
    private Integer state;

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

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public String getPayerTel() {
        return payerTel;
    }

    public void setPayerTel(String payerTel) {
        this.payerTel = payerTel == null ? null : payerTel.trim();
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName == null ? null : remitterName.trim();
    }

    public String getRemitterAccount() {
        return remitterAccount;
    }

    public void setRemitterAccount(String remitterAccount) {
        this.remitterAccount = remitterAccount == null ? null : remitterAccount.trim();
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName == null ? null : payeeName.trim();
    }

    public String getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(String payeeAccount) {
        this.payeeAccount = payeeAccount == null ? null : payeeAccount.trim();
    }

    public String getPayeeAccountDepositBank() {
        return payeeAccountDepositBank;
    }

    public void setPayeeAccountDepositBank(String payeeAccountDepositBank) {
        this.payeeAccountDepositBank = payeeAccountDepositBank == null ? null : payeeAccountDepositBank.trim();
    }

    public Double getRemittanceAmount() {
        return remittanceAmount;
    }

    public void setRemittanceAmount(Double remittanceAmount) {
        this.remittanceAmount = remittanceAmount;
    }

    public String getPaymentVoucherUrlOne() {
        return paymentVoucherUrlOne;
    }

    public void setPaymentVoucherUrlOne(String paymentVoucherUrlOne) {
        this.paymentVoucherUrlOne = paymentVoucherUrlOne == null ? null : paymentVoucherUrlOne.trim();
    }

    public String getPaymentVoucherUrlTwo() {
        return paymentVoucherUrlTwo;
    }

    public void setPaymentVoucherUrlTwo(String paymentVoucherUrlTwo) {
        this.paymentVoucherUrlTwo = paymentVoucherUrlTwo == null ? null : paymentVoucherUrlTwo.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}