package com.xk.mall.view.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.view.activity.CustomizedActivity;
import com.xk.mall.view.activity.DesignerCircleActivity;
import com.xk.mall.view.activity.GroupMainActivity;

public class MoreWindow extends PopupWindow implements OnClickListener{

	private String TAG = MoreWindow.class.getSimpleName();
	Activity mContext;
	private int mWidth;
	private int mHeight;
	private int statusBarHeight ;
	private Bitmap mBitmap= null;
	private Bitmap overlay = null;
	private RelativeLayout rlParent;

	private Handler mHandler = new Handler();

	public MoreWindow(Activity context) {
		mContext = context;
		init();
	}

	private void init() {
		Rect frame = new Rect();
		mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		statusBarHeight = frame.top;
		DisplayMetrics metrics = new DisplayMetrics();
		mContext.getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mHeight = metrics.heightPixels;
		rlParent = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.tab_main_center_more_window, null);
		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		ImageView close = rlParent.findViewById(R.id.center_music_window_close);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Logger.e("关闭对话框111");
				if (isShowing()) {
//					Logger.e("关闭对话框222");
					closeAnimation(rlParent);
				}
			}

		});
		rlParent.measure(w, h);
		int height = rlParent.getMeasuredHeight();
//		Logger.e("布局高度:" + rlParent.getMeasuredHeight());
		setWidth(mWidth);
		setHeight(height);
	}

	public void showMoreWindow(View anchor,int bottomMargin) {
		setContentView(rlParent);

		showAnimation(rlParent);
		setOutsideTouchable(true);
//		setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
		setFocusable(true);
		WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
		lp.alpha = 0.7f;
		mContext.getWindow().setAttributes(lp);
		showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
		lp.alpha = 1.0f;
		mContext.getWindow().setAttributes(lp);
	}

	private void showAnimation(ViewGroup layout){
//		Logger.e("子布局的数量:" + layout.getChildCount());
		for(int i = 0;i < layout.getChildCount(); i++){
			final View child = layout.getChildAt(i);
			if(child.getId() == R.id.center_music_window_close){
				continue;
			}
			child.setOnClickListener(this);
			child.setVisibility(View.INVISIBLE);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
					fadeAnim.setDuration(300);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(450);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
				}
			}, i * 100);
		}

	}

	private void closeAnimation(ViewGroup layout){
		for(int i = 0;i < layout.getChildCount(); i++){
			final View child = layout.getChildAt(i);
			if(child.getId() == R.id.center_music_window_close){
				continue;
			}
			child.setOnClickListener(this);
			mHandler.postDelayed(new Runnable() {

				@Override
				public void run() {
					child.setVisibility(View.VISIBLE);
					ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
					fadeAnim.setDuration(200);
					KickBackAnimator kickAnimator = new KickBackAnimator();
					kickAnimator.setDuration(100);
					fadeAnim.setEvaluator(kickAnimator);
					fadeAnim.start();
					fadeAnim.addListener(new AnimatorListener() {

						@Override
						public void onAnimationStart(Animator animation) {

						}

						@Override
						public void onAnimationRepeat(Animator animation) {

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							child.setVisibility(View.INVISIBLE);
							dismiss();
						}

						@Override
						public void onAnimationCancel(Animator animation) {

						}
					});
				}
			}, (layout.getChildCount()-i-1) * 30);

			if(child.getId() == R.id.more_window_collect){
				mHandler.postDelayed(new Runnable() {

					@Override
					public void run() {
						dismiss();
					}
				}, (layout.getChildCount()-i) * 30 + 80);
			}

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.more_window_collect://定制拼团
				ActivityUtils.startActivity(GroupMainActivity.class);
				dismiss();
				break;
			case R.id.more_window_auto://定制馆
				ActivityUtils.startActivity(CustomizedActivity.class);
				dismiss();
				break;
			case R.id.more_window_external://设计师圈
				ActivityUtils.startActivity(DesignerCircleActivity.class);
				dismiss();
				break;

			default:
				break;
		}
	}

	public void destroy() {
		if (null != overlay) {
			overlay.recycle();
			overlay = null;
			System.gc();
		}
		if (null != mBitmap) {
			mBitmap.recycle();
			mBitmap = null;
			System.gc();
		}
	}

}
