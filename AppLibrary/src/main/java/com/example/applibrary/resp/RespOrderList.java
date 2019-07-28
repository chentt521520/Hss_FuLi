package com.example.applibrary.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.applibrary.entity.OrderStatus;
import com.example.applibrary.entity.ShoppingCartInfo;

import java.util.List;

public class RespOrderList {

    /**
     * code : 200
     * msg : ok
     * data : [{"add_time":1564047396,"seckill_id":0,"bargain_id":0,"combination_id":0,"id":116,"order_id":"wx2019072517363610022","pay_price":"306.00","total_num":1,"total_price":"306.00","pay_postage":"0.00","total_postage":"0.00","paid":0,"status":0,"refund_status":0,"pay_type":"weixin","coupon_price":"0.00","deduction_price":"0.00","pink_id":0,"delivery_type":null,"cartInfo":[{"id":286,"uid":37,"type":"product","product_id":1836,"product_attr_unique":"01b67059","cart_num":1,"add_time":1564047388,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1836,"image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","slider_image":["http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","http://py.haoshusi.com/python/3c76e483518218037743f95e9ea3e54b2880.jpg","http://qiniu.haoshusi.com/images/aa4fd201907251640369227.png"],"price":"306.00","ot_price":"397.80","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"144","sales":0,"stock":50,"store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1836,"suk":"16#","stock":50,"sales":0,"price":"306.00","image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","unique":"01b67059","cost":"0.00"}},"truePrice":306,"vip_truePrice":0,"trueStock":50,"costPrice":"0.00","unique":"ecebb82657a4cacb4e5a66a6b5cf094b","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付"},"_pay_time":"2019-07-25 17:36:36","_add_time":"2019-07-25 17:36:36","status_pic":""},{"add_time":1564040688,"seckill_id":0,"bargain_id":0,"combination_id":0,"id":114,"order_id":"wx2019072515444810020","pay_price":"480.00","total_num":1,"total_price":"480.00","pay_postage":"0.00","total_postage":"0.00","paid":0,"status":0,"refund_status":0,"pay_type":"yue","coupon_price":"0.00","deduction_price":"0.00","pink_id":0,"delivery_type":null,"cartInfo":[{"id":278,"uid":37,"type":"product","product_id":1605,"product_attr_unique":"09cbed3a","cart_num":1,"add_time":1564040682,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1605,"image":"http://py.haoshusi.com/python/3ba23b4440eb976dce44dee8283ac8d35748.jpg","slider_image":["http://py.haoshusi.com/python/3ba23b4440eb976dce44dee8283ac8d35748.jpg"],"price":"480.00","ot_price":"624.00","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"130","sales":0,"stock":5,"store_name":"【香港直邮】IPSA/茵芙莎-自律循环美肌液R3-175ml","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1605,"suk":"175ml","stock":5,"sales":0,"price":"480.00","image":"http://py.haoshusi.com/python/3ba23b4440eb976dce44dee8283ac8d35748.jpg","unique":"09cbed3a","cost":"0.00"}},"truePrice":480,"vip_truePrice":0,"trueStock":5,"costPrice":"0.00","unique":"e5a2cb999634bb513a947e0211a6443a","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"余额支付"},"_pay_time":"2019-07-25 15:44:48","_add_time":"2019-07-25 15:44:48","status_pic":""},{"add_time":1564040545,"seckill_id":0,"bargain_id":0,"combination_id":0,"id":113,"order_id":"wx2019072515422510019","pay_price":"480.00","total_num":1,"total_price":"480.00","pay_postage":"0.00","total_postage":"0.00","paid":0,"status":0,"refund_status":0,"pay_type":"yue","coupon_price":"0.00","deduction_price":"0.00","pink_id":0,"delivery_type":null,"cartInfo":[{"id":276,"uid":37,"type":"product","product_id":1605,"product_attr_unique":"09cbed3a","cart_num":1,"add_time":1564040538,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1605,"image":"http://py.haoshusi.com/python/3ba23b4440eb976dce44dee8283ac8d35748.jpg","slider_image":["http://py.haoshusi.com/python/3ba23b4440eb976dce44dee8283ac8d35748.jpg"],"price":"480.00","ot_price":"624.00","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"130","sales":0,"stock":5,"store_name":"【香港直邮】IPSA/茵芙莎-自律循环美肌液R3-175ml","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1605,"suk":"175ml","stock":5,"sales":0,"price":"480.00","image":"http://py.haoshusi.com/python/3ba23b4440eb976dce44dee8283ac8d35748.jpg","unique":"09cbed3a","cost":"0.00"}},"truePrice":480,"vip_truePrice":0,"trueStock":5,"costPrice":"0.00","unique":"9106f3163b652286afecc37f6f89f063","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"余额支付"},"_pay_time":"2019-07-25 15:42:25","_add_time":"2019-07-25 15:42:25","status_pic":""},{"add_time":1563961397,"seckill_id":0,"bargain_id":0,"combination_id":0,"id":92,"order_id":"wx2019072417431710027","pay_price":"109.99","total_num":1,"total_price":"109.99","pay_postage":"0.00","total_postage":"0.00","paid":0,"status":0,"refund_status":0,"pay_type":"ali","coupon_price":"0.00","deduction_price":"0.00","pink_id":0,"delivery_type":null,"cartInfo":[{"id":252,"uid":37,"type":"product","product_id":1709,"product_attr_unique":"3b0ecf20","cart_num":1,"add_time":1563961394,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1709,"image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","slider_image":["http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg"],"price":"109.99","ot_price":"142.99","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"131","sales":4,"stock":30,"store_name":"【香港直邮】美国科颜氏高保湿小样护肤3件套","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1709,"suk":"1套","stock":26,"sales":4,"price":"109.99","image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","unique":"3b0ecf20","cost":"0.00"}},"truePrice":109.99,"vip_truePrice":0,"trueStock":26,"costPrice":"0.00","unique":"5bb5f8d030f5e6e4b6d137c6990f0772","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"支付宝支付"},"_pay_time":"2019-07-24 17:43:17","_add_time":"2019-07-24 17:43:17","status_pic":""},{"add_time":1563961331,"seckill_id":0,"bargain_id":0,"combination_id":0,"id":91,"order_id":"wx2019072417421110026","pay_price":"109.99","total_num":1,"total_price":"109.99","pay_postage":"0.00","total_postage":"0.00","paid":0,"status":0,"refund_status":0,"pay_type":"weixin","coupon_price":"0.00","deduction_price":"0.00","pink_id":0,"delivery_type":null,"cartInfo":[{"id":249,"uid":37,"type":"product","product_id":1709,"product_attr_unique":"3b0ecf20","cart_num":1,"add_time":1563961324,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1709,"image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","slider_image":["http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg"],"price":"109.99","ot_price":"142.99","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"131","sales":4,"stock":30,"store_name":"【香港直邮】美国科颜氏高保湿小样护肤3件套","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1709,"suk":"1套","stock":26,"sales":4,"price":"109.99","image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","unique":"3b0ecf20","cost":"0.00"}},"truePrice":109.99,"vip_truePrice":0,"trueStock":26,"costPrice":"0.00","unique":"4b23f8dc9eb4ed500a662e396908d39b","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付"},"_pay_time":"2019-07-24 17:42:11","_add_time":"2019-07-24 17:42:11","status_pic":""},{"add_time":1563870917,"seckill_id":0,"bargain_id":0,"combination_id":179,"id":46,"order_id":"wx2019072316351710006","pay_price":"0.01","total_num":1,"total_price":"0.01","pay_postage":"0.00","total_postage":"0.00","paid":0,"status":0,"refund_status":0,"pay_type":"weixin","coupon_price":"0.00","deduction_price":"0.00","pink_id":0,"delivery_type":null,"cartInfo":[{"id":179,"uid":37,"type":"product","product_id":31,"product_attr_unique":"","cart_num":1,"add_time":1563870914,"is_pay":0,"is_del":0,"is_new":1,"combination_id":31,"seckill_id":0,"bargain_id":0,"admin_id":8,"productInfo":{"id":31,"image":"http://qiniu.haoshusi.com/images/b3ad7201907221458433848.png","price":"0.01","postage":"0.00","sales":5,"stock":9997,"store_name":"1","is_show":1,"is_del":0,"is_postage":1,"cost":0},"truePrice":0.01,"vip_truePrice":0,"trueStock":9997,"costPrice":0,"unique":"dc4ff0eb47dc4499c160ae22d8413116","is_reply":0}],"_status":{"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付"},"_pay_time":"2019-07-23 16:35:17","_add_time":"2019-07-23 16:35:17","status_pic":""}]
     * count : 0
     */

