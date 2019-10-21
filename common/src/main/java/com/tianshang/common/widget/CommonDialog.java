package com.tianshang.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianshang.common.R;


public class CommonDialog extends Dialog {


    private CommonDialog(@NonNull Context context) {
        this(context, 0);
    }

    private CommonDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    public interface OnLeftClickListener {
        void onClick(CommonDialog dialog);
    }

    public interface OnRightClickListener{
        void onClick(CommonDialog dialog);
    }


    public static class Builder {

        private Context context;
        private String title;
        private String content;
        private String leftText;
        private String rightText;
        private CommonDialog.OnLeftClickListener leftClickListener;
        private CommonDialog.OnRightClickListener rightClickListener;
        public static CommonDialog dialog;

        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvLeft;
        private TextView tvRight;

        private static CommonDialog getDialog(Context context){
            if (dialog == null){
                synchronized (CommonDialog.class){
                    if (dialog == null){
                        dialog = new CommonDialog(context);
                    }
                }
            }
            return dialog;
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        public CommonDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CommonDialog dialog = getDialog(context);
            View view = inflater.inflate(R.layout.dialog_common, null);

            tvTitle = view.findViewById(R.id.tv_title);
            tvContent = view.findViewById(R.id.tv_content);
            tvLeft = view.findViewById(R.id.tv_cancel);
            tvRight = view.findViewById(R.id.tv_confirm);

            tvTitle.setText(title);
            tvContent.setText(content);


            if (tvLeft != null) {
                tvLeft.setText(leftText);
                if (leftClickListener != null) {
                    tvLeft.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            leftClickListener.onClick(dialog);
                        }
                    });
                }
            } else {
                tvLeft.setVisibility(View.GONE);
            }


            if (rightText != null) {
                tvRight.setText(rightText);
                if (rightClickListener != null) {
                    tvRight.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            rightClickListener.onClick(dialog);
                        }
                    });
                }
            } else {
               tvRight.setVisibility(View.GONE);
            }
            dialog.setContentView(view);
            return dialog;
        }


        public Builder setOnLeftListener(String leftText,OnLeftClickListener leftListener){
            if (leftListener != null){
                this.leftText = leftText;
                leftClickListener = leftListener;
            }
            return this;
        }

        public Builder setOnRightListener(String rightText,OnRightClickListener rightListener){
            if (rightListener != null){
                this.rightText = rightText;
                rightClickListener = rightListener;
            }
            return this;
        }


    }
}
