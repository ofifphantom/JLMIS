package com.jl.mis.model;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:34:03
 * @Description 订单详情model
 */
public class OrderDetail {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 商品详情ID(外键)
	 */
    private Integer goodsDetailsId;
    /**
	 * 商品数量
	 */
    private Integer goodsQuantity;
    /**
	 * 商品规格ID(外键)
	 */
    private Integer goodsSpecificationDetailsId;
    /**
	 * 商品进价(单个)
	 */
    private Double goodsPurchasingPrice;
    /**
	 * 商品原价(单个)
	 */
    private Double goodsOriginalPrice;
    /**
	 * 商品付款金额(不包含邮费)
	 */
    private Double goodsPaymentPrice;
    /**
	 * 商品名称（快照）
	 */
    private String goodsName;
    /**
	 * 商品封面图URL(快照)
	 */
    private String goodsCoverUrl;
    /**
	 * 商品规格名(快照)
	 */
    private String goodsSpecificationName;
    /**
	 * 订单ID(外键)
	 */
    private Integer orderId;
    
    
    
    
    //根据结果需要，在model里另添格外的字段

    /**
	 * 商品类
	 */
    private GoodsDetails goodsDetails;
    
    /**
     * 订单评价
     */
    private GoodsEvaluation goodsEvaluation;
    
    /**
     * 订单表
     */
    private OrderTable orderTable;
    
    
    
    
    
    public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}

	public GoodsEvaluation getGoodsEvaluation() {
		return goodsEvaluation;
	}

	public void setGoodsEvaluation(GoodsEvaluation goodsEvaluation) {
		this.goodsEvaluation = goodsEvaluation;
	}

	public GoodsDetails getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(GoodsDetails goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsDetailsId() {
        return goodsDetailsId;
    }

    public void setGoodsDetailsId(Integer goodsDetailsId) {
        this.goodsDetailsId = goodsDetailsId;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public Integer getGoodsSpecificationDetailsId() {
        return goodsSpecificationDetailsId;
    }

    public void setGoodsSpecificationDetailsId(Integer goodsSpecificationDetailsId) {
        this.goodsSpecificationDetailsId = goodsSpecificationDetailsId;
    }

    public Double getGoodsPurchasingPrice() {
		return goodsPurchasingPrice;
	}

	public void setGoodsPurchasingPrice(Double goodsPurchasingPrice) {
		this.goodsPurchasingPrice = goodsPurchasingPrice;
	}

	public Double getGoodsOriginalPrice() {
        return goodsOriginalPrice;
    }

    public void setGoodsOriginalPrice(Double goodsOriginalPrice) {
        this.goodsOriginalPrice = goodsOriginalPrice;
    }

    public Double getGoodsPaymentPrice() {
        return goodsPaymentPrice;
    }

    public void setGoodsPaymentPrice(Double goodsPaymentPrice) {
        this.goodsPaymentPrice = goodsPaymentPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getGoodsCoverUrl() {
        return goodsCoverUrl;
    }

    public void setGoodsCoverUrl(String goodsCoverUrl) {
        this.goodsCoverUrl = goodsCoverUrl == null ? null : goodsCoverUrl.trim();
    }

    public String getGoodsSpecificationName() {
        return goodsSpecificationName;
    }

    public void setGoodsSpecificationName(String goodsSpecificationName) {
        this.goodsSpecificationName = goodsSpecificationName == null ? null : goodsSpecificationName.trim();
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}