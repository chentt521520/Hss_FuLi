package com.example.haoss.classification.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.haoss.R;
import com.example.haoss.classification.entity.ClassifyItenInfo;

import java.util.List;

//分类子数据适配器
public class AlcssifyItemAdapter extends BaseAdapter {

    Context context;
    List<ClassifyItenInfo> list;    //数据

    public AlcssifyItemAdapter(Context context, List<ClassifyItenInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list!=null)
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
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_brand, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_brand_image);
            info.text = view.findViewById(R.id.item_brand_text);
//            info.buttn = view.findViewById(R.id.item_brand_button);
            info.item_brand_linear = view.findViewById(R.id.item_brand_linear);
            info.item_brand_linear.setBackgroundColor(Color.parseColor("#ffffff"));
            view.setTag(info);
        }
        info = (Info) view.getTag();
        ClassifyItenInfo itenInfo = list.get(position);
        Glide.with(context).load(itenInfo.getPic()).into(info.image);
        info.text.setText(itenInfo.getName());
        return view;
    }

    //设置数据
    public void setData(List<ClassifyItenInfo> list){
        this.list = list;
        notifyDataSetChanged();
    }

    class Info{
        ImageView image;    //图片
        TextView text;  //文字
        TextView buttn; //按钮
        LinearLayout item_brand_linear; //
    }
}
