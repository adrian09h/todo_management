package com.nuasolutions.todomanagement.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.databinding.FragmentLoginBinding;
import com.nuasolutions.todomanagement.interfaces.TextChangedListener;
import com.nuasolutions.todomanagement.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.login_title));
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLoginEnabled(mViewModel.getLoginEnabled());
        binding.loginButton.setOnClickListener(view -> {

        });

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

        binding.gosignup.setOnClickListener(view -> {

        });
    }

}