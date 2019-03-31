package com.jl.mis.service;

import com.jl.mis.model.VatInvoiceAptitude;
import com.jl.mis.utils.DataTables;
/**
 * 增票资质Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:54:12
 * @Description TODO
 */
public interface VatInvoiceAptitudeService extends BaseService<VatInvoiceAptitude>{
	
	public DataTables getDataTables(DataTables dataTables, String identifier, String name, String phone, int state,String operatorTime);
	
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
	public VatInvoiceAptitude getVatInvoiceAptitudeByUserId(int userId);
}