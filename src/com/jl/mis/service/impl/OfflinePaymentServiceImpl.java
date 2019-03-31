package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.OfflinePaymentMapper;
import com.jl.mis.model.OfflinePayment;
import com.jl.mis.model.OrderTable;
import com.jl.mis.service.OfflinePaymentService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:04
 * @Description 线下支付信息ServiceImpl
 */
@Service
public class OfflinePaymentServiceImpl implements OfflinePaymentService{
	@Autowired
	private OfflinePaymentMapper offlinePaymentMapper;

	//原start
	@Override
	public int saveEntity(OfflinePayment t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(OfflinePayment t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OfflinePayment findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return offlinePaymentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OfflinePayment t) throws Exception {
		// TODO Auto-generated method stub
		return offlinePaymentMapper.insert(t);
	}

	@Override
	public int insertSelective(OfflinePayment t) throws Exception {
		// TODO Auto-generated method stub
		return offlinePaymentMapper.insertSelective(t);
	}

	@Override
	public OfflinePayment selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return offlinePaymentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OfflinePayment t) throws Exception {
		// TODO Auto-generated method stub
		return offlinePaymentMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(OfflinePayment t) throws Exception {
		// TODO Auto-generated method stub
		return offlinePaymentMapper.updateByPrimaryKey(t);
	}

	@Override
	public Integer deleteBuOrderId(Integer orderId) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderId", orderId);
		return offlinePaymentMapper.deleteBuOrderId(map);
	}

	@Override
	public OfflinePayment selectOfflineMsgByOrderNoAndUserId(String orderNo, Integer userId) {
		OrderTable orderTable=new OrderTable();
		orderTable.setOrderNo(orderNo);
		orderTable.setUserId(userId);
		return offlinePaymentMapper.selectOfflineMsgByOrderNoAndUserId(orderTable);
	}
   
}