package com.jl.mis.model;

import java.util.Date;
import java.util.List;

public class OrderTable {
   /**
	 * 主键
	 */
    private Integer id;
    /**
	 * 订单号
	 */
    private String orderNo;
    /**
	 * 订单原价(没参加活动的价格)
	 */
    private Double orderOriginalPrice;
    /**
	 * 订单付款价(除邮费的付款价)
	 */
    private Double orderPresentPrice;
    /**
	 * 用户ID(外键)
	 */
    private Integer userId;
    /**
	 * 下单时间
	 */
    private Date placeOrderTime;
    /**
	 * 订单状态
	 */
    private Integer orderState;
    /**
	 * 收货人姓名
	 */
    private String consigneeName;
    /**
	 * 收货人电话
	 */
    private String consigneeTel;
    /**
	 * 收货人地址
	 */
    private String consigneeAddress;
    /**
	 * 订单支付类型(0：快捷支付，1：线下支付)
	 */
    private Integer payType;
    /**
	 * 订单支付时间
	 */
    private Date payTime;
    /**
	 * 邮费
	 */
    private Double postage;
    /**
	 * 邮费支付方式(0：线上，1：到付)
	 */
    private Integer postagePayType;
    /**
	 * 送货时间
	 */
    private Date deliverGoodsTime;
    /**
	 * 支付方式
	 */
    private Integer payMode;
    /**
     * 优惠类型（暂时不加，若界面上要显示，直接显示商品优惠**钱，钱数是用原价-付款价）
     */
    private String preferentialType;
    /**
     * 开始的发货车牌号
     */
    private String carIdSend;
    /**
     * 退换货的入库物流
     */
    private String carIdChangeReturn;
    /**
     * 换货的出库物流
     */
    private String carIdChangeSend;
    
  //根据结果需要，在model里另添格外的字段
    
    /**
     * 保存该订单下的所有商品名称
     */
    private String orderGoodsName;
    
    /**
     * 保存该订单商品对应的个数
     */
    private String orderGoodsNum;
    
    /**
     * 保存该订单商品对应的付款金额
     */
    private String orderGoodsPrice;
    
    /**
     * 订单详情表
     */
    private List<OrderDetail> orderDetails;
    
    /**
     * 用户信息
     */
    private User user;
    
    /**
     * 是否开发票（0：不开，1：开）
     */
    private Integer isHasInvoice;
    
    /**
     * 发票表
     */
    private Invoice invoice;
    
    /**
     * 订单状态详情表
     */
    private List<OrderStateDetail> orderStateDetails;
    
    /**
     * 线下订单详情表
     */
    private OfflinePayment offlinePayment;
    
    /**
     * 售后信息
     */
    private AfterSaleDetail afterSaleDetail;
    
    /**
     * 是否来自购物车（0：不是，1：是），若来自购物车，需要根据商品id和商品详情id删除购物车信息
     */
    private Integer isFromShoppingCart;
    
    /**
     * 是否使用优惠券（0：不使用，1：使用）
     */
    private Integer isUseCoupon;
    
    /**
     * 用户所拥有的优惠券信息
     */
    private UserCoupons userCoupons;
    
    /**
     * 是否评价（0：无，1：已评价）
     */
    private Integer isHasEvaluation;
    /**
     * 活动id
     */
    private Integer activityId;
    /**
     * 退货状态
     */
    private Integer returnState;
    
    public Integer getReturnState() {
		return returnState;
	}

