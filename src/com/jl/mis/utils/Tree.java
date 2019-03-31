package com.jl.mis.utils;

import java.io.Serializable;

/**
 * 树形类
 * */
public class Tree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8068228181356598232L;

	private Integer id ;
	
	private Integer pid;
	
	private String icon = "";
	
	private String url = "";
	
	private String title = "";
	
	/**
	 * 角色权限树使用
	 * */
	private String name = "";
	
	private boolean open = true;
	
	private boolean checked = false;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
	
}
