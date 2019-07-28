package com.example.applibrary.entity;

import java.util.List;

public class ProductAttr {

    /**
     * product_id : 746
     * attr_name : 包装
     * attr_values : ["1罐装","3罐装","6罐装"]
     * attr_value : [{"attr":"1罐装","check":false},{"attr":"3罐装","check":false},{"attr":"6罐装","check":false}]
     */

    private int product_id;
    private String attr_name;
    private List<String> attr_values;
    private List<AttrValue> attr_value;

    public ProductAttr() {
    }

    public ProductAttr(int product_id, String attr_name, List<String> attr_values) {
        this.product_id = product_id;
        this.attr_name = attr_name;
        this.attr_values = attr_values;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getAttr_name() {
        return attr_name;
    }

    public void setAttr_name(String attr_name) {
        this.attr_name = attr_name;
    }

    public List<String> getAttr_values() {
        return attr_values;
    }

    public void setAttr_values(List<String> attr_values) {
        this.attr_values = attr_values;
    }

    public List<AttrValue> getAttr_value() {
        return attr_value;
    }

    public void setAttr_value(List<AttrValue> attr_value) {
        this.attr_value = attr_value;
    }
}
