package ch.erp.management.mvp.model.http;

/**
 * 公共的请求回调监听器
 */

public interface MHttpCallBack<T> {

    /*成功时回调-返回请求数据*/void onSuccess(T t);

    /*失败时回调-返回错误信息*/void onFaild(String errorMsg);
}
