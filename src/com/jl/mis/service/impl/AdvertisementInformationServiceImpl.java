package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ActivityInformationMapper;
import com.jl.mis.mapper.AdvertisementInformationMapper;
import com.jl.mis.mapper.ClassificationMapper;
import com.jl.mis.mapper.GoodsDetailsMapper;
import com.jl.mis.model.ActivityInformation;
import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.model.Classification;
import com.jl.mis.model.CouponInformation;
import com.jl.mis.model.GoodsDetails;
import com.jl.mis.service.AdvertisementInformationService;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:41:27
 * @Description 广告信息ServiceImpl
 */
@Service
public class AdvertisementInformationServiceImpl implements AdvertisementInformationService{

	@Autowired
	private AdvertisementInformationMapper advertisementInformationMapper;
	@Autowired
	private ActivityInformationMapper activityInformationMapper;
	@Autowired
	private GoodsDetailsMapper goodsDetailsMapper;
	@Autowired
	private ClassificationMapper classificationMapper;
	
	//原start
	@Override
	public int saveEntity(AdvertisementInformation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(AdvertisementInformation t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdvertisementInformation findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	//原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return advertisementInformationMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AdvertisementInformation t) throws Exception {
		
		return advertisementInformationMapper.insert(t);
	}

	@Override
	public int insertSelective(AdvertisementInformation t) throws Exception {
		
		return advertisementInformationMapper.insertSelective(t);
	}

	//APP & PC通用
	@Override
	public AdvertisementInformation selectByPrimaryKey(Integer id) {
		
		return advertisementInformationMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AdvertisementInformation t) throws Exception {
		
		return advertisementInformationMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(AdvertisementInformation t) throws Exception {
		
		return advertisementInformationMapper.updateByPrimaryKey(t);
	}

	@Override
	public DataTables getDataTables(DataTables dataTables, String identifier,
			String name, String operatorIdentifier, int type,String operatorTime) {
		// TODO Auto-generated method stub
		String[] columns = {"id", "identifier", "name", "pic_url","url_type","url_parameter_id", "effect_time","operator_identifier","operator_time","id"};
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart",
				Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength",
				Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(),
				columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorIdentifier", operatorIdentifier);
		params.put("type", type);
		params.put("operatorTime", operatorTime);
		// 获取返回的结果集---根据广告的链接类型不同分别添加链接参数信息
		List<Object> advertisementInformationList = advertisementInformationMapper.selectForSearch(params);
		AdvertisementInformation advertisementInformation;
		ActivityInformation activityInformation;
		GoodsDetails goodsDetails;
		Classification classificationTwo, classificationOne;
		for (int i = 0; i < advertisementInformationList.size(); i++) {
			advertisementInformation = (AdvertisementInformation) advertisementInformationList.get(i);
			int urlParameterId = advertisementInformation.getUrlParameterId();
			switch (advertisementInformation.getUrlType()) {
			case 0://商品  设置商品的名称、所属二级分类、所属一级分类
				//根据参数id获取相应的商品信息
				goodsDetails = goodsDetailsMapper.selectByPrimaryKey(urlParameterId);
				//根据商品信息中的分类id获取相应的二级分类信息
				classificationTwo = classificationMapper.selectByPrimaryKey(goodsDetails.getClassificationId());
				//根据二级分类信息获取一级分类信息
				classificationOne = classificationMapper.selectByPrimaryKey(classificationTwo.getParentId());
				
				//设置二级分类信息
				classificationTwo.setParentName(classificationOne.getName());
				//设置商品分类信息
				goodsDetails.setClassification(classificationTwo);
				//设置广告的商品信息
				advertisementInformation.setGoodsDetails(goodsDetails);
				
				break;
			case 1://活动  设置活动的名称及编号
				//根据参数id获取相应的活动信息
				activityInformation = activityInformationMapper.selectByPrimaryKey(urlParameterId);
				//设置广告的活动信息
				advertisementInformation.setActivityInformation(activityInformation);
				break;

			default:
				break;
			}
		}
		
		dataTables.setiTotalDisplayRecords(advertisementInformationMapper.iTotalDisplayRecords(params));// 搜索结果总行数
		dataTables.setiTotalRecords(advertisementInformationMapper.iTotalRecords(params));// 所有记录总行数
		dataTables.setData(advertisementInformationList);// 返回的结果集
		return dataTables;
	}

	@Override
	public boolean updateOneAdToEffect(
			AdvertisementInformation advertisementInformation) {
		// TODO Auto-generated method stub
		//修改其他的同类型正在生效的为失效
		advertisementInformationMapper.updateOtherAdToInvalid(advertisementInformation);
		return advertisementInformationMapper.updateOneAdToEffect(advertisementInformation);
	}
	
	/**王玉林
	 *  生效限时抢购（仅一个有效）
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOneXSQGToEffect(){
		return advertisementInformationMapper.updateOneXSQGToEffect()&&advertisementInformationMapper.updateOtherXSQGToInvalid();
	}
	
	

	@Override
	public List<AdvertisementInformation> selectMsgOrderBySearchMsg(int type,
			String identifier, String name, String operatorIdentifier, String operatorTime) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("type", type);
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorIdentifier", operatorIdentifier);
		params.put("operatorTime", operatorTime);
		
		// 获取返回的结果集---根据广告的链接类型不同分别添加链接参数信息
				List<AdvertisementInformation> advertisementInformationList = advertisementInformationMapper.selectMsgOrderBySearchMsg(params);
				AdvertisementInformation advertisementInformation;
				ActivityInformation activityInformation;
				GoodsDetails goodsDetails;
				Classification classificationTwo, classificationOne;
				for (int i = 0; i < advertisementInformationList.size(); i++) {
					advertisementInformation = (AdvertisementInformation) advertisementInformationList.get(i);
					int urlParameterId = advertisementInformation.getUrlParameterId();
					switch (advertisementInformation.getUrlType()) {
					case 0://商品  设置商品的名称、所属二级分类、所属一级分类
						//根据参数id获取相应的商品信息
						goodsDetails = goodsDetailsMapper.selectByPrimaryKey(urlParameterId);
						//根据商品信息中的分类id获取相应的二级分类信息
						classificationTwo = classificationMapper.selectByPrimaryKey(goodsDetails.getClassificationId());
						//根据二级分类信息获取一级分类信息
						classificationOne = classificationMapper.selectByPrimaryKey(classificationTwo.getParentId());
						
						//设置二级分类信息
						classificationTwo.setParentName(classificationOne.getName());
						//设置商品分类信息
						goodsDetails.setClassification(classificationTwo);
						//设置广告的商品信息
						advertisementInformation.setGoodsDetails(goodsDetails);
						
						break;
					case 1://活动  设置活动的名称及编号
						//根据参数id获取相应的活动信息
						activityInformation = activityInformationMapper.selectByPrimaryKey(urlParameterId);
						//设置广告的活动信息
						advertisementInformation.setActivityInformation(activityInformation);
						break;

					default:
						break;
					}
				}
		
		return advertisementInformationList;
	}

	@Override
	public boolean deleteAdvertisementInformationByIds(ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.deleteAdvertisementInformationByIds(list);
	}

	@Override
	public List<AdvertisementInformation> getAdvertisementInformationByIds(
			ArrayList<Integer> list) {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.getAdvertisementInformationByIds(list);
	}

	@Override
	public int selectByIdentifier(String identifier) {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.selectByIdentifier(identifier);
	}

	@Override
	public int selectNumOfHomeAdEffect() {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.selectNumOfHomeAdEffect();
	}

	@Override
	public boolean updateEffectByIdAndType(
			AdvertisementInformation advertisementInformation) {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.updateEffectByIdAndType(advertisementInformation);
	}

	@Override
	public boolean insertHotCategories(AdvertisementInformation saleAd,
			AdvertisementInformation newAd, AdvertisementInformation hotAd,
			AdvertisementInformation presaleAd) throws Exception {
		// TODO Auto-generated method stub
		 advertisementInformationMapper.deleteHotCategoriesAd();
		
		boolean result = advertisementInformationMapper.insertSelective(saleAd)>0 
					&& advertisementInformationMapper.insertSelective(newAd)>0 
					&& advertisementInformationMapper.insertSelective(hotAd)>0 
					&& advertisementInformationMapper.insertSelective(presaleAd)>0; 
		return result;
	}

	@Override
	public AdvertisementInformation selectByAdName(String name) {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.selectByAdName(name);
	}

	@Override
	public List<AdvertisementInformation> selectByTypeAndEffect(
			AdvertisementInformation advertisementInformation) {
		// TODO Auto-generated method stub
		return advertisementInformationMapper.selectByTypeAndEffect(advertisementInformation);
	}

	@Override
	public List<AdvertisementInformation> selectAdvertismentBySpecificationId(String goodsSpecificationDetailsId) {
		Map<String, Object> map = new HashMap<>();
		map.put("goodsSpecificationDetailsId", Integer.parseInt(goodsSpecificationDetailsId));
		return advertisementInformationMapper.selectAdvertismentBySpecificationId(map);
	}
   
}