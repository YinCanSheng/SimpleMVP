package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * Fragment库存-Contract
 */

public interface MStockContract {

    /**
     * 库存 -IView
     */
    interface MStockIView extends BaseView {

    }

    /**
     * 库存-Presenter
     */
    abstract class MIStockActivityP extends BasePresenter<MStockIView> {
        /**
         * 延時一秒-顯示佈局
         */
        public abstract void delayedDisplay();
    }

    /**
     * 库存-Model
     */
    abstract class MIStockModel extends BaseModel {

    }
}
