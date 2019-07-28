package com.example.applibrary.entity;


import java.io.Serializable;
import java.util.List;

//订单详情信息
public class OrderDetailsInfo implements Serializable {


    /**
     * id : 12
     * order_id : wx2019071716463410012
     * uid : 37
     * admin_id : 0
     * real_name : haha
     * user_phone : 15667074017
     * user_address : 北京市 北京市 东城区 hahahahahahahaha hahahaha
     * cart_id : [23,22]
     * total_num : 2
     * total_price : 109.12
     * total_postage : 0.00
     * pay_price : 109.12
     * pay_postage : 0.00
     * deduction_price : 0.00
     * coupon_id : 0
     * coupon_price : 0.00
     * paid : 1
     * pay_time : 1563353194
     * pay_type : yue
     * add_time : 1563353194
     * status : 0
     * refund_status : 0
     * refund_reason_wap_img : null
     * refund_reason_wap_explain : null
     * refund_reason_time : null
     * refund_reason_wap : null
     * refund_reason : null
     * refund_price : 0.00
     * delivery_name : null
     * delivery_type : null
     * delivery_id : null
     * gain_integral : 0.00
     * use_integral : 0.00
     * back_integral : null
     * mark :
     * is_del : 0
     * unique : 1ec6c98cb61f6082a8cde7aabb3c4c44
     * remark : null
     * mer_id : 0
     * is_mer_check : 0
     * combination_id : 0
     * pink_id : 0
     * cost : 0.00
     * seckill_id : 0
     * bargain_id : 0
     * is_channel : 1
     * is_remind : 1
     * add_time_y : 2019-07-17
     * add_time_h : 16:46:34
     * cartInfo : [{"id":22,"uid":37,"type":"product","product_id":1891,"product_attr_unique":"971476b2","cart_num":1,"add_time":1563353163,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":1891,"image":"http://py.haoshusi.com/python/b8708c1ffe41fcfa1ee846f79734a6235646.jpg","slider_image":["http://py.haoshusi.com/python/b8708c1ffe41fcfa1ee846f79734a6235646.jpg"],"price":"57.12","ot_price":"74.26","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"607","sales":0,"stock":100,"store_name":"【香港直邮】RE:CIPE莱斯璧防紫外线水晶防晒喷雾 150毫升【新旧版本随机】","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1891,"suk":"150ml","stock":100,"sales":0,"price":"57.12","image":"http://py.haoshusi.com/python/b8708c1ffe41fcfa1ee846f79734a6235646.jpg","unique":"971476b2","cost":"0.00"}},"truePrice":57.12,"vip_truePrice":0,"trueStock":100,"costPrice":"0.00","unique":"05a70454516ecd9194c293b0e415777f","is_reply":0},{"id":23,"uid":37,"type":"product","product_id":1516,"product_attr_unique":"4cebd7cb","cart_num":1,"add_time":1563353169,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":1516,"image":"http://py.haoshusi.com/python/0387e0b8cde2471cc7fc30d88ab4167b4749.jpg","slider_image":["http://py.haoshusi.com/python/0387e0b8cde2471cc7fc30d88ab4167b4749.jpg","http://py.haoshusi.com/python/89e0275be823770c354124b9c720ed6b2903.jpg","http://py.haoshusi.com/python/ebd87fd8151e131b759e7d5272799a5f9774.jpg"],"price":"52.00","ot_price":"67.60","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"607","sales":0,"stock":202,"store_name":"JMsolution 海洋珍珠抖音同款防晒喷雾 180ml（新款带防伪）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1516,"suk":"2021年4月","stock":190,"sales":0,"price":"52.00","image":"http://py.haoshusi.com/python/0387e0b8cde2471cc7fc30d88ab4167b4749.jpg","unique":"4cebd7cb","cost":"0.00"}},"truePrice":52,"vip_truePrice":0,"trueStock":190,"costPrice":"0.00","unique":"69dafe8b58066478aea48f3d0f384820","is_reply":0}]
     * _status : {"_type":1,"_title":"未发货","_msg":"商家未发货,请耐心等待","_class":"state-nfh","_payType":"余额支付","_deliveryType":"其他方式"}
     * _pay_time : 2019-07-17 16:46:34
     * _add_time : 1563355937
     * status_pic : http://datong.crmeb.net/public/uploads/attach/2019/03/28/5c9ccca12638a.gif
     */

