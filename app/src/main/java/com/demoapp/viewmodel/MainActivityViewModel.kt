package com.demoapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoapp.database.DataDuo
import com.demoapp.database.DataRepository
import com.demoapp.model.DataEntity
import com.demoapp.retrofit.RetrofitInterface
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(dataDuo: DataDuo, mService: RetrofitInterface) :
    ViewModel() {

    private var dataRepository = DataRepository(dataDuo, mService)

    private var allCategoryList: MutableLiveData<List<DataEntity>> = MutableLiveData()
    fun getDataObserver(): LiveData<List<DataEntity>> {
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

        viewModelScope.launch {
            dataRepository.getCategoryData()
            showLoader.postValue(false)
            populateData()
        }
    }

    private fun populateData() {
        viewModelScope.launch {
            val resultList = dataRepository.getLocalData()
            allCategoryList.postValue(resultList)
        }
    }

}