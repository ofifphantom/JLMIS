package com.jl.mis.service;

import java.util.List;

import com.jl.mis.model.InvoiceUnit;
/**
 * 发票抬头单位Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:43:48
 * @Description TODO
 */
public interface InvoiceUnitService  extends BaseService<InvoiceUnit>{
	
	
	
	//--------------------------------APP-----------------------------------
	/**
	 * 根据用户id获取信息
	 * @param userId
	 * @return
	 */
	public List<InvoiceUnit> getInvoiceUnitByUserId(int userId);
  
}