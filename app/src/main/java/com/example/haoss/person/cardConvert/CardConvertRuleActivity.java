package com.example.haoss.person.cardConvert;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;


public class CardConvertRuleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_card_convert_rule);
        this.getTitleView().setTitleText("兑换规则");
    }
}
