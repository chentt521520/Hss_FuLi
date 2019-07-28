package com.example.haoss.person.opinion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.other.PlusImageActivity;
import com.example.haoss.person.adpter.GridViewAdapter;
import com.example.haoss.person.other.MainConstant;
import com.example.haoss.person.other.PictureSelectorConfig;
import com.example.haoss.views.MyGridView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: HSS
 * time: 2019.5.13
 * page: com.example.haoss.person.opinion
 * blog: "好蔬食"
 * 意见反馈
 */
public class OpinionActivity extends BaseActivity {
    //输入信息
    @BindView(R.id.xq_content)
    EditText xqContent;
    //图片添加
    @BindView(R.id.add_img_btn)
    ImageView addImgBtn;
    //提交
    @BindView(R.id.opin_btn_tijiao)
    Button opinBtnTijiao;
    //添加的图片显示
    @BindView(R.id.add_img_gv)
    MyGridView addImgGv;
    private Context mContext;
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_opinion_page);
        ButterKnife.bind(this);
        mContext = this;
        getTitleView().setTitleText("意见反馈");
        initGridView();

    }

    //初始化展示上传图片的GridView
    private void initGridView() {
        mGridViewAddImgAdapter = new GridViewAdapter(mContext, mPicList);
        addImgGv.setAdapter(mGridViewAddImgAdapter);
        addImgGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        Intent intent = new Intent(mContext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        intent.putExtra(MainConstant.PIC, 1);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }

    /**
     * 打开相册或者照相机选择凭证图片，最多5张
     *
     * @param maxTotal 最多选择的图片的数量
     */
    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    @OnClick({R.id.add_img_btn, R.id.opin_btn_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_img_btn://图片添加
                break;
            case R.id.opin_btn_tijiao://提交
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> list = PictureSelector.obtainMultipleResult(data);
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            String s = list.get(i).getPath();
                            mPicList.add(s);
                        }
                    }
                    mGridViewAddImgAdapter.notifyDataSetChanged();
                    // refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
            // updateImg();
        }
    }
}
