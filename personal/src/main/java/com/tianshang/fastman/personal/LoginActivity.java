package com.tianshang.fastman.personal;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.base.mvp.BaseView;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
@ARouter(path = "/personal/LoginActivity")
public class LoginActivity extends BaseView<LoginPresenter, LoginContract.View> {

    //@BindView(R.id.tv_login_type)
    TextView tvLoginType;
    //@BindView(R.id.tv_type)
    TextView tvType;
    //@BindView(R.id.et_account)
    EditText etAccount;
    //@BindView(R.id.et_password)
    EditText etPassword;
    //@BindView(R.id.tv_login)
    TextView tvLogin;
    //@BindView(R.id.tv_forget)
    TextView tvForget;
    //@BindView(R.id.tv_tag)
    TextView tvTag;
    //@BindView(R.id.tv_rule)
    TextView tvRule;
    //@BindView(R.id.tf_password)
    MaterialTextField tfPassword;

    private int loginType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_login);
        //ButterKnife.bind(this);

        //initView();
    }

    private void initView() {

        if (loginType == 0){
            tvLoginType.setText("密码登录");
            tvType.setText("免密码登录");
            tfPassword.setVisibility(View.GONE);
            tvLogin.setText("获取验证码");
            tvForget.setVisibility(View.GONE);
            tvTag.setVisibility(View.VISIBLE);
        }else if(loginType == 1){
            tvLoginType.setText("免密登录");
            tvType.setText("密码登录");
            tfPassword.setVisibility(View.VISIBLE);
            tvLogin.setText("登录");
            tvForget.setVisibility(View.VISIBLE);
            tvTag.setVisibility(View.GONE);
        }
    }


    //@OnClick({R.id.tv_login_type,R.id.tv_login,R.id.tv_forget})
//    void onClick(View view){
//        switch (view.getId()){
//            case R.id.tv_login_type:
//                if (loginType == 0){
//                    loginType = 1;
//                }else {
//                    loginType = 0;
//                }
//                initView();
//                break;
//            case R.id.tv_login:
//                break;
//            case R.id.tv_forget:
//                break;
//        }
//    }

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
