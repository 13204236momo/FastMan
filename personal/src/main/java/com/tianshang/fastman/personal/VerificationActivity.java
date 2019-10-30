package com.tianshang.fastman.personal;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.widget.VerificationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerificationActivity extends BaseActivity {

    @BindView(R2.id.iv_back)
    ImageView ivBack;
    @BindView(R2.id.tv_phone)
    TextView tvPhone;
    @BindView(R2.id.vv)
    VerificationView verificationView;
    @BindView(R2.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R2.id.tv_sms)
    TextView tvSms;
    @BindView(R2.id.tv_speech)
    TextView tvSpeech;
    @BindView(R2.id.tv_retry)
    TextView tvRetry;
    @BindView(R2.id.line)
    View line;
    @BindView(R2.id.line1)
    View line1;
    @BindView(R2.id.line2)
    View line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.iv_back, R2.id.tv_confirm, R2.id.tv_sms, R2.id.tv_speech, R2.id.tv_retry})
    void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_confirm) {

        } else if (id == R.id.tv_sms) {
            getVerificationCode(0);
        } else if (id == R.id.tv_speech) {
            getVerificationCode(1);
        } else if (id == R.id.tv_retry) {

        }
    }

    private void getVerificationCode(int type) {
        tvSms.setVisibility(View.GONE);
        tvSpeech.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        line1.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        tvRetry.setVisibility(View.VISIBLE);

        if (type == 0) {
            //TODO 获取短信验证码
        } else if (type == 1) {
            //TODO 获取语音验证码
        }
    }
}
