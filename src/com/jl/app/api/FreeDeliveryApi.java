package com.jl.app.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.FreeDelivery;
import com.jl.mis.model.FreeDeliveryAddress;
import com.jl.mis.service.FreeDeliveryService;

/**
 * 包邮设置
 * 
 * @author 柳亚婷
 * @date 2017年12月27日 下午4:18:19
 * @Description
 *
 */
@Controller
@RequestMapping("/freeDelivery/")
public class FreeDeliveryApi {
	
	@Autowired
	private FreeDeliveryService freeDeliveryService;

	/**
	 * 根据用户的收货人地址获取邮费
	 * 
	 * @param request
	 * @param provinceCode
	 *            省编号
	 * @param cityCode
	 *            城市编号 (若为直辖市(例：北京)，该处则传"0")
	 * @param countyCode
	 *            区/县编号
	 * @param ringCode
	 *            环编号 ("-1":表示全部，"2":二环，"3":三环，"4":四环，"5":五环，"6":六环，"7":七环)
	 * @param orderMoney
	 * 			     订单价格
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getPostage", method = RequestMethod.GET)
	public JSONObject getPostage(HttpServletRequest request,
			@RequestParam("provinceCode") String provinceCode, @RequestParam("cityCode") String cityCode,
			@RequestParam("countyCode") String countyCode, @RequestParam("ringCode") String ringCode, @RequestParam("orderMoney") Double orderMoney) {
		JSONObject result = new JSONObject();
		if ((provinceCode == null || "".equals(provinceCode)) || (cityCode == null || "".equals(cityCode))
				|| (countyCode == null || "".equals(countyCode))
				|| (!("-1").equals(ringCode) && !("2").equals(ringCode) && !("3").equals(ringCode)
						&& !("4").equals(ringCode) && !("5").equals(ringCode) && !("6").equals(ringCode)
						&& !("7").equals(ringCode))||(orderMoney==null || orderMoney<0)) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		FreeDeliveryAddress freeDeliveryAddress=new FreeDeliveryAddress();
		freeDeliveryAddress.setProvince(provinceCode);
		freeDeliveryAddress.setCounty(countyCode);
		if(!"0".equals(cityCode)){
			freeDeliveryAddress.setCity(cityCode);
		}
		FreeDelivery freeDelivery=freeDeliveryService.selectFreeDeliveryAndAddressMsgByCode(freeDeliveryAddress);
		//若查到有包邮地址
		//根据用户选择的环，以及订单总额来判断邮费的多少。若订单价格满足包邮的金额，且环数在包邮范围内，此时包邮。若订单不满足，但环数在包邮范围内，此时为基础邮费。
		//若用户选择的环不在包邮范围内，显示到付。
		if(freeDelivery!=null){
			if(freeDelivery.getFreeDeliveryAddresses().size()>=0){
				int ring=Integer.parseInt(freeDelivery.getFreeDeliveryAddresses().get(0).getRing());
				int ringCodeParameter=Integer.parseInt(ringCode);
				Double deliveryMoney=freeDelivery.getFreeDeliveryMoney();
				//ring==-1表示该区的所有地方都在包邮范围中
				if(ring==-1){
					//订单金额大于或等于包邮金额，则免运费
					if(orderMoney>=deliveryMoney){
						result.put("resultData", 0);
						result.put("code", 200);
						result.put("msg", "请求成功");
						return result;
					}
					//订单金额小于包邮金额，则有基础邮费
					else{
						result.put("resultData", freeDelivery.getBasePostage());
						result.put("code", 200);
						result.put("msg", "请求成功");
						return result;
					}
				}
				//ring为其他值则表示只有部分地区包邮，此时需要比较大小
				else{
					//若用户选的环小于或等于包邮的环，说明其地址在包邮的范围内
					if(ringCodeParameter<=ring){
						//订单金额大于或等于包邮金额，则免运费
						if(orderMoney>=deliveryMoney){
							result.put("resultData", 0);
							result.put("code", 200);
							result.put("msg", "请求成功");
							return result;
						}
						//订单金额小于包邮金额，则有基础邮费
						else{
							result.put("resultData", freeDelivery.getBasePostage());
							result.put("code", 200);
							result.put("msg", "请求成功");
							return result;
						}
					}
					//说明不在包邮的范围内，此时是邮费到付
					else{
						result.put("code", 13001);
						result.put("msg", "邮费到付");
						return result;
					}
				}
			}
			else{
				result.put("code", 13001);
				result.put("msg", "邮费到付");
				return result;
			}
			
		}
		//若未查到包邮地址，则说明该地不包邮，此时显示到付。
		else{
			result.put("code", 13001);
			result.put("msg", "邮费到付");
			return result;
		}
	}

}
