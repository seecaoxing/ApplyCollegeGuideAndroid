package com.xyz.custom.recyclerview;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 *
 * Created by md on 2017/12/24.
 */

public abstract class BaseRecyclerViewAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    public BaseRecyclerViewAdapter() {
        super(0);
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

}
