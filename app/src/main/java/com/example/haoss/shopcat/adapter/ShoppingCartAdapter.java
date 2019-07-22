package com.example.haoss.shopcat.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.haoss.R;
import com.example.haoss.goods.details.entity.StoreInfo;
import com.example.haoss.shopcat.entity.ShoppingCartInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

//购物车适配器
public class ShoppingCartAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingCartInfo> list;    //数据
    private ImageView allchecked;    //全选图片按钮
    private TextView allMoney, goPay;    //支付金额。去结账按钮
    private boolean isAllChecked;   //全选按钮是否处于选中状态
    private int chooseNumber;   //选中个数
    private Timer timer;    //修改定时器，每2秒修改一次
    private Map<Integer, Integer> mapNumber = new HashMap<>();   //存放数量和对应购物车id

    public ShoppingCartAdapter(Context context, List<ShoppingCartInfo> list, ImageView allchecked,
                               TextView allMoney, TextView goPay) {
        this.context = context;
        this.list = list;
        this.allchecked = allchecked;
        this.allMoney = allMoney;
        this.goPay = goPay;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Info info;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_shop_cart, null);
            info = new Info();
            info.item_shoppingcart_checked = view.findViewById(R.id.item_shoppingcart_checked);
            info.item_shoppingcart_image = view.findViewById(R.id.item_shoppingcart_image);
            info.item_shoppingcart_name = view.findViewById(R.id.item_shoppingcart_name);
            info.item_shoppingcart_money = view.findViewById(R.id.item_shoppingcart_money);
            info.item_shoppingcart_number = view.findViewById(R.id.item_shoppingcart_number);
            info.item_shoppingcart_jian = view.findViewById(R.id.item_shoppingcart_jian);
            info.item_shoppingcart_jia = view.findViewById(R.id.item_shoppingcart_jia);
            info.item_shoppingcart_suk = view.findViewById(R.id.item_shoppingcart_suk);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        ShoppingCartInfo shoppingCartInfo = list.get(position);
        StoreInfo productInfo = shoppingCartInfo.getProductInfo();
        String image;
        String price;
        String suk = "";
        if (productInfo.getGoodsType() != null) {
            image = productInfo.getGoodsType().getImage();
            price = productInfo.getGoodsType().getPrice();
            suk = productInfo.getGoodsType().getSuk();
        } else {
            image = productInfo.getImage();
            price = productInfo.getPrice();
        }
        Glide.with(context).load(image).into(info.item_shoppingcart_image);
        info.item_shoppingcart_name.setText(productInfo.getStore_name());
        info.item_shoppingcart_money.setText("¥ " + price);
        info.item_shoppingcart_number.setText(shoppingCartInfo.getCart_num() + "");
        info.item_shoppingcart_suk.setText(suk);
        if (shoppingCartInfo.isCheck())
            info.item_shoppingcart_checked.setImageResource(R.mipmap.checked_true);
        else
            info.item_shoppingcart_checked.setImageResource(R.mipmap.checked_false);
        OnClic onClic = new OnClic(position, info);
        info.item_shoppingcart_checked.setOnClickListener(onClic);
        info.item_shoppingcart_jian.setOnClickListener(onClic);
        info.item_shoppingcart_jia.setOnClickListener(onClic);
        return view;
    }

    //点击监听
    class OnClic implements View.OnClickListener {
        Info info;
        int index;

        public OnClic(int index, Info info) {
            this.index = index;
            this.info = info;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_shoppingcart_checked:    //选中
                    if (list.get(index).isCheck()) {
                        list.get(index).setCheck(false);
                        if (isAllChecked) {  //有一个不被选中则取消取消状态
                            isAllChecked = false;
                            changeState();
                        }
                        chooseNumber--;
                    } else {
                        list.get(index).setCheck(true);
                        chooseNumber++;
                        if (chooseNumber == list.size()) {   //全部选中后更改选中状态
                            isAllChecked = true;
                            changeState();
                        }
                    }
                    break;
                case R.id.item_shoppingcart_jian:   //减
                    if (list.get(index).getCart_num() == 1) {
                        Toast.makeText(context, "宝贝不能再减少了呦~", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        list.get(index).setCart_num(list.get(index).getCart_num() - 1);
                        alterGoodsN(list.get(index).getId(), list.get(index).getCart_num());
                    }
                    break;
                case R.id.item_shoppingcart_jia:    //加
                    list.get(index).setCart_num(list.get(index).getCart_num() + 1);
                    if (list.get(index).getCart_num() > 1)
                        alterGoodsN(list.get(index).getId(), list.get(index).getCart_num());
                    break;
            }
            notifyDataSetChanged();
            countMoney();
        }
    }

    //设置全选
    public void setAllChecked() {
        if (isAllChecked) {   //全取消
            isAllChecked = false;
            chooseNumber = 0;
        } else {  //全选
            isAllChecked = true;
            chooseNumber = list.size();
        }
        for (ShoppingCartInfo info : list) {
            if (isAllChecked)
                info.setCheck(true);
            else
                info.setCheck(false);
        }
        notifyDataSetChanged();
        changeState();
        countMoney();
    }

    //改变全选按钮状态
    private void changeState() {
        if (isAllChecked)
            allchecked.setImageResource(R.mipmap.checked_true);
        else
            allchecked.setImageResource(R.mipmap.checked_false);
    }

    //还原默认状态
    public void defaultState() {
        isAllChecked = true;
        setAllChecked();
    }

    //计算金额
    private void countMoney() {
        double money = 0;
        for (ShoppingCartInfo info : list) {
            if (info.isCheck()) {
                if (info.getProductInfo().getGoodsType() != null) {
                    money += info.getCart_num() * Double.parseDouble(info.getProductInfo().getGoodsType().getPrice());
                } else {
                    money += info.getCart_num() * Double.parseDouble(info.getProductInfo().getPrice());
                }
            }
        }
        BigDecimal b = new BigDecimal(money);
        double num = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        allMoney.setText("¥ " + num);
        if (money > 0)
            goPay.setBackgroundResource(R.drawable.bg_red_01);
        else
            goPay.setBackgroundResource(R.drawable.bg_hui_02);
    }

    //修改数量
    private void alterGoodsN(int carId, int number) {
        mapNumber.put(carId, number);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                alterGoodsNumber();
            }
        }, 2000);
    }

    //修改商品数量
    private void alterGoodsNumber() {
        String url = Netconfig.shoppingCarUpdataNumber;
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        if (mapNumber.size() > 0) {
            for (Map.Entry<Integer, Integer> entry : mapNumber.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();
                map.put("cartId", key);
                map.put("cartNum", value);
                httpHander.okHttpMapPost(context, url, map, 2);
            }
            mapNumber.clear();
        }
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
        }
    };

    class Info {
        //选中、商品图
        ImageView item_shoppingcart_checked, item_shoppingcart_image;
        //商品名称、金额、数量
        TextView item_shoppingcart_name, item_shoppingcart_money, item_shoppingcart_number, item_shoppingcart_suk;
        //减少、添加
        ImageView item_shoppingcart_jian, item_shoppingcart_jia;
    }
}
