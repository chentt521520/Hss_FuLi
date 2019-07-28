package com.example.haoss.goods.details;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.entity.GoodsDetailsInfo;
import com.example.applibrary.entity.GoodsMold;
import com.example.applibrary.entity.ProductAttr;
import com.example.applibrary.entity.ReplyInfo;
import com.example.applibrary.entity.StoreInfo;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.widget.CustomTitleView;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.viewfragment.FragmentDataInfo;
import com.example.applibrary.custom.viewfragment.FragmentView;
import com.example.applibrary.custom.viewfragment.OnclickFragmentView;
import com.example.applibrary.custom.webview.NoScrollWebView;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.dialog.sharedialog.ShareWeChar;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.conversation.ServerOnlineActivity;
import com.example.haoss.goods.estimate.EstimateListActivity;
import com.example.haoss.person.login.LoginActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//商品详情
public class GoodsDetailsActivity extends BaseActivity {

    int goodsId;    //商品id
    FragmentView carousel;  //轮播
    ArrayList<FragmentDataInfo> listBanner = new ArrayList<>(); //轮播
    NoScrollWebView webView;    //webview

    TextView goodsdetailsactivity_newmoney, goodsdetailsactivity_shopprice, goodsdetailsactivity_monthlysales; //新区间价格、商家优惠、月销量
    TextView goodsdetailsactivity_originalprice, goodsdetailsactivity_intro;    //元区间价格、商品简单说明(商品名称)
    TextView goodsdetailsactivity_shipmentsland, goodsdetailsactivity_newbag, goodsdetailsactivity_juan;    //发货地、新人礼包、领劵
    TextView goodsdetailsactivity_content, goodsdetailsactivity_exempt, goodsdetailsactivity_loss;  //净含量、免运费、说明
    TextView good_estimate_num, good_favorable_rate, user_name, estmate_content;   //评价数量、满意度
    ImageView userHead;
    RelativeLayout good_estimate_layout;   //评价列表

