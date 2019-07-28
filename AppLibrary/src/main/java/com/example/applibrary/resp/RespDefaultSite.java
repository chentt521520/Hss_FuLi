package com.example.applibrary.resp;

import com.example.applibrary.entity.AddreInfo;

public class RespDefaultSite {

    /**
     * code : 200
     * msg : ok
     * data : {"id":42,"real_name":"陈婷婷","phone":"15667074017","province":"四川省","city":"成都市","district":"青羊区","detail":"府南街道","is_default":1}
     * count : 0
     */

    private int code;
    private String msg;
    private AddreInfo data;
    private int count;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AddreInfo getData() {
        return data;
    }

    public void setData(AddreInfo data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
