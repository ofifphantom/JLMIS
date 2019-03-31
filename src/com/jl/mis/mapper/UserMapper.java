package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.ApiOrderMsg;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.User;
/**
 * 用户mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:13
 * @Description TODO
 */
public interface UserMapper extends BaseMapper<User> {
   
	/**
	 * 根据登录名查询管理员信息
	 * @param loginName 登录名
	 * @return
	 */
	public User selectAdminByLoginName(Map<String,Object> map);
	
	/**
	 * 根据登录名和主键获取管理员信息
	 * @param User 用户model
	 * @return
	 */
	public User selectByPrimaryKeyAndLoginName(User u);
	
	/**
	 * 根据主键联合查询user表和permission权限表
	 * @param id user主键
	 * @return
	 */
	public User selectAdminMsgById(Integer id);
	
	/**
	 *记录用户总行数
	 * @return
	 */
	Integer iTotalRecordsUser(Map<String, Object> params);
	
	/**
	 * 搜索用户记录总行数
	 * @return
	 */
	Integer iTotalDisplayRecordsUser(Map<String, Object> params);
	
	/**
	 * 获取用户的信息
	 * @return
	 */
	List<User> selectForSearchUser(Map<String, Object> params);
	
	/**
	 * 根据主键批量更新用户状态(从VIP改为一般用户)
	 * @return
	 */
	Integer updateBatchByIdentifier(Map<String, Object> map);
	
	/**
	 * 根据电话查询信息
	 * @param id user主键
	 * @return
	 */
	public User selectByPhone(Map<String, Object> map);
	
	/**
	 * 根据手机号更新用户状态(从一般用户改为VIP)
	 * @return
	 */
	Integer updateUserIsVipByPhone(Map<String, Object> map);
	
	/**
	 * 用户VIP页面的导出操作
	 * @param params
	 * @return
	 */
	List<User> userExport(Map<String, Object> map);
	
	/**
	 * 获取所有有效的APP用户
	 * @return
	 */
	List<User> selectAllEffectiveAppUser();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//---------------------APP--------------------
	
	/**
	 * 根据账号获取用户信息
	 * @param map
	 * @return
	 */
	public  User getUserByAccount(Map<String, Object> map);
	
	/**
	 * 客服聊天   用户使用的发送订单接口
	 * @param userId
	 * @return
	 */
	public List<ApiOrderMsg> selectOrderMsgByUserId(Integer userId);
	
	
	
	
	
	
	
	
}