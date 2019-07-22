package com.example.haoss.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.base.BaseFragment;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.MainActivity;
import com.example.haoss.R;

@SuppressLint("ValidFragment")
public class GuideFragment extends BaseFragment {
    View bannerView;    //界面
    Context context;
    int imageId;    //图片地址
    int index;  //下标

    public GuideFragment(int imageId, int index) {
        this.imageId = imageId;
        this.index = index;
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
            bannerView = LayoutInflater.from(context).inflate(R.layout.fragment_guide, null);
            ImageView fragment_guide_image = bannerView.findViewById(R.id.fragment_guide_image);
            LinearLayout fragment_guide_linear = bannerView.findViewById(R.id.fragment_guide_linear);
            TextView fragment_guide_text = bannerView.findViewById(R.id.fragment_guide_text);
            fragment_guide_image.setImageResource(imageId);
            if (index == 3)
                fragment_guide_linear.setVisibility(View.VISIBLE);
            fragment_guide_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtils.startIntent(context, MainActivity.class);
                    ((Activity) context).finish();
                }
            });
        }
        return bannerView;
    }
}
