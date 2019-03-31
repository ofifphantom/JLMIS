package com.jl.mis.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.model.CouponInformation;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:41:27
 * @Description 广告信息Mapper
 */
public interface AdvertisementInformationMapper extends BaseMapper<AdvertisementInformation>{
	
	/**
	 *  生效广告（仅一个有效）
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOneAdToEffect(AdvertisementInformation advertisementInformation);
	
	/**
	 *  生效广告（仅一个有效）时  失效其他广告
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOtherAdToInvalid(AdvertisementInformation advertisementInformation);
	
	/**王玉林
	 *  生效限时抢购（仅一个有效）
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOneXSQGToEffect();
	
	/**王玉林
	 *  生效限时抢购（仅一个有效）时  失效其他广告
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOtherXSQGToInvalid();
	
	/**
	 * 根据参数查询所有信息
	 * @return
	 */
	public List<AdvertisementInformation> selectMsgOrderBySearchMsg(Map<String,Object> map);
	
	/**
	 * 根据id列表删除广告信息
	 * @param list id列表
	 * @return true/false 删除成功或失败
	 */
	public boolean deleteAdvertisementInformationByIds(ArrayList<Integer> list);
	
	/**
	 * 根据id列表获取广告信息
	 * @param map id列表
	 * @return List<AdvertisementInformation>
	 */
	public List<AdvertisementInformation> getAdvertisementInformationByIds(ArrayList<Integer> list);
	
	/**
	 * 根据广告编号查询广告id
	 * @return
	 */
	public int selectByIdentifier(String identifier);
	
	/**
	 * 查询已生效的首页广告（type = 1）的数量
	 * @return
	 */
	public int selectNumOfHomeAdEffect();
	
	/**
	 *  根据id和type更改广告是否有效
	 * @param advertisementInformation  （包含effect id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateEffectByIdAndType(AdvertisementInformation advertisementInformation);
	
	/**
	 * 删除热门分类广告信息
	 * @return true/false 删除成功或失败
	 */
	public boolean deleteHotCategoriesAd();
	
	/**
	 * 根据广告名称和type=4 查询广告图片url
	 * @return
	 */
	public AdvertisementInformation selectByAdName(String name);
	
	/**
	 * 根据商品详情id查询其所属的商品是否在广告中
	 * @param map
	 * @return
	 */
	public List<AdvertisementInformation> selectAdvertismentBySpecificationId(Map<String, Object> map);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------APP------------------------------------
	/**
	 * 根据广告类型和是否有效获取广告信息
	 * @param map id列表
	 * @return List<AdvertisementInformation>
	 */
	public List<AdvertisementInformation> selectByTypeAndEffect(AdvertisementInformation advertisementInformation);
   
}