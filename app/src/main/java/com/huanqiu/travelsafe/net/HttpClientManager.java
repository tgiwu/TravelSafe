package com.huanqiu.travelsafe.net;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/4.
 */
public class HttpClientManager {
    public static String API_URL = "http://ngrok.zhangxd.cn";
    public static ServiceApi mApiService;

    public HttpClientManager() {
    }

    public static void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiService = retrofit.create(ServiceApi.class);
    }
}
