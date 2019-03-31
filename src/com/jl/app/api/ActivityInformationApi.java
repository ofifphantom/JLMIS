package com.jl.app.api;


import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.ActivityMessage;
import com.jl.mis.model.ActivityMsg;
import com.jl.mis.model.Classification;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.model.GoodsMsg;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.ParamForGetActivitysAndCouponsByGoodsMsg;
import com.jl.mis.model.User;
import com.jl.mis.model.UserCouponMsg;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.ActiveCouponService;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.ActivityMessageService;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsDisplayPictureService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.UserCouponsService;
import com.jl.mis.service.UserService1;

/**
 * 活动信息API
 * @author 景雅倩
 * @date  2017年12月18日  17:09:45
 * @Description 
 *
 */
@Controller
@RequestMapping("/activityInformation/")
public class ActivityInformationApi extends BaseController{
	
	@Autowired
	private ActivityMessageService activityMessageService;
	
	@Autowired
	private ActivityInformationService activityInformationService;
	
	@Autowired
	private GoodsActivityService goodsActivityService;

	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	
	@Autowired
	private GoodsDisplayPictureService goodsDisplayPictureService;
	
	@Autowired
	private ActiveCouponService activeCouponService;
	
	@Autowired
	private UserService1 userService;
	
	@Autowired
	private UserCouponsService userCouponsService;
	
