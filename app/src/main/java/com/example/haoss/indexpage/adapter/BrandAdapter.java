package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.applibrary.utils.DensityUtil;
import com.example.haoss.R;
import com.example.haoss.indexpage.entity.BannerInfo;

import java.util.List;

//品牌精品适配器
public class BrandAdapter extends BaseAdapter {
    Context context;
    List<BannerInfo> list;   //精品数据

    public BrandAdapter(Context context, List<BannerInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_image, null);
            int width = (DensityUtil.getScreenWidth(context) - DensityUtil.dip2px(context, 60)) / 3;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width, width);
            view.setLayoutParams(params);
            info = new Info();
            info.image = view.findViewById(R.id.item_brand_image);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        BannerInfo brandInfo = list.get(position);
        Glide.with(context).load(brandInfo.getImgUrl()).into(info.image);
        return view;
    }

    class Info {
        ImageView image;    //图片
    }
}
