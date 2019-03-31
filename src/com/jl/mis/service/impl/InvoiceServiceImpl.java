package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.InvoiceMapper;
import com.jl.mis.model.Invoice;
import com.jl.mis.service.InvoiceService;
/**
 * 发票信息ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:43:17
 * @Description TODO
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{

	@Autowired
	private InvoiceMapper invoiceMapper;
	
	@Override
	public int saveEntity(Invoice t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(Invoice t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Invoice findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return invoiceMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Invoice t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceMapper.insert(t);
	}

	@Override
	public int insertSelective(Invoice t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceMapper.insertSelective(t);
	}

	@Override
	public Invoice selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return invoiceMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Invoice t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(Invoice t) throws Exception {
		// TODO Auto-generated method stub
		return invoiceMapper.updateByPrimaryKey(t);
	}

	@Override
	public Integer deleteByOrderId(Integer orderId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("orderId", orderId);
		return invoiceMapper.deleteByOrderId(map);
	}
   
}