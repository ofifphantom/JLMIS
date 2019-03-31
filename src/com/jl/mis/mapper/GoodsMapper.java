package com.jl.mis.mapper;

import java.util.Map;

import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.Goods;
/**
 * 进销存商品表mapper
 * @author 景雅倩
 * @date  2017-11-14  下午5:53:10
 * @Description TODO
 */
public interface GoodsMapper extends BaseMapper<Goods>{
	
	/**
	 * 提交购物车后修改商品数量
	 * @param map
	 * @return
	 */
	public Integer updateStockByOrderNum(Map<String,Object> map);

	/**
	 * 提交购物车后修改商品数量失败 需要把之前修改过的还原
	 * @param map
	 * @return
	 */
	public Integer updateStockByOrderNum_error(Map<String,Object> map);
	
	/**
	 * 修改商品的占用数量
	 * @param map
	 * @return
	 */
	public Integer updateCommodityOccupiedInventory(Map<String,Object> map);
	
	/**
	 * 修改商品的占用数量 失败时要减回去
	 * @param map
	 * @return
	 */
	public Integer updateCommodityOccupiedInventoryError(Map<String,Object> map);

}