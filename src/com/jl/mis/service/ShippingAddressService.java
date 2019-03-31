package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.ShippingAddress;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:49:00
 * @Description 收货地址Service
 */
public interface ShippingAddressService extends BaseService<ShippingAddress>{
	
	/**
	 * 根据用户id查询信息
	 * @param map
	 * @return
	 */
	public List<ShippingAddress> selectByUserId(int userId);
	
	/**
	 * 根据用户id更改用户的收货人地址都为不默认
	 * @param map
	 * @return
	 */
	public Integer setNoCommonlyByUserId(int userId);

}