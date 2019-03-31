package com.jl.mis.mapper;

import java.util.Map;

import com.jl.mis.model.AfterSaleDetail;
/**
 * 售后详情mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:40:44
 * @Description TODO
 */
public interface AfterSaleDetailMapper extends BaseMapper<AfterSaleDetail> {
	
	/**
	 * 根据用户id 和 主键查询售后详情
	 * @param map
	 * @return
	 */
	public AfterSaleDetail selectByUserIdAndPrimaryKey(Map<String,Object> map);
	
	/**
	 * 根据订单id 和 用户id 查询售后详情
	 * @param map
	 * @return
	 */
	public AfterSaleDetail selectByUserIdAndOrderId(Map<String,Object> map);
	
	/**
	 * 根据订单id查询售后详情
	 * @param orderId
	 * @return
	 */
	public AfterSaleDetail selectByOrderId(int orderId);
	
}