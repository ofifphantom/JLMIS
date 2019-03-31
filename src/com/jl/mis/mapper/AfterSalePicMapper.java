package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.AfterSalePic;
/**
 * 售后图片mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:41:13
 * @Description TODO
 */
public interface AfterSalePicMapper  extends BaseMapper<AfterSalePic>{
	
	/**
	 * 批量存储图片
	 * @param map
	 * @return
	 */
	public Integer insertBatchCustomerServicePic(Map<String,Object> map);
	
	/**
	 * 根据售后id删除图片
	 * @param map
	 * @return
	 */
	public Integer deleteByCustomerServiceId(Map<String,Object> map);
	
	/**
	 * 根据售后服务id查询图片
	 * @param map
	 * @return
	 */
	public List<AfterSalePic> selectByCustomerServiceId(Map<String,Object> map);
   
}