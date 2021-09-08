package com.demoapp.adapterutils

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

open class GenericBaseRecyclerViewAdapter<T : RecyclerViewType?> :
    RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var delegateAdapters: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>>
    protected val items: MutableList<T> = mutableListOf()

    constructor() {
        delegateAdapters = SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>>()
    }

    @JvmName("getItems1")
    fun getItems(): List<T> {
        return items
    }

    @JvmName("getDelegateAdapters1")
    fun getDelegateAdapters(): SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>> {
        return delegateAdapters
    }

    @JvmName("setDelegateAdapters1")
    fun setDelegateAdapters(delegateAdapters: SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>>) {
        this.delegateAdapters = delegateAdapters
    }

    constructor(delegateAdapterMap: HashMap<Int, DelegateAdapter<*, *>>) {
        delegateAdapters = SparseArrayCompat<DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>>(delegateAdapterMap.size)
        val var2: Iterator<*> = delegateAdapterMap.entries.iterator()
        while (var2.hasNext()) {
            val delegateAdapterEntry: Map.Entry<*, *> =
                var2.next() as Map.Entry<*, *>
            delegateAdapters.put(delegateAdapterEntry.key as Int,
                delegateAdapterEntry.value as DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>?
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegateAdapter: DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>? = delegateAdapters[viewType]
        return delegateAdapter?.onCreateViewHolder(parent)
            ?: throw IllegalStateException("The method onCreateViewHolder cannot return null")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegateAdapter: DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>? = delegateAdapters[getItemViewType(position)]
        if (delegateAdapter != null) {
            delegateAdapter.onBindViewHolder(holder, items[position] as RecyclerViewType)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        val delegateAdapter: DelegateAdapter<RecyclerView.ViewHolder, RecyclerViewType>? = delegateAdapters[getItemViewType(position)]
        if (delegateAdapter != null) {
            if (CollectionsUtils.isNullOrEmpty(payloads)) {
                this.onBindViewHolder(holder, position)
            } else {
                delegateAdapter.onBindViewHolder(
                    holder,
                    items[position] as RecyclerViewType,
                    payloads
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return (items[position] as RecyclerViewType).getViewType()
    }

    fun addViewTypeOnceAndNotify(viewType: T) {
        this.addViewTypeOnceAndNotify(items.size, viewType)
    }

    fun addViewTypeOnceAndNotify(position: Int, viewType: T) {
        val viewTypePosition = items.indexOf(viewType)
        if (viewTypePosition == -1) {
            items.add(position, viewType)
            notifyItemInserted(position)
        }
    }

    fun removeViewTypeAndNotify(viewType: T): Int {
        val viewTypePosition = items.indexOf(viewType)
        if (viewTypePosition != -1) {
            items.removeAt(viewTypePosition)
            notifyItemRemoved(viewTypePosition)
        }
        return viewTypePosition
    }

    fun getViewTypePosition(viewType: T): Int {
        return if (!CollectionsUtils.isNullOrEmpty(items)) items.indexOf(viewType) else -1
    }

    fun notifySectionChanged(viewType: T) {
        val viewTypePosition = getViewTypePosition(viewType)
        if (viewTypePosition != -1) {
            this.notifyItemChanged(viewTypePosition)
        }
    }

    fun clearItemsAndNotify() {
        if (!items.isEmpty()) {
            val size = items.size
            items.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    fun removeItemsAfterAndNotify(position: Int) {
        var pos = position
        ++pos
        if (pos > -1 && pos < items.size) {
            val itemsToRemove: List<T> = items.subList(pos, items.size)
            items.removeAll(itemsToRemove)
            notifyItemRangeRemoved(pos, itemsToRemove.size)
        }
    }

    fun removeItemsAfterAndNotify(viewType: T) {
        val viewPos = items.indexOf(viewType)
        this.removeItemsAfterAndNotify(viewPos)
    }

    fun setItemAndNotify(item: T) {
        items.clear()
        items.add(item)
        notifyDataSetChanged()
    }

    fun setItemsAndNotify(items: List<T>?) {
        this.items.clear()
        this.items.addAll(items!!)
        notifyDataSetChanged()
    }

    companion object {
        protected const val NO_POSITION = -1
    }

}
