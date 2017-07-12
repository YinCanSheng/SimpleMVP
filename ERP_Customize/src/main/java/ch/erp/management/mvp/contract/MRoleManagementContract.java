package ch.erp.management.mvp.contract;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 角色管理-Contract
 */

public interface MRoleManagementContract {

    /**
     * 角色管理-IView
     */
    interface MRoleManagementIView extends BaseView {

    }

    /**
     * 角色管理-Presenter
     */
    abstract class MIRoleManagementP extends BasePresenter<MRoleManagementIView> {

    }

    /**
     * 角色管理-Model
     */
    abstract class MIRoleManagementModel extends BaseModel {

    }
}
