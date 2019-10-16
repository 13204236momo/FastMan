package com.tianshang.common.base.mvp;

public abstract class BaseModelFm<P extends BasePresenterFm,CONTRACT> {
    public P p;

    public BaseModelFm(P p) {
        this.p = p;
    }

    public abstract CONTRACT getContract();
}
