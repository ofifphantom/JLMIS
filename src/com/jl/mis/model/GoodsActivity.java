package com.jl.mis.model;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:18:26
 * @Description 商品活动model
 */
public class GoodsActivity {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 商品ID（外键）
	 */
    private Integer goodsId;
    /**
	 * 活动信息ID（外键）
	 */
    private Integer activityInformationId;
    /**
	 * 状态（0：商品，1：分类）
	 */
    private Integer state;
    
    //根据结果需要，在model里另添格外的字段
    
    /**
     * 商品规格表
     */
    private GoodsSpecificationDetails goodsSpecificationDetails;
    /**
     * 商品详情表
     */
    private GoodsDetails goodsDetails;
    /**
     * 商品分类表
     */
    private Classification classification;
    /**
     * 活动信息表
     */
    private ActivityInformation activityInformation;
    
    
    public ActivityInformation getActivityInformation() {
		return activityInformation;
	}

	public void setActivityInformation(ActivityInformation activityInformation) {
		this.activityInformation = activityInformation;
	}

	public GoodsSpecificationDetails getGoodsSpecificationDetails() {
		return goodsSpecificationDetails;
	}

	public void setGoodsSpecificationDetails(
			GoodsSpecificationDetails goodsSpecificationDetails) {
		this.goodsSpecificationDetails = goodsSpecificationDetails;
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

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getActivityInformationId() {
        return activityInformationId;
    }

    public void setActivityInformationId(Integer activityInformationId) {
        this.activityInformationId = activityInformationId;
    }

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}