package com.example.haoss.indexpage.tourdiy.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.indexpage.tourdiy.entity.GrouponListInfo;
import com.example.haoss.indexpage.tourdiy.entity.GrouponUser;

import java.util.List;

//拼团列表适配器
public class GrouponPartnerAdapter extends BaseAdapter {

    private Context context;
    private List<GrouponUser> list;

    public GrouponPartnerAdapter(Context context, List<GrouponUser> list) {
        this.context = context;
        this.list = list;
    }

    public void freshList(List<GrouponUser> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_groupon_person, null);
            info = new Info();
            info.partner_head = view.findViewById(R.id.partner_head);
            info.partner_name = view.findViewById(R.id.partner_name);
            info.left_people_num = view.findViewById(R.id.left_people_num);
            info.left_time_over = view.findViewById(R.id.left_time_over);
            info.groupon_btn = view.findViewById(R.id.groupon_btn);
            view.setTag(info);
        }
        info = (Info) view.getTag();

        //设置数据
        GrouponUser user = list.get(position);

        ImageUtils.loadCirclePic(context, user.getAvatar(), info.partner_head);

        info.partner_name.setText(user.getNickname());
        String count = "还差：" + "<font color = \"#c22222\">" + user.getCount() + "</font>" + "成功";
        info.left_people_num.setText(Html.fromHtml(count));
        info.left_time_over.setText("剩余：" + user.getH() + ":" + user.getI() + ":" + user.getS());

        info.groupon_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnClickListener(position);
            }
        });

        return view;
    }

    class Info {
        private ImageView partner_head;
        private TextView partner_name;
        private TextView left_people_num;
        private TextView left_time_over;
        private TextView groupon_btn;
    }

    public interface onClickLinstener {
        void setOnClickListener(int pos);
    }

    private onClickLinstener listener;

    public void setListener(onClickLinstener listener) {
        this.listener = listener;
    }
}
