package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;
import com.example.haoss.indexpage.entity.NavInfo;

import java.util.List;

//年节礼包钜惠礼包适配器
public class FestivalGiftBagAdapter extends BaseAdapter {

    private Context context;
    private List<NavInfo> list;    //数据

    public FestivalGiftBagAdapter(Context context, List<NavInfo> list) {
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_gift_car_item, null);
            info = new Info();
            info.good_icon = view.findViewById(R.id.item_gift_package_icon);
            info.good_name = view.findViewById(R.id.item_gift_package_descript);
            info.good_price = view.findViewById(R.id.item_gift_package_price);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        NavInfo birhtDayInfo = list.get(position);
        ImageUtils.imageLoad(context, birhtDayInfo.getImageUrl(), info.good_icon);
        info.good_name.setText(birhtDayInfo.getName());
        info.good_price.setText("¥ " + birhtDayInfo.getMoney() + " ");
        return view;
    }

    class Info {
        ImageView good_icon;    //图片
        TextView good_name;  //名称
        TextView good_price;  //金额
    }
}