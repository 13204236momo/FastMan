package com.tianshang.fastman.order.pull;

import com.tianshang.common.base.mvp.BasePresenterFm;
import com.tianshang.fastman.order.push.PushOrderFmContract;
import com.tianshang.fastman.order.push.PushOrderFragment;

public class PullOrderFmPresenter extends BasePresenterFm<PullOrderFragment,PullOrderFmContract.Presenter> {

    @Override
    public PullOrderFmContract.Presenter getContract() {
        return new PullOrderFmContract.Presenter(){

            @Override
            public void getOrderList(int type) {

            }
        };
    }
}
