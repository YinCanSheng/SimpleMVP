package ch.erp.management.mvp.presenter;

import java.util.concurrent.TimeUnit;

import ch.erp.management.mvp.contract.MStockContract;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 财务Fragment-主持者
 */
public class MStockFragmentP extends MStockContract.MIStockActivityP{
    /**
     * 延時一秒-顯示佈局
     */
    @Override
    public void delayedDisplay() {
        mRxManager.addSubscription(Observable.timer(500, TimeUnit.MILLISECONDS) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        mView.hideLoading();
                        return null;
                    }
                }).subscribe());
    }
}
