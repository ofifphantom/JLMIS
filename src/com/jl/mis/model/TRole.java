package com.jl.mis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 角色model类
 * */
public class TRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1898193833467427904L;

	@Id
	@Column(name="roleId")
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator = "JDBC")
	private Integer roleId = 1;
	
	@Column(name="roleName")
	private String roleName = "";
	
	@Column(name="roleDesc")
	private String roleDesc = "";
	
	@Column(name="companyId")
	private Integer companyId = 1;


	public Integer getRoleId() {

		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
