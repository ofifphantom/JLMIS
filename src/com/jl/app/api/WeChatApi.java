package com.jl.app.api;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.JlPay;
import com.jl.mis.model.OrderTable;
import com.jl.mis.service.OrderTableService;
import com.util.HttpUtil;
import com.util.MapUtil;
import com.util.Md5Util;
import com.util.PropertiesUtil;
import com.util.RandomUtil;

/**
 * 微信支付api接口
 * @author guole
 *
 */
@Controller
@RequestMapping("/WeChatApi/")
public class WeChatApi {
	
	private static Logger logger = Logger.getLogger(WeChatApi.class);

	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private JlPay  pay;
	/**
	 * 获取订单支付信息接口
	 * 	@ResponseBody
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getOrderInfo", method = RequestMethod.POST)
	public JSONObject getOrderInfo(@RequestParam("orderId") Integer  orderId) throws Exception {
 		JSONObject result = new JSONObject();
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
		String appid="";
 
		String xml=getOrderInfoParms(orderMsg.getOrderNo(),orderMsg.getOrderPresentPrice(),orderMsg.getPostage());
         //向微信调用统一下单接口
        String  url="https://api.mch.weixin.qq.com/pay/unifiedorder";
        String resultXml=HttpUtil.sendXMLDataByPost(url, xml,false);
        JSONObject weixinResult=xmlToJson(resultXml);
        JSONObject resultData=twoEncryption(weixinResult);
        result.put("resultData", resultData);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	/**
	 * 微信支付结果通知
	 * @param request
	 * @return
	 * @throws Exception
	 */
		@ResponseBody
		@RequestMapping(value = "payResultNotice", method = RequestMethod.POST)
		public String payResultNotice(HttpServletRequest request) throws Exception {
 	 		Map<String, String> mapRequest=xmlToMap(request);
 	 		Map<String, Object> mapResult=new HashMap<String,Object>();

 	 		
	 		String return_code=mapRequest.get("return_code");
	 		String return_msg=mapRequest.get("return_msg");
	 		logger.info("微信返回的结果=========="+mapRequest);
	 		if(return_code.equals("SUCCESS")) {
	 			String orderNo=mapRequest.get("out_trade_no");
	 		//	Integer totalFee=Integer.parseInt(mapRequest.get("total_fee"));	
				// double totalFee=new BigDecimal(Integer.parseInt(mapRequest.get("total_fee"))/100).setScale(2,BigDecimal.ROUND_DOWN).doubleValue();
				
	 			double totalFee=Integer.parseInt(mapRequest.get("total_fee"))/100.0;
	 			logger.info("totalFee=========="+totalFee);
	 			//支付状态设置
	 			pay.pay(orderNo,totalFee,2);
	 			logger.info("微信支付结果通知支付1111成功============="+mapRequest);
	 		}else {
	 			logger.info("微信支付结果通知支付失败============="+return_msg);
	 		}
	 		mapResult.put("return_code", "SUCCESS");
	 		mapResult.put("return_msg", "OK");
			String result=MapUtil.mapXmlString(mapResult);
			return result;
		}
		
		
		/**
		 * 订单查询
		 * @param orderId
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping(value = "orderQuery", method = RequestMethod.POST)
		public JSONObject orderQuery(@RequestParam("orderId") Integer  orderId) throws Exception {
	 		JSONObject result = new JSONObject();
			OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
			result.put("code", 4000);
		result.put("msg", "支付失败");
			String appid="";
		 
		String xml=getOrderQueryParms(orderMsg.getOrderNo());
	        String  url="https://api.mch.weixin.qq.com/pay/orderquery";
        String resultXml=HttpUtil.sendXMLDataByPost(url, xml,false);    
        JSONObject resultData=xmlToJson(resultXml);
	        String trade_state=resultData.getString("trade_state");	
	        if(trade_state.equals("SUCCESS")) {
	           	String out_trade_no=resultData.getString("out_trade_no");	
	 			//支付状态设置
				 double totalFee=resultData.getInteger("total_fee")/100.0;
				 
	 			pay.pay(out_trade_no,totalFee,2);
	 			result.put("code", 200);
			result.put("msg", "支付成功");
      }
	     
	        //logger.info("订单查询接口结果============="+resultData);
 
	         result.put("resultData", resultData);
	
			return result;
		}
		
		
		/**
		 * 申请退款
		 * @param orderId
		 * @return
		 * @throws Exception
		 */
 
