package ch.erp.management.mvp.utils.General;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/**
 * 网络连接状态监控
 *
 * @author ChenHong
 */
public class MNetworkStatusUtils {
    /* 自己 */
    private static MNetworkStatusUtils mNetworkStatusUtils;
    /**
     * 网络管理器
     * <p>
     * 通过API可获取当前设备联网状态信息
     */
    private ConnectivityManager mConnectivityManager;
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 私有化构造
     * <p>
     * 用单例获取对象
     */
    private MNetworkStatusUtils(Context mContext) {
        /* 实例化上下文对象 */
        this.mContext = mContext;
        /* 实例化网络管理器 */
        mConnectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 单例获取对象
     * <p>
     * 饿汉模式
     */

    public static MNetworkStatusUtils getMNetworkStatusHelper(Context mContext) {
        if (mNetworkStatusUtils == null) {
            /**
             * 线程锁
             *
             * 一次只能进来一个线程
             */
            synchronized (MNetworkStatusUtils.class) {
                if (mNetworkStatusUtils == null) {
                    mNetworkStatusUtils = new MNetworkStatusUtils(mContext);
                } else {
                    return mNetworkStatusUtils;
                }
            }
        }
        return mNetworkStatusUtils;
    }

    /**
     * 获取WIFI连接状态
     */
    public boolean getNetworkStatus_wifi() {
        /**
         * 判断网络是否有效
         */
        if (mConnectivityManager != null) {
			/* 得到对应网络类型信息实体 */
            NetworkInfo mNetworkInfo_wifi = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			/* 得到当前网络连接状态 */
            State mWifiState = mNetworkInfo_wifi.getState();
            /**
             * 判断网络是否连接可用
             */
            if (mNetworkInfo_wifi.isConnected()
                    && mNetworkInfo_wifi.isAvailable()) {
                /**
                 * 判断当前网络是已经连接
                 */
                if (State.CONNECTED == mWifiState) {
                    return true;
                }
            }
        } else {
            MToastUtils.showCase(mContext, "网络无效");
        }

        return false;
    }

    /**
     * 获取手机数据流量连接状态
     */
    public boolean getNetworkStatus_mobile() {
        /**
         * 判断网络是否有效
         */
        if (mConnectivityManager != null) {
			/* 得到对应网络类型信息实体 */
            NetworkInfo mNetworkInfo_mobile = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			/* 得到当前网络连接状态 */
            State mWifiState = mNetworkInfo_mobile.getState();
            /**
             * 判断网络是否连接可用
             */
            if (mNetworkInfo_mobile.isConnected()
                    && mNetworkInfo_mobile.isAvailable()) {
                /**
                 * 判断当前网络是已经连接
                 */
                if (State.CONNECTED == mWifiState) {
                    return true;
                }
            }
        } else {
            MToastUtils.showCase(mContext, "网络无效");
        }

        return false;
    }
}
