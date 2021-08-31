package com.demoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_entity")
data class DataEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "image_link") val image_link: String,
    @ColumnInfo(name = "product_id") val product_id: String,
    @ColumnInfo(name = "category_name") val category_name: String,
    @ColumnInfo(name = "is_category") val is_category: Boolean,
)