package com.jl.mis.service;

import java.util.Map;

import com.jl.mis.model.Invoice;
/**
 * 发票信息Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:43:17
 * @Description TODO
 */
public interface InvoiceService extends BaseService<Invoice>{
	
	/**
	 * 根据订单id删除发票详情表
	 * @param map
	 * @return
	 */
	public Integer deleteByOrderId(Integer orderId);
   
}