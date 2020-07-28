package com.nuasolutions.todomanagement;

import android.app.Activity;
import android.app.Application;

import com.nuasolutions.todomanagement.di.module.ApiModule;
import com.nuasolutions.todomanagement.di.module.DbModule;
import com.nuasolutions.todomanagement.di.component.DaggerAppComponent;
import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


public class TODOApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .apiModule(new ApiModule())
                .dbModule(new DbModule())
                .build()
            .inject(this);
    }
}
