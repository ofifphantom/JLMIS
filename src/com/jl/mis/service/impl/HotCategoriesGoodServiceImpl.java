package com.jl.mis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.HotCategoriesGoodMapper;
import com.jl.mis.model.Classification;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.HotCategoriesGood;
import com.jl.mis.service.HotCategoriesGoodService;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:47:32
 * @Description 热门分类商品ServiceImpl
 */
@Service
public class HotCategoriesGoodServiceImpl implements HotCategoriesGoodService {

	@Autowired
	private HotCategoriesGoodMapper hotCategoriesGoodMapper;

	// 原start
	@Override
	public int saveEntity(HotCategoriesGood t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(HotCategoriesGood t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public HotCategoriesGood findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	// 原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(HotCategoriesGood t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.insert(t);
	}

	@Override
	public int insertSelective(HotCategoriesGood t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.insertSelective(t);
	}

	@Override
	public HotCategoriesGood selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(HotCategoriesGood t)
			throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(HotCategoriesGood t) throws Exception {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.updateByPrimaryKey(t);
	}

	@Override
	public void insertHotCategoriesGoods(Map<String, Object> map) throws Exception{
		// TODO Auto-generated method stub
		hotCategoriesGoodMapper.deleteAll();
		
		// 火热促销
		String saleGoods = map.get("saleGoods").toString();
		int saleAdId = (int) map.get("saleAdId");
		if (!("".equals(saleGoods))) {
			String[] goodsId = saleGoods.split(",");
			String[] goodsState = map.get("saleState").toString().split(",");
			HotCategoriesGood hotCategoriesGood = new HotCategoriesGood();
			hotCategoriesGood.setAdvertisementInformationId(saleAdId);
			for (int i = 0; i < goodsId.length; i++) {
				hotCategoriesGood.setGoodsId(Integer.parseInt(goodsId[i]));
				hotCategoriesGood.setState(Integer.parseInt(goodsState[i]));
				hotCategoriesGoodMapper.insert(hotCategoriesGood);
			}
		}
		// 新品上架
		String newGoods = map.get("newGoods").toString();
		int newAdId = (int) map.get("newAdId");
		if (!("".equals(newGoods))) {
			String[] goodsId = newGoods.split(",");
			String[] goodsState = map.get("newState").toString().split(",");
			HotCategoriesGood hotCategoriesGood = new HotCategoriesGood();
			hotCategoriesGood.setAdvertisementInformationId(newAdId);
			for (int i = 0; i < goodsId.length; i++) {
				hotCategoriesGood.setGoodsId(Integer.parseInt(goodsId[i]));
				hotCategoriesGood.setState(Integer.parseInt(goodsState[i]));
				hotCategoriesGoodMapper.insert(hotCategoriesGood);
			}
		}
		// 君霖热卖
		String hotGoods = map.get("hotGoods").toString();
		int hotAdId = (int) map.get("hotAdId");
		if (!("".equals(hotGoods))) {
			String[] goodsId = hotGoods.split(",");
			String[] goodsState = map.get("hotState").toString().split(",");
			HotCategoriesGood hotCategoriesGood = new HotCategoriesGood();
			hotCategoriesGood.setAdvertisementInformationId(hotAdId);
			for (int i = 0; i < goodsId.length; i++) {
				hotCategoriesGood.setGoodsId(Integer.parseInt(goodsId[i]));
				hotCategoriesGood.setState(Integer.parseInt(goodsState[i]));
				hotCategoriesGoodMapper.insert(hotCategoriesGood);
			}
		}
		// 爆品预售
		String presaleGoods = map.get("presaleGoods").toString();
		int presaleAdId = (int) map.get("presaleAdId");
		if (!("".equals(presaleGoods))) {
			String[] goodsId = presaleGoods.split(",");
			String[] goodsState = map.get("presaleState").toString().split(",");
			HotCategoriesGood hotCategoriesGood = new HotCategoriesGood();
			hotCategoriesGood.setAdvertisementInformationId(presaleAdId);
			for (int i = 0; i < goodsId.length; i++) {
				hotCategoriesGood.setGoodsId(Integer.parseInt(goodsId[i]));
				hotCategoriesGood.setState(Integer.parseInt(goodsState[i]));
				hotCategoriesGoodMapper.insert(hotCategoriesGood);
			}
		}
		
	}

	//(APP & PC通用)
	@Override
	public List<HotCategoriesGood> selectByAdvertisementInformationId(
			int advertisementInformationId) {
		// TODO Auto-generated method stub
		return hotCategoriesGoodMapper.selectByAdvertisementInformationId(advertisementInformationId);
	}
	
	@Override
	public HotCategoriesGood getMsgByAdvertisementInformationId1(
			int advertisementInformationId,int goodsId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("advertisementInformationId", advertisementInformationId);
		map.put("goodsId", goodsId);
		return hotCategoriesGoodMapper.getMsgByAdvertisementInformationId1(map);
	}

	@Override
	public HotCategoriesGood getMsgByAdvertisementInformationId2(
			int advertisementInformationId,int goodsId) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("advertisementInformationId", advertisementInformationId);
		map.put("goodsId", goodsId);
		return hotCategoriesGoodMapper.getMsgByAdvertisementInformationId2(map);
	}

	@Override
	public List<HotCategoriesGood> selectHotCategoriesGoodBySpecificationId(String goodsSpecificationDetailsId) {
		Map<String, Object> map = new HashMap<>();
		map.put("goodsSpecificationDetailsId", Integer.parseInt(goodsSpecificationDetailsId));
		return hotCategoriesGoodMapper.selectHotCategoriesGoodBySpecificationId(map);
	}
	
	/**
	 * 查询首页新品上架
	 * @param map
	 * @return
	 */
	public List<Classification> getNews_List(){
		return hotCategoriesGoodMapper.getNews_List();
	}
}