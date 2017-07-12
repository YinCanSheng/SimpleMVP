package ch.erp.management.mvp.utils.General;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * 获取屏幕参数
 * 
 * @author ChenHong
 * 
 */
public class MScreenParaUtils {
	/**
	 * 获取手机屏幕密度
	 * 
	 * @param context
	 * @return
	 */
	public static float getDensity(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}

	/**
	 * 获取手机屏幕DPI
	 * 
	 * @param context
	 * @return
	 */
	public static int getDensityDPI(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.densityDpi;
	}

	/**
	 * 获取手机屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获取手机屏幕的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * @return
	 * @Description： 获取当前屏幕一半宽度
	 */
	public static int getHalfWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels / 2;
	}

	/**
	 * @return
	 * @Description： 获取当前屏幕1/4宽度
	 */
	public static int getQuarterWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels / 4;
	}

	public static Point getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point out = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			display.getSize(out);
		} else {
			int width = display.getWidth();
			int height = display.getHeight();
			out.set(width, height);
		}
		return out;
	}
}
