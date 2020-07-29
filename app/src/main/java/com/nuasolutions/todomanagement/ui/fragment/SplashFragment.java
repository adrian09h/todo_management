package com.nuasolutions.todomanagement.ui.fragment;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.viewmodel.SplashViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashFragment extends Fragment {

    @Inject
    ViewModelFactory viewModelFactory;
    private SplashViewModel mViewModel;
    private final int DELAY_MILLIS = 3000;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mGoNextRunnable = new Runnable() {
        @Override
        public void run() {
            getActivity().runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        if (BaseFragment.tempToken.isEmpty()) {
                            gotoLogin();
                        } else  {
                            gotoTodolist();
                        }
                    }
                }
            );
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentView = view.findViewById(R.id.contentView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel.class);
        mViewModel.getAccessTokenLiveData().observe(this, resource -> {
            if (resource.isLoading()) {
            } else {
                if (!resource.data.isEmpty()) {
                    String token = resource.data.get(0).getAccessToken();
                    Log.d(LoginFragment.class.getSimpleName(), "token:" + token);
                    BaseFragment.tempToken = token;
                } else {

                }
            }
        });
        mViewModel.checkAccessToken();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        hide();
        mHideHandler.postDelayed(mGoNextRunnable, DELAY_MILLIS);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            // Clear the systemUiVisibility flag
            getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
        }
        show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContentView = null;
    }

    private void gotoLogin() {
        NavHostFragment.findNavController(SplashFragment.this)
            .navigate(R.id.action_Splash_to_Login);
    }

    private void gotoTodolist() {
        NavHostFragment.findNavController(SplashFragment.this)
            .navigate(R.id.action_Splash_to_TodoList);
    }

    private void hide() {
        // Hide UI first
        int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        Activity activity = getActivity();
        if (activity != null
            && activity.getWindow() != null) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @SuppressLint("InlinedApi")
    private void show() {
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
    }


    @Nullable
    private ActionBar getSupportActionBar() {
        ActionBar actionBar = null;
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            actionBar = activity.getSupportActionBar();
        }
        return actionBar;
    }
}