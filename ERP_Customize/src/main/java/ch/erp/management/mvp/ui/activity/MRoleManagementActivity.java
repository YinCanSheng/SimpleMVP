package ch.erp.management.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MRoleManagementContract;
import ch.erp.management.mvp.presenter.MRoleManagementP;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 角色管理
 */

public class MRoleManagementActivity extends
        BaseActivity<MRoleManagementContract.MRoleManagementIView, MRoleManagementP>
        implements MRoleManagementContract.MRoleManagementIView {

    /*导航栏*/ private Toolbar mHeadToolbar;
    /*标题*/ private TextView mHeadTitleTex;
    /*提示*/private TextView mToastTex;

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return R.layout.activity_rolemanagement;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MRoleManagementP initPresenter() {
        return new MRoleManagementP();
    }


    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /**toolBar*/mHeadToolbar = getViewObjectOfId(R.id.toolbar_MRoleManagementActivity_head);
        /**头部标题*/mHeadTitleTex = (TextView) mHeadToolbar.findViewById(R.id.TextView_erp_title);
        /**提示*/mToastTex = getViewObjectOfId(R.id.TextView_MRoleManagementActivity_toast);
    }

    /**
     * 配置控件初始化状态
     */
    @Override
    public void configureView() {
        /*替换ActionBar*/
        setSupportActionBar(mHeadToolbar);
        /*给左上角回退图标*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*图标可点击*/
        getSupportActionBar().setHomeButtonEnabled(true);
        /*显示图标*/
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        /*置空标题*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*設置標題*/
        mHeadTitleTex.setText("角色管理");
    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {

    }

    /**
     * 数据操作
     */
    @Override
    public void initData() {
        Observable.timer(3, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).flatMap(new Func1<Long, Observable<?>>() {
            @Override
            public Observable<?> call(Long aLong) {
                mToastTex.setText("角色管理");
                return null;
            }
        }).subscribe();
    }

    /**
     * 单击监听响应
     *
     * @param mWeigetId
     */
    @Override
    public void responseClick(int mWeigetId) {

    }

    /**
     * 显示等待界面
     */
    @Override
    public void showLoading() {

    }

    /**
     * 隐藏等待界面
     */
    @Override
    public void hideLoading() {

    }
}
