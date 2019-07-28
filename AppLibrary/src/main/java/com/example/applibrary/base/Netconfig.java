package com.example.applibrary.base;

import org.json.JSONObject;

import java.util.Map;

//网络请求配置
public class Netconfig {
    //地址
//    public static final String httpHost = "http://demo.haoshusi.com/";
    public static final String httpHost = "http://api.haoshusi.com/";

    //子级地址
    private static final String subHost = "api/";
//    private static final String subHost = "mock/19/api/";
//    private static final String subHost = httpHost + "mock/19/api/";

    //地址就是标记
    private static final String endSite = "?";

    //通用头部参数
    public static final String headers = "Content-Type=application/x-www-form-urlencoded";


    //接口地址
    //个人中心
    public static final String personalCenter = subHost + "user/my" + endSite;
    //修改用户信息
    public static final String updataUserInfo = subHost + "user/edit_user" + endSite;

    //修改用户信息时获取用户信息
    public static final String updataGetUserInfo = subHost + "user/get_user" + endSite;

    //未读信息
    public static final String unreadMsg = subHost + "message/message_count" + endSite;
    //消息列表
    public static final String msgList = subHost + "message/message_list" + endSite;
    //消息详情并设置已读
    public static final String msgDetailsSetRead = subHost + "message/set_message" + endSite;

    //拍照骑牛上传获取token
    public static final String qiNiuGetToken = subHost + "index/uploadToken" + endSite;
    //获取客服token
    public static final String getServiceToken = subHost + "index/custome_token" + endSite;

    //主页
    public static final String homePage = subHost + "index" + endSite;
    public static final String recommend = subHost + "index/recommend" + endSite;
    public static final String like = subHost + "index/like" + endSite;
    //优选生活
    public static final String excellentLife = subHost + "index/life" + endSite;
    //健康生活
    public static final String healthLife = subHost + "index/healthyLife" + endSite;
    //年节礼包
    public static final String yearFestivalGiftBag = subHost + "index/year" + endSite;
    //旅游玩乐
    public static final String travelLibertinism = subHost + "index/travel" + endSite;
    //生日汇
    public static final String birthdayRange = subHost + "index/birthday" + endSite;

    //首页导航
    public static final String indexNav = subHost + "index/getCategory" + endSite;

    //品牌精选列表
    public static final String brandSiftList = subHost + "index/brandList" + endSite;
    //品牌精选详情
    public static final String brandSiftDetails = subHost + "index/brandInfo" + endSite;

    //秒杀时间列表页
    public static final String seckillTimeList = subHost + "seckill/seckill_index" + endSite;
    //秒杀商品列表
    public static final String seckillShopList = subHost + "seckill/seckill_list" + endSite;
    //秒杀商品详情
    public static final String seckillShopDetails = subHost + "seckill/seckill_detail" + endSite;

    //分类列表
    public static final String categoryList = subHost + "store/get_product_list" + endSite;
    //分类导航
    public static final String shoppingGuide = subHost + "category/category" + endSite;
    //商品搜索
    public static final String commoditySearch = subHost + "index/search" + endSite;
    //商品详情
    public static final String commodityDetails = subHost + "store/details" + endSite;

    //注册
    public static final String register = subHost + "login/register" + endSite;
    //注册获取验证码
    public static final String registerGetCode = subHost + "login/get_code" + endSite;
    //手机号登录
    public static final String phoneLogin = subHost + "login/login" + endSite;
    //手机登录获取验证码
    public static final String phoneLoginGetCode = subHost + "login/getPhoneCode" + endSite;
    //手机验证码登录
    public static final String phoneCodeLogin = subHost + "login/phoneLogin" + endSite;

    //忘记密码发送验证码
    public static final String getForgetCode = subHost + "login/getForgetCode" + endSite;
    public static final String getForgetPwd = subHost + "login/getForgetPwd" + endSite;
    //微信登录
    public static final String wechatLogin = subHost + "login/wxLogin" + endSite;


    //添加/修改地址
    public static final String addAndEditAddress = subHost + "user/edit_user_address" + endSite;
    //用户地址列表
    public static final String userAddressList = subHost + "user/user_address_list" + endSite;
    //设置默认地址
    public static final String setDefaultAddress = subHost + "user/set_user_default_address" + endSite;
    //获取默认地址
    public static final String getDefaultAddress = subHost + "user/user_default_address" + endSite;
    //删除收货地址
    public static final String delectAddress = subHost + "user/remove_user_address" + endSite;


    //订单列表
    public static final String orderList = subHost + "user/get_user_order_list" + endSite;
    //订单列表统计数据
    public static final String orderListStatistics = subHost + "auth/get_order_data" + endSite;
    //订单详情
    public static final String orderDetails = subHost + "user/get_order" + endSite;
    //订单查看物流
    public static final String orderLookLogistics = subHost + "user/express" + endSite;
    //订单确认收货
    public static final String orderConfirmReceipt = subHost + "user/user_take_order" + endSite;
    //订单取消订单(回退积分,回退优惠券,回退库存)
    public static final String orderCancel = subHost + "auth/cancel_order" + endSite;
    //订单删除订单
    public static final String orderDelete = subHost + "user/user_remove_order" + endSite;
    //评价订单
    public static final String evaluateOrder = subHost + "user/user_comment_product" + endSite;
    //评价列表
    public static final String evaluateList = subHost + "store/product_reply_list" + endSite;


