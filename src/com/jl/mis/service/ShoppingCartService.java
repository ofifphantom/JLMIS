package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.ShoppingCart;
/**
 * 购物车Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:39:01
 * @Description TODO  
 */
public interface ShoppingCartService  extends BaseService<ShoppingCart>{
	
	/**
	 * 根据商品id以及商品详情id获取有效的购物车信息
	 * @param map
	 * @return
	 */
	public ShoppingCart getShoppingCartByGoodsIdAndSpecId(int goodsDetailsId,int goodsSpecificationDetailsId,int userId);
	
	/**
	 * 根据商品id以及商品详情id以及活动id获取有效的购物车信息
	 * @param map
	 * @return
	 */
	public ShoppingCart getShoppingCartByGoodsIdAndSpecIdAndActivityId(int goodsDetailsId,int goodsSpecificationDetailsId,int userId,int activityId);
	
	/**
	 * 根据用户id获取该用户购物车里的信息
	 * @param map
	 * @return
	 */
	public List<ShoppingCart> getShoppingCarByUserId(int userId);
	
	/**
	 * 批量根据主键删除 信息
	 * @param map
	 * @return
	 */
	public Integer deleteBatchByPrimaryKey(List<Integer> ids);
	
	/**
	 * 若是从购物车到提交订单页面，此时需要把购物车中的信息删除
	 * @param map
	 * @return
	 */
	public Integer deleteByGoodsIdAndGsdIdAndUId(int goodsDetailsId,int goodsSpecificationDetailsId,int userId);
	
	/**
	 * 查询需要发送短信的购物车信息
	 * @return
	 */
	public List<ShoppingCart> selectShopCartGoodSendMessage();
	
	/**
	 * 根据活动id列表更新状态为失效
	 * @param ids
	 * @return
	 */
	public Boolean updateStateByActivityIds(List<Integer> ids);
   
}