package com.tianshang.fastman.personal;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.annotation.behaviour.PermissionCheck;
import com.tianshang.common.base.BaseCaptureActivity;
import com.tianshang.common.entity.personal.AddressEntity;
import com.tianshang.common.utils.Contracts;
import com.tianshang.common.widget.CityRollerDialog;
import com.tianshang.common.widget.PictureDialog;
import com.tianshang.common.widget.CalendarRollerDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ARouter(path = "/personal/PersonalInfoActivity")
public class PersonalInfoActivity extends BaseCaptureActivity {

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

        } else if (id == R.id.tv_birthday) {
            CalendarRollerDialog dialog = new CalendarRollerDialog(this);
            dialog.setOnSelectedListener(new CalendarRollerDialog.OnSelectedListener() {
                @Override
                public void onSelected(String content) {
                    tvBirthday.setText(content);
                }
            });
            dialog.show();
        } else if (id == R.id.tv_address) {
            Type listType = new TypeToken<ArrayList<AddressEntity>>(){}.getType();
            List<AddressEntity> o = new Gson().fromJson(Contracts.address, listType);
            CityRollerDialog dialog = new CityRollerDialog(this,o);
            dialog.setOnSelectedListener(new CityRollerDialog.OnSelectedListener() {
                @Override
                public void onSelected(String content) {
                    tvAddress.setText(content);
                }
            });
            dialog.show();
        }
    }



    @PermissionCheck({Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    private void getProfile() {

        PictureDialog dialog = new PictureDialog(this);
        dialog.setOnClickListener(new PictureDialog.OnClickListener() {
            @Override
            public void onClickTop() {
                takePhoto();
            }

            @Override
            public void onClickBottom() {
                pickPhoto();
            }
        });
        dialog.show();
    }

    @Override
    protected void onPhotoTook(String photoPath) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.icon_default_profile); //设置加载未完成时的占位图
        options.error(R.drawable.icon_default_profile); //设置加载异常时的占位图
        Glide.with(this)
                .load(photoPath)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(ivProfile);
    }
}
