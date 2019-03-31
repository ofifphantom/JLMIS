package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.EvaluationPic;
/**
 * 评价图片Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:41:33
 * @Description TODO
 */
public interface EvaluationPicService  extends BaseService<EvaluationPic>{
	
	/**
	 * 批量存储图片
	 * @param map
	 * @return
	 */
	public Integer insertBatchEvaluationPic(List<EvaluationPic> evaluationPics);
	
	/**
	 * 根据商品评价id删除图片
	 * @param goodsEvaluationId
	 * @return
	 */
	public Integer deleteByGoodsEvaluationId(Integer goodsEvaluationId);
	
	/**
	 * 根据商品评价id查询图片
	 * @param map
	 * @return
	 */
	public List<EvaluationPic> selectByGoodsEvaluationId(Integer goodsEvaluationId);
   
}