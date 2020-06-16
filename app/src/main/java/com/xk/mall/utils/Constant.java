package com.xk.mall.utils;

/**
 * ClassName Constant
 * Description 常量类，字符串
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class Constant {

    /**V2.0一次性使用的推出登录的Key**/
    public static final String V2_LOGIN_OUT = "v2_login_out";
    public static final String FOLLOW="follow";
    public static final String CANCEL="cancel";
    public static final String PRAISE="praise";//点赞

    public static final int limit=10;//每页显示条数
    public static final String  PAY_ORDER_NO="pay_order_no";//支付的订单号
    public static final String  PAY_ORDER_CUT="pay_order_cut";//喜立得支付
    public static final String  PAY_ORDER_TPYE="pay_order_type";//支付的订单号所属的类型
    public static final String  PAY_ORDER_COUPON="pay_order_coupon";//支付的优惠券
    public static final String  PAY_ORDER_MOUNT="pay_order_mount";//支付的实际金额

    /**用户协议地址*/
    public static final String protocolUrl = "https://m.luluxk.com/agreement/service.html";
    /**寄卖协议地址*/
    public static final String sellProtocolUrl = "https://m.luluxk.com/agreement/sales.html";
    /**提现常见问题*/
    public static final String commonProblem = "https://m.luluxk.com/agreement/AccountQuestion.html";
    /**提现常见问题*/
    public static final String couponProblem = "https://m.luluxk.com/agreement/CouponQuestion.html";
    /**品质保障地址*/
    public static final String qualityUrl = "https://static.luluxk.com/quality/index.html";
    /**关于页面地址*/
    public static final String aboutUrl = "https://m.luluxk.com/about.html";
    /**关于页面地址*/
    public static final String noticeUrl = "https://m.luluxk.com/agreement/Notice/CompanyNotice.html";
    /**sp中保存消息开关的key*/
    public static String IS_OPEN_MSG = "is_open_msg";
    /**sp中保存域名的key*/
    public static String SP_BASE_URL = "sp_base_url";
    /**上次获取用户信息的时间*/
    public static String SP_LAST_GET_INFO = "sp_last_get_info";

    /**sp中保存token的key*/
    public static String SP_TOKEN = "sp_token";
    /**sp中保存refreshToken的key*/
    public static String SP_REFRESH_TOKEN = "sp_refresh_token";
    /**sp中保存refreshToken上次刷新时间的key*/
    public static String SP_REFRESH_TOKEN_TIME = "sp_refresh_token_time";
    /**sp中保存useId的key*/
    public static String SP_USER_ID = "sp_user_id";
    /**sp中保存的微信解除绑定时间key*/
    public static String UNBIND_TIME = "unbind_time";
    /**sp中保存是否登录成功的key*/
    public static String IS_LOGIN = "is_login";
    /**sp中保存用户昵称的key*/
    public static final String NICK_NAME = "nick_name";
    /**sp中保存用户定位地址的key*/
    public static final String LOCATION = "location";
    /**sp中保存用户的优惠券的数量*/
    public static final String COUPON_NUM = "coupon_num";
    /**sp中保存用户的优惠券的总额*/
    public static final String COUPON_SUM = "coupon_sum";
    /**sp中保存用户头像的key*/
    public static final String HEAD_URL = "head_url";
    /**sp中保存上次获取行政区域的key*/
    public static String CONDITION_AREA = "condition_area";
    /**sp中保存上次获取行政区域的key*/
    public static String IS_HIDE_CASH = "IS_HIDE_CASH";


    /**传递地址Bean固定的key*/
    public static String INTENT_ADDRESS_BEAN = "intent_address_bean";
    /**传递用户昵称固定的key*/
    public static String INTENT_NAME = "intent_name";

    /**传递的标题固定的key*/
    public static String INTENT_TITLE = "intent_title";
    /**传递到静态网页页面的地址key*/
    public static String INTENT_URL = "intent_url";

    /**首页传递的消息的Key*/
    public static String HOME_MESSAGE = "home_message";

    /**定制馆页面传递设计师头像的key*/
    public static String INTENT_DESIGNER_HEAD = "intent_designer_head";
    /**定制馆页面传递设计师名字的key*/
    public static String INTENT_DESIGNER_NAME = "intent_designer_name";
    /**定制馆页面传递设计师是否关注的key*/
    public static String INTENT_DESIGNER_ATTENTION = "intent_designer_attention";
    /**定制馆页面传递设计师作品名称的key*/
    public static String INTENT_DESIGNER_WORKS_NAME = "intent_designer_works_name";

    /**定制馆页面传递设计师作品所有图片的key*/
    public static String INTENT_DESIGNER_IMGS = "intent_designer_imgs";
    /**定制馆页面传递设计师作品作品发布时间 key*/
    public static String WORK_TIME = "work_time";

    /**定制馆页面传递设计师作品作品描述 key*/
    public static final String WORK_DESCRIPTION = "work_description";
    /**定制馆页面传递设计师作品作品名称 key*/
    public static final String WORK_NAME = "work_name";
    //评论数量
    public static final String WORK_COMMENT_NUM="work_comment_num";
    //点赞数量
    public static final String WORK_PRAISE_NUM="work_praise_num";
    //是否点赞
    public static final String WORK_IS_PRAISE="work_is_praise";
    //设计师id
    public static final String WORK_DESIGNER_ID="work_designer_id";

    //作品id
    public static final String WORK_ID="work_id";


    //用户信息key
    public static String USER_REAL_NAME="is_realname";//是否实名认证
    public static String USER_SET_PWD="is_set_pwd";//是否设置支付密码
    public static String USER_MOBILE="user_mobile";//用户手机号
    public static String USER_INVITE_CODE="user_invite_code";//邀请码

    /**多买多折页面保存的折扣费率 key*/
    public static String RATE_ONE = "float_rate_one";
    /**多买多折页面保存的折扣费率 key*/
    public static String RATE_TWO = "float_rate_two";
    /**多买多折页面保存的折扣费率 key*/
    public static String RATE_THREE = "float_rate_three";
    /**店铺搜索历史的Key*/
    public static String SHOP_SEARCH_HISTORY = "shop_search_history";
    /**商品搜索历史的Key*/
    public static String HOME_SEARCH_HISTORY = "home_search_history";

    /**扫描结果的key*/
    public static String SCAN_RESULT = "scan_result";

    /**传递经度的key*/
    public static String INTENT_LONGITUDE = "intent_longitude";
    /**传递纬度的key*/
    public static String INTENT_LATITUDE = "intent_latitude";
    /**邀请码的key*/
    public static String BASE_CODE_KEY = "extcode=";
    /**本地保存在的用户代付搜索的名字和号码*/
    public static String PAY_SEARCH_PHONE_KEY = "pay_search_phone_key";
