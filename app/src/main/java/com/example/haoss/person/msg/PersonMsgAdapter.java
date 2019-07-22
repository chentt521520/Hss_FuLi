package com.example.haoss.person.msg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//消息适配器
public class PersonMsgAdapter extends BaseAdapter {

    private Context context;
    private List<PersonMsgInfo> list;

    public PersonMsgAdapter(Context context, List<PersonMsgInfo> list) {
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
        Holder holder;
        if (view == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_list_mesage, null);
            holder.messageHead = view.findViewById(R.id.image);
            holder.messageTitle = view.findViewById(R.id.message_title);
            holder.messageContent = view.findViewById(R.id.message_content);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
//        ImageUtils.imageLoad(context, list.get(position).getId(), holder.messageHead);
        holder.messageTitle.setText(list.get(position).getTitle());
        holder.messageContent.setText(list.get(position).getDescription());

        return view;
    }

    class Holder {
        private ImageView messageHead;
        private TextView messageTitle;
        private TextView messageContent;
    }
}
