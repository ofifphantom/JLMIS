package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ActivityInformationMapper;
import com.jl.mis.mapper.ActivityMessageMapper;
import com.jl.mis.mapper.ShoppingCartMapper;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.ActivityMessage;
import com.jl.mis.model.User;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.ActivityMessageService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:40:58
 * @Description 活动信息ServiceImpl
 */
@Service
public class ActivityInformationServiceImpl implements ActivityInformationService{

	@Autowired
	private ActivityInformationMapper activityInformationMapper;
	
	@Autowired
	private ActivityMessageService activityMessageService; 
	
	@Autowired
	private UserService1 userService;
	@Autowired
	private ShoppingCartMapper shoppingCartMapper;
	
	
	//原start
	@Override
	public int saveEntity(ActivityInformation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(ActivityInformation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ActivityInformation findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return activityInformationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ActivityInformation t) throws Exception {
		
		return activityInformationMapper.insert(t);
	}

	@Override
	public int insertSelective(ActivityInformation t) throws Exception {
		
		return activityInformationMapper.insertSelective(t);
	}

	//APP & PC通用
	@Override
	public ActivityInformation selectByPrimaryKey(Integer id) {
		
		return activityInformationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ActivityInformation t) throws Exception {
		
		return activityInformationMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(ActivityInformation t) throws Exception {
		
		return activityInformationMapper.updateByPrimaryKey(t);
	}

	@Override
	public DataTables getDataTables(DataTables dataTables, String identifier,
			String name, String operatorIdentifier, int activityType, int state,String flag,String operatorTime) {
		// TODO Auto-generated method stub
		String[] columns = {"id", "identifier", "name", "activity_type","introduction","budget", "end_validity_time","state","submitter_identifier","submit_time","id"};//state用来显示预警信息
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorIdentifier", operatorIdentifier);
		params.put("activityType", activityType);
		params.put("state", state);
		params.put("flag", flag);//从前台页面获取   用来判断是哪个页面  1：活动送审页面   2:活动审核页面
		params.put("operatorTime", operatorTime);
		
		dataTables.setiTotalDisplayRecords(activityInformationMapper.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(activityInformationMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(activityInformationMapper.selectForSearch(params));// 返回的结果集
		return dataTables;
	}

	@Override
	public boolean updateActivityInformationStateByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return activityInformationMapper.updateActivityInformationStateByIds(map);
	}

	@Override
	public List<ActivityInformation> getActivityInformationByIds(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return activityInformationMapper.getActivityInformationByIds(list);
	}

	@Override
	public int getIdByIdentifier(String identifier) {
		// TODO Auto-generated method stub
		return activityInformationMapper.getIdByIdentifier(identifier);
	}

	@Override
	public boolean deleteActivityInformationByIds(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return activityInformationMapper.deleteActivityInformationByIds(list);
	}

	@Override
	public List<ActivityInformation> selectMsgOrderBySearchMsg(
			String identifier, String name, String operatorIdentifier,
			int activityType, int state, String flag,String operatorTime) {
		// TODO Auto-generated method stub
		
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorIdentifier", operatorIdentifier);
		params.put("activityType", activityType);
		params.put("state", state);
		params.put("flag", flag);//从前台页面获取   用来判断是哪个页面  1：活动送审页面   2:活动审核页面
		params.put("operatorTime", operatorTime);
		return activityInformationMapper.selectMsgOrderBySearchMsg(params);
	}

	@Override
	public List<ActivityInformation> getAllPassActivityInformation() {
		// TODO Auto-generated method stub
		return activityInformationMapper.getAllPassActivityInformation();
	}

	@Override
	public List<ActivityInformation> getAllPassActivityInformationByType2() {
		// TODO Auto-generated method stub
		return activityInformationMapper.getAllPassActivityInformationByType2();
	}

	@Override
	public void updateActivityInformationToOnline() throws Exception {
		// TODO Auto-generated method stub
		List<ActivityInformation> acList = activityInformationMapper.getAllReadyToOnlineActivityInformation();
		if(acList == null || acList.size()<=0){
			return;
		}
		ActivityInformation activityInformation ;
		Date date;
	    ActivityMessage activityMessage;
		for (int i = 0; i < acList.size(); i++) {
			//获取活动id
			int acId = acList.get(i).getId();
			
			//活动自动上线
			activityInformation=new ActivityInformation();
			activityInformation.setState(4);//设置状态为上线
			activityInformation.setId(acId);//设置id
			activityInformationMapper.updateByPrimaryKeySelective(activityInformation);
			
		     //向APP发送活动消息
		     List<User> appUserList = userService.selectAllEffectiveAppUser();
		     for (int j = 0; j < appUserList.size(); j++) {
				int userId = appUserList.get(j).getId();
				activityMessage=new ActivityMessage();
				activityMessage.setActivityInformatId(acId);//设置活动id
				activityMessage.setUserId(userId);//设置用户id
				activityMessage.setStatus(0);//状态默认为未读
				date =new Date();
				activityMessage.setGetTime(date);//设置消息获取时间
				
				activityMessageService.insertSelective(activityMessage);
			 }
			
		}
	}

	@Override
	public void updateActivityInformationToOffline() {
		// TODO Auto-generated method stub
		activityInformationMapper.updateActivityInformationToOffline();
		shoppingCartMapper.updateStatus();
	}

	@Override
	public void updateActivityInformationToInvalid() {
		// TODO Auto-generated method stub
		activityInformationMapper.updateActivityInformationToInvalid();
		
	}

	@Override
	public List<ActivityInformation> getActivityInformationIsContactToADByIds(
			ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return activityInformationMapper.getActivityInformationIsContactToADByIds(list);
	}

	@Override
	public List<ActivityInformation> getAllEffectPreSellActivityInformation() {
		// TODO Auto-generated method stub
		return activityInformationMapper.getAllEffectPreSellActivityInformation();
	}

	@Override
	public List<ActivityInformation> getAllEffectPreSellActivityInformationByGoodsDetailsIdAndCouponId(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return activityInformationMapper.getAllEffectPreSellActivityInformationByGoodsDetailsIdAndCouponId(map);
	}

	@Override
	public List<ActivityInformation> getAllEffectPreSellActivityInformationByGoodsDetailsId(int goodsDetailsId) {
		// TODO Auto-generated method stub
		return activityInformationMapper.getAllEffectPreSellActivityInformationByGoodsDetailsId(goodsDetailsId);
	}

	@Override
	public boolean updateSubmitInfoByIds(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return activityInformationMapper.updateSubmitInfoByIds(map);
	}
    
	
}