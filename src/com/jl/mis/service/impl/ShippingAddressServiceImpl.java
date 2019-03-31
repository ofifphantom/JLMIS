package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ShippingAddressMapper;
import com.jl.mis.model.ShippingAddress;
import com.jl.mis.service.ShippingAddressService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:49:00
 * @Description 收货地址ServiceImpl
 */
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService{

	@Autowired
	private ShippingAddressMapper shippingAddressMapper;
	
	//原start
	@Override
	public int saveEntity(ShippingAddress t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(ShippingAddress t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ShippingAddress findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return shippingAddressMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ShippingAddress t) throws Exception {
		// TODO Auto-generated method stub
		return shippingAddressMapper.insert(t);
	}

	@Override
	public int insertSelective(ShippingAddress t) throws Exception {
		// TODO Auto-generated method stub
		return shippingAddressMapper.insertSelective(t);
	}

	@Override
	public ShippingAddress selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return shippingAddressMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ShippingAddress t) throws Exception {
		// TODO Auto-generated method stub
		return shippingAddressMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(ShippingAddress t) throws Exception {
		// TODO Auto-generated method stub
		return shippingAddressMapper.updateByPrimaryKey(t);
	}

	@Override
	public List<ShippingAddress> selectByUserId(int userId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		return shippingAddressMapper.selectByUserId(map);
	}

	@Override
	public Integer setNoCommonlyByUserId(int userId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		return shippingAddressMapper.setNoCommonlyByUserId(map);
	}

}