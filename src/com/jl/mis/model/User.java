package com.jl.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 用户model
 * @author 景雅倩
 * @date  2017-11-3  下午3:52:45
 * @Description TODO
 */
public class User {
    private Integer id;
    
    /**
     * 编号
     */
    private String identifier;
    
    /**
     * 登录名
     */
    private String loginName;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 用户组编号
     */
    private Integer userGroup;
    
    /**
     * 管理员or用户
     */
    private Integer administratorOrUser;
    
    /**
     * 成为VIP的时间
     */
    private Date isVipTime;
    
    /**
     * 是否是VIP
     */
    private Integer isVip;
    
    /**
     * 头像url
     */
    private String picUrl;
    
    /**
     * 是否有效
     */
    private Integer isEffective;
    
    /**
     * 操作人编号
     */
    private String operatorIdentifier;
    
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 微信号
     */
    private String weixin;
    
    /**
     * 支付宝帐号
     */
    private String zhifubao;
    
    /**
     * QQ号
     */
    private String qq;
    
    //根据结果需要，在model里另添格外的字段
    
    /**
     * 存储管理员的权限信息
     */
    List<Permission> permissions;

    /**
     * 操作人姓名
     */
    private String operatorName;
    
    /**
     * 用户的总交易金额
     */
    private Double historyPayMoney;
    
    /**
     * 用户的月总交易金额(距今30天内)
     */
    private Double monthHistoryPayMoney;
    
    /**
     * 用户的总交易笔数
     */
    private Integer historyPayNum;
    
    /**
     * 用户的月总交易笔数(距今30天内)
     */
    private Integer monthHistoryPayNum;
    
    
    
    
    public Double getHistoryPayMoney() {
		return historyPayMoney;
	}

	public void setHistoryPayMoney(Double historyPayMoney) {
		this.historyPayMoney = historyPayMoney;
	}

	public Double getMonthHistoryPayMoney() {
		return monthHistoryPayMoney;
	}

	public void setMonthHistoryPayMoney(Double monthHistoryPayMoney) {
		this.monthHistoryPayMoney = monthHistoryPayMoney;
	}

	public Integer getHistoryPayNum() {
		return historyPayNum;
	}

	public void setHistoryPayNum(Integer historyPayNum) {
		this.historyPayNum = historyPayNum;
	}

	public Integer getMonthHistoryPayNum() {
		return monthHistoryPayNum;
	}

	public void setMonthHistoryPayNum(Integer monthHistoryPayNum) {
		this.monthHistoryPayNum = monthHistoryPayNum;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier == null ? null : identifier.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    

    public Integer getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Integer userGroup) {
		this.userGroup = userGroup;
	}

	public Integer getAdministratorOrUser() {
        return administratorOrUser;
    }

    public void setAdministratorOrUser(Integer administratorOrUser) {
        this.administratorOrUser = administratorOrUser;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
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

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin == null ? null : weixin.trim();
    }

    public String getZhifubao() {
        return zhifubao;
    }

    public void setZhifubao(String zhifubao) {
        this.zhifubao = zhifubao == null ? null : zhifubao.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Date getIsVipTime() {
		return isVipTime;
	}

	public void setIsVipTime(Date isVipTime) {
		this.isVipTime = isVipTime;
	}
    
}