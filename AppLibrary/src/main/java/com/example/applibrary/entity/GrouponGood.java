package com.example.applibrary.entity;


import java.util.List;

public class GrouponGood {

    /**
     * pink : []
     * pindAll : []
     * storeInfo : {"id":10,"product_id":2676,"mer_id":0,"image":"http://qiniu.haoshusi.com/images/66cdd201907031153011953.jpg","images":["http://qiniu.haoshusi.com/images/66cdd201907031153011953.jpg","http://qiniu.haoshusi.com/images/f1acc20190703115304335.jpg","http://qiniu.haoshusi.com/images/741cd201907031153032285.jpg"],"title":"泉立方 洗衣片小","attr":null,"people":3,"info":"泉立方洗衣片","price":"69.00","sort":0,"sales":1233,"stock":1000,"add_time":"1562126501","is_host":1,"is_show":1,"is_del":0,"combination":1,"mer_use":null,"is_postage":0,"postage":"0.00","description":"","start_time":1562083200,"stop_time":1567180800,"cost":0,"browse":0,"unit_name":"","product_price":"1.00","userCollect":false}
     * pink_ok_list : ["-拼团成功","999拼团成功","用户拼团成功"]
     * pink_ok_sum : 3
     * reply : null
     * replyCount : 0
     * replyChance : 0
     * "details_url": "http://api.haoshusi.com/product.html?id=3187"
     */

    private GrouponGoodInfo storeInfo;
    private int pink_ok_sum;
    private ReplyInfo reply;
    private int replyCount;
    private String replyChance;
    private List<GrouponUser> pink;
    //    private List<?> pindAll;
    private List<String> pink_ok_list;
    private String details_url;
    private AttrInfo StoreProductValue;
    private List<ProductAttr> StoreProductAttr;

    public GrouponGoodInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(GrouponGoodInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public int getPink_ok_sum() {
        return pink_ok_sum;
    }

    public void setPink_ok_sum(int pink_ok_sum) {
        this.pink_ok_sum = pink_ok_sum;
    }

    public ReplyInfo getReply() {
        return reply;
    }

    public void setReply(ReplyInfo reply) {
        this.reply = reply;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getReplyChance() {
        return replyChance;
    }

    public void setReplyChance(String replyChance) {
        this.replyChance = replyChance;
    }

    public List<GrouponUser> getPink() {
        return pink;
    }

    public void setPink(List<GrouponUser> pink) {
        this.pink = pink;
    }

    public List<String> getPink_ok_list() {
        return pink_ok_list;
    }

    public void setPink_ok_list(List<String> pink_ok_list) {
        this.pink_ok_list = pink_ok_list;
    }

    public String getDetails_url() {
        return details_url;
    }

    public void setDetails_url(String details_url) {
        this.details_url = details_url;
    }

    public AttrInfo getStoreProductValue() {
        return StoreProductValue;
    }

    public void setStoreProductValue(AttrInfo storeProductValue) {
        StoreProductValue = storeProductValue;
    }

    public List<ProductAttr> getStoreProductAttr() {
        return StoreProductAttr;
    }

    public void setStoreProductAttr(List<ProductAttr> storeProductAttr) {
        StoreProductAttr = storeProductAttr;
    }
}
