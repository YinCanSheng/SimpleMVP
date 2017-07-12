package ch.erp.management.mvp.ui.customView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 下拉回弹
 */
public class MDefReboundScrollView extends ScrollView {
    /* 允许拖动的距离 2即是屏幕的二分之一 */private static final int size = 3;
    private View mSlideView;
    private float y;
    /* 构造一个矩形 */
    private Rect mRect = new Rect();
    ;
    /* 移动后的Y坐标 */
    private int mLastIntercaptY;
    /* 记录View原始位置 */
    private int mInitialTop, mInitialLeft, mInitialRight, mInitialBottom;
    private boolean mState = true;

    /**
     * 构造方法
     * <p>
     * new 时调用
     *
     * @param context
     */
    public MDefReboundScrollView(Context context) {
        super(context);
    }

    /**
     * 构造方法
     * <p>
     * 在XML里调用
     *
     * @param context
     * @param attrs
     */
    public MDefReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * xml映射完成是调用
     */
    @Override
    protected void onFinishInflate() {
        Log.e("scrollView", "onFinishInflate");
        if (getChildCount() > 0) {
            /* 得到直接子视图 */
            mSlideView = getChildAt(0);
        }
    }

    /**
     * 触摸监听响应
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mSlideView == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }

        return super.onTouchEvent(ev);

    }

    /**
     * @param ev
     */
    public void commOnTouchEvent(MotionEvent ev) {
        /* 获取触摸事件 */
        int action = ev.getAction();
        switch (action) {
        /* 按下 */
            case MotionEvent.ACTION_DOWN:
                break;
        /* 抬起 */
            case MotionEvent.ACTION_UP:
                if (mState == false) {
                /* 如果矩形不为空则开启动画 */
                    animation();
                }
                break;
            /* 移动 */
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
            /* 拖动距离/手势移动距离的size分之一 */
                int deltaY = (int) (nowY - preY) / size;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedMove()) {
                    if (mState) {
                        mInitialTop = mSlideView.getTop();
                        mInitialLeft = mSlideView.getLeft();
                        mInitialRight = mSlideView.getRight();
                        mInitialBottom = mSlideView.getBottom();
                        mState = false;
                    }
                /* 如果矩形为空，则保存一个位置。为控件初始位置 */
				/* 移动布局至手势位置 */
                    mSlideView.layout(mSlideView.getLeft(), mSlideView.getTop()
                            + deltaY, mSlideView.getRight(), mSlideView.getBottom()
                            + deltaY);

                }
                y = nowY;
                break;
            default:
                break;
        }
    }

    /**
     * 移动View到最开始处
     */

    public void animation() {
        /**开启平移动画*/
        ObjectAnimator mTranslationAnimator = ObjectAnimator.ofFloat(mSlideView, "translationY",
                mSlideView.getTop(), mInitialTop);

        mTranslationAnimator.setDuration(200);

        mTranslationAnimator.start();
        /** 回到最开始的地方/保存位置 */
        mSlideView.layout(mInitialLeft, mInitialTop, mInitialRight,
                mInitialBottom);
    }

    /**
     * @return
     */
    // 是否需要移动布局
    public boolean isNeedMove() {
        int offset = mSlideView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    /**
     * 事件拦截/解决单击事件与滑动事件冲突
     * <p>
     * ChenHong
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean mIsIntercept = false;
        int newY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y = ev.getY();
                mIsIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int scrollY;
                if (newY > mLastIntercaptY) {
                    scrollY = newY - mLastIntercaptY;
                } else {
                    scrollY = mLastIntercaptY - newY;
                }
                if (scrollY <= 15) {
                    mIsIntercept = false;
                } else {
                    mIsIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mIsIntercept = false;
                break;
            default:
                break;
        }
        mLastIntercaptY = newY;
        return mIsIntercept;
    }
}
