package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.Classification;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:44:34
 * @Description 分类类型表(一二级分类)Service
 */
public interface ClassificationService extends BaseService<Classification>{

	/**
	 * 获取数据库中获取分类信息
	 * @return 返回一个分类列表对象
	 */
	public DataTables getManagerMsg(DataTables dataTables,String oneOrTwo,String identifier,String name,String operatorName,String operatorTime);
	
	/**
	 * 根据分类名称以及父级id查询内容
	 * @param name
	 * @return
	 */
	public Classification selectByNameAndParentId(String name,String parentId);
	
	/**
	 * 根据分类名称、Id以及父级id查询内容
	 * @param name
	 * @return
	 */
	public Classification selectByNameAndIdAndParentId(String name,Integer id,String parentId);
	
	/**
	 * 根据主键批量查询信息
	 * @param name
	 * @return
	 */
	public List<Classification> selectBatchByPrimaryKey(String[] primaryKey);
	
	/**
	 * 根据一二级分类标识符查询最大的sort值
	 * @param name
	 * @return
	 */
	public Integer selectMaxSort(String oneOrTwo);
	
	/**
	 * 根据一二级分类标识符查询所属的所有分类，并根据sort排序 
	 * @param 
	 * @return
	 */
	public List<Classification> selectMsgOrderBySort(String oneOrTwo);
	
	/**
	 * 根据一二级分类标识符查询所属的所有分类，并根据sort排序 ,有参数
	 * @param 
	 * @return
	 */
	public List<Classification> selectMsgOrderBySearchMsg(String oneOrTwo,String identifier,String name,String operatorName,String operatorTime);
	
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
	 * 根据主键进行批量/单个删除
	 * @param name
	 * @return
	 */
	public Integer deleteBatchByPrimaryKey(String[] primaryKey);
	
	/**
	 * 根据主键批量查询分类是否与广告或活动有关联 
	 * @param map
	 * @return
	 */
	public List<Classification> getClassificationIsContactToADOrACTByIds(String[] primaryKey);
}