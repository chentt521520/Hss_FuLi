package com.example.haoss.integralshop.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoss.R;
import com.example.haoss.integralshop.entity.FuncInfo;

import java.util.List;

//5选项适配器
public class FuncAdapter extends BaseAdapter {
    Context context;
    List<FuncInfo> list;    //选项数据

    public FuncAdapter(Context context, List<FuncInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_func_integralshop, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_func_integralshop_image);
            info.text = view.findViewById(R.id.item_func_integralshop_text);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        FuncInfo funcInfo = list.get(position);
        info.image.setImageResource(funcInfo.getImage());   //模拟用
//        ImageUtils.imageLoad(context,funcInfo.getImageUrl(),info.image,0,0);    //正式用
        info.text.setText(funcInfo.getTitle());
        return view;
    }

    class Info {
        ImageView image;
        TextView text;
    }
}
