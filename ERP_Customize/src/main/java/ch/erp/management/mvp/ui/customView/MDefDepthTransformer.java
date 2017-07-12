package ch.erp.management.mvp.ui.customView;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.Log;
import android.view.View;

/**
 * 自定义ViewPager动画
 * 
 * mPosition参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。当一个页面填充整个屏幕是，它的值是0，
 * 
 * 当一个页面刚刚离开屏幕的右边时，它的值是1。
 * 
 * 当两个也页面分别滚动到一半时，其中一个页面的位置是-0.5，另一个页面的位置是0.5。
 * 
 * 基于屏幕上页面的位置，通过使用诸如setAlpha()、setTranslationX()、或setScaleY()方法来设置页面的属性，
 * 来创建自定义的滑动动画。
 * 
 * @author ChenHong
 */
public class MDefDepthTransformer implements PageTransformer {
	private static float MIN_SCALE = 0.75f;

	@SuppressLint("NewApi")
	@Override
	public void transformPage(View view, float position) {
		Log.e("位置", position+"");
		int pageWidth = view.getWidth();
		if (position < -1) {
			view.setAlpha(0);
		} else if (position <= 0) {
			view.setAlpha(1);
			view.setTranslationX(0);
			view.setScaleX(1);
			view.setScaleY(1);
		} else if (position <= 1) {
			view.setAlpha(1 - position);

			view.setTranslationX(pageWidth * -position);

			float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
					* (1 - Math.abs(position));
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
		} else {
			view.setAlpha(0);

		}
	}

}
