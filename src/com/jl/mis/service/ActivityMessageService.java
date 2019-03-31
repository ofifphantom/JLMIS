package com.jl.mis.service;

import java.util.List;

import com.jl.mis.model.ActivityMessage;
/**
 * 活动信息Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:39:58
 * @Description TODO
 */

public interface ActivityMessageService extends BaseService<ActivityMessage>{
	
	
	//-------------------------------------APP------------------------------------
	
	/**
	 * 根据用户id获取活动消息
	 * @param userId
	 * @return
	 */
	public List<ActivityMessage> selectActivityMessageByUserId(int userId); 
	
	/**
	 * 根据用户id获取该用户未读的活动消息数量
	 * @param userId
	 * @return
	 */
	public int selectUnreadActivityMessageNumByUserId(int userId); 
	
	
	/**
	 * 根据用户id获取该用户未读的活动消息数量
	 * @param userId
	 * @return
	 */
	public Integer updateByStatusByUserId(Integer userId); 
}