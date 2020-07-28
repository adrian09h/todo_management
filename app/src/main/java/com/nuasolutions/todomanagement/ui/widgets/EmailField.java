package com.nuasolutions.todomanagement.ui.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.interfaces.TextChangedListener;
import com.nuasolutions.todomanagement.utils.CommonUtils;

import java.util.Observable;

public class EmailField extends androidx.appcompat.widget.AppCompatEditText {
    @Nullable
    private String emailAddress;
    private boolean isValid = false;
    @Nullable
    private TextChangedListener listener;

    public EmailField(Context context) {
        super(context);
        initView(context, null, 0);
    }

    public EmailField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);

    }

    public EmailField(Context context, AttributeSet attrs, int defStyleAttr) {
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emailAddress = editable.toString();
                isValid = CommonUtils.validateEmail(emailAddress);
                if (!isValid) {
                    EmailField.this.setError(context.getString(R.string.err_email_invalid));
                } else {
                    EmailField.this.setError(null);
                }
                if (TextUtils.isEmpty(emailAddress)) {
                    EmailField.this.setError(null);
                }
                if (listener != null) {
                    listener.onTextChanged(emailAddress, isValid);
                }
            }
        });
    }
    public void setListener(TextChangedListener listener) {
        this.listener = listener;
    }
}
