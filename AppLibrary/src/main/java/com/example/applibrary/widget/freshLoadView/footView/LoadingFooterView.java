package com.example.applibrary.widget.freshLoadView.footView;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.R;
import com.example.applibrary.widget.freshLoadView.IBottomView;
import com.example.applibrary.widget.freshLoadView.freshUtils.DensityUtil;


/**
 * listView中上拉加载的底部
 *
 * @author chentt on 2018/3/9.
 */

public class LoadingFooterView extends FrameLayout implements IBottomView {

    private ImageView loadingView;
    private TextView loadingTextView;
    private Context mContext;
    //下拉时显示的内容
    private String pullLoadingText;
    //释放时显示的内容
    private String releaseLoadingText;
    //加载完成时显示的内容
    private String loadingText;

    public LoadingFooterView(Context context) {
        this(context, null);
    }

    public LoadingFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {

        pullLoadingText = getResources().getString(R.string.listview_load_more);
        releaseLoadingText = getResources().getString(R.string.listview_loading);
        loadingText = "加载完成";

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, DensityUtil.dip2px(mContext, 60));
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);

        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(DensityUtil.dip2px(mContext, 40), DensityUtil.dip2px(mContext, 40));
        loadingView = new ImageView(mContext);
        loadingView.setLayoutParams(params2);
        loadingView.setImageResource(R.drawable.anim_loading_view);
        loadingView.setVisibility(GONE);
        layout.addView(loadingView);

        loadingTextView = new TextView(mContext);
        loadingTextView.setTextSize(17f);
        loadingTextView.setPadding(50, 0, 0, 0);
        layout.addView(loadingTextView);

        addView(layout);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxHeadHeight, float headHeight) {
        loadingTextView.setText(pullLoadingText);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        ((AnimationDrawable) loadingView.getDrawable()).start();
        loadingTextView.setText(releaseLoadingText);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        loadingTextView.setText(releaseLoadingText);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish() {
        loadingTextView.setText(loadingText);
    }

    @Override
    public void reset() {
        loadingTextView.setText(pullLoadingText);
    }

    public void setPullLoadingText(String pullLoadingText) {
        this.pullLoadingText = pullLoadingText;
    }

    public void setRelaseLoadingText(String relaseLoadingText) {
        this.releaseLoadingText = relaseLoadingText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }
}
