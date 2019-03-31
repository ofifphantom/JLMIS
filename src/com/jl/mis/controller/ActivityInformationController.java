package com.jl.mis.controller;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.Classification;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.Log;
import com.jl.mis.service.ActiveCouponService;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.CouponInformationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.LogService;
import com.jl.mis.service.ShoppingCartService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 活动Controller
 * @author 景雅倩
 * @date  2017-11-8  下午4:39:52
 * @Description TODO
 */
@Controller
@RequestMapping("/backgroundConfiguration/activity/activityInformation/")
public class ActivityInformationController extends BaseController{

	@Autowired
	private ActivityInformationService activityInformationService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private GoodsActivityService goodsActivityService;
	
	@Autowired
	private ActiveCouponService activeCouponService; 
	
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService; 
	
	@Autowired
	private GoodsService goodsService; 
	
	@Autowired
	private CouponInformationService couponInformationService; 
	
	@Autowired
	private ClassificationService classificationService; 
	
	@Autowired
	private GoodsDetailsService goodsDetailsService; 
	
	@Autowired
	private ShoppingCartService shoppingCartService;
	
	// 保存活动消息图片的文件夹名
	private String ActivityMessageFolderName = "ActivityMessagePicture";
	// 保存活动展示图片的文件夹名
	private String ActivityShowFolderName = "ActivityShowPicture";
	
