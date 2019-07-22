package com.example.applibrary.utils;

import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.util.Random;

public class UploadPicUtil {
    public static void uploadPic(String token, String key, String picName, final UpLoadPicCallback callback) {
        Configuration config = new Configuration.Builder()
                .useHttps(true)               // 是否+使用https上传域名
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();

        UploadManager uploadManager = new UploadManager(config); // UploadManager对象只需要创建一次重复使用

        /**
         * 调用put方法上传
         * 第一个参数 data：可以是字符串，是要上传图片的路径
         *                可以是File对象，是要上传的文件
         *                可以是byte[]数组，要是上传的数据
         * 第二个参数 key：字符串，是图片在服务器上的名称，要具有唯一性，可以用UUID
         * 第三个参数 token：根据开发者的 AK和SK 生成的token，这个token 应该在后端提供一个接口，然后android代码中发一个get请求获得这个tocken，但这里为了演示，直接写在本地了.
         * 第四个参数：UpCompletionHandler的实例，有个回调方法
         * 第五个参数：可先参数
         */
        uploadManager.put(picName, key, token, new UpCompletionHandler() {
                    /**
                     * 回调方法
                     * @param key 开发者设置的 key 也就是文件名
                     * @param info 日志，包含上传的ip等
                     * @param res 包括一个hash值和key
                     */
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        if (info.isOK()) {
                            callback.result(0);
                            Log.i("上传结果：", "Upload Success");
                        } else {
                            callback.result(-1);
                            Log.i("上传结果：", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("key：", key + "\ninfo：" + info + "\nres：" + res);
                    }
                },
                null);

    }

    /*获取一条随机字符串*/
    public static String getRandomString() { //length表示生成字符串的长度
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
