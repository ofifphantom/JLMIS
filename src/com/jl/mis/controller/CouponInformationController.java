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
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.Log;
import com.jl.mis.service.ActiveCouponService;
import com.jl.mis.service.CouponInformationService;
import com.jl.mis.service.LogService;
import com.jl.mis.service.UserCouponsService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.GetSessionUtil;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 优惠券controller
 * @author 景雅倩
 * @date  2017-11-6  下午3:03:02
 * @Description TODO
 */
@Controller
@RequestMapping("/backgroundConfiguration/activity/couponInformation/")
public class CouponInformationController extends BaseController{

	@Autowired
	private CouponInformationService couponInformationService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private UserCouponsService userCouponsService;
	
	@Autowired
	private ActiveCouponService activeCouponService;
	
	/**
	 * 进入优惠券管理页面
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
	 * 优惠券管理页面dataTable
	 * @param request
	 * @param identifier  编号
	 * @param name  名称
	 * @param operatorIdentifier  操作人
	 * @return dataTable
	 */
	@RequestMapping(value="dataTables",method=RequestMethod.POST)
	@ResponseBody
	public DataTables dataTables(HttpServletRequest request,int flag , String identifier, String name, String operatorIdentifier,String operatorTime){
		DataTables dataTables = DataTables.createDataTables(request);
		return couponInformationService.getDataTables(dataTables, flag, identifier, name, operatorIdentifier,operatorTime);
		
	}
	
	
	
