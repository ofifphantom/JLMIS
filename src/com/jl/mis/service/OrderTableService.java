package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderTable;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:44
 * @Description 订单Service
 */
public interface OrderTableService extends BaseService<OrderTable>{
	
	/**
	 * 获取数据库中获取订单信息
	 * @return 返回一个订单列表对象
	 */
	public DataTables getManagerMsg(DataTables dataTables,String orderNo,String userName,String phone,String orderState,String page,String placeOrderTime);
	
	/**
	 * 根据订单id获取查看详情页面里的值
	 * @param map
	 * @return
	 */
	public OrderTable checkOrderDetailsByOrderId(String id);
	
	/**
	 * 获取最近填写的十个车牌号
	 * @return
	 */
	public List<String> selectTenCarId(String carId);
	
	/**
	 * 根据用户id获取用户的所有订单列表
	 * @param userId
	 * @return
	 */
	public List<OrderTable> selectOrderTableListByUserId(Integer userId);
	
	/**
	 * 根据用户id获取一个订单的所有评价
	 * @param map
	 * @return
	 */
	public OrderTable selectOrderTableListByUserIdAndOrderId(Integer userId,Integer orderId);
	
	/**
	 * 订单不在配送范围内时，状态为邮费到付的订单，待支付时间为24小时，若超过该时间，则自动取消订单
	 * @return
	 */
	public Integer autoCancleOrderByNoIncludedPostage();
	
	/**
	 * 订单在配送范围内时，状态为邮费现付的订单，待支付时间为4小时，若超过该时间，则自动取消订单
	 * @return
	 */
	public Integer autoCancleOrderByIncludedPostage();
		
	/**
	 * 根据订单号查询信息
	 * @param orderNo
	 * @return
	 */
	public OrderTable selectByOrderNo(String orderNo);
	
	/**
	 * app端申请售后为退货时，修改进销存销售订单中相应的单据的状态为退货
	 * @param map
	 * @return
	 */
	public boolean updateJLgxcSaleOrderStateToReturnGoods(String orderNo,Integer id);
		public List<OrderTable> selectReturnOrder();
 
}