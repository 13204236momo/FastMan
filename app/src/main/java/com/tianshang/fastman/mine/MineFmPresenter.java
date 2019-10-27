package com.tianshang.fastman.mine;

import com.tianshang.common.base.mvp.BasePresenterFm;
import com.tianshang.fastman.main.MineFragment;

public class MineFmPresenter extends BasePresenterFm<MineFragment, MineFmContract.Presenter> {



    @Override
    public MineFmContract.Presenter getContract() {
        return new MineFmContract.Presenter() {
            @Override
            public void requestAddress() {

            }

            @Override
            public void destroyLocation() {
            }

            @Override
            public void getTaskList() {

            }

        };
    }


}
