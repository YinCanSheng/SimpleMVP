package ch.erp.management.mvp.model.http;

/**
 * Created by ChenHong on 2016/12/1.
 * API
 */

public class MApi {


    /*开发模式*/ public static String MODEL = "TEST";
    /*默认服务器*/ public static String API_BASE_URL = "";

    /**
     * 服务器地址 （环境配置：TEST测试模式, FORMAL开发模式）
     */
    static {
        /*测试服*/
        if (MODEL.equals("TEST")) {
            API_BASE_URL = "";
        }
        /*正式服*/
        else if (MODEL.equals("FORMAL")) {
            API_BASE_URL = "";
        }
    }


}
