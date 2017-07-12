package ch.erp.management.mvp.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * 通用Fragment 定义子Fragment的逻辑顺序
 *
 * @author ChenHong
 */
public abstract class BaseFragment<V, T extends BasePresenter<V>>
        extends Fragment
        implements OnClickListener, Baselogic {
    /*父布局*/ public View mLyoutView;
    /*主持者*/public T mPresenter = null;
    /*view是否销毁*/public Boolean IsDestroy;
    /*緩存控件-集合*/private SparseArray<View> mViewsSparesA;
    /*当前Fragment是否可见*/private boolean isFragmentVisible = false;
    /*是否与View建立起映射关系*/private boolean isInitView = false;
    /*是否是第一次加载数据*/private boolean isFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**引入订单界面 如果该视图没有根视图 第二个参数可设置null*/mLyoutView = inflater.inflate(getLayoutResID(), null);

        /**初始化控件集合-用于緩存*/mViewsSparesA = new SparseArray<View>();

        /**实例化主持者*/mPresenter = initPresenter();

        /**绑定View*/if (isNullOfObjec(mPresenter)) {

            mPresenter.attachView((V) this);
        }

        /**初始化控件*/findViews();

        /**配置控件初始状态*/configureView();

        /**与View完成映射*/isInitView = true;

        /**设置监听*/setListener();

        /**数据操作-获取*/lazyLoadData();

        return mLyoutView;
    }

    /**
     * 实例化presenter
     */
    public abstract T initPresenter();

    /**
     * 非首次加载
     */
    public abstract void againLoading();

    /**
     * 单击响应
     */
    @Override
    public void onClick(View mView) {
        responseClick(mView.getId());

    }

    /**
     * 懒加载/优先于onCreateView
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*可见时*/
        if (isVisibleToUser) {
            isFragmentVisible = true;
            lazyLoadData();
        }
        /*不可见*/
        else {
            isFragmentVisible = false;
        }
    }

    /**
     * 懒加载-数据加载
     */
    private void lazyLoadData() {
        /*暫不加載*/
        if (!isFragmentVisible || !isInitView) {

            return;
        }
        /*首次加載*/
        if (isFirstLoad) {
            /*完成第一次数据加载*/
            initData();

            isFirstLoad = false;

            return;
        }
        /*非首次加载*/
        if (!isFirstLoad) {

            againLoading();

            return;
        }
    }

    /**
     * Fragment-View销毁时-回收
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isNullOfObjec(mPresenter)) {
            mPresenter.detachView();
        }

    }

    /**
     * 通过控件ID得到控件对象
     */
    public <E extends View> E getViewObjectOfId(int ViewId) {
        if (isNullOfObjec(mLyoutView)) {
           /*拿取已加载过的控件*/
            E mView = (E) mViewsSparesA.get(ViewId);
            /*没有加载过则加载控件*/
            if (mView == null) {
                /*初始化控件*/
                mView = (E) mLyoutView.findViewById(ViewId);
                /*把控件添加到集合*/
                mViewsSparesA.put(ViewId, mView);
            }
            return mView;
        }
        return null;
    }

    /**
     * 判断对象是否为空
     */
    public boolean isNullOfObjec(Object mObject) {
        if (mObject != null) {
            return true;
        }
        return false;
    }
}
