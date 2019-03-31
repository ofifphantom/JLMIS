package com.jl.mis.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @since2017年1月20日10:13:47
 * @author wk
 * 公共的Session获取类
 */
public class GetSessionUtil {
	
	/**
	 * 获取当前用户ID
	 * @param request
	 * @return 用户ID
	 */
	public static int GetSessionUserId(HttpServletRequest request){
		int sessionUserId = (int) request.getSession().getAttribute("userId");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户所属公司ID
	 * @param request
	 * @return 公司ID
	 */
	public static int GetSessionUserCompanyId(HttpServletRequest request){
		int sessionUserId = (int) request.getSession().getAttribute("companyId");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户角色ID
	 * @param request
	 * @return 角色ID
	 */
	public static int GetSessionUserRoleId(HttpServletRequest request){
		int sessionUserId = (int) request.getSession().getAttribute("roleId");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户用户名
	 * @param request
	 * @return 用户用户名
	 */
	public static String GetSessionUserName(HttpServletRequest request){
		String sessionUserId = (String) request.getSession().getAttribute("userName");
		return sessionUserId;
	}
	
	/**
	 * 获取当前用户登录名
	 * @param request
	 * @return 用户登录名
	 */
	public static String GetSessionUserLoginNameId(HttpServletRequest request){
		String sessionUserId = (String) request.getSession().getAttribute("loginName");
		return sessionUserId;
	}
	
	/**
	 * 获取当前登录人的编号
	 * @param request
	 * @return 登录人编号
	 */
	public static String GetSessionUserIdentifier(HttpServletRequest request){
		String sessionUserIdentifier = (String) request.getSession().getAttribute("identifier");
		return sessionUserIdentifier;
	}
	
	
	
	
}
