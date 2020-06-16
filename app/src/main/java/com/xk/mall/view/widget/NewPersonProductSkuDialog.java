package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.sku.OnSkuListener;
import com.xk.mall.view.widget.sku.SkuSelectScrollView;
import com.xk.mall.view.widget.sku.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 吾G sku  规格 弹窗
 */
public class NewPersonProductSkuDialog extends Dialog {
    private static final String TAG = "WuGProductSkuDialog";
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private Context context;
    private GlobalBuyerGoodsDetailBean product;
    private List<GoodsSkuListBean2> skuList;
    private Callback callback;

    private GoodsSkuListBean2 selectedSku;

    private LinearLayout dialog_root;
    //view id
    private TextView tv_goods_name, tv_goods_price, tv_transport, tvSkuQuantity;
    private ImageView iv_sku_logo, tv_sku_close, btnSkuQuantityMinus, btnSkuQuantityPlus;
    //    private Button btnSubmit;
    private EditText etSkuQuantityInput;
    private SkuSelectScrollView scrollSkuList;
    private TextView tvLimit;//限购数量
    private TextView tvCoupon;//用优惠券
    private int limit = 0;//限购数量
    private boolean isGlobal;
//    private int stock = 0;//库存
//    private int price = 0;//商品单价

//    public void setStock(int stock) {
//        this.stock = stock;
//        if(stock <= 0){
//            btnSubmit.setEnabled(false);
//            btnSubmit.setText("已售罄");
//            btnSubmit.setBackgroundResource(R.drawable.bg_login_btn_disable);
//        }
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }

    public void setIsGlobal(boolean isGlobal){
        this.isGlobal = isGlobal;
    }

    private void setCoupon(int coupon, boolean isGlobal) {
        if (coupon == 0) {
            tvCoupon.setVisibility(View.GONE);
        } else {
            tvCoupon.setVisibility(View.VISIBLE);
            if (isGlobal) {
                tvCoupon.setText("用优惠券" + PriceUtil.dividePrice(coupon));
            } else {
                tvCoupon.setText("赠优惠券" + PriceUtil.dividePrice(coupon));
            }
        }
    }

    public void setLimitNum(int limitNum) {
        if (limitNum == 0) {
            tvLimit.setVisibility(View.GONE);
        } else {
            limit = limitNum;
            tvLimit.setVisibility(View.VISIBLE);
            tvLimit.setText("限购" + limitNum + "件");
        }
    }

    /**
     * 打开开关
     */
    public void setPaySwitch(boolean isOpen) {
        if (isOpen) {
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundResource(R.drawable.bg_login_btn);
        } else {
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundResource(R.drawable.bg_login_btn_disable);
        }
    }

    public NewPersonProductSkuDialog(@NonNull Context context) {
        this(context, R.style.CommonBottomDialogStyle);
    }

    public NewPersonProductSkuDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_wug_product_sku, null);
        ButterKnife.bind(this,view);
        setContentView(view);
        dialog_root = view.findViewById(R.id.dialog_root);
        tv_sku_close = view.findViewById(R.id.tv_sku_close);
        tv_goods_name = view.findViewById(R.id.tv_goods_name);
        tv_goods_price = view.findViewById(R.id.tv_goods_price);
        tv_transport = view.findViewById(R.id.tv_transport);//邮费
        tvSkuQuantity = view.findViewById(R.id.tv_stock);//库存
        iv_sku_logo = view.findViewById(R.id.iv_sku_logo);
        btnSkuQuantityMinus = view.findViewById(R.id.btn_sku_quantity_minus);//-
        btnSkuQuantityPlus = view.findViewById(R.id.btn_sku_quantity_plus);//+
