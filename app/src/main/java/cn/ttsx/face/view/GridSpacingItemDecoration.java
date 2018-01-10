package cn.ttsx.face.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hmx on 2016/10/21.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int verticalSpacing;
    private int horizontalSpacing;
    private boolean includeEdge;
    private int spacing = 10;

    public GridSpacingItemDecoration(int spanCount, int horizontalSpacing, int verticalSpacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.verticalSpacing = verticalSpacing;
        this.horizontalSpacing = horizontalSpacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * horizontalSpacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = verticalSpacing; // item top
            }
        }
    }
}
