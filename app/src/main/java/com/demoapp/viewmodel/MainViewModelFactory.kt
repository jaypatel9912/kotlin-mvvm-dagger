package com.demoapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demoapp.database.DataDuo
import com.demoapp.retrofit.RetrofitInterface
import com.demoapp.scope.CommonScope
import java.lang.IllegalArgumentException
import javax.inject.Inject

@CommonScope
class MainViewModelFactory @Inject constructor(val application: Application, val dataDuo: DataDuo, private val mService: RetrofitInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(application, dataDuo, mService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}