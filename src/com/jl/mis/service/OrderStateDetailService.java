package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderStateDetail;
/**
 * 订单状态详情Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:45:12
 * @Description TODO
 */
public interface OrderStateDetailService  extends BaseService<OrderStateDetail>{
	

	/**
	 * 根据订单id删除订单状态详情表
	 * @param orderId
	 * @return
	 */
	public Integer deleteByOrderId(Integer orderId);
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public List<OrderStateDetail> selectByOrderId(Integer orderId);
	
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public List<OrderStateDetail> selectOrderMSGByUserId(Integer userId);
	
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public Integer updateStatusByUserId(Integer userId);
	
   
}