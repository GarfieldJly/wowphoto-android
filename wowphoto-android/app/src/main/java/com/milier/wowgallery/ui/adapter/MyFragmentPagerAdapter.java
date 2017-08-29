package com.milier.wowgallery.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jly on 2016/12/19.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    /**
     * 要显示的Fragment列表
     */
    private List<Fragment> fragments;
    private List<Integer> list_Title;
    private FragmentManager fm;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<Integer> list_Title, Context context) {
        super(fm);
        this.fm = fm;
        this.fragments = fragments;
        this.list_Title = list_Title;
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return mContext.getResources().getString(list_Title.get(position));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragments.get(position);
        fm.beginTransaction().hide(fragment).commit();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fm.beginTransaction().show(fragment).commit();
        return fragment;
    }
}
