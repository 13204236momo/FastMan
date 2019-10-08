package com.tianshang.common.Retrofit.result;

import java.io.Serializable;

public class BaseResult implements Serializable {
    /**
     * 服务器返回的状态码
     */
    private String status;
    /**
     * 服务器返回的消息
     */
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
