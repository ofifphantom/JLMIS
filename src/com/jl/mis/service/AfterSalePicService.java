package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.AfterSalePic;
import com.jl.mis.model.EvaluationPic;
/**
 * 售后图片Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:41:13
 * @Description TODO
 */
public interface AfterSalePicService  extends BaseService<AfterSalePic>{
	
	/**
	 * 批量存储图片
	 * @param map
	 * @return
	 */
	public Integer insertBatchCustomerServicePic(List<AfterSalePic> afterSalePics);
	
	/**
	 * 根据售后id删除图片
	 * @param map
	 * @return
	 */
	public Integer deleteByCustomerServiceId(Integer afterSaleId);
	
	/**
	 * 根据售后服务id查询图片
	 * @param map
	 * @return
	 */
	public List<AfterSalePic> selectByCustomerServiceId(Integer afterSaleId);
   
}