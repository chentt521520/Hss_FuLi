package com.example.haoss.shopcat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.entity.AttrInfo;
import com.example.applibrary.entity.ProductInfo;
import com.example.applibrary.entity.ShoppingCartInfo;
import com.example.applibrary.entity.StoreInfo;
import com.example.applibrary.httpUtils.OkHttpRequest;
import com.example.applibrary.httpUtils.OnHttpCallback;
import com.example.applibrary.resp.RespGoodDetail;
import com.example.applibrary.utils.SharedPreferenceUtils;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.BaseFragment;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.pay.GoodsBuyActivity;
import com.example.haoss.person.AuthenticationActivity;
import com.example.haoss.person.login.LoginActivity;
import com.example.haoss.shopcat.adapter.ShoppingCartAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.shopcat
 * blog: "好蔬食"
 */
//购物车fragment
public class ShopCatFragment extends BaseFragment {
    private Context mContext;
    private View shopCatView;   //界面
    ImageView action_title_goback;  //返回
    //完成、全选、收藏、删除
    TextView action_title_other, three_fragment_allcheck, three_fragment_collect, three_fragment_delete;
    MyListView three_fragment_listview;   //列表
    ImageView three_fragment_allchecked;    //全选图片按钮
    RelativeLayout three_fragment_operate;    //操作栏
    RelativeLayout three_fragment_shoppay;  //付款栏
    TextView three_fragment_allmoney, three_fragment_gopay;   //支付总金额、去付款按钮
    List<ShoppingCartInfo> listShoppingCartInfo;    //购物车数据
    ArrayList<ShoppingCartInfo> listQueryShopping;   //查询后要处理的商品
    ShoppingCartAdapter shoppingCartAdapter;    //购物车适配器
    boolean isOperate;  //是否允许操作
    MyDialogTwoButton myDialogTwoButton;    //对话框
    int flagOperate;    //操作标记:1：收藏，2：删除，3：支付
    int isIntentActivity;   //是否跳转独立Activity

    //未登录控件
    TextView three_fragment_login;  //登录按钮
    AppLibLication application;
    private int index;

    public ShopCatFragment() {
    }

    @SuppressLint("ValidFragment")
    public ShopCatFragment(int isIntentActivity) {
        this.isIntentActivity = isIntentActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        application = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (shopCatView == null) {
            shopCatView = LayoutInflater.from(mContext).inflate(R.layout.fragment_shop_cat_page, null);
            load(shopCatView);
        }
        addListInfo();
        return shopCatView;
    }

