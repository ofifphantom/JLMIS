package com.jl.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.FreeDeliveryMapper;
import com.jl.mis.model.FreeDelivery;
import com.jl.mis.model.FreeDeliveryAddress;
import com.jl.mis.service.FreeDeliveryService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:45:49
 * @Description 包邮设置ServiceImpl
 */
@Service
public class FreeDeliveryServiceImpl implements FreeDeliveryService{
	
	@Autowired
	private FreeDeliveryMapper freeDeliveryMapper;

	//原start
	@Override
	public int saveEntity(FreeDelivery t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(FreeDelivery t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FreeDelivery findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FreeDelivery t) throws Exception {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.insert(t);
	}

	@Override
	public int insertSelective(FreeDelivery t) throws Exception {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.insertSelective(t);
	}

	@Override
	public FreeDelivery selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FreeDelivery t) throws Exception {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(FreeDelivery t) throws Exception {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.updateByPrimaryKey(t);
	}

	@Override
	public FreeDelivery selectFreeDeliveryAndAddressMsg() {
		// TODO Auto-generated method stub
		return freeDeliveryMapper.selectFreeDeliveryAndAddressMsg();
	}

	@Override
	public FreeDelivery selectFreeDeliveryAndAddressMsgByCode(FreeDeliveryAddress freeDeliveryAddress) {
		
		return freeDeliveryMapper.selectFreeDeliveryAndAddressMsgByCode(freeDeliveryAddress);
	}
    
}