//        btnSubmit = view.findViewById(R.id.btn_submit);
        etSkuQuantityInput = view.findViewById(R.id.et_sku_quantity_input);
        scrollSkuList = view.findViewById(R.id.scroll_sku_list);
        tvLimit = view.findViewById(R.id.tv_limit_buy_num);
        tvCoupon = view.findViewById(R.id.tv_gift_coupon);

        dialog_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //×
        tv_sku_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //减少
        btnSkuQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                } else {
                    ToastUtils.showShort("最低选择1件");
                }
            }
        });
        //加
        btnSkuQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity) || selectedSku == null) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < selectedSku.getStock()) {
                    if (limit == 0 || quantityInt < limit) {
                        String newQuantity = String.valueOf(quantityInt + 1);
                        etSkuQuantityInput.setText(newQuantity);
                        etSkuQuantityInput.setSelection(newQuantity.length());
                        updateQuantityOperator(quantityInt + 1);
                    } else{
                        ToastUtils.showShort("当前商品限购哦");
                    }
                } else{
                    ToastUtils.showShort("库存不够啦");
                }
            }
        });

        //输入数量
        etSkuQuantityInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE || selectedSku == null) {
                    return false;
                }
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    etSkuQuantityInput.setText("1");
                    etSkuQuantityInput.setSelection(1);
                    updateQuantityOperator(1);
                } else if (quantityInt >= selectedSku.getStock()) {
                    String newQuantity = String.valueOf(selectedSku.getStock());
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(selectedSku.getStock());
                } else {
                    etSkuQuantityInput.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });

        //scrollSkuList
        scrollSkuList.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                Log.e(TAG, "onUnselected: ");
                selectedSku = null;
//                GlideUtil.show(context, selectedSku.getGoodsImage(), iv_sku_logo);
//
//                tv_goods_price.setText("" + PriceUtil.dividePrice(selectedSku.getSalePrice()));//市场价
//
//                tvSkuQuantity.setText("库存" +selectedSku.getStock() + "件");
                btnSubmit.setEnabled(false);
                btnSubmit.setBackgroundResource(R.drawable.bg_login_btn_disable);
