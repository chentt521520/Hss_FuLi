package com.example.haoss.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.other.UpdateTask;
import com.example.haoss.views.ViewDialog;


import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by lht on 2017/2/28.
 * 项目工具类，定义一些全局的工具方法
 */

public class LhtTool {

    //用于标志用户是否登录，某些操作会用到该字段
    public static boolean isLogin;
    public static boolean isLoginPage = false;
    public static boolean idDianPuLogin = false;
    public static boolean isMainActivity;
    public static boolean isChatActivity;
    private static int num;
    public static boolean firstClick = true;
    public static String isFaLogin = "0";
    public static boolean isLoginFa = true;
    public static int unReadMessage = 0;

    public static void sendMessage(Handler hd, IOException e, int what) {
        Bundle b = new Bundle();
        b.putString("e", e.toString());
        Message ms = new Message();
        ms.what = what;
        ms.setData(b);
        hd.sendMessage(ms);
    }

    //显示网络请求异常对话框

    /**
     * 显示网络请求异常的弹窗
     *
     * @param con
     * @param msg
     */
    public static void showNetworkException(Context con, Message msg) {

        num++;
        if (num == 1) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(con)
                    .setMessage("网络异常，请刷新后重试~~")
                    .setCancelable(true);

            alertDialog.create();

        }
        num = 0;
    }

    /**
     * 创建更新下载的弹窗
     *
     * @param context
     * @param before
     * @param num
     * @param content
     * @param url
     * @return
     */
    public static ViewDialog createUPdateDilog(final BaseActivity context, String before, String num, String content, final String url) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_version_update, null);// 得到加载view
        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.update_dialog);// 加载布局
        TextView versionnum = (TextView) v.findViewById(R.id.textView3);// 软件版本更新內容
        TextView button1 = (TextView) v.findViewById(R.id.textView5);// 以后再说
        TextView button2 = (TextView) v.findViewById(R.id.textView6);// 立即更新

        versionnum.setText(Html.fromHtml("当前版本：v" + before + "<br>最新版本：v" + num + "<br><br>更新内容：<br>" + content));
        final ViewDialog loadingDialog = new ViewDialog(context, R.style.update_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        WindowManager windowManager = context.getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params = loadingDialog.getWindow().getAttributes();
        params.height = (int) (d.getHeight() * 0.5);
        params.width = (int) (d.getWidth() * 0.8);
        loadingDialog.getWindow().setAttributes(params);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.dismiss();

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.dismiss();
                new UpdateTask(context, url);
            }
        });
        return loadingDialog;
    }

    /**
     * 判断是否使用数据流量还是wifi
     *
     * @param con
     * @return
     */
    public static int getNetworkType(Context con) {

        if (null != con) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        PackageManager packageManager = context.getPackageManager();
        int versioncode = 1;
        try {
            versioncode = packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return
     */
    public static String getClientVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        String versionName = null;
        try {
            versionName = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 通过反射得到标题栏高度
     */

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取手机ID
     * @param context
     * @return
     */
//    public static String getHouse_CID(Context context) {
//        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(UserLoginActivity.TELEPHONY_SERVICE);
//        String driveId = null;
//        try {
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return "00";
//            }
//            driveId = mTelephonyManager.getDeviceId();
//            return driveId;
//        } catch (Exception e) {
//            LogUtils.getLogUtils().d("lht", "=====================Exception:" + e.toString());
//        }
//        return null;
//    }


    /**
     * @param action   拨号方式：
     *                 ACTION_CALL方式拨打电话(直接拨打)，
     *                 添加权限android.permission.CALL_PHONE
     *                 ACTION_DIAL方式拨打电话(打开拨号界面)
     *                 为了用户体验，一般推荐用ACTION_DIAL方式
     * @param phoneNum 电话号码
     * @param context  上下文
     */
//    public static void Call(String action, String phoneNum, Context context) throws Exception {
//
//        if (phoneNum != null && phoneNum.trim().length() > 0) {
//            //这里"tel:"+电话号码 是固定格式，系统一看是以"tel:"开头的，就知道后面应该是电话号码。
//            Intent intent = new Intent(action, Uri.parse("tel:" + phoneNum.trim()));
//            context.startActivity(intent);//调用上面这个intent实现拨号
//        } else {
//            CustomToast.showToast(context, "电话号码不能为空", Toast.LENGTH_LONG);
//        }
//
//    }


    /**
     * @param phoneNum 手机号码
     * @return 验证是不是手机号码
     */
    public static boolean isMobile(String phoneNum) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile("^[1][1-9][0-9]{9}$"); // 验证手机号
        m = p.matcher(phoneNum);
        b = m.matches();
        return b;
    }


//    //支付宝线程获得
//    @SuppressLint("HandlerLeak")
//    public static Handler getHander(final Context context, final String num, final LoadingDialog ld) {
//        final int SDK_PAY_FLAG = 1;
//        final int SDK_CHECK_FLAG = 2;
//
//        return new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//
//
//                switch (msg.what) {
//                    case SDK_PAY_FLAG: {
//                        PayResult payResult = new PayResult((String) msg.obj);
//                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
//                        String resultInfo = payResult.getResult();
//                        String resultStatus = payResult.getResultStatus();
//                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                        if (TextUtils.equals(resultStatus, "9000")) {
//                            CustomToast.showToast(context, "支付成功",
//                                    Toast.LENGTH_SHORT);
//                            /**
//                             * float price=1.2;
//                             DecimalFormat decimalFormat=new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//                             String p=decimalFomat.format(price);//format 返回的是字符串
//                             */
////                            DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
////                            float f = Float.valueOf(MyApplication.getInstance().userBean.getBalance()) + Float.valueOf(num);
////                            MyApplication.getInstance().userBean.setBalance(decimalFormat.format(f));
////                            context.startActivity(new Intent(context, MainActivity.class));
//                        } else {
//                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                            if (TextUtils.equals(resultStatus, "8000")) {
//                                CustomToast.showToast(context, "支付结果确认中",
//                                        Toast.LENGTH_SHORT);
//                            } else {
//                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                                CustomToast.showToast(context, "支付失败",
//                                        Toast.LENGTH_SHORT);
//                            }
//                        }
//                        break;
//                    }
//                    case SDK_CHECK_FLAG: {
//                        CustomToast.showToast(context, "检查结果为：" + msg.obj,
//                                Toast.LENGTH_SHORT);
//                        break;
//                    }
//                    default:
//
//                        break;
//                }
//                firstClick = true;
//                if (ld !=null) {
//                    ld.dismiss();
//                }
//
//            }
//        };
//    }

//    /**
//     * 设置webview 的cookie
//     * @param url
//     */
//    public static void setCookieManager(String url) {
//        CookieManager cookieManager = CookieManager.getInstance();
//        StringBuffer sb = new StringBuffer();
//        if (!isLogin) {
//            sb.append("myvikecn=");
//            sb.append("app=" + "1");
//            sb.append(";domain=" + "680.com");
//            sb.append(";path=" + "/");
//        } else {
//            sb.append("myvikecn=");
//            sb.append("vkuserid=" + MyApplication.userInfo.getUserID());
//            sb.append("&vkusername=" + MyApplication.userInfo.getCookieLoginName());
//            sb.append("&vkuserip=" + MyApplication.userInfo.getCookieLoginIpt());
//            sb.append("&vklogtime=" + MyApplication.userInfo.getCookieLoginTime());
//            sb.append("&vktoken=" + MyApplication.userInfo.getCookieLoginToken());
//            sb.append("&vklogs=" + MyApplication.userInfo.getCookieLoginLogs());
//            sb.append("&app=" + "1");
//            sb.append(";domain=" + "680.com");
//            sb.append(";path=" + "/");
//        }
//        cookieManager.setCookie(url, sb.toString());
//    }


    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }


//    /**
//     * 分享
//     * @param context
//     * @param title   标题
//     * @param titleUrl  标题链接
//     * @param content  内容
//     * @param contentUrl  内容链接
//     * @param imgUrl  图片链接
//     */
//    public static void showShare(Context context, String title, String titleUrl, String content, String contentUrl, String imgUrl) {
//        ShareSDK.initSDK(context);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle(title);
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        oks.setTitleUrl(titleUrl);
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText(content+contentUrl);
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
////        oks.setImagePath(Environment.getExternalStorageDirectory() + "/app_logo.png");//确保SDcard下面存在此张图片
//        oks.setImageUrl(imgUrl);
////        oks.setImageUrl("http://git.oschina.net/alexyu.yxj/MyTmpFiles/raw/master/kmk_pic_fld/small/120.JPG");
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl(contentUrl);
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        //oks.setComment("我是测试评论文本");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(context.getResources().getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        oks.setSiteUrl("www.680.com");
//
//// 启动分享GUI
//        oks.show(context);
//    }

    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param color
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 获取状态栏的高度大小
     *
     * @param activity
     * @param color
     * @return
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundColor(color);
        return statusView;
    }


    public static class MyImageGetter implements Html.ImageGetter {

        private Resources resources;

        public MyImageGetter(Resources resources) {
            this.resources = resources;
        }

        @Override
        public Drawable getDrawable(String source) {
            URL url;
            Drawable drawable = null;
            try {
                LogUtils.getLogUtils().d("lht", "=================source:" + source);
                LogUtils.getLogUtils().d("lht", "=================imagegetter加载");
                if (source.charAt(0) == 'h') {
                    url = new URL(source);
                    drawable = Drawable.createFromStream(
                            url.openStream(), null);
                    DisplayMetrics metrics = resources.getDisplayMetrics();
                    int newwidth = metrics.widthPixels - (int) (32 * resources.getDisplayMetrics().density + 0.5f);
                    double factor = (double) newwidth / (double) drawable.getIntrinsicWidth();
                    int newheight = (int) (drawable.getIntrinsicHeight() * factor);
                    drawable.setBounds(0, 0, newwidth, newheight);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return drawable;
        }
    }


}
