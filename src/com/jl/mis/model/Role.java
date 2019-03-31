package com.jl.mis.model;

import java.util.Date;
/**
 * 角色model
 * @author 景雅倩
 * @date  2017-11-3  下午3:51:56
 * @Description TODO
 */
public class Role {
    private Integer id;
    
    /**
     * 角色名
     */
    private String name;
    
    /**
     * 角色描述
     */
    private String description;
    
    /**
     * 操作人编号
     */
    private String operatorIdentifier;
    
    /**
     * 创建时间
     */
    private Date createTime;

    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
}