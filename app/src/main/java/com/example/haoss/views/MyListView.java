package com.example.haoss.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.haoss.R;
import com.example.haoss.util.LogUtils;


/**
 * @author SunnyCoffee
 * @desc 自定义Listview　下拉刷新,上拉加载更多
 */
public class MyListView extends ListView implements OnScrollListener {

    // 区分当前操作是刷新还是加载
    public static final int REFRESH = 0;
    public static final int LOAD = 1;

    // 区分PULL和RELEASE的距离的大小
    private static final int SPACE = 20;

    // 定义header的四种状态和当前状态
    private int state;

    private LayoutInflater inflater;
    private View footer;

    private TextView noData;
    private TextView loadFull;
    private TextView more;
    private ProgressBar loading;

    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;

    private int startY;

    private int firstVisibleItem;
    private int scrollState;
    private int headerContentInitialHeight;
    private int headerContentHeight;

    // 只有在listview第一个item显示的时候（listview滑到了顶部）才进行下拉刷新， 否则此时的下拉只是滑动listview
    private boolean isRecorded;
    private boolean isLoading;// 判断是否正在加载
    private boolean loadEnable = true;// 开启或者关闭加载更多功能
    private boolean isLoadFull;
    private int pageSize = 10;

    private OnLoadListener onLoadListener;

    public MyListView(Context context) {
        super(context);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    // 加载更多监听
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.loadEnable = true;
        this.onLoadListener = onLoadListener;
    }

    public boolean isLoadEnable() {
        return loadEnable;
    }

    // 这里的开启或者关闭加载更多，并支持动态调整
    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
//		this.removeFooterView(footer);
        if (loadEnable) {
            isLoading = false;
            this.footer.setVisibility(VISIBLE);
        } else {
            this.footer.setVisibility(GONE);

        }
    }


    public View getFooter() {
        return footer;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    // 初始化组件
    private void initView(Context context) {

        // 设置箭头特效
        animation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(100);
        reverseAnimation.setFillAfter(true);

        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.listview_footer, null);
        loadFull = footer.findViewById(R.id.loadFull);// 加载全部
        noData = footer.findViewById(R.id.noData);// 暂无数据
        more = footer.findViewById(R.id.more);// 加载更多
        loading = footer.findViewById(R.id.loading);// 等待


        // 为listview添加头部和尾部，并进行初始化
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }


    public void onLoad() {
        if (onLoadListener != null) {
            onLoadListener.onLoad();
        }
    }


    // 用于加载更多结束后的回调
    public void onLoadComplete() {
        isLoading = false;
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        this.firstVisibleItem = firstVisibleItem;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        ifNeedLoad(view, scrollState);
    }

    // 根据listview滑动的状态判断是否需要加载更多
    private void ifNeedLoad(AbsListView view, int scrollState) {
        if (!loadEnable) {
            return;
        }
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                    && !isLoading
                    && view.getLastVisiblePosition() == view
                    .getPositionForView(footer) && !isLoadFull) {
                onLoad();
                isLoading = true;
            }
        } catch (Exception e) {
            LogUtils.getLogUtils().d("lht", "===============Exception:" + e.toString());
        }
    }

    /**
     * 这个方法是根据结果的大小来决定footer显示的。
     * <p>
     * 这里假定每次请求的条数为10。如果请求到了10条。则认为还有数据。如过结果不足10条，则认为数据已经全部加载，这时footer显示已经全部加载
     * </p>
     *
     * @param resultSize
     */
    public void setResultSize(int resultSize) {
        if (resultSize == 0) {
            isLoadFull = true;
            loadFull.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else if (resultSize > 0 && resultSize < pageSize) {
            isLoadFull = true;
            loadFull.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
            more.setVisibility(View.GONE);
            noData.setVisibility(View.GONE);
        } else if (resultSize == pageSize) {
            isLoadFull = false;
            loadFull.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            more.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        }

    }


    /*
     * 定义加载更多接口；
     */
    public interface OnLoadListener {
        public void onLoad();
    }
}
