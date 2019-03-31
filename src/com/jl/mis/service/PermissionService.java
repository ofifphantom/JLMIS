package com.jl.mis.service;

import java.util.Date;
import java.util.List;

import com.jl.mis.model.Permission;
/**
 * 权限Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:45:26
 * @Description TODO
 */
public interface PermissionService  extends BaseService<Permission>{
	
	/**
	 * 批量保存全部信息
	 * @param permissions 管理员所拥有的权限
	 * @param adminId 管理员id
	 * @param userIdentifier 操作人编号
	 * @param date 添加时间
	 * @return
	 */
	public int insertBatch(String[] permissions,Integer adminId,String userIdentifier,Date date);
	
	/**
	 * 根据管理员id删除 信息
	 * @param id 管理员id
	 * @return
	 */
	public int deleteByUserId(Integer id);
    
}