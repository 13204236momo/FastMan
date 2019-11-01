package com.tianshang.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianshang.common.R;


public class PictureDialog extends Dialog {

    private TextView tvTake;
    private TextView tvGallery;

    public PictureDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_picture);

        initView();
    }

    private void initView() {
        tvTake = findViewById(R.id.tv_take);
        tvGallery = findViewById(R.id.tv_gallery);

        tvTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!= null){
                    listener.onClickTop();
                    dismiss();
                }
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickBottom();
                    dismiss();
                }
            }
        });
    }

    public interface OnClickListener{
        void onClickTop();
        void onClickBottom();
    }
    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        if (listener != null){
            this.listener = listener;
        }
    }
}
