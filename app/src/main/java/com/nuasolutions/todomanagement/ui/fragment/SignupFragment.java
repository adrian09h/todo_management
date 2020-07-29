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
import com.nuasolutions.todomanagement.databinding.FragmentSignupBinding;
import com.nuasolutions.todomanagement.utils.ContextUtils;
import com.nuasolutions.todomanagement.viewmodel.LoginViewModel;
import com.nuasolutions.todomanagement.viewmodel.SignupViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import java.util.List;

import javax.inject.Inject;
import dagger.android.support.AndroidSupportInjection;

public class SignupFragment extends BaseFragment {
    @Inject
    ViewModelFactory viewModelFactory;

    private SignupViewModel mViewModel;
    private FragmentSignupBinding mBinding;

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSignupBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        initViews();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SignupViewModel.class);
        mViewModel.getAccessTokenLiveData().observe(this, resource -> {
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
        activity.setTitle(getString(R.string.signuptitle));
        mBinding.setSignupEnabled(mViewModel.getSignupEnabled());

        mBinding.emailInput.setListener((text, isValid) -> {
            boolean passwordMatched = mBinding.passwordFirst.password.equals(mBinding.passwordConfirm.password);
            mViewModel.setSingupEnabled(mBinding.emailInput.isValid
                && mBinding.passwordFirst.isValid
                && mBinding.passwordConfirm.isValid
                && mBinding.nameInput.isValid
                && passwordMatched
            );
        });

        mBinding.passwordFirst.setListener((text, isValid) -> {
            boolean passwordMatched = mBinding.passwordFirst.password.equals(mBinding.passwordConfirm.password);
            mViewModel.setSingupEnabled(mBinding.emailInput.isValid
                && mBinding.passwordFirst.isValid
                && mBinding.passwordConfirm.isValid
                && mBinding.nameInput.isValid
                && passwordMatched
            );
        });

        mBinding.passwordConfirm.setListener((text, isValid) -> {
            boolean passwordMatched = mBinding.passwordFirst.password.equals(mBinding.passwordConfirm.password);
            mViewModel.setSingupEnabled(mBinding.emailInput.isValid
                && mBinding.passwordFirst.isValid
                && mBinding.passwordConfirm.isValid
                && mBinding.nameInput.isValid
                && passwordMatched
            );
        });

        mBinding.nameInput.setListener((text, isValid) -> {
            boolean passwordMatched = mBinding.passwordFirst.password.equals(mBinding.passwordConfirm.password);
            mViewModel.setSingupEnabled(mBinding.emailInput.isValid
                && mBinding.passwordFirst.isValid
                && mBinding.passwordConfirm.isValid
                && mBinding.nameInput.isValid
                && passwordMatched
            );
        });

        mBinding.signupButton.setOnClickListener(view -> {
            ContextUtils.hideKeyboard(activity);
            displayLoader();
            mViewModel.signup(mBinding.nameInput.normalText, mBinding.emailInput.emailAddress,
                mBinding.passwordFirst.password, mBinding.passwordConfirm.password);
        });

        mBinding.gologin.setOnClickListener(view -> {
            ContextUtils.hideKeyboard(activity);
            goBackLogin();
        });
    }

    private void handleErrorResponse(Resource<List<AccessTokenEntity>> resource) {
        String error = resource.message;
        showErrorSnack(error);
    }

    private void goBackLogin() {
        NavHostFragment.findNavController(SignupFragment.this).popBackStack();
    }

    public void gotoTODOListPage() {
        NavHostFragment.findNavController(SignupFragment.this)
            .navigate(R.id.action_Signup_to_TodoList);
    }
}