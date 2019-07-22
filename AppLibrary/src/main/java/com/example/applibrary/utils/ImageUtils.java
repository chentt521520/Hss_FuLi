package com.example.applibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.example.applibrary.R;

//图片处理工具类
public class ImageUtils {

    /**
     * 图片3级普通加载
     *
     * @param context
     * @param imageUrl  图片地址
     * @param imageView 图片控件
     */
    public static void imageLoad(Context context, String imageUrl, ImageView imageView) {
//        Glide.with(context).load(item.getImageUrl()).into(info.imageview);
        Glide.with(context).load(imageUrl).into(imageView);
    }

    /**
     * 待加载异常的图片加载
     *
     * @param context
     * @param imageUrl
     * @param imageView
     * @param placeholderImageId 占位图片
     * @param errorImageId       隐藏图片
     */
    public static void imageLoad(Context context, String imageUrl, ImageView imageView, int placeholderImageId, int errorImageId) {

        if (placeholderImageId == 0)
            placeholderImageId = R.drawable.image_load_wait;
        if (errorImageId == 0)
            errorImageId = R.drawable.image_load_lose;
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderImageId)
                .error(errorImageId)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(imageUrl).apply(options).into(imageView);
    }


    public static void loadCirclePic(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

}
