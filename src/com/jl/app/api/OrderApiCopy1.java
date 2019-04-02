package com.jl.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.JlPay;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsEvaluation;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderStateDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.User;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsEvaluationService;
import com.jl.mis.service.GoodsService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.InvoiceService;
import com.jl.mis.service.OfflinePaymentService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderStateDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.ShoppingCartService;
import com.jl.mis.service.UserCouponsService;
import com.jl.mis.service.UserService1;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 订单API
 * 
 * @author 柳亚婷
 * @date 2017年12月25日 上午9:32:58
 * @Description
 *
 */
@Controller
@RequestMapping("/order/")
public class OrderApi {

	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private UserService1 userService1;
	@Autowired
	private OfflinePaymentService offlinePaymentService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	@Autowired
	private OrderStateDetailService orderStateDetailService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private UserCouponsService userCouponsService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsEvaluationService evaluationService;
	@Autowired
	private JlPay jlpay;

	/**
	 * 判断商品是否有库存
	 * 
	 * @param request
	 * @param orderTable
	 *            传参为json
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "decisionInventory", method = RequestMethod.POST)
	public JSONObject decisionInventory(HttpServletRequest request, @RequestBody OrderTable orderTable)
			throws Exception {
		JSONObject result = new JSONObject();
		// 判断参数的正确性
		if (orderTable == null || (orderTable.getUserId() == null || orderTable.getUserId() <= 0)
				|| orderTable.getOrderDetails().size() == 0 || (orderTable.getIsUseCoupon() == null
						|| (orderTable.getIsUseCoupon() != 0 && orderTable.getIsUseCoupon() != 1))) {
			result.put("code", 10000);
			result.put("msg", "订单详情参数错误");
			return result;
		}
		if (orderTable.getIsUseCoupon() == 1) {
			if (orderTable.getUserCoupons() == null
					|| (orderTable.getUserCoupons().getId() == null || orderTable.getUserCoupons().getId() <= 0)) {
				result.put("code", 10000);
				result.put("msg", "优惠券参数错误");
				return result;
			}
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(orderTable.getUserId());
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
		List<Map<String, Object>> goodsResultList = new ArrayList<>();
		Map<String, Object> resultList = new HashMap<>();
		GoodsSpecificationDetails goodsSpecificationDetails = new GoodsSpecificationDetails();
		GoodsDetails goodsDetails=new GoodsDetails();
		Map<String, Object> map = new HashMap<>();
		// 根据商品规格id 判断商品以及商品的库存是否存在
		for (int i = 0; i < orderTable.getOrderDetails().size(); i++) {
			// 判断参数的正确性
			if ((orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId() == null
					|| orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId() <= 0)
					|| (orderTable.getOrderDetails().get(i).getGoodsQuantity() == null
							|| orderTable.getOrderDetails().get(i).getGoodsQuantity() <= 0)) {
				result.put("code", 10000);
				result.put("msg", "订单详情参数错误");
				return result;
			}
			goodsSpecificationDetails = goodsSpecificationDetailsService.selectGxcGoodsStockByspecificationIdAndGoodsId(
					orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId(),
					orderTable.getOrderDetails().get(i).getGoodsDetailsId(), orderTable.getUserId());
			map = new HashMap<>();
			// 说明没有查到信息
			if (goodsSpecificationDetails == null || goodsSpecificationDetails.getId() == null) {
				map.put("goodsSpecificationDetailsId",
						orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId());
				map.put("code", 80001);
				map.put("msg", "该规格不存在或已下架");

			}
			// 查到商品，此时需判断库存
			else {
				goodsDetails=goodsDetailsService.selectByPrimaryKey(orderTable.getOrderDetails().get(i).getGoodsDetailsId());
				//不是预售商品并且不允许0库存出库
				if(goodsSpecificationDetails.getGxcGoodsState()==2&&goodsDetails.getZeroStock()==0){
					// 无库存
					if (goodsSpecificationDetails.getGxcGoodsStock() <= 0) {
						map.put("goodsSpecificationDetailsId",
								orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId());
						map.put("code", 80003);
						map.put("msg", "无库存");
					}
					// 购买的数量大于库存----库存不足
					else if (orderTable.getOrderDetails().get(i).getGoodsQuantity() > goodsSpecificationDetails
							.getGxcGoodsStock()) {
						map.put("goodsSpecificationDetailsId",
								orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId());
						map.put("stock", goodsSpecificationDetails.getGxcGoodsStock());
						map.put("code", 80004);
						map.put("msg", "库存不足");
					} else {
						map.put("goodsSpecificationDetailsId",
								orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId());
						map.put("code", 200);
						map.put("msg", "库存满足");
					}
				}
				else{
					map.put("goodsSpecificationDetailsId",
							orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId());
					map.put("code", 200);
					map.put("msg", "库存满足");
				}
				
			}
			goodsResultList.add(map);
		}
		resultList.put("goodsMsg", goodsResultList);

		Map<String, Object> couponMap = new HashMap<>();
		// 若使用优惠券，则判断优惠券的有效性
		if (orderTable.getIsUseCoupon() == 1) {
			UserCoupons userCoupons = userCouponsService.selectByPrimaryKey(orderTable.getUserCoupons().getId());
			// 优惠券不存在
			if (userCoupons == null) {
				couponMap.put("code", 30002);
				couponMap.put("msg", "优惠券不存在");
				resultList.put("couponMsg", couponMap);
			}
			// 优惠券存在，则判断用户id是否相等
			else {
				if (orderTable.getUserId() != userCoupons.getUserId()) {
					couponMap.put("code", 30002);
					couponMap.put("msg", "优惠券不存在");
					resultList.put("couponMsg", couponMap);
				} else {
					if (userCoupons.getStatus() != 0) {
						couponMap.put("code", 30002);
						couponMap.put("msg", "优惠券不能使用");
						resultList.put("couponMsg", couponMap);
					}
				}
			}

		}

		result.put("resultData", resultList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 提交订单
	 * 
	 * @param request
	 * @param orderTable
	 *            传参为json
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertOrder", method = RequestMethod.POST)
	public JSONObject insertOrder(HttpServletRequest request, @RequestBody OrderTable orderTable) throws Exception {
		JSONObject result = new JSONObject();
		// 判断传过来的参数
		if (orderTable == null || (orderTable.getOrderOriginalPrice() == null || orderTable.getOrderOriginalPrice() < 0)
				|| (orderTable.getOrderPresentPrice() == null || orderTable.getOrderPresentPrice() < 0)
				|| (orderTable.getUserId() == null || orderTable.getUserId() <= 0)
				|| (orderTable.getConsigneeName() == null || "".equals(orderTable.getConsigneeName()))
				|| (orderTable.getConsigneeTel() == null || "".equals(orderTable.getConsigneeTel()))
				|| (orderTable.getConsigneeAddress() == null || "".equals(orderTable.getConsigneeAddress()))
				|| (orderTable.getPayType() == null || (orderTable.getPayType() != 0 && orderTable.getPayType() != 1))
				|| (orderTable.getIsHasInvoice() == null
						|| (orderTable.getIsHasInvoice() != 0 && orderTable.getIsHasInvoice() != 1))
				|| (orderTable.getPostage() == null || orderTable.getPostage() < 0)
				|| (orderTable.getPostagePayType() == null
						|| (orderTable.getPostagePayType() != 0 && orderTable.getPostagePayType() != 1))
				||  (orderTable.getIsFromShoppingCart() == null
						|| (orderTable.getIsFromShoppingCart() != 0 && orderTable.getIsFromShoppingCart() != 1))
				|| (orderTable.getIsUseCoupon() == null
						|| (orderTable.getIsUseCoupon() != 0 && orderTable.getIsUseCoupon() != 1))) {
			result.put("code", 10000);
			result.put("msg", "订单参数错误");
			return result;
		}
		//若传预售活动id为0 说明不是预售商品
		else if(orderTable.getActivityId()==0){
			if(orderTable.getDeliverGoodsTime() == null){
				result.put("code", 10000);
				result.put("msg", "订单参数错误");
				return result;
			}
		}
		if (orderTable.getOrderDetails().size() == 0) {
			result.put("code", 10000);
			result.put("msg", "订单详情参数错误");
			return result;
		}
		// 如果需要开发票
		if (orderTable.getIsHasInvoice() == 1) {
			if (orderTable.getInvoice() == null || orderTable.getInvoice().getInvoiceType() == null
					|| (orderTable.getInvoice().getInvoiceType() != 0
							&& orderTable.getInvoice().getInvoiceType() != 1)) {
				result.put("code", 10000);
				result.put("msg", "发票信息参数错误");
				return result;
			}
			// 普票参数判断
			if (orderTable.getInvoice().getInvoiceType() == 0) {
				if ((orderTable.getInvoice().getInvoiceContent() == null
						|| (orderTable.getInvoice().getInvoiceContent() != 0
								&& orderTable.getInvoice().getInvoiceContent() != 1))
						|| (orderTable.getInvoice().getInvoiceLookedUpType() == null
								|| (orderTable.getInvoice().getInvoiceLookedUpType() != 0
										&& orderTable.getInvoice().getInvoiceLookedUpType() != 1))) {

					result.put("code", 10000);
					result.put("msg", "发票参数错误");
					return result;
				}
				// 抬头为单位
				if (orderTable.getInvoice().getInvoiceLookedUpType() == 0) {
					if ((orderTable.getInvoice().getUnitName() == null
							|| "".equals(orderTable.getInvoice().getUnitName()))
							|| (orderTable.getInvoice().getTaxpayerIdentificationNumber() == null
									|| "".equals(orderTable.getInvoice().getTaxpayerIdentificationNumber()))) {

						result.put("code", 10000);
						result.put("msg", "发票参数错误");
						return result;
					}
				}

			}
			// 增票参数判断
			else {
				if ((orderTable.getInvoice().getInvoiceContent() == null
						|| orderTable.getInvoice().getInvoiceContent() != 1)
						|| (orderTable.getInvoice().getUnitName() == null
								|| "".equals(orderTable.getInvoice().getUnitName()))
						|| (orderTable.getInvoice().getTaxpayerIdentificationNumber() == null
								|| "".equals(orderTable.getInvoice().getTaxpayerIdentificationNumber()))
						|| (orderTable.getInvoice().getRegisteredAddress() == null
								|| "".equals(orderTable.getInvoice().getRegisteredAddress()))
						|| (orderTable.getInvoice().getRegisteredTel() == null
								|| "".equals(orderTable.getInvoice().getRegisteredTel()))
						|| (orderTable.getInvoice().getDepositBank() == null
								|| "".equals(orderTable.getInvoice().getDepositBank()))
						|| (orderTable.getInvoice().getBankAccount() == null
								|| "".equals(orderTable.getInvoice().getBankAccount()))
						|| (orderTable.getInvoice().getBusinessLicenseUrl() == null
								|| "".equals(orderTable.getInvoice().getBusinessLicenseUrl()))) {

					result.put("code", 10000);
					result.put("msg", "发票参数错误");
					return result;
				}
			}
		}

		if (orderTable.getPayType() == 1) {
			if (orderTable.getOfflinePayment() == null
					|| (orderTable.getOfflinePayment().getPayerName() == null
							|| "".equals(orderTable.getOfflinePayment().getPayerName()))
					|| (orderTable.getOfflinePayment().getPayerTel() == null
							|| "".equals(orderTable.getOfflinePayment().getPayerTel()))) {
				result.put("code", 10000);
				result.put("msg", "线下支付信息参数错误");
				return result;
			}
		}

		if (orderTable.getIsUseCoupon() == 1) {
			if (orderTable.getUserCoupons() == null
					|| (orderTable.getUserCoupons().getId() == null || orderTable.getUserCoupons().getId() <= 0)) {
				result.put("code", 10000);
				result.put("msg", "优惠券参数错误");
				return result;
			}
		}

		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(orderTable.getUserId());
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
		int orderTableResult = -1; // 往订单表中存储后的返回值(是否成功)
		int orderDetailResult = -1; // 往订单详情表中存储后的返回值
		int offlinePaymentResult = -1; // 往线下支付表中存储后的返回值
		int invoiceResult = -1; // 往发票表中存储后的返回值
		int orderStateDetailResult = -1; // 订单状态详情存储后的返回值

		// -------------------------先存订单表------------------------
		// 获取订单的编号
		orderTable.setOrderNo(SundryCodeUtil.getPosCode(Constants.CODE_ORDER));
		// 设置下单时间
		orderTable.setPlaceOrderTime(new Date());
		// 设置订单状态为待支付
		orderTable.setOrderState(0);
		// 往订单表中添加数据
		orderTableResult = orderTableService.insertSelective(orderTable);
		if (orderTableResult > 0) {
			GoodsSpecificationDetails goodsSpecificationDetails = new GoodsSpecificationDetails();

			// -------------------再存订单详情表,订单详情表的值是从移动端获取的-----------------

			for (OrderDetail orderDetail : orderTable.getOrderDetails()) {
				// 先判断参数的正确性
				if ((orderDetail.getGoodsDetailsId() == null || orderDetail.getGoodsDetailsId() <= 0)
						|| (orderDetail.getGoodsQuantity() == null || orderDetail.getGoodsQuantity() <= 0)
						|| (orderDetail.getGoodsSpecificationDetailsId() == null
								|| orderDetail.getGoodsSpecificationDetailsId() <= 0)
						|| (orderDetail.getGoodsPurchasingPrice() == null || orderDetail.getGoodsPurchasingPrice() < 0)
						|| (orderDetail.getGoodsOriginalPrice() == null || orderDetail.getGoodsOriginalPrice() <  0)
						|| (orderDetail.getGoodsPaymentPrice() == null || orderDetail.getGoodsPaymentPrice() < 0)
						|| (orderDetail.getGoodsName() == null || "".equals(orderDetail.getGoodsName()))
						|| (orderDetail.getGoodsCoverUrl() == null || "".equals(orderDetail.getGoodsCoverUrl()))
						|| (orderDetail.getGoodsSpecificationName() == null
								|| "".equals(orderDetail.getGoodsSpecificationName()))) {
					// 先删除刚才添加好的订单表
					orderTableService.deleteByPrimaryKey(orderTable.getId());
					result.put("code", 10000);
					result.put("msg", "订单详情参数错误");
					return result;
				}
				// 判断商品规格和商品id是否能查到信息
				goodsSpecificationDetails = goodsSpecificationDetailsService
						.selectGoodsSpecificationDetailsByIdAndGoodsId(orderDetail.getGoodsSpecificationDetailsId(),
								orderDetail.getGoodsDetailsId());
				if (goodsSpecificationDetails == null) {
					// 先删除刚才添加好的订单表
					orderTableService.deleteByPrimaryKey(orderTable.getId());
					result.put("code", 80001);
					result.put("msg", "部分商品查不到商品规格或规格已下架,请重新选择!");
					return result;
				}
				// 把刚刚存进去的订单的id存入订单详情中
				orderDetail.setOrderId(orderTable.getId());
			}
			orderDetailResult = orderDetailService.insertBatch(orderTable.getOrderDetails());

			// **********************订单详情表存储end********************************************

			if (orderDetailResult > 0 && (orderDetailResult == orderTable.getOrderDetails().size())) {

				// ------------------添加订单状态详情，该值是后台自动添加的--------------------------

				OrderStateDetail orderStateDetail = new OrderStateDetail();
				orderStateDetail.setAddTime(new Date());
				orderStateDetail.setOrderId(orderTable.getId());
				orderStateDetail.setOrderStateDetail("订单提交成功");
				orderStateDetailResult = orderStateDetailService.insertSelective(orderStateDetail);

				// ***********************订单状态详情添加end*********************************

				if (orderStateDetailResult > 0) {

					// -----------------如果是线下支付，则需要往线下支付订单表中添加数据，新添加时默认状态为0未上传，该值是从移动端获取的--------------------------------

					if (orderTable.getPayType() == 1) {
						orderTable.getOfflinePayment().setOrderId(orderTable.getId());
						// 状态改为未上传
						orderTable.getOfflinePayment().setState(0);
						offlinePaymentResult = offlinePaymentService.insertSelective(orderTable.getOfflinePayment());

						// ^^^^^^线下支付信息存储成功 start^^^^^^^
						if (offlinePaymentResult > 0) {
							// --------------------------若有发票，需要往发票表中存储-----------------------------------
							if (orderTable.getIsHasInvoice() == 1) {

								orderTable.getInvoice().setOrderId(orderTable.getId());
								invoiceResult = invoiceService.insertSelective(orderTable.getInvoice());

								// ^^^^^^发票信息存储成功 start^^^^^^^
								if (invoiceResult > 0) {

									// ----------------- 需要减库存 购销存中
									// -----------------
									List<Map<String, Object>> gxcGoods_gsdId = this
											.updateGXCStockByOrderNum(orderTable);

									// 修改购销存的库存成功
									if (gxcGoods_gsdId.size() == orderTable.getOrderDetails().size()) {
										// 判断保存的结果是否成功，若成功则返回200，若不成功则返回提交失败，删除之前保存成功的信息

										// 若是从购物车中进入该页面，则删除购物车中的信息
										if (orderTable.getIsFromShoppingCart() == 1) {
											for (int i = 0; i < orderTable.getOrderDetails().size(); i++) {
												shoppingCartService.deleteByGoodsIdAndGsdIdAndUId(
														orderTable.getOrderDetails().get(i).getGoodsDetailsId(),
														orderTable.getOrderDetails().get(i)
																.getGoodsSpecificationDetailsId(),
														orderTable.getUserId());
											}
										}
										// 若订单使用了优惠券，则需要修改优惠券的状态
										if (orderTable.getIsUseCoupon() == 1) {
											orderTable.getUserCoupons().setUseTime(new Date());
											orderTable.getUserCoupons().setUserId(orderTable.getUserId());
											if (userCouponsService
													.updateByUserIdAndId(orderTable.getUserCoupons()) > 0) {
												result.put("code", 200);
												result.put("msg", "请求成功");
												String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
												result.put("resultData", ids);
												return result;
											} else {
												for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
													GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
													goodsService.updateCommodityOccupiedInventoryError(
															(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
															gsDetails.getCommoditySpecificationId());
												}
												// 先删除添加好的订单表
												orderTableService.deleteByPrimaryKey(orderTable.getId());
												// 根据订单id删除添加好的订单详情
												orderDetailService.deleteByOrderId(orderTable.getId());
												// 根据订单id删除订单状态详情表
												orderStateDetailService.deleteByOrderId(orderTable.getId());
												// 根据订单id删除线下支付表
												offlinePaymentService.deleteBuOrderId(orderTable.getId());
												// 根据订单id删除发票表
												invoiceService.deleteByOrderId(orderTable.getId());

												result.put("code", 12003);
												result.put("msg", "订单提交失败");
												return result;
											}

										} else {
											// result.put("resultData",
											// orderTable);
											result.put("code", 200);
											result.put("msg", "请求成功");
											String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
											result.put("resultData", ids);
											return result;
										}

									}
									// 修改购销存库不成功或个别不成功
									else {
										// 修改购销存库个别不成功,先把之前修改过的数据还原再删除之间加过的数据
										if (gxcGoods_gsdId.size() > 0) {
											for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
												GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
												goodsService.updateCommodityOccupiedInventoryError(
														(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
														gsDetails.getCommoditySpecificationId());
											}
											// 先删除添加好的订单表
											orderTableService.deleteByPrimaryKey(orderTable.getId());
											// 根据订单id删除添加好的订单详情
											orderDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除订单状态详情表
											orderStateDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除线下支付表
											offlinePaymentService.deleteBuOrderId(orderTable.getId());
											// 根据订单id删除发票表
											invoiceService.deleteByOrderId(orderTable.getId());

											result.put("code", 12003);
											result.put("msg", "订单提交失败");
											return result;
										} else {
											// 先删除添加好的订单表
											orderTableService.deleteByPrimaryKey(orderTable.getId());
											// 根据订单id删除添加好的订单详情
											orderDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除订单状态详情表
											orderStateDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除线下支付表
											offlinePaymentService.deleteBuOrderId(orderTable.getId());
											// 根据订单id删除发票表
											invoiceService.deleteByOrderId(orderTable.getId());

											result.put("code", 12003);
											result.put("msg", "订单提交失败");
											return result;
										}
									}

								} // ^^^^^^发票信息存储成功 end^^^^^^^

								else {
									// 先删除添加好的订单表
									orderTableService.deleteByPrimaryKey(orderTable.getId());
									// 根据订单id删除添加好的订单详情
									orderDetailService.deleteByOrderId(orderTable.getId());
									// 根据订单id删除订单状态详情表
									orderStateDetailService.deleteByOrderId(orderTable.getId());
									// 根据订单id删除线下支付表*********
									offlinePaymentService.deleteBuOrderId(orderTable.getId());

									result.put("code", 12003);
									result.put("msg", "订单提交失败");
									return result;
								}

							}
							// ###############发票存储 end######################
							// 不用开发票
							else {

								// -----------------需要减库存 购销存中----------
								List<Map<String, Object>> gxcGoods_gsdId = this.updateGXCStockByOrderNum(orderTable);

								// 修改购销存的库存成功
								if (gxcGoods_gsdId.size() == orderTable.getOrderDetails().size()) {
									// 判断保存的结果是否成功，若成功则返回200，若不成功则返回提交失败，删除之前保存成功的信息

									// 若是从购物车中进入该页面，则删除购物车中的信息
									if (orderTable.getIsFromShoppingCart() == 1) {
										for (int i = 0; i < orderTable.getOrderDetails().size(); i++) {
											shoppingCartService.deleteByGoodsIdAndGsdIdAndUId(
													orderTable.getOrderDetails().get(i).getGoodsDetailsId(), orderTable
															.getOrderDetails().get(i).getGoodsSpecificationDetailsId(),
													orderTable.getUserId());
										}
									}

									// 若订单使用了优惠券，则需要修改优惠券的状态
									if (orderTable.getIsUseCoupon() == 1) {
										orderTable.getUserCoupons().setUseTime(new Date());
										orderTable.getUserCoupons().setUserId(orderTable.getUserId());
										if (userCouponsService.updateByUserIdAndId(orderTable.getUserCoupons()) > 0) {
											result.put("code", 200);
											result.put("msg", "请求成功");
											String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
											result.put("resultData", ids);
											return result;
										} else {
											for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
												GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
												goodsService.updateCommodityOccupiedInventoryError(
														(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
														gsDetails.getCommoditySpecificationId());
											}
											// 先删除添加好的订单表
											orderTableService.deleteByPrimaryKey(orderTable.getId());
											// 根据订单id删除添加好的订单详情
											orderDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除订单状态详情表
											orderStateDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除线下支付表
											offlinePaymentService.deleteBuOrderId(orderTable.getId());

											result.put("code", 12003);
											result.put("msg", "订单提交失败");
											return result;
										}

									} else {
										// result.put("resultData", orderTable);
										result.put("code", 200);
										result.put("msg", "请求成功");
										String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
										result.put("resultData", ids);
										return result;
									}

								}
								// 修改购销存库不成功或个别不成功
								else {
									// 修改购销存库个别不成功,先把之前修改过的数据还原再删除之间加过的数据
									if (gxcGoods_gsdId.size() > 0) {
										for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
											GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
											goodsService.updateCommodityOccupiedInventoryError(
													(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
													gsDetails.getCommoditySpecificationId());
										}
										// 先删除添加好的订单表
										orderTableService.deleteByPrimaryKey(orderTable.getId());
										// 根据订单id删除添加好的订单详情
										orderDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除订单状态详情表
										orderStateDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除线下支付表
										offlinePaymentService.deleteBuOrderId(orderTable.getId());

										result.put("code", 12003);
										result.put("msg", "订单提交失败");
										return result;
									} else {
										// 先删除添加好的订单表
										orderTableService.deleteByPrimaryKey(orderTable.getId());
										// 根据订单id删除添加好的订单详情
										orderDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除订单状态详情表
										orderStateDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除线下支付表
										offlinePaymentService.deleteBuOrderId(orderTable.getId());

										result.put("code", 12003);
										result.put("msg", "订单提交失败");
										return result;
									}
								}
							}

						}
						// ^^^^^^线下支付信息存储成功 end^^^^^^^

						else {
							// 先删除添加好的订单表
							orderTableService.deleteByPrimaryKey(orderTable.getId());
							// 根据订单id删除添加好的订单详情
							orderDetailService.deleteByOrderId(orderTable.getId());
							// 根据订单id删除订单状态详情表
							orderStateDetailService.deleteByOrderId(orderTable.getId());
							result.put("code", 12003);
							result.put("msg", "订单提交失败");
							return result;
						}
					}
					// ---线下支付end---

					// ---线上支付start----
					else {
						// --------------------------若有发票，需要往发票表中存储-----------------------------------
						if (orderTable.getIsHasInvoice() == 1) {

							orderTable.getInvoice().setOrderId(orderTable.getId());
							invoiceResult = invoiceService.insertSelective(orderTable.getInvoice());
							// ^^^^^^^^^^发票存成功 start^^^^^^^^^^^^^^
							if (invoiceResult > 0) {

								// -----------------需要减库存 购销存中----------
								List<Map<String, Object>> gxcGoods_gsdId = this.updateGXCStockByOrderNum(orderTable);

								// 修改购销存的库存成功
								if (gxcGoods_gsdId.size() == orderTable.getOrderDetails().size()) {
									// 判断保存的结果是否成功，若成功则返回200，若不成功则返回提交失败，删除之前保存成功的信息

									// 若是从购物车中进入该页面，则删除购物车中的信息
									if (orderTable.getIsFromShoppingCart() == 1) {
										for (int i = 0; i < orderTable.getOrderDetails().size(); i++) {
											shoppingCartService.deleteByGoodsIdAndGsdIdAndUId(
													orderTable.getOrderDetails().get(i).getGoodsDetailsId(), orderTable
															.getOrderDetails().get(i).getGoodsSpecificationDetailsId(),
													orderTable.getUserId());
										}
									}

									// 若订单使用了优惠券，则需要修改优惠券的状态
									if (orderTable.getIsUseCoupon() == 1) {
										orderTable.getUserCoupons().setUseTime(new Date());
										orderTable.getUserCoupons().setUserId(orderTable.getUserId());
										if (userCouponsService.updateByUserIdAndId(orderTable.getUserCoupons()) > 0) {
											result.put("code", 200);
											result.put("msg", "请求成功");
											String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
											result.put("resultData", ids);
											return result;
										} else {
											for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
												GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
												goodsService.updateCommodityOccupiedInventoryError(
														(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
														gsDetails.getCommoditySpecificationId());
											}
											// 先删除添加好的订单表
											orderTableService.deleteByPrimaryKey(orderTable.getId());
											// 根据订单id删除添加好的订单详情
											orderDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除订单状态详情表
											orderStateDetailService.deleteByOrderId(orderTable.getId());
											// 根据订单id删除发票表
											invoiceService.deleteByOrderId(orderTable.getId());

											result.put("code", 12003);
											result.put("msg", "订单提交失败");
											return result;
										}

									} else {
										// result.put("resultData", orderTable);
										result.put("code", 200);
										result.put("msg", "请求成功");
										String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
										result.put("resultData", ids);
										return result;
									}

								}
								// 修改购销存库不成功或个别不成功
								else {
									// 修改购销存库个别不成功,先把之前修改过的数据还原再删除之间加过的数据
									if (gxcGoods_gsdId.size() > 0) {
										for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
											GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
											goodsService.updateCommodityOccupiedInventoryError(
													(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
													gsDetails.getCommoditySpecificationId());
										}
										// 先删除添加好的订单表
										orderTableService.deleteByPrimaryKey(orderTable.getId());
										// 根据订单id删除添加好的订单详情
										orderDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除订单状态详情表
										orderStateDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除发票表
										invoiceService.deleteByOrderId(orderTable.getId());

										result.put("code", 12003);
										result.put("msg", "订单提交失败");
										return result;
									} else {
										// 先删除添加好的订单表
										orderTableService.deleteByPrimaryKey(orderTable.getId());
										// 根据订单id删除添加好的订单详情
										orderDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除订单状态详情表
										orderStateDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除发票表
										invoiceService.deleteByOrderId(orderTable.getId());

										result.put("code", 12003);
										result.put("msg", "订单提交失败");
										return result;
									}
								}
							} // ^^^^^^^^^^发票存成功 end^^^^^^^^^^^^^^

							else {
								// 先删除添加好的订单表
								orderTableService.deleteByPrimaryKey(orderTable.getId());
								// 根据订单id删除添加好的订单详情
								orderDetailService.deleteByOrderId(orderTable.getId());
								// 根据订单id删除订单状态详情表
								orderStateDetailService.deleteByOrderId(orderTable.getId());

								result.put("code", 12003);
								result.put("msg", "订单提交失败");
								return result;
							}

						} // ---------发票存储 end-------------

						else {

							// -----------------需要减库存 购销存中----------
							List<Map<String, Object>> gxcGoods_gsdId = this.updateGXCStockByOrderNum(orderTable);

							// 修改购销存的库存成功
							if (gxcGoods_gsdId.size() == orderTable.getOrderDetails().size()) {
								// 判断保存的结果是否成功，若成功则返回200，若不成功则返回提交失败，删除之前保存成功的信息

								// 若是从购物车中进入该页面，则删除购物车中的信息
								if (orderTable.getIsFromShoppingCart() == 1) {
									for (int i = 0; i < orderTable.getOrderDetails().size(); i++) {
										shoppingCartService.deleteByGoodsIdAndGsdIdAndUId(
												orderTable.getOrderDetails().get(i).getGoodsDetailsId(),
												orderTable.getOrderDetails().get(i).getGoodsSpecificationDetailsId(),
												orderTable.getUserId());
									}
								}

								// 若订单使用了优惠券，则需要修改优惠券的状态
								if (orderTable.getIsUseCoupon() == 1) {
									orderTable.getUserCoupons().setUseTime(new Date());
									orderTable.getUserCoupons().setUserId(orderTable.getUserId());
									if (userCouponsService.updateByUserIdAndId(orderTable.getUserCoupons()) > 0) {
										result.put("code", 200);
										result.put("msg", "请求成功");
										String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
										result.put("resultData", ids);
										return result;
									} else {
										for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
											GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
											goodsService.updateCommodityOccupiedInventoryError(
													(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
													gsDetails.getCommoditySpecificationId());
										}
										// 先删除添加好的订单表
										orderTableService.deleteByPrimaryKey(orderTable.getId());
										// 根据订单id删除添加好的订单详情
										orderDetailService.deleteByOrderId(orderTable.getId());
										// 根据订单id删除订单状态详情表
										orderStateDetailService.deleteByOrderId(orderTable.getId());

										result.put("code", 12003);
										result.put("msg", "订单提交失败");
										return result;
									}

								} else {
									// result.put("resultData", orderTable);
									result.put("code", 200);
									result.put("msg", "请求成功");
									String[] ids = { orderTable.getId() + "", orderTable.getOrderNo() };
									result.put("resultData", ids);
									return result;
								}

							}
							// 修改购销存库不成功或个别不成功
							else {
								// 修改购销存库个别不成功,先把之前修改过的数据还原再删除之间加过的数据
								if (gxcGoods_gsdId.size() > 0) {
									for (int g = 0; g < gxcGoods_gsdId.size(); g++) {
										GoodsSpecificationDetails gsDetails=goodsSpecificationDetailsService.selectByPrimaryKey((int) gxcGoods_gsdId.get(g).get("gsdId"));
										goodsService.updateCommodityOccupiedInventoryError(
												(int) gxcGoods_gsdId.get(g).get("goodsQuantity"),
												gsDetails.getCommoditySpecificationId());
									}
									// 先删除添加好的订单表
									orderTableService.deleteByPrimaryKey(orderTable.getId());
									// 根据订单id删除添加好的订单详情
									orderDetailService.deleteByOrderId(orderTable.getId());
									// 根据订单id删除订单状态详情表
									orderStateDetailService.deleteByOrderId(orderTable.getId());

									result.put("code", 12003);
									result.put("msg", "订单提交失败");
									return result;
								} else {
									// 先删除添加好的订单表
									orderTableService.deleteByPrimaryKey(orderTable.getId());
									// 根据订单id删除添加好的订单详情
									orderDetailService.deleteByOrderId(orderTable.getId());
									// 根据订单id删除订单状态详情表
									orderStateDetailService.deleteByOrderId(orderTable.getId());

									result.put("code", 12003);
									result.put("msg", "订单提交失败");
									return result;
								}
							}
						}
					}
					// 线上支付end

				}
				// 订单状态详情表添加失败
				else {
					// 先删除添加好的订单表
					orderTableService.deleteByPrimaryKey(orderTable.getId());
					// 根据订单id删除添加好的订单详情
					orderDetailService.deleteByOrderId(orderTable.getId());
					result.put("code", 12003);
					result.put("msg", "订单提交失败");
					return result;
				}

			}
			// 订单详情表添加失败
			// 订单详情表有部分添加成功或都没有添加成功
			else {
				// 先删除刚才添加好的订单表
				orderTableService.deleteByPrimaryKey(orderTable.getId());
				// 大于0表示有部分添加成功，此时要根据订单id删除那部分添加好的订单详情
				if (orderDetailResult > 0) {
					orderDetailService.deleteByOrderId(orderTable.getId());
				}
				result.put("code", 12003);
				result.put("msg", "订单提交失败");
				return result;
			}

		} else {
			// 订单表添加失败
			result.put("code", 12003);
			result.put("msg", "订单提交失败");
			return result;
		}

	}

	/**
	 * 获取用户的订单列表
	 * 
	 * @param request
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getOrderList", method = RequestMethod.GET)
	public JSONObject getOrderList(HttpServletRequest request, @RequestParam("userId") Integer userId)
			throws Exception {
		JSONObject result = new JSONObject();
		// 判断参数的正确性
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

		List<OrderTable> orderTableList = new ArrayList<>();
		orderTableList = orderTableService.selectOrderTableListByUserId(userId);

		// 判断是否有评价
		for (int i = 0; i < orderTableList.size(); i++) {
			int evalNum = 0;
			for (int e = 0; e < orderTableList.get(i).getOrderDetails().size(); e++) {
				// 说明已评价
				if (orderTableList.get(i).getOrderDetails().get(e).getGoodsEvaluation() != null) {
					evalNum++;
				}
			}
			if (evalNum == orderTableList.get(i).getOrderDetails().size()) {
				orderTableList.get(i).setIsHasEvaluation(1);
			} else {
				orderTableList.get(i).setIsHasEvaluation(0);
			}
		}
		result.put("resultData", orderTableList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 查看订单详情
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
	@RequestMapping(value = "getOrderDetail", method = RequestMethod.GET)
	public JSONObject getOrderDetail(HttpServletRequest request, @RequestParam("orderId") Integer orderId,
			@RequestParam("userId") Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 判断参数的正确性
		if ((userId == null || userId <= 0) || (orderId == null || orderId <= 0)) {
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
		// 判断该用户下的此订单是否存在
		OrderTable orderMsg = orderTableService.checkOrderDetailsByOrderId(orderId + "");

		if (orderMsg == null) {
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		} else {
			if (orderMsg.getUserId() == null) {
				result.put("code", 12002);
				result.put("msg", "订单异常");
				return result;
			} else if (orderMsg.getUserId() != userId) {
				result.put("code", 12001);
				result.put("msg", "该用户下的此订单不存在");
				return result;
			}
		}

		int evalNum = 0;
		for (OrderDetail detail : orderMsg.getOrderDetails()) {
			GoodsEvaluation evaluation = evaluationService.selectByOrderDetailId(detail.getId());
			// 说明已评价
			if (evaluation != null) {
				evalNum++;
			}
		}

		if (evalNum == orderMsg.getOrderDetails().size()) {
			orderMsg.setIsHasEvaluation(1);
		} else {
			orderMsg.setIsHasEvaluation(0);
		}

		result.put("resultData", orderMsg);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 查看未评价订单详情
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
	@RequestMapping(value = "getOrderDetailNoEvaluation", method = RequestMethod.GET)
	public JSONObject getOrderDetailNoEvaluation(HttpServletRequest request, @RequestParam("orderId") Integer orderId,
			@RequestParam("userId") Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 判断参数的正确性
		if ((userId == null || userId <= 0) || (orderId == null || orderId <= 0)) {
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
		// 判断该用户下的此订单是否存在
		OrderTable orderMsg = orderTableService.checkOrderDetailsByOrderId(orderId + "");
		if (orderMsg == null) {
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		} else {
			if (orderMsg.getUserId() == null) {
				result.put("code", 12002);
				result.put("msg", "订单异常");
				return result;
			} else if (orderMsg.getUserId() != userId) {
				result.put("code", 12001);
				result.put("msg", "该用户下的此订单不存在");
				return result;
			}
		}
		List<OrderDetail> details = new ArrayList<OrderDetail>();
		for (OrderDetail detail : orderMsg.getOrderDetails()) {
			GoodsEvaluation evaluation = evaluationService.selectByOrderDetailId(detail.getId());
			if (evaluation == null) {
				details.add(detail);
			}

		}
		orderMsg.setOrderDetails(details);
		result.put("resultData", orderMsg);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 订单跟踪
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
	@RequestMapping(value = "getOrderStateDetail", method = RequestMethod.GET)
	public JSONObject getOrderStateDetail(HttpServletRequest request, @RequestParam("orderId") Integer orderId,
			@RequestParam("userId") Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 判断参数的正确性
		if ((userId == null || userId <= 0) || (orderId == null || orderId <= 0)) {
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
		// 判断该用户下的此订单是否存在
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
		if (orderMsg == null) {
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		} else {
			if (orderMsg.getUserId() == null) {
				result.put("code", 12002);
				result.put("msg", "订单异常");
				return result;
			} else if (orderMsg.getUserId() != userId) {
				result.put("code", 12001);
				result.put("msg", "该用户下的此订单不存在");
				return result;
			}
		}
		List<OrderStateDetail> orderStateDetails = new ArrayList<>();
		orderStateDetails = orderStateDetailService.selectByOrderId(orderId);

		result.put("resultData", orderStateDetails);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 用户操作订单
	 * 
	 * @param request
	 * @param id
	 *            需要操作的订单id
	 * @param userId
	 *            用户id
	 * @param operation
	 *            操作(4:取消订单,1:待发货(线上支付后状态自动变为待发货),3:已完成(确认收货后，订单的状态变为已完成)11:删除订单
	 *            )
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "cancleOrder", method = RequestMethod.POST)
	public JSONObject cancleOrder(HttpServletRequest request, @RequestParam Integer orderId,
			@RequestParam Integer userId, @RequestParam Integer operation) throws Exception {
		JSONObject result = new JSONObject();
		// 1.判断参数的有效性
		if ((orderId == null || orderId <= 0) || (userId == null || userId <= 0)
				|| (operation == null || (operation != 1 && operation != 3 && operation != 4 && operation != 11))) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 2.判断该用户是否存在
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
		// 3.判断该用户下是否有该订单
		OrderTable orderMsg = orderTableService.selectByPrimaryKey(orderId);
		if (orderMsg == null) {
			result.put("code", 12001);
			result.put("msg", "订单不存在");
			return result;
		} else {
			if (orderMsg.getUserId() == null) {
				result.put("code", 12002);
				result.put("msg", "订单异常");
				return result;
			} else if (orderMsg.getUserId() != userId) {
				result.put("code", 12001);
				result.put("msg", "该用户下的此订单不存在");
				return result;
			}
			// 用户操作取消订单
			if (operation == 4) {
				// 0表示待支付，1表示待发货，其余的暂不支持取消
				if (orderMsg.getOrderState() >= 1) {
					result.put("code", 12005);
					result.put("msg", "订单暂不支持取消");
					return result;
				}
			}
			// 线上支付
			else if (operation == 1) {
				// 0:待付款
				if (orderMsg.getOrderState() != 0) {
					result.put("code", 12006);
					result.put("msg", "订单暂不用付款");
					return result;
				}
			} else if (operation == 11) {
				// 11:删除订单
				if (orderMsg.getOrderState() != 3 && orderMsg.getOrderState() != 4 && orderMsg.getOrderState() != 5
						&& orderMsg.getOrderState() != 7 && orderMsg.getOrderState() != 8
						&& orderMsg.getOrderState() != 10) {
					result.put("code", 12010);
					result.put("msg", "订单暂不支持删除");
					return result;
				}
			}
			// 用户确认收货
			else {
				// 2:待收货
				if (orderMsg.getOrderState() != 2) {
					result.put("code", 12007);
					result.put("msg", "订单暂不能收货");
					return result;
				}
			}
		}

		// 修改订单的状态为传进来的值
		// operation(4:取消订单,1:待发货(线上支付后状态自动变为待发货),3:已完成(确认收货后，订单的状态变为已完成))
		OrderTable orderTable = new OrderTable();
		orderTable.setId(orderId);
		orderTable.setOrderState(operation);
		if (operation == 1) {
			orderTable.setPayTime(new Date());
		}
		// 状态修改成功
		if (orderTableService.updateByPrimaryKeySelective(orderTable) > 0) {
			// 在订单状态详情中添加（用户取消订单）的状态
			OrderStateDetail orderStateDetail = new OrderStateDetail();
			orderStateDetail.setAddTime(new Date());
			orderStateDetail.setOrderId(orderId);
			if (operation == 4) {
				orderStateDetail.setOrderStateDetail("用户取消订单");
				if (operation == 4) {
					orderTable.setOrderDetails(orderDetailService.selectByOrderId(orderId));
					if (!updateGXCStockByOrderNumForCancelOrder_error(orderTable)) {
						updateGXCStockByOrderNumForCancelOrder(orderTable);
						orderTable.setOrderState(orderMsg.getOrderState());
						orderTableService.updateByPrimaryKeySelective(orderTable);
						result.put("code", 12004);
						result.put("msg", "订单取消失败");
						return result;
					}
				}
			}
			// 线上支付
			else if (operation == 1) {
				orderStateDetail.setOrderStateDetail("订单支付成功");
			}
			// 用户确认收货
			else {
				orderStateDetail.setOrderStateDetail("订单完成");
				for (OrderDetail detail : orderDetailService.selectByOrderId(orderMsg.getId())) {
					goodsSpecificationDetailsService.updateGoodsSpecificationDetailsSalesCount(
							detail.getGoodsSpecificationDetailsId(), detail.getGoodsQuantity());
				}
			}

			// 添加状态成功
			if (orderStateDetailService.insertSelective(orderStateDetail) > 0) {
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			} else {
				// 添加状态失败，此时要删除之前修改好的订单状态
				orderTable.setOrderState(orderMsg.getOrderState());
				orderTableService.updateByPrimaryKeySelective(orderTable);
				updateGXCStockByOrderNumForCancelOrder(orderTable);
				if (operation == 4) {
					result.put("code", 12004);
					result.put("msg", "订单取消失败");
				}
				// 线上支付
				else if (operation == 1) {
					result.put("code", 12008);
					result.put("msg", "订单付款失败");
				}
				// 用户确认收货
				else {
					result.put("code", 12009);
					result.put("msg", "订单收货失败");
				}

				return result;
			}
		} else {
			if (operation == 4) {
				result.put("code", 12004);
				result.put("msg", "订单取消失败");
			}
			// 线上支付
			else if (operation == 1) {
				result.put("code", 12008);
				result.put("msg", "订单付款失败");
			}
			// 用户确认收货
			else {
				result.put("code", 12009);
				result.put("msg", "订单收货失败");
			}
			return result;
		}
	}
	
	/**
	 * 修改订单状态为待发货
	 * @param request
	 * @param orderNo
	 * @param totalFee
	 * @param payMode
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateSatetIsOne", method = RequestMethod.POST)
	public JSONObject updateSatetIsOne(HttpServletRequest request, @RequestParam String orderNo,
			@RequestParam double totalFee, @RequestParam Integer payMode) throws Exception {
		JSONObject result = new JSONObject();
		// 1.判断参数的有效性
		if ((orderNo == null || orderNo == "") || totalFee < 0 || (payMode == null || (payMode != 1 && payMode != 2 && payMode != 3 ))) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		jlpay.pay(orderNo,totalFee,payMode);
		
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	// ###################私有方法################

	/**
	 * 修改购销存的库存
	 * 
	 * @param orderTable
	 * @return
	 */
	private List<Map<String, Object>> updateGXCStockByOrderNum(OrderTable orderTable) {
		List<Map<String, Object>> gxcGoods_gsdId = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for (int k = 0; k < orderTable.getOrderDetails().size(); k++) {
			GoodsSpecificationDetails goodsSpecificationDetails=goodsSpecificationDetailsService.selectByPrimaryKey(orderTable.getOrderDetails().get(k).getGoodsSpecificationDetailsId());
			if (goodsService.updateCommodityOccupiedInventory(orderTable.getOrderDetails().get(k).getGoodsQuantity(),
					goodsSpecificationDetails.getCommoditySpecificationId()) > 0) {
				map = new HashMap<>();
				map.put("goodsQuantity", orderTable.getOrderDetails().get(k).getGoodsQuantity());
				map.put("gsdId", orderTable.getOrderDetails().get(k).getGoodsSpecificationDetailsId());
				gxcGoods_gsdId.add(map);
			}
		}
		return gxcGoods_gsdId;
	}

