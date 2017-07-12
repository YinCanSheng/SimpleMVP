package ch.erp.management.mvp.contract;


import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;

/**
 * 邀请员工-Contract
 */

public interface MInviteEmployeesContract {
    /**
     * 邀请员工-IView
     */
    interface MInviteEmployeesIView extends BaseView {

    }

    /**
     * 邀请员工-Presenter
     */
    abstract class MIInviteEmployeesActivityP extends BasePresenter<MInviteEmployeesIView> {

    }

    /**
     * 邀请员工-Model
     */
    abstract class MIInviteEmployeesModel extends BaseModel {

    }
}
