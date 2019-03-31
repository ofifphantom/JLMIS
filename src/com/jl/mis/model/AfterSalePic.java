package com.jl.mis.model;
/**
 * 售后图片model
 * @author 景雅倩
 * @date  2017-11-3  下午3:47:09
 * @Description TODO
 */
public class AfterSalePic {
    private Integer id;
    
    /**
     * 图片url
     */
    private String picUrl;
    
    /**
     * 售后id
     */
    private Integer afterSaleDetailId;
    
    
    
    
    
    
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

    public Integer getAfterSaleDetailId() {
        return afterSaleDetailId;
    }

    public void setAfterSaleDetailId(Integer afterSaleDetailId) {
        this.afterSaleDetailId = afterSaleDetailId;
    }
}