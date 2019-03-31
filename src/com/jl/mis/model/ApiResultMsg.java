package com.jl.mis.model;

/**
 * 调取API时返回的数据
 * @author 柳亚婷
 * @date  2017年12月8日  上午11:25:29
 * @Description 
 *
 */
public class ApiResultMsg {
	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 返回的信息提示
	 */
	private String msg;
	/**
	 * 返回的数据
	 */
	private String data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	

}
