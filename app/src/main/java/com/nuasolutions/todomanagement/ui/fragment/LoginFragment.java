package com.nuasolutions.todomanagement.ui.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nuasolutions.todomanagement.BuildConfig;
import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.data.Resource;
import com.nuasolutions.todomanagement.data.Status;
import com.nuasolutions.todomanagement.data.local.entity.AccessTokenEntity;
import com.nuasolutions.todomanagement.databinding.FragmentLoginBinding;
import com.nuasolutions.todomanagement.ui.MainActivity;
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
    private FragmentLoginBinding binding;
    private String emailAddress = "";
    private String password = "";
    private boolean isValidEmail = false;
    private boolean isValidPassword = false;
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
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.login_title));
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        mViewModel.getAccessToken().observe(this, resource -> {
            if (resource.isLoading()) {
                //TODO: show loading
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
                        gotoTODOListPage();
                    }
                } else {
                    handleErrorResponse(resource);
                }
            }
        });

        binding.setLoginEnabled(mViewModel.getLoginEnabled());

        binding.emailInput.setListener((text, isValid) -> {
            emailAddress = text;
            isValidEmail = isValid;
            mViewModel.setLoginEnabled(isValidEmail && isValidPassword);
        });

        binding.passwordInput.setListener((text, isValid) -> {
            password = text;
            isValidPassword = isValid;
            mViewModel.setLoginEnabled(isValidEmail && isValidPassword);
        });

        binding.loginButton.setOnClickListener(view -> {
            ContextUtils.hideKeyboard(activity);
            displayLoader();
            mViewModel.login(emailAddress, password, true);
        });

        binding.gosignup.setOnClickListener(view -> {

        });

        if (BuildConfig.DEBUG) {
            binding.emailInput.setText("tester1@email.com");
            binding.passwordInput.setText("tester1");
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