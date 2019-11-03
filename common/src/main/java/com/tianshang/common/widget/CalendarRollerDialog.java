package com.tianshang.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianshang.common.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CalendarRollerDialog extends Dialog{

    RollerView rv_1;
    RollerView rv_2;
    RollerView rv_3;
    TextView tvConfirm;
    TextView tvCancel;

    //没有选择时，将会显示的日期，也可以根据系统获取当前时间
    private String years = "2018",months = "3", days = "13";

    public CalendarRollerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_calender_roller);

        initView();
        initEvent();
    }



    private void initView() {
        rv_1 = findViewById(R.id.rv_1);
        rv_2 = findViewById(R.id.rv_2);
        rv_3 = findViewById(R.id.rv_3);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvCancel = findViewById(R.id.tv_cancel);

        //定义滚动选择器的数据项（年月日的）
        ArrayList<String> gradeYear = new ArrayList<>();
        ArrayList<String> gradeMonth = new ArrayList<>();
        ArrayList<String> gradeDay = new ArrayList<>();

        //为数据项赋值
        int thisYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new java.util.Date()));
        for(int i=1980;i<=thisYear;i++) //从1980到今年
            gradeYear.add(i + "");
        for(int i=1;i<=12;i++)            // 1月到12月
            gradeMonth.add(i + "");
        for(int i=1;i<=31;i++)           // 1日到31日
            gradeDay.add(i + "");

        //为滚动选择器设置数据
        rv_1.setData(gradeYear);
        rv_2.setData(gradeMonth);
        rv_3.setData(gradeDay);
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
                years = data;
            }
        });
        rv_2.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String data,int position) {
                months = data;
            }
        });
        rv_3.setOnSelectListener(new RollerView.onSelectListener() {
            @Override
            public void onSelect(String data,int position) {
                days = data;
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onSelected(years+"-"+months+"-"+days);
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
