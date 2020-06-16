package com.youth.banner;

import android.content.Context;
import android.view.View;

public interface BannerViewHolder<T> {

    View createView(Context context, int position, T data);

}
