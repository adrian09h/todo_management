package com.nuasolutions.todomanagement.di.component;


import android.app.Application;

import com.nuasolutions.todomanagement.TODOApp;
import com.nuasolutions.todomanagement.di.module.ActivityModule;
import com.nuasolutions.todomanagement.di.module.ApiModule;
import com.nuasolutions.todomanagement.di.module.DbModule;
import com.nuasolutions.todomanagement.di.module.FragmentModule;
import com.nuasolutions.todomanagement.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
    ApiModule.class,
    DbModule.class,
    ViewModelModule.class,
    AndroidSupportInjectionModule.class,
    ActivityModule.class,
    FragmentModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule apiModule);

        @BindsInstance
        Builder dbModule(DbModule dbModule);

        AppComponent build();
    }

    void inject(TODOApp todoApp);
}
