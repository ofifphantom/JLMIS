package com.jl.mis.mapper;

import java.util.Map;

import com.jl.mis.model.VatInvoiceAptitude;
/**
 * 增票资质mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:54:12
 * @Description TODO
 */
public interface VatInvoiceAptitudeMapper extends BaseMapper<VatInvoiceAptitude>{
	/**
	 * 根据id更新状态
	 * @param vatInvoiceAptitude
	 * @return
	 */
	public boolean updateStateById(VatInvoiceAptitude vatInvoiceAptitude);
	
	
	
	
	
//--------------------------------APP-----------------------------------
	
	
	/**
	 * 根据用户id获取信息
	 * @param userId
	 * @return
	 */
	public VatInvoiceAptitude selectVatInvoiceAptitudeByUserId(int userId);
}