package com.example.haoss.pay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.entity.AddreInfo;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespDefaultSite;
import com.example.applibrary.utils.DecimalUtils;
import com.example.applibrary.utils.MD5Util;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.applibrary.widget.CustomerKeyboard;
import com.example.applibrary.widget.PasswordEditDialog;
import com.example.applibrary.widget.PasswordEditText;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.base.Constants;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.indexpage.tourdiy.GouponPayActivity;
import com.example.haoss.pay.aliapi.PayAliPay;
import com.example.haoss.pay.wxapi.PayWeChar;
import com.example.haoss.person.address.AddresShowActivity;
import com.example.haoss.person.coupon.CouponAdapter;
import com.example.haoss.person.coupon.CouponInfo;
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.haoss.base.AppLibLication.getInstance;

//商品购买-订单确认页面
public class GoodsBuyActivity extends BaseActivity {

    /**
     * 收货人信息
     */
    TextView person_name, person_phone, person_site; //姓名、电话、地址、
    /**
     * 商品列表
     */
    MyListView goodsbuyactivity_listview;   //商品列表
    /**
     * 金币数量
     */
    TextView gold_icon_text;   //购买数量
    /**
     * 配送方式
     */
    RelativeLayout sendWay;
    TextView goodsbuyactivity_waychoose;  //配送方式、配送选择
    EditText goodsbuyactivity_remark;   //备注
    TextView totalCountView;    //未优惠的金额、小计数量
    TextView coupon;    //优惠券价格
    TextView goodsbuyactivity_submit, goodsbuyactivity_allmoney;//  提交订单、总金额、总数量、优惠金额

    LinearLayout goodsbuyactivity_siteinfo;   //收货地址信息
    TextView goodsbuyactivity_hintsite; //无收货人信息时显示

