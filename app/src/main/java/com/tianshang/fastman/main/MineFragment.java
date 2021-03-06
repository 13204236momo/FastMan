package com.tianshang.fastman.main;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.tianshang.annotation.behaviour.LoginCheck;
import com.tianshang.arouter_api.ARouterManager;
import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.common.utils.MemoryLeakUtil;
import com.tianshang.fastman.R;
import com.tianshang.fastman.mine.MineFmContract;
import com.tianshang.fastman.mine.MineFmPresenter;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MineFragment extends BaseViewFm<MineFmPresenter, MineFmContract.View> {

    @BindView(R.id.iv_profile)
    ImageView ivProfile;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_nick)
    TextView tvNick;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.tv_more_pull)
    TextView tvMorePull;
    @BindView(R.id.tv_un_finish)
    TextView tvUnFinish;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_more_push)
    TextView tvMorePush;
    @BindView(R.id.tv_un_pay)
    TextView tvUnPay;
    @BindView(R.id.tv_un_claim)
    TextView tvUnClaim;
    @BindView(R.id.tv_no_receive)
    TextView tvNoReceive;
    @BindView(R.id.tv_no_appraise)
    TextView tvNoAppraise;
    @BindView(R.id.tv_cancel_push)
    TextView tvCancelPush;

    private Unbinder unbinder;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);

    }

    @OnClick({R.id.iv_profile, R.id.iv_setting, R.id.tv_more_pull,R.id.tv_more_push,
            R.id.tv_un_finish, R.id.tv_finish, R.id.tv_cancel,
            R.id.tv_un_pay, R.id.tv_un_claim, R.id.tv_no_receive,
            R.id.tv_no_appraise, R.id.tv_cancel_push, R.id.iv_message})
    @LoginCheck
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_profile:
                ARouterManager.getInstance()
                        .build("/personal/PersonalInfoActivity")
                        .navigation(getContext());
                break;
            case R.id.iv_setting:
                ARouterManager.getInstance()
                        .build("/personal/SettingActivity")
                        .navigation(getContext());
                break;
            case R.id.iv_message:
                break;
            case R.id.tv_more_push:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .withInt("position",0)
                        .navigation(getContext());
                break;
            case R.id.tv_more_pull:
                ARouterManager.getInstance()
                        .build("/order/PullOrderManagerActivity")
                        .withInt("position",0)
                        .navigation(getContext());
                break;
            case R.id.tv_un_finish:
                ARouterManager.getInstance()
                        .build("/order/PullOrderManagerActivity")
                        .withInt("position",0)
                        .navigation(getContext());
                break;
            case R.id.tv_finish:
                ARouterManager.getInstance()
                        .build("/order/PullOrderManagerActivity")
                        .withInt("position",1)
                        .navigation(getContext());
                break;
            case R.id.tv_cancel:
                ARouterManager.getInstance()
                        .build("/order/PullOrderManagerActivity")
                        .withInt("position",2)
                        .navigation(getContext());
                break;
            case R.id.tv_un_pay:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .withInt("position",0)
                        .navigation(getContext());
                break;
            case R.id.tv_un_claim:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .withInt("position",1)
                        .navigation(getContext());
                break;
            case R.id.tv_no_receive:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .withInt("position",2)
                        .navigation(getContext());
                break;
            case R.id.tv_no_appraise:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .withInt("position",3)
                        .navigation(getContext());
                break;
            case R.id.tv_cancel_push:
                ARouterManager.getInstance()
                        .build("/order/PushOrderManagerActivity")
                        .withInt("position",4)
                        .navigation(getContext());
                break;
        }
    }

    @Override
    public MineFmContract.View getContract() {
        return null;
    }

    @Override
    public MineFmPresenter getPresenter() {
        return new MineFmPresenter();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        MemoryLeakUtil.fixInputMethodMemoryLeak(getContext());
        super.onDestroy();

    }
}
