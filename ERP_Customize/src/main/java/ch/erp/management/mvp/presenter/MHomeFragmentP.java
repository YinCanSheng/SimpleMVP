package ch.erp.management.mvp.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ch.erp.management.R;
import ch.erp.management.mvp.app.MDefApplication;
import ch.erp.management.mvp.contract.MHomeContract;
import ch.erp.management.mvp.model.MHomeModel;
import ch.erp.management.mvp.model.entity.MSalesDetailsInfo;
import ch.erp.management.mvp.model.entity.MShortcutFunctionInfo;
import ch.erp.management.mvp.model.http.MHttpSubscriber;
import ch.erp.management.mvp.model.json.MMSalesDetailsJson;
import ch.erp.management.mvp.ui.adapter.MShortcutRecylerViewAdapter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 主頁Fragment-主持者
 */
public class MHomeFragmentP extends MHomeContract.MIHomeActivityP {

    /*主页-数据处理*/private MHomeModel mHomeModel;
    /*自定义快捷功能-集合数据*/private List<MShortcutFunctionInfo> mShortcutFunctionInfoList;

    public MHomeFragmentP() {
        mHomeModel = new MHomeModel();
        mShortcutFunctionInfoList = new ArrayList<MShortcutFunctionInfo>();
    }

    /**
     * 加载快捷功能
     */
    @Override
    public void loadShortcutFunction() {

        if (mShortcutFunctionInfoList != null) {
            for (int i = 0; i < 21; i++) {
                /**
                 * 创建测试数据
                 */
                MShortcutFunctionInfo mShortcutFunctionInfo = new MShortcutFunctionInfo();
                mShortcutFunctionInfo.setmShortcutIcon(R.drawable.caiwus);
                mShortcutFunctionInfo.setmShortcutTex("付账" + i);
                mShortcutFunctionInfoList.add(mShortcutFunctionInfo);
            }
        }


        if (mView != null) {
            mView.loadShortcutFunction(mShortcutFunctionInfoList);
        }
    }

    /**
     * 新增快捷功能
     *
     * @param mShortcutFunctionInfo
     */
    @Override
    public void addedShortcutFunction(MShortcutFunctionInfo mShortcutFunctionInfo) {
        if (mShortcutFunctionInfoList != null) {
            mShortcutFunctionInfoList.add(mShortcutFunctionInfo);
        }
        if (mView != null) {
            mView.addedShortcutFunction();
        }
    }

    /**
     * 延時一秒-顯示佈局
     */
    @Override
    public void delayedDisplay() {
        /**
         * 请求网络-获取销售信息
         */
        mRxManager.addSubscription(mHomeModel.getSalesDetails().
                subscribe(new MHttpSubscriber<MSalesDetailsInfo>(new MMSalesDetailsJson(), 0) {

                    @Override
                    public void onSuccess(Object o) {
                        /**回调刷新UI*/mView.loadSalesDetails((MSalesDetailsInfo) o);

                        /**
                         * 延時關閉等待頁
                         */
                        mRxManager.addSubscription(Observable.timer(500, TimeUnit.MILLISECONDS)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(new Func1<Long, Observable<?>>() {
                                    @Override
                                    public Observable<?> call(Long aLong) {
                                        /**隐藏等待框*/mView.hideLoading();
                                        return null;
                                    }
                                }).subscribe());
                    }

                    @Override
                    public void onFaild(String errorMsg) {

                    }
                }));
    }
}
