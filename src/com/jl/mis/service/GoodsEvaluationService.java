package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.GoodsEvaluation;
/**
 * 商品评价Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:42:43
 * @Description TODO
 */
public interface GoodsEvaluationService extends BaseService<GoodsEvaluation> {
	
	/**
	 * 根据订单详情id查询评价
	 * @param map
	 * @return
	 */
	public GoodsEvaluation selectByOrderDetailId(Integer orderDetailId);
	
	/**
	 * 查询用户的评价列表
	 * @param map
	 * @return
	 */
	public List<GoodsEvaluation> selectEvaluationMsgByUserId(Integer userId,Integer id,String isAll);
   
}