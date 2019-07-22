package com.example.haoss.person.aftersale.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.ViewUtils;
import com.example.haoss.R;
import com.example.haoss.base.BaseActivity;
import com.example.haoss.person.aftersale.entity.AfterSaleInfo;

//售后详情界面
public class AfterSaleDetailsActivity extends BaseActivity {

    AfterSaleInfo afterSaleInfo;    //传入商品数据
    TextView aftersaledetailsactivity_result, aftersaledetailsactivity_disposetime;   //处理结果、处理时间
    LinearLayout aftersaledetailsactivity_moneylinear;  //总金额控件
    TextView aftersaledetailsactivity_money;    //总金额
    TextView aftersaledetailsactivity_cancelthat;   //取消说明
    TextView aftersaledetailsactivity_info; //类型处理标题

    //商品信息
    ImageView item_aftersale_image; //商品图片
    TextView item_aftersale_name;   //商品名称and规格
    TextView item_aftersale_number; //净含量

    TextView aftersaledetailsactivity_cause, aftersaledetailsactivity_applyformoney, aftersaledetailsactivity_number,
            aftersaledetailsactivity_applyfortime, aftersaledetailsactivity_odd;    //原因、金额、数量、时间、编号

    String revocation = "您已撤销本次申请，如问题仍未解决，售后保障期内，您可以从新发起售后申请。";
    String beDefeated = "本次申请失败，售后保障期内，您可以从新发起售后申请。";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleContentView(R.layout.activity_aftersale_detail);
        init();
        getDate();
    }

    private void init() {
        this.getTitleView().setTitleText("退款详情");

        //结果
        aftersaledetailsactivity_result = findViewById(R.id.aftersaledetailsactivity_result);
        aftersaledetailsactivity_disposetime = findViewById(R.id.aftersaledetailsactivity_disposetime);
        //总金额、说明
        aftersaledetailsactivity_moneylinear = findViewById(R.id.aftersaledetailsactivity_moneylinear);
        aftersaledetailsactivity_money = findViewById(R.id.aftersaledetailsactivity_money);
        aftersaledetailsactivity_cancelthat = findViewById(R.id.aftersaledetailsactivity_cancelthat);
        //标题
        aftersaledetailsactivity_info = findViewById(R.id.aftersaledetailsactivity_info);
        //商品
        findViewById(R.id.item_aftersale_operate).setVisibility(View.INVISIBLE);    //隐藏商品操作
        item_aftersale_number = findViewById(R.id.item_aftersale_number);
        item_aftersale_image = findViewById(R.id.item_aftersale_image);
        item_aftersale_name = findViewById(R.id.item_aftersale_name);

        aftersaledetailsactivity_cause = findViewById(R.id.aftersaledetailsactivity_cause);
        aftersaledetailsactivity_applyformoney = findViewById(R.id.aftersaledetailsactivity_applyformoney);
        aftersaledetailsactivity_number = findViewById(R.id.aftersaledetailsactivity_number);
        aftersaledetailsactivity_applyfortime = findViewById(R.id.aftersaledetailsactivity_applyfortime);
        aftersaledetailsactivity_odd = findViewById(R.id.aftersaledetailsactivity_odd);

        aftersaledetailsactivity_moneylinear.setVisibility(View.GONE);
        aftersaledetailsactivity_cancelthat.setVisibility(View.GONE);
        aftersaledetailsactivity_applyformoney.setVisibility(View.GONE);
    }

    //获取传入数据
    private void getDate() {
        afterSaleInfo = (AfterSaleInfo) getIntent().getExtras().getSerializable(IntentUtils.intentActivityInfo);
        ImageUtils.imageLoad(this, afterSaleInfo.getImage(), item_aftersale_image, 0, 0);
        item_aftersale_name.setText(afterSaleInfo.getName() + " " + afterSaleInfo.getSpecification());
//        aftersaleapplyforactivity_money.setText("¥ " + afterSaleInfo.getNumber()*afterSaleInfo.getMoney());  //设置金额
        item_aftersale_number.setText("净含量：  " + afterSaleInfo.getSpecification());

        afterSaleInfo.setQuitApplyForTime("2017-09-08 25:32:09");
        afterSaleInfo.setQuitCause("送多了");
        afterSaleInfo.setQuitDisposeTime("2017年08月15日 25:32:12");
        afterSaleInfo.setQuitMoney("-100");
        afterSaleInfo.setQuitOdd("tt1234567890");
        String type = "";
        String status = "";
        boolean isBarter = false;   //是否是换货 ==true：是
        switch (afterSaleInfo.getApplyForType()) {   //申请类型 1退款/2退款退货/3换货
            case 1:
                type = "退款";
                aftersaledetailsactivity_info.setText("退款信息");
                break;
            case 2:
                type = "退款退货";
                aftersaledetailsactivity_info.setText("退款退货信息");
                break;
            case 3:
                isBarter = true;
                type = "换货";
                aftersaledetailsactivity_info.setText("换货信息");
                break;
        }
        switch (afterSaleInfo.getStatus()) { //处理状态 0：未知，1：成功，2：失败，3：关闭
            case 1:
                status = type + "成功";
                if (!isBarter) {
                    aftersaledetailsactivity_moneylinear.setVisibility(View.VISIBLE);
                    aftersaledetailsactivity_money.setText("¥ " + afterSaleInfo.getQuitMoney());
                    aftersaledetailsactivity_applyformoney.setVisibility(View.VISIBLE);
                    aftersaledetailsactivity_applyformoney.setText("退款金额：¥ " + afterSaleInfo.getMoney() + afterSaleInfo.getNumber());
                }
                break;
            case 2:
                status = type + "失败";
                aftersaledetailsactivity_cancelthat.setVisibility(View.VISIBLE);
                aftersaledetailsactivity_cancelthat.setText(beDefeated);
                break;
            case 3:
                status = type + "关闭";
                aftersaledetailsactivity_cancelthat.setVisibility(View.VISIBLE);
                aftersaledetailsactivity_cancelthat.setText(revocation);
                break;
        }
        aftersaledetailsactivity_result.setText(status);
        aftersaledetailsactivity_disposetime.setText(afterSaleInfo.getQuitDisposeTime());
        aftersaledetailsactivity_cause.setText("申请原因：" + afterSaleInfo.getQuitCause());
        aftersaledetailsactivity_number.setText("申请数量：" + afterSaleInfo.getNumber());
        aftersaledetailsactivity_applyfortime.setText("申请时间：" + afterSaleInfo.getQuitApplyForTime());
        aftersaledetailsactivity_odd.setText(type + "编号：" + afterSaleInfo.getQuitOdd());
    }

    //点击事件
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_title_goback:  //返回
                    finish();
                    break;
            }
        }
    };

}
