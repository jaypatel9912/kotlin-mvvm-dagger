package com.demoapp.adapterutils

import android.content.Context

/**
 * Recycler adapter used to display the different More screen sections.
 * @author roger.cotrina, VELEJ097
 */
open class BaseAdapter(map: HashMap<Int, DelegateAdapter<*, *>>, val context: Context) :
    GenericBaseRecyclerViewAdapter<RecyclerViewType>(map) {

    /**
     * Adds items on the Adapter.
     * @param list [List<RecyclerViewType>]: items that will be displayed on the screen
     */
    fun setItems(list: List<RecyclerViewType>) {
        items.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    /**
     * Add an item for the list. We can specify the index if it's need or not send the index if
     * it's needed to add it at the end
     * @param item: The item that will be added to the list
     * @param index: the index to add the item
     */
    fun addItem(item: RecyclerViewType, index: Int? = null) {
        items.apply {
            index?.let {
                add(it, item)
                notifyItemChanged(it)
            } ?: run {
                add(item)
                notifyDataSetChanged()
            }
        }
    }

    /**
     * Replace an item from list
     * @param index: Indicate the position that will be replaced
     */
    fun setItem(index: Int, item: RecyclerViewType) {
        items.apply {
            set(index, item)
            notifyItemChanged(index)
        }
    }

    /**
     * Remove an item from the list
     * @param index: Indicate the position that will be removed
     */
    fun removeItem(index: Int) {
        items.apply {
            removeAt(index)
            notifyItemChanged(index)
        }
    }

}
