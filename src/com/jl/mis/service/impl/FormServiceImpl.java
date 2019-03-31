package com.jl.mis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jl.mis.mapper.FormMapper;
import com.jl.mis.model.OrderTable;
import com.jl.mis.service.FormService;
@Service
public class FormServiceImpl implements FormService {

	@Autowired
	private FormMapper f; 
	@Override
	public List<OrderTable> Search(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return f.Search(map);
	}

	@Override
	public List<OrderTable> Order(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return f.Order(map);
	}

	@Override
	public List<Map<String, Object>> Goods(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return f.Goods(map);
	}


}
