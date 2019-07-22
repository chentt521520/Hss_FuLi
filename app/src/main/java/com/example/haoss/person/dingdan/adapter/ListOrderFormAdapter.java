package com.example.haoss.person.dingdan.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;
import com.example.haoss.person.dingdan.entity.OrderListInfo;
import com.example.haoss.shopcat.entity.ShoppingCartInfo;

import java.util.List;

public class ListOrderFormAdapter extends BaseAdapter {

    private Context context;
    private List<OrderListInfo> list;   //订单商品数据

    public ListOrderFormAdapter(Context context, List<OrderListInfo> list) {
        this.context = context;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_order, null);

            holder.layout = convertView.findViewById(R.id.item_orderformshopping_relative);
            holder.time = convertView.findViewById(R.id.item_orderformshopping_time);
            holder.hint = convertView.findViewById(R.id.item_orderformshopping_hint);
            holder.item_right_btn = convertView.findViewById(R.id.item_right_btn);
            holder.item_left_btn = convertView.findViewById(R.id.item_left_btn);
            holder.goodContainer = convertView.findViewById(R.id.order_item);
            holder.allMoney = convertView.findViewById(R.id.item_orderformshopping_allmoney);
            holder.allNumber = convertView.findViewById(R.id.item_orderformshopping_allnumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderListInfo info = list.get(position);

        holder.goodContainer.removeAllViews();
        for (ShoppingCartInfo cartInfo : info.getCartInfo()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_order_good, null);

            ImageView image = view.findViewById(R.id.item_orderformshopping_image);
            TextView name = view.findViewById(R.id.item_orderformshopping_name);
            TextView money = view.findViewById(R.id.item_orderformshopping_money);
            TextView suk = view.findViewById(R.id.item_orderformshopping_specification);
            TextView number = view.findViewById(R.id.item_orderformshopping_number);
            name.setText(cartInfo.getProductInfo().getStore_name());
            suk.setText(cartInfo.getProductInfo().getGoodsType().getSuk());
            number.setText("x " + cartInfo.getCart_num());
            money.setText("¥ " + cartInfo.getProductInfo().getGoodsType().getPrice());
            ImageUtils.imageLoad(context, cartInfo.getProductInfo().getGoodsType().getImage(), image, 0, 0);
            holder.goodContainer.addView(view);
        }

        //0 待付款 1 待发货 2 待收货 4 已完成
        switch (info.get_status().get_type()) {
            case 0:
                holder.item_left_btn.setText("取消订单");
                holder.item_right_btn.setText("付款");
                holder.time.setText(info.get_add_time());
                break;
            case 1:
                holder.item_left_btn.setText("查看物流");
                holder.item_right_btn.setText("催单");
                holder.time.setText(info.get_pay_time());
                break;
            case 2:
                holder.item_left_btn.setText("查看物流");
                holder.item_right_btn.setText("确认收货");
                holder.time.setText(info.get_pay_time());
                break;
            case 3:
                holder.item_left_btn.setText("删除订单");
                holder.item_right_btn.setText("评价");
            case 4:
                holder.item_left_btn.setText("删除订单");
                holder.item_right_btn.setText("已评价");
                holder.time.setText(info.get_pay_time());
                break;
            default:
                holder.item_left_btn.setVisibility(View.INVISIBLE);
                holder.item_right_btn.setVisibility(View.INVISIBLE);
                break;
        }


        holder.hint.setText(info.get_status().get_title());

        String price = "合计：¥" + "<font color = \"#c22222\">" + info.getPay_price() + "</font>";
        holder.allMoney.setText(Html.fromHtml(price));
        String count = "共：" + "<font color = \"#c22222\">" + info.getTotal_num() + "</font>" + "件商品，";
        holder.allNumber.setText(Html.fromHtml(count));

        //左
        holder.item_left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setLeftBtn(position);
            }
        });
        //右
        holder.item_right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setRightBtn(position);
            }
        });
        //整体
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setView(position);
            }
        });

        return convertView;
    }

    public void refresh(List<OrderListInfo> orderList) {
        this.list = orderList;
        notifyDataSetChanged();
    }

    class ViewHolder {
        LinearLayout goodContainer; //item控件
        LinearLayout layout; //item控件
        TextView time, hint;   //时间、说明
        TextView item_right_btn, item_left_btn;   //右按钮、左按钮
        TextView allMoney, allNumber;  //总金额、总数量
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {

        public void setView(int index);

        public void setLeftBtn(int index);

        public void setRightBtn(int index);
    }
}
