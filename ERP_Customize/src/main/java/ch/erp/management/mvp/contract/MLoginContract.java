package ch.erp.management.mvp.contract;

import android.animation.AnimatorSet;
import android.widget.LinearLayout;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 登录-Contract
 */

public interface MLoginContract {

    /**
     * 登录-IView
     */
    interface MLoginIView extends BaseView {
        /**
         * 进入主页
         */
        void delayedJump();
        /**
         * 开启进入动画
         */
        void openTheAnimation(AnimatorSet objectAnimator);
    }

    /**
     * 登錄-Presenter
     */
    abstract class MILoginActivityP extends BasePresenter<MLoginIView> {
        /**
         * 延時跳轉
         */
        protected abstract void delayedJumpMain();
        /**
         * 开启进入动画
         */
        protected abstract void openTheAnimation(LinearLayout mView);
    }

    /**
     * 登录-Model
     */
    abstract class MILoginModel extends BaseModel {

    }
}
