package ch.erp.management.mvp.ui.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 自定义登录框
 * <p>
 * 点击改变状态
 *
 * @author ChenHong
 */
public class MDefLoginEditText extends EditText {
    // /* 画笔 */ Paint mPaint;
    ///* 矩形 */ RectF mRectF;

    public MDefLoginEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化对象
     */
    private void initObj() {
//        mPaint = new Paint();
//        mPaint.setStyle(Style.STROKE);

        /**
         * 根据焦点状态改变提示的颜色
         */
        if (this.isFocused()) {
            setHintTextColor(Color.parseColor("#d0d0d0"));
        } else {
            setHintTextColor(Color.parseColor("#dddddd"));
        }
        /**
         * 定义一个矩形范围/这里是一条线
         */
//        mRectF = new RectF(2 + this.getScrollX(), this.getHeight()
//                + this.getScrollY() - 2, this.getWidth() - 3
//                + this.getScrollX(), this.getHeight() + this.getScrollY() - 1);
    }

    /**
     * 改变边框状态
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas mCanvas) {
        /* 初始化对象与一些参数 */
        initObj();
        /* 绘制到画布 */
        //mCanvas.drawRoundRect(mRectF, 3, 3, mPaint);
        super.onDraw(mCanvas);
    }

}
