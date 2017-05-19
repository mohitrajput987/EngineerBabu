package com.ebabu.engineerbabu.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hp on 20/01/2016.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int leftSpace;
    private int rightLeft;
    private int bottomSpace;

    public SpacesItemDecoration(int leftSpace, int rightLeft, int bottomSpace) {
        this.leftSpace = leftSpace;
        this.rightLeft = rightLeft;
        this.bottomSpace = bottomSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = leftSpace;
        outRect.right = rightLeft;
        outRect.bottom = bottomSpace;
    }
}
