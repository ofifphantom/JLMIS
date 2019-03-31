package com.jl.app.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jl.mis.model.ShippingAddress;
import com.jl.mis.model.User;
import com.jl.mis.service.ShippingAddressService;
import com.jl.mis.service.UserService1;

/**
 * 收货地址API
 * 
 * @author 柳亚婷
 * @date 2017年12月18日 下午3:57:02
 * @Description
 *
 */
@Controller
@RequestMapping("/shippingAddress/")
public class ShippingAddressApi {

	@Autowired
	private ShippingAddressService shippingAddressService;
	@Autowired
	private UserService1 userService1;

	/**
	 * 根据用户ID获取用户的收货人地址
	 * 
	 * @param request
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "getShippingAddressByUserId", method = RequestMethod.GET)
	public JSONObject getShippingAddressByUserId(HttpServletRequest request, @RequestParam("userId") Integer userId)
			throws Exception {
		JSONObject result = new JSONObject();
		if (userId == null || userId <= 0) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		List<ShippingAddress> shippingAddressList = new ArrayList<>();
		shippingAddressList = shippingAddressService.selectByUserId(userId);

		result.put("resultData", shippingAddressList);
		result.put("code", 200);
		result.put("msg", "请求成功");
		return result;
	}

	/**
	 * 新增收货人信息
	 * 
	 * @param request
	 * @param userId
	 *            用户ID
	 * @param consigneeName
	 *            收货人姓名
	 * @param consigneeTel
	 *            收货人电话
	 * @param region
	 *            所在地区
	 * @param detailedAddress
	 *            详细地址
	 * @param isCommonlyUsed
	 *            是否为常用(0：否，1:是)
	 * @param provinceCode
	 *            省编号
	 * @param cityCode
	 *            城市编号 (若为直辖市(例：北京)，该处则传"0")
	 * @param countyCode
	 *            区/县编号
	 * @param ringCode
	 *            环编号 ("-1":表示全部，"2":二环，"3":三环，"4":四环，"5":五环，"6":六环，"7":七环)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "insertShippingAddress", method = RequestMethod.POST)
	public JSONObject insertShippingAddress(HttpServletRequest request, @RequestParam Integer userId,
			@RequestParam String consigneeName, @RequestParam String consigneeTel, @RequestParam String region,
			@RequestParam String detailedAddress, @RequestParam Integer isCommonlyUsed,
			@RequestParam String provinceCode, @RequestParam String cityCode, @RequestParam String countyCode,
			@RequestParam String ringCode) throws Exception {
		JSONObject result = new JSONObject();
		if ((userId == null || userId <= 0) || (consigneeName == null || "".equals(consigneeName))
				|| (consigneeTel == null || "".equals(consigneeTel)) || (region == null || "".equals(region))
				|| (detailedAddress == null || "".equals(detailedAddress))
				|| (isCommonlyUsed != 0 && isCommonlyUsed != 1) || (provinceCode == null || "".equals(provinceCode))
				|| (cityCode == null || "".equals(cityCode)) || (countyCode == null || "".equals(countyCode))
				|| (ringCode == null || "".equals(ringCode))) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		// 新增前先判断传入得地址状态，是否为默认，如果为默认，则先把该用户下的所有地址设置为不默认。
		if (isCommonlyUsed == 1) {
			// 先把该用户下的所有地址设置为不默认
			shippingAddressService.setNoCommonlyByUserId(userId);
		}

		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setConsigneeTel(consigneeTel);
		shippingAddress.setConsigneeName(consigneeName);
		shippingAddress.setDetailedAddress(detailedAddress);
		
		List<ShippingAddress> shippingAddressList = new ArrayList<>();
		shippingAddressList = shippingAddressService.selectByUserId(userId);
		
		shippingAddress.setIsCommonlyUsed(isCommonlyUsed);
		if(shippingAddressList.size()==0){
			shippingAddress.setIsCommonlyUsed(1);
		}
		
	
		shippingAddress.setOperatorTime(new Date());
		shippingAddress.setRegion(region);
		shippingAddress.setUserId(userId);
		if (!"0".equals(cityCode)) {
			shippingAddress.setCityCode(cityCode);
		}
		shippingAddress.setProvinceCode(provinceCode);
		shippingAddress.setCountyCode(countyCode);
		shippingAddress.setRingCode(ringCode);

		if (shippingAddressService.insertSelective(shippingAddress) > 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 60001);
			result.put("msg", "收货人地址添加失败!");
			return result;

		}

	}

	/**
	 * 修改收货人信息
	 * 
	 * @param request
	 * @param id
	 *            主键id
	 * @param userId
	 *            用户ID
	 * @param consigneeName
	 *            收货人姓名
	 * @param consigneeTel
	 *            收货人电话
	 * @param region
	 *            所在地区
	 * @param detailedAddress
	 *            详细地址
	 * @param isCommonlyUsed
	 *            是否为常用(0：否，1:是)
	 * @param provinceCode
	 *            省编号
	 * @param cityCode
	 *            城市编号 (若为直辖市(例：北京)，该处则传"0")
	 * @param countyCode
	 *            区/县编号
	 * @param ringCode
	 *            环编号("-1":表示全部，"2":二环，"3":三环，"4":四环，"5":五环，"6":六环，"7":七环)
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "updateShippingAddressById", method = RequestMethod.POST)
	public JSONObject updateShippingAddressById(HttpServletRequest request, @RequestParam Integer id,
			@RequestParam Integer userId, @RequestParam String consigneeName, @RequestParam String consigneeTel,
			@RequestParam String region, @RequestParam String detailedAddress, @RequestParam Integer isCommonlyUsed,
			@RequestParam String provinceCode, @RequestParam String cityCode, @RequestParam String countyCode,
			@RequestParam String ringCode) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
		if ((id == null || id <= 0) || (userId == null || userId <= 0)
				|| (consigneeName == null || "".equals(consigneeName))
				|| (consigneeTel == null || "".equals(consigneeTel) || consigneeTel.length() > 11)
				|| (region == null || "".equals(region)) || (detailedAddress == null || "".equals(detailedAddress))
				|| (isCommonlyUsed != 0 && isCommonlyUsed != 1) || (provinceCode == null || "".equals(provinceCode))
				|| (cityCode == null || "".equals(cityCode)) || (countyCode == null || "".equals(countyCode))
				|| (ringCode == null || "".equals(ringCode))) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		// 判断id和userid是否重合,避免有人拦截网址篡改信息
		ShippingAddress addressById = shippingAddressService.selectByPrimaryKey(id);
		if (addressById == null) {
			result.put("code", 60002);
			result.put("msg", "该收货人地址不存在");
			return result;
		} else {
			// 验证是否是本人操作本人数据
			if (addressById.getUserId() != userId) {
				result.put("code", 10003);
				result.put("msg", "尝试非法操作");
				return result;
			}
		}
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setIsCommonlyUsed(isCommonlyUsed);
		// 修改前先判断传入得地址状态，是否为默认，如果为默认，则先把该用户下的所有地址设置为不默认。
		if (isCommonlyUsed == 1) {
			// 先把该用户下的所有地址设置为不默认
			shippingAddressService.setNoCommonlyByUserId(userId);
		}else{
			List<ShippingAddress> shippingAddressList = new ArrayList<>();
			shippingAddressList = shippingAddressService.selectByUserId(userId);
			if(shippingAddressList.size()>1){
				for (ShippingAddress Address : shippingAddressList) {
					if(Address.getIsCommonlyUsed()==1&&id==Address.getId()){
						Address.setIsCommonlyUsed(1);
					}
				}
			}
		}
		// 修改
		shippingAddress.setId(id);
		shippingAddress.setConsigneeTel(consigneeTel);
		shippingAddress.setConsigneeName(consigneeName);
		shippingAddress.setDetailedAddress(detailedAddress);
		
		shippingAddress.setRegion(region);
		if (!"0".equals(cityCode)) {
			shippingAddress.setCityCode(cityCode);
		}
		shippingAddress.setProvinceCode(provinceCode);
		shippingAddress.setCountyCode(countyCode);
		shippingAddress.setRingCode(ringCode);

		if (shippingAddressService.updateByPrimaryKeySelective(shippingAddress) > 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 60003);
			result.put("msg", "收货人地址修改失败!");
			return result;
		}

	}

	/**
	 * 根据主键删除收货人信息
	 * 
	 * @param request
	 * @param id
	 *            主键
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "deleteShippingAddressById", method = RequestMethod.POST)
	public JSONObject deleteShippingAddressById(HttpServletRequest request, @RequestParam Integer id,
			@RequestParam Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
		if ((id == null || id <= 0) || (userId == null || userId <= 0)) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		// 判断id和userid是否重合,避免有人拦截网址篡改信息
		ShippingAddress addressById = shippingAddressService.selectByPrimaryKey(id);
		if (addressById == null) {
			result.put("code", 60002);
			result.put("msg", "该收货人地址不存在");
			return result;
		} else {
			// 验证是否是本人操作本人数据
			if (addressById.getUserId() != userId) {
				result.put("code", 10003);
				result.put("msg", "尝试非法操作");
				return result;
			}
		}
		// 删除
		if (shippingAddressService.deleteByPrimaryKey(id) > 0) {
			if(addressById.getIsCommonlyUsed()==1){
				List<ShippingAddress> shippingAddressList = new ArrayList<>();
				shippingAddressList = shippingAddressService.selectByUserId(userId);
				if(shippingAddressList.size()!=0){
					shippingAddressList.get(0).setIsCommonlyUsed(1);
					shippingAddressService.updateByPrimaryKey(shippingAddressList.get(0));
				}
			}
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 60004);
			result.put("msg", "收货人地址删除失败!");
			return result;
		}
	}

	/**
	 * 设置当前地址为默认地址
	 * 
	 * @param request
	 * @param id
	 *            收货地址id
	 * @param userId
	 *            用户id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "setPretermissionShippingAddressById", method = RequestMethod.POST)
	public JSONObject setPretermissionShippingAddressById(HttpServletRequest request, @RequestParam Integer id,
			@RequestParam Integer userId) throws Exception {
		JSONObject result = new JSONObject();
		// 验证参数的合法性
		if ((id == null || id <= 0) || (userId == null || userId <= 0)) {
			result.put("code", 10000);
			result.put("msg", "参数错误");
			return result;
		}
		// 先判断该用户是否存在
		User user = userService1.selectByPrimaryKey(userId);
		// 用户不存在
		if (user == null) {
			result.put("code", 11005);
			result.put("msg", "用户不存在");
			return result;
		} else {
			// 获取到的状态不是用户
			if (user.getAdministratorOrUser() != 1) {
				result.put("code", 11005);
				result.put("msg", "用户不存在");
				return result;
			}
		}
		// 判断id和userid是否重合,避免有人拦截网址篡改信息
		ShippingAddress addressById = shippingAddressService.selectByPrimaryKey(id);
		if (addressById == null) {
			result.put("code", 60002);
			result.put("msg", "该收货人地址不存在");
			return result;
		} else {
			// 验证是否是本人操作本人数据
			if (addressById.getUserId() != userId) {
				result.put("code", 10003);
				result.put("msg", "尝试非法操作");
				return result;
			}
		}
		// 先把该用户下的所有地址设置为不默认
		shippingAddressService.setNoCommonlyByUserId(userId);
		// 把传过来的地址id设置为默认地址
		ShippingAddress shippingAddress = new ShippingAddress();
		shippingAddress.setId(id);
		shippingAddress.setIsCommonlyUsed(1);
		if (shippingAddressService.updateByPrimaryKeySelective(shippingAddress) > 0) {
			result.put("code", 200);
			result.put("msg", "请求成功");
			return result;
		} else {
			result.put("code", 60005);
			result.put("msg", "收货人地址设置默认失败!");
			return result;
		}
	}

}
