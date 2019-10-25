package com.tianshang.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianshang.common.R;
import com.tianshang.common.entity.app.ChooseEtity;
import com.tianshang.common.utils.DisplayUtil;

import java.util.List;

public class SelectDialog extends Dialog {
    private TextView tvTitle;
    private RecyclerView rvContent;

    private List<ChooseEtity> list;
    private SelectAdapter adapter;
    private String title;
    private int backgroundColor;

    public SelectDialog(@NonNull Context context,List<ChooseEtity> list) {
        this(context, R.style.BottomDialog);
        this.list = list;
    }

    public SelectDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_time);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.BottomDialog_Animation);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DisplayUtil.getRealScreenRelatedInformation(getContext())[1] / 2;
        window.setAttributes(params);

        intiView();
    }

    private void intiView() {
        tvTitle = findViewById(R.id.tv_title);
        rvContent = findViewById(R.id.rv_content);

        tvTitle.setText(title);
        tvTitle.setBackgroundColor(backgroundColor);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvContent.setLayoutManager(manager);
        adapter = new SelectAdapter(list);
        rvContent.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (ChooseEtity entity : list){
                    entity.setSelected(false);
                }
                list.get(position).setSelected(true);
                adapter.notifyDataSetChanged();

                if (listener!=null){
                    listener.onSelected(position);
                }
                dismiss();

            }
        });
    }

    private OnSelectedListener listener;
    public interface OnSelectedListener{
        void onSelected(int position);
    }
    public void setOnSelectedListener(OnSelectedListener listener){
        if (listener != null){
            this.listener = listener;
        }
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setTitleBackgroundColor(int color){
        this.backgroundColor = color;
    }


    public class SelectAdapter extends BaseQuickAdapter<ChooseEtity, BaseViewHolder> {

        public SelectAdapter(@Nullable List<ChooseEtity> data) {
            super(R.layout.item_select_dialog, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, ChooseEtity item) {
            helper.setText(R.id.tv_content, item.getContent());
            if (item.getRemark()!=null && !item.getRemark().equals("")){
                helper.setText(R.id.tv_remark,item.getRemark());
            }else {
                helper.setText(R.id.tv_remark,"");
            }

            if (item.isSelected())
                helper.setVisible(R.id.iv_choose, true);
            else
                helper.setVisible(R.id.iv_choose, false);
        }
    }
}
