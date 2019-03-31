package com.jl.mis.model;

import java.util.Date;
/**
 * 权限model
 * @author 景雅倩
 * @date  2017-11-3  下午3:51:45
 * @Description TODO
 */
public class Permission {
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 页面菜单id
     */
    private Integer menuId;
    
    /**
     * 操作人编号
     */
    private String operatorIdentifier;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    
    private Menu menu;
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

   

    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getOperatorIdentifier() {
        return operatorIdentifier;
    }

    public void setOperatorIdentifier(String operatorIdentifier) {
        this.operatorIdentifier = operatorIdentifier == null ? null : operatorIdentifier.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}