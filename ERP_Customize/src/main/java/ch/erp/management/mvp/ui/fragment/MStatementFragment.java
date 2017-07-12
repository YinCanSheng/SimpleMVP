package ch.erp.management.mvp.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.RelativeLayout;
import com.liaoinstan.springview.container.AcFunHeader;
import com.liaoinstan.springview.widget.SpringView;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseFragment;
import ch.erp.management.mvp.contract.MStatementContract;
import ch.erp.management.mvp.presenter.MStatementFragmentP;

/**
 * 报表-Fragment
 */

public class MStatementFragment extends BaseFragment<MStatementContract.MStatementIView, MStatementFragmentP> implements MStatementContract.MStatementIView {
    /*等待框*/private RelativeLayout mLoadingRelat;
    /*回弹View*/private SpringView mReboundSpringView;

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_statement;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MStatementFragmentP initPresenter() {
        return new MStatementFragmentP();
    }


    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /**等待框*/mLoadingRelat = (RelativeLayout) getViewObjectOfId(R.id.MStatementFragment_loading);
        /**回弹View*/mReboundSpringView = getViewObjectOfId(R.id.SpringView_MStatementFragment_Rebound);
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
        // MToastUtils.showCase(getActivity(), "再次进入報表");
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
