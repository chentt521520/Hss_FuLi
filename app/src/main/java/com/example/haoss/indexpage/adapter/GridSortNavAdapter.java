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

public class GridSortNavAdapter extends BaseAdapter {

    Context context;
    List<NavInfo> list;    //数据

    public GridSortNavAdapter(Context context, List<NavInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<NavInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_func, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_func_image);
            info.text = view.findViewById(R.id.item_func_text);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        NavInfo birhtDayInfo = list.get(position);
        ImageUtils.imageLoad(context, birhtDayInfo.getImageUrl(), info.image);
        info.text.setText(birhtDayInfo.getName());
        return view;
    }

    class Info {
        ImageView image;    //图片
        TextView text;  //名称
    }
}