	public void setReturnState(Integer returnState) {
		this.returnState = returnState;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Double getOrderOriginalPrice() {
        return orderOriginalPrice;
    }

    public void setOrderOriginalPrice(Double orderOriginalPrice) {
        this.orderOriginalPrice = orderOriginalPrice;
    }

    public Double getOrderPresentPrice() {
        return orderPresentPrice;
    }

    public void setOrderPresentPrice(Double orderPresentPrice) {
        this.orderPresentPrice = orderPresentPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getPlaceOrderTime() {
        return placeOrderTime;
    }

    public void setPlaceOrderTime(Date placeOrderTime) {
        this.placeOrderTime = placeOrderTime;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel == null ? null : consigneeTel.trim();
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Double getPostage() {
        return postage;
    }

    public void setPostage(Double postage) {
        this.postage = postage;
    }

    public Integer getPostagePayType() {
        return postagePayType;
    }

    public void setPostagePayType(Integer postagePayType) {
        this.postagePayType = postagePayType;
    }

    public Date getDeliverGoodsTime() {
        return deliverGoodsTime;
    }

    public void setDeliverGoodsTime(Date deliverGoodsTime) {
        this.deliverGoodsTime = deliverGoodsTime;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getPreferentialType() {
        return preferentialType;
    }

    public void setPreferentialType(String preferentialType) {
        this.preferentialType = preferentialType == null ? null : preferentialType.trim();
    }

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrderGoodsName() {
		return orderGoodsName;
	}

	public void setOrderGoodsName(String orderGoodsName) {
		this.orderGoodsName = orderGoodsName;
	}

	public String getOrderGoodsNum() {
		return orderGoodsNum;
	}

	public void setOrderGoodsNum(String orderGoodsNum) {
		this.orderGoodsNum = orderGoodsNum;
	}

	public String getOrderGoodsPrice() {
		return orderGoodsPrice;
	}

	public void setOrderGoodsPrice(String orderGoodsPrice) {
		this.orderGoodsPrice = orderGoodsPrice;
	}

	public List<OrderStateDetail> getOrderStateDetails() {
		return orderStateDetails;
	}

	public void setOrderStateDetails(List<OrderStateDetail> orderStateDetails) {
		this.orderStateDetails = orderStateDetails;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public OfflinePayment getOfflinePayment() {
		return offlinePayment;
	}

	public void setOfflinePayment(OfflinePayment offlinePayment) {
		this.offlinePayment = offlinePayment;
	}

	public String getCarIdSend() {
		return carIdSend;
	}

	public void setCarIdSend(String carIdSend) {
		this.carIdSend = carIdSend;
	}

	public String getCarIdChangeReturn() {
		return carIdChangeReturn;
	}

	public void setCarIdChangeReturn(String carIdChangeReturn) {
		this.carIdChangeReturn = carIdChangeReturn;
	}

	public String getCarIdChangeSend() {
		return carIdChangeSend;
	}

	public void setCarIdChangeSend(String carIdChangeSend) {
		this.carIdChangeSend = carIdChangeSend;
	}

	public Integer getIsHasInvoice() {
		return isHasInvoice;
	}

	public void setIsHasInvoice(Integer isHasInvoice) {
		this.isHasInvoice = isHasInvoice;
	}

	public Integer getIsFromShoppingCart() {
		return isFromShoppingCart;
	}

	public void setIsFromShoppingCart(Integer isFromShoppingCart) {
		this.isFromShoppingCart = isFromShoppingCart;
	}

	public Integer getIsUseCoupon() {
		return isUseCoupon;
	}

	public void setIsUseCoupon(Integer isUseCoupon) {
		this.isUseCoupon = isUseCoupon;
	}

	public UserCoupons getUserCoupons() {
		return userCoupons;
	}

	public void setUserCoupons(UserCoupons userCoupons) {
		this.userCoupons = userCoupons;
	}

	public Integer getIsHasEvaluation() {
		return isHasEvaluation;
	}

	public void setIsHasEvaluation(Integer isHasEvaluation) {
		this.isHasEvaluation = isHasEvaluation;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public AfterSaleDetail getAfterSaleDetail() {
		return afterSaleDetail;
	}

	public void setAfterSaleDetail(AfterSaleDetail afterSaleDetail) {
		this.afterSaleDetail = afterSaleDetail;
	}

	
	
    
}