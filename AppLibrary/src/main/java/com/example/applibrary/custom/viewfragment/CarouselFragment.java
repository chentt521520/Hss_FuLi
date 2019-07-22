package com.example.applibrary.custom.viewfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.applibrary.R;
import com.example.applibrary.base.BaseFragment;

//各frament
@SuppressLint("ValidFragment")
public class CarouselFragment extends BaseFragment {
    View view;    //界面
    Context context;
    FragmentDataInfo dataInfo;    //数据
    OnclickFragmentView onclickFragmentView;

    public CarouselFragment(FragmentDataInfo dataInfo, OnclickFragmentView onclickFragmentView) {
        this.dataInfo = dataInfo;
        this.onclickFragmentView = onclickFragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_carousel, null);
            ImageView image = view.findViewById(R.id.fragment_carousel_image);
            Glide.with(context).load(dataInfo.getImageUrl()).into(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {   //点击事件
                    onclickFragmentView.onItemclick(dataInfo.getId(), dataInfo.getSkipUrl());
                }
            });
        }
        return view;
    }

}