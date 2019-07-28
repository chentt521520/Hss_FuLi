package com.example.applibrary.httpUtils;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.resp.RespGoodDetail;
import com.example.applibrary.utils.StringUtils;

import java.io.IOException;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpRequest {

    /**
     * 发起一个异步的post请求
     *
     * @param url    url地址
     * @param params 参数集合
     */
    public static void postRequest(String url, final Map<String, Object> params, final OnHttpCallback<RespGoodDetail.DetailsInfo> callback) {
        String baseUrl = Netconfig.httpHost + url;

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(params);
        OkhttpTool.getOkhttpTool().uploadJson(baseUrl, jsonObject.toString(), new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                callback.error(10002, e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String jsonStr = new String(response.body().bytes());//把原始数据转为字符串
                if (response.isSuccessful()) {
                    try {
                        RespGoodDetail result = JSONObject.parseObject(jsonStr, RespGoodDetail.class);
                        if (result == null) {
                            callback.error(10001, "解析出错");
                        } else if (result.getCode() == 200) {
                            callback.success(result.getData());
                        } else {
                            callback.error(result.getCode(), TextUtils.isEmpty(result.getMsg()) ? "获取失败" : result.getMsg());
                        }
                    } catch (Exception e) {
                        callback.error(10001, e.getMessage());
                    }
                } else {
                    callback.error(response.code(), response.message());
                }
            }
        });
    }
}
