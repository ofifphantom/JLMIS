package com.jl.mis.service;

import com.jl.mis.model.Log;
import com.jl.mis.utils.DataTables;
/**
 * 日志Service
 * @author 景雅倩
 * @date  2017-11-3  下午3:44:28
 * @Description TODO
 */
public interface LogService extends BaseService<Log>{
	
	/**
	 * 获取操作日志表的信息，显示在界面上
	 * @return 返回一个log对象
	 */
	public DataTables getLogMsg(DataTables dataTables,String operatorIdentifier,String operatorType,String operatorTime);
   
}