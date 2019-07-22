package com.example.haoss.person.aftersale.entity;

import java.io.Serializable;

//售后数据
public class AfterSaleInfo implements Serializable {

    private int id; //商品id
    private String image = "";   //图片地址
    private String name = "";    //商品名称
    private String specification = "";   //规格
    private int money;  //金额
    private int number; //数量
    private int type;   //类型：0：完成未申请，1：处理中，2：处理完成
    private int applyForType;   //申请类型1退款/2退款退货/3换货
    private int status; //处理状态 0：未知，1：成功，2：失败，3：关闭
    private String explain = ""; //说明
    private long endTime;  //售后结束时间
    private boolean isTimeout = false;  //是否超出售后时间

    //退货详情部分数据
    private String quitDisposeTime = "";    //退换处理时间
    private String quitCause = "";   //退换原因
    private String quitMoney = "";   // 退换金额
    private String quitApplyForTime = "";    //退换申请时间
    private String quitOdd = ""; //退换单号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getApplyForType() {
        return applyForType;
    }

    public void setApplyForType(int applyForType) {
        this.applyForType = applyForType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isTimeout() {
        return isTimeout;
    }

    public void setTimeout(boolean timeout) {
        isTimeout = timeout;
    }

    public String getQuitDisposeTime() {
        return quitDisposeTime;
    }

    public void setQuitDisposeTime(String quitDisposeTime) {
        this.quitDisposeTime = quitDisposeTime;
    }

    public String getQuitCause() {
        return quitCause;
    }

    public void setQuitCause(String quitCause) {
        this.quitCause = quitCause;
    }

    public String getQuitMoney() {
        return quitMoney;
    }

    public void setQuitMoney(String quitMoney) {
        this.quitMoney = quitMoney;
    }

    public String getQuitApplyForTime() {
        return quitApplyForTime;
    }

    public void setQuitApplyForTime(String quitApplyForTime) {
        this.quitApplyForTime = quitApplyForTime;
    }

    public String getQuitOdd() {
        return quitOdd;
    }

    public void setQuitOdd(String quitOdd) {
        this.quitOdd = quitOdd;
    }
}
