package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.GoodsEvaluationMapper;
import com.jl.mis.model.GoodsEvaluation;
import com.jl.mis.service.GoodsEvaluationService;
/**
 * 商品评价ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:42:43
 * @Description TODO
 */
@Service
public class GoodsEvaluationServiceImpl implements  GoodsEvaluationService {

	
	@Autowired
	private GoodsEvaluationMapper goodsEvaluationMapper;
	
	
	@Override
	public int saveEntity(GoodsEvaluation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(GoodsEvaluation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GoodsEvaluation findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsEvaluationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(GoodsEvaluation t) throws Exception {
		// TODO Auto-generated method stub
		return goodsEvaluationMapper.insert(t);
	}

	@Override
	public int insertSelective(GoodsEvaluation t) throws Exception {
		// TODO Auto-generated method stub
		return goodsEvaluationMapper.insertSelective(t);
	}

	@Override
	public GoodsEvaluation selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsEvaluationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(GoodsEvaluation t) throws Exception {
		// TODO Auto-generated method stub
		return goodsEvaluationMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsEvaluation t) throws Exception {
		// TODO Auto-generated method stub
		return goodsEvaluationMapper.updateByPrimaryKey(t);
	}

	@Override
	public GoodsEvaluation selectByOrderDetailId(Integer orderDetailId) {
		Map<String,Object> map=new HashMap<> ();
		map.put("orderDetailId", orderDetailId);
		return goodsEvaluationMapper.selectByOrderDetailId(map);
	}

	@Override
	public List<GoodsEvaluation> selectEvaluationMsgByUserId(Integer userId,Integer id,String isAll) {
		Map<String,Object> map=new HashMap<> ();
		map.put("userId", userId);
		map.put("id", id);
		map.put("isAll", isAll);
		return goodsEvaluationMapper.selectEvaluationMsgByUserId(map);
	}
   
}