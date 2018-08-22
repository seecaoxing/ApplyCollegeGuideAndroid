package com.guide.applycollegeguide.customview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.guide.applycollegeguide.R;


/**
 * Created by md on 2017/12/16.
 */

public class ToolBar extends FrameLayout {

    private ImageView leftBt;
    private ImageView rightBt;

    public ToolBar(@NonNull Context context) {
        this(context, null);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_tool_bar, this);
        setId(R.id.tool_bar);

        leftBt = findViewById(R.id.iv_tool_left);
        rightBt = findViewById(R.id.iv_tool_right);
    }

    public void setLeft(int resId, OnClickListener clickListener) {
        leftBt.setImageResource(resId);
        leftBt.setOnClickListener(clickListener);
    }

    public void setRight(int resId, OnClickListener clickListener) {
        rightBt.setImageResource(resId);
        rightBt.setOnClickListener(clickListener);
    }

    public void back(OnClickListener onClickListener) {
        setLeft(R.drawable.common_btn_back, onClickListener);
    }

    public void search(OnClickListener onClickListener) {
        setLeft(R.drawable.common_btn_search, onClickListener);
    }

    public void share(OnClickListener onClickListener) {
        setRight(R.drawable.common_btn_share, onClickListener);
    }

    public void clear() {
        setLeft(0, null);
        setRight(0, null);
    }

}
