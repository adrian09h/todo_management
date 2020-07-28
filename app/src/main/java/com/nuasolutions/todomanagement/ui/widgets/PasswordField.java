package com.nuasolutions.todomanagement.ui.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.interfaces.TextChangedListener;
import com.nuasolutions.todomanagement.utils.CommonUtils;

public class PasswordField extends androidx.appcompat.widget.AppCompatEditText {
    @Nullable
    private String password;
    private boolean isValid = false;
    @Nullable
    private TextChangedListener listener;

    public PasswordField(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public PasswordField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public PasswordField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void initView(Context context, @Nullable AttributeSet attributeSet, int defStyleAttr) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
                isValid = CommonUtils.validatePassword(password);
                if (!isValid) {
                    PasswordField.this.setError(context.getString(R.string.err_pwd_invalid));
                } else {
                    PasswordField.this.setError(null);
                }
                if (TextUtils.isEmpty(password)) {
                    PasswordField.this.setError(null);
                }
                if (listener != null) {
                    listener.onTextChanged(password, isValid);
                }
            }
        });
    }
    public void setListener(TextChangedListener listener) {
        this.listener = listener;
    }
}
