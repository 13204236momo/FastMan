package com.tianshang.fastman.task;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianshang.common.base.mvp.BaseView;
import com.tianshang.fastman.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushTaskActivity extends BaseView<PushTaskPresenter, PushTaskContract.View> {

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_last_time)
    TextView tvLastime;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_task_content)
    TextView tvTaskContent;
    @BindView(R.id.tv_pack_no)
    TextView tvPackNo;
    @BindView(R.id.tv_pack_weight)
    TextView tvPackWeight;
    @BindView(R.id.tv_pack_volume)
    TextView tvPackVolume;
   @BindView(R.id.tv_money)
   TextView tvMoney;
   @BindView(R.id.tv_toast)
   TextView tvToast;
   @BindView(R.id.tv_to_pay)
   TextView tvToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_task);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back,R.id.tv_address,R.id.tv_last_time,R.id.tv_pay_way,R.id.tv_task_content,R.id.tv_pack_no,
            R.id.tv_pack_weight,R.id.tv_pack_volume,R.id.tv_money,R.id.tv_toast,R.id.tv_to_pay})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_address:
                break;

        }
    }

    @Override
    public PushTaskContract.View getContract() {
        return null;
    }

    @Override
    public PushTaskPresenter getPresenter() {
        return new PushTaskPresenter();
    }
}
