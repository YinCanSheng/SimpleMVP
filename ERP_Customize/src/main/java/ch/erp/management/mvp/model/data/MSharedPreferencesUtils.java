package ch.erp.management.mvp.model.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcelable;

import java.util.List;
import java.util.Set;

import ch.erp.management.mvp.utils.General.MConstStr;

/**
 * 共享首选项操作类
 * <p>
 * 存取数据到本地文件
 *
 * @author ChenHong
 */
public class MSharedPreferencesUtils {
    /* 自己 */private static MSharedPreferencesUtils mSharedPreferencesUtils;
    /* 共享首选项 */private SharedPreferences mSharedPreferences;

    /**
     * 自己给定文件名
     * <p>
     * mContext :上下文
     * <p>
     * mFileName：文件名
     */
    private MSharedPreferencesUtils(Context mContext, String mFileName) {
        /** 初始化共享首选项/为私有模式/只有当前应用可访问/其他组件皆可访问 */
        mSharedPreferences = mContext.getSharedPreferences(mFileName,
                Context.MODE_PRIVATE);
    }

    /**
     * 默认文件名
     */
    private MSharedPreferencesUtils(Context mContext) {
        mSharedPreferences = mContext.getSharedPreferences(
                MConstStr.M_ERP_FINLENAME, Context.MODE_PRIVATE);
    }

    /**
     * 单例获取操作对象
     */
    public static MSharedPreferencesUtils getMSharedPreferencesUtils(Context mContext) {
        if (mSharedPreferencesUtils == null) {
            synchronized (MSharedPreferencesUtils.class) {
                if (mSharedPreferencesUtils == null) {
                    mSharedPreferencesUtils = new MSharedPreferencesUtils(
                            mContext);
                } else {
                    return mSharedPreferencesUtils;
                }
            }
        }
        return mSharedPreferencesUtils;
    }

    /**
     * 移除某个key对应的值
     */
    public void mRemove(String key) {
        Editor mEditor = mSharedPreferences.edit();
        if (mEditor != null) {
            mEditor.remove(key);
            mEditor.apply();
        }
    }

    /***
     * ==========================写入数据/重载================================
     */
    /**
     * 写入字符串数据
     *
     */
    public void mWrite(String mMarkStr, String mValueStr) {
        /* 得到编辑对象 */
        Editor mEditor = mSharedPreferences.edit();
        if (mEditor != null) {
			/* 绑定数据 */
            mEditor.putString(mMarkStr, mValueStr);
			/* 提交 */
            mEditor.apply();
        }
    }

    /**
     * 写入整型数据
     */

    public void mWrite(String mMarkStr, int mValueInt) {
        Editor mEditor = mSharedPreferences.edit();
        if (mEditor != null) {
            mEditor.putInt(mMarkStr, mValueInt);
            mEditor.apply();
        }
    }

    /**
     * 写入单精度浮点型数据
     *
     */
    public void mWrite(String mMarkStr, float value) {
        Editor mEditor = mSharedPreferences.edit();
        if (mEditor != null) {
            mEditor.putFloat(mMarkStr, value);
            mEditor.apply();
        }
    }

    /**
     * 写入长整型数据
     */
    public void mWrite(String mMarkStr, long mValueLong) {
        Editor mEditor = mSharedPreferences.edit();
        if (mEditor != null) {
            mEditor.putLong(mMarkStr, mValueLong);
            mEditor.apply();
        }
    }

    /**
     * 写入字符串集合数据
     */
    public void mWrite(String mMarkStr, Set<String> mValueSet) {
        Editor mEditor = mSharedPreferences.edit();
        if (mEditor != null) {
            mEditor.putStringSet(mMarkStr, mValueSet);
            mEditor.apply();
        }
    }

    /**
     * =====================获取数据/重载=====================================
     */

    /**
     * 默认为空
     */
    public String mObtain(String key) {
        return mSharedPreferences.getString(key, "");
    }

    /**
     * 得到字符串数据
     * 给定一个默认值
     */
    public String mObtain(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    /**
     * 得到布尔型数据
     */
    public boolean mObtain(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    /**
     * 得到整型数据
     *
     */
    public int mObtain(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    /**
     * 得到浮点型数据
     */
    public float mObtain(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    /**
     * 得到长整型数据
     *
     */
    public long mObtain(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    /**
     * 得到字符串集合数据
     */
    public Set<String> mObtain(String key, Set<String> defValue) {
        return mSharedPreferences.getStringSet(key, defValue);
    }
}
