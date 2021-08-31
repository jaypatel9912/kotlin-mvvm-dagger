package com.demoapp

import android.app.Application
import com.demoapp.dagger.AppComponent
import com.demoapp.dagger.AppComponentProvider
import com.demoapp.dagger.AppModule
import com.demoapp.dagger.DaggerAppComponent

class App : Application() , AppComponentProvider{

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun getAppComponent(): AppComponent {
        return appComponent
    }

}