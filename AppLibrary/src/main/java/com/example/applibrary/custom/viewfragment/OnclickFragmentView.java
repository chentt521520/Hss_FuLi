package com.example.applibrary.custom.viewfragment;

//轮播点击监听
public interface OnclickFragmentView {

    /**
     * @param id  //id
     * @param url //跳转链接
     */
    void onItemclick(int id, String url);
}
