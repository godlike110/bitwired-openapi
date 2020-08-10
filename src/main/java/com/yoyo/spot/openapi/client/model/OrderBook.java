package com.yoyo.spot.openapi.client.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * author:zhiwei
 * <p>
 * email:597384114@qq.com
 * <p>
 * time:2020/2/13
 **/

public class OrderBook {

    private List<BookEntity> buyList;

    private List<BookEntity> sellList;

    public OrderBook(List<BookEntity> buyList, List<BookEntity> sellList) {
        this.buyList = buyList;
        this.sellList = sellList;
    }

    public List<BookEntity> getBuyList() {
        return buyList;
    }

    public void setBuyList(List<BookEntity> buyList) {
        this.buyList = buyList;
    }

    public List<BookEntity> getSellList() {
        return sellList;
    }

    public void setSellList(List<BookEntity> sellList) {
        this.sellList = sellList;
    }
}
