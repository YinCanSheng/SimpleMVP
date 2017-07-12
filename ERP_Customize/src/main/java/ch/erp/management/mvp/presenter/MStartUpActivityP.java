package ch.erp.management.mvp.presenter;

import java.util.concurrent.TimeUnit;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.contract.MStartUpContract;
import ch.erp.management.mvp.model.MStartUpModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 启动页-主持者
 */

public class MStartUpActivityP extends MStartUpContract.MIStartUpActivityP {

    /*启动页-数据处理*/private BaseModel mStartUpModel;

    public MStartUpActivityP() {

        mStartUpModel = new MStartUpModel();
    }

    /**
     * 延时进入下一页
     */
    @Override
    public void delayedJumpMain() {
        /*延时一秒*/
        mRxManager.addSubscription(Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        mView.delayedJump();
                        return null;
                    }
                }).subscribe());
    }
}
