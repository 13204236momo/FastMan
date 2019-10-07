package com.tianshang.fastman.mine;

import com.tianshang.common.base.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel, LoginContract.Presenter> {


    @Override
    public LoginContract.Presenter getContract() {
        return new LoginContract.Presenter<UserInfo>() {
            @Override
            public void requestLogin(String name, String pwd) {
                try {
                    m.getContract().executeLogin(name, pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void responseResult(UserInfo userInfo) {
                getView().getContract().handlerResult(userInfo);
            }
        };
    }

    @Override
    public LoginModel getModel() {
        return new LoginModel(this);
    }
}
