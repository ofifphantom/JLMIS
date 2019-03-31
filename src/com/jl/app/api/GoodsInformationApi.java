package com.jl.app.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.Classification;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsDisplayPictureService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.HotCategoriesGoodService;
import com.jl.mis.utils.Constants;

/**
 * 商品信息API
 * 
 * @author 柳亚婷
 * @date 2017年12月8日 上午9:57:56
 * @Description
 *
 */
@Controller
@RequestMapping("/goodsInformation/")
public class GoodsInformationApi extends BaseController {

	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	@Autowired
	private GoodsDisplayPictureService goodsDisplayPictureService;
	@Autowired
	private GoodsActivityService goodsActivityService;
	
	@Autowired
	private ClassificationService classificationService;
	
	@Autowired
	private HotCategoriesGoodService hotCategoriesGoodService;
	
	/**
	 * 获取商品的list列表 （传默认值则为综合排序列表;刚进商品list显示页是也按默认值传参数;若无需该参数，则需传默认值!!!）
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
	 *            二级分类id(全部传0)，默认传0
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsList", method = RequestMethod.GET)
	public JSONObject getGoodsList(HttpServletRequest request, @RequestParam("sortType") Integer sortType,
			@RequestParam("priceSort") String priceSort, @RequestParam("searchName") String searchName,
			@RequestParam("isHasGoods") String isHasGoods, @RequestParam("minPrice") Double minPrice,
			@RequestParam("maxPrice") Double maxPrice, @RequestParam("brandName") String brandName,
			@RequestParam("classificationId") Integer classificationId) throws Exception {
		JSONObject result = new JSONObject();
		if (sortType == null || priceSort == null || searchName == null || (isHasGoods == null || "".equals(isHasGoods))
				|| minPrice == null || maxPrice == null || (brandName == null || "".equals(brandName))
				|| classificationId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		String[] brandNames=brandName.split(",");
		if ("\"\"".equals(searchName) || "''".equals(searchName) || "".equals(searchName)) {
			searchName = "all";
		}

		List<GoodsDetails> goodsDetailsList = new ArrayList<GoodsDetails>();
		List<GoodsDetails> resultDataList = new ArrayList<GoodsDetails>();
		List<GoodsSpecificationDetails> gsdList = new ArrayList<>();
		List<GoodsDisplayPicture> gdpList = new ArrayList<>();

		// 获取商品列表
		goodsDetailsList = goodsDetailsService.getHomeHotGoods(classificationId, searchName, sortType);
		/*if (goodsDetailsList.size() == 0) {
			result.put("resultData", "未搜索到商品信息!");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else */
		if(goodsDetailsList.size()>0){
			// 如果有列表则获取商品的按价格正序排序的第一个商品详情以及id最小的商品详情图片
			for (int i = 0; i < goodsDetailsList.size(); i++) {
				// 查找商品所拥有的商品详情
				List<GoodsSpecificationDetails> goodsSpecificationDetailslist=new ArrayList<>();
				//不允许0库存出库
				if(goodsDetailsList.get(i).getZeroStock()==0){
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), isHasGoods, brandNames,"on");
				}
				//允许0库存出库
				else{
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), "false", brandNames,"on");
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
						List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
						goodsDisplayPictureList = goodsDisplayPictureService
								.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
						gdpList = new ArrayList<>();

						if (goodsDisplayPictureList.size() > 0) {
							gdpList.add(goodsDisplayPictureList.get(0));
						}
						// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
						goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
						// 获取第 一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
						gsdList.add(goodsSpecificationDetailslist.get(0));
						// 如果该商品存在商品详情，则存入值
						goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

						// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
						resultDataList.add(goodsDetailsList.get(i));
					}

				}

			}
			/*if (resultDataList.size() == 0) {
				result.put("resultData", "未搜索到商品信息!");
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			} else */
			if(resultDataList.size()>0){
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
		
		}
		result.put("resultData", resultDataList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	/**
	 * 根据商品id获取商品详情、商品活动、商品评价等信息
	 * @param request
	 * @param goodsId 商品id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsDetailMsgByGoodsId", method = RequestMethod.GET)
	public JSONObject getGoodsDetailMsgByGoodsId(HttpServletRequest request, @RequestParam("goodsId") Integer goodsId) throws Exception {
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
			goodsSpecificationDetailsList=goodsSpecificationDetailsService.getGoodsDetailMsgByGoodsId(goodsId,"on","false");
			//没有查到规格
			if(goodsSpecificationDetailsList.size()==0){
				result.put("code", 80001);
				result.put("msg", "查不到商品规格或商品所含规格已下架!");
				return result;
			}
			else{
				//再把商品详情放入商品信息中传入移动端
				goodsDetails.setGoodsSpecificationDetails(goodsSpecificationDetailsList);
				
				result.put("resultData", goodsDetails);
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			}
			
		}
		else{
			result.put("code", 80002);
			result.put("msg", "查不到商品信息!");
			return result;
		}
		
		
	}
	
	/**
	 * 热门搜索词 销量前十的商品关键词
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectHotSearchWord", method = RequestMethod.GET)
	public JSONObject selectHotSearchWord(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		List<GoodsDetails> goodsDetails= goodsDetailsService.selectHotSearchWord();
		List<String> hotSearchWord=new ArrayList<>();
		if(goodsDetails!=null&&goodsDetails.size()>0){
			for(int i=0;i<goodsDetails.size();i++){
				if(goodsDetails.get(i).getSaleCount()!=null&&goodsDetails.get(i).getSaleCount()>0){
					hotSearchWord.add(goodsDetails.get(i).getKeyWord());
				}	
			}	
		}
		result.put("resultData", hotSearchWord);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	
	/**
	 * 查询app中的新品上架商品list 
	 * （传默认值则为综合排序列表;刚进商品list显示页是也按默认值传参数;若无需该参数，则需传默认值!!!）
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
	 *            一级分类id(全部新品上架商品 传 0 )
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsList_Ameliorate", method = RequestMethod.GET)
	public JSONObject getGoodsList_Ameliorate(HttpServletRequest request, @RequestParam("sortType") Integer sortType,
			@RequestParam("priceSort") String priceSort, @RequestParam("searchName") String searchName,
			@RequestParam("isHasGoods") String isHasGoods, @RequestParam("minPrice") Double minPrice,
			@RequestParam("maxPrice") Double maxPrice, @RequestParam("brandName") String brandName,
			@RequestParam("classificationId") Integer classificationId) throws Exception {
		JSONObject result = new JSONObject();
		if (sortType == null || priceSort == null || searchName == null || (isHasGoods == null || "".equals(isHasGoods))
				|| minPrice == null || maxPrice == null || (brandName == null || "".equals(brandName))
				|| classificationId == null) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		String[] brandNames=brandName.split(",");
		if ("\"\"".equals(searchName) || "''".equals(searchName) || "".equals(searchName)) {
			searchName = "all";
		}

		List<GoodsDetails> goodsDetailsList = new ArrayList<GoodsDetails>();
		List<GoodsDetails> resultDataList = new ArrayList<GoodsDetails>();
		List<GoodsSpecificationDetails> gsdList = new ArrayList<>();
		List<GoodsDisplayPicture> gdpList = new ArrayList<>();

		// 获取商品列表
		if(classificationId==0){
			List<Classification> classifications=hotCategoriesGoodService.getNews_List();
			for (Classification classification : classifications) {
				goodsDetailsList.addAll(goodsDetailsService.getGoodsList_Ameliorate(classification.getId(), searchName, sortType));
			}
		}else{
			goodsDetailsList = goodsDetailsService.getGoodsList_Ameliorate(classificationId, searchName, sortType);
		}
		/*if (goodsDetailsList.size() == 0) {
			result.put("resultData", "未搜索到商品信息!");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else */
		if(goodsDetailsList.size()>0){
			// 如果有列表则获取商品的按价格正序排序的第一个商品详情以及id最小的商品详情图片
			for (int i = 0; i < goodsDetailsList.size(); i++) {
				// 查找商品所拥有的商品详情
				List<GoodsSpecificationDetails> goodsSpecificationDetailslist=new ArrayList<>();
				//不允许0库存出库
				if(goodsDetailsList.get(i).getZeroStock()==0){
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), isHasGoods, brandNames,"on");
				}
				//允许0库存出库
				else{
					goodsSpecificationDetailslist = goodsSpecificationDetailsService
							.getGoodsSpecificationDetail(goodsDetailsList.get(i).getId(), "false", brandNames,"on");
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
						List<GoodsDisplayPicture> goodsDisplayPictureList=new ArrayList<>(); 
						goodsDisplayPictureList = goodsDisplayPictureService
								.getGoodsDisplayPicture(goodsSpecificationDetailslist.get(0).getId());
						gdpList = new ArrayList<>();

						if (goodsDisplayPictureList.size() > 0) {
							gdpList.add(goodsDisplayPictureList.get(0));
						}
						// 如果该商品详情不存在对应的图片，则存入空的,如果有，则存入的有值
						goodsSpecificationDetailslist.get(0).setGoodsDisplayPictures(gdpList);
						// 获取第 一个商品详情(也就是价格最低的)，存入热门推荐商品中,仅供APP首页的列表显示
						gsdList.add(goodsSpecificationDetailslist.get(0));
						// 如果该商品存在商品详情，则存入值
						goodsDetailsList.get(i).setGoodsSpecificationDetails(gsdList);

						// 把获取到的商品包括详情存入结果list中(如果没有商品详情，可能是根据搜索条件查不出对应的值)
						resultDataList.add(goodsDetailsList.get(i));
					}

				}

			}
			/*if (resultDataList.size() == 0) {
				result.put("resultData", "未搜索到商品信息!");
				result.put("code", 200);
				result.put("msg", "请求成功");
				return result;
			} else */
			if(resultDataList.size()>0){
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
		
		}
		result.put("resultData", resultDataList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
	
	/**
	 * 获取品牌列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "selectBrand", method = RequestMethod.GET)
	public JSONObject selectBrand(HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		List<String> brands= goodsDetailsService.selectBrand();
		result.put("resultData", brands);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}
}
