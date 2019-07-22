package com.example.haoss.person.aftersale.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.aftersale.entity.AfterSaleInfo;

//售后申请界面
public class AfterSaleApplyForActivity extends BaseActivity {

    AfterSaleInfo afterSaleInfo;    //传入商品数据
    ImageView item_aftersale_image; //商品图片
    TextView item_aftersale_name;   //商品名称and规格
    TextView item_aftersale_number; //净含量

    LinearLayout aftersaleapplyforactivity_typechoose, aftersaleapplyforactivity_quitlinear;  //选项界面、退货/换货/退款

    //货物状态（aftersaleapplyforactivity_statetext）、退货原因（aftersaleapplyforactivity_causetext:可修改）
    TextView aftersaleapplyforactivity_state, aftersaleapplyforactivity_causetext, aftersaleapplyforactivity_cause;

    LinearLayout aftersaleapplyforactivity_moneylinear; //退款金额linear
    TextView aftersaleapplyforactivity_money;   //退款金额
    TextView aftersaleapplyforactivity_explaintext; //退款说明（可修改）
    EditText aftersaleapplyforactivity_explain; //退款说明输入

    ImageView aftersaleapplyforactivity_imageone, aftersaleapplyforactivity_imagetwo, aftersaleapplyforactivity_imagethree,
            aftersaleapplyforactivity_imagefour, aftersaleapplyforactivity_imagefive;   //图片1~5张

