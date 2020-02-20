package com.gps.api.modules.cm.luat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LuatApi {

    private static final String URL1 = "http://api.openluat.com/";
    private static final String APP_KEY = "KhJvbHiZVgqzHLPW";
    private static final String APP_SEC = "p6dtyoT8B9L4ZYRpSRm2cHMBaSqxUxN93IQhqyghXY4StPyQK8p7okGj1JqDWjt4";
    private LuatApiService service;
    public class BasicAuthInterceptor implements Interceptor {
        private String credentials;

        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }
    public LuatApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor requestInterceptor = chain -> {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder()
//                    .header("Authorization", "Basic " +
//                            Base64.getUrlEncoder().encodeToString((APP_KEY + ":" + APP_SEC).getBytes()))
                    .header("Content-Type", "application/json");
            Request request = builder.build();
            Response response = chain.proceed(request);
            return response;
        };
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(new BasicAuthInterceptor(APP_KEY, APP_SEC))
                .addInterceptor(loggingInterceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .authenticator((route, response) -> {
                    System.out.println("Authenticating for response: " + response);
                    System.out.println("Challenges: " + response.challenges());
                    String credential = Credentials.basic(APP_KEY, APP_SEC);
                    return response.request().newBuilder().header("Authorization", credential).build();
                })
                .build();

//        ObjectMapper om = new ObjectMapper();
//        om.setTypeFactory(TypeFactory.);
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ApiTypeAdapterFactory())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL1)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)) // 添加Gson转换器
//                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        service = retrofit.create(LuatApiService.class);
    }

    public LuatApiService getService() {
        return service;
    }
}
