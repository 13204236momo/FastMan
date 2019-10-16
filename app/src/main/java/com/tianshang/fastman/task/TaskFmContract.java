package com.tianshang.fastman.task;

import com.tianshang.common.base.mvp.BaseEntity;

public interface TaskFmContract {

    interface Model {
        // Model层子类完成方法的具体实现----------------2
        void getAddress() throws Exception;
    }

    interface View {
        // 真实的项目中，请求结果往往是以javabean--------------4
        void resultAddress(String address);
    }

    interface Presenter<T extends BaseEntity> {
        // 登录请求（接收到View层指令，可以自己做，也可以让Model层去执行）-----------1
        void requestAddress();

    }
}
