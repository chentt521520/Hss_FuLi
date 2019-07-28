package com.example.applibrary.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderStatus {
    /**
     * _type : 1
     * _title : 未发货
     * _msg : 商家未发货,请耐心等待
     * _class : state-nfh
     * _payType : 余额支付
     * _deliveryType : 其他方式
     */

    @JSONField(name = "_type")
    private int type;
    @JSONField(name = "_title")
    private String title;
    @JSONField(name = "_msg")
    private String msg;
    @JSONField(name = "_class")
    private String classs;
    @JSONField(name = "_payType")
    private String payType;
    @JSONField(name = "_deliveryType")
    private String deliveryType;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }


}