    RelativeLayout action_button_kefu, action_button_collect, action_button_car;  //客服、收藏、购物车
    TextView action_button_collect_text, action_button_car_number;    //收藏变更、购物车数量
    TextView action_button_add, action_button_pay; //加入购物车、购买
    boolean isCollect;  //是否收藏
    MyDialogTwoButton myDialogTwoButton;    //登录提示对话框
    DialogGoodsPayOrAddCar dialogGoodsPayOrAddCar;  //购买和加入购物车对话框
    ShareWeChar shareWeChar;    //分享对话框
    private AppLibLication application;
    private GoodsDetailsInfo detailsInfo;
    private int flag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.layout_goodsdetailsactivity);
        application = AppLibLication.getInstance();
        init();
        initTitle();
    }

    private void initTitle() {
        CustomTitleView titleView = this.getTitleView();
        titleView.setTitleText("商品详情");
        titleView.setRightImage(R.drawable.goods_share);
        titleView.setRightImageOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()) {
                    return;
                }
                if (shareWeChar == null) {
                    shareWeChar = new ShareWeChar(GoodsDetailsActivity.this, detailsInfo.getStoreInfo().getStore_name(),
                            detailsInfo.getStoreInfo().getImage(), detailsInfo.getStoreInfo().getStore_info(), detailsInfo.getDetails_url());
                } else {
                    shareWeChar.setUpData(detailsInfo.getStoreInfo().getStore_name(),
                            detailsInfo.getStoreInfo().getImage(), detailsInfo.getStoreInfo().getStore_info(), detailsInfo.getDetails_url());
                }
                shareWeChar.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        carousel.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentUtils.intentActivityRequestCode && resultCode == RESULT_OK) {
            //从支付成功回调到此处，并调到订单列表，同时关闭自己
            IntentUtils.startIntent(0, this, IntentUtils.getIntentClass(this, ConfigVariate.packOrderForm));
            finish();
        }
    }

    //初始化
    public void init() {
        webView = findViewById(R.id.goodsdetailsactivity_webview);
        webView.initWebview(webView);

        carousel = findViewById(R.id.goodsdetailsactivity__carousel);
        goodsdetailsactivity_newmoney = findViewById(R.id.goodsdetailsactivity_newmoney);
        goodsdetailsactivity_shopprice = findViewById(R.id.goodsdetailsactivity_shopprice);
        goodsdetailsactivity_monthlysales = findViewById(R.id.goodsdetailsactivity_monthlysales);
        goodsdetailsactivity_originalprice = findViewById(R.id.goodsdetailsactivity_originalprice);
        goodsdetailsactivity_intro = findViewById(R.id.goodsdetailsactivity_intro);
        goodsdetailsactivity_shipmentsland = findViewById(R.id.goodsdetailsactivity_shipmentsland);
        goodsdetailsactivity_newbag = findViewById(R.id.goodsdetailsactivity_newbag);
        goodsdetailsactivity_juan = findViewById(R.id.goodsdetailsactivity_juan);
        goodsdetailsactivity_content = findViewById(R.id.goodsdetailsactivity_content);
        goodsdetailsactivity_exempt = findViewById(R.id.goodsdetailsactivity_exempt);
        goodsdetailsactivity_loss = findViewById(R.id.goodsdetailsactivity_loss);
        good_estimate_num = findViewById(R.id.good_estimate_num);
        good_favorable_rate = findViewById(R.id.good_favorable_rate);
        good_estimate_layout = findViewById(R.id.good_estimate_layout);
        estmate_content = findViewById(R.id.estmate_content);
        userHead = findViewById(R.id.user_head);
        user_name = findViewById(R.id.user_name);

        //下5操作按钮
        action_button_kefu = findViewById(R.id.action_button_kefu);
        action_button_collect = findViewById(R.id.action_button_collect);
        action_button_car = findViewById(R.id.action_button_car);
        action_button_add = findViewById(R.id.action_button_add);
        action_button_pay = findViewById(R.id.action_button_pay);

        action_button_collect.setEnabled(false);
        action_button_car.setEnabled(false);
        action_button_add.setEnabled(false);
        action_button_pay.setEnabled(false);

        //操作变更
        action_button_collect_text = findViewById(R.id.action_button_collect_text);
        action_button_car_number = findViewById(R.id.action_button_car_number);

        //监听
        goodsdetailsactivity_juan.setOnClickListener(onClickListener);
        goodsdetailsactivity_content.setOnClickListener(onClickListener);
        action_button_kefu.setOnClickListener(onClickListener);
        action_button_collect.setOnClickListener(onClickListener);
        action_button_car.setOnClickListener(onClickListener);
        action_button_add.setOnClickListener(onClickListener);
        action_button_pay.setOnClickListener(onClickListener);
        good_estimate_layout.setOnClickListener(onClickListener);

        goodsdetailsactivity_originalprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        goodsId = getIntent().getIntExtra(IntentUtils.intentActivityFlag, 0);
        flag = getIntent().getIntExtra("flag", 0);
        getGoodDetail();
    }

    private void getGoodDetail() {
        String url;
        if (flag == ConfigVariate.flagSalesIntent) {
            url = Netconfig.seckillShopDetails;
        } else {
            url = Netconfig.commodityDetails;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", goodsId);
        map.put("token", application.getToken());
        httpHander.okHttpMapPost(GoodsDetailsActivity.this, url, map, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新购物车数量
        getShopCarNumber();
    }

    public void getShopCarNumber() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, Netconfig.shoppingCarList, map, 2);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //商品详情
                    //正常商品 {"code":200,"msg":"ok","data":{"valid":[{"id":386,"uid":37,"type":"product","product_id":42,"product_attr_unique":"7a36c34e","cart_num":1,"add_time":1564318906,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":42,"bargain_id":0,"admin_id":null,"productInfo":{"id":42,"image":"http://py.haoshusi.com/python/8042c9c73be8f395e5caf05a2951d1e91092.jpg","price":"189.00","ot_price":"191.92","postage":"10.00","give_integral":"0.00","sales":6671,"stock":1048,"store_name":"英国爱他美Aptamil白金版婴幼儿配方牛奶粉3段 800g 1-2岁适用（英爱白金）","unit_name":"罐","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":42,"suk":"1罐装","stock":1049,"sales":0,"price":"189.00","image":"http://py.haoshusi.com/python/8042c9c73be8f395e5caf05a2951d1e91092.jpg","unique":"7a36c34e","cost":"0.00"}},"truePrice":189,"vip_truePrice":0,"trueStock":1049,"costPrice":"0.00"},{"id":384,"uid":37,"type":"product","product_id":1709,"product_attr_unique":"3b0ecf20","cart_num":1,"add_time":1564316619,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":1709,"image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","slider_image":["http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg"],"price":"109.99","ot_price":"142.99","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"131","sales":4,"stock":30,"store_name":"【香港直邮】美国科颜氏高保湿小样护肤3件套","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1709,"suk":"1套","stock":26,"sales":4,"price":"109.99","image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","unique":"3b0ecf20","cost":"0.00"}},"truePrice":109.99,"vip_truePrice":0,"trueStock":26,"costPrice":"0.00"},{"id":383,"uid":37,"type":"product","product_id":44,"product_attr_unique":"","cart_num":1,"add_time":1564316158,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":44,"bargain_id":0,"admin_id":null,"productInfo":{"id":44,"image":"http://qiniu.haoshusi.com/images/32605201907241554278141.jpg","price":"39.90","ot_price":"49.90","postage":"10.00","give_integral":"0.00","sales":36592,"stock":100000,"store_name":"妍丝柔嫩肤烟酰沐浴露血橙沐浴露 美白补水保湿沐浴露","unit_name":"件","is_show":1,"is_del":0,"is_postage":1,"cost":"0.00"},"truePrice":39.9,"vip_truePrice":0,"trueStock":100000,"costPrice":"0.00"},{"id":382,"uid":37,"type":"product","product_id":2615,"product_attr_unique":"47f93433","cart_num":1,"add_time":1564316130,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":2615,"image":"http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","slider_image":["http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","http://py.haoshusi.com/python/17559cad2468cf190a79b225401fb0f73583.jpg"],"price":"198.00","ot_price":"257.40","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"145","sales":2,"stock":1058,"store_name":"韩国ahc水乳套装 B5玻尿补水保湿清爽型两件套男女官方正品新版","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":2615,"suk":"水乳两件套","stock":1056,"sales":2,"price":"198.00","image":"http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","unique":"47f93433","cost":"0.00"}},"truePrice":198,"vip_truePrice":0,"trueStock":1056,"costPrice":"0.00"},{"id":316,"uid":37,"type":"product","product_id":749,"product_attr_unique":"f771e304","cart_num":3,"add_time":1564133027,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":749,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","slider_image":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"price":"171.00","ot_price":"222.30","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"297","sales":0,"stock":790,"store_name":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":749,"suk":"1罐装,2020年6月","stock":100,"sales":0,"price":"180.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"f771e304","cost":"0.00"}},"truePrice":180,"vip_truePrice":0,"trueStock":100,"costPrice":"0.00"},{"id":315,"uid":37,"type":"product","product_id":749,"product_attr_unique":"03589afd","cart_num":1,"add_time":1564133023,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":749,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","slider_image":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"price":"171.00","ot_price":"222.30","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"297","sales":0,"stock":790,"store_name":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":749,"suk":"1罐装,2020年9月至10月","stock":72,"sales":0,"price":"171.69","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"03589afd","cost":"0.00"}},"truePrice":171.69,"vip_truePrice":0,"trueStock":72,"costPrice":"0.00"},{"id":164,"uid":37,"type":"product","product_id":2905,"product_attr_unique":"6e912d15","cart_num":1,"add_time":1563867694,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":2905,"image":"http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","slider_image":["http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","http://py.haoshusi.com/py0709/56c78b8c55259203a20204dffce449f4.jpg","http://py.haoshusi.com/py0709/d6e5c9c491e93362eb4249068856c90b.jpg","http://py.haoshusi.com/py0709/41b6b77ededdd79619c6b033e89d5f15.jpg"],"price":"124.78","ot_price":"162.21","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"619","sales":0,"stock":33,"store_name":"韩国杯具熊 儿童书包 幼儿园双肩包 男女童宝宝背包卡通包包","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":2905,"suk":"红绿色恐龙","stock":14,"sales":0,"price":"124.78","image":"http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","unique":"6e912d15","cost":"0.00"}},"truePrice":124.78,"vip_truePrice":0,"trueStock":14,"costPrice":"0.00"}],"invalid":[]},"count":0}
                    //特价商品{"code":200,"msg":"ok","data":{"valid":[{"id":386,"uid":37,"type":"product","product_id":42,"product_attr_unique":"7a36c34e","cart_num":1,"add_time":1564318906,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":42,"bargain_id":0,"admin_id":null,"productInfo":{"id":42,"image":"http://py.haoshusi.com/python/8042c9c73be8f395e5caf05a2951d1e91092.jpg","price":"189.00","ot_price":"191.92","postage":"10.00","give_integral":"0.00","sales":6671,"stock":1048,"store_name":"英国爱他美Aptamil白金版婴幼儿配方牛奶粉3段 800g 1-2岁适用（英爱白金）","unit_name":"罐","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":42,"suk":"1罐装","stock":1049,"sales":0,"price":"189.00","image":"http://py.haoshusi.com/python/8042c9c73be8f395e5caf05a2951d1e91092.jpg","unique":"7a36c34e","cost":"0.00"}},"truePrice":189,"vip_truePrice":0,"trueStock":1049,"costPrice":"0.00"},{"id":384,"uid":37,"type":"product","product_id":1709,"product_attr_unique":"3b0ecf20","cart_num":1,"add_time":1564316619,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":1709,"image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","slider_image":["http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg"],"price":"109.99","ot_price":"142.99","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"131","sales":4,"stock":30,"store_name":"【香港直邮】美国科颜氏高保湿小样护肤3件套","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":1709,"suk":"1套","stock":26,"sales":4,"price":"109.99","image":"http://py.haoshusi.com/python/1ec7d8827cc1a3c7029bc6006d4b91b53163.jpg","unique":"3b0ecf20","cost":"0.00"}},"truePrice":109.99,"vip_truePrice":0,"trueStock":26,"costPrice":"0.00"},{"id":383,"uid":37,"type":"product","product_id":44,"product_attr_unique":"","cart_num":1,"add_time":1564316158,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":44,"bargain_id":0,"admin_id":null,"productInfo":{"id":44,"image":"http://qiniu.haoshusi.com/images/32605201907241554278141.jpg","price":"39.90","ot_price":"49.90","postage":"10.00","give_integral":"0.00","sales":36592,"stock":100000,"store_name":"妍丝柔嫩肤烟酰沐浴露血橙沐浴露 美白补水保湿沐浴露","unit_name":"件","is_show":1,"is_del":0,"is_postage":1,"cost":"0.00"},"truePrice":39.9,"vip_truePrice":0,"trueStock":100000,"costPrice":"0.00"},{"id":382,"uid":37,"type":"product","product_id":2615,"product_attr_unique":"47f93433","cart_num":1,"add_time":1564316130,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":2615,"image":"http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","slider_image":["http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","http://py.haoshusi.com/python/17559cad2468cf190a79b225401fb0f73583.jpg"],"price":"198.00","ot_price":"257.40","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"145","sales":2,"stock":1058,"store_name":"韩国ahc水乳套装 B5玻尿补水保湿清爽型两件套男女官方正品新版","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":2615,"suk":"水乳两件套","stock":1056,"sales":2,"price":"198.00","image":"http://py.haoshusi.com/python/55f6ef740825f340f99a147bd7636e733716.jpg","unique":"47f93433","cost":"0.00"}},"truePrice":198,"vip_truePrice":0,"trueStock":1056,"costPrice":"0.00"},{"id":316,"uid":37,"type":"product","product_id":749,"product_attr_unique":"f771e304","cart_num":3,"add_time":1564133027,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":749,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","slider_image":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"price":"171.00","ot_price":"222.30","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"297","sales":0,"stock":790,"store_name":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":749,"suk":"1罐装,2020年6月","stock":100,"sales":0,"price":"180.00","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"f771e304","cost":"0.00"}},"truePrice":180,"vip_truePrice":0,"trueStock":100,"costPrice":"0.00"},{"id":315,"uid":37,"type":"product","product_id":749,"product_attr_unique":"03589afd","cart_num":1,"add_time":1564133023,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":749,"image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","slider_image":["http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","http://py.haoshusi.com/python/a7bca44bf19b2f7f6690152356f20c1a1049.jpg","http://py.haoshusi.com/python/a049f4367d23c33f4625ea4cbfb827027111.jpg"],"price":"171.00","ot_price":"222.30","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"297","sales":0,"stock":790,"store_name":"澳洲爱他美Aptamil金装婴幼儿配方奶粉3段 900g 1-2岁适用（澳爱）","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":749,"suk":"1罐装,2020年9月至10月","stock":72,"sales":0,"price":"171.69","image":"http://py.haoshusi.com/python/8092c66a5a20f05085f4d245ebe78a893751.jpg","unique":"03589afd","cost":"0.00"}},"truePrice":171.69,"vip_truePrice":0,"trueStock":72,"costPrice":"0.00"},{"id":164,"uid":37,"type":"product","product_id":2905,"product_attr_unique":"6e912d15","cart_num":1,"add_time":1563867694,"is_pay":0,"is_del":0,"is_new":0,"combination_id":0,"seckill_id":0,"bargain_id":0,"admin_id":0,"productInfo":{"id":2905,"image":"http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","slider_image":["http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","http://py.haoshusi.com/py0709/56c78b8c55259203a20204dffce449f4.jpg","http://py.haoshusi.com/py0709/d6e5c9c491e93362eb4249068856c90b.jpg","http://py.haoshusi.com/py0709/41b6b77ededdd79619c6b033e89d5f15.jpg"],"price":"124.78","ot_price":"162.21","vip_price":"0.00","postage":"10.00","mer_id":0,"give_integral":"0.00","cate_id":"619","sales":0,"stock":33,"store_name":"韩国杯具熊 儿童书包 幼儿园双肩包 男女童宝宝背包卡通包包","store_info":"","unit_name":"","is_show":1,"is_del":0,"is_postage":0,"cost":"0.00","attrInfo":{"product_id":2905,"suk":"红绿色恐龙","stock":14,"sales":0,"price":"124.78","image":"http://py.haoshusi.com/py0709/8632f19580639c8cb81a7f098acadc42.jpg","unique":"6e912d15","cost":"0.00"}},"truePrice":124.78,"vip_truePrice":0,"trueStock":14,"costPrice":"0.00"}],"invalid":[]},"count":0}
                    try {
                        HashMap map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map != null) {
                            if (getDouble(map, "code") == 200) {
                                Map<String, Object> data = (Map<String, Object>) map.get("data");
                                analysisJson(data);
                            } else {
                                tost(getString(map, "msg"));
                            }
                        } else {
                            tost("解析出错！");
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
                case 2: //获取购物车商品数量
                    getGoodCount(msg.obj.toString());
                    break;
                case 3: //添加或取消收藏
                    if (flag == ConfigVariate.flagSalesIntent) {
                        tost("特价商品不支持收藏");
                    } else {
                        collectUpdata();
                    }
                    break;
            }
        }
    };

    private void getGoodCount(String json) {
        Map<String, Object> map = httpHander.jsonToMap(json);
        if (map != null) {
            ArrayList<Object> arrayList = (ArrayList<Object>) map.get("valid");
            if (arrayList != null) {//有数据
                action_button_car_number.setVisibility(View.VISIBLE);
                action_button_car_number.setText(arrayList.size() + "");
            } else {
                action_button_car_number.setVisibility(View.GONE);
            }
        } else {
            action_button_car_number.setVisibility(View.GONE);
        }
    }


    //解析
    private void analysisJson(Map<String, Object> map) {
        if (map != null) {

            action_button_collect.setEnabled(true);
            action_button_car.setEnabled(true);
            action_button_add.setEnabled(true);
            action_button_pay.setEnabled(true);

            detailsInfo = new GoodsDetailsInfo();

            Map<String, Object> storeInfoMap = (Map<String, Object>) map.get("storeInfo");
            //storeInfo
            if (storeInfoMap != null) {
                StoreInfo storeInfo = new StoreInfo();

                Map<String, String> mapString = httpHander.getStringMap(storeInfoMap, "cate_id", "image", "store_info",
                        "store_name", "unit_name", "postage", "ot_price", "price", "vip_price", "priceName", "title");
                Map<String, Integer> mapInteger = httpHander.getIntegerMap(storeInfoMap, "browse", "ficti", "give_integral",
                        "sales", "stock", "sort", "id", "product_id", "is_seckill", "store_type");
                //轮播
                ArrayList<Object> listImage = httpHander.getList(storeInfoMap, "slider_image");
                ArrayList<Object> images = httpHander.getList(storeInfoMap, "images");

                storeInfo.setId(mapInteger.get("id"));
                /**
                 * 正常商品没有productId,只有id,此时id=product_id
                 * 特价商品有id和product_id，
                 */
                if (flag == ConfigVariate.flagSalesIntent) {
                    storeInfo.setProduct_id(mapInteger.get("id"));
                } else {
                    storeInfo.setProduct_id(mapInteger.get("product_id"));
                }
                storeInfo.setCate_id(mapString.get("cate_id"));
                storeInfo.setImage(mapString.get("image"));
                storeInfo.setStore_info(mapString.get("store_info"));
                storeInfo.setTitle(mapString.get("title"));
                storeInfo.setStore_name(mapString.get("store_name"));
                storeInfo.setUnit_name(mapString.get("unit_name"));
                storeInfo.setBrowse(mapInteger.get("browse"));
                storeInfo.setFicti(mapInteger.get("ficti"));
                storeInfo.setGive_integral(mapString.get("give_integral"));
                storeInfo.setSales(mapInteger.get("sales"));
                storeInfo.setStock(mapInteger.get("stock"));
                storeInfo.setPostage(mapString.get("postage"));
                storeInfo.setOt_price(mapString.get("ot_price"));
                storeInfo.setPrice(mapString.get("price"));
                storeInfo.setSlider_image(listImage);
                storeInfo.setImages(images);
                storeInfo.setUserCollect((Boolean) storeInfoMap.get("userCollect"));
                storeInfo.setStore_type(mapInteger.get("store_type"));


                listBanner.clear();
                if (storeInfo.getSlider_image() != null && storeInfo.getSlider_image().size() > 0) {
                    for (int i = 0; i < storeInfo.getSlider_image().size(); i++) {
                        String imageUrl = storeInfo.getSlider_image().get(i) + "";
                        FragmentDataInfo fragmentDataInfo = new FragmentDataInfo();
                        fragmentDataInfo.setImageUrl(imageUrl);
                        listBanner.add(fragmentDataInfo);
                    }
                } else if (storeInfo.getImages() != null && storeInfo.getImages().size() > 0) {
                    for (int i = 0; i < storeInfo.getImages().size(); i++) {
                        String imageUrl = storeInfo.getImages().get(i) + "";
                        FragmentDataInfo fragmentDataInfo = new FragmentDataInfo();
                        fragmentDataInfo.setImageUrl(imageUrl);
                        listBanner.add(fragmentDataInfo);
                    }
                }

                detailsInfo.setStoreInfo(storeInfo);
            }
            ArrayList<Object> productAttrList = (ArrayList<Object>) map.get("productAttr");
            //productAttr
            List<ProductAttr> AttrList = new ArrayList<>();
            if (productAttrList != null) {
                for (int i = 0; i < productAttrList.size(); i++) {
                    Map<String, Object> mapProductAttr = (Map<String, Object>) productAttrList.get(i);

                    int pid = (int) httpHander.getDouble(mapProductAttr, "product_id");
                    String name = httpHander.getString(mapProductAttr, "attr_name");
                    ArrayList list = httpHander.getList(mapProductAttr, "attr_values");
                    ProductAttr productAttr = new ProductAttr(pid, name, list);
                    AttrList.add(productAttr);
                }
            }
            detailsInfo.setProductAttr(AttrList);

            Map<String, Object> productValue = httpHander.getMap(map, "productValue");
            detailsInfo.setProductValue(productValue);

            Map<String, String> string = httpHander.getStringMap(map, "priceName", "details_url", "replyChance");
            Map<String, Integer> intNum = httpHander.getIntegerMap(map, "replyCount", "mer_id");
//            detailsInfo.setMer_id();
//            detailsInfo.setNotFreight();
            detailsInfo.setDetails_url(string.get("details_url"));
            detailsInfo.setPriceName(string.get("priceName"));
            detailsInfo.setReplyCount(intNum.get("replyCount"));
            detailsInfo.setReplyChance(string.get("replyChance"));

            Map<String, Object> reply = (Map<String, Object>) map.get("reply");
            if (reply != null) {
                ReplyInfo replayInfo = new ReplyInfo(httpHander.getString(reply, "nickname"), httpHander.getString(reply, "avatar"), httpHander.getString(reply, "comment"));
                detailsInfo.setReply(replayInfo);
            } else {
                detailsInfo.setReply(null);
            }
            setData();
        }

    }

    //设置数据
    private void setData() {
        setCarousel();
        webView.loadUrl(detailsInfo.getDetails_url());
        String priceName = TextUtils.isEmpty(detailsInfo.getPriceName()) ? detailsInfo.getStoreInfo().getPrice() : detailsInfo.getPriceName();
        goodsdetailsactivity_newmoney.setText("¥ " + priceName);
        goodsdetailsactivity_originalprice.setText("¥ " + detailsInfo.getStoreInfo().getOt_price());
        goodsdetailsactivity_exempt.setText("¥ " + detailsInfo.getStoreInfo().getPostage());
        goodsdetailsactivity_monthlysales.setText("月销 " + detailsInfo.getStoreInfo().getFicti());
        if (flag == ConfigVariate.flagSalesIntent) {
            goodsdetailsactivity_intro.setText(detailsInfo.getStoreInfo().getStore_name());
        } else {
            goodsdetailsactivity_intro.setText(detailsInfo.getStoreInfo().getTitle());
        }
        isCollect = !detailsInfo.getStoreInfo().isUserCollect();
        collectUpdata();
        if (detailsInfo.getReply() == null) {
            findViewById(R.id.no_estmate_item).setVisibility(View.VISIBLE);
            findViewById(R.id.estmate_item).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.no_estmate_item)).setText("暂无评价");
        } else {
            findViewById(R.id.no_estmate_item).setVisibility(View.GONE);
            findViewById(R.id.estmate_item).setVisibility(View.VISIBLE);
            good_estimate_num.setText("(" + detailsInfo.getReplyCount() + ")");
            good_favorable_rate.setText("满意度" + detailsInfo.getReplyChance() + "%");
            user_name.setText(detailsInfo.getReply().getNickname());
            ImageUtils.loadCirclePic(this, detailsInfo.getReply().getAvatar(), userHead);
            estmate_content.setText(detailsInfo.getReply().getComment());
        }
    }

    //设置轮播数据
    private void setCarousel() {
        carousel.addFragment(getSupportFragmentManager(), listBanner, 3000, new OnclickFragmentView() {
            @Override
            public void onItemclick(int id, String url) {
                //轮播图点击操作
//                tost("点击ID;" + id);
            }
        });
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.good_estimate_layout) {  //查看评论列表
                if (flag == ConfigVariate.flagSalesIntent) {
                    IntentUtils.startIntent(detailsInfo.getStoreInfo().getId(), GoodsDetailsActivity.this, EstimateListActivity.class);
                } else {
                    IntentUtils.startIntent(detailsInfo.getStoreInfo().getId(), GoodsDetailsActivity.this, EstimateListActivity.class);
                }
            }

            if (login())
                return;
            if (v.getId() == R.id.goodsdetailsactivity_content) {//净含量
                dialogPayAndCar();
            } else if (v.getId() == R.id.action_button_kefu) {//客服
                startActivity(new Intent(GoodsDetailsActivity.this, ServerOnlineActivity.class));
            } else if (v.getId() == R.id.action_button_collect) {//收藏
                addOrCancelCollect();
            } else if (v.getId() == R.id.action_button_car) {//购物车
                IntentUtils.startIntentRepeatedly(1, GoodsDetailsActivity.this,
                        IntentUtils.getIntent(GoodsDetailsActivity.this, ConfigVariate.packShopCat));
            } else if (v.getId() == R.id.action_button_add || //加入购物车
                    v.getId() == R.id.action_button_pay) {   //立即购买
                dialogPayAndCar();
            }
        }
    };

    //未登录则先登录
    private boolean login() {
        if (!application.isLogin()) {//未登录
            if (myDialogTwoButton == null)
                myDialogTwoButton = new MyDialogTwoButton(this, "您还未登录，是否立即登录？", new DialogOnClick() {
                    @Override
                    public void operate() {
                        //未登录首先登录
                        IntentUtils.startIntentForResult(0, GoodsDetailsActivity.this, LoginActivity.class);
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
        httpHander.okHttpMapPost(this, url, map, 3);
    }

    //收藏图标修改
    private void collectUpdata() {
        if (isCollect) {  //取消收藏
            isCollect = false;
            action_button_collect_text.setText("收藏");
            action_button_collect_text.setTextColor(Color.parseColor("#0f0f0f"));
            TextViewUtils.setImage(GoodsDetailsActivity.this, action_button_collect_text, R.drawable.goods_collect, 2);
        } else {  //收藏好
            isCollect = true;
            action_button_collect_text.setText("已收藏");
            action_button_collect_text.setTextColor(Color.parseColor("#c22222"));
            TextViewUtils.setImage(GoodsDetailsActivity.this, action_button_collect_text, R.drawable.goods_collect_yes, 2);
        }
    }

    //立即购买和加入购物车对话框
    private void dialogPayAndCar() {
        if (detailsInfo != null) {
            if (dialogGoodsPayOrAddCar == null)
                dialogGoodsPayOrAddCar = new DialogGoodsPayOrAddCar(this, detailsInfo, flag);
            else
                dialogGoodsPayOrAddCar.setRefresh(detailsInfo);
            dialogGoodsPayOrAddCar.show();
        } else {
            tost("未获取到商品信息");
        }

    }

    //尺寸，颜色
    private Map<String, String> sizeColor(Map<String, String> map, List<GoodsMold> list) {
        for (int i = 0; i < list.size(); i++) {
            GoodsMold goodsMold = list.get(0);
            GoodsMold goodsMold1 = null;
            if (i + 1 < list.size())
                goodsMold1 = list.get(1);

            if (goodsMold.getMoldValues() != null && goodsMold.getMoldValues().size() > 0 &&
                    goodsMold1 != null && goodsMold1.getMoldValues() != null && goodsMold1.getMoldValues().size() > 0) {
                for (int j = 0; j < goodsMold.getMoldValues().size(); j++) {
                    for (int k = 0; k < goodsMold1.getMoldValues().size(); k++) {
                        String s = goodsMold.getMoldValues().get(j) + "," + goodsMold1.getMoldValues().get(k);
                        map.put(s, s);
                    }
                }
            } else if (goodsMold.getMoldValues() != null && goodsMold.getMoldValues().size() > 0) {
                for (int j = 0; j < goodsMold.getMoldValues().size(); j++) {
                    String s = goodsMold.getMoldValues().get(j) + "";
                    map.put(s, s);
                }

            } else if (goodsMold1 != null && goodsMold1.getMoldValues() != null && goodsMold1.getMoldValues().size() > 0) {
                for (int j = 0; j < goodsMold1.getMoldValues().size(); j++) {
                    String s = goodsMold1.getMoldValues().get(j) + "";
                    map.put(s, s);
                }
            }
            break;
        }
        return map;
    }
}
