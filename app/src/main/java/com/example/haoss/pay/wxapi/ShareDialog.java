//package com.example.haoss.pay.wxapi;
////
//import android.app.Dialog;
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.StyleRes;
//import android.view.View;
//import android.widget.Toast;
//
//import com.example.haoss.R;
//import com.tencent.connect.share.QQShare;
//import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.SendMessageToWX;
//import com.tencent.mm.sdk.openapi.WXAPIFactory;
//import com.tencent.mm.sdk.openapi.WXImageObject;
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
//import com.tencent.mm.sdk.openapi.WXWebpageObject;
//
///**
// * Created by ZJ  2017/8/5.
// */
//
//public class ShareDialog extends Dialog implements View.OnClickListener {
//    private String mUserId;
//    public final String APP_ID = "wxd743de055a146a46";
//
//    private QQItemClickListener mListener;
//    private IWXAPI api;
//    private static final int shareType = QQShare.SHARE_TO_QQ_TYPE_DEFAULT;
//    private boolean isNeedWXShare = true;
//    Bitmap bitmapResources;//图片
//
//    public ShareDialog(@NonNull Context context, String mUserId, boolean flag, QQItemClickListener listener) {
//        this(context, R.style.dialog_bottom_no_title);
//        this.mUserId = mUserId;
//        api = WXAPIFactory.createWXAPI(context, APP_ID, false);
//        api.registerApp(APP_ID);
//        isNeedWXShare = flag;
//        mListener = listener;
//        Resources res = context.getResources();
//        bitmapResources= BitmapFactory.decodeResource(res, R.drawable.fenxiang_001);
//    }
//
//    private ShareDialog(@NonNull Context context, @StyleRes int themeResId) {
//        super(context, themeResId);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_share_bottom);
//        initView();
//    }
//
//    private void initView() {
//        findViewById(R.id.dialog_share_wei).setOnClickListener(this);
//        findViewById(R.id.dialog_share_wei_friend).setOnClickListener(this);
//        findViewById(R.id.dialog_share_cancel).setOnClickListener(this);
//        findViewById(R.id.dialog_share_qq_friend).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.dialog_share_wei:
//                if (isNeedWXShare) {
//                    share(0);
////                    sharePicture(0);
//                } else {
//                    mListener.onWXShareListen(api);
//                }
//                break;
//            case R.id.dialog_share_wei_friend:
//                if (isNeedWXShare) {
//                    share(1);
//                } else {
//                    mListener.onWXShareFriendListen(api);
//                }
//                Toast.makeText(getContext(), "微信好友分享", Toast.LENGTH_LONG).show();
//                break;
//            case R.id.dialog_share_qq_friend:
//                final Bundle params = new Bundle();
//                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//                params.putString(QQShare.SHARE_TO_QQ_TITLE, "美酷生活");
//                params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "美动中国，酷行天下");
//                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://028spay.com:8005/oytBarCode/register!appAd.action?service_tel=" + mUserId+"&organization=10000022");
//                System.out.println("分享点击链接" + "http://www.xjoyt.com:28614/oytBarCode/register!appAd.action?service_tel=" + mUserId+"&organization=10000022");
//                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://mchtimage.oss-cn-qingdao.aliyuncs.com/icon_share.jpg");
//                if (mListener != null) {
//                    mListener.onQQClickListening(params, shareType);
//                }
//                Toast.makeText(getContext(), "QQ好友分享", Toast.LENGTH_LONG).show();
//                dismiss();
//                break;
//            default:
//                dismiss();
//                break;
//        }
//    }
//
//    public interface QQItemClickListener {
//        void onQQClickListening(Bundle params, int shareType);
//
//        void onWXShareListen(IWXAPI api);
//
//        void onWXShareFriendListen(IWXAPI api);
//
//    }
//
//    private void share(int flag) {
//        WXWebpageObject webpage = new WXWebpageObject();
//         webpage.webpageUrl = "http://028spay.com:8005/oytBarCode/register!appAd.action?service_tel="+mUserId+"&organization=10000022";//上线地址
////        webpage.webpageUrl = "http://028spay.com:8005/oytBarCode/DownLoadAction.action?&fileName=/home/epaysch/mobileApp/MeiKu.apk";//上线地址
//
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = "美酷生活";
//        msg.description = "美动中国，酷行天下!";
//        //这里替换一张自己工程里的图片资源
//        Bitmap thumb = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.shangbaologin);
//        msg.setThumbImage(thumb);
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
//        boolean fla = api.sendReq(req);
//        System.out.println("fla=" + fla);
//        dismiss();
//    }
//
//    private String buildTransaction(final String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis())
//                : type + System.currentTimeMillis();
//    }
//
//    private void sharePicture(int shareType) {//图片分享
//
//        WXImageObject imgObj = new WXImageObject(bitmapResources);
//
//        WXMediaMessage msg = new WXMediaMessage();
//        msg.mediaObject = imgObj;
//
//        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmapResources, 100, 150, true);
//        bitmapResources.recycle();
//        msg.thumbData = Util.bmpToByteArray(thumbBitmap, true);  //设置缩略图
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("imgshareappdata");
//        req.message = msg;
//        req.scene = shareType == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
//    }
//}
