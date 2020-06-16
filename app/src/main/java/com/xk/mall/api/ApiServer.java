package com.xk.mall.api;


import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.*;
import com.xk.mall.model.request.AddBankRequestBody;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.CommentRequestBody;
import com.xk.mall.model.request.CutOrderRequestBody;
import com.xk.mall.model.request.CutRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.model.request.GroupOrderRequestBody;
import com.xk.mall.model.request.InsertOrUpdateAddressRequestBody;
import com.xk.mall.model.request.InvitationRequestBody;
import com.xk.mall.model.request.LoginRequestBody;
import com.xk.mall.model.request.ManyAddCartBody;
import com.xk.mall.model.request.ModifyBuyerNumInBuyerCartRequestBody;
import com.xk.mall.model.request.ModifyOrderTypeRequestBody;
import com.xk.mall.model.request.OtherPayRequestBody;
import com.xk.mall.model.request.PayBackPostRequestBody;
import com.xk.mall.model.request.PersonalInfoRequestBody;
import com.xk.mall.model.request.RemindRequestBody;
import com.xk.mall.model.request.SaveShareRequestBody;
import com.xk.mall.model.request.SetPwdRequestBody;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.model.request.TransferCouponRequestBody;
import com.xk.mall.model.request.TransferRequestBody;
import com.xk.mall.model.request.UnbindRequestBody;
import com.xk.mall.model.request.UserAddressRequestBody;
import com.xk.mall.model.request.ValidCodeRequestBody;
import com.xk.mall.model.request.WXLoginRequestBody;
import com.xk.mall.model.request.WriteLogisticsRequestBody;
import com.xk.mall.model.request.ZeroGivePriceRequestBody;
import com.xk.mall.model.request.ZeroOrderRequestBody;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * File descripition:
 * <p>
 * \@GET  Observable    @Query
 * \@FormUrlEncoded     @POST   Observable  @FieldMap   @FieldMap HashMap<String, String> params
 * \@Multipart  @POST   Observable  @PartMap    @PartMap Map<String, RequestBody> map
 */

public interface ApiServer {
    @GET("/user/appVersionManage/getNewAppVersionInfo/2")
    Observable<BaseModel<UpdateAppBean>> checkVersion();
    //获取支付开关接口
    @GET("/user/users/paySwitch")
    Observable<BaseModel<PaySwitchBean>> getPaySwitch();

    //示例    多种类型请求方式

//    @POST("api/Activity/get_activities?")
//    Observable<BaseModel<List<>>> getApi1(@Query("time") String requestType);

    //获取行政区域接口  等级,1-省；2-市；3-区；4-街道
    @GET("/user/base/queryAreaByIevel/{level}")
    Observable<BaseModel<List<AddressServerBean>>> queryAreaByIeve(@Path("level") int level);

    //刷新token
    @GET("/auth/login/refreshToken")
    Observable<BaseModel<LoginBean>> refreshToken(@Query("refreshToken") String refreshToken);

    //获取验证码接口
    @POST("/auth/login/getValidCode")
    Observable<BaseModel> getValidCode(@Body ValidCodeRequestBody mobile);

    //设置支付密码获取验证码接口
    @GET("/user/users/sendVerificationCode")
    Observable<BaseModel> getSetPwdCode(@Query("mobile") String mobile);


    //检验设置支付密码验证码
    @GET("/user/users/verifyVerificationCode")
    Observable<BaseModel<Boolean>> verificationCodePayPwd(@Query("verificationCode") String code, @Query("mobile") String mobile);

    //设置支付密码
    @POST("/user/users/savePayPassword")
    Observable<BaseModel> savePayPwd(@Body SetPwdRequestBody setPwdRequestBody);


    //登录接口
    @POST("/auth/login/userLogin")
    Observable<BaseModel<LoginBean>> login(@Body LoginRequestBody loginRequestBody);

    //用户注册接口
    @POST("/auth/login/userRegister")
    Observable<BaseModel<LoginBean>> register(@Body InvitationRequestBody invitationRequestBody);

    //手机号是否已注册
    @GET("/auth/login/isRegist")
    Observable<BaseModel> isRegist(@Query("mobile") String mobile, @Query("type") int type);

    //微信登录接口
    @POST("/auth/wx/login")
    Observable<BaseModel<LoginBean>> wxLogin(@Body WXLoginRequestBody wxLoginRequestBody);

    //上传图片
    @Multipart
    @POST("user/oss/putObject")
    Observable<BaseModel<UploadLogoBean>> uploadImg(@Part List<MultipartBody.Part> partList);

    //喜扣素材上传图片
    @Multipart
    @POST("/promotion/advert/app/uploadUserImg")
    Observable<BaseModel<String>> uploadMaterialImg(@Part List<MultipartBody.Part> partList);

    //用户解绑第三方账号
    @POST("/user/users/unBindThirdRelation")
    Observable<BaseModel> unBind(@Body UnbindRequestBody unbindRequestBody);

    //根据用户ID修改用户信息
    @POST("/user/users/update/{id}")
    Observable<BaseModel> modifyPersonalInfo(@Path("id") String userId, @Body PersonalInfoRequestBody personalInfoRequestBody);

    //根据用户ID查询用户资料接口
    @GET("/user/users/user_data/{id}")
    Observable<BaseModel<UserServerBean>> getUserInfoById(@Path("id") String userId);

    //根据用户ID查询推荐人信息
    @GET("/user/users/findInvitationInfoById/{id}")
    Observable<BaseModel<CommendInfoBean>> getCommendInfo(@Path("id") String userId);

    //根据id查询我的页面用户信息
    @GET("/user/users/my_user/{id}")
    Observable<BaseModel<UserServerBean>> getMeUserInfoById(@Path("id") String userId);

    //根据用户ID查询账户信息
    @GET("/user/users/selectAccountByUserId/{id}")
    Observable<BaseModel<RedBagBean>> getRedBagById(@Path("id") String userId);

    //根据用户ID查询用户是否设置支付密码
    @GET("/user/users/selectPayPassworsByUserId/{id}")
    Observable<BaseModel<Boolean>> getSetPayPwd(@Path("id") String userId);


