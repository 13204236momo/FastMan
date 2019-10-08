package com.tianshang.common.Retrofit;

import com.tianshang.common.BuildConfig;
import com.tianshang.common.Retrofit.interceptor.BaseParamsInterceptor;
import com.tianshang.common.Retrofit.interceptor.BaseUrlInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitHelper {
    public static Retrofit instance;
    private static final String BASE_URL_RELEASE = "https://api.yonyoucloud.com/";
    private static final String BASE_URL_DEBUG = "https://api.yonyoucloud.com/";
    /**
     * 超时时间
     */
    private static final long TIME_OUT = 5000;

    public static Retrofit getInstance() {
        if (instance == null) {
            synchronized (RetrofitHelper.class) {
                instance = getRetrofit();
            }
        }
        return instance;
    }


    private static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .client(getMyClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    /**
     * 根据debug或release来设置baseUrl
     * @return
     */
    private static String getBaseUrl(){
        String baseUrl;
        if (BuildConfig.DEBUG){
            baseUrl = BASE_URL_DEBUG;
        }else {
            baseUrl = BASE_URL_RELEASE;
        }
        return baseUrl;
    }

    private static OkHttpClient getMyClient(){
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new BaseUrlInterceptor())
                .addInterceptor(new BaseParamsInterceptor())
                .build();
        return okHttpClient;
    }
}
