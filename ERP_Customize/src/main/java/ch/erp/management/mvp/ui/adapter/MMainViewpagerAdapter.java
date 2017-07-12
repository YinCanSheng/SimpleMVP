package ch.erp.management.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 主页中部ViewPager
 *
 * @author ChenHong
 */
public class MMainViewpagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    /**
     * 构造
     */
    public MMainViewpagerAdapter(FragmentManager fm,
                                 List<Fragment> mFragmentList) {
        super(fm);
        /**
         * 从外界传入数据（Fragment）
         */
        this.mFragmentList = mFragmentList;

    }

    /**
     * 返回一个FFragment
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * 数据量
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}
