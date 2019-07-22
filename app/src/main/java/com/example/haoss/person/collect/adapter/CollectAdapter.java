package com.example.haoss.person.collect.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;
import com.example.haoss.person.collect.entity.CollectionInfo;

import java.util.List;

//收藏适配器
public class CollectAdapter extends BaseAdapter {

    private Context context;
    private List<CollectionInfo> list;

    public CollectAdapter(Context context, List<CollectionInfo> list) {
        this.context = context;
        this.list = list;
    }

    //刷新
    public void setRefresh(List<CollectionInfo> list) {
        this.list = list;
        notifyDataSetChanged();
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
    public View getView(final int position, View view, ViewGroup parent) {
        Info info;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_collect, null);
            info = new Info();
            info.item_collect_image = view.findViewById(R.id.item_collect_image);
            info.item_collect_name = view.findViewById(R.id.item_collect_name);
            info.item_collect_similarity = view.findViewById(R.id.item_collect_similarity);
            info.item_collect_money = view.findViewById(R.id.item_collect_price);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        final CollectionInfo collectInfo = list.get(position);
        ImageUtils.imageLoad(context, collectInfo.getImage(), info.item_collect_image);
        info.item_collect_name.setText(collectInfo.getStore_name());

        if (collectInfo.getIs_show() == 0) {   //失效
            info.item_collect_similarity.setVisibility(View.VISIBLE);
            info.item_collect_similarity.setText("失效");
            info.item_collect_similarity.setBackgroundResource(R.mipmap.collect_lose);
            info.item_collect_money.setTextColor(Color.parseColor("#666666"));
        } else {
            info.item_collect_similarity.setVisibility(View.GONE);
            info.item_collect_money.setTextColor(Color.parseColor("#c22222"));
        }
        return view;
    }

    class Info {
        ImageView item_collect_image;    //图片
        TextView item_collect_name; //名称和规格
        TextView item_collect_similarity;   //找相似按钮
        TextView item_collect_money;    //价格或收藏人数
    }

}
