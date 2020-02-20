package com.gps.api.modules.cm.chinamobile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gps.common.helper.DESUtils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class CmApi {
    private static Logger logger = LoggerFactory.getLogger(CmApi.class);

    private static final String URL1 = "http://120.197.89.173:8081/openapi/";//资产管理
    private static final String APP_KEY = "toem04qw5d";
    private static final String APP_SEC = "6d120f2af39efcbcc6fa0cde28f0aee6";
    private final OkHttpClient mOkHttpClient;
    private CmApiService service;

    public CmApi() {
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor requestInterceptor = chain -> {
            Request original = chain.request();
            String transID = genTransID();
            HttpUrl.Builder ubuilder = original.url().newBuilder()
                    .addQueryParameter("appKey", APP_KEY)
                    .addQueryParameter("v", "3.0")
                    .addQueryParameter("format", "json")
                    .addQueryParameter("transID", transID);
            HttpUrl url = ubuilder.build();
            String signature = computeSignature(url);
            if (signature != null) {
                ubuilder.addQueryParameter("sign", signature);
                url = ubuilder.build();
            }
            Request.Builder builder = original.newBuilder()
                    .url(url)
//                        .header("appuk", appuk)
//                        .header("devicetoken", token)
//                        .header("signature", signature)
//                        .header("timestamp", times)
//                        .header("Accept-Encoding", "gzip")
                    ;
            Request request = builder.build();
            Response response = chain.proceed(request);
            response = processDes(response);
            return response;
        };
        mOkHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("39.108.13.58", 8089)))
                .build();

//        ObjectMapper om = new ObjectMapper();
//        om.setTypeFactory(TypeFactory.);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ApiTypeAdapterFactory())
//                .registerTypeAdapter(java.util.Date.class, new DateSerializer())//.setDateFormat(DateFormat.LONG)
//                .registerTypeAdapter(java.util.Date.class, new DateDeserializer())//.setDateFormat(DateFormat.LONG)
//                .registerTypeAdapter(java.util.Date.class, new String2DateDeserializer())//.setDateFormat("yyyy-MM-dd")
//                .setExclusionStrategies(exclusionStrategy)
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL1)
                .client(mOkHttpClient)
//                .addConverterFactory(JacksonConverterFactory.create(om)) // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson)) // 添加Gson转换器
                .build();

        service = retrofit.create(CmApiService.class);
    }

    private Response processDes(Response response) {
        try {
            String bo = response.body().string();
            logger.info("processDes ORIGIN: " + bo);
            String boD = DESUtils.decrypt(bo, APP_SEC.substring(0, 24));
            logger.info("processDes After: " + boD + "/" +response.body().contentType());
            ResponseBody body1 = ResponseBody.create(response.body().contentType(), boD.getBytes("UTF-8"));
            response = response.newBuilder().body(body1).build();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("processDes ERROR ", e);
        }
        return response;
    }

    private static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private String genTransID() {
        String time = sdfLong.format(new Date());
        return "2002665535" + time + "0001";
    }

    private String computeSignature(HttpUrl url) {
        Set<String> params = url.queryParameterNames();
        TreeMap<String, String> urlParamsMap = new TreeMap<>();
        for (String key : params) {
//            if ("action".equals(key)) {
//                continue;
//            }
            String obj = url.queryParameter(key);
            if (obj == null || "".equals(obj)) {
                continue;
            }
            urlParamsMap.put(key, obj);
        }
        String key = APP_SEC + computeUrl(urlParamsMap) + APP_SEC;
        String signature = Sha1.SHA1(key);
        return signature;
    }

    private String computeUrl(SortedMap<String, String> obj) {
        if (obj == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> key : obj.entrySet()) {
            sb.append(key.getKey());
            sb.append(key.getValue());
        }
        return sb.toString();
    }

    public CmApiService getService() {
        return service;
    }
}
