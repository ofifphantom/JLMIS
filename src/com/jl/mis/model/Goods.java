package com.jl.mis.model;
/**
 * 购销存的商品表model
 * @author 景雅倩
 * @date  2017-11-14  下午5:48:26
 * @Description TODO
 */
public class Goods {
	/**
	 * 主键
	 */
    private Integer id;
    
    /**
	 * 库存
	 */
    private Integer stock;
    
    /**
	 * 进价
	 */
    private Double purchase;
    
    /**
     * 品牌
     */
    private String brand;
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPurchase() {
		return purchase;
	}

	public void setPurchase(Double purchase) {
		this.purchase = purchase;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
    
}
