package ch.erp.management.mvp.contract;

import java.util.List;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;
import ch.erp.management.mvp.model.entity.MSalesDetailsInfo;
import ch.erp.management.mvp.model.entity.MShortcutFunctionInfo;
import ch.erp.management.mvp.ui.adapter.MShortcutRecylerViewAdapter;
import rx.Observable;

/**
 * Fragment首頁-Contract
 */

public interface MHomeContract {
    /**
     * 首頁 -IView
     */
    interface MHomeIView extends BaseView {
        /**
         * 加载快捷功能
         */
        void loadShortcutFunction(List<MShortcutFunctionInfo> mList);

        /**
         * 新增快捷功能
         */
        void addedShortcutFunction();

        /**
         * 加载销售状况
         */
        void loadSalesDetails(MSalesDetailsInfo mSalesDetailsInfo);

    }

    /**
     * 首頁-Presenter
     */
    abstract class MIHomeActivityP extends BasePresenter<MHomeIView> {
        /**
         * 加载快捷功能
         */
        public abstract void loadShortcutFunction();

        /**
         * 新增快捷功能
         */
        public abstract void addedShortcutFunction(MShortcutFunctionInfo mShortcutFunctionInfo);

        /**
         * 延時一秒-顯示佈局
         */
        public abstract void delayedDisplay();


    }

    /**
     * 首頁-Model
     */
    abstract class MIHomeModel extends BaseModel {
        /**
         * 获取销售状况
         */
        public abstract Observable<String> getSalesDetails();
    }
}
