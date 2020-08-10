package com.yoyo.spot.openapi.client.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.yoyo.spot.openapi.client.security.HmacSHA256Signer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author wenzhiwei
 * @time 2019-05-04 10:39
 **/
public class SignUtils {

    public static JSONObject createRequestObject(Map<String, Object> params, String path, long timeStamp, String apiId, String secret) {

        TreeMap<String, Object> paramMap = Maps.newTreeMap();

        JSONObject object = new JSONObject();
        object.put("apiid", apiId);
        object.put("timestamp", timeStamp);

        for (String p : params.keySet()) {
            object.put(p, params.get(p));
            paramMap.put(p, String.valueOf(params.get(p)));
        }

        StringBuffer sb = new StringBuffer();

        Iterator<String> keyIterator = paramMap.keySet().iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            sb.append(key).append("=").append(paramMap.get(key)).append("&");
        }

        String preString = sb.toString().substring(0, sb.toString().length() - 1);

        preString = path + "?" + preString + timeStamp;

        String sign = HmacSHA256Signer.sha256_HMAC(preString, secret);

        object.put("sign", sign);

        return object;

    }

    public static String sign(TreeMap<String, List<String>> params, String path, long timeStamp, String apiId, PrivateKey secret) throws SignatureException, InvalidKeyException, UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        for (String key : params.keySet()) {
            for (String v : params.get(key)) {
                sb.append(key).append("=").append(URLEncoder.encode(v, "UTF-8")).append("&");
            }
        }
        String preString = sb.toString().substring(0, sb.toString().length() - 1);
        preString = path + "?" + preString + timeStamp;
        String sign = HmacSHA256Signer.sha256_HMAC_RSA(preString, secret);
        return sign;
    }

    public static String createRequestObject(Map<String, Object> params, String path, long timeStamp, String apiId, PrivateKey secret) throws SignatureException, InvalidKeyException, UnsupportedEncodingException {

        TreeMap<String, Object> paramMap = Maps.newTreeMap();

        JSONObject object = new JSONObject();
        object.put("apiid", apiId);
        object.put("timestamp", timeStamp);

        for (String p : params.keySet()) {
            object.put(p, params.get(p));
            paramMap.put(p, String.valueOf(params.get(p)));
        }

        StringBuffer sb = new StringBuffer();

        Iterator<String> keyIterator = paramMap.keySet().iterator();

        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            sb.append(key).append("=").append(URLEncoder.encode(paramMap.get(key).toString(), "UTF-8")).append("&");
        }

        String preString = sb.toString().substring(0, sb.toString().length() - 1);

        preString = path + "?" + preString + timeStamp;
        String sign = HmacSHA256Signer.sha256_HMAC_RSA(preString, secret);
        return sign;

    }
}
