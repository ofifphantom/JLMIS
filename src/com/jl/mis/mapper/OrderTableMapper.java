package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderTable;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:44
 * @Description 订单Mapper
 */
public interface OrderTableMapper extends BaseMapper<OrderTable>{
	
	/**
	 * 根据订单id获取查看详情页面里的值
	 * @param map
	 * @return
	 */
	public OrderTable checkOrderDetailsByOrderId(Map<String,Object> map);
	
	/**
	 * 获取最近填写的十个车牌号
	 * @return
	 */
	public List<String> selectTenCarId(Map<String,Object> map);
	
	/**
	 * 根据用户id获取用户的所有订单列表
	 * @param map
	 * @return
	 */
	public List<OrderTable> selectOrderTableListByUserId(Map<String,Object> map);
	
	/**
	 * 根据用户id获取一个订单的所有评价
	 * @param map
	 * @return
	 */
	public OrderTable selectOrderTableListByUserIdAndOrderId(Map<String,Object> map);
	
	
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
	public boolean updateJLgxcSaleOrderStateToReturnGoods(Map<String,Object> map);
	
	/**
	 * 查询退款中的信息
	 * @return
	 */
	public List<OrderTable> selectReturnOrder();
 
}