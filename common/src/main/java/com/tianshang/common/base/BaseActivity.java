package com.tianshang.common.base;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.tianshang.common.R;

public class BaseActivity extends AppCompatActivity {
    protected View contentView;
    private LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initLayout();
    }


    private void initLayout() {
        llContent = findViewById(R.id.ll_content1);
    }


    /**
     * 加入页面内容布局
     *
     * @param layoutId
     */
    protected void contentView(int layoutId) {
        contentView = getLayoutInflater().inflate(layoutId, null);
        if (llContent.getChildCount() > 1) {
            llContent.removeAllViews();
        }
        if (contentView != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            llContent.addView(contentView, params);
        }
    }
}
