package ch.erp.management.mvp.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.liaoinstan.springview.container.AcFunHeader;
import com.liaoinstan.springview.widget.SpringView;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseFragment;
import ch.erp.management.mvp.contract.MStockContract;
import ch.erp.management.mvp.presenter.MStockFragmentP;
import ch.erp.management.mvp.ui.activity.MAccountInfoActivity;
import ch.erp.management.mvp.ui.activity.MInventoryStatusActivity;
import ch.erp.management.mvp.ui.activity.MPurchaseReturnsActivity;
import ch.erp.management.mvp.ui.activity.MPurchaseWareActivity;
import ch.erp.management.mvp.utils.General.MActivityJumpUtils;
import ch.erp.management.mvp.utils.General.MConstInt;


/**
 * 库存-Fragment
 */

public class MStockFragment extends BaseFragment<MStockContract.MStockIView, MStockFragmentP> implements MStockContract.MStockIView {

    /*等待框*/private RelativeLayout mLoadingRelat;
    /*回弹View*/private SpringView mReboundSpringView;

    /*LinearLayout**/
    /*库存状况*/private LinearLayout mInventoryStatusLiear;
    /*采购入库*/private LinearLayout mPurchaseWareLinear;
    /*采购退货*/private LinearLayout mPurchaseReturnsLinear;

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_stock;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MStockFragmentP initPresenter() {
        return new MStockFragmentP();
    }

    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /**等待框*/mLoadingRelat = (RelativeLayout) getViewObjectOfId(R.id.MStockFragment_loading);
        /**回弹View*/mReboundSpringView = getViewObjectOfId(R.id.SpringView_MStockFragment_Rebound);

        /*LinearLayout*/
        /**库存状况*/mInventoryStatusLiear = getViewObjectOfId(R.id.LinearLayout_MStockFragment_InventoryStatus);
        /**采购入库*/mPurchaseWareLinear = getViewObjectOfId(R.id.LinearLayout_MStockFragment_PurchaseWare);
        /**采购退货*/mPurchaseReturnsLinear = getViewObjectOfId(R.id.LinearLayout_MStockFragment_PurchaseReturns);
    }

    /**
     * 配置控件初始化状态
     */
    @Override
    public void configureView() {
        mReboundSpringView.setHeader(new AcFunHeader(getActivity(), 0));
        mReboundSpringView.setFooter(new AcFunHeader(getActivity(), 0));
    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {
        mInventoryStatusLiear.setOnClickListener(this);
        mPurchaseWareLinear.setOnClickListener(this);
        mPurchaseReturnsLinear.setOnClickListener(this);
    }

    /**
     * 数据操作
     */
    @Override
    public void initData() {
        if (isNullOfObjec(mPresenter)) {
            mPresenter.delayedDisplay();
        }
    }

    /**
     * 非首次加载
     */
    @Override
    public void againLoading() {
        // MToastUtils.showCase(getActivity(), "再次进入庫存");
    }

    /**
     * 单击监听响应
     *
     * @param mWeigetId
     */
    @Override
    public void responseClick(int mWeigetId) {
        switch (mWeigetId) {
            /*库存状况**/
            case R.id.LinearLayout_MStockFragment_InventoryStatus:
                MActivityJumpUtils.getActivityJumpUtils().
                        jumpSurfaceActivityOfcustomizeAni(getActivity(),
                                MInventoryStatusActivity.class, MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
                break;
            /*采购入库**/
            case R.id.LinearLayout_MStockFragment_PurchaseWare:
                MActivityJumpUtils.getActivityJumpUtils().
                        jumpSurfaceActivityOfcustomizeAni(getActivity(),
                                MPurchaseWareActivity.class, MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
                break;
            /*采购退货**/
            case R.id.LinearLayout_MStockFragment_PurchaseReturns:
                MActivityJumpUtils.getActivityJumpUtils().
                        jumpSurfaceActivityOfcustomizeAni(getActivity(),
                                MPurchaseReturnsActivity.class, MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
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
        /**淡出-属性动画*/
        ObjectAnimator mFadeOutAnimator = ObjectAnimator.ofFloat(mLoadingRelat, "alpha", 1, 0);
        /**結束時隐藏等待界面*/
        mFadeOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoadingRelat.setVisibility(View.GONE);
            }
        });
        /**动画持续两秒*/
        mFadeOutAnimator.setDuration(500).start();
    }
}
