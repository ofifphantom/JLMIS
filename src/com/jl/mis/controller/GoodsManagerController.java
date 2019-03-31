package com.jl.mis.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsDisplayPicture;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.Log;
import com.jl.mis.service.AdvertisementInformationService;
import com.jl.mis.service.GoodsActivityService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.GoodsDisplayPictureService;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.service.HotCategoriesGoodService;
import com.jl.mis.service.LogService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;

@Controller
@RequestMapping("/backgroundConfiguration/goods/goodsManager/")
public class GoodsManagerController extends BaseController {

	// 当前类操作的类型(往log日志表中存储)
	private int operateType = Constants.TYPE_LOG_GOODS;
	// 保存商品展示图片的文件夹名
	private String folderName = "GoodsDisplayPicture";
	// 声明Log类
	Log log;

	@Autowired
	private GoodsSpecificationDetailsService goodsSpecificationDetailsService;
	@Autowired
	private GoodsDetailsService goodsDetailsService;
	@Autowired
	private GoodsDisplayPictureService goodsDisplayPictureService;
	@Autowired
	private GoodsActivityService goodsActivityService;
	@Autowired
	private HotCategoriesGoodService hotCategoriesGoodService;
	@Autowired
	private AdvertisementInformationService advertisementInformationService;
	@Autowired
	private LogService logService;

	/**
	 * 进入商品管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}

		return "进入商品管理页面";
	}

	/**
	 * 从数据库中获取商品信息
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "getGoodsMsg")
	public DataTables getManagerMsg(HttpServletRequest request, String identifier, String name, String operatorName,
			String gxcGoodsStock, String oneOrTwo, String classificationId, String activityName, String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);

		return goodsSpecificationDetailsService.getManagerMsg(dataTables, identifier, name, operatorName, gxcGoodsStock,
				oneOrTwo, classificationId, activityName, operatorTime);
	}
	/**
	 * 从数据库中获取商品信息
	 * 
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "getCommentMsg")
	public DataTables getManagerMsg(HttpServletRequest request, Integer id) {
		DataTables dataTables = DataTables.createDataTables(request);

		return goodsSpecificationDetailsService.getGoodsComment(request,dataTables, id);
	}
	/**
	 * 从数据库中获取所有商品详情信息
	 * 
	 * @param request
	 */
	/*@ResponseBody
	@RequestMapping(value = "getGoodsSpecificationDetailsMsgByInputValue")
	public List<GoodsSpecificationDetails> getGoodsSpecificationDetailsMsgByInputValue(HttpServletRequest request,
			String inputValue) {

		return goodsSpecificationDetailsService.selectMsgByInputValue(inputValue);
	}*/

	/**
	 * 根据商品名称从数据库中获取对应的商品详情信息
	 * 
	 * @param request
	 */
	/*@ResponseBody
	@RequestMapping(value = "getGoodsDetailsMsgByName")
	public GoodsDetails getGoodsDetailsMsgByName(HttpServletRequest request, String name) {

		return goodsDetailsService.selectGoodsAndClassificationByName(name);
	}*/

