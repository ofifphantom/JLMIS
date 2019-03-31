package com.jl.mis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jl.mis.service.LogService;
import com.jl.mis.utils.DataTables;

/**
 * log日志表controller
 * @author 柳亚婷
 * @date  2017年11月23日  下午5:31:16
 * @Description 
 *
 */
@Controller
@RequestMapping("backgroundManagement/background/LogManager")
public class LogManagerController extends BaseController{
	
	@Autowired
	private LogService logService;
	
	/**
	 * 进入log日志页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		if (!checkSession(request)) {
			return "redirect:/login";
		}
			return "进入log日志页面";

	}
	

	/**
	 * 获取操作日志表的信息，显示在界面上
	 * @param request
	 * @param operatorIdentifier
	 * @param operatorType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getLogMsg")
	public DataTables getLogMsg(HttpServletRequest request,String operatorIdentifier,String operatorType,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);

		return logService.getLogMsg(dataTables, operatorIdentifier, operatorType,operatorTime);
	}

}
