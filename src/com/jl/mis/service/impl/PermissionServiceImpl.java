package com.jl.mis.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.PermissionMapper;
import com.jl.mis.model.Permission;
import com.jl.mis.service.PermissionService;
/**
 * 权限ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:45:26
 * @Description TODO
 */
@Service
public class PermissionServiceImpl  implements PermissionService{

	@Autowired
	private PermissionMapper permissionMapper;
	
	@Override
	public int saveEntity(Permission t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(Permission t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Permission findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Permission t) throws Exception {
		// TODO Auto-generated method stub
		return permissionMapper.insert(t);
	}

	@Override
	public int insertSelective(Permission t) throws Exception {
		// TODO Auto-generated method stub
		return permissionMapper.insertSelective(t);
	}

	@Override
	public Permission selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Permission t) throws Exception {
		// TODO Auto-generated method stub
		return permissionMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(Permission t) throws Exception {
		// TODO Auto-generated method stub
		return permissionMapper.updateByPrimaryKey(t);
	}

	@Override
	public int insertBatch(String[] permissions, Integer adminId,String userIdentifier,Date date) {
		Permission permissionMsg;
		List<Permission> permissionList=new ArrayList<>();
		//组合成一个个permission类，传入mapper中
		for(String permission:permissions){
			permissionMsg=new Permission();
			permissionMsg.setMenuId(Integer.parseInt(permission));
			permissionMsg.setUserId(adminId);
			permissionMsg.setOperatorIdentifier(userIdentifier);
			permissionMsg.setCreateTime(date);
			permissionList.add(permissionMsg);
		}
		return permissionMapper.insertBatch(permissionList);
	}

	@Override
	public int deleteByUserId(Integer id) {
		// TODO Auto-generated method stub
		return permissionMapper.deleteByUserId(id);
	}
    
}