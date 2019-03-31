package com.jl.custom.service.api;

import java.io.UnsupportedEncodingException;
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
import com.jl.mis.service.GoodsSpecificationDetailsService;

/**
 * 客服系统所需的商品接口
 * @author 柳亚婷
 * @Description: TODO
 * @date: 2018年5月28日 下午7:09:35
 */
@Controller
@RequestMapping("/customService/goodsInformation/")
public class GoodsController {
	
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	
	/**
	 * 从数据库中获取所有商品详情信息
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsMsg",method = RequestMethod.GET)
	public ArrayList<Map<String,Object>> getGoodsMsg(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("goodsName") String goodsName) {
		//解决跨域问题
        // 指定允许其他域名访问
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 响应类型
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET");
        // 响应头设置
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

		System.out.println("goodsName:"+goodsName);
		if("null".equals(goodsName)){
			goodsName=null;
		}
		if("“”".equals(goodsName)||"‘’".equals(goodsName)){
			goodsName="";
		}
		ArrayList<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<GoodsSpecificationDetails> goodsSpecificationDetails=goodsSpecificationDetailsService.selectGoodsMsgToCustomService(goodsName);
		Map<String,Object> map;
		for(GoodsSpecificationDetails gsd:goodsSpecificationDetails){
			map=new HashMap<>();
			map.put("goodsId", gsd.getGoodsDetails().getId());
			map.put("goodsName", gsd.getGoodsDetails().getName());
			map.put("goodsSpecificationId", gsd.getId());
			map.put("goodsIdentifier", gsd.getIdentifier());
			map.put("goodsSpecificationName", gsd.getSpecifications());
			map.put("goodsPrice", gsd.getPrice());
			map.put("isPresell", gsd.getGxcGoodsState());
			list.add(map);
		}
		return list;
	}
	
}
