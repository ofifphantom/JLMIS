package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.GoodsDetailsMapper;
import com.jl.mis.mapper.GoodsMapper;
import com.jl.mis.model.Goods;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsService;

/**
 * 进销存商品表impl
 * @author 景雅倩
 * @date  2017-11-14  下午5:55:39
 * @Description TODO
 */
@Service
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public int saveEntity(Goods t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(Goods t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Goods findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Goods t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(Goods t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Goods selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Goods t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Goods t) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Integer updateStockByOrderNum(Integer goodsQuantity, Integer gsdId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goodsQuantity", goodsQuantity);
		map.put("gsdId", gsdId);
		return goodsMapper.updateStockByOrderNum(map);
	}

	@Override
	public Integer updateStockByOrderNum_error(Integer goodsQuantity, Integer gsdId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goodsQuantity", goodsQuantity);
		map.put("gsdId", gsdId);
		return goodsMapper.updateStockByOrderNum_error(map);
	}

	@Override
	public Integer updateCommodityOccupiedInventory(Integer goodsQuantity, Integer specificationId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goodsQuantity", goodsQuantity);
		map.put("specificationId", specificationId);
		return goodsMapper.updateCommodityOccupiedInventory(map);
	}

	@Override
	public Integer updateCommodityOccupiedInventoryError(Integer goodsQuantity, Integer specificationId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goodsQuantity", goodsQuantity);
		map.put("specificationId", specificationId);
		return goodsMapper.updateCommodityOccupiedInventoryError(map);
	}

	

	

}