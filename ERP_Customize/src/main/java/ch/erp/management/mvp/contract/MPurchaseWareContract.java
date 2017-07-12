package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 *采购入库-Contract
 */

public interface MPurchaseWareContract {
    /**
     * 采购入库-IView
     */
    interface MPurchaseWareIView extends BaseView {

    }

    /**
     * 采购入库-Presenter
     */
    abstract class MIPurchaseWareActivityP extends BasePresenter<MPurchaseWareIView> {

    }

    /**
     * 采购入库-Model
     */
    abstract class MIPurchaseWareModel extends BaseModel {

    }
}
