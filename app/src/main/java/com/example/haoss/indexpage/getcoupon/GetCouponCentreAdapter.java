package com.example.haoss.indexpage.getcoupon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;

import java.util.List;

//领券中心适配器
public class GetCouponCentreAdapter extends BaseAdapter {

    private Context context;
    private List<CouponInfo> list;
    private OClick oClick;  //点击监听

    public GetCouponCentreAdapter(Context context, List<CouponInfo> list, OClick oClick) {
        this.context = context;
        this.list = list;
        this.oClick = oClick;
    }

    public void fresh(List<CouponInfo> list) {
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
    public View getView(int position, View view, ViewGroup parent) {
        Info info;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_list_coupon_centre, null);
            info = new Info();
            info.item_getcouponcentre_image = view.findViewById(R.id.item_getcouponcentre_image);
            info.item_getcouponcentre_name = view.findViewById(R.id.item_getcouponcentre_name);
            info.item_getcouponcentre_validity = view.findViewById(R.id.item_getcouponcentre_validity);
            info.item_getcouponcentre_type = view.findViewById(R.id.item_getcouponcentre_type);
            info.item_getcouponcentre_pb = view.findViewById(R.id.item_getcouponcentre_pb);
            info.item_getcouponcentre_maoney = view.findViewById(R.id.item_getcouponcentre_maoney);
            info.item_getcouponcentre_button = view.findViewById(R.id.item_getcouponcentre_button);
            info.item_getcouponcentre_bg = view.findViewById(R.id.item_getcouponcentre_bg);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        CouponInfo couponInfo = list.get(position);
        info.item_getcouponcentre_maoney.setText(couponInfo.getCoupon_price());
        info.item_getcouponcentre_validity.setText("有效期：" + couponInfo.getStart_time() + "-" + couponInfo.getEnd_time());
        int residue = couponInfo.getTotal_count() - couponInfo.getRemain_count();
        boolean b;
        if (couponInfo.isIs_use()) {
            Glide.with(context)
                    .load(R.mipmap.coupon_invalid_head)
                    .into(info.item_getcouponcentre_image);
            info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_used));
            info.item_getcouponcentre_type.setText("已领券");
            info.item_getcouponcentre_button.setText("去用券");
            info.item_getcouponcentre_pb.setVisibility(View.GONE);
            info.item_getcouponcentre_button.setTextColor(Color.parseColor("#0f0f0f"));
            b = true;
        } else {
            Glide.with(context)
                    .load(R.mipmap.getcouponcentre_iten_img)
                    .into(info.item_getcouponcentre_image);
            info.item_getcouponcentre_pb.setVisibility(View.VISIBLE);

            if (couponInfo.getIs_permanent() == 0) {//有限张
                if (residue < 1) {
                    Glide.with(context)
                            .load(R.mipmap.coupon_invalid_head)
                            .into(info.item_getcouponcentre_image);
                    info.item_getcouponcentre_pb.setVisibility(View.VISIBLE);
                    info.item_getcouponcentre_pb.setProgress(0);
                    info.item_getcouponcentre_type.setText("已抢光");
                    info.item_getcouponcentre_button.setText("去逛逛");
                    info.item_getcouponcentre_button.setTextColor(Color.parseColor("#0f0f0f"));
                    info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_used));
                    b = false;
                } else {
                    int get = couponInfo.getTotal_count() - couponInfo.getRemain_count();
                    info.item_getcouponcentre_pb.setProgress((get / couponInfo.getTotal_count()) * couponInfo.getTotal_count());
                    info.item_getcouponcentre_type.setText("剩余：" + residue);
                    info.item_getcouponcentre_button.setText("立即领券");
                    info.item_getcouponcentre_button.setTextColor(Color.parseColor("#f73962"));
                    info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_use));
                    b = true;
                }
            } else {//无限张
                info.item_getcouponcentre_type.setText("剩余：999+");
                info.item_getcouponcentre_pb.setProgress(50);
                info.item_getcouponcentre_button.setText("立即领券");
                info.item_getcouponcentre_button.setTextColor(Color.parseColor("#f73962"));
                info.item_getcouponcentre_bg.setBackground(context.getResources().getDrawable(R.mipmap.bg_coupon_use));
                b = true;
            }
        }
        TextViewUtils.setTextColor(info.item_getcouponcentre_type, b, "#f73962", "#999999");
        info.item_getcouponcentre_button.setOnClickListener(new OnClickGet(couponInfo));
        info.item_getcouponcentre_name.setText(couponInfo.getTitle());

        return view;
    }


    class Info {
        ImageView item_getcouponcentre_image;   //图片
        TextView item_getcouponcentre_name; //名称
        TextView item_getcouponcentre_validity; //有效期
        TextView item_getcouponcentre_type; //状态包含剩余
        ProgressBar item_getcouponcentre_pb;    //进度
        TextView item_getcouponcentre_maoney;   //金额
        TextView item_getcouponcentre_button;   //按钮
        LinearLayout item_getcouponcentre_bg;
    }

    //按钮监听
    class OnClickGet implements View.OnClickListener {

        CouponInfo info;

        public OnClickGet(CouponInfo info) {
            this.info = info;
        }

        @Override
        public void onClick(View v) {
            oClick.onClick(info);
        }
    }

    //点击传递监听
    interface OClick {
        void onClick(CouponInfo info);
    }

}
