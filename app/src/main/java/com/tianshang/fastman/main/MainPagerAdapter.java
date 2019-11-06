package com.tianshang.fastman.main;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public MainPagerAdapter(FragmentManager fm , List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

}
