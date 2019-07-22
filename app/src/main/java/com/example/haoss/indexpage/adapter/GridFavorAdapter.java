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
import com.example.haoss.indexpage.entity.Recommond;

import java.util.List;

public class GridFavorAdapter extends BaseAdapter {

    private Context context;
    private List<Recommond> list;    //数据

    public GridFavorAdapter(Context context, List<Recommond> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<Recommond> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_like, null);
            info = new Info();
            info.shopping_style1_image = view.findViewById(R.id.item_like_image);
            info.shopping_style1_name = view.findViewById(R.id.item_like_title);
            info.shopping_style1_money = view.findViewById(R.id.item_like_money);
            info.shopping_style1_number = view.findViewById(R.id.item_like_hint);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        Recommond birhtDayInfo = list.get(position);
        ImageUtils.imageLoad(context, birhtDayInfo.getImage(), info.shopping_style1_image);
        info.shopping_style1_name.setText(birhtDayInfo.getStore_name());
        info.shopping_style1_money.setText("¥ " + birhtDayInfo.getPrice());
        info.shopping_style1_number.setText(birhtDayInfo.getFicti() + " 人已付款");
        return view;
    }

    class Info {
        ImageView shopping_style1_image;    //图片
        TextView shopping_style1_name;  //名称
        TextView shopping_style1_money, shopping_style1_number;  //金额，人数
    }
}
