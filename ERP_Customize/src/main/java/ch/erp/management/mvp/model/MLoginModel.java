package ch.erp.management.mvp.model;

import android.graphics.Bitmap;

import ch.erp.management.mvp.contract.MLoginContract;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 登录-Model
 * 数据、业务处理
 */

public class MLoginModel extends MLoginContract.MILoginModel {

    public MLoginModel() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer integer) {
                return null;
            }
        }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        }, new Action0() {
            @Override
            public void call() {

            }
        });

    }
}
