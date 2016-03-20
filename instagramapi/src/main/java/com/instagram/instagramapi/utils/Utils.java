package com.instagram.instagramapi.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sayyam on 3/17/16.
 */
public class Utils {

    public static String constructURL(String base, Map<String, String> params) {
        StringBuilder url = new StringBuilder(base);
        if (params != null && params.size() > 0) {
            url.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url.append(entry.getKey());
                url.append("=");
                try {
                    url.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    //TODO: implement some proper exception handling
                }
                url.append("&");   //not for the last one (but should be OK)
            }
        }

        return url.toString();
    }

    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }
}
