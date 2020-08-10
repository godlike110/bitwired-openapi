package com.yoyo.spot.openapi.client.security;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yoyo.spot.openapi.client.utils.SignUtils;
import okhttp3.*;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.*;

import static com.yoyo.spot.openapi.client.constant.SpotConstants.API_BASE_URL;

/**
 * A request interceptor that injects the API Key Header into requests, and signs messages, whenever required.
 */
public class AuthenticationInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private final String apiKey;

    private final String secret;

    public AuthenticationInterceptor(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        String path = original.url().uri().getPath();
        if (path.contains("public/")) {
            return chain.proceed(original);
        }
        String method = original.method().toUpperCase();
        TreeMap<String, List<String>> paramMap = Maps.newTreeMap();
        if (method.equals("POST")) {
            FormBody body = (FormBody)original.body();

            if (body != null) {
                int size = body.size();
                for (int i = 0; i < size; i++) {
                    List<String> values = paramMap.computeIfAbsent(body.encodedName(i), k -> Lists.newArrayList());
                    values.add(body.value(i));
                }
            }
        } else {
            // HTTP GET
            HttpUrl httpUrl = original.url();
            Set<String> paramKeys = httpUrl.queryParameterNames();
            for (String key : paramKeys) {
                paramMap.put(key, httpUrl.queryParameterValues(key));
            }
        }

        String timeStamp = System.currentTimeMillis()+"";
        String sign = "";
        try {
            sign = SignUtils.sign(paramMap, path, timeStamp, this.apiKey, this.secret,method);
        } catch (Exception e) {
            logger.error("yoyoapi sign error", e);
        }

        Request.Builder builder = original.newBuilder()
                .addHeader("X-API-KEY", apiKey)
                .addHeader("X-SIGNATURE", sign)
                .addHeader("X-TIMESTAMP", timeStamp)
                .addHeader("X-GATEWAY-URI", path)
                .addHeader("X-Consumer-Username", "api-" + apiKey);

        return chain.proceed(builder.build());
    }

    private static void addParam(Map<String, List<String>> params, String key, String value) {
        List<String> values = params.computeIfAbsent(key, k -> Lists.newArrayList());
        values.add(value);
    }

    public static String requestBodyToString(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }


    /**
     * Extracts the request body into a String.
     *
     * @return request body as a string
     */
    @SuppressWarnings("unused")
    private static String bodyToString(RequestBody request) {
        try (final Buffer buffer = new Buffer()) {
            final RequestBody copy = request;
            if (copy != null) {
                copy.writeTo(buffer);
            } else {
                return "";
            }
            return buffer.readUtf8();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthenticationInterceptor that = (AuthenticationInterceptor) o;
        return Objects.equals(apiKey, that.apiKey) &&
                Objects.equals(secret, that.secret);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiKey, secret);
    }
}
