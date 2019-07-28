package com.example.haoss.person.cardConvert;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.MainActivity;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.wallet.WalletActivity;

public class CardConvertResultActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_card_convert_result);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("兑换成功");
        findViewById(R.id.ui_card_back_index).setOnClickListener(listener);
        findViewById(R.id.ui_card_back_wallet).setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_card_back_index:
                    Intent intent = new Intent();
                    intent.setClass(CardConvertResultActivity.this, MainActivity.class);
                    intent.putExtra("flag", 0);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                case R.id.ui_card_back_wallet:
                    IntentUtils.startIntent(CardConvertResultActivity.this, WalletActivity.class);
                    break;
            }
        }
    };
}
