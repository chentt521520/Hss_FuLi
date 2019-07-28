package com.example.haoss.person.cardConvert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.haoss.R;

import java.util.List;

public class ListConvertRecodAdapter extends BaseAdapter {

    private Context context;
    private List<CardRecord> list;

    public ListConvertRecodAdapter(Context context, List<CardRecord> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<CardRecord> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_card_record, null);

            holder.recordTime = convertView.findViewById(R.id.item_card_record_time);
            holder.recordCode = convertView.findViewById(R.id.item_card_record_code);
            holder.recordPrice = convertView.findViewById(R.id.item_card_record_price);
            holder.line = convertView.findViewById(R.id.ui_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.recordTime.setText(list.get(position).getAdd_time());
        holder.recordCode.setText(list.get(position).getCard_num());
        holder.recordPrice.setText(list.get(position).getPrice());
        if (position == list.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView recordTime;
        private TextView recordCode;
        private TextView recordPrice;
        private View line;
    }
}
