package com.example.applibrary.resp;

import com.example.applibrary.entity.ProductInfo;
import com.example.applibrary.entity.ShoppingCartInfo;

import java.util.List;

public class RespShopCart {

    /**
     * code : 200
     * msg : ok
     * data : {"valid":[{"id":316,"uid":37,"type":"product","product_id":749,"product_attr_unique":"f771e304","cart_num":1,"add_time":1564133027,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":749,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","slider_image":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"price":"171.00","ot_price":"222.30","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"297","sales":0,"stock":790,"store_name":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":749,"suk":"1罐装,2020年6月","stock":100,"sales":0,"price":"180.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"f771e304","cost":"0.00"}},"truePrice":180,"vip_truePrice":0,"trueStock":100,"costPrice":"0.00"},{"id":315,"uid":37,"type":"product","product_id":749,"product_attr_unique":"03589afd","cart_num":1,"add_time":1564133023,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":749,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","slider_image":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"price":"171.00","ot_price":"222.30","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"297","sales":0,"stock":790,"store_name":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":749,"suk":"1罐装,2020年9月至10月","stock":72,"sales":0,"price":"171.69","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"03589afd","cost":"0.00"}},"truePrice":171.69,"vip_truePrice":0,"trueStock":72,"costPrice":"0.00"},{"id":164,"uid":37,"type":"product","product_id":2905,"product_attr_unique":"6e912d15","cart_num":1,"add_time":1563867694,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":2905,"image":"http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","slider_image":["http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","http://py.haoshusi.com/py0709/56c78b8c55259203a20204dffce449f4.jpg","http://py.haoshusi.com/py0709/d6e5c9c491e93362eb4249068856c90b.jpg","http://py.haoshusi.com/py0709/41b6b77ededdd79619c6b033e89d5f15.jpg"],"price":"124.78","ot_price":"162.21","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"619","sales":0,"stock":33,"store_name":"韩国杯具熊 儿童书包 幼儿园双肩包 男女童宝宝背包卡通包包","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":2905,"suk":"红绿色恐龙","stock":14,"sales":0,"price":"124.78","image":"http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","unique":"6e912d15","cost":"0.00"}},"truePrice":124.78,"vip_truePrice":0,"trueStock":14,"costPrice":"0.00"}],"invalid":[]}
     * count : 0
     */

    private int code;
    private String msg;
    private ShopCart data;
    private int count;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ShopCart getData() {
        return data;
    }

    public void setData(ShopCart data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class ShopCart {
        private List<ShoppingCartInfo> valid;
        private List invalid;

        public List<ShoppingCartInfo> getValid() {
            return valid;
        }

        public void setValid(List<ShoppingCartInfo> valid) {
            this.valid = valid;
        }

        public List getInvalid() {
            return invalid;
        }

        public void setInvalid(List invalid) {
            this.invalid = invalid;
        }
    }
}
