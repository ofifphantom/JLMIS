package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jl.mis.mapper.CouponInformationMapper;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.service.CouponInformationService;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:44:58
 * @Description 优惠券信息ServiceImpl
 */

@Service
public class CouponInformationServiceImpl implements CouponInformationService{

	@Autowired
	private CouponInformationMapper couponInformationMapper;
	
	//原start
	@Override
	public int saveEntity(CouponInformation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(CouponInformation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CouponInformation findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		 
		return couponInformationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(CouponInformation t) throws Exception {
		 
		return couponInformationMapper.insert(t);
	}

	@Override
	public int insertSelective(CouponInformation t) throws Exception {
		 
		return couponInformationMapper.insertSelective(t);
	}

	//APP & PC通用
	@Override
	public CouponInformation selectByPrimaryKey(Integer id) {
		 
		return couponInformationMapper.selectByPrimaryKey(id);
	}

	//APP & PC通用
	@Override
	public int updateByPrimaryKeySelective(CouponInformation t) throws Exception {
		 
		return couponInformationMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(CouponInformation t) throws Exception {
		 
		return couponInformationMapper.updateByPrimaryKey(t);
	}

	@Override
	public DataTables getDataTables(DataTables dataTables,int flag, String identifier, String name, String operatorIdentifier,String operatorTime) {
		// TODO Auto-generated method stub
		String[] columns = {"id", "identifier", "name", "price","total","use_limit", "end_validity_time", "end_time","rules","state","id"};
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		params.put("flag", flag);
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorIdentifier", operatorIdentifier);
		params.put("operatorTime", operatorTime);
		
		dataTables.setiTotalDisplayRecords(couponInformationMapper.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(couponInformationMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(couponInformationMapper.selectForSearch(params));// 返回的结果集
		return dataTables;
	}

	
	@Override
	public boolean deleteCouponInformationByIds(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return couponInformationMapper.deleteCouponInformationByIds(list);
	}

	@Override
	public boolean updateCouponInformationStateByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return couponInformationMapper.updateCouponInformationStateByIds(map);
	}

	@Override
	public List<CouponInformation> getCouponInformationByIds(
			ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return couponInformationMapper.getCouponInformationByIds(list);
	}

	@Override
	public List<CouponInformation> selectAllNormal() {
		// TODO Auto-generated method stub
		return couponInformationMapper.selectAllNormal();
	}

	@Override
	public List<CouponInformation> selectMsgOrderBySearchMsg(
			String identifier, String name, String operatorIdentifier,String operatorTime) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorIdentifier", operatorIdentifier);
		params.put("operatorTime", operatorTime);
		return couponInformationMapper.selectMsgOrderBySearchMsg(params);
	}

	@Override
	public List<CouponInformation> getAllAvailableCoupon() {
		// TODO Auto-generated method stub
		return couponInformationMapper.selectAllAvailableCoupon();
	}

	@Override
	public List<Integer> selectAllAvailableCouponForRegist() {
		// TODO Auto-generated method stub
		return couponInformationMapper.selectAllAvailableCouponForRegist();
	}

	

}