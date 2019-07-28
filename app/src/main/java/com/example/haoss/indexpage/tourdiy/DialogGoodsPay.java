package com.example.haoss.indexpage.tourdiy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.GrouponGoodInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.WordWrapView;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.pay.GoodsBuyActivity;
import com.example.haoss.pay.GoodsBuyInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品购买或者加入购物车
public class DialogGoodsPay extends Dialog {

    public DialogGoodsPay(Context context) {
        super(context);
    }

    public DialogGoodsPay(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogGoodsPay(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private Context context;
    private GrouponGoodInfo storeInfo;  //商品数据
    private LinearLayout dialog_goods_type;    //商品规格
    private ImageView dialog_goods_image;    //图片
    private TextView dialog_goods_name, dialog_goods_number,
            dialog_goods_price, dialog_goods_stock;    //商品名称、数量、价格、库存
    private WordWrapView goodTypeList1;  //类型信息
    private TextView dialog_goods_jian, dialog_goods_jia;  //减、加
    private int number = 1, repertory = 0; //添加数量、库存数量
    private GrouponDetail activity;
    private TextView[] textViews;
    private List<String> mapKey;
    private Map<String, GoodsBuyInfo> mapValue = new HashMap<>();
    private String selectType = "def";
    private int flag;//单独购买0，拼团购买1
    private Map<String, Object> productAttr;

    public DialogGoodsPay(Context context, GrouponGoodInfo storeInfo, Map<String, Object> productAttr, int flag) {
        super(context, com.example.applibrary.R.style.BottomDialog);
        this.activity = (GrouponDetail) context;
        this.context = context;
        this.storeInfo = storeInfo;
        this.flag = flag;
        if (flag == ConfigVariate.flagIntent) {//正常购买
            this.productAttr = productAttr;
            if (productAttr != null && productAttr.size() > 0) {
                this.textViews = new TextView[productAttr.size()];
            }
        } else {
            this.productAttr = null;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_groupon_goods, null);
        setContentView(view);

        dialog_goods_image = findViewById(R.id.dialog_goods_image);
        dialog_goods_name = findViewById(R.id.dialog_goods_name);
        dialog_goods_number = findViewById(R.id.dialog_goods_number);
        dialog_goods_price = findViewById(R.id.dialog_goods_price);
        dialog_goods_stock = findViewById(R.id.dialog_good_stock);
        goodTypeList1 = findViewById(R.id.type1_list);
        dialog_goods_jian = findViewById(R.id.dialog_goods_jian);
        dialog_goods_jia = findViewById(R.id.dialog_goods_jia);
        dialog_goods_type = findViewById(R.id.dialog_goods_type);

        findViewById(R.id.dialog_close).setOnClickListener(onClickListener);
        findViewById(R.id.dialog_confirm).setOnClickListener(onClickListener);
        dialog_goods_jian.setOnClickListener(onClickListener);
        dialog_goods_jia.setOnClickListener(onClickListener);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = context.getResources().getDisplayMetrics().heightPixels / 2;
        view.setLayoutParams(layoutParams);
        this.getWindow().setGravity(Gravity.BOTTOM);

        mapKey = new ArrayList<>();
        setData();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_close) //关闭
                dismiss();
            else if (v.getId() == R.id.dialog_goods_jian)  //减
                setNumber(false);
            else if (v.getId() == R.id.dialog_goods_jia) //加
                setNumber(true);
            else if (v.getId() == R.id.dialog_confirm) //确定
                goBuy();
        }
    };


    //刷新数据
    public void setRefresh(GrouponGoodInfo storeInfo, Map<String, Object> productAttr, int flag) {
        this.storeInfo = storeInfo;
        this.flag = flag;
        if (flag == ConfigVariate.flagIntent) {
            this.productAttr = productAttr;
            if (productAttr != null && productAttr.size() > 0) {
                this.textViews = new TextView[productAttr.size()];
            }
        } else {
            this.productAttr = null;
        }
        setData();
    }

    private void setData() {
        mapKey.clear();
        mapValue.clear();
        //没有商品属性
        if (productAttr == null || productAttr.size() == 0) {
            selectType = "def";
            dialog_goods_type.setVisibility(View.GONE);
            GoodsBuyInfo goodInfo = new GoodsBuyInfo();
            goodInfo.setId(storeInfo.getId());
            goodInfo.setProduct_id(storeInfo.getProduct_id());
            goodInfo.setImage(storeInfo.getImage());
            goodInfo.setPrice(storeInfo.getPrice());
            goodInfo.setOt_price(storeInfo.getProduct_price());
            goodInfo.setSales(storeInfo.getSales());
            goodInfo.setStock(storeInfo.getStock());
            mapValue.put("def", goodInfo);
        } else { //有商品属性
            dialog_goods_type.setVisibility(View.VISIBLE);
            for (Map.Entry<String, Object> entry : productAttr.entrySet()) {
                String key = entry.getKey();
                mapKey.add(key);
                Map<String, Object> value = (Map<String, Object>) productAttr.get(key);
                Map<String, Integer> inter = httpHander.getIntegerMap(value, "stock", "sales", "ficti", "product_id", "id");
                Map<String, String> string1 = httpHander.getStringMap(value, "suk", "price", "image", "unique", "cost");
                GoodsBuyInfo goodInfo = new GoodsBuyInfo();
                goodInfo.setId(storeInfo.getId());
                if (goodInfo.getProduct_id() == 0) {
                    goodInfo.setProduct_id(inter.get("product_id"));//正常购买可用，特价购买不可以
                } else {
                    goodInfo.setProduct_id(storeInfo.getProduct_id());
                }
                goodInfo.setStock(inter.get("stock"));
                goodInfo.setFicti(inter.get("ficti"));
                goodInfo.setSales(inter.get("sales"));
                goodInfo.setImage(string1.get("image"));
                goodInfo.setUniqueId(string1.get("unique"));
                goodInfo.setSuk(string1.get("suk"));
                goodInfo.setCost(string1.get("cost"));
                goodInfo.setPrice(string1.get("price"));
                mapValue.put(key, goodInfo);
            }
            selectType = mapKey.get(0);
            drawSKU();
        }
        setViewData();
    }

