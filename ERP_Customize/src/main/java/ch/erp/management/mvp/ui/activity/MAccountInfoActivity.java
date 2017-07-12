package ch.erp.management.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MAccountInfoContract;
import ch.erp.management.mvp.presenter.MAccountInfoActivityP;

/**
 * 账户信息
 */

public class MAccountInfoActivity extends
        BaseActivity<MAccountInfoContract.MAccountInfoIView, MAccountInfoActivityP>
        implements MAccountInfoContract.MAccountInfoIView {

    /*导航栏*/ private Toolbar mHeadToolbar;
    /*标题*/ private TextView mHeadTitleTex;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_accountinfo;
    }

    @Override
    public MAccountInfoActivityP initPresenter() {
        return new MAccountInfoActivityP();
    }

    @Override
    public void findViews() {
        /**toolBar*/mHeadToolbar = getViewObjectOfId(R.id.toolbar_MAccountInfoActivity_head);
        /**头部标题*/mHeadTitleTex = (TextView) mHeadToolbar.findViewById(R.id.TextView_erp_title);
    }

    @Override
    public void configureView() {
        /**替换ActionBar*/setSupportActionBar(mHeadToolbar);
        /**给左上角回退图标*/getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**图标可点击*/getSupportActionBar().setHomeButtonEnabled(true);
        /**显示图标*/getSupportActionBar().setDisplayShowHomeEnabled(true);
        /**置空标题*/getSupportActionBar().setDisplayShowTitleEnabled(false);
        /**設置標題*/mHeadTitleTex.setText("账户信息");
    }

    @Override
    public void setListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void responseClick(int mWeigetId) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
