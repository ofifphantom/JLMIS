package com.jl.mis.model;
/**
 * 评价图片model
 * @author 景雅倩
 * @date  2017-11-3  下午3:48:27
 * @Description TODO
 */
public class EvaluationPic {
    private Integer id;
    
    /**
     * 评价图片
     */
    private String picUrl;
    
    /**
     * 商品评价表ID(外键)
     */
    private Integer goodsEvaluationId;

    
    
    
    
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

    public Integer getGoodsEvaluationId() {
        return goodsEvaluationId;
    }

    public void setGoodsEvaluationId(Integer goodsEvaluationId) {
        this.goodsEvaluationId = goodsEvaluationId;
    }
}