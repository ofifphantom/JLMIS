package com.jl.app.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.Classification;
import com.jl.mis.service.ClassificationService;

/**
 * 分类信息API
 * @author 柳亚婷
 * @date  2017年12月18日  下午2:55:31
 * @Description 
 *
 */
@Controller
@RequestMapping("/classification/")
public class ClassificationApi{
	
	@Autowired
	private ClassificationService classificationService;
	
	/**
	 * 获取一级分类以及其所包含的二级分类
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getClassificationMsg", method = RequestMethod.GET)
	public JSONObject getClassificationMsg(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		List<Classification>  oneClassificationList=new ArrayList<>();
		List<Classification>  twoClassificationList=new ArrayList<>();
		//先查询一级分类信息
		oneClassificationList=classificationService.selectOneMsg();
		//再根据一级分类的信息查询二级分类的信息
		for(int i=0;i<oneClassificationList.size();i++){
			twoClassificationList=classificationService.selectTwoByOneId(oneClassificationList.get(i).getId());
			oneClassificationList.get(i).setTwoClassifications(twoClassificationList);
		}
		result.put("resultData", oneClassificationList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

}
