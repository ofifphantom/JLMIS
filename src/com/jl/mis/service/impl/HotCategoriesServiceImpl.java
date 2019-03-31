package com.jl.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.HotCategoriesMapper;
import com.jl.mis.model.HotCategories;
import com.jl.mis.service.HotCategoriesService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:47:52
 * @Description 热门分类ServiceImpl
 */
@Service
public class HotCategoriesServiceImpl implements HotCategoriesService{

	@Autowired
	private HotCategoriesMapper hotCategoriesMapper;
	//原start
	@Override
	public int saveEntity(HotCategories t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(HotCategories t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HotCategories findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hotCategoriesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HotCategories t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesMapper.insert(t);
	}

	@Override
	public int insertSelective(HotCategories t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesMapper.insertSelective(t);
	}

	@Override
	public HotCategories selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hotCategoriesMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(HotCategories t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(HotCategories t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesMapper.updateByPrimaryKey(t);
	}

}