	/**
	 * 根据用户id获取活动消息
	 * @param request
	 * @param userId 用户id
	 * @return 用户活动消息列表
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getActivityMessageByUserId", method = RequestMethod.GET)
	public JSONObject getClassificationMsg(HttpServletRequest request,@RequestParam("userId") Integer userId) throws Exception {
		
		if(userId==null){
			JSONObject result = new JSONObject();
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		
		JSONObject result = new JSONObject();
		
		List<ActivityMessage> activityMessageList = activityMessageService.selectActivityMessageByUserId(userId);
		int unreadNum = activityMessageService.selectUnreadActivityMessageNumByUserId(userId);
		
		JSONObject resultInfo = new JSONObject();
		resultInfo.put("activityMessageList", activityMessageList);
		resultInfo.put("unreadNum", unreadNum);
		
		result.put("resultData", resultInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	
	/**
	 * 根据活动信息ID获取活动信息、活动相关商品列表信息、活动相关优惠券信息
	 * 活动消息的点击事件接口
	 * @param request
	 * @param activityInformationId  活动id
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "getActivityInformationById", method = RequestMethod.GET)
	public JSONObject getActivityInformationById(HttpServletRequest request,@RequestParam("activityInformationId") Integer activityInformationId) throws Exception {
		
		JSONObject result = new JSONObject();
		if(activityInformationId==null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		ActivityInformation activityInformation = activityInformationService.selectByPrimaryKey(activityInformationId);
		if(activityInformation == null){
			result.put("code", 70001);
			result.put("msg", "活动信息不存在");
			return result;	
		}
		
		JSONObject resultInfo = new JSONObject();
		List<GoodsDetails> goodsList = new ArrayList<>();
		//获取活动相关优惠券信息
		List<CouponInformation> couponInformation = activeCouponService.getMsgByActivityInformationId(activityInformationId);
		
		//获取该活动下的商品列表
		List<GoodsActivity> goodsActivityList = goodsActivityService.getMsgByActivityInformationId(activityInformationId);
		if(goodsActivityList!=null && goodsActivityList.size()>0){
			List<Integer> gsdGoodsIdList = new ArrayList<>();
			for (int i = 0; i < goodsActivityList.size(); i++) {
				GoodsActivity ga = goodsActivityList.get(i);
				int claOrGsdOrGdId = ga.getGoodsId(); 
				//List<GoodsDetails> goodsList2 = new ArrayList<>();//中间列表
				switch (ga.getState()) {
				case 0://规格  将商品加入商品列表
					int goodsId = goodsSpecificationDetailsService.selectByPrimaryKey(claOrGsdOrGdId).getGoodsId();
					if(!gsdGoodsIdList.contains(goodsId)){//去重
						gsdGoodsIdList.add(goodsId);
					}
					break;
				case 1://商品  将商品加入商品列表
					JSONObject object2 =  getGoodsDetailMsgByGoodsId(request, claOrGsdOrGdId);
					int code2 = object2.getIntValue("code");
					if(code2==200){
						GoodsDetails goodsDetails = (GoodsDetails) object2.get("resultData");
						goodsList.add(goodsDetails);
					}
					break;
				case 2://分类  遍历该分类下的所有商品并加入商品列表
					
					
					//------------------------START对私有方法的处理START-----------------------------
					//判断分类id是一级分类还是二级分类  如果是一级分类  遍历出该一级分类下的二级分类id列表  然后根据二级分类id查询 最后将结果集合起来
					int parerntId = classificationService.selectByPrimaryKey(claOrGsdOrGdId).getParentId();
					if(parerntId == 0){
						List<Classification> twoClaList = classificationService.selectTwoByOneId(claOrGsdOrGdId);
						for (int j = 0; j < twoClaList.size(); j++) {
							int twoClaId = twoClaList.get(j).getId();
							JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",twoClaId);
							int code = object.getIntValue("code");
						
							if(code==200){
								List<GoodsDetails> goodsList1=(List<GoodsDetails>) object.get("resultData");
								goodsList.addAll(goodsList1);
							}
							
						}
						
						
					}else{
						JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",claOrGsdOrGdId);
						int code = object.getIntValue("code");
						if(code==200){
							List<GoodsDetails> goodsList1=(List<GoodsDetails>) object.get("resultData");
							goodsList.addAll(goodsList1);
						}
						
					}
					//------------------------END对私有方法的处理END-----------------------------
					break;


				default:
					break;
				}
			}
			
			//处理类型为规格的活动商品
			if(gsdGoodsIdList.size()>0){
				int goodsId,code;
				for (int j = 0; j < gsdGoodsIdList.size(); j++) {
					goodsId = gsdGoodsIdList.get(j);
					JSONObject object2 =  getGoodsDetailMsgByGoodsId(request, goodsId);
					code = object2.getIntValue("code");
					if(code==200){
						GoodsDetails goodsDetails = (GoodsDetails) object2.get("resultData");
						goodsList.add(goodsDetails);
					}
					
				}
				
			}
			
		}
		
		resultInfo.put("activityInformation", activityInformation);
		resultInfo.put("couponInformation", couponInformation);
		resultInfo.put("goods", goodsList);
		
		
		result.put("resultData", resultInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	
	/**
	 * 根据商品信息列表获取活动列表和优惠券列表
	 * @param request
	 * @param param 商品信息列表&用户id
	 * @return resultData.activitys:经过排序的活动列表
	 *         resultData.coupons:经过排序的优惠券列表
	 * @throws CloneNotSupportedException 
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getActivitysAndCouponsByGoodsMsg", method = RequestMethod.POST)
	public JSONObject getActivitysAndCouponsByGoodsMsg(HttpServletRequest request,@RequestBody ParamForGetActivitysAndCouponsByGoodsMsg param ) throws CloneNotSupportedException{
		JSONObject result = new JSONObject();
		JSONObject resultInfo = new JSONObject();
		
		//获取用户id
		Integer userId = param.getUserId();
		//获取商品信息
		List<GoodsMsg> goodsMsgList = param.getGoodsMsgList();
		//判断参数是否合法
		if(userId == null ){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}else if(goodsMsgList==null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}else if(userId != null){//判断用户信息是否存在
			User user = userService.selectByPrimaryKey(userId);
			if(user == null){
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}else if(user.getAdministratorOrUser()==0){//判断用户是否是管理员
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}else if(user.getIsEffective() == 0 ){//判断用户是否被禁用
				result.put("code", 11002);
				result.put("msg", "帐号被禁用");
				return result;
			}
		}
		
		
		//存放规格id
		List<Integer> goodsSpeIdList = new ArrayList<>();
		
		//所有商品总价
		double amount = 0 ;
		
		//通过验证
		//一、对活动列表进行处理
		//List<ActivityInformation> returnActivityList=new ArrayList<>();
		//1. 合并所有活动列表  得到无序的重复的活动列表
		List<ActivityInformation> allActivityList = new ArrayList<>();//存放商品信息经过合并的所有的活动信息(没有经过去重)
		List<ActivityInformation> goodsActivityList;
		List<ActivityInformation> goodsSpeActivityList;
		
		List<ActivityMsg> activityMsgList = new ArrayList<>();
		
		for (int i = 0; i < goodsMsgList.size(); i++) {
			GoodsMsg item = goodsMsgList.get(i);
			Integer goodsSpeId = item.getGoodsSpeId();
			Integer number = item.getNumber();
			Double unitPrice = item.getUnitPrice();
			if(number ==null || unitPrice == null || goodsSpeId ==null){//验证单个商品的数量、单价、分类id、商品id、商品规格id是否合法
				result.put("code", 10000);
				result.put("msg", "参数错误");
				return result;
			}
			
			goodsSpeIdList.add(goodsSpeId);
		
			//计算所有商品总价 便于优惠券处理时使用
			double totalPrice = unitPrice * number;
			amount+=totalPrice;
			
			
			goodsActivityList = item.getGoodsActivityList();
			goodsSpeActivityList = item.getGoodsSpeActivityList();
			if(goodsActivityList!=null && goodsSpeActivityList!=null){
					goodsActivityList.addAll(goodsSpeActivityList);
					allActivityList.addAll(goodsActivityList);
			}else if(goodsActivityList==null && goodsSpeActivityList!=null){
				allActivityList.addAll(goodsSpeActivityList);
			}else if(goodsActivityList!=null && goodsSpeActivityList==null){
				allActivityList.addAll(goodsActivityList);
			}

		}
		//2.对活动列表进行去重
		int id;
		List<Integer> idList = new ArrayList<>();//存放活动id
		if(allActivityList.size()>0){
			for (int i = 0; i < allActivityList.size(); i++) {
				id = allActivityList.get(i).getId();
				if(idList.contains(id)){
					allActivityList.remove(i);
					i--;
				}else{
					idList.add(id);
				}
			}
		}
		//returnActivityList.addAll(allActivityList) ;
		//3.对活动列表进行排序
	
		for (int i = 0; i < allActivityList.size(); i++) {
			
			double activityAfterDiscountMoney=0, discount=0;
			//存放规格id  ActivityMsg成员变量
			List<Integer> goodsSpeIdListForActivityMsg = new ArrayList<>();
			int maxNum = 0;
			ActivityInformation activityInformation = allActivityList.get(i);//从去重后的活动列表中获取一个活动
			int activityInformationId = activityInformation.getId();
			int activityInformationType = activityInformation.getActivityType();
			
			switch (activityInformationType) {
			case 0://折扣（多商品）
				discount = activityInformation.getDiscount();//折扣
				maxNum = activityInformation.getMaxNum();
				
				for (int j = 0; j < goodsMsgList.size(); j++) {
					GoodsMsg goodsMsg = goodsMsgList.get(j);//获取一个商品信息
					int goodsSpeId = goodsMsg.getGoodsSpeId();
					goodsActivityList = goodsMsg.getGoodsActivityList();//获取商品活动列表
					goodsSpeActivityList = goodsMsg.getGoodsSpeActivityList();//获取商品规格活动列表
					double unitPrice = goodsMsg.getUnitPrice();
					int number = goodsMsg.getNumber();
					double price = unitPrice * discount;
					if(goodsActivityList!=null && goodsActivityList.size()>0){
						for (int k = 0; k < goodsActivityList.size(); k++) {
							int goodsActivityId = goodsActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									if(number>maxNum){//判断购买量是否满足最大购买量
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-price)*maxNum;
										
									}else{
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-price)*number;
									}
								}
								break;
							}
						}
					}
					if(goodsSpeActivityList!=null && goodsSpeActivityList.size()>0){
						for (int k = 0; k < goodsSpeActivityList.size(); k++) {
							int goodsActivityId = goodsSpeActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									if(number>maxNum){//判断购买量是否满足最大购买量
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-price)*maxNum;
										
									}else{
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-price)*number;
									}
								}
								break;
							}
						}
					}
				}
				break;

			case 1://团购
				discount = activityInformation.getDiscount();//团购价
				maxNum = activityInformation.getMaxNum();
				for (int j = 0; j < goodsMsgList.size(); j++) {
					GoodsMsg goodsMsg = goodsMsgList.get(j);//获取一个商品信息
					int goodsSpeId = goodsMsg.getGoodsSpeId();
					goodsSpeActivityList = goodsMsg.getGoodsSpeActivityList();//获取商品规格活动列表
					double unitPrice = goodsMsg.getUnitPrice();
					int number = goodsMsg.getNumber();
					//double price = unitPrice * number;
					
					if(goodsSpeActivityList!=null && goodsSpeActivityList.size()>0){
						for (int k = 0; k < goodsSpeActivityList.size(); k++) {
							int goodsActivityId = goodsSpeActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									if(number>maxNum){//判断购买量是否满足最大购买量
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-discount)*maxNum;
										
									}else{
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-discount)*number;
									}
								}
								break;
							}
						}
					}
				}							
				break;
			
			case 2://秒杀
				discount = activityInformation.getDiscount();//秒杀价
				maxNum = activityInformation.getMaxNum();
				for (int j = 0; j < goodsMsgList.size(); j++) {
					GoodsMsg goodsMsg = goodsMsgList.get(j);//获取一个商品信息
					int goodsSpeId = goodsMsg.getGoodsSpeId();
					goodsSpeActivityList = goodsMsg.getGoodsSpeActivityList();//获取商品规格活动列表
					double unitPrice = goodsMsg.getUnitPrice();
					int number = goodsMsg.getNumber();
					//double price = unitPrice * number;
					if(goodsSpeActivityList!=null && goodsSpeActivityList.size()>0){
						for (int k = 0; k < goodsSpeActivityList.size(); k++) {
							int goodsActivityId = goodsSpeActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									if(number>maxNum){//判断购买量是否满足最大购买量
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-discount)*maxNum;
										
									}else{
										//该商品优惠的价格
										activityAfterDiscountMoney += (unitPrice-discount)*number;
									}
								}
								break;
							}
						}
					}
				}
				break;
			
			case 3://立减
				discount = activityInformation.getDiscount();//立减金额
				maxNum = activityInformation.getMaxNum();
				for (int j = 0; j < goodsMsgList.size(); j++) {
					GoodsMsg goodsMsg = goodsMsgList.get(j);//获取一个商品信息
					int goodsSpeId = goodsMsg.getGoodsSpeId();
					goodsSpeActivityList = goodsMsg.getGoodsSpeActivityList();//获取商品规格活动列表
					double unitPrice = goodsMsg.getUnitPrice();
					int number = goodsMsg.getNumber();
					//double price = unitPrice * number;
					
					if(goodsSpeActivityList!=null && goodsSpeActivityList.size()>0){
						for (int k = 0; k < goodsSpeActivityList.size(); k++) {
							int goodsActivityId = goodsSpeActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									if(number>maxNum){//判断购买量是否满足最大购买量
										//该商品优惠的价格
										activityAfterDiscountMoney += discount*maxNum;
										
									}else{
										//该商品优惠的价格
										activityAfterDiscountMoney += discount*number;
									}
								}
								break;
							}
						}
					}
				}
				break;
				
			case 4://满减（多商品）
				
				for (int j = 0; j < goodsMsgList.size(); j++) {
					GoodsMsg goodsMsg = goodsMsgList.get(j);//获取一个商品信息
					int goodsSpeId = goodsMsg.getGoodsSpeId();
					goodsActivityList = goodsMsg.getGoodsActivityList();//获取商品活动列表
					goodsSpeActivityList = goodsMsg.getGoodsSpeActivityList();//获取商品规格活动列表
					double unitPrice = goodsMsg.getUnitPrice();
					int number = goodsMsg.getNumber();
					double price = unitPrice * number;
					if(goodsActivityList!=null && goodsActivityList.size()>0){
						for (int k = 0; k < goodsActivityList.size(); k++) {
							int goodsActivityId = goodsActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									activityAfterDiscountMoney += price;
								}
								break;
							}
						}
					}
					if(goodsSpeActivityList!=null && goodsSpeActivityList.size()>0){
						for (int k = 0; k < goodsSpeActivityList.size(); k++) {
							int goodsActivityId = goodsSpeActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
									activityAfterDiscountMoney += price;
								}
								
								break;
							}
						}
					}
				}
				
				break;
			case 5://预售（多商品）
				for (int j = 0; j < goodsMsgList.size(); j++) {
					GoodsMsg goodsMsg = goodsMsgList.get(j);//获取一个商品信息
					int goodsSpeId = goodsMsg.getGoodsSpeId();
					goodsActivityList = goodsMsg.getGoodsActivityList();//获取商品活动列表
					goodsSpeActivityList = goodsMsg.getGoodsSpeActivityList();//获取商品规格活动列表
					double unitPrice = goodsMsg.getUnitPrice();
					int number = goodsMsg.getNumber();
					double price = unitPrice * number;
					activityAfterDiscountMoney += price;
					if(goodsActivityList!=null && goodsActivityList.size()>0){
						for (int k = 0; k < goodsActivityList.size(); k++) {
							int goodsActivityId = goodsActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
								}
								break;
							}
						}
					}
					if(goodsSpeActivityList!=null && goodsSpeActivityList.size()>0){
						for (int k = 0; k < goodsSpeActivityList.size(); k++) {
							int goodsActivityId = goodsSpeActivityList.get(k).getId();
							if(goodsActivityId == activityInformationId){//该商品参与了该活动
								if(!goodsSpeIdListForActivityMsg.contains(goodsSpeId)){
									goodsSpeIdListForActivityMsg.add(goodsSpeId);
								}
								break;
							}
						}
					}
				}
				break;
			default:
				break;
			}
			
			if(activityInformationType == 4){
				discount = activityInformation.getDiscount();//减额
				double limitPrice = activityInformation.getPrice();//门槛价
				
				if(activityAfterDiscountMoney>=limitPrice){//商品总价格满足门槛价
					activityAfterDiscountMoney = amount-discount;
					
					ActivityMsg activityMsg = new ActivityMsg();
					activityMsg.setActivityInformation(activityInformation);
					activityMsg.setAfterDiscountMoney(activityAfterDiscountMoney);
					activityMsg.setGoodsSpeIdList(goodsSpeIdListForActivityMsg);
					activityMsgList.add(activityMsg);
				}
			}else{
				ActivityMsg activityMsg = new ActivityMsg();
				activityMsg.setActivityInformation(activityInformation);
				activityMsg.setAfterDiscountMoney(amount-activityAfterDiscountMoney);
				activityMsg.setGoodsSpeIdList(goodsSpeIdListForActivityMsg);
				activityMsgList.add(activityMsg);
			}
			
			
			
		}
		
		
		
		
		
		//二、对优惠券列表进行处理
		//1.获取用户可用的所有优惠券
		List<UserCoupons> couponList = userCouponsService.selectUsableCouponByUserId(userId);
		//2.遍历优惠券列表 
		List<UserCouponMsg> userCouponMsgList = new ArrayList<>();//存放可使用的优惠券列表  
		for (UserCoupons userCoupons : couponList) {

			//查询该优惠券是否有所属活动
			int couponId = userCoupons.getCouponInformationId();
			CouponInformation couponInformation = userCoupons.getCouponInformation();
			double useLimit = couponInformation.getUseLimit();
			double price = couponInformation.getPrice();
			List<ActiveCoupon> activeCouponList = activeCouponService.selectByCouponId(couponId);//获取该优惠券所属的活动列表
			if(activeCouponList!=null && activeCouponList.size()>0){//有所属活动  
				List<Integer> gsdIdList = new ArrayList<>();//存放该优惠券可用的商品规格id
				double totalMoney = 0;
				double discountMoney;
				for (int i = 0; i < activeCouponList.size(); i++) {
					
					//获取该优惠券所属的活动id
					int activityInformationId = activeCouponList.get(i).getActivityInformationId();
					
					//查询该优惠券关联的商品列表
					List<GoodsActivity> getedGoodsActivityList = goodsActivityService.getMsgByActivityInformationId(activityInformationId);
					for (int j = 0; j < getedGoodsActivityList.size(); j++) {
						GoodsActivity item = getedGoodsActivityList.get(j); 
						 
						//判断所属活动中的商品是否包含要购买的商品  包含：放入限购商品列表
						int itemState = item.getState();
						int itemGoodsId = item.getGoodsId();
						
						switch (itemState) {
						case 0://规格
							if(!gsdIdList.contains(itemGoodsId) && goodsSpeIdList.contains(itemGoodsId)){
								gsdIdList.add(itemGoodsId);
								
								int index = goodsSpeIdList.indexOf(itemGoodsId); 
								double unitPrice = goodsMsgList.get(index).getUnitPrice();
								int number = goodsMsgList.get(index).getNumber();
								double money = unitPrice * number;
								//累加商品总价
								totalMoney += money; 
							}
							break;
						case 1://商品
							List<GoodsSpecificationDetails> gsdList = goodsSpecificationDetailsService.selectByGoodsId(itemGoodsId);
							if(gsdList != null && gsdList.size()>0){
								for (int k = 0; k < gsdList.size(); k++) {
									int gsdId = gsdList.get(k).getId();
									if(!gsdIdList.contains(gsdId) && goodsSpeIdList.contains(gsdId)){
										gsdIdList.add(gsdId);
										
										int index = goodsSpeIdList.indexOf(gsdId); 
										double unitPrice = goodsMsgList.get(index).getUnitPrice();
										int number = goodsMsgList.get(index).getNumber();
										double money = unitPrice * number;
										//累加商品总价
										totalMoney += money; 
									}
								}
							}
							
							break;
						case 2://分类
							List<GoodsDetails> gdList = goodsDetailsService.getGoodsListByClassificationId(itemGoodsId);
							if(gdList != null && gdList.size()>0){
								for (int k = 0; k < gdList.size(); k++) {
									int gdId = gdList.get(k).getId();
									List<GoodsSpecificationDetails> gsdList2 = goodsSpecificationDetailsService.selectByGoodsId(gdId);
									if(gsdList2 != null && gsdList2.size()>0){
										for (int n = 0; n < gsdList2.size(); n++) {
											int gsdId = gsdList2.get(n).getId();
											if(!gsdIdList.contains(gsdId) && goodsSpeIdList.contains(gsdId)){
												gsdIdList.add(gsdId);
												
												int index = goodsSpeIdList.indexOf(gsdId); 
												double unitPrice = goodsMsgList.get(index).getUnitPrice();
												int number = goodsMsgList.get(index).getNumber();
												double money = unitPrice * number;
												//累加商品总价
												totalMoney += money;
											}
										}
									}
								}
							}
							break;
						default:
							break;
						}
						
						
					}
					
				}
				//已得到该优惠券可使用的将购买的商品规格id列表
				if(gsdIdList.size()>0){
					//判断该优惠券可购买的商品价格是否满足门槛
					if(totalMoney >= useLimit){//满足门槛
						//计算使用该优惠券之后的商品价格
						discountMoney = amount - price;
						UserCouponMsg userCouponMsg = new UserCouponMsg();
						userCouponMsg.setUserCoupons(userCoupons);
						userCouponMsg.setAfterDiscountMoney(discountMoney);
						userCouponMsgList.add(userCouponMsg);
						
					}
					
				}
			}else{//无所属活动  放入无序优惠券列表
				if(amount>=useLimit){
					UserCouponMsg userCouponMsg = new UserCouponMsg();
					userCouponMsg.setUserCoupons(userCoupons);
					userCouponMsg.setAfterDiscountMoney(amount-price);
					userCouponMsgList.add(userCouponMsg);
				}
				
			}
		
		}
		
		
		//三、对优惠券和活动列表进行排序并处理
		List<UserCouponMsg> allUserCouponMsgList = new ArrayList<>();
		List<ActivityMsg> allActivityMsgList = new ArrayList<>();
		
		if(userCouponMsgList.size()>0){//对优惠券进行处理
			//排序
			allUserCouponMsgList.addAll(orderForCoupon(userCouponMsgList));
		}
		if(activityMsgList.size()>0){//对活动进行处理 
			//排序
			allActivityMsgList.addAll(orderForActivity( activityMsgList));
			
			//向活动中放入活动优惠后可用的优惠券列表
			if(allUserCouponMsgList.size()>0){
				
				for (int i = 0; i < allActivityMsgList.size(); i++) {
					//临时优惠券列表
					List<UserCouponMsg> tempUserCouponMsgList = new ArrayList<>();
					tempUserCouponMsgList.addAll(allUserCouponMsgList);//默认是全部优惠券
					ActivityMsg am = new ActivityMsg();
					am = allActivityMsgList.get(i);
					double afterDiscountMoney = am.getAfterDiscountMoney();
					
					for (int j = 0; j < tempUserCouponMsgList.size(); j++) {
						UserCouponMsg ucm = new UserCouponMsg();
						ucm = tempUserCouponMsgList.get(j).clone();
						
						double useLimit = ucm.getUserCoupons().getCouponInformation().getUseLimit();//获取优惠券的门槛价格
						
						if(afterDiscountMoney<useLimit){//经活动优惠后的价格不满足优惠券门槛价格  将该优惠券从临时优惠券列表中移除
							tempUserCouponMsgList.remove(j);
							j--;
						}else{//满足优惠券门槛价格 进行满减 并设置优惠后的金额
							double price = ucm.getUserCoupons().getCouponInformation().getPrice();//优惠券满减金额
							//满减   活动优惠后的价格-优惠券满减金额
							double endMoney = afterDiscountMoney - price;
							//将最终优惠后的价格放入优惠券优惠后的价格
							ucm.setAfterDiscountMoney(endMoney);
							tempUserCouponMsgList.set(j, ucm);
							
						}
						
						
//						if(afterDiscountMoney>useLimit){//经活动优惠后的价格不满足优惠券门槛价格 进行满减 并设置优惠后的金额 最后将ucm放入tempUserCouponMsgList
//							double price = ucm.getUserCoupons().getCouponInformation().getPrice();//优惠券满减金额
//							//满减   活动优惠后的价格-优惠券满减金额
//							double endMoney = afterDiscountMoney - price;
//							//将最终优惠后的价格放入优惠券优惠后的价格
//							ucm.setAfterDiscountMoney(endMoney);
//							tempUserCouponMsgList.add(ucm);
//						}
						
					}
					//将满足条件的优惠券列表放入活动中
					am.setUserCouponMsgList(orderForCoupon(tempUserCouponMsgList));
					allActivityMsgList.set(i, am);
				}
			}
			
		}
		
		
		resultInfo.put("activitys",allActivityMsgList);
		resultInfo.put("coupons",allUserCouponMsgList);
		result.put("resultData", resultInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
		
		
		
	}
	
	/**
	 * 获取所有正在生效的预售活动信息
	 * 活动消息的点击事件接口
	 * @param request
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "getAllEffectPreSellActivityInformation", method = RequestMethod.GET)
	public JSONObject getAllEffectPreSellActivityInformation(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		
		List<ActivityInformation> activityInformations = new ArrayList<>();
		activityInformations = activityInformationService.getAllEffectPreSellActivityInformation();
		
		result.put("resultData", activityInformations);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
		
	}
	
	/**
	 * 根据商品id和优惠券id获取该商品参与的正在上线的且结束时间最早的预售活动信息
	 * @param goodsDetailsId 商品id
	 * @param couponId 优惠券id
	 * @param request
	 * @param activityInformationId  活动id
	 * @return 
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "getPreSellActivityInformationByGoodsDetailsId", method = RequestMethod.GET)
	public JSONObject getPreSellActivityInformationByGoodsDetailsId(HttpServletRequest request,@RequestParam("goodsDetailsId") Integer goodsDetailsId,@RequestParam("couponId") Integer couponId) throws Exception {
		JSONObject result = new JSONObject();
		if(goodsDetailsId==null || couponId == null || goodsDetailsId<=0 || couponId < 0){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		GoodsDetails goodsDetails = goodsDetailsService.selectByPrimaryKey(goodsDetailsId);
		if(goodsDetails==null){
			result.put("code", 80002);
			result.put("msg", "商品信息不存在");
			return result;
		}
		ActivityInformation activityInformation = null;
		List<ActivityInformation> activityInformations = null;
		if(couponId == 0) {
			activityInformations = activityInformationService.getAllEffectPreSellActivityInformationByGoodsDetailsId(goodsDetailsId);
		}else if(couponId>0) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("goodsDetailsId", goodsDetailsId);
			map.put("couponId", couponId);
			//根据商品id获取正在上线的预售活动信息 并根据结束时间正序排序
			activityInformations = activityInformationService.getAllEffectPreSellActivityInformationByGoodsDetailsIdAndCouponId(map);
		}
		
		//获取该商品参与的且结束时间最早的预售活动
		if(activityInformations != null && activityInformations.size()>0) {
			activityInformation = activityInformations.get(0);
		}
		result.put("resultData", activityInformation);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
		
	}
	
	//以下为私有方法
	
	/**
	 * 对优惠券列表进行冒泡排序
	 * @param userCouponMsgList
	 * @return
	 */
	private List<UserCouponMsg> orderForCoupon(List<UserCouponMsg> userCouponMsgList){
		
		for(int i=0;i<userCouponMsgList.size()-1;i++){//外层循环控制排序趟数
			for(int j=0;j<userCouponMsgList.size()-1-i;j++){//内层循环控制每一趟排序多少次
                double money1 = userCouponMsgList.get(j).getAfterDiscountMoney();	
                double money2 = userCouponMsgList.get(j+1).getAfterDiscountMoney();	
                if(money1 > money2){
                	UserCouponMsg userCouponMsg = userCouponMsgList.get(j);
                	userCouponMsgList.set(j, userCouponMsgList.get(j+1));
                	userCouponMsgList.set(j+1, userCouponMsg);
                	}
			}
		} 
		return userCouponMsgList;

	}
	
