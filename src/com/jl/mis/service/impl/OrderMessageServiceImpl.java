package com.jl.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.OrderMessageMapper;
import com.jl.mis.model.OrderMessage;
import com.jl.mis.service.OrderMessageService;
/**
 * 订单消息ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:44:52
 * @Description TODO
 */
@Service
public class OrderMessageServiceImpl implements OrderMessageService {

	@Autowired
	private OrderMessageMapper orderMessageMapper;
	
	@Override
	public int saveEntity(OrderMessage t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(OrderMessage t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderMessage findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderMessageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderMessage t) throws Exception {
		// TODO Auto-generated method stub
		return orderMessageMapper.insert(t);
	}

	@Override
	public int insertSelective(OrderMessage t) throws Exception {
		// TODO Auto-generated method stub
		return orderMessageMapper.insertSelective(t);
	}

	@Override
	public OrderMessage selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderMessageMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderMessage t) throws Exception {
		// TODO Auto-generated method stub
		return orderMessageMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(OrderMessage t) throws Exception {
		// TODO Auto-generated method stub
		return orderMessageMapper.updateByPrimaryKey(t);
	}
   
}