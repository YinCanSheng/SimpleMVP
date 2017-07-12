package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 库存状况-Contract
 */

public interface MInventoryStatusContract {
    /**
     * 库存状况-IView
     */
    interface MInventoryStatusIView extends BaseView {

    }

    /**
     * 库存状况-Presenter
     */
    abstract class MIInventoryStatusActivityP extends BasePresenter<MInventoryStatusIView> {

    }

    /**
     * 库存状况-Model
     */
    abstract class MIInventoryStatusModel extends BaseModel {

    }
}
