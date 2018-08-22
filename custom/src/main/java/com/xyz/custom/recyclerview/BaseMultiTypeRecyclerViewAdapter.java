package com.xyz.custom.recyclerview;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 *
 * Created by md on 2017/12/24.
 */

public abstract class BaseMultiTypeRecyclerViewAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {

    private int mSingleLayoutId;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseMultiTypeRecyclerViewAdapter() {
        super(null);
    }

    @Override
    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (mSingleLayoutId > 0) {
            return createBaseViewHolder(parent, mSingleLayoutId);
        }
        return super.onCreateDefViewHolder(parent, viewType);
    }

    public void setSingleLayoutId(@LayoutRes int layoutId) {
        this.mSingleLayoutId = layoutId;
    }
}