	/**
	 * 富文本框上传图片
	 * 
	 * @param request
	 * @throws ServletException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@ResponseBody
	@RequestMapping(value = "insertPicUrl")
	public JSONObject insertPicUrl(HttpServletRequest request,
			@RequestParam(value = "yourFileName", required = false) MultipartFile file)
			throws IllegalStateException, IOException, ServletException {
		String folderName = "GoodsEditorPics";
		String img_url = "";
		if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
			img_url = CommonMethod.savePic(request, folderName, file);

		}
		String projectName = request.getContextPath().split("\\/")[1];
		// 获取图片url地址
		String imgUrl = request.getRequestURL().toString().split(projectName)[0] + projectName + "/" + img_url;

		// 这里的data数据形式必须如此，返回的url地址才能匹配上wangeditor3的源程序要求
		String data = "{errno: 0,data: ['" + imgUrl + "']}";
		JSONObject jsonObject = JSONObject.parseObject(data);
		return jsonObject;
	}

	/**
	 * 添加商品规格时，先查询此规格是否存在
	 * 
	 * @param request
	 * @param commoditySpecificationId
	 *            购销存商品规格id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectGoodsSDMsgBySpecificationAndGoodsId", method = RequestMethod.POST)
	public JSONObject selectGoodsSDMsgBySpecificationAndGoodsId(HttpServletRequest request,
			Integer commoditySpecificationId) {
		JSONObject rmsg = new JSONObject();
		// 根据 购销存商品规格id 查找该商品规格是否已存在
		Integer resoult = goodsSpecificationDetailsService
				.selectGoodsSDMsgBySpecificationAndGoodsId(commoditySpecificationId);
		// 没有查到结果集，说明该规格不存在
		if (resoult == null || resoult <= 0) {
			rmsg.put("success", true);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", "该规格已存在,请勿重复添加!");
		return rmsg;
	}

	/**
	 * 根据购销存商品id获取mis商品id
	 * 
	 * @param request
	 * @param commodityId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectIdByCommodityId", method = RequestMethod.GET)
	public JSONObject selectIdByCommodityId(HttpServletRequest request, Integer commodityId) {
		JSONObject rmsg = new JSONObject();
		GoodsDetails details = goodsDetailsService.selectIdByCommodityId(commodityId);
		if (details != null) {
			rmsg.put("resoult", details);
			rmsg.put("success", true);
		} else {
			rmsg.put("success", false);
		}
		return rmsg;
	}

	/**
	 * 添加商品信息
	 * 
	 * @param file
	 *            图片
	 * @param request
	 *            前台传入得数据
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addGoodsSpecificationDetailsMsg", method = RequestMethod.POST)
	public JSONObject addGoodsSpecificationDetailsMsg(
			@RequestParam(value = "previewImg", required = false) MultipartFile[] files, HttpServletRequest request)
			throws Exception {
		JSONObject rmsg = new JSONObject();
		// 商品规格
		GoodsSpecificationDetails goodsSpecificationDetails = new GoodsSpecificationDetails();
		// 商品详情
		GoodsDetails goodsDetails = new GoodsDetails();
		// 商品展示图
		GoodsDisplayPicture goodsDisplayPicture = new GoodsDisplayPicture();
		int goodDetailsResult = -1; // 对商品详情表进行操作的结果
		int goodsSpecificationDetailsResult = -1; // 对商品规格表进行操作的结果

		String goodDetailsId_String = request.getParameter("goodsDetailId");
		int goodDetailsId = -1;
		System.out.println("commodityId:" + request.getParameter("commodityId"));
		// 当id不为空时，说明此商品名称已存在，只是新添规格。
		if (goodDetailsId_String != null && !"".equals(goodDetailsId_String)) {
			goodDetailsId = Integer.parseInt(goodDetailsId_String);
			// 商品规格表的商品详情id为送前台获取的id
			goodsSpecificationDetails.setGoodsId(goodDetailsId);
			// 对商品详情表进行更新
			goodsDetails.setId(goodDetailsId);
			goodsDetails.setKeyWord(request.getParameter("keyWord"));
			goodsDetails.setDetails(request.getParameter("contant"));
			goodsDetails.setIntrodution(request.getParameter("introdution"));
			// 调用商品详情表的更新操作
			goodDetailsResult = goodsDetailsService.updateByPrimaryKeySelective(goodsDetails);
		} else {
			// 对商品详情表进行新增操作
			goodsDetails.setCommodityId(Integer.parseInt(request.getParameter("commodityId")));
			goodsDetails.setClassificationId(Integer.parseInt(request.getParameter("classificationSelect")));
			goodsDetails.setKeyWord(request.getParameter("keyWord"));
			goodsDetails.setDetails(request.getParameter("contant"));
			goodsDetails.setIntrodution(request.getParameter("introdution"));
			goodsDetails.setRecom(0);// 默认不是推荐

			// 调用商品详情表的新增操作
			goodDetailsResult = goodsDetailsService.insertSelective(goodsDetails);

			// 商品规格表的商品详情id为新添加进去的数据的id
			goodsSpecificationDetails.setGoodsId(goodsDetails.getId());

		}
		// 商品详情表操作成功
		if (goodDetailsResult > 0) {
			// 给商品规格中添加信息
			// 获取购销存中商品规格的id
			goodsSpecificationDetails
					.setCommoditySpecificationId(Integer.parseInt(request.getParameter("commoditySpecId")));
			// 原价格(划横线价格)
			if(request.getParameter("oldPrice")!=null&&!"".equals(request.getParameter("oldPrice").toString())){
				goodsSpecificationDetails.setOldPrice(Double.parseDouble(request.getParameter("oldPrice")));
			}
			else{
				goodsSpecificationDetails.setOldPrice(null);
			}
			
			// 销量默认为0
			goodsSpecificationDetails.setSalesCount(0);
			// 添加操作时间
			Date date = new Date();
			goodsSpecificationDetails.setOperatorTime(date);
			// 添加操作人编号，从session中获取
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			goodsSpecificationDetails.setOperatorIdentifier(userIdentifier);
			// 调用商品规格表的新增操作
			goodsSpecificationDetailsResult = goodsSpecificationDetailsService
					.insertSelective(goodsSpecificationDetails);

			// 商品规格表操作成功
			if (goodsSpecificationDetailsResult > 0) {
				// 商品展示图的商品规格id为刚添进去的数据的id
				goodsDisplayPicture.setGoodsSpecificationDetailId(goodsSpecificationDetails.getId());
				// 存储新图片，并保存路径
				if (files != null && files.length > 0) {
					for (int i = 0; i < files.length; i++) {
						MultipartFile file = files[i];
						if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
							goodsDisplayPicture.setPicUrl(CommonMethod.savePic(request, folderName, file));
							// 调用商品展示图表的新增操作
							goodsDisplayPictureService.insertSelective(goodsDisplayPicture);
						}
					}
				}

				// 往log表中插入操作数据
				insertLog(operateType, request.getParameter("commoditySpecIdentifier"), Constants.OPERATE_INSERT, date,
						userIdentifier);
				// 往前台返回结果集
				rmsg.put("success", true);
				rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
				return rmsg;

			}

		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 编辑时根据商品规格表的主键联合查询商品详情表、商品规格表以及分类表
	 * 
	 * @param request
	 * @param id
	 *            商品规格表id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMsgByGSDId")
	public GoodsSpecificationDetails getMsgByGSDId(HttpServletRequest request, String id) {

		return goodsSpecificationDetailsService.selectGSDAndGDAndClassificationMsgByGSDId(id);
	}

	/**
	 * 编辑商品规格时，先查询此规格是否存在
	 * 
	 * @param request
	 * @param goodDetailsId
	 *            商品详情id
	 * @param goodsSpecificationDetailsId
	 *            商品规格id
	 * @param specifications
	 *            商品规格
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "selectGoodsSDMsgBySpecificationAndGoodsSDId", method = RequestMethod.POST)
	public JSONObject selectGoodsSDMsgBySpecificationAndGoodsSDId(HttpServletRequest request, String goodDetailsId,
			String goodsSpecificationDetailsId, String specifications) {
		// System.out.println("goodDetailsId:"+goodDetailsId);
		JSONObject rmsg = new JSONObject();
		// 如果商品详情id不为空，则说明只是新添加商品规格，此时只用根据商品规格id和商品规格名称以及商品规格id(排除其本身)查询规格表，看是否有重复
		if (goodDetailsId != null && !"".equals(goodDetailsId)) {
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectGoodsSDMsgBySpecificationAndGoodsSDId(goodDetailsId, goodsSpecificationDetailsId,
							specifications);
			// 为空说明该规格不存在，可以添加
			if (goodsSpecificationDetails == null) {
				// 往前台返回结果集
				rmsg.put("success", true);
				return rmsg;
			}
		} else {
			rmsg.put("success", true);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", "该规格已存在,请勿重复添加!");
		return rmsg;
	}*/

