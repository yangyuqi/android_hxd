package com.zhejiang.haoxiadan.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by KK on 2017/10/8.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace ;

    public DividerItemDecoration(int px) {
        this.mSpace = px;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.top = 0;
        outRect.bottom = mSpace;
        outRect.right = 0;

    }
}
