package com.xk.mall.view.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ListView;

/***
 * 和scrollView一起用时，需要测量的listView
 */
public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
