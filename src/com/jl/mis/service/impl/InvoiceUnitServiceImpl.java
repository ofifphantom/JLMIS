package com.jl.mis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.InvoiceUnitMapper;
import com.jl.mis.model.InvoiceUnit;
import com.jl.mis.service.InvoiceUnitService;
/**
 * 发票抬头单位ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:43:48
 * @Description TODO
 */
@Service
public class InvoiceUnitServiceImpl  implements InvoiceUnitService{

	@Autowired
	private InvoiceUnitMapper invoiceUnitMapper;
	
	@Override
	public int saveEntity(InvoiceUnit t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(InvoiceUnit t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public InvoiceUnit findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(InvoiceUnit t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.insert(t);
	}

	@Override
	public int insertSelective(InvoiceUnit t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.insertSelective(t);
	}

	@Override
	public InvoiceUnit selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(InvoiceUnit t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(InvoiceUnit t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.updateByPrimaryKey(t);
	}

	@Override
	public List<InvoiceUnit> getInvoiceUnitByUserId(int userId) {
		// TODO Auto-generated method stub
		return invoiceUnitMapper.selectInvoiceUnitByUserId(userId);
	}
  
}