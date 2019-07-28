package com.example.applibrary.entity;

import java.io.Serializable;

//地址信息（保护编辑）
public class AddreInfo implements Serializable {
    private int id; //地址id
    private boolean isChecked;  //是否选中
    private String name = "";    //姓名
    private String phone = "";   //电话
    private String province = "";    //省
    private boolean isDefault;    //省
    private String city = "";    //市
    private String county = "";  //县
    private String addre = "";   //详细地址


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddre() {
        return addre;
    }

    public void setAddre(String addre) {
        this.addre = addre;
    }


    /**
     * id : 42
     * real_name : 陈婷婷
     * phone : 15667074017
     * province : 四川省
     * city : 成都市
     * district : 青羊区
     * detail : 府南街道
     * is_default : 1
     */
//    private int id;
    private String real_name;
    //    private String phone;
//    private String province;
//    private String city;
    private String district;
    private String detail;
    private int is_default;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getIs_default() {
        return is_default;
    }

    public void setIs_default(int is_default) {
        this.is_default = is_default;
    }
}
