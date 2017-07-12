package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 采购退货-Contract
 */

public interface MPurchaseReturnsContract {
    /**
     * 采购退货-IView
     */
    interface MPurchaseReturnsIView extends BaseView {

    }

    /**
     * 采购退货-Presenter
     */
    abstract class MIPurchaseReturnsActivityP extends BasePresenter<MPurchaseReturnsIView> {

    }

    /**
     * 采购退货-Model
     */
    abstract class MIPurchaseReturnsModel extends BaseModel {

    }
}
