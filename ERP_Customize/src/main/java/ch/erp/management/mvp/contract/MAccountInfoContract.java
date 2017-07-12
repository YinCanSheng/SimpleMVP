package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 账户信息-Contract
 */

public interface MAccountInfoContract {

    /**
     * 账户信息-IView
     */
    interface MAccountInfoIView extends BaseView {

    }

    /**
     * 账户信息-Presenter
     */
    abstract class MIAccountInfoActivityP extends BasePresenter<MAccountInfoIView> {

    }

    /**
     * 账户信息-Model
     */
    abstract class MIMAccountInfoIViewModel extends BaseModel {

    }
}