    private void drawSKU() {

        if (mapKey.isEmpty()) {
            return;
        }
        goodTypeList1.removeAllViews();
        for (int i = 0; i < mapKey.size(); i++) {
            final int indexi = i;
            TextView textView = new TextView(context);
            textView.setText(mapKey.get(i));
            textView.setPadding(DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 5), DensityUtil.dip2px(context, 10), DensityUtil.dip2px(context, 5));
            textViews[i] = textView;

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSelect(indexi);
                    selectType = mapKey.get(indexi);
                    setViewData();
                }
            });
            goodTypeList1.addView(textView);
        }
        setSelect(0);
    }


    private void setSelect(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setBackground(context.getResources().getDrawable(R.drawable.bg_red02));
            } else {
                textViews[i].setBackground(context.getResources().getDrawable(R.drawable.bk_hui_04));
            }
        }
    }

    //设置View数据
    private void setViewData() {
        GoodsBuyInfo info = mapValue.get(selectType);
        ImageUtils.imageLoad(context, storeInfo.getImage(), dialog_goods_image, 0, 0);
        dialog_goods_name.setText(storeInfo.getTitle());

        dialog_goods_price.setText("¥ " + info.getPrice());
        repertory = info.getStock();
        dialog_goods_stock.setText("库存数量：" + repertory + "");
        if (repertory == 0) {
            dialog_goods_jian.setTextColor(Color.parseColor("#999999"));
            dialog_goods_jia.setTextColor(Color.parseColor("#999999"));
            dialog_goods_jian.setEnabled(false);
            dialog_goods_jia.setEnabled(false);
        } else {
            dialog_goods_jian.setEnabled(true);
            dialog_goods_jia.setEnabled(true);
        }
    }

    //设置数量 isAdd==true:添加
    private void setNumber(boolean isAdd) {
        if (isAdd) {
            number++;
            if (number > repertory) {//数量 > 库存
                number = repertory;
                toast("没有更多商品了！");
            } else {
                if (number == 1) {
                    dialog_goods_jian.setTextColor(Color.parseColor("#999999"));
                } else {
                    dialog_goods_jian.setTextColor(Color.parseColor("#4d4d4d"));
                }
            }
        } else {
            if (number > 1) {
                dialog_goods_jian.setTextColor(Color.parseColor("#4d4d4d"));
                number--;
            } else {
                dialog_goods_jian.setTextColor(Color.parseColor("#999999"));
                number = 1;
            }
        }
        dialog_goods_number.setText(number + "");
    }


    //立即购买
    private void goBuy() {
//        if (storeInfo.get().getStore_type() == 0) {//国内
//            payOrder();
//        } else {
//            int isRealName = (int) SharedPreferenceUtils.getPreference(activity, ConfigVariate.isRealName, "I");
//            if (isRealName == 1) {//已认证
        payOrder();
//            } else {
//                setAuth();
//            }
//        }

    }

    private void payOrder() {
        GoodsBuyInfo goodInfo = mapValue.get(selectType);

        String url = Netconfig.confirmForm;
        HashMap<String, Object> map = new HashMap<>();

        map.put("cartNum", dialog_goods_number.getText().toString());
        map.put("uniqueId", goodInfo.getUniqueId());
        map.put("token", AppLibLication.getInstance().getToken());
        if (flag == ConfigVariate.flagIntent) {//单独购买，正常商品
            map.put("productId", goodInfo.getProduct_id());
        } else {//拼团购买
            map.put("productId", goodInfo.getId());
            map.put("combinationId", goodInfo.getId());
        }
        httpHander.okHttpMapPost(context, url, map, 2);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 2:
                    DialogGoodsPay.this.dismiss();

                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            Map data = getMap(ret, "data");
                            String cartId = httpHander.getString(data, "cartId");
                            Intent intent = new Intent(context, GoodsBuyActivity.class);
                            intent.putExtra("flag", flag);
                            intent.putExtra("cartId", cartId);
                            intent.putExtra("pinkId", activity.getPinkId());
                            context.startActivity(intent);
                        } else {
                            toast(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        toast(e.getMessage());
                    }
                    break;
            }
        }
    };

    //吐司
    private void toast(String text) {
        activity.tost(text);
    }


}
