package ch.erp.management.mvp.ui.activity;

import android.animation.AnimatorSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.erp.management.R;
import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MLoginContract;
import ch.erp.management.mvp.presenter.MLoginActivityP;
import ch.erp.management.mvp.utils.General.MActivityJumpUtils;
import ch.erp.management.mvp.utils.General.MDialogUtils;


/**
 * 登錄
 */
public class MLoginActivity extends BaseActivity<MLoginContract.MLoginIView, MLoginActivityP> implements MLoginContract.MLoginIView {
    private TextView mLoginTex;
    private LinearLayout mUPLinear;
//  private AVLoadingIndicatorView mIndicatorView;

    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return R.layout.activity_login;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MLoginActivityP initPresenter() {
        return new MLoginActivityP();
    }

    /**
     * 初始化控件
     */
    @Override
    public void findViews() {
        /*登录**/
        mLoginTex = getViewObjectOfId(R.id.TextView_MLoginActivity_login);

        mUPLinear = getViewObjectOfId(R.id.LinearLayout_MLoginActivity_UP);
    }

    /**
     * 配置控件初始化状态
     */
    @Override
    public void configureView() {
        /**禁止滑动关闭*/setSwipeBackEnable(false);
    }

    /**
     * 设置监听
     */
    @Override
    public void setListener() {
        mLoginTex.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        //mPresenter.openTheAnimation(mUPLinear);
    }

    /**
     * 响应单击监听
     */
    @Override
    public void responseClick(int mWeigetId) {
        switch (mWeigetId) {
            case R.id.TextView_MLoginActivity_login:
                mPresenter.delayedJumpMain();
                break;
        }
    }

    /**
     * 显示等待框
     */
    @Override
    public void showLoading() {
        MDialogUtils.getDialogUtils().completeCustomPromptDialog(this, R.layout.erp_waitbox_loading);
    }

    /**
     * 隐藏等待框
     */
    @Override
    public void hideLoading() {
        MDialogUtils.getDialogUtils().closecompleteCustomPromptDialog();
    }

    /**
     * 进入主页
     */
    @Override
    public void delayedJump() {
        MActivityJumpUtils.getActivityJumpUtils().jumpSurfaceActivityOfNoAni (this, MainActivity.class);
        //MDefApplication.getInstance().finishDefActivity(MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * 开启进入动画
     *
     * @param objectAnimator
     */
    @Override
    public void openTheAnimation(AnimatorSet objectAnimator) {
        objectAnimator.start();
    }

    /**
     * 回退监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MDefApplication.getInstance().AppExit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
