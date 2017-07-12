package ch.erp.management.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MInviteEmployeesContract;
import ch.erp.management.mvp.presenter.MInviteEmployeesActivityP;

/**
 * 邀请员工
 */

public class MInviteEmployeesActivity extends
        BaseActivity<MInviteEmployeesContract.MInviteEmployeesIView, MInviteEmployeesActivityP>
        implements MInviteEmployeesContract.MInviteEmployeesIView {

    /*导航栏*/ private Toolbar mHeadToolbar;
    /*标题*/ private TextView mHeadTitleTex;

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return R.layout.activity_inviteemployees;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MInviteEmployeesActivityP initPresenter() {
        return new MInviteEmployeesActivityP();
    }

    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /**toolBar*/mHeadToolbar = getViewObjectOfId(R.id.toolbar_MInviteEmployeesActivity_head);
        /**头部标题*/mHeadTitleTex = (TextView) mHeadToolbar.findViewById(R.id.TextView_erp_title);
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
        mHeadTitleTex.setText("邀请员工");
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