		public   JSONObject refund( Integer  orderId) throws Exception {
 			OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
			String xml=getRefundParms(orderMsg.getOrderNo(),orderMsg.getOrderPresentPrice(),orderMsg.getPostage());
	         //向微信调用申请退款接口
	        String  url="https://api.mch.weixin.qq.com/secapi/pay/refund";
	        String resultXml=HttpUtil.sendXMLDataByPost(url, xml,true);    
	         JSONObject resultData=xmlToJson(resultXml);
		 	  logger.info("申请退款接口结果============="+resultData);
			return resultData;
		}
		
		/**
		 * 查询退款
		 * @param orderId
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping(value = "refundQuery", method = RequestMethod.POST)
		public JSONObject refundQuery(@RequestParam("orderId") Integer  orderId) throws Exception {
 			OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
			JSONObject result=new JSONObject();
			String xml=getRefundQueryParms(orderMsg.getOrderNo());
	         //向微信调用查询退款接口
	        String  url="https://api.mch.weixin.qq.com/pay/refundquery";
	        String resultXml=HttpUtil.sendXMLDataByPost(url, xml,false);    
	         JSONObject resultData=xmlToJson(resultXml);
		 	  logger.info("查询退款接口结果============="+resultData);
             if(resultData.getString("return_code").equals("SUCCESS")&&resultData.getString("result_code").equals("SUCCESS")&&resultData.getString("refund_status_0").equals("SUCCESS")) {
            	 result.put("result_code", "SUCCESS");
             }else {
            	 result.put("result_code", "FAIL");
             }
			return result;
		}
		
 	/**
	 * 微信支付统一调用接口参数
	 * @param orderNo
	 * @param presentPrice
	 * @param postage
	 * @return
	 */
	private  String getOrderInfoParms(String orderNo,double presentPrice,double postage) {
		TreeMap <String,Object> request=new TreeMap<String,Object>();

		String result="";
  
		//应用ID	微信开放平台审核通过的应用APPID
		String appid=PropertiesUtil.GetValueByKey("appid");
		request.put("appid",appid);
		//商户号	mch_id	
		request.put("mch_id",PropertiesUtil.GetValueByKey("mch_id"));
		//随机字符串	nonce_str
		request.put("nonce_str",RandomUtil.getRandomStr(32));
		//商品描述	body	
		request.put("body","订单号："+orderNo);
	//	商户订单号	out_trade_no	
		request.put("out_trade_no", orderNo); 
	//	总金额	元换算成分
		DecimalFormat df = new DecimalFormat("#.##");
		 Double money=Double.parseDouble(df.format(presentPrice+postage));
		 int total_fee=new Double((money)*100).intValue();
		request.put("total_fee", total_fee);
	//	终端IP	spbill_create_ip	
		request.put("spbill_create_ip","127.0.0.1");
		//通知地址	notify_url	
		String notify_url=PropertiesUtil.GetValueByKey("notify_url");
		request.put("notify_url",notify_url);
		//交易类型	trade_type	
		request.put("trade_type","APP");
        System.out.println("treeMap1="+request);  
        //签名sign
		String sign=Md5Util.getMD5(request);
       request.put("sign", sign);
       //转换xml
       result=MapUtil.mapXmlString(request);
		return result;
	}
	/**
	 * 微信二次加密
	 * @return
	 */
	private JSONObject twoEncryption(JSONObject weixinResult) {
		JSONObject result=new JSONObject();
		//应用ID	
		result.put("appid",weixinResult.get("appid"));
		//partnerid
		result.put("partnerid",weixinResult.get("mch_id"));
		//预支付交易会话ID
		result.put("prepayid",weixinResult.get("prepay_id"));
		//扩展字段
		result.put("package","Sign=WXPay");
		//随机字符串
		result.put("noncestr",RandomUtil.getRandomStr(32));
 		//时间戳
		String timestamp= Integer.valueOf(String.valueOf(new Date().getTime()/1000)).toString();  
		result.put("timestamp",timestamp);
		//json转map
		TreeMap<String,Object> treeMap=new TreeMap<>();
		treeMap.put("appid",result.get("appid"));
		treeMap.put("partnerid",result.get("partnerid"));
		treeMap.put("prepayid",result.get("prepayid"));
		treeMap.put("package",result.get("package"));
		treeMap.put("noncestr",result.get("noncestr"));
		treeMap.put("timestamp",result.get("timestamp"));
		//partnerid
	    //签名sign
		String sign=Md5Util.getMD5(treeMap);
		result.put("sign", sign);
 
	return result;
	}
	
