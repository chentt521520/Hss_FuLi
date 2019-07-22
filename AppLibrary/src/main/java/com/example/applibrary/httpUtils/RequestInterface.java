package com.example.applibrary.httpUtils;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

//请求接口
public interface RequestInterface {

    //https://sx.riserays.com/sys/login?passWord=1234&userName=028-69768927
    //https://sx.riserays.com/web/baseDoctor/doctorListOther
    //028-69768927 密码: 1234
    //wss://sx.riserays.com/html/onlineUserControl

    @GET
    Call<ResponseBody> getRequest(@Url String url);

    @POST
    Call<ResponseBody> postRequest(@Url String url);

    @GET
    Observable<RequestMsg> getReqMsg(@Url String url);

    @POST
    Observable<RequestMsg> postReqMsg(@Url String url);
}
