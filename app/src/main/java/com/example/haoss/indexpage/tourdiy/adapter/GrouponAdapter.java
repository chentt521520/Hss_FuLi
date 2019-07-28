package com.example.haoss.indexpage.tourdiy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.entity.GrouponListInfo;
import com.example.applibrary.resp.RespGrouponList;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;

import java.util.List;

//拼团列表适配器
public class GrouponAdapter extends BaseAdapter {

    private Context context;
    private List<RespGrouponList.GrouponList> list;

    public GrouponAdapter(Context context, List<RespGrouponList.GrouponList> list) {
        this.context = context;
        this.list = list;
    }

    public void freshList(List<RespGrouponList.GrouponList> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_groupon, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_tourdiy_image);
            info.goodName = view.findViewById(R.id.item_tourdiy_name);
            info.groupPrice = view.findViewById(R.id.item_tourdiy_cost);
            info.originalPrice = view.findViewById(R.id.item_tourdiy_original_cost);
            view.setTag(info);
        }
        info = (Info) view.getTag();

        //设置数据
        RespGrouponList.GrouponList grouponListInfo = list.get(position);
        Glide.with(context)
                .load(grouponListInfo.getImage())
                .into(info.image);

        info.goodName.setText(grouponListInfo.getTitle());
        info.groupPrice.setText("¥ " + grouponListInfo.getPrice());
        info.originalPrice.setText("¥ " + grouponListInfo.getProduct_price());
        TextViewUtils.setTextAddLine(info.originalPrice);
        return view;
    }

    class Info {
        private ImageView image;
        private TextView goodName;
        private TextView groupPrice;
        private TextView originalPrice;
    }
}
