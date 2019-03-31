package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.Classification;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:44:34
 * @Description 分类类型表(一二级分类)Mapper
 */
public interface ClassificationMapper extends BaseMapper<Classification>{
	
	/**
	 * 根据分类名称以及父级id查询内容
	 * @param 
	 * @return
	 */
	public Classification selectByNameAndParentId(Map<String,Object> map);
	
	/**
	 * 根据分类名称、Id以及父级id查询内容
	 * @param 
	 * @return
	 */
	public Classification selectByNameAndIdAndParentId(Map<String,Object> map);
	
	/**
	 * 根据一二级分类标识符查询最大的sort值
	 * @param 
	 * @return
	 */
	public Integer selectMaxSort(Map<String,Object> map);
	
	/**
	 * 根据一二级分类标识符查询所属的所有分类，并根据sort排序 
	 * @param 
	 * @return
	 */
	public List<Classification> selectMsgOrderBySort(Map<String,Object> map);
	
	/**
	 * 根据主键批量查询信息
	 * @param 
	 * @return
	 */
	public List<Classification> selectBatchByPrimaryKey(Map<String,Object> map);
	
	/**
	 * 查询一级分类信息 
	 * @param 
	 * @return
	 */
	public List<Classification> selectOneMsg();
	
	/**
	 * 根据一级分类id查询二级分类
	 * @param 
	 * @return
	 */
	public List<Classification> selectTwoByOneId(Integer parentId);
	
	/**
	 * 根据主键进行批量删除
	 * @param 
	 * @return
	 */
	public Integer deleteBatchByPrimaryKey(Map<String,Object> map);
	
	/**
	 * 根据主键批量查询分类是否与广告或活动有关联 
	 * @param map
	 * @return
	 */
	public List<Classification> getClassificationIsContactToADOrACTByIds(Map<String,Object> map);
	
	
	
	

}