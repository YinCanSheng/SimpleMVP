package ch.erp.management.mvp.ui.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.List;

import ch.erp.management.R;
import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MMainContract;
import ch.erp.management.mvp.presenter.MMainActivityP;
import ch.erp.management.mvp.ui.adapter.MMainViewpagerAdapter;
import ch.erp.management.mvp.ui.customView.MDefViewPager;
import ch.erp.management.mvp.utils.General.MConstInt;
import ch.erp.management.mvp.utils.General.MScreenParaUtils;

/**
 * 主頁-管理四个Fragment
 * 首页、库存、报表、财务
 */
public class MainActivity extends BaseActivity<MMainContract.MMainIView, MMainActivityP>
        implements MMainContract.MMainIView {
    /*导航栏*/ private Toolbar mHeadToolbar;

    /*侧边栏*/private MenuDrawer menuDrawer;

    /*标题*/ private TextView mHeadTitleTex;

    /*ViewPager-滑動視圖*/private MDefViewPager mDefViewPager;

    /* image集合 */private List<ImageView> mImageVList;

    /* textView集合 */private List<TextView> mTextVList;

    /*上一次点击底部的状态*/ private int mLastStateID = 0;

    /*RelativeLayout**/
    /*账户信息*/private RelativeLayout mAccountInfoRelati;

    /*LinearLayout**/
    /*角色管理*/private LinearLayout mRoleManagementLinear;
    /*邀请员工*/private LinearLayout mInviteEmployeesLinear;
    /*切换公司*/private LinearLayout mSwitchCompaniesLinear;
    /*系统设置*/private LinearLayout mSystemSettingsLinear;
    /*首页*/private LinearLayout mHomeLinear;
    /*库存*/private LinearLayout mStockLinear;
    /*报表*/private LinearLayout mStatementLinear;
    /*财务*/private LinearLayout mFinanceLinear;

    /*ImageView**/
    /*首页*/private ImageView mHomeImage;
    /*库存*/private ImageView mStockImage;
    /*报表*/private ImageView mStatementImage;
    /*财务*/private ImageView mFinanceImage;

    /*TextView**/
    /*首页*/private TextView mHomeTex;
    /*库存*/private TextView mStockTex;
    /*报表*/private TextView mStatementTex;
    /*财务*/private TextView mFinanceTex;

    @Override
    public int getLayoutResID() {

        return 0;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MMainActivityP initPresenter() {
        return new MMainActivityP();
    }


    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /**加载侧滑页*/loadLayoutOfMenuDrawer();
        /**toolBar*/mHeadToolbar = getViewObjectOfId(R.id.toolbar_MainActivity_head);
        /**头部标题*/mHeadTitleTex = (TextView) mHeadToolbar.findViewById(R.id.TextView_erp_title);
        /**ViewPager-滑動視圖*/mDefViewPager = getViewObjectOfId(R.id.MViewPager_MainActivity_content);

        /*RelativeLayout*/
        /**账户信息*/mAccountInfoRelati = getViewObjectOfId(R.id.RelativeLayout_menuview_AccountInfo);

        /*LinearLayout*/
        /**角色管理*/mRoleManagementLinear = getViewObjectOfId(R.id.Linearlayout_menuview_RoleManagement);
        /**邀请员工*/mInviteEmployeesLinear = getViewObjectOfId(R.id.LinearLayout_menuview_InviteEmployees);
        /**切换公司*/mSwitchCompaniesLinear = getViewObjectOfId(R.id.LinearLayout_menuview_SwitchCompanies);
        /**系统设置*/mSystemSettingsLinear = getViewObjectOfId(R.id.LinearLayout_menuview_SystemSettings);

        /**首頁*/mHomeLinear = getViewObjectOfId(R.id.LinearLay_MainActivity_home);
        /**库存*/mStockLinear = getViewObjectOfId(R.id.LinearLay_MainActivity_Stock);
        /**报表*/mStatementLinear = getViewObjectOfId(R.id.LinearLay_MainActivity_Statement);
        /**财务*/mFinanceLinear = getViewObjectOfId(R.id.LinearLay_MainActivity_Finance);

        /*ImageView*/
        /**首頁*/mHomeImage = getViewObjectOfId(R.id.ImageView_MainActivity_home);
        /**库存*/mStockImage = getViewObjectOfId(R.id.ImageView_MainActivity_Stock);
        /**报表*/mStatementImage = getViewObjectOfId(R.id.ImageView_MainActivity_Statement);
        /**财务*/mFinanceImage = getViewObjectOfId(R.id.ImageView_MainActivity_Finance);

         /*TextView*/
        /**首頁*/mHomeTex = getViewObjectOfId(R.id.TextView_MainActivity_home);
        /**库存*/mStockTex = getViewObjectOfId(R.id.TextView_MainActivity_Stock);
        /**报表*/mStatementTex = getViewObjectOfId(R.id.TextView_MainActivity_Statement);
        /**财务*/mFinanceTex = getViewObjectOfId(R.id.TextView_MainActivity_Finance);
    }

    /**
     * 配置控件初始化状态
     */
    @Override
    public void configureView() {
        /**禁止滑动关闭**/setSwipeBackEnable(false);
        /**设置ViewPage缓存邻边三个界面*/mDefViewPager.setOffscreenPageLimit(3);
        /**替换ActionBar*/setSupportActionBar(mHeadToolbar);
        /**给左上角回退图标*/getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /**图标可点击*/getSupportActionBar().setHomeButtonEnabled(true);
        /**显示图标*/getSupportActionBar().setDisplayShowHomeEnabled(true);
        /**置空标题*/getSupportActionBar().setDisplayShowTitleEnabled(false);
        /**自定義左側圖標*/mHeadToolbar.setNavigationIcon(R.drawable.openmenu);

    }

    /**
     * 配置左侧侧滑页
     */
    private void loadLayoutOfMenuDrawer() {
        /**初始化侧滑*/menuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, Position.LEFT);
        /**加载主布局*/menuDrawer.setContentView(R.layout.activity_main);
        /**加载菜单布局*/menuDrawer.setMenuView(R.layout.activity_main_menuview);
        /**自定義陰影*/menuDrawer.setDropShadow(R.drawable.shape_menu_shadow);
        /**设置阴影宽度*/menuDrawer.setDropShadowSize(30);
        /**设置侧滑页的宽度*/menuDrawer.setMenuSize(4 * MScreenParaUtils.getScreenWidth(this) / 5);
        ///**自动开启菜单视图*/menuDrawer.peekDrawer();
    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {
        /**回退监听-打開關閉侧边栏*/
        mHeadToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuDrawer.toggleMenu();
            }
        });
        /**ViewPager滑动监听*/
        mDefViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /*滑动中**/
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /*滑动完成**/
            @Override
            public void onPageSelected(int position) {
                if (isNullOfObject(mPresenter)) {
                    mPresenter.updateTitleAndIndicator(position, mLastStateID);
                    mLastStateID = position;
                }
            }

            /*滑动状态改变**/
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*RelativeLayout监听*/
        /**账户信息*/mAccountInfoRelati.setOnClickListener(this);

        /*LinearLayout-監聽*/
        /**角色管理*/mRoleManagementLinear.setOnClickListener(this);
        /**邀請員工*/mInviteEmployeesLinear.setOnClickListener(this);
        /**切换公司*/mSwitchCompaniesLinear.setOnClickListener(this);
        /**系统设置*/mSystemSettingsLinear.setOnClickListener(this);
        /**主頁*/mHomeLinear.setOnClickListener(this);
        /**库存*/mStockLinear.setOnClickListener(this);
        /**报表*/mStatementLinear.setOnClickListener(this);
        /**财务*/mFinanceLinear.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        /**添加底部控件到集合*/addFragmentToList();
        /*配置ViewPager*/
        if (isNullOfObject(mPresenter)) {
            mPresenter.loadViewPager();
            mPresenter.updateTitleAndIndicator(0, 0);
        }
    }

    /**
     * 响应单击监听
     */
    @Override
    public void responseClick(int mWeigetId) {
        switch (mWeigetId) {
            /*主页**/
            case R.id.LinearLay_MainActivity_home:
                mDefViewPager.setCurrentItem(0, false);
                break;
            /*库存**/
            case R.id.LinearLay_MainActivity_Stock:
                mDefViewPager.setCurrentItem(1, false);
                break;
            /*报表**/
            case R.id.LinearLay_MainActivity_Statement:
                mDefViewPager.setCurrentItem(2, false);
                break;
            /*财务**/
            case R.id.LinearLay_MainActivity_Finance:
                mDefViewPager.setCurrentItem(3, false);
                break;
            /*账户信息**/
            case R.id.RelativeLayout_menuview_AccountInfo:
                /**关闭侧边菜单*/menuDrawer.closeMenu();
                /**跳轉角色管理*/mPresenter.delayedJump(this, MAccountInfoActivity.class);
                break;
            /*角色管理**/
            case R.id.Linearlayout_menuview_RoleManagement:
                /**关闭侧边菜单*/menuDrawer.closeMenu();
                /**跳轉角色管理*/mPresenter.delayedJump(this, MRoleManagementActivity.class);
                break;
            /*邀請員工**/
            case R.id.LinearLayout_menuview_InviteEmployees:
                /**关闭侧边菜单*/menuDrawer.closeMenu();
                /**跳轉角色管理*/mPresenter.delayedJump(this, MInviteEmployeesActivity.class);
                break;
            /*切换公司**/
            case R.id.LinearLayout_menuview_SwitchCompanies:
                /**关闭侧边菜单*/menuDrawer.closeMenu();
                /**跳轉切换公司*/mPresenter.delayedJump(this, MSwitchCompanyActivity.class);
                break;
            /*系统设置**/
            case R.id.LinearLayout_menuview_SystemSettings:
                /**关闭侧边菜单*/menuDrawer.closeMenu();
                /**跳轉角色管理*/mPresenter.delayedJump(this, MSystemSettingsActivity.class);
                break;
        }
    }

    /**
     * 加载ViewPager-填充页面
     *
     * @param mMainViewpagerAdapter
     */
    @Override
    public void loadViewPager(MMainViewpagerAdapter mMainViewpagerAdapter) {
        if (isNullOfObject(mDefViewPager)) {
            mDefViewPager.setAdapter(mMainViewpagerAdapter);

        }
    }


    /**
     * 更新顶部标题
     */
    @Override
    public void updateTopTitle(String mTitle) {
        mHeadTitleTex.setText(mTitle);
    }

    /**
     * 更新底部指示
     */
    @Override
    public void updateBottomIndicator(int nowStateID, int lastStateID) {
        /* 灰色图标 */
        mImageVList.get(lastStateID).setImageResource(MConstInt.mMainImageBodyBlue[lastStateID]);
        /* 深色图标 */
        mImageVList.get(nowStateID).setImageResource(MConstInt.mMainImageMazarine[nowStateID]);
        /* 灰色文字 */
        mTextVList.get(lastStateID).setTextColor(Color.parseColor("#888888"));
        /* 深色文字 */
        mTextVList.get(nowStateID).setTextColor(Color.parseColor("#6495ED"));
    }

    /**
     * 点击Back两次退出程序
     */
    @Override
    public void dropOutApp() {
        MDefApplication.getInstance().AppExit();
    }

    /**
     * 回退監聽
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (menuDrawer.getDrawerState() == MenuDrawer.STATE_OPEN ||
                    menuDrawer.getDrawerState() == MenuDrawer.STATE_OPENING) {
                menuDrawer.closeMenu();
            } else {
                if (isNullOfObject(mPresenter)) {
                    mPresenter.dropOutApp();
                }
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 显示等待條
     */
    @Override
    public void showLoading() {

    }

    /**
     * 隐藏等待條
     */
    @Override
    public void hideLoading() {

    }

    /**
     * 添加数据至集合-统一管理标签按钮
     */
    private void addFragmentToList() {
        mTextVList = new ArrayList<>();
        mImageVList = new ArrayList<>();
        mImageVList.add(mHomeImage);
        mImageVList.add(mStockImage);
        mImageVList.add(mStatementImage);
        mImageVList.add(mFinanceImage);
        mTextVList.add(mHomeTex);
        mTextVList.add(mStockTex);
        mTextVList.add(mStatementTex);
        mTextVList.add(mFinanceTex);
    }
}
