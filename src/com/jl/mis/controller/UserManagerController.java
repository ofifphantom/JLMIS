package com.jl.mis.controller;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.cxf.transport.http.HTTPSession;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jl.mis.model.Classification;
import com.jl.mis.model.Log;
import com.jl.mis.model.Menu;
import com.jl.mis.model.Permission;
import com.jl.mis.model.User;
import com.jl.mis.service.LogService;
import com.jl.mis.service.MenuService;
import com.jl.mis.service.PermissionService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.SHAUtil;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月11日 下午3:36:00
 * @Description 用户管理Controller
 *
 */
@Controller
@RequestMapping("backgroundManagement/background/userManager/")
public class UserManagerController extends BaseController {

	// 声明Log类
	Log log;

	@Autowired
	private UserService1 userService;
	@Autowired
	private LogService logService;
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private MenuService menuService;
	
	//重置密码
	private static final String RESET_PASSWORD="111111";
	/**
	 * 进入用户（管理员）管理页面 page 1:用户（管理员）页面 2：修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, int page) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}
		if (page == 1) {
			return "进入用户（管理员）页面";
		} else if (page == 2) {
			return "进入用户页面";
		} else {
			return "进入修改密码页面";
		}
	}

	/**
	 * 从数据库中获取管理员信息
	 * 
	 * @param request
	 * @param name
	 *            页面搜索--名称
	 * @param telphoneNo
	 *            页面搜索--电话号码
	 */
	@ResponseBody
	@RequestMapping(value = "getAdministratorMsg")
	public DataTables getAdministratorMsg(HttpServletRequest request, String name, String telphoneNo,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);

