package com.tianshang.common.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tianshang.common.R;

public class BaseActivity extends AppCompatActivity {
    protected View contentView;
    private LinearLayout llContent;
    private LinearLayout llTitle;
    private TextView tvBaseTitle;
    private TextView tvBaseRight;
    private ImageView ivBaseLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initLayout();
        initEvent();
    }


    private void initLayout() {
        llTitle = findViewById(R.id.ll_title);
        llContent = findViewById(R.id.ll_content1);
        tvBaseTitle = findViewById(R.id.tv_base_title);
        tvBaseRight = findViewById(R.id.tv_base_right);
        ivBaseLeft = findViewById(R.id.iv_base_left);
    }

    private void initEvent(){
        ivBaseLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    public void setTitleBarVisible(boolean isShow){
        if (isShow){
            llTitle.setVisibility(View.VISIBLE);
        }else {
            llTitle.setVisibility(View.GONE);
        }
    }

    public void setTitle(String title){
        tvBaseTitle.setText(title);
    }

    public void setRightVisibility(boolean isShow){
        if (isShow){
            tvBaseRight.setVisibility(View.VISIBLE);
        }else {
            tvBaseRight.setVisibility(View.INVISIBLE);
        }
    }
}
