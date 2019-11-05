package com.tianshang.fastman.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.annotation.arouter.ARouter;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.entity.personal.PersonalAddressEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@ARouter(path = "/personal/AddressManagerActivity")
public class AddressManagerActivity extends BaseActivity {

    @BindView(R2.id.rv_address)
    RecyclerView rvAddress;
    @BindView(R2.id.tv_add_address)
    TextView tvAddAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_address_manager);
        ButterKnife.bind(this);
        setTitle("收货地址管理");
    }

    @OnClick({R2.id.iv_base_left,R2.id.tv_add_address})
    void onClick(View view) {
        int id = view.getId();

        if (id == R.id.iv_base_left) {
            finish();
        } else if (id == R.id.tv_add_address) {
            Bundle bundle = new Bundle();
            PersonalAddressEntity personalAddressEntity = new PersonalAddressEntity("周默晗","17611417293","北京-北京","北京航空航天大学4栋802",true);
            bundle.putParcelable("address",personalAddressEntity);
            ARouterManager.getInstance()
                    .build("/personal/EditAddressActivity")
                    .withBundle("addressMessage",bundle)
                    .navigation(this);
        }
    }
}
