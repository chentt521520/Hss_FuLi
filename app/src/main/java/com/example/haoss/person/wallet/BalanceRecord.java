package com.example.haoss.person.wallet;

public class BalanceRecord {


    /**
     * mark : 余额支付10.01元购买商品
     * pm : 0
     * number : 10.01
     * add_time : 2019/07/24 10:32
     */

    private String mark;
    private int pm;
    private double number;
    private String add_time;

    public BalanceRecord(String mark, int pm, double number, String add_time) {
        this.mark = mark;
        this.number = number;
        this.pm = pm;
        this.add_time = add_time;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
