package com.example.haoss.person.dingdan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.goods.details.entity.GoodsType;
import com.example.haoss.goods.details.entity.StoreInfo;
import com.example.haoss.helper.DialogHelp;
import com.example.haoss.pay.GoodsPayActivity;
import com.example.haoss.person.dingdan.adapter.ListOrderFormAdapter;
import com.example.haoss.person.dingdan.entity.OrderListInfo;
import com.example.haoss.person.dingdan.entity.OrderStatus;
import com.example.haoss.shopcat.entity.ShoppingCartInfo;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListActivity extends BaseActivity {

    TextView buttonAll, buttonStayPay, buttonStaySend, buttonStayRecv, buttonFinish, buttonCancel;
    TextView lineAll, lineStayPay, lineStaySend, lineStayRecv, lineFinish;  //lineCancel
    private RefreshLayout refreshLayout;
    private ListView listView;
    private ListOrderFormAdapter adapter;
    private int listIndex = -1;

    private List<OrderListInfo> orderList;
    private HashMap<String, List<OrderListInfo>> orderMap;
    private int page = 1;
    int pos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_my_order);

        initView();
        getIntentData();
        initEvent();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        listIndex = intent.getIntExtra(IntentUtils.intentActivityFlag, -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setFragmentItem();
        getOrderList();
    }

    private void initView() {
        this.getTitleView().setTitleText("我的订单");

        //全部
        buttonAll = findViewById(R.id.action_textbutton_all);
        lineAll = findViewById(R.id.action_line_all);
        //待付款
        buttonStayPay = findViewById(R.id.action_textbutton_staypay);
        lineStayPay = findViewById(R.id.action_line_staypay);
        //待发货
        buttonStaySend = findViewById(R.id.action_textbutton_staysend);
        lineStaySend = findViewById(R.id.action_line_staysend);
        //待收货
        buttonStayRecv = findViewById(R.id.action_textbutton_stayrecv);
        lineStayRecv = findViewById(R.id.action_line_stayrecv);
        //已完成
        buttonFinish = findViewById(R.id.action_textbutton_finish);
        lineFinish = findViewById(R.id.action_line_finish);
        //监听
        buttonAll.setOnClickListener(onClickListener);
        buttonStayPay.setOnClickListener(onClickListener);
        buttonStaySend.setOnClickListener(onClickListener);
        buttonStayRecv.setOnClickListener(onClickListener);
        buttonFinish.setOnClickListener(onClickListener);
        listView = findViewById(R.id.list_view);
        refreshLayout = findViewById(R.id.refresh_layout);
    }

    /**
     * 对应界面和样式切换
     */
    private void setFragmentItem() {
        int redColor = Color.parseColor("#c22222"); //红色
        int textColor = Color.parseColor("#666666");    //灰色
        int lineColor = Color.parseColor("#ffffff");    //白色
        //全部
        buttonAll.setTextColor(listIndex == -1 ? redColor : textColor);
        lineAll.setBackgroundColor(listIndex == -1 ? redColor : lineColor);
        //待付款
        buttonStayPay.setTextColor(listIndex == 0 ? redColor : textColor);
        lineStayPay.setBackgroundColor(listIndex == 0 ? redColor : lineColor);
        //待发货
        buttonStaySend.setTextColor(listIndex == 1 ? redColor : textColor);
        lineStaySend.setBackgroundColor(listIndex == 1 ? redColor : lineColor);
        //待收货
        buttonStayRecv.setTextColor(listIndex == 2 ? redColor : textColor);
        lineStayRecv.setBackgroundColor(listIndex == 2 ? redColor : lineColor);
        //已完成
        buttonFinish.setTextColor(listIndex == 3 ? redColor : textColor);
        lineFinish.setBackgroundColor(listIndex == 3 ? redColor : lineColor);
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    return;
                case R.id.action_textbutton_all:  //全部订单
                    listIndex = -1;
                    break;
                case R.id.action_textbutton_staypay:  //待付款
                    listIndex = 0;
                    break;
                case R.id.action_textbutton_staysend:  //待发货
                    listIndex = 1;
                    break;
                case R.id.action_textbutton_stayrecv:  //待收货
                    listIndex = 2;
                    break;
                case R.id.action_textbutton_finish:  //已完成
                    listIndex = 3;
                    break;
            }
            setFragmentItem();
            getOrderList();
        }
    };

    private void initEvent() {
        orderList = new ArrayList<>();
        orderMap = new HashMap<>();
        adapter = new ListOrderFormAdapter(OrderListActivity.this, orderList);
        listView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                page = 1;
                getOrderList();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                page++;
                getOrderList();
            }
        });

        adapter.setListener(new ListOrderFormAdapter.OnItemClickListener() {
            @Override
            public void setView(int i) {
                pos = i;
                Intent intent = new Intent(OrderListActivity.this, MyOrderDetails.class);
                String orderId = orderList.get(i).getOrder_id();
                intent.putExtra("orderId", orderId);
                startActivity(intent);
            }

            @Override
            public void setLeftBtn(int i) {
                pos = i;
                String orderId = orderList.get(i).getOrder_id();
                switch (listIndex) {
                    case -1:
                        int statusOrder = orderList.get(i).get_status().get_type();
                        if (statusOrder == 0) {//取消订单
                            handleOrder(Netconfig.orderCancel, orderId, 3);
                        } else if (statusOrder == 1 || statusOrder == 2) {//查看物流
                            tost("查看物流");
                        } else if (statusOrder == 3) {//删除订单
                            handleOrder(Netconfig.orderDelete, orderId, 2);
                        }
                        break;

                    case 0://取消订单
                        handleOrder(Netconfig.orderCancel, orderId, 3);
                        break;

                    case 1://查看物流
                        tost("查看物流");
                        break;

                    case 2://查看物流
                        tost("查看物流");
                        break;

                    case 3://删除订单
                        handleOrder(Netconfig.orderDelete, orderId, 2);
                        break;
                }
            }

            @Override
            public void setRightBtn(int i) {
                pos = i;
                String orderId = orderList.get(i).getOrder_id();
                switch (listIndex) {
                    case -1:
                        int statusOrder = orderList.get(i).get_status().get_type();
                        if (statusOrder == 0) {//待付款
                            toPayOrder();
                        } else if (statusOrder == 1) {//催单
                            tost("提醒成功");
                        } else if (statusOrder == 2) {//确认收货
                            handleOrder(Netconfig.orderConfirmReceipt, orderId, 1);
                        } else if (statusOrder == 3) {//评价
                            if (orderList.get(i).get_status().get_type() == 4) {
                                toPrise();
                            }
                        }
                        break;

                    case 0://待付款
                        toPayOrder();
                        break;

                    case 1://催单
                        tost("提醒成功");
                        break;

                    case 2://确认收货
                        handleOrder(Netconfig.orderConfirmReceipt, orderId, 1);
                        break;

                    case 3://评价
                        if (orderList.get(i).get_status().get_type() == 4) {
                            toPrise();
                        }
                        break;
                }
            }
        });
    }

    private void toPayOrder() {
        Intent intent = new Intent(OrderListActivity.this, GoodsPayActivity.class);
        intent.putExtra("orderId", orderList.get(pos).getOrder_id());
        intent.putExtra("payType", orderList.get(pos).getPay_type());
        intent.putExtra("price", orderList.get(pos).getPay_price());
        startActivityForResult(intent, IntentUtils.intentActivityRequestCode);
    }

    private void toPrise() {
        String image = orderList.get(pos).getCartInfo().get(0).getProductInfo().getGoodsType().getImage();
        String unique = orderList.get(pos).getCartInfo().get(0).getUnique();
        IntentUtils.startIntent(OrderListActivity.this, EstimatePublishActivity.class, "goodsImage", image, "unique", unique);
    }

    private void getOrderList() {
        DialogHelp.showLoading(OrderListActivity.this);
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        if (listIndex != -1)
            map.put("type", listIndex); //0 待付款 1 待发货 2 待收货 3 已完成
        map.put("page", page);
        map.put("limit", 20);
        String url = Netconfig.orderList;
        httpHander.okHttpMapPost(OrderListActivity.this, url, map, 0);
    }

    /**
     * 确认、删除、取消订单
     *
     * @param url
     * @param order_id orderId
     * @param flag     1：确认，2：删除，3：取消
     */
    private void handleOrder(String url, String order_id, int flag) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        if (flag == 3) {
            map.put("order_id", order_id);
        } else {
            map.put("uni", order_id);

        }
        httpHander.okHttpMapPost(OrderListActivity.this, url, map, flag);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 0: //订单列表
                    analysisJson(msg.obj.toString());
                    break;
                case 1: //确认收货
                case 2://删除订单
                case 3://取消订单
                    handleOrder(msg.obj.toString(), msg.arg1);
                    break;
            }
        }
    };

    private void handleOrder(String json, int flag) {
        try {
            Map<String, Object> map = new Gson().fromJson(json, HashMap.class);
            if (map == null) {
                tost("解析出错");
            } else if (map.containsKey("code") && httpHander.getDouble(map, "code") == 200) {
                orderList.remove(pos);
                adapter.refresh(orderList);
                if (flag == 2) {
                    tost("已取消");
                } else if (flag == 3) {
                    tost("已删除");
                }
            } else {
                tost(httpHander.getString(map, "msg"));
            }
        } catch (Exception e) {
            tost(e.getMessage());
        }
    }

    //解析数据
    private void analysisJson(String json) {

        DialogHelp.hideLoading();

        refreshLayout.finishRefreshing();
        refreshLayout.finishLoadmore();

        ArrayList<Object> arrayList = httpHander.jsonToList(json);
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }

        if (page == 1) orderList.clear();

        for (int i = 0; i < arrayList.size(); i++) {
            Map<String, Object> map = (Map<String, Object>) arrayList.get(i);
            OrderListInfo orderListInfo = new OrderListInfo();

            Map<String, Integer> integerMap = httpHander.getIntegerMap(map, "seckill_id", "bargain_id", "combination_id", "id",
                    "total_num", "paid", "status", "refund_status", "pink_id");
            Map<String, Long> longMap = httpHander.getLongMap(map, "add_time");
            Map<String, String> stringMap = httpHander.getStringMap(map, "order_id", "pay_price", "total_price",
                    "pay_postage", "total_postage", "pay_type", "coupon_price", "deduction_price", "_pay_time",
                    "_add_time", "status_pic");
            orderListInfo.setAdd_time(longMap.get("add_time"));
            orderListInfo.setSeckill_id(integerMap.get("seckill_id"));
            orderListInfo.setBargain_id(integerMap.get("bargain_id"));
            orderListInfo.setCombination_id(integerMap.get("combination_id"));
            orderListInfo.setId(integerMap.get("id"));
            orderListInfo.setOrder_id(stringMap.get("order_id"));//订单id
            orderListInfo.setPay_price(stringMap.get("pay_price"));//订单支付金额
            orderListInfo.setTotal_num(integerMap.get("total_num"));//订单总数量
            orderListInfo.setTotal_price(stringMap.get("total_price"));//订单总金额(不包含运费)
            orderListInfo.setPay_postage(stringMap.get("pay_postage"));//订单运费
            orderListInfo.setTotal_postage(stringMap.get("total_postage"));//订单总运费
            orderListInfo.setPaid(integerMap.get("paid"));
            orderListInfo.setStatus(integerMap.get("status"));//订单状态
            orderListInfo.setRefund_status(integerMap.get("refund_status"));//订单退款状态
            orderListInfo.setPay_type(stringMap.get("pay_type"));//订单支付方式
            orderListInfo.setCoupon_price(stringMap.get("coupon_price"));//订单优惠劵金额
//            orderListInfo.setDeduction_price(stringMap.get("deduction_price"));//订单减去金额
            orderListInfo.setPink_id(integerMap.get("pink_id"));
            orderListInfo.set_pay_time(stringMap.get("_pay_time"));//订单支付时间
            orderListInfo.set_add_time(stringMap.get("_add_time"));//订单添加时间
//            orderListInfo.setStatus_pic(stringMap.get("status_pic"));

            Map<String, Object> map_status = (Map<String, Object>) map.get("_status");
            if (map_status != null) {
                OrderStatus statusBean = new OrderStatus();
                /**
                 * "_type": 1,
                 * 			"_title": "未发货",
                 * 			"_msg": "商家未发货,请耐心等待",
                 * 			"_class": "state-nfh",
                 * 			"_payType": "余额支付"
                 */
                statusBean.set_type((int) httpHander.getDouble(map_status, "_type"));
                statusBean.set_class(httpHander.getString(map_status, "_class"));
                statusBean.set_msg(httpHander.getString(map_status, "_msg"));
                statusBean.set_payType(httpHander.getString(map_status, "_payType"));
                statusBean.set_title(httpHander.getString(map_status, "_title"));
                orderListInfo.set_status(statusBean);
            } else {
                orderListInfo.set_status(null);
            }

            //商品信息
            ArrayList<Object> listCartInfo = (ArrayList<Object>) map.get("cartInfo");
            List<ShoppingCartInfo> cartInfoList = new ArrayList<>();
            if (listCartInfo != null && !listCartInfo.isEmpty()) {
                for (int j = 0; j < listCartInfo.size(); j++) {
                    Map<String, Object> mapGoods = (Map<String, Object>) listCartInfo.get(j);

                    Map<String, Double> doubleMap1 = httpHander.getDoubleMap(mapGoods, "truePrice", "vip_truePrice");
                    Map<String, Integer> integerMap1 = httpHander.getIntegerMap(mapGoods, "seckill_id", "bargain_id", "combination_id", "id",
                            "uid", "product_id", "admin_id", "cart_num", "is_pay", "is_del", "is_new", "trueStock", "is_reply");
                    Map<String, Long> longMap1 = httpHander.getLongMap(mapGoods, "add_time");
                    Map<String, String> stringMap1 = httpHander.getStringMap(mapGoods, "type", "product_attr_unique",
                            "costPrice", "unique");
                    ShoppingCartInfo cartInfo = new ShoppingCartInfo();
                    cartInfo.setId(integerMap1.get("id"));
                    cartInfo.setProduct_id(integerMap1.get("product_id"));
                    cartInfo.setProduct_attr_unique(stringMap1.get("product_attr_unique"));
                    cartInfo.setCart_num(integerMap1.get("cart_num"));
                    cartInfo.setCombination_id(integerMap1.get("combination_id"));
                    cartInfo.setSeckill_id(integerMap1.get("seckill_id"));
                    cartInfo.setBargain_id(integerMap1.get("bargain_id"));
                    cartInfo.setUnique(stringMap1.get("unique"));

                    Map<String, Object> mapProductInfo = (Map<String, Object>) mapGoods.get("productInfo");
                    if (mapProductInfo != null) {
                        StoreInfo productInfo = new StoreInfo();
                        productInfo.setId((int) httpHander.getDouble(mapProductInfo, "id"));
                        productInfo.setStore_name(httpHander.getString(mapProductInfo, "store_name"));

                        Map<String, Object> mapGoodType = (Map<String, Object>) mapProductInfo.get("attrInfo");
                        if (mapGoodType != null) {
                            GoodsType goodType = new GoodsType();
                            goodType.setProduct_id((int) httpHander.getDouble(mapGoodType, "product_id"));
                            goodType.setSuk(httpHander.getString(mapGoodType, "suk"));
                            goodType.setPrice(httpHander.getString(mapGoodType, "price"));
                            goodType.setImage(httpHander.getString(mapGoodType, "image"));
                            productInfo.setGoodsType(goodType);
                        } else {
                            GoodsType goodType = new GoodsType();
                            goodType.setProduct_id((int) httpHander.getDouble(mapProductInfo, "id"));
                            goodType.setPrice(httpHander.getString(mapProductInfo, "price"));
                            goodType.setImage(httpHander.getString(mapProductInfo, "image"));
                            productInfo.setGoodsType(goodType);
                        }
                        cartInfo.setProductInfo(productInfo);
                    }

                    cartInfoList.add(cartInfo);
                }
                orderListInfo.setCartInfo(cartInfoList);
            }
            orderList.add(orderListInfo);
        }
        orderMap.put(listIndex + "", orderList);
        adapter.refresh(orderList);
    }
}
