package ch.erp.management.mvp.utils.General;


import ch.erp.management.R;

/**
 * 整型常量实体类
 *
 * @author ChenHong
 */
public class MConstInt {


    /***/
    public static final int SOTIMEOUT = 10;
    /***/
    public static final int POOLSIZE = 3;
    /*时间单位都为：秒*/ public static final int TIMEOUT = 10;
    /*拍照状态*/public static final int REQUEST_CAMERA = 1;
    /*相册状态*/public static final int SELECT_FILE = 2;
    /*结果*/  public static final int PHOTORESOULT = 3;
    /*响应码标识*/ public static final int DTON__RESULTMARK = 1001;
    /*请求码标识*/public static final int NTOD_REQUESTMARH = 1002;
    /*请求失败*/public static final int ACCOUNT_PASSWORD_ABNORMAL = 0;
    /*网络异常*/public static final int NETWORK_ANOMALY = -2;
    /*服务器异常*/public static final int SERVER_ANOMALY = -1;

    /* 图片ID数组（底部-深色）*/public static int[] mMainImageMazarine = {
            R.drawable.shouyes,
            R.drawable.kucuns,
            R.drawable.baobiaos,
            R.drawable.caiwus};
    /* 图片ID数组（底部-浅色）*/public static int[] mMainImageBodyBlue = {
            R.drawable.shouyeq,
            R.drawable.kucunq,
            R.drawable.baobiaoq,
            R.drawable.caiwuq};

    /*项目-占位图*/public static int M_PLACEHOLDER = 0;

    /* Activity进入退出动画-右侧进入*/public static int[] mSwitchingAnim = {
            R.anim.translate_right_enter,
            R.anim.translate_left_out};
    /*Activity进入退出动画-下侧进入*/public static int[] mSwitchingAnimOfBT = {0, 1};

}
