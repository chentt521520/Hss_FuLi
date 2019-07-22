package com.example.applibrary.custom.webview;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.WebView;

public class MyWebView extends WebView {
    private int lastContentHeight = 0;
    private static final int MSG_CONTENT_CHANGE = 1;
    private onContentChangeListener onContentChangeListener = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CONTENT_CHANGE:
                    if (onContentChangeListener != null) {
                        onContentChangeListener.onContentChange();
                    }
                    break;
            }
        }
    };


    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getContentHeight() != lastContentHeight) {
            handler.sendEmptyMessage(MSG_CONTENT_CHANGE);
            lastContentHeight = getContentHeight();
            System.out.println("网页高度：" + lastContentHeight);
        }
    }

    public void setOnContentChangeListener(MyWebView.onContentChangeListener onContentChangeListener) {
        this.onContentChangeListener = onContentChangeListener;
    }

    /**
     * 监听内容高度发生变化
     */
    public interface onContentChangeListener {
        void onContentChange();
    }
}