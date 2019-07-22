package com.example.haoss.person.dingdan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.TextViewUtils;
import com.example.applibrary.utils.UpLoadPicCallback;
import com.example.applibrary.utils.UploadPicUtil;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.adpter.GridViewAdapter;
import com.example.haoss.person.other.MainConstant;
import com.example.haoss.person.other.PictureSelectorConfig;
import com.example.haoss.person.other.PlusImageActivity;
import com.example.haoss.views.MyGridView;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//评价发表界面
public class EstimatePublishActivity extends BaseActivity {

    ImageView image;   //商品图片
    TextView prise_high, prise_middle, prise_low;//好中差评
    EditText comment_text; //评价内容
    MyGridView comment_pic;    //上传的图片

    private GridViewAdapter adapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList;//上传的图片凭证的数据源
    int chooseFlag = 5; //1：好评，2：中评，3：差评
    String unique, goodsImage;   //商品唯一标示，图片地址
    private String[] picPath;
    private String token;
    private int count;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_estimate_publish);
        initData();
        init();
    }

    private void initData() {
        unique = getIntent().getStringExtra("unique");
        goodsImage = getIntent().getStringExtra("goodsImage");

        mPicList = new ArrayList<>();
        picPath = new String[6];
    }

    private void init() {
        this.getTitleView().setTitleText("发表评价");

        image = findViewById(R.id.estimatepublishactivity_goodsimage);
        prise_high = findViewById(R.id.estimatepublishactivity_good);
        prise_middle = findViewById(R.id.estimatepublishactivity_medium);
        prise_low = findViewById(R.id.estimatepublishactivity_differ);

        prise_high.setOnClickListener(onClickListener);
        prise_middle.setOnClickListener(onClickListener);
        prise_low.setOnClickListener(onClickListener);
        findViewById(R.id.btn_comment_pulish).setOnClickListener(onClickListener);

        comment_text = findViewById(R.id.estimatepublishactivity_input);

        comment_pic = findViewById(R.id.estimatepublishactivity_addimggv);

        ImageUtils.imageLoad(this, goodsImage, image, 0, 0);


        getToken();
        setTextChoose(5);
        initGridView();
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    break;
                case R.id.estimatepublishactivity_good:  //好评
                    setTextChoose(5);
                    break;
                case R.id.estimatepublishactivity_medium:   //中评
                    setTextChoose(3);
                    break;
                case R.id.estimatepublishactivity_differ:   //差评
                    setTextChoose(1);
                    break;
                case R.id.btn_comment_pulish:  //发表
                    updatePic();
                    break;
            }
        }
    };

    //设置textview选择状态
    private void setTextChoose(int flag) {
        chooseFlag = flag;
        String colorRed = "#c22222";
        String colorHui = "#666666";
        TextViewUtils.setImage(this, prise_high, flag == 5 ? R.mipmap.pingjia_good_y : R.mipmap.pingjia_good_n, 1);
        TextViewUtils.setImage(this, prise_middle, flag == 3 ? R.mipmap.pingjia_cha_y : R.mipmap.pingjia_cha_n, 1);
        TextViewUtils.setImage(this, prise_low, flag == 1 ? R.mipmap.pingjia_cha_y : R.mipmap.pingjia_cha_n, 1);

        prise_high.setTextColor(Color.parseColor(flag == 5 ? colorRed : colorHui));
        prise_middle.setTextColor(Color.parseColor(flag == 3 ? colorRed : colorHui));
        prise_low.setTextColor(Color.parseColor(flag == 1 ? colorRed : colorHui));
    }

    //获取token
    private void getToken() {
        String url = Netconfig.qiNiuGetToken;
        httpHander.getHttpGson(this, url, "", 1);
    }

    //发表评价
    private void sendEstimate() {
        String comment = comment_text.getText().toString();

        String url = Netconfig.evaluateOrder;
        Map<String, Object> map = new HashMap<>();
        map.put("unique", unique);
        map.put("comment", comment);
        map.put("product_score", chooseFlag); //产品评分
        map.put("service_score", 5);    //商家评分
        map.put("pics", picPath);
        map.put("token", AppLibLication.getInstance().getToken());
        httpHander.okHttpMapPost(this, url, map, 2);
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
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
                case 2:
                    try {
                        Map ret = new Gson().fromJson(msg.obj.toString(), HashMap.class);
                        if (ret != null && getDouble(ret, "code") == 200) {
                            if (count != 0) {
                                tost("评价成功！上传" + count + "张图片失败");
                            } else {
                                tost("评价成功！");
                            }
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

    private void updatePic() {
        String comment = comment_text.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            tost("评价内容不能为空!");
            return;
        }

        if (mPicList.isEmpty()) {
            sendEstimate();
            return;
        }
        for (int i = 0; i < mPicList.size(); i++) {
            final int index = i;
            if (!TextUtils.isEmpty(token)) {
                final String key = Netconfig.PIC_PATH + System.currentTimeMillis() + UploadPicUtil.getRandomString() + Netconfig.PIC_FORM;
                UploadPicUtil.uploadPic(token, key, mPicList.get(i), new UpLoadPicCallback() {
                    @Override
                    public void result(int ret) {
                        if (ret == 0) {
                            picPath[index] = Netconfig.PIC_URL + key;
                            Log.e("~~~~~~~~~", "上传成功");
                        } else {
                            count++;
                            Log.e("~~~~~~~~~", "上传失败");
                        }
                        if (index == mPicList.size() - 1) {
                            handler.sendEmptyMessage(1);
                        }
                    }
                });
            } else {
                tost("获取token失败");
            }
        }
    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        adapter = new GridViewAdapter(this, mPicList);
        comment_pic.setAdapter(adapter);
        comment_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果“增加按钮形状的”图片的位置是最后一张，且添加了的图片的数量不超过5张，才能点击
                    if (mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM) {
                        //最多添加5张图片
                        viewPluImg(position);
                    } else {
                        //添加凭证图片
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    //查看大图
    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        intent.putExtra(MainConstant.PIC, 1);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> picList = PictureSelector.obtainMultipleResult(data);
                    if (picList != null) {
                        for (int i = 0; i < picList.size(); i++) {
                            mPicList.add(picList.get(i).getCompressPath());
                        }
                    }
                    // refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    adapter.refresh(mPicList);
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            adapter.notifyDataSetChanged();
            // updateImg();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://七牛上传图片完成计数
                    sendEstimate();
                    break;
            }
        }
    };

}
