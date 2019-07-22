package com.example.haoss.person.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoss.R;
import com.example.haoss.util.GridViewInfo;

import java.util.List;


/**
 * Created by
 */

//订单操作适配器
public class SelfGvadapter extends BaseAdapter {

    private Context context;
    private List<GridViewInfo> list;

    public SelfGvadapter(Context context, List<GridViewInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<GridViewInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (list.size() == 0) {
            return 0;
        }
        return list.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private ID id;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null || convertView.getTag() == null) {
            if (context != null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.my_grid_itme_item, null);
            }
            id = new ID();
            id.num = convertView.findViewById(R.id.gv_num);
            id.image = convertView.findViewById(R.id.gv_iv);
            id.name = convertView.findViewById(R.id.gv_tv);
            convertView.setTag(id);
        } else {
            id = (ID) convertView.getTag();
        }
        id.image.setImageResource(list.get(position).getImage());
        id.name.setText(list.get(position).getName());
        id.num.setText(list.get(position).getNum() + "");

        if (position == list.size() - 1) {
            id.num.setVisibility(View.GONE);
        } else {
            if (list.get(position).getNum() > 0) {
                id.num.setVisibility(View.VISIBLE);
            } else {
                id.num.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    class ID {
        private ImageView image;
        private TextView name;
        private TextView num;
    }

}