	/**
	 * 取消订单后 修改购销存的库存
	 * 
	 * @param orderTable
	 * @return
	 */
	private boolean updateGXCStockByOrderNumForCancelOrder_error(OrderTable orderTable) {
		boolean success = true;
		for (int k = 0; k < orderTable.getOrderDetails().size(); k++) {
			GoodsSpecificationDetails goodsSpecificationDetails=goodsSpecificationDetailsService.selectByPrimaryKey(orderTable.getOrderDetails().get(k).getGoodsSpecificationDetailsId());
			if (goodsService.updateCommodityOccupiedInventoryError(orderTable.getOrderDetails().get(k).getGoodsQuantity(),
					goodsSpecificationDetails.getCommoditySpecificationId()) <= 0) {
				success = false;
				break;
			}
		}
		return success;
	}

	/**
	 * 取消订单后 修改购销存的库存
	 * 
	 * @param orderTable
	 * @return
	 */
	private boolean updateGXCStockByOrderNumForCancelOrder(OrderTable orderTable) {
		boolean success = true;
		for (int k = 0; k < orderTable.getOrderDetails().size(); k++) {
			GoodsSpecificationDetails goodsSpecificationDetails=goodsSpecificationDetailsService.selectByPrimaryKey(orderTable.getOrderDetails().get(k).getGoodsSpecificationDetailsId());
			if (goodsService.updateCommodityOccupiedInventory(orderTable.getOrderDetails().get(k).getGoodsQuantity(),
					goodsSpecificationDetails.getCommoditySpecificationId()) <= 0) {
				success = false;
				break;
			}
		}
		return success;
	}
	
	
	private String insertOrder2JLPSI(JSONObject json) {

        HttpClient client = new DefaultHttpClient();
        String URL = "http://39.105.115.214:8001/JLPSI/sales/salesOrder/addSalesOrder?planOrTable=2";
        HttpPost post = new HttpPost(URL);
        post.setHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Basic YWRtaW46");
        String result = "";
        
        try {

            StringEntity s = new StringEntity(json.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();

            result = strber.toString();
            System.out.println(result);
            
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                
                    System.out.println("请求服务器成功，做相应处理");
                
            } else {
                
                System.out.println("请求服务端失败");
                
            }
            

        } catch (Exception e) {
            System.out.println("请求异常");
            throw new RuntimeException(e);
        }

        return result;
		
		
	}

}
