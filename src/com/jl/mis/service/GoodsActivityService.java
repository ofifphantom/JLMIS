package com.jl.mis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.GoodsActivity;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:46:14
 * @Description 商品活动Service
 */
public interface GoodsActivityService extends BaseService<GoodsActivity>{
	/**
	 * 根据活动信息id删除数据
	 * @param activityInformationId
	 * @return
	 */
	public boolean deleteByActivityInformationId(int activityInformationId);

	
	/**
	 * 根据活动信息id数组删除数据
	 * @param activityInformationId
	 * @return
	 */
	public boolean deleteByActivityInformationIds(ArrayList<Integer> list);
	
	/**
	 * 根据活动信息id 获取数据
	 * (APP & PC通用)
	 * @param activityInformationId
	 * @return
	 */
	public List<GoodsActivity> getMsgByActivityInformationId(int activityInformationId);
	
	/**
	 * 根据活动信息id 并且state=0  联合查询获取数据
	 * (APP & PC通用)
	 * @param activityInformationId
	 * @return
	 */
	public GoodsActivity getMsgByActivityInformationId0(int activityInformationId,int goodsId);
	
	/**
	 * 根据活动信息id 并且state=1  联合查询获取数据
	 * (APP & PC通用)
	 * @param activityInformationId
	 * @return
	 */
	public GoodsActivity getMsgByActivityInformationId1(int activityInformationId,int goodsId);
	
	/**
	 * 根据活动信息id 并且state=2  联合查询获取数据
	 * (APP & PC通用)
	 * @param activityInformationId
	 * @return
	 */
	public GoodsActivity getMsgByActivityInformationId2(int activityInformationId,int goodsId);
	
	/**
	 * 根据商品Id或者分类ID查询商品所参加的活动信息
	 * @param map
	 * @return
	 */
	public List<GoodsActivity> getGoodsActivityMsgByStateAndGoodsId(int goodsId,String isGoodsId);
	
	/**
	 * 根据商品详情ID查找所参与的活动
	 * @param map
	 * @return
	 */
	public List<GoodsActivity> selectGoodsActivityBySpecificationId(String goodsSpecificationDetailsId,String isGoodsId);
	
}