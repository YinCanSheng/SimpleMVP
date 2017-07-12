package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * Fragment报表-Contract
 */
public interface MStatementContract {
    /**
     * 报表-IView
     */
    interface MStatementIView extends BaseView {

    }

    /**
     * 报表-Presenter
     */
    abstract class MIStatementActivityP extends BasePresenter<MStatementIView> {
        /**
         * 延時一秒-顯示佈局
         */
        public abstract void delayedDisplay();
    }

    /**
     * 报表-Model
     */
    abstract class MIStatementModel extends BaseModel {

    }
}
