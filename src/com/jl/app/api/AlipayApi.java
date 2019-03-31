package com.jl.app.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jl.JlPay;
import com.jl.mis.model.OrderTable;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.utils.SignUtils;

@Controller
@RequestMapping("/AlipayApi/")
public class AlipayApi {
	
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private JlPay pay;
	
	/** 支付宝支付业务：入参app_id
	 * 沙箱：2016091200495350
	 * 正式：2018020702155294
	 * */
	public static final String APPID = "2018020702155294";

	/** 支付宝账户登录授权业务：入参pid值
	 * 沙箱：2088102175320322
	 * 正式：2088121833489937
	 * */
	public static final String PID = "2088121833489937";
	
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "111";

	/**
	 * 沙箱：MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCB9KTty9hEkevSeAyYDYMh11VWoBOp0usoV2Gx8z51Dkie1moVaE2Y/PmKexoaBU+B3cWBstSFL14p069aUoo0iCF0SPqICUlhLtSFBk5cTB8Stc2pUuumyKqnfyKaiYbZs6L7FVGss6Zg9kqAwDm0O5n9DFwvGNLRAvaT0eK2+23NyWdiZKzelseU0gLOmnG+NZUv4k9bFq7xrAQt9lUK3ifBRYjze2XkU9KpdW9mAzBEGhb1/Y36VZ6JXj8gcPh1d92Yd89IxJu3OBXOkyNRDJSQN0b6+uJgpjzPMh+5rhZV2VnGUVpS1azRyUBjcOWgOUglhS09gX6JnW0ecg2zAgMBAAECggEASc6NITUltgP+IEHoPS9na3/lytYNdOUCg53lMXDJ5ydenT9/bnsXny0F+N1jfKXJeyNeOgQ76Qx2+WBtdens7lrmTSnph/tmPpifQJHNTWQ6PaEdle4vQkEzkv+Ewqoa+Wepx9pa0bASbX1T4P3LiA5Vrb8oV+NMUELTki6hCXTBv5iibpDfwvPYwHqbsLVybSEHiSDKJKshaCy0ePhgthpqpimyqq1K6VuNd+/BUrnx42HHTdXAQpi1f/gWlE5Qpv05u1oXiptARDNVD1qcxQsO+AEfkHr9ubt4NB4azanfltmlI+FKMhHOe4KSiLPFtx0O78W4ttMZIRQr3IaUuQKBgQC3LwXdZkCVrc/Dd5VSWpRR/iW3V2gSuwr/pR8HSPL3Dqb2FRu6zXg2XtBCWAh1A82L4rgK6t0hH2Z1YtjjAHoobP3w2JmWpKb+l8uS+n1Uinrqm7pAmNExi/b9XA6TsvgGqijd4SWNQUmwEXuCm8pqCzE6PO51i00uNfvkKD0M1wKBgQC1nRN8nrJSb/sFfM8cQtpy5jsYmhYdQJDMRDGCF9BbPNbBM75lLufgZrt+2hR1YG7nG2Ay9f9gZaWL7OFvw+esv2nxT/lFxBzg8unLw2GUe0IC9fBgSVD3+iBMn6gvxn66LliXlrEnqTQAkPMLAJqE8ZTJzixefJ7TBKTSsbpuhQKBgFR+XxEpOf8QZfW+MeUDnMhZFuKDcaC6H6+dZTuB/XfRjVeSkvDOF3XUs/D2FCiNMYEleUin3omJp0Pb7Th/ntkItPUjo2xl1BU4xRCtc6PMIDgYzoc/NbbJ3YotrReZhGfmkuUNFboa9Dc/SR4MaAgT04gOVNto/pejFOW/XbGPAoGAAoIFx93FJqBHPh8oGQLPU3Dcdg2Cqqq+lSOH73z2spvXd1mIkosgRpgElM7cI3bOcUCOhoWhHiqpK5J81ndFFryZ8OwizBTWNUEFe3vaOBuvoKzWwe2hQZthoM0/nFFIZLfJH2bEUYDjp/JYeNlsSNOxM0w2jqN36gARaptrsKUCgYEAqbMZzxN+5883tojGF5z0vlJqw7sSXUEaXgx5EMsIokYXzoXGEuBP3mji+YYVDWQQdiApw5ifj/vWULCq+zHGb1AUy/SAryjSwv/30YDALkDCNMmu9/WOBd4zP3KdaFHFO7kN8oCa2DLH/qSlN/Mbsk3awqW//OykE/P/7raGysg=
	 * 正式：MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDKW2Mo4foKW1pvXmD8uVlKTqHojw5DjVsimust4RMvrNoaHOoZ+BXB4cFye5r+hUjZwChn5wVbkIa75UPSJiehtMNSxbmR/WMY8XUsLtlEpsZCS7wGzglc1hpcBCk7eG5u+05vfcrcMLJS+glxawrBMnvpgZq0XYjZLvyREnyQqYXdBq52yaZWZ+d7asxW6CTwfPaQ4rquxdW0bxBbwVy/88vTMkPVnNf9ZvR1RP+WCWhwcOfsYPB2a648r+vIGRUogwqTDiFaJnLHqkPjL9sL7Jl3bRoHH+nNTl7k1dA1FvUoNHVkPQSqHQH5hkwyco4iIwiNhDiCP54/klGDp7HjAgMBAAECggEAKIXMga9fJxvMLGk1+uYTH3JHsg6TyKtSCMgFOJbOBMoUkw4fQzwO/1h6KLbf/wAp6pE0hjoZNJDoqk/rGPZTEWrlgnneA4I+igwwbryi/cB4C2anLKWt8k1d0fdzWllvpAHnXyRNr3KBA2TW50OMT8wQTF6L4HqqoPNr2JFfKIiu44jt2qYY8sZBysW0gWo4WyJBWtG6v/cHlViNDwrrpcQ/NZpJ0G5pJ1ZWVWdaFg0YViiSjG2+2BqJ1XHS9IiWgzwgYggkfjxL+dMqLRGl9HUtCgT25oiGpA3qB4bvZJ3kWdlbQzTXI+1w7kFlhPhBPwsEQMWU67e1s6wAipG+wQKBgQDva0Vk9kS/bSjQhqGUtAXpp48HwjDdgRHwRl1u7gjf7P5q0g18s4IeojKMz6VYRHpOOWuHcoCIjFQG73YiyKnFd3Q8+tDzJCLggOdaCSqNdWYSvF1OBUqNLrHH30C0cjerR6aEqZP7abVkBj6CJZG2+wS/uWNol5kOKEe8hyEb+wKBgQDYXwhXPh7uYm9MTiJp+sPEbSj8/Ktbd8qwNad4PUR9b1KZiViZC6D5YXTmIWqbRn7TnesRksLFRjaDfQ/MxmhOwj/3X70XnYmnOUeiTFSsbOCJthtnmUqITWB4VHJoi+rlOt/EUmq59pTuykWb5DFRqm3iOtnutgyrfWSPZYC1OQKBgCnJAQ2D5a+7L3plZOH9IWMMOWxTOhAOrMDQpxHrPWVHiYe/LVdsRPNvv37P5r2nBemNH/U9yK0MQMYNGQMtHmQ2WvUhmUI2NlgUZ0xReTnDGZN/iJFLXx2Q3rpRfot/u7OLB8bzib5BTsUsSdCRGgNjgAquImlou++6m/qNYvvtAoGBAJ1Yxq0DHOVIqh14eKhPVwxCQdtwN4EF5V0tN4NGMsdxLEVBTy/E+CRCjgtI48jVhCeKIMcfzgo+HCiUyuekpXgY5QGVGuLQiqkUC5boIW9eog8jYlx9amkis/PtnWGP1psqrE2NjdV8jyPz7pkGYiM8aDprh86Dj0Uqo/Vv4CBpAoGADQsswcg0lp5AE8D6zc9WCIR6LR/QUelGPocW48nqUT7P5mKkcNyMlkuAjIinQotQUiwVw0/Rx9M+Y8gD438JjbwOzCN1BnP+T/T6NF4MS+m5pCOM7Di207V9ny5Lv1cIsOabu8r22G6+X0Ml1bfzQQFFSwzNnsxUiq69HuwRE8c=
	 *
	 */
	public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDKW2Mo4foKW1pvXmD8uVlKTqHojw5DjVsimust4RMvrNoaHOoZ+BXB4cFye5r+hUjZwChn5wVbkIa75UPSJiehtMNSxbmR/WMY8XUsLtlEpsZCS7wGzglc1hpcBCk7eG5u+05vfcrcMLJS+glxawrBMnvpgZq0XYjZLvyREnyQqYXdBq52yaZWZ+d7asxW6CTwfPaQ4rquxdW0bxBbwVy/88vTMkPVnNf9ZvR1RP+WCWhwcOfsYPB2a648r+vIGRUogwqTDiFaJnLHqkPjL9sL7Jl3bRoHH+nNTl7k1dA1FvUoNHVkPQSqHQH5hkwyco4iIwiNhDiCP54/klGDp7HjAgMBAAECggEAKIXMga9fJxvMLGk1+uYTH3JHsg6TyKtSCMgFOJbOBMoUkw4fQzwO/1h6KLbf/wAp6pE0hjoZNJDoqk/rGPZTEWrlgnneA4I+igwwbryi/cB4C2anLKWt8k1d0fdzWllvpAHnXyRNr3KBA2TW50OMT8wQTF6L4HqqoPNr2JFfKIiu44jt2qYY8sZBysW0gWo4WyJBWtG6v/cHlViNDwrrpcQ/NZpJ0G5pJ1ZWVWdaFg0YViiSjG2+2BqJ1XHS9IiWgzwgYggkfjxL+dMqLRGl9HUtCgT25oiGpA3qB4bvZJ3kWdlbQzTXI+1w7kFlhPhBPwsEQMWU67e1s6wAipG+wQKBgQDva0Vk9kS/bSjQhqGUtAXpp48HwjDdgRHwRl1u7gjf7P5q0g18s4IeojKMz6VYRHpOOWuHcoCIjFQG73YiyKnFd3Q8+tDzJCLggOdaCSqNdWYSvF1OBUqNLrHH30C0cjerR6aEqZP7abVkBj6CJZG2+wS/uWNol5kOKEe8hyEb+wKBgQDYXwhXPh7uYm9MTiJp+sPEbSj8/Ktbd8qwNad4PUR9b1KZiViZC6D5YXTmIWqbRn7TnesRksLFRjaDfQ/MxmhOwj/3X70XnYmnOUeiTFSsbOCJthtnmUqITWB4VHJoi+rlOt/EUmq59pTuykWb5DFRqm3iOtnutgyrfWSPZYC1OQKBgCnJAQ2D5a+7L3plZOH9IWMMOWxTOhAOrMDQpxHrPWVHiYe/LVdsRPNvv37P5r2nBemNH/U9yK0MQMYNGQMtHmQ2WvUhmUI2NlgUZ0xReTnDGZN/iJFLXx2Q3rpRfot/u7OLB8bzib5BTsUsSdCRGgNjgAquImlou++6m/qNYvvtAoGBAJ1Yxq0DHOVIqh14eKhPVwxCQdtwN4EF5V0tN4NGMsdxLEVBTy/E+CRCjgtI48jVhCeKIMcfzgo+HCiUyuekpXgY5QGVGuLQiqkUC5boIW9eog8jYlx9amkis/PtnWGP1psqrE2NjdV8jyPz7pkGYiM8aDprh86Dj0Uqo/Vv4CBpAoGADQsswcg0lp5AE8D6zc9WCIR6LR/QUelGPocW48nqUT7P5mKkcNyMlkuAjIinQotQUiwVw0/Rx9M+Y8gD438JjbwOzCN1BnP+T/T6NF4MS+m5pCOM7Di207V9ny5Lv1cIsOabu8r22G6+X0Ml1bfzQQFFSwzNnsxUiq69HuwRE8c=";
	public static final String RSA_PRIVATE = "";
	
	
	public static final String PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl8qVYPr9zr0tBlnbayvNI/4aQGhTsMXJwn6Xck7YC4vRi586FAullTJrK2en6VnUwQDrIX1ASR80Utn7ayFhD13MgU6c+5ittBQQV6bfr5/Go/j0MoD5k26D3/Xkn0qTyMxVzJBweldx6GXmB1oQ0K+505FtjHudteJRwUD5XvgIHd/E2TRfosPNiXap3L8+MTWnO0nRq8vTdoRvmlVWNOALB/KvnDozO3Sky14/82DXPdAmSHt/k2ZgDZkD7uVP7TzaQ46AWOGehjh53JKP1cgEbfz/G/f4UkBILQZXOmuopRRqRlphMWtJeTsznGXvpXb17z0txx2bth7vFYGpswIDAQAB";
	