    List<GoodsBuyInfo> listGoodsBuyInfo;    //商品数据
    GoodsBuyAdapter goodsBuyAdapter;    //商品适配器
    AddreInfo siteInfo;  //收货地址信息
    private AppLibLication application;
    private GoodsBuyInfo goodsBuyInfo;
    private double totalPrice;
    private String carId;
    private List<CouponInfo> listUseCoupon;
    private List<CouponInfo> listUnuseCoupon;
    private int totalCount;
    private String orderKey;
    private String payType = "weixin";
    private ImageView wexin_pay_check;
    private ImageView ali_pay_check;
    private ImageView banlence_pay_check;
    double storePostage;
    private CouponAdapter couponAdapter;
    private List<CouponInfo> listCoupon;
    private int intentFlag;
    private int pinkId;
    private int couponType = 1;
    private int selectIndex = -1;
    private int couponIndex = -1;
    private double payPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_submit_order);
        application = AppLibLication.getInstance();
        registerReceiver(mReceiver, new IntentFilter(IntentUtils.pay));
        init();
        getDefultSite();
        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == IntentUtils.intentActivityRequestCode && resultCode == RESULT_OK) {
            Intent intent = new Intent();
            setResult(IntentUtils.intentActivityRequestCode, intent);    //支付成功后，发到商品详情并关闭自己
            finish();
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            siteInfo = (AddreInfo) data.getSerializableExtra("addressInfo");
            if (siteInfo != null) {
                goodsbuyactivity_siteinfo.setVisibility(View.VISIBLE);
                goodsbuyactivity_hintsite.setVisibility(View.GONE);
            }
            person_name.setText(siteInfo.getName());
            person_phone.setText(siteInfo.getPhone());
            person_site.setText(siteInfo.getProvince() + siteInfo.getCity() + siteInfo.getCounty() + " " + siteInfo.getAddre());
        }
    }

    private void init() {
        this.getTitleView().setTitleText("确认订单");

        goodsbuyactivity_siteinfo = findViewById(R.id.ui_good_pay_siteinfo);
        goodsbuyactivity_hintsite = findViewById(R.id.ui_good_pay_no_site);
        //收货信息
        person_name = findViewById(R.id.ui_good_pay_name);
        person_phone = findViewById(R.id.ui_good_pay_phone);
        person_site = findViewById(R.id.ui_good_pay_site);
        //商品列表
        goodsbuyactivity_listview = findViewById(R.id.ui_good_pay_listview);
        //配送
        sendWay = findViewById(R.id.ui_good_pay_send_way_view);
        goodsbuyactivity_waychoose = findViewById(R.id.ui_good_pay_send_way);
        //买家备注
        goodsbuyactivity_remark = findViewById(R.id.ui_good_pay_remark);

        gold_icon_text = findViewById(R.id.ui_good_pay_gold_icon_text);
        //小计
        totalCountView = findViewById(R.id.ui_good_pay_total_count);
        //优惠券金额
        coupon = findViewById(R.id.ui_good_pay_coupon);
        //提交
        goodsbuyactivity_submit = findViewById(R.id.ui_good_pay_submit);
        goodsbuyactivity_allmoney = findViewById(R.id.ui_good_pay_total_price);
        wexin_pay_check = findViewById(R.id.wechart_pay_check);
        ali_pay_check = findViewById(R.id.alipay_pay_check);
        banlence_pay_check = findViewById(R.id.banlence_pay_check);

        //地址选择
        goodsbuyactivity_siteinfo.setOnClickListener(onClickListener);
        sendWay.setOnClickListener(onClickListener);
        coupon.setOnClickListener(onClickListener);
        goodsbuyactivity_submit.setOnClickListener(onClickListener);
        goodsbuyactivity_hintsite.setOnClickListener(onClickListener);
        wexin_pay_check.setOnClickListener(onClickListener);
        ali_pay_check.setOnClickListener(onClickListener);
        banlence_pay_check.setOnClickListener(onClickListener);

        goodsbuyactivity_submit.setEnabled(false);

        goodsbuyactivity_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsBuyInfo item = (GoodsBuyInfo) goodsBuyAdapter.getItem(position);
                int product_id = item.getProduct_id();
                IntentUtils.startIntent(product_id, GoodsBuyActivity.this, GoodsDetailsActivity.class);
            }
        });
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.action_title_goback://返回
                    finish();
                    break;
                case R.id.ui_good_pay_siteinfo://选择地址
                case R.id.ui_good_pay_no_site:
                    //地址选择
                    intent = new Intent(GoodsBuyActivity.this, AddresShowActivity.class);
                    intent.putExtra("flag", 2);
                    startActivityForResult(intent, 1);
                    break;
                case R.id.ui_good_pay_coupon://优惠抵扣选择
                    showCoupon();
                    break;
                case R.id.ui_good_pay_submit://提交
                    submit();
                    break;
                case R.id.wechart_pay_check://微信支付
                    setChoose(1);
                    break;
                case R.id.alipay_pay_check://支付宝支付
                    setChoose(2);
                    break;
                case R.id.banlence_pay_check://余额支付
                    setChoose(3);
                    break;

            }
        }
    };

    //设置选中 type==1:微信，==2：支付宝，==3：余额
    private void setChoose(int type) {
        if (type == 1) {
            wexin_pay_check.setImageResource(R.mipmap.check_box_true);
            ali_pay_check.setImageResource(R.mipmap.check_box_false);
            banlence_pay_check.setImageResource(R.mipmap.check_box_false);
            payType = Constants.WEIXIN;
        } else if (type == 2) {
            wexin_pay_check.setImageResource(R.mipmap.check_box_false);
            ali_pay_check.setImageResource(R.mipmap.check_box_true);
            banlence_pay_check.setImageResource(R.mipmap.check_box_false);
            payType = Constants.ALI;
        } else if (type == 3) {
            wexin_pay_check.setImageResource(R.mipmap.check_box_false);
            ali_pay_check.setImageResource(R.mipmap.check_box_false);
            banlence_pay_check.setImageResource(R.mipmap.check_box_true);
            payType = Constants.YUE;
        }
    }

    private void showCoupon() {

        listCoupon = listUseCoupon;

        View view = LayoutInflater.from(GoodsBuyActivity.this).inflate(R.layout.pop_coupon_select, null);
        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        final TextView viableBtn = view.findViewById(R.id.item_pop_viable_btn);
        final TextView unavailableBtn = view.findViewById(R.id.item_pop_unavailable_btn);
        final TextView saveText = view.findViewById(R.id.item_pop_save_text);
        final TextView btn_ok = view.findViewById(R.id.btn_ok);
        final ImageView close = view.findViewById(R.id.close);
        ListView couponList = view.findViewById(R.id.item_pop_coupon_list);
        viableBtn.setText("可用优惠券(" + listUseCoupon.size() + ")");
        unavailableBtn.setText("不可用优惠券(" + listUnuseCoupon.size() + ")");

        saveText.setText("已选中推荐优惠券，使用该优惠券抵扣¥" + getCouponPrice() + "元");

        viableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponType = 1;
                viableBtn.setTextColor(Color.parseColor("#c22222"));
                viableBtn.getPaint().setFakeBoldText(true);
                unavailableBtn.setTextColor(Color.parseColor("#0f0f0f"));
                unavailableBtn.getPaint().setFakeBoldText(false);
                listCoupon = listUseCoupon;
                couponAdapter.setRefresh(listCoupon);
            }
        });

        unavailableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponType = 0;
                unavailableBtn.setTextColor(Color.parseColor("#c22222"));
                unavailableBtn.getPaint().setFakeBoldText(true);
                viableBtn.setTextColor(Color.parseColor("#0f0f0f"));
                viableBtn.getPaint().setFakeBoldText(false);
                listCoupon = listUnuseCoupon;
                couponAdapter.setRefresh(listCoupon);
            }
        });

        couponAdapter = new CouponAdapter(this, listCoupon, 2);
        couponList.setAdapter(couponAdapter);

        couponAdapter.setListener(new CouponAdapter.OnItemClick() {

            @Override
            public void onCheck(int i) {
                if (couponType != 1) {
                    return;
                }
                for (CouponInfo info : listCoupon) {
                    info.setCheck(false);
                }

                if (couponIndex == i) {
                    listCoupon.get(i).setCheck(false);
                    selectIndex = -1;
                } else {
                    listCoupon.get(i).setCheck(true);
                    selectIndex = i;
                }
                couponAdapter.setRefresh(listCoupon);
            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponIndex = selectIndex;
                setData();
                popupWindow.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        popupWindow.setFocusable(true);
        //设置背景半透明
        backgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

        View footView = LayoutInflater.from(GoodsBuyActivity.this).inflate(R.layout.activity_submit_order, null);
        popupWindow.setAnimationStyle(com.example.applibrary.R.style.FadeInPopWin);
        popupWindow.showAtLocation(footView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = GoodsBuyActivity.this.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        GoodsBuyActivity.this.getWindow().setAttributes(lp);
    }


    //获取数据
    private void getData() {
        listCoupon = new ArrayList<>();
        Intent intent1 = getIntent();
        intentFlag = intent1.getIntExtra("flag", -1);
        pinkId = intent1.getIntExtra("pinkId", 0);
        carId = intent1.getStringExtra("cartId");
        listGoodsBuyInfo = new ArrayList<>();

        if (intentFlag == ConfigVariate.flagGrouponIntent || intentFlag == ConfigVariate.flagSalesIntent) {
            findViewById(R.id.ui_good_pay_coupon_view).setVisibility(View.GONE);
        } else {
            findViewById(R.id.ui_good_pay_coupon_view).setVisibility(View.VISIBLE);
        }
        submitFromShopCar();
    }

    private void jsonCarFrom(Map<String, Object> mapJson) {

        if (mapJson == null) {
            return;
        }
        orderKey = httpHander.getString(mapJson, "orderKey");
        //优惠券
        ArrayList<Object> usableCoupon = httpHander.getList(mapJson, "usableCoupon");
        listUseCoupon = new ArrayList<>();
        listUnuseCoupon = new ArrayList<>();
        if (usableCoupon != null) {
            for (int i = 0; i < usableCoupon.size(); i++) {
                Map<String, Object> mapArray = (Map<String, Object>) usableCoupon.get(i);
                String coupon_title = httpHander.getString(mapArray, "coupon_title");
                String coupon_price = httpHander.getString(mapArray, "coupon_price");
                String min_price = httpHander.getString(mapArray, "use_min_price");
                int id = (int) httpHander.getDouble(mapArray, "id");
                int is_available = (int) httpHander.getDouble(mapArray, "is_available");
                CouponInfo couponInfo = new CouponInfo();
                couponInfo.setId(id);
                couponInfo.setCoupon_title(coupon_title);
                couponInfo.setCoupon_price(coupon_price);
                couponInfo.setUse_min_price(min_price);
                if (is_available == 0) {//不可使用
                    couponInfo.setStatus(1);
                    listUnuseCoupon.add(couponInfo);
                } else {
                    couponInfo.setStatus(0);
                    listUseCoupon.add(couponInfo);
                }
            }
        }

        if (listUseCoupon.isEmpty()) {
            couponIndex = -1;
        } else {
            couponIndex = 0;
        }

        ArrayList<Object> cartInfo = httpHander.getList(mapJson, "cartInfo");
        for (int i = 0; i < cartInfo.size(); i++) {
            Map<String, Object> mapArray = (Map<String, Object>) cartInfo.get(i);
            int id = (int) httpHander.getDouble(mapArray, "id");
            int product_id = (int) httpHander.getDouble(mapArray, "product_id");
            int count = (int) httpHander.getDouble(mapArray, "cart_num");
            double truePrice = (int) httpHander.getDouble(mapArray, "truePrice");
            String unique = httpHander.getString(mapArray, "product_attr_unique");

            Map<String, Object> productInfo = httpHander.getMap(mapArray, "productInfo");
            String image = httpHander.getString(productInfo, "image");
            String store_name = httpHander.getString(productInfo, "store_name");
            double postage = httpHander.getDouble(productInfo, "postage");

            Map<String, Object> attrInfo = httpHander.getMap(mapArray, "attrInfo");
            String suk = httpHander.getString(attrInfo, "suk");

            goodsBuyInfo = new GoodsBuyInfo();
            goodsBuyInfo.setName(store_name);
            goodsBuyInfo.setId(id);
            goodsBuyInfo.setProduct_id(product_id);
            goodsBuyInfo.setImage(image);
            goodsBuyInfo.setMoney(truePrice);
            goodsBuyInfo.setPostage(postage);
            goodsBuyInfo.setNumber(count);
            goodsBuyInfo.setSuk(suk);
            goodsBuyInfo.setUniqueId(unique);
            listGoodsBuyInfo.add(goodsBuyInfo);
            totalCount += count;
        }

        Map<String, Object> priceGroup = httpHander.getMap(mapJson, "priceGroup");
        totalPrice = httpHander.getDouble(priceGroup, "totalPrice");
        storePostage = httpHander.getDouble(priceGroup, "storePostage");

        if (goodsBuyAdapter == null) {
            goodsBuyAdapter = new GoodsBuyAdapter(this, listGoodsBuyInfo);
            goodsbuyactivity_listview.setAdapter(goodsBuyAdapter);
        } else
            goodsBuyAdapter.setRefresh(listGoodsBuyInfo);

        setData();
    }

    private String getCouponPrice() {
        if (couponIndex != -1) {
            listUseCoupon.get(couponIndex).setCheck(true);
            return listUseCoupon.get(couponIndex).getCoupon_price();
        } else {
            return "0";
        }
    }


    private void setData() {
        String couponPrice = "0";
        if (listUseCoupon.size() == 0) {
            coupon.setText("暂无可用优惠劵");
        } else {
            couponPrice = getCouponPrice();
            coupon.setText("已选推荐优惠券 -¥" + couponPrice);
        }

        double price;
        if (Double.parseDouble(couponPrice) >= totalPrice) {
            price = 0.0;
        } else {
            price = totalPrice - Double.parseDouble(couponPrice);
        }

        if (intentFlag == ConfigVariate.flagGrouponIntent || intentFlag == ConfigVariate.flagSalesIntent) {
            payPrice = price;
            goodsbuyactivity_waychoose.setText("快递免邮");
        } else {
            if (price >= 99) {
                payPrice = price;
                goodsbuyactivity_waychoose.setText("快递免邮");
            } else {
                payPrice = price + storePostage;
                goodsbuyactivity_waychoose.setText("快递 ¥" + storePostage);
            }
        }

        goodsbuyactivity_allmoney.setText("¥ " + DecimalUtils.format2Number(payPrice));
        totalCountView.setText("共" + totalCount + "件");

        goodsbuyactivity_submit.setEnabled(true);
        goodsbuyactivity_submit.setBackgroundColor(getResources().getColor(R.color.baseRedColor));
    }

    //获取默认地址
    private void getDefultSite() {
        String url = Netconfig.getDefaultAddress;
        Map<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, application.getToken());
        httpHander.okHttpMapPost(this, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //获取默认收货地址
                    getDefaultSite(msg.obj.toString(), new OnHttpCallback<AddreInfo>() {
                        @Override
                        public void success(AddreInfo result) {
                            if (result == null) {
                                goodsbuyactivity_siteinfo.setVisibility(View.GONE);
                                goodsbuyactivity_hintsite.setVisibility(View.VISIBLE);
                            } else {
                                goodsbuyactivity_siteinfo.setVisibility(View.VISIBLE);
                                goodsbuyactivity_hintsite.setVisibility(View.GONE);
                                siteInfo = result;

                                //{"id":12,"real_name":"errrr88888","phone":"13222222222","province":"内蒙古自治区","city":"通辽市","district":"库伦旗","detail":"rrrrrr","is_default":1}
                                //设置地址信息
                                person_name.setText(siteInfo.getReal_name());
                                person_phone.setText(siteInfo.getPhone());
                                person_site.setText(siteInfo.getProvince() + siteInfo.getCity() + siteInfo.getDistrict()
                                        + " " + siteInfo.getDetail());
                            }
                        }

                        @Override
                        public void error(int code, String msg) {
                            goodsbuyactivity_siteinfo.setVisibility(View.GONE);
                            goodsbuyactivity_hintsite.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                case 2://创建订单
                    try {
                        Map map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map == null) {
                            tost("解析出错！");
                        } else if (getDouble(map, "code") == 200) {
                            jsonCarFrom(jsonToMap(msg.obj.toString()));
                        } else {
                            tost(getString(map, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
                case 3:
                    jsonDeal(msg.obj.toString());
                    break;

                case 4://验证余额支付有无密码
                    try {
                        Map map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map == null) {
                            tost("解析出错！");
                        } else if (getDouble(map, "code") == 200) {
                            Map<String, Object> data = getMap(map, "data");
                            boolean is_pass = (boolean) data.get("is_pass");
                            SharedPreferenceUtils.setPreference(GoodsBuyActivity.this, "PASSWORD", is_pass, "B");
                            if (is_pass) {
                                getCurrentBalance();
                            } else {
                                setPwd();
                            }
                        } else {
                            tost(getString(map, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
                case 5:
                    Map<String, Object> map = jsonToMap(msg.obj.toString());
                    if (map != null) {
                        String money = getString(map, "now_money");
                        if (Double.parseDouble(money) < payPrice) {
                            tost("余额不足" + payPrice);
                        } else {
                            showPwd();
                        }
                    }
                    break;
            }
        }
    };

    /**
     * 设置支付密码
     */
    private void setPwd() {
        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(GoodsBuyActivity.this, "检测到您没有设置支付密码，是否继续购买？", new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(GoodsBuyActivity.this, PaySettingActivity.class);
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();
    }


    //确认订单
    private void submitFromShopCar() {
        String url = Netconfig.confirmOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("cartId", carId);
        map.put("token", application.getToken());
        httpHander.okHttpMapPost(this, url, map, 2);
    }


    //跳转支付界面并传入数据
    private void submit() {
        if (siteInfo == null) {
            tost("请选择收货地址！");
            return;
        }

        if (orderKey.equals("")) {
            tost("确认订单失败，请重新提交！");
            return;
        }
        payOrder();
    }

    private void payOrder() {
        if (TextUtils.equals(payType, Constants.YUE)) {
            checkYE();
        } else {
            commiteOrder("");
        }
    }


    private void commiteOrder(String pwd) {
        String url = Netconfig.submitOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("key", orderKey);
        map.put("addressId", siteInfo.getId());
        if (couponIndex != -1)
            map.put("couponId", listUseCoupon.get(couponIndex).getId());
        map.put("mark", "");
        map.put("payType", payType);
        if (!TextUtils.isEmpty(pwd)) {
            map.put("payPass", MD5Util.getMD5String(pwd));
        }
        map.put("token", getInstance().getToken());
        map.put("useIntegral", false);
        /**拼团产品id 普通商品为空或0
         * combination_id	T文本	是
         * 1
         * 团长id，如果开团为0
         * pinkId	T文本	是
         * 1
         * 拼团id 普通商品为空或0
         * seckill_id
         */
        if (intentFlag == ConfigVariate.flagGrouponIntent) {
            map.put("combination_id", goodsBuyInfo.getId());
            map.put("pinkId", pinkId);
        } else if (intentFlag == ConfigVariate.flagSalesIntent) {
            map.put("seckill_id", goodsBuyInfo.getId());
        }
        httpHander.okHttpMapPost(GoodsBuyActivity.this, url, map, 3);
    }

    private void jsonDeal(String json) {

        if (payType.equals(Constants.WEIXIN)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                if (jsonObject1 != null) {
                    String sign = jsonObject1.get("sign") + "";
                    String partnerid = jsonObject1.get("partnerid") + "";
                    String prepayid = jsonObject1.get("prepayid") + "";
                    String noncestr = jsonObject1.get("noncestr") + "";
                    String timestamp = jsonObject1.get("timestamp") + "";
                    new PayWeChar(GoodsBuyActivity.this, partnerid,
                            prepayid, noncestr,
                            timestamp, sign).toWXPay();
                } else
                    tost("请求失败，重新尝试");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (payType.equals(Constants.ALI)) {
            String sAli = httpHander.jsonToString(json);
            if (sAli == null) {
                return;
            } else {
                new PayAliPay(GoodsBuyActivity.this).PayZFB(sAli);
            }

        } else if (payType.equals(Constants.YUE)) {

            try {
                Map<String, Object> map = new Gson().fromJson(json, HashMap.class);
                if (map == null) {
                    tost("解析出错");
                    return;
                }
                if (httpHander.getDouble(map, "code") == 200) {
                    tost("支付成功");
                    if (intentFlag == ConfigVariate.flagGrouponIntent) {//开团成功
                        Intent intent = new Intent(GoodsBuyActivity.this, GouponPayActivity.class);
                        intent.putExtra("orderId", httpHander.getString(map, "msg"));
                        intent.putExtra("id", goodsBuyInfo.getId());
                        startActivity(intent);
                    } else {
                        toOrderList(1);
                    }
                } else {
                    tost(httpHander.getString(map, "msg"));
                    toOrderList(0);
                }

            } catch (Exception e) {
                tost(e.getMessage());
            }
        }
    }


    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String status = intent.getStringExtra("status");
            int flag;
            if (TextUtils.isEmpty(status)) {
                return;
            }
            if (action.equals(IntentUtils.pay)) {//微信
                switch (status) {
                    case "0":
                        tost("支付成功");
                        if (intentFlag == ConfigVariate.flagGrouponIntent) {
                            //开团成功
                            IntentUtils.startIntent(GoodsBuyActivity.this, GouponPayActivity.class);
                            return;
                        } else {
                            flag = 1;
                        }
                        break;
                    case "-1":
                        tost("检测到您没有安装微信");
                        flag = 0;
                        break;
                    case "1":
                        tost("支付失败");
                        flag = 0;
                        break;
                    case "2":
                        tost("支付取消");
                        flag = 0;
                        break;
                    default:
                        tost("支付失败");
                        flag = 0;
                        break;
                }
                toOrderList(flag);
                finish();
            }
        }
    };

    private void checkYE() {
        boolean is_pass = (boolean) SharedPreferenceUtils.getPreference(GoodsBuyActivity.this, "PASSWORD", "B");
        if (is_pass) {
            getCurrentBalance();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("token", application.getToken());
            httpHander.okHttpMapPost(GoodsBuyActivity.this, Netconfig.checkYuE, map, 4);
        }
    }


    private void showPwd() {
        final PasswordEditDialog passwordEditDialog = new PasswordEditDialog(GoodsBuyActivity.this);
        passwordEditDialog.setTitle("请输入密码");
        final PasswordEditText password_edit_text = passwordEditDialog.getPasswordEdit();
        passwordEditDialog.setPasswordClickListeners(new PasswordEditText.PasswordFullListener() {
            @Override
            public void passwordFull(String pwd) {
                commiteOrder(pwd);
                passwordEditDialog.dismiss();
            }
        });
        passwordEditDialog.customKeyBoard(new CustomerKeyboard.CustomerKeyboardClickListener() {
            @Override
            public void click(String number) {
                password_edit_text.addpassword(number);
            }

            @Override
            public void delete() {
                password_edit_text.deleteLastPassword();
            }
        });

    }

    //获取个人中心信息
    public void getCurrentBalance() {
        String url = Netconfig.personalCenter;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 5);
    }

    private void toOrderList(final int flag) {
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            public void run() {
                IntentUtils.startIntent(flag, GoodsBuyActivity.this, OrderListActivity.class);
//                finish();
//            }
//        };
//        timer.schedule(task, 2500);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
