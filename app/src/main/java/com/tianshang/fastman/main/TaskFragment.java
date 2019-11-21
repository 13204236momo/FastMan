package com.tianshang.fastman.main;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.annotation.behaviour.PermissionCheck;
import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.common.entity.app.TaskListEntity;
import com.tianshang.common.utils.Helper;
import com.tianshang.common.utils.PermissionUtility;
import com.tianshang.common.widget.CommonDialog;
import com.tianshang.common.widget.SwipeItemLayout;
import com.tianshang.fastman.R;
import com.tianshang.fastman.task.TaskFmContract;
import com.tianshang.fastman.task.TaskFmPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;


public class TaskFragment extends BaseViewFm<TaskFmPresenter, TaskFmContract.View> {

    @BindView(R.id.rv_task)
    RecyclerView rvTask;

    private TextView tvTaskNum;
    private TextView tvAddress;

    TaskAdapter taskAdapter;
    private Unbinder unbinder;
    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);


        getPerMission();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvTask.setLayoutManager(manager);
        rvTask.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));

    }

    private View getHeader() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.rv_task_head, null);
        tvTaskNum = header.findViewById(R.id.tv_task_num);
        tvAddress = header.findViewById(R.id.tv_address);
        return header;
    }

    @PermissionCheck({Manifest.permission.ACCESS_COARSE_LOCATION})
    private void getPerMission() {
        p.getContract().requestAddress();
    }


    @Override
    public TaskFmContract.View getContract() {
        return new TaskFmContract.View() {
            @Override
            public void resultAddress(String address) {
                if (address != null) {
                    CommonDialog.Builder builder = new CommonDialog.Builder(getContext());
                    builder.setTitle("提示")
                            .setContent("定位到您在"+address+"，请选择学校")
                            .setOnLeftListener("取消", new CommonDialog.OnLeftClickListener() {
                                @Override
                                public void onClick(CommonDialog dialog) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnRightListener("设置", new CommonDialog.OnRightClickListener() {
                                @Override
                                public void onClick(CommonDialog dialog) {
                                    dialog.dismiss();
                                    p.getContract().getTaskList();
                                }
                            }).create().show();
                } else {
                    Helper.showToast("定位失败！");
                    p.getContract().getTaskList();
                }

            }

            @Override
            public void setTaskList(List<TaskListEntity> list) {
                taskAdapter = new TaskAdapter(R.layout.item_task, list);
                taskAdapter.addHeaderView(getHeader());
                rvTask.setAdapter(taskAdapter);
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
        unbinder.unbind();
    }
}
