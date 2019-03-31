package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.EvaluationPicMapper;
import com.jl.mis.model.EvaluationPic;
import com.jl.mis.service.EvaluationPicService;
/**
 * 评价图片ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:41:33
 * @Description TODO
 */
@Service
public class EvaluationPicServiceImpl implements EvaluationPicService{

	@Autowired
	private EvaluationPicMapper evaluationPicMapper;
	
	@Override
	public int saveEntity(EvaluationPic t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(EvaluationPic t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public EvaluationPic findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return evaluationPicMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(EvaluationPic t) throws Exception {
		// TODO Auto-generated method stub
		return evaluationPicMapper.insert(t);
	}

	@Override
	public int insertSelective(EvaluationPic t) throws Exception {
		// TODO Auto-generated method stub
		return evaluationPicMapper.insertSelective(t);
	}

	@Override
	public EvaluationPic selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return evaluationPicMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(EvaluationPic t) throws Exception {
		// TODO Auto-generated method stub
		return evaluationPicMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(EvaluationPic t) throws Exception {
		// TODO Auto-generated method stub
		return evaluationPicMapper.updateByPrimaryKey(t);
	}

	@Override
	public Integer insertBatchEvaluationPic(List<EvaluationPic> evaluationPics) {
		Map<String,Object> map=new HashMap<>();
		map.put("list", evaluationPics);
		return evaluationPicMapper.insertBatchEvaluationPic(map);
	}

	@Override
	public Integer deleteByGoodsEvaluationId(Integer goodsEvaluationId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goodsEvaluationId",goodsEvaluationId);
		return evaluationPicMapper.deleteByGoodsEvaluationId(map);
	}

	@Override
	public List<EvaluationPic> selectByGoodsEvaluationId(Integer goodsEvaluationId) {
		Map<String,Object> map=new HashMap<>();
		map.put("goodsEvaluationId",goodsEvaluationId);
		return evaluationPicMapper.selectByGoodsEvaluationId(map);
	}
   
}