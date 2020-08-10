package com.yoyo.spot.openapi.client.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyo.spot.openapi.client.YoApiError;
import com.yoyo.spot.openapi.client.exception.ApiException;
import com.yoyo.spot.openapi.client.security.AuthenticationInterceptor;
import com.yoyo.spot.openapi.client.service.YoYoApiService;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

/**
 * Generates a yoyo API implementation based on @see {@link YoYoApiService}.
 */
public class SpotApiServiceGenerator {
    private static final OkHttpClient sharedClient = new OkHttpClient.Builder()
            .pingInterval(20, TimeUnit.SECONDS)
            .build();

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final Converter.Factory converterFactory = JacksonConverterFactory.create(OBJECT_MAPPER);

    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, YoApiError> errorBodyConverter =
            (Converter<ResponseBody, YoApiError>) converterFactory.responseBodyConverter(
                    YoApiError.class, new Annotation[0], null);

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, String host, String apiKey, PrivateKey secret) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(converterFactory);

        if (StringUtils.isEmpty(apiKey) || secret == null) {
            retrofitBuilder.client(sharedClient);
        } else {
            // `adaptedClient` will use its own interceptor, but share thread pool etc with the 'parent' client
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey, secret);
            OkHttpClient adaptedClient = sharedClient.newBuilder().addInterceptor(interceptor).build();
            retrofitBuilder.client(adaptedClient);
        }

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    /**
     * Execute a REST call and block until the response is received.
     */
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                YoApiError apiError = getBHexApiError(response);
                throw new ApiException(apiError);
            }
        } catch (IOException e) {
            throw new ApiException(e);
        }
    }

    /**
     * Extracts and converts the response error body into an object.
     */
    public static YoApiError getBHexApiError(Response<?> response) throws IOException, ApiException {
        return errorBodyConverter.convert(response.errorBody());
    }

    /**
     * Returns the shared OkHttpClient instance.
     */
    public static OkHttpClient getSharedClient() {
        return sharedClient;
    }
}
