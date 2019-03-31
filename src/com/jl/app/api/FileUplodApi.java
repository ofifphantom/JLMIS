package com.jl.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.utils.CommonMethod;
@Controller
@RequestMapping("/FileUpload/")
public class FileUplodApi {
	// 保存用户上传的图片的文件夹名
		private String folderName = "uploadImages";
	
	/** 
	 * 图片上传
	 * @param file
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject imgUpload(@RequestParam MultipartFile[] file,HttpServletResponse response, HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {
			JSONObject result = new JSONObject();
			// 4.验证图片 格式和大小
			int maxSize = 1024 * 1024 * 5;
			for (int i = 0; i < file.length; i++) {
				MultipartFile fileItem = file[i];
				String fileName = fileItem.getOriginalFilename();// 文件原名称
				System.out.println("fileName:" + fileName);
				if (fileName != null && !"".equals(fileName)) {
					// 判断文件类型
					String type = fileName.indexOf(".") != -1
							? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
					if (type == null) {
						result.put("code", 10000);
						result.put("msg", "图片参数错误");
						return result;
					}
					String UpType = type.toUpperCase();
					if (!("JPEG".equals(UpType)) && !("PNG".equals(UpType)) && !("BMP".equals(UpType))
							&& !("JPG".equals(UpType))) {
						result.put("code", 10001);
						result.put("msg", "文件类型不匹配");
						return result;

					}
					if (fileItem.getSize() > maxSize) {
						result.put("code", 10002);
						result.put("msg", "文件过大");
						return result;
					}

				}
			}
			List<String> list=new ArrayList<String>();
			for (int i = 0; i < file.length; i++) {
				list.add(CommonMethod.savePic1(request,folderName, file[i],i));//保存图片
			}
			result.put("resultData", list);
			result.put("code", 200);
			result.put("msg", "请求成功");
			
			return  result;
	}
	

		
}
