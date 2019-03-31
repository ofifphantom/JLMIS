package com.jl.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jl.app.api.AlipayApi;
import com.jl.app.api.WeChatApi;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.AfterSaleDetail;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.Log;
import com.jl.mis.model.OfflinePayment;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderMessage;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.SalesPlanOrder;
import com.jl.mis.model.SalesPlanOrderCommodity;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.AfterSaleDetailService;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.ActiveCouponService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.LogService;
import com.jl.mis.service.OfflinePaymentService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderMessageService;
import com.jl.mis.service.OrderStateDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.UserCouponsService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
import com.util.HttpUtil;
import com.util.PropertiesUtil;

/**
 * 订单管理controller
 * @author 柳亚婷
 * @date  2017年12月1日  下午2:42:32
 * @Description 
 *
 */
@Controller
@RequestMapping("/orderManagement/order/order/")
public class OrderController extends BaseController {
	
	// 保存汇款/转账凭证图片的文件夹名
	private String folderName = "offlinePaymentPicture";
	// 当前类操作的类型(往log日志表中存储)
	private int operateType = Constants.TYPE_LOG_ORDER;
	// 声明Log类
	Log log;
	
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private OfflinePaymentService offlinePaymentService;
	@Autowired
	private OrderStateDetailService orderStateDetailService;
	@Autowired
	OrderMessageService orderMessageService;
	@Autowired
	ActivityInformationService activityInformationService;
	@Autowired
	private LogService logService;	
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	private UserCouponsService userCouponsService;
	@Autowired
	private ActiveCouponService activeCouponService;
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	@Autowired
	private GoodsActivityService goodsActivityService;
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	@Autowired
	private AfterSaleDetailService afterSaleDetailService;
	@Autowired
	private WeChatApi	weChatApi;
	@Autowired
	private AlipayApi	alipayApi;
	/**
	 * 进入订单页面 page 1:基础订单管理页面 2：线下支付审核页面 3:线下支付确认 4:待发货订单页面 5:售后服务页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, int page) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}
		//跳转页面
		switch (page) {
		case 1:
			return "/junlin/mis_jsp/orderManagement/order/searchOrder";
		case 2:
			return "/junlin/mis_jsp/orderManagement/order/pendingAudit";
		case 3:
			return "/junlin/mis_jsp/orderManagement/order/pendingConfirmed";
		case 4:
			return "/junlin/mis_jsp/orderManagement/order/pendingSendOutGoods";
		case 5:
			return "/junlin/mis_jsp/orderManagement/order/customerService";
		default:
			return "";
		}
	}
	
	/**
	 * 从数据库中获取订单信息
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "getManagerMsg")
	public DataTables getManagerMsg(HttpServletRequest request,String orderNo,String userName,String phone,String orderState,String page,String placeOrderTime) {
		DataTables dataTables = DataTables.createDataTables(request);
		System.out.println("page:"+page);
		return orderTableService.getManagerMsg(dataTables,orderNo,userName,phone,orderState,page,placeOrderTime);
	}
	
	/**
	 * 根据订单id获取查看详情页面里的值
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkOrderDetailsByOrderId")
	public OrderTable checkOrderDetailsByOrderId(HttpServletRequest request,String id){
		return orderTableService.checkOrderDetailsByOrderId(id);
	}
	
	/**
	 * 根据前台的操作，更改订单的状态
	 * @param request
	 * @param orderTable
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOrderStatusByOrderId", method = RequestMethod.POST)
	public JSONObject updateOrderStatusByOrderId(HttpServletRequest request, OrderTable orderTable) throws Exception {
		JSONObject rmsg = new JSONObject();
		int result=orderTableService.updateByPrimaryKeySelective(orderTable);
		// 添加操作时间
		Date date = new Date();
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		if(result>0){
			OrderStateDetail orderStateDetail=new OrderStateDetail();
			orderStateDetail.setOrderId(orderTable.getId());
			if(orderTable.getOrderState()==5){
				orderStateDetail.setOrderStateDetail("订单已关闭");
			}
			else if(orderTable.getOrderState()==0){
				orderStateDetail.setOrderStateDetail("订单已恢复");
			}
			orderStateDetail.setAddTime(new Date());
			if(orderStateDetailService.insertSelective(orderStateDetail)>0){
				
				// 往log表中插入操作数据
				insertLog(operateType,orderTable.getOrderNo(), Constants.OPERATE_UPDATE, date,
						userIdentifier);
				
				rmsg.put("success", true);
				rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
				return rmsg;
			}
			
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 线下支付审核/确认
	 * @param request
	 * @param auditingOrVerifyOkOrNot 1:审核通过 2：审核未通过 3：确认通过 4：确认未通过
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/auditingOrVerifyOrderById", method = RequestMethod.POST)
	public JSONObject auditingOrVerifyOrderById(HttpServletRequest request, String oId,String opId,String auditingOrVerifyOkOrNot,String orderNo) throws Exception {
		JSONObject rmsg = new JSONObject();
		int result=-1;
		if("1".equals(auditingOrVerifyOkOrNot)){
			//审核通过，修改线下支付信息表的状态为4(待确认)
			OfflinePayment offlinePayment=new OfflinePayment();
			offlinePayment.setId(Integer.parseInt(opId));
			offlinePayment.setState(4);
			result=offlinePaymentService.updateByPrimaryKeySelective(offlinePayment);
		}
		else if("2".equals(auditingOrVerifyOkOrNot)){
			//审核没通过，修改线下支付信息表的状态为2(审核未通过)，同时修改订单状态为0（待支付）,同时删除之前添加好的线下支付信息
			
			OfflinePayment offlinePayment=offlinePaymentService.selectByPrimaryKey(Integer.parseInt(opId));
			//判断是否有图片需要删除
			List<String> offlinePaymentPicUrlList = new ArrayList<>();	
			if(offlinePayment.getPaymentVoucherUrlOne()!=null&&!"".equals(offlinePayment.getPaymentVoucherUrlOne())){
				offlinePaymentPicUrlList.add(offlinePayment.getPaymentVoucherUrlOne());
			}
			if(offlinePayment.getPaymentVoucherUrlTwo()!=null&&!"".equals(offlinePayment.getPaymentVoucherUrlTwo())){
				offlinePaymentPicUrlList.add(offlinePayment.getPaymentVoucherUrlTwo());
			}
			//删除图片
			if(offlinePaymentPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, folderName, offlinePaymentPicUrlList);
			}
			//删除之前添加好的线下支付信息
			offlinePayment.setState(2);
			offlinePayment.setRemitterName("");
			offlinePayment.setRemitterAccount("");
			offlinePayment.setPayeeName("");
			offlinePayment.setPayeeAccount("");
			offlinePayment.setPayeeAccountDepositBank("");
			offlinePayment.setRemittanceAmount(0.0);
			offlinePayment.setPaymentVoucherUrlOne("");
			offlinePayment.setPaymentVoucherUrlTwo("");
			
			OrderTable orderTable=new OrderTable();
			orderTable.setId(Integer.parseInt(oId));
			orderTable.setOrderState(0);
			orderTable.setPayTime(null);
			if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
				result=offlinePaymentService.updateByPrimaryKeySelective(offlinePayment);
			}
		}
		else if("3".equals(auditingOrVerifyOkOrNot)){
			//确认通过，修改线下支付信息表的状态为6(确认通过)，同时修改订单状态为1（待发货），同时需要在订单状态详情中添加状态(订单支付成功)
			OfflinePayment offlinePayment=new OfflinePayment();
			offlinePayment.setId(Integer.parseInt(opId));
			offlinePayment.setState(6);
			OrderTable orderTable=new OrderTable();
			orderTable.setId(Integer.parseInt(oId));
			orderTable.setOrderState(1);
			OrderStateDetail orderStateDetail=new OrderStateDetail();
			orderStateDetail.setAddTime(new Date());
			orderStateDetail.setOrderId(Integer.parseInt(oId));
			orderStateDetail.setOrderStateDetail("订单支付成功");
			if(orderTableService.updateByPrimaryKeySelective(orderTable)>0 && orderStateDetailService.insertSelective(orderStateDetail)>0){
				result=offlinePaymentService.updateByPrimaryKeySelective(offlinePayment);
				//购物返券
				shoppingBackCouponToUser(Integer.parseInt(oId));
			}
			//往进销存中存入订单的数据
			OrderTable orderMsg=orderTableService.selectByPrimaryKey(Integer.parseInt(oId));
			//向进销存发送订单信息 start
			SalesPlanOrder  salesPlanOrder=new SalesPlanOrder();
			salesPlanOrder.setAppConsigneeAddress(orderMsg.getConsigneeAddress());
			salesPlanOrder.setAppConsigneeName(orderMsg.getConsigneeName());
			salesPlanOrder.setAppConsigneePhone(orderMsg.getConsigneeTel());
			salesPlanOrder.setCreateTime(orderMsg.getPlaceOrderTime());
			salesPlanOrder.setIsAppOrder(2);
			salesPlanOrder.setMissOrderId(orderMsg.getId());
			salesPlanOrder.setIdentifier(orderNo);
		     Map<String,Object> map=new HashMap<String,Object>();
		     map.put("activityId",0);
		     if(orderMsg.getActivityId()!=null&&orderMsg.getActivityId()!=0) {
					ActivityInformation ai=  activityInformationService.selectByPrimaryKey(orderMsg.getActivityId());
		    	map.put("generatedTime",ai.getEndValidityTime());
		    	map.put("activityId",orderMsg.getActivityId());
		     }
		     map.put("activityType",1);
		     List<SalesPlanOrderCommodity> spcs=orderDetailService.selectOrderCommodity(orderMsg.getId());
		     salesPlanOrder.setSalesPlanOrderCommodities(spcs);
		     map.put("planOrder",salesPlanOrder);
		     
		     String jsonStr=JSON.toJSONString(map);
		     String url= PropertiesUtil.GetValueByKey("jlpsi_url");
		     HttpUtil.sendPost(url, jsonStr);
		   //向进销存发送订单信息 end
		}
		else{
			//确认未通过，修改线下支付信息表的状态为5(确认未通过)，同时修改订单状态为0（待支付）
			OfflinePayment offlinePayment=offlinePaymentService.selectByPrimaryKey(Integer.parseInt(opId));
			//判断是否有图片需要删除
			List<String> offlinePaymentPicUrlList = new ArrayList<>();	
			if(offlinePayment.getPaymentVoucherUrlOne()!=null&&!"".equals(offlinePayment.getPaymentVoucherUrlOne())){
				offlinePaymentPicUrlList.add(offlinePayment.getPaymentVoucherUrlOne());
			}
			if(offlinePayment.getPaymentVoucherUrlTwo()!=null&&!"".equals(offlinePayment.getPaymentVoucherUrlTwo())){
				offlinePaymentPicUrlList.add(offlinePayment.getPaymentVoucherUrlTwo());
			}
			//删除图片
			if(offlinePaymentPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, folderName, offlinePaymentPicUrlList);
			}
			//删除之前添加好的线下支付信息
			offlinePayment.setState(5);
			offlinePayment.setRemitterName("");
			offlinePayment.setRemitterAccount("");
			offlinePayment.setPayeeName("");
			offlinePayment.setPayeeAccount("");
			offlinePayment.setPayeeAccountDepositBank("");
			offlinePayment.setRemittanceAmount(0.0);
			offlinePayment.setPaymentVoucherUrlOne("");
			offlinePayment.setPaymentVoucherUrlTwo("");
			
			OrderTable orderTable=new OrderTable();
			orderTable.setId(Integer.parseInt(oId));
			orderTable.setOrderState(0);
			orderTable.setPayTime(null);
			if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
				result=offlinePaymentService.updateByPrimaryKeySelective(offlinePayment);
			}
		}
		// 添加操作时间
		Date date = new Date();
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		if(result>0){
			
			// 往log表中插入操作数据
			insertLog(operateType,orderNo, Constants.OPERATE_UPDATE, date,
					userIdentifier);
			
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;	
		}
			
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 根据车牌号模糊查询获取最近填写的十个车牌号
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectTenCarId")
	public List<String> selectTenCarId(HttpServletRequest request,String carId){
		return orderTableService.selectTenCarId(carId);
	}
	
	/**
	 * 发货操作
	 * @param request
	 * @param oId 订单id
	 * @param carId 车牌号
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/shippingGoods", method = RequestMethod.POST)
	public JSONObject shippingGoods(HttpServletRequest request, String oId,String carId,String orderNo) throws Exception {
		JSONObject rmsg = new JSONObject();
		//点击发货操作，先往order表中存入车牌号同时更改订单状态为2（待收货）
		//再在订单状态详情表中存入 ‘已发货，物流信息：【车牌号】’
		OrderTable orderTable=new OrderTable();
		orderTable.setCarIdSend(carId);
		orderTable.setOrderState(2);
		orderTable.setId(Integer.parseInt(oId));
		// 添加操作时间
		Date date = new Date();
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		
		if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
			OrderStateDetail orderStateDetail=new OrderStateDetail();
			orderStateDetail.setOrderId(Integer.parseInt(oId));
			orderStateDetail.setOrderStateDetail("已发货，物流信息：【"+carId+"】");
			orderStateDetail.setAddTime(new Date());
			if(orderStateDetailService.insertSelective(orderStateDetail)>0){
				OrderMessage orderMessage=new OrderMessage();
				orderMessage.setOrderStateDetailId(orderStateDetail.getOrderId());
				orderMessage.setStatus(0);
				//插入消息表
				orderMessageService.insertSelective(orderMessage);
				// 往log表中插入操作数据
//				OrderTable  oo=orderTableService.checkOrderDetailsByOrderId(oId);
//				insertLog(operateType,oo.getOrderNo(), Constants.OPERATE_UPDATE, date,userIdentifier);
				
				rmsg.put("success", true);
				rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
				return rmsg;
			}
		}
		
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 售后页面操作
	 * @param request
	 * @param oId 订单id
	 * @param state 状态： 7(已退货退款)，8(已换货)，10(关闭售后)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/afterSaleOperation", method = RequestMethod.POST)
	public JSONObject afterSaleOperation(HttpServletRequest request, String oId,String state,String returnCadId,String sendcarId,String orderNo,String payMode) throws Exception {
		JSONObject rmsg = new JSONObject();
		
		
		//售后页面操作，先往order表中更改订单状态
		//再在订单状态详情表中存入 相应的信息
		OrderTable orderTable=new OrderTable();
		orderTable.setOrderState(Integer.parseInt(state));
		orderTable.setId(Integer.parseInt(oId));
		//特殊处理全部退款
		if(orderTable.getOrderState()==11) {
			//设置退货中
 			orderTable.setReturnState(1);
 			//设置售后中
 			orderTable.setOrderState(6);
 			orderTable.setCarIdChangeReturn(returnCadId);
 			//调用退货接口
 			JSONObject refundResult=new JSONObject();
 			//支付宝
 			 
 			if(Integer.parseInt(payMode)==1) {
 				
 				  refundResult=alipayApi.refund(orderTable.getId());
 		 
 			}
 			//微信
 			else {
 				  refundResult=weChatApi.refund(orderTable.getId());
 			}
 			if(refundResult.get("result_code").equals("FAIL")) {
					rmsg.put("success", false);
					rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
					return rmsg;
				}
		}
		
		
		
		if("7".equals(state)){
			orderTable.setCarIdChangeReturn(returnCadId);
		}
		else if("8".equals(state)){
			orderTable.setCarIdChangeReturn(returnCadId);
			orderTable.setCarIdChangeSend(sendcarId);
		}	
 
		// 添加操作时间
		Date date = new Date();
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
			OrderStateDetail orderStateDetail=new OrderStateDetail();
			orderStateDetail.setOrderId(Integer.parseInt(oId));
			if("7".equals(state)){
				orderStateDetail.setOrderStateDetail("订单已退货退款");
			}
			else if("8".equals(state)){
				orderStateDetail.setOrderStateDetail("订单已换货");
			}
			else if("10".equals(state)){
				orderStateDetail.setOrderStateDetail("售后已关闭");
			}else if("11".equals(state)) {
				orderStateDetail.setOrderStateDetail("订单退货中");
			}		
	       
			orderStateDetail.setAddTime(new Date());
			//修改售后详情状态为已处理
			if(!state.equals("11")) {
				AfterSaleDetail afterSaleDetail = afterSaleDetailService.selectByOrderId(Integer.parseInt(oId));
				afterSaleDetail.setStatus(1);
				afterSaleDetailService.updateByPrimaryKeySelective(afterSaleDetail);
			}
			if(orderStateDetailService.insertSelective(orderStateDetail)>0){
				
				// 往log表中插入操作数据
				insertLog(operateType,orderNo, Constants.OPERATE_UPDATE, date,
						userIdentifier);
				rmsg.put("success", true);
				rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
				return rmsg;
			}
		}
		
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 根据前台的操作，修改收货信息
	 * @param request
	 * @param orderTable
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateConsigneeMsgByOrderId", method = RequestMethod.POST)
	public JSONObject updateConsigneeMsgByOrderId(HttpServletRequest request, OrderTable orderTable) throws Exception {
		JSONObject rmsg = new JSONObject();
	
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
			// 往log表中插入操作数据
			insertLog(operateType,orderTable.getOrderNo(), Constants.OPERATE_UPDATE, new Date(),
					userIdentifier);
				
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;
		}
			

		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}
	
	
	
	/**
	 * 往Log表中添加日志信息
	 * 
	 * @param operateType
	 *            操作类型
	 * @param identifier
	 *            操作对象的编号
	 * @param operateMethod
	 *            操作的方法(添加/删除/修改)
	 * @param date
	 *            操作日期
	 * @param userIdentifier
	 *            操作人编号
	 * @throws Exception
	 */
	public void insertLog(Integer operateType, String identifier, String operateMethod, Date date,
			String userIdentifier) throws Exception {
		log = new Log();
		log.setOperateType(operateType);
		log.setOperateObject(identifier + "(" + operateMethod + ")");
		log.setOperateTime(date);
		log.setOperatorIdentifier(userIdentifier);
		logService.insert(log);
	}
	
