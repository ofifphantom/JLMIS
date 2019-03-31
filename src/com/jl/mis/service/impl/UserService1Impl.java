package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.CouponInformationMapper;
import com.jl.mis.mapper.UserCouponsMapper;
import com.jl.mis.mapper.UserMapper;
import com.jl.mis.model.ApiOrderMsg;
import com.jl.mis.model.User;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.DataTables;
/**
 * 用户ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:13
 * @Description TODO
 */
@Service
public class UserService1Impl implements UserService1 {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private CouponInformationMapper couponMapper;
	@Autowired
	private UserCouponsMapper userCouponsMapper;
	
	//原start
	@Override
	public int saveEntity(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public User findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.insert(t);
	}

	//(APP & PC通用)
	@Override
	public int insertSelective(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.insertSelective(t);
	}

	@Override
	public User selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	////(APP & PC通用)
	@Override
	public int updateByPrimaryKeySelective(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(User t) throws Exception {
		// TODO Auto-generated method stub
		return userMapper.updateByPrimaryKey(t);
	}
	
	@Override
	public User findUserByLoginName(String loginName) {
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("loginName", loginName);
		return userMapper.selectAdminByLoginName(map);
	}
	
	@Override
	public DataTables getAdministratorMsg(DataTables dataTables,String name,String telphoneNo,String operatorTime) {
		String[] columns = { "identifier", "name", "phone","user_group","operator_identifier","create_time","id" };
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		//保存搜索词
		params.put("name", name);
		params.put("telphoneNo", telphoneNo);
		params.put("operatorTime", operatorTime);
		dataTables.setiTotalDisplayRecords(userMapper
				.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(userMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(userMapper.selectForSearch(params));// 返回的结果集
		return dataTables;
		
	}
	
	@Override
	public DataTables getUserMsg(DataTables dataTables,String isVip, String identifier, String name,String telphoneNo,String operatorIdentifierName,String lowestPayMoney,String onlinePayMoney,String historyPayMoney,String historyPayNum,String startPayTime,String endPayTime,String isVipTime,String operatorTime) {
		String[] columns = {"id", "identifier", "name", "phone","odrderMsg.payPrice","odrderMsg.monthPayPrice","operator_identifier","create_time","id" };
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		//保存搜索词
		params.put("isVip", Integer.parseInt(isVip));
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("telphoneNo", telphoneNo);
		params.put("operatorIdentifierName", operatorIdentifierName);
		params.put("startPayTime", startPayTime);
		params.put("endPayTime", endPayTime);
		params.put("isVipTime", isVipTime);
		params.put("operatorTime", operatorTime);
		if(lowestPayMoney!=null&&!"".equals(lowestPayMoney)){
			params.put("lowestPayMoney", Double.parseDouble(lowestPayMoney));
		}
		else{
			params.put("lowestPayMoney", "");
		}
		if(onlinePayMoney!=null&&!"".equals(onlinePayMoney)){
			params.put("onlinePayMoney", Double.parseDouble(onlinePayMoney));
		}
		else{
			params.put("onlinePayMoney", "");
		}
		if(historyPayMoney!=null&&!"".equals(historyPayMoney)){
			params.put("historyPayMoney", Double.parseDouble(historyPayMoney));
		}
		else{
			params.put("historyPayMoney", "");
		}
		
		if(historyPayNum!=null&&!"".equals(historyPayNum)){
			params.put("historyPayNum", Integer.parseInt((historyPayNum)));
		}
		else{
			params.put("historyPayNum", -1);
		}
		
		dataTables.setiTotalDisplayRecords(userMapper.iTotalDisplayRecordsUser(params));// 搜索结果总行数
		dataTables.setiTotalRecords(userMapper.iTotalRecordsUser(params));// 所有记录总行数
		dataTables.setData(userMapper.selectForSearchUser(params));// 返回的结果集
		return dataTables;
		
	}

	@Override
	public User selectAdminMsgById(String id) {
		// TODO Auto-generated method stub
		return userMapper.selectAdminMsgById(Integer.parseInt(id));
	}

	@Override
	public User selectByPrimaryKeyAndLoginName(User u) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKeyAndLoginName(u);
	}

	@Override
	public Integer updateBatchByIdentifier(String[] identifiers,String isVip,String operatorIdentifier,Date isVipTime) {
		List<String> identifierList=new ArrayList<>();
		for(String identifier:identifiers){
			identifierList.add(identifier);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", identifierList);
		map.put("isVip", Integer.parseInt(isVip));
		map.put("operatorIdentifier", operatorIdentifier);
		map.put("isVipTime", isVipTime);
		return userMapper.updateBatchByIdentifier(map);
	}

	@Override
	public User selectByPhone(String phone) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("phone", phone);
		return userMapper.selectByPhone(map);
	}

	@Override
	public Integer updateUserIsVipByPhone(String phone,String operatorIdentifier,Date isVipTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("phone", phone);
		map.put("operatorIdentifier", operatorIdentifier);
		map.put("isVipTime", isVipTime);
		return userMapper.updateUserIsVipByPhone(map);
	}

	@Override
	public List<User> userExport(String identifier, String name, String telphoneNo,
			String operatorIdentifierName, String lowestPayMoney, String onlinePayMoney, String isVipTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("identifier", identifier);
		map.put("name", name);
		map.put("telphoneNo", telphoneNo);
		map.put("operatorIdentifierName", operatorIdentifierName);
		map.put("historyPayNum", -1);
		map.put("isVipTime", isVipTime);
		if(lowestPayMoney!=null&&!"".equals(lowestPayMoney)){
			map.put("lowestPayMoney", Double.parseDouble(lowestPayMoney));
		}
		else{
			map.put("lowestPayMoney", "");
		}
		if(onlinePayMoney!=null&&!"".equals(onlinePayMoney)){
			map.put("onlinePayMoney", Double.parseDouble(onlinePayMoney));
		}
		else{
			map.put("onlinePayMoney", "");
		}
		return userMapper.userExport(map);
	}

	@Override
	public List<User> selectAllEffectiveAppUser() {
		// TODO Auto-generated method stub
		return userMapper.selectAllEffectiveAppUser();
	}

	@Override
	public User getUserByAccount(String account, Integer type) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("account", account);
		return userMapper.getUserByAccount(map);
	}

	@Override
	public boolean userRegist(User user) throws Exception {
		// TODO Auto-generated method stub
		//添加用户
		boolean userResult = userMapper.insertSelective(user)>0;
		//发放优惠券（注册返券）
		//获取userId
		Map<String, Object> map = new HashMap<>();
		map.put("type", 0);
		map.put("account", user.getPhone());
		int userId = userMapper.getUserByAccount(map).getId();
		//获取可以发放的优惠券的id列表
		List<Integer> couponIds = couponMapper.selectAllAvailableCouponForRegist();
		if(couponIds!=null && couponIds.size()>0){
			//用户优惠券表的处理
			UserCoupons userCoupons;
			boolean couponResult = false;
			for (int i = 0; i < couponIds.size(); i++) {
				userCoupons = new UserCoupons();
				userCoupons.setStatus(0);
				userCoupons.setGetTime(new Date());
				userCoupons.setUserId(userId);
				userCoupons.setCouponInformationId(couponIds.get(i));
				couponResult = userCouponsMapper.insertSelective(userCoupons)>0;
			}
		}
		
		
		return false;
	}

	@Override
	public List<ApiOrderMsg> selectOrderMsgByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.selectOrderMsgByUserId(userId);
	}


   
}