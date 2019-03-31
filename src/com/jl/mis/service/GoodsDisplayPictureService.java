package com.jl.mis.service;

import java.util.List;
import java.util.Map;

import com.jl.mis.mapper.GoodsDisplayPictureMapper;
import com.jl.mis.model.GoodsDisplayPicture;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:46:42
 * @Description 商品展示图片Service
 */
public interface GoodsDisplayPictureService extends BaseService<GoodsDisplayPicture> {

	/**
	 * 根据规格id批量删除 信息
	 * 
	 * @param
	 * @return
	 */
	public Integer deleteBatchByGoodsSpecificationDetailId(String[] goodsSpecificationDetailId);

	/**
	 * 根据规格id批量查询信息
	 * 
	 * @param
	 * @return
	 */
	public List<GoodsDisplayPicture> selectBatchByGoodsSpecificationDetailId(String[] goodsSpecificationDetailId);

	// 接口 移动端使用

	/**
	 * 根据商品详情获取商品详情对应的图片
	 * 
	 * @param map
	 * @return
	 */
	public List<GoodsDisplayPicture> getGoodsDisplayPicture(Integer goodsSpecificationDetailId);

}