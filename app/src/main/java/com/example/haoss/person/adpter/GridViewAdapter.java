package com.example.haoss.person.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.haoss.R;
import com.example.haoss.person.other.MainConstant;

import java.util.List;


public class GridViewAdapter extends android.widget.BaseAdapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    public void refresh(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView 
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent, false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            String picUrl = mList.get(position); //图片路径
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(mContext).load(picUrl).apply(options).into(iv);
        } else {
            iv.setImageResource(R.mipmap.bg_select_pic);//最后一个显示加号图片
        }
        return convertView;
    }
}  
