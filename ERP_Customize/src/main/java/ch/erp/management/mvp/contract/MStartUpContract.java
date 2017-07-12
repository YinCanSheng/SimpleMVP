package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 启动页-Contract
 */

public interface MStartUpContract {

    /**
     * 启动页-IView
     */
    interface MStartUpIView extends BaseView {
        /**
         * 进入主页
         */
        void delayedJump();
    }

    /**
     * 启动页-Presenter
     */
    abstract class MIStartUpActivityP  extends BasePresenter<MStartUpIView>{
        /**
         * 延时进入下一页
         */
        protected abstract void delayedJumpMain();
    }
    /**
     * 启动页-Model
     */
    abstract class MIStartUpModel extends BaseModel {

    }
}
