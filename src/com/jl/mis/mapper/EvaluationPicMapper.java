package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.EvaluationPic;
/**
 * 评价图片mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:41:33
 * @Description TODO
 */
public interface EvaluationPicMapper  extends BaseMapper<EvaluationPic>{
	
	/**
	 * 批量存储图片
	 * @param map
	 * @return
	 */
	public Integer insertBatchEvaluationPic(Map<String,Object> map);
	
	/**
	 * 根据商品评价id删除图片
	 * @param map
	 * @return
	 */
	public Integer deleteByGoodsEvaluationId(Map<String,Object> map);
	
	/**
	 * 根据商品评价id查询图片
	 * @param map
	 * @return
	 */
	public List<EvaluationPic> selectByGoodsEvaluationId(Map<String,Object> map);
   
}