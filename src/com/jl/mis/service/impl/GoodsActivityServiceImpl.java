package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.GoodsActivityMapper;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.service.GoodsActivityService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:46:14
 * @Description 商品活动ServiceImpl
 */
@Service
public class GoodsActivityServiceImpl implements GoodsActivityService{
	
	@Autowired
	private GoodsActivityMapper goodsActivityMapper;

	//原start
	@Override
	public int saveEntity(GoodsActivity t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(GoodsActivity t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GoodsActivity findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsActivityMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(GoodsActivity t) throws Exception {
		// TODO Auto-generated method stub
		return goodsActivityMapper.insert(t);
	}

	@Override
	public int insertSelective(GoodsActivity t) throws Exception {
		// TODO Auto-generated method stub
		return goodsActivityMapper.insertSelective(t);
	}

	@Override
	public GoodsActivity selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsActivityMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(GoodsActivity t) throws Exception {
		// TODO Auto-generated method stub
		return goodsActivityMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsActivity t) throws Exception {
		// TODO Auto-generated method stub
		return goodsActivityMapper.updateByPrimaryKey(t);
	}

	@Override
	public boolean deleteByActivityInformationId(int activityInformationId) {
		// TODO Auto-generated method stub
		return goodsActivityMapper.deleteByActivityInformationId(activityInformationId);
	}

	@Override
	public boolean deleteByActivityInformationIds(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return goodsActivityMapper.deleteByActivityInformationIds(list);
	}

	//APP & PC通用
	@Override
	public List<GoodsActivity> getMsgByActivityInformationId(
			int activityInformationId) {
		// TODO Auto-generated method stub
		return goodsActivityMapper.getMsgByActivityInformationId(activityInformationId);
	}

	//APP & PC通用
	@Override
	public GoodsActivity getMsgByActivityInformationId0(
			int activityInformationId,int goodsId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("activityInformationId", activityInformationId);
		map.put("goodsId", goodsId);
		return goodsActivityMapper.getMsgByActivityInformationId0(map);
	}

	//APP & PC通用
	@Override
	public GoodsActivity getMsgByActivityInformationId1(
			int activityInformationId,int goodsId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("activityInformationId", activityInformationId);
		map.put("goodsId", goodsId);
		return goodsActivityMapper.getMsgByActivityInformationId1(map);
	}

	//APP & PC通用
	@Override
	public GoodsActivity getMsgByActivityInformationId2(
			int activityInformationId,int goodsId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("activityInformationId", activityInformationId);
		map.put("goodsId", goodsId);
		return goodsActivityMapper.getMsgByActivityInformationId2(map);
	}

	@Override
	public List<GoodsActivity> getGoodsActivityMsgByStateAndGoodsId(int goodsId, String isGoodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("isGoodsId", isGoodsId);
		return goodsActivityMapper.getGoodsActivityMsgByStateAndGoodsId(map);
	}

	@Override
	public List<GoodsActivity> selectGoodsActivityBySpecificationId(String goodsSpecificationDetailsId,String isGoodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsSpecificationDetailsId", goodsSpecificationDetailsId);
		map.put("isGoodsId", isGoodsId);
		return goodsActivityMapper.selectGoodsActivityBySpecificationId(map);
	}

}