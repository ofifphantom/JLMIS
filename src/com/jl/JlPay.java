package com.jl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jl.app.api.WeChatApi;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.SalesPlanOrder;
import com.jl.mis.model.SalesPlanOrderCommodity;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderStateDetailService;
import com.jl.mis.service.OrderTableService;
import com.util.HttpUtil;
import com.util.PropertiesUtil;

@Service
public  class JlPay {
  
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private OrderStateDetailService orderStateDetailService;
	@Autowired
	private ActivityInformationService activityInformationService;
	@Autowired
	private OrderDetailService	orderDetailService;
	private static Logger logger = Logger.getLogger(WeChatApi.class);

	/**
	 * 修改订单状态为代收货
	 * @param orderId
	 * @return
	 */
	  public  synchronized  boolean     pay(String orderNo,double totalFee,Integer payMode) {
		  boolean sucess = false;
		try {
			logger.info("进入修改订单方法===========");
				OrderTable orderMsg = orderTableService.selectByOrderNo(orderNo);
 			// double orderTotalFee=new BigDecimal(orderMsg.getOrderPresentPrice()+orderMsg.getPostage()).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
 			DecimalFormat df = new DecimalFormat("#.##");
 			 double orderTotalFee=Double.parseDouble(df.format(orderMsg.getOrderPresentPrice()+orderMsg.getPostage()));
 			 
 	
  			 
			    if(orderMsg.getOrderState()==0&&orderMsg.getPayType()==0&&(orderTotalFee==totalFee)) {      
			    	
			    	orderMsg.setOrderState(1);
			    	orderMsg.setPayMode(payMode);
			    	orderMsg.setPayTime(new Date());
			    	 orderTableService.updateByPrimaryKey(orderMsg);
			    	 logger.info("订单已修改为待收货===========");
			    	 //添加状态详情
			    	 OrderStateDetail orderStateDetail = new OrderStateDetail();
					orderStateDetail.setAddTime(new Date());
					orderStateDetail.setOrderId(orderMsg.getId());
					orderStateDetail.setOrderStateDetail("订单支付成功");
					orderStateDetailService.insertSelective(orderStateDetail);
		        
					//向进销存发送订单信息
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
					
					
					
			    }
			   
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return sucess;  
	  }
}
