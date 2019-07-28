package com.example.haoss.person.footprint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.resp.RespFootPrints;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//足迹适配器
public class FootprintAdapter extends BaseAdapter {

    private Context context;
    private List<RespFootPrints.FootPrints> list;
    private boolean flag;

    public FootprintAdapter(Context context, List<RespFootPrints.FootPrints> list, boolean flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
    }

    //刷新
    public void setRefresh(List<RespFootPrints.FootPrints> list, boolean flag) {
        this.list = list;
        this.flag = flag;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_footprint, null);
            info = new Info();
            info.footprint_shoppingview = view.findViewById(R.id.footprint_shoppinglinear);
            info.footprint_shoppingchecked = view.findViewById(R.id.footprint_shoppingchecked);
            info.footprint_shoppingimage = view.findViewById(R.id.footprint_shoppingimage);
            info.footprint_shoppingname = view.findViewById(R.id.footprint_shoppingname);
            info.footprint_shoppingmoney = view.findViewById(R.id.footprint_shoppingmoney);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        final RespFootPrints.FootPrints footprintIfo = list.get(position);
        if (flag) {//浏览状态
            info.footprint_shoppingchecked.setVisibility(View.GONE);
        } else {//编辑状态
            info.footprint_shoppingchecked.setVisibility(View.VISIBLE);
            if (footprintIfo.isCheck()) {
                info.footprint_shoppingchecked.setImageResource(R.mipmap.checked_true);
            } else {
                info.footprint_shoppingchecked.setImageResource(R.mipmap.checked_false);
            }
        }
        ImageUtils.imageLoad(context, footprintIfo.getImage(), info.footprint_shoppingimage, 0, 0);
        info.footprint_shoppingname.setText(footprintIfo.getStore_name());
        info.footprint_shoppingmoney.setText("¥ " + footprintIfo.getPrice());

        info.footprint_shoppingchecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnItemCheckListener(position);
            }
        });

        info.footprint_shoppingview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setOnItemClickListener(position);

            }
        });
        //事件
        return view;
    }

    class Info {
        RelativeLayout footprint_shoppingview;
        ImageView footprint_shoppingchecked, footprint_shoppingimage;    //商品选中、商品图片
        TextView footprint_shoppingname, footprint_shoppingmoney;    //商品名称、价格、找相似
    }

    public interface OnItemClickListener {
        void setOnItemClickListener(int pos);

        void setOnItemCheckListener(int pos);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
