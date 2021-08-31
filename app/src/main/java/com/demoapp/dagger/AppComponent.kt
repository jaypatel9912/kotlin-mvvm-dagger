package com.demoapp.dagger

import com.demoapp.retrofit.RetrofitModule
import com.demoapp.viewmodel.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RetrofitModule::class])
interface AppComponent {

        fun inject(mainActivityViewModel: MainActivityViewModel)

}