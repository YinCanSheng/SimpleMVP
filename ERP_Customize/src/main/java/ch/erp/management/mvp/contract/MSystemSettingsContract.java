package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 系統設置-Contract
 */

public interface MSystemSettingsContract {
    /**
     * 系統設置-IView
     */
    interface MSystemSettingsIView extends BaseView {

    }

    /**
     * 系統設置-Presenter
     */
    abstract class MISystemSettingsP extends BasePresenter<MSystemSettingsIView> {

    }

    /**
     * 系統設置-Model
     */
    abstract class MISystemSettingsModel extends BaseModel {

    }
}
