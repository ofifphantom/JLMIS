package com.jl.mis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.FreeDeliveryAddressMapper;
import com.jl.mis.model.FreeDeliveryAddress;
import com.jl.mis.service.FreeDeliveryAddressService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:45:24
 * @Description 包邮地址ServiceImpl
 */
@Service
public class FreeDeliveryAddressServiceImpl implements FreeDeliveryAddressService{

	@Autowired
	private FreeDeliveryAddressMapper freeDeliveryAddressMapper;
	
	//原start
	@Override
	public int saveEntity(FreeDeliveryAddress t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(FreeDeliveryAddress t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FreeDeliveryAddress findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		 
		return freeDeliveryAddressMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(FreeDeliveryAddress t) throws Exception {
		 
		return freeDeliveryAddressMapper.insert(t);
	}

	@Override
	public int insertSelective(FreeDeliveryAddress t) throws Exception {
		 
		return freeDeliveryAddressMapper.insertSelective(t);
	}

	@Override
	public FreeDeliveryAddress selectByPrimaryKey(Integer id) {
		 
		return freeDeliveryAddressMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FreeDeliveryAddress t) throws Exception {
		 
		return freeDeliveryAddressMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(FreeDeliveryAddress t) throws Exception {
		 
		return freeDeliveryAddressMapper.updateByPrimaryKey(t);
	}

	@Override
	public int insertBatch(List<FreeDeliveryAddress> freeDeliveryAddressList) {
		
		return freeDeliveryAddressMapper.insertBatch(freeDeliveryAddressList);
	}

	@Override
	public int deleteBatchByPrimaryKey(List<Integer> primaryKeys) {
		// TODO Auto-generated method stub
		return freeDeliveryAddressMapper.deleteBatchByPrimaryKey(primaryKeys);
	}
 
}