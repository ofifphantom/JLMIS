package com.jl.mis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jl.mis.model.AdvertisementInformation;
import com.jl.mis.utils.DataTables;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午4:41:27
 * @Description 广告信息Service
 */
public interface AdvertisementInformationService extends BaseService<AdvertisementInformation>{
	/**
	 * 广告信息dataTables
	 * @param request
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @param activityType 广告类型
	 * @return
	 */
	public DataTables getDataTables(DataTables dataTables, String identifier, String name, String operatorIdentifier, int type,String operatorTime);
	
	/**
	 *  生效广告（仅一个有效）
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOneAdToEffect(AdvertisementInformation advertisementInformation);
	
	/**王玉林
	 *  生效限时抢购（仅一个有效）
	 * @param advertisementInformation  （包含id type identifier）
	 * @return true/false 更改成功或失败
	 */
	public boolean updateOneXSQGToEffect();
	/**
	 * 根据参数查询所有信息
	 * @param type 广告类型  （0：开屏，1：首页广告，2：分类广告，3：限时抢购，4：热门分类）
	 * @param identifier 编号
	 * @param name 名称
	 * @param operatorIdentifier 操作人
	 * @return
	 */
	public List<AdvertisementInformation> selectMsgOrderBySearchMsg(int type, String identifier, String name, String operatorIdentifier, String operatorTime);
	
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
	 * 生效热门分类广告
	 * @param saleAd 火热促销
	 * @param newAd  新品上架
	 * @param hotAd  君霖热卖
	 * @param presaleAd  爆品预售
	 * @return  
	 * @throws Exception
	 */
	public boolean insertHotCategories(AdvertisementInformation saleAd,AdvertisementInformation newAd,AdvertisementInformation hotAd,AdvertisementInformation presaleAd)throws Exception;
   
	
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
	public List<AdvertisementInformation> selectAdvertismentBySpecificationId(String goodsSpecificationDetailsId);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//-------------------------------------APP------------------------------------
	/**
	 * 根据广告类型和是否有效获取广告信息
	 * @param map id列表
	 * @return List<AdvertisementInformation>
	 */
	public List<AdvertisementInformation> selectByTypeAndEffect(AdvertisementInformation advertisementInformation);
	
	
	
	
	
	
	
	
	
	
	
	
	
}