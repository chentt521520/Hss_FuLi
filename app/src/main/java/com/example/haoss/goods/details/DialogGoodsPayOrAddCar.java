package com.example.haoss.goods.details;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.GoodsDetailsInfo;
import com.example.applibrary.entity.StoreInfo;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.widget.WordWrapView;
import com.example.haoss.R;
import com.example.haoss.pay.GoodsBuyActivity;
import com.example.haoss.pay.GoodsBuyInfo;
import com.example.haoss.person.AuthenticationActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品购买或者加入购物车
public class DialogGoodsPayOrAddCar extends Dialog {

    public DialogGoodsPayOrAddCar(Context context) {
        super(context);
    }

    public DialogGoodsPayOrAddCar(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogGoodsPayOrAddCar(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private Context context;
    private GoodsDetailsInfo detailsInfo;  //商品数据
    private LinearLayout dialog_goodspayoraddcar_type;    //图片
    private ImageView dialog_goodspayoraddcar_image;    //图片
    private TextView dialog_goodspayoraddcar_name, dialog_goodspayoraddcar_number,
            dialog_goodspayoraddcar_money, dialog_goodspayoraddcar_repertory;    //商品名称、数量、价格、库存
    private WordWrapView goodTypeList1;  //类型信息
    private TextView dialog_goodspayoraddcar_jian, dialog_goodspayoraddcar_jia;  //减、加
    private int number = 1, repertory = 0; //添加数量、库存数量
    private GoodsDetailsActivity activity;
    private TextView[] textViews;
    private List<String> mapKey;
    private Map<String, GoodsBuyInfo> map = new HashMap<>();
    private String selectType = "def";
    private int flag = 0;

    public DialogGoodsPayOrAddCar(Context context, GoodsDetailsInfo storeInfo, int flag) {
        super(context, com.example.applibrary.R.style.BottomDialog);
        this.activity = (GoodsDetailsActivity) context;
        this.context = context;
        this.detailsInfo = storeInfo;
        this.flag = flag;
        if (storeInfo.getProductValue() != null && storeInfo.getProductValue().size() > 0) {
            this.textViews = new TextView[storeInfo.getProductValue().size()];
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_goodspayoraddcar, null);
        setContentView(view);

        dialog_goodspayoraddcar_image = findViewById(R.id.dialog_goodspayoraddcar_image);
        dialog_goodspayoraddcar_name = findViewById(R.id.dialog_goodspayoraddcar_name);
        dialog_goodspayoraddcar_number = findViewById(R.id.dialog_goodspayoraddcar_number);
        dialog_goodspayoraddcar_money = findViewById(R.id.dialog_goodspayoraddcar_money);
        dialog_goodspayoraddcar_repertory = findViewById(R.id.dialog_goodspayoraddcar_repertory);
        goodTypeList1 = findViewById(R.id.type1_list);
        dialog_goodspayoraddcar_jian = findViewById(R.id.dialog_goodspayoraddcar_jian);
        dialog_goodspayoraddcar_jia = findViewById(R.id.dialog_goodspayoraddcar_jia);
        dialog_goodspayoraddcar_type = findViewById(R.id.dialog_goodspayoraddcar_type);
        dialog_goodspayoraddcar_number.setText("1");

        findViewById(R.id.dialog_goodspayoraddcar_close).setOnClickListener(onClickListener);
        dialog_goodspayoraddcar_jian.setOnClickListener(onClickListener);
        dialog_goodspayoraddcar_jia.setOnClickListener(onClickListener);
        findViewById(R.id.dialog_goodspayoraddcar_add).setOnClickListener(onClickListener);
        findViewById(R.id.dialog_goodspayoraddcar_pay).setOnClickListener(onClickListener);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        layoutParams.height = context.getResources().getDisplayMetrics().heightPixels / 2;
        view.setLayoutParams(layoutParams);
        this.getWindow().setGravity(Gravity.BOTTOM);

        setData();
        drawSKU();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_goodspayoraddcar_close) //关闭
                dismiss();
            else if (v.getId() == R.id.dialog_goodspayoraddcar_jian)  //减
                setNumber(false);
            else if (v.getId() == R.id.dialog_goodspayoraddcar_jia) //加
                setNumber(true);
            else if (v.getId() == R.id.dialog_goodspayoraddcar_add) //加入购物车
                addCar();
            else if (v.getId() == R.id.dialog_goodspayoraddcar_pay) //立即购买
                goBuy();
        }
    };


