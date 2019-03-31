package com.jl.app.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.transport.http.HTTPSession;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.ActivityMessage;
import com.jl.mis.model.ApiOrderMsg;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.model.User;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.ActivityMessageService;
import com.jl.mis.service.CouponInformationService;
import com.jl.mis.service.OrderStateDetailService;
import com.jl.mis.service.UserCouponsService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.ALiYunMessageService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 用户信息管理API
 * 
 * @author 柳亚婷
 * @date 2017年12月18日 下午3:54:40
 * @Description
 * @return
 */
@Controller
@RequestMapping("/user/")
public class UserInformationApi extends BaseController {
	// 保存送票图片的文件夹名
	private String folderName = "uploadImages";
	@Autowired
	private UserService1 userService;

	@Autowired
	private CouponInformationService couponInformationService;

	@Autowired
	private UserCouponsService userCouponsService;

	@Autowired
	private OrderStateDetailService orderStateDetailService;

	@Autowired
	private ActivityMessageService activityMessageService;

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param account
	 *            帐号
	 * @param type
	 *            帐号类型(0:手机号码 1:微信 2:QQ 3:支付宝)
	 * @return resultData.flag:旗帜变量 用来标志是否需要绑定手机号：0:是 1:否
	 *         当flag=1时：resultData.message:注册成功无可返优惠券/注册成功并返券/登录成功
	 *         resultData.userId :用户id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public JSONObject login(HttpServletRequest request, String account, Integer type) throws Exception {
		JSONObject result = new JSONObject();
		// 1.参数验证
		if (account == null || type == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 2.验证帐号类型是否合法

		if (type != 0 && type != 1 && type != 2 && type != 3) {
			result.put("code", 11001);
			result.put("msg", "未知帐号类型");
			return result;
		}

		int flag = 1;// 旗帜变量 用来标志是否需要绑定手机号：0:是 1:否
		JSONObject resultInfo = new JSONObject();

		// 根据帐号信息查询用户
		User user = userService.getUserByAccount(account, type);
		if (user != null) {// 有用户信息 已注册过的用户
			// 判断帐号是否被禁用
			if (user.getIsEffective() == 0) {// 被禁用
				result.put("code", 11002);
				result.put("msg", "帐号被禁用");
				return result;
			} else if (type != 0) {// 未禁用 & 非手机号登录
				// 判断是否绑定手机号
				String phone = user.getPhone();
				if (phone == null || phone == "") {// 未绑定手机号
					flag = 0; // 返回绑定手机号
				} else {
					resultInfo.put("message", "登录成功");
					resultInfo.put("userId", user.getId());
					resultInfo.put("userPhone", user.getPhone());
					if (user.getPicUrl() == null) {
						resultInfo.put("userPicUrl", "");
					} else {
						resultInfo.put("userPicUrl", user.getPicUrl());
					}
					resultInfo.put("userName", user.getName());
					resultInfo.put("isVIP", user.getIsVip());
				}
			} else {// 未禁用 & 手机号登录
				resultInfo.put("message", "登录成功");
				resultInfo.put("userId", user.getId());
				resultInfo.put("userPhone", user.getPhone());
				if (user.getPicUrl() == null) {
					resultInfo.put("userPicUrl", "");
				} else {
					resultInfo.put("userPicUrl", user.getPicUrl());
				}
				resultInfo.put("userName", user.getName());
				resultInfo.put("isVIP", user.getIsVip());
			}

		} else {// 新用户
				// 判断是否使用手机号登录
			if (type == 0) {// 手机号登录
				boolean registResult = regist(account, account, type);

				if (!registResult) {// 注册失败
					result.put("code", 11003);
					result.put("msg", "帐号注册失败");
					return result;
				} else {// 注册成功
						// 发放优惠券（注册返券）
						// 获取userId
					int userId = userService.selectByPhone(account).getId();
					user = userService.selectByPrimaryKey(userId);
					// 获取可以发放的优惠券的id列表
					List<Integer> couponIds = couponInformationService.selectAllAvailableCouponForRegist();
					if (couponIds != null && couponIds.size() > 0) {
						// 用户优惠券表的处理
						UserCoupons userCoupons;
						for (int i = 0; i < couponIds.size(); i++) {
							userCoupons = new UserCoupons();
							userCoupons.setStatus(0);
							userCoupons.setGetTime(new Date());
							userCoupons.setUserId(userId);
							userCoupons.setCouponInformationId(couponIds.get(i));
							userCouponsService.insertSelective(userCoupons);
						}
						resultInfo.put("message", "注册成功并返券");
						resultInfo.put("userId", userId);
						resultInfo.put("userPhone", user.getPhone());
						if (user.getPicUrl() == null) {
							resultInfo.put("userPicUrl", "");
						} else {
							resultInfo.put("userPicUrl", user.getPicUrl());
						}
						resultInfo.put("userName", user.getName());
						resultInfo.put("isVIP", user.getIsVip());
					} else {// 没有可以领取的注册返券
						resultInfo.put("message", "注册成功无可返优惠券");
						resultInfo.put("userId", userId);
						resultInfo.put("userPhone", user.getPhone());
						if (user.getPicUrl() == null) {
							resultInfo.put("userPicUrl", "");
						} else {
							resultInfo.put("userPicUrl", user.getPicUrl());
						}
						resultInfo.put("userName", user.getName());
						resultInfo.put("isVIP", user.getIsVip());
					}

					// resultInfo.put("message", "注册成功");
				}
			} else {// 非手机号登录
				flag = 0; // 返回绑定手机号
			}

		}

		resultInfo.put("flag", flag);

		result.put("resultData", resultInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 绑定手机号
	 * 
	 * @param request
	 * @param phone
	 *            手机号
	 * @param account
	 *            帐号
	 * @param type
	 *            帐号类型 (0:手机号码 1:微信 2:QQ 3:支付宝)
	 * @return resultData.message:注册成功无可返优惠券/注册成功并返券/绑定成功 resultData.userId
	 *         :用户id
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "bindPhone", method = RequestMethod.POST)
	public JSONObject bindPhone(HttpServletRequest request, String phone, String account, Integer type)
			throws Exception {
		JSONObject result = new JSONObject();
		JSONObject resultInfo = new JSONObject();
		// 1.参数验证
		if (phone == null || account == null || type == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 2.验证帐号类型是否合法

		if (type != 0 && type != 1 && type != 2 && type != 3) {
			result.put("code", 11001);
			result.put("msg", "未知帐号类型");
			return result;
		}

		// 根据手机号查询用户
		User user = userService.getUserByAccount(phone, 0);
		if (user == null) {// 用户不存在 注册
			boolean registResult = regist(phone, account, type);
			if (!registResult) {// 注册失败
				result.put("code", 11003);
				result.put("msg", "帐号注册失败");
				return result;
			} else {// 注册成功
					// 发放优惠券（注册返券）
					// 获取userId
				user = userService.selectByPhone(phone);
				int userId = user.getId();
				// 获取可以发放的优惠券的id列表
				List<Integer> couponIds = couponInformationService.selectAllAvailableCouponForRegist();
				if (couponIds != null && couponIds.size() > 0) {
					// 用户优惠券表的处理
					UserCoupons userCoupons;
					for (int i = 0; i < couponIds.size(); i++) {
						userCoupons = new UserCoupons();
						userCoupons.setStatus(0);
						userCoupons.setGetTime(new Date());
						userCoupons.setUserId(userId);
						userCoupons.setCouponInformationId(couponIds.get(i));
						userCouponsService.insertSelective(userCoupons);
					}
					resultInfo.put("message", "注册成功并返券");
					resultInfo.put("userId", userId);
					resultInfo.put("userPhone", user.getPhone());
					if (user.getPicUrl() == null) {
						resultInfo.put("userPicUrl", "");
					} else {
						resultInfo.put("userPicUrl", user.getPicUrl());
					}
					resultInfo.put("userName", user.getName());
					resultInfo.put("isVIP", user.getIsVip());
				} else {// 没有可以领取的注册返券
					resultInfo.put("message", "注册成功无可返优惠券");
					resultInfo.put("userId", userId);
					resultInfo.put("userPhone", user.getPhone());
					if (user.getPicUrl() == null) {
						resultInfo.put("userPicUrl", "");
					} else {
						resultInfo.put("userPicUrl", user.getPicUrl());
					}
					resultInfo.put("userName", user.getName());
					resultInfo.put("isVIP", user.getIsVip());
				}

				result.put("resultData", resultInfo);
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			}

		} else {//手机号存在
			// 1.判断该手机号是否绑定过此类型的账户
			String accountString = "";
			switch (type) {
			case 1:
				accountString = user.getWeixin();
				break;
			case 2:
				accountString = user.getQq();
				break;
			case 3:
				accountString = user.getZhifubao();
				break;

			default:
				break;
			}
			
			if(accountString == null || accountString == "") {//没有绑定过此类型的账户:更新
				switch (type) {
				case 1:
					user.setWeixin(account);
					break;
				case 2:
					user.setQq(account);
					break;
				case 3:
					user.setZhifubao(account);
					break;

				default:
					break;
				}
				if (!(userService.updateByPrimaryKeySelective(user) > 0)) {
					result.put("code", 11004);
					result.put("msg", "手机号绑定失败");
					return result;
				} else {
					user = userService.selectByPhone(phone);
					resultInfo.put("message", "绑定成功");
					resultInfo.put("userId", user.getId());
					resultInfo.put("userPhone", user.getPhone());
					if (user.getPicUrl() == null) {
						resultInfo.put("userPicUrl", "");
					} else {
						resultInfo.put("userPicUrl", user.getPicUrl());
					}
					resultInfo.put("userName", user.getName());
					resultInfo.put("isVIP", user.getIsVip());

					result.put("resultData", resultInfo);
					result.put("code", 200);
					result.put("msg", "请求成功");
					return result;
				}
			}else {//该手机号绑定过此类型的账户:返回客户端 "此手机号已被占用，换一个试试吧~"
				
				switch (type) {
				case 1:
					result.put("code", 11006);
					result.put("msg", "此手机号已绑定过微信账号，换一个试试吧~");
					return result;
				case 2:
					result.put("code", 11006);
					result.put("msg", "此手机号已绑定过QQ账号，换一个试试吧~");
					return result;
				case 3:
					result.put("code", 11006);
					result.put("msg", "此手机号已绑定过支付宝账号，换一个试试吧~");
					return result;
				}
			}
		

		}
		result.put("code", 11004);
		result.put("msg", "手机号绑定失败");
		return result;

	}

	// 以下为私有方法
	/**
	 * 用户注册
	 * 
	 * @param phone
	 *            手机号
	 * @param account
	 *            帐号
	 * @param type
	 *            帐号类型 (0:手机号码 1:微信 2:QQ 3:支付宝)
	 * @return
	 * @throws Exception
	 */
	private boolean regist(String phone, String account, int type) throws Exception {
		User user = new User();
		// 手机号
		user.setPhone(phone);
		switch (type) {
		case 1:
			user.setWeixin(account);
			break;
		case 2:
			user.setQq(account);
			break;
		case 3:
			user.setZhifubao(account);
			break;

		default:
			break;
		}

		// 编号
		String identifier = SundryCodeUtil.getPosCode(Constants.CODE_USER);
		user.setIdentifier(identifier);
		// 姓名 默认为手机号
		user.setName("测试用" + new Date().getTime());
		// 用户
		user.setAdministratorOrUser(1);
		// 非vip
		user.setIsVip(0);
		// 有效
		user.setIsEffective(1);
		// 创建时间
		user.setCreateTime(new Date());

		return userService.insertSelective(user) > 0;
		// return userService.userRegist(user);
	}
	
	

	/**
	 * 短信验证码接口
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "MessageCode", method = RequestMethod.GET)
	public JSONObject MessageCode(HttpServletRequest request, @RequestParam("mobile") String mobile) throws Exception {
		System.out.println("我进入了MessageCode");
		JSONObject resultInfo = new JSONObject();
		/**
		 * 正则表达式：验证手机号
		 */
		String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

		if (!Pattern.matches(REGEX_MOBILE, mobile)) {
			resultInfo.put("code", 10000);
			resultInfo.put("msg", "参数错误");
			return resultInfo;
		}

		String code = "";// 验证码
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			code += random.nextInt(10);
		}
		
		//调用阿里云发送短信的方法
		SendSmsResponse response = ALiYunMessageService.sendSms(code,mobile,"","");
		System.out.println("短信接口返回的数据----------------");
		String backCode=response.getCode();//返回值
	    System.out.println("Code=" + response.getCode());
	    System.out.println("Message=" + response.getMessage());
	    System.out.println("RequestId=" + response.getRequestId());
	    System.out.println("BizId=" + response.getBizId());
	    
		Date now = new Date();
		Map<String, Object> map = new HashMap<String, Object>();


		switch (backCode) {
		case "OK":
			resultInfo.put("msg", "短信发送成功");
			resultInfo.put("code", 200);
			map.put("MessageCode", code);
			map.put("FailureTime", new Date(now.getTime() + 300020));
			resultInfo.put("resultData", map);
			break;// 提交成功
		case "isp.RAM_PERMISSION_DENY":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// RAM权限DENY
		case "isv.OUT_OF_SERVICE":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 业务停机
		case "isv.PRODUCT_UN_SUBSCRIPT":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 未开通云通信产品的阿里云客户
		case "isv.PRODUCT_UNSUBSCRIBE":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 产品未开通
		case "isv.ACCOUNT_NOT_EXISTS":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 账户不存在
		case "isv.ACCOUNT_ABNORMAL":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 账户异常
		case "isv.MOBILE_NUMBER_ILLEGAL":
			resultInfo.put("msg", "短信发送失败，请联检查手机号码");
			resultInfo.put("code", 17003);
			break;// 非法手机号
		case "isv.MOBILE_COUNT_OVER_LIMIT":
			resultInfo.put("msg", "短信发送失败，请联检查手机号码");
			resultInfo.put("code", 17003);
			break;// 手机号码数量超过限制
		case "isv.AMOUNT_NOT_ENOUGH":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 账户余额不足
		case "isv.PARAM_NOT_SUPPORT_URL":
			resultInfo.put("msg", "短信发送失败，请联系系统管理员");
			resultInfo.put("code", 17002);
			break;// 不支持URL
		default:
			resultInfo.put("msg", "短信发送失败");
			resultInfo.put("code", 17001);
			break;
		}
		return resultInfo;
	}
	



	/**
	 * 
	 * 修改头像接口
	 * 
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserPicUrl", method = RequestMethod.POST)
	public JSONObject updateUserPicUrl(HttpServletRequest request, Integer userId, String[] files) throws Exception {
		JSONObject resultInfo = new JSONObject();
		if (userId == null) {
			System.out.println("用户userId为空");
		} else {
			System.out.println("用户userId:" + userId);
		}
		if (files == null) {
			System.out.println("用户头像files为空");
		} else {
			System.out.println("用户头像files.size:" + files.length);
			if (files.length > 0) {
				System.out.println("用户头像files[0]:" + files[0]);
			}
		}

		if (userId == null || files == null || files.length == 0 || files.length > 1) {

			if (files != null) {
				System.out.println("files----size:" + files.length);
				List<String> oldUrl = new ArrayList<String>();
				for (int i = 0; i < files.length; i++) {
					oldUrl.add(files[i]);
					System.out.println("files----name:" + files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			resultInfo.put("code", 10000);
			resultInfo.put("msg", "参数错误");
			return resultInfo;
		}

		// 删除原有的头像图片以及多上传的头像图片
		User u = userService.selectByPrimaryKey(userId);
		List<String> otherfile = new ArrayList<String>();
		if (u.getPicUrl() != null && !u.getPicUrl().equals("")) {
			otherfile.add(u.getPicUrl());
		}
		if (files.length > 1) {
			for (int i = 1; i < files.length; i++) {
				otherfile.add(files[i]);
			}
		}
		CommonMethod.deleteOldPic(request, folderName, otherfile);

		User user = new User();
		user.setId(userId);
		user.setPicUrl(files[0]);
		if (userService.updateByPrimaryKeySelective(user) > 0) {
			resultInfo.put("code", 200);
			resultInfo.put("msg", "请求成功");
			return resultInfo;
		} else {
			List<String> oldUrl = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				oldUrl.add(files[i]);
			}
			CommonMethod.deleteOldPic(request, folderName, oldUrl);
			resultInfo.put("code", 11001);
			resultInfo.put("msg", "修改失败");
			return resultInfo;
		}
	}

	/**
	 * 
	 * 修改用户昵称接口
	 * 
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "updateUserName", method = RequestMethod.POST)
	public JSONObject updateUserName(HttpServletRequest request, Integer userId, String name) throws Exception {
		JSONObject resultInfo = new JSONObject();
		if (userId == null || name == null || name == "") {
			resultInfo.put("code", 10000);
			resultInfo.put("msg", "参数错误");
			return resultInfo;
		}

		User user = new User();
		user.setId(userId);
		user.setName(name);
		if (userService.updateByPrimaryKeySelective(user) > 0) {
			resultInfo.put("code", 200);
			resultInfo.put("msg", "请求成功");
			return resultInfo;
		} else {
			resultInfo.put("code", 11001);
			resultInfo.put("msg", "修改失败");
			return resultInfo;
		}
	}

	/**
	 * 
	 * 获取用户未读消息个数
	 * 
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "getMessageNum", method = RequestMethod.GET)
	public JSONObject getMessageNum(HttpServletRequest request, Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}

		// 获取未读订单信息的个数
		Integer orderNum = userCouponsService.getNotReadOrderNUM(userId);
		// 获取未读活动信息的个数
		Integer ActivityNum = userCouponsService.getNotReadActivityNUM(userId);
		// 获取未读客服信息的个数
		Integer UserMessageNum = userCouponsService.getNotReadUserMessageNUM(userId);

		Map<String, Object> map1 = userCouponsService.getNewReadUserMessageTime(userId);

		Map<String, Object> map = new HashMap<>();

		map.put("UserMessageNum", UserMessageNum);

		if (map1 == null || UserMessageNum <= 0) {
			map.put("UserMessageTime", null);
		} else {
			map.put("UserMessageTime", map1.get("UserMessageTime"));
		}

		map.put("orderNum", orderNum);
		List<OrderStateDetail> list = orderStateDetailService.selectOrderMSGByUserId(userId);
		if (list != null && list.size() > 0) {
			map.put("orderNO", list.get(0).getOrderTable().getOrderNo());
			map.put("orderTime", list.get(0).getAddTime());
		} else {
			map.put("orderNO", null);
			map.put("orderTime", null);
		}

		map.put("ActivityNum", ActivityNum);
		List<ActivityMessage> list1 = activityMessageService.selectActivityMessageByUserId(userId);
		if (list1 != null && list1.size() > 0) {
			map.put("ActivityName", list1.get(0).getActivityInformation().getName());
			map.put("ActivityTime", list1.get(0).getGetTime());
		} else {
			map.put("ActivityName", null);
			map.put("ActivityTime", null);
		}

		if (orderNum != null && ActivityNum != null && map.get("UserMessageNum") != null) {
			result.put("resultData", map);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 11001);
			result.put("msg", "请求失败");
			return result;
		}
	}

	/**
	 * 
	 * 获取用户未订单消息列表
	 * 
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "getOrderStateDetailList", method = RequestMethod.GET)
	public JSONObject getOrderStateDetailList(HttpServletRequest request, Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}

		List<OrderStateDetail> list = orderStateDetailService.selectOrderMSGByUserId(userId);

		if (list != null) {
			result.put("resultData", list);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 11001);
			result.put("msg", "请求失败");
			return result;
		}
	}

	/**
	 * 
	 * 修改订单信息为已读状态
	 * 
	 * @param orderStateDetailId
	 *            订单消息id
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "updateOrderMSGStauts", method = RequestMethod.POST)
	public JSONObject updateOrderMSGStauts(HttpServletRequest request, Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}

		if (orderStateDetailService.updateStatusByUserId(userId) >= 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 11001);
			result.put("msg", "修改失败");
			return result;
		}
	}

	/**
	 * 
	 * 修改活动信息为已读状态
	 * 
	 * @param activityMSGId
	 *            活动消息id
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "updateActivityMSGStauts", method = RequestMethod.POST)
	public JSONObject updateActivityMSGStauts(HttpServletRequest request, Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		if (activityMessageService.updateByStatusByUserId(userId) >= 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 11001);
			result.put("msg", "修改失败");
			return result;
		}
	}

	/**
	 * 
	 * 删除所有消息
	 * 
	 * @param activityMSGId
	 *            活动消息id
	 * @param postUrl
	 *            请求地址
	 */
	@ResponseBody
	@RequestMapping(value = "deldectMessage", method = RequestMethod.POST)
	public JSONObject deldectMessageNum(HttpServletRequest request, Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		if (userCouponsService.deldectActivityMessage(userId) >= 0
				&& userCouponsService.deldectOrderMessage(userId) >= 0
				&& userCouponsService.deldectUserMessage(userId) >= 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 11001);
			result.put("msg", "删除失败");
			return result;
		}
	}
	
	/**
	 * 客服聊天   用户使用的发送订单接口
	 * @param request
	 * @param userId 用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getOrderMsgByUserId", method = RequestMethod.GET)
	public JSONObject selectOrderMsgByUserId(HttpServletRequest request, HttpServletResponse httpServletResponse, Integer userId) throws Exception {
		//解决跨域问题
        // 指定允许其他域名访问
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 响应类型
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET");
        // 响应头设置
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		
		JSONObject result = new JSONObject();
		if (userId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}

		List<ApiOrderMsg> list = userService.selectOrderMsgByUserId(userId);
		for(ApiOrderMsg api:list){
			api.setGoodsCoverUrl(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+api.getGoodsCoverUrl());		
		}

		if (list != null) {
			result.put("resultData", list);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 11001);
			result.put("msg", "请求失败");
			return result;
		}
	}
}
