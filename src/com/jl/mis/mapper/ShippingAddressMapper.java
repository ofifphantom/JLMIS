package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.ShippingAddress;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:49:00
 * @Description 收货地址Mapper
 */
public interface ShippingAddressMapper extends BaseMapper<ShippingAddress>{
	
	/**
	 * 根据用户id查询信息
	 * @param map
	 * @return
	 */
	public List<ShippingAddress> selectByUserId(Map<String,Object> map);
	
	/**
	 * 根据用户id更改用户的收货人地址都为不默认
	 * @param map
	 * @return
	 */
	public Integer setNoCommonlyByUserId(Map<String,Object> map);

}