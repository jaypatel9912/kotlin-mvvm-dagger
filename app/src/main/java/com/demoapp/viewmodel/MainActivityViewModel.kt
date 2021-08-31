package com.demoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.demoapp.App
import com.demoapp.retrofit.RetrofitInterface
import com.demoapp.database.DataDuo
import com.demoapp.database.DataRepository
import com.demoapp.model.DataEntity
import javax.inject.Inject

class MainActivityViewModel(application: Application, dataDuo: DataDuo, mService: RetrofitInterface) : AndroidViewModel(application) {

    private var dataRepository = DataRepository(dataDuo, mService)

    private var allCategoryList: MutableLiveData<List<DataEntity>> = MutableLiveData()
    fun getDataObserver(): MutableLiveData<List<DataEntity>> {
        return allCategoryList
    }

    private var showLoader: MutableLiveData<Boolean> = MutableLiveData()
    fun getLoaderObserver(): MutableLiveData<Boolean> {
        return showLoader
    }

    private var message: MutableLiveData<String> = MutableLiveData()
    fun getMessageObserver(): MutableLiveData<String> {
        return message
    }

    fun setMessage(msg: String) {
        message.postValue(msg)
    }

    init {
        populateData()

        if (allCategoryList.value.isNullOrEmpty())
            showLoader.postValue(true)

        dataRepository.getCategoryData(result = { isSuccess ->
            showLoader.postValue(false)
            if (!isSuccess)
                message.postValue("Something went wrong. Please try again.")
            else
                populateData()
        })
    }

    fun populateData() {
        dataRepository.getLocalData(result = { resultList ->
            allCategoryList.postValue(resultList)
        })
    }

}