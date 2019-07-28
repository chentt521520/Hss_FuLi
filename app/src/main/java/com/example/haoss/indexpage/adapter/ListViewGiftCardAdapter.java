package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.NavInfo;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

public class ListViewGiftCardAdapter extends BaseAdapter {
    private Context context;
    private List<NavInfo> list;

    public ListViewGiftCardAdapter(Context context, List<NavInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<NavInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder info;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_gift_card, null);
            info = new ViewHolder();
            info.cardIcon = view.findViewById(R.id.shopping_style_healthlife_image);
            info.cardName = view.findViewById(R.id.shopping_style_healthlife_name);
            info.cardDesript = view.findViewById(R.id.shopping_style_healthlife_explain);
            info.cardPrice = view.findViewById(R.id.shopping_style_healthlife_mony);
            info.cardReservation = view.findViewById(R.id.shopping_style_healthlife_number);
            view.setTag(info);
        }
        info = (ViewHolder) view.getTag();
        NavInfo birhtDayInfo = list.get(position);
        ImageUtils.imageLoad(context, birhtDayInfo.getImageUrl(), info.cardIcon);
        info.cardName.setText(birhtDayInfo.getName());
        info.cardDesript.setText(birhtDayInfo.getDescript());
        info.cardPrice.setText("¥ " + birhtDayInfo.getMoney() + " ");
        info.cardReservation.setText(birhtDayInfo.getNumber() + " 人已付款");
        return view;
    }

    class ViewHolder {
        private ImageView cardIcon;
        private TextView cardName;
        private TextView cardDesript;
        private TextView cardPrice;
        private TextView cardReservation;
    }
}