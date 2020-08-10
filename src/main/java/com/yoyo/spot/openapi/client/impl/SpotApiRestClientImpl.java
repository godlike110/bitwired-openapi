package com.yoyo.spot.openapi.client.impl;


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
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Map;

import static com.yoyo.spot.openapi.client.impl.SpotApiServiceGenerator.executeSync;

/**
 * Implementation of yoyo spot's REST API using Retrofit with synchronous/blocking method calls.
 */

public class SpotApiRestClientImpl implements YoSpotApiRestClient {

    private static Logger logger = LoggerFactory.getLogger(SpotApiRestClientImpl.class);

    private final YoYoApiService apiService;

    public SpotApiRestClientImpl(String host, String apiKey, String secret) {
        PrivateKey privateKey = null;
        try {
            privateKey = getPrivateKey(secret);
        } catch (Exception e) {
            logger.error("init spotopenapi error", e);
        }
        apiService = SpotApiServiceGenerator.createService(YoYoApiService.class, host, apiKey, privateKey);
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
        Long result = executeSync(apiService.placeOrder("SPOT", price.toPlainString(), quantity.toPlainString(), orderSide.getType(), symbol, "GTC", "LIMIT"));
        return result;
    }

    @Override
    public Long privatePlaceOrder(String symbol, OrderSide orderSide, BigDecimal price, BigDecimal quantity) {
        Long result = executeSync(apiService.privateOrder("SPOT", price.toPlainString(), quantity.toPlainString(), orderSide.getType(), symbol));
        return result;
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
    public Long[] cancelOrders(List<Long> orders) {
        return executeSync(apiService.cancelBatch(orders));
    }

    @Override
    public AccountBalance[] getAllUserAssets(String symbol) {
        return executeSync(apiService.getAccountInfo(symbol)).getDetails();
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

        JSONObject ja = executeSync(apiService.getOpenOrders(symbol,pageNo,pageSize));
        return (List)ja.get("list");
    }

    @Override
    public BigDecimal getLastPrice(String symbol) {

        String price = executeSync(apiService.getPrice(symbol));

        return new BigDecimal(price);
    }

}
