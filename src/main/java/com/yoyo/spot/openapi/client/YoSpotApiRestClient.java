package com.yoyo.spot.openapi.client;


import com.yoyo.spot.openapi.client.enums.OrderSide;
import com.yoyo.spot.openapi.client.model.AccountBalance;
import com.yoyo.spot.openapi.client.model.OrderBook;
import com.yoyo.spot.openapi.client.model.OrderInfo;

import java.math.BigDecimal;
import java.util.List;

public interface YoSpotApiRestClient {

    Long placeOrder(String symbol, OrderSide orderSide, BigDecimal price, BigDecimal quantity);

    Long privatePlaceOrder(String symbol, OrderSide orderSide, BigDecimal price, BigDecimal quantity);


    Long[] cancelAll(String symbol);

    void cancel(Long orderId);

    Long[] cancelOrders(List<Long> orders);

    AccountBalance[] getAllUserAssets(String symbol);

    OrderBook getOrderBook(String symbol,int limit);

    List<OrderInfo> getOpenOrder(String symbol,int pageNo,int pageSize);

    BigDecimal getLastPrice(String symbol);

}
