package com.tianshang.fastman.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.utils.PreferencesUtil;

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
    @BindView(R2.id.tv_quite)
    TextView tvQuite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setTitle("设置");

        initView();
    }

    private void initView() {
        if (PreferencesUtil.getInstance().isLogin()){
            tvQuite.setVisibility(View.VISIBLE);
        }else {
            tvQuite.setVisibility(View.GONE);
        }
    }

    @OnClick({R2.id.tv_personal,R2.id.tv_account,R2.id.tv_address,R2.id.tv_last,R2.id.tv_about,R2.id.tv_quite})
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

        }else if (id==R.id.tv_account){
            ARouterManager.getInstance()
                    .build("/personal/AccountActivity")
                    .navigation(this);
        }else if (id==R.id.tv_quite){
            //TODO 清除用户信息缓存
            PreferencesUtil.getInstance().setLogin(false);
            ARouterManager.getInstance()
                    .build("/app/MainActivity")
                    .withInt("tab",3)
                    .navigation(SettingActivity.this);

        }
    }
}
