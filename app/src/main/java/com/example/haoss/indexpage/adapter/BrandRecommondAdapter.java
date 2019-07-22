package com.example.haoss.indexpage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;
import com.example.haoss.indexpage.entity.NavInfo;

import java.util.List;

public class BrandRecommondAdapter extends RecyclerView.Adapter<BrandRecommondAdapter.MyViewHolder> {


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
    private List<NavInfo> strings;

    /*
     *
     * 构造方法初始化数据
     *
     * */
    public BrandRecommondAdapter(Context context, List<NavInfo> strings) {
        this.context = context;
        this.strings = strings;
    }

    public void freshList(List<NavInfo> strings) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new MyViewHolder(view);
    }

    /*
     * 绑定viewholder
     * 主要是数据源与viewHolder的绑定
     * 这里可以实现监听事件
     * */
    @Override
    public void onBindViewHolder(@NonNull BrandRecommondAdapter.MyViewHolder myViewHolder, final int i) {
        ImageUtils.imageLoad(context, strings.get(i).getImageUrl(), myViewHolder.image);
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
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = itemView.findViewById(R.id.brand_image);

        }
    }
}
