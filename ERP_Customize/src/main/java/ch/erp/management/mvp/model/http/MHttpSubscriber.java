package ch.erp.management.mvp.model.http;

import android.util.Log;

import rx.Subscriber;

/**
 * 订阅者-解析Json并且回调
 */
public abstract class MHttpSubscriber<T> extends Subscriber<String> implements MHttpCallBack {
    /*自定义Json解析工具**/private MJSONAnalyInterf mJsonAnalyInterf = null;
    /*解析类型-0->集合or1->对象**/private int mTypes;

    public MHttpSubscriber(MJSONAnalyInterf<T> mJsonAnalyInterf, int mTypes) {
        this.mJsonAnalyInterf = mJsonAnalyInterf;
        this.mTypes = mTypes;
    }

    /**
     * 网络访问成功
     *
     * @param mJson
     */
    @Override
    public void onNext(String mJson) {

        Log.e("JSON", "onNext: "+mJson);
        /**
         * 判断请求结果==根据接口后期完善
         */

        /**
         * 親求成功
         */
        if (mTypes == 0) {
            /**回调对象数据*/onSuccess(mJsonAnalyInterf.handleObjectJSON(mJson));
        } else if (mTypes == 1) {
            /**回调集合数据*/onSuccess(mJsonAnalyInterf.handleArrayJSON(mJson));
        }

        /**
         * 請求失敗
         */

    }

    /**
     * 网络访问结束-唯一调用一次
     */
    @Override
    public void onCompleted() {

        mJsonAnalyInterf = null;
    }

    /**
     * 网络访问失败-唯一调用一次
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {

        mJsonAnalyInterf = null;
    }


}
