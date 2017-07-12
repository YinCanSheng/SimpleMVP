package ch.erp.management.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 添加快捷方式-JavaBean
 */

public class MShortcutFunctionInfo implements Parcelable {
    /*快捷图标*/private int mShortcutIcon;
    /*快捷标签*/private String mShortcutTex;

    public int getmShortcutIcon() {
        return mShortcutIcon;
    }

    public void setmShortcutIcon(int mShortcutIcon) {
        this.mShortcutIcon = mShortcutIcon;
    }

    public String getmShortcutTex() {
        return mShortcutTex;
    }

    public void setmShortcutTex(String mShortcutTex) {
        this.mShortcutTex = mShortcutTex;
    }

    public MShortcutFunctionInfo(){}

    protected MShortcutFunctionInfo(Parcel in) {
        mShortcutIcon = in.readInt();
        mShortcutTex = in.readString();
    }

    public static final Creator<MShortcutFunctionInfo> CREATOR = new Creator<MShortcutFunctionInfo>() {
        @Override
        public MShortcutFunctionInfo createFromParcel(Parcel in) {
            return new MShortcutFunctionInfo(in);
        }

        @Override
        public MShortcutFunctionInfo[] newArray(int size) {
            return new MShortcutFunctionInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mShortcutIcon);
        parcel.writeString(mShortcutTex);
    }
}
