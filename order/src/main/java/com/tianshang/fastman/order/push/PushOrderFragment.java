package com.tianshang.fastman.order.push;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.fastman.order.R;
import com.tianshang.fastman.order.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PushOrderFragment extends BaseViewFm<PushOrderFmPresenter, PushOrderFmContract.View> {

    @BindView(R2.id.rv_order)
    RecyclerView rvOrder;
    /**
     * 1:待付款2：待认领3：待收件4：待评价5：已取消
     */
    private int type;

    public PushOrderFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_pull_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }


    @Override
    public PushOrderFmContract.View getContract() {
        return null;
    }

    @Override
    public PushOrderFmPresenter getPresenter() {
        return new PushOrderFmPresenter();
    }
}
