package com.jl.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.EvaluationPic;
import com.jl.mis.model.GoodsEvaluation;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.User;
import com.jl.mis.service.EvaluationPicService;
import com.jl.mis.service.GoodsEvaluationService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.CommonMethod;

/**
 * 订单评价API
 * @author 柳亚婷
 * @date  2018年1月10日  下午2:10:35
 * @Description 
 *
 */
@Controller
@RequestMapping("/goodsEvaluation/")
public class GoodsEvaluationApi {
	// 保存送票图片的文件夹名
	private String folderName = "uploadImages";

	@Autowired
	private UserService1 userService1;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private GoodsEvaluationService goodsEvaluationService;
	@Autowired
	private EvaluationPicService evaluationPicService;

	/**
	 * 根据用户id获取用户的评价列表
	 * 
	 * @param request
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getEvaluationByUserId", method = RequestMethod.GET)
	public JSONObject getEvaluationByUserId(HttpServletRequest request, @RequestParam("userId") Integer userId)
			throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null || userId <= 0) {
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
		List<GoodsEvaluation> goodsEvaluationList = new ArrayList<>();
		goodsEvaluationList = goodsEvaluationService.selectEvaluationMsgByUserId(userId, -1, "all");

		result.put("resultData", goodsEvaluationList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 查看评价详情
	 * 
	 * @param request
	 * @param id
	 *            评价id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getEvaluationDetailByUserIdAndId", method = RequestMethod.GET)
	public JSONObject getEvaluationDetailByUserIdAndId(HttpServletRequest request, @RequestParam("id") Integer id,
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
		List<GoodsEvaluation> goodsEvaluationList = new ArrayList<>();
		goodsEvaluationList = goodsEvaluationService.selectEvaluationMsgByUserId(userId, id, "no");

		if (goodsEvaluationList.size() != 1) {
			result.put("code", 15003);
			result.put("msg", "评价信息异常");
			return result;
		} else {
			result.put("resultData", goodsEvaluationList.get(0));
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}

	}
	
	
	/**
	 * 查看评价详情
	 * 
	 * @param request
	 * @param id
	 *            评价id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getEvaluationDetailByUserIdAndOrderId", method = RequestMethod.GET)
	public JSONObject getEvaluationDetailByUserIdAndOrderId(HttpServletRequest request, @RequestParam("orderId") Integer orderId,
			@RequestParam("userId") Integer userId) throws Exception {
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
		OrderTable orderTable=orderTableService.selectOrderTableListByUserIdAndOrderId(userId, orderId);

		if (orderTable == null) {
			result.put("code", 12001 );
			result.put("msg", "订单不存在");
			return result;
		} else {
			result.put("resultData",orderTable);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}

	}
	
	
	

	/**
	 * 保存订单的评价 一个订单若是多商品，则需依次调用此方法
	 * 
	 * @param request
	 * @param files
	 *            图片文件
	 * @param goodsEvaluation
	 *            商品评价
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertGoodsEvaluation", method = RequestMethod.POST)
	public JSONObject insertGoodsEvaluation(HttpServletRequest request,
			String[] files,
			GoodsEvaluation goodsEvaluation, Integer userId) throws Exception {
		JSONObject result = new JSONObject();

		// 判断参数的正确性
		if(files!=null){
			if ( files.length > 5 || goodsEvaluation == null
					|| (goodsEvaluation.getEvaluationContent() == null || "".equals(goodsEvaluation.getEvaluationContent()))
					|| (goodsEvaluation.getOrderDetailId() == null || "".equals(goodsEvaluation.getOrderDetailId()))
					|| (goodsEvaluation.getScore() == null || goodsEvaluation.getScore() < 0
							|| goodsEvaluation.getScore() > 5)
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
		OrderDetail orderdetailMsg = orderDetailService.selectByPrimaryKey(goodsEvaluation.getOrderDetailId());
		if (orderdetailMsg == null) {
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
			if (orderdetailMsg.getOrderId() == null || orderdetailMsg.getOrderId() <= 0) {
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
			}
			OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderdetailMsg.getOrderId());
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
		}
		// 4.判断该订单详情是否有评价
		GoodsEvaluation evaluation = goodsEvaluationService.selectByOrderDetailId(goodsEvaluation.getOrderDetailId());
		if (evaluation != null) {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 15002);
			result.put("msg", "订单已评价");
			return result;
		}
	
		goodsEvaluation.setEvaluationTime(new Date());
		goodsEvaluation.setEvaluationContentWordNum(goodsEvaluation.getEvaluationContent().length());
		if (goodsEvaluationService.insertSelective(goodsEvaluation) > 0) {
			// 开始存储评价的图片
			List<EvaluationPic> evaluationPics = new ArrayList<>();
			EvaluationPic evaluationPic = new EvaluationPic();
			if(files!=null){
				for (int i = 0; i < files.length; i++) {
					evaluationPic = new EvaluationPic();
					evaluationPic.setPicUrl(files[i]/*CommonMethod.savePic(request, folderName, file)*/);
					evaluationPic.setGoodsEvaluationId(goodsEvaluation.getId());
					evaluationPics.add(evaluationPic);
				}
			}
			// 有图片需要存储
			if (evaluationPics.size() > 0) {
				int evaluationPicResult = evaluationPicService.insertBatchEvaluationPic(evaluationPics);
				// 保存成功
				if (evaluationPicResult > 0 && evaluationPicResult == evaluationPics.size()) {
					Map<String, Object> resultData=new HashMap<String, Object>();
					resultData.put("id", goodsEvaluation.getId());
					result.put("resultData", resultData);
					result.put("code", 200);
					result.put("msg", "请求成功");
					return result;
				}
				// 保存不成功或只有部分图片保存成功
				else {
					// 说明只有部分保存成功，需删除数据库中保存的这部分的数据，同时需要删除已经保存在服务器上的图片
					if (evaluationPicResult > 0) {
						List<EvaluationPic> evaluationPiclist = new ArrayList<>();
						List<String> evaluationPicUrls = new ArrayList<>();
						// 获取已存入数据库的图片路径
						evaluationPiclist = evaluationPicService.selectByGoodsEvaluationId(goodsEvaluation.getId());
						if (evaluationPiclist.size() > 0) {
							for (int i = 0; i < evaluationPiclist.size(); i++) {
								evaluationPicUrls.add(evaluationPiclist.get(i).getPicUrl());
							}
						}
						if (evaluationPicUrls.size() > 0) {
							evaluationPicService.deleteByGoodsEvaluationId(goodsEvaluation.getId());
							CommonMethod.deleteOldPic(request, folderName, evaluationPicUrls);
						}
					}

					goodsEvaluationService.deleteByPrimaryKey(goodsEvaluation.getId());

					result.put("code", 15001);
					result.put("msg", "订单评价失败");
					return result;
				}
			}
			// 无图片存储
			else {
				Map<String, Object> resultData=new HashMap<String, Object>();
				resultData.put("id", goodsEvaluation.getId());
				result.put("resultData", resultData);
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			}

		}
		// 评价表存储失败
		else {
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 15001);
			result.put("msg", "订单评价失败");
			return result;
		}

	}

}
