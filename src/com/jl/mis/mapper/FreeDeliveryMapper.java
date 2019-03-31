package com.jl.mis.mapper;

import java.util.Map;

import com.jl.mis.model.FreeDelivery;
import com.jl.mis.model.FreeDeliveryAddress;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:45:49
 * @Description 包邮设置Mapper
 */
public interface FreeDeliveryMapper extends BaseMapper<FreeDelivery>{
	
	/**
	 * 查询包邮设置信息以及包邮设置的地址信息
	 * @return
	 */
	public FreeDelivery selectFreeDeliveryAndAddressMsg();
	
	/**
	 * 根据省编码/市编码/区编码查询包邮设置信息以及包邮设置的地址信息
	 * @param map
	 * @return
	 */
	public FreeDelivery selectFreeDeliveryAndAddressMsgByCode(FreeDeliveryAddress freeDeliveryAddress);
    
}