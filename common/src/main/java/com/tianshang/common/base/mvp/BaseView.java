package com.tianshang.common.base.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tianshang.common.base.BaseActivity;

//view层基类
public abstract class BaseView<P extends BasePresenter,CONTRACT> extends BaseActivity {

    protected P p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = getPresenter();
        p.bindView(this);
    }

    //让P层做什么需求
    public abstract CONTRACT getContract();

    //从子类中获取具体的契约
    public abstract P getPresenter();

    //如果P层出现了异常，需要告知view层
    public void error(Exception e){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.unBindView();
    }
}
