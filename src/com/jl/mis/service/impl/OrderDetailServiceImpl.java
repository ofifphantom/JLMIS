package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.OrderDetailMapper;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.SalesPlanOrderCommodity;
import com.jl.mis.service.OrderDetailService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:26
 * @Description 订单详情ServiceImpl
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService{

	@Autowired
	private OrderDetailMapper orderDetailMapper;
	//原start
	@Override
	public int saveEntity(OrderDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(OrderDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderDetail findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderDetailMapper.insert(t);
	}

	@Override
	public int insertSelective(OrderDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderDetailMapper.insertSelective(t);
	}

	@Override
	public OrderDetail selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderDetailMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(OrderDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderDetailMapper.updateByPrimaryKey(t);
	}

	@Override
	public List<OrderDetail> selectByOrderId(int orderId) {
		// TODO Auto-generated method stub
		return orderDetailMapper.selectByOrderId(orderId);
	}

	@Override
	public Integer insertBatch(List<OrderDetail> orderDetails) {
		Map<String,Object> map=new HashMap<>();
		map.put("list", orderDetails);
		return orderDetailMapper.insertBatch(map);
	}

	@Override
	public Integer deleteByOrderId(Integer orderId) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderId", orderId);
		return orderDetailMapper.deleteByOrderId(map);
	}
	
	@Override
	public List<SalesPlanOrderCommodity> selectOrderCommodity(Integer orderId) {
		// TODO Auto-generated method stub
		return orderDetailMapper.selectOrderCommodity(orderId);
	}

}