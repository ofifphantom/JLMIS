package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.AfterSalePicMapper;
import com.jl.mis.model.AfterSalePic;
import com.jl.mis.service.AfterSalePicService;
/**
 * 售后图片ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:41:13
 * @Description TODO
 */
@Service
public class AfterSalePicServiceImpl implements AfterSalePicService{

	
	@Autowired
	private AfterSalePicMapper afterSalePicMapper;
	
	@Override
	public int saveEntity(AfterSalePic t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(AfterSalePic t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AfterSalePic findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return afterSalePicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AfterSalePic t) throws Exception {
		// TODO Auto-generated method stub
		return afterSalePicMapper.insert(t);
	}

	@Override
	public int insertSelective(AfterSalePic t) throws Exception {
		// TODO Auto-generated method stub
		return afterSalePicMapper.insertSelective(t);
	}

	@Override
	public AfterSalePic selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return afterSalePicMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AfterSalePic t) throws Exception {
		// TODO Auto-generated method stub
		return afterSalePicMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(AfterSalePic t) throws Exception {
		// TODO Auto-generated method stub
		return afterSalePicMapper.updateByPrimaryKey(t);
	}

	@Override
	public Integer insertBatchCustomerServicePic(List<AfterSalePic> afterSalePics) {
		Map<String,Object> map=new HashMap<>();
		map.put("list", afterSalePics);
		return afterSalePicMapper.insertBatchCustomerServicePic(map);
	}

	@Override
	public Integer deleteByCustomerServiceId(Integer afterSaleId) {
		Map<String,Object> map=new HashMap<>();
		map.put("afterSaleDetailId", afterSaleId);
		return afterSalePicMapper.deleteByCustomerServiceId(map);
	}

	@Override
	public List<AfterSalePic> selectByCustomerServiceId(Integer afterSaleId) {
		Map<String,Object> map=new HashMap<>();
		map.put("afterSaleDetailId", afterSaleId);
		return afterSalePicMapper.selectByCustomerServiceId(map);
	}
   
}