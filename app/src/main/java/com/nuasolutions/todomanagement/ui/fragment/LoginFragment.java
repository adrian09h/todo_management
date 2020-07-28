package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nuasolutions.todomanagement.BuildConfig;
import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.Status;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.databinding.FragmentLoginBinding;
import com.nuasolutions.todomanagement.utils.ContextUtils;
import com.nuasolutions.todomanagement.viewmodel.LoginViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class LoginFragment extends BaseFragment {
    @Inject
    ViewModelFactory viewModelFactory;

    private LoginViewModel mViewModel;
    private FragmentLoginBinding mBinding;
    private String mEmailAddress = "";
    private String mPassword = "";
    private boolean mIsValidEmail = false;
    private boolean mIsValidPassword = false;
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initViews();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        mViewModel.getAccessToken().observe(this, resource -> {
            if (resource.isLoading()) {
            } else {
                hideLoader();
                if (!resource.data.isEmpty()) {
                    String token = resource.data.get(0).getAccessToken();
                    Log.d(LoginFragment.class.getSimpleName(), "token:" + token);
                    if (resource.status != Status.SUCCESS) {
                        //failed to get a response from server
                        handleErrorResponse(resource);
                    } else {
                        //successfully got a response from server
                        BaseFragment.tempToken = token;
                        gotoTODOListPage();
                    }
                } else {
                    handleErrorResponse(resource);
                }
            }
        });
    }

    private void initViews() {
        activity.setTitle(getString(R.string.login_title));
        mBinding.setLoginEnabled(mViewModel.getLoginEnabled());

        mBinding.emailInput.setListener((text, isValid) -> {
            mEmailAddress = text;
            mIsValidEmail = isValid;
            mViewModel.setLoginEnabled(mIsValidEmail && mIsValidPassword);
        });

        mBinding.passwordInput.setListener((text, isValid) -> {
            mPassword = text;
            mIsValidPassword = isValid;
            mViewModel.setLoginEnabled(mIsValidEmail && mIsValidPassword);
        });

        mBinding.loginButton.setOnClickListener(view -> {
            ContextUtils.hideKeyboard(activity);
            displayLoader();
            mViewModel.login(mEmailAddress, mPassword, true);
        });

        mBinding.gosignup.setOnClickListener(view -> {

        });

        if (BuildConfig.DEBUG) {
            mBinding.emailInput.setText("tester1@email.com");
            mBinding.passwordInput.setText("tester1");
        }
    }

    private void handleErrorResponse(Resource<List<AccessTokenEntity>> resource) {
        String error = resource.message;
        showErrorSnack(error);
    }

    public void gotoTODOListPage() {
        NavHostFragment.findNavController(LoginFragment.this)
            .navigate(R.id.action_Login_to_TodoList);
    }

}