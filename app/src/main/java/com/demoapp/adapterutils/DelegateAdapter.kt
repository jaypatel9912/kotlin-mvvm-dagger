package com.demoapp.adapterutils

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface DelegateAdapter<H : RecyclerView.ViewHolder?, T : RecyclerViewType?> {

    fun onCreateViewHolder(parent: ViewGroup?): H

    fun onBindViewHolder(holder: H, item: T)

    fun onBindViewHolder(holder: H, item: T, payload: List<Any?>?) {
        this.onBindViewHolder(holder, item)
    }

}
