package ch.erp.management.mvp.ui.customView;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;

/**
 * 主页中部布局
 * 
 * @author Administrator
 * 
 */
public class MDefViewPager extends ViewPager {
	/**
	 * 是否禁止滑动
	 */
	private boolean scrollble = true;

	/**
	 * 构造
	 * 
	 * @param context
	 */
	public MDefViewPager(Context context) {
		super(context);

	}

	/**
	 * 构造
	 * 
	 * @param context
	 * @param attrs
	 */
	public MDefViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 事件拦截
	 */
	@Override
	protected void dispatchThawSelfOnly(SparseArray<Parcelable> container) {
		// TODO Auto-generated method stub
		super.dispatchThawSelfOnly(container);
	}

	/**
	 * 事件处理
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(arg0);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isScrollble() {
		return scrollble;
	}

	/**
	 * 外界控制滑动
	 * 
	 * @param scrollble
	 */
	public void setScrollble(boolean scrollble) {
		this.scrollble = scrollble;
	}
}
