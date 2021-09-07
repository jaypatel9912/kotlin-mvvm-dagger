package com.demoapp.adapter

import com.demoapp.adapterutils.AdapterConstants
import com.demoapp.adapterutils.RecyclerViewType
import java.io.Serializable

class CategoryViewRM(var name: String?) : RecyclerViewType, Serializable {

    override fun getViewType(): Int = AdapterConstants.LIST_ITEM_CATEGORY

}
