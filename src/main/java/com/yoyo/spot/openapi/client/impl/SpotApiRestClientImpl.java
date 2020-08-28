package com.yoyo.spot.openapi.client.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yoyo.spot.openapi.client.YoSpotApiRestClient;
import com.yoyo.spot.openapi.client.enums.OrderSide;
import com.yoyo.spot.openapi.client.model.AccountBalance;
import com.yoyo.spot.openapi.client.model.BookEntity;
import com.yoyo.spot.openapi.client.model.OrderBook;
import com.yoyo.spot.openapi.client.model.OrderInfo;
import com.yoyo.spot.openapi.client.service.YoYoApiService;
import com.yoyo.spot.openapi.client.utils.HttpsUrlValidator;
import com.yoyo.spot.openapi.client.utils.SignUtils;
import okhttp3.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.yoyo.spot.openapi.client.constant.SpotConstants.*;
import static com.yoyo.spot.openapi.client.impl.SpotApiServiceGenerator.executeSync;

/**
 * Implementation of yoyo spot's REST API using Retrofit with synchronous/blocking method calls.
 */

public class SpotApiRestClientImpl implements YoSpotApiRestClient {

    private static Logger logger = LoggerFactory.getLogger(SpotApiRestClientImpl.class);

    private final YoYoApiService apiService;

    private String apiKey;
    private String apiSec;

    private static final OkHttpClient client = new OkHttpClient.Builder().build();

    public static OkHttpClient getUnsafeOkHttpClient() {
        return client;
    }

    public SpotApiRestClientImpl(String host, String apiKey, String secret) {
//        PrivateKey privateKey = null;
//        try {
//            privateKey = getPrivateKey(secret);
//        } catch (Exception e) {
//            logger.error("init spotopenapi error", e);
//        }
        this.apiKey = apiKey;
        this.apiSec = secret;
        apiService = SpotApiServiceGenerator.createService(YoYoApiService.class, host, apiKey, secret);
    }

