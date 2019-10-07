package com.tianshang.fastman.main;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.common.base.BaseFragment;
import com.tianshang.fastman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskFragment extends BaseFragment {

   @BindView(R.id.rv_task)
    RecyclerView rvTask;


    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_task;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);

        List<String> list = new ArrayList<>();
        list.add("任务1");
        list.add("任务2");
        list.add("任务3");
        list.add("任务4");
        list.add("任务5");
        list.add("任务6");
        list.add("任务7");

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvTask.setLayoutManager(manager);
        rvTask.setAdapter(new TaskAdapter(getContext(),list));


    }
}
