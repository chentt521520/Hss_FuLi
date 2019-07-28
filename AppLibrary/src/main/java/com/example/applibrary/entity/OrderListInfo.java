package com.example.applibrary.entity;


import java.util.List;

public class OrderListInfo {

    /**
     * add_time : 1563159104
     * seckill_id : 0
     * bargain_id : 0
     * combination_id : 0
     * id : 352
     * order_id : wx2019071510514410001
     * pay_price : 63.00
     * total_num : 1
     * total_price : 53.00
     * pay_postage : 10.00
     * total_postage : 10.00
     * paid : 1
     * status : 0
     * refund_status : 0
     * pay_type : yue
     * coupon_price : 0.00
     * deduction_price : 0.00
     * pink_id : 0
     * delivery_type : null
     * cartInfo : [{"id":761,"uid":15,"type":"product","product_id":51,"product_attr_unique":"048849c4","cart_num":1,"add_time":1563159100,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":1,"productInfo":{"id":51,"image":"http://qiniu.haoshusi.com/images/f3f57374e147fe71e08de2cd5fa0513f.png","slider_image":["http://qiniu.haoshusi.com/images/2e2f54719e6239da4b0a124ea0e1fa9f.png","http://qiniu.haoshusi.com/images/a7f08cbfb6add6829e0ea86027f94a28.png","http://qiniu.haoshusi.com/images/e04e018629ba8092e7ded5892535b1e9.png","http://qiniu.haoshusi.com/images/f53095394d85317f5d509fbf5556796c.png","http://qiniu.haoshusi.com/images/f3f57374e147fe71e08de2cd5fa0513f.png"],"price":"53.00","ot_price":"68.90","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"612","sales":1,"stock":25767,"store_name":"脉动菠萝青柠口味600ml*15瓶/整箱装维生素功能运动饮料健康解渴","store_info":"","unit_name":"件","is_show":1,"is_del":0,"is_postage":0,"cost":"1.00","attrInfo":{"product_id":51,"suk":"青柠600ml*15","stock":233,"sales":0,"price":"53.00","image":"http://qiniu.haoshusi.com/images/f3f57374e147fe71e08de2cd5fa0513f.png","unique":"048849c4","cost":"1.00"}},"truePrice":53,"vip_truePrice":0,"trueStock":233,"costPrice":"1.00","unique":"e5c63870e64b16af4d472cc9637840aa","is_reply":0}]
     * _status : {"_type":1,"_title":"未发货","_msg":"商家未发货,请耐心等待","_class":"state-nfh","_payType":"余额支付"}
     * _pay_time : 2019-07-15 10:51:44
     * _add_time : 2019-07-15 10:51:44
     * status_pic :
     */

    private long add_time;
    private int seckill_id;
    private int bargain_id;
    private int combination_id;
    private int id;
    private String order_id;
    private String pay_price;
    private int total_num;
    private String total_price;
    private String pay_postage;
    private String total_postage;
    private int paid;
    private int status;
    private int refund_status;
    private String pay_type;
    private String coupon_price;
    private String deduction_price;
    private int pink_id;
    private Object delivery_type;
    private OrderStatus _status;
    private String _pay_time;
    private String _add_time;
    private String status_pic;
    private List<ShoppingCartInfo> cartInfo;

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public int getSeckill_id() {
        return seckill_id;
    }

    public void setSeckill_id(int seckill_id) {
        this.seckill_id = seckill_id;
    }

    public int getBargain_id() {
        return bargain_id;
    }

    public void setBargain_id(int bargain_id) {
        this.bargain_id = bargain_id;
    }

    public int getCombination_id() {
        return combination_id;
    }

    public void setCombination_id(int combination_id) {
        this.combination_id = combination_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getPay_postage() {
        return pay_postage;
    }

    public void setPay_postage(String pay_postage) {
        this.pay_postage = pay_postage;
    }

    public String getTotal_postage() {
        return total_postage;
    }

    public void setTotal_postage(String total_postage) {
        this.total_postage = total_postage;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getDeduction_price() {
        return deduction_price;
    }

    public void setDeduction_price(String deduction_price) {
        this.deduction_price = deduction_price;
    }

    public int getPink_id() {
        return pink_id;
    }

    public void setPink_id(int pink_id) {
        this.pink_id = pink_id;
    }

    public Object getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(Object delivery_type) {
        this.delivery_type = delivery_type;
    }

    public OrderStatus get_status() {
        return _status;
    }

    public void set_status(OrderStatus _status) {
        this._status = _status;
    }

    public String get_pay_time() {
        return _pay_time;
    }

    public void set_pay_time(String _pay_time) {
        this._pay_time = _pay_time;
    }

    public String get_add_time() {
        return _add_time;
    }

    public void set_add_time(String _add_time) {
        this._add_time = _add_time;
    }

    public String getStatus_pic() {
        return status_pic;
    }

    public void setStatus_pic(String status_pic) {
        this.status_pic = status_pic;
    }

    public List<ShoppingCartInfo> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<ShoppingCartInfo> cartInfo) {
        this.cartInfo = cartInfo;
    }

}
