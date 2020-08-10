package com.yoyo.spot.openapi.client.service;


import com.alibaba.fastjson.JSONObject;
import com.yoyo.spot.openapi.client.constant.SpotConstants;
import com.yoyo.spot.openapi.client.model.AccountData;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * REST API URL mappings and endpoint security configuration.
 */
public interface YoYoApiService {
    @FormUrlEncoded
    @POST(SpotConstants.PLACE_ORDER)
    Call<JSONObject> placeOrder(
            @Field("price") String price,
            @Field("quantity") String quantity,
            @Field("direction") String side,
            @Field("symbol") String symbol,
            @Field("order_type") String type);

    @FormUrlEncoded
    @POST(SpotConstants.SPOT_CANCELALL)
    Call<Long[]> cancelAll(@Field("accountType") String accountType, @Field("symbol") String symbol);

    @FormUrlEncoded
    @POST(SpotConstants.CANCEL)
    Call<Void> cancel(@Field("orderId") Long orderId);

    @FormUrlEncoded
    @POST(SpotConstants.CANCEL_BATCH)
    Call<Long[]> cancelBatch(@Field("orderIds") List<Long> orderIds);


    @GET(SpotConstants.ACCOUNT_INFO_URL)
    Call<AccountData> getAccountInfo(@Query("assetSymbol") String symbol);

    @GET(SpotConstants.MARKET_DEPTH)
    Call<JSONObject> getMarketDepth(@Query("symbol") String symbol,@Query("limit") int limit);

    @GET(SpotConstants.OPEN_ORDER)
    Call<JSONObject> getOpenOrders(@Query("symbol") String symbol,
                                   @Query("pageNum") int pageNum,
                                   @Query("pageSize") int pageSize);

    @GET(SpotConstants.PRICE)
    Call<String> getPrice(@Query("symbol") String symbol);

    @FormUrlEncoded
    @POST(SpotConstants.PRIVATE_ORDER)
    Call<Long> privateOrder(@Field("accountType") String accountType,
                            @Field("price") String price,
                            @Field("quantity") String quantity,
                            @Field("side") String side,
                            @Field("symbol") String symbol);

}
