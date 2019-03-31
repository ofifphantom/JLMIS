package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.AfterSaleDetailMapper;
import com.jl.mis.model.AfterSaleDetail;
import com.jl.mis.service.AfterSaleDetailService;
/**
 * 售后详情ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:40:44
 * @Description TODO
 */
@Service
public class AfterSaleDetailServiceImpl implements AfterSaleDetailService  {

	@Autowired
	private AfterSaleDetailMapper afterSaleDetailMapper;
	
	@Override
	public int saveEntity(AfterSaleDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(AfterSaleDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AfterSaleDetail findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AfterSaleDetail t) throws Exception {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.insert(t);
	}

	@Override
	public int insertSelective(AfterSaleDetail t) throws Exception {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.insertSelective(t);
	}

	@Override
	public AfterSaleDetail selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AfterSaleDetail t) throws Exception {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(AfterSaleDetail t) throws Exception {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.updateByPrimaryKey(t);
	}

	@Override
	public AfterSaleDetail selectByUserIdAndPrimaryKey(Integer id, Integer userId) {
		Map<String,Object> map=new HashMap<>();
		map.put("id", id);
		map.put("userId", userId);
		return afterSaleDetailMapper.selectByUserIdAndPrimaryKey(map);
	}

	@Override
	public AfterSaleDetail selectByUserIdAndOrderId(Integer orderId, Integer userId) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderId", orderId);
		map.put("userId", userId);
		return afterSaleDetailMapper.selectByUserIdAndOrderId(map);
	}

	@Override
	public AfterSaleDetail selectByOrderId(int orderId) {
		// TODO Auto-generated method stub
		return afterSaleDetailMapper.selectByOrderId(orderId);
	}
	
}