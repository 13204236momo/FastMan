package com.tianshang.fastman.main;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.retrofit.RetrofitHelper;
import com.tianshang.common.retrofit.RetrofitRequest;
import com.tianshang.common.retrofit.result.CommonListResult;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.entity.app.School;
import com.tianshang.common.widget.NoScrollViewPager;
import com.tianshang.fastman.R;
import com.tianshang.fastman.task.AddRecordDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    NoScrollViewPager vpMain;
    @BindView(R.id.tab1)
    TextView tvTab1;
    @BindView(R.id.tab2)
    TextView tvTab2;
    @BindView(R.id.tab3)
    TextView tvTab3;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private int currentTab;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initView();
        initData();
    }

    private void initView() {
        setTitleBarVisible(false);
        mFragmentList.add(new TaskFragment());
        //mFragmentList.add(new TaskFragment());
        mFragmentList.add(new MineFragment());

        vpMain.setScrollable(false);
        vpMain.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragmentList));
        tvTab1.setTextColor(getResources().getColor(R.color.brown));
        tvTab1.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.brown));

//        tvTab2.setOnClickListener(v -> http());
    }

    private void initData() {
        currentTab = getIntent().getIntExtra("tab", 1);
        if (currentTab == 1) {
            selectTab1();
        } else if (currentTab == 3) {
            selectTab3();
        }
    }

    @OnClick({R.id.tab1, R.id.tab2, R.id.tab3})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab1:
                selectTab1();
                break;
            case R.id.tab2:
                tvTab1.setTextColor(getResources().getColor(R.color.black));
                tvTab1.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.black));
                tvTab2.setTextColor(getResources().getColor(R.color.brown));
                tvTab2.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.brown));
                tvTab3.setTextColor(getResources().getColor(R.color.black));
                tvTab3.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.black));
                addRecord();
                break;
            case R.id.tab3:
                selectTab3();
                break;
        }
    }


    private void selectTab1() {
        tvTab1.setTextColor(getResources().getColor(R.color.brown));
        tvTab1.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.brown));
        tvTab2.setTextColor(getResources().getColor(R.color.black));
        tvTab2.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.black));
        tvTab3.setTextColor(getResources().getColor(R.color.black));
        tvTab3.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.black));
        vpMain.setCurrentItem(0);
    }

    private void selectTab3() {
        tvTab1.setTextColor(getResources().getColor(R.color.black));
        tvTab1.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.black));
        tvTab2.setTextColor(getResources().getColor(R.color.black));
        tvTab2.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.black));
        tvTab3.setTextColor(getResources().getColor(R.color.brown));
        tvTab3.getCompoundDrawables()[1].setTint(getResources().getColor(R.color.brown));
        vpMain.setCurrentItem(1);
    }


    private void addRecord() {
        Dialog dialog = new AddRecordDialog(this);
        dialog.show();
        Log.d("zhou", System.currentTimeMillis() + "");
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
                        Log.v("zyl", "result:" + result.getMsg());
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.v("zyl", "请求失败:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
