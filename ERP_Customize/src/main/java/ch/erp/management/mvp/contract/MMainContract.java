package ch.erp.management.mvp.contract;

import android.support.v7.app.AppCompatActivity;

import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.base.BasePresenter;
import ch.erp.management.mvp.base.BaseView;
import ch.erp.management.mvp.ui.adapter.MMainViewpagerAdapter;

/**
 * 主页-Contract
 */

public interface MMainContract {

    /**
     * 主页-IView
     */
    interface MMainIView extends BaseView {
        /**
         * 加载ViewPager-填充页面
         */
        void loadViewPager(MMainViewpagerAdapter mMainViewpagerAdapter);

        /**
         * 点击Back两次退出程序
         */
        void dropOutApp();

        /**
         * 更新顶部标题
         */
        void updateTopTitle(String mTitle);

        /**
         * 更新底部指示
         */
        void updateBottomIndicator(int nowStateID, int lastStateID);
    }

    /**
     * 主頁-Presenter
     */
    abstract class MIMainActivityP extends BasePresenter<MMainIView> {
        /**
         * 加载ViewPager-填充页面
         */
        public abstract void loadViewPager();

        /**
         * 点击Back两次退出程序
         */
        public abstract void dropOutApp();

        /**
         * 更新頂部標題与底部指示
         */
        public abstract void updateTitleAndIndicator(int nowStateID, int lastStateID);
        /**
         * 延时跳转-等待侧边菜单关闭
         */
        public abstract void delayedJump(AppCompatActivity mContext, Class<?> mClass);
    }

    /**
     * 主页-Model
     */
    abstract class MIMianModel extends BaseModel {

    }
}
