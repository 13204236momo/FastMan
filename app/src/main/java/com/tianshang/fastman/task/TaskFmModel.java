package com.tianshang.fastman.task;

import com.tianshang.common.base.mvp.BaseModelFm;

public class TaskFmModel extends BaseModelFm<TaskFmPresenter, TaskFmContract.Model> {
    public TaskFmModel(TaskFmPresenter taskFmPresenter) {
        super(taskFmPresenter);
    }

    @Override
    public TaskFmContract.Model getContract() {
        return null;
    }
}