	/**
	 * 对活动列表进行冒泡排序
	 * @param activityMsgList
	 * @return
	 */
	private List<ActivityMsg> orderForActivity(List<ActivityMsg> activityMsgList){
		
		for(int i=0;i<activityMsgList.size()-1;i++){//外层循环控制排序趟数
			for(int j=0;j<activityMsgList.size()-1-i;j++){//内层循环控制每一趟排序多少次
                double money1 = activityMsgList.get(j).getAfterDiscountMoney();	
                double money2 = activityMsgList.get(j+1).getAfterDiscountMoney();	
                if(money1 > money2){
                	ActivityMsg activityMsg = activityMsgList.get(j);
                	activityMsgList.set(j, activityMsgList.get(j+1));
                	activityMsgList.set(j+1, activityMsg);
                	}
			}
		} 
		
		return activityMsgList;

	}
	
	/**
	 * 私有方法一
	 * 
	 * 获取商品的list列表 （传默认值则为热门推荐列表;刚进商品list显示页是也按默认值传参数;若无需该参数，则需传默认值!!!）
	 * 
	 * @param request
	 * @param sortType
	 *            页面list排序(1:综合排序，2：销量排序，3：价格排序，4：热门分类)，默认传1
	 * @param priceSort
	 *            根据价格排序时 是倒序还是正序（asc：正序，desc：倒序）默认为""
	 * @param searchName
	 *            页面头部的搜索栏 (如果没值则传""),默认传""
	 * @param isHasGoods
	 *            是否仅查看有货("true":是，"false"：否),默认传true
	 * @param minPrice
	 *            价格区间的最低价(不设置的话传0)，默认传0
	 * @param maxPrice
	 *            价格区间的最高价(不设置的话传0)，默认传0
	 * @param brandName
	 *            品牌名称(全部传 all)，默认传 all
	 * @param classificationId
	 *            分类id(全部传0)，默认传0
	 * @return
	 * @throws Exception
	 */
	private JSONObject getGoodsList(HttpServletRequest request,  Integer sortType,String priceSort,String searchName,String isHasGoods,Double minPrice,Double maxPrice,String brandName,Integer classificationId) throws Exception {
		JSONObject result = new JSONObject();
		if (sortType == null || priceSort == null || searchName == null || (isHasGoods == null || "".equals(isHasGoods))
				|| minPrice == null || maxPrice == null || (brandName == null || "".equals(brandName))
				|| classificationId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		String[] brandNames=brandName.split(",");
		if ("\"\"".equals(searchName) || "''".equals(searchName) || "".equals(searchName)) {
			searchName = "all";
		}
		
		List<GoodsDetails> goodsDetailsList = new ArrayList<GoodsDetails>();
		List<GoodsDetails> resultDataList = new ArrayList<GoodsDetails>();
		List<GoodsSpecificationDetails> gsdList = new ArrayList<>();
		List<GoodsDisplayPicture> gdpList = new ArrayList<>();

		// 获取商品列表
		goodsDetailsList = goodsDetailsService.getHomeHotGoods(classificationId, searchName, sortType);
		if (goodsDetailsList.size() == 0) {
			result.put("resultData", "未搜索到商品信息!");
			result.put("code", 201);
			result.put("msg", "请求成功");
			return result;
		} else {
			// 如果有列表则获取商品的按价格正序排序的第一个商品详情以及id最小的商品详情图片
			for (int i = 0; i < goodsDetailsList.size(); i++) {
				// 查找商品所拥有的商品详情
				List<GoodsSpecificationDetails> goodsSpecificationDetailslist=new ArrayList<>();
				//不允许0库存出库
				if(goodsDetailsList.get(i).getZeroStock()==0){
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), isHasGoods, brandNames,"");
				}
				//允许0库存出库
				else{
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), "false", brandNames,"");
				}
				
