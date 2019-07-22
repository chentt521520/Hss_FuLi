package com.example.haoss.integralshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;
import com.example.haoss.integralshop.entity.IntegralInfo;

import java.util.List;

//积分商城--猜您喜欢列表适配器
public class IntegralSListAdapter extends BaseAdapter {

    Context context;
    List<IntegralInfo> list;

    public IntegralSListAdapter(Context context, List<IntegralInfo> list) {
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
        if (view == null) {  //暂时放一张图片，
            view = LayoutInflater.from(context).inflate(R.layout.item_integralslist, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_integralslist_image);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        IntegralInfo integralInfo = list.get(position);
        ImageUtils.imageLoad(context, integralInfo.getImage(), info.image, 0, 0);
        return view;
    }

    class Info {
        ImageView image;    //图片
    }
}
