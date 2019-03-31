package com.jl.mis.mapper;

import java.util.List;

import com.jl.mis.model.FreeDeliveryAddress;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:45:24
 * @Description 包邮地址Mapper
 */
public interface FreeDeliveryAddressMapper extends BaseMapper<FreeDeliveryAddress>{
	
	/**
	 * 批量保存信息
	 * @param freeDeliveryAddressList
	 * @return
	 */
	public int insertBatch(List<FreeDeliveryAddress> freeDeliveryAddressList);
	
	/**
	 * 根据主键批量删除 信息
	 * @param freeDeliveryAddressList
	 * @return
	 */
	public int deleteBatchByPrimaryKey(List<Integer> primaryKeys);
 
}