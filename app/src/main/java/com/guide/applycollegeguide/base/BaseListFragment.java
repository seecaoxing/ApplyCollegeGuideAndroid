package com.guide.applycollegeguide.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.guide.applycollegeguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 目的地、行程单、锦囊等列表
 * Created by md on 2018/2/26.
 */

public abstract class BaseListFragment extends BaseFragment {

    @BindView(R.id.recycler_simple)
    RecyclerView recyclerView;

    @BindView(R.id.tv_title)
    TextView titleTv;

    @BindView(R.id.bt_filter)
    Button filterBt;

    @BindView(R.id.tool_bar)
    View toolBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_filter, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        RecyclerView.ItemDecoration decoration = getDecoration();
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setAdapter(getAdapter());

        if (TextUtils.isEmpty(getTitleString())) {
            toolBar.setVisibility(View.GONE);
        } else {
            titleTv.setText(getTitleString());
        }

        View.OnClickListener onClickListener = getFilterClickListener();
        if (onClickListener == null) {
            filterBt.setVisibility(View.GONE);
        } else {
            filterBt.setOnClickListener(onClickListener);
        }
    }

    public abstract RecyclerView.Adapter getAdapter();

    public abstract RecyclerView.ItemDecoration getDecoration();

    public CharSequence getTitleString() {
        return null;
    }

    public View.OnClickListener getFilterClickListener() {
        return null;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public TextView getTitleTv() {
        return titleTv;
    }
}
