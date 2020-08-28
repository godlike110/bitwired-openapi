package com.yoyo.spot.openapi.client.constant;

import java.security.PrivateKey;

public class SpotConstants {

    public static final long DEFAULT_RECEIVING_WINDOW = 5_000L;

    public static final String API_ID = "uo3a6u07df2R8Z4TICDaKAWv5Rfo24dL";

    public static PrivateKey PRIVATE_KEY = null;

    public static final String SECRET = "Kr8tsJcLjV2tOM13JMouaKRGNf7uCGnz";


    public static final String API_BASE_URL = "https://api.bitwired.com";

    //public static final String API_BASE_URL = "http://127.0.0.1:18000";


    public static final String PONG_MSG_KEY = "pong";

    public static final String PING_MSG_KEY = "ping";

    /**
     * Streaming API base URL.
     */
    public static final String WS_API_BASE_URL = "wss://openapi-contract.coinbene.com/openapi/quote/ws/v1";


    public static final String SING_HEADER_HEADER_KEY = "SIGN";
    public static final String SING_HEADER_HEADER = SING_HEADER_HEADER_KEY + ":#";

    /**
     * 心跳间隔 1分钟一次
     */
    public static final long HEART_BEAT_INTERVAL = 60 * 1000;


    public static final String PLACE_ORDER = "/sapi/v1/spot/order";

    public static final String SPOT_CANCELALL = "/api-order/order/cancelAll";

    public static final String CANCEL = "/api-order/order/cancel";


    public static final String CANCEL_BATCH = "/sapi/v1/spot/cancel-orders";


    public static final String ACCOUNT_INFO_URL = "/sapi/v1/account/single";

    public static final String MARKET_DEPTH = "/api-market/public/market/depth";

    public static final String OPEN_ORDER = "/sapi/v1/spot/open-orders";

    public static final String PRICE = "/sapi/v1/market/price";

    public static final String PRIVATE_ORDER = "/api-private-order/order/place";


}
