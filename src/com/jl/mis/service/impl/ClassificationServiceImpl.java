package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ClassificationMapper;
import com.jl.mis.model.Classification;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:44:34
 * @Description 分类类型表(一二级分类)ServiceImpl
 */
@Service
public class ClassificationServiceImpl implements ClassificationService{

	@Autowired
	private ClassificationMapper classificationMapper;
	
	//原start
	@Override
	public int saveEntity(Classification t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(Classification t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Classification findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end
	
	@Override
	public DataTables getManagerMsg(DataTables dataTables,String oneOrTwo,String identifier,String name,String operatorName,String operatorTime) {
		String[] columns = { "sort", "name", "key_word", "pic_url","operator_identifier","operator_time","id" };
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		//保存是一级分类还是二级分类
		params.put("oneOrTwo",oneOrTwo);
		//保存搜索词
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorName", operatorName);
		params.put("operatorTime", operatorTime);
		
		dataTables.setiTotalDisplayRecords(classificationMapper
				.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(classificationMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(classificationMapper.selectForSearch(params));// 返回的结果集
		return dataTables;
		
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		 
		return classificationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Classification t) throws Exception {
		 
		return classificationMapper.insert(t);
	}

	@Override
	public int insertSelective(Classification t) throws Exception {
		
		return classificationMapper.insertSelective(t);
	}

	//APP & PC通用
	@Override
	public Classification selectByPrimaryKey(Integer id) {
		 
		return classificationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Classification t) throws Exception {
		 
		return classificationMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(Classification t) throws Exception {
		 
		return classificationMapper.updateByPrimaryKey(t);
	}
	
	@Override
	public Classification selectByNameAndParentId(String name,String parentId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		if(parentId!=null&&!"".equals(parentId)){
			map.put("parentId", Integer.parseInt(parentId));
		}
		else{
			map.put("parentId", 0);
		}
		
		 
		return classificationMapper.selectByNameAndParentId(map);
	}
	
	@Override
	public Classification selectByNameAndIdAndParentId(String name,Integer id, String parentId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("name", name);
		map.put("id", id);
		if(parentId!=null&&!"".equals(parentId)){
			map.put("parentId", Integer.parseInt(parentId));
		}
		else{
			map.put("parentId", 0);
		}
		 
		return classificationMapper.selectByNameAndIdAndParentId(map);
	}

	@Override
	public Integer deleteBatchByPrimaryKey(String[] primaryKey) {
		List<Integer> primaryKeys=new ArrayList<Integer>();
		for(String id:primaryKey){
			primaryKeys.add(Integer.parseInt(id));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", primaryKeys);
		return classificationMapper.deleteBatchByPrimaryKey(map);
	}

	@Override
	public List<Classification> selectBatchByPrimaryKey(String[] primaryKey) {
		List<Integer> primaryKeys=new ArrayList<Integer>();
		for(String id:primaryKey){
			primaryKeys.add(Integer.parseInt(id));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", primaryKeys);
		return classificationMapper.selectBatchByPrimaryKey(map);
	}

	@Override
	public Integer selectMaxSort(String oneOrTwo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("oneOrTwo", oneOrTwo);
		return classificationMapper.selectMaxSort(map);
	}
	
	@Override
	public List<Classification> selectMsgOrderBySort(String oneOrTwo) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("oneOrTwo", oneOrTwo);
		return classificationMapper.selectMsgOrderBySort(map);
	}

	@Override
	public List<Classification> selectMsgOrderBySearchMsg(String oneOrTwo,String identifier,String name,String operatorName,String operatorTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("oneOrTwo", oneOrTwo);
		map.put("identifier", identifier);
		map.put("name", name);
		map.put("operatorName", operatorName);
		map.put("operatorTime", operatorTime);
		return classificationMapper.selectMsgOrderBySort(map);
	}

	@Override
	public List<Classification> selectTwoByOneId(Integer parentId) {
		// TODO Auto-generated method stub
		return classificationMapper.selectTwoByOneId(parentId);
	}

	@Override
	public List<Classification> selectOneMsg() {
		// TODO Auto-generated method stub
		return classificationMapper.selectOneMsg();
	}

	@Override
	public List<Classification> getClassificationIsContactToADOrACTByIds(String[] primaryKey) {
		List<Integer> primaryKeys=new ArrayList<Integer>();
		for(String id:primaryKey){
			primaryKeys.add(Integer.parseInt(id));
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", primaryKeys);
		return classificationMapper.getClassificationIsContactToADOrACTByIds(map);
	}

	

	
	

}