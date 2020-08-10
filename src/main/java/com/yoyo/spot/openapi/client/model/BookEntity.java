package com.yoyo.spot.openapi.client.model;

import java.math.BigDecimal;

/**
 * author:zhiwei
 * <p>
 * email:597384114@qq.com
 * <p>
 * time:2020/2/13
 **/
public class BookEntity {

    private BigDecimal price;
    private BigDecimal qty;
    private Long orderId = 0l;

    public Long getOrderId() {
        return orderId;
    }

    public BookEntity setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public BookEntity(BigDecimal price, BigDecimal qty) {
        this.price = price;
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

}
