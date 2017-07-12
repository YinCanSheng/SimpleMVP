package ch.erp.management.mvp.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.container.AcFunHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.superrecycleview.superlibrary.adapter.AnimationType;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.callback.ItemDragCallback;
import com.superrecycleview.superlibrary.callback.OnItemDragListener;
import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.List;

import ch.erp.management.R;
import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.base.BaseFragment;
import ch.erp.management.mvp.contract.MHomeContract;
import ch.erp.management.mvp.model.entity.MSalesDetailsInfo;
import ch.erp.management.mvp.model.entity.MShortcutFunctionInfo;
import ch.erp.management.mvp.presenter.MHomeFragmentP;
import ch.erp.management.mvp.ui.adapter.MShortcutRecylerViewAdapter;
import ch.erp.management.mvp.ui.customView.MScrollGridLayoutManager;
import ch.erp.management.mvp.utils.General.MToastUtils;

/**
 * 首頁-Fragment
 */

public class MHomeFragment extends BaseFragment<MHomeContract.MHomeIView, MHomeFragmentP>
        implements MHomeContract.MHomeIView, OnItemDragListener, SuperBaseAdapter.OnItemClickListener<MShortcutFunctionInfo> {

    /*等待框*/private RelativeLayout mLoadingRelat;
    /*回弹View*/private SpringView mReboundSpringView;
    /*新增模块-RecyclerView*/private SuperRecyclerView mAddToolRecyclerV;
    /*RxJava测试View*/private TextView mTestTex;
    /*自定义快捷功能-适配器*/private MShortcutRecylerViewAdapter mShortcutRecylerViewAdapter;
    /*自定义快捷功能-尾部View*/private View mShortcutRecylerViewFooterView;
    /**/private ItemTouchHelper mItemTouchHelper;
    /**/private ItemDragCallback mItemDragAndSwipeCallback;

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_home;
    }

    /**
     * 实例化presenter3
     */
    @Override
    public MHomeFragmentP initPresenter() {
        return new MHomeFragmentP();
    }

    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /**等待框*/mLoadingRelat = (RelativeLayout) getViewObjectOfId(R.id.MHomeFragment_loading);
        /**RxJava测试*/mTestTex = getViewObjectOfId(R.id.TextView_MHomeFragment_Test);
        /**新增模块-RecyclerView*/mAddToolRecyclerV = getViewObjectOfId(R.id.SuperRecylerView_MHomeFragment_addModel);
        /**回弹View*/mReboundSpringView = getViewObjectOfId(R.id.SpringView_MHomeFragment_Rebound);
        /**自定义快捷功能-尾部View*/mShortcutRecylerViewFooterView = getActivity().getLayoutInflater().
                inflate(R.layout.recyclerview_shortcutfunction_footerview, (ViewGroup) mAddToolRecyclerV.getParent(), false);
    }

    /**
     * 配置控件初始化状态
     */
    @Override
    public void configureView() {
        /**实例化LayoutManager*/MScrollGridLayoutManager mScrollGridLayoutManager = new MScrollGridLayoutManager(getActivity(), 4);
        /**禁止滑动*/mScrollGridLayoutManager.setScrollEnabled(false);
        /**设置全局下拉头布局*/mReboundSpringView.setHeader(new AcFunHeader(getActivity(), 0));
        /**设置全局下拉尾布局*/mReboundSpringView.setFooter(new AcFunHeader(getActivity(), 0));

        /**设置RecyclerView-新增模块-网格布局*/mAddToolRecyclerV.setLayoutManager(mScrollGridLayoutManager);
        /**设置RecyclerView-新增模块-允许下拉*/mAddToolRecyclerV.setRefreshEnabled(false);
        /**设置RecyclerView-新增模块-允许上拉*/mAddToolRecyclerV.setLoadMoreEnabled(false);
    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {
        mShortcutRecylerViewFooterView.setOnClickListener(this);
    }

    /**
     * 数据操作
     */
    @Override
    public void initData() {
        if (isNullOfObjec(mPresenter)) {
            mPresenter.loadShortcutFunction();
            mPresenter.delayedDisplay();
        }
    }

    /**
     * 非首次加载
     */
    @Override
    public void againLoading() {
        //MToastUtils.showCase(getActivity(), "再次进入主页");
        if (mTestTex.getVisibility() == View.VISIBLE) {
            // mTestTex.setVisibility(View.GONE);
        }
    }

    /**
     * 单击监听响应
     *
     * @param mWeigetId
     */
    @Override
    public void responseClick(int mWeigetId) {
        switch (mWeigetId) {
            case R.id.recyclerview_shortcutfuntion_footerView:
                MToastUtils.showCase(getActivity(), "新增模块");
                break;
        }
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
        /**淡出-属性动画*/ObjectAnimator mFadeOutAnimator = ObjectAnimator.ofFloat(mLoadingRelat, "alpha", 1, 0);
        /**結束時隐藏等待界面*/mFadeOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoadingRelat.setVisibility(View.GONE);
            }
        });
        /**动画持续两秒*/mFadeOutAnimator.setDuration(500).start();
    }

    /**
     * 加载销售状况
     */
    @Override
    public void loadSalesDetails(MSalesDetailsInfo mSalesDetailsInfo) {
        // mTestTex.setText(mSalesDetailsInfo.getmName());
    }

    /**
     * 加载快捷功能
     */
    @Override
    public void loadShortcutFunction(List<MShortcutFunctionInfo> mList) {
        /**
         * 实例化适配器
         */
        mShortcutRecylerViewAdapter = new MShortcutRecylerViewAdapter(MDefApplication.mContext, mList);
        /**
         * 设置适配器
         */
        if (mShortcutRecylerViewAdapter != null) {
            /**添加尾布局*/mShortcutRecylerViewAdapter.addFooterView(mShortcutRecylerViewFooterView);
            /**添加Item动画*/mShortcutRecylerViewAdapter.setItemAnimation(AnimationType.SCALE);
            /***/mItemDragAndSwipeCallback = new ItemDragCallback(mShortcutRecylerViewAdapter);
            /***/mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
            /***/mItemTouchHelper.attachToRecyclerView(mAddToolRecyclerV);
            /***/mItemDragAndSwipeCallback.setDragMoveFlags(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
            /***/mShortcutRecylerViewAdapter.enableDragItem(mItemTouchHelper);
            /***/mShortcutRecylerViewAdapter.setOnItemDragListener(this);
            /***/mShortcutRecylerViewAdapter.setOnItemClickListener(this);
            /**适配*/mAddToolRecyclerV.setAdapter(mShortcutRecylerViewAdapter);
        }
    }

    /**
     * 新增快捷功能
     */
    @Override
    public void addedShortcutFunction() {
        if (mShortcutRecylerViewAdapter != null) {
            mShortcutRecylerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        mAddToolRecyclerV.setRefreshEnabled(false);
    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        mAddToolRecyclerV.setRefreshEnabled(true);
    }

    @Override
    public void onItemClick(View view, MShortcutFunctionInfo item, int position) {
        MToastUtils.showCase(getActivity(), item.getmShortcutTex());
    }
}
