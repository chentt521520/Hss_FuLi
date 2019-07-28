package com.example.haoss.person.dingdan.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.entity.ShoppingCartInfo;
import com.example.applibrary.resp.RespOrderList;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

public class ListOrderFormAdapter extends BaseAdapter {

    private Context context;
    private List<RespOrderList.OrderList> list;   //订单商品数据

    public ListOrderFormAdapter(Context context, List<RespOrderList.OrderList> list) {
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
        RespOrderList.OrderList info = list.get(position);

        holder.goodContainer.removeAllViews();

        for (ShoppingCartInfo cartInfo : info.getCartInfo()) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_list_order_good, null);

            ImageView image = view.findViewById(R.id.item_orderformshopping_image);
            TextView name = view.findViewById(R.id.item_orderformshopping_name);
            TextView money = view.findViewById(R.id.item_orderformshopping_money);
            TextView suk = view.findViewById(R.id.item_orderformshopping_specification);
            TextView number = view.findViewById(R.id.item_orderformshopping_number);
            name.setText(cartInfo.getProductInfo().getStore_name());
            if (cartInfo.getProductInfo().getAttrInfo() == null) {//无规格
                suk.setText("");
                money.setText("¥ " + cartInfo.getProductInfo().getPrice());
                ImageUtils.imageLoad(context, cartInfo.getProductInfo().getImage(), image, 0, 0);
            } else {//有规格
                suk.setText(cartInfo.getProductInfo().getAttrInfo().getSuk());
                money.setText("¥ " + cartInfo.getProductInfo().getAttrInfo().getPrice());
                ImageUtils.imageLoad(context, cartInfo.getProductInfo().getAttrInfo().getImage(), image, 0, 0);
            }
            number.setText("x " + cartInfo.getCart_num());
            holder.goodContainer.addView(view);
        }

        //0 待付款 1 待发货 2 待收货 4 已完成
        switch (info.getStatu().getType()) {
            case 0:
                holder.item_left_btn.setText("取消订单");
                holder.item_right_btn.setText("付款");
                holder.time.setText(info.getAdd_time());
                break;
            case 1:
                holder.item_left_btn.setText("查看物流");
                holder.item_right_btn.setText("催单");
                holder.time.setText(info.getPay_time());
                break;
            case 2:
                holder.item_left_btn.setText("查看物流");
                holder.item_right_btn.setText("确认收货");
                holder.time.setText(info.getPay_time());
                break;
            case 3:
                holder.item_left_btn.setText("删除订单");
                holder.item_right_btn.setText("评价");
            case 4:
                holder.item_left_btn.setText("删除订单");
                holder.item_right_btn.setText("已评价");
                holder.time.setText(info.getPay_time());
                break;
            default:
                holder.item_left_btn.setVisibility(View.INVISIBLE);
                holder.item_right_btn.setVisibility(View.INVISIBLE);
                break;
        }


        holder.hint.setText(info.getStatu().getTitle());

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

    public void refresh(List<RespOrderList.OrderList> orderList) {
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
