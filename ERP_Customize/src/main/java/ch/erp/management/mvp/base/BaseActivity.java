package ch.erp.management.mvp.base;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import ch.erp.management.R;
import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.utils.General.MConstInt;
import ch.erp.management.mvp.utils.General.MStatusBarUtils;
import ch.erp.management.mvp.utils.SwipeBack.SwipeBackActivity;

/**
 * activity基类
 *
 * @author ChenHong
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>>
        extends SwipeBackActivity
        implements OnClickListener,
        Baselogic {

    /*主持者*/public T mPresenter = null;
    /*緩存控件-集合*/private SparseArray<View> mViewsSparesA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*透明状态栏*/
       MStatusBarUtils.transparencyBar(this);

        /*把每一个Activity添加到栈 */
        MDefApplication.getInstance().addActivity(this);

        /*加载界面布局*/
        if (getLayoutResID() != 0) {
            setContentView(getLayoutResID());
        }
         /*初始化控件集合-用于緩存*/
        mViewsSparesA = new SparseArray<View>();

       /*实例化主持者*/
        mPresenter = initPresenter();

         /*绑定View*/
        if (isNullOfObject(mPresenter)) {

            mPresenter.attachView((V) this);
        }

        /*初始化控件*/
        findViews();

        /*配置控件初始状态*/
        configureView();

        /*设置监听*/
        setListener();

        /*数据操作*/
        initData();
    }

    /**
     * 实例化presenter
     */
    public abstract T initPresenter();

    /**
     * 单击监听响应
     */
    @Override
    public void onClick(View mView) {
        responseClick(mView.getId());

    }

    /**
     * 按下Back时设置一个退出动画
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MDefApplication.getInstance().finishDefActivity(MConstInt.mSwitchingAnim[0], R.anim.translate_right_out);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 从栈清除Activity
     * 解除View的绑定
     * 统一退订
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        MDefApplication.getInstance().finishActivity(this);
    }


    /**
     * 通过控件ID得到控件对象
     */
    public <E extends View> E getViewObjectOfId(int ViewId) {
           /*拿取已加载过的控件*/
        E mView = (E) mViewsSparesA.get(ViewId);
            /*没有加载过则加载控件*/
        if (mView == null) {
                /*初始化控件*/
            mView = (E) findViewById(ViewId);
                /*把控件添加到集合*/
            mViewsSparesA.put(ViewId, mView);
        }
        return mView;
    }

    /**
     * 判断对象是否为空
     */
    public boolean isNullOfObject(Object mObject) {
        if (mObject != null) {
            return true;
        }
        return false;
    }
}
