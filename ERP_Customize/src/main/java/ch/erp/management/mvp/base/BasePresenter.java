package ch.erp.management.mvp.base;

import ch.erp.management.mvp.model.http.MRxManager;

/**
 * Presenter-通用
 * view的绑定与销毁
 */

public abstract class BasePresenter<T> {

    /*视图层-Activity-Fragment*/ public T mView;

    /*订阅者管理器*/public MRxManager mRxManager = new MRxManager();

    /**
     * 绑定Presenter与View
     */
    public void attachView(T mView) {
        if (mView != null) {
            this.mView = mView;
        }

    }

    /**
     * 組件銷毀時
     * 解除Presenter与View
     * Subscribe退订
     */
    public void detachView() {

        /**回收View*/mView = null;

        /**訂閲者統一退訂*/if (mRxManager != null) {
            mRxManager.clearAllSubscription();
            mRxManager = null;
        }
    }


}
