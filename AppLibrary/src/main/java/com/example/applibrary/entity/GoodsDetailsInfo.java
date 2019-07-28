package com.example.applibrary.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

//商品详情信息
public class GoodsDetailsInfo implements Serializable {

    /**
     * storeInfo : {"add_time":1560419253,"browse":0,"cate_id":"379","store_type":0,"code_path":"","cost":"0.00","ficti":343,"give_integral":"0.00","id":30,"image":"http://qiniu.haoshusi.com/images/6c963864afcf3453b53480437ef8909a.png","is_bargain":null,"is_benefit":1,"is_best":0,"is_del":0,"is_hot":0,"is_new":0,"is_postage":0,"is_seckill":0,"is_show":1,"keyword":"","mer_id":0,"mer_use":0,"ot_price":"201.76","postage":"10.00","price":"155.20","sales":1,"slider_image":["http://qiniu.haoshusi.com/images/6c963864afcf3453b53480437ef8909a.png","http://qiniu.haoshusi.com/images/9e9a73e1b648f31bcd2e211d7b06119c.png","http://qiniu.haoshusi.com/images/b9e0d7a34d1e3703bcc96d85ed525af4.png"],"sort":0,"stock":100,"store_info":"","store_name":"oba欧芭洗发水二代A5去屑控油止痒洗头皮修护欧巴洗头膏男女 正品740ml","unit_name":"件","vip_price":"153.65","fsales":"344","userCollect":false}
     * productAttr : [{"product_id":30,"attr_name":"规格","attr_values":["740mm"],"attr_value":[{"attr":"740mm","check":false}]}]
     * productValue : {"740mm":{"product_id":30,"suk":"740mm","stock":100,"sales":0,"price":"155.20","image":"http://qiniu.haoshusi.com/images/6c963864afcf3453b53480437ef8909a.png","unique":"ecdec0bc","cost":"0.00"}}
     * priceName : 155.20
     * reply : null
     * replyCount : 0
     * replyChance : 0
     * mer_id : 0
     * details_url : http://api.haoshusi.com/product.html?id=30
     * notFreight : 99
     */

    private StoreInfo storeInfo;
    //    private AttrInfo productValue;\
    //无法解析
    private Map<String, Object> productValue;
    private List<ProductAttr> productAttr;
    private String priceName;
    private ReplyInfo reply;
    private int replyCount;
    private String replyChance;
    private int mer_id;
    private String details_url;
    private int notFreight;

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

//    public AttrInfo getProductValue() {
//        return productValue;
//    }
//
//    public void setProductValue(AttrInfo productValue) {
//        this.productValue = productValue;
//    }


    public Map<String, Object> getProductValue() {
        return productValue;
    }

    public void setProductValue(Map<String, Object> productValue) {
        this.productValue = productValue;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
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

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public String getDetails_url() {
        return details_url;
    }

    public void setDetails_url(String details_url) {
        this.details_url = details_url;
    }

    public int getNotFreight() {
        return notFreight;
    }

    public void setNotFreight(int notFreight) {
        this.notFreight = notFreight;
    }

    public List<ProductAttr> getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(List<ProductAttr> productAttr) {
        this.productAttr = productAttr;
    }
}
