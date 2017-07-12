package ch.erp.management.mvp.ui.activity;

import android.view.KeyEvent;

import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.base.BaseActivity;
import ch.erp.management.mvp.contract.MStartUpContract;
import ch.erp.management.mvp.presenter.MStartUpActivityP;
import ch.erp.management.mvp.utils.General.MActivityJumpUtils;
import ch.erp.management.mvp.utils.General.MConstInt;

/**
 * 启动页
 */
public class MStartUpActivity extends BaseActivity<MStartUpContract.MStartUpIView, MStartUpActivityP>
        implements MStartUpContract.MStartUpIView {


    /**
     * 给实现类用 返回布局资源ID加载界面布局
     */
    @Override
    public int getLayoutResID() {
        return 0;
    }

    /**
     * 实例化presenter
     */
    @Override
    public MStartUpActivityP initPresenter() {

        return new MStartUpActivityP();
    }

    /**
     * 初始化控件
     */
    @Override
    public void findViews() {

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

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        if (isNullOfObject(mPresenter)) {
            mPresenter.delayedJumpMain();
        }
    }

    /**
     * 响应单击监听
     */
    @Override
    public void responseClick(int mWeigetId) {

    }

    /**
     * 显示等待页
     */
    @Override
    public void showLoading() {

    }

    /**
     * 隐藏等待页
     */
    @Override
    public void hideLoading() {

    }

    /**
     * 进入主页
     */
    @Override
    public void delayedJump() {
        MActivityJumpUtils.getActivityJumpUtils().jumpSurfaceActivityOfNoAni(this, MLoginActivity.class);
        MDefApplication.getInstance().finishDefActivity(MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * 启动时屏蔽回退
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode)
            return false;
        return super.onKeyDown(keyCode, event);
    }
}
