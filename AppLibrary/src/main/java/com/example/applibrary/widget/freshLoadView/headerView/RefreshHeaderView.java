package com.example.applibrary.widget.freshLoadView.headerView;

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
import com.example.applibrary.widget.freshLoadView.IHeaderView;
import com.example.applibrary.widget.freshLoadView.OnAnimEndListener;
import com.example.applibrary.widget.freshLoadView.freshUtils.DensityUtil;


/**
 * listView中下拉刷新加载的头部
 *
 * @author chentt on 2018/3/9.
 */

public class RefreshHeaderView extends FrameLayout implements IHeaderView {

    private ImageView refreshArrow;
    private ImageView loadingView;
    private TextView refreshTextView;
    private Context mContext;

    //上拉时显示的内容
    private String pullDownStr;
    //释放时显示的内容
    private String releaseRefreshStr;
    //刷新时显示的内容
    private String refreshingStr;

    public RefreshHeaderView(Context context) {
        this(context, null);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {

        pullDownStr = getResources().getString(R.string.listview_pull_to_refresh);
        releaseRefreshStr = getResources().getString(R.string.listview_release_refresh);
        refreshingStr = getResources().getString(R.string.listview_refreshing);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, DensityUtil.dip2px(mContext, 70));
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER);

        ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(DensityUtil.dip2px(mContext, 30), DensityUtil.dip2px(mContext, 30));
        refreshArrow = new ImageView(mContext);
        refreshArrow.setLayoutParams(params1);
        refreshArrow.setImageDrawable(getResources().getDrawable(R.mipmap.ic_arrow));
        layout.addView(refreshArrow);

        ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(DensityUtil.dip2px(mContext, 40), DensityUtil.dip2px(mContext, 40));
        loadingView = new ImageView(mContext);
        loadingView.setLayoutParams(params2);
        loadingView.setImageDrawable(getResources().getDrawable(R.drawable.anim_loading_view));
        loadingView.setVisibility(GONE);
        layout.addView(loadingView);

        refreshTextView = new TextView(mContext);
        refreshTextView.setPadding(50, 0, 0, 0);
        refreshTextView.setTextSize(17f);
        layout.addView(refreshTextView);

        addView(layout);

    }

    public void setPullDownStr(String pullDownStr1) {
        pullDownStr = pullDownStr1;
    }

    public void setReleaseRefreshStr(String releaseRefreshStr1) {
        releaseRefreshStr = releaseRefreshStr1;
    }

    public void setRefreshingStr(String refreshingStr1) {
        refreshingStr = refreshingStr1;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) refreshTextView.setText(pullDownStr);
        if (fraction > 1f) refreshTextView.setText(releaseRefreshStr);
        refreshArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            refreshTextView.setText(pullDownStr);
            refreshArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);
            if (refreshArrow.getVisibility() == GONE) {
                refreshArrow.setVisibility(VISIBLE);
                loadingView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText(refreshingStr);
        refreshArrow.setVisibility(GONE);
        loadingView.setVisibility(VISIBLE);
        ((AnimationDrawable) loadingView.getDrawable()).start();
    }

    @Override
    public void onFinish(OnAnimEndListener listener) {
        listener.onAnimEnd();
    }

    @Override
    public void reset() {
        refreshArrow.setVisibility(VISIBLE);
        loadingView.setVisibility(GONE);
        refreshTextView.setText(pullDownStr);
    }
}
