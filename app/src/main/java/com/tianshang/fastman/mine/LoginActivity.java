package com.tianshang.fastman.mine;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tianshang.common.base.mvp.BaseView;
import com.tianshang.fastman.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseView<LoginPresenter, LoginContract.View> {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_login);
        ButterKnife.bind(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String pwd = etPwd.getText().toString();
                p.getContract().requestLogin(name, pwd);
            }
        });
    }

    @Override
    public LoginContract.View getContract() {
        return new LoginContract.View<UserInfo>() {
            @Override
            public void handlerResult(UserInfo userInfo) {
                if (userInfo != null) {
                    Toast.makeText(LoginActivity.this,userInfo.toString(),Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,"登录失败！",Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }
}
