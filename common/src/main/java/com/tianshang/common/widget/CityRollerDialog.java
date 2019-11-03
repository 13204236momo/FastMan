package com.tianshang.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianshang.common.R;
import com.tianshang.common.entity.personal.AddressEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CityRollerDialog extends Dialog{

    RollerView rv_1;
    RollerView rv_2;
    TextView tvConfirm;
    TextView tvCancel;

    //没有选择时，将会显示的日期，也可以根据系统获取当前时间
    private String province = "银河系",city = "地球";
    private List<AddressEntity> entity;
    public CityRollerDialog(@NonNull Context context, List<AddressEntity> entity) {
        super(context);
        this.entity = entity;
    }

    public CityRollerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_city_roller);

        initView();
        initEvent();
    }



    private void initView() {
        rv_1 = findViewById(R.id.rv_1);
        rv_2 = findViewById(R.id.rv_2);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvCancel = findViewById(R.id.tv_cancel);

        //为滚动选择器设置数据
        rv_1.setData(entity.get(1).getCity().get(0).getCounty());
        rv_2.setData(entity.get(0).getCity().get(0).getCounty());
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(false);
    }


    private void initEvent() {
        //滚动选择事件
        rv_1.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String data,int position) {
                province = data;
            }
        });
        rv_2.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String data,int position) {
                city = data;
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onSelected(province+"-"+city);
                }
                dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    private OnSelectedListener listener;
    public interface OnSelectedListener{
        void onSelected(String content);
    }

    public void setOnSelectedListener(OnSelectedListener listener){
        if (listener!=null){
            this.listener = listener;
        }

    }


}
