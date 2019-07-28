package com.example.haoss.indexpage.tourdiy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.dialog.sharedialog.ShareWeChar;
import com.example.applibrary.entity.GrouponGood;
import com.example.applibrary.entity.GrouponGoodInfo;
import com.example.applibrary.entity.GrouponUser;
import com.example.applibrary.entity.ReplyInfo;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
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
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.conversation.ServerOnlineActivity;
import com.example.haoss.goods.estimate.EstimateListActivity;
import com.example.haoss.indexpage.tourdiy.adapter.GrouponPartnerAdapter;
import com.example.haoss.person.login.LoginActivity;

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
    private TextView good_estimate_num, good_favorable_rate, user_name, estmate_content;   //评价数量、满意度
    private ImageView userHead;
    private RelativeLayout good_estimate_layout;   //评价列表

    private DialogGoodsPay dialog;
    private List<GrouponUser> userList;
    private MyListView partnerList;
    private GrouponPartnerAdapter adapter;
    private GrouponGood grouponGood;
    private ShareWeChar shareWeChar;
    private int pinkId;
    private int id;


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
        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText("商品详情");
        titleView.setRightImage(R.drawable.goods_share);
        titleView.setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareWeChar == null) {
                    shareWeChar = new ShareWeChar(GrouponDetail.this, grouponGood.getStoreInfo().getTitle(),
                            grouponGood.getStoreInfo().getImage(), grouponGood.getStoreInfo().getInfo(), grouponGood.getDetails_url());
                } else {
                    shareWeChar.setUpData(grouponGood.getStoreInfo().getTitle(),
                            grouponGood.getStoreInfo().getImage(), grouponGood.getStoreInfo().getInfo(), grouponGood.getDetails_url());
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
        estmate_content = findViewById(R.id.estmate_content);
        userHead = findViewById(R.id.user_head);
        user_name = findViewById(R.id.user_name);
        good_estimate_layout.setOnClickListener(onClickListener);

        findViewById(R.id.look_more).setOnClickListener(onClickListener);
        btnCollect.setOnClickListener(onClickListener);
        btnService.setOnClickListener(onClickListener);
        btnSingleBuy.setOnClickListener(onClickListener);
        btnGrouponBuy.setOnClickListener(onClickListener);

        adapter = new GrouponPartnerAdapter(this, userList);
        partnerList.setAdapter(adapter);

        id = (int) SharedPreferenceUtils.getPreference(this, ConfigVariate.uid, "I");
        adapter.setListener(new GrouponPartnerAdapter.onClickLinstener() {
            @Override
            public void setOnClickListener(int pos) {
                setGroupon(pos);
            }
        });
    }

    private void initData() {

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
                    //{"code":200,"msg":"ok","data":{"pink":[],"pink_id":"30","pindAll":[],"storeInfo":{"id":30,"product_id":749,"mer_id":0,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","images":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"title":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","attr":null,"people":3,"info":"爱他美","price":"169.00","sort":0,"sales":2112,"stock":786,"add_time":"1563255118","is_host":1,"is_show":1,"is_del":0,"combination":1,"mer_use":null,"is_postage":1,"postage":"0.00","description":"","start_time":1561910400,"stop_time":1567180800,"cost":0,"browse":0,"unit_name":"","product_price":"171.00","combination_id":30,"userCollect":false},"pink_ok_list":["用户10013拼团成功","hhg拼团成功","999拼团成功","999拼团成功","用户10012yuh拼团成功","DIDI拼团成功","用户10013拼团成功","hhg拼团成功","hhg拼团成功"],"pink_ok_sum":10,"StoreProductAttr":[{"product_id":749,"attr_name":"包装","attr_values":["1罐装","3罐装","6罐装"],"attr_value":[{"attr":"1罐装","check":false},{"attr":"3罐装","check":false},{"attr":"6罐装","check":false}]},{"product_id":749,"attr_name":"有效期","attr_values":["2020年5月","2020年6月","2020年8月","2020年9月至10月"],"attr_value":[{"attr":"2020年5月","check":false},{"attr":"2020年6月","check":false},{"attr":"2020年8月","check":false},{"attr":"2020年9月至10月","check":false}]}],"StoreProductValue":{"1罐装,2020年5月":{"product_id":749,"suk":"1罐装,2020年5月","stock":88,"sales":0,"price":"171.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"5b9d6c1c","cost":"0.00"},"1罐装,2020年6月":{"product_id":749,"suk":"1罐装,2020年6月","stock":100,"sales":0,"price":"180.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"f771e304","cost":"0.00"},"1罐装,2020年8月":{"product_id":749,"suk":"1罐装,2020年8月","stock":269,"sales":0,"price":"173.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"4b7023fe","cost":"0.00"},"1罐装,2020年9月至10月":{"product_id":749,"suk":"1罐装,2020年9月至10月","stock":72,"sales":0,"price":"171.69","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"03589afd","cost":"0.00"},"2020年5月,3罐装":{"product_id":749,"suk":"2020年5月,3罐装","stock":29,"sales":0,"price":"480.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"18d053b2","cost":"0.00"},"2020年5月,6罐装":{"product_id":749,"suk":"2020年5月,6罐装","stock":14,"sales":0,"price":"980.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"1b148996","cost":"0.00"},"2020年6月,3罐装":{"product_id":749,"suk":"2020年6月,3罐装","stock":33,"sales":0,"price":"530.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"5eaa4941","cost":"0.00"},"2020年6月,6罐装":{"product_id":749,"suk":"2020年6月,6罐装","stock":16,"sales":0,"price":"1050.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"10fd3aea","cost":"0.00"},"2020年8月,3罐装":{"product_id":749,"suk":"2020年8月,3罐装","stock":89,"sales":0,"price":"500.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"56691451","cost":"0.00"},"2020年8月,6罐装":{"product_id":749,"suk":"2020年8月,6罐装","stock":44,"sales":0,"price":"980.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"3b1fae24","cost":"0.00"},"2020年9月至10月,3罐装":{"product_id":749,"suk":"2020年9月至10月,3罐装","stock":24,"sales":0,"price":"500.07","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"ae435651","cost":"0.00"},"2020年9月至10月,6罐装":{"product_id":749,"suk":"2020年9月至10月,6罐装","stock":12,"sales":0,"price":"990.14","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"1d6608ec","cost":"0.00"}},"reply":{"product_score":5,"service_score":5,"comment":"不错啊还会来的!","merchant_reply_content":null,"merchant_reply_time":"1970-01-01 08:00","pics":null,"add_time":"2019-07-15 03:43","nickname":"*","avatar":"http://py.haoshusi.com/avatar/90d916e7e5eedf074e20f7823438abaf.jpg","suk":"1罐装,2020年5月","star":"5"},"replyCount":22,"replyChance":"100.00","details_url":"http://api.haoshusi.com/product.html?id=749"},"count":0}
                    analysisJson(msg.obj.toString());
                    break;
                case 2:
                    collectUpdata(msg.obj.toString());
                    break;
            }
        }
    };

    private void analysisJson(String json) {
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
                    ArrayList<Object> allPeople = httpHander.getList(pink, "allPeople");
                    List<GrouponUser.Bean> beanList = new ArrayList<>();
                    for (int j = 0; j < allPeople.size(); j++) {
                        GrouponUser.Bean bean = new GrouponUser.Bean();
                        Map<String, Object> o = (Map<String, Object>) allPeople.get(j);
                        int uid = (int) httpHander.getDouble(o, "uid");
                        bean.setUid(uid);
                        beanList.add(bean);
                    }

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
                    user.setAllPeople(beanList);

                    if (user.getUid() == id) {//我是团长,查看拼团
                        user.setMyPink(true);
                    } else {//立即参团
                        user.setMyPink(false);
                    }

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

            grouponGood.setReplyCount((int) httpHander.getDouble(map, "replyCount"));
            grouponGood.setReplyChance(httpHander.getString(map, "replyChance"));

            Map<String, Object> replay = (Map<String, Object>) map.get("reply");
            if (replay != null) {
                ReplyInfo replayInfo = new ReplyInfo(httpHander.getString(replay, "nickname"), httpHander.getString(replay, "avatar"), httpHander.getString(replay, "comment"));
                grouponGood.setReply(replayInfo);
            } else {
                grouponGood.setReply(null);
            }
            updateUI();
        } else {

        }
    }

    private void updateUI() {

        if (userList.size() > 3) {
            findViewById(R.id.look_more).setVisibility(View.VISIBLE);
            adapter.freshList(userList.subList(0, 3));
        } else {
            adapter.freshList(userList);
            findViewById(R.id.look_more).setVisibility(View.GONE);
        }
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

        if (grouponGood.getReply() == null) {
            findViewById(R.id.no_estmate_item).setVisibility(View.VISIBLE);
            findViewById(R.id.estmate_item).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.no_estmate_item)).setText("暂无评价");
        } else {
            findViewById(R.id.no_estmate_item).setVisibility(View.GONE);
            findViewById(R.id.estmate_item).setVisibility(View.VISIBLE);
            good_estimate_num.setText("(" + grouponGood.getReplyCount() + ")");
            good_favorable_rate.setText("满意度" + grouponGood.getReplyChance() + "%");
            user_name.setText(grouponGood.getReply().getNickname());
            ImageUtils.loadCirclePic(this, grouponGood.getReply().getAvatar(), userHead);
            estmate_content.setText(grouponGood.getReply().getComment());
        }
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.good_estimate_layout:
                    IntentUtils.startIntent(goodInfo.getProduct_id(), GrouponDetail.this, EstimateListActivity.class);
                    break;
                case R.id.button_service:
                    if (!login()) {
                        startActivity(new Intent(GrouponDetail.this, ServerOnlineActivity.class));
                    }
                    break;
                case R.id.button_collect:
                    if (!login()) {
                        addOrCancelCollect();
                    }
                    break;
                case R.id.button_single_buy:
                    if (!login()) {
                        dialogPay(ConfigVariate.flagIntent);
                    }
                    break;
                case R.id.button_groupon_buy:
                    pinkId = 0;
                    if (!login()) {
                        dialogPay(ConfigVariate.flagGrouponIntent);
                    }
                    break;
                case R.id.look_more:
                    new DialogGrouponList(GrouponDetail.this, userList, grouponGood, new DialogGrouponList.OnItenClickListener() {
                        @Override
                        public void setOnItenClickListener(int pos) {
                            setGroupon(pos);
                        }
                    }).show();
                    break;
            }
        }
    };


    private void setGroupon(int pos) {

        GrouponUser user = grouponGood.getPink().get(pos);
        if (isMyPink(pos)) {
            IntentUtils.startIntent(user.getId(), GrouponDetail.this, GrouponNoticeActivity.class);
        } else {
            pinkId = user.getId();
            dialogPay(ConfigVariate.flagGrouponIntent);
        }
    }

    private boolean isMyPink(int pos) {
        List<GrouponUser.Bean> allPeople = userList.get(pos).getAllPeople();
        int uid = (int) SharedPreferenceUtils.getPreference(GrouponDetail.this, ConfigVariate.uid, "I");
        for (int i = 0; i < allPeople.size(); i++) {
            if (uid == allPeople.get(i).getUid()) {
                return true;
            }
        }
        return false;
    }

    public int getPinkId() {
        return pinkId;
    }

    public void setPinkId(int pinkId) {
        this.pinkId = pinkId;
    }

    //立即购买和加入购物车对话框
    private void dialogPay(int flag) {
        //未登录则先登录
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
