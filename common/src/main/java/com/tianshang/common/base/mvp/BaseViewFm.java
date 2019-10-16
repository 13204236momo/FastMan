package com.tianshang.common.base.mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.tianshang.common.base.BaseFragment;

//view层基类
public abstract class BaseViewFm<P extends BasePresenterFm,CONTRACT> extends BaseFragment {

    protected P p;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        p = getPresenter();
        p.bindView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //让P层做什么需求
    public abstract CONTRACT getContract();

    //从子类中获取具体的契约
    public abstract P getPresenter();

    //如果P层出现了异常，需要告知view层
    public void error(Exception e){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        p.unBindView();
    }
}
