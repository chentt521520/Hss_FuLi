package com.example.applibrary.httpUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.applibrary.base.Netconfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//网络
public class Notwork {

    //接口
    private static RequestInterface requestInterface;
    //连接
    private static OkHttpClient okHttpClient = new OkHttpClient();
    //配置转化库，采用Gson
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    //配置回调库，采用RxJava
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();

    //设置http
    private static OkHttpClient okHttpSet() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //设置请求超时时长为30秒
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }

    //url：服务器地址，基础请求路径，最好以"/"结尾
    public static Retrofit getRetrofit() {
        try {
            Retrofit.Builder retrofit = new Retrofit.Builder()
                    .client(okHttpClient);
            retrofit.baseUrl(Netconfig.httpHost)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
            ;
            return retrofit.build();
        } catch (Exception e) {
            return null;
        }
    }

    //核查网络
    public static boolean checkNet(Context context) {
        boolean flag = true;//有网络
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo == null || !netInfo.isAvailable()) {
            flag = false;
        }
        return flag;
    }

    //获取接口、处方加载时可以调用
    public static RequestInterface getRequestInterface() {
        if (requestInterface == null) {
            requestInterface = getRetrofit().create(RequestInterface.class);
        }
        return requestInterface;
    }


}
