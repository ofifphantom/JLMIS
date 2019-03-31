package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.GoodsDetails;
import com.jl.mis.model.GoodsSpecificationDetails;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:47:05
 * @Description 商品规格详情Mapper
 */
public interface GoodsSpecificationDetailsMapper extends BaseMapper<GoodsSpecificationDetails> {

	/**
	 * 根据商品ID查询信息
	 * 
	 * @param goodsId
	 *            商品ID
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectByGoodsId(Integer goodsId);

	/**
	 * 根据商品id查询正在上架的商品
	 * 
	 * @param goodsId
	 *            商品ID
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectOnByGoodsId(Integer goodsId);
	
	/**
	 * 根据输入框的值查询信息
	 * 
	 * @param
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectMsgByInputValue(Map<String, Object> map);

	/**
	 * 根据主键批量查询信息
	 * 
	 * @param
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectBatchByPrimaryKey(Map<String, Object> map);

	/**
	 * 根据规格名称和商品详情id查询商品规格表
	 * 
	 * @param
	 * @return
	 */
	public Integer selectGoodsSDMsgBySpecificationAndGoodsId(Integer commoditySpecificationId);

	/**
	 * 根据规格名称和商品规格id查询商品规格表
	 * 
	 * @param
	 * @return
	 */
	public GoodsSpecificationDetails selectGoodsSDMsgBySpecificationAndGoodsSDId(Map<String, Object> map);

	/**
	 * 根据商品规格表的主键联合查询商品详情表、商品规格表以及分类表
	 * 
	 * @param
	 * @return
	 */
	public GoodsSpecificationDetails selectGSDAndGDAndClassificationMsgByGSDId(Map<String, Object> map);

	/**
	 * 根据购销存规格id修改商品为下架 购销存那边操作停用
	 * 
	 * @param
	 * @return
	 */
	public Integer updateByCommoditySpecificationId(GoodsSpecificationDetails goodsSpecificationDetails);
	
	/**
	 * 根据主键批量更新内容
	 * 
	 * @param
	 * @return
	 */
	public Integer updateBatchByPrimaryKey(Map<String, Object> map);

	/**
	 * 根据主键批量删除 信息
	 * 
	 * @param
	 * @return
	 */
	public Integer deleteBatchByPrimaryKey(Map<String, Object> map);

	/**
	 * 根据联合查询商品详情表、商品规格表以及分类表 和购销存的商品表，用于获取需导出的数据
	 * 
	 * @param
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectGSDAndGDAndClassificationMsgToExport(Map<String, Object> map);

	// 接口 移动端使用
	/**
	 * 获取根据价格排序的商品详情信息
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsSpecificationDetails> getGoodsSpecificationDetail(Map<String, Object> map);

	/**
	 * 根据商品id获取商品详情、商品详情对应的图片、购销存里的库存 状态 以及 品牌
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsSpecificationDetails> getGoodsDetailMsgByGoodsId(Map<String, Object> map);

	/**
	 * 根据商品规格id查库存
	 * 
	 * @param map
	 * @return
	 */
	public GoodsSpecificationDetails selectGxcGoodsStockByspecificationId(Map<String, Object> map);
	
	/**
	 * 根据商品规格id查库存
	 * 
	 * @param map
	 * @return
	 */
	public GoodsSpecificationDetails selectGxcGoodsStockAndZeroByspecificationId(Map<String, Object> map);
	
	/**
	 * 根据商品规格id以及商品id查库存
	 * 
	 * @param map
	 * @return
	 */
	public GoodsSpecificationDetails selectGxcGoodsStockByspecificationIdAndGoodsId(Map<String, Object> map);
	
	/**
	 * 根据主键和商品id查询商品详情 
	 * @param map
	 * @return
	 */
	public GoodsSpecificationDetails selectGoodsSpecificationDetailsByIdAndGoodsId(Map<String, Object> map);
	
	/**
	 * 根据主键和商品id查询商品详情包括商品信息 必须是上架的
	 * @param map
	 * @return
	 */
	public GoodsSpecificationDetails selectGoodsDetailsByIdAndGoodsId(Map<String, Object> map);
	
	/**
	 * 根据id 更新销量 
	 * @param map
	 * @return
	 */
	public int updateGoodsSpecificationDetailsSalesCount(Map<String,Object> map);
	
	/**
	 * 查询所有信息 结果集--客服系统所用
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectGoodsMsgToCustomService(Map<String,Object> map);
	
	/**
	 * 根据id获取商品规格编号
	 * @param id
	 * @return
	 */
	public GoodsSpecificationDetails selectSpecIdentifierById(Integer id);
	
	/**
	 * 获取购销存商品状态是否已停用    购销存上架是需判断该状态
	 * @param id
	 * @return
	 */
	public Integer selectjlgxcCommoditySpecificationIsDeleteByPrimaryKey(Integer id);
	
	/**
	 * 查询所有信息 结果集 不分页查询 供页面中根据促销进行搜索
	 * @return
	 */
	public List<GoodsSpecificationDetails> selectForSearchNoLimit(Map<String,Object> map);
	
}