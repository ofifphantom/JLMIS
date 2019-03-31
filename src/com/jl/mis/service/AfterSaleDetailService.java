package com.jl.mis.service;

import java.util.Map;

import com.jl.mis.model.AfterSaleDetail;
/**
 * 售后详情Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:40:44
 * @Description TODO
 */
public interface AfterSaleDetailService extends BaseService<AfterSaleDetail> {
	
	/**
	 * 根据用户id 和 主键查询售后详情
	 * @param map
	 * @return
	 */
	public AfterSaleDetail selectByUserIdAndPrimaryKey(Integer id,Integer userId);
	
	/**
	 * 根据订单id 和 用户id 查询售后详情
	 * @param map
	 * @return
	 */
	public AfterSaleDetail selectByUserIdAndOrderId(Integer orderId,Integer userId);
	/**
	 * 根据订单id查询售后详情
	 * @param orderId
	 * @return
	 */
	public AfterSaleDetail selectByOrderId(int orderId);
	
}