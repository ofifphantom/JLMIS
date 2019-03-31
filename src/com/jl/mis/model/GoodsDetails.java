package com.jl.mis.model;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:19:19
 * @Description 商品详情model
 */
public class GoodsDetails {
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 分类ID（外键）
	 */
	private Integer classificationId;
	/**
	 * 商品id（购销存）
	 */
	private Integer commodityId;
	/**
	 * 关键词
	 */
	private String keyWord;
	/**
	 * 简介
	 */
	private String introdution;
	/**
	 * 是否推荐
	 */
	private Integer recom;
	/**
	 * 推荐时间
	 */
	private Date recomTime;
	/**
	 * 详情
	 */
	private String details;
	
	 //根据结果需要，在model里另添格外的字段
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 分类信息
	 */
	private Classification classification;
	
	/**
	 * 商品详情
	 */
	private List<GoodsSpecificationDetails> goodsSpecificationDetails;
	
	/**
	 * 总销量
	 */
	private Integer saleCount;
	
	/**
	 * 商品评价
	 */
	private List<GoodsEvaluation> goodsEvaluations;
	
	/**
	 * 商品参与的活动
	 */
	private List<GoodsActivity> goodsActivitys;
	/**
	 * 是否允许0库存出库
	 */
	private Integer zeroStock;
	
	
	
	
	public List<GoodsActivity> getGoodsActivitys() {
		return goodsActivitys;
	}

	public void setGoodsActivitys(List<GoodsActivity> goodsActivitys) {
		this.goodsActivitys = goodsActivitys;
	}

	public List<GoodsEvaluation> getGoodsEvaluations() {
		return goodsEvaluations;
	}

	public void setGoodsEvaluations(List<GoodsEvaluation> goodsEvaluations) {
		this.goodsEvaluations = goodsEvaluations;
	}

	public Integer getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
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

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord == null ? null : keyWord.trim();
	}

	public String getIntrodution() {
		return introdution;
	}

	public void setIntrodution(String introdution) {
		this.introdution = introdution == null ? null : introdution.trim();
	}

	public Integer getRecom() {
		return recom;
	}

	public void setRecom(Integer recom) {
		this.recom = recom;
	}

	public Date getRecomTime() {
		return recomTime;
	}

	public void setRecomTime(Date recomTime) {
		this.recomTime = recomTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details == null ? null : details.trim();
	}

	public List<GoodsSpecificationDetails> getGoodsSpecificationDetails() {
		return goodsSpecificationDetails;
	}

	public void setGoodsSpecificationDetails(List<GoodsSpecificationDetails> goodsSpecificationDetails) {
		this.goodsSpecificationDetails = goodsSpecificationDetails;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getZeroStock() {
		return zeroStock;
	}

	public void setZeroStock(Integer zeroStock) {
		this.zeroStock = zeroStock;
	}
	
}