package com.example.haoss.pay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//商品购买-订单确认页面-商品信息适配器
public class GoodsBuyAdapter extends BaseAdapter {

    private Context context;
    private List<GoodsBuyInfo> list;

    public GoodsBuyAdapter(Context context, List<GoodsBuyInfo> list) {
        this.context = context;
        this.list = list;
    }

    //刷新
    public void setRefresh(List<GoodsBuyInfo> list) {
        this.list = list;
        notifyDataSetChanged();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_goods_buy, null);
            info = new Info();
            info.item_goodsbuyadapter_image = view.findViewById(R.id.item_goodsbuyadapter_image);
            info.item_goodsbuyadapter_money = view.findViewById(R.id.item_goodsbuyadapter_money);
            info.item_goodsbuyadapter_number = view.findViewById(R.id.item_goodsbuyadapter_number);
            info.item_goodsbuyadapter_name = view.findViewById(R.id.item_goodsbuyadapter_name);
            info.item_goodsbuyadapter_type = view.findViewById(R.id.item_goodsbuyadapter_type);
            view.setTag(info);
        } else {
            info = (Info) view.getTag();
        }
        GoodsBuyInfo goodsBuyInfo = list.get(position);
        ImageUtils.imageLoad(context, goodsBuyInfo.getImage(), info.item_goodsbuyadapter_image, 0, 0);
        info.item_goodsbuyadapter_money.setText("¥ " + goodsBuyInfo.getMoney());
        info.item_goodsbuyadapter_number.setText("x " + goodsBuyInfo.getNumber());
        info.item_goodsbuyadapter_name.setText(goodsBuyInfo.getName());
        info.item_goodsbuyadapter_type.setText(goodsBuyInfo.getType());
        return view;
    }

    class Info {
        ImageView item_goodsbuyadapter_image;   //图片
        TextView item_goodsbuyadapter_money;    //金额
        TextView item_goodsbuyadapter_number;   //数量
        TextView item_goodsbuyadapter_name; //名称
        TextView item_goodsbuyadapter_type; //样式
    }
}
