package com.example.applibrary.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class CustomerScrollView extends ScrollView {
    public CustomerScrollView(Context context) {
        this(context, null);
    }

    public CustomerScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t == 0) {

        } else if (t + this.getMeasuredHeight() == this.getChildAt(0).getMeasuredHeight()) {
            listener.loadMore();
        }
    }

    public OnScrollListener listener;

    public interface OnScrollListener {
        void loadMore();
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }
}
