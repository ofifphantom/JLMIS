package com.jl.app.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.ActivityMessage;
import com.jl.mis.model.VersionMessage;
import com.jl.mis.service.VersionMessageService;

/**
 * 版本信息api
 * @author 柳亚婷
 * @date  2018年3月14日  下午2:53:51
 * @Description 
 *
 */
@Controller
@RequestMapping("/versionMessage/")
public class VersionMessageApi extends BaseController{
	
	@Autowired
	VersionMessageService versionMessageService;
	
	/**
	 * 获取新版本信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getNewVersionMessage", method = RequestMethod.GET)
	public JSONObject getNewVersionMessage(HttpServletRequest request,Integer isAndroidOriOS) throws Exception {
		JSONObject result = new JSONObject();
		if(isAndroidOriOS==null || (isAndroidOriOS!=0 && isAndroidOriOS!=1)){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		List<VersionMessage> versionMessages=new ArrayList<>();
		VersionMessage versionMessage=new VersionMessage();
		versionMessage.setIsAndroidOriOS(isAndroidOriOS);
	
		versionMessages=versionMessageService.selectAllMessageByIsAndroidOriOS(versionMessage);
		if(versionMessages.size()>0){
			result.put("resultData", versionMessages.get(versionMessages.size()-1));
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		else{
			result.put("code", 18001);
			result.put("msg", "版本信息不存在");
			return result;
		}	
	}
	
	/**
	 * 保存新版本信息
	 * @param request
	 * @param versionCode
	 * @param versionName
	 * @param isMustUpdate
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertNewVersionMessage", method = RequestMethod.POST)
	public JSONObject insertNewVersionMessage(HttpServletRequest request,Integer versionCode,String versionName,Integer isMustUpdate,Integer isAndroidOriOS,String description) throws Exception {
		JSONObject result = new JSONObject();
		//判断参数的有效性
		if((versionCode==null||versionCode<=0)||(versionName==null||"".equals(versionName))
				||(isMustUpdate==null||(isMustUpdate!=0&&isMustUpdate!=1))||isAndroidOriOS==null||(isAndroidOriOS!=0&&isAndroidOriOS!=1)){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		VersionMessage versionMessage=new VersionMessage();
		versionMessage.setIsMustUpdate(isMustUpdate);
		versionMessage.setVersionCode(versionCode);
		versionMessage.setVersionName(versionName);
		versionMessage.setDescription(description);
		versionMessage.setIsAndroidOriOS(isAndroidOriOS);
	
		//查询是否有重复
		VersionMessage messages=versionMessageService.selectByVersionName(versionMessage);
		//如果没有重复，则进行保存
		if(messages==null){
			if(versionMessageService.insertSelective(versionMessage)>0){
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			}
			else{
				result.put("code", 00000);
				result.put("msg", "保存失败");
				return result;
			}	
		}
		else{
			result.put("code", 00000);
			result.put("msg", "该版本名已存在，请勿重新保存。");
			return result;
		}
		
		
	}

}
