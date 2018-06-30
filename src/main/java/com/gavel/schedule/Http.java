package com.gavel.schedule;

import okhttp3.*;
import okhttp3.OkHttpClient.Builder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Http {

    private static final Logger LOG = LoggerFactory.getLogger(Http.class);

    static class CookieManager implements okhttp3.CookieJar {

        private final Map<String, List<Cookie>> cookieStore = new ConcurrentHashMap<String, List<Cookie>>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            String host = url.host();
            int port = url.port();
            String key = host + "_" + port;
            cookieStore.put(key, cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            String host = url.host();
            int port = url.port();
            String key = host + "_" + port;
            if (StringUtils.isBlank(key)){
                return Collections.EMPTY_LIST;
            }
            return cookieStore.containsKey(key) ? cookieStore.get(key) : Collections.EMPTY_LIST;
        }
    }

    /**
     * 解析query参数名称和值
     * @param uri
     * @return
     */
    public static Map<String, String> queryParamsParser(String uri){
        if (StringUtils.isBlank(uri)){
            return Collections.EMPTY_MAP;
        }

        String queryString = uri;
        if ( uri.contains("?") ){
            queryString = StringUtils.substringAfter(uri, "?");
        }
        LOG.debug(queryString);
        if (StringUtils.isBlank(queryString)){
            return Collections.EMPTY_MAP;
        }

        String[] params = queryString.split("&");
        if ( params==null || params.length==0 ){
            return Collections.EMPTY_MAP;
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        for (String param : params) {
            if ( !param.contains("=") ){
                LOG.error("Param: " + param + ": 格式不正确");
                continue;
            }
            String[] values = param.split("=");
            if ( values==null || values.length<1 || values.length>2 ){
                LOG.error("Param: " + param + ": 格式不正确");
                continue;
            }
            paramMap.put(values[0], values.length==1?"":values[1]);
        }

        LOG.debug("Param: " + paramMap);
        return paramMap;
    }

    private static final OkHttpClient client = new Builder()
            .cookieJar(new CookieManager())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();


    public static String get(String url) throws Exception {

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        LOG.debug("Status Code: " + response.code() + ": " + url);
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        return response.body().string();
    }

    public static String post(String url, Map<String, String> params) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null && params.size() > 0) {
            for (String name : params.keySet()) {
                builder.add(name, params.get(name));
            }
        }

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Referer", "http://113.140.66.227:8111/envinfo_web/enterprisePgasR.do?op=toList")
                .post(builder.build()).build();
        Response response = client.newCall(request).execute();
        LOG.debug("Status Code: " + response.code() + ": " + url);
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        return response.body().string();
    }


}