    //刷新数据
    public void setRefresh(GoodsDetailsInfo storeInfo) {
        this.detailsInfo = storeInfo;
        dialog_goodspayoraddcar_number.setText("1");
        drawSKU();
    }

    private void drawSKU() {
        if (mapKey.size() == 0) {
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


    private void setData() {
        mapKey = new ArrayList<>();
        map.clear();
        StoreInfo storeInfo = detailsInfo.getStoreInfo();
        Map<String, Object> productValue = detailsInfo.getProductValue();
        //没有商品属性
        /**
         * 特价商品有属性与无属性是product_id=id
         * 正常商品无属性时使用
         */
        if (productValue == null || productValue.size() == 0) {
            dialog_goodspayoraddcar_type.setVisibility(View.GONE);
            GoodsBuyInfo goodInfo = new GoodsBuyInfo();
            goodInfo.setId(storeInfo.getId());
            goodInfo.setProduct_id(storeInfo.getId());
            goodInfo.setImage(storeInfo.getImage());
            goodInfo.setPrice(storeInfo.getPrice());
            goodInfo.setSales(storeInfo.getSales());
            goodInfo.setStock(storeInfo.getStock());
            map.put("def", goodInfo);
        } else { //有商品属性
            /**
             * 特价商品有属性与无属性是product_id=id
             */
            dialog_goodspayoraddcar_type.setVisibility(View.VISIBLE);
            for (Map.Entry<String, Object> entry : productValue.entrySet()) {
                String key = entry.getKey();
                mapKey.add(key);
                Map<String, Object> value = (Map<String, Object>) productValue.get(key);
                Map<String, Integer> inter = httpHander.getIntegerMap(value, "stock", "sales", "ficti", "product_id");
                Map<String, String> string1 = httpHander.getStringMap(value, "suk", "price", "image", "unique", "cost");
                GoodsBuyInfo goodInfo = new GoodsBuyInfo();
                goodInfo.setId(storeInfo.getId());
                goodInfo.setProduct_id(inter.get("product_id"));
                goodInfo.setStock(inter.get("stock"));
                goodInfo.setFicti(inter.get("ficti"));
                goodInfo.setSales(inter.get("sales"));
                goodInfo.setImage(string1.get("image"));
                goodInfo.setUniqueId(string1.get("unique"));
                goodInfo.setSuk(string1.get("suk"));
                goodInfo.setCost(string1.get("cost"));
                goodInfo.setPrice(string1.get("price"));
                map.put(key, goodInfo);
            }
            selectType = mapKey.get(0);
        }
        setViewData();
    }

    private void setSelect(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setBackground(context.getResources().getDrawable(R.drawable.bk_corner_red_20));
            } else {
                textViews[i].setBackground(context.getResources().getDrawable(R.drawable.bk_corner_grey_20));
            }
        }
    }

