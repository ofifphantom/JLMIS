package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ShoppingCartMapper;
import com.jl.mis.model.ShoppingCart;
import com.jl.mis.service.ShoppingCartService;
/**
 * 购物车ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:39:01
 * @Description TODO  
 */
@Service
public class ShoppingCartServiceImpl  implements ShoppingCartService{

	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	@Override
	public int saveEntity(ShoppingCart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(ShoppingCart t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ShoppingCart findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return shoppingCartMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ShoppingCart t) throws Exception {
		// TODO Auto-generated method stub
		return shoppingCartMapper.insert(t);
	}

	@Override
	public int insertSelective(ShoppingCart t) throws Exception {
		// TODO Auto-generated method stub
		return shoppingCartMapper.insertSelective(t);
	}

	@Override
	public ShoppingCart selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return shoppingCartMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ShoppingCart t) throws Exception {
		// TODO Auto-generated method stub
		return shoppingCartMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(ShoppingCart t) throws Exception {
		// TODO Auto-generated method stub
		return shoppingCartMapper.updateByPrimaryKey(t);
	}

	@Override
	public ShoppingCart getShoppingCartByGoodsIdAndSpecId(int goodsDetailsId, int goodsSpecificationDetailsId,int userId) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("goodsDetailsId", goodsDetailsId);
		map.put("goodsSpecificationDetailsId", goodsSpecificationDetailsId);
		map.put("userId", userId);
		return shoppingCartMapper.getShoppingCartByGoodsIdAndSpecId(map);
	}
	
	@Override
	public ShoppingCart getShoppingCartByGoodsIdAndSpecIdAndActivityId(int goodsDetailsId,
			int goodsSpecificationDetailsId, int userId, int activityId) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("goodsDetailsId", goodsDetailsId);
		map.put("goodsSpecificationDetailsId", goodsSpecificationDetailsId);
		map.put("userId", userId);
		map.put("activityId", activityId);
		return shoppingCartMapper.getShoppingCartByGoodsIdAndSpecIdAndActivityId(map);
	}

	@Override
	public List<ShoppingCart> getShoppingCarByUserId(int userId) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("userId", userId);
		return shoppingCartMapper.getShoppingCarByUserId(map);
	}

	@Override
	public Integer deleteBatchByPrimaryKey(List<Integer> ids) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("list", ids);
		return shoppingCartMapper.deleteBatchByPrimaryKey(map);
	}

	@Override
	public Integer deleteByGoodsIdAndGsdIdAndUId(int goodsDetailsId, int goodsSpecificationDetailsId, int userId) {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("goodsDetailsId", goodsDetailsId);
		map.put("goodsSpecificationDetailsId", goodsSpecificationDetailsId);
		map.put("userId", userId);
		return shoppingCartMapper.deleteByGoodsIdAndGsdIdAndUId(map);
	}

	@Override
	public List<ShoppingCart> selectShopCartGoodSendMessage() {
	
		return shoppingCartMapper.selectShopCartGoodSendMessage();
	}

	@Override
	public Boolean updateStateByActivityIds(List<Integer> ids) {
		// TODO Auto-generated method stub
		return shoppingCartMapper.updateStateByActivityIds(ids);
	}

	
   
}