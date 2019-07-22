package com.example.haoss.classification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.applibrary.base.BaseFragment;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.custom.MyListView;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.utils.StringUtils;
import com.example.haoss.R;
import com.example.haoss.classification.adapter.AlcssifyInfoAdapter;
import com.example.haoss.classification.adapter.AlcssifyItemAdapter;
import com.example.haoss.classification.entity.ClassifyInfo;
import com.example.haoss.classification.entity.ClassifyItenInfo;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.indexpage.activity.BabyProductsActivity;
import com.example.haoss.indexpage.adapter.CarouselAdapter;
import com.example.haoss.indexpage.fragment.BannerFragment;
import com.example.haoss.views.MyGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.hss.fuli_hss.classification
 * blog: "好蔬食"
 */
//分类fragment
public class ClassIficationFragment extends BaseFragment {
    private Context mContext;
    private View classView;
    private TextView action_search_ss;  //搜索
    private ListView twopage_listview;    //左类型列表
    private ViewPager twopage_image;    //右大图
    private TextView twopage_title,twopage_more;    //右类型名，更多
    private GridView twopage_gridview;    //商品

    ArrayList<ClassifyInfo> listClassifyInfo = new ArrayList<>();   //分类数据
    AlcssifyInfoAdapter alcssifyInfoAdapter;    //左列表适配器
    AlcssifyItemAdapter alcssifyItemAdapter;    //子类适配器
    List<ClassifyItenInfo> listSub = new ArrayList<>(); //分类子数据

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext= getActivity();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(classView==null) {
            classView = LayoutInflater.from(mContext).inflate(R.layout.fragment_class_inifcation_page, null);
            load(classView);
        }
        return classView;
    }

    //控件加载
    private void load(View view){
        action_search_ss = view.findViewById(R.id.action_search_ss);
        twopage_listview = view.findViewById(R.id.twopage_listview);
        twopage_image = view.findViewById(R.id.twopage_image);
        twopage_title = view.findViewById(R.id.twopage_title);
        twopage_more = view.findViewById(R.id.twopage_more);
        twopage_gridview = view.findViewById(R.id.twopage_gridview);
        action_search_ss.setOnClickListener(onClickListener);
        twopage_more.setOnClickListener(onClickListener);
        twopage_listview.setOnItemClickListener(onItemClickListenerListView);
        twopage_gridview.setOnItemClickListener(onItemClickListenerGridView);
        String url = Netconfig.shoppingGuide + Netconfig.headers;
        httpHander.postHttpGson(mContext,url,"",1);
    }

    HttpHander httpHander = new HttpHander(){
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            analysisJson(msg.obj.toString());
        }
    };

    //解析
    private void analysisJson(String json){
        ArrayList<Object> list = httpHander.jsonToList(json);

        if(list!=null) {
            listClassifyInfo.clear();
            ArrayList<ClassifyInfo> classifyInfoList = new ArrayList<>();   //轮播数据
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = (Map<String, Object>) list.get(i);
                if(map != null) {
                    int id = (int) httpHander.getDouble(map,"id");
                    String name = httpHander.getString(map,"cate_name");
                    List<Object> listObject = (List<Object>) map.get("child");
                    //装父类数据
                    ClassifyInfo classifyInfo = new ClassifyInfo();
                    classifyInfo.setId(id);
                    classifyInfo.setName(name);
                    List<ClassifyItenInfo> classifyItenList = new ArrayList<>();
                    //装子类数据
                    if (listObject != null) {
                        for (int j = 0; j < listObject.size(); j++) {
                            Map<String, Object> mapSub = (Map<String, Object>) listObject.get(j);
                            if(mapSub != null) {
                                int subId = (int) httpHander.getDouble(mapSub,"id");
                                String subName = httpHander.getString(mapSub,"cate_name");
                                String subImage = httpHander.getString(mapSub,"pic");
                                ClassifyItenInfo classifyItenInfo = new ClassifyItenInfo();
                                classifyItenInfo.setId(subId);
                                classifyItenInfo.setName(subName);
                                classifyItenInfo.setPic(subImage);
                                classifyItenList.add(classifyItenInfo);
                            }
                        }
                    }
                    classifyInfo.setChildList(classifyItenList);
                    if (classifyInfo.getId() == 1)   //轮播
                        classifyInfoList.add(classifyInfo);
                    else    //分类
                        listClassifyInfo.add(classifyInfo);
                }
            }
            //适配
            if(alcssifyInfoAdapter == null){    //左列表适配
                alcssifyInfoAdapter = new AlcssifyInfoAdapter(mContext,listClassifyInfo);
                twopage_listview.setAdapter(alcssifyInfoAdapter);
            }

            listSub = listClassifyInfo.get(0).getChildList();
            twopage_title.setText(listClassifyInfo.get(0).getName());
            if(alcssifyItemAdapter == null){    //子类适配器
                alcssifyItemAdapter = new AlcssifyItemAdapter(mContext,listSub);
                twopage_gridview.setAdapter(alcssifyItemAdapter);
            }

            if(classifyInfoList.size()>0)   //适配轮播
                if(classifyInfoList.get(0).getChildList() != null && classifyInfoList.get(0).getChildList().size()>0)
                    addFragment(classifyInfoList.get(0).getChildList());
        }
    }

    //添加轮播图
    private void addFragment(List<ClassifyItenInfo> classifyItenList){
        for (ClassifyItenInfo classifyItem : classifyItenList) {
            listCarousel.add(new BannerFragment(classifyItem.getPic()));
        }
        CarouselAdapter carouselAdapter = new CarouselAdapter(getChildFragmentManager(),listCarousel);
        twopage_image.setAdapter(carouselAdapter);
        if(timerClassifyItem!=null)
            timerClassifyItem.cancel();
        timerClassifyItem = new Timer();
        index=1;
        timerClassifyItem.schedule(new TimerTask() {
            @Override
            public void run() {
                handlerBanner.sendEmptyMessage(index%listCarousel.size());
                index++;
            }
        },5000,5000);
    }


    //换图
    private List<Fragment> listCarousel = new ArrayList<>();    //轮播界面
    Timer timerClassifyItem;  //轮播定时器
    int index=1;    //循环次数
    Handler handlerBanner = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            twopage_image.setCurrentItem(msg.what);
        }
    };

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.action_search_ss:
                    IntentUtils.startIntent(mContext,GoodsSearchActivity.class);
                    break;
                case R.id.twopage_more:
                    tost("我是分类更多");
                    break;
            }
        }
    };

    //列表点击监听
    AdapterView.OnItemClickListener onItemClickListenerListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            alcssifyInfoAdapter.setChoose(position);
            alcssifyInfoAdapter.notifyDataSetChanged();
            listSub = listClassifyInfo.get(position).getChildList();
            twopage_title.setText(listClassifyInfo.get(position).getName());
            if (alcssifyItemAdapter == null) {
                alcssifyItemAdapter = new AlcssifyItemAdapter(mContext,listSub);
                twopage_gridview.setAdapter(alcssifyItemAdapter);
            }else {
                alcssifyItemAdapter.setData(listSub);
            }
        }
    };

    //gridview点击监听
    AdapterView.OnItemClickListener onItemClickListenerGridView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext, GoodsListActivity.class);
            intent.putExtra("searchType", listSub.get(position).getId());
            intent.putExtra("searchName", listSub.get(position).getName());
            startActivity(intent);
        }
    };
}
