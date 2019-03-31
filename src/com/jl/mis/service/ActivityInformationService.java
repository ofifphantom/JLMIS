package com.jl.mis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.Classification;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:40:58
 * @Description 活动信息Service
 */
public interface ActivityInformationService extends BaseService<ActivityInformation>{
	/**
	 * 活动信息dataTables
	 * @param dataTables
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @param activityType 活动类型
	 * @param state 状态
	 * @param flag 标志 1：活动管理页面   2:活动审核页面
	 * @return
	 */
	public DataTables getDataTables(DataTables dataTables, String identifier, String name, String operatorIdentifier, int activityType, int state,String flag,String operatorTime);
	
	/**
	 * 根据id列表删除活动信息
	 * @param list id列表
	 * @return true/false 删除成功或失败
	 */
	public boolean deleteActivityInformationByIds(ArrayList<Integer> list);
	
	/**
	 * 根据id列表更改活动状态
	 * @param map id列表、修改的状态
	 * @return true/false 更改成功或失败
	 */
	public boolean updateActivityInformationStateByIds(Map<String, Object> map);
	
	
	/**
	 * 根据id列表获取活动信息
	 * @param map id列表
	 * @return List<ActivityInformation>
	 */
	public List<ActivityInformation> getActivityInformationByIds(ArrayList<Integer> list);
	/**
	 * 根据id列表获取活动列表中是否有与进行活动关联的
	 * @param map id列表
	 * @return List<ActivityInformation>
	 */
	public List<ActivityInformation> getActivityInformationIsContactToADByIds(ArrayList<Integer> list);
	/**
	 * 根据编号获取活动id	
	 */
	public int getIdByIdentifier(String identifier);
	/**
	 * 根据参数查询所有信息
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @param activityType 活动类型
	 * @param state 状态
	 * @param flag 标志 1：活动管理页面   2:活动审核页面
	 * @return
	 */
	public List<ActivityInformation> selectMsgOrderBySearchMsg(String identifier, String name, String operatorIdentifier , int activityType, int state,String flag,String operatorTime);
    
	/**
	 * 获取所有的审核通过的活动信息
	 * @return List<AcivityInformation>
	 */
	public List<ActivityInformation> getAllPassActivityInformation();
	
	/**
	 * 获取所有的审核通过的秒杀活动信息（activityType==2）
	 * @return List<AcivityInformation>
	 */
	public List<ActivityInformation> getAllPassActivityInformationByType2();	
	
	/**
	 * 活动自动上线 （state==2 && 当前时间>=有效期开始时间）
	 */
	public void updateActivityInformationToOnline()  throws Exception;
	/**
	 * 活动自动下线 （state==4 && 当前时间>有效期结束时间）
	 */
	public void updateActivityInformationToOffline();
	/**
	 * 活动自动失效 （state==1 && 当前时间>有效期结束时间）
	 */
	public void updateActivityInformationToInvalid();
	
	/**
	 * 获取所有正在生效的预售活动信息
	 * @return List<AcivityInformation>
	 */
	public List<ActivityInformation> getAllEffectPreSellActivityInformation();
	
	/**
	 * 根据商品id和优惠券id获取该优惠券关联的&正在上线的预售活动信息 并根据结束时间正序排序
	 * @param goodsDetailsId
	 * @return
	 */
	public List<ActivityInformation> getAllEffectPreSellActivityInformationByGoodsDetailsIdAndCouponId(Map<String, Object> map);
	
	/**
	 * 根据商品id该活动参与的正在上线的预售活动信息 并根据结束时间正序排序
	 * @param goodsDetailsId
	 * @return
	 */
	public List<ActivityInformation> getAllEffectPreSellActivityInformationByGoodsDetailsId(int goodsDetailsId);
	
	/**
	 * 根据id列表更改活动提交人&提交时间状态
	 * @param map id列表、提交人、提交时间
	 * @return true/false 更改成功或失败
	 */
	public boolean updateSubmitInfoByIds(Map<String, Object> map);
	
}