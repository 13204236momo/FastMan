package com.tianshang.fastman.order.push;

import com.tianshang.common.base.mvp.BasePresenterFm;

public class PushOrderFmPresenter extends BasePresenterFm<PushOrderFragment,PushOrderFmContract.Presenter> {

    @Override
    public PushOrderFmContract.Presenter getContract() {
        return new PushOrderFmContract.Presenter(){

            @Override
            public void getOrderList(int type) {

            }
        };
    }
}
