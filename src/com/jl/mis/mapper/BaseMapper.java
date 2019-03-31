package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface BaseMapper<T> {

	/**
	 *根据主键删除 信息
	 **/
	public int deleteByPrimaryKey(Integer id) ;
    /**
	 *保存全部信息 
	 **/
	public int insert(T t) throws Exception;
    /**
	 *保存所选内容的信息 
	 **/
	public int insertSelective(T t) throws Exception;
    /**
	 *根据主键查询信息
	 **/ 
	public T selectByPrimaryKey(Integer id);
    /**
	 *根据主键更新所选内容 
	 **/
	public int updateByPrimaryKeySelective(T t) throws Exception;
    /**
	 *根据主键更新全部内容  
	 **/
	public int updateByPrimaryKey(T t) throws Exception;

	
	/**
	 *记录总行数
	 * @return
	 */
	Integer iTotalRecords(Map<String, Object> params);
	
	/**
	 * 搜索记录总行数
	 * @return
	 */
	Integer iTotalDisplayRecords(Map<String, Object> params);
	
	/**
	 * @return
	 */
	List<Object> selectForSearch(Map<String, Object> params);
	
	/**
	 * 查询当前用户是否有相应权限
	 * @param username
	 * @param PrivilegeName
	 * @return
	 */
	int HasPrivilege(JSONObject jsob);
}