    //根据用户ID查询优惠券总额
    @GET("/user/users/queryCouponSumNumByUserId/{userId}")
    Observable<BaseModel<CouponTotalBean>> getCouponTotal(@Path("userId") String userId);

    //根据用户ID查询可使用的优惠券总额
    @GET("/user/users/queryUsableCouponSumByUserId/{userId}")
    Observable<BaseModel<Integer>> getCouponSum(@Path("userId") String userId);

    //根据用户ID查询可使用的优惠券
    @GET("/user/users/selectUsableBySelective")
    Observable<BaseModel<CouponListBean>> queryUsableBySelective(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //根据用户ID查询已使用的优惠券
    @GET("/user/users/selectUnusableBySelective")
    Observable<BaseModel<CouponListBean>> queryUnusableBySelective(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //根据用户ID查询已失效的优惠券
    @GET("/user/users/selectLostEfficacyBySelective")
    Observable<BaseModel<CouponListBean>> queryLostEfficacyBySelective(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //根据优惠券ID查询优惠券使用明细
    @GET("/user/users/queryCouponRecordByUserId/{id}")
    Observable<BaseModel<CouponDetailBean>> queryCouponRecordByCouponId(@Path("id") String couponId);

    //根据用户ID查询关注设计师列表信息
    @GET("/merchant/designer/designerFollows/{userId}")
    Observable<BaseModel<DesignerBean>> getAttentionListDataByID(@Path("userId") String userId, @Query("page") int page, @Query("limit") int limit);


    //根据用户ID查询关注店铺信息
    @GET("/merchant/merchant/shop/pageUserFollowShop")
    Observable<BaseModel<AttenShopListBean>> getAttenShopListDataByID(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //设计师圈列表
    @GET("/merchant/designer/queryDesignerCircleByList")
    Observable<BaseModel<DesignerCirclBean>> getDesignerCircleData(@Query("page") int page, @Query("limit") int limit);

    //设计师个人主页
    @GET("/merchant/queryDesignerHomePage/index")
    Observable<BaseModel<DesignerInfoBean>> getDesignerUserInfo(@Query("designerId") String designerId, @Query("userId") String userId);

    //关注设计师接口
    @POST("/merchant/designer/createDesignerFollow")
    Observable<BaseModel> addAttentionDesigner(@Body AttentionRequestBody attentionRequestBody);

    //设计师作品点赞 或取消
    @POST("/merchant/designerWork/workPraise")
    Observable<BaseModel> addDianZanOrCancel(@Body AttentionRequestBody attentionRequestBody);


    //获取店铺详情

    @GET("/merchant/merchant/shop/queryShopDetail")
    Observable<BaseModel<ShopBean>> getShopDetail(@Query("id") String shopId, @Query("userId") String userId);

    //关注店铺接口
    @POST("/merchant/merchant/createShopFollow")
    Observable<BaseModel> addAttentionShop(@Body AttentionRequestBody attentionRequestBody);

    /***附近 模块接口 */
    //查询附近的商铺列表
    @GET("/merchant/merchant/shop/queryPageBusiness")
    Observable<BaseModel<NearAddressBean>> getNearList(@Query("rate") int rate, @Query("pop") int pop, @Query("page") int page, @Query("limit") int limit,
                                                       @Query("longitude") double longitude, @Query("latitude") double latitude);

    //根据行业ID查询附近的商铺列表
    @GET("/merchant/merchant/shop/queryPageBusiness")
    Observable<BaseModel<NearAddressBean>> getNearListByType(@Query("rate") int rate, @Query("pop") int pop, @Query("page") int page, @Query("limit") int limit,
                                                             @Query("industry1") int industry, @Query("longitude") double longitude, @Query("latitude") double latitude);

    //搜索店铺
    @GET("/merchant/merchant/shop/queryPageShop")
    Observable<BaseModel<NearAddressBean>> searchShop(@Query("shopName") String shopName, @Query("longitude") double longitude,@Query("latitude") double latitude,
                                                      @Query("page") int page, @Query("limit") int limit);

    //查询所有行业信息
    @GET("/merchant/merchant/queryShopIndustryInfo")
    Observable<BaseModel<List<IndustryBean>>> getNearIndutry();

    //获取定制馆数据
    @GET("/merchant/designerWork/queryDesignerWorks")
    Observable<BaseModel<CustomGuanBean>> getCustomGuanData(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //获取某个设计师的作品
    @GET("/merchant/designerWork/queryDesignerWorks")
    Observable<BaseModel<CustomGuanBean>> getDesignerWorks(@Query("designerId") String designerId, @Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //根据设计师id 作品id 查询评论列表
    @GET("/merchant/merchant/designer/queryWorkCommentByDesigner")
    Observable<BaseModel<CommentsBean>> getWorkCommentList(@Query("workId") String workId, @Query("designerId") String designerId, @Query("page") int page, @Query("limit") int limit);

    //添加评论
    @POST("/merchant/merchant/designer/createWorkComment")
    Observable<BaseModel> addComments(@Body CommentRequestBody commentRequestBody);

    //根据id查询是否设置2级密码
    @GET("/user/users/selectPayPassworsByUserId/{id}")
    Observable<BaseModel<UserServerBean>> getIsSettingPwd(@Path("id") String userId);

    //消息模块
    //获取消息未读数
    @GET("/user/users/messageTpyesAndUnreadNum/{userId}")
    Observable<BaseModel<HomeMessageBean>> getUnreadMessage(@Path("userId") String userId);

    //分页查询消息列表
    @GET("/user/users/message/list")
    Observable<BaseModel<MessageBean>> getMessageList(@Query("userId") String userId, @Query("typeId") String typeId,
                                                      @Query("page") int page, @Query("limit") int limit);

    //查询消息详情
    @GET("/user/users/message/{id}")
    Observable<BaseModel<MessageBean.MessageChildBean>> getMessageDetail(@Path("id") String id, @Query("typeId") String typeId);


    /***用户地址模块接口*/
    //根据用户ID查询用户地址信息列表
    @GET("/user/userAddress/userAddressList/{userId}")
    Observable<BaseModel<List<AddressBean>>> getUserAddressList(@Path("userId") String userId);

    /**
     * 根据地址id 查询 地址信息
     *
     * @param addressId
     * @return
     */
    @GET("/user/userAddress/userAddress/{id}")
    Observable<BaseModel<AddressBean>> getUserAddressById(@Path("id") String addressId);

    //根据地址ID删除地址信息
    @POST("/user/userAddress/delete/{id}")
    Observable<BaseModel> deleteAddress(@Path("id") String addressId);

    //根据地址ID修改用户地址信息
    @POST("/user/userAddress/update/{id}")
    Observable<BaseModel> updateUserAddress(@Path("id") String addressId, @Body UserAddressRequestBody requestBody);

    //新增用户地址信息
    @POST("/user/userAddress/createUserAddress")
    Observable<BaseModel> insertUserAddress(@Body UserAddressRequestBody requestBody);

    /**
     * 0元拍 活动页面的接口
     **/
    //获取活动轮次接口
    @GET("/promotion/auction/round/query/{activityType}")
    Observable<BaseModel<List<ActivityRoundBean>>> getActivityType(@Path("activityType") int activityType);

    //根据活动轮次ID获取活动商品竞拍情况
    @GET("/promotion/auction/infosbyround/query/{id}")
    Observable<BaseModel<List<ActivityRoundBean>>> getGoodsInfo(@Path("id") String activityID);

    //根据轮次ID获取该轮次下的活动商品
    @GET("/promotion/auction/commodity/query/{roundId}")
    Observable<BaseModel<List<ZeroGoodsBean>>> getGoodsByRoundId(@Path("roundId") String roundId);

    //0 元拍 根据 轮询id 获取活动商品竞拍情况  得到当前竞拍价 剩余秒数
    @GET("/promotion/auction/infosbyround/query/{id}")
    Observable<BaseModel<List<ZeroCurrentPriceBean>>> getZeroFragGoodsInfo(@Path("id") String roundId);

    //根据商品ID获取活动商品竞拍情况----用于轮询获取竞拍情况
    @GET("/promotion/auction/info/query/{id}/{userId}")
    Observable<BaseModel<ZeroAuctionBean>> getZeroGoodsAuctionByGoodsId(@Path("id") String goodsId, @Path("userId") String userId);

    //0元拍商品详情接口
    @GET("/promotion/activity/commodity/queryCommodityDetails/{activityId}/{commodityId}/{activityType}/{goodsId}")
    Observable<BaseModel<ZeroGoodsDetailBean>> getZeroGoodsDetailByGoodsId(@Path("activityId") String activityId, @Path("commodityId") String commodityId,
                                                                           @Path("activityType") int activityType, @Path("goodsId") String goodsId);

    //新增或修改0元拍订单收货地址
    @POST("/order/order/addOrModifyDeliveryInfo")
    Observable<BaseModel> insertOrUpdateAddress(@Body InsertOrUpdateAddressRequestBody insertOrUpdateAddressBean);

    //校验收货地址是否超出配送范围
    @GET("/order/order/verifyUserAddressInfo")
    Observable<BaseModel> verifyUserAddressInfo(@Query("addressRef") String addressRef);

    //竞拍出价
    @POST("/promotion/auction/offer")
    Observable<BaseModel> givePrice(@Body ZeroGivePriceRequestBody rRequestBody);

    //根据活动商品ID获取活动商品出价记录 分页
    @GET("/promotion/auction/records/query/{id}/{limit}")
    Observable<BaseModel<List<ZeroAuctionBean.RecordListBean>>> getRecordByGoodsId(@Path("id") String goodsId, @Path("limit") int limit);

    //全球买手页面获取首页数据的接口
    @GET("/promotion/promotionCategory/queryGlobalBuyerActivitySection")
    Observable<BaseModel<GlobalBuyerServerBean>> getGlobalBuyerData();

    //全球买手商品详情接口
    @GET("/promotion/activity/commodity/queryCommodityDetailForSpu")
    Observable<BaseModel<GlobalBuyerGoodsDetailBean>> getGlobalBuyerGoodsDetailByGoodsId(@Query("id") String activityGoodsId,
                                                                                         @Query("activityType") int activityType, @Query("userId") String userId);

    //吾G分场次商品详情接口
    @GET("/promotion/promotion/commodity/queryPromotionCommodityDetail")
    Observable<BaseModel<GlobalBuyerGoodsDetailBean>> getWuGGoodsDetailByGoodsId(@Query("id") String activityGoodsId,
                                                                                 @Query("activityType") int activityType, @Query("userId") String userId);


    //0元拍 商品详情接口
    @GET("/promotion/activity/commodity/queryCommodityDetail")
    Observable<BaseModel<GlobalBuyerGoodsDetailBean>> getZeroGoodsDetailByGoodsId(@Query("id") String activityGoodsId,
                                                                                  @Query("activityType") int activityType, @Query("userId") String userId);

    //全球买手下单
    @POST("/order/order/createGlobalBuyerOrder")
    Observable<BaseModel<GlobalBuyerOrderResultBean>> orderGlobalBuyer(@Body GlobalBuyerOrderBean body);

    //全球买手查询订单列表
    @GET("/order/order/selectGlobalBuyerOrderPageToClient")
    Observable<BaseModel<OrderPageBean>> getGlobalBuyerOrderList(@Query("searchName") String searchName,
                                                                 @Query("buyerAccount") String buyerAccount,
                                                                 @Query("state") int state,
                                                                 @Query("createTimeFlag") int createTimeFlag,
                                                                 @Query("orderAmountL") String orderAmountL,
                                                                 @Query("orderAmountR") String orderAmountR,
                                                                 @Query("page") int page,
                                                                 @Query("limit") int limit);

    //查询全球买手订单详情
    @GET("/order/order/selectGlobalBuyerOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<GlobalBuyerOrderDetailBean>> getGlobalBuyerOrderDetail(@Path("orderNo") String orderNo);

    //取消订单
    @POST("/order/order/modifyOrderStateToCanceled")
    Observable<BaseModel<String>> cancelOrder(@Body CancelOrderRequestBody body);

    //提醒发货
    @POST("/order/order/modifyRemindShipment")
    Observable<BaseModel> remindShip(@Body RemindRequestBody remindRequestBody);

    //延长收货
    @POST("/order/order/extendTheTimeOfReceipt")
    Observable<BaseModel> extendTheTime(@Body RemindRequestBody remindRequestBody);

    //确认收货
    @POST("/order/order/modifyOrderStateToCompleted")
    Observable<BaseModel<String>> completeOrder(@Body CancelOrderRequestBody body);

    //查看物流
    @GET("/order/order/queryLogistics")
    Observable<BaseModel<LogisticsResultBean>> getLogistics(@Query("orderNo") String orderNo, @Query("orderType") int orderType);

    //删除订单
    @POST("/order/order/deleteOrder")
    Observable<BaseModel<String>> deleteOrder(@Body DeleteOrderRequestBody body);

    //修改全球买手订单处理方式
    @POST("/order/order/modifyGlobalBuyerProcessingMethod")
    Observable<BaseModel<ShareBean>> modifyOrderType(@Body ModifyOrderTypeRequestBody body);

    //修改我的寄卖商品订单处理方式
    @POST("/order/order/modifyBuyGiftOrderProcessingMethod")
    Observable<BaseModel<ShareBean>> modifySellOrderType(@Body ModifyOrderTypeRequestBody body);

    /**
     * 我的卖家接口
     */
    //查询我要寄卖订单信息
    @GET("/order/order/queryGlobalBuyerConsignmentOrderPage/{buyerAccount}")
    Observable<BaseModel<OrderPageBean>> getWantSell(@Path("buyerAccount") String buyerAccount, @Query("page") int page, @Query("limit") int limit);

    //查询我的寄卖订单列表信息
    @GET("/promotion/promotion/buyGiftCommodity/queryBuyGiftCommodity/{userId}")
    Observable<BaseModel<SellOrderPageBean>> getSelledOrderList(@Path("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    //提升排名接口
    @POST("/promotion/promotion/buyGiftCommodity/increaseRanking/{id}")
    Observable<BaseModel<RankResultBean>> increaseRanking(@Path("id") String id);

    //给好友购买，寄卖到吾G
    @GET("/promotion/promotion/buyGiftCommodity/shareFriendOrAll")
    Observable<BaseModel<ShareBean>> shareFriendOrAll(@Query("id") String commodityId, @Query("shareModel") int shareModel, @Query("userId") String userId);

    //我是卖家寄卖订单列表
    @GET("/order/order/queryMyConsignmentOrderPageToClient")
    ///order/order/queryGlobalBuyerConsignmentOrderPage/{buyerAccount}
    Observable<BaseModel<OrderPageBean>> getSellConsignmentOrderList(@Query("consignmentAccount") String buyerAccount,@Query("searchName") String searchName,
                                                                     @Query("state") int state, @Query("orderAmountL") String orderAmountL,
                                                                     @Query("orderAmountR") String orderAmountR, @Query("page") int page, @Query("limit") int limit);

    //我是卖家 寄卖订单详情
    @GET("/order/order/selectMyConsignmentOrderDetailByOrderNo/{orderNo}")
    Observable<BaseModel<SellOrderDetailBean>> getMySellOrderDetail(@Path("orderNo") String orderNo);

    @GET("/order/order/queryMyConsignmentOrderPageToClient")
    Observable<BaseModel<OrderPageBean>> getSellConsignmentOrderList(@QueryMap Map<String, Object> params);

    //分页查询代付订单列表
    @GET("/order/order/queryInsteadPaymentOrderPageToClient")
    Observable<BaseModel<DPayResultPageBean>> getInsteadPayment(@Query("buyerAccount") String buyerAccount, @Query("state") int state, @Query("page") int page,
                                                                @Query("limit") int limit);

    /**
     * 我的卖家接口
     */
    //根据不同条件查询订单列表信息


    //根据条件分页查询全球买手子页面活动商品
    @GET("/promotion/globalBuyer/queryPaging")
    Observable<BaseModel<GlobalBuyerChildPageBean>> getGlobalBuyerChildData(@Query("activityId") String activityId,
                                                                            @Query("categoryId") String categoryId, @Query("sortType") int sortType,
                                                                            @Query("sortMode") int sortMode, @Query("page") int page, @Query("limit") int limit);

    //根据条件分页查询全球买手子页面活动商品
    @GET("/promotion/globalBuyer/queryPaging")
    Observable<BaseModel<GlobalBuyerChildPageBean>> getGlobalBuyerChildData(@Query("activityId") String activityId,
                                                                            @Query("categoryId") String categoryId,
                                                                            @Query("page") int page, @Query("limit") int limit);

    //多买多折活动商品列表
    @GET("/promotion/moreDiscount/activityCommodity/query")
    Observable<BaseModel<ManyPageBean>> getManyBuyData(@Query("page") int page, @Query("limit") int limit);

    //查询吾G场次信息和开关
    @GET("/promotion/promotion/schedule/queryBuyGiftScheduleConfig")
    Observable<BaseModel<WuGConfigBean>> getWuGSchedule();

    //查询活动版块信息
    @GET("/promotion/promotionCategory/queryPromotionCategory")
    Observable<BaseModel<ActiveSectionBean>> getActiveSectionData(@Query("activityType") int activityType);

    //查询活动版块商品信息
    @GET("/promotion/promotionCategory/queryPagePromotionCategoryCommodity")
    Observable<BaseModel<ActiveSectionGoodsPageBean>> getActiveSectionGoods(@Query("categoryId") String categoryId, @Query("activityType") int activityType,
                                                                            @Query("userId")String userId,
                                                                            @Query("page") int page, @Query("limit") int limit);

    //查询吾G活动版块商品信息
    @GET("/promotion/promotion/commodity/queryCategoryCommodityPage")
    Observable<BaseModel<ActiveSectionGoodsPageBean>> getWuGActiveSectionGoods(@Query("scheduleId") String scheduleId, @Query("categoryId") String categoryId,
                                                                               @Query("activityType") int activityType, @Query("userId")String userId,
                                                                               @Query("page") int page, @Query("limit") int limit);

    //查询多买多折活动商品折扣费率
    @GET("/promotion/moreDiscount/activityCommodityDiscountRate/query")
    Observable<BaseModel<ManyBuyRateBean>> getManyBuyRate(@Query("activityId") String activity, @Query("commodityId") String commodityId);

////    //根据商品ID查询商品详情  多买多折，全球买手
//    @GET("/goods/merchant/goods/goods_id/{id}")
//    Observable<BaseModel<GoodsServerDetailBean>> getGoodsDetailByGoodsId(@Path("id") String goodsId);

//    //所有活动商品详情
    //根据商品ID查询商品详情  多买多折，全球买手
//    @GET("/goods/merchant/goods/goods_id/{id}")
//    Observable<BaseModel<GoodsServerDetailBean>> getGoodsDetailByGoodsId(@Path("id") String goodsId);

    //多买多折商品详情
    @GET("/promotion/activity/commodity/queryCommodityDetails/{activityId}/{commodityId}/{activityType}/{goodsId}")
    Observable<BaseModel<HuoDongGoodsBean>> getGoodsDetailByGoodsId(@Path("activityId") String activityId, @Path("commodityId") String commodityId,
                                                                    @Path("activityType") int activityType, @Path("goodsId") String goodsId);

    //多买多折 添加到购物车
    @POST("/promotion/moreDiscount/buyerCart/create")
    Observable<BaseModel> addToMoreCart(@Body ManyAddCartBody manyAddRequestBody);

    //修改多买多折购物车数量
    @POST("/promotion/moreDiscount/buyerCart/modifyBuyerNumInBuyerCart")
    Observable<BaseModel> modifyBuyerNumInBuyerCart(@Body ModifyBuyerNumInBuyerCartRequestBody modifyBuyerNumInBuyerCartRequestBody);

    //多买多折 查询购物车商品 by userId
    @GET("/promotion/moreDiscount/buyerCart/commodity/{buyerUserId}")
    Observable<BaseModel<List<ManyCartsBean>>> getCartListByUserId(@Path("buyerUserId") String userId);

    //删除多买多折 购物车  单个删除  根据 ManyCartsBean.getList(0).getId  删除
    @POST("/promotion/moreDiscount/buyerCart/clearBuyerCart/{id}")
    Observable<BaseModel<String>> deleteCartData(@Path("id") String id);

    //多买多折下单
    @POST("/order/order/createMoreBuyMoreDiscountOrders")
    Observable<BaseModel<ManyBuyOrderResultBean>> orderManyBuy(@Body ManyBuyOrderRequestBean manyBuyOrderRequestBean);

    //多买多折订单列表
    @GET("/order/order/selectBuyMoreFoldsOrderPageToClient")
    Observable<BaseModel<ManyBuyOrderBean>> getManyBuyOrderList(@QueryMap Map<String, Object> map);

//    //多买多折搜索订单列表
//    @GET("/order/order/selectBuyMoreFoldsOrderPageToClient")
//    Observable<BaseModel<ManyBuyOrderBean>> getManyBuyOrderList(@Query("searchName") String searchName,
//                                                                @Query("buyerAccount") String buyerAccount,
//                                                                @Query("state") int state,
//                                                                @Query("createTimeFlag") int createTimeFlag,
//                                                                @Query("orderAmountL") int orderAmountL,
//                                                                @Query("orderAmountR") int orderAmountR,
//                                                                @Query("page") int page,
//                                                                @Query("limit") int limit);

    //多买多折订单列表
    @GET("/order/order/selectBuyMoreFoldsOrderPageToClient")
    Observable<BaseModel<ManyBuyOrderBean>> getFilterManyBuyOrderList(@Query("buyerAccount") String buyerAccount, @Query("searchName") String searchName,
                                                                      @Query("createTimeFlag") int createTimeFlag, @Query("orderAmountL") int orderAmountL,
                                                                      @Query("orderAmountR") int orderAmountR,
                                                                      @Query("page") int page, @Query("limit") int limit);

    //查询多买多折订单详情
    @GET("/order/order/selectBuyMoreFoldsOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<List<ManyBuyOrderDetailBean>>> getManyBuyOrderDetail(@Path("orderNo") String orderNo);

    /*喜立得页面接口*/
    //喜立得商品列表
    @GET("/promotion/bargain/activityCommodity/query")
    Observable<BaseModel<CutPageBean>> getCutData(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    /*喜立得最新上架商品接口*/
    @GET("/promotion/bargain/activityNewCommodity/query")
    Observable<BaseModel<CutShangJiaBean>> getCutNewsData(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);

    /**
     * 用户发起砍价
     **/
    @POST("/promotion/bargain/saveUserBargain")
    Observable<BaseModel<CutSuccessBean>> goCut(@Body CutRequestBody body);

    /**
     * 喜立得商品详情接口
     **/
    @GET("/promotion/activity/commodity/queryCommodityDetails/{activityId}/{commodityId}/{activityType}/{goodsId}")
    Observable<BaseModel<CutGoodsDetailBean>> geCutGoodsDetail(@Path("activityId") String activityId, @Path("commodityId") String commodityId,
                                                               @Path("activityType") int activityType, @Path("goodsId") String goodsId);

    /**
     * 定制拼团商品详情接口
     **/
    @GET("/promotion/activity/commodity/queryCommodityDetailForSpu")
    Observable<BaseModel<GlobalBuyerGoodsDetailBean>> getGroupGoodsDetail(@Query("id") String activityGoodsId,
                                                                          @Query("activityType") int activityType, @Query("userId") String userId);

    //获取吾G页面列表数据
    @GET("/promotion/promotion/buyGiftCommodity/queryListNew")
    Observable<BaseModel<WuGPageBean>> getWuGData(@QueryMap Map<String, Object> map);

    //吾G商品详情接口
    @GET("/promotion/activity/commodity/queryCommodityDetails/{activityId}/{commodityId}/{activityType}/{goodsId}")
    Observable<BaseModel<WuGGoodsDetailBean>> getWuGGoodsDetail(@Path("activityId") String activityId, @Path("commodityId") String commodityId,
                                                                @Path("activityType") int activityType, @Path("goodsId") String goodsId);

    //吾G商品下单接口
    @POST("/order/order/createConsignmentOrder")
    Observable<BaseModel<WuGOrderResultBean>> orderWuG(@Body WuGOrderBean body);

    //吾G订单列表
    @GET("/order/order/selectConsignmentOrderPageToClient")
    Observable<BaseModel<OrderPageBean>> getWuGOrderList(@Query("searchName") String searchName,
                                                         @Query("buyerAccount") String buyerAccount,
                                                         @Query("state") int state,
                                                         @Query("createTimeFlag") int createTimeFlag,
                                                         @Query("orderAmountL") String orderAmountL,
                                                         @Query("orderAmountR") String orderAmountR,
                                                         @Query("page") int page,
                                                         @Query("limit") int limit);

    //查询吾G订单详情
    @GET("/order/order/selectConsignmentOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<GlobalBuyerOrderDetailBean>> getWuGOrderDetail(@Path("orderNo") String orderNo);

    //获取吾G订单额度
    @GET("/order/order/queryLimitAmount/{userId}")
    Observable<BaseModel<WuGOrderMoneyBean>> getWuGOrderMoney(@Path("userId") String userId);

    //定制拼团下单
    @POST("/order/order/createGroupOrder")
    Observable<BaseModel<ConfirmGroupOrderBean>> createGroupOrder(@Body GroupOrderRequestBody body);

    //新人专区商品下单接口
    @POST("/order/order/createNewComerOrder")
    Observable<BaseModel<WuGOrderResultBean>> orderNewPerson(@Body WuGOrderBean body);

    //新人专区订单列表
    @GET("/order/order/selectNewComerOrderPageToClient")
    Observable<BaseModel<OrderPageBean>> getNewOrderList(@Query("searchName") String searchName,
                                                         @Query("buyerAccount") String buyerAccount,
                                                         @Query("state") int state,
                                                         @Query("createTimeFlag") int createTimeFlag,
                                                         @Query("orderAmountL") String orderAmountL,
                                                         @Query("orderAmountR") String orderAmountR,
                                                         @Query("page") int page,
                                                         @Query("limit") int limit);

    //查询新人专区订单详情
    @GET("/order/order/selectNewComerOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<GlobalBuyerOrderDetailBean>> getNewOrderDetail(@Path("orderNo") String orderNo);

    /**
     * 0元拍下单
     **/
    @POST("/order/order/createAuctionOrder")
    Observable<BaseModel<String>> createZeroOrder(@Body ZeroOrderRequestBody body);

    /**
     * 获取首页数据
     **/
    @GET("/promotion/activity/commodity/queryHomePageActivity")
    Observable<BaseModel<HomeBean>> getHomeData();

    /**
     * 获取活动首页数据
     **/
    @GET("/promotion/activity/commodity/queryActivityPage")
    Observable<BaseModel<ActivityBean>> getActivityData();

    /**
     * 根据用户账号与state 获取拼团订单列表
     **/
    @GET("/order/order/selectFightGroupOrderPageToClient")
    Observable<BaseModel<FightGroupOrderBean>> getAllOrderListPT(@Query("searchName") String searchName,
                                                                 @Query("buyerAccount") String buyerAccount,
                                                                 @Query("state") int state,
                                                                 @Query("createTimeFlag") int createTimeFlag,
                                                                 @Query("orderAmountL") int orderAmountL,
                                                                 @Query("orderAmountR") int orderAmountR,
                                                                 @Query("page") int page,
                                                                 @Query("limit") int limit);

    /**
     * 查询拼团订单信息详情
     **/
    @GET("/order/order/selectFightGroupOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<GroupOrderDetailBean>> getGroupOrderDetail(@Path("orderNo") String orderNo);

    /**
     * 根据用户账号与state 获取0元拍 订单列表
     **/
    @GET("/order/order/selectAuctionOrderPageToClient")
    Observable<BaseModel<ZeroOrderBean>> getZeroOrderList(@QueryMap Map<String, Object> map);

    /**
     * 按状态分页查询订单信息（所有板块）
     */
    @GET("/order/order/queryUnifiedOrderPageToClient")
    Observable<BaseModel<OrderStateBean>> queryOrderListByState(@QueryMap Map<String, Object> map);


    /**
     * 查询0元拍订单信息详情
     **/
    @GET("/order/order/selectAuctionOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<ZeroOrderDetailBean>> getZeroOrderDetail(@Path("orderNo") String orderNo);


    /**
     * OTO下单接口
     */
    @POST("/order/order/createOtoOrder")
    Observable<BaseModel<OTOOrderResultBean>> orderOto(@Body OTOOrderRequestBean requestBean);

    /**
     * 根据用户账号与state 获取喜立得订单列表
     **/
    @GET("/order/order/selectBargainOrderPageToClient")
    Observable<BaseModel<CutOrderBean>> getCutOrderList(@QueryMap Map<String, Object> map);

    /**
     * 查询喜立得订单信息详情
     **/
    @GET("/order/order/selectBargainOrderDetailByOrderNoToClient/{orderNo}")
    Observable<BaseModel<CutOrderDetailBean>> getCutOrderDetail(@Path("orderNo") String orderNo);


    //OTO分页查询订单信息
    @GET("/order/order/queryOtoOrderPage/{buyerAccount}")
    Observable<BaseModel<OtoOrderPageBean>> getOrderList(@Path("buyerAccount") String buyerAccount,
                                                         @Query("page") int page, @Query("limit") int limit);


    /**
     * 获取轮播图
     *
     * @param moudle   1: 首页 2: 活动首页 3: 吾G频道
     * @param position 位置(1： 头部BANNER 2： 中部BANNER 3: 活动主图)
     */
    @GET("/promotion/banner/query/banners")
    Observable<BaseModel<List<BannerBean>>> getBannerList(@Query("moudle") int moudle, @Query("position") int position);

    /**
     * 查询用户参与砍价的信息
     *
     * @param userId     用户id
     * @param activityId 活动id
     * @param cutId      主页Id
     */
    @GET("/promotion/bargain/queryByUserIdAndCommodityId/{userId}/{activityId}/{commodityId}")
    Observable<BaseModel<CutSuccessBean>> getCutContinute(@Path("userId") String userId,
                                                          @Path("activityId") String activityId,
                                                          @Path("commodityId") String cutId);

    /**
     * 喜立得 下单
     */
    @POST("/order/order/createBargainOrder")
    Observable<BaseModel<ConfirmCutOrderBean>> createCutOrder(@Body CutOrderRequestBody body);

    /**
     * 获取用户砍价获得的红包金额
     */
    @GET("/promotion/bargain/queryBargainRecordByScheduleId/{scheduleId}")
    Observable<BaseModel<List<CutRedBean>>> getCutRedList(@Path("scheduleId") String scheduleId);

    /**
     * 爆品榜
     */
    @GET("/promotion/activity/rank/queryExplosive")
    Observable<BaseModel<List<BaoPinGoodsBean>>> getBaoPinRankList();


    /**
     * 热推榜
     */
    @GET("/promotion/activity/rank/queryHotPush")
    Observable<BaseModel<List<BaoPinGoodsBean>>> getHotPushList();

    /**
     * 喜赚榜
     */
    @GET("/promotion/activity/rank/queryHotEarn")
    Observable<BaseModel<List<BaoPinGoodsBean>>> getXiZuanList();

    //创建代付订单
    @POST("/order/order/createInitiatingInsteadOrder")
    Observable<BaseModel<OtherPayResultBean>> goOtherPay(@Body OtherPayRequestBody payRequestBody);

    //支付宝支付
    @POST("/order/payment/uniformPaymentOfOrders")
    Observable<BaseModel<PayResultBean>> aliPay(@Body PayOrderRequestBean requestBody);

    //支付宝支付
    @POST("/order/payment/uniformPaymentOfOrders")
    Observable<BaseModel<PayPingResultBean>> aliPayPing(@Body PayOrderRequestBean requestBody);

    //微信支付
    @POST("/order/payment/uniformPaymentOfOrders")
    Observable<BaseModel<PayResultBean>> wxPay(@Body PayOrderRequestBean payOrderRequestBean);

    //微信支付
    @POST("/order/payment/uniformPaymentOfOrders")
    Observable<BaseModel<PayPingResultBean>> wxPayPing(@Body PayOrderRequestBean payOrderRequestBean);

    //红包支付
    @POST("/order/payment/uniformPaymentOfOrders")
    Observable<BaseModel> redPay(@Body PayOrderRequestBean redPayRequestBean);

    //任务中心页面接口
    @GET("/user/users/tasks")
    Observable<BaseModel<TaskBean>> getTaskList(@Query("userId") String userId);

    //获取分享数据
    @POST("/user/share/queryShareLinkInfo")
    Observable<BaseModel<ShareBean>> getShareContent(@Body ShareRequestBody shareRequestBody);

    //分享回调接口
    @POST("/user/share/queryShareCallBack")
    Observable<BaseModel> shareCallback(@Body ShareSuccessBean shareSuccessBean);

    /**
     * 查询实名认证信息
     */
    @GET("/user/users/selectRealNameCertification")
    Observable<BaseModel<RealNameInfoBean>> getRealNameInfoByUserId(@Query("userId") String userId);

    /**
     * 保存实名认证 信息
     */
    @POST("/user/users/saveRealName")
    Observable<BaseModel<String>> saveRealName(@Body RealNameRequestBody requestBody);

    /**
     * 推广分享
     */
    @GET("/user/share/queryShareImage")
    Observable<BaseModel<PromotionShareBean>> getPromotionShare();

    //获取小程序二维码地址
    @GET("/user/users/qrcode/{id}")
    Observable<BaseModel<String>> getQRCode(@Path("id") String userId);

    /**
     * 根据用户ID查询我的提现账号
     */
    @GET("/user/users/selectSettleChannel/{id}")
    Observable<BaseModel<List<WithdrawAccountBean>>> getAccountList(@Path("id") String userId);

    /**
     * 用户申请提现
     */
    @POST("/user/cash/createCashOrder")
    Observable<BaseModel<String>> goWithDraw(@Body WithDrawRequestBean withDrawRequestBean);

    /**
     *  校验支付密码是否输入正确
     */
    @GET("/user/users/verifyPayPassword")
    Observable<BaseModel<String>> verifyPayPwd(@Query("userId") String userId,@Query("payPassword") String payPassword);

    /**
     * 删除我的提现账户
     */
    @POST("/user/users/deleteSettleChannel/{id}")
    Observable<BaseModel<String>> deleteWtihdrawAccount(@Path("id") String id);

    /**
     * 添加银行卡 发送验证码
     */
    @GET("/user/users/sendValidCode")
    Observable<BaseModel> addBankGetCode(@Query("mobile") String mobile);

    /**
     * 保存银行卡账户
     */
    @POST("/user/users/saveSettleChannel")
    Observable<BaseModel> saveBankAccout(@Body AddBankRequestBody requestBody);

    /**
     * 获取银行卡信息
     */
    @GET("/user/users/bankcode")
    Observable<BaseModel<ResultPageBean<BankBean>>> getBankList(@Query("name") String name, @Query("page") int page, @Query("limit") int limit);

    /**
     * 待结算明细
     */
    @GET("/user/users/queryAccountOnWayDetailByUserId")
    Observable<BaseModel<SettlementMxBean>> getSettlementDetailList(@Query("userId") String userId, @Query("page") int page, @Query("limit") int limit);
    /**
     * 待结算明细详情
     */
    @GET("/user/users/queryOnWayDetail")
    Observable<BaseModel<SettlementMxChildBean>> getSettlementDetail(@Query("moduleId") String moduleId, @Query("refKey") String refKey);

    /**
     * 提现月统计
     */
    @GET("/user/cash/queryCashCount/{userId}/{searchTime}")
    Observable<BaseModel<WithDrawCountBean>> getWithDrawMonthCount(@Path("userId") String userId, @Path("searchTime") String searchTime);
    /**
     * 提现明细
     */
    @GET("/user/cash/queryCashOrderList")
    Observable<BaseModel<WithDrawMxBean>> getWithDrawDetailList(@Query("userId") String userId, @Query("cashTime")String searchTime,
                                                                @Query("userType") int userType, @Query("page") int page, @Query("limit") int limit);

    /**
     * 提现明细详情
     */
    @GET("/user/cash/queryCashOrderDetail/{id}")
    Observable<BaseModel<WithDrawDetailBean>> getWithDrawDetail(@Path("id") String cashId);

    /**
     * 查询本月数据
     */
    @GET("/user/users/queryMonthAccountCount")
    Observable<BaseModel<MonthAccountBean>> queryMonthAccountCount(@Query("searchTime") String searchTime, @Query("userId") String userId);

    /***
     * 查询本月数据2
     */
    @GET("/user/users/queryMonthAccountCount")
    Observable<BaseModel<MonthAccountBean>> queryMonthAccountCountByType(@Query("searchTime") String searchTime, @Query("userId") String userId,
                                                                         @Query("businessTypeList") String businessTypeList);

    /**
     * 红包明细
     */
    @GET("/user/users/queryAccountReaPacket")
    Observable<BaseModel<RedBagMxBean>> getRedPagDetailList(@Query("userId") String userId, @Query("searchTime") String searchTime,
                                                            @Query("page") int page, @Query("limit") int limit);
    /**
     * 红包明细2
     */
    @GET("/user/users/queryAccountReaPacket")
    Observable<BaseModel<RedBagMxBean>> getRedPagDetailListByType(@Query("userId") String userId, @Query("searchTime") String searchTime,
                                                                  @Query("page") int page, @Query("limit") int limit, @Query("businessTypeList") String businessTypeList);

    /**
     * 红包明细详情
     */
    @GET("/user/users/queryRedPacketDetail")
    Observable<BaseModel<SettlementMxChildBean>> getRedPagDetail(@Query("id") String moduleId, @Query("refKey") String refKey);

    //首页搜索接口
    @GET("/promotion/activity/commodity/queryPagePromotionCommodityToSearch")
    Observable<BaseModel<HomeSearchGoodsPageBean>> homeSearch(@Query("commodityName") String commodityName, @Query("salesNumFlag") int salesNumFlag,
                                                              @Query("priceFlag") int priceFlag, @Query("newFlag") int newFlag,
                                                              @Query("page") int page, @Query("limit") int limit);

    //社交流量页面接口
    @GET("/user/circles/queryInfo/{id}")
    Observable<BaseModel<CommunityFlowBean>> getCommunity(@Path("id") String userId);

    //用户手机号码查询转账用户信息
    @GET("/user/users/queryUserByMobile/{mobile}")
    Observable<BaseModel<TransferInfoBean>> getUserByMobile(@Path("mobile") String mobile);

    //用户转账接口
    @POST("/user/users/accountTransport")
    Observable<BaseModel<TransferResultBean>> userTransfer(@Body TransferRequestBody requestBody);

    //用户转赠优惠券接口
    @POST("/user/users/coupon/transfer")
    Observable<BaseModel> userTransferCoupon(@Body TransferCouponRequestBody requestBody);

    //账户操作类型查询接口
    @GET("/user/users/queryAccountPaymentType")
    Observable<BaseModel<RedChooseBean>> getAccountType();

    //第一次申请退款接口
    @POST("/order/order/salesReturn")
    Observable<BaseModel<String>> salesReturn(@Body PayBackPostRequestBody requestBody);
    //查询退货订单列表
    @GET("/order/order/salesReturnQuery")
    Observable<BaseModel<OrderPageBean>> getPayBackOrderList(@Query("buyerAccount") String buyerAccount, @Query("orderType") int orderType,
                                                             @Query("page") int page, @Query("limit") int limit);

    //取消退货接口
    @GET("/order/order/cancelSalesReturn")
    Observable<BaseModel> cancelSalesReturn(@Query("refundOrderNo") String refundOrderNo);

    //单个退货订单查询
    @GET("/order/order/salesReturnQueryByOne")
    Observable<BaseModel<PayBackDetailBean>> getPayBackDetail(@Query("buyerAccount") String buyerAccount, @Query("orderType") int orderType,
                                                              @Query("refundOrderNo") String refundOrderNo);

    //我是卖家分页查询我的吾G订单信息(退款的)
    @GET("/order/order/queryMyConsignmentOrderPageToClientByRefund")
    Observable<BaseModel<OrderPageBean>> getSellPayBackList(@Query("consignmentAccount") String consignmentAccount,
                                                            @Query("page") int page, @Query("limit") int limit);

    //用户填写物流信息接口
    @POST("/order/order/writeLogisticsInfo")
    Observable<BaseModel> writeLogisticsInfo(@Body WriteLogisticsRequestBody requestBody);

    //获取用户不能寄卖到吾G文字提示
    @GET("/promotion/activity/commodity/queryConsignment/{userId}/{commodityId}")
    Observable<BaseModel<String>> getOutSellContent(@Path("userId")String userId, @Path("commodityId") String commodityId);

    //保存审核图片相关信息
    @POST("/promotion/advert/app/saveAuditImg")
    Observable<BaseModel> saveAuditImg(@Body SaveShareRequestBody requestBody);
    //获取审核列表数据
    @GET("/promotion/advert/app/getAuditImgList")
    Observable<BaseModel<ResultPageBean<ShareCheckBean>>> getShareList(@Query("userPhone") String userPhone,
                                                                       @Query("userName") String userName,
                                                                       @Query("auditStatus") String auditState,
                                                                       @Query("page") int page,
                                                                       @Query("limit") int limit);
    //获取素材配置文本
    @GET("/promotion/advert/app/getRemark")
    Observable<BaseModel<MaterialRemarkBean>> getRemarks(@Query("viewType") String viewType);
    //获取素材接口
    @GET("/promotion/advert/material/advertMaterialList")
    Observable<BaseModel<ResultPageBean<MaterialBean>>> getMaterial(@Query("page") int page, @Query("limit") int limit);
}