    //设置View数据
    private void setViewData() {
        GoodsBuyInfo info = map.get(selectType);
        ImageUtils.imageLoad(context, detailsInfo.getStoreInfo().getImage(), dialog_goodspayoraddcar_image, 0, 0);
        dialog_goodspayoraddcar_name.setText(detailsInfo.getStoreInfo().getStore_name());
        dialog_goodspayoraddcar_money.setText("¥ " + info.getPrice());
        repertory = info.getStock();
        dialog_goodspayoraddcar_repertory.setText("库存数量：" + repertory + "");
        if (repertory == 0) {
            dialog_goodspayoraddcar_jian.setTextColor(Color.parseColor("#999999"));
            dialog_goodspayoraddcar_jia.setTextColor(Color.parseColor("#999999"));
            dialog_goodspayoraddcar_jian.setEnabled(false);
            dialog_goodspayoraddcar_jia.setEnabled(false);
        } else {
            dialog_goodspayoraddcar_jian.setEnabled(true);
            dialog_goodspayoraddcar_jia.setEnabled(true);
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
                    dialog_goodspayoraddcar_jian.setTextColor(Color.parseColor("#999999"));
                } else {
                    dialog_goodspayoraddcar_jian.setTextColor(Color.parseColor("#4d4d4d"));
                }
            }
        } else {
            if (number > 1) {
                dialog_goodspayoraddcar_jian.setTextColor(Color.parseColor("#4d4d4d"));
                number--;
            } else {
                dialog_goodspayoraddcar_jian.setTextColor(Color.parseColor("#999999"));
                number = 1;
            }
        }
        dialog_goodspayoraddcar_number.setText(number + "");
    }

    //加入购物车
    private void addCar() {
        GoodsBuyInfo goodInfo = map.get(selectType);
        String url = Netconfig.addShoppingCar;
        HashMap<String, Object> map = new HashMap<>();
        map.put("cartNum", number);
        map.put("uniqueId", goodInfo.getUniqueId());
        map.put("token", AppLibLication.getInstance().getToken());
        if (flag == ConfigVariate.flagSalesIntent) {//特价
            map.put("productId", goodInfo.getId());
            map.put("seckillId", goodInfo.getId());
        } else {
            map.put("productId", goodInfo.getId());
        }
        httpHander.okHttpMapPost(context, url, map, 1);
    }


    //立即购买
    private void goBuy() {

        if (detailsInfo.getStoreInfo().getStore_type() == 0) {//国内
            payOrder();
        } else {
            int isRealName = (int) SharedPreferenceUtils.getPreference(activity, ConfigVariate.isRealName, "I");
            if (isRealName == 1) {//已认证
                payOrder();
            } else {
                setAuth();
            }
        }
    }

    /**
     * 认证提示框
     */
    private void setAuth() {
        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(activity, "您所购买的商品需要实名认证，是否继续购买？", "去认证", "", new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(activity, AuthenticationActivity.class);
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();
    }

    private void payOrder() {
        GoodsBuyInfo goodInfo = map.get(selectType);

        String url = Netconfig.confirmForm;
        HashMap<String, Object> map = new HashMap<>();
        map.put("cartNum", number);
        map.put("uniqueId", goodInfo.getUniqueId());
        map.put("token", AppLibLication.getInstance().getToken());
        if (flag == ConfigVariate.flagSalesIntent) {//特价
            map.put("seckillId", goodInfo.getId());
            map.put("productId", goodInfo.getId());
        } else {
            map.put("productId", goodInfo.getProduct_id());
        }
        httpHander.okHttpMapPost(context, url, map, 2);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    DialogGoodsPayOrAddCar.this.dismiss();
                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            toast("加入购物车成功！");
                            activity.getShopCarNumber();
                        } else {
                            toast(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        toast(e.getMessage());
                    }

                    break;
                case 2:
                    DialogGoodsPayOrAddCar.this.dismiss();

                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            Map data = getMap(ret, "data");
                            String cartId = httpHander.getString(data, "cartId");
                            Intent intent = new Intent(context, GoodsBuyActivity.class);
                            if (flag == ConfigVariate.flagSalesIntent) {
                                intent.putExtra("flag", ConfigVariate.flagSalesIntent);
                            } else {
                                intent.putExtra("flag", ConfigVariate.flagGoodsToPayIntent);
                            }
                            intent.putExtra("cartId", cartId);
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
        ((GoodsDetailsActivity) context).tost(text);
    }


}
