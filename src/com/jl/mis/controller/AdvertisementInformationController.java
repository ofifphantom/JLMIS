package com.jl.mis.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.model.Classification;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.HotCategoriesGood;
import com.jl.mis.model.Log;
import com.jl.mis.service.AdvertisementInformationService;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.HotCategoriesGoodService;
import com.jl.mis.service.LogService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 广告信息controller
 * 
 * @author 景雅倩
 * @date 2017-11-13 下午3:41:25
 * @Description TODO
 */
@Controller
@RequestMapping("/backgroundConfiguration/advertisement/advertisementInformation/")
public class AdvertisementInformationController extends BaseController {
	@Autowired
	private AdvertisementInformationService advertisementInformationService;

	@Autowired
	private LogService logService;

	@Autowired
	private HotCategoriesGoodService hotCategoriesGoodService;

	@Autowired
	private ClassificationService classificationService;

	// 保存广告图片的文件夹名
	private String folderName = "advertisementInformationPicture";

	/**
	 * 进入广告管理页面
	 * 
	 * @param request
	 * @param page
	 *            0：开屏 1：首页广告 2：分类广告 3：限时抢购 4：首页分类
	 * @return 页面路径
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, int page) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}

		switch (page) {
		case 0:
			return "";

		case 1:
			return "";

		case 2:
			return "";

		case 3:
			return "";

		case 4:
			return "";

		default:
			return "";
		}

	}

	/**
	 * 广告信息dataTables
	 * 
	 * @param request
	 * @param identifier
	 *            编号
	 * @param name
	 *            名称
	 * @param operatorIdentifier
	 *            操作人
	 * @param type
	 *            广告类型 （必须） 0：开屏 1：首页广告 2：分类广告 3：限时抢购 4：首页分类
	 * 
	 * @return
	 */
	@RequestMapping(value = "dataTables", method = RequestMethod.POST)
	@ResponseBody
	public DataTables dataTables(HttpServletRequest request, String identifier,
			String name, String operatorIdentifier, int type,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);
		return advertisementInformationService.getDataTables(dataTables,
				identifier, name, operatorIdentifier, type, operatorTime);

	}

	/**
	 * 生效广告（仅一个有效）
	 * 
	 * @param request
	 * @param advertisementInformation
	 *            （包含id type identifier）
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "updateOneAdToEffect", method = RequestMethod.POST)
	public JSONObject updateOneAdToEffect(HttpServletRequest request,
			AdvertisementInformation advertisementInformation) throws Exception {

		JSONObject rmsg = new JSONObject();
		if (advertisementInformationService
				.updateOneAdToEffect(advertisementInformation)) {

			// 插入日志
			Log log = new Log();
			// 操作类型
			log.setOperateType(Constants.TYPE_LOG_ADVERTISE);
			// 操作对象
			String operateObject = advertisementInformation.getIdentifier()
					+ "(" + Constants.OPERATE_UPDATE + ")";
			log.setOperateObject(operateObject);
			// 操作人
			String operatorIdentifier = GetSessionUtil
					.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			// 操作时间
			Date operateTime = new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);

			rmsg.put("success", true);
			rmsg.put("msg", Constants.EFFECT_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.EFFECT_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 生效广告（10个有效）首页广告
	 * 
	 * @param request
	 * @param advertisementInformation
	 *            （包含id type identifier effect）
	 * @return
	 * @throws Exception
	 */

	@ResponseBody
	@RequestMapping(value = "updateHomeAdToEffectOrUneffect", method = RequestMethod.POST)
	public JSONObject updateHomeAdToEffectOrUneffect(
			HttpServletRequest request,
			AdvertisementInformation advertisementInformation) throws Exception {
		JSONObject rmsg = new JSONObject();
		boolean result = false;
		if (advertisementInformation.getEffect() == 0) {// 使生效
			// 查询正在生效的首页广告有几条
			int number = advertisementInformationService
					.selectNumOfHomeAdEffect();
			if (number >= 10) {// 当前有10条广告正在生效 生效失败
				rmsg.put("success", false);
				rmsg.put("msg", "生效广告数已达10条,生效失败!");
				return rmsg;
			} else {// 可以生效
				result = advertisementInformationService
						.updateEffectByIdAndType(advertisementInformation);
				rmsg.put("msg", Constants.EFFECT_SUCCESS_MSG);
			}

		} else if (advertisementInformation.getEffect() == 1) {// 使失效
			result = advertisementInformationService
					.updateEffectByIdAndType(advertisementInformation);
			rmsg.put("msg", "操作成功,已失效!");
		}

		if (result) {

			// 插入日志
			Log log = new Log();
			// 操作类型
			log.setOperateType(Constants.TYPE_LOG_ADVERTISE);
			// 操作对象
			String operateObject = advertisementInformation.getIdentifier()
					+ "(" + Constants.OPERATE_UPDATE + ")";
			log.setOperateObject(operateObject);
			// 操作人
			String operatorIdentifier = GetSessionUtil
					.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			// 操作时间
			Date operateTime = new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);

			rmsg.put("success", true);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.EFFECT_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 导出活动信息
	 * 
	 * @param request
	 * @param response
	 * @param type
	 *            广告类型 （0：开屏，1：首页广告，2：分类广告，3：限时抢购，4：热门分类）
	 * @param identifier
	 *            编号
	 * @param name
	 *            名称
	 * @param operatorIdentifier
	 *            操作人
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exportAdvertisementInformation")
	public boolean exportAdvertisementInformation(HttpServletRequest request,
			HttpServletResponse response, int type, String identifier,
			String name, String operatorIdentifier,String operatorTime) throws Exception {

		identifier = URLDecoder.decode(identifier, "UTF-8");
		name = URLDecoder.decode(name, "UTF-8");
		operatorIdentifier = URLDecoder.decode(operatorIdentifier, "UTF-8");
		operatorTime = URLDecoder.decode(operatorTime, "UTF-8");
		String fileName = "";// 文档名称以及Excel里头部信息
		List<AdvertisementInformation> advertisementInformations = advertisementInformationService
				.selectMsgOrderBySearchMsg(type, identifier, name,
						operatorIdentifier,operatorTime);
		List<String[]> dataList = new ArrayList<>();
		String[] title; // 保存Excel表头
		String[] value; // 保存要导出的内容

		switch (type) {
		case 0:// 开屏
			fileName = "开屏广告";// 文档名称以及Excel里头部信息
			break;

		case 1:// 首页广告
			fileName = "首页广告";// 文档名称以及Excel里头部信息
			break;
		case 2:// 分类广告
			fileName = "分类广告";// 文档名称以及Excel里头部信息
			break;
		case 3:// 限时抢购
			fileName = "限时抢购";// 文档名称以及Excel里头部信息
			break;
		case 4:// 热门分类
			fileName = "热门分类";// 文档名称以及Excel里头部信息
			break;
		default:
			break;
		}

		// 搜索的有数据
		if (advertisementInformations.size() > 0) {

			// 需导出的表头与信息
			if (type == 3) {// 限时抢购
				// 保存Excel表头
				title = new String[7];
				title[0] = "编号";
				title[1] = "名称";
				title[2] = "链接类型";
				title[3] = "链接参数信息";
				title[4] = "生效时间";
				title[5] = "创建人";
				title[6] = "创建时间";
				// 保存要导出的内容
				for (AdvertisementInformation c : advertisementInformations) {
					value = new String[7];
					value[0] = c.getIdentifier();
					value[1] = c.getName();
					switch (c.getUrlType()) {
					case 0:
						value[2] = "商品";
						value[3] = c.getGoodsDetails().getName()
								+ "("
								+ c.getGoodsDetails().getClassification()
										.getParentName()
								+ "/"
								+ c.getGoodsDetails().getClassification()
										.getName() + ")";
						break;
					case 1:
						value[2] = "活动";
						value[3] = c.getActivityInformation().getName() + "("
								+ c.getActivityInformation().getIdentifier()
								+ ")";
						break;
					default:
						break;
					}

					if (c.getEffectTime() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd");
						value[4] = sdf.format(c.getEffectTime());
					} else {
						value[4] = "";
					}

					
					
					if(c.getOperatorIdentifier()!=null && c.getOperatorIdentifier()!="") {
						value[5] = c.getOperatorIdentifier() + "("
								+ c.getUser().getName() + ")";
					}else {
						value[5] = "";
					}
					
					
					value[6] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(c.getOperatorTime());

					dataList.add(value);
				}
			} else {
				// 保存Excel表头
				title = new String[6];
				title[0] = "编号";
				title[1] = "名称";
				title[2] = "链接类型";
				title[3] = "链接参数信息";
				title[4] = "创建人";
				title[5] = "创建时间";
				// 保存要导出的内容
				for (AdvertisementInformation c : advertisementInformations) {
					value = new String[6];
					value[0] = c.getIdentifier();
					value[1] = c.getName();
					switch (c.getUrlType()) {
					case 0:
						value[2] = "商品";
						value[3] = c.getGoodsDetails().getName()
								+ "("
								+ c.getGoodsDetails().getClassification()
										.getParentName()
								+ "/"
								+ c.getGoodsDetails().getClassification()
										.getName() + ")";
						break;
					case 1:
						value[2] = "活动";
						value[3] = c.getActivityInformation().getName() + "("
								+ c.getActivityInformation().getIdentifier()
								+ ")";
						break;
					default:
						break;
					}

					if(c.getOperatorIdentifier()!=null && c.getOperatorIdentifier()!="") {
						value[4] = c.getOperatorIdentifier() + "("
								+ c.getUser().getName() + ")";
					}else {
						value[4] = "";
					}
					value[5] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(c.getOperatorTime());

					dataList.add(value);
				}
			}

		} else {// 没有搜索到数据
			title = new String[1];
			title[0] = Constants.NO_DATA_EXPORT;// 无数据提示
		}
		// 调用公共方法导出数据
		CommonMethod.exportData(request, response, fileName, title, dataList);
		return true;
	}

	/**
	 * 删除（根据id列表删除活动信息）
	 * 
	 * @param request
	 * @param ids
	 *            id数组
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteAdvertisementInformationByIds", method = RequestMethod.POST)
	public JSONObject deleteAdvertisementInformationByIds(
			HttpServletRequest request, String[] ids) throws Exception {

		JSONObject rmsg = new JSONObject();

		ArrayList<Integer> list = new ArrayList<Integer>();
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				int int_id = Integer.valueOf(id);
				list.add(int_id);

			}
		}
	

		// 获取将要删除的数据的信息 并保存将要删除的对象编号
		List<AdvertisementInformation> advertisementInformations = advertisementInformationService
				.getAdvertisementInformationByIds(list);
	
		// 判断有没有正在生效的广告 如果有 返回优惠券编号
		String str = "";
		List<String> oldPicUrlList=new ArrayList<>();
		for (int i = 0; i < advertisementInformations.size(); i++) {
			if (advertisementInformations.get(i).getEffect() == 0) {// 要删除的对象中有正在生效的广告
																	// 记录编号
				str += advertisementInformations.get(i).getIdentifier() + ",";
			}
			//删除服务器文件夹中的旧图片
			if(advertisementInformations.get(i).getPicUrl()!=null&&!"".equals(advertisementInformations.get(i).getPicUrl())){
				oldPicUrlList.add(advertisementInformations.get(i).getPicUrl());
			}
		}
		if (!"".equals(str)) {// 说明要删除的对象中有正在生效的广告
			str = str.substring(0, str.length() - 1);// 去除最后一个","
			rmsg.put("success", false);
			rmsg.put("msg", "要删除的广告(" + str + ")正在生效,删除失败！");
			return rmsg;
		}
		
		
		if (advertisementInformationService
				.deleteAdvertisementInformationByIds(list)) {
			
			//删除服务器图片文件夹中的相应图片
			if(oldPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
			}
			
			
			// 插入日志
			Log log = new Log();
			// 操作类型
			log.setOperateType(Constants.TYPE_LOG_ADVERTISE);
			// 操作对象
			String operateObject = "";

			for (int i = 0; i < advertisementInformations.size(); i++) {
				operateObject += advertisementInformations.get(i)
						.getIdentifier();
				if (i < advertisementInformations.size() - 1) {
					operateObject += ",";
				}
			}
			operateObject += "(" + Constants.OPERATE_DELETE + ")";

			log.setOperateObject(operateObject);
			// 操作人
			String operatorIdentifier = GetSessionUtil
					.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			// 操作时间
			Date operateTime = new Date();
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
	 * 添加广告信息
	 * 
	 * @param file
	 *            图片
	 * @param request
	 *            前台传入得数据
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addAdvertisementInformation", method = RequestMethod.POST)
	public JSONObject addAdvertisementInformation(
			@RequestParam(value = "previewImg", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		AdvertisementInformation advertisementInformation = new AdvertisementInformation();
		// 自动生成编号
		String identifier = SundryCodeUtil
				.getPosCode(Constants.CODE_ADVERTISEMENT);
		advertisementInformation.setIdentifier(identifier);
		// 广告类型
		int type = Integer.parseInt(request.getParameter("type"));
		advertisementInformation.setType(type);
		// 名称
		advertisementInformation.setName(request.getParameter("name"));
		int UrlType = Integer.parseInt(request.getParameter("urlType"));
		// 链接类型
		advertisementInformation.setUrlType(UrlType);
		switch (UrlType) {
		case 0:// 商品
				// 链接参数id
			advertisementInformation.setUrlParameterId(Integer.parseInt(request
					.getParameter("goodsId")));
			break;
		case 1:// 活动
				// 链接参数id
			advertisementInformation.setUrlParameterId(Integer.parseInt(request
					.getParameter("activityId")));
			break;

		default:
			break;
		}
		if (type == 3) {// 限时抢购
			// 生效时间（限时抢购）
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date effectTime = (Date) sdf.parse(request.getParameter(
					"effectTime").toString());
			advertisementInformation.setEffectTime(effectTime);

		}
		// 是否生效 默认不生效
		advertisementInformation.setEffect(1);

		// 添加创建时间
		Date operateTime = new Date();
		advertisementInformation.setOperatorTime(operateTime);
		// 添加创建人编号，从session中获取
		String operatorIdentifier = GetSessionUtil
				.GetSessionUserIdentifier(request);
		advertisementInformation.setOperatorIdentifier(operatorIdentifier);

		// 存储新图片，并保存路径
		if (file.getOriginalFilename() != null
				&& !"".equals(file.getOriginalFilename())) {
			advertisementInformation.setPicUrl(CommonMethod.savePic(request,
					folderName, file));
		}

		JSONObject rmsg = new JSONObject();
		if (advertisementInformationService
				.insertSelective(advertisementInformation) > 0) {
			if (type == 3
					&& "saveAndEffect".equals(request.getParameter("flag"))) {// 限时抢购
																				// 并且点击了"提交并生效"按钮
				advertisementInformation.setEffect(0);// 设置是否有效为有效
				int id = advertisementInformationService
						.selectByIdentifier(identifier);// 获取要修改对象的id
				advertisementInformation.setId(id);// 设置id
				advertisementInformationService
						.updateOneAdToEffect(advertisementInformation);// 将此广告设置为有效
																		// 并将其他广告设置为无效
			}

			// 插入日志
			Log log = new Log();
			log.setOperateType(Constants.TYPE_LOG_ADVERTISE);
			String operateObject = identifier + "(" + Constants.OPERATE_INSERT
					+ ")";
			log.setOperateObject(operateObject);
			log.setOperatorIdentifier(operatorIdentifier);
			log.setOperateTime(operateTime);
			logService.insert(log);

			// 往前台返回结果集
			rmsg.put("success", true);
			rmsg.put("msg", Constants.SAVE_SUCCESS_MSG);
			return rmsg;
		}

		rmsg.put("success", false);
		rmsg.put("msg", Constants.SAVE_FAILURE_MSG);

		return rmsg;
	}

	/**
	 * 生效热门分类广告信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "effectHotCategories", method = RequestMethod.POST)
	public JSONObject effectHotCategories(
			HttpServletRequest request,
			@RequestParam(value = "previewImg0", required = false) MultipartFile saleFile,
			@RequestParam(value = "previewImg1", required = false) MultipartFile newFile,
			@RequestParam(value = "previewImg2", required = false) MultipartFile hotFile,
			@RequestParam(value = "previewImg3", required = false) MultipartFile presaleFile)
			throws Exception {
		
		//获取原有的广告信息
		AdvertisementInformation saleAi=advertisementInformationService.selectByAdName(Constants.AD_SALE);
		AdvertisementInformation newAi=advertisementInformationService.selectByAdName(Constants.AD_NEW);	
		AdvertisementInformation hotAi=advertisementInformationService.selectByAdName(Constants.AD_HOT);	
		AdvertisementInformation presaleAi=advertisementInformationService.selectByAdName(Constants.AD_PRESALE);
		
		//获取原有的图片地址
		String oldSalePicUrl=null;
		if(saleAi!=null){
			oldSalePicUrl=saleAi.getPicUrl();
		}
		String oldNewPicUrl=null;
		if(newAi!=null){
			oldNewPicUrl=newAi.getPicUrl();
		}
		String oldHotPicUrl=null;
		if(hotAi!=null){
			oldHotPicUrl=hotAi.getPicUrl();
		}
		String oldPresalePicUrl=null;
		if(presaleAi!=null){
			oldPresalePicUrl=presaleAi.getPicUrl();
		}
		
		
		
		
		
		
		String identifier = SundryCodeUtil
				.getPosCode(Constants.CODE_ADVERTISEMENT);
		String baseIdentifier = identifier
				.substring(0, identifier.length() - 1);

		// 火热促销
		AdvertisementInformation saleAd = new AdvertisementInformation();
		// 编号
		String saleIdentifier = baseIdentifier + 0;
		saleAd.setIdentifier(saleIdentifier);
		saleAd.setName(Constants.AD_SALE);// 名称

		// 图片上传	
		if (saleFile.getOriginalFilename() != null
				&& !"".equals(saleFile.getOriginalFilename())) {
			saleAd.setPicUrl(CommonMethod
					.savePic(request, folderName, saleFile));
		}else{//上传图片为空 设置picUrl为原来的图片路径
			saleAd.setPicUrl(oldSalePicUrl);	
		}
		// 所属商品的id和state
		String saleGoods = request.getParameter("goodsidStr_sale");
		String saleState = request.getParameter("stateStr_sale");

		// 新品上架
		AdvertisementInformation newAd = new AdvertisementInformation();
		// 编号
		String newIdentifier = baseIdentifier + 1;
		newAd.setIdentifier(newIdentifier);
		newAd.setName(Constants.AD_NEW);// 名称
		// 图片上传
		if (newFile.getOriginalFilename() != null
				&& !"".equals(newFile.getOriginalFilename())) {
			newAd.setPicUrl(CommonMethod.savePic(request, folderName, newFile));
		}else{//上传图片为空 设置picUrl为原来的图片路径
			newAd.setPicUrl(oldNewPicUrl);	
		}
		// 所属商品的id和state
		String newGoods = request.getParameter("goodsidStr_new");
		String newState = request.getParameter("stateStr_new");

		// 君霖热卖
		AdvertisementInformation hotAd = new AdvertisementInformation();
		// 编号
		String hotIdentifier = baseIdentifier + 2;
		hotAd.setIdentifier(hotIdentifier);
		hotAd.setName(Constants.AD_HOT);// 名称
		// 图片上传
		if (hotFile.getOriginalFilename() != null
				&& !"".equals(hotFile.getOriginalFilename())) {
			hotAd.setPicUrl(CommonMethod.savePic(request, folderName, hotFile));
		}else{//上传图片为空 设置picUrl为原来的图片路径
			hotAd.setPicUrl(oldHotPicUrl);	
		}
		// 所属商品的id和state
		String hotGoods = request.getParameter("goodsidStr_hot");
		String hotState = request.getParameter("stateStr_hot");

		// 爆品预售
		AdvertisementInformation presaleAd = new AdvertisementInformation();
		// 编号
		String presaleIdentifier = baseIdentifier + 3;
		presaleAd.setIdentifier(presaleIdentifier);
		presaleAd.setName(Constants.AD_PRESALE);// 名称
		// 图片上传
		if (presaleFile.getOriginalFilename() != null
				&& !"".equals(presaleFile.getOriginalFilename())) {
			presaleAd.setPicUrl(CommonMethod.savePic(request, folderName,
					presaleFile));
		}else{//上传图片为空 设置picUrl为原来的图片路径
			presaleAd.setPicUrl(oldPresalePicUrl);	
		}
		// 所属商品的id和state
		String presaleGoods = request.getParameter("goodsidStr_presale");
		String presaleState = request.getParameter("stateStr_presale");

		// 公共属性
		// 创建人
		String operatorIdentifier = GetSessionUtil
				.GetSessionUserIdentifier(request);
		// 创建时间
		Date operateTime = new Date();

		saleAd.setType(4);// 属于热门分类
		saleAd.setEffect(0);// 有效
		saleAd.setOperatorTime(operateTime);
		saleAd.setOperatorIdentifier(operatorIdentifier);

		newAd.setType(4);// 属于热门分类
		newAd.setEffect(0);// 有效
		newAd.setOperatorTime(operateTime);
		newAd.setOperatorIdentifier(operatorIdentifier);

		hotAd.setType(4);// 属于热门分类
		hotAd.setEffect(0);// 有效
		hotAd.setOperatorTime(operateTime);
		hotAd.setOperatorIdentifier(operatorIdentifier);

		presaleAd.setType(4);// 属于热门分类
		presaleAd.setEffect(0);// 有效
		presaleAd.setOperatorTime(operateTime);
		presaleAd.setOperatorIdentifier(operatorIdentifier);

		/*
		 * System.out.println("saleGoods: "+saleGoods);
		 * System.out.println("saleState: "+saleState);
		 * System.out.println("identifier: "+saleAd.getIdentifier());
		 * System.out.println("name: "+saleAd.getName());
		 * System.out.println("operatorIdentifier: "
		 * +saleAd.getOperatorIdentifier());
		 * System.out.println("operateTime: "+saleAd.getOperatorTime());
		 * System.out.println("salePicUrl: "+saleAd.getPicUrl());
		 * System.out.println("newPicUrl: "+newAd.getPicUrl());
		 */

		JSONObject rmsg = new JSONObject();
		if (advertisementInformationService.insertHotCategories(saleAd, newAd,
				hotAd, presaleAd)) {
			// 根据广告编号获取广告id
			int saleAdId = advertisementInformationService
					.selectByIdentifier(saleAd.getIdentifier());
			int newAdId = advertisementInformationService
					.selectByIdentifier(newAd.getIdentifier());
			int hotAdId = advertisementInformationService
					.selectByIdentifier(hotAd.getIdentifier());
			int presaleAdId = advertisementInformationService
					.selectByIdentifier(presaleAd.getIdentifier());
			
			// 不为空说明有新的图片需要重新上传,如果有旧图片则需要删除旧的图片
			if (saleFile.getOriginalFilename() != null && !"".equals(saleFile.getOriginalFilename())){
				
				if(oldSalePicUrl!=null){
					List<String> oldPicUrlList=new ArrayList<>();
					oldPicUrlList.add(oldSalePicUrl);
					CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
				}
				
			}
			if (newFile.getOriginalFilename() != null && !"".equals(newFile.getOriginalFilename())){
				
				if(oldNewPicUrl!=null){
					List<String> oldPicUrlList=new ArrayList<>();
					oldPicUrlList.add(oldNewPicUrl);
					CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
				}
				
			}
			if (hotFile.getOriginalFilename() != null && !"".equals(hotFile.getOriginalFilename())){
				
				if(oldHotPicUrl!=null){
					List<String> oldPicUrlList=new ArrayList<>();
					oldPicUrlList.add(oldHotPicUrl);
					CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
				}
				
			}
			if (presaleFile.getOriginalFilename() != null && !"".equals(presaleFile.getOriginalFilename())){
				
				if(oldPresalePicUrl!=null){
					List<String> oldPicUrlList=new ArrayList<>();
					oldPicUrlList.add(oldPresalePicUrl);
					CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
				}
				
			}
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("saleGoods", saleGoods);
			map.put("saleState", saleState);
			map.put("saleAdId", saleAdId);

			map.put("newGoods", newGoods);
			map.put("newState", newState);
			map.put("newAdId", newAdId);

			map.put("hotGoods", hotGoods);
			map.put("hotState", hotState);
			map.put("hotAdId", hotAdId);

			map.put("presaleGoods", presaleGoods);
			map.put("presaleState", presaleState);
			map.put("presaleAdId", presaleAdId);

			// 添加热门分类商品表
			hotCategoriesGoodService.insertHotCategoriesGoods(map);

			// 插入日志
			Log log = new Log();
			log.setOperateType(Constants.TYPE_LOG_ADVERTISE);
			String operateObject = baseIdentifier + "0," + baseIdentifier
					+ "1," + baseIdentifier + "2," + baseIdentifier + "3("
					+ Constants.OPERATE_INSERT + ")";
			log.setOperateObject(operateObject);
			log.setOperatorIdentifier(operatorIdentifier);
			log.setOperateTime(operateTime);
			logService.insert(log);

			rmsg.put("success", true);
			rmsg.put("msg", Constants.EFFECT_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.EFFECT_FAILURE_MSG);
		return rmsg;

	}

	/**
	 * 获取获取页面所需信息（商品列表和广告图片）
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getMsgForPage", method = RequestMethod.POST)
	public JSONObject getMsgForPage(HttpServletRequest request) {

		JSONObject object=null;

		// 获取广告信息
		AdvertisementInformation saleAd = advertisementInformationService.selectByAdName(Constants.AD_SALE), 
				newAd = advertisementInformationService.selectByAdName(Constants.AD_NEW), 
				hotAd = advertisementInformationService.selectByAdName(Constants.AD_HOT), 
				presaleAd = advertisementInformationService.selectByAdName(Constants.AD_PRESALE);
		if(saleAd!=null && newAd!=null && hotAd!=null && presaleAd!=null){
			object = new JSONObject();
			// 获取图片信息
			String saleFile = saleAd.getPicUrl(), newFile = newAd.getPicUrl(), hotFile = hotAd
					.getPicUrl(), presaleFile = presaleAd.getPicUrl();
			// 向返回结果中加入图片信息
			object.put("saleFile", saleFile);
			object.put("newFile", newFile);
			object.put("hotFile", hotFile);
			object.put("presaleFile", presaleFile);

			
			
			// 商品信息处理
			
			int saleAdId = saleAd.getId(), 
				newAdId = newAd.getId(), 
				hotAdId = hotAd.getId(), 
				presaleAdId = presaleAd.getId();
			JSONObject saleObject = this.getGoodsInfoByAdId(saleAdId);
			JSONObject newObject = this.getGoodsInfoByAdId(newAdId);
			JSONObject hotObject = this.getGoodsInfoByAdId(hotAdId);
			JSONObject presaleObject = this.getGoodsInfoByAdId(presaleAdId);
			object.put("saleObject", saleObject);
			object.put("newObject", newObject);
			object.put("hotObject", hotObject);
			object.put("presaleObject", presaleObject);
			
		}

		return object;
		
		
	}
	
	public JSONObject getGoodsInfoByAdId(int AdvertisementInformationId){
		
		JSONObject object = new JSONObject();
		// 商品信息处理
		HotCategoriesGood hcg = null;
		GoodsDetails gd = null;
		Classification c = null;

		// 获取商品信息
		List<HotCategoriesGood> hotCategoriesGoodsList;
		JSONArray dataJsonArray = new JSONArray();
		JSONArray checkGoods1 = new JSONArray();
		JSONArray checkGoods2 = new JSONArray();
		JSONArray checkGoods3 = new JSONArray();

		String hotCategoriesGoodsName = "";
		int hotCategoriesGoodsState = -1, hotCategoriesGoodsId = -1;
		// 火热促销
		hotCategoriesGoodsList = hotCategoriesGoodService
				.selectByAdvertisementInformationId(AdvertisementInformationId);// 从hot_categories_good表中查询该广告下的所有商品信息
		for (int i = 0; i < hotCategoriesGoodsList.size(); i++) {
			int state = hotCategoriesGoodsList.get(i).getState();// 1：商品 2：分类
			int goodsId = hotCategoriesGoodsList.get(i).getGoodsId();

			JSONObject hotCategoriesGoodsJsonObject = new JSONObject();

			JSONObject obj = new JSONObject();
			// 获取商品列表信息
			switch (state) {

			case 1:// 商品
				hcg = hotCategoriesGoodService
						.getMsgByAdvertisementInformationId1(AdvertisementInformationId, goodsId);// select
																				// gd.name
																				// ,gd.id,gd.classification_id
				gd = hcg.getGoodsDetails();
				hotCategoriesGoodsName = gd.getName();
				hotCategoriesGoodsId = gd.getId();
				hotCategoriesGoodsState = 1;

				int classification_id1 = gd.getClassificationId();
				Classification classification1 = classificationService
						.selectByPrimaryKey(classification_id1);
				obj.put("goodsid", hotCategoriesGoodsId);
				obj.put("classificationTwoid", classification1.getId());
				obj.put("classificationOneid", classification1.getParentId());
				checkGoods1.add(obj);
				break;
			case 2:// 分类
				hcg = hotCategoriesGoodService
						.getMsgByAdvertisementInformationId2(AdvertisementInformationId, goodsId);// select
																				// c.name,c.id,c.parent_id
				c = hcg.getClassification();
				hotCategoriesGoodsName = c.getName();
				hotCategoriesGoodsId = c.getId();

				
				if (c.getParentId() == 0) {
					hotCategoriesGoodsState = 3;

					obj.put("classificationOneid", hotCategoriesGoodsId);
					checkGoods3.add(obj);
				} else {
					hotCategoriesGoodsState = 2;

					obj.put("classificationTwoid", hotCategoriesGoodsId);
					obj.put("classificationOneid", c.getParentId());
					checkGoods2.add(obj);
				}

				break;
			default:
				break;
			}

			hotCategoriesGoodsJsonObject.put("name", hotCategoriesGoodsName);
			hotCategoriesGoodsJsonObject.put("id", hotCategoriesGoodsId);
			hotCategoriesGoodsJsonObject.put("state", hotCategoriesGoodsState);
			dataJsonArray.add(hotCategoriesGoodsJsonObject);
		}
		object.put("dataJsonArray", dataJsonArray);
		object.put("checkGoods1", checkGoods1);
		object.put("checkGoods2", checkGoods2);
		object.put("checkGoods3", checkGoods3);
		
		return object;
	}
	
}
