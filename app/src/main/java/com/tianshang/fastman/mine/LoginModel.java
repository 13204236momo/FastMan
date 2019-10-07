package com.tianshang.fastman.mine;

import com.tianshang.common.base.mvp.BaseModel;

public class LoginModel extends BaseModel<LoginPresenter,LoginContract.Model> {

    public LoginModel(LoginPresenter loginPresenter) {
        super(loginPresenter);
    }

    @Override
    public LoginContract.Model getContract() {
        return new LoginContract.Model() {
            @Override
            public void executeLogin(String name, String pwd) throws Exception {
                if ("netease".equals(name) && "163".equals(pwd)){
                    p.getContract().responseResult(new UserInfo("网易","zhou"));
                }else {
                    p.getContract().responseResult(null);
                }
            }
        };
    }
}
