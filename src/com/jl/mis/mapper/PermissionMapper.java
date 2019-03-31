package com.jl.mis.mapper;

import java.util.List;

import com.jl.mis.model.Permission;
/**
 * 权限mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:45:26
 * @Description TODO
 */
public interface PermissionMapper  extends BaseMapper<Permission>{
	
	/**
	 * 批量保存全部信息
	 * @param permissionList 管理员所拥有的权限信息
	 * @return
	 */
	public int insertBatch(List<Permission> permissionList);
	
	/**
	 * 根据管理员id删除 信息
	 * @param id 管理员id
	 * @return
	 */
	public int deleteByUserId(Integer id);
    
}