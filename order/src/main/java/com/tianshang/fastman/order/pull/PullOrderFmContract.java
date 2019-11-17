package com.tianshang.fastman.order.pull;

import com.tianshang.common.entity.app.TaskListEntity;

import java.util.List;

public interface PullOrderFmContract {

    interface View {
        void resultAddress(String address);
        void setTaskList(List<TaskListEntity> list);
    }

    interface Presenter {

        void getOrderList(int type);

    }
}
