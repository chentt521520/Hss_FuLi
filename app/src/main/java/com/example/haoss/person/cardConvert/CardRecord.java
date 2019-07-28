package com.example.haoss.person.cardConvert;

public class CardRecord {

    /**
     * id : 1
     * uid : 37
     * price : 100.00
     * card_id : 17
     * card_num : 1bc3e331
     * add_time : 2019-07-23 14:19:11
     */

    private int id;
    private int uid;
    private String price;
    private int card_id;
    private String card_num;
    private String add_time;

    public CardRecord(String card_num, String price, String add_time) {
        this.card_num = card_num;
        this.price = price;
        this.add_time = add_time;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
