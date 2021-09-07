package com.demoapp.adapter

import android.view.ViewGroup
import com.demoapp.R
import com.demoapp.adapterutils.DelegateAdapter
import com.demoapp.adapterutils.LayoutViewHolder
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryViewDA : DelegateAdapter<CategoryViewDA.ViewHolder, CategoryViewRM> {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: CategoryViewRM) = holder.bind(item)

    inner class ViewHolder(parent: ViewGroup?) :
        LayoutViewHolder<CategoryViewRM>(parent!!, R.layout.item_category) {

        override fun bind(item: CategoryViewRM) {
            itemView.category_name.text = item.name
        }
    }

}
