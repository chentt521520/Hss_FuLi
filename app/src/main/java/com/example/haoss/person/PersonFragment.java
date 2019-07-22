package com.example.haoss.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.BaseFragment;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.LoginInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.R;
import com.example.haoss.conversation.ServerOnlineActivity;
import com.example.haoss.integralshop.IntegralShopActivity;
import com.example.haoss.person.address.AddresShowActivity;
import com.example.haoss.person.adpter.SelfGvadapter;
import com.example.haoss.person.aftersale.AfterSaleActivity;
import com.example.haoss.person.collect.CollectListActivity;
import com.example.haoss.person.coupon.CouponActivity;
import com.example.haoss.person.dingdan.OrderListActivity;
import com.example.haoss.person.footprint.FootprintActivity;
import com.example.haoss.person.integral.IntegralActivity;
import com.example.haoss.person.login.LoginActivity;
import com.example.haoss.person.msg.PersonMsgActivity;
import com.example.haoss.person.opinion.OpinionActivity;
import com.example.haoss.person.other.TermsOfService;
import com.example.haoss.person.setting.PersonalSettingActivity;
import com.example.haoss.person.setting.SystemSettingActivity;
import com.example.haoss.person.setting.systemsetting.PaySettingActivity;
import com.example.haoss.person.wallet.WalletActivity;
import com.example.haoss.util.GridViewInfo;
import com.example.haoss.views.MyGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.person
 * blog: "好蔬食"
 */
//个人中心fragment
public class PersonFragment extends BaseFragment {
    //消息按钮
    @BindView(R.id.person_xiaoxi_btn)
    ImageView personXiaoxiBtn;
    @BindView(R.id.person_xiaoxi_msg)
    TextView person_xiaoxi_msg; //消息数量
    //设置按钮
    @BindView(R.id.person_setting_btn)
    ImageView personSettingBtn;
    //收藏
    @BindView(R.id.person_collection_num)
    TextView personCollectionNum;
    //优惠劵
    @BindView(R.id.person_coupons_num)
    TextView personCouponsNum;
    //积分
    @BindView(R.id.person_integral_num)
    TextView personIntegralNum;
    //我的订单
    @BindView(R.id.person_chakan_dingdan)
    LinearLayout personChakanDingdan;
    //订单列表操作按钮
    @BindView(R.id.person_gv)
    MyGridView personGv;
    //我的钱包
    @BindView(R.id.person_my_money_pg)
    TextView personMyMoneyPg;
    //收货地址
    @BindView(R.id.person_my_shouhuo_address)
    TextView personMyShouhuoAddress;
    // 服务条款
    @BindView(R.id.person_my_fuwu_tiaok)
    TextView personMyFuwuTiaok;
    //意见反馈
    @BindView(R.id.person_my_yijian_fankui)
    TextView personMyYijianFankui;
    //我的客服
    @BindView(R.id.person_my_shouhuo_myservice)
    TextView myService;
    //积分商城
    @BindView(R.id.person_my_shouhuo_integralshop)
    TextView integralshop;
    Unbinder unbinder;
    private Context mContext;
    private View personView;
    //订单操作数据
    private String[] person_dingdan = {"待付款", "待发货", "待收货", "已完成", "售后"};
    private int[] person_dingd_img = {R.mipmap.person_no_payment_img, R.mipmap.person_no_delivery_img, R.mipmap.person_no_shouhuo_img,
            R.mipmap.person_no_pingjia_img, R.mipmap.person_shouhou_img};
    private List<GridViewInfo> dingdan_list = new ArrayList<>();//存放订单操作信息
    private SelfGvadapter gvadapter;//订单操作适配器

