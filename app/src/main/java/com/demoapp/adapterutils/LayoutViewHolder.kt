package com.demoapp.adapterutils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class LayoutViewHolder<T>(parent: ViewGroup, @LayoutRes layoutResId: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)) {

    /**
     *  Method that allows an item object to be binded to the view.
     * @item - item of type <T>.
     */
    internal abstract fun bind(item: T)

}
