package com.tianshang.fastman.personal;


import android.os.Bundle;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.base.BaseActivity;
@ARouter(path = "/personal/EditPhoneNumActivity")
public class EditPhoneNumActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_edit_phone);
        setTitle("修改手机号");
    }

    
}
