package com.tianshang.fastman.personal.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianshang.common.entity.personal.PersonalAddressEntity;
import com.tianshang.fastman.personal.R;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<PersonalAddressEntity, BaseViewHolder> {

    public AddressAdapter(@Nullable List<PersonalAddressEntity> data) {
        super(R.layout.item_address, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PersonalAddressEntity item) {

    }
}
