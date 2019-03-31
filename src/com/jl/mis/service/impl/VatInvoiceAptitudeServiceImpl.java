package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.VatInvoiceAptitudeMapper;
import com.jl.mis.model.VatInvoiceAptitude;
import com.jl.mis.service.VatInvoiceAptitudeService;
import com.jl.mis.utils.DataTables;
/**
 * 增票资质ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:54:12
 * @Description TODO
 */
@Service
public class VatInvoiceAptitudeServiceImpl implements VatInvoiceAptitudeService{

	@Autowired
	private VatInvoiceAptitudeMapper vatInvoiceAptitudeMapper;
	
	@Override
	public int saveEntity(VatInvoiceAptitude t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(VatInvoiceAptitude t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VatInvoiceAptitude findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VatInvoiceAptitude t) throws Exception {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.insert(t);
	}

	@Override
	public int insertSelective(VatInvoiceAptitude t) throws Exception {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.insertSelective(t);
	}

	@Override
	public VatInvoiceAptitude selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VatInvoiceAptitude t)
			throws Exception {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(VatInvoiceAptitude t) throws Exception {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.updateByPrimaryKey(t);
	}

	@Override
	public DataTables getDataTables(DataTables dataTables, String identifier,
			String name, String phone, int state,String operatorTime) {
		// TODO Auto-generated method stub
		String[] columns = { "identifier", "u.name", "u.phone","operator_time","state","id","state"};
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("phone", phone);
		params.put("state", state);
		params.put("operatorTime", operatorTime);
		
		dataTables.setiTotalDisplayRecords(vatInvoiceAptitudeMapper.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(vatInvoiceAptitudeMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(vatInvoiceAptitudeMapper.selectForSearch(params));// 返回的结果集
		return dataTables;
	}

	@Override
	public boolean updateStateById(VatInvoiceAptitude vatInvoiceAptitude) {
		// TODO Auto-generated method stub
		return  vatInvoiceAptitudeMapper.updateStateById(vatInvoiceAptitude);
	}

	@Override
	public VatInvoiceAptitude getVatInvoiceAptitudeByUserId(int userId) {
		// TODO Auto-generated method stub
		return vatInvoiceAptitudeMapper.selectVatInvoiceAptitudeByUserId(userId);
	}
    
}