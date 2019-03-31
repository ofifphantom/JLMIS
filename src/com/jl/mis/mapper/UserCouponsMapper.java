package com.jl.mis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.UserCoupons;
/**
 * 用户优惠券mapper
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:34
 * @Description TODO
 */
public interface UserCouponsMapper extends BaseMapper<UserCoupons>{
	
	/**
	 * 根据优惠券id更新所有优惠券状态
	 * @param couponInformationIds
	 * @return
	 */
	public boolean updateStatusByCouponInformationId(List<Integer> couponInformationIds);
	
	/**
	 *根据优惠券id获取优惠券名称
	 * @param activityInformationId
	 * @return
	 */
	public List<CouponInformation> selectCouponIdentifierByCouponId(ArrayList<Integer> list);
	
	/**
	 * 用户优惠券自动失效 （status==0 && 当前时间>优惠券有效期结束时间）
	 */
	public void updateUserCouponsToInvalid();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------APP------------------------------------
		/**
		 * 根据广告类型和是否有效获取广告信息
		 * @param userCoupons 包含用户id和优惠券id
		 * @return 查询结果数量
		 */
		public int selectByUserIdAndcouponId(UserCoupons userCoupons);
		
		/**
		 * 根据用户id查询该用户可以使用的优惠券信息
		 * @param userId 用户id
		 * @return List<UserCoupons>
		 */
		public List<UserCoupons> selectUsableCouponByUserId(int userId);
		
		/**
		 * 根据用户id查询该用户不可使用的优惠券信息
		 * @param userId 用户id
		 * @return List<UserCoupons>
		 */
		public List<UserCoupons> selectDisabledCouponByUserId(int userId);
		
		/**
		 * 根据id和用户id修改状态为已使用和使用时间
		 * @param couponInformationIds
		 * @return
		 */
		public Integer updateByUserIdAndId(UserCoupons userCoupons);
		
		/**
		 * 获取未读订单信息的个数
		 * @param userId 用户id
		 * @return 
		 */
		public Integer getNotReadOrderNUM(int userId);
		/**
		 * 获取未读活动信息的个数
		 * @param userId 用户id
		 * @return 
		 */
		public Integer getNotReadActivityNUM(int userId);
		/**
		 * 获取未读客服信息的个数
		 * @param userId 用户id
		 * @return 
		 */
		public Integer getNotReadUserMessageNUM(int userId);
		
		/**
		 * 获取客服信息最新的时间
		 * @param userId 用户id
		 * @return 
		 */
		public  Map<String,Object> getNewReadUserMessageTime(int userId);
		
		
		/**
		 * 删除所有的消息
		 * @param userId 用户id
		 * @return 
		 */
		
		public Integer deldectActivityMessage(int userId);
		/**
		 * 删除所有的消息
		 * @param userId 用户id
		 * @return 
		 */
		
		public Integer deldectUserMessage(int userId);
		/**
		 * 删除所有的消息
		 * @param userId 用户id
		 * @return 
		 */
		
		public Integer deldectOrderMessage(int userId);
		
		
		
		
		
		
}