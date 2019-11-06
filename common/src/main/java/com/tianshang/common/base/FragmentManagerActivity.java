package com.tianshang.common.base;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tianshang.common.R;
import com.tianshang.common.entity.TabEntity;

import java.util.ArrayList;


public abstract class FragmentManagerActivity extends BaseActivity {

    public CommonTabLayout tlPull;
    public ViewPager vpPull;

    public ArrayList<String> mTitles;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_pull_order_manager);


        initView();
        initEvent();
    }

    private void initView() {
        tlPull = findViewById(R.id.tl_pull);
        vpPull = findViewById(R.id.vp_pull);

        setTitles();
        if (mTabEntities.size() == 0) {
            for (int i = 0; i < mTitles.size(); i++) {
                mTabEntities.add(new TabEntity(mTitles.get(i),0,0));
            }
        }
        tlPull.setTabData(mTabEntities);

    }

    private void initEvent() {
        vpPull.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tlPull.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        tlPull.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpPull.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    public abstract void setTitles();
}
