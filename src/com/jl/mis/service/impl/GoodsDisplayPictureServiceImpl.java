package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.GoodsDisplayPictureMapper;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.service.GoodsDisplayPictureService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:46:42
 * @Description 商品展示图片ServiceImpl
 */
@Service
public class GoodsDisplayPictureServiceImpl implements GoodsDisplayPictureService{

	@Autowired
	private GoodsDisplayPictureMapper goodsDisplayPictureMapper;
	//原start
	@Override
	public int saveEntity(GoodsDisplayPicture t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(GoodsDisplayPicture t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GoodsDisplayPicture findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsDisplayPictureMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(GoodsDisplayPicture t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDisplayPictureMapper.insert(t);
	}

	@Override
	public int insertSelective(GoodsDisplayPicture t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDisplayPictureMapper.insertSelective(t);
	}

	@Override
	public GoodsDisplayPicture selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsDisplayPictureMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(GoodsDisplayPicture t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDisplayPictureMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsDisplayPicture t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDisplayPictureMapper.updateByPrimaryKey(t);
	}

	@Override
	public Integer deleteBatchByGoodsSpecificationDetailId(String[] goodsSpecificationDetailId) {
		List<Integer> goodsSpecificationDetailIds=new ArrayList<Integer>();
		for(String id:goodsSpecificationDetailId){
			goodsSpecificationDetailIds.add(Integer.parseInt(id));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", goodsSpecificationDetailIds);
		return goodsDisplayPictureMapper.deleteBatchByGoodsSpecificationDetailId(map);
	}

	@Override
	public List<GoodsDisplayPicture> selectBatchByGoodsSpecificationDetailId(
			String[] goodsSpecificationDetailId) {
		List<Integer> goodsSpecificationDetailIds=new ArrayList<Integer>();
		for(String id:goodsSpecificationDetailId){
			goodsSpecificationDetailIds.add(Integer.parseInt(id));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", goodsSpecificationDetailIds);
		return goodsDisplayPictureMapper.selectBatchByGoodsSpecificationDetailId(map);
	}

	@Override
	public List<GoodsDisplayPicture> getGoodsDisplayPicture(Integer goodsSpecificationDetailId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("goodsSpecificationDetailId", goodsSpecificationDetailId);
		return goodsDisplayPictureMapper.getGoodsDisplayPicture(map);
	}
  
}