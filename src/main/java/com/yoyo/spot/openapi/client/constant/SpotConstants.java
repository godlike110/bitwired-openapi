package com.yoyo.spot.openapi.client.constant;

import java.security.PrivateKey;

public class SpotConstants {

    public static final long DEFAULT_RECEIVING_WINDOW = 5_000L;

    public static final String API_ID = "279807384145125376";

    public static PrivateKey PRIVATE_KEY = null;

    public static final String SECRET = "" +
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEArqcIKQxR+h3xerwpxBzCtsw//tBqFKAgYQXmAZLtPqy+vMrZ\n" +
            "VUg0QRSnRWmv+7WGSPHyCIZ6xQopcmKOc+IFKR3vEzF8w33jO4kD+5Slo2v/gxIQ\n" +
            "GCtOGsE5e0bYTf7cBvVWnwfVzh2ZtoUIjqpWJFTs2J6QgKOduj2OJ2erXLO0L8rc\n" +
            "F3gqGKHG3j9fOovffUej2RnS+k7BzS4YiHXICtPVRajXu3AmnZ9QGz7Zy/YdfBZT\n" +
            "uUzspZo40XXd1rCKXYNHWwh4kSwrjRb7cuzFYOFSWlvvLpQEqQD/DFExdKlp2mlZ\n" +
            "CSCqr08PWQWWXcs2mRWGpZMSluKctZlWlsv1sQIDAQABAoIBAEVxcJMxrN6wTTWp\n" +
            "30l0zg70w3VD1QoCaR1qxPtXOjGzaduTJDr+jJhb5+53iVuh35TdbojpP6Nc2QH8\n" +
            "Ifv9oGOq3UZaZOsuAM3JOuYrQnjqFhw9cFzLyzBkJ8DWeGb6sO2rWxe8e8yKhHXI\n" +
            "C5D+NWYYxluTII2lgK9ujjkoxUAEI5Axc4BwsynrhpO8usA+jg+CTTzztperFblP\n" +
            "VT5KWUHY8HqnTx/qk32VvkAzRyFFjlWu4jrFssUtUyhh43jDc6jnvW8pJDz0lgkn\n" +
            "ZAC207hAULW+RlTztCe2emyQtBDXTlZPnj24LdhLzSW4Y65/EFbK8W6iRX2dVgJk\n" +
            "FTi1aOkCgYEA3MfjLKFatbimvLry9BZdgUDbpyAZHyD98PBlLsQn8B6e44x+AP5P\n" +
            "/364u41QDNNmYiGI9OBCNRVeUFqUjixtCf+naInzMNHxdWpEHmt4U6rV6b4MJej4\n" +
            "iDvRnSbLUfy0KmVSToLnH4Wc2/bStiS8QL3QQ7c2MKQWjUzpJSMHUQ8CgYEAyoNi\n" +
            "HjiN7SXkmJ3BL0NKnp+akP9VvuC06UMQfLOqc39VKh5R3OV3hAlprTG1HovH3xUG\n" +
            "IYI3SMbWERSlqOv1iDj3BW/RSGT5zYUwiTUrML4ep5pgXmlT9Gfkt4Ounh9KZI62\n" +
            "SV3c2Kvj770T1k+0r+PEB/1lle35oPKosPaKzT8CgYAbq9nnQBn0aL5NLUlCGoOB\n" +
            "rbVaDwXXBvWM+zRTVjaDO/NCJdutGDLCnQleSGneEajlZ7qYpnrl2FXH0HKKFztb\n" +
            "Y3WB/rhCSC3xP25mOaYNJn5FG5K+NaLgwE6XWcNl9TxHjAdy26lIJCDy2M60EYqe\n" +
            "uqudsRrTod1JvHgENY23NQKBgGyRuUWYbkZoXUCQuqQbAGNAD76SlSarE3JG2Qzx\n" +
            "yXz4E/Frpdm2T7fC0BvhHUfOCo6Hl+zZAciLiPJJTnLcZZrtZ0lL5Dhaoi3TrOyy\n" +
            "S4BrPMYAFpAnCbJrDIxyFWiCQADfHc0BfEfRB3dct8janHT7t/nMmOyjU4Q888FD\n" +
            "u7BDAoGAQvuA//2h5+LK8lU1Kew8RW0jNAIL6uCybUla/c99LLeTuQUx7lHyDUEg\n" +
            "rR+zKNRPskmN1CsIo8VLexe0ly3+9et1MRkE4pKXkY/fV9qP5icu7g1YMmA6vT5a\n" +
            "MviSQ/RkB+jY0sOF1PRaN6eo4sinBt0Ohoip/CXqEhHX7WSqwGA=\n" +
            "-----END RSA PRIVATE KEY-----";


    public static final String API_BASE_URL = "http://api.dev.mochini.cn";

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


    public static final String PLACE_ORDER = "/api-order/order/place";

    public static final String SPOT_CANCELALL = "/api-order/order/cancelAll";

    public static final String CANCEL = "/api-order/order/cancel";


    public static final String CANCEL_BATCH = "/api-order/order/cancelOrders";


    public static final String ACCOUNT_INFO_URL = "/api-asset/asset/getAllUserAssets";

    public static final String MARKET_DEPTH = "/api-market/public/market/depth";

    public static final String OPEN_ORDER = "/api-order/order/getOpenOrders";

    public static final String PRICE = "/api-market/public/market/price";

    public static final String PRIVATE_ORDER = "/api-private-order/order/place";


}
