package com.example.haoss.person.coupon;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.utils.DateTimeUtils;
import com.example.haoss.R;
import com.example.haoss.goods.goodslist.GoodsListActivity;

import java.util.List;

//优惠劵适配器
public class CouponAdapter extends BaseAdapter {

    private Context context;
    private List<CouponInfo> list;
    private int flag;

    public CouponAdapter(Context context, List<CouponInfo> list, int flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
    }

    //刷新
    public void setRefresh(List<CouponInfo> list) {
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_coupon, null);
            info = new Info();
            info.item_getcouponcentre_bg = view.findViewById(R.id.item_getcouponcentre_bg);
            info.item_coupon_image = view.findViewById(R.id.item_coupon_image);
            info.item_coupon_name = view.findViewById(R.id.item_coupon_name);
            info.item_coupon_validity = view.findViewById(R.id.item_coupon_validity);
            info.item_coupon_tip = view.findViewById(R.id.item_coupon_tip);
            info.item_coupon_price = view.findViewById(R.id.item_coupon_price);
            info.item_coupon_receive = view.findViewById(R.id.item_coupon_receive);
            info.item_coupon_status = view.findViewById(R.id.item_coupon_status);
            info.item_coupon_check = view.findViewById(R.id.item_coupon_check);
            view.setTag(info);
        }
        info = (Info) view.getTag();

        final CouponInfo couponInfo = list.get(position);

        if (flag == 1) {
            info.item_coupon_check.setVisibility(View.GONE);
            info.item_coupon_receive.setVisibility(View.VISIBLE);
            info.item_coupon_validity.setVisibility(View.VISIBLE);

            long nowTime = System.currentTimeMillis() / 1000L;
            long endTime = DateTimeUtils.getDateToLong(couponInfo.getEnd_time(), "yyyy/MM/dd") / 1000L;
            if (endTime - nowTime < 60 * 60 * 24) {
                info.item_coupon_tip.setText("即将到期");
                info.item_coupon_tip.setVisibility(View.VISIBLE);
            } else {
                info.item_coupon_tip.setVisibility(View.GONE);
            }
        } else if (flag == 2) {
            info.item_coupon_tip.setVisibility(View.GONE);
            info.item_coupon_validity.setVisibility(View.GONE);
            info.item_coupon_receive.setVisibility(View.GONE);
            info.item_coupon_check.setVisibility(View.VISIBLE);
        }

        info.item_coupon_price.setText(couponInfo.getCoupon_price());
        info.item_coupon_name.setText(couponInfo.getCoupon_title());
        info.item_coupon_validity.setText(couponInfo.getAdd_time() + " - " + couponInfo.getEnd_time());

        switch (couponInfo.getStatus()) {    //0：未使用，1：已使用, 2:已过期
            case 0:
                info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_use));
                info.item_coupon_image.setBackgroundResource(R.mipmap.getcouponcentre_iten_img);
                info.item_coupon_receive.setText("去使用");
                break;
            case 1:
                info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_used));
                info.item_coupon_image.setBackgroundResource(R.mipmap.coupon_invalid_head);
                info.item_coupon_receive.setText("已使用");
                break;
            case 2:
                info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_used));
                info.item_coupon_image.setBackgroundResource(R.mipmap.coupon_invalid_head);
                info.item_coupon_status.setBackgroundResource(R.mipmap.coupon_invalid_foot);
                info.item_coupon_receive.setText("已过期");
                break;
        }
        info.item_coupon_receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (couponInfo.getStatus() == 0) {
                    Intent intent = new Intent(context, GoodsListActivity.class);
                    intent.putExtra("searchType", couponInfo.getCategory_id());
                    context.startActivity(intent);
                }
            }
        });

        if (couponInfo.isCheck()) {
            info.item_coupon_check.setImageResource(R.mipmap.coupon_checked);
        } else {
            info.item_coupon_check.setImageResource(R.mipmap.coupon_uncheck);
        }
        info.item_coupon_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                couponInfo.setCheck(true);
                listener.onCheck(position);
            }
        });
        return view;
    }

    class Info {
        ImageView item_coupon_image;   //图片
        TextView item_coupon_name; //名称
        TextView item_coupon_validity; //有效期
        TextView item_coupon_price;   //金额
        TextView item_coupon_tip;   //金额
        TextView item_coupon_receive;   //按钮
        LinearLayout item_coupon_status;   //按钮
        LinearLayout item_getcouponcentre_bg; //名称
        ImageView item_coupon_check;   //图片
    }

    public interface OnItemClick {

        void onCheck(int i);
    }

    private OnItemClick listener;

    public void setListener(OnItemClick listener) {
        this.listener = listener;
    }
}
