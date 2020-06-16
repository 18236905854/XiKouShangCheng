package com.xk.mall.view.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.MaterialBean;
import com.xk.mall.model.entity.MaterialListBean;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.impl.MaterialViewImpl;
import com.xk.mall.presenter.MaterialPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.InstallUtil;
import com.xk.mall.utils.ScreenShotUtils;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.view.adapter.MaterialContentAdapter;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName XiKouMaterialActivity
 * Description  喜扣素材 页面
 * Author
 * Date
 * Version
 */
public class XiKouMaterialActivity extends BaseActivity<MaterialPresenter> implements MaterialViewImpl,CommonPopupWindow.ViewInterface {

    @BindView(R.id.refresh_material)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.rl_material_right)
    RelativeLayout rlMaterialRight;//提交记录
    @BindView(R.id.ll_material_left)
    LinearLayout llMaterialLeft;//左边返回按钮
    private CommonPopupWindow popupWindow;
    private List<String> images = new ArrayList<>();
    int IMAGE_NAME = 0;
    private int page = 1;
    private List<MaterialListBean> materialListBeans;//新数据源
    private List<MaterialBean> materialBeans;//老数据源
    private AdapterRecycler adapterRecycler;

    @Override
    protected MaterialPresenter createPresenter() {
        return new MaterialPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xikou_sucai;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("喜扣素材");
    }

    @Override
    protected void initData() {
        mPresenter.getMaterial(page, Constant.limit);
        materialListBeans = new ArrayList<>();
        materialBeans = new ArrayList<>();
        adapterRecycler = new AdapterRecycler(mContext, R.layout.item_recycler_view, materialListBeans);
        rvMaterial.setLayoutManager(new LinearLayoutManager(mContext));
        rvMaterial.addItemDecoration(new SpacesItemDecoration(ConvertUtils.dp2px(10)));
        rvMaterial.setAdapter(adapterRecycler);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getMaterial(page, Constant.limit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getMaterial(page, Constant.limit);
        });

    }

    @Override
    public void onGetMaterialSuccess(BaseModel<ResultPageBean<MaterialBean>> baseModel) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(baseModel != null && baseModel.getData() != null && baseModel.getData().getResult() != null){
            materialListBeans.clear();
            if(page == 1){
                materialBeans.clear();
            }
            materialBeans.addAll(baseModel.getData().getResult());
            materialListBeans.addAll(formartData(materialBeans));
            adapterRecycler.notifyDataSetChanged();
            if(baseModel.getData().getResult().size() < Constant.limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
        }
    }

    private List<MaterialListBean> formartData(List<MaterialBean> materialBeans){
        List<MaterialListBean> result = new ArrayList<>();
        Map<String, List<MaterialBean>> map = new HashMap<>();
        for(int  i = 0; i < materialBeans.size(); i++){
            MaterialBean materialBean = materialBeans.get(i);
            if(map.containsKey(DateToolUtils.dateToNYR(materialBean.getUpdateTime()))){
                List<MaterialBean> mapList = map.get(DateToolUtils.dateToNYR(materialBean.getUpdateTime()));
                if(mapList != null){
                    mapList.add(materialBean);
                }
            }else {
                List<MaterialBean> mapList = new ArrayList<>();
                mapList.add(materialBean);
                map.put(DateToolUtils.dateToNYR(materialBean.getUpdateTime()), mapList);
            }
        }

        for(String key : map.keySet()){
            MaterialListBean materialListBean = new MaterialListBean();
            materialListBean.setMaterialBeans(map.get(key));
            materialListBean.setTime(TimeUtils.string2Millis(map.get(key).get(0).getUpdateTime()));
            result.add(materialListBean);
        }
        Collections.sort(result, new Comparator<MaterialListBean>() {
            @Override
            public int compare(MaterialListBean o1, MaterialListBean o2) {
                return (o1.getTime() - o2.getTime()) > 0 ? -1 : 1;
            }
        });
        return result;
    }

    class AdapterRecycler extends CommonAdapter<MaterialListBean> {

        public AdapterRecycler(Context context, int layoutId, List<MaterialListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, MaterialListBean materialListBean, int position) {
            RecyclerView recyclerView = holder.getView(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            MaterialContentAdapter materialContentAdapter = new MaterialContentAdapter(mContext, R.layout.item_material, materialListBean.getMaterialBeans());
            recyclerView.setAdapter(materialContentAdapter);
            materialContentAdapter.setRvButtonListener((view, position1) -> {
                MaterialBean materialBean = materialListBean.getMaterialBeans().get(position1);
                images.clear();
                for(MaterialBean.AdvertImgModelsBean url : materialBean.getAdvertImgModels()){
                    images.add(url.getImgUrl());
                }
                if(view.getId() == R.id.ll_material_share){
                    //分享
                    showSharePop();
                }else if(view.getId() == R.id.ll_material_save){
                    //一键保存
                    saveImgs();
                }
            });
        }
    }

    @OnClick({R.id.rl_material_right, R.id.ll_material_left})
    public void clickView(View view) {
        if(view.getId() == R.id.rl_material_right){
            ActivityUtils.startActivity(MaterialPostActivity.class);
        }else if(view.getId() == R.id.ll_material_left){
            finish();
        }
    }

    /**
     * 保存图片
     */
    private void saveImgs(){
        setShowDialog(true);
        showLoading();
        if(images == null || images.size() == 0){
            ToastUtils.showShort("图片有误");
            return;
        }
        IMAGE_NAME = 0;
        for(int i = 0; i <= images.size() - 1; i++){
            //获取图片真正的宽高
            int finalI = i;
            Glide.with(mContext).asBitmap().load(images.get(i)).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    ScreenShotUtils.saveBitmapToSdCard(mContext, bitmap , "IMG" + IMAGE_NAME);
                    IMAGE_NAME++;
                    if(finalI == images.size() - 1){
                        EventBus.getDefault().post(1);
                    }
                }
            });
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void saveImgSuccess(Integer integer){
        setShowDialog(false);
        hideLoading();
        ToastUtils.showShort("图片保存成功，保存在本地xikou文件夹中");
    }

    /**
     * 分享弹窗
     */
    private void showSharePop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(mContext).inflate(R.layout.dialog_share_material, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.dialog_share_material)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        //防止PopupWindow被软件盘挡住
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        ImageView ivClose = view.findViewById(R.id.iv_share_close);
        LinearLayout llShareWeChat = view.findViewById(R.id.ll_share_cut_wechat);
        LinearLayout llShareCircle = view.findViewById(R.id.ll_share_cut_circle);
        LinearLayout llShareCutSina = view.findViewById(R.id.ll_share_cut_sina);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        llShareWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                //点击微信保存第一张图片，并分享
                sharePlatForm(SHARE_MEDIA.WEIXIN, images);
            }
        });

        llShareCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                sharePlatForm(SHARE_MEDIA.WEIXIN_CIRCLE, images);
            }
        });

        llShareCutSina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                sharePlatForm(SHARE_MEDIA.SINA, images);
            }
        });


    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtils.showShort("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.showShort("分享失败" + t.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.showShort("取消分享");
        }
    };

    /**
     * 分享图片到个个平台
     *
     * @param type     平台类型
     * @param images 文件列表
     */
    private void sharePlatForm(SHARE_MEDIA type, List<String> images) {
        if (type.equals(SHARE_MEDIA.WEIXIN) || type.equals(SHARE_MEDIA.WEIXIN_CIRCLE)) {
            if (!InstallUtil.isWeChatAppInstalled(mContext)) {
                ToastUtils.showShort("您未安装微信");
            } else {
                if(images != null && images.size() != 0){
                    UMImage image = new UMImage(mContext, images.get(0));//本地文件
                    new ShareAction(XiKouMaterialActivity.this)
                            .setCallback(shareListener).setPlatform(type).withMedia(image).share();
                }
            }

        } else if (type.equals(SHARE_MEDIA.SINA)) {
            if (!InstallUtil.uninstallSoftware(mContext, "com.sina.weibo")) {
                ToastUtils.showShort("您未安装新浪微博");

            } else {
                ShareAction shareAction = new ShareAction(XiKouMaterialActivity.this);
                List<UMImage> umImageList = new ArrayList<>();
                if(images != null && images.size() != 0){
                    for(String path : images){
                        umImageList.add(new UMImage(mContext, path));
                    }
                }
                shareAction.setPlatform(type).withText("今日分享")
                        .withMedias(umImageList.toArray(new UMImage[images.size()]))
                        .setCallback(shareListener).share();
//                new ShareAction(MyPromotionActivity.this).withText("22323232323")
//                        .setPlatform(type)
//                        .setCallback(shareListener).share();
            }
        }

    }

}
