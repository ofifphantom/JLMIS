package com.jl.mis.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jl.mis.utils.Constants;
import com.jl.mis.utils.ResultInfo;
import com.jl.mis.utils.SHAUtil;

public class BaseController {

	public static ResultInfo info = new ResultInfo();
	
	/**
	 * 获取当前用户所属企业
	 * */
	public int getCompanyId(HttpServletRequest request){
		int companyId = (int)request.getSession().getAttribute("companyId");
		return companyId;
	}
	/**
	 * 验证session是否失效
	 */
	public boolean checkSession(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(!session.getAttributeNames().hasMoreElements()){
			try {
				SHAUtil.shaEncode("1");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Constants.FAILURE;
		}else{
			return Constants.SUCCESS;
		}
	};
	
}
