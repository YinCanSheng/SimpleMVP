package ch.erp.management.mvp.utils.General;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;

import ch.erp.management.R;

/**
 * 对话框工具
 *
 * @author ChenHong
 */
public class MDialogUtils {
    static MDialogUtils mDialogUtils;

    /* 完全自定义对话框 */
    AlertDialog mDialog;

    /**
     * 私有化构造
     */
    private MDialogUtils() {

    }


    /**
     * 单例获取对象
     *
     * @return
     */
    public static MDialogUtils getDialogUtils() {
        if (mDialogUtils == null) {
            synchronized (MDialogUtils.class) {
                if (mDialogUtils == null) {
                    mDialogUtils = new MDialogUtils();
                } else {
                    return mDialogUtils;
                }
            }
        }
        return mDialogUtils;
    }


    /**
     * 完全自定义
     * <p>
     * 事件由外界定义
     */
    public Window completeCustomPromptDialog(Context mContext, int mXMLId) {
        /** 实例化对话框 */
        mDialog = new Builder(mContext, R.style.AppLoginTheme).create();

        mDialog.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    /** 关闭等待框 */
                    MDialogUtils.getDialogUtils()
                            .closecompleteCustomPromptDialog();
                }
                return true;
            }
        });

        /**设置失去焦点*/mDialog.setCancelable(false);

        /** 显示 */mDialog.show();

        /** 得到顶级容器 */Window mWindow = mDialog.getWindow();

        /**设置顶部对齐*/mWindow.setGravity(Gravity.TOP);

        /**设置背景为空-*/mWindow.setBackgroundDrawable(null);

        /** 往容器里装入视图 */mWindow.setContentView(mXMLId);

        return mWindow;
    }

    /**
     * 关闭完全自定义对话框
     */
    public void closecompleteCustomPromptDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }


}
