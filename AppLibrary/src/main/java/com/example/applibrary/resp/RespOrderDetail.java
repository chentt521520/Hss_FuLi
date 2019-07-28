package com.example.applibrary.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.applibrary.entity.OrderStatus;
import com.example.applibrary.entity.ShoppingCartInfo;

import java.util.List;

public class RespOrderDetail {

    /**
     * code : 200
     * msg : ok
     * data : {"id":116,"order_id":"wx2019072517363610022","uid":37,"admin_id":9,"real_name":"陈婷婷","user_phone":"15667074017","user_address":"四川省 成都市 青羊区 府南街道","cart_id":[286],"total_num":1,"total_price":"306.00","total_postage":"0.00","pay_price":"306.00","pay_postage":"0.00","deduction_price":"0.00","coupon_id":0,"coupon_price":"0.00","paid":0,"pay_time":null,"pay_type":"weixin","add_time":1564047396,"status":0,"refund_status":0,"refund_reason_wap_img":null,"refund_reason_wap_explain":null,"refund_reason_time":null,"refund_reason_wap":null,"refund_reason":null,"refund_price":"0.00","delivery_name":null,"delivery_type":null,"delivery_id":null,"gain_integral":"0.00","use_integral":"0.00","back_integral":null,"mark":"","is_del":0,"unique":"601b5efb91a7af7135f20e43e10d9f43","remark":null,"mer_id":0,"is_mer_check":0,"combination_id":0,"pink_id":0,"cost":"0.00","seckill_id":0,"bargain_id":0,"is_channel":1,"is_remind":0,"add_time_y":"2019-07-25","add_time_h":"17:36:36","cartInfo":[{"id":286,"uid":37,"type":"product","product_id":1836,"product_attr_unique":"01b67059","cart_num":1,"add_time":1564047388,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1836,"image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","slider_image":["http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","http://py.haoshusi.com/python/3c76e483518218037743f95e9ea3e54b2880.jpg","http://qiniu.haoshusi.com/images/aa4fd201907251640369227.png"],"price":"306.00","ot_price":"397.80","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"144","sales":0,"stock":50,"store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1836,"suk":"16#","stock":50,"sales":0,"price":"306.00","image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","unique":"01b67059","cost":"0.00"}},"truePrice":306,"vip_truePrice":0,"trueStock":50,"costPrice":"0.00","unique":"ecebb82657a4cacb4e5a66a6b5cf094b","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付","_deliveryType":"其他方式"},"_pay_time":"2019-07-25 17:36:36","_add_time":1564237282,"status_pic":"http://datong.crmeb.net/public/uploads/attach/2019/03/28/5c9ccca151e99.gif"}
     * count : 0
     */

    private int code;
    private String msg;
    private OrderDetail data;
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

    public OrderDetail getData() {
        return data;
    }

    public void setData(OrderDetail data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class OrderDetail {
        /**
         * id : 116
         * order_id : wx2019072517363610022
         * uid : 37
         * admin_id : 9
         * real_name : 陈婷婷
         * user_phone : 15667074017
         * user_address : 四川省 成都市 青羊区 府南街道
         * cart_id : [286]
         * total_num : 1
         * total_price : 306.00
         * total_postage : 0.00
         * pay_price : 306.00
         * pay_postage : 0.00
         * deduction_price : 0.00
         * coupon_id : 0
         * coupon_price : 0.00
         * paid : 0
         * pay_time : null
         * pay_type : weixin
         * add_time : 1564047396
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
         * unique : 601b5efb91a7af7135f20e43e10d9f43
         * remark : null
         * mer_id : 0
         * is_mer_check : 0
         * combination_id : 0
         * pink_id : 0
         * cost : 0.00
         * seckill_id : 0
         * bargain_id : 0
         * is_channel : 1
         * is_remind : 0
         * add_time_y : 2019-07-25
         * add_time_h : 17:36:36
         * cartInfo : [{"id":286,"uid":37,"type":"product","product_id":1836,"product_attr_unique":"01b67059","cart_num":1,"add_time":1564047388,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1836,"image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","slider_image":["http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","http://py.haoshusi.com/python/3c76e483518218037743f95e9ea3e54b2880.jpg","http://qiniu.haoshusi.com/images/aa4fd201907251640369227.png"],"price":"306.00","ot_price":"397.80","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"144","sales":0,"stock":50,"store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1836,"suk":"16#","stock":50,"sales":0,"price":"306.00","image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","unique":"01b67059","cost":"0.00"}},"truePrice":306,"vip_truePrice":0,"trueStock":50,"costPrice":"0.00","unique":"ecebb82657a4cacb4e5a66a6b5cf094b","is_reply":0}]
         * _status : {"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付","_deliveryType":"其他方式"}
         * _pay_time : 2019-07-25 17:36:36
         * _add_time : 1564237282
         * status_pic : http://datong.crmeb.net/public/uploads/attach/2019/03/28/5c9ccca151e99.gif
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
        //        private Object pay_time;
        private String pay_type;
        //        private int add_time;
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
        @JSONField(name = "_status")
        private OrderStatus statu;
        @JSONField(name = "_add_time")
        private String add_time;
        @JSONField(name = "_pay_time")
        private String pay_time;

        //        private OrderStatus _status;
//        private String _pay_time;
//        private int _add_time;
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

//        public Object getPay_time() {
//            return pay_time;
//        }
//
//        public void setPay_time(Object pay_time) {
//            this.pay_time = pay_time;
//        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

//        public int getAdd_time() {
//            return add_time;
//        }
//
//        public void setAdd_time(int add_time) {
//            this.add_time = add_time;
//        }


        public String getPay_time() {

            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
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

        public OrderStatus getStatu() {
            return statu;
        }

        public void setStatu(OrderStatus statu) {
            this.statu = statu;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
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
}
