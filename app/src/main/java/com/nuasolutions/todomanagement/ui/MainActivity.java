package com.nuasolutions.todomanagement.ui;

import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.nuasolutions.todomanagement.R;
import com.nuasolutions.todomanagement.databinding.MainAcitivityBinding;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.View;
import javax.inject.Inject;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    MainAcitivityBinding binding;

    private AppBarLayout appBarLayout;

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initialiseView();
    }

    private void initialiseView(){
        appBarLayout = binding.appbarLayout;
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
    }
    public void hideAppBar() {
        appBarLayout.setVisibility(View.GONE);
    }

    public void showAppBar() {
        appBarLayout.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        binding.progressBar.setVisibility(View.GONE);
    }
}