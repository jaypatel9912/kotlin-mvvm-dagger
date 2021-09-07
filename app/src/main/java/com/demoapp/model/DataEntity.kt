package com.demoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "data_entity")
data class DataEntity(
    @PrimaryKey @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "price") val price: Double = 0.0,
    @SerializedName("image_link") @ColumnInfo(name = "image_link") val imageLink: String = "",
    @ColumnInfo(name = "product_id") val productId: String = "",
    @ColumnInfo(name = "category_name") val categoryName: String = ""
)
