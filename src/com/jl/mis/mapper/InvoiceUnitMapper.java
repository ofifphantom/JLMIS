package com.jl.mis.mapper;

import java.util.List;

import com.jl.mis.model.InvoiceUnit;
import com.jl.mis.model.VatInvoiceAptitude;
/**
 * 发票抬头单位mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:43:48
 * @Description TODO
 */
public interface InvoiceUnitMapper  extends BaseMapper<InvoiceUnit>{
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//--------------------------------APP-----------------------------------
	/**
	 * 根据用户id获取信息
	 * @param userId
	 * @return
	 */
	public List<InvoiceUnit> selectInvoiceUnitByUserId(int userId);
}