package com.jl.mis.service;


import java.util.Map;

import com.jl.mis.model.Goods;

/**
 * 进销存商品表service
 * @author 景雅倩
 * @date  2017-11-14  下午5:53:59
 * @Description TODO
 */
public interface GoodsService extends BaseService<Goods>{
	
	/**
	 * 提交购物车后修改商品数量
	 * @param map
	 * @return
	 */
	public Integer updateStockByOrderNum(Integer goodsQuantity,Integer gsdId);
	
	/**
	 * 提交购物车后修改商品数量失败 需要把之前修改过的还原
	 * @param map
	 * @return
	 */
	public Integer updateStockByOrderNum_error(Integer goodsQuantity,Integer gsdId);
	
	/**
	 * 修改商品的占用数量
	 * @param map
	 * @return
	 */
	public Integer updateCommodityOccupiedInventory(Integer goodsQuantity, Integer specificationId);
	
	/**
	 * 修改商品的占用数量 失败时要减回去
	 * @param map
	 * @return
	 */
	public Integer updateCommodityOccupiedInventoryError(Integer goodsQuantity, Integer specificationId);
	
	

}