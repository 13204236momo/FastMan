package com.tianshang.fastman.main;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
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

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.default_icon); //设置加载未完成时的占位图
        options.error(R.drawable.default_icon); //设置加载异常时的占位图
        Glide.with(mContext)
                .load(item.getProfile())
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into((ImageView) helper.getView(R.id.iv_profile));

    }
}


