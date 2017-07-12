package ch.erp.management.mvp.presenter;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.base.BaseModel;
import ch.erp.management.mvp.contract.MMainContract;
import ch.erp.management.mvp.model.MMainModel;
import ch.erp.management.mvp.ui.adapter.MMainViewpagerAdapter;
import ch.erp.management.mvp.ui.fragment.MFinanceFragment;
import ch.erp.management.mvp.ui.fragment.MHomeFragment;
import ch.erp.management.mvp.ui.fragment.MStatementFragment;
import ch.erp.management.mvp.ui.fragment.MStockFragment;
import ch.erp.management.mvp.utils.General.MActivityJumpUtils;
import ch.erp.management.mvp.utils.General.MToastUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 主页-主持者
 */

public class MMainActivityP extends MMainContract.MIMainActivityP {

    /*数据模型**/private BaseModel mMainModel;
    /*Fragment集合**/private List<Fragment> mFragmentList;
    /*標題集合*/private List<String> mTitleList;
    /*首頁-Fragment**/private MHomeFragment mHomeFragment;
    /*庫存-Fragment**/private MStockFragment mStockFragment;
    /*报表-Fragment**/ private MStatementFragment mStatementFragment;
    /*财务-Fragment**/private MFinanceFragment mFinanceFragment;
    /*回退标识**/public int backState = 0;

    /**
     * 初始化配置
     */
    public MMainActivityP() {

       /*初始化模型**/
        mMainModel = new MMainModel();

        /*初始化标题集合**/
        mTitleList = new ArrayList<String>();
        mTitleList.add("ERP");
        mTitleList.add("库存");
        mTitleList.add("报表");
        mTitleList.add("财务");
    }


    /**
     * 加载ViewPager-填充页面
     */
    @Override
    public void loadViewPager() {
        mFragmentList = new ArrayList<Fragment>();
        /*初始化碎片*/
        mHomeFragment = new MHomeFragment();
        mStockFragment = new MStockFragment();
        mStatementFragment = new MStatementFragment();
        mFinanceFragment = new MFinanceFragment();
        /*添加碎片至集合*/
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mStockFragment);
        mFragmentList.add(mStatementFragment);
        mFragmentList.add(mFinanceFragment);
        /*回調Activity刷新ui*/
        mView.loadViewPager(new MMainViewpagerAdapter
                (MDefApplication.getInstance().currentActivity().
                        getSupportFragmentManager(),
                        mFragmentList));
    }

    /**
     * 点击Back两次退出程序
     */
    @Override
    public void dropOutApp() {
        if (backState == 1) {
            backState = 0;
            mView.dropOutApp();
        } else {
            MToastUtils.showCase(MDefApplication.mContext, "亲,再按一次就退出了哦");
            backState = 1;
            /*兩秒内重置退出**/
            Observable.timer(2, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<Long, Observable<?>>() {
                        @Override
                        public Observable<?> call(Long aLong) {
                            backState = 0;
                            return null;
                        }
                    }).subscribe();
        }
    }

    /**
     * 更新頂部標題与底部指示
     */
    @Override
    public void updateTitleAndIndicator(int nowStateID, int lastStateID) {
        if (mView != null) {
            /**更新顶部标题*/
            if (mTitleList != null && mTitleList.size() > nowStateID) {

                mView.updateTopTitle(mTitleList.get(nowStateID));

            }
            /*更新底部状态**/
            mView.updateBottomIndicator(nowStateID, lastStateID);
        }

    }

    /**
     * 延时跳转-等待侧边菜单关闭
     */
    @Override
    public void delayedJump(final AppCompatActivity mContext, final Class<?> mClass) {
       /*等待侧边菜单关闭后跳转**/
        Observable.timer(300, TimeUnit.MILLISECONDS).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                flatMap(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        MActivityJumpUtils.getActivityJumpUtils().jumpSurfaceFadeActivity(mContext, mClass);
                        return null;
                    }
                }).subscribe();
    }
}
