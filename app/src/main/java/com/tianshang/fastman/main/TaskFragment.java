package com.tianshang.fastman.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.common.base.BaseFragment;
import com.tianshang.common.entity.app.TaskListEntity;
import com.tianshang.common.widget.SwipeItemLayout;
import com.tianshang.fastman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskFragment extends BaseFragment {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;

    private TextView tvTaskNum;
    private TextView tvAddress;

    TaskAdapter taskAdapter;

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        List<TaskListEntity> list = new ArrayList<>();

        list.add(new TaskListEntity("到8栋天天快递取件",R.mipmap.img1));
        list.add(new TaskListEntity("到23栋中通快递取件",R.mipmap.img2));
        list.add(new TaskListEntity("到19栋申通快递取件",R.mipmap.img3));
        list.add(new TaskListEntity("到5栋EMS快递取件",R.mipmap.img4));
        list.add(new TaskListEntity("到12栋圆通快递取件",R.mipmap.img5));
        list.add(new TaskListEntity("到三食堂买大份黄焖鸡",R.mipmap.img6));
        list.add(new TaskListEntity("到8栋天天快递取件",R.mipmap.img1));
        list.add(new TaskListEntity("到23栋中通快递取件",R.mipmap.img2));
        list.add(new TaskListEntity("到19栋申通快递取件",R.mipmap.img3));
        list.add(new TaskListEntity("到5栋EMS快递取件",R.mipmap.img4));
        list.add(new TaskListEntity("到12栋圆通快递取件",R.mipmap.img5));
        list.add(new TaskListEntity("到三食堂买大份黄焖鸡",R.mipmap.img6));
        list.add(new TaskListEntity("到8栋天天快递取件",R.mipmap.img1));
        list.add(new TaskListEntity("到23栋中通快递取件",R.mipmap.img2));
        list.add(new TaskListEntity("到19栋申通快递取件",R.mipmap.img3));
        list.add(new TaskListEntity("到5栋EMS快递取件",R.mipmap.img4));
        list.add(new TaskListEntity("到12栋圆通快递取件",R.mipmap.img5));
        list.add(new TaskListEntity("到三食堂买大份黄焖鸡",R.mipmap.img6));

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvTask.setLayoutManager(manager);
        rvTask.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        taskAdapter = new TaskAdapter(R.layout.item_task, list);
        taskAdapter.addHeaderView(getHeader());
        rvTask.setAdapter(taskAdapter);


    }

    private View getHeader() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.rv_task_head, null);
        tvTaskNum = header.findViewById(R.id.tv_task_num);
        tvAddress = header.findViewById(R.id.tv_address);
        return header;
    }
}
