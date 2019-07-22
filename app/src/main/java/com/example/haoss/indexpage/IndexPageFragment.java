package com.example.haoss.indexpage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.applibrary.custom.CustomerScrollView;
import com.example.haoss.base.AppLibLication;
import com.example.applibrary.base.BaseFragment;
import com.example.applibrary.base.ConfigHttpReqFields;
import com.example.applibrary.base.ConfigVariate;
import com.example.applibrary.base.Netconfig;
import com.example.applibrary.dialog.MyDialogTwoButton;
import com.example.applibrary.dialog.interfac.DialogOnClick;
import com.example.applibrary.httpUtils.HttpHander;
import com.example.applibrary.utils.ImageUtils;
import com.example.applibrary.utils.IntentUtils;
import com.example.applibrary.widget.freshLoadView.RefreshLayout;
import com.example.applibrary.widget.freshLoadView.RefreshListenerAdapter;
import com.example.haoss.R;
import com.example.haoss.goods.details.GoodsDetailsActivity;
import com.example.haoss.goods.goodslist.GoodsListActivity;
import com.example.haoss.goods.search.GoodsSearchActivity;
import com.example.haoss.indexpage.activity.ExcellentBrandActivity;
import com.example.haoss.indexpage.activity.ExcellentLifeActivity;
import com.example.haoss.indexpage.activity.FestivalGiftActivity;
import com.example.haoss.indexpage.activity.HealthLifeActivity;
import com.example.haoss.indexpage.activity.MakeUpActivity;
import com.example.haoss.indexpage.activity.BabyProductsActivity;
import com.example.haoss.indexpage.adapter.BrandAdapter;
import com.example.haoss.indexpage.adapter.CarouselAdapter;
import com.example.haoss.indexpage.adapter.FuncAdapter;
import com.example.haoss.indexpage.adapter.GridFavorAdapter;
import com.example.haoss.indexpage.entity.BannerInfo;
import com.example.haoss.indexpage.entity.IndexInfo;
import com.example.haoss.indexpage.entity.Recommond;
import com.example.haoss.indexpage.fragment.BannerFragment;
import com.example.haoss.indexpage.getcoupon.GetCouponCentreActivity;
import com.example.haoss.indexpage.specialoffer.NowSpecialOfferActivity;
import com.example.haoss.indexpage.tourdiy.GrouponListActivity;
import com.example.haoss.person.msg.PersonMsgActivity;
import com.example.haoss.views.MyGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;

/**
 * author: HSS
 * time: 2019.5.10
 * page: com.example.haoss.indexpage
 * blog: "好蔬食"
 */
//首页fragment
public class IndexPageFragment extends BaseFragment {
    private View indexView;
    private Context mContext;
    LinearLayout fistactivity_search_sys;//扫一扫
    RelativeLayout fistactivity_search_msg;   //消息
    TextView fistactivity_search_msgnumber; //消息数
    TextView fistactivity_search_cont;  //new 搜索
    private TextView fistpage_search;   //搜索按钮
    private ViewPager fistpage_carousel;    //轮播
    private RadioGroup fistpage_dot;    //小点
    private ImageView fistpage_dapai_image; //大牌图片
    private MyGridView fistpage_func, fistpage_sift, fistpage_like;   //5选项、品牌精品、喜欢
//    private RefreshLayout refreshLayout;

    ImageView fistpage_hdjx_limage, fistpage_hdjx_rimage; //精选活动大图、今日特价、拼团图片
    GifImageView fistpage_hdjx_bigimage;
    TextView fistpage_hdjx_snapup, fistpage_hdjx_prefecture;  //特价按钮、拼团按钮

    private List<Fragment> listCarousel = new ArrayList<>();    //轮播界面
    Timer timerBanner;  //轮播定时器
    FuncAdapter funcAdapter;    //导航功能适配器
    BrandAdapter brandAdapter;  //品牌精品适配器
    GridFavorAdapter likeAdapter;    //猜您喜欢适配器

