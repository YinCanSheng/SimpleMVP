package ch.erp.management.mvp.utils.General;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import ch.erp.management.R;
import ch.erp.management.mvp.base.BaseActivity;

/**
 * Activity界面跳转
 *
 * @author ChenHong
 */
public class MActivityJumpUtils {
    /* Activity跳转工具类 */static MActivityJumpUtils activityJumpUtils;
    /* 跳转意图 */private Intent mIntent;

    /**
     * 私有构造
     */
    private MActivityJumpUtils() {
    }

    /**
     * 单例模式实例化对象
     */
    public static MActivityJumpUtils getActivityJumpUtils() {
        if (activityJumpUtils == null) {
            synchronized (MActivityJumpUtils.class) {
                if (activityJumpUtils == null) {
                    activityJumpUtils = new MActivityJumpUtils();
                } else {
                    return activityJumpUtils;
                }
            }
        }
        return activityJumpUtils;
    }

    /**
     * Activity隐式跳转
     */
    public void jumpHideActivity(Context mContext, String mStr) {
        mIntent = new Intent(mStr);
        mContext.startActivity(mIntent);
    }

    /**
     * Activity显示跳转
     */
    public void jumpSurfaceActivity(AppCompatActivity mActivity, Class<?> mClass) {
        /* 实例化意图 */
        mIntent = new Intent(mActivity, mClass);
        /* 执行意图 */
        mActivity.startActivity(mIntent);
        /* 开启一个activity进入动画/自左向右进入 */
        mActivity.overridePendingTransition(
                MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * Activity显示跳转/不带动画
     */
    public void jumpSurfaceActivityOfNoAni(AppCompatActivity mActivity, Class<?> mClass) {
        /* 实例化意图 */
        mIntent = new Intent(mActivity, mClass);
        /* 执行意图 */
        mActivity.startActivity(mIntent);

    }

    /**
     * Activity显示跳转/自定义动画
     */
    public void jumpSurfaceActivityOfcustomizeAni(Context mContext, Class<?> mClass, int enterAni, int outAni) {
        /* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
        /* 执行意图 */
        mContext.startActivity(mIntent);
          /*跳转动画*/
        ((BaseActivity) mContext).overridePendingTransition(enterAni,
                outAni);
    }

    /**
     * Activity显示跳转/淡入淡出
     */
    public void jumpSurfaceFadeActivity(Context mContext, Class<?> mClass) {
        /* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
		/* 执行意图 */
        mContext.startActivity(mIntent);
        /*跳转动画*/
        ((BaseActivity) mContext).overridePendingTransition(R.anim.alpha_fadein,
                R.anim.alpha_fadeout);
    }

    /**
     * Activity显示跳转/下侧进入
     */
    public void jumpSurfaceBottomToActivity(Context mContext, Class<?> mClass) {
		/* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
		/* 执行意图 */
        mContext.startActivity(mIntent);
		/* 开启一个activity进入动画/自左向右进入 */
        ((Activity) mContext).overridePendingTransition(
                R.anim.translate_bottom_enter, R.anim.translate_top_out);
    }

    /**
     * Activity显示跳转/右侧进入
     */
    public void jumpSurfaceRightToActivity(Context mContext, Class<?> mClass) {
		/* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
		/* 执行意图 */
        mContext.startActivity(mIntent);
    }


    /**
     * 携带整型数据跳转
     */
    public void jumpSurFaceCarryInt(Context mContext,
                                    Class<?> mClass, String mMark, int mData) {
		/* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
		/* 绑定数据 */
        mIntent.putExtra(mMark, mData);
		/* 执行意图 */
        mContext.startActivity(mIntent);
		/* 开启一个activity进入动画/自左向右进入 */
        ((Activity) mContext).overridePendingTransition(
                MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * 携带字符串型数据跳转
     */
    public void jumpSurFaceCarryStr(Context mContext, Class<?> mClass,
                                    String mMark, String mData) {

		/* 实例化意图 */
        mIntent = new Intent(mContext, mClass);

		/* 绑定数据 */
        mIntent.putExtra(mMark, mData);

		/* 执行意图 */
        mContext.startActivity(mIntent);

		/* 开启一个activity进入动画/自左向右进入 */
        ((Activity) mContext).overridePendingTransition(
                MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * 携带集合数据跳转
     */
    public void jumpSurFaceCarryList(Context mContext, Class<?> mClass,
                                     String mMark, ArrayList<Object> mArrayList) {
		/* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
		/* 绑定数据 */
        mIntent.putExtra(mMark, mArrayList);
		/* 执行意图 */
        mContext.startActivity(mIntent);
		/* 开启一个activity进入动画/自左向右进入 */
        ((Activity) mContext).overridePendingTransition(
                MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * 携带对象数据跳转
     */
    public void jumpSurFaceCarryObject(Context mContext, Class<?> mClass,
                                       String mMark, Serializable mSerializable) {
		/* 实例化意图 */
        mIntent = new Intent(mContext, mClass);
		/* 绑定数据 */
        mIntent.putExtra(mMark, mSerializable);
		/* 执行意图 */
        mContext.startActivity(mIntent);
		/* 开启一个activity进入动画/自左向右进入 */
        ((Activity) mContext).overridePendingTransition(
                MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }

    /**
     * Fragment显示跳转/可回传
     */
    public void jumpSurfaceActivityForResult(Fragment mActivity,
                                             Class<?> mClass, int mRequestCode) {
		/* 实例化意图 */
        mIntent = new Intent(mActivity.getContext(), mClass);
		/* 执行意图 */
        if (mActivity != null) {
            mActivity.startActivityForResult(mIntent, mRequestCode);
        }
		/* 开启一个activity进入动画/自左向右进入 */
        mActivity.getActivity().overridePendingTransition(
                MConstInt.mSwitchingAnim[0], MConstInt.mSwitchingAnim[1]);
    }
    /**
     * 相机-相册
     */

    /**
     * 打开相册
     *
     * @param mActivity
     * @param mRequestCode
     */
    public void jumpHidePhotographForResultActivi(Activity mActivity,
                                                  int mRequestCode) {
		/* 调用系统相册的意图 */
        Intent mPhotographIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mPhotographIntent.setType("image/*");
        if (mActivity != null) {
            mActivity.startActivityForResult(
                    Intent.createChooser(mPhotographIntent, "Select File"),
                    mRequestCode);
        }
    }


    /**
     * 打开相机
     *
     * @param mActivity
     * @param mRequestCode
     */
    public void jumpHideSelectPhotoForResultActivi(Activity mActivity,
                                                   int mRequestCode) {
		/* 调用相机的意图 */
        Intent mSelectPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		/* 根据File构造一个File/是文件与文件路径的抽象 */
        File company = new File(MPictureUtils.getAppDir(), "company.png");
		/* 指定一个路径 */
        mSelectPhotoIntent.putExtra("output", Uri.fromFile(company));
        if (mActivity != null) {
			/* 跳转 */
            mActivity.startActivityForResult(mSelectPhotoIntent, mRequestCode);
        }
    }

    /**
     * 跳转至自定义头像裁剪页
     */
    public void jumpActivityToClip(Activity mActivity, Class<?> mClass,
                                   String mFileId) {
		/* 初始化跳转意图 */
        mIntent = new Intent(mActivity, mClass);
		/* 绑定订单ID */
        mIntent.putExtra(MConstStr.M_PHOTOSTR_MARK, mFileId);
		/* 执行意图 */
        mActivity.startActivityForResult(mIntent, 3);
		/* 开启一个activity进入动画/自左向右进入 */
        mActivity.overridePendingTransition(MConstInt.mSwitchingAnimOfBT[0],
                MConstInt.mSwitchingAnimOfBT[1]);
    }
}
