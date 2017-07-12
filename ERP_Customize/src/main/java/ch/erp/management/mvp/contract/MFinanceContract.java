package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * Fragment财务-Contract
 */

public interface MFinanceContract {
    /**
     * 财务 -IView
     */
    interface MFinanceIView extends BaseView {

    }

    /**
     * 财务-Presenter
     */
    abstract class MIFinanceActivityP extends BasePresenter<MFinanceIView> {
        /**
         * 延時一秒-顯示佈局
         */
        public abstract void delayedDisplay();
    }

    /**
     * 财务-Model
     */
    abstract class MIFinanceModel extends BaseModel {

    }
}
