package com.nuasolutions.todomanagement.di.module;

import com.nuasolutions.todomanagement.ui.fragment.LoginFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

}
