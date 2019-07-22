package com.example.haoss.person.footprint;

//足迹数据
public class FootprintInfo {

    /**
     * id : 318
     * uid : 15
     * product_id : 2630
     * add_time : 2019-07-10 15:15:25
     * is_del : 0
     * store_name : GIVENCHY 纪梵希四宫格散粉 12g
     * image : http://py.haoshusi.com/python/45a7b780744ec5bba7041e86c3a7d6db1851.jpg
     * price : 430.61
     * ficti : 149
     */

    private int id;
    private int uid;
    private int product_id;
    private String add_time;
    private int is_del;
    private String store_name;
    private String image;
    private String price;
    private int ficti;
    private boolean isCheck;//是否选中
    private int type;//0：正常浏览;1:编辑状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public int getIs_del() {
        return is_del;
    }

    public void setIs_del(int is_del) {
        this.is_del = is_del;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getFicti() {
        return ficti;
    }

    public void setFicti(int ficti) {
        this.ficti = ficti;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
