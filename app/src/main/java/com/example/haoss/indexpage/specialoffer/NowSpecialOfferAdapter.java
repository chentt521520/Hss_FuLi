package com.example.haoss.indexpage.specialoffer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.resp.RespTodaySales;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.haoss.R;
import com.example.haoss.goods.details.GoodsDetailsActivity;

import java.util.List;

//特价列表适配器
public class NowSpecialOfferAdapter extends BaseAdapter {

    private Context context;
    private List<RespTodaySales.TodaySales> list;

    public NowSpecialOfferAdapter(Context context, List<RespTodaySales.TodaySales> list) {
        this.context = context;
        this.list = list;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_list_today_sales, null);
            info = new Info();
            info.item_nowspecialoffer_image = view.findViewById(R.id.item_nowspecialoffer_image);
            info.item_nowspecialoffer_name = view.findViewById(R.id.item_nowspecialoffer_name);
            info.item_nowspecialoffer_sb = view.findViewById(R.id.item_nowspecialoffer_sb);
            info.item_nowspecialoffer_money = view.findViewById(R.id.item_nowspecialoffer_money);
            info.item_nowspecialoffer_originalcost = view.findViewById(R.id.item_nowspecialoffer_originalcost);
            info.item_nowspecialoffer_go = view.findViewById(R.id.item_nowspecialoffer_go);
            info.item_nowspecialoffer_sales = view.findViewById(R.id.item_nowspecialoffer_sales);
            view.setTag(info);
        }
        info = (Info) view.getTag();

        //设置数据
        RespTodaySales.TodaySales todaySales = list.get(position);
        Glide.with(context)
                .load(todaySales.getImage())
                .into(info.item_nowspecialoffer_image);
        info.item_nowspecialoffer_name.setText(todaySales.getTitle());
        TextViewUtils.setTextAddLine(info.item_nowspecialoffer_originalcost);
        info.item_nowspecialoffer_originalcost.setText("¥ " + todaySales.getOt_price());
        info.item_nowspecialoffer_money.setText("¥ " + todaySales.getPrice());
        info.item_nowspecialoffer_sales.setText("已抢" + todaySales.getSales() + "件");
        info.item_nowspecialoffer_sb.setMax(100); //最大进度
        info.item_nowspecialoffer_sb.setProgress(todaySales.getPercent());  //当前进度
        //购买监听
        info.item_nowspecialoffer_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra(IntentUtils.intentActivityFlag, list.get(position).getId());
                intent.putExtra("flag", 1);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class Info {
        ImageView item_nowspecialoffer_image;   //图片
        TextView item_nowspecialoffer_name; //名称
        ProgressBar item_nowspecialoffer_sb;    //进度条
        TextView item_nowspecialoffer_money, item_nowspecialoffer_originalcost;    //价格、原价
        TextView item_nowspecialoffer_go;   //去抢购
        TextView item_nowspecialoffer_sales;   //去抢购
    }

//    class Oclick implements View.OnClickListener{
//
//        NowSpecialOfferInfo nowSpecialOfferInfo;
//
//        public Oclick(NowSpecialOfferInfo nowSpecialOfferInfo) {
//            this.nowSpecialOfferInfo = nowSpecialOfferInfo;
//        }
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(context,"点击了：" + nowSpecialOfferInfo.getGoodsSum(),Toast.LENGTH_SHORT).show();
//        }
//    }
}
