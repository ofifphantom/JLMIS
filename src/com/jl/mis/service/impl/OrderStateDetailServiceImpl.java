package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.OrderStateDetailMapper;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.service.OrderStateDetailService;
/**
 * 订单状态详情ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:45:12
 * @Description TODO
 */
@Service
public class OrderStateDetailServiceImpl  implements OrderStateDetailService{

	@Autowired
	private OrderStateDetailMapper orderStateDetailMapper;
	
	@Override
	public int saveEntity(OrderStateDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(OrderStateDetail t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderStateDetail findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderStateDetailMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderStateDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderStateDetailMapper.insert(t);
	}

	@Override
	public int insertSelective(OrderStateDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderStateDetailMapper.insertSelective(t);
	}

	@Override
	public OrderStateDetail selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderStateDetailMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderStateDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderStateDetailMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(OrderStateDetail t) throws Exception {
		// TODO Auto-generated method stub
		return orderStateDetailMapper.updateByPrimaryKey(t);
	}

	@Override
	public Integer deleteByOrderId(Integer orderId) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderId", orderId);
		return orderStateDetailMapper.deleteByOrderId(map);
	}

	@Override
	public List<OrderStateDetail> selectByOrderId(Integer orderId) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderId", orderId);
		return orderStateDetailMapper.selectByOrderId(map);
	}
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public List<OrderStateDetail> selectOrderMSGByUserId(Integer userId){
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		return orderStateDetailMapper.selectOrderMSGByUserId(map);
	}

	@Override
	public Integer updateStatusByUserId(Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
		return orderStateDetailMapper.updateStatusByUserId(map);
	}
	
   
}