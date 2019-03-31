package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.UserCouponsMapper;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.UserCoupons;
import com.jl.mis.service.UserCouponsService;
/**
 * 用户优惠券ServiceImpl
 * @author 景雅倩
 * @date  2017-11-3  下午3:55:34
 * @Description TODO
 */
@Service
public class UserCouponsServiceImpl implements UserCouponsService{

	@Autowired
	private UserCouponsMapper userCouponsMapper;
	
	@Override
	public int saveEntity(UserCoupons t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(UserCoupons t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserCoupons findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userCouponsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserCoupons t) throws Exception {
		// TODO Auto-generated method stub
		return userCouponsMapper.insert(t);
	}

	//APP & PC通用
	@Override
	public int insertSelective(UserCoupons t) throws Exception {
		// TODO Auto-generated method stub
		return userCouponsMapper.insertSelective(t);
	}

	@Override
	public UserCoupons selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return userCouponsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserCoupons t) throws Exception {
		// TODO Auto-generated method stub
		return userCouponsMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(UserCoupons t) throws Exception {
		// TODO Auto-generated method stub
		return userCouponsMapper.updateByPrimaryKey(t);
	}

	@Override
	public boolean updateStatusByCouponInformationId(List<Integer> couponInformationIds) {
		// TODO Auto-generated method stub
		return userCouponsMapper.updateStatusByCouponInformationId(couponInformationIds);
	}

	@Override
	public List<CouponInformation> selectCouponIdentifierByCouponId(
			ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return userCouponsMapper.selectCouponIdentifierByCouponId(list);
	}

	@Override
	public int selectByUserIdAndcouponId(UserCoupons userCoupons) {
		// TODO Auto-generated method stub
		return userCouponsMapper.selectByUserIdAndcouponId(userCoupons);
	}

	@Override
	public List<UserCoupons> selectUsableCouponByUserId(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.selectUsableCouponByUserId(userId);
	}

	@Override
	public List<UserCoupons> selectDisabledCouponByUserId(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.selectDisabledCouponByUserId(userId);
	}

	@Override
	public void updateUserCouponsToInvalid() {
		// TODO Auto-generated method stub
		userCouponsMapper.updateUserCouponsToInvalid();
	}

	@Override
	public Integer updateByUserIdAndId(UserCoupons userCoupons) {
		// TODO Auto-generated method stub
		return userCouponsMapper.updateByUserIdAndId(userCoupons);
	}

	@Override
	public Integer getNotReadOrderNUM(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.getNotReadOrderNUM(userId);
	}

	@Override
	public Integer getNotReadActivityNUM(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.getNotReadActivityNUM(userId);
	}

	@Override
	public  Integer getNotReadUserMessageNUM(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.getNotReadUserMessageNUM(userId);
	}
	@Override
	public  Map<String,Object> getNewReadUserMessageTime(int userId){
		// TODO Auto-generated method stub
		return userCouponsMapper.getNewReadUserMessageTime(userId);
	}
	@Override
	public Integer deldectActivityMessage(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.deldectActivityMessage(userId);
	}

	@Override
	public Integer deldectUserMessage(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.deldectUserMessage(userId);
	}

	@Override
	public Integer deldectOrderMessage(int userId) {
		// TODO Auto-generated method stub
		return userCouponsMapper.deldectOrderMessage(userId);
	}

}