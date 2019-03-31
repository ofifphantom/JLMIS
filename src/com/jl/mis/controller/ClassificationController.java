package com.jl.mis.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.Classification;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.Log;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.GoodsDetailsService;
import com.jl.mis.service.LogService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.SundryCodeUtil;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.Number;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月6日 下午3:25:18
 * @Description 分类类型表(一二级分类)Controller
 *
 */
@Controller
@RequestMapping("/backgroundConfiguration/classification/classification")
public class ClassificationController extends BaseController {

	// 当前类操作的类型(往log日志表中存储)
	private int operateType = Constants.TYPE_LOG_GOODS;
	// 保存二级分类图片的文件夹名
	private String folderName = "TwoClassificationPicture";
	// 声明Log类
	Log log;

	@Autowired
	private ClassificationService classificationService;
	@Autowired
	private LogService logService;
	@Autowired
	private GoodsDetailsService goodsDetailsService;

	/**
	 * 进入分类类型表(一二级分类)页面 page 1:一级页面 2：二级页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request, int page) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}
		if (page == 1) {
			return "进入一级页面";
		} else {
			return "进入二级页面";
		}
	}

	/**
	 * 从数据库中获取分类信息,根据sort排序
	 * 
	 * @param request
	 * @param oneOrTwo
	 *            一二级分类的标识符
	 */
	@ResponseBody
	@RequestMapping(value = "getManagerMsg")
	public DataTables getManagerMsg(HttpServletRequest request, String oneOrTwo, String identifier, String name,
			String operatorName,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);

