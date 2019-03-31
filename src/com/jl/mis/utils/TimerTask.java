package com.jl.mis.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jl.app.api.AlipayApi;
import com.jl.app.api.WeChatApi;
import com.jl.mis.model.AfterSaleDetail;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.ShoppingCart;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.AdvertisementInformationService;
import com.jl.mis.service.AfterSaleDetailService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderStateDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.ShoppingCartService;
import com.jl.mis.service.UserCouponsService;

/**
 * 类名称：TimerTask 类描述：定时器任务
 * 
 * @author 景雅倩
 * @date 2017-12-05 下午3:08:23
 */
@Component
public class TimerTask {
	@Autowired
	private ActivityInformationService activityInformationService;

	@Autowired
	private UserCouponsService userCouponsService;
	@Autowired
	private AdvertisementInformationService advertisementInformationService;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private WeChatApi	weChatApi;
	@Autowired
	private AlipayApi	alipayApi;
	@Autowired
	private OrderStateDetailService orderStateDetailService;
	@Autowired
	private AfterSaleDetailService afterSaleDetailService;
	/**
	 * 每天0点执行任务 活动自动上下线 活动上线时自动向APP发送活动消息 活动&优惠券自动过期
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void myTask() throws Exception {
		Date startTime = new Date();

		// 用户优惠券自动过期
		userCouponsService.updateUserCouponsToInvalid();
		// 活动自动下线
		activityInformationService.updateActivityInformationToOffline();
		// 活动自动过期
		activityInformationService.updateActivityInformationToInvalid();
		// 活动自动上线
		activityInformationService.updateActivityInformationToOnline();
		// 限时抢购自动上线
		//advertisementInformationService.updateOneXSQGToEffect();

		Date endTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sdf.format(startTime) + "~" + sdf.format(endTime) + "定时任务执行完成！");

	}

	@Scheduled(cron = "1 * * * * ? ")
	public void OrderTask() throws Exception {
		orderTableService.autoCancleOrderByNoIncludedPostage();
		orderTableService.autoCancleOrderByIncludedPostage();
	}
	
	@Scheduled(cron = "0/5 * * * * ?")
	public void returnTask() throws Exception {
 
		List<OrderTable> list=orderTableService.selectReturnOrder();
		for (OrderTable orderTable : list) {
			JSONObject result=new JSONObject();
			if(orderTable.getPayMode()==1) {
				//支付宝
			  result=	alipayApi.refundQuery(orderTable.getId());
			}else {
				//微信
				result=	weChatApi.refundQuery(orderTable.getId());
			}
			System.out.println(result);
			if(result.getString("result_code").equals("SUCCESS")) {
				//设置退款
				orderTable.setReturnState(2);
				orderTable.setOrderState(7);
				if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
					//设置已退款退货
					OrderStateDetail orderStateDetail=new OrderStateDetail();
					orderStateDetail.setOrderId(orderTable.getId());
					orderStateDetail.setOrderStateDetail("订单已退货退款");
					orderStateDetail.setAddTime(new Date());
					orderStateDetailService.insertSelective(orderStateDetail);
					//售后详情状态设置已处理
					AfterSaleDetail afterSaleDetail = afterSaleDetailService.selectByOrderId(orderTable.getId());
					afterSaleDetail.setStatus(1);
					afterSaleDetailService.updateByPrimaryKeySelective(afterSaleDetail);
				}
				
			}
		
		}
			
	}

}