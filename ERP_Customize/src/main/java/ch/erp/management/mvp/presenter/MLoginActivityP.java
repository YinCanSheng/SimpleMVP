package ch.erp.management.mvp.presenter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.LinearLayout;

import java.util.concurrent.TimeUnit;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.contract.MLoginContract;
import ch.erp.management.mvp.model.MLoginModel;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * 登录-主持者
 */
public class MLoginActivityP extends MLoginContract.MILoginActivityP {
    /*登录页数据处理*/ private BaseModel mLoginModel;

    public MLoginActivityP() {
        mLoginModel = new MLoginModel();
    }

    /**
     * 延时三秒进入主页
     */
    @Override
    public void delayedJumpMain() {
        mView.showLoading();
        mRxManager.addSubscription(Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        mView.hideLoading();
                        mView.delayedJump();
                        return null;
                    }
                }).subscribe());
    }

    /**
     * 开启进入动画
     */
    @Override
    public void openTheAnimation(LinearLayout mAttributesView) {
        ObjectAnimator mTransObjectAnimator = ObjectAnimator.ofFloat(mAttributesView, "translationY", 1500, 0);
        ObjectAnimator mAlphaObjectAnimator=ObjectAnimator.ofFloat(mAttributesView,"alpha",0,1);
        AnimatorSet mAnimatorSet=new AnimatorSet();
        mAnimatorSet.play(mTransObjectAnimator).with(mAlphaObjectAnimator);
        mAnimatorSet.setDuration(900);
        mView.openTheAnimation(mAnimatorSet);
    }

}
