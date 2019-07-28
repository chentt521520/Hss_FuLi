package com.example.haoss.indexpage.tourdiy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.applibrary.entity.GrouponGood;
import com.example.applibrary.entity.GrouponUser;
import com.example.applibrary.utils.DensityUtil;
import com.example.haoss.R;
import com.example.haoss.indexpage.tourdiy.adapter.GrouponPartnerAdapter;

import java.util.List;

//商品购买或者加入购物车
public class DialogGrouponList extends Dialog {

    public DialogGrouponList(Context context) {
        super(context);
    }

    public DialogGrouponList(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogGrouponList(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private Context context;
    private ListView listView;    //商品规格
    private GrouponDetail activity;
    private List<GrouponUser> userList;
    private GrouponPartnerAdapter adapter;
    private GrouponGood grouponGood;

    public DialogGrouponList(Context context, List<GrouponUser> userList, GrouponGood grouponGood, OnItenClickListener listener) {
        super(context, com.example.applibrary.R.style.BottomDialog);
        this.activity = (GrouponDetail) context;
        this.context = context;
        this.userList = userList;
        this.listener = listener;
        this.grouponGood = grouponGood;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_groupon_goods, null);
        setContentView(view);

        listView = findViewById(R.id.dialog_groupon_list_view);
        findViewById(R.id.dialog_close).setOnClickListener(onClickListener);


        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (3 * DensityUtil.getScreenWidth(context)) / 4;
        layoutParams.height = (2 * DensityUtil.getScreenHeight(context)) / 3;
        view.setLayoutParams(layoutParams);
        this.getWindow().setGravity(Gravity.CENTER);

        setData();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dialog_close) //关闭
                dismiss();
        }
    };

    private void setData() {

        adapter = new GrouponPartnerAdapter(context, userList);
        listView.setAdapter(adapter);

        adapter.setListener(new GrouponPartnerAdapter.onClickLinstener() {
            @Override
            public void setOnClickListener(int pos) {
                listener.setOnItenClickListener(pos);
                dismiss();
            }
        });
    }

    //吐司
    private void toast(String text) {
        activity.tost(text);
    }

    private OnItenClickListener listener;

    public interface OnItenClickListener {
        public void setOnItenClickListener(int pos);
    }

}
