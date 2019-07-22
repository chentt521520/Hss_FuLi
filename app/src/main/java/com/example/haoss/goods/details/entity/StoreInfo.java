package com.example.haoss.goods.details.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoreInfo {

    /**
     * add_time : 0
     * browse : 0
     * cate_id : 207  商品分类
     * store_type : 2
     * code_path :
     * cost : 0.00
     * ficti : 872
     * give_integral : 0.00
     * id : 746
     * image : http://py.haoshusi.com/python/e775eeefa81f466768cb67f06af83ab02201.jpg
     * is_bargain : null
     * is_benefit : 0
     * is_best : 0
     * is_del : 0
     * is_hot : 0
     * is_new : 0
     * is_postage : 0
     * is_seckill : 0
     * is_show : 1
     * keyword :
     * mer_id : 0
     * mer_use : 0
     * ot_price : 182.00
     * postage : 10.00
     * price : 140.00
     * sales : 0
     * slider_image : ["http://py.haoshusi.com/python/e775eeefa81f466768cb67f06af83ab02201.jpg","http://py.haoshusi.com/python/36ea4d70bbfde00f0d073b90895a0a882172.jpg","http://py.haoshusi.com/python/13bd5519e5aac952d24cbeb2a88f5e8c8580.jpg"]
     * sort : 0
     * stock : 1028
     * store_info :
     * store_name : 德国喜宝HiPP益生菌婴幼儿配方奶粉2段 600g 6-10个月适用
     * unit_name :
     * vip_price : 138.60
     * fsales : 872
     * userCollect : false
     * priceName: "140.00~900.00",
     * reply: null,
     * replyCount: 0,
     * replyChance: 0,
     * mer_id: 0,
     * details_url: "http://api.haoshusi.com/product.html?id=746", //网页长图
     * notFreight: 99
     */

    private int add_time;
    private int browse;
    private String cate_id;
    private int store_type;
    private String code_path;
    private String cost;
    private int ficti;
    private String give_integral;
    private int id;
    private String image;
    private Object is_bargain;
    private int is_benefit;
    private int is_best;
    private int is_del;
    private int is_hot;
    private int is_new;
    private int is_postage;
    private int is_seckill;
    private int is_show;
    private String keyword;
    private int mer_id;
    private int mer_use;
    private String ot_price;
    private String postage;
    private String price;
    private int sales;
    private int sort;
    private int stock;
    private String store_info;
    private String store_name;
    private String unit_name;
    private String vip_price;
    private String fsales;
    private boolean userCollect;
    private List<Object> slider_image;
    private String reply;
    private int replyCount;
    private int replyChance;
    private String details_url;
    private String priceName;
    private List<ProductAttr> productAttr;
    private Map<String, Object> productValue;
    private GoodsType goodsType;
    /**
     * product_id : 12
     * images : ["http://qiniu.haoshusi.com/images/88b4edb74e22e4b60a6b0d6164f1eb29.png","http://qiniu.haoshusi.com/images/69abbb26c3da0ccd57861d040bcf8ec6.png","http://qiniu.haoshusi.com/images/4fa4b0d5fe08970fd5240be8d6ef95f6.png"]
     * title : ABC-KMS-日用+夜用
     * info :
     * description : null
     * start_time : 1561910400
     * stop_time : 1567180800
     * add_time : 1562555111
     * status : 1
     * num : 1
     * userLike : false
     * like_num : 0
     */

    private int product_id;
    private String title;
    private String info;
    private Object description;
    private String start_time;
    private String stop_time;
    private int status;
    private int num;
    private boolean userLike;
    private int like_num;
    private List<Object> images;


    public int getAdd_time() {
        return add_time;
    }

    public void setAdd_time(int add_time) {
        this.add_time = add_time;
    }

    public int getBrowse() {
        return browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public int getStore_type() {
        return store_type;
    }

    public void setStore_type(int store_type) {
        this.store_type = store_type;
    }

    public String getCode_path() {
        return code_path;
    }

    public void setCode_path(String code_path) {
        this.code_path = code_path;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public String getGive_integral() {
        return give_integral;
    }

    public void setGive_integral(String give_integral) {
        this.give_integral = give_integral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getIs_bargain() {
        return is_bargain;
    }

    public void setIs_bargain(Object is_bargain) {
        this.is_bargain = is_bargain;
    }

    public int getIs_benefit() {
        return is_benefit;
    }

    public void setIs_benefit(int is_benefit) {
        this.is_benefit = is_benefit;
    }

    public int getIs_best() {
        return is_best;
    }

    public void setIs_best(int is_best) {
        this.is_best = is_best;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public int getIs_postage() {
        return is_postage;
    }

    public void setIs_postage(int is_postage) {
        this.is_postage = is_postage;
    }

    public int getIs_seckill() {
        return is_seckill;
    }

    public void setIs_seckill(int is_seckill) {
        this.is_seckill = is_seckill;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public int getMer_use() {
        return mer_use;
    }

    public void setMer_use(int mer_use) {
        this.mer_use = mer_use;
    }

    public String getOt_price() {
        return ot_price;
    }

    public void setOt_price(String ot_price) {
        this.ot_price = ot_price;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStore_info() {
        return store_info;
    }

    public void setStore_info(String store_info) {
        this.store_info = store_info;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getVip_price() {
        return vip_price;
    }

    public void setVip_price(String vip_price) {
        this.vip_price = vip_price;
    }

    public String getFsales() {
        return fsales;
    }

    public void setFsales(String fsales) {
        this.fsales = fsales;
    }

    public boolean isUserCollect() {
        return userCollect;
    }

    public void setUserCollect(boolean userCollect) {
        this.userCollect = userCollect;
    }

    public List<Object> getSlider_image() {
        return slider_image;
    }

    public void setSlider_image(ArrayList<Object> slider_image) {
        this.slider_image = slider_image;
    }

    public void setSlider_image(List<Object> slider_image) {
        this.slider_image = slider_image;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getReplyChance() {
        return replyChance;
    }

    public void setReplyChance(int replyChance) {
        this.replyChance = replyChance;
    }

    public String getDetails_url() {
        return details_url;
    }

    public void setDetails_url(String details_url) {
        this.details_url = details_url;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public List<ProductAttr> getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(List<ProductAttr> productAttr) {
        this.productAttr = productAttr;
    }

    public Map<String, Object> getProductValue() {
        return productValue;
    }

    public void setProductValue(Map<String, Object> productValue) {
        this.productValue = productValue;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isUserLike() {
        return userLike;
    }

    public void setUserLike(boolean userLike) {
        this.userLike = userLike;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public List<Object> getImages() {
        return images;
    }

    public void setImages(List<Object> images) {
        this.images = images;
    }

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }
}
