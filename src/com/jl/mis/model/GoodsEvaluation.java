package com.jl.mis.model;

import java.util.Date;
import java.util.List;
/**
 * 商品评价model
 * @author 景雅倩
 * @date  2017-11-3  下午3:48:51
 * @Description TODO
 */
/**
 * @author 柳亚婷
 * @Description: TODO
 * @date: 2018年6月13日 上午10:22:50
 */
public class GoodsEvaluation {
    private Integer id;
    
    /**
     * 订单详情ID（外键）
     */
    private Integer orderDetailId;
    
    /**
     * 评价内容
     */
    private String evaluationContent;
    
    /**
     * 评分
     */
    private Double score;
    
    /**
     * 评价时间
     */
    private Date evaluationTime;
    
    /**
     * 评价内容字数
     */
    private Integer evaluationContentWordNum;

  //根据结果需要，在model里另添格外的字段
    
    /**
     * 评价图片表
     */
    private List<EvaluationPic> evaluationPics;
    /**
     * 用户信息
     */
    private User user;
    
    /**
     * 订单详情表
     */
    private OrderDetail orderDetail;
    
    
    
    
    
    public OrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<EvaluationPic> getEvaluationPics() {
		return evaluationPics;
	}

	public void setEvaluationPics(List<EvaluationPic> evaluationPics) {
		this.evaluationPics = evaluationPics;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent == null ? null : evaluationContent.trim();
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Date getEvaluationTime() {
        return evaluationTime;
    }

    public void setEvaluationTime(Date evaluationTime) {
        this.evaluationTime = evaluationTime;
    }

	public Integer getEvaluationContentWordNum() {
		return evaluationContentWordNum;
	}

	public void setEvaluationContentWordNum(Integer evaluationContentWordNum) {
		this.evaluationContentWordNum = evaluationContentWordNum;
	}
    
    
}