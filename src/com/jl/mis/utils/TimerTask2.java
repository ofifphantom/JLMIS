package com.jl.mis.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jl.mis.model.ShoppingCart;
import com.jl.mis.service.ShoppingCartService;

/**
 * 类名称：TimerTask 类描述：定时器任务
 * 
 * @author 柳亚婷
 * @date 2018-03-26 下午3:08:23
 */
@Component
public class TimerTask2 {


	@Autowired
	private ShoppingCartService shoppingCartService;
	
	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAICNr1sfd3dMqz";
    static final String accessKeySecret = "9LkV14sUdYtGeoHtxZCVSGTxUgW0q0";
  //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

	/**
	 * 查询需要发送短信的购物车信息  10分钟发一次
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0/10 * * * ? ")
	public void ShopCartSendMessageTask() throws Exception {
		Date startTime = new Date();
		List<ShoppingCart> shoppingCarts = new ArrayList<>();
		shoppingCarts = shoppingCartService.selectShopCartGoodSendMessage();
		SendSmsResponse response;
		if (shoppingCarts.size() > 0) {
			int userId = shoppingCarts.get(0).getUserId();
			String goodsName = "";
			int goodsNum = 0;
			int i=0;
			
			
			for (i =0; i < shoppingCarts.size(); i++) {
				if (shoppingCarts.get(i).getUserId() == userId) {
					goodsNum = shoppingCarts.get(i).getGoodsAllNum();
					goodsName = shoppingCarts.get(i).getGoodsName();
				} else {
					userId = shoppingCarts.get(i).getUserId();
					System.out.println("我执行了定时的else");				
					response=ALiYunMessageService.sendSms("", shoppingCarts.get(i-1).getUser().getPhone(), goodsName, goodsNum+"");
					
					aaa(response);
			        
					goodsNum = shoppingCarts.get(i).getGoodsAllNum();
					goodsName = shoppingCarts.get(i).getGoodsName();
				}
			}
			System.out.println("i:"+i);
			System.out.println("shoppingCarts.size():"+shoppingCarts.size());
		
			if(i==shoppingCarts.size()){
				System.out.println("我执行了定时外面的if");
				response=ALiYunMessageService.sendSms("", shoppingCarts.get(shoppingCarts.size()-1).getUser().getPhone(), goodsName, goodsNum+"");
				aaa(response);
			}
		}
		
		Date endTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println(sdf.format(startTime) + "~" + sdf.format(endTime) + "发送短信定时任务执行完成！");

	}
	
	public void aaa(SendSmsResponse response) throws ClientException{
		System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());

        try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }
	}
	
	public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

	

}