package com.jl.mis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.AdvertisementPictureMapper;
import com.jl.mis.model.AdvertisementPicture;
import com.jl.mis.service.AdvertisementPictureService;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:43:22
 * @Description 广告图片ServiceImpl
 */
@Service
public class AdvertisementPictureServiceImpl implements AdvertisementPictureService{

	@Autowired
	private AdvertisementPictureMapper advertisementPictureMapper;
	
	//原start
	@Override
	public int saveEntity(AdvertisementPicture t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(AdvertisementPicture t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdvertisementPicture findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		 
		return advertisementPictureMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AdvertisementPicture t) throws Exception {
		 
		return advertisementPictureMapper.insert(t);
	}

	@Override
	public int insertSelective(AdvertisementPicture t) throws Exception {
		 
		return advertisementPictureMapper.insertSelective(t);
	}

	@Override
	public AdvertisementPicture selectByPrimaryKey(Integer id) {
		 
		return advertisementPictureMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AdvertisementPicture t) throws Exception {
		
		return advertisementPictureMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(AdvertisementPicture t) throws Exception {
		
		return advertisementPictureMapper.updateByPrimaryKey(t);
	}

}