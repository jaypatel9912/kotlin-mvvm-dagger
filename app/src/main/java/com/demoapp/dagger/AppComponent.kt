package com.demoapp.dagger

import com.demoapp.retrofit.RetrofitModule
import com.demoapp.scope.CommonScope
import com.demoapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@CommonScope
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

        fun inject(mainActivity: MainActivity)

}