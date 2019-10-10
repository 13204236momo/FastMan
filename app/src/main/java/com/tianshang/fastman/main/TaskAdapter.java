package com.tianshang.fastman.main;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianshang.common.entity.app.TaskListEntity;
import com.tianshang.fastman.R;

import java.util.List;

public class TaskAdapter extends BaseQuickAdapter<TaskListEntity,BaseViewHolder> {
    public TaskAdapter(int id, List<TaskListEntity> data) {
        super(id, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TaskListEntity item) {
        helper.setText(R.id.tv_content,item.getContent());
        Glide.with(mContext).load(item.getProfile()).into((ImageView) helper.getView(R.id.iv_profile));

    }
}


