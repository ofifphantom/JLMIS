package com.jl.mis.service;

import java.util.List;

import com.jl.mis.mapper.VersionMessageMapper;
import com.jl.mis.model.VersionMessage;

public interface VersionMessageService extends BaseService<VersionMessage>{
	
	/**
	 * 获取所有的版本信息
	 * @return
	 */
	public List<VersionMessage> selectAllMessage();
	
	/**
	 * 根据版本名称查询版本信息
	 * @param versionMessage 
	 * @return
	 */
	public VersionMessage selectByVersionName(VersionMessage versionMessage);
	
	/**
	 * 根据isAndroidOriOS查询所有信息
	 * @param versionMessage 
	 * @return
	 */
	public List<VersionMessage> selectAllMessageByIsAndroidOriOS(VersionMessage versionMessage);

}
