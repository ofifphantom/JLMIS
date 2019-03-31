package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.GoodsDetails;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:46:24
 * @Description 商品详情Service
 */
public interface GoodsDetailsService extends BaseService<GoodsDetails> {

	/**
	 * 根据分类ID查询商品
	 * 
	 * @param classificationId
	 *            分类ID
	 * @return
	 */
	public List<GoodsDetails> getGoodsListByClassificationId(Integer classificationId);

	/**
	 * 根据名称查询商品
	 * 
	 * @param name
	 *            商品名称
	 * @return
	 */
	public GoodsDetails selectGoodsAndClassificationByName(String name);

	/**
	 * 根据主键批量更新内容（商品的推荐操作）
	 * 
	 * @return
	 */
	public Integer updateBatchByPrimaryKey(List<Integer> primaryKey, String recomTime);

	/**
	 * 查询所有商品信息
	 * 
	 * @return
	 */
	public List<GoodsDetails> selectAllGoodsDetails(int classificationId);

	// 接口 移动端使用
	/**
	 * 查询app首页的热门推荐
	 * 
	 * @return
	 */
	public List<GoodsDetails> getHomeHotGoods(int classificationId,String searchName,int sortType);
	/**
	 * 查询app中的新品上架商品list 
	 * 
	 * @return
	 */
	public List<GoodsDetails> getGoodsList_Ameliorate(int classificationId,String searchName,int sortType);
	
	/**
	 * 根据商品id获取商品的信息以及有关商品的评价信息
	 * @param map
	 * @return
	 */
	public GoodsDetails getGoodsDetailAndEvaluationByGoodsId(int id);
	
	/**
	 * 热门搜索词 销量前十的商品关键词
	 * @return
	 */
	public List<GoodsDetails> selectHotSearchWord();
	
	/**
	 * 根据购销存商品id获取mis商品id
	 * @param commodityId
	 * @return
	 */
	public GoodsDetails selectIdByCommodityId(Integer commodityId);
	
	/**
	 * 根据分类id查询预售的商品信息
	 * @param classificationId
	 * @return
	 */
	public List<GoodsDetails> selectPresellGoodsByClassificationId(Integer classificationId);
	
	/**
	 * 根据分类id查询正常（不是预售）的商品信息 
	 * @param classificationId
	 * @return
	 */
	public List<GoodsDetails> selectNotPresellGoodsByClassificationId(Integer classificationId);
	
	/**
	 * 获取品牌信息
	 * @return
	 */
	List<String> selectBrand();

}