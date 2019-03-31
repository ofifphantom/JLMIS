package com.jl.mis.controller;


import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.Log;
import com.jl.mis.model.VatInvoiceAptitude;
import com.jl.mis.service.LogService;
import com.jl.mis.service.VatInvoiceAptitudeService;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
/**
 * 增票资质controller
 * 
 * @author 景雅倩
 * @date 2017-12-04 下午3:55:25
 * @Description TODO
 */
@Controller
@RequestMapping("/orderManagement/invoice/vatInvoiceAptitude/")
public class VatInvoiceAptitudeController extends BaseController{
	
	@Autowired
	private VatInvoiceAptitudeService vatInvoiceAptitudeService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 进入增票资质审核页面
	 * @param request
	 * @return 页面路径
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list(HttpServletRequest request){
		if(!checkSession(request)){
			return "redirect:/login";
		}
		return "";
	}
	
	
	/**
	 * 增票资质审核页面dataTables
	 * @param request
	 * @param identifier 编号
	 * @param name 用户姓名
	 * @param phone 用户手机号
	 * @param state 状态
	 * @return
	 */
	@RequestMapping(value="dataTables",method=RequestMethod.POST)
	@ResponseBody
	public DataTables dataTables(HttpServletRequest request,String identifier, String name, String phone, int state,String operatorTime) {
		DataTables dataTables = DataTables.createDataTables(request);

		return vatInvoiceAptitudeService.getDataTables(dataTables, identifier, name, phone,state,operatorTime);
		
	}
	
	
	/**
	 * 资质审核
	 * @param request
	 * @param vatInvoiceAptitude  包含identifier、id和state
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateState", method = RequestMethod.POST)
	public JSONObject updateState(HttpServletRequest request,VatInvoiceAptitude vatInvoiceAptitude) throws Exception {
	
		
		
		JSONObject rmsg=new JSONObject();
		
		if (vatInvoiceAptitudeService.updateStateById(vatInvoiceAptitude)) {
			
			switch (vatInvoiceAptitude.getState()) {
			case 1://不通过
				rmsg.put("msg","操作成功，已拒绝！");
				break;
			case 2://通过
				rmsg.put("msg","操作成功，已通过！");
				break;

			default:
				break;
			}
					
			//插入日志
			Log log =new Log();
			
			//操作类型
			log.setOperateType(Constants.TYPE_LOG_CHECK);
			
			//操作对象
			//获取操作的对象编号
			String operateObject = vatInvoiceAptitude.getIdentifier()+"("+Constants.OPERATE_UPDATE+")";
			log.setOperateObject(operateObject);
	
			//操作人
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
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

}
