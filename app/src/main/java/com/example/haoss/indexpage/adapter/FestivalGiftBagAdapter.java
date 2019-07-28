package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.Recommond;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//年节礼包钜惠礼包适配器
public class FestivalGiftBagAdapter extends RecyclerView.Adapter<FestivalGiftBagAdapter.MyViewHolder> {


    /*
     * 另一种实现监听事件的实现方式采用接口回调的方式
     * */
    public interface OnItemClickListener {
        public void onItemClickListener(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnViewClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /*
     *
     * 属性一般俩一个是数据源
     * 另一个是上下问对象
     *
     * */
    private Context context;
    private List<Recommond> strings;

    /*
     *
     * 构造方法初始化数据
     *
     * */
    public FestivalGiftBagAdapter(Context context, List<Recommond> strings) {
        this.context = context;
        this.strings = strings;
    }

    public void freshList(List<Recommond> strings) {
        this.strings = strings;
        notifyDataSetChanged();
    }

    /*
     * 创建viewHolder
     * 这个主要作用就是找到item布局
     * 把布局里面有用的控件与Viewholder进行关联
     * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gift_car_item, parent, false);
        return new MyViewHolder(view);
    }

    /*
     * 绑定viewholder
     * 主要是数据源与viewHolder的绑定
     * 这里可以实现监听事件
     * */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        ImageUtils.imageLoad(context, strings.get(i).getImage(), myViewHolder.image);
        myViewHolder.descript.setText(strings.get(i).getStore_name());
        myViewHolder.price.setText(strings.get(i).getPrice());
        /*
         * 接口回调方式实现监听事件
         * */
        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(i);
                }
            }
        });
    }

    /*
     * 获取数据源条数
     * */
    @Override
    public int getItemCount() {
        return strings == null ? 0 : strings.size();
    }

    /*
     * viewholder与布局中的控件关联
     * view代表一个item
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView descript;
        TextView price;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.item_gift_package_icon);
            descript = itemView.findViewById(R.id.item_gift_package_descript);
            price = itemView.findViewById(R.id.item_gift_package_price);

        }
    }
}
