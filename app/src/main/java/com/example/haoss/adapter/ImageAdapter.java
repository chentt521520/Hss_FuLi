package com.example.haoss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private List<Object> imageList;

    public ImageAdapter(Context context, String[] imageList) {
        this.context = context;
        this.imageList = new ArrayList<>();
        for (String image : imageList) {
            this.imageList.add(image);
        }
    }

    public ImageAdapter(Context context, List<Object> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList == null ? 0 : imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_image, null);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.brand_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageUtils.imageLoad(context, (String) imageList.get(position), holder.image);

        return convertView;
    }

    private class ViewHolder {
        private ImageView image;
    }
}
