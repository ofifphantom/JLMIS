package com.jl.mis.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.ApiOrderMsg;
import com.jl.mis.model.User;
import com.jl.mis.utils.DataTables;
/**
 * 用户Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:13
 * @Description TODO
 */
public interface UserService1 extends BaseService<User> {
	
	/**
	 * 根据登录名查询管理员
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName);
	
	/**
	 * 根据登录名和主键获取管理员信息
	 * @param User 用户model
	 * @return
	 */
	public User selectByPrimaryKeyAndLoginName(User u);
	
	/**
	 * 从数据库中获取管理员信息
	 * @return 返回一个用户列表对象
	 */
	public DataTables getAdministratorMsg(DataTables dataTables,String name,String telphoneNo,String operatorTime);
	
	/**
	 * 从数据库中获取用户信息
	 * @return 返回一个用户列表对象
	 */
	public DataTables getUserMsg(DataTables dataTables,String isVip, String identifier, String name,String telphoneNo,String operatorIdentifierName,String lowestPayMoney,String onlinePayMoney,String historyPayMoney,String historyPayNum,String startPayTime,String endPayTime,String isVipTime,String operatorTime);
	
	/**
	 * 根据主键联合查询user表和permission权限表
	 * @param id user主键
	 * @return
	 */
	public User selectAdminMsgById(String id);
	
	/**
	 * 根据主键批量更新用户状态(从VIP改为一般用户)
	 * @return
	 */
	Integer updateBatchByIdentifier(String[] primaryKey,String isVip,String operatorIdentifier,Date isVipTime);
	
	/**
	 * 根据电话查询信息
	 * @param id user主键
	 * @return
	 */
	public User selectByPhone(String phone);
	
	/**
	 * 根据手机号更新用户状态(从一般用户改为VIP)
	 * @return
	 */
	Integer updateUserIsVipByPhone(String phone,String operatorIdentifier,Date isVipTime);
	
	/**
	 * 用户VIP页面的导出操作
	 * @param params
	 * @return
	 */
	List<User> userExport(String identifier, String name,String telphoneNo,String operatorIdentifierName,String lowestPayMoney,String onlinePayMoney, String isVipTime);
   
	/**
	 * 获取所有有效的APP用户
	 * @return
	 */
	List<User> selectAllEffectiveAppUser();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//---------------------APP--------------------
	
		/**
		 * 根据账号获取用户信息
		 * @param account 帐号
		 * @param type 帐号类型
		 * @return
		 */
		public  User getUserByAccount(String account,Integer type);
		
		/**
		 * 用户注册
		 * @param user 用户信息
		 * @return
		 */
		public boolean userRegist(User user) throws Exception;
	
		/**
		 * 客服聊天   用户使用的发送订单接口
		 * @param userId
		 * @return
		 */
		public List<ApiOrderMsg> selectOrderMsgByUserId(Integer userId);
	
	
	
	
	
	
}