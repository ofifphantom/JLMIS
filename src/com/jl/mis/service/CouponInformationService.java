package com.jl.mis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:44:58
 * @Description 优惠券信息Service
 */
public interface CouponInformationService extends BaseService<CouponInformation>{
	
	/**
	 * 优惠券信息dataTables
	 * @param dataTables 
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @return  dateTables
	 */
	 
	public DataTables getDataTables(DataTables dataTables,int flag, String identifier, String name, String operatorIdentifier,String operatorTime);
	
	
	/**
	 * 根据id列表删除优惠券信息
	 * @param list id列表
	 * @return true/false 删除成功或失败
	 */
	public boolean deleteCouponInformationByIds(ArrayList<Integer> list);
	
	/**
	 * 根据id列表更改优惠券状态
	 * @param map id列表、修改的状态
	 * @return true/false 更改成功或失败
	 */
	public boolean updateCouponInformationStateByIds(Map<String, Object> map);
	
	/**
	 * 根据id列表获取优惠券信息
	 * @param map id列表
	 * @return List<CouponInformation>
	 */
	public List<CouponInformation> getCouponInformationByIds(ArrayList<Integer> list);
	
	/**
	 * 获取所有优惠券信息
	 * @return List<CouponInformation>
	 */
	public List<CouponInformation> selectAllNormal();
	
	/**
	 * 根据参数查询所有信息
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @return
	 */
	public List<CouponInformation> selectMsgOrderBySearchMsg(String identifier, String name, String operatorIdentifier,String operatorTime);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------APP------------------------------------
		/**
		 * 查询所有可以领取的优惠券信息（即：当前时间在领取时间内 && 剩余数量>0）
		 * @return
		 */
		public List<CouponInformation> getAllAvailableCoupon();
	
	
		/**
		 * 查询所有当前可以领取的注册返券优惠券信息（即：当前时间在领取时间内 && 剩余数量>0 && 规则=注册返券）
		 * @return
		 */
		public List<Integer> selectAllAvailableCouponForRegist();
		
	
	
	
	
	
}