	/**
	 * 编辑时，页面中删除已存入数据库的图片
	 * 
	 * @param request
	 * @param url
	 *            商品展示图路径
	 * @param id
	 *            商品展示图id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deleteGoodsDisplayPictureByUrl")
	public JSONObject deleteGoodsDisplayPictureByUrl(HttpServletRequest request, String url, String id) {
		JSONObject rmsg = new JSONObject();
		// 删除商品展示图表中保存的图片信息
		if (goodsDisplayPictureService.deleteByPrimaryKey(Integer.parseInt(id)) > 0) {
			String projectName = request.getContextPath().split("\\/")[1];
			// 获取图片url地址
			String imgUrl = url.split(projectName + "/")[1];
			List<String> oldPicUrlList = new ArrayList<>();
			oldPicUrlList.add(imgUrl);
			// 删除图片
			CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
			rmsg.put("success", true);
			return rmsg;
		} else {
			rmsg.put("success", false);
			rmsg.put("msg", "删除失败!");
			return rmsg;
		}

	}

	/**
	 * 编辑商品信息
	 * 
	 * @param file
	 *            图片
	 * @param request
	 *            前台传入得数据
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateGoodsSpecificationDetailsMsg", method = RequestMethod.POST)
	public JSONObject updateGoodsSpecificationDetailsMsg(
			@RequestParam(value = "previewImg", required = false) MultipartFile[] files, HttpServletRequest request,String identifier)
			throws Exception {
		JSONObject rmsg = new JSONObject();
		// 商品规格
		GoodsSpecificationDetails goodsSpecificationDetails = new GoodsSpecificationDetails();
		// 商品详情
		GoodsDetails goodsDetails = new GoodsDetails();
		// 商品展示图
		GoodsDisplayPicture goodsDisplayPicture = new GoodsDisplayPicture();
		int goodDetailsResult = -1; // 对商品详情表进行操作的结果
		int goodsSpecificationDetailsResult = -1; // 对商品规格表进行操作的结果

		String goodDetailsId_String = request.getParameter("goodsDetailId");

		int goodDetailsId = Integer.parseInt(goodDetailsId_String);
		// 商品规格表的商品详情id为送前台获取的id
		goodsSpecificationDetails.setGoodsId(goodDetailsId);
		// 对商品详情表进行更新
		goodsDetails.setId(goodDetailsId);
		goodsDetails.setKeyWord(request.getParameter("keyWord"));
		goodsDetails.setDetails(request.getParameter("contant"));
		goodsDetails.setIntrodution(request.getParameter("introdution"));
		// 调用商品详情表的更新操作
		goodDetailsResult = goodsDetailsService.updateByPrimaryKeySelective(goodsDetails);

		// 商品详情表操作成功
		if (goodDetailsResult > 0) {
			// 给商品规格中添加信息
			// 商品规格id
			goodsSpecificationDetails.setId(Integer.parseInt(request.getParameter("goodsSpecDetailId")));
			// 商品价格
			if(request.getParameter("oldPrice")!=null&&!"".equals(request.getParameter("oldPrice").toString())){
				goodsSpecificationDetails.setOldPrice(Double.parseDouble(request.getParameter("oldPrice")));
			}
			else{
				goodsSpecificationDetails.setOldPrice(null);
			}
			

			// 商品编号
			String goodsSpecificationDetailsIdentifier = request.getParameter("update_identifier");

			// 调用商品规格表的更新操作
			goodsSpecificationDetailsResult = goodsSpecificationDetailsService
					.updateByPrimaryKeySelective(goodsSpecificationDetails);

			// 商品规格表操作成功
			if (goodsSpecificationDetailsResult > 0) {
				goodsDisplayPicture.setGoodsSpecificationDetailId(goodsSpecificationDetails.getId());
				// 存储新图片，并保存路径
				if (files != null && files.length > 0) {
					for (int i = 0; i < files.length; i++) {
						MultipartFile file = files[i];
						if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
							goodsDisplayPicture.setPicUrl(CommonMethod.savePic(request, folderName, file));
							// 调用商品展示图表的新增操作
							goodsDisplayPictureService.insertSelective(goodsDisplayPicture);
						}
					}
				}

				// 往log表中插入操作数据
				insertLog(operateType, identifier, Constants.OPERATE_UPDATE, new Date(),
						GetSessionUtil.GetSessionUserIdentifier(request));
				// 往前台返回结果集
				rmsg.put("success", true);
				rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
				return rmsg;

			}

		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 删除前判断该商品详情或者其所属的商品有没有参加活动或者存在于热门分类以及广告中。
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/decideGoodsIsHasActivityBeforeDelete", method = RequestMethod.POST)
	public JSONObject decideGoodsIsHasActivityBeforeDelete(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		List<String> isNotDeletePrimaryKeys = new ArrayList<>();
		String resultMsg = "";
		// 从前台中获取选中的id
		String[] primaryKeys = request.getParameterValues("id");
		for (String key : primaryKeys) {
			GoodsSpecificationDetails goodsSpecificationDetails = goodsSpecificationDetailsService
					.selectSpecIdentifierById(Integer.parseInt(key));
			// 先判断该商品详情id是否参与了活动，若参与，该商品详情则不能删除
			if (goodsActivityService.selectGoodsActivityBySpecificationId(key, "false").size() > 0) {
				isNotDeletePrimaryKeys.add(goodsSpecificationDetails.getIdentifier());
			}
			// 再判断该商品详情所属的商品有没有参加活动，若参与，该商品详情则不能删除
			else if (goodsActivityService.selectGoodsActivityBySpecificationId(key, "true").size() > 0) {
				isNotDeletePrimaryKeys.add(goodsSpecificationDetails.getIdentifier());
			}
			// 判断商品详情所属的商品有没有在热门分类中
			if (hotCategoriesGoodService.selectHotCategoriesGoodBySpecificationId(key).size() > 0) {
				if (isNotDeletePrimaryKeys.size() == 0) {
					isNotDeletePrimaryKeys.add(goodsSpecificationDetails.getIdentifier());
				} else {
					for (int i = 0; i < isNotDeletePrimaryKeys.size(); i++) {
						if (!isNotDeletePrimaryKeys.contains(goodsSpecificationDetails.getIdentifier())) {
							isNotDeletePrimaryKeys.add(goodsSpecificationDetails.getIdentifier());
						}
					}
				}

			}
			// 判断商品详情所属的商品有没有在广告中
			if (advertisementInformationService.selectAdvertismentBySpecificationId(key).size() > 0) {
				if (isNotDeletePrimaryKeys.size() == 0) {
					isNotDeletePrimaryKeys.add(goodsSpecificationDetails.getIdentifier());
				} else {
					for (int i = 0; i < isNotDeletePrimaryKeys.size(); i++) {
						if (!isNotDeletePrimaryKeys.contains(goodsSpecificationDetails.getIdentifier())) {
							isNotDeletePrimaryKeys.add(goodsSpecificationDetails.getIdentifier());
						}
					}
				}

			}
		}

		for (int i = 0; i < isNotDeletePrimaryKeys.size(); i++) {
			if (i == 0) {
				resultMsg += "(";
			}
			if (i < isNotDeletePrimaryKeys.size() - 1) {
				resultMsg += isNotDeletePrimaryKeys.get(i) + ",";
			} else {
				resultMsg += isNotDeletePrimaryKeys.get(i);
			}
			if (i == isNotDeletePrimaryKeys.size() - 1) {
				resultMsg += ")";
			}
		}
		if (!"".equals(resultMsg)) {

			rmsg.put("msg", resultMsg + "以上显示的商品详情编号参与了活动/限时抢购/热门分类,暂不能删除!");
			rmsg.put("success", false);
			return rmsg;
		}

		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 根据主键(批量/单个)删除类型信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delBatchByPrimaryKey", method = RequestMethod.POST)
	public JSONObject delBatchByPrimaryKey(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 从前台中获取选中的id
		String[] primaryKeys = request.getParameterValues("id");
		// 在删除前保存要删除的规格的信息
		List<GoodsSpecificationDetails> goodsSpecificationDetails = goodsSpecificationDetailsService
				.selectBatchByPrimaryKey(primaryKeys);
		// 批量删除规格表
		int result = goodsSpecificationDetailsService.deleteBatchByPrimaryKey(primaryKeys);

		// 删除完之后，根据之前保存的规格信息里的商品详情id查询规格表，如果没有对应的规格，则需要把此商品详情信息给删除。
		for (GoodsSpecificationDetails gsd : goodsSpecificationDetails) {
			List<GoodsSpecificationDetails> goodsSpecificationDetailsByGoodsId = new ArrayList<>();
			goodsSpecificationDetailsByGoodsId = goodsSpecificationDetailsService.selectByGoodsId(gsd.getGoodsId());
			// 说明该商品详情id下没有对应的商品规格，此时要删除该商品详情信息
			if (goodsSpecificationDetailsByGoodsId.size() == 0) {
				goodsDetailsService.deleteByPrimaryKey(gsd.getGoodsId());
			}
		}

		// 在删除展示图信息前保存要删除的信息，以便之后删除图片
		List<GoodsDisplayPicture> goodsDisplayPictureList = goodsDisplayPictureService
				.selectBatchByGoodsSpecificationDetailId(primaryKeys);

		// 保存往Log表中添加的操作对象的编号
		String identifierList = "";
		for (int i = 0; i < goodsSpecificationDetails.size(); i++) {
			if (i < goodsSpecificationDetails.size() - 1) {
				identifierList += goodsSpecificationDetails.get(i).getIdentifier() + ",";
			} else {
				identifierList += goodsSpecificationDetails.get(i).getIdentifier();
			}
		}
		// 如果有图片，则要批量删除图片
		List<String> goodsDisplayOldPicUrlList = new ArrayList<>();
		for (int i = 0; i < goodsDisplayPictureList.size(); i++) {
			if (goodsDisplayPictureList.get(i).getPicUrl() != null
					&& !"".equals(goodsDisplayPictureList.get(i).getPicUrl())) {
				goodsDisplayOldPicUrlList.add(goodsDisplayPictureList.get(i).getPicUrl());
			}
		}
		// 获取当前操作人的编号
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		// 往log表中插入操作数据
		insertLog(operateType, identifierList, Constants.OPERATE_DELETE, new Date(), userIdentifier);

		if (result > 0) {
			// 批量删除成功后，批量删除规格对应的展示图表信息
			goodsDisplayPictureService.deleteBatchByGoodsSpecificationDetailId(primaryKeys);
			// 删除图片
			if (goodsDisplayOldPicUrlList.size() > 0) {
				CommonMethod.deleteOldPic(request, folderName, goodsDisplayOldPicUrlList);
			}
			rmsg.put("success", true);
			rmsg.put("msg", Constants.DELE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.DELE_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 下架商品-------进销存操作商品的停用此时要把mis的该商品给下架
	 * @param request
	 * @param commoditySpecId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/offShelvesOperation", method = RequestMethod.POST)
	public JSONObject offShelvesOperation(HttpServletRequest request, Integer commoditySpecId) throws Exception {
		JSONObject rmsg = new JSONObject();
		GoodsSpecificationDetails goodsSpecificationDetails=new GoodsSpecificationDetails();
		goodsSpecificationDetails.setCommoditySpecificationId(commoditySpecId);
		goodsSpecificationDetails.setState(1);
		goodsSpecificationDetails.setOffShelvesTime(new Date());
		if(goodsSpecificationDetailsService.updateByCommoditySpecificationId(goodsSpecificationDetails)>0){
			rmsg.put("success", true);
		}
		rmsg.put("success", false);
		return rmsg;
	}

	/**
	 * 批量上架/下架操作
	 * 
	 * @param build
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/onOrOffShelvesOperation", method = RequestMethod.POST)
	public JSONObject onOrOffShelvesOperation(HttpServletRequest request, String onOrOff) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 从前台中获取选中的id
		String[] primaryKeys = request.getParameterValues("id");
		// 根据主键批量查询规格信息
		List<GoodsSpecificationDetails> goodsSpecificationDetails = goodsSpecificationDetailsService
				.selectBatchByPrimaryKey(primaryKeys);
		String date_String = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<Integer> shouldOperationPrimaryKeys = new ArrayList<Integer>();
		List<String> isDeleteValues = new ArrayList<String>();//进销存中被停用的商品
		// 保存往Log表中添加的操作对象的编号
		String identifierList = "";
		for (int i = 0; i < goodsSpecificationDetails.size(); i++) {
			//获取购销存中该商品是否已停用
			Integer isDelete=goodsSpecificationDetailsService.selectjlgxcCommoditySpecificationIsDeleteByPrimaryKey(goodsSpecificationDetails.get(i).getCommoditySpecificationId());
			
			
			// 如果是上架操作，只会操作不是上架中且没被停用的数据。
			if ("on".equals(onOrOff) && (goodsSpecificationDetails.get(i).getState() == null
					|| goodsSpecificationDetails.get(i).getState() == 1)) {
				//已被下架
				if(isDelete==1){
					isDeleteValues.add(goodsSpecificationDetails.get(i).getIdentifier());
				}
				else{
					shouldOperationPrimaryKeys.add(goodsSpecificationDetails.get(i).getId());
					
					if (i < goodsSpecificationDetails.size() - 1) {
						identifierList += goodsSpecificationDetails.get(i).getIdentifier() + ",";
					} else {
						identifierList += goodsSpecificationDetails.get(i).getIdentifier();
					}
				}
				
			}
			// 如果是下架操作，只会操作上架中的数据。
			else if ("off".equals(onOrOff) && (goodsSpecificationDetails.get(i).getState() != null
					&& goodsSpecificationDetails.get(i).getState() == 0)) {
				shouldOperationPrimaryKeys.add(goodsSpecificationDetails.get(i).getId());
				
				if (i < goodsSpecificationDetails.size() - 1) {
					identifierList += goodsSpecificationDetails.get(i).getIdentifier() + ",";
				} else {
					identifierList += goodsSpecificationDetails.get(i).getIdentifier();
				}
			}
		}
		int result = -1;
		if (shouldOperationPrimaryKeys.size() > 0) {
			result = goodsSpecificationDetailsService.updateBatchByPrimaryKey(shouldOperationPrimaryKeys, onOrOff,
					date_String);
		} else {
			rmsg.put("success", false);
			if(isDeleteValues.size()>0){
				String msg="";
				for(int i=0;i<isDeleteValues.size();i++){
					if(i==0){
						msg+="(";
					}
					if(i<isDeleteValues.size()-1){
						msg+=isDeleteValues.get(i)+",";
					}
					else{
						msg+=isDeleteValues.get(i) ;
					}
					if(i==isDeleteValues.size()-1){
						msg+=")";
					}
				}
				rmsg.put("msg", msg+"所选的商品在进销存系统中已被停用，暂不能上架。");
			}else{
				if ("on".equals(onOrOff)) {
					rmsg.put("msg", "所选的商品中，暂无需要上架的商品。");
				} else {
					rmsg.put("msg", "所选的商品中，暂无需要下架的商品。");
				}
			}
			

			return rmsg;
		}

		if (result > 0) {
			// 获取当前操作人的编号
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 往log表中插入操作数据
			insertLog(operateType, identifierList, Constants.OPERATE_UPDATE, new Date(), userIdentifier);
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 批量推荐操作
	 * 
	 * @param build
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/recomOperation", method = RequestMethod.POST)
	public JSONObject recomOperation(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 从前台中获取选中的id
		String[] primaryKeys = request.getParameterValues("id");
		// 根据主键批量查询规格信息
		List<GoodsSpecificationDetails> goodsSpecificationDetails = goodsSpecificationDetailsService
				.selectBatchByPrimaryKey(primaryKeys);
		String date_String = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

		// 保存往Log表中添加的操作对象的编号
		String identifierList = "";
		List<Integer> goodsDetailPrimaryKeys = new ArrayList<Integer>();
		for (int i = 0; i < goodsSpecificationDetails.size(); i++) {
			if (i < goodsSpecificationDetails.size() - 1) {
				identifierList += goodsSpecificationDetails.get(i).getIdentifier() + ",";
			} else {
				identifierList += goodsSpecificationDetails.get(i).getIdentifier();
			}
			int h = 0;
			for (h = 0; h < goodsDetailPrimaryKeys.size(); h++) {
				// 判断是否有相同的商品规格id，若没有则进行添加操作
				if (goodsDetailPrimaryKeys.get(h) == goodsSpecificationDetails.get(i).getGoodsId()) {
					break;
				}
			}
			if (h == goodsDetailPrimaryKeys.size()) {
				goodsDetailPrimaryKeys.add(goodsSpecificationDetails.get(i).getGoodsId());
			}

		}
		int result = -1;
		if (goodsDetailPrimaryKeys.size() > 0) {
			result = goodsDetailsService.updateBatchByPrimaryKey(goodsDetailPrimaryKeys, date_String);
		} else {
			rmsg.put("success", false);
			rmsg.put("msg", "暂无需要推荐的商品。");
			return rmsg;
		}

		if (result > 0) {
			// 获取当前操作人的编号
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 往log表中插入操作数据
			insertLog(operateType, identifierList, Constants.OPERATE_UPDATE, new Date(), userIdentifier);
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 商品管理页面的导出操作
	 * 
	 * @param request
	 * @param response
	 * @param identifier
	 *            编号搜索框内容
	 * @param name
	 *            名称搜索框内容
	 * @param operatorIdentifier
	 *            添加人编号搜索框内容
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/exportGoodsSpecificationDetails")
	public boolean exportGoodsSpecificationDetails(HttpServletRequest request, HttpServletResponse response,
			String identifier, String name, String operatorName, String gxcGoodsStock, String oneOrTwo,
			String classificationId, String activityName, String operatorTime) throws Exception {

		String fileName = "商品信息";// 文档名称以及Excel里头部信息
		List<GoodsSpecificationDetails> gsdlist = goodsSpecificationDetailsService
				.selectGSDAndGDAndClassificationMsgToExport(URLDecoder.decode(identifier, "UTF-8"),
						URLDecoder.decode(name, "UTF-8"), URLDecoder.decode(operatorName, "UTF-8"),
						URLDecoder.decode(gxcGoodsStock, "UTF-8"), URLDecoder.decode(oneOrTwo, "UTF-8"),
						URLDecoder.decode(classificationId, "UTF-8"), URLDecoder.decode(activityName, "UTF-8"),
						URLDecoder.decode(operatorTime, "UTF-8"));
		List<String[]> dataList = new ArrayList<>();
		String[] title; // 保存Excel表头
		String[] value; // 保存要导出的内容
		// 搜索的有数据
		if (gsdlist.size() > 0) {
			// 保存Excel表头
			title = new String[12];
			title[0] = "编号";
			title[1] = "名称";
			title[2] = "分类";
			title[3] = "原始价格";
			title[4] = "价格";
			title[5] = "规格";
			title[6] = "库存";
			title[7] = "促销状态";
			title[8] = "状态";
			title[9] = "上/下架时间";
			title[10] = "创建人编号";
			title[11] = "添加时间";
			// 保存要导出的内容
			for (GoodsSpecificationDetails gsd : gsdlist) {
				value = new String[12];
				value[0] = gsd.getIdentifier();
				value[1] = gsd.getGoodsDetails().getName();
				value[2] = gsd.getGoodsDetails().getClassification().getParentName() + " "
						+ gsd.getGoodsDetails().getClassification().getName();
				value[3] = gsd.getOldPrice() + "";
				value[4] = gsd.getPrice() + "";
				value[5] = gsd.getSpecifications();
				if(gsd.getGxcGoodsStock()!=null){
					value[6] = gsd.getGxcGoodsStock() + "";
				}
				else{
					value[6] = "0";
				}
				
				value[7] = gsd.getParticipateActivities();
				if (gsd.getState() != null && gsd.getState() == 0) {
					value[8] = "上架中";
					if (gsd.getOnShelvesTime() == null) {
						value[9] = "";
					} else {
						value[9] = new SimpleDateFormat("yyyy-MM-dd").format(gsd.getOnShelvesTime());
					}

				} else if (gsd.getState() != null && gsd.getState() == 1) {
					value[8] = "已下架";
					if (gsd.getOffShelvesTime() == null) {
						value[9] = "";
					} else {
						value[9] = new SimpleDateFormat("yyyy-MM-dd").format(gsd.getOffShelvesTime());
					}

				} else {
					value[8] = "暂无状态";
					value[9] = "";
				}
				if (gsd.getOperatorIdentifier() != null && !"".equals(gsd.getOperatorIdentifier())) {
					if (gsd.getUser() != null) {
						value[10] = gsd.getOperatorIdentifier() + "(" + gsd.getUser().getName() + ")";
					} else {
						value[10] = gsd.getOperatorIdentifier();
					}

				} else {
					value[10] = "";
				}

				if (gsd.getOperatorTime() != null) {

					value[11] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(gsd.getOperatorTime());

				} else {
					value[11] = "";
				}

				dataList.add(value);
			}

		}
		// 没有搜索到数据
		else {
			title = new String[1];
			title[0] = Constants.NO_DATA_EXPORT;// 无数据提示
		}
		// 调用公共方法导出数据
		CommonMethod.exportData(request, response, fileName, title, dataList);
		return true;
	}

	/**
	 * 往Log表中添加日志信息
	 * 
	 * @param operateType
	 *            操作类型
	 * @param identifier
	 *            操作对象的编号
	 * @param operateMethod
	 *            操作的方法(添加/删除/修改)
	 * @param date
	 *            操作日期
	 * @param userIdentifier
	 *            操作人编号
	 * @throws Exception
	 */
	public void insertLog(Integer operateType, String identifier, String operateMethod, Date date,
			String userIdentifier) throws Exception {
		log = new Log();
		log.setOperateType(operateType);
		log.setOperateObject(identifier + "(" + operateMethod + ")");
		log.setOperateTime(date);
		log.setOperatorIdentifier(userIdentifier);
		logService.insert(log);
	}

	/**
	 * 获取所有商品信息
	 * 
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = "selectAllGoodsDetails", method = RequestMethod.POST)
	public List<GoodsDetails> selectAllGoodsDetails(int classificationId) {

		return goodsDetailsService.selectAllGoodsDetails(classificationId);
	}*/

}
