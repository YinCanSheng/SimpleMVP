package ch.erp.management.mvp.ui.customView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * 自定义GridLayoutManager-控制RecyclerView滑动
 */

public class MScrollGridLayoutManager extends GridLayoutManager {

    /*默认允许滑动*/private boolean isScrollEnabled = true;

    /**
     * 暴露接口-由外部控制是否可以滑动
     */
    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    public MScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled && super.canScrollVertically();
    }
}