//
//                String quantity = etSkuQuantityInput.getText().toString();
//                if (!TextUtils.isEmpty(quantity)) {
//                    updateQuantityOperator(Integer.valueOf(quantity));
//                } else {
//                    updateQuantityOperator(0);
//                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();

            }

            @Override
            public void onSkuSelected(GoodsSkuListBean2 sku) {
                Log.e(TAG, "onSkuSelected: ");
                selectedSku = sku;
                //暂时sku没图
                if(XiKouUtils.isNullOrEmpty(selectedSku.getSkuImage())){
                    selectedSku.setSkuImage(product.getActivityCommodityVo().getImageList().get(0).getUrl());
                }
                GlideUtil.show(context, selectedSku.getSkuImage(), iv_sku_logo);
                tv_goods_name.setText(selectedSku.getCommodityName());
                tv_goods_price.setText(PriceUtil.dividePrice(sku.getCommodityPrice()));
                setCoupon(selectedSku.getCouponValue(), isGlobal);
                scrollSkuList.setSelectedSku(selectedSku);
                scrollSkuList.setStock(selectedSku.getStock());
                if(selectedSku.getStock() > 0){
                    if(MyApplication.switchBean != null && MyApplication.switchBean.getBuyGift() == 1){
                        setPaySwitch(true);
                    }else {
                        setPaySwitch(false);
                    }
                }else {
                    btnSubmit.setEnabled(false);
                    btnSubmit.setBackgroundResource(R.drawable.bg_login_btn_disable);
                }
//                List<SkuAttribute> attributeList = selectedSku.getSpecMap();
//                StringBuilder builder = new StringBuilder();
//                for (int i = 0; i < attributeList.size(); i++) {
//                    if (i != 0) {
//                        builder.append("");
//                    }
//                    SkuAttribute attribute = attributeList.get(i);
//                    builder.append(attribute.getSpecValue() + ",");
//                }
//               tv_allready_check.setText("已选：" + builder.toString());
//                tv_allready_check.setText(builder.toString());
                tvSkuQuantity.setText("库存" + sku.getStock() + "件");
//                tvSkuQuantity.setText("库存"+selectedSku.getStock());
                //根据开关控制确定按钮
//                if (MyApplication.switchBean != null && MyApplication.switchBean.getBuyGift() == 1) {
//                    btnSubmit.setEnabled(true);
//                    btnSubmit.setBackgroundResource(R.drawable.bg_login_btn);
//                } else {
//                    btnSubmit.setEnabled(false);
//                    btnSubmit.setBackgroundResource(R.drawable.bg_login_btn_disable);
//                }

                /*库存*/
                String quantity = etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });

    }

    public void setData(final GlobalBuyerGoodsDetailBean product, Callback callback) {
        this.product = product;
        this.skuList = product.getActivityCommodityVo().getSkuList();
        this.callback = callback;

//        priceFormat = context.getString(R.string.comm_price_format);
//        stockQuantityFormat = context.getString(R.string.product_detail_sku_stock);

        updateSkuData();
        updateQuantityOperator(1);

        btnSkuQuantityMinus.setEnabled(true);
        btnSkuQuantityPlus.setEnabled(true);
        etSkuQuantityInput.setEnabled(true);

    }

    //-----1
    private void updateSkuData() {
        GoodsSkuListBean2 firstSku = product.getActivityCommodityVo().getSkuList().get(0);
        scrollSkuList.setStock(firstSku.getStock());
        scrollSkuList.setSkuList(product.getActivityCommodityVo().getSkuList());
        tv_goods_name.setText(firstSku.getCommodityName());
//        boolean isVip= (boolean) SPUtils.getInstance(context).get(Constant.USER_IS_VIP,false);
//        if(isVip){
//            tv_transport.setText("免运费");
//        }else{
//            if(product.getTransport().equals("0")){
//                tv_transport.setText("免运费");
//            }else{
//                tv_transport.setText("运费:"+product.getTransport());
//            }
//        }
        selectedSku = firstSku;
        //无 skuImageUrl
        if(XiKouUtils.isNullOrEmpty(selectedSku.getSkuImage())){
            selectedSku.setSkuImage(product.getActivityCommodityVo().getImageList().get(0).getUrl());
        }
        GlideUtil.show(context, selectedSku.getSkuImage(), iv_sku_logo);
        tv_goods_price.setText(PriceUtil.dividePrice(selectedSku.getCommodityPrice()));
        tvSkuQuantity.setText("库存" + selectedSku.getStock() + "件");
        setCoupon(selectedSku.getCouponValue(), isGlobal);
        if (selectedSku.getStock() > 0) {
            // 选中第一个sku
            scrollSkuList.setSelectedSku(selectedSku);
            if(MyApplication.switchBean != null && MyApplication.switchBean.getBuyGift() == 1){
                setPaySwitch(true);
            }else {
                setPaySwitch(false);
            }
            btnSubmit.setEnabled(selectedSku.getStock() > 0);
            List<SkuAttribute> attributeList = selectedSku.getSpecMap();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append(attribute.getSpecValue() + ",");
            }
        } else {
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundResource(R.drawable.bg_login_btn_disable);
        }
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
//            btnSkuQuantityMinus.setEnabled(false);
//            btnSkuQuantityPlus.setEnabled(false);
//            etSkuQuantityInput.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
//                btnSkuQuantityMinus.setEnabled(false);
//                btnSkuQuantityPlus.setEnabled(true);
            } else if (newQuantity >= selectedSku.getStock()) {
//                btnSkuQuantityMinus.setEnabled(true);
//                btnSkuQuantityPlus.setEnabled(false);
            } else {
//                btnSkuQuantityMinus.setEnabled(true);
//                btnSkuQuantityPlus.setEnabled(true);
            }
            etSkuQuantityInput.setEnabled(true);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决键盘遮挡输入框问题
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        // KeyboardConflictCompat.assistWindow(getWindow());
        ViewUtils.transparencyBar(getWindow());
    }

    @Keep
    @LoginFilter
    @OnClick(R.id.btn_submit)
    public void onClick() {
        String quantity = etSkuQuantityInput.getText().toString();
        if (TextUtils.isEmpty(quantity)) {
            return;
        }
        int quantityInt = Integer.parseInt(quantity);
        if (quantityInt > 0 && quantityInt <= selectedSku.getStock()) {
            callback.onAdded(selectedSku, quantityInt, "");
            dismiss();
        } else {
            Toast.makeText(getContext(), "商品数量超出库存，请修改数量", Toast.LENGTH_SHORT).show();
        }
    }

    public interface Callback {
        //sku  数量   已选 specValue 值  //SpecMap
//        void onAdded(SkuListBean sku, int quantity, String memo);
        void onAdded(GoodsSkuListBean2 sku, int quantity, String memo);

        /**
         *
         * @param sku   sku
         * @param quantity  数量
         * @param mode   型号
         * @param type   规格
         */
//        void onAdded(GoodsSkuListBean sku, int quantity, String mode,String type);
    }
}