    @Override
    public void onResume() {
        super.onResume();
        addListInfo();
        if (shoppingCartAdapter != null) {
            shoppingCartAdapter.defaultState();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myDialogTwoButton = null;
        if (three_fragment_allchecked != null)
            three_fragment_allchecked.setImageResource(R.mipmap.checked_false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        addListInfo();
    }

    //判断界面是否可见
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        try {
            if (getUserVisibleHint()) {//界面可见时
                addListInfo();
                if (shoppingCartAdapter != null) {
                    shoppingCartAdapter.defaultState();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //控件加载
    private void load(View view) {
        ((TextView) view.findViewById(R.id.action_title_text)).setText("购物车");
        action_title_goback = view.findViewById(R.id.action_title_goback);
        if (isIntentActivity == IntentUtils.intentActivityAssign)
            action_title_goback.setVisibility(View.VISIBLE);
        else
            action_title_goback.setVisibility(View.INVISIBLE);
        action_title_other = view.findViewById(R.id.action_title_other);
        action_title_other.setText("编辑");
        three_fragment_allchecked = view.findViewById(R.id.three_fragment_allchecked);
        three_fragment_allcheck = view.findViewById(R.id.three_fragment_allcheck);
        three_fragment_operate = view.findViewById(R.id.three_fragment_operate);
        three_fragment_collect = view.findViewById(R.id.three_fragment_collect);
        three_fragment_delete = view.findViewById(R.id.three_fragment_delete);
        three_fragment_listview = view.findViewById(R.id.three_fragment_listview);
        three_fragment_shoppay = view.findViewById(R.id.three_fragment_shoppay);
        three_fragment_allmoney = view.findViewById(R.id.three_fragment_allmoney);
        three_fragment_gopay = view.findViewById(R.id.three_fragment_gopay);

        three_fragment_login = view.findViewById(R.id.three_fragment_login);

        action_title_goback.setOnClickListener(onClickListener);
        action_title_other.setOnClickListener(onClickListener);
        three_fragment_allchecked.setOnClickListener(onClickListener);
        three_fragment_allcheck.setOnClickListener(onClickListener);
        three_fragment_gopay.setOnClickListener(onClickListener);
        three_fragment_collect.setOnClickListener(onClickListener);
        three_fragment_delete.setOnClickListener(onClickListener);
        three_fragment_login.setOnClickListener(onClickListener);

        three_fragment_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int seckill_id = listShoppingCartInfo.get(position).getSeckill_id();
                if (seckill_id == 0) {//正常商品
                    int product_id = listShoppingCartInfo.get(position).getProductInfo().getId();
                    IntentUtils.startIntent(product_id, getContext(), GoodsDetailsActivity.class);
                } else {//特价商品
                    Intent intent = new Intent(getContext(), GoodsDetailsActivity.class);
                    intent.putExtra(IntentUtils.intentActivityFlag, seckill_id);
                    intent.putExtra("flag", ConfigVariate.flagSalesIntent);
                    startActivity(intent);
                }
            }
        });
    }


    //监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    ((Activity) mContext).finish();
                    break;
                case R.id.action_title_other:   //编辑和完成
                    if (listShoppingCartInfo != null && listShoppingCartInfo.size() > 0) {
                        if (isOperate) {  //编辑：允许删除和收藏
                            isOperate = false;
                            action_title_other.setText("编辑");
                            three_fragment_operate.setVisibility(View.GONE);
                            three_fragment_shoppay.setVisibility(View.VISIBLE);

                        } else {  //完成：允许支付
                            isOperate = true;
                            action_title_other.setText("完成");
                            three_fragment_shoppay.setVisibility(View.GONE);
                            three_fragment_operate.setVisibility(View.VISIBLE);
                        }
                        if (shoppingCartAdapter != null)
                            shoppingCartAdapter.defaultState();
                    } else {
                        if (isOperate) {
                            isOperate = false;
                            action_title_other.setText("编辑");
                            three_fragment_operate.setVisibility(View.GONE);
                            three_fragment_shoppay.setVisibility(View.VISIBLE);
                            if (shoppingCartAdapter != null)
                                shoppingCartAdapter.defaultState();
                        }
                        tost("请选择商品！");
                    }
                    break;
                case R.id.three_fragment_allcheck:   //全选
                case R.id.three_fragment_allchecked:   //全选
                    if (shoppingCartAdapter != null)
                        shoppingCartAdapter.setAllChecked();
                    break;
                case R.id.three_fragment_gopay:   //去付款
                    queryInfo(3, "");
                    break;
                case R.id.three_fragment_collect:   //收藏
                    queryInfo(1, "确定要将这**种商品移入收藏？");
                    break;
                case R.id.three_fragment_delete:    //删除
                    queryInfo(2, "确定要删除所选商品？");
                    break;
                case R.id.three_fragment_login:  //登录
                    intentLogin();
                    break;
            }
        }
    };

    /**
     * 查询选中的数据
     *
     * @param flag 标记：1：收藏，2：删除，3：支付
     * @param text 消息内容
     */
    private void queryInfo(int flag, String text) {
        flagOperate = flag;
        if (listQueryShopping == null)
            listQueryShopping = new ArrayList<>();
        listQueryShopping.clear();
        for (ShoppingCartInfo info : listShoppingCartInfo) {
            if (info.isCheck())
                listQueryShopping.add(info);
        }
        if (listQueryShopping.size() == 0) {
            if (flag != 3)
                tost("请选择商品！");
            return;
        }
        if (flag == 3) {
            int isRealName = (int) SharedPreferenceUtils.getPreference(getContext(), ConfigVariate.isRealName, "I");
            if (isRealName == 1) {//已认证
                goBuy();
            } else {
                getGoodType();
            }
            return;
        }
        //支付
//        if (flag == 3) {
//            goBuy();
//            return;
//        }
        //删除和收藏
        if (flag == 1)
            text = text.replace("**", listQueryShopping.size() + "");
        collectAndDelete(text);
    }

    /**
     * 删除和收藏
     *
     * @param text 消息内容
     */
    private void collectAndDelete(String text) {
        if (myDialogTwoButton == null) {
            myDialogTwoButton = new MyDialogTwoButton(mContext, text, new DialogOnClick() {
                @Override
                public void operate() {
                    if (flagOperate == 1) {
                        addCollect();
                    } else {
                        delectGoods();
                    }
                }

                @Override
                public void cancel() {

                }
            });
        } else
            myDialogTwoButton.setMsg(text);
        myDialogTwoButton.show();
    }

    //添加购物车数据
    private void addListInfo() {
        if (!application.isLogin()) {//未登录
            action_title_other.setVisibility(View.GONE);    //隐藏编辑
            shopCatView.findViewById(R.id.not_login_hint).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.shop_car_empty).setVisibility(View.GONE);
            shopCatView.findViewById(R.id.shop_car_list).setVisibility(View.GONE);
            return;
        }
        action_title_other.setVisibility(View.VISIBLE);    //隐藏编辑
        shopCatView.findViewById(R.id.not_login_hint).setVisibility(View.GONE);
        shopCatView.findViewById(R.id.shop_car_empty).setVisibility(View.VISIBLE);
        shopCatView.findViewById(R.id.shop_car_list).setVisibility(View.VISIBLE);

        String url = Netconfig.shoppingCarList;
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(mContext, url, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1: //列表获取
                    final Map<String, Object> map = jsonToMap(msg.obj.toString());
                    if (map != null)
                        analysisData(map);
                    break;
                case 2: //修改数量
                    break;
                case 3: //删除商品
                    delete(msg.obj.toString());
                    break;
                case 4: //移入收藏
                    try {
                        HashMap ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            tost("成功移入收藏");
                            delectGoods();
                        } else {
                            tost(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
                case 5:
                    getGoodDetails(msg.obj.toString(), new OnHttpCallback<RespGoodDetail.DetailsInfo>() {
                        @Override
                        public void success(RespGoodDetail.DetailsInfo result) {
                            if (result.getStoreInfo().getStore_type() != 0) {//需要认证
                                setAuth();
                                return;
                            } else {//不需要，继续搜索
                                if (index == listQueryShopping.size() - 1) {//全部搜索完，直接支付
                                    goBuy();
                                } else {
                                    index++;
                                    getGoodType();
                                }
                            }
                        }

                        @Override
                        public void error(int code, String msg) {
                            tost(code + "," + msg);
                        }
                    });

                    break;
            }
        }
    };

    //解析数据
    private void analysisData(Map<String, Object> map) {
        if (listShoppingCartInfo == null) {
            listShoppingCartInfo = new ArrayList<>();
        } else {
            listShoppingCartInfo.clear();
        }
        ArrayList<Object> arrayList = (ArrayList<Object>) map.get("valid");
        if (arrayList != null) {//有数据
            for (int i = 0; i < arrayList.size(); i++) {
                Map<String, Object> mapArray = (Map<String, Object>) arrayList.get(i);
                if (mapArray != null) {
                    int carId = (int) httpHander.getDouble(mapArray, "id");
                    int number = (int) httpHander.getDouble(mapArray, "cart_num");
                    int seckill_id = (int) httpHander.getDouble(mapArray, "seckill_id");

                    ShoppingCartInfo info = new ShoppingCartInfo();
                    /**
                     * "cart_num": 1,
                     */
                    info.setId(carId);
                    info.setCart_num(number);
                    info.setSeckill_id(seckill_id);

                    Map<String, Object> mapProductInfo = (Map<String, Object>) mapArray.get("productInfo");
                    if (mapProductInfo != null) {
                        /**
                         *              "id": 60,
                         * 				"image": "http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png",
                         * 				"slider_image": ["http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png", "http://qiniu.haoshusi.com/images/60706f317da687354540db8bc4a3d727.png", "http://qiniu.haoshusi.com/images/f0889d2ae445102ae130b1db43c6b5a9.png"],
                         * 				"price": "35.49",
                         * 				"ot_price": "46.14",
                         * 				"vip_price": "0.00",
                         * 				"postage": "10.00",
                         * 				"mer_id": 0,
                         * 				"give_integral": "0.00",
                         * 				"cate_id": "587",
                         * 				"sales": 4,
                         * 				"stock": 1996,
                         * 				"store_name": "《当日精选鲜货》埃及橙脐橙夏橙新鲜水果当季橙子果冻橙冰糖橙带箱5-10斤装",
                         * 				"store_info": "",
                         * 				"unit_name": "件",
                         * 				"is_show": 1,
                         * 				"is_del": 0,
                         * 				"is_postage": 0,
                         * 				"cost": "0.00",
                         */
                        int id = (int) httpHander.getDouble(mapProductInfo, "id");
                        Map<String, String> mapString = httpHander.getStringMap(mapProductInfo, "image", "price", "store_name");

                        ProductInfo storeInfo = new ProductInfo();
                        storeInfo.setId(id);
                        storeInfo.setImage(mapString.get("image"));
                        storeInfo.setStore_name(mapString.get("store_name"));
                        storeInfo.setPrice(mapString.get("price"));

                        Map<String, Object> mapAttrInfo = (Map<String, Object>) mapProductInfo.get("attrInfo");
                        if (mapAttrInfo != null) {
                            String image = httpHander.getString(mapAttrInfo, "image");
                            String price = httpHander.getString(mapAttrInfo, "price");
                            String suk = httpHander.getString(mapAttrInfo, "suk");

                            /**
                             * "product_id": 60,
                             * 					"suk": "5斤装",
                             * 					"stock": 996,
                             * 					"sales": 4,
                             * 					"price": "35.49",
                             * 					"image": "http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png",
                             * 					"unique": "74c726e8",
                             * 					"cost": "0.00"
                             */

                            AttrInfo productAttr = new AttrInfo();
                            productAttr.setImage(image);
                            productAttr.setPrice(price);
                            productAttr.setSuk(suk);

                            storeInfo.setAttrInfo(productAttr);
                        } else {
                            AttrInfo productAttr = new AttrInfo();
                            productAttr.setImage(mapString.get("image"));
                            productAttr.setPrice(mapString.get("price"));

                            storeInfo.setAttrInfo(productAttr);
                        }
                        info.setProductInfo(storeInfo);
                        listShoppingCartInfo.add(info);
                    }
                }
            }
            updateList();

        } else {//没有数据
            shopCatView.findViewById(R.id.shop_car_empty).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.shop_car_list).setVisibility(View.GONE);
            action_title_other.setVisibility(View.GONE);
        }

        //适配
        if (shoppingCartAdapter == null) {
            shoppingCartAdapter = new ShoppingCartAdapter(mContext, listShoppingCartInfo,
                    three_fragment_allchecked, three_fragment_allmoney, three_fragment_gopay);
            three_fragment_listview.setAdapter(shoppingCartAdapter);
        } else
            shoppingCartAdapter.notifyDataSetChanged();
    }

    private void delete(String json) {
        try {
            HashMap map = new Gson().fromJson(json, HashMap.class);
            if (map == null) {
                tost("解析出错");
                return;
            }

            if (httpHander.getDouble(map, "code") != 200) {
                tost(httpHander.getString(map, "msg"));
                return;
            }

        } catch (Exception e) {
            Log.e("ShopCatFragment", "e: " + e.getMessage());
        }

        //刷新
        listShoppingCartInfo.removeAll(listQueryShopping);
        shoppingCartAdapter.notifyDataSetChanged();
        updateList();
    }

    private void updateList() {
        if (listShoppingCartInfo.size() == 0) {//购物车为空
            shopCatView.findViewById(R.id.shop_car_empty).setVisibility(View.VISIBLE);
            shopCatView.findViewById(R.id.shop_car_list).setVisibility(View.GONE);
            action_title_other.setVisibility(View.GONE);
        } else {
            shopCatView.findViewById(R.id.shop_car_empty).setVisibility(View.GONE);
            shopCatView.findViewById(R.id.shop_car_list).setVisibility(View.VISIBLE);
            action_title_other.setVisibility(View.VISIBLE);
        }
    }

    //去结账
    private void goBuy() {

        StringBuffer cartId = new StringBuffer();
        for (ShoppingCartInfo info1 : listQueryShopping) {
            cartId.append(info1.getId()).append(",");
        }
        cartId.deleteCharAt(cartId.length() - 1);

        Intent intent = new Intent(getActivity(), GoodsBuyActivity.class);
        intent.putExtra("cartId", cartId.toString());
        intent.putExtra("flag", ConfigVariate.flagCarToPayIntent);
        startActivity(intent);

    }

    //未登录首先登录
    private void intentLogin() {
        IntentUtils.startIntentForResult(0, mContext, LoginActivity.class, null, 3);
    }

    //删除商品
    private void delectGoods() {

        String url = Netconfig.shoppingCarDelete;
        Map<String, Object> map = new HashMap<>();
        map.put("ids", getCartId());
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(mContext, url, map, 3);
    }

    //添加入收藏
    private void addCollect() {
        String url = Netconfig.addShoppingCollect;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
//        for (ShoppingCartInfo info : listQueryShopping) {
        map.put(ConfigHttpReqFields.sendProductId, getCartId());
        httpHander.okHttpMapPost(mContext, url, map, 4);
//        }
    }


    private String getCartId() {
        String ids = "";
        for (ShoppingCartInfo info : listQueryShopping) {
            ids += info.getId() + ",";
        }
        if (!TextUtils.isEmpty(ids)) {
            return ids.substring(0, ids.length() - 1);
        }
        return ids;
    }

    private void getGoodType() {

        String url;
        int goodsId;
        ShoppingCartInfo info = listQueryShopping.get(index);
        int seckill_id = info.getSeckill_id();
        if (seckill_id == 0) {//正常商品
            url = Netconfig.commodityDetails;
            goodsId = info.getProductInfo().getId();
        } else {
            url = Netconfig.seckillShopDetails;
            goodsId = info.getSeckill_id();
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("id", goodsId);
        map.put("token", application.getToken());
        httpHander.okHttpMapPost(getContext(), url, map, 5);
    }

    /**
     * 认证提示框
     */
    private void setAuth() {
        MyDialogTwoButton myDialogTwoButton = new MyDialogTwoButton(getContext(), "您所购买的商品需要实名认证，是否继续购买？", "去认证", "", new DialogOnClick() {
            @Override
            public void operate() {
                IntentUtils.startIntent(getContext(), AuthenticationActivity.class);
            }

            @Override
            public void cancel() {
            }
        });
        myDialogTwoButton.show();
    }
}
