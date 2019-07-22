package com.example.haoss.classification.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haoss.R;
import com.example.haoss.classification.entity.ClassifyInfo;

import java.util.List;

//分类父类适配器
public class AlcssifyInfoAdapter extends BaseAdapter {

    Context context;
    List<ClassifyInfo> list;    //数据
    int chooseIndex;    //选中的下标,默认第一个

    public AlcssifyInfoAdapter(Context context, List<ClassifyInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_textview_a, null);
            info = new Info();
            info.item_textviewa = view.findViewById(R.id.item_textviewa);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        info.item_textviewa.setText(list.get(position).getName());
        info.item_textviewa.setTextColor(Color.parseColor("#0f0f0f"));
        if(chooseIndex == position) //选中
            info.item_textviewa.setTextColor(Color.parseColor("#C22222"));
        return view;
    }

    //设置选中的下标
    public void setChoose(int index){
        chooseIndex = index;
    }

    class Info{
        TextView item_textviewa;
    }
}
