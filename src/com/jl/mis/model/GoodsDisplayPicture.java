package com.jl.mis.model;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:22:45
 * @Description 商品展示图片model
 */
public class GoodsDisplayPicture {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 图片URL
	 */
    private String picUrl;
    /**
	 * 商品规格ID(外键)
	 */
    private Integer goodsSpecificationDetailId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

	public Integer getGoodsSpecificationDetailId() {
		return goodsSpecificationDetailId;
	}

	public void setGoodsSpecificationDetailId(Integer goodsSpecificationDetailId) {
		this.goodsSpecificationDetailId = goodsSpecificationDetailId;
	}

    

	


    
}