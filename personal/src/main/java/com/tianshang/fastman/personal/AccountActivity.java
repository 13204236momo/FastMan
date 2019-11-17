package com.tianshang.fastman.personal;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ARouter(path = "/personal/AccountActivity")
public class AccountActivity extends BaseActivity {

    @BindView(R2.id.tv_phone_name)
    TextView tvPhoneName;
    @BindView(R2.id.tv_password_login)
    TextView tvPasswordLogin;
    @BindView(R2.id.tv_password_pay)
    TextView tvPasswordPay;
    @BindView(R2.id.tv_attestation)
    TextView tvAttestation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_account);
        setTitle("账户管理");
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.tv_phone_name,R2.id.tv_password_login,R2.id.tv_password_pay,
            R2.id.tv_attestation})
    void onClick(View view){
        int id = view.getId();
        if (id==R.id.tv_phone_name){
            ARouterManager.getInstance()
                    .build("/personal/EditPhoneNumActivity")
                    .navigation(this);
        }else if (id == R.id.tv_password_login){
            ARouterManager.getInstance()
                    .build("/personal/EditLoginPasswordActivity")
                    .navigation(this);
        }else if (id==R.id.tv_password_pay){

        }else if (id==R.id.tv_attestation){

        }
    }
}
