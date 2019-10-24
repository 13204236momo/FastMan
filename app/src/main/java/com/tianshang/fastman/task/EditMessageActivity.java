package com.tianshang.fastman.task;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tianshang.common.base.BaseActivity;
import com.tianshang.fastman.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.lujun.androidtagview.TagContainerLayout;

public class EditMessageActivity extends BaseActivity {

    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tag)
    TagContainerLayout tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView(R.layout.activity_edit_message);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

        List<String> tags = new ArrayList<>();
        tags.add("到南昌航空大学8栋天天快递取件");
        tags.add("去申通拿快递");
        tags.add("到中通取个件");
        tags.add("去三食堂买家常豆腐");

        tag.setTags(tags);
        //tag.setOnCli
    }
}
