package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderStateDetail;
/**
 * 订单状态详情mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:45:12
 * @Description TODO
 */
public interface OrderStateDetailMapper  extends BaseMapper<OrderStateDetail>{
	
	/**
	 * 根据订单id删除订单状态详情表
	 * @param map
	 * @return
	 */
	public Integer deleteByOrderId(Map<String,Object> map);
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public List<OrderStateDetail> selectByOrderId(Map<String,Object> map);
	
	
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public List<OrderStateDetail> selectOrderMSGByUserId(Map<String,Object> map);
	
	/**
	 * 根据订单id查询信息 
	 * @param map
	 * @return
	 */
	public Integer updateStatusByUserId(Map<String,Object> map);
   
}