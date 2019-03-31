package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ActiveCouponMapper;
import com.jl.mis.model.ActiveCoupon;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.service.ActiveCouponService;
/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午3:50:48
 * @Description  活动优惠券ServiceImpl
 */
@Service
public class ActiveCouponServiceImpl implements ActiveCouponService{
	
	@Autowired
	private ActiveCouponMapper activeCouponMapper;

	//原start
	@Override
	public int saveEntity(ActiveCoupon t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(ActiveCoupon t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ActiveCoupon findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}	
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return activeCouponMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ActiveCoupon t) throws Exception {
		
		return activeCouponMapper.insert(t);
	}

	@Override
	public int insertSelective(ActiveCoupon t) throws Exception {
		
		return activeCouponMapper.insertSelective(t);
	}

	@Override
	public ActiveCoupon selectByPrimaryKey(Integer id) {
		
		return activeCouponMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ActiveCoupon t) throws Exception {
		
		return activeCouponMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(ActiveCoupon t) throws Exception {
		
		return activeCouponMapper.updateByPrimaryKey(t);
	}

	@Override
	public boolean deleteByActivityInformationId(int activityInformationId) {
		// TODO Auto-generated method stub
		return activeCouponMapper.deleteByActivityInformationId(activityInformationId);
	}

	@Override
	public boolean deleteByActivityInformationIds(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return activeCouponMapper.deleteByActivityInformationIds(list);
	}

	//(APP & PC通用)
	@Override
	public List<CouponInformation> getMsgByActivityInformationId(
			int activityInformationId) {
		// TODO Auto-generated method stub
		return activeCouponMapper.getMsgByActivityInformationId(activityInformationId);
	}

	@Override
	public List<CouponInformation> selectCouponIdentifierByCouponId(
			ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return activeCouponMapper.selectCouponIdentifierByCouponId(list);
	}

	@Override
	public List<ActiveCoupon> selectByCouponId(int couponId) {
		// TODO Auto-generated method stub
		return activeCouponMapper.selectByCouponId(couponId);
	}
	
	@Override
	public List<CouponInformation> getShoppingBackCouponByActivityInformationId(
			int activityInformationId) {
		// TODO Auto-generated method stub
		return activeCouponMapper.getShoppingBackCouponByActivityInformationId(activityInformationId);
	}

}