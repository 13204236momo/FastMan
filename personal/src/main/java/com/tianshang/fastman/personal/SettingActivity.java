package com.tianshang.fastman.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@ARouter(path = "/personal/SettingActivity")
public class SettingActivity extends BaseActivity {

    @BindView(R2.id.tv_personal)
    TextView tvPersonal;
    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.tv_last)
    TextView tvLast;
    @BindView(R2.id.tv_about)
    TextView tvAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setTitle("设置");
    }

    @OnClick({R2.id.iv_base_left,R2.id.tv_personal,R2.id.tv_account,R2.id.tv_address,R2.id.tv_last,R2.id.tv_about})
    void onClick(View view){
        int id = view.getId();
        if (id==R.id.tv_personal){
            ARouterManager.getInstance()
                    .build("/personal/PersonalInfoActivity")
                    .navigation(this);
        }else if (id==R.id.tv_address){
            ARouterManager.getInstance()
                    .build("/personal/AddressManagerActivity")
                    .navigation(this);
        }else if (id==R.id.tv_last){

        }else if (id==R.id.tv_about){

        }else if (id==R.id.iv_base_left){
            finish();
        }else if (id==R.id.tv_account){
            ARouterManager.getInstance()
                    .build("/personal/AccountActivity")
                    .navigation(this);
        }
    }
}