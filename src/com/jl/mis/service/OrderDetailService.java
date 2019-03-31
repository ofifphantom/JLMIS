package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.SalesPlanOrderCommodity;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:26
 * @Description 订单详情Service
 */
public interface OrderDetailService extends BaseService<OrderDetail>{

	public  List<SalesPlanOrderCommodity> selectOrderCommodity(Integer orderId);
	
	//--------------------------------APP-----------------------------------
	/**
	 * 根据订单号获取信息
	* @param orderId
	* @return
	*/
	public List<OrderDetail> selectByOrderId(int orderId);	
	
	/**
	 * 批量保存信息 
	 * @param map
	 * @return
	 */
	public Integer insertBatch(List<OrderDetail> orderDetails);
	
	/**
	 * 根据订单id删除订单详情表
	 * @param map
	 * @return
	 */
	public Integer deleteByOrderId(Integer orderId);
}