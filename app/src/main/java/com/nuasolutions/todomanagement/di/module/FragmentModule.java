package com.nuasolutions.todomanagement.di.module;

import com.nuasolutions.todomanagement.ui.fragment.LoginFragment;
import com.nuasolutions.todomanagement.ui.fragment.TodoDetailFragment;
import com.nuasolutions.todomanagement.ui.fragment.TodoListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract TodoListFragment contributeTodoListFragment();

    @ContributesAndroidInjector
    abstract TodoDetailFragment contributeTodoDetailFragment();

}
