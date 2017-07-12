package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 切换公司-Contract
 */

public interface MSwitchCompanyContract {

    /**
     * 切换公司-IView
     */
    interface MSwitchCompanyIView extends BaseView {

    }

    /**
     * 切换公司-Presenter
     */
    abstract class MISwitchCompanyActivityP extends BasePresenter<MSwitchCompanyIView> {

    }

    /**
     * 切换公司-Model
     */
    abstract class MISwitchCompanyModel extends BaseModel {

    }
}
