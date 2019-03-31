package com.jl.mis.utils;


/**
 * 员工Code等获取
 * @author wk
 * @since 2017年1月20日13:21:57
 */
public class SundryCodeUtil {
	
	
	/**
	 * 流水编号获取规则
	 * @param Code类型代码 
	 * @return 流水编号
	 */
	public static String getPosCode(int code){
		
		String company = "JL";
		
		String result =  null;
		switch (code) {
		case Constants.CODE_YHQ:
			result =company + Constants.NO_YHQ + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_ONECLASSIFICATION:
			result =company + Constants.NO_ONECLASSIFICATION + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_TWOCLASSIFICATION:
			result =company + Constants.NO_TWOCLASSIFICATION + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_HD:
			result =company + Constants.NO_HD + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_ADMIN:
			result =company + Constants.NO_ADMIN + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_GOODS:
			result =company + Constants.NO_GOODS + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_ADVERTISEMENT:
			result =company + Constants.NO_ADVERTISEMENT + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_USER:
			result =company + Constants.NO_USER + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_ORDER:
			result =company + Constants.NO_ORDER + DateUtil.getCurDateTime();
			break;
		case Constants.CODE_INVOICE:
			result =company + Constants.NO_INVOICE + DateUtil.getCurDateTime();
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * 获取管理员所处的用户组名称
	 * @param userGroupNo 用户组编号
	 * @return
	 */
	public static String getUserGroup(int userGroupNo){
		String result =  null;
		switch (userGroupNo) {
		case Constants.USER_GROUP_SUPER_ADMINISTRATOR:
			result ="超级管理员";
			break;
		case Constants.USER_GROUP_ADMINISTRATOR:
			result ="管理员";
			break;
		case Constants.USER_GROUP_OPERATOR:
			result ="操作员";
			break;
		default:
			break;
		}
		return result;
	}
	
	/**
	 * 获取活动类型名称
	 * @param activityTypeNo 活动类型编号
	 * @return
	 */
	public static String getActivityTypeName(int activityTypeNo){
		String result =  null;
		switch (activityTypeNo) {
		case Constants.TYPE_HD_ZK:
			result ="折扣";
			break;
		case Constants.TYPE_HD_TG:
			result ="团购";
			break;
		case Constants.TYPE_HD_MS:
			result ="秒杀";
			break;
		case Constants.TYPE_HD_LJ:
			result ="立减";
			break;
		case Constants.TYPE_HD_MJ:
			result ="满减";
			break;
		case Constants.TYPE_HD_YS:
			result ="预售";
			break;
		default:
			break;
		}
		return result;
	}
	
	
	
	
}
