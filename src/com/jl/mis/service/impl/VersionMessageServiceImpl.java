package com.jl.mis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.VersionMessageMapper;
import com.jl.mis.model.VersionMessage;
import com.jl.mis.service.VersionMessageService;

@Service
public class VersionMessageServiceImpl implements VersionMessageService {
	
	@Autowired
	private VersionMessageMapper versionMessageMapper;
	

	//原start
	@Override
	public int saveEntity(VersionMessage t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(VersionMessage t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VersionMessage findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return versionMessageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(VersionMessage t) throws Exception {
		// TODO Auto-generated method stub
		return versionMessageMapper.insert(t);
	}

	@Override
	public int insertSelective(VersionMessage t) throws Exception {
		// TODO Auto-generated method stub
		return versionMessageMapper.insertSelective(t);
	}

	@Override
	public VersionMessage selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return versionMessageMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(VersionMessage t) throws Exception {
		// TODO Auto-generated method stub
		return versionMessageMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(VersionMessage t) throws Exception {
		// TODO Auto-generated method stub
		return versionMessageMapper.updateByPrimaryKey(t);
	}

	@Override
	public List<VersionMessage> selectAllMessage() {
		// TODO Auto-generated method stub
		return versionMessageMapper.selectAllMessage();
	}

	@Override
	public VersionMessage selectByVersionName(VersionMessage versionMessage) {
		// TODO Auto-generated method stub
		return versionMessageMapper.selectByVersionName(versionMessage);
	}

	@Override
	public List<VersionMessage> selectAllMessageByIsAndroidOriOS(VersionMessage versionMessage) {
		// TODO Auto-generated method stub
		return versionMessageMapper.selectAllMessageByIsAndroidOriOS(versionMessage);
	}

}
