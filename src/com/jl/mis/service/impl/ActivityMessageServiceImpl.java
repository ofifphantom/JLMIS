package com.jl.mis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ActivityMessageMapper;
import com.jl.mis.model.ActivityMessage;
import com.jl.mis.service.ActivityMessageService;
/**
 * 活动信息ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:39:58
 * @Description TODO
 */
@Service
public class ActivityMessageServiceImpl implements ActivityMessageService{

	@Autowired
	private ActivityMessageMapper activityMessageMapper;
	
	@Override
	public int saveEntity(ActivityMessage t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(ActivityMessage t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ActivityMessage findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return activityMessageMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ActivityMessage t) throws Exception {
		// TODO Auto-generated method stub
		return activityMessageMapper.insert(t);
	}

	@Override
	public int insertSelective(ActivityMessage t) throws Exception {
		// TODO Auto-generated method stub
		return activityMessageMapper.insertSelective(t);
	}

	@Override
	public ActivityMessage selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return activityMessageMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ActivityMessage t) throws Exception {
		// TODO Auto-generated method stub
		return activityMessageMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(ActivityMessage t) throws Exception {
		// TODO Auto-generated method stub
		return activityMessageMapper.updateByPrimaryKey(t);
	}

	@Override
	public List<ActivityMessage> selectActivityMessageByUserId(int userId) {
		// TODO Auto-generated method stub
		return activityMessageMapper.selectActivityMessageByUserId(userId);
	}

	@Override
	public int selectUnreadActivityMessageNumByUserId(int userId) {
		// TODO Auto-generated method stub
		return activityMessageMapper.selectUnreadActivityMessageNumByUserId(userId);
	}
	
	/**
	 * 根据用户id获取该用户未读的活动消息数量
	 * @param userId
	 * @return
	 */
	public Integer updateByStatusByUserId(Integer userId){
		return activityMessageMapper.updateByStatusByUserId(userId);
	}
	
}