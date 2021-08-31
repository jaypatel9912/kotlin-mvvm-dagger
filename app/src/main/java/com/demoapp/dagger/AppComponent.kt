package com.demoapp.dagger

import androidx.lifecycle.ViewModelProvider
import com.demoapp.retrofit.RetrofitModule
import com.demoapp.scope.CommonScope
import com.demoapp.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@CommonScope
@Component(modules = [AppModule::class, RetrofitModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun provideViewModelFactory(): ViewModelProvider.Factory
}