package com.tianshang.fastman.mine;

import com.tianshang.common.entity.app.TaskListEntity;

import java.util.List;

public interface MineFmContract {

    interface View {
        void resultAddress(String address);
        void setTaskList(List<TaskListEntity> list);
    }

    interface Presenter {

        void requestAddress();
        void destroyLocation();
        void getTaskList();

    }
}
