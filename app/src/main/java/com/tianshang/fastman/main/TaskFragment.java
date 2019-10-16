package com.tianshang.fastman.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.common.entity.app.TaskListEntity;
import com.tianshang.common.utils.Helper;
import com.tianshang.common.widget.SwipeItemLayout;
import com.tianshang.fastman.R;
import com.tianshang.fastman.task.TaskFmContract;
import com.tianshang.fastman.task.TaskFmPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskFragment extends BaseViewFm<TaskFmPresenter, TaskFmContract.View> {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;

    private TextView tvTaskNum;
    private TextView tvAddress;

    TaskAdapter taskAdapter;
    private List<TaskListEntity> list = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        p.getContract().requestAddress();


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


    @Override
    public TaskFmContract.View getContract() {
        return new TaskFmContract.View() {
            @Override
            public void resultAddress(String address) {
                if (address != null) {
                    Helper.showToast(address);
                    p.getContract().getTaskList(list);
                } else {
                    Helper.showToast("定位失败！");
                    p.getContract().getTaskList(list);
                }

            }

            @Override
            public void setTaskList(List<TaskListEntity> list) {
                taskAdapter.setNewData(list);
            }
        };
    }

    @Override
    public TaskFmPresenter getPresenter() {
        return new TaskFmPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        p.getContract().destroyLocation();
    }
}
