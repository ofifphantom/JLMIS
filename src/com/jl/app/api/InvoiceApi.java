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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.controller.BaseController;
import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.InvoiceUnit;
import com.jl.mis.model.Log;
import com.jl.mis.model.VatInvoiceAptitude;
import com.jl.mis.service.InvoiceUnitService;
import com.jl.mis.service.VatInvoiceAptitudeService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.SundryCodeUtil;


/**
 * 发票相关API
 * @author 景雅倩
 * @date  2017年12月11日  下午13:57:56
 * @Description 
 *
 */
@Controller
@RequestMapping("/invoice/")
public class InvoiceApi extends BaseController{
	
	
		// 保存送票图片的文件夹名
		private String folderName = "uploadImages";
		
		@Autowired
		private VatInvoiceAptitudeService vatInvoiceAptitudeService;
		
		@Autowired
		private InvoiceUnitService invoiceUnitService;
	
		/**
		 * 根据用户id获取增票资质信息
		 * @param request
		 * @param userId 用户id
		 * @return 该用户的增票资质信息
		 */
		@ResponseBody
		@RequestMapping(value = "getVatInvoiceAptitudeByUserId", method = RequestMethod.GET)
		public JSONObject getVatInvoiceAptitudeByUserId(HttpServletRequest request,@RequestParam("userId") Integer userId) {
			JSONObject result = new JSONObject();
			//参数判空
			if(userId==null){
				result.put("code", 10000);
				result.put("msg", "参数错误");
				return result;
			}
			VatInvoiceAptitude vatInvoiceAptitude = vatInvoiceAptitudeService.getVatInvoiceAptitudeByUserId(userId);
//			if(vatInvoiceAptitude==null){
//				result.put("code", 50002);
//				result.put("msg", "增票资质不存在");
//				return result;
//			}
			
			result.put("resultData", vatInvoiceAptitude);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		
		
		/**
		 * 根据用户id获取普票信息
		 * @param request
		 * @param userId 用户id
		 * @return 该用户的普票信息列表
		 */
		@ResponseBody
		@RequestMapping(value = "getInvoiceUnitByUserId", method = RequestMethod.GET)
		public JSONObject getInvoiceUnitByUserId(HttpServletRequest request,@RequestParam("userId") Integer userId) {
			JSONObject result = new JSONObject();
			//参数判空
			if(userId==null){
				result.put("code", 10000);
				result.put("msg", "参数错误");
				return result;
			}
			List<InvoiceUnit> invoiceUnitList = invoiceUnitService.getInvoiceUnitByUserId(userId);
//			if(invoiceUnitList==null||invoiceUnitList.size()<=0){
//				result.put("code", 50006);
//				result.put("msg", "普票信息不存在");
//				return result;
//			}
			
			result.put("resultData", invoiceUnitList);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		
		/**
		 * 根据用户id获取该用户的普票信息以及增票信息
		 * @param request
		 * @param userId 用户id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "getInvoiceUnitAndVatInvoiceAptitudeByUserId", method = RequestMethod.GET)
		public JSONObject getInvoiceUnitAndVatInvoiceAptitudeByUserId(HttpServletRequest request,@RequestParam("userId") Integer userId) {
			JSONObject result = new JSONObject();
			Map<String,Object> invoiceMsg=new HashMap<>();
			//参数判空
			if(userId==null){
				result.put("code", 10000);
				result.put("msg", "参数错误");
				return result;
			}
			List<InvoiceUnit> invoiceUnitList = new ArrayList<>();
			invoiceUnitList = invoiceUnitService.getInvoiceUnitByUserId(userId);
			VatInvoiceAptitude vatInvoiceAptitude = vatInvoiceAptitudeService.getVatInvoiceAptitudeByUserId(userId);
			
			invoiceMsg.put("invoiceUnit", invoiceUnitList);
			invoiceMsg.put("vatInvoiceAptitude", vatInvoiceAptitude);
			
			result.put("resultData", invoiceMsg);
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
	
	
                                 
	/**
	 * 添加发票审核信息
	 * 对应APP中用户提交增票资质审核
	 * @param request
	 * @param files  增票资质图片
	 * @param unitName 单位名称
	 * @param taxpayerIdentificationNumber 纳税人识别号
	 * @param registeredAddress 注册地址
	 * @param registeredTel 注册电话
	 * @param depositBank 开户银行
	 * @param bankAccount 银行账号
	 * @param userId 用户id
	 * @return  提交成功或失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "commitVatInvoiceAptitudeToCheck", method = RequestMethod.POST)
	public JSONObject commitVatInvoiceAptitudeToCheck(HttpServletRequest request, String[] files,
			String unitName,
			String taxpayerIdentificationNumber,
			String registeredAddress,
			String registeredTel,
			String depositBank,
			String bankAccount,
			Integer userId) throws Exception {
		
		//验证参数信息是否合法
		//1.判空

		if(files==null||files.length<=0||unitName == null
				||taxpayerIdentificationNumber == null
				||registeredAddress == null
				||registeredTel == null
				||depositBank == null
				||bankAccount == null
				||userId == null){
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			JSONObject result = new JSONObject();
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		
		
		//将提交过来的审核信息封装成对象 然后添加如数据库
	
		VatInvoiceAptitude vatInvoiceAptitude = new VatInvoiceAptitude();
		vatInvoiceAptitude.setUnitName(unitName);
		vatInvoiceAptitude.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
		vatInvoiceAptitude.setRegisteredAddress(registeredAddress);
		vatInvoiceAptitude.setRegisteredTel(registeredTel);
		vatInvoiceAptitude.setDepositBank(depositBank);
		vatInvoiceAptitude.setBankAccount(bankAccount);
		
		//编号
		String identifier=SundryCodeUtil.getPosCode(Constants.CODE_INVOICE);
		vatInvoiceAptitude.setIdentifier(identifier);
		//状态
		vatInvoiceAptitude.setState(0);
		//用户id
		vatInvoiceAptitude.setUserId(userId);
		//提交时间
		Date operatorTime = new Date();
		vatInvoiceAptitude.setOperatorTime(operatorTime);
		
		// 存储新图片，并保存路径
		for (int i = 0; i < files.length; i++) {
				vatInvoiceAptitude.setBusinessLicenseUrl(files[i]/*CommonMethod.savePic(request,
						folderName, file)*/);
		}
	
		
		
