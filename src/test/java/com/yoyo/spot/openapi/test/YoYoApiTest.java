package com.yoyo.spot.openapi.test;

import com.google.common.collect.Lists;
import com.yoyo.spot.openapi.client.YoSpotApiClientFactory;
import com.yoyo.spot.openapi.client.YoSpotApiRestClient;

import com.yoyo.spot.openapi.client.constant.SpotConstants;
import com.yoyo.spot.openapi.client.enums.OrderSide;
import com.yoyo.spot.openapi.client.model.AccountBalance;
import com.yoyo.spot.openapi.client.model.OrderBook;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class YoYoApiTest {


    public static void main(String[] args) throws InterruptedException {

        YoSpotApiClientFactory factory = YoSpotApiClientFactory.newInstance(SpotConstants.API_BASE_URL,SpotConstants.API_ID, SpotConstants.SECRET);
        YoSpotApiRestClient client = factory.newRestClient();

        BigDecimal price = client.getLastPrice("GGC/USDT");

        OrderBook ob = client.getOrderBook("BTC/USDT",10);

        Long b = client.placeOrder("BTC/USDT", OrderSide.BUY, new BigDecimal(9000),new BigDecimal(0.01));

        Long[] a = client.cancelAll("BTC/USDT");

        List<Long> orders = Lists.newArrayList();
        orders.add(434343l);
        orders.add(1324433l);

        Long[] c = client.cancelOrders(orders);

        System.out.println("hello");


        try {
            Long[] res  = client.cancelAll("BTC/USDT");

            System.out.println(Arrays.toString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            AccountBalance[] res = client.getAllUserAssets("");

            System.out.println(Arrays.toString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        //获取盘口价格信息
//        Map<String,Ticker> tickers = client.getTickers();
//        System.out.println("tickers");
//
//        //获取盘口挂单
//        Map<String,List<MarketOrder>> orderBooks = client.getOrderBook("ETHUSDT",10);
////        System.out.println("orderBooks");
//
//        AccountInfo accountInfo = client.getAccountInfo();
//        System.out.println("accountInfo" + JSON.toJSONString(accountInfo));
//
//        //获取仓位列表
//        List<PositionInfo> positionInfos = client.getPositionList("BTCUSDT");
//        System.out.println("positionLists" + JSON.toJSONString(positionInfos));
//
//        //下单
//        String orderid = client.placeOrder("BTCUSDT", OrderType.LIMIT,100,"5799",1900000, Direction.CLOSELONG,String.valueOf(System.currentTimeMillis()));
//        System.out.println("place order");
//
//
//        //撤单
//        String result = client.cancelOrder(orderid);
//        System.out.println("cancelorder");


    }

}
