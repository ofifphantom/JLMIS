package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.OrderTable;
/**
 * 报表mapper
 * @author 王玉林
 * @date  2017-12-25  下午3:41:33
 * @Description TODO
 */
public interface FormService {
		/*
		 * 综合查询
		 */
		public List<OrderTable> Search(Map<String, Object> map);
		/*
		 * 订单报表查询
		 */
		public List<OrderTable> Order(Map<String, Object> map);
		/*
		 * 商品报表查询
		 */
		public List<Map<String, Object>> Goods(Map<String, Object> map);
}
