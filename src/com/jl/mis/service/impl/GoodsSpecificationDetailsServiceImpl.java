package com.jl.mis.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.ClassificationMapper;
import com.jl.mis.mapper.GoodsActivityMapper;
import com.jl.mis.mapper.GoodsCommentMapper;
import com.jl.mis.mapper.GoodsSpecificationDetailsMapper;
import com.jl.mis.model.GoodsActivity;
import com.jl.mis.model.GoodsComment;
import com.jl.mis.model.GoodsSpecificationDetails;
import com.jl.mis.service.GoodsSpecificationDetailsService;
import com.jl.mis.utils.DataTables;
import com.jl.mis.utils.SundryCodeUtil;

/**
 * 
 * @author 柳亚婷
 * @date 2017年11月3日 下午4:47:05
 * @Description 商品规格详情ServiceImpl
 */
@Service
public class GoodsSpecificationDetailsServiceImpl implements GoodsSpecificationDetailsService {

	@Autowired
	private GoodsSpecificationDetailsMapper goodsSpecificationDetailsMapper;
	@Autowired
	private GoodsActivityMapper goodsActivityMapper;
	@Autowired
	private ClassificationMapper classificationMapper;
	@Autowired
	private GoodsCommentMapper goodsCommentMapper;

	// 原start
	@Override
	public int saveEntity(GoodsSpecificationDetails t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DataTables getGoodsComment(HttpServletRequest request, DataTables dataTables, Integer gsdId) {
		// TODO Auto-generated method stub
		String path = request.getContextPath();
		String basePath = path + "/";
		String[] columns = { "id" };
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("iDisplayStart", Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength", Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		params.put(dataTables.getsSortDir_0(), columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		params.put("gsdId", gsdId);
		List<Object> goodsCommentList = new ArrayList<>();
		List<GoodsComment> gc = new ArrayList<>();
		Set<Object> id = new ConcurrentSkipListSet<>();

		Map<String, GoodsComment> map = new HashMap<>();

		List<GoodsComment> resultTableList = new ArrayList<>();
		goodsCommentList = goodsCommentMapper.selectForSearch(params);
		for (int i = 0; i < goodsCommentList.size(); i++) {
			id.add(0);

			GoodsComment comments = (GoodsComment) goodsCommentList.get(i);
			// System.out.println(comments.toString());
			// Iterator<Object> commentId=id.iterator();
			for (Object commentId : id) {

				String commentUrl = "";

				// Object cc=commentId;

				if (comments.getId().equals(commentId)) {
					String Url = comments.getCommentUrl();
					commentUrl = "<img onclick=\"evaluatePic()\" class=\"img-responsive\" layer-src=\"" + basePath + ""
							+ Url + "\" src=\"" + basePath + "" + Url + "\" alt=\"\" />";

					GoodsComment cc = map.get(String.valueOf(comments.getId()));

					String sameId = cc.getImgUrl();
					sameId += "<img onclick=\"evaluatePic()\" class=\"img-responsive\" layer-src=\"" + basePath + ""
							+ Url + "\" src=\"" + basePath + "" + Url + "\" alt=\"\" />";
					if (commentUrl.equals(cc.getImgUrl())) {

					} else {
						comments.setImgUrl(sameId);
						map.put(String.valueOf(comments.getId()), comments);
						// System.out.println(comments.toString());
						break;
					}

				} else {
					id.remove(0);
					String Url = comments.getCommentUrl();

					commentUrl = "<img onclick=\"evaluatePic()\" class=\"img-responsive\" layer-src=\"" + basePath + ""
							+ Url + "\" src=\"" + basePath + "" + Url + "\" alt=\"\" />";
					// System.out.println("else>>>>"+Url);
					if (map.get(String.valueOf(comments.getId())) == null) {
						if (Url != null) {
							comments.setImgUrl(commentUrl);
						}

						map.put(String.valueOf(comments.getId()), comments);
						// System.out.println(comments.toString());
					}

					id.add(comments.getId());

					// id.remove(cc);
				}
			}
			// comments.setCommentUrl(map.get(String.valueOf(comments.getId())));
			// System.out.println(comments.getCommentUrl());
		}
		// 对list排序

		// System.out.println(resultTableList);
		for (GoodsComment v : map.values()) {
			// resultTableList.add(v);
			gc.add(v);
		}
		Collections.sort(gc, new Comparator<GoodsComment>() {

			public int compare(GoodsComment o1, GoodsComment o2) {

				// 按照id降序排列
				if (o1.getId() < o2.getId()) {
					return 1;
				}
				if (o1.getId() == o2.getId()) {
					return 0;
				}
				return -1;
			}
		});
		if ((gc.size() - Integer.parseInt(dataTables.getiDisplayStart())) >= Integer
				.parseInt(dataTables.getPageDisplayLength())) {

			for (int i = Integer.parseInt(dataTables.getiDisplayStart()); i < Integer.parseInt(
					dataTables.getiDisplayStart()) + Integer.parseInt(dataTables.getPageDisplayLength()); i++) {
				resultTableList.add(gc.get(i));
			}
		} else {

			for (int i = 0; i < gc.size(); i++) {
				// System.out.println((GoodsComment)gc.get(i));
				resultTableList.add(gc.get(i));
			}
		}

		dataTables.setiTotalDisplayRecords(goodsCommentMapper.iTotalDisplayRecords(params));
		dataTables.setiTotalRecords(goodsCommentMapper.iTotalRecords(params));
		dataTables.setData(resultTableList);
		return dataTables;
	}

	@Override
	public int deleteEntity(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEntity(GoodsSpecificationDetails t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public GoodsSpecificationDetails findEntityById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	// 原end

	@Override
	public int deleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(GoodsSpecificationDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.insert(t);
	}

	@Override
	public int insertSelective(GoodsSpecificationDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.insertSelective(t);
	}

	@Override
	public GoodsSpecificationDetails selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(GoodsSpecificationDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int updateByPrimaryKey(GoodsSpecificationDetails t) throws Exception {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.updateByPrimaryKey(t);
	}

	@Override
	public DataTables getManagerMsg(DataTables dataTables, String identifier, String name, String operatorName,
			String gxcGoodsStock, String oneOrTwo, String classificationId, String activityName, String operatorTime) {
		String[] columns = { "gsd.id", "gsd.identifier", "gd.NAME", "gsd.price", "gsd.specifications", "gxcgoods.stock",
				"participateActivities", "gsd.operatorIdentifier", "gsd.operatorTime", "id" };
		Map<String, Object> params = new HashMap<String, Object>();// 传给Mapper的参数
		params.put("iDisplayStart", Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
		params.put("pageDisplayLength", Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
		System.out.println("iDisplayStart:"+Integer.parseInt(dataTables.getiDisplayStart()));
		System.out.println("pageDisplayLength:"+Integer.parseInt(dataTables.getPageDisplayLength()));
		params.put(dataTables.getsSortDir_0(), columns[Integer.parseInt(dataTables.getiSortCol_0())]);// 获取需要的列和对应的排序方式
		// 保存搜索词
		params.put("identifier", identifier);
		params.put("name", name);
		params.put("operatorName", operatorName);
		params.put("operatorTime", operatorTime);
		// 库存select选择的id(有库存/无库存)
		params.put("gxcGoodsStock", gxcGoodsStock);
		// 判断选择的是一级分类还是二级分类
		params.put("oneOrTwo", oneOrTwo);
		if (classificationId != null && !"".equals(classificationId)) {
			params.put("classificationId", Integer.parseInt(classificationId));
		} else {
			params.put("classificationId", "");
		}
		// 说明前台页面中的搜索框中选择的有活动名称
		if (activityName != null && !"-1".equals(activityName)) {
			// 获取返回的结果集---添加该商品现在的状态(预售/现货)以及参加的活动
			List<GoodsSpecificationDetails> goodsSpecificationDetailsList = goodsSpecificationDetailsMapper.selectForSearchNoLimit(params);
			// 获取商品活动表中的所有信息
			List<GoodsActivity> goodsActivityList = goodsActivityMapper.selectAllMsg();
			GoodsSpecificationDetails goodsSpecificationDetails;
			// 保存商品所参加的活动
			List<String> goodsParticipateActivitiesList = new ArrayList<>();
			String goodsParticipateActivities = "";
			for (int i = 0; i < goodsSpecificationDetailsList.size(); i++) {
				goodsParticipateActivitiesList = new ArrayList<>();
				goodsSpecificationDetails = goodsSpecificationDetailsList.get(i);
				if (goodsSpecificationDetails.getGxcGoodsStock() == null) {
					goodsSpecificationDetails.setGxcGoodsStock(0);
				}
				for (GoodsActivity goodsActivity : goodsActivityList) {
					// 商品活动表存的是规格id
					if (goodsActivity.getState() == 0) {
						// 传入商品的规格与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
						if (goodsSpecificationDetails.getId().equals(goodsActivity.getGoodsId())) {
							System.out.println(
									"activityType if:" + goodsActivity.getActivityInformation().getActivityType());
							// 根据活动类型编号获取活动类型名称
							String activityType = SundryCodeUtil
									.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
							// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
							int h = 0;
							for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
								if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
									break;
								}
							}
							if (h == goodsParticipateActivitiesList.size()) {
								goodsParticipateActivitiesList.add(activityType);
							}

						}
					}
					// 商品活动表存的是商品id
					else if (goodsActivity.getState() == 1) {
						// 传入商品的id与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
						if (goodsSpecificationDetails.getGoodsDetails().getId().equals(goodsActivity.getGoodsId())) {
							System.out.println(
									"activityType else if:" + goodsActivity.getActivityInformation().getActivityType());
							// 根据活动类型编号获取活动类型名称
							String activityType = SundryCodeUtil
									.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
							// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
							int h = 0;
							for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
								if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
									break;
								}
							}
							if (h == goodsParticipateActivitiesList.size()) {
								goodsParticipateActivitiesList.add(activityType);
							}
						}
					}
					// 商品活动表存的是分类id
					else {
						// 传入商品的id与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
						if (goodsSpecificationDetails.getGoodsDetails().getClassificationId()
								.equals(goodsActivity.getGoodsId())) {
							System.out.println(
									"activityType else:" + goodsActivity.getActivityInformation().getActivityType());
							// 根据活动类型编号获取活动类型名称
							String activityType = SundryCodeUtil
									.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
							// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
							int h = 0;
							for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
								if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
									break;
								}
							}
							if (h == goodsParticipateActivitiesList.size()) {
								goodsParticipateActivitiesList.add(activityType);
							}
						}
					}
				}
				System.out.println("goodsParticipateActivitiesList.size:" + goodsParticipateActivitiesList.size());
				goodsParticipateActivities = "";
				for (int h = 0; h < goodsParticipateActivitiesList.size(); h++) {
					if (h < goodsParticipateActivitiesList.size() - 1) {
						goodsParticipateActivities += goodsParticipateActivitiesList.get(h) + ",";
					} else {
						goodsParticipateActivities += goodsParticipateActivitiesList.get(h);
					}

				}
				System.out.println("goodsParticipateActivities:" + goodsParticipateActivities);
				// 把获取到的该商品参加的活动存到model中
				((GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i))
						.setParticipateActivities(goodsParticipateActivities);
				((GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i))
						.setParticipateActivitieList(goodsParticipateActivitiesList);
			}
			List<Object> showGoodsSpecificationDetailsList = new ArrayList<>();
			// 说明前台页面中的搜索框中选择的有活动名称
			
			for (int i = 0; i < goodsSpecificationDetailsList.size(); i++) {
				GoodsSpecificationDetails gsd = (GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i);
				List<String> activityList = gsd.getParticipateActivitieList();
				for (int k = 0; k < activityList.size(); k++) {
					// 说明该商品信息符合页面的搜索的搜索结果
					if (activityName.equals(activityList.get(k))) {
						showGoodsSpecificationDetailsList.add(gsd);
					}
				}
			}
			List<Object> showGoodsSpecificationDetailsList2 = new ArrayList<>();
			//params.put("iDisplayStart", Integer.parseInt(dataTables.getiDisplayStart()));// 获取开始位置
			//params.put("pageDisplayLength", Integer.parseInt(dataTables.getPageDisplayLength()));// 获取分页大小
			//
			if(showGoodsSpecificationDetailsList.size()>=Integer.parseInt(dataTables.getiDisplayStart())+Integer.parseInt(dataTables.getPageDisplayLength())){
				for(int i=Integer.parseInt(dataTables.getiDisplayStart());i<Integer.parseInt(dataTables.getiDisplayStart())+Integer.parseInt(dataTables.getPageDisplayLength());i++){
					showGoodsSpecificationDetailsList2.add(showGoodsSpecificationDetailsList.get(i));
				}
			}
			else{
				for(int i=Integer.parseInt(dataTables.getiDisplayStart());i<showGoodsSpecificationDetailsList.size();i++){
					showGoodsSpecificationDetailsList2.add(showGoodsSpecificationDetailsList.get(i));
				}
			}
			dataTables.setData(showGoodsSpecificationDetailsList2);// 返回的结果集
			// dataTables.setiTotalDisplayRecords(goodsSpecificationDetailsMapper.iTotalDisplayRecords(params)); 搜索结果总行数
			dataTables.setiTotalDisplayRecords(showGoodsSpecificationDetailsList.size());// 搜索结果总行数
			
		}
		//说明前台没有根据促销即活动名称查询商品
		else {
			// 获取返回的结果集---添加该商品现在的状态(预售/现货)以及参加的活动
			List<Object> goodsSpecificationDetailsList = goodsSpecificationDetailsMapper.selectForSearch(params);
			// 获取商品活动表中的所有信息
			List<GoodsActivity> goodsActivityList = goodsActivityMapper.selectAllMsg();
			GoodsSpecificationDetails goodsSpecificationDetails;
			// 保存商品所参加的活动
			List<String> goodsParticipateActivitiesList = new ArrayList<>();
			String goodsParticipateActivities = "";
			for (int i = 0; i < goodsSpecificationDetailsList.size(); i++) {
				goodsParticipateActivitiesList = new ArrayList<>();
				goodsSpecificationDetails = (GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i);
				/*
				 * // 判断购销存中该商品的状态 是否是预售,0不是(现货)，1是(预售) if
				 * (goodsSpecificationDetails.getGxcGoodsState() != null &&
				 * goodsSpecificationDetails.getGxcGoodsState() == 2) {
				 * goodsParticipateActivitiesList.add("现货"); } else if
				 * (goodsSpecificationDetails.getGxcGoodsState() != null &&
				 * goodsSpecificationDetails.getGxcGoodsState() == 1) {
				 * goodsParticipateActivitiesList.add("预售"); }
				 */
				if (goodsSpecificationDetails.getGxcGoodsStock() == null) {
					goodsSpecificationDetails.setGxcGoodsStock(0);
				}
				for (GoodsActivity goodsActivity : goodsActivityList) {
					// 商品活动表存的是规格id
					if (goodsActivity.getState() == 0) {
						// 传入商品的规格与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
						if (goodsSpecificationDetails.getId().equals(goodsActivity.getGoodsId())) {
							System.out.println(
									"activityType if:" + goodsActivity.getActivityInformation().getActivityType());
							// 根据活动类型编号获取活动类型名称
							String activityType = SundryCodeUtil
									.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
							// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
							int h = 0;
							for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
								if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
									break;
								}
							}
							if (h == goodsParticipateActivitiesList.size()) {
								goodsParticipateActivitiesList.add(activityType);
							}

						}
					}
					// 商品活动表存的是商品id
					else if (goodsActivity.getState() == 1) {
						// 传入商品的id与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
						if (goodsSpecificationDetails.getGoodsDetails().getId().equals(goodsActivity.getGoodsId())) {
							System.out.println(
									"activityType else if:" + goodsActivity.getActivityInformation().getActivityType());
							// 根据活动类型编号获取活动类型名称
							String activityType = SundryCodeUtil
									.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
							// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
							int h = 0;
							for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
								if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
									break;
								}
							}
							if (h == goodsParticipateActivitiesList.size()) {
								goodsParticipateActivitiesList.add(activityType);
							}
						}
					}
					// 商品活动表存的是分类id
					else {
						// 传入商品的id与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
						if (goodsSpecificationDetails.getGoodsDetails().getClassificationId()
								.equals(goodsActivity.getGoodsId())) {
							System.out.println(
									"activityType else:" + goodsActivity.getActivityInformation().getActivityType());
							// 根据活动类型编号获取活动类型名称
							String activityType = SundryCodeUtil
									.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
							// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
							int h = 0;
							for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
								if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
									break;
								}
							}
							if (h == goodsParticipateActivitiesList.size()) {
								goodsParticipateActivitiesList.add(activityType);
							}
						}
					}
				}
				System.out.println("goodsParticipateActivitiesList.size:" + goodsParticipateActivitiesList.size());
				goodsParticipateActivities = "";
				for (int h = 0; h < goodsParticipateActivitiesList.size(); h++) {
					if (h < goodsParticipateActivitiesList.size() - 1) {
						goodsParticipateActivities += goodsParticipateActivitiesList.get(h) + ",";
					} else {
						goodsParticipateActivities += goodsParticipateActivitiesList.get(h);
					}

				}
				System.out.println("goodsParticipateActivities:" + goodsParticipateActivities);
				// 把获取到的该商品参加的活动存到model中
				((GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i))
						.setParticipateActivities(goodsParticipateActivities);
				((GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i))
						.setParticipateActivitieList(goodsParticipateActivitiesList);
			}
			dataTables.setData(goodsSpecificationDetailsList);// 返回的结果集
			dataTables.setiTotalDisplayRecords(goodsSpecificationDetailsMapper.iTotalDisplayRecords(params));// 搜索结果总行数
		}

		dataTables.setiTotalRecords(goodsSpecificationDetailsMapper.iTotalRecords(params));// 所有记录总行数

		return dataTables;

	}

	@Override
	public List<GoodsSpecificationDetails> selectByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.selectByGoodsId(goodsId);
	}

	@Override
	public List<GoodsSpecificationDetails> selectMsgByInputValue(String inputValue) {
		Map<String, Object> map = new HashMap<>();
		map.put("inputValue", inputValue);
		return goodsSpecificationDetailsMapper.selectMsgByInputValue(map);
	}

	@Override
	public Integer selectGoodsSDMsgBySpecificationAndGoodsId(Integer commoditySpecificationId) {
		return goodsSpecificationDetailsMapper.selectGoodsSDMsgBySpecificationAndGoodsId(commoditySpecificationId);
	}

	@Override
	public GoodsSpecificationDetails selectGSDAndGDAndClassificationMsgByGSDId(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", Integer.parseInt(id));
		GoodsSpecificationDetails goodsSpecificationDetails = new GoodsSpecificationDetails();
		goodsSpecificationDetails = goodsSpecificationDetailsMapper.selectGSDAndGDAndClassificationMsgByGSDId(map);
		// 获取一级分类的名称
		goodsSpecificationDetails.getGoodsDetails().getClassification()
				.setParentName(classificationMapper
						.selectByPrimaryKey(
								goodsSpecificationDetails.getGoodsDetails().getClassification().getParentId())
						.getName());
		return goodsSpecificationDetails;
	}

	@Override
	public GoodsSpecificationDetails selectGoodsSDMsgBySpecificationAndGoodsSDId(String goodsId, String id,
			String specifications) {
		Map<String, Object> map = new HashMap<>();
		map.put("goodsId", Integer.parseInt(goodsId));
		map.put("id", Integer.parseInt(id));
		map.put("specifications", specifications);
		return goodsSpecificationDetailsMapper.selectGoodsSDMsgBySpecificationAndGoodsSDId(map);
	}

	@Override
	public List<GoodsSpecificationDetails> selectBatchByPrimaryKey(String[] primaryKey) {
		List<Integer> primaryKeys = new ArrayList<Integer>();
		for (String id : primaryKey) {
			primaryKeys.add(Integer.parseInt(id));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", primaryKeys);
		return goodsSpecificationDetailsMapper.selectBatchByPrimaryKey(map);
	}

	@Override
	public Integer deleteBatchByPrimaryKey(String[] primaryKey) {
		List<Integer> primaryKeys = new ArrayList<Integer>();
		for (String id : primaryKey) {
			primaryKeys.add(Integer.parseInt(id));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", primaryKeys);
		return goodsSpecificationDetailsMapper.deleteBatchByPrimaryKey(map);
	}

	@Override
	public Integer updateBatchByPrimaryKey(List<Integer> primaryKeyList, String onOrOff, String date) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", primaryKeyList);
		map.put("onOrOff", onOrOff);
		map.put("date", date);
		return goodsSpecificationDetailsMapper.updateBatchByPrimaryKey(map);
	}

	@Override
	public List<GoodsSpecificationDetails> selectGSDAndGDAndClassificationMsgToExport(String identifier, String name,
			String operatorName, String gxcGoodsStock, String oneOrTwo, String classificationId, String activityName,
			String operatorTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("identifier", identifier);
		map.put("name", name);
		map.put("operatorName", operatorName);
		map.put("operatorTime", operatorTime);
		// 库存select选择的id(有库存/无库存)
		map.put("gxcGoodsStock", gxcGoodsStock);
		// 判断选择的是一级分类还是二级分类
		map.put("oneOrTwo", oneOrTwo);
		if (classificationId != null && !"".equals(classificationId)) {
			map.put("classificationId", Integer.parseInt(classificationId));
		} else {
			map.put("classificationId", "");
		}
		List<GoodsSpecificationDetails> goodsSpecificationDetailsList = goodsSpecificationDetailsMapper
				.selectGSDAndGDAndClassificationMsgToExport(map);
		if (goodsSpecificationDetailsList != null && goodsSpecificationDetailsList.size() > 0) {
			for (GoodsSpecificationDetails gsd : goodsSpecificationDetailsList) {
				// 获取一级分类的名称
				gsd.getGoodsDetails().getClassification().setParentName(classificationMapper
						.selectByPrimaryKey(gsd.getGoodsDetails().getClassification().getParentId()).getName());
			}

		}
		// 获取商品活动表中的所有信息
		List<GoodsActivity> goodsActivityList = goodsActivityMapper.selectAllMsg();
		GoodsSpecificationDetails goodsSpecificationDetails;
		// 保存商品所参加的活动
		List<String> goodsParticipateActivitiesList = new ArrayList<>();
		String goodsParticipateActivities = "";
		for (int i = 0; i < goodsSpecificationDetailsList.size(); i++) {
			goodsParticipateActivitiesList = new ArrayList<>();
			goodsSpecificationDetails = (GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i);
			// 判断购销存中该商品的状态(0:预售,1:现货)
			if (goodsSpecificationDetails.getGxcGoodsState() != null
					&& goodsSpecificationDetails.getGxcGoodsState() == 0) {
				goodsParticipateActivitiesList.add("预售");
			} else if (goodsSpecificationDetails.getGxcGoodsState() != null
					&& goodsSpecificationDetails.getGxcGoodsState() == 1) {
				goodsParticipateActivitiesList.add("现货");
			}
			for (GoodsActivity goodsActivity : goodsActivityList) {
				// 商品活动表存的是规格id
				if (goodsActivity.getState() == 0) {
					// 传入商品的规格与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
					if (goodsSpecificationDetails.getId() == goodsActivity.getGoodsId()) {
						// 根据活动类型编号获取活动类型名称
						String activityType = SundryCodeUtil
								.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
						// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
						int h = 0;
						for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
							if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
								break;
							}
						}
						if (h == goodsParticipateActivitiesList.size()) {
							goodsParticipateActivitiesList.add(activityType);
						}
					}
				}
				// 商品活动表存的是商品id
				else if (goodsActivity.getState() == 1) {
					// 传入商品的id与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
					if (goodsSpecificationDetails.getGoodsDetails().getId() == goodsActivity.getGoodsId()) {
						// 根据活动类型编号获取活动类型名称
						String activityType = SundryCodeUtil
								.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
						// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
						int h = 0;
						for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
							if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
								break;
							}
						}
						if (h == goodsParticipateActivitiesList.size()) {
							goodsParticipateActivitiesList.add(activityType);
						}
					}
				}
				// 商品活动表存的是分类id
				else {
					// 传入商品的id与商品活动表里的信息进行比较,如果一样，说明该商品参加的有活动
					if (goodsSpecificationDetails.getGoodsDetails().getClassificationId() == goodsActivity
							.getGoodsId()) {
						// 根据活动类型编号获取活动类型名称
						String activityType = SundryCodeUtil
								.getActivityTypeName(goodsActivity.getActivityInformation().getActivityType());
						// 判断原先的列表中加的是否有该活动类型，如果有，则不添加。
						int h = 0;
						for (h = 0; h < goodsParticipateActivitiesList.size(); h++) {
							if (goodsParticipateActivitiesList.get(h).equals(activityType)) {
								break;
							}
						}
						if (h == goodsParticipateActivitiesList.size()) {
							goodsParticipateActivitiesList.add(activityType);
						}
					}
				}
			}
			goodsParticipateActivities = "";
			for (int h = 0; h < goodsParticipateActivitiesList.size(); h++) {
				if (h < goodsParticipateActivitiesList.size() - 1) {
					goodsParticipateActivities += goodsParticipateActivitiesList.get(h) + ",";
				} else {
					goodsParticipateActivities += goodsParticipateActivitiesList.get(h);
				}

			}
			// 把获取到的该商品参加的活动存到model中
			goodsSpecificationDetailsList.get(i).setParticipateActivities(goodsParticipateActivities);
			goodsSpecificationDetailsList.get(i).setParticipateActivitieList(goodsParticipateActivitiesList);
		}
		List<GoodsSpecificationDetails> showGoodsSpecificationDetailsList = new ArrayList<>();
		// 说明前台页面中的搜索框中选择的有活动名称
		if (activityName != null && !"-1".equals(activityName)) {
			for (int i = 0; i < goodsSpecificationDetailsList.size(); i++) {
				GoodsSpecificationDetails gsd = (GoodsSpecificationDetails) goodsSpecificationDetailsList.get(i);
				List<String> activityList = gsd.getParticipateActivitieList();
				for (int k = 0; k < activityList.size(); k++) {
					// 说明该商品信息符合页面的搜索的搜索结果
					if (activityName.equals(activityList.get(k))) {
						showGoodsSpecificationDetailsList.add(gsd);
					}
				}
			}
			return showGoodsSpecificationDetailsList;
		} else {
			return goodsSpecificationDetailsList;
		}

	}

	@Override
	public List<GoodsSpecificationDetails> getGoodsSpecificationDetail(Integer goodsId, String isHasGoods,
			String[] brandName, String gsdState) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("isHasGoods", isHasGoods);
		map.put("brandName", brandName);
		map.put("gsdState", gsdState);
		return goodsSpecificationDetailsMapper.getGoodsSpecificationDetail(map);
	}

	@Override
	public List<GoodsSpecificationDetails> getGoodsDetailMsgByGoodsId(Integer goodsId, String gsdState,
			String isHasGoods) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("gsdState", gsdState);
		map.put("isHasGoods", isHasGoods);
		return goodsSpecificationDetailsMapper.getGoodsDetailMsgByGoodsId(map);
	}

	@Override
	public GoodsSpecificationDetails selectGxcGoodsStockByspecificationId(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return goodsSpecificationDetailsMapper.selectGxcGoodsStockByspecificationId(map);
	}

	@Override
	public GoodsSpecificationDetails selectGxcGoodsStockAndZeroByspecificationId(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return goodsSpecificationDetailsMapper.selectGxcGoodsStockAndZeroByspecificationId(map);
	}

	@Override
	public GoodsSpecificationDetails selectGoodsSpecificationDetailsByIdAndGoodsId(Integer id, Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("id", id);
		return goodsSpecificationDetailsMapper.selectGoodsSpecificationDetailsByIdAndGoodsId(map);
	}

	@Override
	public GoodsSpecificationDetails selectGoodsDetailsByIdAndGoodsId(Integer id, Integer goodsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("id", id);
		return goodsSpecificationDetailsMapper.selectGoodsDetailsByIdAndGoodsId(map);
	}

	@Override
	public List<GoodsSpecificationDetails> selectOnByGoodsId(Integer goodsId) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.selectOnByGoodsId(goodsId);
	}

	@Override
	public GoodsSpecificationDetails selectGxcGoodsStockByspecificationIdAndGoodsId(Integer id, Integer goodsId,
			Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsId", goodsId);
		map.put("id", id);
		map.put("userId", userId);
		return goodsSpecificationDetailsMapper.selectGxcGoodsStockByspecificationIdAndGoodsId(map);
	}

	@Override
	public int updateGoodsSpecificationDetailsSalesCount(Integer detailsId, Integer count) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("detailsId", detailsId);
		map.put("count", count);
		return goodsSpecificationDetailsMapper.updateGoodsSpecificationDetailsSalesCount(map);
	}

	@Override
	public List<GoodsSpecificationDetails> selectGoodsMsgToCustomService(String goodsName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsName", goodsName);
		return goodsSpecificationDetailsMapper.selectGoodsMsgToCustomService(map);
	}

	@Override
	public GoodsSpecificationDetails selectSpecIdentifierById(Integer id) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.selectSpecIdentifierById(id);
	}

	@Override
	public Integer updateByCommoditySpecificationId(GoodsSpecificationDetails goodsSpecificationDetails) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.updateByCommoditySpecificationId(goodsSpecificationDetails);
	}

	@Override
	public Integer selectjlgxcCommoditySpecificationIsDeleteByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return goodsSpecificationDetailsMapper.selectjlgxcCommoditySpecificationIsDeleteByPrimaryKey(id);
	}

}