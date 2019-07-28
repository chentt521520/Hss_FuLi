package com.example.haoss.person.cardConvert;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.haoss.R;
import com.example.haoss.base.AppLibLication;
import com.example.haoss.base.BaseActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class CardNumberConvertActivity extends BaseActivity {

    private EditText numberInput;
    private Button convertBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_card_convert);
        init();
    }

    private void init() {
        this.getTitleView().setTitleText("兑换");
        numberInput = findViewById(R.id.ui_card_number_input);
        convertBtn = findViewById(R.id.ui_card_number_convert_btn);
        convertBtn.setEnabled(false);
        findViewById(R.id.ui_card_convert_rule).setOnClickListener(listener);
        ;
        findViewById(R.id.ui_card_convert_record).setOnClickListener(listener);


        convertBtn.setOnClickListener(listener);

        numberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() > 0) {
                    convertBtn.setEnabled(true);
                } else {
                    convertBtn.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_card_number_convert_btn:
                    cartConvert();
                    break;
                case R.id.ui_card_convert_rule:
                    IntentUtils.startIntent(CardNumberConvertActivity.this, CardConvertRuleActivity.class);
                    break;
                case R.id.ui_card_convert_record:
                    IntentUtils.startIntent(CardNumberConvertActivity.this, CardConvertRecordActivity.class);
                    break;
            }
        }
    };

    private void cartConvert() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppLibLication.getInstance().getToken());
        map.put("cardNum", numberInput.getText().toString());
        httpHander.okHttpMapPost(this, Netconfig.exchangeCard, map, 1);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    Log.e("~~~~~~", msg.obj.toString());
                    try {
                        HashMap<String, Object> map = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (map != null) {
                            if (map.containsKey("code") && getDouble(map, "code") == 200) {
                                numberInput.setText("");
                                IntentUtils.startIntentForResult(1, CardNumberConvertActivity.this, CardConvertResultActivity.class);
                            } else {
                                tost(getString(map, "msg"));
                            }
                        } else {
                            tost("解析出错");
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }
        }
    };
}
