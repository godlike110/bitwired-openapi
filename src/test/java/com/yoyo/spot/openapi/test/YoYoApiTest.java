package com.yoyo.spot.openapi.test;

import com.google.common.collect.Lists;
import com.sun.org.apache.xml.internal.security.keys.KeyUtils;
import com.yoyo.spot.openapi.client.YoSpotApiClientFactory;
import com.yoyo.spot.openapi.client.YoSpotApiRestClient;

import com.yoyo.spot.openapi.client.constant.SpotConstants;
import com.yoyo.spot.openapi.client.enums.OrderSide;
import com.yoyo.spot.openapi.client.model.AccountBalance;
import com.yoyo.spot.openapi.client.model.OrderBook;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class YoYoApiTest {


    public static void main(String[] args) throws InterruptedException {

        YoSpotApiClientFactory factory = YoSpotApiClientFactory.newInstance(SpotConstants.API_BASE_URL,SpotConstants.API_ID, SpotConstants.SECRET);
        YoSpotApiRestClient client = factory.newRestClient();

//        for(int i=0;i<100;i++) {
//            System.out.println(NormalDistribution(1,0.0003f));
//        }\
      //  Long b = client.placeOrder("BTCUSDT", OrderSide.BUY, new BigDecimal(11400),new BigDecimal(1.1));


       // BigDecimal price = client.getLastPrice("GGC/USDT");

    //    OrderBook ob = client.getOrderBook("BTC/USDT",10);

       // Long[] a = client.cancelAll("BTC/USDT");

//        List<Long> orders = Lists.newArrayList();
//        orders.add(285100891183349760l);
//
//        Long[] c = client.cancelOrders("BTCUSDT",orders);
//
//        System.out.println("hello");


//        try {
//            Long[] res  = client.cancelAll("BTC/USDT");
//
//            System.out.println(Arrays.toString(res));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try {
            AccountBalance[] res = client.getAllUserAssets("BTC");

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


    public static double NormalDistribution(float u,float v){
        java.util.Random random = new java.util.Random();
        return Math.sqrt(v)*random.nextGaussian()+u;
    }


}
