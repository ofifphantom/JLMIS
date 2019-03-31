package com.jl.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.OfflinePayment;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.User;
import com.jl.mis.service.OfflinePaymentService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.CommonMethod;

/**
 * 线下订单支付信息API
 * 
 * @author 柳亚婷
 * @date 2018年1月4日 下午3:28:23
 * @Description
 *
 */
@Controller
@RequestMapping("/offlinePayment/")
public class OfflinePaymentApi {
	// 保存送票图片的文件夹名
	private String folderName = "uploadImages";

	@Autowired
	private UserService1 userService1;
	@Autowired
	private OfflinePaymentService offlinePaymentService;
	@Autowired
	private OrderTableService orderTableService;

	/**
	 * 添加线下付款信息
	 * 
	 * @param request
	 * @param files
	 *            两张汇款单图片
	 * @param orderId
	 *            订单编号
	 * @param userId
	 *            用户id
	 * @param remitterName
	 *            汇款人姓名
	 * @param remitterAccount
	 *            汇款人账号
	 * @param payeeName
	 *            收款人姓名
	 * @param payeeAccount
	 *            收款人账号
	 * @param payeeAccountDepositBank
	 *            收款人开户行
	 * @param remittanceAmount
	 *            汇款金额
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertOfflinePayment", method = RequestMethod.POST)
	public JSONObject insertOfflinePayment(HttpServletRequest request,
			@RequestParam String[] files, @RequestParam String orderNo,
			@RequestParam Integer userId, @RequestParam String remitterName, @RequestParam String remitterAccount,
			@RequestParam String payeeName, @RequestParam String payeeAccount,
			@RequestParam String payeeAccountDepositBank, @RequestParam Double remittanceAmount) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数信息是否合法
		// 1.判空
		if ((files == null || files.length <= 0||files.length > 2) || (orderNo == null || "".equals(orderNo))
				|| (userId == null || userId <= 0) || (remitterName == null || "".equals(remitterName))
				|| (remitterAccount == null || "".equals(remitterAccount))
				|| (payeeName == null || "".equals(payeeName)) || (payeeAccount == null || "".equals(payeeAccount))
				|| (payeeAccountDepositBank == null || "".equals(payeeAccountDepositBank))
				|| (remittanceAmount == null || remittanceAmount <= 0)) {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}

		// 2.判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				if(files!=null){
					List<String> oldUrl=new ArrayList<String>();
		 			for (int i = 0; i < files.length; i++) {
		 				oldUrl.add(files[i]);
					}
					CommonMethod.deleteOldPic(request, folderName, oldUrl);
				}
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}

		// 3.订单id与用户id联合查询，判断该订单是否存在于线下支付信息表中(状态为待支付)
		OfflinePayment offlinePayment = offlinePaymentService.selectOfflineMsgByOrderNoAndUserId(orderNo, userId);
		if (offlinePayment == null) {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 12001);
			result.put("msg", "订单不存在或该订单不存在线下支付信息");
			return result;
		}

		

		// 往线下支付信息表中存储数据
		offlinePayment.setRemitterName(remitterName);
		offlinePayment.setRemitterAccount(remitterAccount);
		offlinePayment.setPayeeName(payeeName);
		offlinePayment.setPayeeAccount(payeeAccount);
		offlinePayment.setPayeeAccountDepositBank(payeeAccountDepositBank);
		offlinePayment.setRemittanceAmount(remittanceAmount);
		offlinePayment.setState(1);
		// 存储支付凭证图片，并保存路径
		for (int i = 0; i < files.length; i++) {

			if(i==0){
				offlinePayment.setPaymentVoucherUrlOne(files[i]);
			}
			else{
				offlinePayment.setPaymentVoucherUrlTwo(files[i]);
			}
		}
			
		//执行存储代码成功
		if(offlinePaymentService.updateByPrimaryKeySelective(offlinePayment)>0){
			//需要修改订单状态为9(已付款)
			OrderTable orderTable=new OrderTable();
			orderTable.setId(offlinePayment.getOrderId());
			//付款时间
			orderTable.setPayTime(new Date());
			orderTable.setOrderState(9);
			//修改成功
			if(orderTableService.updateByPrimaryKeySelective(orderTable)>0){
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			}
			else{
				//修改订单表状态未成功，此时需要删除之前添加好的线下支付信息以及删除图片
				
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
				offlinePayment.setRemitterName("");
				offlinePayment.setRemitterAccount("");
				offlinePayment.setPayeeName("");
				offlinePayment.setPayeeAccount("");
				offlinePayment.setPayeeAccountDepositBank("");
				offlinePayment.setRemittanceAmount(0.0);
				offlinePayment.setState(0);
				offlinePayment.setPaymentVoucherUrlOne("");
				offlinePayment.setPaymentVoucherUrlTwo("");
				offlinePaymentService.updateByPrimaryKeySelective(offlinePayment);
				if(files!=null){
					List<String> oldUrl=new ArrayList<String>();
		 			for (int i = 0; i < files.length; i++) {
		 				oldUrl.add(files[i]);
					}
					CommonMethod.deleteOldPic(request, folderName, oldUrl);
				}
				result.put("code", 14001);
				result.put("msg", "线下支付信息存储错误");
				return result;
			}
			
		}
		//执行存储代码失败
		else{
			//若存储失败，则要删除之前已存好的图片
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
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 14001);
			result.put("msg", "线下支付信息存储错误");
			return result;
		}
	}

}
