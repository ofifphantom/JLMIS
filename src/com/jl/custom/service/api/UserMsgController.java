package com.jl.custom.service.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.User;
import com.jl.mis.service.UserService1;

/**
 * 用户信息controller
 * @author 柳亚婷
 * @Description: TODO
 * @date: 2018年5月28日 下午7:36:36
 */
@Controller
@RequestMapping("/customService/User/")
public class UserMsgController {
	
	@Autowired
	UserService1 userService;
	
	/**
	 * 从数据库中获取所有商品详情信息
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "getUserMsg",method = RequestMethod.GET)
	public JSONObject getUserMsg(HttpServletRequest request, HttpServletResponse httpServletResponse,@RequestParam("userId") Integer userId) {
		//解决跨域问题
		// 指定允许其他域名访问
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 响应类型
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET");
        // 响应头设置
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		JSONObject jsonObject=new JSONObject();
		User user=userService.selectByPrimaryKey(userId);
		jsonObject.put("userName", user.getName());
		jsonObject.put("phone", user.getPhone());
		if(user.getIsVip()==0){
			jsonObject.put("isVip", "不是");
		}
		else{
			jsonObject.put("isVip", "是");
		}
		
		return jsonObject;
	}

}
