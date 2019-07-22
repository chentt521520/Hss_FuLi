package com.example.haoss.person.dingdan.entity;

import com.example.haoss.pay.GoodsBuyInfo;

import java.io.Serializable;

//订单类商品
public class OrderFormShoppingInfo implements Serializable {
    private int idOrder; //
    private String orderId = "";    //订单id
    private String orderUnique = "";    //订单id
    private String payPriceOrder = "0";    //订单支付金额
    private String totalNumOrder = "0";    //订单总数量
    private String totalPriceOrder = "0";  //订单总金额
    private String payPostageOrder = "0";  //订单运费
    private String totalPostageOrder = "0";    //订单总运费
    private int statusOrder; //订单状态
    private int refundStatusOrder;   //订单退款状态
    private String payTypeOrder = ""; //订单支付方式
    private String couponPriceOrder = "0"; //订单优惠劵金额
    private String deductionPriceOrder = "0";  //订单减去金额
    //附加
    private String payTimeOrder = ""; //订单支付时间
    private String addTimeOrder = ""; //订单添加时间
    private int typeOrder;   //订单状态 0：待发货；1：待收货；2：已收货；3：待评价；4：已完成(已评价);
    private String titleOrder = "";   //订单支付状态
    private String msgOrder = ""; //订单提示信息

    //订单商品信息list型
    private int goodsId; //商品id
    private String goodsImage = "";  //商品图片
    private String goodsName = "";    //商品名称
    private int goodsNum;    //商品数量
    private String goodsAddTime = "";    //商品添加时间
    private int goodsIsPay; //商品是否支付
    private int goodsIsDel; //商品是否删除
    private double goodsPrice;   //商品价格
    private String goodsTruePrice = "0";  //商品最终价格
    private String goodsUnitName = "";   //商品单位
    private String goodsUnique = ""; //

    //订单商品信息再加
    private String goodsState = "";   //商品说明
    private String goodsSpec = "";   //商品规格

    private GoodsBuyInfo goodsBuyInfo;

    public GoodsBuyInfo getGoodsBuyInfo() {
        return goodsBuyInfo;
    }

    public void setGoodsBuyInfo(GoodsBuyInfo goodsBuyInfo) {
        this.goodsBuyInfo = goodsBuyInfo;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderUnique() {
        return orderUnique;
    }

    public void setOrderUnique(String orderUnique) {
        this.orderUnique = orderUnique;
    }

    public String getPayPriceOrder() {
        return payPriceOrder;
    }

    public void setPayPriceOrder(String payPriceOrder) {
        this.payPriceOrder = payPriceOrder;
    }

    public String getTotalNumOrder() {
        return totalNumOrder;
    }

    public void setTotalNumOrder(String totalNumOrder) {
        this.totalNumOrder = totalNumOrder;
    }

    public String getTotalPriceOrder() {
        return totalPriceOrder;
    }

    public void setTotalPriceOrder(String totalPriceOrder) {
        this.totalPriceOrder = totalPriceOrder;
    }

    public String getPayPostageOrder() {
        return payPostageOrder;
    }

    public void setPayPostageOrder(String payPostageOrder) {
        this.payPostageOrder = payPostageOrder;
    }

    public String getTotalPostageOrder() {
        return totalPostageOrder;
    }

    public void setTotalPostageOrder(String totalPostageOrder) {
        this.totalPostageOrder = totalPostageOrder;
    }

    public int getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(int statusOrder) {
        this.statusOrder = statusOrder;
    }

    public int getRefundStatusOrder() {
        return refundStatusOrder;
    }

    public void setRefundStatusOrder(int refundStatusOrder) {
        this.refundStatusOrder = refundStatusOrder;
    }

    public String getPayTypeOrder() {
        return payTypeOrder;
    }

    public void setPayTypeOrder(String payTypeOrder) {
        this.payTypeOrder = payTypeOrder;
    }

    public String getCouponPriceOrder() {
        return couponPriceOrder;
    }

    public void setCouponPriceOrder(String couponPriceOrder) {
        this.couponPriceOrder = couponPriceOrder;
    }

    public String getDeductionPriceOrder() {
        return deductionPriceOrder;
    }

    public void setDeductionPriceOrder(String deductionPriceOrder) {
        this.deductionPriceOrder = deductionPriceOrder;
    }

    public String getPayTimeOrder() {
        return payTimeOrder;
    }

    public void setPayTimeOrder(String payTimeOrder) {
        this.payTimeOrder = payTimeOrder;
    }

    public String getAddTimeOrder() {
        return addTimeOrder;
    }

    public void setAddTimeOrder(String addTimeOrder) {
        this.addTimeOrder = addTimeOrder;
    }

    public int getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(int typeOrder) {
        this.typeOrder = typeOrder;
    }

    public String getTitleOrder() {
        return titleOrder;
    }

    public void setTitleOrder(String titleOrder) {
        this.titleOrder = titleOrder;
    }

    public String getMsgOrder() {
        return msgOrder;
    }

    public void setMsgOrder(String msgOrder) {
        this.msgOrder = msgOrder;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage) {
        this.goodsImage = goodsImage;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsAddTime() {
        return goodsAddTime;
    }

    public void setGoodsAddTime(String goodsAddTime) {
        this.goodsAddTime = goodsAddTime;
    }

    public int getGoodsIsPay() {
        return goodsIsPay;
    }

    public void setGoodsIsPay(int goodsIsPay) {
        this.goodsIsPay = goodsIsPay;
    }

    public int getGoodsIsDel() {
        return goodsIsDel;
    }

    public void setGoodsIsDel(int goodsIsDel) {
        this.goodsIsDel = goodsIsDel;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsTruePrice() {
        return goodsTruePrice;
    }

    public void setGoodsTruePrice(String goodsTruePrice) {
        this.goodsTruePrice = goodsTruePrice;
    }

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public String getGoodsUnique() {
        return goodsUnique;
    }

    public void setGoodsUnique(String goodsUnique) {
        this.goodsUnique = goodsUnique;
    }

    public String getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(String goodsState) {
        this.goodsState = goodsState;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }
}
