package com.tianshang.common.entity.app;

import android.graphics.drawable.Drawable;

public class TaskListEntity {

    private String content;
    private int profile;

    public TaskListEntity(String content, int profile) {
        this.content = content;
        this.profile = profile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }
}
