package com.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Md5Util {
	
	
	
	
	
	 public static String getMD5(Map<String,Object> request) {
 	        try {
	        	//map 转string
	        	StringBuffer sb=new StringBuffer();
	            Set<Entry<String, Object>> set = request.entrySet();  

	            for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {  
	                Map.Entry<String, Object> me = it.next();  
	                sb.append(me.getKey()+"="+me.getValue()+"&"); 
	            }  
	     
	        	 String str=sb.toString().trim();
	        	 
	        	 //去除最后的&
	        	 String key=PropertiesUtil.GetValueByKey("app_key");
	        	 //增加商户key
	        	 str=str+"key="+key;
	        	 
	        	
	        	
	            // 生成一个MD5加密计算摘要
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            

	            // 计算md5函数
	         //   str="appid=wx037454184aee70e9&body=test&mch_id=012012&nonce_str=9fn5p2vy2hiuvdiaiu0pvkyvyr2zzrz2&notify_url=http://www.weixin.qq.com/wxpay/pay.php&out_trade_no=0012&spbill_create_ip=127.0.0.1&total_fee=100&trade_type=APP&key=192006250b4c09247ec02edce69f6a2d";
	            md.update(str.getBytes("UTF-8"));
	            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	            return new BigInteger(1, md.digest()).toString(16).toUpperCase();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "";
	        }
	    }
	   public static void main(String[] args) {
		   String params="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA&key=cbdab39fdc019dac230045a25e745ae1";
	      //  String md5 = getMD5(params).toUpperCase();
	    //    System.out.println(md5);
	    }
}
