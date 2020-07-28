package com.nuasolutions.todomanagement.di.module;


import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nuasolutions.todomanagement.viewmodel.LoginViewModel;
import com.nuasolutions.todomanagement.viewmodel.TodoListViewModel;
import com.nuasolutions.todomanagement.viewmodel.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    protected abstract ViewModel loginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TodoListViewModel.class)
    protected abstract ViewModel todoListViewModel(TodoListViewModel viewModel);
}