package com.example.haoss.indexpage.tourdiy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.dialog.sharedialog.ShareWeChar;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.viewfragment.FragmentDataInfo;
import com.example.applibrary.custom.viewfragment.FragmentView;
import com.example.applibrary.custom.viewfragment.OnclickFragmentView;
import com.example.applibrary.custom.webview.NoScrollWebView;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.conversation.ServerOnlineActivity;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.estimate.EstimateListActivity;
import com.example.haoss.helper.DialogHelp;
import com.example.haoss.indexpage.tourdiy.adapter.GrouponPartnerAdapter;
import com.example.haoss.indexpage.tourdiy.entity.GrouponGood;
import com.example.haoss.indexpage.tourdiy.entity.GrouponGoodInfo;
import com.example.haoss.indexpage.tourdiy.entity.GrouponUser;
import com.example.haoss.person.login.LoginActivity;
import com.example.haoss.views.MyListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrouponDetail extends BaseActivity {

    int goodsId;    //商品id
    FragmentView carousel;  //轮播
    ArrayList<FragmentDataInfo> listBanner = new ArrayList<>(); //轮播
    NoScrollWebView webView;    //webview
    private TextView curPrice;
    private TextView oriPrice;
    private TextView salesText;
    private GrouponGoodInfo goodInfo;
    private Map<String, Object> productAttr;
    private TextView goodName;
    private TextView grouponNumber;
    private AppLibLication application;
    private boolean isCollect;
    private TextView btnCollect;
    private TextView btnService;
    private TextView btnSingleBuy;
    private TextView btnGrouponBuy;
    private TextView good_estimate_num, good_favorable_rate, estmate_item;   //评价数量、满意度
    private RelativeLayout good_estimate_layout;   //评价列表

    private DialogGoodsPay dialog;
    private List<GrouponUser> userList;
    private MyListView partnerList;
    private GrouponPartnerAdapter adapter;
    private GrouponGood grouponGood;
    private ShareWeChar shareWeChar;
    private int pinkId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_groupon_detail);
        application = AppLibLication.getInstance();
        initTitle();
        iniView();
        initData();
    }


    private void initTitle() {
        CustomTitleView titleView = new CustomTitleView(this);
        titleView.setTitleText("商品详情");
        titleView.setRightImage(R.drawable.goods_share);
        titleView.setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareWeChar == null) {
                    shareWeChar = new ShareWeChar(GrouponDetail.this, grouponGood.getStoreInfo().getTitle(),
                            grouponGood.getStoreInfo().getInfo(), grouponGood.getDetails_url());
                } else {
                    shareWeChar.setUpData(grouponGood.getStoreInfo().getTitle(),
                            grouponGood.getStoreInfo().getInfo(), grouponGood.getDetails_url());
                }
                shareWeChar.show();
            }
        });
    }

    private void iniView() {

        webView = findViewById(R.id.goodsdetailsactivity_webview);
        webView.initWebview(webView);
        carousel = findViewById(R.id.goodsdetailsactivity_carousel);
        curPrice = findViewById(R.id.goodsdetailsactivity_newmoney);
        oriPrice = findViewById(R.id.goodsdetailsactivity_shopprice);
        salesText = findViewById(R.id.goodsdetailsactivity_monthlysales);
        goodName = findViewById(R.id.goodsdetailsactivity_name);
        grouponNumber = findViewById(R.id.goodsdetailsactivity_intro);
        partnerList = findViewById(R.id.partner_listview);
        btnCollect = findViewById(R.id.button_collect);
        btnService = findViewById(R.id.button_service);
        btnSingleBuy = findViewById(R.id.button_single_buy);
        btnGrouponBuy = findViewById(R.id.button_groupon_buy);

        good_estimate_num = findViewById(R.id.good_estimate_num);
        good_favorable_rate = findViewById(R.id.good_favorable_rate);
        good_estimate_layout = findViewById(R.id.good_estimate_layout);
        estmate_item = findViewById(R.id.estmate_item);
        good_estimate_layout.setOnClickListener(onClickListener);

        btnCollect.setOnClickListener(onClickListener);
        btnService.setOnClickListener(onClickListener);
        btnSingleBuy.setOnClickListener(onClickListener);
        btnGrouponBuy.setOnClickListener(onClickListener);

        adapter = new GrouponPartnerAdapter(this, userList);
        partnerList.setAdapter(adapter);

        adapter.setListener(new GrouponPartnerAdapter.onClickLinstener() {
            @Override
            public void setOnClickListener(int pos) {
                pinkId = grouponGood.getPink().get(pos).getPink_id();
                dialogPay(1);
            }
        });
    }

    private void initData() {
        DialogHelp.showLoading(GrouponDetail.this);

        userList = new ArrayList<>();

        goodsId = getIntent().getIntExtra("id", -1);
        String url = Netconfig.pinTuanDetails + Netconfig.headers;
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", goodsId);
        httpHander.okHttpPost(GrouponDetail.this, url, map, 1);

    }

    //设置轮播数据
    private void setCarousel() {
        carousel.addFragment(getSupportFragmentManager(), listBanner, 3000, new OnclickFragmentView() {
            @Override
            public void onItemclick(int id, String url) {
                //轮播图点击操作
            }
        });
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisJson(msg.obj.toString());
                    break;
                case 2:
                    collectUpdata(msg.obj.toString());
                    break;
            }
        }
    };

    private void analysisJson(String json) {
        DialogHelp.hideLoading();
        Map<String, Object> map = httpHander.jsonToMap(json);
        if (map != null) {
            grouponGood = new GrouponGood();
            /**
             * 拼团的人
             */
            String details_url = httpHander.getString(map, "details_url");
            grouponGood.setDetails_url(details_url);
            List<Object> pinkList = (List<Object>) map.get("pink");
            if (pinkList != null && !pinkList.isEmpty()) {
                for (int i = 0; i < pinkList.size(); i++) {

                    Map<String, Object> pink = (Map<String, Object>) pinkList.get(i);
                    Map<String, String> mapString = httpHander.getStringMap(pink, "nickname", "avatar", "count", "h", "i", "s", "add_time",
                            "stop_time", "price", "total_price", "order_id");
                    Map<String, Integer> mapInteger = httpHander.getIntegerMap(pink, "id", "uid", "pink_id", "order_id_key", "total_num", "cid",
                            "pid", "people", "k_id", "is_tpl", "is_refund");

                    GrouponUser user = new GrouponUser();
                    user.setNickname(mapString.get("nickname"));
                    user.setAvatar(mapString.get("avatar"));
                    user.setCount(mapString.get("count"));
                    user.setPrice(mapString.get("price"));
                    user.setTotal_price(mapString.get("total_price"));
                    user.setAdd_time(mapString.get("add_time"));
                    user.setStop_time(mapString.get("stop_time"));
                    user.setOrder_id(mapString.get("order_id"));
                    user.setH(mapString.get("h"));
                    user.setI(mapString.get("i"));
                    user.setS(mapString.get("s"));
                    user.setId(mapInteger.get("id"));
                    user.setPink_id(mapInteger.get("pink_id"));
                    user.setUid(mapInteger.get("uid"));
                    user.setOrder_id_key(mapInteger.get("order_id_key"));
                    user.setTotal_num(mapInteger.get("total_num"));
                    user.setCid(mapInteger.get("cid"));
                    user.setPid(mapInteger.get("pid"));
                    user.setPeople(mapInteger.get("people"));
                    user.setK_id(mapInteger.get("k_id"));
                    user.setIs_tpl(mapInteger.get("is_tpl"));
                    user.setIs_refund(mapInteger.get("is_refund"));

                    userList.add(user);
                }
                findViewById(R.id.groupon_parter_list).setVisibility(View.VISIBLE);
            } else {
                findViewById(R.id.groupon_parter_list).setVisibility(View.GONE);
            }
            grouponGood.setPink(userList);
            Map<String, Object> storeInfo = (Map<String, Object>) map.get("storeInfo");
            if (storeInfo != null) {
                Map<String, String> mapString = httpHander.getStringMap(storeInfo, "title", "image", "price",
                        "product_price", "postage", "info");
                Map<String, Integer> mapInteger = httpHander.getIntegerMap(storeInfo, "id", "product_id", "people", "sales", "stock");
                Map<String, Long> mapDouble = httpHander.getLongMap(storeInfo, "start_time", "stop_time");
                //轮播
                ArrayList<Object> listImage = httpHander.getList(storeInfo, "images");


                listBanner.clear();
                for (int i = 0; i < listImage.size(); i++) {
                    String imageUrl = listImage.get(i) + "";
                    FragmentDataInfo fragmentDataInfo = new FragmentDataInfo();
                    fragmentDataInfo.setId(i);
                    fragmentDataInfo.setImageUrl(imageUrl);
                    listBanner.add(fragmentDataInfo);
                }

                goodInfo = new GrouponGoodInfo();
                goodInfo.setId(mapInteger.get("id"));
                goodInfo.setProduct_id(mapInteger.get("product_id"));
                goodInfo.setPeople(mapInteger.get("people"));
                goodInfo.setSales(mapInteger.get("sales"));
                goodInfo.setStock(mapInteger.get("stock"));
                goodInfo.setTitle(mapString.get("title"));
                goodInfo.setImage(mapString.get("image"));
                goodInfo.setPrice(mapString.get("price"));
                goodInfo.setProduct_price(mapString.get("product_price"));
                goodInfo.setPostage(mapString.get("postage"));
                goodInfo.setStart_time(mapDouble.get("start_time"));
                goodInfo.setStop_time(mapDouble.get("stop_time"));
            }
            grouponGood.setStoreInfo(goodInfo);

            productAttr = (Map<String, Object>) map.get("StoreProductValue");
            updateUI();
        } else {

        }
    }

    private void updateUI() {
        adapter.freshList(userList);
        setCarousel();

        curPrice.setText("¥ " + goodInfo.getPrice());
        oriPrice.setText("¥ " + goodInfo.getProduct_price());
        oriPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        goodName.setText(goodInfo.getTitle());
        salesText.setText("月销" + goodInfo.getSales());
        grouponNumber.setText(goodInfo.getPeople() + "人拼团");
        webView.loadUrl(grouponGood.getDetails_url());

        btnSingleBuy.setText(goodInfo.getProduct_price() + "\n单独购买");
        btnGrouponBuy.setText(goodInfo.getPrice() + "\n拼团购买");

        if (grouponGood.getReplyCount() == 0) {
            good_estimate_layout.setVisibility(View.GONE);
            estmate_item.setText("暂无评价");
        } else {
            good_estimate_layout.setVisibility(View.VISIBLE);
            good_estimate_num.setText("(" + grouponGood.getReplyCount() + ")");
        }
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.good_estimate_layout:
                    IntentUtils.startIntent(goodInfo.getCombination_id(), GrouponDetail.this, EstimateListActivity.class);
                    break;
                case R.id.button_service:
                    startActivity(new Intent(GrouponDetail.this, ServerOnlineActivity.class));
                    break;

                case R.id.button_collect:
                    if (login()) {
                        return;
                    } else {
                        addOrCancelCollect();
                    }
                    break;
                case R.id.button_single_buy:
                    dialogPay(0);
                    break;

                case R.id.button_groupon_buy:
                    pinkId = 0;
                    dialogPay(1);
                    break;
            }
        }
    };

    public int getPinkId() {
        return pinkId;
    }

    public void setPinkId(int pinkId) {
        this.pinkId = pinkId;
    }

    //立即购买和加入购物车对话框
    private void dialogPay(int flag) {
        if (goodInfo == null) {
            tost("加载数据失败");
        } else {
            if (dialog == null)
                dialog = new DialogGoodsPay(this, goodInfo, productAttr, flag);
            else {
                dialog.setRefresh(goodInfo, productAttr, flag);
            }
            dialog.show();
        }
    }


    //未登录则先登录
    private boolean login() {
        if (!application.isLogin()) {//未登录
            MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(this, "您还未登录，是否立即登录？", new DialogOnClick() {
                @Override
                public void operate() {
                    //未登录首先登录
                    IntentUtils.startIntentForResult(0, GrouponDetail.this, LoginActivity.class);
                }

                @Override
                public void cancel() {

                }
            });
            myDialogTwoButton.show();
            return true;
        }
        return false;
    }

    //添加或取消收藏
    private void addOrCancelCollect() {
        String url = "";
        if (!isCollect) //添加收藏
            url = Netconfig.addShoppingCollect;
        else    //取消收藏
            url = Netconfig.cancleShoppingCollect;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, application.getToken());
        map.put(ConfigHttpReqFields.sendProductId, goodsId);
        httpHander.okHttpMapPost(this, url, map, 2);
    }

    //收藏图标修改
    private void collectUpdata(String json) {
        try {
            HashMap map = new Gson().fromJson(json, HashMap.class);
            if (map != null && Double.parseDouble(map.get("code") + "") == 200) {
                if (isCollect) {  //取消收藏
                    isCollect = false;
                    btnCollect.setText("收藏");
                    btnCollect.setTextColor(Color.parseColor("#0f0f0f"));
                    TextViewUtils.setImage(GrouponDetail.this, btnCollect, R.drawable.goods_collect, 2);
                } else {  //收藏好
                    isCollect = true;
                    btnCollect.setText("已收藏");
                    btnCollect.setTextColor(Color.parseColor("#c22222"));
                    TextViewUtils.setImage(GrouponDetail.this, btnCollect, R.drawable.goods_collect_yes, 2);
                }
            } else {
                tost(map.get("msg") + "");
            }
        } catch (Exception e) {
            tost(e.getMessage());
        }
    }

}
