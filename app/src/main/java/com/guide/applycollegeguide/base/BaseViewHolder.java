package com.guide.applycollegeguide.base;

import android.view.View;

/**
 * Created by md on 2017/12/17.
 */

public abstract class BaseViewHolder<T> extends com.chad.library.adapter.base.BaseViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(T t);
}