		return classificationService.getManagerMsg(dataTables, oneOrTwo, identifier, name, operatorName,operatorTime);
	}

	/**
	 * 添加一级分类信息
	 * 
	 * @param request
	 * @param c
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/addOneClassificationMsg", method = RequestMethod.POST)
	public JSONObject addOneClassificationMsg(@RequestParam(value = "previewImg", required = false) MultipartFile file,HttpServletRequest request, Classification c) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 自动生成编号
		c.setIdentifier(SundryCodeUtil.getPosCode(Constants.CODE_ONECLASSIFICATION));
		// 添加操作时间
		Date date = new Date();
		c.setOperatorTime(date);
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		c.setOperatorIdentifier(userIdentifier);
		// 默认一级分类的父级Id为0
		c.setParentId(0);
		// 从前台根据name名获取数据
		c.setKeyWord(request.getParameter("key_word"));
		c.setName(request.getParameter("name"));
		// 获取数据库中存储的一级分类里最大的sort值
		int sort = 0;
		if (classificationService.selectMaxSort("one") != null) {
			sort = classificationService.selectMaxSort("one");
			c.setSort(sort + 1);
		} else {
			sort = 1;
			c.setSort(sort);
		}
		// 存储新图片，并保存路径
		if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
			c.setPicUrl(CommonMethod.savePic(request, folderName, file));
		}

		if (classificationService.insert(c) > 0) {
			// 往log表中插入操作数据
			insertLog(operateType, c.getIdentifier(), Constants.OPERATE_INSERT, date, userIdentifier);
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
	 * 添加二级分类的信息
	 * 
	 * @param file
	 *            图片
	 * @param request
	 *            前台传入得数据
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addTwoClassificationMsg", method = RequestMethod.POST)
	public JSONObject addTwoClassificationMsg(
			@RequestParam(value = "previewImg", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		Classification classification = new Classification();
		// 自动生成编号
		classification.setIdentifier(SundryCodeUtil.getPosCode(Constants.CODE_TWOCLASSIFICATION));
		// 二级分类的父级Id为一级分类的id
		classification.setParentId(Integer.parseInt(request.getParameter("parentId")));
		// 从前台根据name名获取数据
		classification.setKeyWord(request.getParameter("key_word"));
		classification.setName(request.getParameter("name"));
		// 获取数据库中存储的一级分类里最大的sort值
		int sort = 0;
		if (classificationService.selectMaxSort("two") != null) {
			sort = classificationService.selectMaxSort("two");
			classification.setSort(sort + 1);
		} else {
			sort = 1;
			classification.setSort(sort);
		}
		// 添加操作时间
		Date date = new Date();
		classification.setOperatorTime(date);
		// 添加操作人编号，从session中获取
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		classification.setOperatorIdentifier(userIdentifier);

		// 存储新图片，并保存路径
		if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
			classification.setPicUrl(CommonMethod.savePic(request, folderName, file));
		}

		JSONObject rmsg = new JSONObject();
		if (classificationService.insert(classification) > 0) {
			// 往log表中插入操作数据
			insertLog(operateType, classification.getIdentifier(), Constants.OPERATE_INSERT, date, userIdentifier);
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
	 * 添加时判断需要添加的分类是否重复 根据name和一二级分类标识符进行查询
	 * 不同一级分类下的二级分类可以重复
	 * @param request
	 * @param name
	 *            分类名称
	 * @param parentId
	 *           父级ID
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByNameAndParentId", method = RequestMethod.POST)
	public JSONObject selectByNameAndParentId(HttpServletRequest request, String name, String parentId) throws Exception {
		JSONObject rmsg = new JSONObject();
		Classification classification = new Classification();
		classification = classificationService.selectByNameAndParentId(name, parentId);
		if (classification != null) {
			rmsg.put("success", false);
			if(parentId!=null&&!"".equals(parentId)){
				rmsg.put("msg", "此一级分类下的该分类名称已存在，请勿重复添加!");
			}
			else{
				rmsg.put("msg", "该分类名称已存在，请勿重复添加!");
			}
			
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 修改时判断需要添加的分类是否重复 根据name和一二级分类标识符进行查询
	 * 不同一级分类下的二级分类可以重复
	 * @param request
	 * @param Classification
	 *            分类信息
	 * @param parentId
	 *            父级id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByNameAndIdAndParentId", method = RequestMethod.POST)
	public JSONObject selectByNameAndIdAndParentId(HttpServletRequest request, Classification c, String parentId)
			throws Exception {
		JSONObject rmsg = new JSONObject();
		Classification classification = new Classification();
		classification = classificationService.selectByNameAndIdAndParentId(c.getName(), c.getId(), parentId);
		if (classification != null) {
			rmsg.put("success", false);
			if(parentId!=null&&!"".equals(parentId)){
				rmsg.put("msg", "此一级分类下的该分类名称已存在，请勿重复添加!");
			}
			else{
				rmsg.put("msg", "该分类名称已存在，请勿重复添加!");
			}
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 点击编辑时根据主键ID查询信息
	 * 
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selectById")
	public Classification selectById(HttpServletRequest request, String id) throws Exception {

		return classificationService.selectByPrimaryKey(Integer.parseInt(id));
	}

	/**
	 * 根据主键更新一级分类信息
	 * 
	 * @param Classification
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/updateOnePrimaryKey", method = RequestMethod.POST)
	public JSONObject updateOnePrimaryKey(HttpServletRequest request,@RequestParam(value = "previewImg", required = false) MultipartFile file) throws Exception {
		JSONObject rmsg = new JSONObject();
		Classification classification = new Classification();
		// 一级分类的父及id
		classification.setParentId(0);
		// 从前台根据name名获取数据
		classification.setId(Integer.parseInt(request.getParameter("primaryKey")));
		classification.setKeyWord(request.getParameter("key_word"));
		classification.setName(request.getParameter("name"));
		// 通过id获取当前这条分类信息，获取里面旧的图片地址与分类的编号(存入log日志表中)。
		Classification classificationMsgById = classificationService.selectByPrimaryKey(classification.getId());
		// 获取已存入的图片地址。
		String oldPicUrl = classificationMsgById.getPicUrl();

		// 不为空说明有新的图片需要重新上传
		if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
			// 调用方法上传图片并接受传回来的图片地址
			classification.setPicUrl(CommonMethod.savePic(request, folderName, file));
		}

		// 往数据库中根据id修改信息
		int result = classificationService.updateByPrimaryKeySelective(classification);
		if (result > 0) {
			// 不为空说明有新的图片需要重新上传,如果有旧图片则需要删除旧的图片
			if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
				// 如果有旧图片地址，调用方法删除旧图片
				if (oldPicUrl != null) {
					List<String> oldPicUrlList = new ArrayList<>();
					oldPicUrlList.add(oldPicUrl);
					CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
				}
			}
			// 获取当前操作人的编号
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 往log表中插入操作数据
			insertLog(operateType, classificationMsgById.getIdentifier(), Constants.OPERATE_UPDATE, new Date(),
					userIdentifier);

			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 根据主键更新二级分类信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateTwoPrimaryKey", method = RequestMethod.POST)
	public JSONObject updateTwoPrimaryKey(
			@RequestParam(value = "previewImg", required = false) MultipartFile file,
			HttpServletRequest request) throws Exception {
		Classification classification = new Classification();
		// 二级分类的父级Id为一级分类的id
		classification.setParentId(Integer.parseInt(request.getParameter("parentId")));
		// 从前台根据name名获取数据
		classification.setId(Integer.parseInt(request.getParameter("primaryKey")));
		classification.setKeyWord(request.getParameter("key_word"));
		classification.setName(request.getParameter("name"));

		// 通过id获取当前这条分类信息，获取里面旧的图片地址与分类的编号(存入log日志表中)。
		Classification classificationMsgById = classificationService.selectByPrimaryKey(classification.getId());
		// 获取已存入的图片地址。
		String oldPicUrl = classificationMsgById.getPicUrl();

		// 不为空说明有新的图片需要重新上传
		if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
			// 调用方法上传图片并接受传回来的图片地址
			classification.setPicUrl(CommonMethod.savePic(request, folderName, file));
		}

		JSONObject rmsg = new JSONObject();
		// 往数据库中根据id修改信息
		int result = classificationService.updateByPrimaryKeySelective(classification);
		if (result > 0) {
			// 不为空说明有新的图片需要重新上传,如果有旧图片则需要删除旧的图片
			if (file.getOriginalFilename() != null && !"".equals(file.getOriginalFilename())) {
				// 如果有旧图片地址，调用方法删除旧图片
				if (oldPicUrl != null) {
					List<String> oldPicUrlList = new ArrayList<>();
					oldPicUrlList.add(oldPicUrl);
					CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
				}
			}
			// 获取当前操作人的编号
			String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			// 往log表中插入操作数据
			insertLog(operateType, classificationMsgById.getIdentifier(), Constants.OPERATE_UPDATE, new Date(),
					userIdentifier);

			rmsg.put("success", true);
			rmsg.put("msg", Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}

	/**
	 * 删除时需确认该分类下有无包含的商品
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selectByClassificationId", method = RequestMethod.POST)
	public JSONObject selectByClassificationId(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		Classification classification = new Classification();
		// 获取从前台传入的选择的分类id
		String[] primaryKeys = request.getParameterValues("id");
		//保存不能删除的分类名称
		ArrayList<String> notRemoveName = new ArrayList<>();
		//把不能删除的分类名称组合成string类型输出到前台
		String resultClassificationName = "";

		for (int i = 0; i < primaryKeys.length; i++) {

			// 通过主键查找商品表，看是否有数据。>0表示有数据
			if (goodsDetailsService.getGoodsListByClassificationId(Integer.parseInt(primaryKeys[i])).size() > 0) {
				// 通过主键查询分类数据。
				classification = classificationService.selectByPrimaryKey(Integer.parseInt(primaryKeys[i]));
				notRemoveName.add(classification.getName());
			}
		}
		for(int i=0;i<notRemoveName.size();i++){
			if(i==0){
				resultClassificationName+="(";
			}
			if(i<notRemoveName.size()-1){
				resultClassificationName+=notRemoveName.get(i)+"、";
			}
			else{
				resultClassificationName+=notRemoveName.get(i);
			}
			if(i==notRemoveName.size()-1){
				resultClassificationName+=")";
			}
			
		}
		if (!"".equals(resultClassificationName)) {
			rmsg.put("success", false);
			rmsg.put("msg", resultClassificationName + "以上显示的分类名称下包含的有商品，暂不能删除!");
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}
	
	/**
	 * 删除一级分类时需确认该分类下有无包含二级分类
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selectTwoByOneClassificationId", method = RequestMethod.POST)
	public JSONObject selectTwoByOneClassificationId(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		Classification classification = new Classification();
		// 获取从前台传入的选择的分类id
		String[] primaryKeys = request.getParameterValues("id");
		//保存不能删除的分类名称
		ArrayList<String> notRemoveName = new ArrayList<>();
		//把不能删除的分类名称组合成string类型输出到前台
		String resultClassificationName = "";

		for (int i = 0; i < primaryKeys.length; i++) {

			// 通过一级分类id查找二级分类，看是否有数据。>0表示有数据
			if (classificationService.selectTwoByOneId(Integer.parseInt(primaryKeys[i])).size() > 0) {
				// 通过主键查询分类数据。
				classification = classificationService.selectByPrimaryKey(Integer.parseInt(primaryKeys[i]));
				notRemoveName.add(classification.getName());
			}
		}
		for(int i=0;i<notRemoveName.size();i++){
			if(i==0){
				resultClassificationName+="(";
			}
			if(i<notRemoveName.size()-1){
				resultClassificationName+=notRemoveName.get(i)+"、";
			}
			else{
				resultClassificationName+=notRemoveName.get(i);
			}
			if(i==notRemoveName.size()-1){
				resultClassificationName+=")";
			}
			
		}
		if (!"".equals(resultClassificationName)) {
			rmsg.put("success", false);
			rmsg.put("msg", resultClassificationName + "以上显示的分类名称下包含的有二级分类，请先删除二级分类!");
			return rmsg;
		}
		rmsg.put("success", true);
		return rmsg;
	}
	
	/**
	 * 根据主键批量查询分类是否与广告或活动有关联。
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getClassificationIsContactToADOrACTByIds", method = RequestMethod.POST)
	public JSONObject getClassificationIsContactToADOrACTByIds(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		String resultMsg="";
		// 从前台中获取选中的id
		String[] primaryKeys = request.getParameterValues("id");
		List<Classification> isNotDeleteClassification=new ArrayList<>();
		isNotDeleteClassification=classificationService.getClassificationIsContactToADOrACTByIds(primaryKeys);
		
		for(int i=0;i<isNotDeleteClassification.size();i++){
			if(i==0){
				resultMsg+="(";
			}
			if(i<isNotDeleteClassification.size()-1){
				resultMsg+=isNotDeleteClassification.get(i).getName()+",";
			}
			else{
				resultMsg+=isNotDeleteClassification.get(i).getName();
			}
			if(i==isNotDeleteClassification.size()-1){
				resultMsg+=")";
			}
		}
		if(!"".equals(resultMsg)){
			
			rmsg.put("msg",resultMsg+"以上显示的分类参与了活动/广告,暂不能删除!");
			rmsg.put("success", false);
			return rmsg;
		}
		
		rmsg.put("success", true);
		return rmsg;
	}

	/**
	 * 根据主键(批量/单个)删除类型信息
	 * 
	 * @param build
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delBatchByPrimaryKey", method = RequestMethod.POST)
	public JSONObject delBatchByPrimaryKey(HttpServletRequest request) throws Exception {
		JSONObject rmsg = new JSONObject();
		// 从前台中获取选中的id
		String[] primaryKeys = request.getParameterValues("id");
		// 在删除前保存要删除的分类的信息
		List<Classification> classificationList = classificationService.selectBatchByPrimaryKey(primaryKeys);
		int result = classificationService.deleteBatchByPrimaryKey(primaryKeys);
		// 保存往Log表中添加的操作对象的编号
		String identifierList = "";
		//如果有图片，则要批量删除图片
		List<String> classificationOldPicUrlList = new ArrayList<>();
		for (int i = 0; i < classificationList.size(); i++) {
			if (i < classificationList.size() - 1) {
				identifierList += classificationList.get(i).getIdentifier() + ",";
			} else {
				identifierList += classificationList.get(i).getIdentifier();
			}
			if (classificationList.get(i).getPicUrl() != null && !"".equals(classificationList.get(i).getPicUrl())) {
				classificationOldPicUrlList.add(classificationList.get(i).getPicUrl());
			}

		}
		// 获取当前操作人的编号
		String userIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		// 往log表中插入操作数据
		insertLog(operateType, identifierList, Constants.OPERATE_DELETE, new Date(), userIdentifier);

		if (result > 0) {
			if (classificationOldPicUrlList.size() > 0) {
				CommonMethod.deleteOldPic(request, folderName, classificationOldPicUrlList);
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
	 * 页面中的上移/下移操作
	 * 
	 * @param request
	 * @param id
	 *            需要上移/下移的分类ID
	 * @param oneOrTwo
	 *            一二级分类的标识符
	 * @param upOrDown
	 *            上移/下移
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/moveUpOrDown")
	public JSONObject moveUpOrDown(HttpServletRequest request, String id, String oneOrTwo, String upOrDown)
			throws Exception {
		JSONObject rmsg = new JSONObject();
		// 需要进行操作的id
		int moveId = Integer.parseInt(id);
		// 需要进行操作的分类的上一个/下一个ID
		int upOrDownId = -1;
		// 需要进行操作的id移动后的sort
		int moveIdSort = -1;
		// 需要进行操作的分类的上一个/下一个ID移动后的ID
		int upOrDownIdSort = -1;
		// 获取从数据库中根据sort字段排好序的分类列表
		List<Classification> classificationList = classificationService.selectMsgOrderBySort(oneOrTwo);
		Classification classification = null;
		Classification c = null;
		// 上移
		if ("up".equals(upOrDown)) {
			// 获取第一个分类
			classification = classificationList.get(0);
			if (classification.getId() == Integer.parseInt(id)) {
				rmsg.put("success", false);
				rmsg.put("msg", "当前分类是第一个，不能进行上移操作。");
				return rmsg;
			}
			for (int i = 1; i <= classificationList.size(); i++) {
				c = classificationList.get(i);
				// 证明当前的分类需要上移
				if (c.getId() == moveId) {
					upOrDownId = classificationList.get(i - 1).getId();
					// 保存需要操作的分类移动后的sort
					moveIdSort = classificationList.get(i - 1).getSort();
					// 保存需要操作的分类上一个/下一个ID移动后的sort
					upOrDownIdSort = c.getSort();
					break;

				}
			}
		}
		// 下移
		else {
			// 获取最后一个分类
			classification = classificationList.get(classificationList.size() - 1);
			if (classification.getId() == Integer.parseInt(id)) {
				rmsg.put("success", false);
				rmsg.put("msg", "当前分类是最后一个，不能进行下移操作。");
				return rmsg;
			}
			for (int i = 0; i < classificationList.size() - 1; i++) {
				c = classificationList.get(i);
				// 证明当前的分类需要下移
				if (c.getId() == moveId) {
					upOrDownId = classificationList.get(i + 1).getId();
					// 保存需要操作的分类移动后的sort
					moveIdSort = classificationList.get(i + 1).getSort();
					// 保存需要操作的分类上一个/下一个ID移动后的sort
					upOrDownIdSort = c.getSort();
					break;

				}
			}
		}
		// 把换过位置的两个分类进行数据库的更新
		classification = new Classification();
		classification.setId(moveId);
		classification.setSort(moveIdSort);
		int resultOne = classificationService.updateByPrimaryKeySelective(classification);
		classification = new Classification();
		classification.setId(upOrDownId);
		classification.setSort(upOrDownIdSort);
		int resultTwo = classificationService.updateByPrimaryKeySelective(classification);
		// 往前台返回结果集
		if (resultOne > 0 && resultTwo > 0) {
			rmsg.put("success", true);
			rmsg.put("msg", Constants.OPERATION_SUCCESS_MSG);
			return rmsg;
		}

		rmsg.put("success", false);
		rmsg.put("msg", Constants.OPERATION_FAILURE_MSG);
		return rmsg;
	}
	
	
	/**
	 * 获取所有的一级分类
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getAllOneClassification")
	public List<Classification> getAllOneClassification(HttpServletRequest request){
		return classificationService.selectMsgOrderBySort("one");
	}
	
	
	/**
	 * 根据一级分类获取所属的二级分类
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="selectTwoByOneId")
	public List<Classification> selectTwoByOneId(HttpServletRequest request,String id){
		return classificationService.selectTwoByOneId(Integer.parseInt(id));
	}
	
	/**
	 * 一二级分类页面的导出操作
	 * @param request
	 * @param response
	 * @param oneOrTwo 一二级分类的标识符
	 * @param identifier 编号搜索框内容
	 * @param name  名称搜索框内容
	 * @param operatorIdentifier 添加人编号搜索框内容
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/exportClassification")
	public boolean exportClassification(HttpServletRequest request,HttpServletResponse response,String oneOrTwo,String identifier,String name,String operatorName,String operatorTime) throws Exception{
		System.out.println("oneOrTwo:"+URLDecoder.decode(oneOrTwo, "UTF-8"));
		String fileName = "";//文档名称以及Excel里头部信息
		List<Classification> clist=classificationService.selectMsgOrderBySearchMsg(URLDecoder.decode(oneOrTwo, "UTF-8"),URLDecoder.decode(identifier, "UTF-8"),URLDecoder.decode(name, "UTF-8"),URLDecoder.decode(operatorName, "UTF-8"),URLDecoder.decode(operatorTime, "UTF-8"));
		List<String[]> dataList=new ArrayList<>();
		Classification oneClassification;//保存根据二级分类的父级id查询的一级分类的信息
		String[] title; //保存Excel表头
		String[] value; //保存要导出的内容
		//搜索的有数据
		if(clist.size()>0){
			//一级菜单需导出的表头与信息
			if("one".equals(URLDecoder.decode(oneOrTwo, "UTF-8"))){
				fileName = "一级分类信息";//文档名称以及Excel里头部信息
				//保存Excel表头
				title=new String[5];
				title[0]="编号";
				title[1]="分类名称";
				title[2]="关键词";
				title[3]="创建人编号";
				title[4]="添加时间";
				//保存要导出的内容
				for(Classification c:clist){
					value=new String[5];
					value[0]=c.getIdentifier();
					value[1]=c.getName();
					value[2]=c.getKeyWord();		
					
					if(c.getOperatorIdentifier()!=null&&!"".equals(c.getOperatorIdentifier())){
						if(c.getUser()!=null){
							value[3]=c.getOperatorIdentifier()+"("+c.getUser().getName()+")";
						}
						else{
							value[3]=c.getOperatorIdentifier();
						}
						
					}
					else{
						value[3]="";
					}
					if(c.getOperatorTime()!=null){
						value[4]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getOperatorTime());
					}
					else{
						value[4]="";
					}
					
					dataList.add(value);
				}
				
			}
			//二级菜单需导出的表头与信息
			else{
				fileName = "二级分类信息";//文档名称以及Excel里头部信息
				//保存Excel表头
				title=new String[6];
				title[0]="编号";
				title[1]="分类名称";
				title[2]="关键词";
				title[3]="所属一级分类";
				title[4]="创建人编号";
				title[5]="添加时间";
				//保存要导出的内容
				for(Classification c:clist){
					//根据二级分类的父级id查询的一级分类的信息
					oneClassification=classificationService.selectByPrimaryKey(c.getParentId());
					value=new String[6];
					value[0]=c.getIdentifier();
					value[1]=c.getName();
					value[2]=c.getKeyWord();
					value[3]=oneClassification.getName();			
					if(c.getOperatorIdentifier()!=null&&!"".equals(c.getOperatorIdentifier())){
						if(c.getUser()!=null){
							value[4]=c.getOperatorIdentifier()+"("+c.getUser().getName()+")";
						}
						else{
							value[4]=c.getOperatorIdentifier();
						}
						
					}
					else{
						value[4]="";
					}
					if(c.getOperatorTime()!=null){
						value[5]=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getOperatorTime());
					}
					else{
						value[5]="";
					}
					dataList.add(value);
				}
			}
		}
		//没有搜索到数据
		else{
			if("one".equals(URLDecoder.decode(oneOrTwo, "UTF-8"))){
				fileName = "一级分类信息";//文档名称以及Excel里头部信息
			}
			else{
				fileName = "二级分类信息";//文档名称以及Excel里头部信息
			}
			title=new String[1];
			title[0]=Constants.NO_DATA_EXPORT;//无数据提示
		}
		//调用公共方法导出数据
		CommonMethod.exportData(request, response, fileName,title, dataList);
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

}
