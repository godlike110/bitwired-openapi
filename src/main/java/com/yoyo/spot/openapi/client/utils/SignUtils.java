package com.yoyo.spot.openapi.client.utils;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.util.*;

/**
 * @author wenzhiwei
 * @time 2019-05-04 10:39
 **/
public class SignUtils {

    public static String  sign(TreeMap<String, List<String>> params, String path, String timeStamp, String apiId, String secret,String method) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (String key : params.keySet()) {
            List<String> values = params.get(key);

            if(values.size()>1) {
                values.sort(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
            }
            for (String v : values) {
                sb.append(key).append("=").append(URLEncoder.encode(v, "UTF-8")).append("&");
            }
        }
        String preString = sb.toString().substring(0, sb.toString().length() - 1);
        preString = method.toLowerCase() + "|" + path + "|" + timeStamp + "|" + preString + "|" + apiId;
        String sign = HMACSHA256(preString, secret);
        return sign;
    }

    public static String HMACSHA256(String data, String key) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }

        return sb.toString();

    }

}
