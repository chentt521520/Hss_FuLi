package com.example.applibrary.resp;

import java.util.List;

public class RespCoupon {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":69,"cid":35,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"5.00","use_min_price":"99.00","category_name":"牛奶饮料","category_id":609,"title":"牛奶饮料满99元减5元","is_use":true},{"id":68,"cid":31,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"5.00","use_min_price":"99.00","category_name":"家用洗涤","category_id":38,"title":"家用洗涤满99元减5元","is_use":true},{"id":67,"cid":29,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"5.00","use_min_price":"99.00","category_name":"休闲零食","category_id":36,"title":"休闲零食满99元减5元","is_use":true},{"id":66,"cid":28,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"5.00","use_min_price":"99.00","category_name":"孕妇奶粉","category_id":57,"title":"孕妇奶粉满99减5元","is_use":false},{"id":64,"cid":27,"start_time":"2019/07/01","end_time":"2019/08/24","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"20.00","use_min_price":"300.00","category_name":"家电厨卫","category_id":42,"title":"家电厨卫满300减20元","is_use":false},{"id":63,"cid":26,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"5.00","use_min_price":"99.00","category_name":"粮油杂货","category_id":37,"title":"粮油杂货满99元减5元","is_use":false},{"id":61,"cid":25,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"10.00","use_min_price":"300.00","category_name":"品质彩妆","category_id":71,"title":"品质彩妆满300减10元","is_use":false},{"id":60,"cid":24,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"20.00","use_min_price":"300.00","category_name":"婴儿奶粉","category_id":58,"title":"婴儿奶粉满300减20元","is_use":false},{"id":59,"cid":23,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"10.00","use_min_price":"200.00","category_name":"尿不湿","category_id":613,"title":"纸尿裤满200建10元","is_use":true},{"id":56,"cid":32,"start_time":"2019/07/01","end_time":"2019/08/31","total_count":0,"remain_count":0,"is_permanent":1,"status":1,"is_del":0,"add_time":"2019/07/22","coupon_price":"5.00","use_min_price":"99.00","category_name":"轻奢护肤","category_id":72,"title":"轻奢护肤满99元减5元","is_use":true}]
     * count : 0
     */

    private int code;
    private String msg;
    private int count;
    private List<Coupon> data;

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

    public List<Coupon> getData() {
        return data;
    }

    public void setData(List<Coupon> data) {
        this.data = data;
    }

    public static class Coupon {
        /**
         * id : 69
         * cid : 35
         * start_time : 2019/07/01
         * end_time : 2019/08/31
         * total_count : 0
         * remain_count : 0
         * is_permanent : 1
         * status : 1
         * is_del : 0
         * add_time : 2019/07/22
         * coupon_price : 5.00
         * use_min_price : 99.00
         * category_name : 牛奶饮料
         * category_id : 609
         * title : 牛奶饮料满99元减5元
         * is_use : true
         */

        private int id;
        private int cid;
        private String start_time;
        private String end_time;
        private int total_count;
        private int remain_count;
        private int is_permanent;
        private int status;
        private int is_del;
        private String add_time;
        private String coupon_price;
        private String use_min_price;
        private String category_name;
        private int category_id;
        private String title;
        private boolean is_use;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public int getRemain_count() {
            return remain_count;
        }

        public void setRemain_count(int remain_count) {
            this.remain_count = remain_count;
        }

        public int getIs_permanent() {
            return is_permanent;
        }

        public void setIs_permanent(int is_permanent) {
            this.is_permanent = is_permanent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_del() {
            return is_del;
        }

        public void setIs_del(int is_del) {
            this.is_del = is_del;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getCoupon_price() {
            return coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getUse_min_price() {
            return use_min_price;
        }

        public void setUse_min_price(String use_min_price) {
            this.use_min_price = use_min_price;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isIs_use() {
            return is_use;
        }

        public void setIs_use(boolean is_use) {
            this.is_use = is_use;
        }
    }
}