	//获取授权信息接口。
	@ResponseBody
	@RequestMapping(value = "getAuthInfo", method = RequestMethod.GET)
	public JSONObject getAuthInfo(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap =buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		 
		result.put("resultData", authInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		
		return result;
	}
	
	
	
	
	
	//获取订单支付信息接口
	@ResponseBody
	@RequestMapping(value = "getOrderInfo", method = RequestMethod.POST)
	public JSONObject getOrderInfo(HttpServletRequest request,@RequestParam("orderId") Integer  orderId) throws Exception {
		JSONObject result = new JSONObject();
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
		
		Map<String, String> params = buildOrderParamMap(APPID, rsa2, orderId,orderMsg.getOrderNo(),orderMsg.getOrderPresentPrice(),orderMsg.getPostage());
		String orderParam = buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign =getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;

		
		result.put("resultData", orderInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	
	//获取订单支付是否成功接口
	@ResponseBody
	@RequestMapping(value = "getOrderState", method = RequestMethod.POST)
	public Object getOrderState(@RequestParam("orderId") Integer  orderId) {
		JSONObject result= new JSONObject();
		result.put("code", 4000);
		result.put("msg", "支付失败");
		try {
			OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APPID,RSA2_PRIVATE,"json","utf-8",PUBLIC_KEY,"RSA2");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{" +
			"\"out_trade_no\":\""+orderMsg.getOrderNo()+"\"," +
			"\"trade_no\":\"\"" +
			"  }");
			AlipayTradeQueryResponse response = alipayClient.execute(request);
 			if(response.getTradeStatus().equals("TRADE_SUCCESS")){
 			
			 Double  totalFee =Double.parseDouble(response.getTotalAmount());
				// double totalFee=new BigDecimal(Double.parseDouble(response.getTotalAmount())).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();

				//修改订单状态为代收货
				pay.pay(orderMsg.getOrderNo(), totalFee,1);
				//支付成功
				result.put("resultData", response.getBody());
				result.put("code", 200);
				result.put("msg", "支付成功");
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	//申请退款接口
	public JSONObject refund(Integer  orderId) throws Exception {
		JSONObject result = new JSONObject();
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
 			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APPID,RSA2_PRIVATE,"json","utf-8",PUBLIC_KEY,"RSA2");
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" +
					"\"out_trade_no\":\""+orderMsg.getOrderNo()+"\"," +
					"\"trade_no\":\"\"," +
					"\"refund_amount\":"+(orderMsg.getOrderPresentPrice()+orderMsg.getPostage())+"," +
					"\"refund_currency\":\"\"," +
					"\"refund_reason\":\"正常退款\"," +
					"\"out_request_no\":\""+orderMsg.getOrderNo()+"\"," +
					"\"operator_id\":\"\"," +
					"\"store_id\":\"\"," +
					"\"terminal_id\":\"\"," +
					"      \"goods_detail\":[]" +
					"  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
				if(response.isSuccess()) {
					result.put("result_code", "SUCCESS");
				}else {
					result.put("result_code", "FAIL");
					
				}
		return result;
	}
	
	//退款查询
	public JSONObject refundQuery(Integer  orderId) throws Exception {
		JSONObject result = new JSONObject();
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
 			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",APPID,RSA2_PRIVATE,"json","utf-8",PUBLIC_KEY,"RSA2");
 			AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
 			request.setBizContent("{" +
 					"\"trade_no\":\"\"," +
 					"\"out_trade_no\":\""+orderMsg.getOrderNo()+"\"," +
 					"\"out_request_no\":\""+orderMsg.getOrderNo()+"\"" +
 					"  }");
 			AlipayTradeFastpayRefundQueryResponse  response = alipayClient.execute(request);
				if(response.isSuccess()) {
					result.put("result_code", "SUCCESS");
				}else {
					result.put("result_code", "FAIL");
					
				}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 构造支付订单参数列表
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	
	
	public static Map<String, String> buildOrderParamMap(String app_id, boolean rsa2,int orderid,String OrderNo,double PresentPrice,double Postage) {
		
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", app_id);
		DecimalFormat df = new DecimalFormat("#.##");
		 Double money=Double.parseDouble(df.format(PresentPrice+Postage));
		 //  Double money=new BigDecimal(PresentPrice+Postage).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
	 
		 keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+money+"\",\"subject\":\"君霖食品订单\",\"body\":\"君霖食品订单:"+OrderNo+"\",\"out_trade_no\":\"" + OrderNo + "\"}");
 
		keyValues.put("charset", "utf-8");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);
		
		keyValues.put("timestamp", key);

		keyValues.put("version", "1.0");
		
		return keyValues;
	}
	
	
	/**
	 * 构造授权参数列表
	 * 
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id, boolean rsa2) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值LOGIN登录  ，AUTHACCOUNT授权
		keyValues.put("auth_type", "AUTHACCOUNT");
		
		/*//接口方法method
		keyValues.put("method", "alipay.open.auth.sdk.code.get");*/

		// 签名类型
		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		return keyValues;
	}
	
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	/**
	 * 拼接键值对
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * 对支付参数信息进行签名
	 * @param map
	 *            待签名授权信息
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}
	
 
}
