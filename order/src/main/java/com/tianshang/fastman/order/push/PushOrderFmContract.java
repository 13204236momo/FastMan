package com.tianshang.fastman.order.push;

import com.tianshang.common.entity.app.TaskListEntity;

import java.util.List;

public interface PushOrderFmContract {

    interface View {
        void resultAddress(String address);
        void setTaskList(List<TaskListEntity> list);
    }

    interface Presenter {

        void getOrderList(int type);

    }
}