				gsdList = new ArrayList<>();

				// 说明可以查出来商品详情
				if (goodsSpecificationDetailslist.size() > 0) {
					// 说明只用查询最低值
					if (minPrice != 0 && maxPrice == 0) {
						if (goodsSpecificationDetailslist.get(0).getPrice() >= minPrice) {
							// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
							List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
							goodsDisplayPictureList = goodsDisplayPictureService
									.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
							gdpList = new ArrayList<>();

							if (goodsDisplayPictureList.size() > 0) {
								gdpList.add(goodsDisplayPictureList.get(0));
							}
							// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
							goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
							// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
							gsdList.add(goodsSpecificationDetailslist.get(0));
							// 如果该商品存在商品详情，则存入值
							goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

							// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
							resultDataList.add(goodsDetailsList.get(i));
						}
					}
					// 说明查询区间值
					else if (minPrice != 0 && maxPrice != 0) {
						if (goodsSpecificationDetailslist.get(0).getPrice() >= minPrice
								&& goodsSpecificationDetailslist.get(0).getPrice() <= maxPrice) {
							// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
							List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
							goodsDisplayPictureList = goodsDisplayPictureService
									.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
							gdpList = new ArrayList<>();

							if (goodsDisplayPictureList.size() > 0) {
								gdpList.add(goodsDisplayPictureList.get(0));
							}
							// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
							goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
							// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
							gsdList.add(goodsSpecificationDetailslist.get(0));
							// 如果该商品存在商品详情，则存入值
							goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

							// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
							resultDataList.add(goodsDetailsList.get(i));
						}
					}
					// 说明只用查询最高值
					else if (minPrice == 0 && maxPrice != 0) {
						if (goodsSpecificationDetailslist.get(0).getPrice() <= maxPrice) {
							// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
							List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
							goodsDisplayPictureList = goodsDisplayPictureService
									.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
							gdpList = new ArrayList<>();

							if (goodsDisplayPictureList.size() > 0) {
								gdpList.add(goodsDisplayPictureList.get(0));
							}
							// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
							goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
							// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
							gsdList.add(goodsSpecificationDetailslist.get(0));
							// 如果该商品存在商品详情，则存入值
							goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

							// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
							resultDataList.add(goodsDetailsList.get(i));
						}
					}
					// 说明不用判断价格
					else {
						// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
						List<GoodsDisplayPicture> goodsDisplayPictureList = goodsDisplayPictureService
								.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
						gdpList = new ArrayList<>();

						if (goodsDisplayPictureList.size() > 0) {
							gdpList.add(goodsDisplayPictureList.get(0));
						}
						// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
						goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
						// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
						gsdList.add(goodsSpecificationDetailslist.get(0));
						// 如果该商品存在商品详情，则存入值
						goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

						// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
						resultDataList.add(goodsDetailsList.get(i));
					}

				}

			}
			if (resultDataList.size() == 0) {
				result.put("resultData", "未搜索到商品信息!");
				result.put("code", 201);
				result.put("msg", "请求成功");
				return result;
			} else {
				// 如果按价格排序，价格则冒泡排序
				if (sortType == 3) {
					for (int i = 1; i < resultDataList.size(); i++) {
						for (int k = 0; k < resultDataList.size() - i; k++) {

							// 正序
							if ("asc".equals(priceSort)) {
								if (resultDataList.get(k).getGoodsSpecificationDetails().get(0)
										.getPrice() > resultDataList.get(k + 1).getGoodsSpecificationDetails().get(0)
												.getPrice()) {
									GoodsDetails goodsDetails = new GoodsDetails();
									goodsDetails = resultDataList.get(k);
									resultDataList.set(k, resultDataList.get(k + 1));
									resultDataList.set(k + 1, goodsDetails);
								}
							}
							// 倒序
							else {
								if (resultDataList.get(k).getGoodsSpecificationDetails().get(0)
										.getPrice() < resultDataList.get(k + 1).getGoodsSpecificationDetails().get(0)
												.getPrice()) {
									GoodsDetails goodsDetails = new GoodsDetails();
									goodsDetails = resultDataList.get(k);
									resultDataList.set(k, resultDataList.get(k + 1));
									resultDataList.set(k + 1, goodsDetails);
								}
							}
						}
					}
				}
			}


			
			
			result.put("resultData", resultDataList);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
	}

	
	/**
	 * 私有方法二
	 * 
	 * 根据商品id获取商品详情、商品活动、商品评价等信息
	 * @param request
	 * @param goodsId 商品id
	 * @return
	 * @throws Exception
	 */
	
	private JSONObject getGoodsDetailMsgByGoodsId(HttpServletRequest request,Integer goodsId) throws Exception {
		JSONObject result = new JSONObject();
		if (goodsId == null || goodsId <= 0) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		//先获取商品信息，然后查找该商品参与的活动信息	
		GoodsDetails goodsDetails=goodsDetailsService.getGoodsDetailAndEvaluationByGoodsId(goodsId);
		List<GoodsActivity> goodsActivityList=new ArrayList<>();
		
		if(goodsDetails!=null){
			//传入商品id和isGoodsId=‘true’/分类id和isGoodsId=‘false’ 把查出来的结果整合即是商品所参与的所有活动，放入商品信息中
			List<GoodsActivity> goodsActivityListByGoodsId=goodsActivityService.getGoodsActivityMsgByStateAndGoodsId(goodsDetails.getId(), "true");
			List<GoodsActivity> goodsActivityListByClassificationId=goodsActivityService.getGoodsActivityMsgByStateAndGoodsId(goodsDetails.getClassificationId(), "false");
			if(goodsActivityListByGoodsId!=null && goodsActivityListByGoodsId.size()>0){
				for(int i=0;i<goodsActivityListByGoodsId.size();i++){
					goodsActivityList.add(goodsActivityListByGoodsId.get(i));
				}
			}
			if(goodsActivityListByClassificationId!=null && goodsActivityListByClassificationId.size()>0){
				for(int i=0;i<goodsActivityListByClassificationId.size();i++){
					goodsActivityList.add(goodsActivityListByClassificationId.get(i));
				}
			}
			goodsDetails.setGoodsActivitys(goodsActivityList);
			
			//再根据商品id查出商品详情，该商品详情包含该商品详情所参与的活动
			List<GoodsSpecificationDetails> goodsSpecificationDetailsList=new ArrayList<>();
			//不允许0库存出库
			if(goodsDetails.getZeroStock()==0){
				goodsSpecificationDetailsList=goodsSpecificationDetailsService.getGoodsDetailMsgByGoodsId(goodsId,"","true");
			}
			//允许0库存出库
			else{
				goodsSpecificationDetailsList=goodsSpecificationDetailsService.getGoodsDetailMsgByGoodsId(goodsId,"","false");
			}
			
			if(goodsSpecificationDetailsList.size()>0) {
				//再把商品详情放入商品信息中传入移动端
				goodsDetails.setGoodsSpecificationDetails(goodsSpecificationDetailsList);
			}else {
				result.put("resultData", null);
				result.put("code", 2000);
				result.put("msg", "商品无库存");
				return result;
			}
		}
		
		result.put("resultData", goodsDetails);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	
	
	
	
	
	
	
	
}