    private int code;
    private String msg;
    private int count;
    private List<OrderList> data;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<OrderList> getData() {
        return data;
    }

    public void setData(List<OrderList> data) {
        this.data = data;
    }

    public static class OrderList {
        /**
         * add_time : 1564047396
         * seckill_id : 0
         * bargain_id : 0
         * combination_id : 0
         * id : 116
         * order_id : wx2019072517363610022
         * pay_price : 306.00
         * total_num : 1
         * total_price : 306.00
         * pay_postage : 0.00
         * total_postage : 0.00
         * paid : 0
         * status : 0
         * refund_status : 0
         * pay_type : weixin
         * coupon_price : 0.00
         * deduction_price : 0.00
         * pink_id : 0
         * delivery_type : null
         * cartInfo : [{"id":286,"uid":37,"type":"product","product_id":1836,"product_attr_unique":"01b67059","cart_num":1,"add_time":1564047388,"is_pay":0,"is_del":0,"is_new":1,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":9,"productInfo":{"id":1836,"image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","slider_image":["http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","http://py.haoshusi.com/python/3c76e483518218037743f95e9ea3e54b2880.jpg","http://qiniu.haoshusi.com/images/aa4fd201907251640369227.png"],"price":"306.00","ot_price":"397.80","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"144","sales":0,"stock":50,"store_name":"【新品推荐】【香港直邮】YSL/圣罗兰2018小金条哑光方管细管唇膏口红 16#浅豆沙粉色","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1836,"suk":"16#","stock":50,"sales":0,"price":"306.00","image":"http://py.haoshusi.com/python/e7952d0bd7ae42d92b2a78da92f097f23882.jpg","unique":"01b67059","cost":"0.00"}},"truePrice":306,"vip_truePrice":0,"trueStock":50,"costPrice":"0.00","unique":"ecebb82657a4cacb4e5a66a6b5cf094b","is_reply":0}]
         * _status : {"_type":0,"_title":"未支付","_msg":"立即支付订单吧","_class":"nobuy","_payType":"微信支付"}
         * _pay_time : 2019-07-25 17:36:36
         * _add_time : 2019-07-25 17:36:36
         * status_pic :
         */

//        private int add_time;
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
        @JSONField(name = "_status")
        private OrderStatus statu;
        @JSONField(name = "_add_time")
        private String add_time;
        @JSONField(name = "_pay_time")
        private String pay_time;
        private String status_pic;
        private List<ShoppingCartInfo> cartInfo;

//        public int getAdd_time() {
//            return add_time;
//        }
//
//        public void setAdd_time(int add_time) {
//            this.add_time = add_time;
//        }

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

        public OrderStatus getStatu() {
            return statu;
        }

        public void setStatu(OrderStatus statu) {
            this.statu = statu;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void set_pay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String _add_time) {
            this.add_time = add_time;
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
}