    //确认订单1 - 提交商品属性
    public static final String confirmForm = subHost + "auth/now_buy" + endSite;
    //确认订单2
    public static final String confirmOrder = subHost + "auth/confirm_order" + endSite;
    //提交订单
    public static final String submitOrder = subHost + "auth/create_order" + endSite;

    //支付宝支付
    public static final String payAliPay = subHost + "pay/aliPay" + endSite;
    //微信支付
    public static final String payWeChat = subHost + "pay/wxPay" + endSite;
    //余额支付
    public static final String yuePay = subHost + "auth/yuePay" + endSite;

    //收藏商品列表
    public static final String collectShoppingList = subHost + "user/get_user_collect_product" + endSite;
    //添加商品收藏
    public static final String addShoppingCollect = subHost + "store/collect_product" + endSite;
    //取消商品收藏
    public static final String cancleShoppingCollect = subHost + "store/uncollect_product" + endSite;

    //优惠劵列表
    public static final String couponsList = subHost + "coupons/get_use_coupons" + endSite;
    //优惠劵领取
    public static final String couponsGet = subHost + "coupons/user_get_coupon" + endSite;
    //获取 可以领取的优惠券
    public static final String couponsOkGet = subHost + "coupons/get_issue_coupon_list" + endSite;
    public static final String AccessCoupon = subHost + "coupons/user_get_coupon" + endSite;

    //加入购物车
    public static final String addShoppingCar = subHost + "auth/set_cart" + endSite;
    //购物车列表
    public static final String shoppingCarList = subHost + "auth/get_cart_list" + endSite;
    //修改购物车数量
    public static final String shoppingCarUpdataNumber = subHost + "auth/change_cart_num" + endSite;
    //购物车删除
    public static final String shoppingCarDelete = subHost + "auth/remove_cart" + endSite;
    //获取购物车数量
    public static final String shoppingCarNumber = subHost + "auth/get_cart_num" + endSite;

    //充值余额
    public static final String topUp = subHost + "user/user_wechat_recharge" + endSite;
    //充值列表
    public static final String topUpList = subHost + "user/nowMoneyList" + endSite;

    //余额使用记录
    public static final String balanceUseRecord = subHost + "user/user_balance_list" + endSite;
    //积分使用记录
    public static final String integralUseRecord = subHost + "user/user_integral_list" + endSite;

    //积分商城列表
    public static final String integralShopList = subHost + "user/integral_list" + endSite;
    //提交积分商城订单
    public static final String integralShopOrder = subHost + "user/createIntegralOrder" + endSite;
    //积分商城获取默认地址
    public static final String integralShopDefault = subHost + "user/user_default_address_1560407597894" + endSite;
    public static final String exchangeCard = subHost + "user/exchangeCard" + endSite;
    public static final String cardOrderList = subHost + "user/cardOrderList" + endSite;

    //拼团列表
    public static final String pinTuanList = subHost + "pink/get_combination_list" + endSite;
    //拼团详情
    public static final String pinTuanDetails = subHost + "pink/combination_detail" + endSite;
    //取消拼团
    public static final String pinTuanCancel = subHost + "pink/remove_pink" + endSite;
    //开团页面
    public static final String kaiTuanActivity = subHost + "pink/get_pink" + endSite;
    //支付成功后获取订单状态
    public static final String getPinkStatus = subHost + "auth/get_order_pay_info" + endSite;
    //获取足迹列表
    public static final String footPrint = subHost + "user/footPrint" + endSite;
    //删除足迹
    public static final String delFootPrint = subHost + "user/delFootPrint" + endSite;
    //清空足迹
    public static final String delAllFootPrint = subHost + "user/delAllFootPrint" + endSite;
    //获取七牛token
    public static final String qiniuToken = subHost + "index/uploadToken" + endSite;
    public static final String checkYuE = subHost + "user/checkPayPss" + endSite;
    public static final String payPassCode = subHost + "user/payPassCode" + endSite;
    public static final String addPayPass = subHost + "user/addPayPass" + endSite;
    public static final String express = subHost + "user/express" + endSite;
    public static final String Authentic = subHost + "user/realName" + endSite;

    public static final String PIC_URL = "http://qiniu.haoshusi.com/";
    public static final String PIC_PATH = "Android/";
    public static final String PIC_FORM = ".jpg";


    /**
     * map转String请求
     *
     * @param isAdd 是否添加头部参数 ==true：添加
     * @param map   参数集合
     * @return
     */
    public static String mapToString(boolean isAdd, Map<String, String> map) {
        int index = 0;
        int ind = 1; //添加 & 的起始位置
        String result = "";
        if (isAdd) {
            result += headers;
            ind = 0;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (index >= ind)
                result += "&";
            result += (entry.getKey() + "=" + entry.getValue());
            index++;
        }
        return result;
    }

    /**
     * 拼装字符串参数，以键值对成对出现
     *
     * @param isAdd   是否添加头部参数 ==true：添加
     * @param strings 顺风车集合
     * @return
     */
    public static String assemble(boolean isAdd, String... strings) {
        String result = "";
        int index = 2;
        if (isAdd) {
            result = headers;
            index = 0;
        }
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i += 2) {
                if (i + 2 <= strings.length) {
                    if (i >= index)
                        result += "&";
                    String key = strings[i];
                    String value = strings[i + 1];
                    result += (key + "=" + value);
                }
            }
        }
        return result;
    }

}
