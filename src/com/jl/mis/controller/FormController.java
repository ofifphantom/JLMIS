package com.jl.mis.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jl.mis.mapper.FormMapper;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.service.ClassificationService;
import com.jl.mis.service.FormService;
import com.jl.mis.utils.CommonMethod;
import com.jl.mis.utils.Constants;
import com.sun.org.apache.xalan.internal.lib.NodeInfo;

import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * 
 * @author 王玉林
 * @date 2017年12月16日 下午3:36:00
 * @Description 报表Controller
 *
 */
@Controller
@RequestMapping("backgroundManagement/background/form/")
public class FormController extends BaseController {
	@Autowired
	private FormService service;
	@Autowired
	private ClassificationService classificationService;
	
	
	
	@RequestMapping(value="Search")
	public ModelAndView Search(String name,String phone,String Sname,String OrderNo,String OrderTime) {
		ModelAndView modelAndView=new ModelAndView();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("name", name);
		map.put("phone", phone);
		map.put("Sname", Sname);
		map.put("OrderNo", OrderNo);
		map.put("OrderTime", OrderTime);
		modelAndView.addObject("map", map);
		modelAndView.addObject("list", service.Search(map));
		modelAndView.setViewName("junlin/mis_jsp/reportSystem/formSearch");
		return modelAndView;
	}
	
	@RequestMapping(value="Order")
	public ModelAndView Order(String choosetime,Integer selectType) {
		System.out.println("展示");
		ModelAndView modelAndView=new ModelAndView();
		DecimalFormat nf = new DecimalFormat(".00");
		nf.setRoundingMode(RoundingMode.UP);
		//保留两位小数
		nf.setMaximumFractionDigits(2); 
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("choosetime", choosetime);
		map.put("selectType", selectType);
		if(choosetime!=null && !"".equals(choosetime)){
			String [] a=choosetime.split("~");
			map.put("start", a[0].substring(0, a[0].length()-1));
			map.put("end", a[1].substring(1, a[1].length()));
		}
		Integer OrderNum=0; //订单总数
		double OrderMONEY=0; //订单总金额
		Integer QOrderNum=0; //取消总数
		double QOrderMONEY=0; //订单总金额
		Integer TOrderNum=0; //退货总数
		double TOrderMONEY=0; //订单总金额
		Integer SOrderNum=0; //实际总数
		double SOrderMONEY=0; //订单总金额
		
		double 	CostMONEY=0;			 	//成本金额
		double 	ProfitMONEY=0;				//利润
		
		List<OrderTable> list = service.Order(map);
		OrderNum=list.size();
		for (OrderTable orderTable : list) {
			List<OrderDetail> list1=new ArrayList<OrderDetail>();
			if(orderTable.getOrderState()==7){										//退货
				TOrderNum+=1;
				
//				BigDecimal bd1 = new BigDecimal(Double.toString(TOrderMONEY)); 
//		        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
//		        TOrderMONEY=TOrderMONEY+bd1.add(bd2).doubleValue();
				
				TOrderMONEY+=orderTable.getOrderPresentPrice();
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(list1.size()>0){
						boolean have=false;
						int m = 0,n = 0;
						for(int j=0;j<list1.size();j++){
							if(list1.get(j).getGoodsDetails().getClassification().getName().equals(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName())){
								have=true;
								m=i;
								n=j;
							}
						}
						if(have){
							list1.get(n).setGoodsQuantity(list1.get(n).getGoodsQuantity()+orderTable.getOrderDetails().get(m).getGoodsQuantity());
						}else{
							list1.add(orderTable.getOrderDetails().get(i));
						}
					}else{
						list1.add(orderTable.getOrderDetails().get(i));
					}
				}
			}else if(orderTable.getOrderState()==4||orderTable.getOrderState()==5){	//取消
				QOrderNum+=1;
//				BigDecimal bd1 = new BigDecimal(Double.toString(QOrderMONEY)); 
//		        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
		       // QOrderMONEY=QOrderMONEY+bd1.add(bd2).doubleValue();
				
				QOrderMONEY+=orderTable.getOrderPresentPrice();
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(list1.size()>0){
						boolean have=false;
						int m = 0,n = 0;
						for(int j=0;j<list1.size();j++){
							if(list1.get(j).getGoodsDetails().getClassification().getName().equals(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName())){
								have=true;
								m=i;
								n=j;
							}
						}
						if(have){
							list1.get(n).setGoodsQuantity(list1.get(n).getGoodsQuantity()+orderTable.getOrderDetails().get(m).getGoodsQuantity());
						}else{
							list1.add(orderTable.getOrderDetails().get(i));
						}
					}else{
						list1.add(orderTable.getOrderDetails().get(i));
					}
				}
			}else{																	//实际
				SOrderNum+=1;
//				BigDecimal bd1 = new BigDecimal(Double.toString(SOrderMONEY)); 
//		        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
//		        SOrderMONEY=SOrderMONEY+bd1.add(bd2).doubleValue();
		        
		        SOrderMONEY+=orderTable.getOrderPresentPrice();
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(list1.size()>0){
						boolean have=false;
						int m = 0,n = 0;
						for(int j=0;j<list1.size();j++){
							if(list1.get(j).getGoodsDetails().getClassification().getName().equals(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName())){
								have=true;
								m=i;
								n=j;
							}
						}
						if(have){
							list1.get(n).setGoodsQuantity(list1.get(n).getGoodsQuantity()+orderTable.getOrderDetails().get(m).getGoodsQuantity());
						}else{
							list1.add(orderTable.getOrderDetails().get(i));
						}
					}else{
						list1.add(orderTable.getOrderDetails().get(i));
					}
					CostMONEY=CostMONEY+orderTable.getOrderDetails().get(i).getGoodsPurchasingPrice()*orderTable.getOrderDetails().get(i).getGoodsQuantity();
				}
			}
