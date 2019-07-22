package com.example.haoss.person.dingdan.entity;

public class OrderStatus {
    /**
     * _type : 1
     * _title : 未发货
     * _msg : 商家未发货,请耐心等待
     * _class : state-nfh
     * _payType : 余额支付
     * _deliveryType : 其他方式
     */

    private int _type;
    private String _title;
    private String _msg;
    private String _class;
    private String _payType;
    private String _deliveryType;

    public int get_type() {
        return _type;
    }

    public void set_type(int _type) {
        this._type = _type;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_msg() {
        return _msg;
    }

    public void set_msg(String _msg) {
        this._msg = _msg;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String get_payType() {
        return _payType;
    }

    public void set_payType(String _payType) {
        this._payType = _payType;
    }

    public String get_deliveryType() {
        return _deliveryType;
    }

    public void set_deliveryType(String _deliveryType) {
        this._deliveryType = _deliveryType;
    }
}
