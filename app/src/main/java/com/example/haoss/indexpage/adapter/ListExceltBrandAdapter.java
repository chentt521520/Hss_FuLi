package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.indexpage.entity.GoodInfo;

import java.util.List;

//拼团列表适配器
public class ListExceltBrandAdapter extends BaseAdapter {

    private Context context;
    private List<GoodInfo> list;

    public ListExceltBrandAdapter(Context context, List<GoodInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void freshList(List<GoodInfo> list) {
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
    public View getView(final int position, View view, ViewGroup parent) {
        Info info;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_excel_brand, null);
            info = new Info();
            info.image = view.findViewById(R.id.item_list_image);
            info.goodName = view.findViewById(R.id.item_list_name);
            info.groupPrice = view.findViewById(R.id.item_list_price);
            info.groupOrgPrice = view.findViewById(R.id.item_list_original_price);
            info.buyBtn = view.findViewById(R.id.item_list_buy_btn);
            view.setTag(info);
        }
        info = (Info) view.getTag();

        //设置数据
        GoodInfo brandInfo = list.get(position);
        Glide.with(context)
                .load(brandInfo.getImage())
                .into(info.image);

        info.goodName.setText(brandInfo.getStore_name());
        info.groupPrice.setText("¥ " + brandInfo.getPrice());
        info.groupOrgPrice.setText("¥ " + brandInfo.getOt_price());
        TextViewUtils.setTextAddLine(info.groupOrgPrice);

        info.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra(IntentUtils.intentActivityFlag, list.get(position).getId());
                intent.putExtra("flag", 0);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class Info {
        private ImageView image;
        private TextView goodName;
        private TextView groupPrice;
        private TextView groupOrgPrice;
        private TextView buyBtn;
    }
}
