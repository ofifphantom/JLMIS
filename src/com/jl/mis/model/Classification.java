package com.jl.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * @author 柳亚婷
 * @date  2017年11月3日  下午3:55:30
 * @Description 分类类型表(一二级分类)model
 */
public class Classification {
	/**
	 * 主键
	 */
    private Integer id;
    /**
	 * 编号
	 */
    private String identifier;
    /**
	 * 名称
	 */
    private String name;
    /**
	 * 关键词
	 */
    private String keyWord;
    /**
	 * 父级ID
	 */
    private Integer parentId;
    /**
	 * 图片URL
	 */
    private String picUrl;
    /**
     * 排序
     */
    private Integer sort;
    /**
	 * 操作人编号
	 */
    private String operatorIdentifier;
    /**
	 * 操作时间
	 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operatorTime;
    
    //根据结果需要，在model里另添格外的字段
    /**
     * 父级分类名称
     */
    private String parentName;
    /**
     * 操作人
     */
    private User user;
    /**
     * 二级分类
     */
    private List<Classification> twoClassifications;
    /**
     * 商品详情
     */
    private List<GoodsDetails> goodsDetails;
    
    
    
    
    public List<GoodsDetails> getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(List<GoodsDetails> goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public List<Classification> getTwoClassifications() {
		return twoClassifications;
	}

	public void setTwoClassifications(List<Classification> twoClassifications) {
		this.twoClassifications = twoClassifications;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getOperatorIdentifier() {
        return operatorIdentifier;
    }

    public void setOperatorIdentifier(String operatorIdentifier) {
        this.operatorIdentifier = operatorIdentifier == null ? null : operatorIdentifier.trim();
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
    
}