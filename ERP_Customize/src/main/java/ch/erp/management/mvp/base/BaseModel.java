package ch.erp.management.mvp.base;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 模型-通用
 */
public abstract class BaseModel {

    /**
     * 指定事件生产线程和事件消费线程
     */
    public Observable<String> assign_thread(Observable<String> mObservable) {
        return mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
