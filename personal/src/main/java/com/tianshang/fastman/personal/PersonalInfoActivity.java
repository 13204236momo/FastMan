package com.tianshang.fastman.personal;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.annotation.behaviour.PermissionCheck;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.utils.Helper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ARouter(path = "/personal/PersonalInfoActivity")
public class PersonalInfoActivity extends BaseActivity {

    @BindView(R2.id.iv_profile)
    ImageView ivProfile;
    @BindView(R2.id.et_nick)
    EditText etNick;
    @BindView(R2.id.tv_sex)
    TextView tvSex;
    @BindView(R2.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R2.id.tv_address)
    TextView tvAddress;
    @BindView(R2.id.et_introduce)
    EditText etIntroduce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        setTitle("个人资料");
    }

    @OnClick({R2.id.iv_profile, R2.id.tv_sex, R2.id.tv_birthday, R2.id.tv_address})
    void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_profile) {
            getProfile();
        } else if (id == R.id.iv_profile) {

        } else if (id == R.id.tv_sex) {

        } else if (id == R.id.tv_sex) {

        } else if (id == R.id.tv_address) {

        }
    }

    @PermissionCheck({Manifest.permission.CAMERA})
    private void getProfile() {
        Helper.showToast("我有相机权限");
    }
}