    ArrayList<BannerInfo> listBannerInfo = new ArrayList<>();   //轮播图
    ArrayList<BannerInfo> brandUrlInfo = new ArrayList<>();   //轮播图
    ArrayList<IndexInfo> listFuncInfo = new ArrayList<>();   //5选项
    ArrayList<BannerInfo> listBrandInfo = new ArrayList<>(); //精选
    ArrayList<Recommond> listFavor = new ArrayList<>();   //喜欢
    MyDialogTwoButton myDialogTwoButton;    //登录提示对话框
    private AppLibLication application;
    private int page = 1;
    private CustomerScrollView scrollView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        application = AppLibLication.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (indexView == null) {
            indexView = LayoutInflater.from(mContext).inflate(R.layout.fragment_index_page, null);
            load(indexView);
        }
        return indexView;
    }

    //显示时刷新
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (indexView != null) {
                msgUnread();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        msgUnread();
    }

    //加载
    private void load(View view) {
        scrollView = view.findViewById(R.id.scroll_view);
        fistactivity_search_sys = view.findViewById(R.id.fistactivity_search_sys);
        fistactivity_search_msg = view.findViewById(R.id.fistactivity_search_msg);
        fistactivity_search_cont = view.findViewById(R.id.fistactivity_search_cont);
        fistactivity_search_msgnumber = view.findViewById(R.id.fistactivity_search_msgnumber);

        fistpage_search = view.findViewById(R.id.fistactivity_search_cont);
        fistpage_carousel = view.findViewById(R.id.fistpage_carousel);
        fistpage_func = view.findViewById(R.id.fistpage_func);
        fistpage_sift = view.findViewById(R.id.fistpage_sift);
        fistpage_like = view.findViewById(R.id.grid_view);
        fistpage_dapai_image = view.findViewById(R.id.fistpage_dapai_image);
        fistpage_dot = view.findViewById(R.id.fistpage_dot);

        //特价/拼团
        fistpage_hdjx_bigimage = view.findViewById(R.id.fistpage_hdjx_bigimage);
        fistpage_hdjx_limage = view.findViewById(R.id.fistpage_hdjx_limage);
        fistpage_hdjx_rimage = view.findViewById(R.id.fistpage_hdjx_rimage);
        fistpage_hdjx_snapup = view.findViewById(R.id.fistpage_hdjx_snapup);
        fistpage_hdjx_prefecture = view.findViewById(R.id.fistpage_hdjx_prefecture);
//        refreshLayout = view.findViewById(R.id.refresh_layout);

        fistactivity_search_sys.setOnClickListener(onClickListener);    //扫一扫
        fistactivity_search_msg.setOnClickListener(onClickListener);    //消息
        fistactivity_search_cont.setOnClickListener(onClickListener);   //搜索
        fistpage_search.setOnClickListener(onClickListener);    //搜索

        fistpage_func.setOnItemClickListener(onItemClickListener);
        fistpage_sift.setOnItemClickListener(onsiftClickListener);
        fistpage_like.setOnItemClickListener(onlikeClickListener);

        fistpage_dapai_image.setOnClickListener(onClickListener);   //商品精选大图监听
        fistpage_hdjx_bigimage.setOnClickListener(onClickListener);
        fistpage_hdjx_snapup.setOnClickListener(onClickListener);
        fistpage_hdjx_prefecture.setOnClickListener(onClickListener);
        view.findViewById(R.id.today_sales).setOnClickListener(onClickListener);
        view.findViewById(R.id.group_buying).setOnClickListener(onClickListener);
//        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
//
//            @Override
//            public void onLoadMore(RefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//                page++;
//                getRecommond();
//            }
//        });

        scrollView.setOnScrollListener(new CustomerScrollView.OnScrollListener() {
            @Override
            public void loadMore() {
                page++;
                getRecommond();
            }
        });
        likeAdapter = new GridFavorAdapter(getContext(), listFavor);
        fistpage_like.setAdapter(likeAdapter);

        String url = Netconfig.homePage + Netconfig.headers;
        httpHander.postHttpGson(mContext, url, "", 1);
        msgUnread();
        getSiftList();
        getRecommond();
    }

    HttpHander httpHander = new HttpHander() {
        @Override
        public void onSucceed(Message msg) {
            super.onSucceed(msg);
            switch (msg.arg1) {
                case 1:
                    analysisJson(msg.obj.toString());
                    break;
                case 2:
                    Map<String, Object> mapMsg = jsontoMap(msg.obj.toString());
                    if (mapMsg != null) {
                        if (getDouble(mapMsg, "code") == 200) {
                            fistactivity_search_msgnumber.setText((int) getDouble(mapMsg, "data") + "");
                        }
                    }
                    break;
                case 3:
                    analysisJson(msg.obj.toString());
                    break;

                case 4:
                    recommondJson(msg.obj.toString());
                    break;
            }
        }
    };

    private void recommondJson(String s) {
        ArrayList<Object> listLike = httpHander.jsonToList(s);   //猜您喜欢

        //"like":[{"id":2,"image":"http://datong.crmeb.net/public/uploads/attach/2019/01/15/5c3dbc27c69c7.jpg","store_name":"智能马桶盖 AI版","sort":0,"is_benefit":1,"is_show":1}
//        refreshLayout.finishLoadmore();
        if (listLike == null) {
            return;
        }

        for (int i = 0; i < listLike.size(); i++) {
            Map<String, Object> mapLike = (Map<String, Object>) listLike.get(i);
            if (mapLike != null) {
                Recommond recommond = new Recommond();
                recommond.setId((int) httpHander.getDouble(mapLike, "id"));
                recommond.setImage(httpHander.getString(mapLike, "image"));
                recommond.setStore_name(httpHander.getString(mapLike, "store_name"));
                recommond.setPrice(httpHander.getString(mapLike, "price"));
                recommond.setFicti((int) httpHander.getDouble(mapLike, "ficti"));
                listFavor.add(recommond);
            }
        }
        likeAdapter.refresh(listFavor);
    }


    //解析数据
    private void analysisJson(String json) {
        //解析
        Map<String, Object> map = httpHander.jsonToMap(json);
        if (map != null) {
            /**
             * 首页轮播图 4张
             */
            ArrayList<Object> listBanner = (ArrayList<Object>) map.get("banner");   //轮播
            /**
             * brandUrl
             */
            ArrayList<Object> brandUrl = (ArrayList<Object>) map.get("brandUrl");   //轮播
            /**
             * 三张图片：领券，特价，拼团
             */
            Map<String, Object> mapActivity = (Map<String, Object>) map.get("activity"); //精选活动

            ArrayList<Object> listNav = (ArrayList<Object>) map.get("nav"); //导航
            ArrayList<Object> listBrand = (ArrayList<Object>) map.get("brand"); //品牌精选


            //{"code":200,"msg":"ok","data":{"banner":[{"id":2,"type":1,"title":"22","imgUrl":"http://qiniu.haoshusi.com/timg.jpg","order":"2","add_time":1123,"status":"1"}
            //数据分装
            if (listBanner != null) {//轮播图
                for (int i = 0; i < listBanner.size(); i++) {
                    Map<String, Object> mapBanner = (Map<String, Object>) listBanner.get(i);
                    if (mapBanner != null) {
                        BannerInfo bannerInfo = new BannerInfo();
                        bannerInfo.setImgUrl(httpHander.getString(mapBanner, "imgUrl"));
                        bannerInfo.setCategory_id(httpHander.getString(mapBanner, "category_id"));
                        listBannerInfo.add(bannerInfo);
                    }
                }
                if (listBannerInfo.size() > 0) {
                    addFragment(listBannerInfo);
                }
            } else {
                fistpage_carousel.setVisibility(View.GONE);
            }

            if (brandUrl != null) {//轮播图
                for (int i = 0; i < brandUrl.size(); i++) {
                    Map<String, Object> mapBanner = (Map<String, Object>) brandUrl.get(i);
                    if (mapBanner != null) {
                        BannerInfo bannerInfo = new BannerInfo();
                        bannerInfo.setImgUrl(httpHander.getString(mapBanner, "imgUrl"));
                        bannerInfo.setCategory_id(httpHander.getString(mapBanner, "category_id"));
                        brandUrlInfo.add(bannerInfo);
                    }
                }
            }

            if (listNav != null) {//5选项
                for (int i = 0; i < listNav.size(); i++) {
                    Map<String, Object> mapNav = (Map<String, Object>) listNav.get(i);
                    if (mapNav != null) {
                        float id = Float.valueOf(httpHander.getString(mapNav, "id"));
                        String title = httpHander.getString(mapNav, "cate_name");
                        String imgUrl = httpHander.getString(mapNav, "pic");
                        IndexInfo funcInfo = new IndexInfo((int) id, title, imgUrl);
                        listFuncInfo.add(funcInfo);
                    }
                }
                if (listFuncInfo.size() > 0) {
                    if (funcAdapter == null) {
                        funcAdapter = new FuncAdapter(getContext(), listFuncInfo);
                        fistpage_func.setAdapter(funcAdapter);
                    } else
                        funcAdapter.notifyDataSetChanged();
                }
            } else
                fistpage_func.setVisibility(View.GONE);

            //"brand":[{"id":13,"pid":0,"cate_name":"服饰鞋帽","sort":7,"pic":"http://api.haoshusi.com/uploads/attach/2019/05/21/5ce3763fe14aa.png","is_show":1,"add_time":1553852314,"is_index":1,"is_life":"1"}
            if (listBrand != null) {//品牌精选
                for (int i = 0; i < listBrand.size(); i++) {
                    Map<String, Object> mapBrand = (Map<String, Object>) listBrand.get(i);
                    if (mapBrand != null) {
                        BannerInfo brandInfo = new BannerInfo();
                        brandInfo.setImgUrl(httpHander.getString(mapBrand, "imgUrl"));
                        brandInfo.setCategory_id(httpHander.getString(mapBrand, "category_id"));
                        listBrandInfo.add(brandInfo);
                    }
                }
                if (listBrandInfo.size() > 0) {
                    if (brandAdapter == null) {
                        brandAdapter = new BrandAdapter(getContext(), listBrandInfo);
                        fistpage_sift.setAdapter(brandAdapter);
                    } else
                        brandAdapter.notifyDataSetChanged();
                }
            } else
                fistpage_sift.setVisibility(View.GONE);


            if (mapActivity != null) {   //精选活动
                String couponUrl = httpHander.getString(mapActivity, "couponUrl");
                String Seckill = httpHander.getString(mapActivity, "Seckill");
                String pink = httpHander.getString(mapActivity, "pink");

                ImageUtils.imageLoad(mContext, Seckill, fistpage_hdjx_limage, 0, 0);
                ImageUtils.imageLoad(mContext, pink, fistpage_hdjx_rimage, 0, 0);
//                fistpage_hdjx_bigimage.setImageURI(Uri.parse(couponUrl));

            }
        }
    }

    //添加轮播图
    private void addFragment(final ArrayList<BannerInfo> listBannerInfo) {
        for (BannerInfo bannerInfo : listBannerInfo) {
            listCarousel.add(new BannerFragment(bannerInfo.getImgUrl()));
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setButtonDrawable(getResources().getDrawable(android.R.color.transparent));
            radioButton.setWidth(16);
            radioButton.setHeight(16);
            radioButton.setBackgroundResource(R.drawable.radiobutton_checked_on_off);
            fistpage_dot.addView(radioButton);
        }
        CarouselAdapter carouselAdapter = new CarouselAdapter(getChildFragmentManager(), listCarousel);
        fistpage_carousel.setAdapter(carouselAdapter);
        if (timerBanner != null)
            timerBanner.cancel();
        timerBanner = new Timer();
        index = 1;
        fistpage_dot.check(index);
        timerBanner.schedule(new TimerTask() {
            @Override
            public void run() {
                handlerBanner.sendEmptyMessage(index % listCarousel.size());
                index++;
            }
        }, 5000, 5000);
        //滑动监听
        fistpage_carousel.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                fistpage_dot.check(i + 1);    //联动
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        fistpage_carousel.setOnTouchListener(new View.OnTouchListener() {
            int flage = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        flage = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1;
                        break;
                    case MotionEvent.ACTION_UP:

                        if (flage == 0) {
                            int pos = fistpage_carousel.getCurrentItem();
                            String category_id = listBannerInfo.get(pos).getCategory_id();
                            Intent intent = new Intent(getContext(), GoodsListActivity.class);
                            intent.putExtra("searchType", (int) Double.parseDouble(category_id));
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void getRecommond() {
        String url = Netconfig.like;
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("limit", 20);
        httpHander.okHttpMapPost(mContext, url, map, 4);
    }


    //换图
    int index = 1;    //循环次数
    Handler handlerBanner = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            fistpage_carousel.setCurrentItem(msg.what);
        }
    };

    //未读信息
    private void msgUnread() {
        if (application.isLogin()) {
            String url = Netconfig.unreadMsg;
            HashMap<String, Object> map = new HashMap<>();
            map.put(ConfigHttpReqFields.sendToken, application.getToken());
            httpHander.okHttpMapPost(mContext, url, map, 2);
        }
    }

    //品牌精选列表
    private void getSiftList() {
        String url = Netconfig.brandSiftList + Netconfig.headers;
        httpHander.postHttpGson(mContext, url, "", 3);
    }

    //点击监听
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fistactivity_search_sys:  //扫一扫
                    //暂时用了测试、

                    break;
                case R.id.fistactivity_search_msg:  //消息
                    if (login())
                        return;
                    IntentUtils.startIntent(mContext, PersonMsgActivity.class);
                    break;
                case R.id.fistactivity_search_cont: //搜索
                case R.id.action_search_ss:
                    IntentUtils.startIntent(mContext, GoodsSearchActivity.class);
                    break;
                case R.id.fistpage_dapai_image: //商品精选列表
                    IntentUtils.startIntent(mContext, ExcellentBrandActivity.class);
                    break;
                case R.id.fistpage_hdjx_bigimage:  //活动精选大图(优惠劵)
                    if (login())
                        return;
                    IntentUtils.startIntent(mContext, GetCouponCentreActivity.class);
                    break;
                //今日特价与拼团功能未登录也可使用
                case R.id.fistpage_hdjx_snapup:  //今日特价，立即疯抢按钮
                case R.id.today_sales:
                    IntentUtils.startIntent(mContext, NowSpecialOfferActivity.class);
                    break;
                case R.id.fistpage_hdjx_prefecture://进入专区按钮
                case R.id.group_buying:
                    IntentUtils.startIntent(mContext, GrouponListActivity.class);
                    break;
            }
        }
    };

    //5选项监听
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int recommondId = listFuncInfo.get(position).getId();
            String title = listFuncInfo.get(position).getCate_name();
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putInt("id", recommondId);
            switch (recommondId) {
                case 34://优选生活
                    IntentUtils.startIntent(mContext, ExcellentLifeActivity.class, bundle);
                    break;
                case 43://健康
                    IntentUtils.startIntent(mContext, HealthLifeActivity.class, bundle);
                    break;
                case 48://美妆护肤
                    IntentUtils.startIntent(mContext, MakeUpActivity.class, bundle);
                    break;
                case 53://母婴用品
                    IntentUtils.startIntent(mContext, BabyProductsActivity.class, bundle);
                    break;
                case 59://节日礼包
                    IntentUtils.startIntent(mContext, FestivalGiftActivity.class, bundle);
                    break;
                default:
                    IntentUtils.startIntent(mContext, FestivalGiftActivity.class, bundle);
                    break;
            }
        }
    };

    //精品监听
    AdapterView.OnItemClickListener onsiftClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(mContext, GoodsListActivity.class);
            String category_id = listBrandInfo.get(position).getCategory_id();
            intent.putExtra("searchType", (int) Double.parseDouble(category_id));
            startActivity(intent);
        }
    };

    //喜欢监听
    AdapterView.OnItemClickListener onlikeClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            jumpGoodsDetails(listFavor.get(position).getId());
        }
    };

    /**
     * 切换到商品详情
     *
     * @param goodsId 商品Id
     */
    private void jumpGoodsDetails(int goodsId) {
        IntentUtils.startIntent(goodsId, mContext, GoodsDetailsActivity.class);
    }

    //未登录则先登录
    private boolean login() {
        if (!application.isLogin()) {//未登录
            if (myDialogTwoButton == null)
                myDialogTwoButton = new MyDialogTwoButton(mContext, "您还未登录，是否立即登录？", new DialogOnClick() {
                    @Override
                    public void operate() {
                        //未登录首先登录
                        IntentUtils.startIntentForResult(0, mContext,
                                IntentUtils.getIntentClass(mContext, ConfigVariate.packLogin), null, 1);
                    }

                    @Override
                    public void cancel() {

                    }
                });
            myDialogTwoButton.show();
            return true;
        }
        return false;
    }
}