		JSONObject result=new JSONObject();
		if (vatInvoiceAptitudeService.insertSelective(vatInvoiceAptitude)>0) {
			result.put("resultData", "提交成功！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}else{
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			
			result.put("resultData", "提交失败！");
			result.put("code", 50001);
			result.put("msg", "增票资质信息提交失败");
			return result;
		}
		
	}
	
	/**
	 * 根据用户id添加普票信息
	 * @param request
	 * @param unitName 单位名称
	 * @param taxpayerIdentificationNumber 纳税人识别号
	 * @param userId 用户id
	 * @return  添加成功或/失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addInvoiceUnit", method = RequestMethod.POST)
	public JSONObject addInvoiceUnit(HttpServletRequest request, String unitName, String taxpayerIdentificationNumber, Integer userId) throws Exception{
		
		//验证参数信息是否合法
		JSONObject result = new JSONObject();
		if(unitName == null ||taxpayerIdentificationNumber == null ||userId == null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		InvoiceUnit invoiceUnit = new InvoiceUnit();
		invoiceUnit.setUnitName(unitName);
		invoiceUnit.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
		invoiceUnit.setUserId(userId);
		Date operatorTime = new Date();
		invoiceUnit.setOperatorTime(operatorTime);
		if (invoiceUnitService.insertSelective(invoiceUnit)>0) {
			result.put("resultData", "提交成功！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		result.put("resultData", "提交失败！");
		result.put("code", 50005);
		result.put("msg", "普票信息添加失败");
		return result;
	}
	
	
	
	
	
	/**
	 * 根据用户id修改增票资质信息
	 * 对应APP中用户修改增票资质审核
	 * @param request
	 * @param files  增票资质图片
	 * @param unitName 单位名称
	 * @param taxpayerIdentificationNumber 纳税人识别号
	 * @param registeredAddress 注册地址
	 * @param registeredTel 注册电话
	 * @param depositBank 开户银行
	 * @param bankAccount 银行账号
	 * @param userId 用户id
	 * @param id 增票资质id
	 * @return  修改成功或失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateVatInvoiceAptitudeById", method = RequestMethod.POST)
	public JSONObject updateVatInvoiceAptitudeById(HttpServletRequest request, String[] files,
			String unitName,
			String taxpayerIdentificationNumber,
			String registeredAddress,
			String registeredTel,
			String depositBank,
			String bankAccount,
			Integer userId,
			Integer id) throws Exception  {
		
		
		JSONObject result=new JSONObject();
		//验证参数信息是否合法
		//1.判空
		if(unitName == null ||taxpayerIdentificationNumber == null ||registeredAddress == null
		    ||registeredTel == null ||depositBank == null ||bankAccount == null ||userId == null||id == null||files == null){
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
		
		//获取将要删除的信息
		
		VatInvoiceAptitude getedVatInvoiceAptitude = vatInvoiceAptitudeService.selectByPrimaryKey(id);
		if(getedVatInvoiceAptitude==null){
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 50002);
			result.put("msg", "增票资质不存在");
			return result;	
		}
				
		//2.验证是否是本人操作本人数据
		int getedUserId =getedVatInvoiceAptitude.getUserId();
		if(getedUserId != userId){
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("code", 10003);
			result.put("msg", "尝试非法操作");
			return result;
		}
		
		
		VatInvoiceAptitude vatInvoiceAptitude = new VatInvoiceAptitude();
		List<String> oldPicUrlList=new ArrayList<>();
		
//		//3.验证图片  格式和大小
//		if(( && files.length > 0)){//有新图片
//			
//			int maxSize = 1024*1024*5;
//			for (int i = 0; i < files.length; i++) {
//				MultipartFile file = files[i];
//				String fileName=file.getOriginalFilename();// 文件原名称
//				
//				if (fileName != null && !"".equals(fileName)) {
//					 // 判断文件类型
//					String type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
//					String UpType = type.toUpperCase();
//					
//					 if (!("JPEG".equals(UpType)) && !("PNG".equals(UpType)) && !("BMP".equals(UpType)) && !("JPG".equals(UpType))) {
//						
//							result.put("code", 10001);
//							result.put("msg", "文件类型不匹配");
//							return result;
//					
//					 }else if(file.getSize()>maxSize){
//						
//							result.put("code", 10002);
//							result.put("msg", "文件过大");
//							return result;
//					 }
//					
//					 
//				}
//			}
//		}
	
		
		for(int i = 0; i < files.length; i++){
			 //设置新图片路径 并保存图片
			 vatInvoiceAptitude.setBusinessLicenseUrl(files[i]);
			 //保存要删除的旧图片
			//保存将要删除的服务器文件夹中的旧图片路径
			
			VatInvoiceAptitude v=vatInvoiceAptitudeService.getVatInvoiceAptitudeByUserId(userId);
			if(v.getBusinessLicenseUrl()!=null && !"".equals(v.getBusinessLicenseUrl())){
				oldPicUrlList.add(v.getBusinessLicenseUrl());
			}
		}
		
		
		//将提交过来的审核信息封装成对象 然后添加如数据库
		vatInvoiceAptitude.setId(id);
		vatInvoiceAptitude.setUnitName(unitName);
		vatInvoiceAptitude.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
		vatInvoiceAptitude.setRegisteredAddress(registeredAddress);
		vatInvoiceAptitude.setRegisteredTel(registeredTel);
		vatInvoiceAptitude.setDepositBank(depositBank);
		vatInvoiceAptitude.setBankAccount(bankAccount);
		
		
		//状态
		vatInvoiceAptitude.setState(0);
		//用户id
		vatInvoiceAptitude.setUserId(userId);
		
		
		
		if (vatInvoiceAptitudeService.updateByPrimaryKeySelective(vatInvoiceAptitude)>0) {
			
			//删除服务器图片文件夹中的相应图片
			if(oldPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
			}
			
			
			result.put("resultData", "提交成功！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}else{
			if(files!=null){
				List<String> oldUrl=new ArrayList<String>();
	 			for (int i = 0; i < files.length; i++) {
	 				oldUrl.add(files[i]);
				}
				CommonMethod.deleteOldPic(request, folderName, oldUrl);
			}
			result.put("resultData", "提交失败！");
			result.put("code", 50001);
			result.put("msg", "增票资质信息提交失败");
			return result;
		}
	}
	
	
	/**
	 * 根据用户id和普票信息id修改普票信息
	 * @param request
	 * @param id 普票id 
	 * @param unitName 单位名称
	 * @param taxpayerIdentificationNumber 纳税人识别号
	 * @param userId 用户id
	 * @return  添加成功或/失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateInvoiceUnitById", method = RequestMethod.POST)
	public JSONObject updateInvoiceUnitById(HttpServletRequest request,Integer id, String unitName, String taxpayerIdentificationNumber, Integer userId) throws Exception{
		
		//1。验证参数信息是否合法
		JSONObject result = new JSONObject();
		if(id == null ||unitName == null ||taxpayerIdentificationNumber == null ||userId == null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		
		
		//2.获取将要修改的信息
		InvoiceUnit getedInvoiceUnit = invoiceUnitService.selectByPrimaryKey(id);
		if(getedInvoiceUnit==null){
			result.put("code", 50006);
			result.put("msg", "普票信息不存在");
			return result;	
		}
		//3.验证是否是本人操作本人数据
		int getedUserId =getedInvoiceUnit.getUserId();
		if(getedUserId != userId){
			result.put("code", 10003);
			result.put("msg", "尝试非法操作");
			return result;
		}
		
		InvoiceUnit invoiceUnit = new InvoiceUnit();
		invoiceUnit.setId(id);
		invoiceUnit.setUnitName(unitName);
		invoiceUnit.setTaxpayerIdentificationNumber(taxpayerIdentificationNumber);
		invoiceUnit.setUserId(userId);
		if (invoiceUnitService.updateByPrimaryKeySelective(invoiceUnit)>0) {
			result.put("resultData", "修改成功！");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		result.put("resultData", "修改失败！");
		result.put("code", 50007);
		result.put("msg", "普票信息修改失败");
		return result;
	}
	
	
	/**
	 * 删除增票资质
	 * @param request
	 * @param id  赠票资质id
	 * @param userId 用户id
	 * @return 删除成功/失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteVatInvoiceAptitudeById", method = RequestMethod.POST)
	public JSONObject deleteVatInvoiceAptitudeById(HttpServletRequest request, Integer id , Integer userId) throws Exception {

		JSONObject result = new JSONObject();
		//1.参数判空
		if(id==null || userId==null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
	
		//获取将要删除的信息
		VatInvoiceAptitude vatInvoiceAptitude = vatInvoiceAptitudeService.selectByPrimaryKey(id);
		if(vatInvoiceAptitude==null){
			result.put("code", 50002);
			result.put("msg", "增票资质不存在");
			return result;	
		}
		
		//2.验证是否是本人操作本人数据
		int getedUserId =vatInvoiceAptitude.getUserId();
		if(getedUserId != userId){
			result.put("code", 10003);
			result.put("msg", "尝试非法操作");
			return result;
		}
		//保存将要删除的服务器文件夹中的旧图片路径
		List<String> oldPicUrlList=new ArrayList<>();
		if(vatInvoiceAptitude.getBusinessLicenseUrl()!=null && !"".equals(vatInvoiceAptitude.getBusinessLicenseUrl())){
			oldPicUrlList.add(vatInvoiceAptitude.getBusinessLicenseUrl());
		}

		if (vatInvoiceAptitudeService.deleteByPrimaryKey(id)>0) {
			
			//删除服务器图片文件夹中的相应图片
			if(oldPicUrlList.size()>0){
				CommonMethod.deleteOldPic(request, folderName, oldPicUrlList);
			}
			
			
			

			result.put("resultData", "删除成功!");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		
		result.put("resultData", "删除失败!");
		result.put("code", 50004);
		result.put("msg", "增票资质删除失败");
		return result;
	}
	
	/**
	 * 删除普票信息
	 * @param request
	 * @param id  普票id
	 * @param userId 用户id
	 * @return 删除成功/失败
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteInvoiceUnitById", method = RequestMethod.POST)
	public JSONObject deleteInvoiceUnitById(HttpServletRequest request, Integer id , Integer userId) throws Exception {

		JSONObject result = new JSONObject();
		//1.参数判空
		if(id==null || userId==null){
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
	
		//获取将要删除的信息
		InvoiceUnit invoiceUnit = invoiceUnitService.selectByPrimaryKey(id);
		if(invoiceUnit==null){
			result.put("code", 50006);
			result.put("msg", "普票信息不存在");
			return result;	
		}
		
		//2.验证是否是本人操作本人数据
		int getedUserId =invoiceUnit.getUserId();
		if(getedUserId != userId){
			result.put("code", 10003);
			result.put("msg", "尝试非法操作");
			return result;
		}
		

		if (invoiceUnitService.deleteByPrimaryKey(id)>0) {
			result.put("resultData", "删除成功!");
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		}
		
		result.put("resultData", "删除失败!");
		result.put("code", 50008);
		result.put("msg", "普票信息删除失败");
		return result;
	}

	
}
