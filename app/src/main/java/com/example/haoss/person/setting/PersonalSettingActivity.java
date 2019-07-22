package com.example.haoss.person.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.UpLoadPicCallback;
import com.example.applibrary.utils.UploadPicUtil;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.entity.LoginInfo;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.widget.pickView.popwindow.DatePickerPopWin;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.other.PictureSelectorConfig;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//个人设置
public class PersonalSettingActivity extends BaseActivity {

    private ImageView head;    //头像
    private EditText nickname;  //昵称
    private RadioGroup sex; //性别选择
    private TextView birthday;  //生日
    private int sexual = 1;    // 0未知 1 男 2女
    private LoginInfo info;
    private String token;
    private String picPath;
    private String picKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_personal_setting);
        init();
    }

    //初始化
    private void init() {

        this.getTitleView().setTitleText("个人信息");
        this.getTitleView().setRightText("保存");
        this.getTitleView().setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        head = findViewById(R.id.ui_person_setting_head);
        nickname = findViewById(R.id.ui_person_setting_nickname);
        sex = findViewById(R.id.ui_person_setting_sex);
        birthday = findViewById(R.id.ui_person_setting_birthday);

        head.setOnClickListener(onClickListener);
        birthday.setOnClickListener(onClickListener);
        sex.setOnCheckedChangeListener(onCheckedChangeListener);

        getInfo();
        getToken();
    }

    //获取token
    private void getToken() {
        String url = Netconfig.qiNiuGetToken;
        httpHander.getHttpGson(this, url, "", 2);
    }

    //获取个人中心信息
    private void getInfo() {
        String url = Netconfig.personalCenter;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ConfigHttpReqFields.sendToken, AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(PersonalSettingActivity.this, url, map, 1);
    }

    //获取用户信息
    private void setData() {
        picKey = info.getAvatar();
        ImageUtils.loadCirclePic(this, info.getAvatar(), head);
        nickname.setText(info.getNickname());
        if (info.getSex() == 2) {
            sexual = 2;
            sex.check(R.id.ui_person_setting_female);
        } else {
            sexual = 1;
            sex.check(R.id.ui_person_setting_male);
        }
        birthday.setText(info.getBirthday());
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ui_person_setting_head:   //头像
                    PictureSelectorConfig.initMultiConfig(PersonalSettingActivity.this, 1);
                    break;
                case R.id.ui_person_setting_birthday:   //生日
                    if (info.getIs_birthday() != 0) {
                        tost("生日只能设置一次！");
                    } else {
                        showDatePop();
                    }
                    break;
            }
        }
    };

    private void saveData() {

        if (TextUtils.isEmpty(nickname.getText().toString())) {
            tost("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(birthday.getText().toString())) {
            tost("请选择生日");
        } else {
            String url = Netconfig.updataUserInfo;
            Map<String, Object> map = new HashMap<>();
            map.put("token", AppLibLication.getInstance().getToken());
            map.put("nickname", nickname.getText().toString());
            map.put("sex", sexual);
            map.put("avatar", picKey);
            map.put("birthday", birthday.getText().toString());
            httpHander.okHttpMapPost(PersonalSettingActivity.this, url, map, 3);
        }
    }

    private void showDatePop() {
        DatePickerPopWin datePickerPopWin = new DatePickerPopWin.Builder(PersonalSettingActivity.this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                birthday.setText(dateDesc);
            }
        }).build();
        datePickerPopWin.showPopWin(PersonalSettingActivity.this);
    }

    //性别选择
    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.ui_person_setting_male: //男
                    sexual = 1;
                    break;
                case R.id.ui_person_setting_female: //女
                    sexual = 2;
                    break;
            }
        }
    };

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    Map<String, Object> map = jsonToMap(msg.obj.toString());
                    if (map != null) {
                        info = new LoginInfo();
                        info.setNickname(getString(map, "nickname"));
                        info.setAvatar(getString(map, "avatar"));
                        info.setStatus((int) getDouble(map, "status"));
                        info.setSex((int) getDouble(map, "sex"));
                        info.setIs_birthday((int) getDouble(map, "is_birthday"));
                        info.setBirthday(getString(map, "birthday"));
                        setData();
                    }
                    break;
                case 2:
                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            token = (String) ret.get("data");
                        } else {
                            tost(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            tost(getString(ret, "msg"));
                            finish();
                        } else {
                            tost(getString(ret, "msg"));
                        }
                    } catch (Exception e) {
                        tost(e.getMessage());
                    }
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> picList = PictureSelector.obtainMultipleResult(data);
                    picPath = picList.get(0).getCompressPath();
                    getPic();
                    break;
            }
        }
    }


    private void getPic() {
        if (!TextUtils.isEmpty(token)) {
            final String key = Netconfig.PIC_PATH + System.currentTimeMillis() + UploadPicUtil.getRandomString() + Netconfig.PIC_FORM;
            UploadPicUtil.uploadPic(token, key, picPath, new UpLoadPicCallback() {
                @Override
                public void result(int ret) {
                    if (ret == 0) {
                        ImageUtils.loadCirclePic(PersonalSettingActivity.this, picPath, head);
                        picKey = Netconfig.PIC_URL + key;
                        Log.e("~~~~~~~~~", "上传成功");
                    } else {
                        Log.e("~~~~~~~~~", "上传失败");
                    }
                }
            });
        } else {
            tost("获取token失败");
        }
    }
}
