package ch.erp.management.mvp.model;

import ch.erp.management.mvp.contract.MHomeContract;
import rx.Observable;

/**
 * 主页-Model
 */

public class MHomeModel extends MHomeContract.MIHomeModel {
    /**
     * 获取销售状况
     */
    @Override
    public Observable<String> getSalesDetails() {
        return assign_thread(Observable.just("123"));
    }
}
