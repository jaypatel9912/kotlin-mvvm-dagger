package com.demoapp.dagger

import android.app.Application
import android.content.Context
import com.demoapp.database.AppDatabase
import com.demoapp.database.DataDuo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun getDataDao(appDatabase: AppDatabase): DataDuo {
        return appDatabase.getDataDao()
    }

    @Singleton
    @Provides
    fun getRoomDBInstance(): AppDatabase {
        return AppDatabase.getDatabaseInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }

}
