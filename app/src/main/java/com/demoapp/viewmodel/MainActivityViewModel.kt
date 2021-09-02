package com.demoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demoapp.database.DataRepository
import com.demoapp.model.DataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private var _allCategoryList = MutableStateFlow<List<DataEntity>>(emptyList())
    private var _showLoader = MutableStateFlow(false)
    private var _message = MutableStateFlow("")

    val categoryList: Flow<List<DataEntity>> = _allCategoryList
    val showLoader: Flow<Boolean> = _showLoader
    val message: Flow<String> = _message

    fun setMessage(msg: String) {
        _message.value = msg
    }

    init {
        populateData()

        if (_allCategoryList.value.isNullOrEmpty())
            _showLoader.value = true

        viewModelScope.launch {
            dataRepository.getCategoryData()
            _showLoader.value = false
            populateData()
        }
    }

    private fun populateData() {
        viewModelScope.launch {
            val resultList = dataRepository.getLocalData()
            _allCategoryList.value = resultList
        }
    }
}