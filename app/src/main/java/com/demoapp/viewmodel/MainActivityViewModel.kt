package com.demoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoapp.adapterutils.RecyclerViewType
import com.demoapp.database.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private var _showLoader = MutableStateFlow(false)
    private var _message = MutableStateFlow("")

    val categoryList: Flow<List<RecyclerViewType>> = dataRepository.getLocalData()
    val showLoader: Flow<Boolean> = _showLoader
    val message: Flow<String> = _message

    fun setMessage(msg: String) {
        _message.value = msg
    }

    init {
        checkIfNeedToShoLoader()
        getDataFromApi()
    }


    private fun checkIfNeedToShoLoader() {
        viewModelScope.launch {
            if (categoryList.first().isNullOrEmpty())
                _showLoader.value = true
        }
    }

    private fun getDataFromApi() {
        viewModelScope.launch {
             dataRepository.getCategoryData()
            _showLoader.value = false
        }
    }

}
