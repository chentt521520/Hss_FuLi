package com.example.haoss.person.aftersale.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.person.aftersale.activity.AfterSaleApplyForActivity;
import com.example.haoss.person.aftersale.activity.AfterSaleDetailsActivity;
import com.example.haoss.person.aftersale.entity.AfterSaleInfo;

import java.util.List;

//售后情况适配器
public class AfterSaleAdapter extends BaseAdapter {

    Context context;
    List<AfterSaleInfo> list;

    public AfterSaleAdapter(Context context, List<AfterSaleInfo> list) {
        this.context = context;
        this.list = list;
    }

    //刷新数据
    public void setRefresh(List<AfterSaleInfo> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_aftersale, null);
            info = new Info();
            info.item_aftersale_image = view.findViewById(R.id.item_aftersale_image);
            info.item_aftersale_name = view.findViewById(R.id.item_aftersale_name);
            info.item_aftersale_operate = view.findViewById(R.id.item_aftersale_operate);
            info.item_aftersale_explain = view.findViewById(R.id.item_aftersale_explain);
            info.item_aftersale_number = view.findViewById(R.id.item_aftersale_number);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        AfterSaleInfo afterSaleInfo = list.get(position);
        ImageUtils.imageLoad(context, afterSaleInfo.getImage(), info.item_aftersale_image, 0, 0);

        info.item_aftersale_name.setText(afterSaleInfo.getName() + " " + afterSaleInfo.getSpecification());
        info.item_aftersale_number.setText("数量：" + afterSaleInfo.getNumber());
        info.item_aftersale_explain.setVisibility(View.INVISIBLE);
        if (afterSaleInfo.getType() == 0) {    //0：完成未申请，1：处理中，2：处理完成
            setTextOperate(info.item_aftersale_operate, afterSaleInfo.isTimeout(), "申请售后");
            if (afterSaleInfo.isTimeout()) {
                info.item_aftersale_explain.setText(afterSaleInfo.getExplain());
                info.item_aftersale_explain.setVisibility(View.VISIBLE);
            }
        } else if (afterSaleInfo.getType() == 1)
            setTextOperate(info.item_aftersale_operate, false, "处理中");
        else {
            setTextOperate(info.item_aftersale_operate, false, "查看详情");
            String tex = "";
            switch (afterSaleInfo.getApplyForType()) {//1退款/2退款退货/3换货
                case 1:
                    tex = "仅退款";
                    break;
                case 2:
                    tex = "退款退货";
                    break;
                case 3:
                    tex = "换货";
                    break;
            }
            switch (afterSaleInfo.getStatus()) {//0：未知，1：成功，2：失败，3：关闭
                case 1:
                    tex += "成功";
                    break;
                case 2:
                    tex += "失败";
                    break;
                case 3:
                    tex += "关闭";
                    break;
            }
            info.item_aftersale_explain.setText(tex);
            info.item_aftersale_explain.setVisibility(View.VISIBLE);
        }
        Onclick onclick = new Onclick(position);
        info.item_aftersale_operate.setOnClickListener(onclick);
        return view;
    }

    class Info {
        ImageView item_aftersale_image; //图片
        TextView item_aftersale_name;   //名称
        TextView item_aftersale_operate;    //操作
        TextView item_aftersale_explain;    //说明
        TextView item_aftersale_number; //数量
    }

    class Onclick implements View.OnClickListener {

        int index;  //下标

        public Onclick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            //0：完成未申请，1：处理中，2：处理完成
            switch (list.get(index).getType()) {
                case 0: //申请
                    if (!list.get(index).isTimeout()) {
                        Bundle bundleApplyFor = new Bundle();
                        bundleApplyFor.putSerializable(IntentUtils.intentActivityInfo, list.get(index));
                        IntentUtils.startIntentForResult(0, context, AfterSaleApplyForActivity.class, bundleApplyFor);
                    }
                    break;
                case 1: //处理中
                    toast("售后处理中");
                    break;
                case 2: //详情查看
                    Bundle bundleDetails = new Bundle();
                    bundleDetails.putSerializable(IntentUtils.intentActivityInfo, list.get(index));
                    IntentUtils.startIntent(0, context, AfterSaleDetailsActivity.class, bundleDetails);
                    break;
            }
        }
    }

    /**
     * 设置操作按钮状态
     *
     * @param textView
     * @param b        是否超时
     * @param text
     */
    private void setTextOperate(TextView textView, boolean b, String text) {
        if (b) {
            textView.setBackgroundResource(R.drawable.bk_hui_04);
            textView.setTextColor(Color.parseColor("#d0d0d0"));
        } else {
            textView.setBackgroundResource(R.drawable.bk_red_01);
            textView.setTextColor(Color.parseColor("#c22222"));
        }
        textView.setText(text);
    }

    //吐司
    private void toast(String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
