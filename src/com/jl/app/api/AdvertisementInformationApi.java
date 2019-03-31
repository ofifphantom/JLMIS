package com.jl.app.api;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.hamcrest.core.IsEqual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.visitor.functions.Isnull;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.model.Classification;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.HotCategoriesGood;
import com.jl.mis.service.ActiveCouponService;
import com.jl.mis.service.ActivityInformationService;
import com.jl.mis.service.AdvertisementInformationService;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.CouponInformationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsDisplayPictureService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.HotCategoriesGoodService;
import com.jl.mis.utils.Constants;
import com.sun.istack.internal.NotNull;

/**
 * 广告信息API
 * 
 * @author 景雅倩
 * @date 2017-12-05 下午3:08:23
 * @Description TODO
 */
@Controller
@RequestMapping("/advertisementInformation/")
public class AdvertisementInformationApi extends BaseController {
	@Autowired
	private AdvertisementInformationService advertisementInformationService;

	@Autowired
	private ActivityInformationService activityInformationService;
	
	@Autowired
	private GoodsActivityService goodsActivityService;
	
	@Autowired
	private HotCategoriesGoodService hotCategoriesGoodService;

	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	
	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	
	@Autowired
	private GoodsDisplayPictureService goodsDisplayPictureService;
	
	@Autowired
	private ActiveCouponService activeCouponService;

