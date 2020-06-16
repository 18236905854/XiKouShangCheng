package com.xk.mall.api;


import com.blankj.utilcode.util.DeviceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.xk.mall.BuildConfig;
import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.convert.MyGsonConverterFactory;
import com.xk.mall.base.cookie.CookieManger;
import com.xk.mall.base.gson.DoubleDefault0Adapter;
import com.xk.mall.base.gson.IntegerDefault0Adapter;
import com.xk.mall.base.gson.LongDefault0Adapter;
import com.xk.mall.utils.LoggingInterceptor;
import com.xk.mall.utils.OSUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static okhttp3.internal.Util.UTF_8;

/**
 * File descripition:   创建Retrofit
 */

public class ApiRetrofit {
    public static String BASE_SERVER_URL = BaseContent.baseUrl;
    private static final String TAG = "ApiRetrofit %s";
    private static ApiRetrofit apiRetrofit;
    private Retrofit retrofit;
    private ApiServer apiServer;
    private static Gson gson;
    private static final int DEFAULT_TIMEOUT = 30;


    public ApiRetrofit() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder
                .cookieJar(new CookieManger(MyApplication.getContext()))
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .addInterceptor(new CommonInterceptor())
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeadUrlInterceptor())
                .sslSocketFactory(getSSLSocketFactory())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);//错误重联

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create(buildGson()))//添加json转换框架(正常转换框架)
                .addConverterFactory(MyGsonConverterFactory.create(buildGson()))//添加json自定义（根据需求）
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build();
        apiServer = retrofit.create(ApiServer.class);
    }

    /**
     * 重新设置请求地址
     * @param url
     */
    public void setBaseServerUrl(String url){
        BASE_SERVER_URL = url;
    }

    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     */
    public static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                    .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                    .registerTypeAdapter(long.class, new LongDefault0Adapter())
                    .create();
        }
        return gson;
    }

    public static ApiRetrofit getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }

    public ApiServer getApiService() {
        return apiServer;
    }

    /**
     * 请求访问quest    打印日志
     * response拦截器
     */
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.currentTimeMillis();
            Response response = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Logger.wtf(TAG, "----------Request Start----------------");
            Logger.e(TAG, "| " + request.toString() + "===========" + request.headers().toString());
//            printParams(request.body());
            Logger.json(content);
            Logger.e(content);
            Logger.wtf(TAG, "----------Request End:" + duration + "毫秒----------");

            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    };

    /**
     * 添加 请求头
     */
    public class HeadUrlInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "text/html; charset=UTF-8")
                    .addHeader("Vary", "Accept-Encoding")
                    .addHeader("Server", "Apache")
                    .addHeader("Pragma", "no-cache")
                    .addHeader("Authorization", MyApplication.token)
                    .build();
            return chain.proceed(request);
        }
    }

    /**
     * 不验证证书
     *
     * @return
     */
    private static SSLSocketFactory getSSLSocketFactory() {
        //创建一个不验证证书链的证书信任管理器。
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            return sslContext
                    .getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 打印请求参数
     * @param body
     */
    private void printParams(RequestBody body) {
        Buffer buffer = new Buffer();
        try {
            body.writeTo(buffer);
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = body.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF_8);
            }
            String params = buffer.readString(charset);
            Logger.e(TAG, "请求参数： | " + params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class CommonInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request oldRequest = chain.request();
            // 添加新的参数
            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
                    .addQueryParameter("xkuid", MyApplication.userId)
                    .addQueryParameter("deviceType", "1")
                    .addQueryParameter("bundleId", BuildConfig.APPLICATION_ID)
                    .addQueryParameter("deviceModel", DeviceUtils.getManufacturer() + " " + DeviceUtils.getModel())
                    .addQueryParameter("deviceId", MyApplication.imei)
                    .addQueryParameter("osType", "1")
                    .addQueryParameter("osVersion", DeviceUtils.getSDKVersionName())
                    .addQueryParameter("timestamp", "" + System.currentTimeMillis())
                    .addQueryParameter("appVersion", BuildConfig.VERSION_NAME);
            // 新的请求
            Request newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
                    .build();
            return chain.proceed(newRequest);
        }
    }
}
