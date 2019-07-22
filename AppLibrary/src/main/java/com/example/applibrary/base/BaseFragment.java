package com.example.applibrary.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.applibrary.custom.ToastUtils;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.hss.fuli_hss.base
 * blog: "好蔬食"
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //吐司
    public void tost(String text) {
        ToastUtils.getToastUtils().showToast(getContext(), text);
    }
}
