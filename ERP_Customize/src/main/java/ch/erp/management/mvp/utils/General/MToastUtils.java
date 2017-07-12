package ch.erp.management.mvp.utils.General;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

/**
 * 提示框工具
 * 
 * @author ChenHong
 */
public class MToastUtils {
	/**
	 * 显示提示框/预定义
	 * 
	 * @param mContext
	 * @param mStr
	 */
	public static void showCase(Context mContext, String mStr) {
		Toast mToast = Toast.makeText(mContext, mStr, Toast.LENGTH_SHORT);
		mToast.setGravity(Gravity.CENTER, 0, 250);
		mToast.show();
	}

	/**
	 * 自定义提示框
	 */
	public static void showCustomCase(Context mContext, int mXMLId) {
		View layout = LayoutInflater.from(mContext).inflate(mXMLId, null);
		Toast mToast = new Toast(mContext);
		mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		mToast.setDuration(Toast.LENGTH_SHORT);
		mToast.setView(layout);
		mToast.show();
	}

	/**
	 * Snackbar提示框
	 */
	public static void showSnackbar(View mContext, String mStr) {
		Snackbar mSnackbar = Snackbar.make(mContext, mStr,
				Snackbar.LENGTH_SHORT);
		mSnackbar.show();
	}
}
