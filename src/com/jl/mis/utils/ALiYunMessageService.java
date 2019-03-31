package com.jl.mis.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class ALiYunMessageService {
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAICNr1sfd3dMqz";
    static final String accessKeySecret = "9LkV14sUdYtGeoHtxZCVSGTxUgW0q0";
    
  //阿里云短信接口
  	public static SendSmsResponse sendSms(String code,String telphone,String goodsName,String goodsNum) {
  		if(goodsName.length()>20){
  			goodsName=goodsName.substring(0, 14);
  			goodsName+="...";
  		}
  		System.out.println("我执行了ALiYunMessageService phone:"+telphone+" goodsName:"+goodsName+" goodsNum:"+goodsNum);
          //可自助调整超时时间
          System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
          System.setProperty("sun.net.client.defaultReadTimeout", "10000");

          //初始化acsClient,暂不支持region化
          IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
          try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("11111");
		}
          IAcsClient acsClient = new DefaultAcsClient(profile);

          //组装请求对象-具体描述见控制台-文档部分内容
          SendSmsRequest request = new SendSmsRequest();
          //必填:待发送手机号
          request.setPhoneNumbers(telphone);
          //必填:短信签名-可在短信控制台中找到
          request.setSignName("君霖");
          
          if("".equals(goodsName)&&"".equals(goodsNum)){
        	//必填:短信模板-可在短信控制台中找到
              request.setTemplateCode("SMS_135033624");
        	  //可选:模板中的变量替换JSON串,模板内容为"您的验证码为:${code}，该验证码5分钟内有效，请勿泄漏于他人！感谢您的使用。"时,此处的值为
              request.setTemplateParam("{\"code\":\""+code+"\"}");
          }
          else{
        	//必填:短信模板-可在短信控制台中找到
              request.setTemplateCode("SMS_135043923");
        	  //可选:模板中的变量替换JSON串,模板内容为"尊敬的客户您好：您购物车内的${good}等${num}件商品将为您保留库存15分钟，为确保正常下单，请您尽快结算并支付订单，感谢您的支持！"时,此处的值为
              request.setTemplateParam("{\"good\":\""+goodsName+"\",\"num\":\""+goodsNum+"\"}");
          }
        

          //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
          //request.setSmsUpExtendCode("90997");

          //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
          request.setOutId("111");

          //hint 此处可能会抛出异常，注意catch
         // SendSmsResponse sendSmsResponse;
         // SendSmsResponse sendSmsResponse=null;
		try {
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			return sendSmsResponse;
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("1false");
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("2false");
		}
		return null;
          
      }
}