    private int id;
    private String order_id;
    private int uid;
    private int admin_id;
    private String real_name;
    private String user_phone;
    private String user_address;
    private int total_num;
    private String total_price;
    private String total_postage;
    private String pay_price;
    private String pay_postage;
    private String deduction_price;
    private int coupon_id;
    private String coupon_price;
    private int paid;
    private int pay_time;
    private String pay_type;
    private int add_time;
    private int status;
    private int refund_status;
    private Object refund_reason_wap_img;
    private Object refund_reason_wap_explain;
    private Object refund_reason_time;
    private Object refund_reason_wap;
    private Object refund_reason;
    private String refund_price;
    private Object delivery_name;
    private Object delivery_type;
    private Object delivery_id;
    private String gain_integral;
    private String use_integral;
    private Object back_integral;
    private String mark;
    private int is_del;
    private String unique;
    private Object remark;
    private int mer_id;
    private int is_mer_check;
    private int combination_id;
    private int pink_id;
    private String cost;
    private int seckill_id;
    private int bargain_id;
    private int is_channel;
    private int is_remind;
    private String add_time_y;
    private String add_time_h;
    private OrderStatus _status;
    private String _pay_time;
    private long _add_time;
    private String status_pic;
    private List<Integer> cart_id;
    private List<ShoppingCartInfo> cartInfo;

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
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

    public String getTotal_postage() {
        return total_postage;
    }

    public void setTotal_postage(String total_postage) {
        this.total_postage = total_postage;
    }

    public String getPay_price() {
        return pay_price;
    }

    public void setPay_price(String pay_price) {
        this.pay_price = pay_price;
    }

    public String getPay_postage() {
        return pay_postage;
    }

    public void setPay_postage(String pay_postage) {
        this.pay_postage = pay_postage;
    }

    public String getDeduction_price() {
        return deduction_price;
    }

    public void setDeduction_price(String deduction_price) {
        this.deduction_price = deduction_price;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getPay_time() {
        return pay_time;
    }

    public void setPay_time(int pay_time) {
        this.pay_time = pay_time;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
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

    public Object getRefund_reason_wap_img() {
        return refund_reason_wap_img;
    }

    public void setRefund_reason_wap_img(Object refund_reason_wap_img) {
        this.refund_reason_wap_img = refund_reason_wap_img;
    }

    public Object getRefund_reason_wap_explain() {
        return refund_reason_wap_explain;
    }

    public void setRefund_reason_wap_explain(Object refund_reason_wap_explain) {
        this.refund_reason_wap_explain = refund_reason_wap_explain;
    }

    public Object getRefund_reason_time() {
        return refund_reason_time;
    }

    public void setRefund_reason_time(Object refund_reason_time) {
        this.refund_reason_time = refund_reason_time;
    }

    public Object getRefund_reason_wap() {
        return refund_reason_wap;
    }

    public void setRefund_reason_wap(Object refund_reason_wap) {
        this.refund_reason_wap = refund_reason_wap;
    }

    public Object getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(Object refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getRefund_price() {
        return refund_price;
    }

    public void setRefund_price(String refund_price) {
        this.refund_price = refund_price;
    }

    public Object getDelivery_name() {
        return delivery_name;
    }

    public void setDelivery_name(Object delivery_name) {
        this.delivery_name = delivery_name;
    }

    public Object getDelivery_type() {
        return delivery_type;
    }

    public void setDelivery_type(Object delivery_type) {
        this.delivery_type = delivery_type;
    }

    public Object getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(Object delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getGain_integral() {
        return gain_integral;
    }

    public void setGain_integral(String gain_integral) {
        this.gain_integral = gain_integral;
    }

    public String getUse_integral() {
        return use_integral;
    }

    public void setUse_integral(String use_integral) {
        this.use_integral = use_integral;
    }

    public Object getBack_integral() {
        return back_integral;
    }

    public void setBack_integral(Object back_integral) {
        this.back_integral = back_integral;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public int getIs_mer_check() {
        return is_mer_check;
    }

    public void setIs_mer_check(int is_mer_check) {
        this.is_mer_check = is_mer_check;
    }

    public int getCombination_id() {
        return combination_id;
    }

    public void setCombination_id(int combination_id) {
        this.combination_id = combination_id;
    }

    public int getPink_id() {
        return pink_id;
    }

    public void setPink_id(int pink_id) {
        this.pink_id = pink_id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public int getIs_channel() {
        return is_channel;
    }

    public void setIs_channel(int is_channel) {
        this.is_channel = is_channel;
    }

    public int getIs_remind() {
        return is_remind;
    }

    public void setIs_remind(int is_remind) {
        this.is_remind = is_remind;
    }

    public String getAdd_time_y() {
        return add_time_y;
    }

    public void setAdd_time_y(String add_time_y) {
        this.add_time_y = add_time_y;
    }

    public String getAdd_time_h() {
        return add_time_h;
    }

    public void setAdd_time_h(String add_time_h) {
        this.add_time_h = add_time_h;
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

    public long get_add_time() {
        return _add_time;
    }

    public void set_add_time(long _add_time) {
        this._add_time = _add_time;
    }

    public String getStatus_pic() {
        return status_pic;
    }

    public void setStatus_pic(String status_pic) {
        this.status_pic = status_pic;
    }

    public List<Integer> getCart_id() {
        return cart_id;
    }

    public void setCart_id(List<Integer> cart_id) {
        this.cart_id = cart_id;
    }

    public List<ShoppingCartInfo> getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(List<ShoppingCartInfo> cartInfo) {
        this.cartInfo = cartInfo;
    }
}
