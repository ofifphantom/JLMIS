package com.jl.mis.model;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:26:21
 * @Description 热门分类商品model
 */
public class HotCategoriesGood {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 热门分类ID
	 */
    private Integer advertisementInformationId;
    /**
	 * 商品/分类ID
	 */
    private Integer goodsId;
    /**
	 * 状态
	 */
    private Integer state;
    
    //根据业务需要新添加的字段 不属于数据库表
    
    /**
     * 商品详情表
     */
    private GoodsDetails goodsDetails;
    /**
     * 商品分类表
     */
    private Classification classification;
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

  

	public Integer getAdvertisementInformationId() {
		return advertisementInformationId;
	}

	public void setAdvertisementInformationId(Integer advertisementInformationId) {
		this.advertisementInformationId = advertisementInformationId;
	}

	public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public GoodsDetails getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(GoodsDetails goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}
}