package com.example.haoss.person.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoss.R;

import java.util.List;

public class ConsumeRecordAdapter extends BaseAdapter {

    private Context context;
    private List<BalanceRecord> list;

    public ConsumeRecordAdapter(Context context, List<BalanceRecord> list) {
        this.context = context;
        this.list = list;
    }

    public void refresh(List<BalanceRecord> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_consume_record, null);

            holder.image = convertView.findViewById(R.id.image);
            holder.record = convertView.findViewById(R.id.item_balance_record);
            holder.recordTime = convertView.findViewById(R.id.item_balance_record_time);
            holder.recordPrice = convertView.findViewById(R.id.item_card_record_price);
            holder.line = convertView.findViewById(R.id.ui_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.record.setText(list.get(position).getMark());
        holder.recordTime.setText(list.get(position).getAdd_time());
        if (list.get(position).getPm() == 0) {
            holder.recordPrice.setText("-" + list.get(position).getNumber());
        } else if (list.get(position).getPm() == 1) {
            holder.recordPrice.setText("+" + list.get(position).getNumber());
        } else {
            holder.recordPrice.setText("" + list.get(position).getNumber());
        }

        if (position == list.size() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView image;
        private TextView record;
        private TextView recordTime;
        private TextView recordPrice;
        private View line;
    }
}