    int flagChoose = 0; //选择类型

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_aftersale_detail);
        init();
        getDate();
    }

    //初始化
    private void init() {
        this.getTitleView().setTitleText("服务类型");

        //商品
        findViewById(R.id.item_aftersale_operate).setVisibility(View.INVISIBLE);    //隐藏商品操作
        item_aftersale_number = findViewById(R.id.item_aftersale_number);
        item_aftersale_image = findViewById(R.id.item_aftersale_image);
        item_aftersale_name = findViewById(R.id.item_aftersale_name);

        aftersaleapplyforactivity_typechoose = findViewById(R.id.aftersaleapplyforactivity_typechoose);
        aftersaleapplyforactivity_quitlinear = findViewById(R.id.aftersaleapplyforactivity_quitlinear);
        aftersaleapplyforactivity_state = findViewById(R.id.aftersaleapplyforactivity_state);
        aftersaleapplyforactivity_causetext = findViewById(R.id.aftersaleapplyforactivity_causetext);
        aftersaleapplyforactivity_cause = findViewById(R.id.aftersaleapplyforactivity_cause);
        aftersaleapplyforactivity_moneylinear = findViewById(R.id.aftersaleapplyforactivity_moneylinear);   //关于退款金额
        aftersaleapplyforactivity_money = findViewById(R.id.aftersaleapplyforactivity_money);
        aftersaleapplyforactivity_explaintext = findViewById(R.id.aftersaleapplyforactivity_explaintext);
        aftersaleapplyforactivity_explain = findViewById(R.id.aftersaleapplyforactivity_explain);
        //图片
        aftersaleapplyforactivity_imageone = findViewById(R.id.aftersaleapplyforactivity_imageone);
        aftersaleapplyforactivity_imagetwo = findViewById(R.id.aftersaleapplyforactivity_imagetwo);
        aftersaleapplyforactivity_imagethree = findViewById(R.id.aftersaleapplyforactivity_imagethree);
        aftersaleapplyforactivity_imagefour = findViewById(R.id.aftersaleapplyforactivity_imagefour);
        aftersaleapplyforactivity_imagefive = findViewById(R.id.aftersaleapplyforactivity_imagefive);

        //3选择
        findViewById(R.id.aftersaleapplyforactivity_refund).setOnClickListener(onClickListener);    //退款（无需退货）
        findViewById(R.id.aftersaleapplyforactivity_refundandsalesreturn).setOnClickListener(onClickListener);    //退货退款
        findViewById(R.id.aftersaleapplyforactivity_barter).setOnClickListener(onClickListener);    //换货

        //内容点击
        //货物状态监听
        aftersaleapplyforactivity_state.setOnClickListener(onClickListener);
        findViewById(R.id.aftersaleapplyforactivity_statetext).setOnClickListener(onClickListener);
        //退货原因监听
        aftersaleapplyforactivity_causetext.setOnClickListener(onClickListener);
        aftersaleapplyforactivity_cause.setOnClickListener(onClickListener);
        //图片点击
        aftersaleapplyforactivity_imageone.setOnClickListener(onClickListener);
        aftersaleapplyforactivity_imagetwo.setOnClickListener(onClickListener);
        aftersaleapplyforactivity_imagethree.setOnClickListener(onClickListener);
        aftersaleapplyforactivity_imagefour.setOnClickListener(onClickListener);
        aftersaleapplyforactivity_imagefive.setOnClickListener(onClickListener);

        findViewById(R.id.aftersaleapplyforactivity_submit).setOnClickListener(onClickListener);    //提交

    }

    //获取传入数据
    private void getDate() {
        afterSaleInfo = (AfterSaleInfo) getIntent().getExtras().getSerializable(IntentUtils.intentActivityInfo);
        ImageUtils.imageLoad(this, afterSaleInfo.getImage(), item_aftersale_image, 0, 0);
        item_aftersale_name.setText(afterSaleInfo.getName() + " " + afterSaleInfo.getSpecification());
        aftersaleapplyforactivity_money.setText("¥ " + afterSaleInfo.getNumber() * afterSaleInfo.getMoney());  //设置金额
        item_aftersale_number.setText("净含量：  " + afterSaleInfo.getSpecification());
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    if (flagChoose == 0)
                        finish();
                    else
                        changeType(0);
                    break;
                case R.id.aftersaleapplyforactivity_refund:  //退款（无需退货）
                    changeType(1);
                    break;
                case R.id.aftersaleapplyforactivity_refundandsalesreturn:  //退货退款
                    changeType(2);
                    break;
                case R.id.aftersaleapplyforactivity_barter:  //换货
                    changeType(3);
                    break;
                case R.id.aftersaleapplyforactivity_state:  //货物状态监听
                case R.id.aftersaleapplyforactivity_statetext://货物状态
                    break;
                case R.id.aftersaleapplyforactivity_cause:  //退货原因监听
                case R.id.aftersaleapplyforactivity_causetext:
                    break;
                case R.id.aftersaleapplyforactivity_imageone:  //图1
                    break;
                case R.id.aftersaleapplyforactivity_imagetwo:  //图2
                    break;
                case R.id.aftersaleapplyforactivity_imagethree:  //图3
                    break;
                case R.id.aftersaleapplyforactivity_imagefour:  //图4
                    break;
                case R.id.aftersaleapplyforactivity_imagefive:  //图5
                    break;
                case R.id.aftersaleapplyforactivity_submit:  //提交
                    goBackWay();
                    break;
            }
        }
    };


    //返回方法
    private void goBackWay() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    //更改显示状态
    private void changeType(int flag) {
        flagChoose = flag;
        //0选项界面、1退款/2退款退货/3换货
        aftersaleapplyforactivity_typechoose.setVisibility(flag == 0 ? View.VISIBLE : View.GONE);   //显示选项隐藏界面
        aftersaleapplyforactivity_quitlinear.setVisibility(flag != 0 ? View.VISIBLE : View.GONE);   //隐藏选项显示界面
        aftersaleapplyforactivity_moneylinear.setVisibility(flag != 3 ? View.VISIBLE : View.GONE);  //退款金额隐藏
        aftersaleapplyforactivity_causetext.setText(flag == 3 ? "换货原因" : "退货原因");
        aftersaleapplyforactivity_explaintext.setText(flag == 3 ? "换货说明" : "退款说明");
    }
}
