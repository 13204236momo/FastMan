package com.tianshang.fastman.personal;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.entity.personal.PersonalAddressEntity;
import com.tianshang.common.widget.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ARouter(path = "/personal/EditAddressActivity")
public class EditAddressActivity extends BaseActivity {

    @BindView(R2.id.et_name)
    EditText etName;
    @BindView(R2.id.iv_name)
    ImageView ivName;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.iv_phone)
    ImageView ivPhone;
    @BindView(R2.id.et_address_city)
    EditText etAddressCity;
    @BindView(R2.id.iv_address_city)
    ImageView ivAddressCity;
    @BindView(R2.id.et_address_des)
    EditText etAddressDes;
    @BindView(R2.id.iv_address_des)
    ImageView ivAddressDes;
    @BindView(R2.id.switch_button)
    SwitchButton switchButton;
    @BindView(R2.id.tv_save)
    TextView tvSave;

    private PersonalAddressEntity entity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);


        initView();
        initEvent();
    }

    private void initView() {
        entity = getIntent().getParcelableExtra("address");
        if (entity !=null){
            setTitle("编辑收货地址");
            etName.setText(entity.getName());
            etPhone.setText(entity.getPhone());
            etAddressCity.setText(entity.getAddressCity());
            etAddressDes.setText(entity.getAddressDes());
            switchButton.setChecked(entity.isDefault());
        }else {
            setTitle("新建收货地址");
        }
    }

    private void initEvent() {
        setVisibility(etName, ivName);
        setVisibility(etPhone, ivPhone);
        setVisibility(etAddressCity, ivAddressCity);
        setVisibility(etAddressDes, ivAddressDes);
    }


    @OnClick({ R2.id.iv_name, R2.id.iv_phone, R2.id.iv_address_city, R2.id.iv_address_des, R2.id.tv_save})
    void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_name) {
            clearText(etName, ivName);
        } else if (id == R.id.iv_phone) {
            clearText(etPhone, ivPhone);
        } else if (id == R.id.iv_address_city) {
            clearText(etAddressCity, ivAddressCity);
        } else if (id == R.id.iv_address_des) {
            clearText(etAddressDes, ivAddressDes);
        } else if (id == R.id.tv_save) {

        }
    }

    private void setVisibility(EditText editText, ImageView imageView) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null) {
                    if (editable.length() > 0) {
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        imageView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    private void clearText(EditText editText, ImageView imageView) {
        editText.setText("");
        imageView.setVisibility(View.INVISIBLE);
    }


}
