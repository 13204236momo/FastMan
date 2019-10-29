package com.tianshang.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianshang.common.R;

import java.util.ArrayList;
import java.util.List;

public class VerificationView extends RelativeLayout {
    private Context context;
    private TextView tv_code1;
    private TextView tv_code2;
    private TextView tv_code3;
    private TextView tv_code4;
    private EditText et_code;
    private List<String> codes = new ArrayList<>();
    private InputMethodManager imm;

    public VerificationView(Context context) {
        super(context);
        this.context = context;
        loadView();
    }

    public VerificationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        loadView();
    }

    private void loadView() {
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.verification_code, this);
        initView(view);
        initEvent();
    }

    private void initView(View view) {
        tv_code1 = view.findViewById(R.id.tv_code1);
        tv_code2 = view.findViewById(R.id.tv_code2);
        tv_code3 = view.findViewById(R.id.tv_code3);
        tv_code4 = view.findViewById(R.id.tv_code4);
        et_code = view.findViewById(R.id.et_code);
    }

    private void initEvent() {
        //验证码输入
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable != null && editable.length() > 0) {
                    et_code.setText("");
                    if (codes.size() < 4) {
                        codes.add(editable.toString());
                        showCode();
                    }
                }
            }
        });
        // 监听验证码删除按键
        et_code.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN && codes.size() > 0) {
                    codes.remove(codes.size() - 1);
                    showCode();
                    return true;
                }
                return false;
            }
        });
       et_code.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               setColor();
           }
       });

    }

    /**
     * 显示输入的验证码
     */
    private void showCode() {
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        if (codes.size() >= 1) {
            code1 = codes.get(0);
        }
        if (codes.size() >= 2) {
            code2 = codes.get(1);
        }
        if (codes.size() >= 3) {
            code3 = codes.get(2);
        }
        if (codes.size() >= 4) {
            code4 = codes.get(3);
        }
        tv_code1.setText(code1);
        tv_code2.setText(code2);
        tv_code3.setText(code3);
        tv_code4.setText(code4);

        setColor();//设置高亮颜色
        callBack();//回调
    }

    /**
     * 设置高亮颜色
     */
    private void setColor() {
        int color_focus = Color.parseColor("#3F8EED");
        if (codes.size() == 0) {
            tv_code1.setText("—");
            tv_code1.setTextColor(color_focus);
        }
        if (codes.size() == 1) {
            tv_code2.setText("—");
            tv_code1.setTextColor(Color.WHITE);
            tv_code2.setTextColor(color_focus);
        }
        if (codes.size() == 2) {
            tv_code3.setText("—");
            tv_code1.setTextColor(Color.WHITE);
            tv_code2.setTextColor(Color.WHITE);
            tv_code3.setTextColor(color_focus);
        }
        if (codes.size() == 3) {
            tv_code4.setText("—");
            tv_code1.setTextColor(Color.WHITE);
            tv_code2.setTextColor(Color.WHITE);
            tv_code3.setTextColor(Color.WHITE);
            tv_code4.setTextColor(color_focus);
        }
          if (codes.size() == 4){
            tv_code1.setTextColor(Color.WHITE);
            tv_code2.setTextColor(Color.WHITE);
            tv_code3.setTextColor(Color.WHITE);
            tv_code4.setTextColor(Color.WHITE);
        }
    }

    /**
     * 回调
     */
    private void callBack() {
        if (onInputListener == null) {
            return;
        }
        if (codes.size() == 4) {
            onInputListener.onSucess(getPhoneCode());
        } else {
            onInputListener.onInput();
        }
    }

    //定义回调
    public interface OnInputListener {
        void onSucess(String code);

        void onInput();
    }

    private OnInputListener onInputListener;

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    /**
     * 显示键盘
     */
    public void showSoftInput() {
        //显示软键盘
        if (imm != null && et_code != null) {
            et_code.postDelayed(new Runnable() {
                @Override
                public void run() {
                    imm.showSoftInput(et_code, 0);
                }
            }, 200);
        }
    }

    /**
     * 获得手机号验证码
     *
     * @return 验证码
     */
    public String getPhoneCode() {
        StringBuilder sb = new StringBuilder();
        for (String code : codes) {
            sb.append(code);
        }
        return sb.toString();
    }
}
