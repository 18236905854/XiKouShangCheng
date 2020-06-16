package com.xk.mall.view.widget;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;


/**
 * Created by Linhh on 16/5/24.
 */
public class Instrument {
    private static Instrument mInstrument;
    public static Instrument getInstance(){
        if(mInstrument == null){
            mInstrument = new Instrument();
        }
        return mInstrument;
    }

    @SuppressLint("NewApi") public float getTranslationY(View view){
        if(view == null){
            return 0;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            return view.getTranslationY();
        }else{
            return ViewHelper.getTranslationY(view);
        }
    }

    @SuppressLint("NewApi") public void slidingByDelta(View view ,float delta){
        if(view == null){
            return;
        }
        view.clearAnimation();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            view.setTranslationY(delta);
        }else{
            ViewHelper.setTranslationY(view, delta);
        }
    }

    @SuppressLint("NewApi") public void slidingToY(View view ,float y){
        if(view == null){
            return;
        }
        view.clearAnimation();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            view.setY(y);
        }else{
            ViewHelper.setY(view, y);
        }
    }

    @SuppressLint("NewApi") public void reset(View view, long duration){
        if(view == null){
            return;
        }
        view.clearAnimation();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.animation.ObjectAnimator.ofFloat(view, "translationY", 0F).setDuration(duration).start();
        }else{
            ObjectAnimator.ofFloat(view, "translationY", 0F).setDuration(duration).start();
        }
    }

    @SuppressLint("NewApi") public void smoothTo(View view ,float y, long duration){
        if(view == null){
            return;
        }
        view.clearAnimation();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            ObjectAnimator.ofFloat(view, "translationY", y).setDuration(duration).start();
        }else{
            ObjectAnimator.ofFloat(view, "translationY", y).setDuration(duration).start();
        }
    }
}
