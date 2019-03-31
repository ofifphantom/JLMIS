package com.jl.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.model.ShippingAddress;
import com.jl.mis.model.ShoppingCart;
import com.jl.mis.model.User;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsDisplayPictureService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.OrderDetailService;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.service.ShoppingCartService;
import com.jl.mis.service.UserService1;

/**
 * 购物车信息
 * 
 * @author 柳亚婷
 * @date 2017年12月19日 下午2:06:38
 * @Description
 *
 */
@Controller
@RequestMapping("/shoppingCart/")
public class ShoppingCartApi {
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	@Autowired
	private GoodsDisplayPictureService goodsDisplayPictureService;
	@Autowired
	private ShoppingCartService shoppingCartService;
	@Autowired
	private GoodsActivityService goodsActivityService;
	@Autowired
	private UserService1 userService1;
	@Autowired
	private OrderTableService orderTableService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private ActivityInformationService activityInformationService;
	/**
	 * 根据用户ID获取用户的购物车信息
	 * 
	 * @param request
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getShoppingCart", method = RequestMethod.GET)
	public JSONObject getShoppingCart(HttpServletRequest request, @RequestParam("userId") Integer userId)
			throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
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
		List<ShoppingCart> shoppingCartList = new ArrayList<>();
		shoppingCartList = shoppingCartService.getShoppingCarByUserId(userId);
		ShoppingCart cart = new ShoppingCart();
		// 循环获取的信息，判断购物车中的信息是否是有效的
		for (int i = 0; i < shoppingCartList.size(); i++) {
			ShoppingCart shoppingCart = shoppingCartList.get(i);
			// 获取对应的商品规格id，判断该商品规格的状态，及时修改购物车中该信息的状态
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectByPrimaryKey(shoppingCart.getGoodsSpecificationDetailsId());
			cart = new ShoppingCart();
			
			
			// 说明该商品规格不存在，此时要把购物车中的该信息的状态改为无效
			if (goodsSpecificationDetails == null) {
				cart.setId(shoppingCart.getId());
				cart.setState(1);
				shoppingCartService.updateByPrimaryKeySelective(cart);
			} else {
				if(goodsSpecificationDetails.getPrice()!=cart.getGoodsUnitlPrice()){
					cart.setGoodsUnitlPrice(goodsSpecificationDetails.getPrice());
				}
				//说明该商品不是预售，则判断状态，是预售，活动下架购物车里商品的状态会进行修改，这里不用再二次判断
				if(shoppingCart.getActivityId()==null||shoppingCart.getActivityId()<=0){
					// 说明该商品上架(可能为重新上架)，此时要把购物车中的该信息的状态改为有效
					if (goodsSpecificationDetails.getState() == 0) {
						cart.setId(shoppingCart.getId());
						cart.setState(0);
						shoppingCartService.updateByPrimaryKeySelective(cart);
					}
					// 说明该商品已下架，此时要把购物车中的该信息的状态改为无效
					else {
						cart.setId(shoppingCart.getId());
						cart.setState(1);
						shoppingCartService.updateByPrimaryKeySelective(cart);
					}
				}
				
			}
		}
		
		
		// 重新获取一遍信息,此时获取商品的所参加的活动
		shoppingCartList = shoppingCartService.getShoppingCarByUserId(userId);
		if (shoppingCartList.size() > 0) {
			for (int i = 0; i < shoppingCartList.size(); i++) {
				// 先获取商品信息，然后查找该商品参与的活动信息
				GoodsDetails goodsDetails = goodsDetailsService
						.getGoodsDetailAndEvaluationByGoodsId(shoppingCartList.get(i).getGoodsDetailsId());
				List<GoodsActivity> goodsActivityList = new ArrayList<>();
				if (goodsDetails != null) {
					// 传入商品id和isGoodsId=‘true’/分类id和isGoodsId=‘false’
					// 把查出来的结果整合即是商品所参与的所有活动，放入商品信息中
					List<GoodsActivity> goodsActivityListByGoodsId = goodsActivityService
							.getGoodsActivityMsgByStateAndGoodsId(goodsDetails.getId(), "true");
					List<GoodsActivity> goodsActivityListByClassificationId = goodsActivityService
							.getGoodsActivityMsgByStateAndGoodsId(goodsDetails.getClassificationId(), "false");
					if (goodsActivityListByGoodsId != null && goodsActivityListByGoodsId.size() > 0) {
						for (int j = 0; j < goodsActivityListByGoodsId.size(); j++) {
							goodsActivityList.add(goodsActivityListByGoodsId.get(j));
						}
					}
					if (goodsActivityListByClassificationId != null && goodsActivityListByClassificationId.size() > 0) {
						for (int j = 0; j < goodsActivityListByClassificationId.size(); j++) {
							goodsActivityList.add(goodsActivityListByClassificationId.get(j));
						}
					}
					shoppingCartList.get(i).getGoodsDetails().setGoodsActivitys(goodsActivityList);
				}
			}
		}
		result.put("resultData", shoppingCartList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 添加购物车
	 * 
	 * @param request
	 * @param goodsDetailsId
	 *            商品id
	 * @param goodsSpecificationDetailsId
	 *            商品规格id
	 * @param goodsNum
	 *            加入购物车的数量
	 * @param userId
	 *            用户id
	 * @param activityId
	 *            活动id (针对预售活动 需要传此参数) 若不是预售 传0
	 * @param activityName
	 *            活动名称 (针对预售活动 需要传此参数) 若不是预售 传""
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertShoppingCart", method = RequestMethod.POST)
	public JSONObject insertShoppingCart(HttpServletRequest request, @RequestParam Integer goodsDetailsId,
			@RequestParam Integer goodsSpecificationDetailsId, @RequestParam Integer goodsNum,
			@RequestParam Integer userId,@RequestParam Integer activityId,@RequestParam String activityName) throws Exception {
		JSONObject result = new JSONObject();
		int sqlResult = -1;
		// 验证参数的合法性
		if ((goodsDetailsId == null || goodsDetailsId <= 0)
				|| (goodsSpecificationDetailsId == null || goodsSpecificationDetailsId <= 0)
				|| (goodsNum == null || goodsNum <= 0) || (userId == null || userId <= 0)) {
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
		// 根据商品id获取商品信息
		GoodsDetails goodsDetails = goodsDetailsService.selectByPrimaryKey(goodsDetailsId);
		if (goodsDetails == null) {
			result.put("code", 80002);
			result.put("msg", "查不到商品信息!");
			return result;
		} else {
			// 根据商品详情id获取商品详情信息
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectGoodsSpecificationDetailsByIdAndGoodsId(goodsSpecificationDetailsId, goodsDetailsId);
			if (goodsSpecificationDetails == null) {
				result.put("code", 80001);
				result.put("msg", "查不到该商品规格或该规格已下架!");
				return result;
			} else {			
				//不是预售商品
				if(activityId==0){
					// 查询购物车表，判断该商品的该规格在有效的范围内是否存在，若存在，只用数量加1，若不存在，再重新加
					ShoppingCart shoppingCartByGoodsIdAndSpecId = shoppingCartService
							.getShoppingCartByGoodsIdAndSpecId(goodsDetailsId, goodsSpecificationDetailsId, userId);
					ShoppingCart shoppingCart = new ShoppingCart();
					// 购物车存在该商品，因此只用数量+1即可
					if (shoppingCartByGoodsIdAndSpecId != null) {
						//不允许0库存出库
						if(goodsDetails.getZeroStock()==0){
							GoodsSpecificationDetails specificationDetails=goodsSpecificationDetailsService.selectGxcGoodsStockByspecificationId(goodsSpecificationDetailsId);
							//判断现在购物车的添加该商品的数量是否大于剩余库存
							if(shoppingCartByGoodsIdAndSpecId.getGoodsNum()<specificationDetails.getGxcGoodsStock()){
								shoppingCart.setId(shoppingCartByGoodsIdAndSpecId.getId());
								shoppingCart.setGoodsNum(shoppingCartByGoodsIdAndSpecId.getGoodsNum() + goodsNum);
								shoppingCart.setAddTime(new Date());
								sqlResult = shoppingCartService.updateByPrimaryKeySelective(shoppingCart);
							}
							else{
								result.put("code", 90006);
								result.put("msg", "您购物车内的该商品已为最小库存，添加失败!");
								return result;
							}
						}
						//允许0库存出库
						else{
							shoppingCart.setId(shoppingCartByGoodsIdAndSpecId.getId());
							shoppingCart.setGoodsNum(shoppingCartByGoodsIdAndSpecId.getGoodsNum() + goodsNum);
							shoppingCart.setAddTime(new Date());
							sqlResult = shoppingCartService.updateByPrimaryKeySelective(shoppingCart);
						}
						
						
					}
					// 购物车不存在该商品，因此要新添加
					else {
						shoppingCart.setGoodsDetailsId(goodsDetailsId);
						shoppingCart.setGoodsSpecificationDetailsId(goodsSpecificationDetailsId);
						shoppingCart.setAddTime(new Date());
						shoppingCart.setUserId(userId);
						shoppingCart.setGoodsNum(goodsNum);
						shoppingCart.setState(0);// 状态默认为有效
						// 把查出来的商品信息里的商品名称放入购物车表中
						shoppingCart.setGoodsName(goodsDetails.getName());
						// 把查出的商品规格信息里的规格名和价格放入购物车表中
						shoppingCart.setGoodsSpecificationDetailsName(goodsSpecificationDetails.getSpecifications());
						shoppingCart.setGoodsUnitlPrice(goodsSpecificationDetails.getPrice());
						// 根据商品规格ID查询商品展示图，取第一张放入购物车表中
						List<GoodsDisplayPicture> goodsDisplayPictureList = goodsDisplayPictureService
								.getGoodsDisplayPicture(goodsSpecificationDetailsId);
						if (goodsDisplayPictureList != null && goodsDisplayPictureList.size() > 0) {
							shoppingCart.setGoodsPicUrl(goodsDisplayPictureList.get(0).getPicUrl());
						} else {
							shoppingCart.setGoodsPicUrl("");
						}

						sqlResult = shoppingCartService.insertSelective(shoppingCart);
					}

				}
				//是预售商品 此时要根据活动id与商品规格id，商品id以及用户id查询购物车是否存在该商品的信息，若存在，则数量递增，若不存在，则视为新商品
				else{
					// 查询购物车表，判断该商品的该规格在有效的范围内是否存在，若存在，只用数量加1，若不存在，再重新加
					ShoppingCart shoppingCartByGoodsIdAndSpecIdAndActivityId = shoppingCartService
							.getShoppingCartByGoodsIdAndSpecIdAndActivityId(goodsDetailsId, goodsSpecificationDetailsId, userId, activityId);
					ShoppingCart shoppingCart = new ShoppingCart();
					// 购物车存在该商品，因此只用数量+1即可
					if (shoppingCartByGoodsIdAndSpecIdAndActivityId != null) {
						shoppingCart.setId(shoppingCartByGoodsIdAndSpecIdAndActivityId.getId());
						shoppingCart.setGoodsNum(shoppingCartByGoodsIdAndSpecIdAndActivityId.getGoodsNum() + goodsNum);
						shoppingCart.setAddTime(new Date());
						sqlResult = shoppingCartService.updateByPrimaryKeySelective(shoppingCart);
					}
					// 购物车不存在该商品，因此要新添加
					else {
						shoppingCart.setGoodsDetailsId(goodsDetailsId);
						shoppingCart.setGoodsSpecificationDetailsId(goodsSpecificationDetailsId);
						shoppingCart.setAddTime(new Date());
						shoppingCart.setUserId(userId);
						shoppingCart.setGoodsNum(goodsNum);
						shoppingCart.setState(0);// 状态默认为有效
						shoppingCart.setActivityId(activityId);//预售活动id
						shoppingCart.setActivityName(activityName);//预售活动名称
						// 把查出来的商品信息里的商品名称放入购物车表中
						shoppingCart.setGoodsName(goodsDetails.getName());
						// 把查出的商品规格信息里的规格名和价格放入购物车表中
						shoppingCart.setGoodsSpecificationDetailsName(goodsSpecificationDetails.getSpecifications());
						shoppingCart.setGoodsUnitlPrice(goodsSpecificationDetails.getPrice());
						// 根据商品规格ID查询商品展示图，取第一张放入购物车表中
						List<GoodsDisplayPicture> goodsDisplayPictureList = goodsDisplayPictureService
								.getGoodsDisplayPicture(goodsSpecificationDetailsId);
						if (goodsDisplayPictureList != null && goodsDisplayPictureList.size() > 0) {
							shoppingCart.setGoodsPicUrl(goodsDisplayPictureList.get(0).getPicUrl());
						} else {
							shoppingCart.setGoodsPicUrl("");
						}

						sqlResult = shoppingCartService.insertSelective(shoppingCart);
					}
				}
				
				if (sqlResult > 0) {
					result.put("code", 200);
					result.put("msg", "请求成功");
					return result;
				} else {
					result.put("code", 90002);
					result.put("msg", "购物车添加失败!");
					return result;
				}
			}

		}
	}

	/**
	 * 根据id删除加在购物车中的商品信息
	 * 
	 * @param request
	 * @param id
	 *            该需要删除的信息id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteShoppingCartById", method = RequestMethod.POST)
	public JSONObject deleteShoppingCartById(HttpServletRequest request, @RequestParam Integer id,
			@RequestParam Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
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
		// 判断id和userid是否重合,避免有人拦截网址篡改信息
		ShoppingCart shoppingCartById = shoppingCartService.selectByPrimaryKey(id);
		if (shoppingCartById == null) {
			result.put("code", 90001);
			result.put("msg", "该商品在购物车中不存在");
			return result;
		} else {
			// 验证是否是本人操作本人数据
			if (shoppingCartById.getUserId() != userId) {
				result.put("code", 10003);
				result.put("msg", "尝试非法操作");
				return result;
			}
		}
		// 删除
		if (shoppingCartService.deleteByPrimaryKey(id) > 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 90004);
			result.put("msg", "购物车删除失败!");
			return result;
		}
	}

	/**
	 * 批量根据id删除加在购物车中的商品信息
	 * 
	 * @param request
	 * @param id
	 *            该需要删除的信息id数组
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteBatchShoppingCartById", method = RequestMethod.POST)
	public JSONObject deleteBatchShoppingCartById(HttpServletRequest request, @RequestParam int[] ids,
			@RequestParam Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		List<Integer> idList = new ArrayList<>();
		// 验证参数的合法性
		if ((ids == null || ids.length == 0) || (userId == null || userId <= 0)) {
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
		for (int i = 0; i < ids.length; i++) {
			// 判断id和userid是否重合,避免有人拦截网址篡改信息
			ShoppingCart shoppingCartById = shoppingCartService.selectByPrimaryKey(ids[i]);
			if (shoppingCartById != null) {

				// 验证是否是本人操作本人数据
				if (shoppingCartById.getUserId() != userId) {
					result.put("code", 10003);
					result.put("msg", "尝试非法操作");
					return result;
				} else {
					idList.add(ids[i]);
				}
			}

		}
		if (idList.size() > 0) {
			// 删除
			if (shoppingCartService.deleteBatchByPrimaryKey(idList) > 0) {
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			} else {
				result.put("code", 90004);
				result.put("msg", "购物车删除失败!");
				return result;
			}
		} else {
			result.put("code", 90001);
			result.put("msg", "购物车中不存在需要删除的商品");
			return result;
		}
	}

	/**
	 * 加或减商品数量
	 * 
	 * @param request
	 * @param id
	 *            购物车中该条信息的id
	 * @param userId
	 *            用户id
	 * @param operation
	 *            操作 (0:表示减，1：表示加)
	 * @return
	 * @throws Exception
	 */
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "plusOrSubtractGoodsNumById", method =
	 * RequestMethod.POST) public JSONObject
	 * plusOrSubtractGoodsNumById(HttpServletRequest request, @RequestParam
	 * Integer id,
	 * 
	 * @RequestParam Integer userId, @RequestParam Integer operation) throws
	 * Exception { JSONObject result = new JSONObject(); int sqlResult = -1; //
	 * 验证参数的合法性 if ((id == null || id <= 0) || (userId == null || userId <= 0)
	 * || (operation != 0 && operation != 1)) { result.put("code", 10000);
	 * result.put("msg", "参数错误"); return result; } //
	 * 判断id和userid是否重合,避免有人拦截网址篡改信息 ShoppingCart shoppingCartById =
	 * shoppingCartService.selectByPrimaryKey(id); if (shoppingCartById == null)
	 * { result.put("code", 90001); result.put("msg", "该商品在购物车中不存在"); return
	 * result; } else { // 验证是否是本人操作本人数据 if (shoppingCartById.getUserId() !=
	 * userId) { result.put("code", 10003); result.put("msg", "尝试非法操作"); return
	 * result; } } ShoppingCart shoppingCart = new ShoppingCart(); // 减 if
	 * (operation == 0) { // 判断当前数量是否为1 if (shoppingCartById.getGoodsNum() - 1
	 * == 0) { result.put("code", 90005); result.put("msg", "数量不能再减了!"); return
	 * result; } shoppingCart.setId(id);
	 * shoppingCart.setGoodsNum(shoppingCartById.getGoodsNum() - 1); sqlResult =
	 * shoppingCartService.updateByPrimaryKeySelective(shoppingCart); } // 加
	 * else { // 获取该商品规格的库存量 GoodsSpecificationDetails goodsSpecificationDetails
	 * = goodsSpecificationDetailsService
	 * .selectGxcGoodsStockByspecificationId(shoppingCartById.
	 * getGoodsSpecificationDetailsId()); if (goodsSpecificationDetails == null)
	 * { result.put("code", 80001); result.put("msg", "查不到该商品规格或该规格已下架!");
	 * return result; } // 判断增加过后的数量是否会超过库存量 if ((shoppingCartById.getGoodsNum()
	 * + 1) > goodsSpecificationDetails.getGxcGoodsStock()) { result.put("code",
	 * 90005); result.put("msg", "数量已大于库存!"); return result; }
	 * shoppingCart.setId(id);
	 * shoppingCart.setGoodsNum(shoppingCartById.getGoodsNum() + 1); sqlResult =
	 * shoppingCartService.updateByPrimaryKeySelective(shoppingCart); } if
	 * (sqlResult > 0) { result.put("resultData", "修改成功!"); result.put("code",
	 * 200); result.put("msg", "请求成功"); return result; } else {
	 * result.put("resultData", "修改失败！"); result.put("code", 90003);
	 * result.put("msg", "购物车修改失败!"); return result; }
	 * 
	 * }
	 */

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "aaa", method = RequestMethod.POST) public
	 * JSONObject aaa(HttpServletRequest request, @RequestBody
	 * List<ShoppingCart> shoppingCarts) throws Exception { JSONObject result =
	 * new JSONObject(); // shoppingCarts 购物车里的需要修改的信息 json //
	 * //例：[{"id":1,"userId":2},{"id":2,"userId":3}]
	 * result.put("shoppingCarts:", shoppingCarts.size());
	 * result.put("aa:",shoppingCarts.get(1).getId()); return result; }
	 */

	/**
	 * 购物车的编辑事件/(在购物车中+/-数量)
	 * 
	 * @param request
	 * @param id
	 *            购物车中当前要操作信息的id
	 * @param userId
	 *            用户id
	 * @param goodsSpecificationDetailsId
	 *            商品规格id
	 * @param operation
	 *            操作(-1:表示进行规格的替换或同时进行了价格的修改，0:表示进行数量的减小，1:表示进行数量的增加,2:直接修改数量)
	 * @param goodsNum
	 *            商品数量（用于直接修改商品数量(操作为1)/（进行规格的替换或同时进行了价格的修改(操作为-1)））。
	 *            注：操作为-1时，不管有没有进行价格的修改，这里都要传入购物车中该商品的当前购买数量。
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "editShoppingCartGoodsById", method = RequestMethod.POST)
	public JSONObject editShoppingCartGoodsById(HttpServletRequest request, @RequestParam Integer id,
			@RequestParam Integer userId, @RequestParam Integer goodsSpecificationDetailsId,
			@RequestParam Integer operation, @RequestParam Integer goodsNum) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
		if ((id == null || id <= 0) || (userId == null || userId <= 0)
				|| (goodsSpecificationDetailsId == null || goodsSpecificationDetailsId <= 0)
				|| (operation != -1 && operation != 0 && operation != 1 && operation != 2)||(goodsNum<=0)) {
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
		// 判断id和userid是否重合,避免有人拦截网址篡改信息
		ShoppingCart shoppingCartById = shoppingCartService.selectByPrimaryKey(id);
		if (shoppingCartById == null) {
			result.put("code", 90001);
			result.put("msg", "该商品在购物车中不存在");
			return result;
		} else {
			// 验证是否是本人操作本人数据
			if (shoppingCartById.getUserId() != userId) {
				result.put("code", 10003);
				result.put("msg", "尝试非法操作");
				return result;
			}
		}
		ShoppingCart shoppingCart = new ShoppingCart();
		// 表示不进行数量的操作，只是重新选择规格
		// 先判断该规格是否处于上架中，再进行更新的操作
		if (operation == -1) {
			// 判断该商品规格的状态，若为下架，则不能进行添加
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectGoodsSpecificationDetailsByIdAndGoodsId(goodsSpecificationDetailsId,
							shoppingCartById.getGoodsDetailsId());
			if (goodsSpecificationDetails == null) {
				result.put("code", 80001);
				result.put("msg", "查不到该商品规格或该规格已下架!");
				return result;
			} else {
				// 为0说明是上架中，不为0说明已下架或者没有进行上架的操作
				if (goodsSpecificationDetails.getState() != 0) {
					result.put("code", 80001);
					result.put("msg", "查不到该商品规格或该规格已下架!");
					return result;
				}
			}
			// 判断购物车里存储的商品数量是否大于当前所选规格的库存
			// 获取该商品规格的库存量
			GoodsSpecificationDetails getGxcGoodsStock = goodsSpecificationDetailsService
					.selectGxcGoodsStockAndZeroByspecificationId(goodsSpecificationDetailsId);
			if (getGxcGoodsStock == null) {
				result.put("code", 80001);
				result.put("msg", "查不到该商品规格或该规格已下架!");
				return result;
			}
			//不是预售且不允许0库存出库  此时要判断购买数量是否已大于库存
			if(getGxcGoodsStock.getGoodsDetails().getZeroStock()==0&&getGxcGoodsStock.getGxcGoodsState()==2){
				if (shoppingCartById.getGoodsNum() > getGxcGoodsStock.getGxcGoodsStock()) {
					result.put("code", 90005);
					result.put("msg", "数量已大于库存!");
					return result;
				}
			}
			
			//购物类表获取
			List<ShoppingCart> carts=shoppingCartService.getShoppingCarByUserId(userId);
			boolean seam=false;
			//判断是否重复
			for (ShoppingCart shoppingCart2 : carts) {
				if(shoppingCart2.getGoodsSpecificationDetailsId()==goodsSpecificationDetailsId&&shoppingCart2.getId()!=id){
					seam=true;
					shoppingCartService.deleteByPrimaryKey(id);
					id=shoppingCart2.getId();
					goodsNum+=shoppingCart2.getGoodsNum();
					break;
				}
			}
			//删除，修改数量
			
			if(seam){
				shoppingCart.setId(id);
				shoppingCart.setGoodsNum(goodsNum);
			}else{
				shoppingCart.setId(id);
				shoppingCart.setGoodsSpecificationDetailsId(goodsSpecificationDetailsId);
				shoppingCart.setGoodsSpecificationDetailsName(goodsSpecificationDetails.getSpecifications());
				// 根据商品规格ID查询商品展示图，取第一张放入购物车表中
				List<GoodsDisplayPicture> goodsDisplayPictureList = goodsDisplayPictureService
						.getGoodsDisplayPicture(goodsSpecificationDetailsId);
				if (goodsDisplayPictureList != null && goodsDisplayPictureList.size() > 0) {
					shoppingCart.setGoodsPicUrl(goodsDisplayPictureList.get(0).getPicUrl());
				} else {
					shoppingCart.setGoodsPicUrl("");
				}
				shoppingCart.setGoodsUnitlPrice(goodsSpecificationDetails.getPrice());
				shoppingCart.setGoodsNum(goodsNum);
			}
			
		}
		// 减
		else if (operation == 0) {
			// 判断当前数量是否为1
			if (shoppingCartById.getGoodsNum() - 1 == 0) {
				result.put("code", 90005);
				result.put("msg", "数量不能再减了!");
				return result;
			}
			shoppingCart.setId(id);
			shoppingCart.setGoodsNum(shoppingCartById.getGoodsNum() - 1);
		}
		// 加
		else if (operation == 1) {
			// 获取该商品规格的库存量
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectGxcGoodsStockAndZeroByspecificationId(shoppingCartById.getGoodsSpecificationDetailsId());
			if (goodsSpecificationDetails == null) {
				result.put("code", 80001);
				result.put("msg", "查不到该商品规格或该规格已下架!");
				return result;
			}
			//不是预售且不允许0库存出库  此时要判断购买数量是否已大于库存
			if(goodsSpecificationDetails.getGoodsDetails().getZeroStock()==0&&goodsSpecificationDetails.getGxcGoodsState()==2){
				// 判断增加过后的数量是否会超过库存量
				if ((shoppingCartById.getGoodsNum() + 1) > goodsSpecificationDetails.getGxcGoodsStock()) {
					result.put("code", 90005);
					result.put("msg", "数量已大于库存!");
					return result;
				}
			}
			
			shoppingCart.setId(id);
			shoppingCart.setGoodsNum(shoppingCartById.getGoodsNum() + 1);
		}
		// 2:直接修改数量
		else {
			// 获取该商品规格的库存量
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectGxcGoodsStockAndZeroByspecificationId(shoppingCartById.getGoodsSpecificationDetailsId());
			if (goodsSpecificationDetails == null) {
				result.put("code", 80001);
				result.put("msg", "查不到该商品规格或该规格已下架!");
				return result;
			}
			//不是预售且不允许0库存出库  此时要判断购买数量是否已大于库存
			if(goodsSpecificationDetails.getGoodsDetails().getZeroStock()==0&&goodsSpecificationDetails.getGxcGoodsState()==2){
				// 判断传入的数量是否会超过库存量
				if (goodsNum > goodsSpecificationDetails.getGxcGoodsStock()) {
					result.put("code", 90005);
					result.put("msg", "数量已大于库存!");
					return result;
				}
			}
			
			shoppingCart.setId(id);
			shoppingCart.setGoodsNum(goodsNum);
		}
		// 根据id修改购物车表的信息
		if (shoppingCartService.updateByPrimaryKeySelective(shoppingCart) > 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 90003);
			result.put("msg", "购物车修改失败!");
			return result;
		}
	}

	/**
	 * 添加购物车
	 * 
	 * @param request
	 * @param goodsDetailsId
	 *            商品id
	 * @param goodsSpecificationDetailsId
	 *            商品规格id
	 * @param goodsNum
	 *            加入购物车的数量
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "copyorderShoppingCart", method = RequestMethod.POST)
	public JSONObject copyorderShoppingCart(HttpServletRequest request, @RequestParam Integer orderId,
			@RequestParam Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
		if ((orderId == null || orderId <= 0)|| (userId == null || userId <= 0)) {
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
				result.put("msg", "订单不存在");
				return result;
			}
			
		}
		orderMsg.setOrderDetails(orderDetailService.selectByOrderId(orderId));
		List<ShoppingCart> carts=new ArrayList<ShoppingCart>();
		if(orderMsg.getOrderDetails()!=null&&orderMsg.getOrderDetails().size()>0){
			for (OrderDetail elemnet : orderMsg.getOrderDetails()) {
				ShoppingCart shoppingCart = new ShoppingCart();
				//是预售商品订单 此时要根据活动id与商品规格id，商品id以及用户id查询购物车是否存在该商品的信息，若存在，则数量递增，若不存在，则视为新商品
				if(orderMsg.getActivityId()!=null&&orderMsg.getActivityId()>0){
					// 查询购物车表，判断该商品的该规格在有效的范围内是否存在，若存在，只用数量加1，若不存在，再重新加
					ShoppingCart shoppingCartByGoodsIdAndSpecIdAndActivityId = shoppingCartService
							.getShoppingCartByGoodsIdAndSpecIdAndActivityId(elemnet.getGoodsDetailsId(), elemnet.getGoodsSpecificationDetailsId(), userId, orderMsg.getActivityId());
					// 购物车存在该商品，因此只用数量+1即可
					if (shoppingCartByGoodsIdAndSpecIdAndActivityId != null) {
						shoppingCart.setId(shoppingCartByGoodsIdAndSpecIdAndActivityId.getId());
						shoppingCart.setGoodsNum(shoppingCartByGoodsIdAndSpecIdAndActivityId.getGoodsNum() + elemnet.getGoodsQuantity());
						shoppingCart.setAddTime(new Date());
						if(shoppingCartService.updateByPrimaryKeySelective(shoppingCart)<0){
							break;
						}
						shoppingCart.setId(0);
						carts.add(shoppingCart);
					}
					else{
						ActivityInformation activityInformation=activityInformationService.selectByPrimaryKey(orderMsg.getActivityId());
						
						shoppingCart.setUserId(userId);
						if(activityInformation!=null){
							//失效或已下线
							if(activityInformation.getState()!=4){
								shoppingCart.setState(1);
							}
							//上线中
							else{
								shoppingCart.setState(0);
							}	
							shoppingCart.setActivityId(orderMsg.getActivityId());
							shoppingCart.setActivityName(activityInformation.getName());
						}
						else{
							shoppingCart.setState(1);
						}					
						shoppingCart.setGoodsUnitlPrice(elemnet.getGoodsOriginalPrice());
						shoppingCart.setGoodsSpecificationDetailsName(elemnet.getGoodsSpecificationName());
						shoppingCart.setGoodsSpecificationDetailsId(elemnet.getGoodsSpecificationDetailsId());
						shoppingCart.setGoodsPicUrl(elemnet.getGoodsCoverUrl());
						shoppingCart.setGoodsNum(elemnet.getGoodsQuantity());
						shoppingCart.setGoodsName(elemnet.getGoodsName());
						shoppingCart.setGoodsDetailsId(elemnet.getGoodsDetailsId());
						shoppingCart.setAddTime(new Date());
						if(shoppingCartService.insert(shoppingCart)<0){
							break;
						}
						shoppingCart.setId(1);
						carts.add(shoppingCart);
					}
				}
				//不是预售商品
				else{
					ShoppingCart shoppingCartByGoodsIdAndSpecId = shoppingCartService
							.getShoppingCartByGoodsIdAndSpecId(elemnet.getGoodsDetailsId(), elemnet.getGoodsSpecificationDetailsId(), userId);
					
					// 购物车存在该商品，因此只用数量+1即可
					if (shoppingCartByGoodsIdAndSpecId != null) {
						shoppingCart.setId(shoppingCartByGoodsIdAndSpecId.getId());
						shoppingCart.setGoodsNum(shoppingCartByGoodsIdAndSpecId.getGoodsNum() + elemnet.getGoodsQuantity());
						shoppingCart.setAddTime(new Date());
						if(shoppingCartService.updateByPrimaryKeySelective(shoppingCart)<0){
							break;
						}
						shoppingCart.setId(0);
						carts.add(shoppingCart);
					}else{
						shoppingCart.setUserId(userId);
						shoppingCart.setState(0);
						shoppingCart.setGoodsUnitlPrice(elemnet.getGoodsOriginalPrice());
						shoppingCart.setGoodsSpecificationDetailsName(elemnet.getGoodsSpecificationName());
						shoppingCart.setGoodsSpecificationDetailsId(elemnet.getGoodsSpecificationDetailsId());
						shoppingCart.setGoodsPicUrl(elemnet.getGoodsCoverUrl());
						shoppingCart.setGoodsNum(elemnet.getGoodsQuantity());
						shoppingCart.setGoodsName(elemnet.getGoodsName());
						shoppingCart.setGoodsDetailsId(elemnet.getGoodsDetailsId());
						shoppingCart.setAddTime(new Date());
						if(shoppingCartService.insert(shoppingCart)<0){
							break;
						}
						shoppingCart.setId(1);
						carts.add(shoppingCart);
					}
					
				}
				
			}
			if(orderMsg.getOrderDetails().size()>carts.size()){
				for (ShoppingCart shoppingCart : carts) {
					if(shoppingCart.getId()==0){
						for (OrderDetail elemnet : orderMsg.getOrderDetails()) {
							ShoppingCart shoppingCartByGoodsIdAndSpecId = shoppingCartService
									.getShoppingCartByGoodsIdAndSpecId(elemnet.getGoodsDetailsId(), elemnet.getGoodsSpecificationDetailsId(), userId);
							ShoppingCart cat = new ShoppingCart();
							// 购物车存在该商品，因此只用数量+1即可
							if (shoppingCartByGoodsIdAndSpecId != null) {
								cat.setId(shoppingCartByGoodsIdAndSpecId.getId());
								cat.setGoodsNum(shoppingCartByGoodsIdAndSpecId.getGoodsNum() - elemnet.getGoodsQuantity());
								shoppingCartService.updateByPrimaryKeySelective(cat);
								}
						}
					}else{
						shoppingCartService.deleteByPrimaryKey(shoppingCart.getId());
					}
				}
				result.put("code", 90002);
				result.put("msg", "购物车添加失败");
				return result;
			}
		}else{
			result.put("code", 90002);
			result.put("msg", "购物车添加失败");
			return result;
		}
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
		
	}
}
