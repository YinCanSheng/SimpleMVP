package ch.erp.management.mvp.model.http;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * RxJava管理器
 */

public class MRxManager {

    /*统一管理订阅者*/ private CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    /**
     * 订阅时将订阅者添加至管理器
     */
    public void addSubscription(Subscription mSubscription) {

        if (mSubscription != null && !mSubscription.isUnsubscribed() && mCompositeSubscription != null) {

            mCompositeSubscription.add(mSubscription);

        }
    }

    /**
     *统一退订
     */
    public void clearAllSubscription(){
        if(!mCompositeSubscription.isUnsubscribed()){
            mCompositeSubscription.unsubscribe();
        }
    }
}
