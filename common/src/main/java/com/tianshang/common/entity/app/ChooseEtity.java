package com.tianshang.common.entity.app;

public class ChooseEtity {

    private String content;
    private String remark;
    private boolean isSelected;

    public ChooseEtity(String content, String remark, boolean isSelected) {
        this.content = content;
        this.remark = remark;
        this.isSelected = isSelected;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
