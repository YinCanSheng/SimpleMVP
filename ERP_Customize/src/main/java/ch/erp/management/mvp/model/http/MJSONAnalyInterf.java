package ch.erp.management.mvp.model.http;

import java.util.List;

/**
 *JSON-解析
 */

public interface MJSONAnalyInterf<T> {

    /*返回集合数据*/List<T> handleArrayJSON(String mJson);

    /*返回对象数据*/T handleObjectJSON(String mJson);
}