	/**
	 * 根据广告类型获取正在生效的广告信息
	 * APP首页获取相关广告信息
	 * @param type (0：开屏，1：首页广告，2：分类广告，3：限时抢购，4：热门分类)
	 * @param request
	 * @return 正在生效的广告信息列表
	 */
	@ResponseBody
	@RequestMapping(value = "getEffectAdvertisementByType", method = RequestMethod.GET)
	public JSONObject getMsgForPage(@RequestParam("type") Integer type) {
	
		if(type==null){
			JSONObject result = new JSONObject();
			
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		List<AdvertisementInformation> advertisementInformationList=new ArrayList<>();
		
		AdvertisementInformation advertisementInformation = new AdvertisementInformation();
		advertisementInformation.setEffect(0);//有效
		advertisementInformation.setType(type);//首页广告
		
		if(type == 3){//限时抢购  设置生效时间   用来判断是否需要联合查询
			advertisementInformation.setEffectTime(new Date());
		}
		advertisementInformationList = advertisementInformationService.selectByTypeAndEffect(advertisementInformation);
		
		
		
		
		JSONObject result = new JSONObject();
		result.put("resultData", advertisementInformationList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
		
		
	}
	
	/**
	 * 根据广告信息ID获取广告及相关商品/活动信息
	 * 广告的点击事件接口
	 * @param request
	 * @param advertisementInformationId  广告id
	 * @return resultData.flag:旗帜变量  用来标志点击后跳转到:商品详情页面（0）  活动页面（1）  商品列表页面（2） 
	 *         resultData.goods:(flag==0)商品信息/(flag==1、flag==2)商品列表信息
	 *         当flag==1时：resultData.activityInformation:活动信息     resultData.couponInformation:活动相关优惠券列表
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "getAdvertisementById", method = RequestMethod.GET)
	public JSONObject getAdvertisementById(HttpServletRequest request,@RequestParam("advertisementInformationId") Integer advertisementInformationId) throws Exception {
		JSONObject result = new JSONObject();
		if(advertisementInformationId==null){
			
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		int flag = 0;//flag为旗帜变量  用来标志点击后跳转到:商品详情页面（0）活动页面 （1）  商品列表页面（2） 默认是跳转到商品详情页面
		JSONObject resultInfo = new JSONObject();//用来存放返回APP的信息（商品：商品相关信息           活动：活动和活动商品）
		List<GoodsDetails> goodsList = new ArrayList<>();
		
		//根据广告id获取广告信息
		AdvertisementInformation item = advertisementInformationService.selectByPrimaryKey(advertisementInformationId);
		if(item==null){
			
			result.put("code", 40001);
			result.put("msg", "查询不到广告信息");
			return result;	
		}
		
		//判断是否是热门分类广告
		if(item.getType() == 4){//是热门分类广告
			//设置跳转页面旗帜变量
			flag = 2;
			
			//获取商品列表
			List<HotCategoriesGood> hotCategoriesGoodList = hotCategoriesGoodService.selectByAdvertisementInformationId(advertisementInformationId);
			if(hotCategoriesGoodList !=null && hotCategoriesGoodList.size()>0){//该广告对应的有商品信息
				for (int i = 0; i < hotCategoriesGoodList.size(); i++) {
					HotCategoriesGood hotCategoriesGood = hotCategoriesGoodList.get(i);
					List<GoodsDetails> goodsList2 = new ArrayList<>();//中间列表
					int state = hotCategoriesGood.getState();
					int id =  hotCategoriesGood.getGoodsId();
//					System.out.println("id:"+id);
					switch (state) {
					case 1://商品     id:商品详情id
						JSONObject object2 =  getGoodsDetailMsgByGoodsId(request, id);
						int code2 = object2.getIntValue("code");
						System.out.println("-----------code2-----------:"+code2);
						if(code2==200){
							System.out.println("----------------------");
							GoodsDetails goodsDetails = (GoodsDetails) object2.get("resultData");
							goodsList2.add(goodsDetails);
						}
						break;
					case 2://分类     id:分类id 
						//------------------------START对私有方法的处理START-----------------------------
					
						//判断分类id是一级分类还是二级分类  如果是一级分类  遍历出该一级分类下的二级分类id列表  然后根据二级分类id查询 最后将结果集合起来
						int parerntId = classificationService.selectByPrimaryKey(id).getParentId();
						if(parerntId == 0){

							List<Classification> twoClaList = classificationService.selectTwoByOneId(id);
							for (int j = 0; j < twoClaList.size(); j++) {
								int twoClaId = twoClaList.get(j).getId();
								JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",twoClaId);
								int code = object.getIntValue("code");
							
								if(code==200){
									List<GoodsDetails> goodsList1=(List<GoodsDetails>) object.get("resultData");
									goodsList2.addAll(goodsList1);
								}
								
							}
							
							
						}else{
							JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",id);
							int code = object.getIntValue("code");
							if(code==200){
								goodsList2=(List<GoodsDetails>) object.get("resultData");
							}
							
						}
						//------------------------END对私有方法的处理END-----------------------------
						break;

					default:
						break;
					}
					goodsList.addAll(goodsList2);
				}
			}
			
			
			
			
			
			resultInfo.put("goods",goodsList);//活动相关商品信息
			resultInfo.put("flag",flag );//跳转页面旗帜变量
			
			resultInfo.put("activityInformation",null );//活动信息   
			resultInfo.put("couponInformation",null );
			
			result.put("resultData", resultInfo);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		
		
		
		
		
		
		//非热门分类广告
		List<GoodsDetails> goods=new ArrayList<GoodsDetails>();

		int urlType = item.getUrlType();
		int urlParameterId = item.getUrlParameterId();
		JSONObject object0 ;
		int code0 ;
		GoodsDetails goodsDetails0 = new GoodsDetails();
		switch (urlType) {
		case 0://商品
			//获取展示商品详情所需内容  跳转至商品详情页
			
			object0 =  getGoodsDetailMsgByGoodsId(request, urlParameterId);
			code0 = object0.getIntValue("code");
			if(code0==200){
				goodsDetails0 = (GoodsDetails) object0.get("resultData");
			}
			goods.add(goodsDetails0);
			resultInfo.put("goods", goods);//商品信息
			resultInfo.put("flag",flag );//跳转页面旗帜变量
			resultInfo.put("activityInformation",null );//活动信息   
			resultInfo.put("couponInformation",null );
			result.put("resultData", resultInfo);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
			
		case 1://活动
			
			
			
			//获取展示活动详情所需内容（活动信息以及参与活动的商品）
			//获取活动信息
			ActivityInformation activityInformation = activityInformationService.selectByPrimaryKey(urlParameterId);
			//获取活动相关优惠券信息
			List<CouponInformation> couponInformation = activeCouponService.getMsgByActivityInformationId(urlParameterId);
			
			//获取该活动下的商品列表
			List<GoodsActivity> goodsActivityList = goodsActivityService.getMsgByActivityInformationId(urlParameterId);
			if(goodsActivityList!=null && goodsActivityList.size()>0){//该活动有对应的商品信息
				
				
				if(goodsActivityList.size()==1){
					GoodsActivity ga = goodsActivityList.get(0);
					switch (ga.getState()) {
					case 0://规格    跳转至商品详情页
						int goodsId = goodsSpecificationDetailsService.selectByPrimaryKey(ga.getGoodsId()).getGoodsId();
						
						object0 =  getGoodsDetailMsgByGoodsId(request, goodsId);
						code0 = object0.getIntValue("code");
						if(code0==200){
							goodsDetails0 = (GoodsDetails) object0.get("resultData");
						}
						
						goods.add(goodsDetails0);
						resultInfo.put("goods", goods);//商品信息
						resultInfo.put("flag",flag );//跳转页面旗帜变量
						resultInfo.put("activityInformation",null );//活动信息   
						resultInfo.put("couponInformation",null );
						
						result.put("resultData", resultInfo);
						result.put("code", 200);
						result.put("msg", "请求成功");
						return result;
					case 1://商品    跳转至商品详情页
						
						object0 =  getGoodsDetailMsgByGoodsId(request, ga.getGoodsId());
						code0 = object0.getIntValue("code");
						if(code0==200){
							goodsDetails0 = (GoodsDetails) object0.get("resultData");
						}
						
						goods.add(goodsDetails0);
						resultInfo.put("goods", goods);//商品信息
						resultInfo.put("flag",flag );//跳转页面旗帜变量
						resultInfo.put("activityInformation",null );//活动信息   
						resultInfo.put("couponInformation",null );
						result.put("resultData", resultInfo);
						result.put("code", 200);
						result.put("msg", "请求成功");
						return result;
					case 2://分类    跳转至活动页展示活动列表
						flag = 1;
						//------------------------START对私有方法的处理START-----------------------------
						int classificationId = ga.getGoodsId();
						//判断分类id是一级分类还是二级分类  如果是一级分类  遍历出该一级分类下的二级分类id列表  然后根据二级分类id查询 最后将结果集合起来
						int parerntId = classificationService.selectByPrimaryKey(classificationId).getParentId();
						if(parerntId == 0){
							
							List<Classification> twoClaList = classificationService.selectTwoByOneId(classificationId);
							for (int i = 0; i < twoClaList.size(); i++) {
								int twoClaId = twoClaList.get(i).getId();
								JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",twoClaId);
								int code = object.getIntValue("code");
								if(code == 200){
									List<GoodsDetails> goodsList1=(List<GoodsDetails>) object.get("resultData");
									goodsList.addAll(goodsList1);
								}
								
							}
							
							
						}else{
							JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",classificationId);
							int code = object.getIntValue("code");
							if(code == 200){
								goodsList=(List<GoodsDetails>) object.get("resultData");
							}
							
						}
						//------------------------END对私有方法的处理END-----------------------------

						break;
	

					
					}
					
				}else if(goodsActivityList.size()>1){//多商品   跳转至活动页展示活动列表
//					System.out.println("goodsActivityList.size:"+goodsActivityList.size());
					List<Integer> gsdGoodsIdList = new ArrayList<>();
					List<GoodsDetails> goodsList3 = new ArrayList<>();//中间列表
					for (int i = 0; i < goodsActivityList.size(); i++) {
						GoodsActivity ga = goodsActivityList.get(i);
						List<GoodsDetails> goodsList2 = new ArrayList<>();//中间列表
						switch (ga.getState()) {
						case 0://规格  将商品加入商品列表
							int goodsId = goodsSpecificationDetailsService.selectByPrimaryKey(ga.getGoodsId()).getGoodsId();
							if(!gsdGoodsIdList.contains(goodsId)){//去重
								gsdGoodsIdList.add(goodsId);
							}
//							System.out.println("case0 "+goodsId);
							break;
						case 1://商品  将商品加入商品列表
							JSONObject object2 =  getGoodsDetailMsgByGoodsId(request, ga.getGoodsId());
							int code2 = object2.getIntValue("code");
							if(code2==200){
								GoodsDetails goodsDetails = (GoodsDetails) object2.get("resultData");
								goodsList2.add(goodsDetails);
							}
//							System.out.println("case1 "+ga.getGoodsId());
							
							break;
						case 2://分类  遍历该分类下的所有商品并加入商品列表
							
							
							//------------------------START对私有方法的处理START-----------------------------
							int classificationId = ga.getGoodsId();
							//判断分类id是一级分类还是二级分类  如果是一级分类  遍历出该一级分类下的二级分类id列表  然后根据二级分类id查询 最后将结果集合起来
							int parerntId = classificationService.selectByPrimaryKey(classificationId).getParentId();
							if(parerntId == 0){
//								System.out.println("case2 if");
								List<Classification> twoClaList = classificationService.selectTwoByOneId(classificationId);
								for (int j = 0; j < twoClaList.size(); j++) {
									int twoClaId = twoClaList.get(j).getId();
									JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",twoClaId);
//									System.out.println("case2 if twoClaId:"+twoClaId);
									int code = object.getIntValue("code");
								
									if(code==200){
										List<GoodsDetails> goodsList1=(List<GoodsDetails>) object.get("resultData");
										goodsList2.addAll(goodsList1);
									}
									
								}
								
								
							}else{
//								System.out.println("case2 else");
								JSONObject object = getGoodsList(request,1,"","","true",0.0,0.0,"all",classificationId);
								int code = object.getIntValue("code");
								if(code==200){
									goodsList2=(List<GoodsDetails>) object.get("resultData");
								}
								
							}
							//------------------------END对私有方法的处理END-----------------------------
							break;
		

						default:
							break;
						}
						
						goodsList.addAll(goodsList2);
					}
					//处理类型为规格的活动商品
					if(gsdGoodsIdList.size()>0){
						int goodsId,code;
						for (int j = 0; j < gsdGoodsIdList.size(); j++) {
							goodsId = gsdGoodsIdList.get(j);
//							System.out.println("gsdGoodsId "+j+":"+goodsId);
							JSONObject object2 =  getGoodsDetailMsgByGoodsId(request, goodsId);
							code = object2.getIntValue("code");
							if(code==200){
								GoodsDetails goodsDetails = (GoodsDetails) object2.get("resultData");
								goodsList3.add(goodsDetails);
							}
							
						}
						
					}
					goodsList.addAll(goodsList3);
					
					flag = 1;
					
				}
			
			}
			
			resultInfo.put("activityInformation", activityInformation);//活动信息
			resultInfo.put("couponInformation", couponInformation);
			resultInfo.put("goods",goodsList);//活动相关商品信息
			resultInfo.put("flag",flag );//跳转页面旗帜变量
			
			break;
		default:
			break;
		}
		
		result.put("resultData", resultInfo);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//以下为私有方法
	
	
	
	/**
	 * 私有方法一
	 * 
	 * 获取商品的list列表 （传默认值则为热门推荐列表;刚进商品list显示页是也按默认值传参数;若无需该参数，则需传默认值!!!）
	 * 
	 * @param request
	 * @param sortType
	 *            页面list排序(1:综合排序，2：销量排序，3：价格排序，4：热门分类)，默认传1
	 * @param priceSort
	 *            根据价格排序时 是倒序还是正序（asc：正序，desc：倒序）默认为""
	 * @param searchName
	 *            页面头部的搜索栏 (如果没值则传""),默认传""
	 * @param isHasGoods
	 *            是否仅查看有货("true":是，"false"：否),默认传true
	 * @param minPrice
	 *            价格区间的最低价(不设置的话传0)，默认传0
	 * @param maxPrice
	 *            价格区间的最高价(不设置的话传0)，默认传0
	 * @param brandName
	 *            品牌名称(全部传 all)，默认传 all
	 * @param classificationId
	 *            分类id(全部传0)，默认传0
	 * @return
	 * @throws Exception
	 */
	private JSONObject getGoodsList(HttpServletRequest request,  Integer sortType,String priceSort,String searchName,String isHasGoods,Double minPrice,Double maxPrice,String brandName,Integer classificationId) throws Exception {
		JSONObject result = new JSONObject();
		if (sortType == null || priceSort == null || searchName == null || (isHasGoods == null || "".equals(isHasGoods))
				|| minPrice == null || maxPrice == null || (brandName == null || "".equals(brandName))
				|| classificationId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		if ("\"\"".equals(searchName) || "''".equals(searchName) || "".equals(searchName)) {
			searchName = "all";
		}
		String[] brandNames=brandName.split(",");
		List<GoodsDetails> goodsDetailsList = new ArrayList<GoodsDetails>();
		List<GoodsDetails> resultDataList = new ArrayList<GoodsDetails>();
		List<GoodsSpecificationDetails> gsdList = new ArrayList<>();
		List<GoodsDisplayPicture> gdpList = new ArrayList<>();

		// 获取商品列表
		goodsDetailsList = goodsDetailsService.getHomeHotGoods(classificationId, searchName, sortType);
		if (goodsDetailsList.size() == 0) {
			result.put("resultData", "未搜索到商品信息!");
			result.put("code", 201);
			result.put("msg", "请求成功");
			return result;
		} else {
			// 如果有列表则获取商品的按价格正序排序的第一个商品详情以及id最小的商品详情图片
			for (int i = 0; i < goodsDetailsList.size(); i++) {
				// 查找商品所拥有的商品详情
				List<GoodsSpecificationDetails> goodsSpecificationDetailslist=new ArrayList<>();
				//不允许0库存出库
				if(goodsDetailsList.get(i).getZeroStock()==0){
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), isHasGoods, brandNames,"");
				}
				//允许0库存出库
				else{
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), "false", brandNames,"");
				}
				
				gsdList = new ArrayList<>();

				// 说明可以查出来商品详情
				if (goodsSpecificationDetailslist.size() > 0) {
					// 说明只用查询最低值
					if (minPrice != 0 && maxPrice == 0) {
						if (goodsSpecificationDetailslist.get(0).getPrice() >= minPrice) {
							// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
							List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
							goodsDisplayPictureList = goodsDisplayPictureService
									.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
							gdpList = new ArrayList<>();

							if (goodsDisplayPictureList.size() > 0) {
								gdpList.add(goodsDisplayPictureList.get(0));
							}
							// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
							goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
							// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
							gsdList.add(goodsSpecificationDetailslist.get(0));
							// 如果该商品存在商品详情，则存入值
							goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

							// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
							resultDataList.add(goodsDetailsList.get(i));
						}
					}
					// 说明查询区间值
					else if (minPrice != 0 && maxPrice != 0) {
						if (goodsSpecificationDetailslist.get(0).getPrice() >= minPrice
								&& goodsSpecificationDetailslist.get(0).getPrice() <= maxPrice) {
							// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
							List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
							goodsDisplayPictureList = goodsDisplayPictureService
									.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
							gdpList = new ArrayList<>();

							if (goodsDisplayPictureList.size() > 0) {
								gdpList.add(goodsDisplayPictureList.get(0));
							}
							// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
							goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
							// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
							gsdList.add(goodsSpecificationDetailslist.get(0));
							// 如果该商品存在商品详情，则存入值
							goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

							// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
							resultDataList.add(goodsDetailsList.get(i));
						}
					}
					// 说明只用查询最高值
					else if (minPrice == 0 && maxPrice != 0) {
						if (goodsSpecificationDetailslist.get(0).getPrice() <= maxPrice) {
							// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
							List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
							goodsDisplayPictureList = goodsDisplayPictureService
									.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
							gdpList = new ArrayList<>();

							if (goodsDisplayPictureList.size() > 0) {
								gdpList.add(goodsDisplayPictureList.get(0));
							}
							// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
							goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
							// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
							gsdList.add(goodsSpecificationDetailslist.get(0));
							// 如果该商品存在商品详情，则存入值
							goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

							// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
							resultDataList.add(goodsDetailsList.get(i));
						}
					}
					// 说明不用判断价格
					else {
						// 获取第一个商品详情对应的第一个商品详情图片，存入热门推荐商品详情中,仅供APP首页的列表显示
						List<GoodsDisplayPicture> goodsDisplayPictureList = goodsDisplayPictureService
								.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
						gdpList = new ArrayList<>();

						if (goodsDisplayPictureList.size() > 0) {
							gdpList.add(goodsDisplayPictureList.get(0));
						}
						// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
						goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
						// 获取第一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
						gsdList.add(goodsSpecificationDetailslist.get(0));
						// 如果该商品存在商品详情，则存入值
						goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

						// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
						resultDataList.add(goodsDetailsList.get(i));
					}

				}

			}
			if (resultDataList.size() == 0) {
				result.put("resultData", "未搜索到商品信息!");
				result.put("code", 201);
				result.put("msg", "请求成功");
				return result;
			} else {
				// 如果按价格排序，价格则冒泡排序
				if (sortType == 3) {
					for (int i = 1; i < resultDataList.size(); i++) {
						for (int k = 0; k < resultDataList.size() - i; k++) {

							// 正序
							if ("asc".equals(priceSort)) {
								if (resultDataList.get(k).getGoodsSpecificationDetails().get(0)
										.getPrice() > resultDataList.get(k + 1).getGoodsSpecificationDetails().get(0)
												.getPrice()) {
									GoodsDetails goodsDetails = new GoodsDetails();
									goodsDetails = resultDataList.get(k);
									resultDataList.set(k, resultDataList.get(k + 1));
									resultDataList.set(k + 1, goodsDetails);
								}
							}
							// 倒序
							else {
								if (resultDataList.get(k).getGoodsSpecificationDetails().get(0)
										.getPrice() < resultDataList.get(k + 1).getGoodsSpecificationDetails().get(0)
												.getPrice()) {
									GoodsDetails goodsDetails = new GoodsDetails();
									goodsDetails = resultDataList.get(k);
									resultDataList.set(k, resultDataList.get(k + 1));
									resultDataList.set(k + 1, goodsDetails);
								}
							}
						}
					}
				}
			}

			for (int i = 0; i < resultDataList.size(); i++) {
//				System.out.println("方法一goodsId "+i+":"+resultDataList.get(i).getId());
			}
			
			
			result.put("resultData", resultDataList);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
	}

	
	/**
	 * 私有方法二
	 * 
	 * 根据商品id获取商品详情、商品活动、商品评价等信息
	 * @param request
	 * @param goodsId 商品id
	 * @return
	 * @throws Exception
	 */
	
