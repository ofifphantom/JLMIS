package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ClassificationMapper;
import com.jl.mis.mapper.GoodsDetailsMapper;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.service.GoodsDetailsService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:46:24
 * @Description 商品详情ServiceImpl
 */
@Service
public class GoodsDetailsServiceImpl implements GoodsDetailsService{
	
	@Autowired
	private GoodsDetailsMapper goodsDetailsMapper;
	@Autowired
	private ClassificationMapper classificationMapper;

	//原start
	@Override
	public int saveEntity(GoodsDetails t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(GoodsDetails t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GoodsDetails findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(GoodsDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.insert(t);
	}

	@Override
	public int insertSelective(GoodsDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.insertSelective(t);
	}

	@Override
	public GoodsDetails selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(GoodsDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.updateByPrimaryKey(t);
	}

	@Override
	public List<GoodsDetails> getGoodsListByClassificationId(Integer classificationId) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectGoodsByClassificationId(classificationId);
	}

	@Override
	public GoodsDetails selectGoodsAndClassificationByName(String name) {
		/*GoodsDetails goodsDetails=new GoodsDetails();
		goodsDetails=goodsDetailsMapper.selectGoodsAndClassificationByName(name);
		//根据分类的父级id查询父级分类的名称
		goodsDetails.getClassification().setParentName(classificationMapper.selectByPrimaryKey(goodsDetails.getClassification().getParentId()).getName());*/
		return goodsDetailsMapper.selectGoodsAndClassificationByName(name);
	}

	@Override
	public Integer updateBatchByPrimaryKey(List<Integer> primaryKey, String recomTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", primaryKey);
		map.put("recomTime", recomTime);
		return goodsDetailsMapper.updateBatchByPrimaryKey(map);
	}

	@Override
	public List<GoodsDetails> selectAllGoodsDetails(int classificationId) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectAllGoodsDetails(classificationId);
	}

	@Override
	public List<GoodsDetails> getHomeHotGoods(int classificationId,String searchName,int sortType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("classificationId", classificationId);
		map.put("searchName", searchName);
		map.put("sortType", sortType);
		return goodsDetailsMapper.getHomeHotGoods(map);
	}
	@Override
	public List<GoodsDetails> getGoodsList_Ameliorate(int classificationId,String searchName,int sortType) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("classificationId", classificationId);
		map.put("searchName", searchName);
		map.put("sortType", sortType);
		return goodsDetailsMapper.getGoodsList_Ameliorate(map);
	}
	@Override
	public GoodsDetails getGoodsDetailAndEvaluationByGoodsId(int id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", id);
		return goodsDetailsMapper.getGoodsDetailAndEvaluationByGoodsId(map);
	}

	@Override
	public List<GoodsDetails> selectHotSearchWord() {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectHotSearchWord();
	}

	@Override
	public GoodsDetails selectIdByCommodityId(Integer commodityId) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectIdByCommodityId(commodityId);
	}

	@Override
	public List<GoodsDetails> selectPresellGoodsByClassificationId(Integer classificationId) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectPresellGoodsByClassificationId(classificationId);
	}

	@Override
	public List<GoodsDetails> selectNotPresellGoodsByClassificationId(Integer classificationId) {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectNotPresellGoodsByClassificationId(classificationId);
	}

	@Override
	public List<String> selectBrand() {
		// TODO Auto-generated method stub
		return goodsDetailsMapper.selectBrand();
	}


}