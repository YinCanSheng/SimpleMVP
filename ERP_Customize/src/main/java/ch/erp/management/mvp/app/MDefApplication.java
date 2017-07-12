package ch.erp.management.mvp.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import java.util.Stack;

import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.utils.General.MNetworkStatusUtils;
import ch.erp.management.mvp.utils.SwipeBack.SwipeBackActivity;

/**
 * Activity管理器
 * ChenHong
 */
public class MDefApplication extends Application {

    /*上下文*/public static Context mContext = null;
    /*activity堆*/private static Stack<SwipeBackActivity> activityStack;
    /*自己*/private static MDefApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        singleton = this;
    }

    public static MDefApplication getInstance() {
        return singleton;
    }

    /**
     * add Activity 添加Activity到栈
     */
    public void addActivity(SwipeBackActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<SwipeBackActivity>();
        }
        activityStack.add(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public SwipeBackActivity currentActivity() {
        SwipeBackActivity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        SwipeBackActivity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(SwipeBackActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 結束指定Activity
     * 自定義退出动画
     */
    public void finishDefActivity(int enterAni, int outAni) {
        SwipeBackActivity activity = activityStack.lastElement();
        finishDefActivity(activity, enterAni, outAni);
    }

    /**
     * 结束指定的Activity
     */
    public void finishDefActivity(AppCompatActivity activity, int enterAni, int outAni) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity.overridePendingTransition(enterAni, outAni);
            activity = null;
        }
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (SwipeBackActivity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {

            finishAllActivity();

        } catch (Exception e) {

        }
    }

    /**
     * 判断网络状况
     */
    public boolean getNetworkStatus() {
        return MNetworkStatusUtils.getMNetworkStatusHelper(this)
                .getNetworkStatus_mobile()
                || MNetworkStatusUtils.getMNetworkStatusHelper(this)
                .getNetworkStatus_wifi();
    }

    /**
     * 收起软键盘
     */
    public void stopKeyBoard(BaseActivity mBaseActivity) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (mInputMethodManager != null) {
            mInputMethodManager.hideSoftInputFromWindow(mBaseActivity.getWindow()
                    .getDecorView().getWindowToken(), 0);
        }
    }


}
