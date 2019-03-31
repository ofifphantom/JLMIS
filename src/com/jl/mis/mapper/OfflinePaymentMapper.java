package com.jl.mis.mapper;

import java.util.Map;

import com.jl.mis.model.OfflinePayment;
import com.jl.mis.model.OrderTable;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:04
 * @Description 线下支付信息Mapper
 */
public interface OfflinePaymentMapper extends BaseMapper<OfflinePayment>{
	
	/**
	 * 根据订单id删除线下支付信息
	 * @param map
	 * @return
	 */
	public Integer deleteBuOrderId(Map<String,Object> map);
	
	/**
	 * 根据订单编号以及用户id获取线下支付信息表中状态为0(待支付)的信息
	 * @param orderTable
	 * @return
	 */
	public OfflinePayment selectOfflineMsgByOrderNoAndUserId(OrderTable orderTable);
   
}