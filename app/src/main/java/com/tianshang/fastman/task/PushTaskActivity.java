package com.tianshang.fastman.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tianshang.common.base.mvp.BaseView;
import com.tianshang.common.entity.app.ChooseEtity;
import com.tianshang.common.widget.SelectDialog;
import com.tianshang.fastman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PushTaskActivity extends BaseView<PushTaskPresenter, PushTaskContract.View> {

    @BindView(R.id.ib_left)
    ImageView ivLeft;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_last_time)
    TextView tvLasTime;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;
    @BindView(R.id.tv_task_content)
    TextView tvTaskContent;
    @BindView(R.id.tv_pack_no)
    TextView tvPackNo;
    @BindView(R.id.tv_pack_weight)
    TextView tvPackWeight;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_toast)
    TextView tvToast;
    @BindView(R.id.tv_to_pay)
    TextView tvToPay;

    List<ChooseEtity> timeList = new ArrayList<>();
    List<ChooseEtity> payWay = new ArrayList<>();
    List<ChooseEtity> weightList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_task);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        //时间选项
        ChooseEtity entity1 = new ChooseEtity("当天送达(默认)", null, true);
        timeList.add(entity1);
        for (int i = 0; i < 5; i++) {
            ChooseEtity entity = new ChooseEtity(i + ":00:00", null, false);
            timeList.add(entity);
        }

        //支付选项
        ChooseEtity aliPay = new ChooseEtity("支付宝", null, true);
        ChooseEtity wxPay = new ChooseEtity("微信", null, false);
        payWay.add(aliPay);
        payWay.add(wxPay);

        //重量选项
        ChooseEtity item = new ChooseEtity("小于2 Kg", null, true);
        ChooseEtity item1 = new ChooseEtity("3~5 Kg", null, false);
        ChooseEtity item2 = new ChooseEtity("6~15 Kg", null, false);
        ChooseEtity item3 = new ChooseEtity("16~50 Kg", null, false);
        weightList.add(item);
        weightList.add(item1);
        weightList.add(item2);
        weightList.add(item3);
    }

    @OnClick({R.id.ib_left, R.id.tv_address, R.id.tv_last_time, R.id.tv_pay_way,
            R.id.tv_task_content, R.id.tv_pack_no, R.id.tv_pack_weight,
            R.id.tv_money, R.id.tv_toast, R.id.tv_to_pay})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_left:
                finish();
                break;
            case R.id.tv_address:
                break;
            case R.id.tv_last_time:
                SelectDialog dialog = new SelectDialog(this, timeList);
                dialog.setTitle("选择送达时间");
                dialog.setTitleBackgroundColor(getResources().getColor(R.color.gray_line));
                dialog.show();
                dialog.setOnSelectedListener(new SelectDialog.OnSelectedListener() {
                    @Override
                    public void onSelected(int position) {
                        tvLasTime.setText(timeList.get(position).getContent());
                    }
                });
                break;
            case R.id.tv_pay_way:
                SelectDialog dialogPay = new SelectDialog(this, payWay);
                dialogPay.setTitle("选择支付方式");
                dialogPay.setTitleBackgroundColor(getResources().getColor(R.color.gray_line));
                dialogPay.show();
                dialogPay.setOnSelectedListener(new SelectDialog.OnSelectedListener() {
                    @Override
                    public void onSelected(int position) {
                        tvPayWay.setText(payWay.get(position).getContent());
                    }
                });
                break;
            case R.id.tv_task_content:
                Intent intent = new Intent(this,EditMessageActivity.class);
                intent.putExtra("tag","task_content");
                startActivityForResult(intent,1001);
                break;
            case R.id.tv_pack_no:
                Intent intent1 = new Intent(this,EditMessageActivity.class);
                intent1.putExtra("tag","task_no");
                startActivityForResult(intent1,1002);
                break;
            case R.id.tv_pack_weight:
                SelectDialog dialogWeight = new SelectDialog(this, weightList);
                dialogWeight.setTitle("选择重量范围");
                dialogWeight.setTitleBackgroundColor(getResources().getColor(R.color.gray_line));
                dialogWeight.show();
                dialogWeight.setOnSelectedListener(new SelectDialog.OnSelectedListener() {
                    @Override
                    public void onSelected(int position) {
                        tvPackWeight.setText(weightList.get(position).getContent());
                    }
                });
                break;
            case R.id.tv_money:
                break;
            case R.id.tv_toast:
                break;
            case R.id.tv_to_pay:
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 1001:
                    tvTaskContent.setText(data.getStringExtra("msg"));
                    break;
                case 1002:
                    tvPackNo.setText(data.getStringExtra("msg"));
                    break;
            }
        }
    }
}
