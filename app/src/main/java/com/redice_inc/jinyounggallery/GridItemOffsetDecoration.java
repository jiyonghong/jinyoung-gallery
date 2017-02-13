package com.redice_inc.jinyounggallery;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by redice on 2/13/17.
 */

public class GridItemOffsetDecoration extends RecyclerView.ItemDecoration {
    private int mItemOffset;
    private int spanCount;
    private boolean includeEdge;

    public GridItemOffsetDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    public GridItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId, int spanCount, boolean includeEdge) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            outRect.left = mItemOffset - column * mItemOffset / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * mItemOffset / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = mItemOffset;
            }
            outRect.bottom = mItemOffset; // item bottom
        } else {
            outRect.left = column * mItemOffset / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = mItemOffset - (column + 1) * mItemOffset / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = mItemOffset; // item top
            }
        }
    }
}