    LinearLayout person_collection_linear, person_coupons_linear, person_integral_linear, person_foot_linear;  //收藏按钮、优惠劵按钮、积分按钮、足迹
    //头像
    ImageView person_user_head;
    //名称
    TextView person_user_name;
    //个人消息修改按钮
    ImageView person_user_name_riht;
    //足迹数量
    TextView person_foot_num;
    AppLibLication instance;
    private LoginInfo info;
    private int payCount;
    private int sendCount;
    private int receiveCount;
    private int estimateCount;
    List<Integer> orderCount = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        instance = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (personView == null) {
            personView = LayoutInflater.from(mContext).inflate(R.layout.fragment_person_page, null);
            unbinder = ButterKnife.bind(this, personView);
            iniView();
        }
        return personView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getInfo();
        getFormCountByType();
    }

    //显示时刷新
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (personView != null) {
//                msgUnread();
                getInfo();
                getFormCountByType();
            }
        }
    }

    /**
     * 初始化
     */
    private void iniView() {

        personView.findViewById(R.id.action_title_goback).setVisibility(View.GONE);
        ((TextView) personView.findViewById(R.id.action_title_text)).setText("我的");
        ImageView image = personView.findViewById(R.id.action_title_add);
        image.setVisibility(View.VISIBLE);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtils.startIntent(0, mContext, SystemSettingActivity.class);    //系统设置
            }
        });
        image.setImageDrawable(getResources().getDrawable(R.mipmap.person_setting_img));

        person_collection_linear = personView.findViewById(R.id.person_collection_linear);
        person_coupons_linear = personView.findViewById(R.id.person_coupons_linear);
        person_integral_linear = personView.findViewById(R.id.person_integral_linear);
        person_foot_linear = personView.findViewById(R.id.person_foot_linear);

        person_user_head = personView.findViewById(R.id.person_user_head);
        person_user_name = personView.findViewById(R.id.person_user_name);
        person_user_name_riht = personView.findViewById(R.id.person_user_name_riht);
        person_foot_num = personView.findViewById(R.id.person_foot_num);

        personView.findViewById(R.id.person_info).setOnClickListener(onClickListener);
        person_collection_linear.setOnClickListener(onClickListener);
        person_coupons_linear.setOnClickListener(onClickListener);
        person_integral_linear.setOnClickListener(onClickListener);
        person_foot_linear.setOnClickListener(onClickListener);
        person_user_head.setOnClickListener(onClickListener);
        person_user_name.setOnClickListener(onClickListener);
        person_user_name_riht.setOnClickListener(onClickListener);

        if (dingdan_list.size() > 0)
            dingdan_list.clear();
        for (int i = 0; i < person_dingdan.length; i++) {
            GridViewInfo info = new GridViewInfo();
            info.setName(person_dingdan[i]);
            info.setImage(person_dingd_img[i]);
            dingdan_list.add(info);
        }
        gvadapter = new SelfGvadapter(mContext, dingdan_list);
        personGv.setAdapter(gvadapter);
        personGv.setOnItemClickListener(onItemClickListener);

        getInfo();
        getFormCountByType();
    }

    //请求获取
    private void getFormCountByType() {
        if (instance.isLogin()) {//已登录
            Map<String, Object> map = new HashMap<>();
            map.put("token", instance.getToken());
//        map.put("type", 0); //0 待付款 1 待发货 2 待收货 3 已收货 4 已完成
            String url = Netconfig.orderListStatistics;
            httpHander.okHttpMapPost(mContext, url, map, 3);
        }
    }

    //获取个人中心信息
    private void getInfo() {
        if (instance.isLogin()) {//已登录
            String url = Netconfig.personalCenter;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendToken, instance.getToken());
            httpHander.okHttpMapPost(mContext, url, map, 1);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!instance.isLogin()) {//w未登录
                IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
                return;
            }
            switch (v.getId()) {
                case R.id.person_collection_linear: //收藏
                    IntentUtils.startIntent(mContext, CollectListActivity.class);
                    break;
                case R.id.person_coupons_linear:    //优惠劵
                    IntentUtils.startIntent(mContext, CouponActivity.class);
                    break;
                case R.id.person_integral_linear:   //积分
                    String integral = info.getIntegral();
                    IntentUtils.startIntent(mContext, IntegralActivity.class, integral);
                    break;
                case R.id.person_foot_linear:   //足迹
                    IntentUtils.startIntent(mContext, FootprintActivity.class);
                    break;
                case R.id.person_user_head:   //头像
                case R.id.person_user_name:   //头像
                case R.id.person_user_name_riht:   //头像
                    IntentUtils.startIntent(mContext, PersonalSettingActivity.class);    //进入个人设置
                    break;
            }
        }
    };

    //钱包、地址、条款、意见、设置、订单按钮监听
    @OnClick({R.id.person_my_money_pg, R.id.person_my_shouhuo_address, R.id.person_my_fuwu_tiaok, R.id.person_my_yijian_fankui,
            R.id.person_setting_btn, R.id.person_chakan_dingdan, R.id.person_xiaoxi_btn, R.id.person_xiaoxi_msg, R.id.person_my_shouhuo_myservice, R.id.person_my_shouhuo_integralshop})
    public void onViewClicked(View view) {
        if (!instance.isLogin()) {//w未登录
            IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
            return;
        }
        switch (view.getId()) {
            case R.id.person_xiaoxi_btn://消息按钮
            case R.id.person_xiaoxi_msg://消息数量
                IntentUtils.startIntent(mContext, PersonMsgActivity.class);
                break;
            case R.id.person_setting_btn://设置
                IntentUtils.startIntent(0, mContext, SystemSettingActivity.class);    //系统设置
                break;
            case R.id.person_chakan_dingdan://我的全部订单
                IntentUtils.startIntent(-1, mContext, OrderListActivity.class);
                break;
            case R.id.person_my_money_pg://钱包
                IntentUtils.startIntent(mContext, WalletActivity.class, info.getNow_money());
                break;
            case R.id.person_my_shouhuo_address://地址
                IntentUtils.startIntent(1, mContext, AddresShowActivity.class);
                break;
            case R.id.person_my_fuwu_tiaok://条款
                IntentUtils.startIntent(mContext, TermsOfService.class);
                break;
            case R.id.person_my_yijian_fankui://反馈
                IntentUtils.startIntent(mContext, OpinionActivity.class);
                break;
            case R.id.person_my_shouhuo_myservice:   //我的客服
                startActivity(new Intent(getContext(), ServerOnlineActivity.class));
                break;
            case R.id.person_my_shouhuo_integralshop:   //积分商城
                IntentUtils.startIntent(mContext, IntegralShopActivity.class);
                break;
        }
    }

    //订单项点击监听
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!instance.isLogin()) {//w未登录
                IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 4);
                return;
            }
            switch (position) {
                case 0: //待付款
                    IntentUtils.startIntent(0, mContext, OrderListActivity.class);
                    break;
                case 1: //待发货
                    IntentUtils.startIntent(1, mContext, OrderListActivity.class);
                    break;
                case 2: //待收货
                    IntentUtils.startIntent(2, mContext, OrderListActivity.class);
                    break;
                case 3: //已完成
                    IntentUtils.startIntent(3, mContext, OrderListActivity.class);
                    break;
                case 4: //售后
                    IntentUtils.startIntent(mContext, AfterSaleActivity.class);
                    break;
            }
        }
    };

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    Map<String, Object> map = jsonToMap(msg.obj.toString());
                    if (map != null) {//nickname、avatar、now_money、integral、status、level、like、couponCount
                        info = new LoginInfo();
                        info.setNickname(getString(map, "nickname"));
                        info.setAvatar(getString(map, "avatar"));
                        info.setNow_money(getString(map, "now_money"));
                        info.setIntegral(getString(map, "integral"));
                        info.setStatus((int) getDouble(map, "status"));
                        info.setLevel((int) getDouble(map, "level"));
                        info.setLike((int) getDouble(map, "like"));
                        info.setCouponCount((int) getDouble(map, "couponCount"));
                        info.setSign_num((int) getDouble(map, "sign_num"));
                        info.setFootprintCount((int) getDouble(map, "footprintCount"));
                        info.setIs_birthday((int) getDouble(map, "is_birthday"));

                        setLoginData();
                    }
                    break;
                case 2:
                    Map<String, Object> mapMsg = jsontoMap(msg.obj.toString());
                    if (mapMsg != null) {
                        if (getDouble(mapMsg, "code") == 200) {
                            person_xiaoxi_msg.setText((int) getDouble(mapMsg, "data") + "");
                        }
                    }
                    break;
                case 3:
                    updateUI(msg.obj.toString());
                    break;

                case 4:
                    try {
                        Map<String, Object> ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret == null) {
                            tost("解析出错！");
                        } else if (getDouble(ret, "code") == 200) {
                            Map<String, Object> data = getMap(ret, "data");
                            boolean is_pass = (boolean) data.get("is_pass");
                            SharedPreferenceUtils.setPreference(getContext(), "PASSWORD", is_pass, "B");
                        } else {
                            tost(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }
        }
    };

    private void updateUI(String json) {
        Map map = httpHander.jsonToMap(json);
        if (map != null) {
            payCount = (int) httpHander.getDouble(map, "payment_count");
            sendCount = (int) httpHander.getDouble(map, "unpaid_count");
            receiveCount = (int) httpHander.getDouble(map, "unshipped_count");
            estimateCount = (int) httpHander.getDouble(map, "complete_count");
        }

        orderCount.clear();
        orderCount.add(payCount);
        orderCount.add(sendCount);
        orderCount.add(receiveCount);
        orderCount.add(estimateCount);
        orderCount.add(0);

        dingdan_list.clear();
        for (int i = 0; i < person_dingdan.length; i++) {
            GridViewInfo info = new GridViewInfo();
            info.setName(person_dingdan[i]);
            info.setImage(person_dingd_img[i]);
            info.setNum(orderCount.get(i));
            dingdan_list.add(info);
        }

        gvadapter.refresh(dingdan_list);
    }

    //设置登录后数据
    private void setLoginData() {
        SharedPreferenceUtils.setPreference(getContext(), ConfigVariate.goldCoin, info.getIntegral(), "S");

        person_foot_num.setText(info.getFootprintCount() + "");
        personCollectionNum.setText(info.getLike() + "");
        personCouponsNum.setText(info.getCouponCount() + "");
        personIntegralNum.setText(info.getIntegral() + "");
        ImageUtils.loadCirclePic(mContext, info.getAvatar(), person_user_head);

        person_user_name.setText(info.getNickname());
        msgUnread();
    }

    //未读信息
    private void msgUnread() {
        if (instance.isLogin()) {
            String url = Netconfig.unreadMsg;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendToken, instance.getToken());
            httpHander.okHttpMapPost(mContext, url, map, 2);
        }
    }

    /**
     * 检测是否有设置过密码
     */
    private void CheckPwd() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", instance.getToken());
        httpHander.okHttpMapPost(getContext(), Netconfig.checkYuE, map, 3);
    }
}
