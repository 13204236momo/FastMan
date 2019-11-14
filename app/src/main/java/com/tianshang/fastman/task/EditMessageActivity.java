package com.tianshang.fastman.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.tianshang.common.base.BaseActivity;
import com.tianshang.common.utils.Helper;
import com.tianshang.fastman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class EditMessageActivity extends BaseActivity {

    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tag)
    TagContainerLayout tagLayout;
    @BindView(R.id.tf)
    MaterialTextField tf;
    @BindView(R.id.et_task_no)
    EditText etTaskNo;

    private String tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_edit_message);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        tag = getIntent().getStringExtra("tag");
        if (tag.equals("task_content")){
            taskContent();
        }else if (tag.equals("task_no")){
            taskNo();
        }
    }

    private void taskNo() {
        setTitle("取件号");
        etMessage.setVisibility(View.GONE);
        tagLayout.setVisibility(View.GONE);
        tvEdit.setVisibility(View.GONE);
    }

    private void taskContent() {
        setTitle("任务描述");
        tf.setVisibility(View.GONE);

        List<String> tags = new ArrayList<>();
        tags.add("到南昌航空大学8栋天天快递取件");
        tags.add("去申通拿快递");
        tags.add("到中通取个件");
        tags.add("去三食堂买家常豆腐");

        tagLayout.setTags(tags);
        tagLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                etMessage.setText(text);
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onSelectedTagDrag(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {
                tagLayout.removeTag(position);
            }
        });
    }

    @OnClick({R.id.tv_base_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_base_right:
                Intent intent = new Intent();
                if (tag.equals("task_content")){
                    if (!etMessage.getText().toString().equals("")){
                        intent.putExtra("msg",etMessage.getText().toString());
                    }else {
                        Helper.showToast("任务描述信息不能为空");
                        return;
                    }
                }else if (tag.equals("task_no")){
                    if (etTaskNo.getText().toString().length()!=0){
                        intent.putExtra("msg",etTaskNo.getText().toString());
                    }else {
                        Helper.showToast("取件号不能为空");
                        return;
                    }
                }
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
