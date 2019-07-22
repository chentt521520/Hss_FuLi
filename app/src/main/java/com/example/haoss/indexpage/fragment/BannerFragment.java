package com.example.haoss.indexpage.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.applibrary.base.BaseFragment;
import com.example.haoss.R;

//轮播图fragment
@SuppressLint("ValidFragment")
public class BannerFragment extends BaseFragment {
    View bannerView;    //界面
    Context context;
    String imageUrl;    //图片地址

    public BannerFragment(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (bannerView == null) {
            bannerView = LayoutInflater.from(context).inflate(R.layout.fragment_banner, null);
            ImageView fragment_banner_image = bannerView.findViewById(R.id.fragment_banner_image);
            Glide.with(context).load(imageUrl).into(fragment_banner_image);
        }
        return bannerView;
    }
}
