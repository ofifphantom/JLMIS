package com.jl.mis.mapper;

import java.util.List;
import java.util.Map;

import com.jl.mis.model.Classification;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.HotCategoriesGood;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:47:32
 * @Description 热门分类商品Mapper
 */
public interface HotCategoriesGoodMapper extends BaseMapper<HotCategoriesGood>{
	/**
	 * 删除全部信息
	 * @return true/false 删除成功或失败
	 */
	public boolean deleteAll();
	
	/**
	 * 根据广告信息id查询信息
	 * (APP & PC通用)
	 * @return
	 */
	public List<HotCategoriesGood> selectByAdvertisementInformationId(int advertisementInformationId);
	
	/**
	 * 根据广告信息id 并且state=1  联合查询获取数据
	 * @param activityInformationId
	 * @return
	 */
	public HotCategoriesGood getMsgByAdvertisementInformationId1(Map<String, Integer> map);
	
	/**
	 * 根据广告信息id 并且state=2  联合查询获取数据
	 * @param activityInformationId
	 * @return
	 */
	public HotCategoriesGood getMsgByAdvertisementInformationId2(Map<String, Integer> map);
	
	/**
	 * 根据商品分类Id查询热门分类商品
	 * @param map
	 * @return
	 */
	public List<HotCategoriesGood> selectHotCategoriesGoodBySpecificationId(Map<String, Object> map);
	/**
	 * 查询首页新品上架
	 * @param map
	 * @return
	 */
	public List<Classification> getNews_List();

}