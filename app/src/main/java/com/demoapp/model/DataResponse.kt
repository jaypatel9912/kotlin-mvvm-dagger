package com.demoapp.model

class DataResponse : ArrayList<DataResponseItem>()

data class DataResponseItem(
    val category: String,
    val items: List<DataEntity>
)
