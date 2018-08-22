package com.xyz.custom;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by md on 2017/12/17.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    public static final int GRID = 2;

    private int mSpace;
    private int mOrientation;

    public SpaceItemDecoration(int space) {
        this(space, VERTICAL);
    }

    public SpaceItemDecoration(int space, int orientation) {
        this.mSpace = space;
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildLayoutPosition(view);

        if (mOrientation == VERTICAL) {
            if (position == parent.getLayoutManager().getItemCount() - 1) {
                return;
            }
            outRect.set(0, 0, 0, mSpace);
        } else if (mOrientation == HORIZONTAL) {
            if (position == parent.getLayoutManager().getItemCount() - 1) {
                return;
            }
            outRect.set(0, 0, mSpace, 0);
        } else if (mOrientation == GRID) {
            // 第一列右侧一半，最后一列左侧一半
            int spanCount = ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
            if (position % spanCount == 0) {
                outRect.set(mSpace, 0, mSpace / 2, mSpace);
            } else if (position % spanCount == spanCount - 1) {
                outRect.set(mSpace / 2, 0, mSpace, mSpace);
            } else {
                outRect.set(mSpace / 2, 0, mSpace / 2, mSpace);
            }
        }
    }
}