	/**
	 * 进入活动管理页面
	 * @param request
	 * @param flag 标志 1：活动管理页面   2:活动审核页面
	 * @return 页面路径
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list(HttpServletRequest request, int flag){
		if(!checkSession(request)){
			return "redirect:/login";
		}
		switch (flag) {
		case 1:
			return "";
			
		case 2:
			return "";
			

		default:
			return "";
			
		}
		
	}
	
	
	/**
	 * 活动信息dataTables
	 * @param request
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @param activityType 活动类型
	 * @param state 状态
	 * @param flag 标志 1：活动管理页面   2:活动审核页面   3:活动低价审核页面
	 * @return
	 */
	@RequestMapping(value="dataTables",method=RequestMethod.POST)
	@ResponseBody
	public DataTables dataTables(HttpServletRequest request,String identifier, String name, String operatorIdentifier , int activityType, int state,String flag ,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);
		return activityInformationService.getDataTables(dataTables, identifier, name, operatorIdentifier,activityType,state,flag,operatorTime);
		
	}
	
	/**
	 * 送审、通过、拒绝
	 * @param request
	 * @param ids id数组
	 * @param flag 1：送审  2：审核通过  3：审核未通过   4：低价审核通过
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "updateState", method = RequestMethod.POST)
	public JSONObject updateState(HttpServletRequest request,String[] ids, int flag) throws Exception {
	
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(ids!=null && ids.length>0){
			for (int i = 0; i < ids.length; i++) {
				
				list.add(Integer.valueOf(ids[i]));
			}
//			for(String id:ids){
//				System.out.println("id:"+id);
//				list.add(Integer.valueOf(id));
//				
//			}
		}
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("ids", list);
		
		boolean result = false;
		JSONObject rmsg=new JSONObject();
		switch (flag) {
		case 1:
			map.put("state", Constants.STATE_HD_CHECKING);
			result = activityInformationService.updateActivityInformationStateByIds(map);
			rmsg.put("msg","操作成功，已送审！");
			break;
		case 2:
			//判断有没有商品低于库存价格  如果有 修改状态为7，进行低价审核；如果没有，直接通过
			int id;
			ActivityInformation t = new ActivityInformation(); 
			boolean result2 = false;
					result = true;
			for (int i = 0; i < list.size(); i++) {
				id = list.get(i);
				t.setId(id);
				if(isLowPrice(id)) {
					t.setState(Constants.STATE_HD_LOWPRICE_CHECKING);
					result2 = activityInformationService.updateByPrimaryKeySelective(t)>0;
				}else {
					t.setState(Constants.STATE_HD_PASS);
					result2 = activityInformationService.updateByPrimaryKeySelective(t)>0;
				}
				result = result && result2;
			}
			
			rmsg.put("msg","操作成功，已通过！");
			break;
		case 3:
			map.put("state", Constants.STATE_HD_REFUSED);
			result = activityInformationService.updateActivityInformationStateByIds(map);
			rmsg.put("msg","操作成功，已拒绝！");
			break;
		case 4:
			map.put("state", Constants.STATE_HD_PASS);
			result = activityInformationService.updateActivityInformationStateByIds(map);
			rmsg.put("msg","操作成功，已通过！");
			break;

		default:
			break;
		}
		
		
		
		
		if (result) {
			
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			
			//送审时保存提交人和提交时间
			if(flag==1){
				map=new HashMap<String, Object>();
				map.put("ids", list);
				map.put("submitterIdentifier", operatorIdentifier);
				map.put("submitTime",new Date());
				activityInformationService.updateSubmitInfoByIds(map);
			}
			
			
			//保存操作的对象编号
			List<ActivityInformation> activityInformations = activityInformationService.getActivityInformationByIds(list);
			//插入日志
			Log log =new Log();
			String operateObject="";
			switch (flag) {
			case 1:
				//操作类型
				log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
				break;
			case 2:
				//操作类型
				log.setOperateType(Constants.TYPE_LOG_CHECK);
				break;
			case 3:
				//操作类型
				log.setOperateType(Constants.TYPE_LOG_CHECK);
				break;
			case 4:
				//操作类型
				log.setOperateType(Constants.TYPE_LOG_CHECK);
				break;
			default:
				break;
			}
			
			//操作对象
			operateObject="";
			for (int i = 0; i < activityInformations.size(); i++) {
				operateObject += activityInformations.get(i).getIdentifier();
				if(i < activityInformations.size()-1){
					operateObject += ",";
				}
			}
			operateObject += "("+Constants.OPERATE_UPDATE+")";
			log.setOperateObject(operateObject);
	
			//操作人
			log.setOperatorIdentifier(operatorIdentifier);
			//操作时间
			Date operateTime=new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);

			rmsg.put("success", true);
			
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", "操作失败，请确认后重新操作！");
		return rmsg;
	}
	
	
	
	
	/**
	 * 添加活动信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addActivityInformation", method = RequestMethod.POST)
	public JSONObject addActivityInformation(HttpServletRequest request,
			@RequestParam(value = "messagePicUrl", required = false) MultipartFile messageFile,
			@RequestParam(value = "showPicUrl", required = false) MultipartFile showFile) throws Exception {
		
		
		ActivityInformation activityInformation=new ActivityInformation();
		activityInformation.setName(request.getParameter("name"));
		activityInformation.setActivityType(Integer.parseInt(request.getParameter("activityType")));
		
		if(activityInformation.getActivityType()==Constants.TYPE_HD_MJ){
			activityInformation.setPrice(Double.parseDouble(request.getParameter("box1")));
			activityInformation.setDiscount(Double.parseDouble(request.getParameter("box2")));
		}else if(activityInformation.getActivityType()==Constants.TYPE_HD_YS){
			
		}else{
			activityInformation.setDiscount(Double.parseDouble(request.getParameter("box1")));
			activityInformation.setMaxNum(Integer.parseInt(request.getParameter("box2")));
		}
		

		
		activityInformation.setIntroduction(request.getParameter("introduction"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date beginValidityTime = (Date)sdf.parse(request.getParameter("beginValidityTime").toString());  
		Date endValidityTime = (Date)sdf.parse(request.getParameter("endValidityTime").toString());		
		activityInformation.setBeginValidityTime(beginValidityTime);
		activityInformation.setEndValidityTime(endValidityTime);
		activityInformation.setGoods(request.getParameter("goods"));
		activityInformation.setGoodsState(request.getParameter("goodsState"));
		activityInformation.setCoupon(request.getParameter("coupon"));
		
		
		//编号
		String identifier=SundryCodeUtil.getPosCode(Constants.CODE_HD);
		activityInformation.setIdentifier(identifier);
		//状态
		activityInformation.setState(Constants.STATE_HD_NOTSUBMIT);
		
		//创建人
		String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		activityInformation.setOperatorIdentifier(operatorIdentifier);
		
		//创建时间
		Date operateTime=new Date();
		activityInformation.setOperatorTime(operateTime);
		
		
		if (activityInformation.getActivityType()!=Constants.TYPE_HD_YS) {
			//预算
			double budget,afterBudget;
			BigDecimal b;
			int id;
			GoodsSpecificationDetails goodsSpecificationDetails;
			double price;
			int stock;
			//判断有没有优惠券列表
			if (!("".equals(activityInformation.getCoupon()))) {//有优惠券
				budget = 0;
				String coupon = activityInformation.getCoupon();

				String[] couponId = coupon.split(",");

				CouponInformation couponInformation;
				for (int i = 0; i < couponId.length; i++) {

					//获取优惠券信息表id
					int couponInformationId = Integer.parseInt(couponId[i]);
					//获取优惠券信息
					couponInformation = couponInformationService.selectByPrimaryKey(couponInformationId);
					//获取优惠券金额
					double couponPrice = couponInformation.getPrice();
					//获取优惠券剩余数量
					int count = couponInformation.getCount();

					//计算该优惠券的预算(单个)
					double unitBudget = couponPrice * count;

					budget += unitBudget;

				}
				switch (activityInformation.getActivityType()) {

				case Constants.TYPE_HD_TG:
					//根据商品规格id获取商品原价
					id = Integer.parseInt(request.getParameter("goods"));
					goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
					price = goodsSpecificationDetails.getPrice();
					//根据商品规格id获取商品原价
					//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
					stock = goodsSpecificationDetails.getGxcGoodsStock();

					budget += (price - activityInformation.getDiscount()) * stock;
					//预算四舍五入
					b = new BigDecimal(budget);  
					afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					activityInformation.setBudget(afterBudget);
					break;
				case Constants.TYPE_HD_MS:
					//根据商品规格id获取商品原价
					id = Integer.parseInt(request.getParameter("goods"));
					goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
					price = goodsSpecificationDetails.getPrice();
					//根据商品规格id获取商品原价
					//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
					stock = goodsSpecificationDetails.getGxcGoodsStock();

					budget += (price - activityInformation.getDiscount()) * stock;
					//预算四舍五入
					b = new BigDecimal(budget);  
					afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					activityInformation.setBudget(afterBudget);
					
					break;

				default:
					//预算四舍五入
					b = new BigDecimal(budget);  
					afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					activityInformation.setBudget(afterBudget);
					break;
				}

			} else {//没有优惠券
				switch (activityInformation.getActivityType()) {

				case Constants.TYPE_HD_TG:
					//根据商品规格id获取商品原价
					id = Integer.parseInt(request.getParameter("goods"));
					goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
					price = goodsSpecificationDetails.getPrice();
					//根据商品规格id获取商品原价
					//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
					stock = goodsSpecificationDetails.getGxcGoodsStock();

					budget = (price - activityInformation.getDiscount()) * stock;
					//预算四舍五入
					b = new BigDecimal(budget);  
					afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					activityInformation.setBudget(afterBudget);
					break;
				case Constants.TYPE_HD_MS:
					//根据商品规格id获取商品原价
					id = Integer.parseInt(request.getParameter("goods"));
					goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
					price = goodsSpecificationDetails.getPrice();
					//根据商品规格id获取商品原价
					//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
					stock = goodsSpecificationDetails.getGxcGoodsStock();

					budget = (price - activityInformation.getDiscount()) * stock;
					//预算四舍五入
					b = new BigDecimal(budget);  
					afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					activityInformation.setBudget(afterBudget);
					break;
				default:
					break;
				}
			} 
		}
		
		
				//图片上传
			//消息图
				// 存储新图片，并保存路径
				if (messageFile.getOriginalFilename() != null && !"".equals(messageFile.getOriginalFilename())) {
					activityInformation.setMessagePicUrl(CommonMethod.savePic(request, ActivityMessageFolderName, messageFile));
				}
		
			//展示图
				// 存储新图片，并保存路径
				if (showFile.getOriginalFilename() != null && !"".equals(showFile.getOriginalFilename())) {
					activityInformation.setShowPicUrl(CommonMethod.savePic(request, ActivityShowFolderName, showFile));
				}
		
		
		JSONObject rmsg=new JSONObject();
		if (activityInformationService.insertSelective(activityInformation)>0) {
			//根据活动编号获取活动id
			int activityInformationId = activityInformationService.getIdByIdentifier(identifier);
			
		//对其他两张表插入信息
			
			//商品活动表获取商品id
			GoodsActivity goodsActivity;
			String goods=activityInformation.getGoods();
			if (!("".equals(goods))) {
				String goodsType = activityInformation.getGoodsState();
				String[] goodsId = goods.split(",");
				String[] goodsState = goodsType.split(",");
				goodsActivity = new GoodsActivity();
				goodsActivity.setActivityInformationId(activityInformationId);
				for (int i = 0; i < goodsId.length; i++) {
					goodsActivity.setGoodsId(Integer.parseInt(goodsId[i]));
					goodsActivity.setState(Integer.parseInt(goodsState[i]));
					goodsActivityService.insert(goodsActivity);
				}
			}
			//活动优惠券获取优惠券id
			ActiveCoupon activeCoupon;
			String coupon=activityInformation.getCoupon();
			if (!("".equals(coupon))) {
				String[] couponId = coupon.split(",");
				activeCoupon = new ActiveCoupon();
				activeCoupon.setActivityInformationId(activityInformationId);
				for (int i = 0; i < couponId.length; i++) {
					activeCoupon.setCouponId(Integer.parseInt(couponId[i]));
					activeCouponService.insert(activeCoupon);
				}
			}
			//插入日志
			Log log =new Log();
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			String operateObject=identifier+"("+Constants.OPERATE_INSERT+")";
			log.setOperateObject(operateObject);
			log.setOperatorIdentifier(operatorIdentifier);
			log.setOperateTime(operateTime);
			logService.insert(log);
			
			rmsg.put("success", true);
			rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);
		return rmsg;
		
	}
	
	
	/**
	 * 编辑活动信息
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateActivityInformation", method = RequestMethod.POST)
	public JSONObject updateActivityInformation(HttpServletRequest request,
			@RequestParam(value = "messagePicUrl", required = false) MultipartFile messageFile,
			@RequestParam(value = "showPicUrl", required = false) MultipartFile showFile) throws Exception {

		ActivityInformation activityInformation=new ActivityInformation();
		
		
		//获取需要更新的数据的id
		int activityInformationId = Integer.parseInt(request.getParameter("id"));
		activityInformation.setId(activityInformationId);
		
		
		
		activityInformation.setName(request.getParameter("name"));
		activityInformation.setActivityType(Integer.parseInt(request.getParameter("activityType")));
		
		if(activityInformation.getActivityType()==Constants.TYPE_HD_MJ){
			activityInformation.setPrice(Double.parseDouble(request.getParameter("box1")));
			activityInformation.setDiscount(Double.parseDouble(request.getParameter("box2")));
		}else if(activityInformation.getActivityType()==Constants.TYPE_HD_YS){
			
		}else{
			activityInformation.setDiscount(Double.parseDouble(request.getParameter("box1")));
			activityInformation.setMaxNum(Integer.parseInt(request.getParameter("box2")));
		}
		

		
		activityInformation.setIntroduction(request.getParameter("introduction"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		Date beginValidityTime = (Date)sdf.parse(request.getParameter("beginValidityTime").toString());  
		Date endValidityTime = (Date)sdf.parse(request.getParameter("endValidityTime").toString());		
		activityInformation.setBeginValidityTime(beginValidityTime);
		activityInformation.setEndValidityTime(endValidityTime);
		activityInformation.setGoods(request.getParameter("goods"));
		activityInformation.setGoodsState(request.getParameter("goodsState"));
		activityInformation.setCoupon(request.getParameter("coupon"));
		
		
		
	if(activityInformation.getActivityType()!=Constants.TYPE_HD_YS) {
		//预算
		double budget,afterBudget;
		BigDecimal b;
		int id;
		GoodsSpecificationDetails goodsSpecificationDetails;
		double price;
		int stock;
		//判断有没有优惠券列表
		if (!("".equals(activityInformation.getCoupon()))) {//有优惠券
			budget = 0;
			String coupon = activityInformation.getCoupon();

			String[] couponId = coupon.split(",");

			CouponInformation couponInformation;
			for (int i = 0; i < couponId.length; i++) {

				//获取优惠券信息表id
				int couponInformationId = Integer.parseInt(couponId[i]);
				//获取优惠券信息
				couponInformation = couponInformationService.selectByPrimaryKey(couponInformationId);
				//获取优惠券金额
				double couponPrice = couponInformation.getPrice();
				//获取优惠券剩余数量
				int count = couponInformation.getCount();

				//计算该优惠券的预算(单个)
				double unitBudget = couponPrice * count;

				budget += unitBudget;

			}
			switch (activityInformation.getActivityType()) {

			case Constants.TYPE_HD_TG:
				//根据商品规格id获取商品原价
				id = Integer.parseInt(request.getParameter("goods"));
				goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
				price = goodsSpecificationDetails.getPrice();
				//根据商品规格id获取商品原价
				//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
				stock = goodsSpecificationDetails.getGxcGoodsStock();

				budget += (price - activityInformation.getDiscount()) * stock;
				//预算四舍五入
				b = new BigDecimal(budget);  
				afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				activityInformation.setBudget(afterBudget);
				break;
			case Constants.TYPE_HD_MS:
				//根据商品规格id获取商品原价
				id = Integer.parseInt(request.getParameter("goods"));
				goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
				price = goodsSpecificationDetails.getPrice();
				//根据商品规格id获取商品原价
				//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
				stock = goodsSpecificationDetails.getGxcGoodsStock();

				budget += (price - activityInformation.getDiscount()) * stock;
				//预算四舍五入
				b = new BigDecimal(budget);  
				afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				activityInformation.setBudget(afterBudget);
				
				break;

			default:
				//预算四舍五入
				b = new BigDecimal(budget);  
				afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				activityInformation.setBudget(afterBudget);
				break;
			}

		} else {//没有优惠券
			switch (activityInformation.getActivityType()) {

			case Constants.TYPE_HD_TG:
				//根据商品规格id获取商品原价
				id = Integer.parseInt(request.getParameter("goods"));
				goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
				price = goodsSpecificationDetails.getPrice();
				//根据商品规格id获取商品原价
				//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
				stock = goodsSpecificationDetails.getGxcGoodsStock();

				budget = (price - activityInformation.getDiscount()) * stock;
				//预算四舍五入
				b = new BigDecimal(budget);  
				afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				activityInformation.setBudget(afterBudget);
				break;
			case Constants.TYPE_HD_MS:
				//根据商品规格id获取商品原价
				id = Integer.parseInt(request.getParameter("goods"));
				goodsSpecificationDetails = goodsSpecificationDetailsService.selectByPrimaryKey(id);
				price = goodsSpecificationDetails.getPrice();
				//根据商品规格id获取商品原价
				//stock = goodsService.selectByPrimaryKey(goodsSpecificationDetails.getSaleId()).getStock();
				stock = goodsSpecificationDetails.getGxcGoodsStock();

				budget = (price - activityInformation.getDiscount()) * stock;
				//预算四舍五入
				b = new BigDecimal(budget);  
				afterBudget = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				activityInformation.setBudget(afterBudget);
				break;
			default:
				activityInformation.setBudget(0.0);
				break;
			}
		} 
	}
		

		
		//获取更新前的活动信息
		ActivityInformation ai=activityInformationService.selectByPrimaryKey(activityInformationId);
		
		//获取原有的图片地址
		String oldMessagePicUrl = ai.getMessagePicUrl();
		String oldShowPicUrl = ai.getShowPicUrl();
		
		
		//图片上传  不为空说明有新的图片需要重新上传
			//消息图
				// 存储新图片，并保存路径
				if (messageFile.getOriginalFilename() != null && !"".equals(messageFile.getOriginalFilename())) {
					activityInformation.setMessagePicUrl(CommonMethod.savePic(request, ActivityMessageFolderName, messageFile));
				}
		
			//展示图
				// 存储新图片，并保存路径
				if (showFile.getOriginalFilename() != null && !"".equals(showFile.getOriginalFilename())) {
					activityInformation.setShowPicUrl(CommonMethod.savePic(request, ActivityShowFolderName, showFile));
				}
		
		
		JSONObject rmsg=new JSONObject();
		if (activityInformationService.updateByPrimaryKeySelective(activityInformation)>0) {
			
			// 不为空说明有新的图片需要重新上传,如果有旧图片则需要删除旧的图片
			if (messageFile.getOriginalFilename() != null && !"".equals(messageFile.getOriginalFilename())) {
				//如果有旧图片地址，调用方法删除旧图片
				if(oldMessagePicUrl!=null){
					List<String> oldPicUrlList=new ArrayList<>();
					oldPicUrlList.add(oldMessagePicUrl);
					CommonMethod.deleteOldPic(request, ActivityMessageFolderName, oldPicUrlList);
				}
			}
			
			// 不为空说明有新的图片需要重新上传,如果有旧图片则需要删除旧的图片
			if (showFile.getOriginalFilename() != null && !"".equals(showFile.getOriginalFilename())) {
				//如果有旧图片地址，调用方法删除旧图片
				if(oldShowPicUrl!=null){
					List<String> oldPicUrlList=new ArrayList<>();
					oldPicUrlList.add(oldShowPicUrl);
					CommonMethod.deleteOldPic(request, ActivityShowFolderName, oldPicUrlList);
				}
			}			
		//对其他两张表进行操作
			
			//删除原有的记录
			goodsActivityService.deleteByActivityInformationId(activityInformationId);
			activeCouponService.deleteByActivityInformationId(activityInformationId);
			
			//插入新数据
			//商品活动表获取商品id
			GoodsActivity goodsActivity;
			String goods=activityInformation.getGoods();
			if (!("".equals(goods))) {
				String goodsType = activityInformation.getGoodsState();
				String[] goodsId = goods.split(",");
				String[] goodsState = goodsType.split(",");
				goodsActivity = new GoodsActivity();
				goodsActivity.setActivityInformationId(activityInformationId);
				for (int i = 0; i < goodsId.length; i++) {
					goodsActivity.setGoodsId(Integer.parseInt(goodsId[i]));
					goodsActivity.setState(Integer.parseInt(goodsState[i]));
					goodsActivityService.insert(goodsActivity);
				}
			}
			//活动优惠券获取优惠券id
			ActiveCoupon activeCoupon;
			String coupon=activityInformation.getCoupon();
			if (!("".equals(coupon))) {
				String[] couponId = coupon.split(",");
				activeCoupon = new ActiveCoupon();
				activeCoupon.setActivityInformationId(activityInformationId);
				for (int i = 0; i < couponId.length; i++) {
					activeCoupon.setCouponId(Integer.parseInt(couponId[i]));
					activeCouponService.insert(activeCoupon);
				}
			}
			//插入日志
			//获取操作对象编号
			String identifier=ai.getIdentifier();
			//操作人
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			//操作时间
			Date operateTime=new Date();
			
			
			Log log =new Log();
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			String operateObject=identifier+"("+Constants.OPERATE_UPDATE+")";
			log.setOperateObject(operateObject);
			log.setOperatorIdentifier(operatorIdentifier);
			log.setOperateTime(operateTime);
			logService.insert(log);
			
			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
		
	}
	
	
	/**
	 * 删除（根据id列表删除活动信息）
	 * @param request
	 * @param ids  id数组
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	@ResponseBody
	@RequestMapping(value = "deleteActivityInformationByIds", method = RequestMethod.POST)
	public JSONObject deleteActivityInformationByIds(HttpServletRequest request) throws Exception {
		String[] ids = request.getParameterValues("id");
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(ids!=null && ids.length>0){
			for(String id:ids){
				list.add(Integer.valueOf(id));
			}
		}
		JSONObject rmsg=new JSONObject();
		List<ActivityInformation> activityInformations0 = activityInformationService.getActivityInformationIsContactToADByIds(list);
		if(activityInformations0.size()>0){
			String resultActivityInformationName = "";
			for(int i=0;i<activityInformations0.size();i++){
				if(i==0){
					resultActivityInformationName+="(";
				}
				if(i<activityInformations0.size()-1){
					resultActivityInformationName+=activityInformations0.get(i).getName()+"、";
				}
				else{
					resultActivityInformationName+=activityInformations0.get(i).getName();
				}
				if(i==activityInformations0.size()-1){
					resultActivityInformationName+=")";
				}
				
			}
			rmsg.put("success", false);
			rmsg.put("msg",resultActivityInformationName + "以上显示的活动名称下关联的有广告，暂不能删除!");
			return rmsg;
		}
		
		
		//保存将要删除的对象编号
		List<ActivityInformation> activityInformations = activityInformationService.getActivityInformationByIds(list);
		
		List<String> messageOldPicUrlList=new ArrayList<>();
		List<String> showOldPicUrlList=new ArrayList<>();
		for (int i = 0; i < activityInformations.size(); i++) {
			
			if(activityInformations.get(i).getMessagePicUrl()!=null&&!"".equals(activityInformations.get(i).getMessagePicUrl())){
				messageOldPicUrlList.add(activityInformations.get(i).getMessagePicUrl());
			}
			
			if(activityInformations.get(i).getShowPicUrl()!=null&&!"".equals(activityInformations.get(i).getShowPicUrl())){
				showOldPicUrlList.add(activityInformations.get(i).getShowPicUrl());
			}
			
		}
		
		if (activityInformationService.deleteActivityInformationByIds(list)) {
			
			
			//删除其他两张表中的关联数据
			goodsActivityService.deleteByActivityInformationIds(list);
			activeCouponService.deleteByActivityInformationIds(list);
			
			//删除服务器图片文件夹中的相应图片
			if(messageOldPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, ActivityMessageFolderName, messageOldPicUrlList);
			}
			if(showOldPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, ActivityShowFolderName, showOldPicUrlList);
			}
			
			//处理购物车表  删除的活动有预售活动时修改购物车数据状态为1（失效）
			shoppingCartService.updateStateByActivityIds(list);
			
			//插入日志
			Log log =new Log();
			//操作类型
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			//操作对象
			String operateObject="";
			for (int i = 0; i < activityInformations.size(); i++) {
				operateObject += activityInformations.get(i).getIdentifier();
				if(i < activityInformations.size()-1){
					operateObject += ",";
				}
			}
			operateObject += "("+Constants.OPERATE_DELETE+")";
			
			log.setOperateObject(operateObject);
			//操作人
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			//操作时间
			Date operateTime=new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);
			
			rmsg.put("success", true);
			rmsg.put("msg", Constants.DELE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.DELE_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 根据活动信息id获取活动优惠券信息、活动商品信息、预警信息
	 * @param request
	 * @param activityInformation  只包含活动信息的id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getMsgByActivityInformationId", method = RequestMethod.POST)
	public JSONObject  getMsgByActivityInformationId(HttpServletRequest request, ActivityInformation activityInformation) throws Exception {
		int activityInformationId = activityInformation.getId();
		ActivityInformation ai = activityInformationService.selectByPrimaryKey(activityInformationId);//活动信息表对象
		 int activityType = ai.getActivityType();//获取活动类型
		
		//获取活动优惠券信息
		List<CouponInformation> activeCouponList = activeCouponService.getMsgByActivityInformationId(activityInformationId);
		
		//List<String> activeCouponNameList = new ArrayList<>();
		JSONArray activityCouponJsonArray = new JSONArray();
		JSONArray couponObj =new JSONArray();
		
		CouponInformation couponInformation;
		String couponName;
		int couponId;
		for (int i = 0; i < activeCouponList.size(); i++) {
			couponInformation = activeCouponList.get(i);	
			JSONObject couponJsonObject = new JSONObject();
			couponName = couponInformation.getName()+"(满"+couponInformation.getUseLimit()+"减"+couponInformation.getPrice()+")";
			couponId = couponInformation.getId();
			couponJsonObject.put("name", couponName);
			couponJsonObject.put("id", couponId);
			//activeCouponNameList.add(couponName);
			activityCouponJsonArray.add(couponJsonObject);
			
			JSONObject idObj = new JSONObject();
			idObj.put("id", couponId);
			couponObj.add(idObj);
			
		}
		
		
		
		GoodsActivity ga = null;
		GoodsDetails gd = null;
		GoodsSpecificationDetails gsd = null;
		Classification c = null;
		
		
		
		//获取活动商品信息
		List<GoodsActivity> goodsActivitieList = goodsActivityService.getMsgByActivityInformationId(activityInformationId);//从goods_activity表中查询该行活动下的所有商品信息
		//List<String> goodsNameList = new ArrayList<>();
		JSONArray activityGoodsJsonArray = new JSONArray();
		JSONArray checkGoods0 = new JSONArray();
		JSONArray checkGoods1 = new JSONArray();
		JSONArray checkGoods2 = new JSONArray();
		JSONArray checkGoods3 = new JSONArray();
		
		
		String activityGoodsName="";
		int activityGoodsState=-1,activityGoodsId=-1;
		
		for (int i = 0; i < goodsActivitieList.size(); i++) {
			int state = goodsActivitieList.get(i).getState();//0：规格   1：商品  2：分类 
			int goodsId = goodsActivitieList.get(i).getGoodsId();
		/*	System.out.println("--id--"+goodsActivitieList.get(i).getId());
			System.out.println("--ActivityInformationId--"+goodsActivitieList.get(i).getActivityInformationId());
			System.out.println("--GoodsId--"+goodsActivitieList.get(i).getGoodsId());
			System.out.println("--State--"+goodsActivitieList.get(i).getState());*/
			
			JSONObject activityGoodsJsonObject = new JSONObject();

			JSONObject obj = new JSONObject();
			//获取商品列表信息
			System.out.println("state:"+state);
			switch (state) {
			
			case 0:
				ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId); //select gd.name,gd.id,gd.classification_id,gsd.specifications,gsd.price,gsd.sale_id,gsd.id
				gd = ga.getGoodsDetails();
				gsd = ga.getGoodsSpecificationDetails();
				activityGoodsName = gd.getName()+"("+gsd.getSpecifications()+")";
				activityGoodsId = gsd.getId();
				
				activityGoodsState = 0;
				
				activityGoodsJsonObject.put("price", gsd.getPrice());
				
				int classification_id0 = gd.getClassificationId();
				Classification classification0 = classificationService.selectByPrimaryKey(classification_id0);
				obj.put("goodstypeid", activityGoodsId);
				obj.put("goodsid", gd.getId());
				obj.put("classificationTwoid",classification0.getId() );
				obj.put("classificationOneid", classification0.getParentId());
				checkGoods0.add(obj);
				break;
			case 1:
				ga = goodsActivityService.getMsgByActivityInformationId1(activityInformationId,goodsId);//select gd.name ,gd.id,gd.classification_id 
				gd = ga.getGoodsDetails();
				activityGoodsName = gd.getName();
				activityGoodsId = gd.getId();
				activityGoodsState = 1;
				
				int classification_id1 = gd.getClassificationId();
				Classification classification1 = classificationService.selectByPrimaryKey(classification_id1);
				obj.put("goodsid", activityGoodsId);
				obj.put("classificationTwoid",classification1.getId() );
				obj.put("classificationOneid", classification1.getParentId());
				checkGoods1.add(obj);
				break;
			case 2:
				ga = goodsActivityService.getMsgByActivityInformationId2(activityInformationId,goodsId);//select c.name,c.id,c.parent_id
				c = ga.getClassification();
				activityGoodsName = c.getName();
				activityGoodsId = c.getId();
				
				/*System.out.println("c.name:"+activityGoodsName);
				System.out.println("c.id:"+activityGoodsId);*/
				if(c.getParentId()==0){
					activityGoodsState = 3;
					
					
					obj.put("classificationOneid",activityGoodsId);
					checkGoods3.add(obj);
				}else{
					activityGoodsState = 2;
					
					obj.put("classificationTwoid", activityGoodsId);
					obj.put("classificationOneid", c.getParentId());
					checkGoods2.add(obj);
				}
				
				break;
			default:
				break;
			}
			
			/*System.out.println("name:"+activityGoodsName);
			System.out.println("id:"+activityGoodsId);
			System.out.println("state:"+activityGoodsState);*/
			
			activityGoodsJsonObject.put("name", activityGoodsName);
			activityGoodsJsonObject.put("id", activityGoodsId);
			activityGoodsJsonObject.put("state", activityGoodsState);
			//goodsNameList.add(goodsName);
			activityGoodsJsonArray.add(activityGoodsJsonObject);
		}
		
		
		
		//是否显示预警信息
		boolean warning = false;
		for (int i = 0; i < goodsActivitieList.size(); i++) {
			int state = goodsActivitieList.get(i).getState();//0：规格   1：商品  2：分类 
			int goodsId1 = goodsActivitieList.get(i).getGoodsId();
			//获取预警信息
			int saleId ;  //规格表中的购销存商品id 
			double stockPrice;    //库存价格
			switch (activityType) {
			case Constants.TYPE_HD_ZK:
				//折扣 多商品 
				
				double yPrice,zk,zkPrice;
				int goodsId;
				
				switch (state) {
				case 0://规格
					
					ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
					gsd = ga.getGoodsSpecificationDetails();
					
					//获取库存价格 所需参数：规格表中的购销存商品id  
					//规格表中的购销存商品id 
					//saleId = gsd.getSaleId();
					//获取库存价格
					//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
					stockPrice = gsd.getMiniPrice();
					//获取原价
					yPrice = gsd.getPrice();
					//获取折扣
					zk  = ai.getDiscount();
					//折扣价
					zkPrice = yPrice * zk;
					warning = zkPrice < stockPrice;
					
					break;
				case 1://商品详情
					ga = goodsActivityService.getMsgByActivityInformationId1(activityInformationId,goodsId1);//select gd.name,gd.id 
					gd = ga.getGoodsDetails();
					goodsId = gd.getId();
					//根据商品id获取规格
				  List<GoodsSpecificationDetails> gsds = goodsSpecificationDetailsService.selectByGoodsId(goodsId);
				  for (int j = 0; j < gsds.size(); j++) {
					  gsd = gsds.get(j);
					//获取库存价格 所需参数：规格表中的购销存商品id  
						//规格表中的购销存商品id 
						//saleId = gsd.getSaleId();
						//获取库存价格
						//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
					  	stockPrice = gsd.getMiniPrice();
						//获取原价
						yPrice = gsd.getPrice();
						//获取折扣
						zk  = ai.getDiscount();
						//折扣价
						zkPrice = yPrice * zk;
						warning = zkPrice < stockPrice;
						if(warning){
							break;
						}
				  	}
					break;
				case 2://分类
					ga = goodsActivityService.getMsgByActivityInformationId2(activityInformationId,goodsId1);//select c.name,c.id
					c = ga.getClassification();
					int classificationId = c.getId();
					List<GoodsDetails> gds = goodsDetailsService.getGoodsListByClassificationId(classificationId);
					for (int j = 0; j < gds.size(); j++) {
						gd = gds.get(j);
						goodsId = gd.getId();
						//根据商品id获取规格
					  List<GoodsSpecificationDetails> gsds2 = goodsSpecificationDetailsService.selectByGoodsId(goodsId);
					  for (int k = 0; k < gsds2.size(); k++) {
						  gsd = gsds2.get(k);
						//获取库存价格 所需参数：规格表中的购销存商品id  
							//规格表中的购销存商品id 
							//saleId = gsd.getSaleId();
							//获取库存价格
							//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
						  stockPrice = gsd.getMiniPrice();
							//获取原价
							yPrice = gsd.getPrice();
							//获取折扣
							zk  = ai.getDiscount();
							//折扣价
							zkPrice = yPrice * zk;
							warning = zkPrice < stockPrice;
							if(warning){
								break;
							}
					  	}
					  if(warning){
							break;
						}
					}
					break;

				default:
					break;
				}
				break;
			case Constants.TYPE_HD_TG:
				//团购 单商品 state=0
				
				ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
				gsd = ga.getGoodsSpecificationDetails();
				
				 //获取库存价格 所需参数：规格表中的购销存商品id  
				//规格表中的购销存商品id 
				//saleId = gsd.getSaleId();
				//获取库存价格
				//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
				stockPrice = gsd.getMiniPrice();
				//获取团购价
				double tgPrice = ai.getDiscount();
				warning = tgPrice<stockPrice;
				
				break;
			case Constants.TYPE_HD_MS:
				//秒杀 单商品 state=0
				
				ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
				gsd = ga.getGoodsSpecificationDetails();
				
				//获取库存价格 所需参数：规格表中的购销存商品id  
				//规格表中的购销存商品id 
				//saleId = gsd.getSaleId();
				//获取库存价格
				//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
				stockPrice = gsd.getMiniPrice();
				//获取秒杀价
				double msPrice = ai.getDiscount();
				warning = msPrice<stockPrice;
				
				break;
			case Constants.TYPE_HD_LJ:
				//立减 单商品 state=0 
				
				ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
				gsd = ga.getGoodsSpecificationDetails();
				
				//获取库存价格 所需参数：规格表中的购销存商品id  
				//规格表中的购销存商品id 
				//saleId = gsd.getSaleId();
				//获取库存价格
				//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
				stockPrice = gsd.getMiniPrice();
				//获取立减金额
				double ljPrice = ai.getDiscount();
				//从规格表中获取商品原价
				double price = gsd.getPrice();
				
				warning = (price - ljPrice)<stockPrice;
				
				break;
			case Constants.TYPE_HD_MJ:
				//满减 多商品  无预警
				
				break;
			case Constants.TYPE_HD_YS:
				//预售 多商品  无预警
				
				break;
			default:
				break;
			}
			
			if(warning){
				break;
			}
		}
		
		
		
		
		
		JSONObject object=new JSONObject();
		object.put("activityCouponJsonArray", activityCouponJsonArray);
		object.put("activityGoodsJsonArray", activityGoodsJsonArray);
		object.put("warning", warning);
		object.put("checkGoods0", checkGoods0);
		object.put("checkGoods1", checkGoods1);
		object.put("checkGoods2", checkGoods2);
		object.put("checkGoods3", checkGoods3);
		object.put("couponObj", couponObj);
		
		return object;
	}
	
	
	/**
	 * 获取所有有效商品信息 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getClassificationByParentId", method = RequestMethod.POST)
	public List<Classification> getClassificationByParentId() {
		
		List<Classification> allClassification= classificationService.selectTwoByOneId(0);//获取所有的一级
		
			//根据一级分类获取二级分类
			for (int i = 0; i < allClassification.size(); i++) {
				
				int oneClaId = allClassification.get(i).getId();//获取一级分类的id
				List<Classification> twoClaList = classificationService.selectTwoByOneId(oneClaId);//根据一级分类的id获取二级分类信息
				//判断该一级分类下是否有二级分类 
				if(twoClaList ==null || twoClaList.size()<=0){//没有二级分类
					allClassification.remove(i);//将该一级分类从列表中移除
					i=i-1;
				}else{//有二级分类
					for (int j = 0; j < twoClaList.size(); j++) {
						int twoClaId = twoClaList.get(j).getId();//获取二级分类的id
						List<GoodsDetails> goodsList = goodsDetailsService.getGoodsListByClassificationId(twoClaId);//根据分类获取该分类下的商品
						
						if(goodsList == null || goodsList.size()<=0){//该二级分类下无商品
							twoClaList.remove(j);//将该二级分类从列表中移除
							j=j-1;
						}else{//该二级分类下有商品
//							tempGoodsList.clear();
//							tempGoodsList.addAll(goodsList);
							for (int k = 0; k < goodsList.size(); k++) {
								int goodsId = goodsList.get(k).getId();//获取商品的id
								List<GoodsSpecificationDetails> goodsSpecificationDetailList = goodsSpecificationDetailsService.selectOnByGoodsId(goodsId);//根据商品id获取该商品下的正在上线的规格
								if(goodsSpecificationDetailList == null || goodsSpecificationDetailList.size()<=0){//该商品下无正在上线的规格
									goodsList.remove(k);//将该商品从列表中移除
									k=k-1;
								}else{//该商品下有正在上线的规格
									goodsList.get(k).setGoodsSpecificationDetails(goodsSpecificationDetailList);
								}
							}
							//判断remove过后该二级分类下还有没有商品
							if(goodsList.size()>0){//有 该二级分类设置商品列表
								twoClaList.get(j).setGoodsDetails(goodsList);
							}else{//没有 将该二级分类移除
								twoClaList.remove(j);
								j=j-1;
							}
							
						}
					}
					//判断remove过后该一级分类下还有没有二级分类
					if(twoClaList.size()>0){//有 该一级分类设置二级分类列表
						allClassification.get(i).setTwoClassifications(twoClaList);
					}else{//没有 将该一级分类移除
						allClassification.remove(i);
						i=i-1;
					}
					
				}
			}
		
		
		return allClassification;
	}
	
	/**
	 * 获取所有有效非预售商品信息 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getClassificationByParentIdForNotPresell", method = RequestMethod.POST)
	public List<Classification> getClassificationByParentIdForNotPresell() {
		
		List<Classification> allClassification= classificationService.selectTwoByOneId(0);//获取所有的一级
		
			//根据一级分类获取二级分类
			for (int i = 0; i < allClassification.size(); i++) {
				
				int oneClaId = allClassification.get(i).getId();//获取一级分类的id
				List<Classification> twoClaList = classificationService.selectTwoByOneId(oneClaId);//根据一级分类的id获取二级分类信息
				//判断该一级分类下是否有二级分类 
				if(twoClaList ==null || twoClaList.size()<=0){//没有二级分类
					allClassification.remove(i);//将该一级分类从列表中移除
					i=i-1;
				}else{//有二级分类
					for (int j = 0; j < twoClaList.size(); j++) {
						int twoClaId = twoClaList.get(j).getId();//获取二级分类的id
						//List<GoodsDetails> goodsList = goodsDetailsService.getGoodsListByClassificationId(twoClaId);//根据分类获取该分类下的商品
						List<GoodsDetails> goodsList = goodsDetailsService.selectNotPresellGoodsByClassificationId(twoClaId);//根据分类获取该分类下的商品
						if(goodsList == null || goodsList.size()<=0){//该二级分类下无商品
							twoClaList.remove(j);//将该二级分类从列表中移除
							j=j-1;
						}else{//该二级分类下有商品
//							tempGoodsList.clear();
//							tempGoodsList.addAll(goodsList);
							for (int k = 0; k < goodsList.size(); k++) {
								int goodsId = goodsList.get(k).getId();//获取商品的id
								List<GoodsSpecificationDetails> goodsSpecificationDetailList = goodsSpecificationDetailsService.selectOnByGoodsId(goodsId);//根据商品id获取该商品下的正在上线的规格
								if(goodsSpecificationDetailList == null || goodsSpecificationDetailList.size()<=0){//该商品下无正在上线的规格
									goodsList.remove(k);//将该商品从列表中移除
									k=k-1;
								}else{//该商品下有正在上线的规格
									goodsList.get(k).setGoodsSpecificationDetails(goodsSpecificationDetailList);
								}
							}
							//判断remove过后该二级分类下还有没有商品
							if(goodsList.size()>0){//有 该二级分类设置商品列表
								twoClaList.get(j).setGoodsDetails(goodsList);
							}else{//没有 将该二级分类移除
								twoClaList.remove(j);
								j=j-1;
							}
							
						}
					}
					//判断remove过后该一级分类下还有没有二级分类
					if(twoClaList.size()>0){//有 该一级分类设置二级分类列表
						allClassification.get(i).setTwoClassifications(twoClaList);
					}else{//没有 将该一级分类移除
						allClassification.remove(i);
						i=i-1;
					}
					
				}
			}
		
		
		return allClassification;
	}
	
	/**
	 * 获取所有有效非预售商品信息 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getClassificationByParentIdForPresell", method = RequestMethod.POST)
	public List<Classification> getClassificationByParentIdForPresell() {
		
		List<Classification> allClassification= classificationService.selectTwoByOneId(0);//获取所有的一级
		
			//根据一级分类获取二级分类
			for (int i = 0; i < allClassification.size(); i++) {
				
				int oneClaId = allClassification.get(i).getId();//获取一级分类的id
				List<Classification> twoClaList = classificationService.selectTwoByOneId(oneClaId);//根据一级分类的id获取二级分类信息
				//判断该一级分类下是否有二级分类 
				if(twoClaList ==null || twoClaList.size()<=0){//没有二级分类
					allClassification.remove(i);//将该一级分类从列表中移除
					i=i-1;
				}else{//有二级分类
					for (int j = 0; j < twoClaList.size(); j++) {
						int twoClaId = twoClaList.get(j).getId();//获取二级分类的id
						//List<GoodsDetails> goodsList = goodsDetailsService.getGoodsListByClassificationId(twoClaId);//根据分类获取该分类下的商品
						List<GoodsDetails> goodsList = goodsDetailsService.selectPresellGoodsByClassificationId(twoClaId);//根据分类获取该分类下的商品
						if(goodsList == null || goodsList.size()<=0){//该二级分类下无商品
							twoClaList.remove(j);//将该二级分类从列表中移除
							j=j-1;
						}else{//该二级分类下有商品
//							tempGoodsList.clear();
//							tempGoodsList.addAll(goodsList);
							for (int k = 0; k < goodsList.size(); k++) {
								int goodsId = goodsList.get(k).getId();//获取商品的id
								List<GoodsSpecificationDetails> goodsSpecificationDetailList = goodsSpecificationDetailsService.selectOnByGoodsId(goodsId);//根据商品id获取该商品下的正在上线的规格
								if(goodsSpecificationDetailList == null || goodsSpecificationDetailList.size()<=0){//该商品下无正在上线的规格
									goodsList.remove(k);//将该商品从列表中移除
									k=k-1;
								}else{//该商品下有正在上线的规格
									goodsList.get(k).setGoodsSpecificationDetails(goodsSpecificationDetailList);
								}
							}
							//判断remove过后该二级分类下还有没有商品
							if(goodsList.size()>0){//有 该二级分类设置商品列表
								twoClaList.get(j).setGoodsDetails(goodsList);
							}else{//没有 将该二级分类移除
								twoClaList.remove(j);
								j=j-1;
							}
							
						}
					}
					//判断remove过后该一级分类下还有没有二级分类
					if(twoClaList.size()>0){//有 该一级分类设置二级分类列表
						allClassification.get(i).setTwoClassifications(twoClaList);
					}else{//没有 将该一级分类移除
						allClassification.remove(i);
						i=i-1;
					}
					
				}
			}
		
		
		return allClassification;
	}
	
//	/**
//	 * 根分类id获取商品详情信息
//	 * @param classificationId
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "getGoodsDetailsByClassificationId", method = RequestMethod.POST)
//	public List<GoodsDetails> getGoodsDetailsByClassificationId(int classificationId ) {
//		
//		return goodsDetailsService.getGoodsListByClassificationId(classificationId);
//	}
//	
//	/**
//	 * 根商品id获取商品规格详情信息
//	 * @param goodsId
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "selectGoodsSpecificationDetailsByGoodsId", method = RequestMethod.POST)
//	public List<GoodsSpecificationDetails> selectGoodsSpecificationDetailsByGoodsId(int goodsId ) {
//		
//		return goodsSpecificationDetailsService.selectByGoodsId(goodsId);
//	}

	/**
	 * 导出活动信息
	 * @param request
	 * @param response
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @param activityType 活动类型
	 * @param state 状态
	 * @param flag 标志 1：活动管理页面   2:活动审核页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/exportActivityInformation")
	public boolean exportActivityInformation(HttpServletRequest request,HttpServletResponse response,String identifier, String name, String operatorIdentifier , int activityType, int state,String flag,String operatorTime ) throws Exception{
		/*System.out.println("identifier"+identifier);
		System.out.println("name"+name);
		System.out.println("operatorIdentifier"+operatorIdentifier);
		System.out.println("activityType"+activityType);
		System.out.println("state"+state);
		System.out.println("flag"+flag);*/
		
		identifier =  URLDecoder.decode(identifier, "UTF-8");
		name = URLDecoder.decode(name, "UTF-8");
		operatorIdentifier = URLDecoder.decode(operatorIdentifier, "UTF-8");
		operatorTime = URLDecoder.decode(operatorTime, "UTF-8");
		String fileName = "";//文档名称以及Excel里头部信息
		List<ActivityInformation> activityInformations=activityInformationService.selectMsgOrderBySearchMsg(identifier, name, operatorIdentifier, activityType, state, flag, operatorTime);
		List<String[]> dataList=new ArrayList<>();
		String[] title; //保存Excel表头
		String[] value; //保存要导出的内容
		if("1".equals(flag)){
			fileName = "活动信息";//活动管理页面文档名称以及Excel里头部信息
		}
		else if("2".equals(flag)){
			fileName = "活动审核信息";//活动审核页面文档名称以及Excel里头部信息
		}
		//搜索的有数据
		if(activityInformations.size()>0){
			//需导出的表头与信息
			
			//保存Excel表头
			title=new String[7];
			title[0]="编号";
			title[1]="活动类型";
			title[2]="活动名称";
			title[3]="活动介绍";
			title[4]="活动预算";
			title[5]="活动时间";
			title[6]="状态";
			//保存要导出的内容
			for(ActivityInformation c:activityInformations){
				value=new String[7];
				value[0]=c.getIdentifier();
				switch (c.getActivityType()) {
				case 0:
					value[1]="折扣";
					break;
				case 1:
					value[1]="团购";
					break;
				case 2:
					value[1]="秒杀";
					break;
				case 3:
					value[1]="立减";
					break;
				case 4:
					value[1]="满减";
					break;
				case 5:
					value[1]="预售";
					break;
				default:
					break;
				}
				
				value[2]=c.getName();		
				value[3]=c.getIntroduction();
				if(c.getBudget()==null){
					value[4]="无";
				}else{
					value[4]=c.getBudget().toString();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				value[5]=sdf.format(c.getBeginValidityTime())+" ~ "+sdf.format(c.getEndValidityTime()) ;
				switch (c.getState()) {
				case 0:
					value[6]="未送审";
					break;
				case 1:
					value[6]="审核中";
					break;
				case 2:
					value[6]="审核通过";
					break;
				case 3:
					value[6]="审核未通过";
					break;
				case 4:
					value[6]="上线中";
					break;
				case 5:
					value[6]="已下线";
					break;
				case 6:
					value[6]="已失效";
					break;
				case 7:
					value[6]="低价活动审核中";
					break;
				default:
					break;
				}
				dataList.add(value);
			}
		}
		//没有搜索到数据
		else{
			title=new String[1];
			title[0]=Constants.NO_DATA_EXPORT;//无数据提示
		}
		//调用公共方法导出数据
		CommonMethod.exportData(request, response, fileName,title, dataList);
		return true;
	}
	
	/**
	 * 获取所有的审核通过的活动信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllPassActivityInformation", method = RequestMethod.POST)
	public List<ActivityInformation> getAllPassActivityInformation() {
		
		return activityInformationService.getAllPassActivityInformation();
	}
	
	/**
	 * 获取所有的审核通过的秒杀活动信息（activityType==2）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getAllPassActivityInformationByType2", method = RequestMethod.POST)
	public List<ActivityInformation> getAllPassActivityInformationByType2() {
		
		return activityInformationService.getAllPassActivityInformationByType2();
	}
	
	
	private boolean isLowPrice(int activityInformationId) {
		ActivityInformation ai = activityInformationService.selectByPrimaryKey(activityInformationId);//活动信息表对象
		int activityType = ai.getActivityType();//获取活动类型
		GoodsActivity ga = null;
		GoodsDetails gd = null;
		GoodsSpecificationDetails gsd = null;
		Classification c = null;
		//获取活动商品信息
		List<GoodsActivity> goodsActivitieList = goodsActivityService.getMsgByActivityInformationId(activityInformationId);//从goods_activity表中查询该行活动下的所有商品信息		
		//是否显示预警信息
		boolean warning = false;
		for (int i = 0; i < goodsActivitieList.size(); i++) {
			int state = goodsActivitieList.get(i).getState();//0：规格   1：商品  2：分类 
			int goodsId1 = goodsActivitieList.get(i).getGoodsId();
			//获取预警信息
			int saleId ;  //规格表中的购销存商品id 
			double stockPrice;    //库存价格
			switch (activityType) {
			case Constants.TYPE_HD_ZK:
				//折扣 多商品 			
				double yPrice,zk,zkPrice;
				int goodsId;		
				switch (state) {
				  case 0://规格
						ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
						gsd = ga.getGoodsSpecificationDetails();
						//获取库存价格 所需参数：规格表中的购销存商品id  
						//规格表中的购销存商品id 
						//saleId = gsd.getSaleId();
						//获取库存价格
						//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
						stockPrice = gsd.getMiniPrice();
						//获取原价
						yPrice = gsd.getPrice();
						//获取折扣
						zk  = ai.getDiscount();
						//折扣价
						zkPrice = yPrice * zk;
						warning = zkPrice < stockPrice;
						break;
					case 1://商品详情
						ga = goodsActivityService.getMsgByActivityInformationId1(activityInformationId,goodsId1);//select gd.name,gd.id 
						gd = ga.getGoodsDetails();
						goodsId = gd.getId();
						//根据商品id获取规格
						List<GoodsSpecificationDetails> gsds = goodsSpecificationDetailsService.selectByGoodsId(goodsId);
						for (int j = 0; j < gsds.size(); j++) {
							  gsd = gsds.get(j);
							  //获取库存价格 所需参数：规格表中的购销存商品id  
							  //规格表中的购销存商品id 
							  //saleId = gsd.getSaleId();
							  //获取库存价格
							  //stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
							  stockPrice = gsd.getMiniPrice();
							  //获取原价
							  yPrice = gsd.getPrice();
							  //获取折扣
							  zk  = ai.getDiscount();
							  //折扣价
							  zkPrice = yPrice * zk;
							  warning = zkPrice < stockPrice;
							  if(warning){
							  	 break;
							  }
						}
						break;
						case 2://分类
							ga = goodsActivityService.getMsgByActivityInformationId2(activityInformationId,goodsId1);//select c.name,c.id
							c = ga.getClassification();
							int classificationId = c.getId();
							List<GoodsDetails> gds = goodsDetailsService.getGoodsListByClassificationId(classificationId);
							for (int j = 0; j < gds.size(); j++) {
								gd = gds.get(j);
								goodsId = gd.getId();
								//根据商品id获取规格
							  List<GoodsSpecificationDetails> gsds2 = goodsSpecificationDetailsService.selectByGoodsId(goodsId);
							  for (int k = 0; k < gsds2.size(); k++) {
								  gsd = gsds2.get(k);
								//获取库存价格 所需参数：规格表中的购销存商品id  
									//规格表中的购销存商品id 
									//saleId = gsd.getSaleId();
									//获取库存价格
									//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
								  stockPrice = gsd.getMiniPrice();
									//获取原价
									yPrice = gsd.getPrice();
									//获取折扣
									zk  = ai.getDiscount();
									//折扣价
									zkPrice = yPrice * zk;
									warning = zkPrice < stockPrice;
									if(warning){
										break;
									}
							  	}
							  if(warning){
									break;
								}
							}
							break;

						default:
							break;
						}
						break;
					case Constants.TYPE_HD_TG:
						//团购 单商品 state=0
						
						ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
						gsd = ga.getGoodsSpecificationDetails();
						
						 //获取库存价格 所需参数：规格表中的购销存商品id  
						//规格表中的购销存商品id 
						//saleId = gsd.getSaleId();
						//获取库存价格
						//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
						stockPrice = gsd.getMiniPrice();
						//获取团购价
						double tgPrice = ai.getDiscount();
						warning = tgPrice<stockPrice;
						
						break;
					case Constants.TYPE_HD_MS:
						//秒杀 单商品 state=0
						
						ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
						gsd = ga.getGoodsSpecificationDetails();
						
						//获取库存价格 所需参数：规格表中的购销存商品id  
						//规格表中的购销存商品id 
						//saleId = gsd.getSaleId();
						//获取库存价格
						//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
						stockPrice = gsd.getMiniPrice();
						//获取秒杀价
						double msPrice = ai.getDiscount();
						warning = msPrice<stockPrice;
						
						break;
					case Constants.TYPE_HD_LJ:
						//立减 单商品 state=0 
						
						ga = goodsActivityService.getMsgByActivityInformationId0(activityInformationId,goodsId1);//select gd.name,gsd.specifications,gsd.price,gsd.sale_id
						gsd = ga.getGoodsSpecificationDetails();
						
						//获取库存价格 所需参数：规格表中的购销存商品id  
						//规格表中的购销存商品id 
						//saleId = gsd.getSaleId();
						//获取库存价格
						//stockPrice = goodsService.selectByPrimaryKey(saleId).getPurchase();
						stockPrice = gsd.getMiniPrice();
						//获取立减金额
						double ljPrice = ai.getDiscount();
						//从规格表中获取商品原价
						double price = gsd.getPrice();
						
						warning = (price - ljPrice)<stockPrice;
						
						break;
					case Constants.TYPE_HD_MJ:
						//满减 多商品  无预警
						
						break;
					case Constants.TYPE_HD_YS:
						//预售 多商品  无预警
						
						break;
					default:
						break;
					}
					
					if(warning){
						break;
					}
				}
		return warning;
	}

}
	
	
	

