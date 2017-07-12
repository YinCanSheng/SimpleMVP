package ch.erp.management.mvp.ui.activity;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MInventoryStatusContract;
import ch.erp.management.mvp.presenter.MInventoryStatusActivityP;

/**
 * 库存状况
 */

public class MInventoryStatusActivity extends
        BaseActivity<MInventoryStatusContract.MInventoryStatusIView, MInventoryStatusActivityP>
        implements MInventoryStatusContract.MInventoryStatusIView {

    /*导航栏*/ private Toolbar mHeadToolbar;
    /*标题*/ private TextView mHeadTitleTex;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_inventorystatus;
    }

    @Override
    public MInventoryStatusActivityP initPresenter() {

        return new MInventoryStatusActivityP();
    }

    @Override
    public void findViews() {
        /**toolBar*/mHeadToolbar = getViewObjectOfId(R.id.toolbar_MInventoryStatusActivity_head);
        /**头部标题*/mHeadTitleTex = (TextView) mHeadToolbar.findViewById(R.id.TextView_erp_title);
    }

    @Override
    public void configureView() {
        /**替换ActionBar*/setSupportActionBar(mHeadToolbar);
        /**给左上角回退图标*/getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**图标可点击*/getSupportActionBar().setHomeButtonEnabled(true);
        /**显示图标*/getSupportActionBar().setDisplayShowHomeEnabled(true);
        /**置空标题*/getSupportActionBar().setDisplayShowTitleEnabled(false);
        /**設置標題*/mHeadTitleTex.setText("库存状况");
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
