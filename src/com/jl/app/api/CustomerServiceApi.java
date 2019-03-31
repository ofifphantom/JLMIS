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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.AfterSaleDetail;
import com.jl.mis.model.AfterSalePic;
import com.jl.mis.model.EvaluationPic;
import com.jl.mis.model.GoodsEvaluation;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.User;
import com.jl.mis.service.AfterSaleDetailService;
import com.jl.mis.service.AfterSalePicService;
import com.jl.mis.service.OrderStateDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.CommonMethod;


/**
 * 售后服务API
 * 
 * @author 柳亚婷
 * @date 2018年1月7日 下午6:50:22
 * @Description
 *
 */
@Controller
@RequestMapping("/customerService/")
public class CustomerServiceApi {

	// 保存用户上传的图片的文件夹名
	private String folderName = "uploadImages";

	@Autowired
	private UserService1 userService1;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private AfterSaleDetailService afterSaleDetailService;
	@Autowired
	private AfterSalePicService afterSalePicService;
	@Autowired
	private OrderStateDetailService orderStateDetailService;

	/**
	 * 根据用户id以及售后信息id获取售后的详情信息
	 * 
	 * @param request
	 * @param id
	 *            售后信息主键id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCustomerServiceByUserIdAndId", method = RequestMethod.GET)
	public JSONObject getCustomerServiceByUserIdAndId(HttpServletRequest request, @RequestParam("id") Integer id,
			@RequestParam("userId") Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if ((id == null || id <= 0) || (userId == null || userId <= 0)) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		AfterSaleDetail afterSaleDetail = afterSaleDetailService.selectByUserIdAndPrimaryKey(id, userId);

		if (afterSaleDetail == null) {
			result.put("code", 16003);
			result.put("msg", "售后信息异常");
			return result;
		} else {
			result.put("resultData", afterSaleDetail);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}

	}

	/**
	 * 根据用户id以及订单id获取售后的详情信息
	 * 
	 * @param request
	 * @param orderId
	 *            订单id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getCustomerServiceByUserIdAndOrderId", method = RequestMethod.GET)
	public JSONObject getCustomerServiceByUserIdAndOrderId(HttpServletRequest request,
			@RequestParam("orderId") Integer orderId, @RequestParam("userId") Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		if ((orderId == null || orderId <= 0) || (userId == null || userId <= 0)) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		// 判断订单是否存在
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
		if (orderMsg == null) {
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		} else {
			if(orderMsg.getUserId()==null){
				result.put("code", 12002);
				result.put("msg", "订单异常");
				return result;
			}
			else if (orderMsg.getUserId() != userId) {
				result.put("code", 12001);
				result.put("msg", "该用户下的此订单不存在");
				return result;
			}
		}

		AfterSaleDetail afterSaleDetail = afterSaleDetailService.selectByUserIdAndOrderId(orderId, userId);

		if (afterSaleDetail == null) {
			result.put("code", 16003);
			result.put("msg", "售后信息异常");
			return result;
		} else {
			result.put("resultData", afterSaleDetail);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}

	}

	/**
	 * 申请售后
	 * 
	 * @param request
	 * @param files
	 *            售后图片
	 * @param afterSaleDetail
	 *            售后信息
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "applyCustomerService", method = RequestMethod.POST)
	public JSONObject applyCustomerService(HttpServletRequest request,
			String[] files,
			AfterSaleDetail afterSaleDetail, Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 1.判断参数的正确性
		if(files!=null){
			if (( files.length > 5) || afterSaleDetail == null
					|| (afterSaleDetail.getServiceType() == null || (afterSaleDetail.getServiceType() != 0
							&& afterSaleDetail.getServiceType() != 1 && afterSaleDetail.getServiceType() != 2))
					|| (afterSaleDetail.getProblemDescription() == null
							|| "".equals(afterSaleDetail.getProblemDescription())
							|| afterSaleDetail.getProblemDescription().length() > 500)
					|| (afterSaleDetail.getOrderId() == null || afterSaleDetail.getOrderId() < 0)
					|| (afterSaleDetail.getName() == null || "".equals(afterSaleDetail.getName()))
					|| (afterSaleDetail.getPhone() == null || "".equals(afterSaleDetail.getPhone())
							|| afterSaleDetail.getPhone().length() > 11)
					|| (userId == null || userId <= 0)) {
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
		// 3.判断该用户下的此订单是否存在
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(afterSaleDetail.getOrderId());
		if (orderMsg == null) {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		} else {
			if (orderMsg.getUserId() == null) {
				if(files!=null){
					List<String> oldUrl=new ArrayList<String>();
		 			for (int i = 0; i < files.length; i++) {
		 				oldUrl.add(files[i]);
					}
					CommonMethod.deleteOldPic(request, folderName, oldUrl);
				}
				result.put("code", 12002);
				result.put("msg", "订单异常");
				return result;
			} else if (orderMsg.getUserId() != userId) {
				if(files!=null){
					List<String> oldUrl=new ArrayList<String>();
		 			for (int i = 0; i < files.length; i++) {
		 				oldUrl.add(files[i]);
					}
					CommonMethod.deleteOldPic(request, folderName, oldUrl);
				}
				result.put("code", 12001);
				result.put("msg", "该用户下的此订单不存在");
				return result;
			}
			// 3、10表示该订单已完成
			else if (orderMsg.getOrderState() != 3 && orderMsg.getOrderState() != 10) {
				if(files!=null){
					List<String> oldUrl=new ArrayList<String>();
		 			for (int i = 0; i < files.length; i++) {
		 				oldUrl.add(files[i]);
					}
					CommonMethod.deleteOldPic(request, folderName, oldUrl);
				}
				result.put("code", 16001);
				result.put("msg", "该订单暂不能申请售后");
				return result;
			}
		}
	
		afterSaleDetail.setAddTime(new Date());
		// 默认为未处理
		afterSaleDetail.setStatus(0);
		// 售后信息保存成功
		if (afterSaleDetailService.insertSelective(afterSaleDetail) > 0) {
			// 开始存储图片
			List<AfterSalePic> afterSalePics = new ArrayList<>();
			AfterSalePic afterSalePic = new AfterSalePic();
			if(files!=null){
				for (int i = 0; i < files.length; i++) {
					afterSalePic = new AfterSalePic();
					/*if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {*/
					afterSalePic.setPicUrl(files[i]/*CommonMethod.savePic(request, folderName, file)*/);
					afterSalePic.setAfterSaleDetailId(afterSaleDetail.getId());
					afterSalePics.add(afterSalePic);

				}
			}

			// 说明有图片需要存储
			if (afterSalePics.size() > 0) {
				int afterSalePicResult = afterSalePicService.insertBatchCustomerServicePic(afterSalePics);
				// 说明存储成功
				if (afterSalePicResult > 0 && afterSalePics.size() == afterSalePicResult) {
					// 需要修改订单表的状态为6（售后中）
					OrderTable orderTable = new OrderTable();
					orderTable.setId(afterSaleDetail.getOrderId());
					orderTable.setOrderState(6);
					// 说明修改订单表状态成功
					if (orderTableService.updateByPrimaryKeySelective(orderTable) > 0) {
						// 此时要往订单状态详情表中添加状态(用户申请退款/退货)
						OrderStateDetail orderStateDetail = new OrderStateDetail();
						orderStateDetail.setAddTime(new Date());
						orderStateDetail.setOrderId(afterSaleDetail.getOrderId());
						orderStateDetail.setOrderStateDetail("用户申请退款/退货");
						// 添加状态成功
						if (orderStateDetailService.insertSelective(orderStateDetail) > 0) {
							//如果是退货
							if(afterSaleDetail.getServiceType()==0){
								//如果是退货，则要修改进销存中相应订单的状态
								OrderTable orderTable2=orderTableService.selectByPrimaryKey(afterSaleDetail.getOrderId());
								orderTableService.updateJLgxcSaleOrderStateToReturnGoods(orderTable2.getOrderNo(), afterSaleDetail.getOrderId());
							}
							result.put("code", 200);
							result.put("msg", "请求成功");
							return result;
						} else {
							// 添加状态失败，需要删除之前修改好的订单状态以及其他添加好的信息。
							orderTable = new OrderTable();
							orderTable.setId(afterSaleDetail.getOrderId());
							orderTable.setOrderState(orderMsg.getOrderState());
							orderTableService.updateByPrimaryKeySelective(orderTable);

							// 需要删除已存入数据库的图片路径以及存入服务器的图片
							List<AfterSalePic> afterSalePicList = new ArrayList<>();
							List<String> afterSalePicUrls = new ArrayList<>();
							// 获取已存入数据库的图片路径
							afterSalePicList = afterSalePicService.selectByCustomerServiceId(afterSaleDetail.getId());
							if (afterSalePicList.size() > 0) {
								for (int i = 0; i < afterSalePicList.size(); i++) {
									afterSalePicUrls.add(afterSalePicList.get(i).getPicUrl());
								}
							}
							if (afterSalePicUrls.size() > 0) {
								afterSalePicService.deleteByCustomerServiceId(afterSaleDetail.getId());
								CommonMethod.deleteOldPic(request, folderName, afterSalePicUrls);
							}
							afterSaleDetailService.deleteByPrimaryKey(afterSaleDetail.getId());

							result.put("code", 16002);
							result.put("msg", "订单申请售后失败");
							return result;
						}

					} else {
						// 需要删除已存入数据库的图片路径以及存入服务器的图片
						List<AfterSalePic> afterSalePicList = new ArrayList<>();
						List<String> afterSalePicUrls = new ArrayList<>();
						// 获取已存入数据库的图片路径
						afterSalePicList = afterSalePicService.selectByCustomerServiceId(afterSaleDetail.getId());
						if (afterSalePicList.size() > 0) {
							for (int i = 0; i < afterSalePicList.size(); i++) {
								afterSalePicUrls.add(afterSalePicList.get(i).getPicUrl());
							}
						}
						if (afterSalePicUrls.size() > 0) {
							afterSalePicService.deleteByCustomerServiceId(afterSaleDetail.getId());
							CommonMethod.deleteOldPic(request, folderName, afterSalePicUrls);
						}
						afterSaleDetailService.deleteByPrimaryKey(afterSaleDetail.getId());

						result.put("code", 16002);
						result.put("msg", "订单申请售后失败");
						return result;
					}

				}
				// 保存不成功或只有部分图片保存成功
				else {
					// 说明只有部分保存成功，需删除数据库中保存的这部分的数据，同时需要删除已经保存在服务器上的图片
					if (afterSalePicResult > 0) {
						List<AfterSalePic> afterSalePicList = new ArrayList<>();
						List<String> afterSalePicUrls = new ArrayList<>();
						// 获取已存入数据库的图片路径
						afterSalePicList = afterSalePicService.selectByCustomerServiceId(afterSaleDetail.getId());
						if (afterSalePicList.size() > 0) {
							for (int i = 0; i < afterSalePicList.size(); i++) {
								afterSalePicUrls.add(afterSalePicList.get(i).getPicUrl());
							}
						}
						if (afterSalePicUrls.size() > 0) {
							afterSalePicService.deleteByCustomerServiceId(afterSaleDetail.getId());
							CommonMethod.deleteOldPic(request, folderName, afterSalePicUrls);
						}
					}

					afterSaleDetailService.deleteByPrimaryKey(afterSaleDetail.getId());

					result.put("code", 16002);
					result.put("msg", "订单申请售后失败");
					return result;
				}
			}
			// 无图片存储
			else {
				// 需要修改订单表的状态为6（售后中）
				OrderTable orderTable = new OrderTable();
				orderTable.setId(afterSaleDetail.getOrderId());
				orderTable.setOrderState(6);
				// 说明修改订单表状态成功
				if (orderTableService.updateByPrimaryKeySelective(orderTable) > 0) {
					// 此时要往订单状态详情表中添加状态(用户申请退款/退货)
					OrderStateDetail orderStateDetail = new OrderStateDetail();
					orderStateDetail.setAddTime(new Date());
					orderStateDetail.setOrderId(afterSaleDetail.getOrderId());
					orderStateDetail.setOrderStateDetail("用户申请退款/退货");
					// 添加状态成功
					if (orderStateDetailService.insertSelective(orderStateDetail) > 0) {
						//如果是退货
						if(afterSaleDetail.getServiceType()==0){
							//如果是退货，则要修改进销存中相应订单的状态
							OrderTable orderTable2=orderTableService.selectByPrimaryKey(afterSaleDetail.getOrderId());
							orderTableService.updateJLgxcSaleOrderStateToReturnGoods(orderTable2.getOrderNo(), afterSaleDetail.getOrderId());
						}
						result.put("code", 200);
						result.put("msg", "请求成功");
						return result;
					} else {
						// 添加状态失败，需要删除之前修改好的订单状态以及其他添加好的信息。
						orderTable = new OrderTable();
						orderTable.setId(afterSaleDetail.getOrderId());
						orderTable.setOrderState(orderMsg.getOrderState());
						orderTableService.updateByPrimaryKeySelective(orderTable);
						result.put("code", 16002);
						result.put("msg", "订单申请售后失败");
						return result;
					}

				} else {
					result.put("code", 16002);
					result.put("msg", "订单申请售后失败");
					return result;
				}

			}
		}
		// 售后表保存失败
		else {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 16002);
			result.put("msg", "订单申请售后失败");
			return result;
		}

	}
	

	
}
