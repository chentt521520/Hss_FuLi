package com.example.applibrary.resp;

import com.example.applibrary.entity.GrouponInfo;
import com.example.applibrary.entity.GrouponUser;

import java.util.List;

public class RespGrouponResult {


    /**
     * code : 200
     * msg : ok
     * data : {"pinkBool":0,"is_ok":0,"userBool":1,"store_combination":{"id":31,"product_id":3189,"mer_id":0,"image":"http://qiniu.haoshusi.com/images/b3ad7201907221458433848.png","title":"1","people":3,"info":"而我却二","price":"0.01","sort":0,"sales":51,"stock":9951,"add_time":"1563870476","is_host":1,"is_show":1,"is_del":0,"is_postage":1,"postage":"0.00","start_time":1561910400,"stop_time":1567180800,"cost":0,"browse":0,"unit_name":"","product_price":"0.10","combination_id":31},"pinkAll":[{"avatar":"http://qiniu.haoshusi.com/Android/15635087997855GT2YCEX8T.jpg","k_id":0}],"count":2,"current_pink_order":"wx2019072515132210015"}
     * count : 0
     */

    private int code;
    private String msg;
    private GrouponResult data;
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

    public GrouponResult getData() {
        return data;
    }

    public void setData(GrouponResult data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class GrouponResult {
        /**
         * pinkBool : 0
         * is_ok : 0
         * userBool : 1
         * store_combination : {"id":31,"product_id":3189,"mer_id":0,"image":"http://qiniu.haoshusi.com/images/b3ad7201907221458433848.png","title":"1","people":3,"info":"而我却二","price":"0.01","sort":0,"sales":51,"stock":9951,"add_time":"1563870476","is_host":1,"is_show":1,"is_del":0,"is_postage":1,"postage":"0.00","start_time":1561910400,"stop_time":1567180800,"cost":0,"browse":0,"unit_name":"","product_price":"0.10","combination_id":31}
         * pinkAll : [{"avatar":"http://qiniu.haoshusi.com/Android/15635087997855GT2YCEX8T.jpg","k_id":0}]
         * count : 2
         * current_pink_order : wx2019072515132210015
         */

        private int pinkBool;
        private int is_ok;
        private int userBool;
        private GrouponInfo store_combination;
        private int count;
        private String current_pink_order;
        private List<GrouponUser> pinkAll;

        public int getPinkBool() {
            return pinkBool;
        }

        public void setPinkBool(int pinkBool) {
            this.pinkBool = pinkBool;
        }

        public int getIs_ok() {
            return is_ok;
        }

        public void setIs_ok(int is_ok) {
            this.is_ok = is_ok;
        }

        public int getUserBool() {
            return userBool;
        }

        public void setUserBool(int userBool) {
            this.userBool = userBool;
        }

        public GrouponInfo getStore_combination() {
            return store_combination;
        }

        public void setStore_combination(GrouponInfo store_combination) {
            this.store_combination = store_combination;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getCurrent_pink_order() {
            return current_pink_order;
        }

        public void setCurrent_pink_order(String current_pink_order) {
            this.current_pink_order = current_pink_order;
        }

        public List<GrouponUser> getPinkAll() {
            return pinkAll;
        }

        public void setPinkAll(List<GrouponUser> pinkAll) {
            this.pinkAll = pinkAll;
        }
    }
}
