package com.example.haoss.base;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.applibrary.utils.DensityUtil;
import com.example.applibrary.widget.CustomTitleView;

public class BaseActivity extends AppCompatActivity {

    /**
     * 全局的LayoutInflater对象，已经完成初始化.
     */
    public LayoutInflater mInflater;

    /**
     * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, FILL_PARENT
     */
    public LinearLayout.LayoutParams layoutParamsFF = null;

    /**
     * LinearLayout.LayoutParams，已经初始化为FILL_PARENT, WRAP_CONTENT
     */
    public LinearLayout.LayoutParams layoutParamsFW = null;

    /**
     * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, FILL_PARENT
     */
    public LinearLayout.LayoutParams layoutParamsWF = null;

    /**
     * LinearLayout.LayoutParams，已经初始化为WRAP_CONTENT, WRAP_CONTENT
     */
    public LinearLayout.LayoutParams layoutParamsWW = null;

    private CustomTitleView titleView;
    /**
     * 水印显示的文本
     */
    public String waterText = "";

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 描述：创建.
     *
     * @param savedInstanceState the saved instance state
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);

        mInflater = LayoutInflater.from(this);

        layoutParamsFF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsFW = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsWF = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParamsWW = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        titleView = new CustomTitleView(this);
        Application application = (Application) getApplication();
    }

    /**
     * 设置界面显示（含标题栏）
     */
    public void setTitleContentView(int resId) {
        this.setTitleContentView(mInflater.inflate(resId, null));
    }

    /**
     * 设置界面显示（含标题栏）
     */
    public void setTitleContentView(View contentView) {
        setTitleView(contentView);
    }

    private void setTitleView(View contentView) {

        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, DensityUtil.dip2px(this, 45));
        baseLayout.addView(titleView, params);
        RelativeLayout contentLayout = new RelativeLayout(this);
        contentLayout.setPadding(0, 0, 0, 0);
        contentLayout.addView(contentView, layoutParamsFF);
        RelativeLayout.LayoutParams layoutParamsFW1 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParamsFW1.addRule(RelativeLayout.BELOW, titleView.getId());
        baseLayout.addView(contentLayout, layoutParamsFW1);
        this.setContentView(baseLayout);
    }

    public CustomTitleView getTitleView() {
        return this.titleView;
    }


    /**
     * 描述：设置界面显示（忽略标题栏）
     *
     * @see android.app.Activity#setContentView(int)
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    /**
     * 描述：设置界面显示（忽略标题栏）
     *
     * @see android.app.Activity#setContentView(View, android.view.ViewGroup.LayoutParams)
     */
    @Override
    public void setContentView(View view, android.view.ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    /**
     * 描述：设置界面显示（忽略标题栏）
     *
     * @see android.app.Activity#setContentView(View)
     */
    public void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * 弹出Toast
     *
     * @param message 提示信息
     */
    public void showToast(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出Toast
     *
     * @param message 提示信息
     */
    public void tost(CharSequence message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出Toast
     *
     * @param message  提示信息
     * @param duration Toast显示多长时间
     */
    public void showToast(CharSequence message, int duration) {
        Toast.makeText(this, message, duration).show();
    }

    /**
     * 弹出Toast
     *
     * @param resId 提示信息的资源id
     */
    public void showToast(int resId) {
        Toast.makeText(this, "" + this.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 弹出Toast
     *
     * @param resId    提示信息的资源id
     * @param duration Toast显示多长时间
     */
    public void showToast(int resId, int duration) {
        Toast.makeText(this, "" + this.getResources().getText(resId), duration).show();
    }
}