		return userService.getAdministratorMsg(dataTables, name, telphoneNo,operatorTime);
	}

	/**
	 * 从数据库中获取用户信息
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "getUserMsg")
	public DataTables getUserMsg(HttpServletRequest request, String isVIP, String identifier, String name,
			String telphoneNo, String operatorIdentifierName, String lowestPayMoney, String onlinePayMoney,
			String historyPayMoney, String historyPayNum, String startPayTime, String endPayTime,String isVipTime,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);
		// isVIP为1表示是VIP页面，0表示是待添加页面
		return userService.getUserMsg(dataTables, isVIP, identifier, name, telphoneNo, operatorIdentifierName,
				lowestPayMoney, onlinePayMoney, historyPayMoney, historyPayNum, startPayTime, endPayTime,isVipTime,operatorTime);
	}

	/**
	 * 新增前，查询登录名是否存在
	 * 
	 * @param request
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectAdminByLoginName")
	public JSONObject selectAdminByLoginName(HttpServletRequest request, String loginName) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = userService.findUserByLoginName(loginName);
		if (u != null) {
			// 往前台返回结果集
			rmsg.put("success", false);
			rmsg.put("msg", "该登录名已被占用!");
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 新增管理员信息
	 * 
	 * @param request
	 * @param user
	 *            前台输入的管理员信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertAdminMsg")
	public JSONObject insertAdminMsg(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		User user = new User();
		// 从前台获取数据
		user.setLoginName(request.getParameter("loginName"));
		user.setName(request.getParameter("name"));
		user.setPhone(request.getParameter("phone"));
		user.setPassword(request.getParameter("password"));
		user.setUserGroup(Integer.parseInt(request.getParameter("userGroup")));
		// 从前台获取选择的权限
		String[] permissions = request.getParameter("resIds").split(",");
		// 自动生成编号
		user.setIdentifier(SundryCodeUtil.getPosCode(Constants.CODE_ADMIN));
		// 添加管理员标志
		user.setAdministratorOrUser(Constants.ADMIN_FLAG);
		// 默认设置新添的管理员账号为有效1：有效，0：无效
		user.setIsEffective(1);
		// 给密码加密
		user.setPassword(SHAUtil.shaEncode(user.getPassword()));
		// 添加操作时间
		Date date = new Date();
		user.setCreateTime(date);
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		user.setOperatorIdentifier(userIdentifier);
		int result = userService.insertSelective(user);
		// 获取刚才存入数据的那条数据的主键
		int id = user.getId();
		if (result > 0) {
			// 往权限表中批添加该管理员的权限
			permissionService.insertBatch(permissions, id, userIdentifier, date);
			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_USERMANAGER, user.getIdentifier(), Constants.OPERATE_INSERT, date,
					userIdentifier);
			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
			return rmsg;
		}

		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 点击编辑时，根据主键查询管理员信息以及所拥有的权限
	 * 
	 * @param request
	 * @param id
	 *            管理员主键id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAdminMsgById")
	public User selectAdminMsgById(HttpServletRequest request, String id) {

		return userService.selectAdminMsgById(id);

	}
	
	/**
	 * 编辑前，查询登录名是否存在
	 * 
	 * @param request
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectAdminByLoginNameAndId")
	public JSONObject selectAdminByLoginNameAndId(HttpServletRequest request, User user) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = userService.selectByPrimaryKeyAndLoginName(user);
		if (u != null) {
			// 往前台返回结果集
			rmsg.put("success", false);
			rmsg.put("msg", "该登录名已被占用!");
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 编辑管理员信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateAdminMsgById")
	public JSONObject updateAdminMsgById(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		User user = new User();
		// 从前台获取数据
		user.setId(Integer.parseInt(request.getParameter("primaryKey")));
		user.setName(request.getParameter("name"));
		user.setPhone(request.getParameter("phone"));
		user.setUserGroup(Integer.parseInt(request.getParameter("userGroup")));
		// 从前台获取选择的权限
		String[] permissions = request.getParameter("resIds").split(",");
		// 从前台获取用户编号
		String identifier = request.getParameter("updateIdentifier");
		// 添加操作时间
		Date date = new Date();
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);

		int result = userService.updateByPrimaryKeySelective(user);

		if (result > 0) {
			// 修改时先删除权限表中属于该管理员的所有权限，重新添加
			if (permissionService.deleteByUserId(user.getId()) >= 0) {
				// 往权限表中批添加该管理员的权限
				permissionService.insertBatch(permissions, user.getId(), userIdentifier, date);
			}

			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_USERMANAGER, identifier, Constants.OPERATE_UPDATE, date, userIdentifier);
			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}

		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 删除管理员
	 * 
	 * @param request
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteByPrimaryKey", method = RequestMethod.POST)
	public JSONObject deleteByPrimaryKey(HttpServletRequest request, User user) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 根据主键删除管理员
		int result = userService.deleteByPrimaryKey(user.getId());
		// 操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);

		if (result > 0) {
			// 根据管理员id删除权限表中对应的权限
			permissionService.deleteByUserId(user.getId());

			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_USERMANAGER, user.getIdentifier(), Constants.OPERATE_DELETE, new Date(),
					userIdentifier);
			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.DELE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.DELE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 管理员禁用操作
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserForbidden", method = RequestMethod.POST)
	public JSONObject updateUserForbidden(HttpServletRequest request, User u) throws Exception {
		JSONObject rmsg = new JSONObject();

		// 前台传来的当前需要操作的管理员处于未禁用状态，需要改成禁用状态
		if (u.getIsEffective() == 0) {
			u.setIsEffective(1);
		}
		// 前台传来的当前需要操作的管理员处于禁用状态，需要改成未禁用状态
		else {
			u.setIsEffective(0);
		}
		// 更新当前管理员的状态
		int result = userService.updateByPrimaryKeySelective(u);
		if (result > 0) {
			// 添加操作人编号，从session中获取
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_USERMANAGER, u.getIdentifier(), Constants.OPERATE_UPDATE, new Date(),
					userIdentifier);
			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 判断输入的原密码与登陆的密码是否一样
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "decidePwd")
	public JSONObject decidePwd(HttpServletRequest request, String password) throws Exception {
		JSONObject rmsg = new JSONObject();
		if ((request.getSession().getAttribute("password")).equals(SHAUtil.shaEncode(password))) {
			rmsg.put("success", true);
			return rmsg;
		}
		rmsg.put("success", false);
		return rmsg;
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updatePwd")
	public JSONObject updatePwd(HttpServletRequest request, String password) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = new User();
		u.setId((Integer) request.getSession().getAttribute("userId"));
		u.setPassword(SHAUtil.shaEncode(password));
		if (userService.updateByPrimaryKeySelective(u) > 0) {
			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_USERMANAGER, (String) request.getSession().getAttribute("identifier"),
					Constants.OPERATE_UPDATE, new Date(), GetSessionUtil.GetSessionUserIdentifier(request));

			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "resetPwd")
	public JSONObject resetPwd(HttpServletRequest request, String id,String identifier) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = new User();
		u.setId(Integer.valueOf(id));
		u.setPassword(SHAUtil.shaEncode(RESET_PASSWORD));
		if (userService.updateByPrimaryKeySelective(u) > 0) {
			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_USERMANAGER, identifier,
					Constants.OPERATE_UPDATE, new Date(), GetSessionUtil.GetSessionUserIdentifier(request));

			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 操作vip用户---即将其状态改为一般用户/VIP。
	 * 
	 * @param isVip
	 *            1表示添加VIP，0表示删除VIP
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "operationVIPUser")
	public JSONObject operationVIPUser(HttpServletRequest request, String isVip) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 获取从前台传入的选择的分类的编号
		String[] identifiers = request.getParameterValues("user_identifier");
		// 保存往Log表中添加的操作对象的编号
		String identifierList = "";
		for (int i = 0; i < identifiers.length; i++) {
			if (i < identifiers.length - 1) {
				identifierList += identifiers[i] + ",";
			} else {
				identifierList += identifiers[i];
			}
		}
		// 通过session获取操作人编号
		String operatorIdentifier = "";
		Date date = null;
		if ("1".equals(isVip)) {
			operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			date = new Date();
		}

		if (userService.updateBatchByIdentifier(identifiers, isVip, operatorIdentifier, date) > 0) {
			// 往log表中插入操作数据
			if ("1".equals(isVip)) {
				insertLog(Constants.TYPE_LOG_VIP, identifierList, Constants.OPERATE_INSERT, new Date(),
						GetSessionUtil.GetSessionUserIdentifier(request));
			} else {
				insertLog(Constants.TYPE_LOG_VIP, identifierList, Constants.OPERATE_DELETE, new Date(),
						GetSessionUtil.GetSessionUserIdentifier(request));
			}

			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);

			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);

		return rmsg;
	}

	/**
	 * 根据手机号，判断该手机号是否进行注册
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getUserByPhone")
	public JSONObject getUserByPhone(HttpServletRequest request, String phone) throws Exception {
		JSONObject rmsg = new JSONObject();
		User u = userService.selectByPhone(phone);
		if (u == null) {
			rmsg.put("success", false);
			rmsg.put("msg", "该手机号未注册，不能进行添加操作。");
			return rmsg;
		}
		else if(u.getIsVip()==1){
			rmsg.put("success", false);
			rmsg.put("msg", "该手机号已是VIP，请勿重复添加。");
			return rmsg;
		}
		rmsg.put("user", u);
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 根据手机号，添加VIP
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertUserByPhone")
	public JSONObject insertUserByPhone(HttpServletRequest request, String phone, String identifier) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 通过session获取操作人编号
		String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		Date date = new Date();
		if (userService.updateUserIsVipByPhone(phone,operatorIdentifier,date) > 0) {
			// 往log表中插入操作数据
			insertLog(Constants.TYPE_LOG_VIP, identifier, Constants.OPERATE_INSERT, date,
					operatorIdentifier);

			rmsg.put("success", true);
			rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * VIP用户的导出
	 * 
	 * @param request
	 * @param response
	 * @param identifier
	 *            用户编号
	 * @param name
	 *            用户名称
	 * @param telphoneNo
	 *            电话号码
	 * @param operatorIdentifierName
	 *            操作人姓名
	 * @param lowestPayMoney
	 *            月交易额下线
	 * @param onlinePayMoney
	 *            月交易额上线
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/exportUserVIP")
	public boolean exportUserVIP(HttpServletRequest request, HttpServletResponse response, String identifier,
			String name, String telphoneNo, String operatorIdentifierName, String lowestPayMoney, String onlinePayMoney, String isVipTime)
			throws Exception {
		String fileName = "VIP用户信息";// 文档名称以及Excel里头部信息
		List<User> ulist = userService.userExport(URLDecoder.decode(identifier, "UTF-8"),
				URLDecoder.decode(name, "UTF-8"), URLDecoder.decode(telphoneNo, "UTF-8"),
				URLDecoder.decode(operatorIdentifierName, "UTF-8"), URLDecoder.decode(lowestPayMoney, "UTF-8"),
				URLDecoder.decode(onlinePayMoney, "UTF-8"),URLDecoder.decode(isVipTime, "UTF-8"));
		List<String[]> dataList = new ArrayList<>();
		String[] title; // 保存Excel表头
		String[] value; // 保存要导出的内容
		// 搜索的有数据
		if (ulist.size() > 0) {
			// 保存Excel表头
			title = new String[7];
			title[0] = "编号";
			title[1] = "姓名";
			title[2] = "手机号";
			title[3] = "总交易金额/订单数";
			title[4] = "月交易金额/订单";
			title[5] = "创建人";
			title[6] = "创建时间";
			// 保存要导出的内容
			for (User u : ulist) {
				value = new String[7];
				value[0] = u.getIdentifier();
				value[1] = u.getName();
				value[2] = u.getPhone();
				if (u.getHistoryPayMoney()!=null && u.getHistoryPayMoney() > 0) {
					value[3] = u.getHistoryPayMoney() + "/" + u.getHistoryPayNum();
				} else {
					value[3] = 0 + "";
				}
				if (u.getMonthHistoryPayMoney()!=null && u.getMonthHistoryPayMoney() > 0) {
					value[4] = u.getMonthHistoryPayMoney() + "/" + u.getMonthHistoryPayNum();
				} else {
					value[4] = 0 + "";
				}

				if (u.getOperatorIdentifier() != null && !"".equals(u.getOperatorIdentifier())) {
					value[5] = u.getOperatorIdentifier() + "(" + u.getOperatorName() + ")";
				} else {
					value[5] = "";
				}

				if (u.getIsVipTime() != null && !"".equals(u.getIsVipTime())) {
					value[6] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u.getIsVipTime());
				} else {
					value[6] = "";
				}

				dataList.add(value);
			}

		}
		// 没有搜索到数据
		else {
			title = new String[1];
			title[0] = Constants.NO_DATA_EXPORT;// 无数据提示
		}
		// 调用公共方法导出数据
		CommonMethod.exportData(request, response, fileName, title, dataList);
		return true;
	}

	/**
	 * 往Log表中添加日志信息
	 * 
	 * @param operateType
	 *            操作类型
	 * @param identifier
	 *            操作对象的编号
	 * @param operateMethod
	 *            操作的方法(添加/删除/修改)
	 * @param date
	 *            操作日期
	 * @param userIdentifier
	 *            操作人编号
	 * @throws Exception
	 */
	public void insertLog(Integer operateType, String identifier, String operateMethod, Date date,
			String userIdentifier) throws Exception {
		log = new Log();
		log.setOperateType(operateType);
		log.setOperateObject(identifier + "(" + operateMethod + ")");
		log.setOperateTime(date);
		log.setOperatorIdentifier(userIdentifier);
		logService.insert(log);
	}
	/**
	 * 
	 * @param 登录显示集体列表
	 * @param id
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "selectMenuById")
	public JSONArray selectMenuById(HttpServletRequest request, String id) {
		HttpSession session=request.getSession();
		List<Menu> list=new ArrayList<Menu>();
		List<Menu> listall=menuService.selectByAll();
		List<Integer> parent=new ArrayList<Integer>();
		for (Permission object : userService.selectAdminMsgById(id).getPermissions()) {
			/*if(object.getMenu().getId()==38){
				object.getMenu().setUrl(object.getMenu().getUrl()+session.getAttribute("loginName"));
			}*/
			if(object.getMenu().getSort()!=null&&object.getMenu().getSort()==0){
				parent.add(object.getMenu().getId());
			}else{
				list.add(object.getMenu());
			}
			
			boolean have=false;
			for(Integer Pid:parent){
				if(Pid==object.getMenu().getParentId()){
					have=true;
				}
			}
			if(object.getMenu().getSort()==null||object.getMenu().getSort()!=0){
				if(!have){
					parent.add(object.getMenu().getParentId());
				}
			}
			
		}
		for(Menu menu:listall){
			if(menu.getId()==38){
				menu.setUrl(menu.getUrl()+session.getAttribute("loginName"));
			}
			for(Integer Pid:parent){
				if(menu.getId()==Pid){
					list.add(menu);
				}
			}
		}
		JSONArray array=changMenutoJason(list,0);
		
		changMenutoJasonall(array);
		
		return array;
	}
	
	/**
	 * 
	 * @param 用户编辑时调用
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAllMenu")
	public JSONArray selectAllMenu(HttpServletRequest request, String id) {
		List<Menu> listall=menuService.selectByAll();
		JSONArray menuall=changMenutoJason(listall,0);
		List<Menu> list=new ArrayList<Menu>();
		if(id!=null&&!"".equals(id)){
			for (Permission object : userService.selectAdminMsgById(id).getPermissions()) {
				list.add(object.getMenu());
			}
		}
		//JSONArray menu=changMenutoJason(list,0);
		changMenutoJasonall(menuall, list);;
		return menuall;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	public JSONArray changMenutoJason(List<Menu> menus,Integer parentid){
		JSONArray array=new JSONArray();
		for (Menu menu : menus) {
			JSONObject jsonObject=new JSONObject();
			if(menu.getSort()!=null&&menu.getSort()==0){
				if(menu.getParentId()==parentid){
					jsonObject.put("id", menu.getId());
					jsonObject.put("text", menu.getName());
					jsonObject.put("state", "closed");
					jsonObject.put("children", changMenutoJason(menus,menu.getId()));
					array.add(jsonObject);
				}
			}else{
				if(menu.getParentId()==parentid){
					jsonObject.put("id", menu.getId());
					jsonObject.put("text", menu.getName());
					jsonObject.put("iconCls","icon-no");
					JSONObject o=new JSONObject();
					o.put("name", menu.getUrl());
					jsonObject.put("attributes", o);
					array.add(jsonObject);
				}
			}
			
		}
		return array;
	}
	
	/**
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	
	public void changMenutoJasonall(JSONArray menusall,List<Menu> menu){
		for(int i=0;i<menusall.size();i++){
			((JSONObject) menusall.get(i)).put("state", "open");
			for(int j=0;j<menu.size();j++){
				if(((JSONObject) menusall.get(i)).get("id").equals(menu.get(j).getId())){
					((JSONObject) menusall.get(i)).put("checked", true);
					break;
				}
			}
			if(((JSONObject) menusall.get(i)).get("children")!=null){
				changMenutoJasonall((JSONArray) ((JSONObject) menusall.get(i)).get("children"),menu);
			}
		}
	}
	
	public void changMenutoJasonall(JSONArray menusall){
		if(menusall.get(0)!=null){
			((JSONObject) menusall.get(0)).put("state", "open");
			if(((JSONObject) menusall.get(0)).get("children")!=null){
				changMenutoJasonall((JSONArray)((JSONObject) menusall.get(0)).get("children"));
			}
		}
	}
	
	
}
