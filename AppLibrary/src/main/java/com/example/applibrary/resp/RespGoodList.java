package com.example.applibrary.resp;

import com.example.applibrary.entity.GoodList;

import java.util.List;

public class RespGoodList {

    /**
     * {
     * "code":200,
     * "msg":"ok",
     * "data":[
     * {"id":17,"title":"asd","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},
     * {"id":18,"title":"asd","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},
     * {"id":19,"title":"asd","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},
     * {"id":20,"title":"213","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},
     * {"id":21,"title":"213","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},
     * {"id":22,"title":"213","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":0},
     * {"id":23,"title":"123","description":null,"is_show":0,"type":"","status":1,"add_time":0,"is_read":1}
     * ],"count":0}
     */
    private int code;
    private String msg;
    private List<GoodList> data;
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

    public List<GoodList> getData() {
        return data;
    }

    public void setData(List<GoodList> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean OK() {
        return code == 200;
    }
}
