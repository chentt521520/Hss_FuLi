package com.example.haoss.goods.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applibrary.entity.GoodList;
import com.example.applibrary.utils.ImageUtils;
import com.example.haoss.R;

import java.util.List;

//商品搜索适配器
public class GoodsSearchAdapter extends BaseAdapter {

    private Context context;
    private List<GoodList> list;

    public GoodsSearchAdapter(Context context, List<GoodList> list) {
        this.context = context;
        this.list = list;
    }

    //刷新数据
    public void setRefresh(List<GoodList> list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.list_item_good, null);
            info = new Info();
            info.item_goodssearch_lmage = view.findViewById(R.id.item_goodssearch_lmage);
            info.item_goodssearch_name = view.findViewById(R.id.item_goodssearch_name);
            info.item_goodssearch_money = view.findViewById(R.id.item_goodssearch_money);
            info.item_goodssearch_sales = view.findViewById(R.id.item_goodssearch_sales);
            info.item_goodssearch_repertory = view.findViewById(R.id.item_goodssearch_repertory);
            info.item_good_type = view.findViewById(R.id.item_good_type);
            view.setTag(info);
        }
        info = (Info) view.getTag();
        GoodList goodsInfo = list.get(position);
        ImageUtils.imageLoad(context, goodsInfo.getImage(), info.item_goodssearch_lmage, 0, 0);
        info.item_goodssearch_name.setText(goodsInfo.getStore_name());
        info.item_goodssearch_money.setText("¥ " + goodsInfo.getPrice());
        info.item_goodssearch_sales.setText(goodsInfo.getSales() + " 人已购买");
        info.item_goodssearch_repertory.setText("库存数量：" + goodsInfo.getStock());
        if (goodsInfo.getStore_type() == 1) {
            info.item_good_type.setText("海外直邮");
        } else if (goodsInfo.getStore_type() == 2) {
            info.item_good_type.setText("保税区");
        } else {
            info.item_good_type.setText("国内");
        }
        return view;
    }

    class Info {
        ImageView item_goodssearch_lmage;   //图片
        TextView item_goodssearch_name, item_goodssearch_money, item_good_type;  //名称、价格
        TextView item_goodssearch_sales, item_goodssearch_repertory; //销量、库存
    }
}