//			BigDecimal bd1 = new BigDecimal(Double.toString(OrderMONEY)); 
//	        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
//	        OrderMONEY=OrderMONEY+bd1.add(bd2).doubleValue();
			
			OrderMONEY+=orderTable.getOrderPresentPrice();
			orderTable.setOrderDetails(list1);
		}
		ProfitMONEY=OrderMONEY-TOrderMONEY-QOrderMONEY-CostMONEY;
		
		modelAndView.addObject("OrderNum", OrderNum);
		modelAndView.addObject("OrderMONEY", nf.format(OrderMONEY));
		modelAndView.addObject("QOrderNum", QOrderNum);
		modelAndView.addObject("QOrderMONEY", nf.format(QOrderMONEY));
		modelAndView.addObject("TOrderNum", TOrderNum);
		modelAndView.addObject("TOrderMONEY", nf.format(TOrderMONEY));
		modelAndView.addObject("SOrderNum", SOrderNum);
		modelAndView.addObject("SOrderMONEY", nf.format(SOrderMONEY));
		modelAndView.addObject("CostMONEY", nf.format(CostMONEY));
		modelAndView.addObject("ProfitMONEY", nf.format(ProfitMONEY));
		
		modelAndView.addObject("map", map);
		modelAndView.addObject("list" , list);
		modelAndView.setViewName("junlin/mis_jsp/reportSystem/formOrder");
		return modelAndView;
	}
	
	@RequestMapping(value="Goods")
	public ModelAndView Goods(String choosetime,String citTwo,String citOne,Integer classificationOne,Integer classificationTwo) {
		ModelAndView modelAndView=new ModelAndView();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("choosetime", choosetime);
		map.put("citOne", citOne);
		map.put("citTwo", citTwo);
		if(classificationOne!=null){
			if(classificationOne==-1){
				classificationOne=null;
			}else{
				if(classificationTwo != null){
					if(classificationTwo == -1){
						classificationTwo=null;
						map.put("listOne", classificationService.selectTwoByOneId(classificationOne));
					}
				}else{
					map.put("listOne", classificationService.selectTwoByOneId(classificationOne));
				}
			}
		}
		map.put("classificationOne", classificationOne);
		
		map.put("classificationTwo", classificationTwo);
		
		if(choosetime!=null && !"".equals(choosetime)){
			String [] a=choosetime.split("~");
			map.put("start", a[0].substring(0, a[0].length()-1));
			map.put("end", a[1].substring(1, a[1].length()));
		}
		List<Map<String, Object>> listm= service.Goods(map);
		Integer GoodsNum=0;     //商品个数
		Integer SGoodsNum=0;	//销售总数
		Integer BGoodsNum=0;	//售后数
		Integer RGoodsNum=0;	//实际销售数
		
		GoodsNum=listm.size();
		
		for (Map<String, Object> map2 : listm) {
			SGoodsNum+=((BigDecimal)map2.get("NUM")).intValue();
			BGoodsNum+=((BigDecimal)map2.get("SNUM")).intValue();
		}
		RGoodsNum=SGoodsNum-BGoodsNum;
		
		modelAndView.addObject("GoodsNum", GoodsNum);
		modelAndView.addObject("SGoodsNum", SGoodsNum);
		modelAndView.addObject("BGoodsNum", BGoodsNum);
		modelAndView.addObject("RGoodsNum", RGoodsNum);
		
		modelAndView.addObject("map", map);
		modelAndView.addObject("list",listm);
		modelAndView.setViewName("junlin/mis_jsp/reportSystem/formGoods");
		return modelAndView;
	}
	
	
	
	/**
	 * 订单报表导出
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/exportOrder")
	public boolean exportOrder(HttpServletRequest request, HttpServletResponse response,String choosetime,Integer selectType) throws Exception {
		DecimalFormat nf = new DecimalFormat(".00");
		nf.setRoundingMode(RoundingMode.UP);
		//保留两位小数
		nf.setMaximumFractionDigits(2); 

		ModelAndView modelAndView=new ModelAndView();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("choosetime", choosetime);
		map.put("selectType", selectType);
		if(choosetime!=null && !"".equals(choosetime)){
			String [] a=choosetime.split("~");
			map.put("start", a[0].substring(0, a[0].length()-1));
			map.put("end", a[1].substring(1, a[1].length()));
		}
		Integer OrderNum=0; //订单总数
		double OrderMONEY=0; //订单总金额
		Integer QOrderNum=0; //取消总数
		double QOrderMONEY=0; //订单总金额
		Integer TOrderNum=0; //退货总数
		double TOrderMONEY=0; //订单总金额
		Integer SOrderNum=0; //实际总数
		double SOrderMONEY=0; //订单总金额
		
		double 	CostMONEY=0;			 	//成本金额
		double 	ProfitMONEY=0;				//利润
		double yPrice=0; //优惠价格
		double pMoney=0; //实付款
		List<OrderTable> list = service.Order(map);
		OrderNum=list.size();
		for (OrderTable orderTable : list) {
			List<OrderDetail> list1=new ArrayList<OrderDetail>();
			if(orderTable.getOrderState()==7){										//退货
				TOrderNum+=1;
				TOrderMONEY+=orderTable.getOrderPresentPrice();
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(list1.size()>0){
						boolean have=false;
						int m = 0,n = 0;
						for(int j=0;j<list1.size();j++){
							if(list1.get(j).getGoodsDetails().getClassification().getName().equals(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName())){
								have=true;
								m=i;
								n=j;
							}
						}
						if(have){
							list1.get(n).setGoodsQuantity(list1.get(n).getGoodsQuantity()+orderTable.getOrderDetails().get(m).getGoodsQuantity());
						}else{
							list1.add(orderTable.getOrderDetails().get(i));
						}
					}else{
						list1.add(orderTable.getOrderDetails().get(i));
					}
				}
			}else if(orderTable.getOrderState()==4||orderTable.getOrderState()==5){	//取消
				QOrderNum+=1;
				QOrderMONEY+=orderTable.getOrderPresentPrice();
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(list1.size()>0){
						boolean have=false;
						int m = 0,n = 0;
						for(int j=0;j<list1.size();j++){
							if(list1.get(j).getGoodsDetails().getClassification().getName().equals(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName())){
								have=true;
								m=i;
								n=j;
							}
						}
						if(have){
							list1.get(n).setGoodsQuantity(list1.get(n).getGoodsQuantity()+orderTable.getOrderDetails().get(m).getGoodsQuantity());
						}else{
							list1.add(orderTable.getOrderDetails().get(i));
						}
					}else{
						list1.add(orderTable.getOrderDetails().get(i));
					}
				}
			}else{																	//实际
				SOrderNum+=1;
//				BigDecimal bd1 = new BigDecimal(Double.toString(SOrderMONEY)); 
//		        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
//		        SOrderMONEY=SOrderMONEY+bd1.add(bd2).doubleValue();
				
		        SOrderMONEY+=orderTable.getOrderPresentPrice();
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(list1.size()>0){
						boolean have=false;
						int m = 0,n = 0;
						for(int j=0;j<list1.size();j++){
							if(list1.get(j).getGoodsDetails().getClassification().getName().equals(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName())){
								have=true;
								m=i;
								n=j;
							}
						}
						if(have){
							list1.get(n).setGoodsQuantity(list1.get(n).getGoodsQuantity()+orderTable.getOrderDetails().get(m).getGoodsQuantity());
						}else{
							list1.add(orderTable.getOrderDetails().get(i));
						}
					}else{
						list1.add(orderTable.getOrderDetails().get(i));
					}
					CostMONEY=CostMONEY+orderTable.getOrderDetails().get(i).getGoodsPurchasingPrice()*orderTable.getOrderDetails().get(i).getGoodsQuantity();
				}
			}
//			BigDecimal bd1 = new BigDecimal(Double.toString(OrderMONEY)); 
//	        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
//	        OrderMONEY=OrderMONEY+bd1.add(bd2).doubleValue();
	        
	        OrderMONEY+=orderTable.getOrderPresentPrice();
	       // System.out.println(bd1+">>>>>>"+bd2);
	        
			//OrderMONEY+=orderTable.getOrderPresentPrice();
			orderTable.setOrderDetails(list1);
		}
		ProfitMONEY=OrderMONEY-TOrderMONEY-QOrderMONEY-CostMONEY;
		
		modelAndView.addObject("OrderNum", OrderNum);
		modelAndView.addObject("OrderMONEY", (double)Math.round(OrderMONEY*100)/100);
		modelAndView.addObject("QOrderNum", QOrderNum);
		modelAndView.addObject("QOrderMONEY", QOrderMONEY);
		modelAndView.addObject("TOrderNum", TOrderNum);
		modelAndView.addObject("TOrderMONEY", TOrderMONEY);
		modelAndView.addObject("SOrderNum", SOrderNum);
		modelAndView.addObject("SOrderMONEY",(double)Math.round(SOrderMONEY*100)/100);
		modelAndView.addObject("CostMONEY", CostMONEY);
		modelAndView.addObject("ProfitMONEY",(double)Math.round(ProfitMONEY*100)/100);
		
		modelAndView.addObject("map", map);
		modelAndView.addObject("list" , list);
		modelAndView.setViewName("junlin/mis_jsp/reportSystem/formOrder");
	
		
		
		//String fileName = choosetime!=null && !"".equals(choosetime)?"订单报表_"+choosetime:"订单报表_全部";// 文档名称以及Excel里头部信息\
		String fileName = "订单报表";
		List<String[]> dataList = new ArrayList<>();
		String[] title; // 保存Excel表头
		String[] value; // 保存要导出的内容
		// 搜索的有数据
		if (list.size() > 0) {
			// 保存Excel表头
			title = new String[13];
			title[0] = "订单号";
			title[1] = "订单状态";
			title[2] = "商品种类";
			title[3] = "商品数量";
			title[4] = "用户姓名";
			title[5] = "用户电话";
			title[6] = "收货信息";
			//title[7] = "发票信息";
			title[7] = "下单时间";		
			title[8] = "支付时间";
			title[9] = "订单价格";
			title[10] = "优惠价格";
			title[11] = "实付款";
			title[12] = "配送信息";
			
			// 保存要导出的内容
			for (OrderTable orderTable : list) {
				value = new String[13];
				value[0] = orderTable.getOrderNo();
				switch (orderTable.getOrderState()) {
					case 0: value[1] = "待支付"; break;
					case 1: value[1] = "待发货"; break;
					case 2: value[1] = "待收货"; break;
					case 3: value[1] = "已完成"; break;
					case 4: value[1] = "已取消"; break;
					case 5: value[1] = "已关闭"; break;
					case 6: value[1] = "售后中"; break;
					case 7: value[1] = "已关闭"; break;
					case 8: value[1] = "已关闭"; break;
					case 9: value[1] = "已支付"; break;
					case 10: value[1] = "已完成"; break;
				}
				String nameString="";
				String NumString="";
				for (int i=0;i<orderTable.getOrderDetails().size();i++) {
					if(i<(orderTable.getOrderDetails().size()-1)){
						nameString+=(orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName()+"/");
						NumString+=(orderTable.getOrderDetails().get(i).getGoodsQuantity()+"/");
					}else{
						nameString+=orderTable.getOrderDetails().get(i).getGoodsDetails().getClassification().getName();
						NumString+=orderTable.getOrderDetails().get(i).getGoodsQuantity();
					}
				}
				value[2] = nameString;
				value[3] = NumString;
				value[4] = orderTable.getUser().getName();
				value[5] = orderTable.getUser().getPhone();
				value[6] = orderTable.getConsigneeName()+"/"+orderTable.getConsigneeTel()+"/"+orderTable.getConsigneeAddress();
		/*		String Invoice="";
				if(orderTable.getInvoice().getInvoiceType()==null){
					Invoice+="无/";
				}else{
					Invoice+=orderTable.getInvoice().getInvoiceType();
					Invoice+="/";
				}
				
				if(orderTable.getInvoice().getUnitName()==null){
					Invoice+="无/";
				}else{
					Invoice+=orderTable.getInvoice().getUnitName();
					Invoice+="/";
				}
				if(orderTable.getInvoice().getTaxpayerIdentificationNumber()==null){
					Invoice+="无";
				}else{
					Invoice+=orderTable.getInvoice().getTaxpayerIdentificationNumber();
				}
				
				value[7] =Invoice;*/
				if(orderTable.getPlaceOrderTime()!=null){
					value[7] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderTable.getPlaceOrderTime());
				}else{
					value[7] = null;	
				}
				if(orderTable.getPayTime()!=null){
					value[8] = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderTable.getPayTime());
				}else{
					value[8] = null;	
				}
				value[9] = String.valueOf(orderTable.getOrderOriginalPrice());
				BigDecimal bd1 = new BigDecimal(nf.format(orderTable.getOrderOriginalPrice())); 
		        BigDecimal bd2 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice()));
		        yPrice=bd1.subtract(bd2).doubleValue();
		        if (yPrice==0.0) {
					value[10]="0";
				}else {
					value[10] =yPrice+"";
				}
				
				BigDecimal bd3 = new BigDecimal(nf.format(orderTable.getOrderPresentPrice())); 
				if ((bd3+"").equals("0.00")) {
					value[11] ="0";
				}else {
					value[11] =bd3+"";
				}
				System.out.println(bd3);
				value[12]=orderTable.getCarIdSend();
				dataList.add(value);
			}

		}
		// 没有搜索到数据
		else {
			title = new String[1];
			title[0] = Constants.NO_DATA_EXPORT;// 无数据提示
		}
		// 调用公共方法导出数据
		this.exportData(request, response, fileName, title, dataList,0,modelAndView);
		return true;
	}
	
	

	/**
	 * 商品报表导出
	 * 
	 */
	@ResponseBody
	@RequestMapping(value = "/exportGoods")
	public boolean exportGoods(HttpServletRequest request, HttpServletResponse response,String choosetime,String citTwo,String citOne,Integer classificationOne,Integer classificationTwo) throws Exception {
		ModelAndView modelAndView=new ModelAndView();
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("choosetime", choosetime);
		map.put("citOne", citOne);
		map.put("citTwo", citTwo);
		if(classificationOne!=null){
			if(classificationOne==-1){
				classificationOne=null;
			}else{
				if(classificationTwo != null){
					if(classificationTwo == -1){
						classificationTwo=null;
						map.put("listOne", classificationService.selectTwoByOneId(classificationOne));
					}
				}else{
					map.put("listOne", classificationService.selectTwoByOneId(classificationOne));
				}
			}
		}
		map.put("classificationOne", classificationOne);
		
		map.put("classificationTwo", classificationTwo);
		
		if(choosetime!=null && !"".equals(choosetime)){
			String [] a=choosetime.split("~");
			map.put("start", a[0].substring(0, a[0].length()-1));
			map.put("end", a[1].substring(1, a[1].length()));
		}
		List<Map<String, Object>> listm= service.Goods(map);
		Integer GoodsNum=0;     //商品个数
		Integer SGoodsNum=0;	//销售总数
		Integer BGoodsNum=0;	//售后数
		Integer RGoodsNum=0;	//实际销售数
		
		GoodsNum=listm.size();
		
		for (Map<String, Object> map2 : listm) {
			SGoodsNum+=((BigDecimal)map2.get("NUM")).intValue();
			BGoodsNum+=((BigDecimal)map2.get("SNUM")).intValue();
		}
		RGoodsNum=SGoodsNum-BGoodsNum;
		
		modelAndView.addObject("GoodsNum", GoodsNum);
		modelAndView.addObject("SGoodsNum", SGoodsNum);
		modelAndView.addObject("BGoodsNum", BGoodsNum);
		modelAndView.addObject("RGoodsNum", RGoodsNum);
		
		modelAndView.addObject("map", map);
		modelAndView.addObject("list",listm);
		modelAndView.setViewName("junlin/mis_jsp/reportSystem/formGoods");
		
		//String fileName = choosetime!=null && !"".equals(choosetime)?"商品销量报表_"+choosetime:"商品销量报表_全部";// 文档名称以及Excel里头部信息
		String fileName = "商品销量报表";
		List<String[]> dataList = new ArrayList<>();
		String[] title; // 保存Excel表头
		String[] value; // 保存要导出的内容
		// 搜索的有数据
		if (listm.size() > 0) {
			// 保存Excel表头
			title = new String[5];
			title[0] = "商品编号";
			title[1] = "商品名称";
			title[2] = "商品数量";
			title[3] = "售后数量";
			title[4] = "实际销售数量";
			
			// 保存要导出的内容
			for (Map<String,Object> map1 : listm) {
				value = new String[5];
				value[0] = 	(String) map1.get("identifier");
				value[1] =	(String) map1.get("goods_name")+"/"+(String) map1.get("goods_specification_name");
				value[2] = 	String.valueOf((BigDecimal) map1.get("NUM"));
				value[3] =	String.valueOf((BigDecimal) map1.get("SNUM"));
				value[4] = 	String.valueOf(((BigDecimal)map1.get("NUM")).intValue()-((BigDecimal)map1.get("SNUM")).intValue());
			
				dataList.add(value);
			}

		}
		// 没有搜索到数据
		else {
			title = new String[1];
			title[0] = Constants.NO_DATA_EXPORT;// 无数据提示
		}
		// 调用公共方法导出数据
		this.exportData(request, response, fileName, title, dataList,1,modelAndView);
		return true;
	}

	/**
	 * 导出数据
	 * 
	 * @param fileName
	 *            文件名称
	 * @param title
	 *            数据title
	 * @param dataMap
	 *            需要输出的数据
	 * @return
	 * @throws Exception
	 */
	public  boolean exportData(HttpServletRequest request, HttpServletResponse response, String fileName,
			String[] title, List<String[]> dataList,int TYPE,ModelAndView modelAndView) throws Exception {

		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		String titleName = fileName;
		// 下面是对中文文件名的处理
		response.setCharacterEncoding("UTF-8");// 设置相应内容的编码格式
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8"); // 经过处理的该数据，传到putExcel()方法会造成乱码，需在上面保存一下。
		response.setHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xls");
		response.setContentType("application/msexcel");// 定义输出类型

		this.putExcel(os, titleName, title, dataList,TYPE,modelAndView);

		return true;
	}

	public  void putExcel(OutputStream os, String fileName, String[] dataTitle, List<String[]> dataList,int TYPE,ModelAndView modelAndView) {
		// 创建工作薄
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			// 创建新的一页
			WritableSheet sheet = workbook.createSheet("First Sheet", 0);
			// 构造表头
			sheet.mergeCells(0, 0, dataTitle.length, 0); // 添加合并单元格，第一个参数是起始列，第二个参数是起始行，第三个参数是终止列，第四个参数是终止行

			WritableFont bold = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);// 设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
			WritableCellFormat titleFormate = new WritableCellFormat(bold);// 生成一个单元格样式控制对象
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);// 单元格中的内容水平方向居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 单元格的内容垂直方向居中
			Label title = new Label(0, 0, fileName, titleFormate);
			CellView cellView = new CellView();
			cellView.setAutosize(true); // 设置自动大小
			sheet.setRowView(0, 600, false);// 设置第一行的高度
			for (int i = 0; i < dataTitle.length; i++) {
				sheet.setColumnView(i, cellView);// 设置列宽
				sheet.setColumnView(i, 1000);
			}
			sheet.addCell(title);

			bold = new WritableFont(WritableFont.ARIAL, 14);// 设置字体种类和黑体显示,字体为Arial,字号大小为10,采用黑体显示
			titleFormate = new WritableCellFormat(bold);// 生成一个单元格样式控制对象
			titleFormate.setAlignment(jxl.format.Alignment.CENTRE);// 单元格中的内容水平方向居中
			titleFormate.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 单元格的内容垂直方向居中

			Label label;
			Number number;
			// 报表第一列表头
			label = new Label(0, 1, "序号", titleFormate);
			sheet.addCell(label);
			// 没有需要显示的数据，则在导出时文件里显示无数据。
			if (dataTitle.length == 1 && Constants.NO_DATA_EXPORT.equals(dataTitle[0])) {
				sheet.mergeCells(0, 1, dataTitle.length, 0);// 合并单元格
				label = new Label(0, 1, Constants.NO_DATA_EXPORT, titleFormate);
				sheet.addCell(label);
				if(TYPE==1){
					label = new Label(0, 2,"导出时间：", titleFormate);
					sheet.addCell(label);
					if(((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")).equals("")){
						label = new Label(1, 2,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime"), titleFormate);
					}else{
						label = new Label(1, 2,"全部", titleFormate);
					}
					sheet.addCell(label);
					label = new Label(0, 3,"商品种类：", titleFormate);
					sheet.addCell(label);
					if(((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne")).equals("")&&((Map<String,Object>)modelAndView.getModel().get("map")).get("classificationOne")!=null){
						if(((Map<String,Object>)modelAndView.getModel().get("map")).get("citTwo")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citTwo")).equals("")&&((Map<String,Object>)modelAndView.getModel().get("map")).get("classificationTwo")!=null){
							label = new Label(1, 3,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne")+"-"+(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citTwo"), titleFormate);
						}else{
							label = new Label(1, 3,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne"), titleFormate);
						}
					}else{
						label = new Label(1, 3,"全部", titleFormate);
					}
					sheet.addCell(label);
					                                                                                 
					label = new Label(0, 4,"商品数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 4,String.valueOf((Integer)(modelAndView.getModel().get("GoodsNum"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, 5,"商品销售数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 5,String.valueOf((Integer)(modelAndView.getModel().get("SGoodsNum"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, 6,"售后数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 6,String.valueOf((Integer)(modelAndView.getModel().get("BGoodsNum"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, 7,"实际订销售数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 7,String.valueOf((Integer)(modelAndView.getModel().get("RGoodsNum"))), titleFormate);
					sheet.addCell(label);
				}else{
					label = new Label(0, 2,"导出时间：", titleFormate);
					sheet.addCell(label);
					if(((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")).equals("")){
						label = new Label(1, 2,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime"), titleFormate);
					}else{
						label = new Label(1, 2,"全部", titleFormate);
					}
					sheet.addCell(label);
					label = new Label(0, 3,"订单状态：", titleFormate);
					sheet.addCell(label);
					switch ((Integer)((Map<String,Object>)modelAndView.getModel().get("map")).get("selectType")) {
					case 0:
						label = new Label(1, 3,"待支付", titleFormate);
						break;
					case 1:
						label = new Label(1, 3,"待发货", titleFormate);
						break;
					case 2:
						label = new Label(1, 3,"待收货", titleFormate);
						break;
					case 3:
						label = new Label(1, 3,"已完成", titleFormate);
						break;
					case 4:
						label = new Label(1, 3,"已取消", titleFormate);
						break;
					case 5:
						label = new Label(1, 3,"已关闭", titleFormate);
						break;
					case 6:
						label = new Label(1, 3,"售后中", titleFormate);
						break;
					case 7:
						label = new Label(1, 3,"已关闭", titleFormate);
						break;
					case 8:
						label = new Label(1, 3,"已关闭", titleFormate);
						break;
					case 9:
						label = new Label(1, 3,"已支付", titleFormate);
						break;
					case 10:
						label = new Label(1, 3,"已完成", titleFormate);
						break;
					default:
						label = new Label(1, 3,"全部", titleFormate);
						break;
					}
					sheet.addCell(label);
					
					label = new Label(0, 4,"订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 4,String.valueOf((Integer)(modelAndView.getModel().get("OrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, 4,"订单总金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, 4,String.valueOf((Double)(modelAndView.getModel().get("OrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, 5,"取消订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 5,String.valueOf((Integer)(modelAndView.getModel().get("QOrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, 5,"取消订单金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, 5,String.valueOf((Double)(modelAndView.getModel().get("QOrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, 6,"退货订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 6,String.valueOf((Integer)(modelAndView.getModel().get("TOrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, 6,"退货订单金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, 6,String.valueOf((Double)(modelAndView.getModel().get("TOrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, 7,"实际订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, 7,String.valueOf((Integer)(modelAndView.getModel().get("SOrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, 7,"实际订单总金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, 7,String.valueOf((Double)(modelAndView.getModel().get("SOrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					sheet.mergeCells(0, 8, 1, 8);// 合并单元格
					label = new Label(0, 8, "成本金额：", titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(2, 8, 3, 8);// 合并单元格
					label = new Label(2, 8, String.valueOf((Double)(modelAndView.getModel().get("CostMONEY"))), titleFormate);
					sheet.addCell(label);
					
					sheet.mergeCells(0, 9, 1, 9);// 合并单元格
					label = new Label(0, 9, "利润：", titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(2, 9, 3, 9);// 合并单元格                  
					label = new Label(2, 9, String.valueOf((Double)(modelAndView.getModel().get("ProfitMONEY"))), titleFormate);
					sheet.addCell(label);
				
				}
			} else {
				// 设置列表头
				for (int i = 0; i < dataTitle.length; i++) {
					label = new Label(i + 1, 1, dataTitle[i], titleFormate);
					sheet.addCell(label);
				}
				// 设置列内容
				for (int j = 0; j < dataList.size(); j++) {
					// 序号
					number = new Number(0, j + 2, j + 1, titleFormate);
					sheet.addCell(number);
					for (int i = 0; i < dataList.get(j).length; i++) {
						// 创建要显示的具体内容
						label = new Label(i + 1, j + 2, dataList.get(j)[i], titleFormate);
						sheet.addCell(label);
					}
				}
				if(TYPE==1){
					label = new Label(0, dataList.size()+2,"导出时间：", titleFormate);
					sheet.addCell(label);
					if(((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")).equals("")){
						label = new Label(1, dataList.size()+2,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime"), titleFormate);
					}else{
						label = new Label(1, dataList.size()+2,"全部", titleFormate);
					}
					sheet.addCell(label);
					label = new Label(0, dataList.size()+3,"商品种类：", titleFormate);
					sheet.addCell(label);
					if(((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne")).equals("")&&((Map<String,Object>)modelAndView.getModel().get("map")).get("classificationOne")!=null){
						if(((Map<String,Object>)modelAndView.getModel().get("map")).get("citTwo")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citTwo")).equals("")&&((Map<String,Object>)modelAndView.getModel().get("map")).get("classificationTwo")!=null){
							label = new Label(1, dataList.size()+3,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne")+"-"+(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citTwo"), titleFormate);
						}else{
							label = new Label(1, dataList.size()+3,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("citOne"), titleFormate);
						}
					}else{
						label = new Label(1, dataList.size()+3,"全部", titleFormate);
					}
					sheet.addCell(label);
					                                                                                 
					label = new Label(0, dataList.size()+4,"商品数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+4,String.valueOf((Integer)(modelAndView.getModel().get("GoodsNum"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+5,"商品销售数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+5,String.valueOf((Integer)(modelAndView.getModel().get("SGoodsNum"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+6,"售后数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+6,String.valueOf((Integer)(modelAndView.getModel().get("BGoodsNum"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+7,"实际订销售数量：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+7,String.valueOf((Integer)(modelAndView.getModel().get("RGoodsNum"))), titleFormate);
					sheet.addCell(label);
				}else{
					label = new Label(0, dataList.size()+2,"导出时间：", titleFormate);
					sheet.addCell(label);
					if(((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")!=null&&!((String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime")).equals("")){
						label = new Label(1, dataList.size()+2,(String)((Map<String,Object>)modelAndView.getModel().get("map")).get("choosetime"), titleFormate);
					}else{
						label = new Label(1, dataList.size()+2,"全部", titleFormate);
					}
					sheet.addCell(label);
					label = new Label(0, dataList.size()+3,"订单状态：", titleFormate);
					sheet.addCell(label);
					switch ((Integer)((Map<String,Object>)modelAndView.getModel().get("map")).get("selectType")) {
					case 0:
						label = new Label(1, dataList.size()+3,"待支付", titleFormate);
						break;
					case 1:
						label = new Label(1, dataList.size()+3,"待发货", titleFormate);
						break;
					case 2:
						label = new Label(1, dataList.size()+3,"待收货", titleFormate);
						break;
					case 3:
						label = new Label(1, dataList.size()+3,"已完成", titleFormate);
						break;
					case 4:
						label = new Label(1, dataList.size()+3,"已取消", titleFormate);
						break;
					case 5:
						label = new Label(1, dataList.size()+3,"已关闭", titleFormate);
						break;
					case 6:
						label = new Label(1, dataList.size()+3,"售后中", titleFormate);
						break;
					case 7:
						label = new Label(1, dataList.size()+3,"已关闭", titleFormate);
						break;
					case 8:
						label = new Label(1, dataList.size()+3,"已关闭", titleFormate);
						break;
					case 9:
						label = new Label(1, dataList.size()+3,"已支付", titleFormate);
						break;
					case 10:
						label = new Label(1, dataList.size()+3,"已完成", titleFormate);
						break;
					default:
						label = new Label(1, dataList.size()+3,"全部", titleFormate);
						break;
					}
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+4,"订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+4,String.valueOf((Integer)(modelAndView.getModel().get("OrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, dataList.size()+4,"订单总金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, dataList.size()+4,String.valueOf((Double)(modelAndView.getModel().get("OrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+5,"取消订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+5,String.valueOf((Integer)(modelAndView.getModel().get("QOrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, dataList.size()+5,"取消订单金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, dataList.size()+5,String.valueOf((Double)(modelAndView.getModel().get("QOrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+6,"退货订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+6,String.valueOf((Integer)(modelAndView.getModel().get("TOrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, dataList.size()+6,"退货订单金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, dataList.size()+6,String.valueOf((Double)(modelAndView.getModel().get("TOrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					label = new Label(0, dataList.size()+7,"实际订单数：", titleFormate);
					sheet.addCell(label);
					label = new Label(1, dataList.size()+7,String.valueOf((Integer)(modelAndView.getModel().get("SOrderNum"))), titleFormate);
					sheet.addCell(label);
					label = new Label(2, dataList.size()+7,"实际订单总金额：", titleFormate);
					sheet.addCell(label);
					label = new Label(3, dataList.size()+7,String.valueOf((Double)(modelAndView.getModel().get("SOrderMONEY"))), titleFormate);
					sheet.addCell(label);
					
					sheet.mergeCells(0, dataList.size()+8, 1, dataList.size()+8);// 合并单元格
					label = new Label(0, dataList.size()+8, "成本金额：", titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(2, dataList.size()+8, 3, dataList.size()+8);// 合并单元格
					label = new Label(2, dataList.size()+8, String.valueOf((Double)(modelAndView.getModel().get("CostMONEY"))), titleFormate);
					sheet.addCell(label);
					
					sheet.mergeCells(0, dataList.size()+9, 1, dataList.size()+9);// 合并单元格
					label = new Label(0, dataList.size()+9, "利润：", titleFormate);
					sheet.addCell(label);
					sheet.mergeCells(2, dataList.size()+9, 3, dataList.size()+9);// 合并单元格                  
					label = new Label(2, dataList.size()+9, String.valueOf((Double)(modelAndView.getModel().get("ProfitMONEY"))), titleFormate);
					sheet.addCell(label);
				
				}
			}
			// 把创建的内容写入到输出流中，并关闭输出流
			workbook.write();
			workbook.close();
			os.close();
		} catch (WriteException e) {
			System.out.println("写入错误");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO错误");
		}
	}

}
