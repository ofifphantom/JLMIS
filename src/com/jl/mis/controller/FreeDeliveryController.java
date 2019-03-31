package com.jl.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.FreeDelivery;
import com.jl.mis.model.FreeDeliveryAddress;
import com.jl.mis.service.FreeDeliveryAddressService;
import com.jl.mis.service.FreeDeliveryService;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.GetSessionUtil;

import net.sf.json.JSONArray;

/**
 * 包邮设置controller
 * @author 柳亚婷
 * @date  2017年11月28日  下午7:34:45
 * @Description 
 *
 */
@Controller
@RequestMapping("/backgroundConfiguration/postage/freedelivery/")
public class FreeDeliveryController extends BaseController{
	
	@Autowired
	private FreeDeliveryAddressService freeDeliveryAddressService;
	@Autowired
	private FreeDeliveryService freeDeliveryService;
	
	/**
	 * 进入包邮设置页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}

		return "/junlin/mis_jsp/backgroundConfiguration/postage/postage";
	}
	
	/**
	 * 进入包邮设置页面时获取该页面的信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getFreeDeliveryMsg")
	public FreeDelivery getFreeDeliveryMsg(HttpServletRequest request){
		return freeDeliveryService.selectFreeDeliveryAndAddressMsg();
	}
	
	/**
	 * 保存/更新邮费设置的值
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addOrUpdateFreeDeliveryMsg", method = RequestMethod.POST)
	public JSONObject addOrUpdateFreeDeliveryMsg(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		//从数据库中获取包邮信息，若库中有包邮信息，则以下只做更新操作，若库中没有包邮信息，则做添加操作。
		FreeDelivery freeDeliveryMsg=freeDeliveryService.selectFreeDeliveryAndAddressMsg();
		boolean isAdd=false; //是否执行添加操作
		if(freeDeliveryMsg!=null){
			isAdd=false;
		}
		else{
			isAdd=true;
		}
		int fdResult=-1;//对FreeDelivery表操作的结果
		//从前台获取传入的地址，转换成json
		String freeDeliveryAddressStr=request.getParameter("freeDeliveryAddress");
		JSONArray jsonArray = JSONArray.fromObject(freeDeliveryAddressStr);
		FreeDelivery freeDelivery=new FreeDelivery();
		FreeDeliveryAddress freeDeliveryAddress =new FreeDeliveryAddress();
		List<FreeDeliveryAddress> freeDeliveryAddressList=new ArrayList<>();
		freeDelivery.setFreeDeliveryMoney(Double.parseDouble(request.getParameter("freeDeliveryMoney")));
		freeDelivery.setBasePostage(Double.parseDouble(request.getParameter("basePostage")));
		//执行添加操作
		if(isAdd){
			fdResult=freeDeliveryService.insert(freeDelivery);
		}
		//执行更新操作
		else{
			freeDelivery.setId(freeDeliveryMsg.getId());
			fdResult=freeDeliveryService.updateByPrimaryKeySelective(freeDelivery);
		}
		if(fdResult>0){
			//说明库里面有包邮设置对应的地址信息，此时先删除旧信息，再重新添加
			if(!isAdd){
				List<FreeDeliveryAddress> freeDeliveryAddresses=freeDeliveryMsg.getFreeDeliveryAddresses();
				List<Integer> primaryKeys=new ArrayList<>();
				if(freeDeliveryAddresses!=null&&freeDeliveryAddresses.size()>0){
					for(FreeDeliveryAddress fda:freeDeliveryAddresses){
						primaryKeys.add(fda.getId());
					}
					freeDeliveryAddressService.deleteBatchByPrimaryKey(primaryKeys);
				}
			}
			// 添加操作人编号，从session中获取
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 添加操作时间
			Date date = new Date();
			//便利json的值，把json的值转换成对象
			for(int i=0;i<jsonArray.size();i++){
				freeDeliveryAddress=new FreeDeliveryAddress();	
				
				freeDeliveryAddress.setFreeDeliveryId(freeDelivery.getId());
				freeDeliveryAddress.setOperatorIdentifier(userIdentifier);
				freeDeliveryAddress.setOperatorTime(date);
				
				net.sf.json.JSONObject object= (net.sf.json.JSONObject) jsonArray.get(i);
				freeDeliveryAddress.setProvince(object.getString("province"));
				freeDeliveryAddress.setRing(object.getString("ring"));
				if(object.has("city")){
					freeDeliveryAddress.setCity(object.getString("city"));
				}
				else{
					freeDeliveryAddress.setCity("");
				}
				net.sf.json.JSONObject area=object.getJSONObject("area");
				freeDeliveryAddress.setCounty(area.getString("value"));
				freeDeliveryAddressList.add(freeDeliveryAddress);
			}
			if(freeDeliveryAddressService.insertBatch(freeDeliveryAddressList)>0){
				rmsg.put("success", true);
				rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
				return rmsg;
			}
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);
		return rmsg;
	}


}
