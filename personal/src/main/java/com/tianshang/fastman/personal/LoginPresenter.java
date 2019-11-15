package com.tianshang.fastman.personal;

import com.tianshang.common.base.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginActivity, LoginContract.Presenter> {


    @Override
    public LoginContract.Presenter getContract() {
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                if ("netease".equals(name) && "163".equals(pwd)) {
                    responseResult(new UserInfo("网易", "zhou"));
                } else {
                    responseResult(null);
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                getView().getContract().handlerResult(userInfo);
            }
        };
    }

}