	/**
	 * 用户购物返券
	 * @param request
	 * @param orderId 订单id
	 * @throws Exception
	 */
	private void shoppingBackCouponToUser(Integer orderId) throws Exception {
		OrderTable orderTable = orderTableService.selectByPrimaryKey(orderId);
		
		List<OrderDetail> orderDetailList = orderDetailService.selectByOrderId(orderId);

		//获取userId				
		int userId = orderTable.getUserId();
		List<Integer> activityInformationIdList = new ArrayList<>();
		for (int i = 0; i < orderDetailList.size(); i++) {
			
			OrderDetail orderDetail = orderDetailList.get(i);
			int goodsDetailId = orderDetail.getGoodsDetailsId();
			int activityInformationId;
			int goodsSpecificationDetailsId = orderDetail.getGoodsSpecificationDetailsId();
			JSONObject object2 =  getGoodsDetailMsgByGoodsId( goodsDetailId);
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
				
			}
			
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
	
	private JSONObject getGoodsDetailMsgByGoodsId(Integer goodsId) throws Exception {
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
			goodsSpecificationDetailsList=goodsSpecificationDetailsService.getGoodsDetailMsgByGoodsId(goodsId,"","false");
			//再把商品详情放入商品信息中传入移动端
			goodsDetails.setGoodsSpecificationDetails(goodsSpecificationDetailsList);
		}
		
		result.put("resultData", goodsDetails);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
}
