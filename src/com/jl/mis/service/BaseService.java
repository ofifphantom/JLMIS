package com.jl.mis.service;

/**
 * 基础业务类
 * */
public interface BaseService<T> {
	//原start
	/**
	 * 保存
	 * 
	 * @param entity
	 *            实体
	 * @return 返回保存后的id
	 */
	int saveEntity(T t);

	/**
	 * 根据id删除实体
	 * 
	 * @param id
	 *            要删除的主键id
	 */
	int deleteEntity(int id);

	/**
	 * 更新实体
	 * @param entity
	 */
	int updateEntity(T t);
	
	/**
	 * 查找实体
	 * @param id
	 * */
	T findEntityById(int id);
	//原end
	
	
	
	
	
	

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

	
}