	/**
	 * 添加优惠券信息
	 * @param request
	 * @param couponInformation
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "addCouponInformation", method = RequestMethod.POST)
	public JSONObject addCouponInformation(HttpServletRequest request,  CouponInformation couponInformation) throws Exception {
		
		//编号
		String identifier=SundryCodeUtil.getPosCode(Constants.CODE_YHQ);
		couponInformation.setIdentifier(identifier);
		//状态
		couponInformation.setState(Constants.STATE_YHQ_NOSEND);
		//操作人
		String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
		couponInformation.setOperatorIdentifier(operatorIdentifier);
		
		//操作时间
		Date operateTime=new Date();
		couponInformation.setOperatorTime(operateTime);
		//剩余数量  默认为总数量
		couponInformation.setCount(couponInformation.getTotal());
		
		JSONObject rmsg=new JSONObject();
		if (couponInformationService.insert(couponInformation)>0) {
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
	 * 删除（根据id列表删除优惠券信息）
	 * @param request
	 * @param ids  id数组
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteCouponInformationByIds", method = RequestMethod.POST)
	public JSONObject deleteCouponInformationByIds(HttpServletRequest request, String[] ids) throws Exception {
		
		JSONObject rmsg=new JSONObject();
		
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(ids!=null && ids.length>0){
			for(String id:ids){
				int int_id = Integer.valueOf(id);
				list.add(int_id);
				
			}
		}
		//判断有没有被活动占用的优惠券  如果有 返回优惠券编号
		List<CouponInformation> couponInformationIdentifier =  activeCouponService.selectCouponIdentifierByCouponId(list);
		
		if(couponInformationIdentifier.size()>0){
			String str="";
			for (int i = 0; i < couponInformationIdentifier.size(); i++) {
				str += couponInformationIdentifier.get(i).getIdentifier();
				if(i<couponInformationIdentifier.size()-1){
					str += ",";
				}
			}
			rmsg.put("success", false);
			rmsg.put("msg", "要删除的优惠券("+str+")有所属活动,删除失败！");
			return rmsg;
		}
		
		//判断有所要删除的优惠券中有没有客户领取过并且未使用的    如果有 返回优惠券编号
				List<CouponInformation> couponInformationIdentifier2 =  userCouponsService.selectCouponIdentifierByCouponId(list);
				
				if(couponInformationIdentifier2.size()>0){
					String str="";
					for (int i = 0; i < couponInformationIdentifier2.size(); i++) {
						str += couponInformationIdentifier2.get(i).getIdentifier();
						if(i<couponInformationIdentifier2.size()-1){
							str += ",";
						}
					}
					rmsg.put("success", false);
					rmsg.put("msg", "要删除的优惠券("+str+")有用户已领取且未使用,删除失败！");
					return rmsg;
				}
		
		//保存将要删除的对象编号
		List<CouponInformation> couponInformations = couponInformationService.getCouponInformationByIds(list);
		if (couponInformationService.deleteCouponInformationByIds(list)) {
			//插入日志
			Log log =new Log();
			//操作类型
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			//操作对象
			String operateObject="";
			for (int i = 0; i < couponInformations.size(); i++) {
				operateObject += couponInformations.get(i).getIdentifier();
				if(i < couponInformations.size()-1){
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
	 * 立即失效
	 * @param request
	 * @param ids id数组
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "updateStateToInvalid", method = RequestMethod.POST)
	public JSONObject updateStateToInvalid(HttpServletRequest request, String[] ids) throws Exception {
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(ids!=null && ids.length>0){
			for(String id:ids){
				list.add(Integer.valueOf(id));
			}
		}
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("ids", list);
		map.put("state", Constants.STATE_YHQ_INVALID);
		
		JSONObject rmsg=new JSONObject();
		if (couponInformationService.updateCouponInformationStateByIds(map)) {
			//根据优惠券id更新用户优惠券表中所有优惠券状态为已失效
			userCouponsService.updateStatusByCouponInformationId(list);
			//保存操作的对象编号
			List<CouponInformation> couponInformations = couponInformationService.getCouponInformationByIds(list);
			//插入日志
			Log log =new Log();
			//操作类型
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			//操作对象
			String operateObject="";
			for (int i = 0; i < couponInformations.size(); i++) {
				operateObject += couponInformations.get(i).getIdentifier();
				if(i < couponInformations.size()-1){
					operateObject += ",";
				}
			}
			operateObject += "("+Constants.OPERATE_UPDATE+")";
			log.setOperateObject(operateObject);
			//操作人
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			//操作时间
			Date operateTime=new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);
			
			
			
			
			rmsg.put("success", true);
			rmsg.put("msg","操作成功，已失效！");
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", "操作失败，请确认后重新操作！");
		return rmsg;
	}
	
	
	/**
	 * 撤回
	 * @param request
	 * @param ids id数组
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "updateStateToBackout", method = RequestMethod.POST)
	public JSONObject updateStateToBackout(HttpServletRequest request, String[] ids) throws Exception {
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(ids!=null && ids.length>0){
			for(String id:ids){
				list.add(Integer.valueOf(id));
				
			}
		}
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("ids", list);
		map.put("state", Constants.STATE_YHQ_BACKOUT);
		
		JSONObject rmsg=new JSONObject();
		if (couponInformationService.updateCouponInformationStateByIds(map)) {
			//根据优惠券id更新用户优惠券表中所有优惠券状态为已失效
			userCouponsService.updateStatusByCouponInformationId(list);
			//保存操作的对象编号
			List<CouponInformation> couponInformations = couponInformationService.getCouponInformationByIds(list);
			//插入日志
			Log log =new Log();
			//操作类型
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			//操作对象
			String operateObject="";
			for (int i = 0; i < couponInformations.size(); i++) {
				operateObject += couponInformations.get(i).getIdentifier();
				if(i < couponInformations.size()-1){
					operateObject += ",";
				}
			}
			operateObject += "("+Constants.OPERATE_UPDATE+")";
			log.setOperateObject(operateObject);
			//操作人
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			//操作时间
			Date operateTime=new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);
			
			
			
			
			rmsg.put("success", true);
			rmsg.put("msg","操作成功，已撤回！");
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", "操作失败，请确认后重新操作！");
		return rmsg;
	}
	
	/**
	 * 根据id列表修改状态
	 * @param request
	 * @param ids id数组
	 * @param state 要修改为的状态值（4:待审核,5:审核驳回,0:正常）
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "updateStateByIds", method = RequestMethod.POST)
	public JSONObject updateStateByIds(HttpServletRequest request, String[] ids,int state) throws Exception {
		ArrayList<Integer> list=new ArrayList<Integer>();
		if(ids!=null && ids.length>0){
			for(String id:ids){
				list.add(Integer.valueOf(id));
			}
		}
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("ids", list);
		map.put("state", state);
		
		JSONObject rmsg=new JSONObject();
		if (couponInformationService.updateCouponInformationStateByIds(map)) {
			
			//保存操作的对象编号
			List<CouponInformation> couponInformations = couponInformationService.getCouponInformationByIds(list);
			//插入日志
			Log log =new Log();
			//操作类型
			switch (state) {
			case Constants.STATE_YHQ_NORMAL://审核通过  状态修改为0：正常
				log.setOperateType(Constants.TYPE_LOG_CHECK);
				rmsg.put("msg","操作成功，已通过！");
				break;
			case Constants.STATE_YHQ_CHECKING://送审  状态修改为4：待审核
				log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
				rmsg.put("msg","操作成功，已送审！");
				break;
			case Constants.STATE_YHQ_REFUSE://驳回  状态修改为5：已驳回
				log.setOperateType(Constants.TYPE_LOG_CHECK);
				rmsg.put("msg","操作成功，已驳回！");
				break;

			default:
				break;
			}
			
			//操作对象
			String operateObject="";
			for (int i = 0; i < couponInformations.size(); i++) {
				operateObject += couponInformations.get(i).getIdentifier();
				if(i < couponInformations.size()-1){
					operateObject += ",";
				}
			}
			operateObject += "("+Constants.OPERATE_UPDATE+")";
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
	
	/**
	 * 编辑
	 * @param request
	 * @param 
	 * @return
	 * @throws Exception
	 */
	