	private JSONObject getGoodsDetailMsgByGoodsId(HttpServletRequest request,Integer goodsId) throws Exception {
		JSONObject result = new JSONObject();
		if (goodsId == null || goodsId <= 0) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		//先获取商品信息，然后查找该商品参与的活动信息	
		GoodsDetails goodsDetails=goodsDetailsService.getGoodsDetailAndEvaluationByGoodsId(goodsId);
		List<GoodsActivity> goodsActivityList=new ArrayList<>();
		
		if(goodsDetails!=null){
			//传入商品id和isGoodsId=‘true’/分类id和isGoodsId=‘false’ 把查出来的结果整合即是商品所参与的所有活动，放入商品信息中
			List<GoodsActivity> goodsActivityListByGoodsId=goodsActivityService.getGoodsActivityMsgByStateAndGoodsId(goodsDetails.getId(), "true");
			List<GoodsActivity> goodsActivityListByClassificationId=goodsActivityService.getGoodsActivityMsgByStateAndGoodsId(goodsDetails.getClassificationId(), "false");
			if(goodsActivityListByGoodsId!=null && goodsActivityListByGoodsId.size()>0){
				for(int i=0;i<goodsActivityListByGoodsId.size();i++){
					goodsActivityList.add(goodsActivityListByGoodsId.get(i));
				}
			}
			if(goodsActivityListByClassificationId!=null && goodsActivityListByClassificationId.size()>0){
				for(int i=0;i<goodsActivityListByClassificationId.size();i++){
					goodsActivityList.add(goodsActivityListByClassificationId.get(i));
				}
			}
			goodsDetails.setGoodsActivitys(goodsActivityList);
			//再根据商品id查出商品详情，该商品详情包含该商品详情所参与的活动
			List<GoodsSpecificationDetails> goodsSpecificationDetailsList=new ArrayList<>();
			//不允许0库存出库
			if(goodsDetails.getZeroStock()==0){
				goodsSpecificationDetailsList=goodsSpecificationDetailsService.getGoodsDetailMsgByGoodsId(goodsId,"","true");
			}
			//允许0库存出库
			else{
				goodsSpecificationDetailsList=goodsSpecificationDetailsService.getGoodsDetailMsgByGoodsId(goodsId,"","false");
			}
			if(goodsSpecificationDetailsList.size()>0) {
				//再把商品详情放入商品信息中传入移动端
				goodsDetails.setGoodsSpecificationDetails(goodsSpecificationDetailsList);
			}else {
				result.put("resultData", null);
				result.put("code", 2000);
				result.put("msg", "商品无库存");
				return result;
			}
			
		}
		
		result.put("resultData", goodsDetails);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 首页获取新品上架的5种一级分类
	 * @param request
	 * @param 无
	 * @return name:分类名称 
	 *         id:一级分类id
	 *         keyWord: 关键词
	 *         picUrl:图片路径
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "getNews_List", method = RequestMethod.GET)
	public JSONObject getNews_List(){
		JSONArray object = new JSONArray();
		JSONObject result = new JSONObject();
		List<Classification> classifications=hotCategoriesGoodService.getNews_List();
		object=(JSONArray) JSONArray.toJSON(classifications);
		result.put("code", 200);
		result.put("msg", "请求成功");
		result.put("resultData", object);
		return result;
	}
}
