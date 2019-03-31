package com.jl.app.api;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.Classification;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.ActiveCouponService;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.CouponInformationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsDisplayPictureService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.UserCouponsService;

/**
 * 优惠券信息API
 * 
 * @author 景雅倩
 * @date 2017-12-06 下午3:41:25
 * @Description TODO
 */
@Controller
@RequestMapping("/couponInformation/")
public class CouponInformationApi extends BaseController {
	@Autowired
	private CouponInformationService couponInformationService;
	
	@Autowired
	private UserCouponsService userCouponsService;
	
	@Autowired
	private ActiveCouponService activeCouponService;

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
	private OrderTableService orderTableService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private ActivityInformationService activityInformationService;
	


	/**
	 * 获取所有可以领取的优惠券信息（即：当前时间在领取时间内 && 剩余数量>0）
	 * 对应APP领券中心展示的优惠券信息
	 * @param request
	 * @return  可以领取的优惠券列表
	 */
	@ResponseBody
	@RequestMapping(value = "getAllAvailableCoupon", method = RequestMethod.GET)
	public JSONObject getAllAvailableCoupon(HttpServletRequest request) {
		
		List<CouponInformation> resultData = couponInformationService.getAllAvailableCoupon();
		JSONObject result = new JSONObject();
		result.put("resultData", resultData);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	/**
	 * 用户领券
	 * 用户点击领券按钮触发事件接口
	 * 对应APP领券中心展示的优惠券信息
	 * @param userId  用户id
	 * @param couponId  优惠券id
	 * @param request
	 * @return  是否领取成功信息
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "userGetCoupon", method = RequestMethod.POST)
	public JSONObject userGetCoupon(HttpServletRequest request,Integer userId,Integer couponId) throws Exception {
		if(userId==null || couponId==null|| userId<=0 || couponId<=0){
			JSONObject result = new JSONObject();
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		
		
		
		UserCoupons userCoupons = new UserCoupons();
		
		userCoupons.setUserId(userId);
		userCoupons.setCouponInformationId(couponId);
		userCoupons.setStatus(0);//默认状态为未使用
		Date getTime=new Date();
		userCoupons.setGetTime(getTime);
		
//		JSONObject rmsg = new JSONObject();
		JSONObject result = new JSONObject();
		
		
		//判断优惠券当前数量是否>=1
		CouponInformation couponInformation = couponInformationService.selectByPrimaryKey(couponId);
		
		if(couponInformation == null){
			result.put("code", 30002);
			result.put("msg", "优惠券信息不存在");
			return result;	
		}
		
		//判断优惠券状态
		int state = couponInformation.getState();
		switch (state) {
		case 1://已失效
			result.put("resultData", "该优惠券已失效！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		case 2://已撤回
			result.put("resultData", "该优惠券已被撤回！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;

		default:
			break;
		}
		
		
		int count = couponInformation.getCount();
		if(count>=1){//优惠券数量足够领取
			//判断当前用户是否领取过此优惠券
			int haveNum = userCouponsService.selectByUserIdAndcouponId(userCoupons);
			if(haveNum==0){//未领取过  可以领取
				if(userCouponsService.insertSelective(userCoupons)>0){
					//优惠券剩余数量-1
					couponInformation.setCount(couponInformation.getCount()-1);
					couponInformationService.updateByPrimaryKeySelective(couponInformation);
					
//					rmsg.put("success", true);
//					rmsg.put("msg", "领取成功！");
					result.put("resultData", "领取成功！");
					result.put("code", 200);
					result.put("msg", "请求成功");
					return result;
					
				}else{
//					rmsg.put("success", false);
//					rmsg.put("msg", "领取失败！");
					result.put("resultData", "领取失败！");
					result.put("code", 30001);
					result.put("msg", "优惠券领取错误");
					return result;
				}
				
			}else{//当前用户领取过该优惠
				
//				rmsg.put("success", false);
//				rmsg.put("msg", "您已领取过该优惠券！");
				result.put("resultData", "您已领取过该优惠券！");
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			}
			
		}else{//优惠券已经被抢光了
//			rmsg.put("success", false);
//			rmsg.put("msg", "该优惠券被抢光了！");
			result.put("resultData", "该优惠券被抢光了！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		
		
		
	}
	
	
	
	/**
	 * 获取该用户拥有的所有优惠券信息（包括可用的不可用的）
	 * 对应APP中用户优惠券页面展示的优惠券信息
	 * @param userId 用户id
	 * @param request
	 * @return  可使用的优惠券列表（resultData.usableCoupon）和不可使用的优惠券列表（resultData.disabledCoupon）
	 */
	
	@ResponseBody
	@RequestMapping(value = "getCouponInfoByUserId", method = RequestMethod.GET)
	public JSONObject getCouponInfoByUserId(HttpServletRequest request,@RequestParam("userId") Integer userId) {
		if(userId==null){
			JSONObject result = new JSONObject();
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		
		
		
		
		//可使用的优惠券列表
		List<UserCoupons> usableCoupon = userCouponsService.selectUsableCouponByUserId(userId) ;
		
		//不可使用的优惠券列表
		List<UserCoupons> disabledCoupon=userCouponsService.selectDisabledCouponByUserId(userId);
		
		this.forEachGoods(usableCoupon, true);
		this.forEachGoods(disabledCoupon, false);
		
		
		JSONObject dataObj = new JSONObject();
		dataObj.put("usableCoupon", usableCoupon);
		dataObj.put("disabledCoupon", disabledCoupon);
		
		
		JSONObject result = new JSONObject();
		result.put("resultData", dataObj);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	
	
	/**
	 * 获取某张优惠券对应的商品信息
	 * 对应APP中优惠券立即使用按钮点击事件
	 * @param userId 用户id
	 * @param request
	 * @return  resultData.flag:旗帜变量  用来标志点击后跳转到:APP首页（0）    优惠券可用商品列表页面（1） 
	 *          resultData.goods:优惠券可用商品列表信息
	 * @throws Exception 
	 */
	
	@ResponseBody
	@RequestMapping(value = "getMsgByCouponId", method = RequestMethod.GET)
	public JSONObject getMsgByCouponId(HttpServletRequest request,@RequestParam("couponId") Integer couponId) throws Exception {
		JSONObject result = new JSONObject();
		if(couponId==null){
			
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		JSONObject resultInfo = new JSONObject();
		int flag = 0;//旗帜变量  标志点击后跳转到哪个页面 [APP首页（0）    优惠券可用商品列表页面（1）]  默认跳转到APP首页
		
		List<ActiveCoupon> activeCouponList = activeCouponService.selectByCouponId(couponId);
		List<GoodsDetails> goodsList = new ArrayList<>();
		List<Integer> tempList = new ArrayList<>();
		List<GoodsDetails> tempGoodsList = new ArrayList<>();
		if(activeCouponList!=null && activeCouponList.size()>0){//该优惠券有对应商品
			flag = 1;
			//遍历活动优惠券列表 
			for (int i = 0; i < activeCouponList.size(); i++) {
				//获取活动id
				int activityInformationId = activeCouponList.get(i).getActivityInformationId();
				
				//根据活动id获取活动信息
				ActivityInformation activityInformation = activityInformationService.selectByPrimaryKey(activityInformationId);
				if(activityInformation!=null) {
					//获取活动状态
					int state = activityInformation.getState();
					if(state == 4){//上线中
						//根据活动id获取活动商品列表 并放入goodsList
						goodsList.addAll(getGoodsDetailsListByActivityInformationId(request, activityInformationId)); 
					}
				}
				
			}
			
			
			//商品列表去重
			tempGoodsList.addAll(goodsList);
			int itemId=0;
			
			for(int i=0;i<tempGoodsList.size();i++){

				itemId = tempGoodsList.get(i).getId();
				if(!tempList.contains(itemId)){  
			       tempList.add(itemId);  
			    }else{
			    	goodsList.remove(tempGoodsList.get(i));
			    }
			} 

		}
		
		
		resultInfo.put("goods", goodsList);
		resultInfo.put("flag", flag);
		
		result.put("resultData", resultInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	
	/**
	 * 用户购物返券
	 * @param request
	 * @param orderId 订单id
	 * @return 无可返优惠券/返券成功
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "shoppingBackCouponToUser", method = RequestMethod.POST)
	public JSONObject shoppingBackCouponToUser(HttpServletRequest request,Integer orderId) throws Exception {
		
		JSONObject result = new JSONObject();
		//参数判空
		if(orderId==null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		//查询订单是否存在
		OrderTable orderTable = orderTableService.selectByPrimaryKey(orderId);
		if(orderTable == null){
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		}
		//订单存在
		List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(orderId);
		if(orderDetailList == null || orderDetailList.size()<=0){//订单下无商品
			result.put("code", 12002);
			result.put("msg", "订单异常");
			return result;
		}
		//订单存在 & 订单下有商品
		//获取userId				
		int userId = orderTable.getUserId();
		List<Integer> activityInformationIdList = new ArrayList<>();
//		System.out.println("orderDetailesList.size:"+orderDetailList.size());
		for (int i = 0; i < orderDetailList.size(); i++) {
			
			OrderDetail orderDetail = orderDetailList.get(i);
			int goodsDetailId = orderDetail.getGoodsDetailsId();
			int activityInformationId;
			int goodsSpecificationDetailsId = orderDetail.getGoodsSpecificationDetailsId();
//			System.out.println("gdid:"+goodsDetailId+"   gsdId:"+goodsSpecificationDetailsId);
			JSONObject object2 =  getGoodsDetailMsgByGoodsId(request, goodsDetailId);
			int code2 = object2.getIntValue("code");
			if(code2==200){
//				System.out.println("code=200");
				//根据商品id获取商品详情信息
				GoodsDetails goodsDetails = (GoodsDetails) object2.get("resultData");
				//获取该商品参与的活动列表
				List<GoodsActivity> goodsActivitiesForGdList = goodsDetails.getGoodsActivitys();
				if(goodsActivitiesForGdList!=null && goodsActivitiesForGdList.size()>0){//该商品详情有参与的活动
//					System.out.println("gdlist!=null");
					for (int j = 0; j < goodsActivitiesForGdList.size(); j++) {//遍历获取活动id
						activityInformationId = goodsActivitiesForGdList.get(j).getActivityInformationId();
						if(!activityInformationIdList.contains(activityInformationId)){
							activityInformationIdList.add(activityInformationId);
						}
					}
				}
				
				//获取该商品的商品详情列表
				List<GoodsSpecificationDetails> goodsSpecificationDetailsList = goodsDetails.getGoodsSpecificationDetails();
				for (int j = 0; j < goodsSpecificationDetailsList.size(); j++) {
					GoodsSpecificationDetails gsd = goodsSpecificationDetailsList.get(j);
					int gsdId =gsd.getId();
					if(gsdId == goodsSpecificationDetailsId){
						List<GoodsActivity> goodsActivitiesForGsdList = gsd.getGoodsActivitys();
						if(goodsActivitiesForGsdList!=null && goodsActivitiesForGsdList.size()>0){//该商品规格详情有参与的活动
//							System.out.println("gsdlist!=null");
							for (int k = 0; k < goodsActivitiesForGsdList.size(); k++) {//遍历获取活动id
								activityInformationId = goodsActivitiesForGsdList.get(k).getActivityInformationId();
								if(!activityInformationIdList.contains(activityInformationId)){
									activityInformationIdList.add(activityInformationId);
								}
							}
						}
						break;
					}
				}
				
				
			}
			
		}
		
		if(activityInformationIdList.size()>0){//购买的商品参与的有活动
//			System.out.println("if");
			//遍历活动列表 获取规则是购物返券的优惠券id列表
			List<Integer> couponIdList = new ArrayList<>();
			int activityInformationId,couponId ;
			for (int i = 0; i < activityInformationIdList.size(); i++) {
				activityInformationId = activityInformationIdList.get(i);
//				System.out.println("activityInformationId"+i+":  "+activityInformationId);
				List<CouponInformation> couponInformations=activeCouponService.getShoppingBackCouponByActivityInformationId(activityInformationId);
				for (int j = 0; j < couponInformations.size(); j++) {
					CouponInformation couponInformation = couponInformations.get(j);
					couponId  = couponInformation.getId();
					if(!couponIdList.contains(couponId)){
						couponIdList.add(couponId);
					}
				}
			}
			//获取到购物返券的优惠券id列表   向用户账户发放优惠券
		
			if(couponIdList!=null && couponIdList.size()>0){
				
				//用户优惠券表的处理
				UserCoupons userCoupons;
				for (int i = 0; i < couponIdList.size(); i++) {
//					System.out.println("couponId"+i+":  "+couponIdList.get(i));
					userCoupons = new UserCoupons();
					userCoupons.setStatus(0);
					userCoupons.setGetTime(new Date());
					userCoupons.setUserId(userId);
					userCoupons.setCouponInformationId(couponIdList.get(i));
					userCouponsService.insertSelective(userCoupons);
				}
				result.put("resultData", "返券成功");
			}else{//没有可以领取的购物返券
				result.put("resultData", "无可返优惠券");
			}
			
		}else{//购买的商品没有参与活动
//			System.out.println("else");
			result.put("resultData", "无可返优惠券");
		}
		
		
		
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	
	
	
	
	//以下为私有方法
	
	/**
	 * 私有方法  遍历优惠券列表 向优惠券模型中设置状态信息和使用说明
	 * @param couponList  优惠券列表
	 * @param usable      该优惠券列表是可用优惠券列表还是不可用优惠券列表
	 */
	private void forEachGoods(List<UserCoupons> couponList,  boolean usable){
		for (UserCoupons userCoupons : couponList) {
			//设置状态信息
			if (usable) {
				userCoupons.setStateMsg("可使用");
			}else{
				switch (userCoupons.getStatus()) {
				case 0:
					userCoupons.setStateMsg("不可用");
					break;
				case 1:
					userCoupons.setStateMsg("已使用");
					break;
				case 2:
					userCoupons.setStateMsg("已过期");
					break;
				case 3:
					userCoupons.setStateMsg("已失效");
					break;
					
				default:
					break;
				}
			}
			
			//设置使用说明
			
			//查询该优惠券是否有所属活动
			int couponId = userCoupons.getCouponInformationId();
			List<ActiveCoupon> activeCouponList = activeCouponService.selectByCouponId(couponId);//获取该优惠券所属的活动列表
			if(activeCouponList!=null){//有所属活动  遍历出该活动下的商品的一级分类名称  作为使用说明
				
				List<String> goodsCFOneNameList = new ArrayList<>();//存放商品一级分类名称
				for (ActiveCoupon activeCoupon : activeCouponList) {//遍历优惠券所属活动列表
					int activityInformationId = activeCoupon.getActivityInformationId();//获取活动id
					//根据活动id获取该活动下的商品信息
					List<GoodsActivity> goodsActivityList = goodsActivityService.getMsgByActivityInformationId(activityInformationId);
					
					for (GoodsActivity goodsActivity : goodsActivityList) {//遍历活动商品列表 获取一级分类名称
						int goodsId = goodsActivity.getGoodsId();
						int classificationId,classificationParentId;
						String classificationOneName="";
						switch (goodsActivity.getState()) {
						case 0://规格
							classificationId = goodsActivityService.getMsgByActivityInformationId0(activityInformationId, goodsId).getGoodsDetails().getClassificationId();
							classificationParentId = classificationService.selectByPrimaryKey(classificationId).getParentId();
							classificationOneName = classificationService.selectByPrimaryKey(classificationParentId).getName();
							break;
						case 1://商品
							classificationId = goodsActivityService.getMsgByActivityInformationId1(activityInformationId, goodsId).getGoodsDetails().getClassificationId();
							classificationParentId = classificationService.selectByPrimaryKey(classificationId).getParentId();
							classificationOneName = classificationService.selectByPrimaryKey(classificationParentId).getName();
							break;
						case 2://分类
							Classification classification = goodsActivityService.getMsgByActivityInformationId2(activityInformationId, goodsId).getClassification();
							classificationParentId = classification.getParentId();
							if(classificationParentId!=0){//二级分类
								classificationOneName = classificationService.selectByPrimaryKey(classificationParentId).getName();
							}else{//一级分类
								classificationOneName = classification.getName();
							}
							
							break;

						default:
							break;
						}
						
						if(!goodsCFOneNameList.contains(classificationOneName)){//列表中没有要添加的名称    添加名称
							goodsCFOneNameList.add(classificationOneName);
						}
					}
					
					
					
				}
				String useInstruction = "限购部分";
				for (int i = 0; i < goodsCFOneNameList.size(); i++) {
					useInstruction += goodsCFOneNameList.get(i);
					if(i<goodsCFOneNameList.size()-1){
						useInstruction += "类、";
					}
					
				}
				useInstruction +="类商品";
				userCoupons.setUseInstruction(useInstruction);
			}else{
				userCoupons.setUseInstruction("全部商品可用");
			}
			
			
		}
	}


	/**
	 * 私有方法   根据活动id获取参与该活动的商品列表
	 * @param request
	 * @param activityInformationId 活动id
	 * @return
	 * @throws Exception
	 */
	private List<GoodsDetails> getGoodsDetailsListByActivityInformationId( HttpServletRequest request, int activityInformationId) throws Exception{
		List<GoodsDetails> goodsList = new ArrayList<>();
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
					GoodsSpecificationDetails goodsSpecificationDetails=goodsSpecificationDetailsService.selectByPrimaryKey(claOrGsdOrGdId);
					//商品上架中
					if(goodsSpecificationDetails!=null&&goodsSpecificationDetails.getState()==0){
						int goodsId = goodsSpecificationDetails.getGoodsId();
						if(!gsdGoodsIdList.contains(goodsId)){//去重
							gsdGoodsIdList.add(goodsId);
						}
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
		return goodsList;
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
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), isHasGoods, brandNames,"on");
				}
				//允许0库存出库
				else{
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), "false", brandNames,"on");
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

			for (int i = 0; i < resultDataList.size(); i++) {
//				System.out.println("方法一goodsId "+i+":"+resultDataList.get(i).getId());
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
