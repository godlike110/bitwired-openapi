package com.yoyo.spot.openapi.client;


import com.yoyo.spot.openapi.client.impl.SpotApiRestClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A factory for creating api client objects.
 */
public class YoSpotApiClientFactory {

    private static Logger logger = LoggerFactory.getLogger(YoSpotApiClientFactory.class);

    /**
     * API Key
     */
    private String apiKey;

    /**
     * Secret.
     */
    private String secret;

    private String host;

    /**
     * Instantiates a new api client factory.
     *
     * @param apiKey the API key
     * @param secret the Secret
     */
    private YoSpotApiClientFactory(String host, String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
        this.host = host;
    }


    /**
     * New instance.
     *
     * @param apiKey the API key
     * @param secret the Secret
     * @return the  api client factory
     */
    public static YoSpotApiClientFactory newInstance(String host, String apiKey, String secret) {
        return new YoSpotApiClientFactory(host, apiKey, secret);
    }


    /**
     * New instance without authentication.
     *
     * @return the api client factory
     */
    public static YoSpotApiClientFactory newInstance() {
        return new YoSpotApiClientFactory(null, null, null);
    }

    /**
     * Creates a new synchronous/blocking REST client.
     */
    public YoSpotApiRestClient newRestClient() {
        return new SpotApiRestClientImpl(host, apiKey, secret);
    }


}
