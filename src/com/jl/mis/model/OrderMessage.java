package com.jl.mis.model;
/**
 * 订单消息model
 * @author 景雅倩
 * @date  2017-11-3  下午3:51:07
 * @Description TODO
 */
public class OrderMessage {
    private Integer id;
    
    /**
     * 订单状态详情id
     */
    private Integer orderStateDetailId;
    
    /**
     * 状态
     */
    private Integer status;

    
    
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderStateDetailId() {
        return orderStateDetailId;
    }

    public void setOrderStateDetailId(Integer orderStateDetailId) {
        this.orderStateDetailId = orderStateDetailId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}