    public static PrivateKey getPrivateKey(String privateKeyPem) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        Security.addProvider(new BouncyCastleProvider());
        StringReader stringReader = new StringReader(privateKeyPem);
        PEMParser pemParser = new PEMParser(stringReader);
        Object keyObj = pemParser.readObject();
        PEMKeyPair obj = (PEMKeyPair) keyObj;
        JcaPEMKeyConverter converter = (new JcaPEMKeyConverter()).setProvider("BC");
        KeyPair kp = converter.getKeyPair(obj);
        PrivateKey privateKey = kp.getPrivate();
        return privateKey;

    }


    @Override
    public Long placeOrder(String symbol, OrderSide orderSide, BigDecimal price, BigDecimal quantity) {

        String timestamp = System.currentTimeMillis() + "";//时间戳毫秒数
        FormBody formBody = new FormBody.Builder()
                .add("direction", orderSide.getType())
                .add("order_type", "LIMIT")
                .add("price", price.setScale(4,RoundingMode.DOWN).toPlainString())
                .add("quantity", quantity.setScale(4, RoundingMode.DOWN).toPlainString())
                .add("symbol", symbol)
                .build();

        String params = "direction="+orderSide.getType()+"&order_type=LIMIT&price="+price.setScale(4,RoundingMode.DOWN).toPlainString()+"&quantity="+quantity.setScale(4,RoundingMode.DOWN).toPlainString()+"&symbol="+symbol;
        String apiKey = this.apiKey;//自定义66位api key
        String path = PLACE_ORDER;
        String content = "POST" + "|" + path + "|" + timestamp + "|" + params + "|" + apiKey;
        String secret = this.apiSec;//私钥
        String sign = "";
        try {
            sign = SignUtils.HMACSHA256(content, secret);
        } catch (Exception e) {
        }

        Request request = new Request.Builder()
                .url(API_BASE_URL + path).addHeader("X-API-KEY", apiKey)
                .addHeader("X-SIGNATURE", sign)
                .addHeader("X-TIMESTAMP", timestamp)
                .addHeader("X-GATEWAY-URI", path)
                .addHeader("X-Consumer-Username", "api-" + apiKey)
                .post(formBody)
                .build();
        try {
           // HttpsUrlValidator.retrieveResponseFromServer(API_BASE_URL);
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            System.out.println("result=" + jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }

       // JSONObject result = executeSync(apiService.placeOrder(price.setScale(4,RoundingMode.DOWN).toPlainString(),quantity.setScale(4,BigDecimal.ROUND_DOWN).toPlainString(),orderSide.getType(),symbol,"LIMIT"));
        return 1l;
    }

    @Override
    public Long privatePlaceOrder(String symbol, OrderSide orderSide, BigDecimal price, BigDecimal quantity) {
       // Long result = executeSync(apiService.privateOrder("SPOT", price.toPlainString(), quantity.toPlainString(), orderSide.getType(), symbol));

        OrderSide fist = null;
        OrderSide sec = null;
        if (orderSide == OrderSide.BUY) {
            fist = OrderSide.SELL;
            sec = OrderSide.BUY;
        } else {
            fist = OrderSide.BUY;
            sec = OrderSide.SELL;
        }
        placeOrder(symbol,fist,price,quantity);
        placeOrder(symbol,sec,price,quantity);
        return 0l;
    }

    @Override
    public Long[] cancelAll(String symbol) {
        return executeSync(apiService.cancelAll("SPOT", symbol));
    }

    @Override
    public void cancel(Long orderId) {
        executeSync(apiService.cancel(orderId));
    }

    @Override
    public Long[] cancelOrders(String symbol,List<Long> orders) {

        StringBuffer sb = new StringBuffer();

        for(int i=0;i<orders.size();i++) {
            sb.append(orders.get(i)).append(",");
        }

        String ids = sb.substring(0,sb.length()-1);

        String timestamp = System.currentTimeMillis() + "";//时间戳毫秒数
        FormBody formBody = new FormBody.Builder()
                .add("order_ids", ids)
                .add("symbol", symbol)
                .build();

        String params = "order_ids="+ids+"&symbol=" + symbol;
        String apiKey = this.apiKey;//自定义66位api key
        String path = CANCEL_BATCH;
        String content = "POST" + "|" + path + "|" + timestamp + "|" + params + "|" + apiKey;
        String secret = this.apiSec;//私钥
        String sign = "";
        try {
            sign = SignUtils.HMACSHA256(content, secret);
        } catch (Exception e) {
        }

        Request request = new Request.Builder()
                .url(API_BASE_URL + path).addHeader("X-API-KEY", apiKey)
                .addHeader("X-SIGNATURE", sign)
                .addHeader("X-TIMESTAMP", timestamp)
                .addHeader("X-GATEWAY-URI", path)
                .addHeader("X-Consumer-Username", "api-" + apiKey)
                .post(formBody)
                .build();
        try {
           // HttpsUrlValidator.retrieveResponseFromServer(API_BASE_URL);
            Response response = client.newCall(request).execute();
            String jsonString = response.body().string();
            System.out.println("result=" + jsonString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public AccountBalance[] getAllUserAssets(String code) {

        String timestamp = System.currentTimeMillis() + "";//时间戳毫秒数
        String apiKey = this.apiKey;//自定义66位api key
        String path = ACCOUNT_INFO_URL;
        String content = "GET" + "|" + path + "|" + timestamp + "|code="+code+"|" + apiKey;
        String secret = this.apiSec;//私钥
        String sign = "";
        try {
            sign = SignUtils.HMACSHA256(content, secret);
        } catch (Exception e) {
        }

        Request request = new Request.Builder()
                .url(API_BASE_URL + path+"?code=" + code).addHeader("X-API-KEY", apiKey)
                .addHeader("X-SIGNATURE", sign)
                .addHeader("X-TIMESTAMP", timestamp)
                .addHeader("X-GATEWAY-URI", path)
                .addHeader("X-Consumer-Username", "api-" + apiKey)
                .get()
                .build();
        Response response = null;
        try {
            // HttpsUrlValidator.retrieveResponseFromServer(API_BASE_URL);
            response = client.newCall(request).execute();
            AccountBalance acc = new AccountBalance();
            JSONObject obj = JSON.parseObject(response.body().string());
            acc.setFreezeBalance(obj.getJSONObject("data").getBigDecimal("frozen"));
            acc.setAssetSymbol(code);
            acc.setTotalBalance(obj.getJSONObject("data").getBigDecimal("free").add(obj.getJSONObject("data").getBigDecimal("frozen")));
           // String jsonString = response.body().string();
            //System.out.println("result=" + jsonString);

            return new AccountBalance[]{acc};

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public OrderBook getOrderBook(String symbol, int limit) {

        JSONObject jo = executeSync(apiService.getMarketDepth(symbol,limit));
        List<BookEntity> sellList = Lists.newArrayList();
        List<BookEntity> buyList = Lists.newArrayList();

        JSONArray buyArray = jo.getJSONArray("bids");
        for(int i=0;i<buyArray.size();i++) {
            buyList.add(new BookEntity(buyArray.getJSONArray(i).getBigDecimal(0),buyArray.getJSONArray(i).getBigDecimal(1)));
        }

        JSONArray sellArray = jo.getJSONArray("asks");
        for(int i=0;i<sellArray.size();i++) {
            sellList.add(new BookEntity(sellArray.getJSONArray(i).getBigDecimal(0),sellArray.getJSONArray(i).getBigDecimal(1)));
        }

        return new OrderBook(buyList,sellList);
    }

    @Override
    public List<OrderInfo> getOpenOrder(String symbol, int pageNo, int pageSize) {

        String timestamp = System.currentTimeMillis() + "";//时间戳毫秒数
        String apiKey = this.apiKey;//自定义66位api key
        String path = OPEN_ORDER;
        String params = "page_num="+pageNo+"&page_size="+pageSize+"&symbol="+symbol;
        String content = "GET" + "|" + path + "|" + timestamp + "|"+params+"|" + apiKey;
        String secret = this.apiSec;//私钥
        String sign = "";
        try {
            sign = SignUtils.HMACSHA256(content, secret);
        } catch (Exception e) {
        }

        Request request = new Request.Builder()
                .url(API_BASE_URL + path+"?" + params).addHeader("X-API-KEY", apiKey)
                .addHeader("X-SIGNATURE", sign)
                .addHeader("X-TIMESTAMP", timestamp)
                .addHeader("X-GATEWAY-URI", path)
                .addHeader("X-Consumer-Username", "api-" + apiKey)
                .get()
                .build();
        Response response = null;
        try {
            // HttpsUrlValidator.retrieveResponseFromServer(API_BASE_URL);
            response = client.newCall(request).execute();
            AccountBalance acc = new AccountBalance();
            JSONObject obj = JSON.parseObject(response.body().string());
             // String jsonString = response.body().string();
            //System.out.println("result=" + jsonString);
            List<OrderInfo> orderInfos = Lists.newArrayList();
            for(int i=0;i<obj.getJSONArray("data").size();i++) {
                OrderInfo o = new OrderInfo();
                JSONObject item = obj.getJSONArray("data").getJSONObject(i);
                o.setExecutedPrice(item.getString("average_price"));
                o.setExecutedQty(item.getString("filled_quantity"));
                o.setOrderId(item.getString("order_id"));
                o.setOrigQty(item.getString("quantity"));
                o.setPrice(item.getString("price"));
                o.setSide(item.getString("order_direction"));
                o.setSymbol(symbol);
                orderInfos.add(o);
            }

            return orderInfos;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public BigDecimal getLastPrice(String symbol) {

        Request request = new Request.Builder()
                .url(API_BASE_URL + PRICE+"?symbol=" + symbol)
                .get()
                .build();
        Response response = null;
        try {
            // HttpsUrlValidator.retrieveResponseFromServer(API_BASE_URL);
            response = client.newCall(request).execute();
            JSONObject jo = JSON.parseObject(response.body().string());
            return jo.getJSONObject("data").getBigDecimal("price");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String price = executeSync(apiService.getPrice(symbol));

        return new BigDecimal(price);
    }

}
