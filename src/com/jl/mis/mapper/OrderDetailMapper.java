package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderDetail;
import com.jl.mis.model.SalesPlanOrderCommodity;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:48:26
 * @Description 订单详情Mapper
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail>{

		/**
		 * 查询订单商品详情-传递供销存使用
		 * @param orderId
		 * @return
		 */
		public  List<SalesPlanOrderCommodity> selectOrderCommodity(Integer orderId);
	
	
	
	//--------------------------------APP-----------------------------------
		/**
		 * 根据订单号获取信息
		 * @param orderId
		 * @return
		 */
		public List<OrderDetail> selectByOrderId(int orderId);
		
		/**
		 * 批量保存信息 
		 * @param map
		 * @return
		 */
		public Integer insertBatch(Map<String,Object> map);
		
		/**
		 * 根据订单id删除订单详情表
		 * @param map
		 * @return
		 */
		public Integer deleteByOrderId(Map<String,Object> map);
}