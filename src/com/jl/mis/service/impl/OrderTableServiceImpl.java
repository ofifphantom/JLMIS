package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.OrderTableMapper;
import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.OrderTable;
import com.jl.mis.service.OrderTableService;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:44
 * @Description 订单ServiceImpl
 */
@Service
public class OrderTableServiceImpl implements OrderTableService{
	
	@Autowired
	private OrderTableMapper orderMapper;

	//原start
	@Override
	public int saveEntity(OrderTable t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(OrderTable t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public OrderTable findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OrderTable t) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.insert(t);
	}

	@Override
	public int insertSelective(OrderTable t) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.insertSelective(t);
	}

	@Override
	public OrderTable selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return orderMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderTable t) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(OrderTable t) throws Exception {
		// TODO Auto-generated method stub
		return orderMapper.updateByPrimaryKey(t);
	}

	@Override
	public DataTables getManagerMsg(DataTables dataTables,String orderNo,String userName,String phone,String orderState,String page,String placeOrderTime) {
		String[] columns = { "ot.order_no", "od.id", "od.goods_quantity", "ot.order_present_price","u.name","u.phone","ot.place_order_time","ot.order_state","ot.id" };
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		
		//保存查询的关键词
		params.put("orderNo", orderNo);
		params.put("userName", userName);
		params.put("phone", phone);
		if(orderState!=null&&!"".equals(orderState)){
			params.put("orderState", Integer.parseInt(orderState));
		}
		else{
			params.put("orderState", -1);
		}
		
		params.put("page", Integer.parseInt(page));
		params.put("placeOrderTime", placeOrderTime);
		
		List<Object> orderTableList=new ArrayList<>();
		orderTableList=orderMapper.selectForSearch(params);
		//循环列表，把一个订单表下的所买的商品的名称以及数量整合在一起传到前台进行显示。
		for(int i=0;i<orderTableList.size();i++){
			List<OrderDetail> orderDetailList=((OrderTable)orderTableList.get(i)).getOrderDetails();
			String orderGoodsName="";	//整合每个订单下的商品名称
			String orderGoodsNum="";	//整合每个订单下的商品数量
			String orderGoodsPrice="";	//整合每个订单下的商品价格
			for(int h=0;h<orderDetailList.size();h++){
				
				if(h<orderDetailList.size()-1){
					//整合每个订单下的商品名称
					orderGoodsName+=orderDetailList.get(h).getGoodsName()+"("+orderDetailList.get(h).getGoodsSpecificationName()+")"+"<br>";				
					//整合每个订单下的商品数量
					orderGoodsNum+=orderDetailList.get(h).getGoodsQuantity()+"<br>";
					//整合每个订单下的商品价格
					orderGoodsPrice+=orderDetailList.get(h).getGoodsPaymentPrice()+"<br>";
				}
				else{
					orderGoodsName+=orderDetailList.get(h).getGoodsName()+"("+orderDetailList.get(h).getGoodsSpecificationName()+")";
					orderGoodsNum+=orderDetailList.get(h).getGoodsQuantity();
					orderGoodsPrice+=orderDetailList.get(h).getGoodsPaymentPrice();
				}
				
			}
			((OrderTable)orderTableList.get(i)).setOrderGoodsName(orderGoodsName);
			((OrderTable)orderTableList.get(i)).setOrderGoodsNum(orderGoodsNum);
			((OrderTable)orderTableList.get(i)).setOrderGoodsPrice(orderGoodsPrice);
		}
		//不能用limit来进行分页，因为结果集有重复，只能在service中手动进行分页
		List<Object> resultTableList=new ArrayList<>();
		if((orderTableList.size()-Integer.parseInt(dataTables.getiDisplayStart()))>=Integer.parseInt(dataTables.getPageDisplayLength())){
			
			for(int i=Integer.parseInt(dataTables.getiDisplayStart());i<Integer.parseInt(dataTables.getiDisplayStart())+Integer.parseInt(dataTables.getPageDisplayLength());i++){
				resultTableList.add(orderTableList.get(i));
			}
		}
		else{
			
			for(int i=Integer.parseInt(dataTables.getiDisplayStart());i<orderTableList.size();i++){
				resultTableList.add(orderTableList.get(i));
			}
		}
		
		dataTables.setiTotalDisplayRecords(orderMapper
				.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(orderMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(resultTableList);// 返回的结果集
		return dataTables;
	}

	@Override
	public OrderTable checkOrderDetailsByOrderId(String id) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", Integer.parseInt(id));
		return orderMapper.checkOrderDetailsByOrderId(map);
	}

	@Override
	public List<String> selectTenCarId(String carId) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("carId", carId);
		return orderMapper.selectTenCarId(map);
	}

	@Override
	public List<OrderTable> selectOrderTableListByUserId(Integer userId) {
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("userId", userId);
		return orderMapper.selectOrderTableListByUserId(map);
	}
	
	public OrderTable selectOrderTableListByUserIdAndOrderId(Integer userId,Integer orderId){
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("orderId", orderId);
		return orderMapper.selectOrderTableListByUserIdAndOrderId(map);
	}
	
	@Override
	public Integer autoCancleOrderByNoIncludedPostage() {
		// TODO Auto-generated method stub
		return orderMapper.autoCancleOrderByNoIncludedPostage();
	}

	@Override
	public Integer autoCancleOrderByIncludedPostage() {
		// TODO Auto-generated method stub
		return orderMapper.autoCancleOrderByIncludedPostage();
	}
	
	@Override
	public OrderTable selectByOrderNo(String orderNo) {
		return orderMapper.selectByOrderNo(orderNo);
	}

	@Override
	public boolean updateJLgxcSaleOrderStateToReturnGoods(String orderNo, Integer id) {
		Map<String,Object> map=new HashMap<>();
		map.put("orderNo", orderNo);
		map.put("id", id);
		return orderMapper.updateJLgxcSaleOrderStateToReturnGoods(map);
	}

	@Override
	public List<OrderTable> selectReturnOrder() {
		// TODO Auto-generated method stub
		return orderMapper.selectReturnOrder();
	}
 
}