	@ResponseBody
	@RequestMapping(value = "updateCouponInformationById", method = RequestMethod.POST)
	public JSONObject updateCouponInformationById(HttpServletRequest request,  CouponInformation couponInformation) throws Exception {
		
		int id = couponInformation.getId();
		CouponInformation beforeCoupon = couponInformationService.selectByPrimaryKey(id);	
		//获取原来的剩余数量和总数量
		int count = beforeCoupon.getCount();
		int total = beforeCoupon.getTotal();
		//获取新的总数量
		int newTotal = couponInformation.getTotal();
		if(total != newTotal ) {//修改了优惠券总数量  
			 int gap = newTotal - total;//新增的优惠券数量（正：新增  负：减少）
			 int newCount = count+gap;
			 couponInformation.setCount(newCount);
		}
		
		
		JSONObject rmsg=new JSONObject();
		if (couponInformationService.updateByPrimaryKeySelective(couponInformation)>0) {
			
			//保存操作的对象编号
			//List<CouponInformation> couponInformations = couponInformationService.getCouponInformationByIds(list);
			
			//插入日志
			Log log =new Log();
			//操作类型
			log.setOperateType(Constants.TYPE_LOG_ACTIVITY);
			//操作对象
			String identifier=beforeCoupon.getIdentifier();
			String operateObject = identifier + "("+Constants.OPERATE_UPDATE+")";
			log.setOperateObject(operateObject);
			
			//操作人
			String operatorIdentifier = GetSessionUtil.GetSessionUserIdentifier(request);
			log.setOperatorIdentifier(operatorIdentifier);
			//操作时间
			Date operateTime=new Date();
			log.setOperateTime(operateTime);
			logService.insert(log);
			
			
			
			
			rmsg.put("success", true);
			rmsg.put("msg",Constants.UPDATE_SUCCESS_MSG);
			return rmsg;
		}
		rmsg.put("success", false);
		rmsg.put("msg", Constants.UPDATE_FAILURE_MSG);
		return rmsg;
	}
	
	/**
	 * 获取所有状态为正常的优惠券信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "selectAllNormalCouponInformation", method = RequestMethod.POST)
	public List<CouponInformation> selectAllNormalCouponInformation() {
		
		return couponInformationService.selectAllNormal();
	}
	
	/**
	 * 导出优惠券信息
	 * @param request
	 * @param response
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/exportCouponInformation")
	public boolean exportCouponInformation(HttpServletRequest request,HttpServletResponse response,String identifier, String name, String operatorIdentifier,String operatorTime) throws Exception{
		
		identifier =  URLDecoder.decode(identifier, "UTF-8");
		name = URLDecoder.decode(name, "UTF-8");
		operatorIdentifier = URLDecoder.decode(operatorIdentifier, "UTF-8");
		operatorTime = URLDecoder.decode(operatorTime, "UTF-8");
		
		String fileName = "";//文档名称以及Excel里头部信息
		List<CouponInformation> couponInformations=couponInformationService.selectMsgOrderBySearchMsg(identifier, name, operatorIdentifier,operatorTime);
		List<String[]> dataList=new ArrayList<>();
		String[] title; //保存Excel表头
		String[] value; //保存要导出的内容
		fileName = "优惠券信息";//文档名称以及Excel里头部信息
		
		//搜索的有数据
		if(couponInformations.size()>0){
			//需导出的表头与信息
			
			//保存Excel表头
			title=new String[9];
			title[0]="编号";
			title[1]="名称";
			title[2]="金额(元)";
			title[3]="数量(张)";
			title[4]="门槛(元)";
			title[5]="有效期";
			title[6]="领取时间";
			title[7]="规则";
			title[8]="状态";
			//保存要导出的内容
			for(CouponInformation c:couponInformations){
				value=new String[9];
				value[0]=c.getIdentifier();
				value[1]=c.getName();
				value[2]=c.getPrice()+"";
				value[3]=c.getTotal()+"";
				value[4]=c.getUseLimit()+"";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				value[5]=sdf.format(c.getBeginValidityTime())+" ~ "+sdf.format(c.getEndValidityTime()) ;
				value[6]=sdf.format(c.getBeginTime())+" ~ "+sdf.format(c.getEndTime()) ;
				switch (c.getRules()) {
				case 0:
					value[7]="注册返券";
					break;
				case 1:
					value[7]="领券";
					break;
				case 2:
					value[7]="购物返券";
					break;
				default:
					break;
				}
				switch (c.getState()) {
				case 0:
					value[8]="正常";
					break;
				case 1:
					value[8]="已失效";
					break;
				case 2:
					value[8]="已撤回";
					break;
				case 3:
					value[8]="未送审";
					break;
				case 4:
					value[8]="待审核";
					break;
				case 5:
					value[8]="已驳回";
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
	
	
	
	
	
	
	
	
	
	
	
	
	
}
