package com.example.haoss.other;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.haoss.MainActivity;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.util.LhtTool;
import com.example.haoss.views.MyProgressDialog;

import java.io.File;

/**
 * Created by lht on 2017/3/2.
 */

public class UpdateTask implements DownLoadTask.DownlaodListener {
    private Activity context;
    private File file;
    private String url;
    private int THREAD_NUM = 5;
    private MyProgressDialog mProgressDialog;
    private int progressVaue;

    private boolean flag = true;

    public UpdateTask(BaseActivity context, String url) {
        this.context = context;
        this.url = url;
        if (LhtTool.getNetworkType(context) == 0) {
            new AlertDialog.Builder(context).setTitle("温馨提醒")
                    .setMessage("亲，您当前使用的是手机网络是否继续下?").setCancelable(false)
                    .setPositiveButton("继续下载", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            downApk();
                        }
                    }).setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int which) {

                }
            }).show();
        } else {
            downApk();
        }
    }


    /**
     * 进入主页
     */
    private void gotoHome() {
        Intent mIntent = new Intent();
        mIntent.setClass(context, MainActivity.class);
        context.startActivity(mIntent);
        context.finish();
    }

    /**
     * 从服务器下载新的Apk
     */
    private void downApk() {
        initProgressDialog();
        file = new File(Environment.getExternalStorageDirectory(), "m680com.apk");
        DownLoadTask downLoadTask = new DownLoadTask(url, file.getAbsolutePath(), THREAD_NUM);
        downLoadTask.setListener(this);
        // ThreadPoolManager.getInstance().addTask(downLoadTask);
    }

    /**
     * 安装Apk
     */
    private void installApk() {

        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.fromFile(file),
//                "application/vnd.android.package-archive");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.vikecn" + ".provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        context.finish();
    }

    /**
     * 初始化进度条
     */
    private void initProgressDialog() {
        mProgressDialog = new MyProgressDialog(context);// 进度条初始化

        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMessage("更新下载中……");
        mProgressDialog.show();
    }


    @Override
    public void update(int total, int len, int threadid) {
        if (flag) {
            mProgressDialog.setMax(total);
            flag = false;
        }
        progressVaue += len;
        mProgressDialog.setProgress(progressVaue);
    }

    @Override
    public void downLoadFinish(int totalSucess) {

        if (totalSucess == THREAD_NUM) {
            installApk();
        } else {
            Looper.prepare();
            Toast.makeText(context, "下载失败", Toast.LENGTH_LONG).show();
            Looper.loop();
        }

    }

    @Override
    public void downLoadError(int type) {


    }
}