//    /**吾G商品详情页面测试地址*/
//    public static String WUG_SHARE_LOCAL_ADDRESS = "https://wx-test.luluxk.com/myGDetail?shareDetail=myGDetail?commodity=";
//    public static String WUG_SHARE_PRODUCT_ADDRESS = "https://wx.luluxk.com/myGDetail?shareDetail=myGDetail?commodity=";
//    /**喜立得商品详情页面地址*/
//    public static String CUT_SHARE_LOCAL_ADDRESS = "https://wx-test.luluxk.com/BargainBuyDetail?shareDetail=BargainBuyDetail?commodity=";
//    public static String CUT_SHARE_PRODUCT_ADDRESS = "https://wx.luluxk.com/BargainBuyDetail?shareDetail=BargainBuyDetail?commodity=";
//    /**0元拍商品详情页面地址*/
//    public static String ZERO_SHARE_LOCAL_ADDRESS = "https://wx-test.luluxk.com/ZeroBuyDetail?shareDetail=ZeroBuyDetail?commodity=";
//    public static String ZERO_SHARE_PRODUCT_ADDRESS = "https://wx.luluxk.com/ZeroBuyDetail?shareDetail=ZeroBuyDetail?commodity=";
//    /**全球买手商品详情页面地址*/
//    public static String GLOBAL_SHARE_LOCAL_ADDRESS = "https://wx-test.luluxk.com/GlobalBuyDetail?shareDetail=GlobalBuyDetail?commodity=";
//    public static String GLOBAL_SHARE_PRODUCT_ADDRESS = "https://wx.luluxk.com/GlobalBuyDetail?shareDetail=GlobalBuyDetail?commodity=";
//    /**多买多折商品详情页面地址*/
//    public static String MANY_SHARE_LOCAL_ADDRESS = "https://wx-test.luluxk.com/DiscountBuyDetail?shareDetail=DiscountBuyDetail?commodity=";
//    public static String MANY_SHARE_PRODUCT_ADDRESS = "https://wx.luluxk.com/DiscountBuyDetail?shareDetail=DiscountBuyDetail?commodity=";
//    /**新人专区商品详情页面地址*/
//    public static String NEW_SHARE_LOCAL_ADDRESS = "https://wx-test.luluxk.com/newPersonBuyDetail?shareDetail=newPersonBuyDetail?commodity=";
//    public static String NEW_SHARE_PRODUCT_ADDRESS = "https://wx.luluxk.com/newPersonBuyDetail?shareDetail=newPersonBuyDetail?commodity=";
}
