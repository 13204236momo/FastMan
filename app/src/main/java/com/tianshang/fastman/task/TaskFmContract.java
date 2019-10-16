package com.tianshang.fastman.task;

import com.tianshang.common.entity.app.TaskListEntity;

import java.util.List;

public interface TaskFmContract {

    interface View {
        void resultAddress(String address);
        void setTaskList(List<TaskListEntity> list);
    }

    interface Presenter {

        void requestAddress();
        void destroyLocation();
        void getTaskList(List<TaskListEntity> list);

    }
}
