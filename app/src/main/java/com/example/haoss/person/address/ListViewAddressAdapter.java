package com.example.haoss.person.address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.AddreInfo;
import com.example.haoss.R;

import java.util.List;

public class ListViewAddressAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddreInfo> list;   //地址信息

    public ListViewAddressAdapter(Context mContext, List<AddreInfo> list) {
        this.mContext = mContext;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder myViewHolder;
        if (convertView == null) {
            myViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_address, parent, false);
            myViewHolder.yn_xuan = convertView.findViewById(R.id.item_address_check);
            myViewHolder.ed = convertView.findViewById(R.id.item_address_edit);
            myViewHolder.userName = convertView.findViewById(R.id.item_user_name);
            myViewHolder.phone = convertView.findViewById(R.id.item_user_phone);
            myViewHolder.moren = convertView.findViewById(R.id.item_user_address_def);
            myViewHolder.addre = convertView.findViewById(R.id.item_user_address);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (ViewHolder) convertView.getTag();
        }
        final AddreInfo info = list.get(position);
        if (info.isChecked()) {    //选中
            myViewHolder.yn_xuan.setBackgroundResource(R.mipmap.addre_xuan_img);
        } else
            myViewHolder.yn_xuan.setBackgroundResource(R.mipmap.addre_no_xuan_img);
        if (info.isDefault())  //默认
            myViewHolder.moren.setVisibility(View.VISIBLE);
        else
            myViewHolder.moren.setVisibility(View.GONE);
        myViewHolder.userName.setText(info.getName());    //名称
        myViewHolder.phone.setText(info.getPhone());  //电话
        myViewHolder.addre.setText(info.getProvince() + " " + info.getCity() + " " + info.getCounty() + " " + info.getAddre());   //地址
        //编辑  （修改收货地址）
        myViewHolder.ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEditClick(position);
            }
        });
        //选中
        myViewHolder.yn_xuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onRadioCheck(position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private ImageView yn_xuan, ed;  //选中,编辑
        private TextView userName, phone, moren, addre;    //名称、电话、默认、地址
    }

    public interface OnItemClickListener {
        void onEditClick(int i);

        void onRadioCheck(int i);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
