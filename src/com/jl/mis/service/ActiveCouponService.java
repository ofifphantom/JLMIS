package com.jl.mis.service;

import java.util.ArrayList;
import java.util.List;

import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午3:50:48
 * @Description  活动优惠券Service
 */
public interface ActiveCouponService extends BaseService<ActiveCoupon>{
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
	public List<CouponInformation> getMsgByActivityInformationId(int activityInformationId);
	
	/**
	 *根据优惠券id获取优惠券名称
	 * @param activityInformationId
	 * @return
	 */
	public List<CouponInformation> selectCouponIdentifierByCouponId(ArrayList<Integer> list);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------APP------------------------------------
	
	/**
	 * 根据优惠券id查询信息
	 * @param couponId 优惠券id
	 * @return List<ActiveCoupon>
	 */
	public List<ActiveCoupon> selectByCouponId(int couponId);
	
	
	/**
	 * 根据活动信息id 获取购物返券数据
	 * @param activityInformationId
	 * @return
	 */
	public List<CouponInformation> getShoppingBackCouponByActivityInformationId(int activityInformationId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}