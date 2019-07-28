package com.example.haoss.goods.estimate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.ReplyInfo;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;
import com.example.haoss.adapter.ImageAdapter;

import java.util.List;

public class ListEstimateAdapter extends BaseAdapter {

    private Context context;
    private List<ReplyInfo> replyList;

    public ListEstimateAdapter(Context context, List<ReplyInfo> replyList) {
        this.context = context;
        this.replyList = replyList;
    }

    public void refresh(List<ReplyInfo> replyList) {
        this.replyList = replyList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return replyList == null ? 0 : replyList.size();
    }

    @Override
    public Object getItem(int position) {
        return replyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_estimate, null);
            holder = new ViewHolder();
            holder.userHeader = convertView.findViewById(R.id.ui_estimate_user_head);
            holder.userName = convertView.findViewById(R.id.ui_estimate_user_name);
            holder.addTime = convertView.findViewById(R.id.ui_estimate_add_time);
            holder.goodSuk = convertView.findViewById(R.id.ui_estimate_good_suk);
            holder.comment = convertView.findViewById(R.id.ui_estimate_comment);
            holder.my_grid_view = convertView.findViewById(R.id.my_grid_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ReplyInfo replyInfo = replyList.get(position);
        ImageUtils.loadCirclePic(context, replyInfo.getAvatar(), holder.userHeader);
        holder.userName.setText(replyInfo.getNickname());
        holder.addTime.setText(replyInfo.getAdd_time());
        holder.goodSuk.setText("净含量：" + replyInfo.getSuk());
        holder.comment.setText(replyInfo.getComment());

        ImageAdapter adapter = new ImageAdapter(context, replyInfo.getPics());
        holder.my_grid_view.setAdapter(adapter);

        return convertView;
    }

    private class ViewHolder {
        private ImageView userHeader;
        private TextView userName;
        private TextView addTime;
        private TextView goodSuk;
        private TextView comment;
        private GridView my_grid_view;
    }

}
