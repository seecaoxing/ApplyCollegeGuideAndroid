package com.xyz.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 带divider linearlayout
 * Created by jason on 2017/8/2.
 */
public class DividerLinearLayout extends LinearLayout {
    private Drawable mDividerDrawable;

    private int mDividerPaddingLeft;
    private int mDividerPaddingTop;
    private int mDividerPaddingRight;
    private int mDividerPaddingBottom;

    private List<View> mVisiableChildren = new ArrayList<View>();

    public DividerLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public DividerLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DividerLinearLayout(Context context) {
        super(context);
    }

    public void setDivider(int drawable) {
        if (mDividerDrawable != null) {
            mDividerDrawable.setCallback(null);
        }
        mDividerDrawable = getContext().getResources().getDrawable(drawable);
    }

    public void setDividerPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        mDividerPaddingLeft = paddingLeft;
        mDividerPaddingTop = paddingTop;
        mDividerPaddingRight = paddingRight;
        mDividerPaddingBottom = paddingBottom;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() > 1 && mDividerDrawable != null) {
            int dividerWidth = mDividerDrawable.getIntrinsicWidth();
            int dividerHeight = mDividerDrawable.getIntrinsicHeight();

            dividerWidth = (dividerWidth + 1) / 2;
            dividerHeight = (dividerHeight + 1) / 2;

            getVisiableChildren();
            final int count = mVisiableChildren.size();

            if (getOrientation() == VERTICAL) {

                for (int i = 0; i < count; i++) {
                    View child = mVisiableChildren.get(i);
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (i != 0) {
                        lp.topMargin = dividerHeight;
                    }

                    if (i != count - 1) {
                        lp.bottomMargin = dividerHeight;
                    }

                }

            } else {
                for (int i = 0; i < count; i++) {
                    View child = mVisiableChildren.get(i);
                    LayoutParams lp = (LayoutParams) child.getLayoutParams();
                    if (i != 0) {
                        lp.leftMargin = dividerWidth;
                    }

                    if (i != count - 1) {
                        lp.rightMargin = dividerWidth;
                    }

                }
            }

            mVisiableChildren.clear();
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (getChildCount() > 1 && mDividerDrawable != null) {
            int dividerWidth = mDividerDrawable.getIntrinsicWidth();
            int dividerHeight = mDividerDrawable.getIntrinsicHeight();

            dividerWidth = (dividerWidth + 1) / 2 * 2;
            dividerHeight = (dividerHeight + 1) / 2 * 2;

            getVisiableChildren();
            final int dividerCount = mVisiableChildren.size() - 1;

            if (getOrientation() == VERTICAL) {

                for (int i = 0; i < dividerCount; i++) {
                    View child = mVisiableChildren.get(i);
                    final int l = mDividerPaddingLeft;
                    final int t = child.getBottom();
                    final int r = Math.max(getWidth() - mDividerPaddingRight, l);
                    final int b = Math.max(t + dividerHeight, t);
                    mDividerDrawable.setBounds(l, t, r, b);
                    mDividerDrawable.draw(canvas);
                }

            } else {
                for (int i = 0; i < dividerCount; i++) {
                    View child = mVisiableChildren.get(i);
                    final int l = child.getRight();
                    final int t = mDividerPaddingTop;
                    final int r = Math.max(l + dividerWidth, l);
                    final int b = Math.max(getHeight() - mDividerPaddingBottom, t);
                    mDividerDrawable.setBounds(l, t, r, b);
                    mDividerDrawable.draw(canvas);
                }
            }

            mVisiableChildren.clear();
        }
    }


    private void getVisiableChildren() {
        mVisiableChildren.clear();

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                mVisiableChildren.add(child);
            }
        }
    }

}
