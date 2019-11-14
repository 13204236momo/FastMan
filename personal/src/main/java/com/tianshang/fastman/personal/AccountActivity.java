package com.tianshang.fastman.personal;


import android.os.Bundle;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.common.base.BaseActivity;
@ARouter(path = "/personal/AccountActivity")
public class AccountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_account);
        setTitle("账户管理");
    }
}
