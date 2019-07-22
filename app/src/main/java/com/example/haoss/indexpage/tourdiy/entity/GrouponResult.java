package com.example.haoss.indexpage.tourdiy.entity;

import java.util.List;

public class GrouponResult {

//    "pinkBool": false,
//            *     "is_ok": 0,
//            *     "userBool": 1,
//            "count": 2,
//            *     "current_pink_order": "wx2019071711512010002"

    private int pinkBool;
    private int is_ok;
    private int userBool;
    private int count;
    private String current_pink_order;
    private GrouponGoodInfo store_combination;
    private List<UserBean> pinkAll;

    public static class UserBean {
        private String avatar;
        private int k_id;

        public UserBean(int k_id, String avatar) {
            this.avatar = avatar;
            this.k_id = k_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getK_id() {
            return k_id;
        }

        public void setK_id(int k_id) {
            this.k_id = k_id;
        }
    }

    public int isPinkBool() {
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

    public GrouponGoodInfo getStore_combination() {
        return store_combination;
    }

    public void setStore_combination(GrouponGoodInfo store_combination) {
        this.store_combination = store_combination;
    }

    public List<UserBean> getPinkAll() {
        return pinkAll;
    }

    public void setPinkAll(List<UserBean> pinkAll) {
        this.pinkAll = pinkAll;
    }
}
