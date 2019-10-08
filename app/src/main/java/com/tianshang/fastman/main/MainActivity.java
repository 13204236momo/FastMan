package com.tianshang.fastman.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.tianshang.common.Retrofit.result.CommonListResult;
import com.tianshang.common.Retrofit.RetrofitRequest;
import com.tianshang.common.Retrofit.RetrofitHelper;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.fastman.R;
import com.tianshang.common.entity.app.School;
import com.tianshang.fastman.mine.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.tab1)
    TextView tvTab1;
    @BindView(R.id.tab2)
    TextView tvTab2;
    @BindView(R.id.tab3)
    TextView tvTab3;


    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        mFragmentList.add(new TaskFragment());
        mFragmentList.add(new TaskFragment());
        mFragmentList.add(new TaskFragment());

        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragmentList));

        tvTab3.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        tvTab2.setOnClickListener(v -> http());
    }

    private void http() {
       RetrofitHelper.getInstance()
                .create(RetrofitRequest.class)
                .getCall()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonListResult<School>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CommonListResult<School> result) {
                        Log.v("zyl","result:"+ result.getMsg());
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.v("zyl", "请求失败:"+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
