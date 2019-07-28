package com.example.applibrary.entity;

import java.util.List;

public class GrouponGoodInfo {

    /**
     * id : 30
     * product_id : 749
     * mer_id : 0
     * image : http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg
     * images : ["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"]
     * title : 澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）
     * attr : null
     * people : 3
     * info : 爱他美
     * price : 169.00
     * sort : 0
     * sales : 2112
     * stock : 786
     * add_time : 1563255118
     * is_host : 1
     * is_show : 1
     * is_del : 0
     * combination : 1
     * mer_use : null
     * is_postage : 1
     * postage : 0.00
     * description :
     * start_time : 1561910400
     * stop_time : 1567180800
     * cost : 0
     * browse : 0
     * unit_name :
     * product_price : 171.00
     * combination_id : 30
     * userCollect : false
     */

    private int id;
    private int product_id;
    private int mer_id;
    private String image;
    private String title;
    private Object attr;
    private int people;
    private String info;
    private String price;
    private int sort;
    private int sales;
    private int stock;
    private String add_time;
    private int is_host;
    private int is_show;
    private int is_del;
    private int combination;
    private Object mer_use;
    private int is_postage;
    private String postage;
    private String description;
    private long start_time;
    private long stop_time;
    private int cost;
    private int browse;
    private String unit_name;
    private String product_price;
    private int combination_id;
    private boolean userCollect;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getMer_id() {
        return mer_id;
    }

    public void setMer_id(int mer_id) {
        this.mer_id = mer_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getAttr() {
        return attr;
    }

    public void setAttr(Object attr) {
        this.attr = attr;
    }

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getIs_host() {
        return is_host;
    }

    public void setIs_host(int is_host) {
        this.is_host = is_host;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public int getCombination() {
        return combination;
    }

    public void setCombination(int combination) {
        this.combination = combination;
    }

    public Object getMer_use() {
        return mer_use;
    }

    public void setMer_use(Object mer_use) {
        this.mer_use = mer_use;
    }

    public int getIs_postage() {
        return is_postage;
    }

    public void setIs_postage(int is_postage) {
        this.is_postage = is_postage;
    }

    public String getPostage() {
        return postage;
    }

    public void setPostage(String postage) {
        this.postage = postage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getStop_time() {
        return stop_time;
    }

    public void setStop_time(long stop_time) {
        this.stop_time = stop_time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getBrowse() {
        return browse;
    }

    public void setBrowse(int browse) {
        this.browse = browse;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public int getCombination_id() {
        return combination_id;
    }

    public void setCombination_id(int combination_id) {
        this.combination_id = combination_id;
    }

    public boolean isUserCollect() {
        return userCollect;
    }

    public void setUserCollect(boolean userCollect) {
        this.userCollect = userCollect;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
