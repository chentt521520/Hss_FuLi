package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoss.R;
import com.example.haoss.indexpage.entity.FuncInfo;
import com.example.haoss.indexpage.entity.IndexInfo;

import java.util.List;

//5选项适配器
public class FuncAdapter extends BaseAdapter {
    private Context context;
    private List<IndexInfo> list;    //选项数据

    public FuncAdapter(Context context, List<IndexInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_func, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_func_image);
            info.text = view.findViewById(R.id.item_func_text);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        IndexInfo funcInfo = list.get(position);
        Glide.with(context).load(funcInfo.getPic()).into(info.image);
        info.text.setText(funcInfo.getCate_name());
        return view;
    }

    class Info {
        ImageView image;
        TextView text;
    }
}