	 /**
	  * 微信支付查询订单接口参数
	  * @param orderNo
	  * @return
	  */
	private  String getOrderQueryParms(String orderNo) {
		TreeMap <String,Object> request=new TreeMap<String,Object>();

		String result="";
		//应用ID	微信开放平台审核通过的应用APPID
	   String appid=PropertiesUtil.GetValueByKey("appid");
		request.put("appid",appid);
		//商户号	mch_id	
		request.put("mch_id",PropertiesUtil.GetValueByKey("mch_id"));
	//	商户订单号	out_trade_no	
		request.put("out_trade_no", orderNo); 
		//随机字符串	nonce_str
		request.put("nonce_str",RandomUtil.getRandomStr(32));
        //签名sign
		String sign=Md5Util.getMD5(request);
       request.put("sign", sign);
       //转换xml
       result=MapUtil.mapXmlString(request);
		return result;
	}
	
 
	/**
	 * 微信支付申请退款接口参数
	 * @param orderNo
	 * @return
	 */
	private  String getRefundParms(String orderNo,double presentPrice,double postage) {
		TreeMap <String,Object> request=new TreeMap<String,Object>();
		String result="";
		//应用ID	微信开放平台审核通过的应用APPID
		request.put("appid",PropertiesUtil.GetValueByKey("appid"));
		//商户号	mch_id	
		request.put("mch_id",PropertiesUtil.GetValueByKey("mch_id"));
	//	商户订单号	out_trade_no	
		request.put("out_trade_no", orderNo); 
		//商户退货单号
		request.put("out_refund_no",orderNo); 
 //		总金额	元换算成分
		DecimalFormat df = new DecimalFormat("#.##");
		Double money=Double.parseDouble(df.format(presentPrice+postage));
		int total=new Double((money)*100).intValue();
		//订单金额
		request.put("total_fee",total); 
		//退货金额
		request.put("refund_fee",total);
		//随机字符串	nonce_str
		request.put("nonce_str",RandomUtil.getRandomStr(32));
        //签名sign
		String sign=Md5Util.getMD5(request);
       request.put("sign", sign);
       //转换xml
       result=MapUtil.mapXmlString(request);
		return result;
	}
	
	
	/**
	 * 微信支付查询退款接口参数
	 * @param orderNo
	 * @return
	 */
	private  String getRefundQueryParms(String orderNo) {
		TreeMap <String,Object> request=new TreeMap<String,Object>();
		String result="";
		//应用ID	微信开放平台审核通过的应用APPID
		request.put("appid",PropertiesUtil.GetValueByKey("appid"));
		//商户号	mch_id	
		request.put("mch_id",PropertiesUtil.GetValueByKey("mch_id"));
	//	商户订单号	out_trade_no	
		request.put("out_trade_no", orderNo); 
		//随机字符串	nonce_str
		request.put("nonce_str",RandomUtil.getRandomStr(32));
        //签名sign
		String sign=Md5Util.getMD5(request);
       request.put("sign", sign);
       //转换xml
       result=MapUtil.mapXmlString(request);
		return result;
	}
	
  /**
   * 微信支付 获取xml数据
   * @param request
   * @return
   * @throws IOException
   */ 
   public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException{
       Map<String, String> map = new HashMap<String, String>();
       SAXReader reader = new SAXReader();
       
       InputStream ins = null;
       try {
           ins = request.getInputStream();
       } catch (IOException e1) {
           e1.printStackTrace();
       }
       Document doc = null;
       try {
           doc = reader.read(ins);
           Element root = doc.getRootElement();
           
           List<Element> list = root.elements();
           
           for (Element e : list) {
               map.put(e.getName(), e.getText());
           }
           
           return map;
       } catch (DocumentException e1) {
           e1.printStackTrace();
       }finally{
           ins.close();
       }
       
       return null;
   }
   /**
    * xml转json
    * @param xml
    * @return
    * @throws IOException
    */
   public static JSONObject xmlToJson(String xml) throws IOException{
	   Document doc;
	   JSONObject resultData=new JSONObject();

	try {
		doc = (Document)DocumentHelper.parseText(xml);
	       Element root=doc.getRootElement();
         List<Element> list = root.elements();
	           
	           for (Element e : list) {
	        	   resultData.put(e.getName(), e.getText());
	           }
	} catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}        
       return resultData;
   }
   

}
