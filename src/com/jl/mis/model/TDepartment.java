package com.jl.mis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * 部门model类
 * */
public class TDepartment implements Serializable {

	/**
	 * 
	 */
	private static final Long serialVersionUID = 168884317939700508L;

	@Id
	@Column(name="departmentId")
	private Integer departmentId = 1;
	
	@Column(name="departmentName")
	private String departmentName = "";
	
	@Column(name="departmentCode")
	private String departmentCode = "";
	
	@Column(name="departmentDesc")
	private String departmentDesc = "";
	
	@Column(name="companyId")
	private Integer companyId = 1;

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public static Long getSerialversionuid() {
		return serialVersionUID;
	}


}
